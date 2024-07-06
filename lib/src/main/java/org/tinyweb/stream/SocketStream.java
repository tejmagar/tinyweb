package org.tinyweb.stream;

import org.tinyweb.TinyWebLogging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class SocketStream extends Stream {
    private final Socket socket;
    private final int bufferSize;
    private byte[] restoredBytes = null;

    public SocketStream(Socket socket, int bufferSize) {
        this.socket = socket;
        this.bufferSize = bufferSize;
    }

    @Override
    public int bufferSize() {
        return this.bufferSize;
    }

    @Override
    public byte[] readChunk() throws StreamReadException {
        if (restoredBytes != null) {
            byte[] restoredBytesCloned = restoredBytes.clone();
            restoredBytes = null;
            return restoredBytesCloned;
        }

        byte[] buffer = new byte[bufferSize];

        try {
            InputStream inputStream = socket.getInputStream();
            int readSize = inputStream.read(buffer);

            if (readSize <= 0) {
                throw new StreamReadException("Read size is " + readSize + " May be connection broken.");
            }

            // Copy bytes
            byte[] chunk = new byte[readSize];
            System.arraycopy(buffer, 0, chunk, 0, readSize);
            return chunk;
        } catch (IOException e) {
            throw new StreamReadException("Failed to read chunk. " + e.getMessage());
        }
    }

    @Override
    public void writeChunk(byte[] bytes) throws StreamWriteException {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            TinyWebLogging.error(e);
            throw new StreamWriteException("Failed to write chunk. " + e.getMessage());
        }
    }

    @Override
    public void restoreChunk(byte[] bytes) {
        restoredBytes = bytes;
    }

    @Override
    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
