package org.tinyweb.urls;

import org.tinyweb.views.BaseView;

public class Path extends BasePath {
    public Path(String path, BaseView view) {
        super(path, view);
    }

    @Override
    public boolean isMatched(String currentUrl) {
        return this.getPath().equals(currentUrl);
    }
}
