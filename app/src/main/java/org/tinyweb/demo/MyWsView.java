package org.tinyweb.demo;

import org.tinyweb.views.WsView;
import org.tinyweb.websocket.TinyWebSocket;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public class MyWsView extends WsView {

    @Override
    public NanoWSD.WebSocket webSocket(NanoHTTPD.IHTTPSession request) {
        return new MyTinyWebSocket(request);
    }

    static class MyTinyWebSocket extends TinyWebSocket {

        public MyTinyWebSocket(NanoHTTPD.IHTTPSession handshakeRequest) {
            super(handshakeRequest);
        }

        @Override
        protected void onOpen() {
            super.onOpen();
            try {
                send("Connected");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void onMessage(NanoWSD.WebSocketFrame messageFrame) {
            super.onMessage(messageFrame);
            System.out.println(messageFrame.getTextPayload());
        }
    }
}
