package org.tinyweb.lib.views;

import androidx.annotation.Nullable;

import org.tinyweb.lib.paths.Path;
import org.tinyweb.lib.request.Request;
import org.tinyweb.lib.response.Response;

public interface Wrap {
    Response wrap(Request request, @Nullable  Class<? extends View> view);
}
