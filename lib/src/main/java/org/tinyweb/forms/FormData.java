package org.tinyweb.forms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormData {
    private final HashMap<String, List<String>> formValues = new HashMap<>();

    public void set(String name, String value) {
        if (formValues.containsKey(name)) {
            List<String> values = formValues.get(name);
            if (values != null) {
                values.add(value);
            }
        } else {
            List<String> values = new ArrayList<>();
            values.add(value);
            formValues.put(name, values);
        }
    }

    public @Nullable String value(String name) {
        List<String> values = formValues.get(name);

        if (values != null && values.size() > 0) {
            return values.get(0);
        }
        return null;
    }

    public @Nullable List<String> values(String name) {
        return formValues.get(name);
    }

    public HashMap<String, List<String>> inner() {
        return formValues;
    }

    @NonNull
    @Override
    public String toString() {
        return inner().toString();
    }
}
