package org.tinyweb.urls;

import org.tinyweb.views.View;

public abstract class BasePath {
    private final String path;
    private final View view;

    public BasePath(String path, View view) {
        this.path = path;
        this.view = view;
    }

    public String getPath() {
        return path;
    }

    public View getView() {
        return view;
    }

    public abstract boolean isMatched(String currentUrl);
}
