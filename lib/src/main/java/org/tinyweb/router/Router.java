package org.tinyweb.router;

import androidx.annotation.Nullable;

import org.tinyweb.paths.Path;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Router {
    private final List<Path> paths;

    public Router(List<Path> paths) {
        this.paths = paths;
    }

    public @Nullable Path match(String pathName) {
        for (Path path: paths) {
            Pattern pattern = Pattern.compile(path.getPattern());
            Matcher matcher = pattern.matcher(pathName);

            if (matcher.matches()) {
                return path;
            }
        }
        return null;
    }
}
