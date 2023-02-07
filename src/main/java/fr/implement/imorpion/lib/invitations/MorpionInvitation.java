package fr.implement.imorpion.lib.invitations;

import net.dv8tion.jda.api.entities.Member;

import java.util.Random;

public class MorpionInvitation {

    private int id;
    private int timer;
    private Member member;
    private Member target;

    public MorpionInvitation(Member member, Member target) {
        this.member = member;
        this.target = target;
        this.timer = 0;
        this.id = new Random().nextInt(9999);
    }


    public Member getMember() {
        return member;
    }

    public Member getTarget() {
        return target;
    }

    public int getTimer() {
        return timer;
    }

    public void addTime() {
        this.timer++;
    }

    public int getId() {
        return id;
    }
}
