package fr.implement.imorpion;

import fr.implement.imorpion.managers.BotManagers;
import fr.implement.imorpion.runnable.GameTask;
import fr.implement.imorpion.runnable.InvitationTask;
import fr.implement.imorpion.utils.Utils;
import fr.implement.imorpion.utils.database.DatabaseUser;
import fr.implement.imorpion.utils.database.TaskBDD;
import fr.implement.imorpion.utils.database.mysql.MySQL;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.Timer;

public class JDABot extends ListenerAdapter {

    private static JDA jda;
    private static Activity activity;
    private static String nameBot;

    public static void login(@NotNull String token, Activity activity, String nameLog) throws LoginException {
        setNameBot(nameLog);
        setActivity(activity);

        jda = JDABuilder.createDefault(token)
                .setEnabledIntents(EnumSet.allOf(GatewayIntent.class))
                .addEventListeners(new JDABot())
                .setCompression(Compression.NONE)
                .setAutoReconnect(true)
                .setActivity(getActivity())
                .build();


        Timer t = new Timer();
    }

    public static JDA getJDA() {
        return jda;
    }

    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        JDABot.activity = activity;
    }

    public static String getNameBot() {
        return nameBot;
    }

    public static void setNameBot(String nameBot) {
        JDABot.nameBot = nameBot;
    }

    @Override
    public void onReady(@NotNull ReadyEvent e) {
        new BotManagers(jda).init();
        new GameTask();
        new InvitationTask();
        new TaskBDD();

        Utils.logSucces("Tout les taches ont été lancer.");

        // Connexion Mysql

        MySQL.connect(new DatabaseUser("localhost", 3306, "morpion", "username", "password"), true);

        Utils.logSucces("Le bot est connecter à " + e.getGuildTotalCount() + " serveur(s) discord.");
    }
}
