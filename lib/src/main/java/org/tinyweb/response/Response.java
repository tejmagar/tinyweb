package org.tinyweb.lib.response;

import androidx.annotation.NonNull;

import org.tinyweb.lib.headers.Headers;
import org.tinyweb.lib.response.status.StatusUtil;

public abstract class Response {
    public abstract StatusUtil.ResponseStatus responseStatus();
    public abstract @NonNull Headers headers();
    public abstract boolean serveDefault();
    public abstract boolean keepAlive();
    public abstract @NonNull byte[] body();
}
