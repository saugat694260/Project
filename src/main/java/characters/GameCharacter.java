package characters;
import java.lang.reflect.*;
import java.util.List;


import java.util.List;

public class GameCharacter {
    public int id;
    public String name;
    public String characterClass;
    public int level;
    public List<String> abilities;
    public Stats stats;
    public Equipment equipment;
    public Images images;
    public Audios audios;

    public static class Stats {
        public int health;
        public int mana;
        public int strength;
        public int agility;
        public int speed;
    }

    public static class Equipment {
        public String weapon;
        public String armor;
        public List<String> accessory;
    }

    public static class Images {
        public String profile;
        public String body;
    }

    public static class Audios {
        public String voice;
        public String sharpAttack;
    }
    
    
}
