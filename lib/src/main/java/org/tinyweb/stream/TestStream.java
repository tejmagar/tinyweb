package org.tinyweb.lib.stream;

public class TestStream extends Stream{
    private byte[] testBytes;
    private final int bufferSize;
    private byte[] restoredBytes = null;

    public TestStream(byte[] testBytes, int bufferSize) {
        this.testBytes = testBytes;
        this.bufferSize = bufferSize;
    }

    @Override
    public int bufferSize() {
        return bufferSize;
    }

    @Override
    public byte[] readChunk() throws StreamReadException {
        if (restoredBytes != null) {
            byte[] restoredBytesCloned = restoredBytes.clone();
            restoredBytes = null;
            return restoredBytesCloned;
        }

        if (testBytes.length == 0) {
            throw new StreamReadException("Read size is 0.");
        }

        if (bufferSize > testBytes.length) {
            byte[] chunk = testBytes.clone();
            testBytes = new byte[0];
            return chunk;
        }

        // Copy bytes
        byte[] chunk = new byte[bufferSize];
        System.arraycopy(testBytes, 0, chunk, 0, bufferSize);

        // Store remaining chunk temporary
        int newTestBytesLength = testBytes.length - bufferSize;
        byte[] tmpBytes = new byte[newTestBytesLength];
        if (newTestBytesLength > 0) {
            // Copies bytes from testBytes to tmp Bytes after reading some bytes of buffer size.
            System.arraycopy(testBytes, chunk.length, tmpBytes, 0, newTestBytesLength);
        }

        testBytes = tmpBytes;
        return chunk;
    }

    @Override
    public void writeChunk(byte[] bytes) throws StreamWriteException {
        // Not handled
    }

    @Override
    public void restoreChunk(byte[] bytes) {
        restoredBytes = bytes;
    }

    @Override
    public void shutdown() {
        // Not handled
    }
}
