package org.tinyweb.response;

import androidx.annotation.NonNull;

import org.tinyweb.headers.Headers;
import org.tinyweb.response.status.Status;
import org.tinyweb.response.status.StatusUtil;

import java.nio.charset.StandardCharsets;

public class HttpResponse extends Response {
    private final Headers headers = new Headers();
    private final String text;
    private boolean serveDefault = true;

    private final StatusUtil.ResponseStatus responseStatus;

    public HttpResponse(String text) {
        this.responseStatus = StatusUtil.fromStatus(Status.OK);
        this.text = text;
        initHeaders();
    }

    public HttpResponse(Status status, String text) {
        this.responseStatus = StatusUtil.fromStatus(status);
        this.text = text;
        initHeaders();
    }

    private void initHeaders() {
        headers.set("Content-Length", String.valueOf(text.length()));
        headers.set("Content-Type", "text/html");
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
        return serveDefault;
    }

    @Override
    public void disableServeDefault() {
        serveDefault = false;
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
