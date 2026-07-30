package com.example.reservations_app.storage;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonFileStorageService {

    private final ObjectMapper objectMapper;
    private final Path dataDirectory;

    public JsonFileStorageService() {
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.dataDirectory = Path.of("data");
    }

    public synchronized <T> List<T> readAll(String fileName, Class<T> itemType) {
        Path filePath = resolve(fileName);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try {
            JavaType listType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, itemType);
            return objectMapper.readValue(filePath.toFile(), listType);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read JSON file: " + filePath, exception);
        }
    }

    public synchronized <T> void writeAll(String fileName, List<T> records) {
        Path filePath = resolve(fileName);
        Path tempPath = filePath.resolveSibling(filePath.getFileName() + ".tmp");

        try {
            objectMapper.writeValue(tempPath.toFile(), records);
            Files.move(tempPath, filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not write JSON file: " + filePath, exception);
        }
    }

    private Path resolve(String fileName) {
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not create data directory.", exception);
        }
        return dataDirectory.resolve(fileName);
    }
}
