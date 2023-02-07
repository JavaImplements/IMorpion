package fr.implement.imorpion.listeners;

import fr.implement.imorpion.lib.MorpionUtils;
import fr.implement.imorpion.lib.invitations.InvitationsManager;
import fr.implement.imorpion.lib.invitations.MorpionInvitation;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class InvitationListeners extends ListenerAdapter {

    private InvitationsManager invitationsManager;

    public InvitationListeners() {
        this.invitationsManager = InvitationsManager.get();
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent e) {

        if (e.getComponentId().startsWith("yes")) {
            String[] splitID = e.getComponentId().split("\\.");
            String idMember = splitID[1];
            String idtarget = splitID[2];

            if (e.getMember().getId().equalsIgnoreCase(idtarget)) {

                String idInvitation = splitID[3];
                Optional<MorpionInvitation> invite = invitationsManager.getInvitationWithID(idInvitation);

                invite.ifPresent(i -> {
                    e.getMessage().delete().queue();
                    MorpionUtils.get().loadMorpion(e, i);
                    invitationsManager.removeInvitation(i);
                });
            }

            e.deferEdit().queue();

        } else if (e.getComponentId().startsWith("no")) {
            String[] splitID = e.getComponentId().split("\\.");
            String idMember = splitID[1];
            String idtarget = splitID[2];

            Optional<MorpionInvitation> invitation = invitationsManager.getInvitationWithID(splitID[3]);

            invitation.ifPresent(i -> {

                if (e.getMember().getId().equalsIgnoreCase(idtarget)) {
                    e.getMessage().delete().queue();
                    e.reply(e.getMember().getAsMention() + " à refuser de faire un morpion avec " + i.getMember().getAsMention() + " .").queue();
                    invitationsManager.removeInvitation(i);
                } else e.deferEdit().queue();

            });
        }

    }
}
