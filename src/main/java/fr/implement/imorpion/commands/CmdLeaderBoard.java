package fr.implement.imorpion.commands;

import fr.implement.imorpion.utils.SlashCommand;
import fr.implement.imorpion.utils.classement.ClassementManager;
import fr.implement.imorpion.utils.database.DataEnum;
import fr.implement.imorpion.utils.database.tables.StatsMorpion;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

public class CmdLeaderBoard extends SlashCommand {

    private StatsMorpion stats = (StatsMorpion) DataEnum.STATS.getDatabase();

    public CmdLeaderBoard() {
        super("morpion-top", "Permet d'afficher le leaderboard");
    }

    @Override
    public void execute(SlashCommandEvent e, Member member, User user, TextChannel textChannel) {
        e.replyEmbeds(ClassementManager.get().generated().build()).complete();
    }
}
