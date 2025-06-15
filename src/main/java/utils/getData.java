package utils;

import java.util.List;



public class getData {

    public static class GameCharacterList {
        public List<GameCharacter> gameCharacters;
    }

    public static class GameCharacter {
        public int id;
        public String name;
        public String clazz; // "class" is a reserved word
        public int level;
        public List<String> abilities;
        public Stats stats;
        public Equipment equipment;
    }

    public static class Stats {
        public int health;
        public int mana;
        public int strength;
        public int agility;
        public int intelligence;
    }

    public static class Equipment {
        public String weapon;
        public String armor;
        public String accessory;
    }
}
