package org.tinyweb.handlers;

import org.tinyweb.urls.BasePath;
import org.tinyweb.urls.Routes;
import org.tinyweb.views.WsView;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class WsHandler {
    private final Routes routes;

    public WsHandler(Routes routes) {
        this.routes = routes;
    }

    public NanoWSD.WebSocket getWebSocket(NanoHTTPD.IHTTPSession session) {
        BasePath path = routes.getMatchedPath(session.getUri());
        if (path != null) {
            WsView wsView = (WsView) path.getView();
            return wsView.webSocket(session);
        }

        return null;
    }
}
