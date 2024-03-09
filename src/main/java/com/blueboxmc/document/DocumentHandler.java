package com.blueboxmc.document;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DocumentHandler<T> {

    private static final String DOCUMENTS = "documents";
    private final String name;
    private final Class<T> type;

    public DocumentHandler(String name, Class<T> documentType) {
        this.name = name;
        this.type = documentType;
    }

    public T getDocument() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(String.format("%s/%s.json", DOCUMENTS, name));
        if (inputStream == null) {
            throw new IllegalStateException("No document exists with name: " + name);
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return new Gson().fromJson(reader, type);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read document: " + name);
        }
    }
}
