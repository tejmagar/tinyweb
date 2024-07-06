package org.tinyweb.lib.response;

import androidx.annotation.NonNull;

import org.tinyweb.lib.headers.Headers;
import org.tinyweb.lib.response.status.Status;
import org.tinyweb.lib.response.status.StatusUtil;

import java.nio.charset.StandardCharsets;

public class HttpResponse extends Response {
    private final Headers headers = new Headers();
    private final String text;

    private final StatusUtil.ResponseStatus responseStatus;

    public HttpResponse(String text) {
        this.responseStatus = StatusUtil.fromStatus(Status.Ok);
        this.text = text;
        headers.set("Content-Length", String.valueOf(text.length()));
    }

    public HttpResponse(Status status, String text) {
        this.responseStatus = StatusUtil.fromStatus(status);
        this.text = text;
        headers.set("Content-Length", String.valueOf(text.length()));
    }

    @Override
    public StatusUtil.ResponseStatus responseStatus() {
        return responseStatus;
    }

    @NonNull
    @Override
    public Headers headers() {
        return headers;
    }

    @Override
    public boolean serveDefault() {
        return true;
    }

    @Override
    public boolean keepAlive() {
        return true;
    }

    @NonNull
    @Override
    public byte[] body() {
        return text.getBytes(StandardCharsets.UTF_8);
    }
}
