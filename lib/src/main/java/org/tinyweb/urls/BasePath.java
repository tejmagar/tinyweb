package org.tinyweb.urls;

import org.tinyweb.views.BaseView;

public abstract class BasePath {
    private final String path;
    private final BaseView view;

    public BasePath(String path, BaseView view) {
        this.path = path;
        this.view = view;
    }

    public String getPath() {
        return path;
    }

    public BaseView getView() {
        return view;
    }

    public abstract boolean isMatched(String currentUrl);
}
