package org.tinyweb.lib;

import androidx.annotation.NonNull;

import org.tinyweb.lib.parsers.MultipartParser;
import org.tinyweb.lib.paths.Path;
import org.tinyweb.lib.request.Request;
import org.tinyweb.lib.response.HttpResponse;
import org.tinyweb.lib.response.Response;
import org.tinyweb.lib.stream.Stream;
import org.tinyweb.lib.views.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ServerTest {

    public static class Home extends View {

        public Home() {}

        @NonNull
        @Override
        public Response response(Request request) {
            MultipartParser multipartParser = new MultipartParser(request.headers, request.stream);
            try {
                MultipartParser.ParseResult parseResult = multipartParser.parse();
                System.out.println(parseResult.formData.inner());
            } catch (MultipartParser.MultipartParseException | Stream.StreamReadException |
                     IOException e) {
                return new HttpResponse(Objects.requireNonNull(e.getMessage()));
            }

            return new HttpResponse("Hello World");
        }
    }

//    @Test
    public void testServer() {
        TinyWebLogging.enableLogging = true;
        System.out.println("Running");
        Server server = new Server(8080);

        List<Path> paths = new ArrayList<>();
        paths.add(new Path("/", Home.class));
        server.setRoutes(paths);

        try {
            server.runBlocking();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
