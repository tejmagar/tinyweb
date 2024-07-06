package org.tinyweb.paths;

import android.net.Uri;

import org.tinyweb.TinyWebLogging;
import org.tinyweb.commons.QueryUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class PathUtil {
    public static class PathParseResult {
        public String pathName;
        public HashMap<String, List<String>> queryParams;
    }

    public static PathParseResult parsePath(String rawPath) {
        PathParseResult pathParseResult = new PathParseResult();

        int firstQuestionMarkIndex = rawPath.indexOf("?");
        if (firstQuestionMarkIndex == -1) {
            try {
                pathParseResult.pathName = URLDecoder.decode(rawPath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                pathParseResult.pathName = rawPath;
            }
            pathParseResult.queryParams = new HashMap<>();
            return pathParseResult;
        }

        // Skips question mark character
        String pathName = rawPath.substring(0, firstQuestionMarkIndex);
        try {
            pathParseResult.pathName = URLDecoder.decode(pathName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            pathParseResult.pathName = pathName;
        }

        String rawQueryParams = rawPath.substring(firstQuestionMarkIndex + 1);
        pathParseResult.queryParams = QueryUtil.queryParamsFromUrl(rawQueryParams);
        return pathParseResult;
    }
}
