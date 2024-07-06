package org.tinyweb.lib.paths;

import org.tinyweb.lib.TinyWebLogging;
import org.tinyweb.lib.commons.QueryUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
                TinyWebLogging.debug(e);
                pathParseResult.pathName = rawPath;
            }

            pathParseResult.queryParams = new HashMap<>();
            return pathParseResult;
        }

        // Skips question mark character
        String pathName = rawPath.substring(firstQuestionMarkIndex);
        try {
            pathParseResult.pathName = URLDecoder.decode(pathName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            TinyWebLogging.debug(e);
            pathParseResult.pathName = pathName;
        }

        String rawQueryParams = rawPath.substring(firstQuestionMarkIndex + 1);
        pathParseResult.queryParams = QueryUtil.queryParamsFromUrl(rawQueryParams);
        return pathParseResult;
    }
}
