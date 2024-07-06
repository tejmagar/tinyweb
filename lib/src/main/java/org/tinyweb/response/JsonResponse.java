package org.tinyweb.response;

import org.tinyweb.response.status.Status;

public class JsonResponse extends HttpResponse {

    public JsonResponse(String text) {
        super(text);
    }

    public JsonResponse(Status status, String text) {
        super(status, text);
        headers().set("Content-Type", "application/json");
    }
}
