package org.tinyweb.lib;

import static junit.framework.TestCase.assertEquals;

import org.tinyweb.lib.forms.Files;
import org.tinyweb.lib.headers.Headers;
import org.tinyweb.lib.parsers.MultipartParser;
import org.tinyweb.lib.stream.Stream;
import org.tinyweb.lib.stream.TestStream;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MultipartParserUnitTest {
    @Test
    public void testMultipartBoundary() {
        String boundary = MultipartParser.getMultipartBoundary("multipart/form-data; boundary=--123");
        assertEquals("--123", boundary);
    }

    @Test
    public void testMultipart() throws RuntimeException {
        byte[] testBytes = new byte[]{
                45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                45, 45, 45, 45, 45, 45, 45, 54, 48, 49, 51, 56, 55, 51, 49, 50, 51, 48, 51, 53, 51,
                57, 56, 51, 55, 53, 56, 56, 48, 56, 54, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45,
                68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32, 102, 111, 114, 109, 45,
                100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 110, 97, 109, 101, 34, 13, 10,
                13, 10, 74, 111, 104, 110, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 54, 48, 49, 51, 56,
                55, 51, 49, 50, 51, 48, 51, 53, 51, 57, 56, 51, 55, 53, 56, 56, 48, 56, 54, 13, 10,
                67, 111, 110, 116, 101, 110, 116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105,
                111, 110, 58, 32, 102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109,
                101, 61, 34, 110, 97, 109, 101, 34, 13, 10, 13, 10, 78, 105, 107, 104, 105, 108,
                13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                45, 45, 45, 45, 45, 45, 45, 45, 45, 54, 48, 49, 51, 56, 55, 51, 49, 50, 51, 48, 51,
                53, 51, 57, 56, 51, 55, 53, 56, 56, 48, 56, 54, 13, 10, 67, 111, 110, 116, 101, 110,
                116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32, 102, 111,
                114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 102, 105, 108,
                101, 34, 59, 32, 102, 105, 108, 101, 110, 97, 109, 101, 61, 34, 101, 120, 97, 109,
                112, 108, 101, 46, 116, 120, 116, 34, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45,
                84, 121, 112, 101, 58, 32, 116, 101, 120, 116, 47, 112, 108, 97, 105, 110, 13, 10,
                13, 10, 48, 49, 50, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 54, 48, 49, 51, 56, 55, 51, 49, 50, 51,
                48, 51, 53, 51, 57, 56, 51, 55, 53, 56, 56, 48, 56, 54, 45, 45, 13, 10
        };

        Stream stream = new TestStream(testBytes, 1024);
        Headers headers = new Headers();
        headers.set("Content-Type",
                "multipart/form-data; boundary=--------------------------601387312303539837588086");
        MultipartParser multipartParser = new MultipartParser(headers, stream);

        try {
            MultipartParser.ParseResult parseResult = multipartParser.parse();
            List<String> names = parseResult.formData.values("name");

            assert names != null;
            assertEquals(2, names.size());

            Files.TempFile file = parseResult.files.value("file");
            assert file != null;
            assertEquals("example.txt", file.fileName);
        } catch (MultipartParser.MultipartParseException | Stream.StreamReadException |
                 IOException e) {
            throw new RuntimeException(e);
        }
    }
}
