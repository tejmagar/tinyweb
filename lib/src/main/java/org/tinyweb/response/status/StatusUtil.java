package org.tinyweb.response.status;

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
            case CONTINUE:
                return new ResponseStatus(100, "Continue");

            case SWITCHING_PROTOCOLS:
                return new ResponseStatus(101, "Switching Protocols");

            case PROCESSING:
                return new ResponseStatus(102, "Processing");

            case EARLY_HINTS:
                return new ResponseStatus(103, "Early Hints");

            case OK:
                return new ResponseStatus(200, "OK");

            case CREATED:
                return new ResponseStatus(201, "CREATED");

            case ACCEPTED:
                return new ResponseStatus(202, "ACCEPTED");

            case NON_AUTHORITATIVE_INFORMATION:
                return new ResponseStatus(203, "Non-Authoritative Information");

            case NO_CONTENT:
                return new ResponseStatus(204, "No Content");

            case PARTIAL_CONTENT:
                return new ResponseStatus(205, "Reset Content");

            case MULTI_STATUS:
                return new ResponseStatus(207, "Multi-Status");

            case ALREADY_REPORTED:
                return new ResponseStatus(208, "Already Reported");

            case IM_USED:
                return new ResponseStatus(226, "IM Used");

            case MULTIPLE_CHOICES:
                return new ResponseStatus(300, "Multiple Choices");

            case MOVED_PERMANENTLY:
                return new ResponseStatus(301, "Moved Permanently");

            case FOUND:
                return new ResponseStatus(302, "Found");

            case SEE_OTHER:
                return new ResponseStatus(303, "See Other");

            case NOT_MODIFIED:
                return new ResponseStatus(304, "Not Modified");

            case USE_PROXY:
                return new ResponseStatus(305, "Use Proxy");
            case UNUSED:
                return new ResponseStatus(306, "unused");

            case TEMPORARY_REDIRECT:
                return new ResponseStatus(307, "Temporary Redirect");

            case PERMANENT_REDIRECT:
                return new ResponseStatus(308, "Permanent Redirect");

            case BAD_REQUEST:
                return new ResponseStatus(400, "Bad Request");

            case UNAUTHORIZED:
                return new ResponseStatus(401, "Unauthorized");

            case PAYMENT_REQUIRED:
                return new ResponseStatus(402, "Payment Required");

            case FORBIDDEN:
                return new ResponseStatus(403, "Forbidden");

            case NOT_FOUND:
                return new ResponseStatus(404, "Not Found");

            case METHOD_NOT_ALLOWED:
                return new ResponseStatus(405, "Method Not Allowed");

            case NOT_ACCEPTABLE:
                return new ResponseStatus(406, "Not Acceptable");

            case REQUEST_TIMEOUT:
                return new ResponseStatus(408, "Request Timeout");

            case CONFLICT:
                return new ResponseStatus(409, "Conflict");

            case GONE:
                return new ResponseStatus(410, "Gone");

            case LENGTH_REQUIRED:
                return new ResponseStatus(411, "Length Required");

            case PRECONDITION_FAILED:
                return new ResponseStatus(412, "Precondition Failed");

            case PAYLOAD_TOO_LARGE:
                return new ResponseStatus(413, "Payload Too Large");

            case URI_TOO_LONG:
                return new ResponseStatus(414, "URI Too long");

            case UNSUPPORTED_MEDIA_TYPE:
                return new ResponseStatus(415, "Unsupported Media Type");

            case RANGE_NOT_SATISFIABLE:
                return new ResponseStatus(416, "Range Not Satisfiable");

            case EXPECTATION_FAILED:
                return new ResponseStatus(417, "Expectation Failed");

            case IM_A_TEAPOT:
                return new ResponseStatus(418, "I'm a teapot");

            case MISDIRECTED_REQUEST:
                return new ResponseStatus(421, "Misdirected Request");

            case UNPROCESSABLE_CONTENT:
                return new ResponseStatus(422, "Unprocessable Content");

            case LOCKED:
                return new ResponseStatus(423, "Locked");

            case FAILED_DEPENDENCY:
                return new ResponseStatus(424, "Failed Dependency");

            case TOO_EARLY:
                return new ResponseStatus(425, "Too Early");

            case UPGRADE_REQUIRED:
                return new ResponseStatus(426, "Upgrade Required");

            case PRECONDITION_REQUIRED:
                return new ResponseStatus(428, "Precondition Required");

            case TOO_MANY_REQUESTS:
                return new ResponseStatus(429, "Too Many Requests");

            case REQUEST_HEADER_FIELDS_TOO_LARGE:
                return new ResponseStatus(431, "Request Header Fields Too Large");

            case UNAVAILABLE_FOR_LEGAL_REASONS:
                return new ResponseStatus(451, "Unavailable For Legal Reasons");

            case INTERNAL_SERVER_ERROR:
                return new ResponseStatus(500, "Internal Server Error");

            case NOT_IMPLEMENTED:
                return new ResponseStatus(501, "Not Implemented");

            case BAD_GATEWAY:
                return new ResponseStatus(502, "Bad Gateway");

            case SERVICE_UNAVAILABLE:
                return new ResponseStatus(503, "Service Unavailable");

            case GATEWAY_TIMEOUT:
                return new ResponseStatus(504, "Gateway Timeout");

            case HTTP_VERSION_NOT_SUPPORTED:
                return new ResponseStatus(505, "HTTP Version Not Supported");

            case VARIANT_ALSO_NEGOTIATES:
                return new ResponseStatus(506, "Variant Also Negotiates");

            case INSUFFICIENT_STORAGE:
                return new ResponseStatus(507, "Insufficient Storage");

            case LOOP_DETECTED:
                return new ResponseStatus(508, "Loop Detected");
            case NOT_EXTENDED:
                return new ResponseStatus(510, "Not Extended");

            case NETWORK_AUTHENTICATION_REQUIRED:
                return new ResponseStatus(511, "Network Authentication Required");
        }

        throw new RuntimeException("Status not found");
    }
}
