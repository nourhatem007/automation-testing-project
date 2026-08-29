package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class JsonDataReader {
    private static final JsonNode ROOT;

    static {
        try (InputStream input = JsonDataReader.class.getClassLoader().getResourceAsStream("testdata.json")) {
            if (input == null) {
                throw new IllegalStateException("testdata.json was not found in src/test/resources");
            }
            ROOT = new ObjectMapper().readTree(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read testdata.json", e);
        }
    }

    private JsonDataReader() {}

    public static String get(String path) {
        JsonNode node = ROOT;
        for (String part : path.split("\\.")) {
            node = node.path(part);
        }
        if (node.isMissingNode() || node.isNull()) {
            throw new IllegalArgumentException("Missing JSON test data: " + path);
        }
        return node.asText();
    }
}
