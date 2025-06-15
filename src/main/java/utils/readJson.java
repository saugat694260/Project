package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class readJson {

    public static void readAndPrintCharacters() {
        Gson gson = new GsonBuilder().create();

        // Load resource from classpath
        InputStream is = readJson.class.getResourceAsStream("/gameData/characterData.json");

        if (is == null) {
            System.err.println("ERROR: Resource /gameData/characterData.json not found!");
            return;
        }

        try (Reader reader = new InputStreamReader(is)) {
            getData.GameCharacterList characterList = gson.fromJson(reader, getData.GameCharacterList.class);

            for (getData.GameCharacter character : characterList.gameCharacters) {
                System.out.println("Name: " + character.name +
                        ", Class: " + character.clazz +
                        ", Level: " + character.level);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
