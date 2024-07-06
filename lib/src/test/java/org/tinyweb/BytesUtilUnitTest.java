package org.tinyweb;

import static junit.framework.TestCase.assertEquals;

import org.tinyweb.commons.ByteUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BytesUtilUnitTest {
    @Test
    public void testScan() {
        List<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 1);
        bytes.add((byte) 2);
        bytes.add((byte) 3);
        bytes.add((byte) 4);
        bytes.add((byte) 5);
        bytes.add((byte) 6);
        bytes.add((byte) 7);
        bytes.add((byte) 8);

        int position = ByteUtil.scan(bytes, new byte[]{5, 6, 7});
        assertEquals(4, position);
    }
}
