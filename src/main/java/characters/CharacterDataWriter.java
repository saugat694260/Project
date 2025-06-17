package characters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterDataWriter {

	public static void writeCharacterToJsonFile(int id) {
	    try {
	    	String fileLocation = "src/main/resources/currentCharacterData/character_1.json";
	        if (fileLocation == null) {
	            System.out.println("Resource file not found: gameData/characterData.json");
	            return;
	        }


	        ReadCharacterDataJson data = new ReadCharacterDataJson(id,fileLocation);
	        GameCharacter character = data.getCharacterById(id);

	        if (character != null) {

	            // Define path inside resources folder
	            String directoryPath = "src/main/resources/currentCharacterData";
	            File directory = new File(directoryPath);

	            if (!directory.exists()) {
	                boolean dirCreated = directory.mkdirs();
	                if (!dirCreated) {
	                    System.out.println("Failed to create directory: " + directoryPath);
	                    return;
	                }
	            }

	            String filePath = directoryPath + "/character_" + id + ".json";

	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            try (FileWriter writer = new FileWriter(filePath)) {
	                gson.toJson(character, writer);
	            }

	            System.out.println("Character data written to file: " + filePath);
	        } else {
	            System.out.println("Character not found!");
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
