# TinyWeb
TinyWeb is a simple Android library based on NanoHTTPD. This library handles routing and middlewares
features.


## Add jitpack in repositories

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

```

## Install gradle dependencies

### Without WebSocket
```
dependencies {
    implementation 'com.github.tejmagar:tinyweb:1.0.0'
    implementation 'org.nanohttpd:nanohttpd:2.3.1'
}
```

### With WebSocket

```
dependencies {
    implementation 'com.github.tejmagar:tinyweb:1.0.0'
    implementation 'org.nanohttpd:nanohttpd:2.3.1'
}
```

## Creating a View

```
import org.tinyweb.views.View;
import fi.iki.elonen.NanoHTTPD;

public class HomeView extends View {

    @Override
    public NanoHTTPD.Response getResponse(NanoHTTPD.IHTTPSession request) {
        return NanoHTTPD.newFixedLengthResponse("Hello World");
    }
}


## Creating a server
```        
TinyWeb tinyWeb = new TinyWeb(8000);
Routes routes = new Routes();
routes.addRoute(new Path("/", new HomeView()));
tinyWeb.setRoutes(routes);

try {
    tinyWeb.start();
} catch (IOException e) {
    throw new RuntimeException(e);
}
```

## Creating a server with WebSocket 
```

### Creating a WsView
```

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

```

### Create a Server
```
Routes routes = new Routes();
routes.addRoute(new Path("/", new HomeView()));

Routes wsRoutes = new Routes();
wsRoutes.addRoute(new Path("/ws/", new MyWsView()));

TinyWebWS tinyWeb = new TinyWebWS(8000);
tinyWeb.setRoutes(routes);
tinyWeb.setWsRoutes(wsRoutes);
```

## Tips
Running server in foreground service helps to serve pages in background even after closing app.


## Web