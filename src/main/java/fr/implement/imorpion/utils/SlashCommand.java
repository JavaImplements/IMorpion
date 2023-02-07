package fr.implement.imorpion.utils;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;


public abstract class SlashCommand {

    public final String command;
    public final String description;
    public final OptionData[] optionData;

    public SlashCommand(String command, String description, OptionData... optionData) {
        this.command = command;
        this.description = description;
        this.optionData = optionData;
    }

    public abstract void execute(SlashCommandEvent e, Member member, User user, TextChannel textChannel);

    public Permission getPermission() {
        return null;
    }

    ;


}
