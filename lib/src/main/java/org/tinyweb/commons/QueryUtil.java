package org.tinyweb.commons;

import org.tinyweb.TinyWebLogging;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryUtil {
    public static HashMap<String, List<String>> queryParamsFromUrl(String rawUrl) {
        HashMap<String, List<String>> hashMap = new HashMap<>();

        String decodedUrl;
        try {
            decodedUrl = URLDecoder.decode(rawUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            TinyWebLogging.error(e);
            decodedUrl = rawUrl;
        }

        // Split by ampersand
        String[] params = decodedUrl.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length > 2) {
                String name = keyValue[0].trim();
                String value = keyValue[1].trim();

                if (hashMap.containsKey(name)) {
                    List<String> values = hashMap.get(name);
                    if (values != null) {
                        values.add(value);
                    }
                } else {
                    List<String> values = new ArrayList<>();
                    values.add(value);
                    hashMap.put(name, values);
                }
            }
        }

        return hashMap;
    }
}
