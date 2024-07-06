package org.tinyweb.lib.forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Files {
    public static class TempFile {
        public String fileName;
        public File file;
    };
    private final HashMap<String, List<TempFile>> filesHashMap = new HashMap<>();

    public void set(String name, TempFile tempFile) {
        if (filesHashMap.containsKey(name)) {
            List<TempFile> files = filesHashMap.get(name);

            if (files != null) {
                files.add(tempFile);
            }
        } else {
            List<TempFile> files = new ArrayList<>();
            files.add(tempFile);
            filesHashMap.put(name, files);
        }
    }

    public @Nullable TempFile value(String name) {
        List<TempFile> files = filesHashMap.get(name);

        if (files != null && files.size() > 0) {
            return files.get(0);
        }

        return null;
    }

    public HashMap<String, List<TempFile>> inner() {
        return filesHashMap;
    }

    @NonNull
    @Override
    public String toString() {
        return inner().toString();
    }
}
