package fr.implement.imorpion.utils.classement;

import fr.implement.imorpion.Main;
import fr.implement.imorpion.utils.database.DataEnum;
import fr.implement.imorpion.utils.database.tables.StatsMorpion;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ClassementManager {

    private static ClassementManager instance;

    private ArrayList<PlayerStats> playerStats = new ArrayList<>();
    private StatsMorpion stats = (StatsMorpion) DataEnum.STATS.getDatabase();

    public static ClassementManager get() {
        if (instance == null) instance = new ClassementManager();
        return instance;
    }

    public EmbedBuilder generated() {
        long system = System.currentTimeMillis();

        playerStats.clear();

        stats.takeAllH("win").forEach((username, win) -> {
            playerStats.add(new PlayerStats(username, win));
        });

        Comparator<PlayerStats> c = Comparator.comparingInt(PlayerStats::getWin).reversed();
        playerStats.sort(c);

        if (playerStats.size() >= 10) playerStats = new ArrayList<>(playerStats.subList(0, 10));

        StringBuilder str = new StringBuilder();

        int i = 0;

        for (PlayerStats playerStat : playerStats) {
            i++;
            str.append(i + " - __" + playerStat.getUsername() + "__ : *" + playerStat.getWin() + " parties gagnées*\n");
        }

        long ms = (System.currentTimeMillis() - system);
        EmbedBuilder em = new EmbedBuilder().setTitle("**Morpion - Classement**").setDescription("\n \uD83D\uDD79 __Le leaderboard à été générer en " + ms + " ms__ \n\n" + str).setTimestamp(new Date().toInstant()).setFooter("Développed by Implements", Main.getJDA().getSelfUser().getAvatarUrl());
        return em;
    }


}
