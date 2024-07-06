package org.tinyweb;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import androidx.annotation.NonNull;

import org.tinyweb.paths.Path;
import org.tinyweb.request.Request;
import org.tinyweb.response.Response;
import org.tinyweb.router.Router;
import org.tinyweb.views.View;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RouterUnitTest {
    static class Home extends View {
        public Home() {}
        @NonNull
        @Override
        public Response response(Request request) {
            return null;
        }
    }

    @Test
    public void testRouter() {
        // Setup routes
        List<Path> paths = new ArrayList<>();
        paths.add(new Path("/", View.class));

        Router router = new Router(paths);
        assertEquals(paths.get(0), router.match("/"));
        assertNull(router.match("/404"));
    }
}
