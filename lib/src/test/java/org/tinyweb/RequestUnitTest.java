package org.tinyweb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.tinyweb.paths.PathUtil;
import org.tinyweb.request.RequestParser;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequestUnitTest {
    @Test
    public void testSimpleRequestPath() {
        String[] headers = new String[]{"GET /?name=john HTTP/1.1"};
        RequestParser.RequestInfo requestInfo = RequestParser.parseRequestInfo(headers);
        assert requestInfo != null;
        assertEquals("GET", requestInfo.method);
        assertEquals("/?name=john", requestInfo.rawPath);
        assertEquals("/", requestInfo.pathName);
        assertEquals("HTTP/1.1", requestInfo.httpVersion);
    }

    @Test
    public void testParsePath() {
        PathUtil.PathParseResult pathParseResult = PathUtil.parsePath("/?name=1");
        assertEquals("/", pathParseResult.pathName);
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