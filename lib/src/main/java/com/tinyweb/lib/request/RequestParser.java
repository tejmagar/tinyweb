package com.tinyweb.lib.request;

import com.tinyweb.lib.stream.Stream;
import com.tinyweb.lib.headers.Headers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {
    public static int MAX_REQUEST_HEADER_SIZE = 200 * 1024;

    static class HeaderTooLargeException extends Exception {
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
        int bodySize = buffer.size() - headerSize - 4;
        // Skip 4 bytes \r\n\r\n line breaks.
        int bodyOffset = headerEndPosition + 4;

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
            }
        }

        // Restore misread body bytes back to stream to read later.
        stream.restoreChunk(bodyBytes);

        String headerText = new String(headerBytes, StandardCharsets.UTF_8);
        System.out.println("Headers: " + headerText);
        String[] headerLines = headerText.split("\r\n");

        // Skips first line header as it contains request method, pathname and HTTP version.
        Headers headers = parseHeaderLines(headerLines, 1);
        return new Request(stream, "", "", "", headers, responseHeaders);
    }

    static int scanHeaderEnd(List<Byte> bytes) {
        /*
         * Searches \r\n\r\n CRLF line breaks.
         * Scanning (totalLength - \r\n\r\n length) bytes is enough.
         */

        for (int i = 0; i < bytes.size() - 4; i++) {
            if (bytes.get(i + 1) == 13 && bytes.get(i + 2) == 10 && bytes.get(i + 3) == 13 && bytes.get(i + 4) == 10) {
                return i;
            }
        }

        return -1;
    }

    /**
     * @param headerLines - Individual header key values line. Example: "Host: example.com"
     * @param offset      - Starts parsing from the given offset index.
     * @return Headers
     */
    static Headers parseHeaderLines(String[] headerLines, int offset) {
        return new Headers();
    }
}
