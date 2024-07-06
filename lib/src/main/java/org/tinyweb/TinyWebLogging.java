package org.tinyweb;

import android.util.Log;

public class TinyWebLogging {
    public static boolean enableLogging = false;

    public static void debug(String tag, String message) {
        if (enableLogging) {
            Log.d(tag, message);
        }
    }

    public static void debug(Exception exception) {
        if (enableLogging) {
            exception.printStackTrace();
        }
    }

    public static void info(String tag, String message) {
        if (enableLogging) {
            Log.i(tag, message);
        }
    }

    public static void info(Exception e) {
        if (enableLogging) {
            e.printStackTrace();
        }
    }

    public static void error(String tag, String message) {
        if (enableLogging) {
            Log.e(tag, message);
        }
    }

    public static void error(Exception e) {
        if (enableLogging) {
            e.printStackTrace();
        }
    }

    public static void warning(String tag, String message) {
        if (enableLogging) {
            Log.w(tag, message);
        }
    }

    public static void warning(Exception e) {
        if (enableLogging) {
            e.printStackTrace();
        }
    }
}
