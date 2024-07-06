package org.tinyweb.lib.stream;

public abstract class Stream {
    public static class StreamReadException extends Exception {
        public StreamReadException(String message) {
            super(message);
        }
    }

    public static class StreamWriteException extends Exception {
        public StreamWriteException(String message) {
            super(message);
        }
    }

    public abstract int bufferSize();
    public abstract byte[] readChunk() throws StreamReadException;

    public abstract void writeChunk(byte[] bytes) throws StreamWriteException;

    public abstract void restoreChunk(byte[] bytes);

    public abstract void shutdown();
}
