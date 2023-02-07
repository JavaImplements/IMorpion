package fr.implement.imorpion.lib.invitations;

import net.dv8tion.jda.api.entities.Member;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class InvitationsManager {

    private static InvitationsManager instance;
    private final Set<MorpionInvitation> invitations = new HashSet<>();

    public static InvitationsManager get() {
        if (instance == null) instance = new InvitationsManager();
        return instance;
    }

    public Set<MorpionInvitation> getInvitations() {
        return invitations;
    }

    public Optional<MorpionInvitation> getInvitationWithID(String id) {
        for (MorpionInvitation invitation : invitations) {
            if (invitation.getId() == Integer.parseInt(id)) return Optional.of(invitation);
        }
        return Optional.empty();
    }

    public boolean alreadySendInvitation(Member m) {
        for (MorpionInvitation invitation : invitations) {
            if (invitation.getMember().equals(m)) return true;
        }
        return false;
    }

    public void addInvitations(MorpionInvitation invitation) {
        this.invitations.add(invitation);
    }

    public void removeInvitation(MorpionInvitation invitation) {
        this.invitations.remove(invitation);
    }

}
