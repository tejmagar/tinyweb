package org.tinyweb.urls;

import java.util.ArrayList;
import java.util.List;

public class Routes {
    private final List<BasePath> basePaths = new ArrayList<>();

    public void addRoute(BasePath basePath) {
        this.basePaths.add(basePath);
    }

    public List<BasePath> getAllRoutes() {
        return basePaths;
    }

    public BasePath getMatchedPath(String currentUrl) {
        for (BasePath path : basePaths) {
            if (path.isMatched(currentUrl)) {
                return path;
            }
        }

        return null;
    }
}
