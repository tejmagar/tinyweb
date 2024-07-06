package org.tinyweb.lib.views;

import androidx.annotation.NonNull;

import org.tinyweb.lib.request.Request;
import org.tinyweb.lib.response.Response;

public abstract class View {
    public abstract @NonNull Response response(Request request);
}
