package org.tinyweb.request;

import org.tinyweb.paths.PathUtil;
import org.tinyweb.stream.Stream;
import org.tinyweb.headers.Headers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RequestParser {
    public static int MAX_REQUEST_HEADER_SIZE = 200 * 1024;

    static class MalformedRequestException extends Exception {
    }

    static class HeaderTooLargeException extends Exception {
    }

    public static class RequestInfo {
        public String method;
        public String rawPath;
        public String pathName;
        public String httpVersion;
        public HashMap<String, List<String>> queryParams;
    }

    public static Request parse(Stream stream, Headers responseHeaders) throws Exception {

        // Unoptimized solution
        List<Byte> buffer = new ArrayList<>(MAX_REQUEST_HEADER_SIZE); // 200 KiB
        int headerEndPosition;

        while ((headerEndPosition = scanHeaderEnd(buffer)) == -1) {
            byte[] chunk = stream.readChunk();

            // Appends chunk to buffer
            for (byte b : chunk) {
                buffer.add(b);
            }
        }

        if (buffer.size() > MAX_REQUEST_HEADER_SIZE) {
            throw new HeaderTooLargeException();
        }

        int headerSize = headerEndPosition + 1;
        int bodySize = buffer.size() - headerSize - "\r\n\r\n".length();
        // Skip 4 bytes \r\n\r\n line breaks.
        int bodyOffset = headerEndPosition + "\r\n\r\n".length() + 1;

        byte[] headerBytes = new byte[headerSize];
        for (int i = 0; i < headerSize; i++) {
            headerBytes[i] = buffer.get(i);
        }

        // Copy misread bytes
        byte[] bodyBytes = new byte[bodySize];
        int bodyIndex = 0;

        // Checks if there is body bytes to copy.
        if (bodySize > 0) {
            for (int i = bodyOffset; i < buffer.size(); i++) {
                bodyBytes[bodyIndex] = buffer.get(i);
                bodyIndex++;
            }
        }

        // Restore misread body bytes back to stream to read later.
        stream.restoreChunk(bodyBytes);

        String headerText = new String(headerBytes, StandardCharsets.UTF_8);
        String[] headerLines = headerText.split("\r\n");

        RequestInfo requestInfo = parseRequestInfo(headerLines);
        if (requestInfo == null) {
            throw new MalformedRequestException();
        }

        Headers headers = parseRequestHeaders(headerLines);
        return new Request(stream, requestInfo.method, requestInfo.pathName,
                requestInfo.httpVersion, headers, responseHeaders, requestInfo.queryParams);
    }

    public static int scanHeaderEnd(List<Byte> bytes) {
        /*
         * Searches \r\n\r\n CRLF line breaks.
         * Scanning (totalLength - \r\n\r\n length) bytes is enough.
         */

        for (int i = 0; i < bytes.size() - 4; i++) {
            if (bytes.get(i + 1) == 13 && bytes.get(i + 2) == 10 && bytes.get(i + 3) == 13 &&
                    bytes.get(i + 4) == 10) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns RequestInfo object by parsing raw header lines.
     * Modifies request method and HTTP version to uppercase.
     *
     * @param rawHeaderLines - Header Lines
     * @return RequestInfo
     */
    public static RequestInfo parseRequestInfo(String[] rawHeaderLines) {
        if (rawHeaderLines.length == 0) {
            return null;
        }

        // Expected pattern: GET / HTTP/1.1
        String headerLine = rawHeaderLines[0];
        String[] parts = headerLine.split(" ");

        if (parts.length >= 3) {
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.method = parts[0].trim().toUpperCase();

            // Constructs raw path
            StringBuilder pathBuilder = new StringBuilder();
            for (int i = 1; i < parts.length - 1; i++) {
                // Add space back to path since it was split by space.
                if (i != 1) {
                    pathBuilder.append(" ");
                }

                pathBuilder.append(parts[i]);
            }

            requestInfo.rawPath = pathBuilder.toString();
            requestInfo.httpVersion = parts[parts.length - 1].trim().toUpperCase();

            PathUtil.PathParseResult result = PathUtil.parsePath(requestInfo.rawPath);
            requestInfo.pathName = result.pathName;
            requestInfo.queryParams = result.queryParams;
            return requestInfo;
        }

        return null;
    }

    /**
     * @param rawHeaderLines - Individual header lines. Example: "Host: example.com"
     * @return Headers
     */
    static Headers parseRequestHeaders(String[] rawHeaderLines) {
        Headers headers = new Headers();

        for (String headerLine : rawHeaderLines) {
            String[] keyValues = headerLine.split(":", 2);

            if (keyValues.length == 2) {
                String header = keyValues[0].trim();
                String value = keyValues[1].trim();
                headers.setMultiple(header, value);
            }
        }
        return headers;
    }
}
