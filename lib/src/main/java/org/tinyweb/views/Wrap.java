package org.tinyweb.views;

import androidx.annotation.Nullable;

import org.tinyweb.paths.Path;
import org.tinyweb.request.Request;
import org.tinyweb.response.Response;

public interface Wrap {
    Response wrap(Request request, @Nullable  Class<? extends View> view);
}
