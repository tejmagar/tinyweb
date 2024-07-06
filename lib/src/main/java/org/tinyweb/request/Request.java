package org.tinyweb.lib.request;

import org.tinyweb.lib.stream.Stream;
import org.tinyweb.lib.headers.Headers;

import java.util.HashMap;
import java.util.List;

public class Request {
    public final Stream stream;
    public final String method;
    public final String pathName;
    public final String httpVersion;

    public final Headers headers;

    public final HashMap<String, List<String>> queryParams;


    /**
     * Allows to modify response header from Request object.
     */
    public final Headers responseHeaders;

    public Request(Stream stream, String method, String pathName, String httpVersion,
                   Headers headers, Headers responseHeaders, HashMap<String, List<String>> queryParams) {
        this.stream = stream;
        this.method = method;
        this.pathName = pathName;
        this.httpVersion = httpVersion;
        this.headers = headers;
        this.responseHeaders = responseHeaders;
        this.queryParams = queryParams;
    }

    public void parseFile() {

    }
}
