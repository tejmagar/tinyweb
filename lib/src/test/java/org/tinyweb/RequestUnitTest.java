package org.tinyweb.lib;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.tinyweb.lib.request.RequestParser;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequestUnitTest {
    @Test
    public void testSimpleRequestPath() {
        String[] headers = new String[]{"GET / HTTP/1.1"};
        RequestParser.RequestInfo requestInfo = RequestParser.parseRequestInfo(headers);
        assert requestInfo != null;
        assertEquals("GET", requestInfo.method);
        assertEquals("/", requestInfo.rawPath);
        assertEquals("HTTP/1.1", requestInfo.httpVersion);
    }

    @Test
    public void testComplexRequestPath() {
        String[] headers = new String[]{"GET / / / HTTP/1.1"};
        RequestParser.RequestInfo requestInfo = RequestParser.parseRequestInfo(headers);
        assert requestInfo != null;
        assertEquals("GET", requestInfo.method);
        assertEquals("/ / /", requestInfo.rawPath);
        assertEquals("HTTP/1.1", requestInfo.httpVersion);
    }
}