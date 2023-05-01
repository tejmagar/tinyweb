package org.tinyweb.conf;

import java.util.HashMap;
import java.util.Map;

public class Configurations {
    private final Map<String, Object> configurations = new HashMap<>();

    public Map<String, Object> get() {
        return configurations;
    }
}
