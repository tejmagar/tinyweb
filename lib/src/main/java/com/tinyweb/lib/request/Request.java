package com.tinyweb.lib.request;

import com.tinyweb.lib.stream.Stream;
import com.tinyweb.lib.headers.Headers;

public class Request {
    public final Stream stream;
    public final String method;
    public final String pathName;
    public final String httpVersion;

    public final Headers headers;


    /**
     * Allows to modify response header from Request object.
     */
    public final Headers responseHeaders;

    public Request(Stream stream, String method, String pathName, String httpVersion,
                   Headers headers, Headers responseHeaders) {
        this.stream = stream;
        this.method = method;
        this.pathName = pathName;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.responseHeaders = responseHeaders;
    }
}
