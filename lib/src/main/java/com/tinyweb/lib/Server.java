package com.tinyweb.lib;

import static com.tinyweb.lib.request.RequestParser.parse;

import com.tinyweb.lib.headers.Headers;
import com.tinyweb.lib.request.Request;
import com.tinyweb.lib.stream.SocketStream;
import com.tinyweb.lib.stream.Stream;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private boolean isRunning = true;
    private int bufferSize = 8096;

    public Server(int port) {
        this.port = port;
    }

    /**
     * TCP Stream read buffer size
     * @param bufferSize - Buffer Size
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
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
        while(isRunning) {
            Socket socket = serverSocket.accept();

            if(!isRunning) {
                /// Shutdown instruction is already received.
                break;
            }

            Thread thread = new Thread(() -> {
                handleConnection(socket);
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
            // Used for injecting additional headers.
            Headers responseHeaders = new Headers();

            try {
                Request request = parse(stream, responseHeaders);
            } catch (Exception e) {
                e.printStackTrace();
                stream.shutdown();
                keepAlive = false;
                continue;
            }

            System.out.println("Received");
        }
    }
}
