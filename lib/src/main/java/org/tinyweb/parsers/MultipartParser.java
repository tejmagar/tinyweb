package org.tinyweb.lib.parsers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.tinyweb.lib.commons.ByteUtil;
import org.tinyweb.lib.forms.Files;
import org.tinyweb.lib.forms.FormData;
import org.tinyweb.lib.headers.Headers;
import org.tinyweb.lib.request.RequestParser;
import org.tinyweb.lib.stream.Stream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultipartParser {
    private final Headers headers;
    private final Stream stream;
    private String boundary;

    public static class FormPartHeader {
        public String name;
        public String filename;
    }

    public static class ParseResult {
        public FormData formData;
        public Files files;
    }

    public static class ValueParseResult {
        public boolean hasNext;
        public String value;
    }

    public static class FileParseResult {
        public boolean hasNext;
        public File file;
    }

    public static class MultipartParseException extends Exception {
        public MultipartParseException(String message) {
            super(message);
        }
    }

    public MultipartParser(Headers headers, Stream stream) {
        this.headers = headers;
        this.stream = stream;
    }

    public ParseResult parse() throws MultipartParseException,
            Stream.StreamReadException, IOException {
        String contentType = headers.value("content-type");
        if (contentType == null) {
            throw new MultipartParseException("Content-Type header missing.");
        }

        if (!contentType.toLowerCase().startsWith("multipart/form-data;")) {
            throw new MultipartParseException("Invalid content type for multipart. Found: "
                    + contentType);
        }

        boundary = getMultipartBoundary(contentType);
        return parseBody();
    }

    private ParseResult parseBody() throws MultipartParseException,
            Stream.StreamReadException, IOException {
        if (boundary == null) {
            throw new MultipartParseException("Boundary missing");
        }

        // Cleans first start header
        String scanBoundary = "--" + boundary + "\r\n";

        // Buffer to extract starting boundary
        List<Byte> buffer = new ArrayList<>();

        while (scanBoundary.length() > buffer.size()) {
            byte[] bytes = stream.readChunk();
            for (byte b : bytes) {
                buffer.add(b);
            }
        }

        byte[] matchedBytes = ByteUtil.slice(buffer, 0, scanBoundary.length());
        if (!scanBoundary.equals(new String(matchedBytes))) {
            throw new MultipartParseException("Start boundary does not match.");
        }

        byte[] remainingBytes = ByteUtil.slice(buffer, scanBoundary.length());
        stream.restoreChunk(remainingBytes);

        ParseResult parseResult = new ParseResult();
        parseResult.formData = new FormData();
        parseResult.files = new Files();

        boolean hasNext = true;

        while (hasNext) {
            FormPartHeader formPartHeader = parseHeader();

            if (formPartHeader.filename != null) {
                FileParseResult fileParseResult = parseFile();
                Files.TempFile tempFile = new Files.TempFile();
                tempFile.fileName = formPartHeader.filename;
                tempFile.file = fileParseResult.file;
                parseResult.files.set(formPartHeader.name, tempFile);
                hasNext = fileParseResult.hasNext;
            } else {
                ValueParseResult valueParseResult = parseValue();
                parseResult.formData.set(formPartHeader.name, valueParseResult.value);
                hasNext = valueParseResult.hasNext;
            }
        }

        return parseResult;
    }

    private @NonNull FormPartHeader parseHeader() throws Stream.StreamReadException {
        List<Byte> buffer = new ArrayList<>();

        int matchedIndex;
        while ((matchedIndex = RequestParser.scanHeaderEnd(buffer)) == -1) {
            byte[] chunk = stream.readChunk();
            ByteUtil.extendSlice(buffer, chunk);
        }

        byte[] headers = ByteUtil.slice(buffer, 0, matchedIndex + 1);
        byte[] misRead = ByteUtil.slice(buffer, matchedIndex + 1 + "\r\n\r\n".length());
        stream.restoreChunk(misRead);

        String headerString = new String(headers);
        String[] headerLines = headerString.split("\r\n");

        FormPartHeader formPartHeader = new FormPartHeader();
        for (String headerLine : headerLines) {
            String[] attrValues = headerLine.split(";");

            for (String arrValue : attrValues) {
                Pattern pattern = Pattern.compile("^(.+?)=\"(.+?)\"$");
                Matcher matcher = pattern.matcher(arrValue.trim());

                if (matcher.find() && matcher.groupCount() >= 2) {
                    String attrName = matcher.group(1);
                    String value = matcher.group(2);

                    if (attrName != null && value != null) {
                        if (attrName.trim().equalsIgnoreCase("name")) {
                            formPartHeader.name = value.trim();
                        } else if (attrName.trim().equalsIgnoreCase("filename")) {
                            formPartHeader.filename = value.trim();
                        }
                    }
                }
            }
        }

        return formPartHeader;
    }

    private @NonNull ValueParseResult parseValue() throws Stream.StreamReadException {
        String scanBoundary = "\r\n--" + boundary;
        byte[] scanBoundaryBytes = scanBoundary.getBytes(StandardCharsets.UTF_8);

        List<Byte> buffer = new ArrayList<>();
        int matchedIndex;

        while (((matchedIndex = ByteUtil.scan(buffer, scanBoundaryBytes)) == -1)
                && buffer.size() < matchedIndex + "--\r\n".length()) {
            ByteUtil.extendSlice(buffer, stream.readChunk());
        }

        // Matched index is equal to the length since it returns beginning index where match starts.
        byte[] valueBytes = ByteUtil.slice(buffer, 0, matchedIndex);

        int skipOffset = matchedIndex + scanBoundaryBytes.length;
        boolean isFinal = buffer.get(skipOffset) == 45 && buffer.get(skipOffset + 1) == 45
                && buffer.get(skipOffset + 2) == 13 && buffer.get(skipOffset + 3) == 10;

        if (isFinal) {
            skipOffset += 4;
        } else {
            skipOffset += 2;
        }

        // Skips scan boundary
        byte[] restoredBytes = ByteUtil.slice(buffer, skipOffset);
        stream.restoreChunk(restoredBytes);

        ValueParseResult valueParseResult = new ValueParseResult();
        valueParseResult.hasNext = !isFinal;
        valueParseResult.value = new String(valueBytes);
        return valueParseResult;
    }

    private @NonNull FileParseResult parseFile() throws Stream.StreamReadException, IOException {
        String tempFilename = UUID.randomUUID().toString();
        File file = File.createTempFile(tempFilename, ".tmp");
        FileOutputStream outputStream = new FileOutputStream(file);

        String scanBoundary = "\r\n--" + boundary;
        byte[] scanBoundaryBytes = scanBoundary.getBytes(StandardCharsets.UTF_8);

        List<Byte> buffer = new ArrayList<>();
        boolean isFinal;

        while (true) {
            int matched = ByteUtil.scan(buffer, scanBoundaryBytes);
            if (matched != -1) {
                // Requires at least 4 bytes to check whether the form part is last or not.
                if (buffer.size() < matched + 4) {
                    continue;
                }

                // Found
                byte[] toCopy = ByteUtil.slice(buffer, 0, matched);
                outputStream.write(toCopy);

                // Skips boundary bytes
                int skipOffset = toCopy.length + scanBoundaryBytes.length;

                isFinal = buffer.get(skipOffset) == 45 && buffer.get(skipOffset + 1) == 45
                        && buffer.get(skipOffset + 2) == 13 && buffer.get(skipOffset + 3) == 10;

                if (isFinal) {
                    // Skips --\r\n bytes.
                    skipOffset += 4;
                } else {
                    // Skips -- bytes
                    skipOffset += 2;
                }

                byte[] remainingToMatch = ByteUtil.slice(buffer, skipOffset);
                stream.restoreChunk(remainingToMatch);
                break;
            } else {
                if (buffer.size() > scanBoundaryBytes.length) {
                    // Write some bytes to files
                    byte[] toCopy = ByteUtil.slice(buffer, 0, buffer.size() - scanBoundaryBytes.length);
                    outputStream.write(toCopy);

                    byte[] remainingToMatch = ByteUtil.slice(buffer, toCopy.length);

                    // Swap with new bytes
                    buffer.clear();
                    ByteUtil.extendSlice(buffer, remainingToMatch);
                }

                ByteUtil.extendSlice(buffer, stream.readChunk());
            }
        }

        outputStream.flush();
        outputStream.close();

        FileParseResult fileParseResult = new FileParseResult();
        fileParseResult.hasNext = !isFinal;
        fileParseResult.file = file;
        return fileParseResult;
    }

    public static @Nullable String getMultipartBoundary(String contentType) {
        String[] values = contentType.split(";");

        if (values.length < 2) {
            return null;
        }

        String[] boundaryKeyValue = values[1].split("=");
        if (boundaryKeyValue.length < 2) {
            return null;
        }

        return boundaryKeyValue[1].trim();
    }
}
