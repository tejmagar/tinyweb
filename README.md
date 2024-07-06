# TinyWeb

TinyWeb is a simple http server library for Android.

## Update settings.gradle

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

```

## Install gradle dependencies

```gradle
dependencies {
    implementation 'com.github.tejmagar:tinyweb:1.0.1-alpha'
}
```

## Creating a View

Create public class for each route.
Note: if the class is not public, the server may not behave as expected.

```java        
import androidx.annotation.NonNull;

import org.tinyweb.request.Request;
import org.tinyweb.response.HttpResponse;
import org.tinyweb.response.Response;
import org.tinyweb.views.View;

public class HomeView extends View {
    @NonNull
    @Override
    public Response response(Request request) {
        return new HttpResponse("Hello World");
    }
}
```

## Creating a server instance
```java
    // Other imports...

    import org.tinyweb.Server;
    import org.tinyweb.TinyWebLogging;
    import org.tinyweb.paths.Path;

    public class MainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // To see logs
            // TinyWebLogging.enableLogging = true;
            Server server = new Server(8080);
            server.addRoute(new Path("/", HomeView.class));
            server.run();
        }
    }
```

## Tips
Running server in foreground service helps to serve pages in background even after closing app.
