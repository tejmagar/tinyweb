package org.tinyweb.response;

import androidx.annotation.NonNull;

import org.tinyweb.headers.Headers;
import org.tinyweb.response.status.Status;
import org.tinyweb.response.status.StatusUtil;

import java.nio.charset.StandardCharsets;

public class JsonResponse extends Response {
    private final Headers headers = new Headers();
    private final String text;

    private final StatusUtil.ResponseStatus responseStatus;

    public JsonResponse(String text) {
        this.responseStatus = StatusUtil.fromStatus(Status.Ok);
        this.text = text;
        initHeaders();
    }

    public JsonResponse(Status status, String text) {
        this.responseStatus = StatusUtil.fromStatus(status);
        this.text = text;
        initHeaders();
    }

    private void initHeaders() {
        headers.set("Content-Length", String.valueOf(text.length()));
        headers.set("Content-Type", "application/json");
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
