package com.tinyweb.lib.paths;

import com.tinyweb.lib.views.View;

public class Path {
    private final String name;
    private final Class<? extends View> view;

    public Path(String name, Class<? extends View> view) {
        this.name = name;
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public Class<? extends View> getView() {
        return view;
    }
}
