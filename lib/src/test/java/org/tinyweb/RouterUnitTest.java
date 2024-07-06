package org.tinyweb.lib;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

import androidx.annotation.NonNull;

import org.tinyweb.lib.paths.Path;
import org.tinyweb.lib.request.Request;
import org.tinyweb.lib.response.Response;
import org.tinyweb.lib.router.Router;
import org.tinyweb.lib.views.View;

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
