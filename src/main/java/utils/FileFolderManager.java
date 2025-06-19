package utils;

import java.io.File;
import java.nio.file.Paths;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

public class FileFolderManager {
  private static final String DIR_MAIN = Paths.get("").toAbsolutePath().toString() + File.separator + "rpgGameName";

  private static final String DIR_RESOURCES = DIR_MAIN + File.separator + "resources";
  private static final String DIR_GAME_DATA = DIR_RESOURCES + File.separator + "gameData";

  private static final String CHARACTER_DATA = DIR_GAME_DATA + File.separator + "characterData.json";

  private static final String DIR_USERS = DIR_MAIN + File.separator + "users";

  public static boolean initializeFileFolder() {
    boolean initialized = false;

    initialized = getDirMain().mkdir() && getDirUsers().mkdir() && getDirResources().mkdir()
        && getDirGameData().mkdir();
    initialized = createCharacterData();
    if (!initialized) {
      System.out.println("Unable to initialize files and folders");
      System.exit(1);
    } else {
      System.out.println("Folders initialized");
    }

    return initialized;
  }

  public static File getDirMain() {
    File file = new File(DIR_MAIN);
    return file;
  }

  public static File getDirUsers() {
    File file = new File(DIR_USERS);
    return file;
  }

  public static File getDirResources() {
    File file = new File(DIR_RESOURCES);
    return file;
  }

  public static File getDirGameData() {
    File file = new File(DIR_GAME_DATA);
    return file;
  }

  public static File getCharacterData() {
    File file = new File(CHARACTER_DATA);
    return file;
  }

  private static boolean createCharacterData() {
    String s = """
          {
          "gameCharacters": [
            {
              "id": 0,
              "name": "slick",
              "characterClass": "thief",
              "level": 0,
              "abilities": ["sneak", "stealth", "quick stab"],
              "stats": {
                "health": 100,
                "mana": 100,
                "strength": 10,
                "agility": 50,
                "speed": 10
              },
              "equipment": {
                "weapon": "knife",
                "armor": "Blessed Plate Armor",
                "accessory": ["ring", "knife of chaos"]
              },
              "images": {
                "profile": "url:/gogo",
                "body": "url:hoho"
              },
              "audios": {
                "voice": "gogo.mp3",
                "sharpAttack": "hehe.mp3"
              }
            },
            {
              "id": 1,
              "name": "mira",
              "characterClass": "magician",
              "level": 0,
              "abilities": ["fireball", "teleport", "arcane shield"],
              "stats": {
                "health": 80,
                "mana": 200,
                "strength": 5,
                "agility": 20,
                "speed": 15
              },
              "equipment": {
                "weapon": "magic staff",
                "armor": "Mystic Robes",
                "accessory": ["amulet of wisdom", "mana crystal"]
              },
              "images": {
                "profile": "url:/mira-profile",
                "body": "url:/mira-body"
              },
              "audios": {
                "voice": "mira.mp3",
                "sharpAttack": "zap.mp3"
              }
            }
          ]
        }
              """;

    boolean created = true;

    try (BufferedWriter bfw = new BufferedWriter(new FileWriter(getCharacterData()))) {
      bfw.write(s);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("error writing to characterData");
      created = false;
    }
    return created;
  }

}
