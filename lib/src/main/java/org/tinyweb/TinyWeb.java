package org.tinyweb;

import fi.iki.elonen.NanoHTTPD;

import org.tinyweb.conf.Configurations;
import org.tinyweb.handlers.HttpHandler;
import org.tinyweb.middlewares.Middlewares;
import org.tinyweb.urls.Routes;
import java.io.IOException;

public class TinyWeb extends NanoHTTPD {
    private Routes routes;
    private Middlewares middlewares;
    private HttpHandler httpHandler;

    private Configurations configurations;

    public TinyWeb(int port) {
        super(port);
    }

    public TinyWeb(String hostname, int port) {
        super(hostname, port);
    }


    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public void setMiddlewares(Middlewares middlewares) {
        this.middlewares = middlewares;
    }

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }

    public void start() throws IOException {
        httpHandler = new HttpHandler(routes, middlewares);
        super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }


    @Override
    public Response serve(IHTTPSession session) {
        Response response = httpHandler.handleHttp(session, configurations);
        if (response != null) {
            return response;
        }
        return super.serve(session);
    }
}