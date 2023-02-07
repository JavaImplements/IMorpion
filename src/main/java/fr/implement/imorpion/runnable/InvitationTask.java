package fr.implement.imorpion.runnable;

import fr.implement.imorpion.lib.invitations.InvitationsManager;
import fr.implement.imorpion.utils.Utils;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class InvitationTask extends TimerTask {

    private final InvitationsManager invitationsManager;

    public InvitationTask() {
        this.invitationsManager = InvitationsManager.get();
        Timer t = new Timer();
        t.schedule(this, 1000, 1000);
    }

    @Override
    public void run() {
        new HashSet<>(invitationsManager.getInvitations()).forEach(invitation -> {

            if (invitation.getTimer() >= 30) {
                Utils.logSucces("L'invitation " + invitation.getId() + " à été supprimer en raison de la non reponse du joueurs.");
                invitationsManager.removeInvitation(invitation);
                return;
            }

            invitation.addTime();
        });
    }
}
