package org.tinyweb.middlewares;

import org.tinyweb.TinyWeb;

import java.util.ArrayList;
import java.util.List;

public class Middlewares {
    private final List<Middleware> middlewares = new ArrayList<>();

    public void add(Middleware middleware) {
        this.middlewares.add(middleware);
    }

    public TinyWeb.Response handleMiddleware(TinyWeb.IHTTPSession session) {
        for (Middleware middleware : middlewares) {
            TinyWeb.Response response = middleware.getResponse(session);
            if (response != null) {
                return response;
            }
        }

        return null;
    }
}
