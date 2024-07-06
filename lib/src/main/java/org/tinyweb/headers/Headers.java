package org.tinyweb.headers;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Headers {
    private final HashMap<String, List<String>> headers = new HashMap<>();

    /**
     * Insert single value with the given header name. Does not allow to insert multiple values.
     * Removes existing header values if already present with the same header name.
     *
     * @param name  - Header name
     * @param value - Header value
     */
    public void set(String name, String value) {
        List<String> values = new ArrayList<>(1);
        values.add(value);
        headers.put(name, values);
    }

    /**
     * Allows to insert multiple header values with same header name.
     *
     * @param name  - Header name
     * @param value - Header value
     */
    public void setMultiple(String name, String value) {
        List<String> values = headers.get(name);

        if (values != null) {
            // Header with this name already exists. So, insert multiple values with same header
            // name.
            values.add(value);
        } else {
            // Header with this header name does not exist.
            set(name, value);
        }
    }

    /**
     * Performs case insensitive lookup and returns value if present else null.
     *
     * @param name - Header name
     * @return String
     */
    public @Nullable String value(String name) {
        for (String key : headers.keySet()) {
            if (!key.equalsIgnoreCase(name)) {
                continue;
            }

            List<String> values = headers.get(key);
            if (values != null && values.size() > 0) {
                return values.get(0);
            }
        }

        return null;
    }

    public @Nullable List<String> values(String name) {
        for (String key : headers.keySet()) {
            if (!key.equalsIgnoreCase(name)) {
                continue;
            }

            return headers.get(key);
        }

        return null;
    }

    public HashMap<String, List<String>> inner() {
        return headers;
    }
}
