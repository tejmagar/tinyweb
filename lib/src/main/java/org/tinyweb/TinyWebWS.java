package org.tinyweb;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

import org.tinyweb.conf.Configurations;
import org.tinyweb.handlers.HttpHandler;
import org.tinyweb.handlers.WsHandler;
import org.tinyweb.middlewares.Middlewares;
import org.tinyweb.urls.Routes;

import java.io.IOException;

public class TinyWebWS extends NanoWSD {
    private Routes routes;
    private Routes wsRoutes;

    private Middlewares middlewares;
    private HttpHandler httpHandler;
    private WsHandler wsHandler;


    private Configurations configurations;

    public TinyWebWS(int port) {
        super(port);
    }

    public TinyWebWS(String hostname, int port) {
        super(hostname, port);
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public void setWsRoutes(Routes routes) {
        this.wsRoutes = routes;
    }

    public void setMiddlewares(Middlewares middlewares) {
        this.middlewares = middlewares;
    }

    public void setConfigurations(Configurations configurations) {
        this.configurations = configurations;
    }

    public void start() throws IOException {
        httpHandler = new HttpHandler(routes, middlewares);
        wsHandler = new WsHandler(wsRoutes);
        super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    @Override
    protected WebSocket openWebSocket(IHTTPSession handshake) {
        return wsHandler.getWebSocket(handshake);
    }

    @Override
    public Response serveHttp(IHTTPSession session) {
        Response response = httpHandler.handleHttp(session, configurations);
        if (response != null) {
            return response;
        }
        return super.serve(session);
    }
}