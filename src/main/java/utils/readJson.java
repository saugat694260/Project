package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.getData;

public class readJson {

    public static void readAndPrintCharacters() {
    	
        Gson gson = new GsonBuilder().create();

        try (Reader reader = Files.newBufferedReader(Paths.get("src/main/java/gameData/characterData.json"))) {

            getData.GameCharacterList characterList = gson.fromJson(reader, getData.GameCharacterList.class);

            for (getData.GameCharacter character : characterList.gameCharacters) {
                System.out.println("Name: " + character.name +
                        ", Class: " + character.clazz+
                        ", Level: " + character.level);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
