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
```

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

## Tips
Running server in foreground service helps to serve pages in background even after closing app.
