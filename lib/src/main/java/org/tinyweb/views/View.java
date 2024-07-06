package org.tinyweb.views;

import androidx.annotation.NonNull;

import org.tinyweb.request.Request;
import org.tinyweb.response.Response;

public abstract class View {
    public abstract @NonNull Response response(Request request);
}
