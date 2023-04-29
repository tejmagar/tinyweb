package org.tinyweb.middlewares;

import org.tinyweb.TinyWeb;

public abstract class Middleware {
    public abstract TinyWeb.Response getResponse(TinyWeb.IHTTPSession request);
}
