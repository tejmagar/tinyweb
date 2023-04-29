package org.tinyweb.urls;

import org.tinyweb.views.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPath extends BasePath {
    private final Pattern pattern;

    public RegexPath(String pattern, View view) {
        super(pattern, view);
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean isMatched(String currentUrl) {
        Matcher matcher = pattern.matcher(currentUrl);
        return matcher.matches();
    }
}
