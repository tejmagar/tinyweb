package org.tinyweb.views;


import org.tinyweb.TinyWeb;

public abstract class View extends BaseView {
    public abstract TinyWeb.Response getResponse(TinyWeb.IHTTPSession request);
}
