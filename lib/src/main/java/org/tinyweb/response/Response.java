package org.tinyweb.response;

import androidx.annotation.NonNull;

import org.tinyweb.headers.Headers;
import org.tinyweb.response.status.StatusUtil;

public abstract class Response {
    public abstract StatusUtil.ResponseStatus responseStatus();
    public abstract @NonNull Headers headers();
    public abstract boolean serveDefault();
    public abstract void disableServeDefault();

    public abstract boolean keepAlive();
    public abstract @NonNull byte[] body();
}
