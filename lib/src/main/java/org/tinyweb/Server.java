package org.tinyweb;

import org.tinyweb.headers.Headers;
import org.tinyweb.paths.Path;
import org.tinyweb.request.Request;
import org.tinyweb.request.RequestParser;
import org.tinyweb.response.HttpResponse;
import org.tinyweb.response.Response;
import org.tinyweb.response.status.Status;
import org.tinyweb.response.status.StatusUtil;
import org.tinyweb.router.Router;
import org.tinyweb.stream.SocketStream;
import org.tinyweb.stream.Stream;
import org.tinyweb.views.View;
import org.tinyweb.views.Wrap;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Server {
    private final int port;
    private boolean isRunning = true;
    private int bufferSize = 8096;

    private Router router;
    private Wrap wrap;

    public Server(int port) {
        this.port = port;
    }

    /**
     * TCP Stream read buffer size
     *
     * @param bufferSize - Buffer Size
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setRoutes(List<Path> paths) {
        router = new Router(paths);
    }

    public void wrap(Wrap wrap) {
        this.wrap = wrap;
    }

    /**
     * Blocks the current running thread.
     */
    public void run() {
        Thread thread = new Thread(() -> {
            try {
                runBlocking();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }

    public void runBlocking() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        while (isRunning) {
            Socket socket = serverSocket.accept();

            if (!isRunning) {
                /// Shutdown instruction is already received.
                break;
            }

            Thread thread = new Thread(() -> {
                try {
                    handleConnection(socket);
                } catch (Exception e) {
                    TinyWebLogging.error(e);
                }
            });

            thread.setDaemon(true);
            thread.start();
        }

        serverSocket.close();
    }

    /**
     * Stops listening for TCP connections by closing server socket.
     * Does not close forcefully existing connection.
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Handles each client's TCP connection.
     *
     * @param socket - TCP Socket
     */
    private void handleConnection(Socket socket) {
        Stream stream = new SocketStream(socket, bufferSize);
        boolean keepAlive = true;

        // Reuses existing connection if possible
        while (keepAlive) {
            try {
                // Used for injecting additional headers.
                Headers responseHeaders = new Headers();

                Request request = RequestParser.parse(stream, responseHeaders);
                Headers requestHeaders = request.headers;
                String connectionHeader = requestHeaders.value("connection");
                if (connectionHeader != null && connectionHeader.equalsIgnoreCase("close")) {
                    keepAlive = false;
                }

                if (request.httpVersion.equalsIgnoreCase("HTTP/1.0")) {
                    keepAlive = false;
                }

                Path path = null;
                if (router != null) {
                     path = router.match(request.pathName);
                }

                Response response;
                if (router !=null && path != null) {
                    Class<? extends View> view;
                    view = path.getView();

                    if (wrap == null) {
                        /*
                         * Creates new Response instance from class name by passing request object.
                         */
                        response = Path.resolve(request, view);
                    } else {
                        response = wrap.wrap(request, view);
                    }
                } else {
                    response = new HttpResponse(Status.NOT_FOUND, "404 NOT FOUND");
                }


                if (!keepAlive) {
                    response.headers().set("Connection", "close");
                } else {
                    if (response.headers().value("connection") == null) {
                        response.headers().set("Connection", "keep-alive");
                    }
                }

                if (response.serveDefault()) {
                    writeResponse(stream, response);
                }

                if (!keepAlive) {
                    stream.shutdown();
                }
            } catch (Exception e) {
                TinyWebLogging.error(e);
                stream.shutdown();
                return;
            }
        }
    }

    private static void writeResponse(Stream stream, Response response) throws Stream.StreamWriteException {
        StatusUtil.ResponseStatus responseStatus = response.responseStatus();
        byte[] responseStatusBytes = ("HTTP/1.1 " + responseStatus.getStatusCode() + " " +
                responseStatus.getStatusText() + "\r\n").getBytes(StandardCharsets.UTF_8);
        stream.writeChunk(responseStatusBytes);

        StringBuilder stringBuilder = new StringBuilder();
        Headers responseHeaders = response.headers();

        for (String headerName : responseHeaders.inner().keySet()) {
            List<String> values = responseHeaders.values(headerName);

            if (values != null) {
                for (String value : values) {
                    String headerLine = headerName + ": " + value + "\r\n";
                    stringBuilder.append(headerLine);
                }
            }
        }

        stringBuilder.append("\r\n");
        stream.writeChunk(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));

        byte[] responseBody = response.body();
        if (responseBody.length > 0) {
            stream.writeChunk(responseBody);
        }
    }
}
