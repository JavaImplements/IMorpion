package fr.implement.imorpion.lib;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;

public class MorpionManager {

    public static MorpionManager instance;
    public HashMap<TextChannel, Morpion> morpions = new HashMap<>();

    public MorpionManager() {
        instance = this;
    }

    public static MorpionManager get() {
        if (instance == null) instance = new MorpionManager();
        return instance;
    }

    public HashMap<TextChannel, Morpion> getMorpions() {
        return morpions;
    }

    public void addMorpion(Morpion m) {
        morpions.put(m.getChannel(), m);
    }

    public void removeMorpion(TextChannel textChannel) {
        morpions.remove(textChannel);
    }

    public boolean isMorpion(TextChannel textChannel) {
        return morpions.containsKey(textChannel);
    }

    public Morpion getMorpionByChannel(TextChannel textChannel) {
        return morpions.get(textChannel);
    }
}
