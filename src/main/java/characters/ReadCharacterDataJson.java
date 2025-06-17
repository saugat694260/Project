package characters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;

public class ReadCharacterDataJson {
    private GameCharactersWrapper charactersWrapper;
    private GameCharacter singleCharacter;

    public ReadCharacterDataJson(int id, String filePath) throws IOException {
        Gson gson = new GsonBuilder().create();

        try (FileReader reader = new FileReader(filePath)) {
            // Try to parse as wrapper first
            charactersWrapper = gson.fromJson(reader, GameCharactersWrapper.class);
        }

        // If that failed (or wrapper has no characters), try to parse as a single character
        if (charactersWrapper == null || charactersWrapper.gameCharacters == null) {
            try (FileReader reader = new FileReader(filePath)) {
                Gson gson1 = new GsonBuilder().create();
                singleCharacter = gson1.fromJson(reader, GameCharacter.class);
            }
        }
    }

    public GameCharacter getCharacterById(int id) {
        if (charactersWrapper != null && charactersWrapper.gameCharacters != null) {
            for (GameCharacter character : charactersWrapper.gameCharacters) {
                if (character.id == id) {
                    return character;
                }
            }
        } else if (singleCharacter != null && singleCharacter.id == id) {
            return singleCharacter;
        }

        return null;
    }

    public static class GameCharactersWrapper {
        public java.util.List<GameCharacter> gameCharacters;
    }
}
