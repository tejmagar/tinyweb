package org.tinyweb.urls;

import org.tinyweb.views.View;

public class Path extends BasePath {
    public Path(String path, View view) {
        super(path, view);
    }

    @Override
    public boolean isMatched(String currentUrl) {
        return this.getPath().equals(currentUrl);
    }
}
