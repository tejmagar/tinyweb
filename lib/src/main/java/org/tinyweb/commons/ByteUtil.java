package org.tinyweb.commons;

import java.util.List;

public class ByteUtil {
    public static byte[] byteFromList(List<Byte> bytes) {
        byte[] tmpBytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            tmpBytes[i] = bytes.get(i);
        }
        return tmpBytes;
    }

    public static byte[] slice(List<Byte> bytes, int offset) {
        int length = bytes.size() - offset;
        byte[] clonedBytes = new byte[length];

        int destIndex = 0;
        for (int i = offset; i < bytes.size(); i++) {
            clonedBytes[destIndex] = bytes.get(i);
            destIndex++;
        }

        return clonedBytes;
    }

    public static byte[] slice(List<Byte> bytes, int offset, int length) {
        byte[] clonedBytes = new byte[length];

        int destIndex = 0;
        for (int i = offset; i < length; i++) {
            clonedBytes[destIndex] = bytes.get(i);
            destIndex++;
        }

        return clonedBytes;
    }

    public static void extendSlice(List<Byte> bytes, byte[] slice) {
        for (byte b : slice) {
            bytes.add(b);
        }
    }

    public static int scan(List<Byte> scanFrom, byte[] scan) {
        if (scanFrom.size() < scan.length) {
            return -1;
        }

        for (int i = 0; i < scanFrom.size() - scan.length; i++) {
            boolean found = false;

            for (int j = 0; j < scan.length; j++) {
                if (scanFrom.get(i + j) != scan[j]) {
                    break;
                }

                found = (j == scan.length - 1);
            }

            if (found) {
                return i;
            }
        }
        return -1;
    }
}
