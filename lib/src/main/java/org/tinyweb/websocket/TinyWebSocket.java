package org.tinyweb.websocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoWSD;

public  class TinyWebSocket extends NanoWSD.WebSocket {

    private Timer timer;

    public TinyWebSocket(NanoHTTPD.IHTTPSession handshakeRequest) {
        super(handshakeRequest);
    }

    private void ping() {
        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    ping("ping".getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        // Send a ping message every 3 seconds
        timer.schedule(timerTask, 0, 3000);
    }

    @Override
    protected void onOpen() {
        ping();
    }

    @Override
    protected void onPong(NanoWSD.WebSocketFrame pongFrame) {
    }

    @Override
    protected void onMessage(NanoWSD.WebSocketFrame messageFrame) {
    }

    @Override
    protected void onClose(NanoWSD.WebSocketFrame.CloseCode code, String reason,
                           boolean initiatedByRemote) {

        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onException(IOException e) {
        if (timer != null) {
            timer.cancel();
        }
    }
}