package org.tinyweb.lib.response.status;

import androidx.annotation.NonNull;

public class StatusUtil {
    public static class ResponseStatus {
        int statusCode;
        String statusText;

        public ResponseStatus(int statusCode, String statusText) {
            this.statusCode = statusCode;
            this.statusText = statusText;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusText() {
            return statusText;
        }
    }

    public static @NonNull ResponseStatus fromStatus(Status status) {
        switch (status) {
            case Ok:
                return new ResponseStatus(200, "OK");

            case FILE_NOT_FOUND:
                return new ResponseStatus(404, "File Not Found");

        }

        throw new RuntimeException("Status not found");
    }
}
