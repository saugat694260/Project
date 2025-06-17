

import java.io.IOException;

import characters.CharacterDataWriter;
import characters.CharacterUpdater;
import characters.GameCharacter;
import characters.ReadCharacterDataJson;


public class Main {
    public static void main(String[] args) throws IOException {
    	  
    	 
    	 
          //String characterDataPaths = "src/main/resources/gameData/characterData.json";
          String characterDataPath = "src/main/resources/currentCharacterData/character_1.json";

         // CharacterDataWriter.writeCharacterToJsonFile(1);
         
    	
    	
    	ReadCharacterDataJson data = new ReadCharacterDataJson(1,characterDataPath);
        GameCharacter character = data.getCharacterById(1);
        CharacterUpdater.updateCharacterData("name", "gigi",characterDataPath);
        CharacterUpdater.updateCharacterData("level", "3",characterDataPath);
        CharacterUpdater.updateCharacterData("stats.health", "10000",characterDataPath);

        if (character != null) {
            System.out.println("id: " + character.id);
            System.out.println("Name: " + character.name);
            System.out.println("characterClass: " + character.characterClass);
            System.out.println("level: " + character.level);
            System.out.println("Abilities: " + character.abilities);
            System.out.println("stats: " + character.stats);
            System.out.println("equipment: " + character.equipment);
            System.out.println("Images: " + character.images);
            System.out.println("Audios: " + character.audios);}
    }
}

