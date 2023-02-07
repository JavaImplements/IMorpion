package fr.implement.imorpion.listeners;

import fr.implement.imorpion.lib.MorpionManager;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MorpionListeners extends ListenerAdapter {

    private final MorpionManager manager = MorpionManager.get();


    @Override
    public void onButtonClick(@NotNull ButtonClickEvent e) {
        TextChannel channel = e.getTextChannel();
        if (manager.isMorpion(channel)) {
            manager.getMorpionByChannel(channel).playerPushedButton(e);
        }
    }


}
