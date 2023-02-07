package fr.implement.imorpion.lib;

import fr.implement.imorpion.utils.Utils;
import fr.implement.imorpion.utils.database.DataEnum;
import fr.implement.imorpion.utils.database.tables.StatsMorpion;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Morpion {


    private final int id;
    private final Member player;
    private final Member target;
    private final TextChannel textChannel;
    private StatsMorpion stats = (StatsMorpion) DataEnum.STATS.getDatabase();
    private Member gamePlayer;
    private int gameTime;
    private int playerGameTime;
    private boolean statusGame;
    private int matchNull;

    private HashMap<String, Component> cases = new HashMap<>();

    public Morpion(TextChannel textChannel, Member player, Member target) {
        this.textChannel = textChannel;
        this.player = player;
        this.target = target;
        this.gamePlayer = new Random().nextBoolean() ? target : player;
        this.gameTime = 0;
        this.playerGameTime = 0;
        this.statusGame = true;
        this.matchNull = 0;
        this.id = new Random().nextInt(9999);

        // Create all cases
        for (int i = 0; i <= 9; i++)
            cases.put(this.getChannelId() + "." + i, Button.secondary(this.getChannelId() + "." + i, " "));

        Utils.logSucces("Création d'un morpion avec l'id : " + this.id);
    }

    public void playerPushedButton(ButtonClickEvent e) {
        Message msg = e.getMessage();

        if (gamePlayer != null && !gamePlayer.equals(e.getMember())) {
            msg.editMessage(gamePlayer.getUser().getAsMention()).queue();
            e.deferEdit().queue();
            return;
        }

        String buttonId = e.getComponentId();
        Button nButton = Button.primary(buttonId, Emoji.fromUnicode(getSymbole(gamePlayer))).asDisabled();

        e.getInteraction().editButton(nButton).complete();
        cases.replace(buttonId, nButton);

        Optional<Member> winner = Optional.ofNullable(getWinner(gamePlayer));


        if (winner.isPresent()) {
            e.getMessage().editMessage(gamePlayer.getUser().getAsMention() + " vous avez gagné.").complete();
            this.gamePlayer = null;
            this.statusGame = false;
            Utils.logSucces(winner.get().getUser().getName() + " à gagner.");
            int count = (int) stats.get("user_id", winner.get().getId(), "win");
            stats.set("user_id", winner.get().getId(), "win", count + 1);
            return;
        }


        this.matchNull++;
        if (matchNull >= 9) {
            msg.editMessage("Personne à gagné.. :'(").complete();
            MorpionManager.get().removeMorpion(textChannel);
            textChannel.delete().completeAfter(5, TimeUnit.SECONDS);
            return;
        }

        changeTurnPlayer();
        msg.editMessage("A votre tour " + gamePlayer.getUser().getAsMention() + " !").complete();
    }

    private Member getWinner(Member m) {
        int[][] possibilities = new int[][]{{0, 4, 8}, {2, 4, 6}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        int[] currentPossiblility = new int[4];
        int j = 0;

        for (int[] possibility : possibilities) {
            Arrays.fill(currentPossiblility, 0);
            for (int i : possibility) {
                currentPossiblility[j] = i;
                j++;
            }
            j = 0;

            Button button1 = (Button) cases.get(this.getChannelId() + "." + currentPossiblility[0]);
            Button button2 = (Button) cases.get(this.getChannelId() + "." + currentPossiblility[1]);
            Button button3 = (Button) cases.get(this.getChannelId() + "." + currentPossiblility[2]);

            if (button1.getEmoji() == null || button2.getEmoji() == null || button3.getEmoji() == null) continue;

            if (button1.getEmoji().getName().equalsIgnoreCase(button3.getEmoji().getName()) && button1.getEmoji().getName().equalsIgnoreCase(button2.getEmoji().getName()))
                return getMemberWithSymbol(getSymbole(m));

        }

        return null;
    }


    public String getChannelId() {
        return textChannel.getId();
    }

    public TextChannel getChannel() {
        return textChannel;
    }

    public int getId() {
        return id;
    }

    public HashMap<String, Component> getCases() {
        return cases;
    }

    public String getSymbole(Member m) {
        return m.equals(player) ? "U+274C" : "U+2B55";
    }

    public Member getMemberWithSymbol(String symbol) {
        return getSymbole(player).equalsIgnoreCase(symbol) ? player : target;
    }

    public int getGameTime() {
        return this.gameTime;
    }

    public void addGameTime() {
        this.gameTime++;
    }

    public int getPlayerGameTime() {
        return this.playerGameTime;
    }

    public Morpion resetPlayerGameTime() {
        this.playerGameTime = 0;
        return this;
    }

    public void addPlayerGameTime() {
        this.playerGameTime++;
    }

    public Member getPlayerGame() {
        return this.gamePlayer;
    }

    public Member getPlayerNotGame() {
        return this.gamePlayer.equals(player) ? target : player;
    }

    public void changeTurnPlayer() {
        resetPlayerGameTime();
        gamePlayer = gamePlayer.equals(player) ? target : player;
    }

    public boolean getStatus() {
        return this.statusGame;
    }

    ;

}
