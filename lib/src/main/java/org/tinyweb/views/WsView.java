package org.tinyweb.views;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public abstract class WsView extends BaseView {
    public abstract NanoWSD.WebSocket webSocket(NanoHTTPD.IHTTPSession request);
}
