package fr.implement.imorpion.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.util.ArrayList;

public class SlashHandler extends ListenerAdapter {

    public ArrayList<SlashCommand> commands = new ArrayList<>();

    public SlashHandler(JDA jda) {
        jda.addEventListener(this);
    }

    public void registerCommands(JDA jda) {
        double machineTime = System.currentTimeMillis();

        Utils.log("Register des commands avec '/' .... ");

        CommandListUpdateAction command = jda.updateCommands();
        for (SlashCommand slashCommand : commands) {
            Utils.log("Nouvelle commands enregister : " + slashCommand.command);
            command.addCommands(new CommandData(slashCommand.command, slashCommand.description).addOptions(slashCommand.optionData));
        }
        command.queue();

        Utils.log("Tout les commands on été enregistrer avec sucess (" + (System.currentTimeMillis() - machineTime) + "ms)");
    }

    public void registerCommand(SlashCommand slashCommand) {
        commands.add(slashCommand);
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        User user = event.getUser();
        Member member = event.getMember();
        TextChannel textChannel = event.getTextChannel();
        for (SlashCommand slashCommand : commands) {
            if (event.getCommandPath().equalsIgnoreCase(slashCommand.command)) {


                if (slashCommand.getPermission() != null && !member.getPermissions().contains(slashCommand.getPermission())) {
                    event.replyEmbeds(Utils.getEmbedNoPerm(event.getJDA()).build()).setEphemeral(true).queue();
                } else slashCommand.execute(event, member, user, textChannel);
            }
        }

    }
}
