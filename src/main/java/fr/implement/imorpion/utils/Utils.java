package fr.implement.imorpion.utils;

import fr.implement.imorpion.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static EmbedBuilder getEmbedNoPerm(JDA jda) {
        return new EmbedBuilder().setFooter("Developped by Implements", jda.getSelfUser().getAvatarUrl()).setTimestamp(new Date().toInstant()).setDescription("Vous n'avez pas la permissions.").setColor(Color.RED);
    }

    public static String getHeure() {
        DateFormat f = new SimpleDateFormat("HH:mm:ss");
        return f.format(new Date().getTime());
    }

    public static void log(String str) {
        System.out.println(getHeure() + ConsoleColor.RESET.getColorString() + " - " + ConsoleColor.BLUE.getColorString() + Main.getNameBot() + ConsoleColor.RESET.getColorString() + str + ConsoleColor.RESET.getColorString());
    }

    public static void logSucces(String str) {
        System.out.println(getHeure() + " - " + Main.getNameBot() + ConsoleColor.GREEN.getColorString() + str + ConsoleColor.RESET.getColorString());
    }

    public static void logError(String str) {
        System.out.println(getHeure() + " - " + Main.getNameBot() + ConsoleColor.RED.getColorString() + str + ConsoleColor.RESET.getColorString());
    }

    public static String date2str(Date date) {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return f.format(date);
    }

    public static Date str2date(String date) {
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = null;
        try {
            d = f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
