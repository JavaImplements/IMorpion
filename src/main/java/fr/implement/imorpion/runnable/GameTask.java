package fr.implement.imorpion.runnable;

import fr.implement.imorpion.lib.Morpion;
import fr.implement.imorpion.lib.MorpionManager;
import fr.implement.imorpion.utils.Utils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameTask extends TimerTask {

    private final MorpionManager manager;
    private Timer t;

    public GameTask() {
        this.manager = MorpionManager.get();
        this.t = new Timer();
        t.schedule(this, 1000, 1000);
    }

    @Override
    public void run() {

        if (manager.getMorpions().isEmpty()) return;

        new HashMap<>(manager.morpions).forEach(((textChannel, morpion) -> {
            //System.out.println("channel id : " + textChannel.getId() + " | id morpion: " + morpion.getId());
            if (!morpion.getStatus() || morpion.getGameTime() >= 300) {
                deleteMorpion(morpion);
                return;
            }
            if (morpion.getPlayerGameTime() >= 30) {
                Utils.logSucces(morpion.getId() + " - Changement de joueurs car " + morpion.getPlayerGame().getUser().getName() + " à mis trop de temps pour jouer.");
                morpion.getChannel().sendMessage(morpion.getPlayerGame().getAsMention() + " à mis trop de temps pour jouez c'est donc à votre tour : " + morpion.getPlayerNotGame().getAsMention()).queue();
                morpion.resetPlayerGameTime().changeTurnPlayer();
            }

            // Add timer
            morpion.addGameTime();
            morpion.addPlayerGameTime();
        }));
    }

    private void deleteMorpion(Morpion morpion) {
        manager.removeMorpion(morpion.getChannel());
        morpion.getChannel().delete().completeAfter(5, TimeUnit.SECONDS);
        Utils.logSucces("[Morpion-" + morpion.getId() + "] Le channel à été supprimer car le jeux est finit.");

    }
}
