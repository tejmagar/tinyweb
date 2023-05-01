package org.tinyweb;

import org.junit.Test;
import org.tinyweb.conf.Configurations;
import org.tinyweb.urls.Path;
import org.tinyweb.urls.Routes;
import org.tinyweb.views.View;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

class HomeView extends View {

    @Override
    public TinyWeb.Response getResponse(TinyWeb.IHTTPSession request) {
        return null;
    }
}

public class ExampleUnitTest {
    @Test
    public void server_isRunning() throws IOException {
        Configurations configurations = new Configurations();
        configurations.get().put("context", "value");

        TinyWeb tinyWeb = new TinyWeb(8000);
        Routes routes = new Routes();
        routes.addRoute(new Path("/", new HomeView()));
        tinyWeb.setRoutes(routes);
        tinyWeb.start();

    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}