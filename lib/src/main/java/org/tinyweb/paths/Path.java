package org.tinyweb.lib.paths;

import androidx.annotation.NonNull;

import org.tinyweb.lib.request.Request;
import org.tinyweb.lib.response.Response;
import org.tinyweb.lib.views.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Path {
    private final String pattern;
    private final Class<? extends View> view;

    public Path(String pattern, Class<? extends View> view) {
        this.pattern = pattern;
        this.view = view;
    }

    public String getPattern() {
        return pattern;
    }

    public Class<? extends View> getView() {
        return view;
    }

    public static @NonNull Response resolve(Request request, Class<? extends View> viewClass)
        throws IllegalAccessException, InstantiationException {
        View view = viewClass.newInstance();
        return view.response(request);
    }
}
