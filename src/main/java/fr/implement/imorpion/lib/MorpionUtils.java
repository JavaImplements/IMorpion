package fr.implement.imorpion.lib;

import fr.implement.imorpion.Main;
import fr.implement.imorpion.lib.invitations.InvitationsManager;
import fr.implement.imorpion.lib.invitations.MorpionInvitation;
import fr.implement.imorpion.utils.database.DataEnum;
import fr.implement.imorpion.utils.database.tables.StatsMorpion;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.Date;

public class MorpionUtils {

    private static MorpionUtils instance;
    private InvitationsManager invitationsManager;
    private StatsMorpion stats = (StatsMorpion) DataEnum.STATS.getDatabase();

    public MorpionUtils() {
        instance = this;
        this.invitationsManager = InvitationsManager.get();
    }

    public static MorpionUtils get() {
        if (instance == null) instance = new MorpionUtils();
        return instance;
    }

    public void loadMorpion(ButtonClickEvent e, MorpionInvitation invitation) {
        Guild guild = e.getGuild();
        Member member = invitation.getMember();
        Member target = invitation.getTarget();

        stats.createAccount(member.getId(), member.getUser().getName());
        stats.createAccount(target.getId(), target.getUser().getName());

        guild.createTextChannel("morpion-").submit().whenComplete((textChannel, throwable) -> {

            textChannel.sendMessageEmbeds(new EmbedBuilder().setTitle("\uD83D\uDD79 | Un duel à été lancer entre " + member.getUser().getName() + " et " + target.getUser().getName() + ".").setDescription("Bonne chance... \nBienvenue dans une partie de morpion.\nCliquez sur les cases pour jouer.").setColor(7005763).setFooter("Développed by Implements", e.getJDA().getSelfUser().getAvatarUrl()).setTimestamp(new Date().toInstant()).build()).queue();

            Morpion m = new Morpion(textChannel, member, target);
            MorpionManager.get().addMorpion(m);

            MessageAction ma1 = textChannel.sendMessage("C'est le tour de " + m.getPlayerGame().getAsMention());
            ma1.setActionRows(ActionRow.of(m.getCases().get(textChannel.getId() + ".0"), m.getCases().get(textChannel.getId() + ".1"), m.getCases().get(textChannel.getId() + ".2")),
                    ActionRow.of(m.getCases().get(textChannel.getId() + ".3"), m.getCases().get(textChannel.getId() + ".4"), m.getCases().get(textChannel.getId() + ".5")),
                    ActionRow.of(m.getCases().get(textChannel.getId() + ".6"), m.getCases().get(textChannel.getId() + ".7"), m.getCases().get(textChannel.getId() + ".8"))).queue();


            textChannel.getManager().setName("morpion-" + m.getId()).queue();

            // Permissions
            textChannel.createPermissionOverride(e.getGuild().getPublicRole()).setAllow(Permission.MESSAGE_WRITE).setDeny(Permission.VIEW_CHANNEL).submit();
            textChannel.createPermissionOverride(member).setAllow(Permission.VIEW_CHANNEL).setDeny(Permission.MESSAGE_WRITE).submit();
            textChannel.createPermissionOverride(target).setAllow(Permission.VIEW_CHANNEL).setDeny(Permission.MESSAGE_WRITE).submit();


        });
    }

    public void sendInvitation(TextChannel textChannel, Member member, Member target) {
        MorpionInvitation morpionInvitation = new MorpionInvitation(member, target);
        invitationsManager.addInvitations(morpionInvitation);

        EmbedBuilder em = new EmbedBuilder().setTitle("\uD83D\uDD79 | Bienvenue dans le Morpion ! ").setDescription("Bonjour, " + target.getAsMention() + "\n c'est très simple vous avez une une interface avec 9 cases.\nLorsque il vous sera indiquer de jouer, il vous faudra juste cliquer ou vous voulez placer votre X ou O suivant ce qu'il vous à été définit.").setColor(7005763).setFooter("Développed by Implements", Main.getJDA().getSelfUser().getAvatarUrl()).setTimestamp(new Date().toInstant());
        MessageAction ma = textChannel.sendMessageEmbeds(em.build());
        ma.setActionRows(ActionRow.of(Button.success("yes." + member.getId() + "." + target.getId() + "." + morpionInvitation.getId(), "\t").withEmoji(Emoji.fromUnicode("U+2705")), Button.danger("no." + member.getId() + "." + target.getId() + "." + morpionInvitation.getId(), "\t").withEmoji(Emoji.fromUnicode("U+274C")))).queue();


    }


}
