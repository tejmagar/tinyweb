package org.tinyweb.views;


import org.tinyweb.TinyWeb;

public abstract class View {
    public abstract TinyWeb.Response getResponse(TinyWeb.IHTTPSession request);
}
