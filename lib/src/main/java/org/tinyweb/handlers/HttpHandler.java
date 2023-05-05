package org.tinyweb.handlers;

import fi.iki.elonen.NanoHTTPD;

import org.tinyweb.conf.Configurations;
import org.tinyweb.middlewares.Middlewares;
import org.tinyweb.urls.BasePath;
import org.tinyweb.urls.Routes;
import org.tinyweb.views.BaseView;
import org.tinyweb.views.View;

public class HttpHandler {
    private final Routes routes;
    private final Middlewares middlewares;

    public HttpHandler(Routes routes, Middlewares middlewares){
        this.routes = routes;
        this.middlewares = middlewares;
    }

    public NanoHTTPD.Response handleHttp(NanoHTTPD.IHTTPSession session, Configurations configurations) {
        if (middlewares != null) {
            NanoHTTPD.Response response = middlewares.handleMiddleware(session);
            if (response != null) {
                return response;
            }
        }

        String currentUrl = session.getUri();

        if (routes != null) {
            BasePath path = routes.getMatchedPath(currentUrl);

            if (path != null) {
                BaseView view = path.getView();
                view.setConfigurations(configurations);
                return ((View)view).getResponse(session);
            }
        }

        return NanoHTTPD.newFixedLengthResponse("404 Page not found");
    }
}