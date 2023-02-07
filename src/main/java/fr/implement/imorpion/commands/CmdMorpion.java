package fr.implement.imorpion.commands;


import fr.implement.imorpion.lib.MorpionUtils;
import fr.implement.imorpion.lib.invitations.InvitationsManager;
import fr.implement.imorpion.utils.SlashCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CmdMorpion extends SlashCommand {

    private final MorpionUtils morpionUtils;
    private final InvitationsManager invitationsManager;

    public CmdMorpion() {
        super("morpion", "Faire un morpion", new OptionData(OptionType.USER, "user", "inviter un joueur").setRequired(true));
        this.morpionUtils = MorpionUtils.get();
        this.invitationsManager = InvitationsManager.get();
    }

    @Override
    public void execute(SlashCommandEvent e, Member member, User user, TextChannel textChannel) {
        //morpionUtils.loadMorpion(e);

        if (invitationsManager.alreadySendInvitation(member)) {
            e.reply("Vous avez déjà envoyez une invitation à un joueurs.").setEphemeral(true).queue();
            return;
        }

        Member target = e.getOption("user").getAsMember();

        if (target.getUser().isBot()) {
            e.reply("Vous ne pouvez pas inviter un bot.").setEphemeral(true).queue();
            return;
        }

        if (target.getId().equalsIgnoreCase(member.getId())) {
            e.reply("Vous ne pouvez pas jouer contre vous même.").setEphemeral(true).queue();
            return;
        }

        e.reply(target.getAsMention()).complete();
        morpionUtils.sendInvitation(textChannel, member, target);

        /*

        if(member.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("Morpion"))){
            if(invitationsManager.alreadySendInvitation(member)){
                e.reply("Vous avez déjà envoyez une invitation à un joueurs.").setEphemeral(true).queue();
                return;
            }

            Member target = e.getOption("user").getAsMember();
            e.reply(target.getAsMention()).queue();
            morpionUtils.sendInvitation(textChannel, member, target);
        }else e.reply("Vous n'avez pas la permission.").setEphemeral(true).queue();

         */

    }

    @Override
    public Permission getPermission() {
        return Permission.MESSAGE_WRITE;
    }

}
