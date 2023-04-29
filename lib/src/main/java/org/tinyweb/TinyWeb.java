package org.tinyweb;

import fi.iki.elonen.NanoHTTPD;
import org.tinyweb.middlewares.Middlewares;
import org.tinyweb.urls.BasePath;
import org.tinyweb.urls.Routes;
import org.tinyweb.views.View;

import java.io.IOException;

public class TinyWeb extends NanoHTTPD {
    private Routes routes;
    private Middlewares middlewares;

    public TinyWeb(int port) {
        super(port);
    }

    public void start() throws IOException {
        super.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
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

    @Override
    public Response serve(IHTTPSession session) {
        TinyWeb.Response response = middlewares.handleMiddleware(session);
        if (response != null) {
            return response;
        }

        String currentUrl = session.getUri();

        if (routes != null) {
            BasePath path = routes.getMatchedPath(currentUrl);

            if (path != null) {
                View view = path.getView();
                return view.getResponse(session);
            }
        }

        return super.serve(session);
    }
}