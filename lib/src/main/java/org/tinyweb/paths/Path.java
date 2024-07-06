package org.tinyweb.paths;

import androidx.annotation.NonNull;

import org.tinyweb.request.Request;
import org.tinyweb.response.Response;
import org.tinyweb.views.View;

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
