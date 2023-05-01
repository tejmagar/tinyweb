package org.tinyweb.demo;

import org.tinyweb.TinyWeb;
import org.tinyweb.views.View;

public class HomeView extends View {
    @Override
    public TinyWeb.Response getResponse(TinyWeb.IHTTPSession request) {
        return TinyWeb.newFixedLengthResponse("It's working");
    }
}
