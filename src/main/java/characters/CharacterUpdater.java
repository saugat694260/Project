package characters;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;

public class CharacterUpdater {
    static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void updateCharacterData(String key, String newValue, String filePath) throws IOException {
        JsonObject jsonObject;
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        }

        String[] keys = key.split("\\."); // Split nested keys by '.'
        JsonObject currentObj = jsonObject;

        // Traverse to the second last key
        for (int i = 0; i < keys.length - 1; i++) {
            if (currentObj.has(keys[i]) && currentObj.get(keys[i]).isJsonObject()) {
                currentObj = currentObj.getAsJsonObject(keys[i]);
            } else {
                throw new IllegalArgumentException("Key not found or not a JSON object: " + keys[i]);
            }
        }

        String lastKey = keys[keys.length - 1];
        if (currentObj.has(lastKey)) {
            // Try to guess the type from the existing value and convert newValue accordingly
            if (currentObj.get(lastKey).isJsonPrimitive()) {
                if (currentObj.get(lastKey).getAsJsonPrimitive().isNumber()) {
                    try {
                        // Try updating as number
                        String intValue = newValue;
                        currentObj.addProperty(lastKey, intValue);
                    } catch (NumberFormatException e) {
                        // fallback to string if parsing fails
                        currentObj.addProperty(lastKey, newValue);
                    }
                } else {
                    currentObj.addProperty(lastKey, newValue);
                }
            } else {
                throw new IllegalArgumentException("Cannot update non-primitive JSON element: " + lastKey);
            }
        } else {
            throw new IllegalArgumentException("Key not found: " + lastKey);
        }

        try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
            gson.toJson(jsonObject, writer);
        }
    }

}
