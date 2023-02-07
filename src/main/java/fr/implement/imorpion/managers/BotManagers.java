package fr.implement.imorpion.managers;

import fr.implement.imorpion.commands.CmdLeaderBoard;
import fr.implement.imorpion.commands.CmdMorpion;
import fr.implement.imorpion.listeners.InvitationListeners;
import fr.implement.imorpion.listeners.MorpionListeners;
import fr.implement.imorpion.utils.SlashHandler;
import net.dv8tion.jda.api.JDA;

import java.util.Arrays;

public class BotManagers {

    private final JDA jda;

    public BotManagers(JDA jda) {
        this.jda = jda;
    }

    public JDA getJda() {
        return jda;
    }

    public void registerCommands() {
        SlashHandler slashHandler = new SlashHandler(jda);
        slashHandler.registerCommand(new CmdMorpion());
        slashHandler.registerCommand(new CmdLeaderBoard());
        slashHandler.registerCommands(jda);

    }

    public void registerListeners() {
        Arrays.asList(
                new MorpionListeners(),
                new InvitationListeners()
        ).forEach(jda::addEventListener);
    }

    public void init() {
        this.registerCommands();
        this.registerListeners();
    }

}
