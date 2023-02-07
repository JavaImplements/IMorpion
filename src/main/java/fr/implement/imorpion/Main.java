package fr.implement.imorpion;

import fr.implement.imorpion.utils.ConsoleColor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class Main extends JDABot {

    /**
     * @author Implements
     * @updated 05/01/22
     * <p>
     * Cette template est en cours de développement.
     */


    public static void main(String[] args) throws LoginException {
        login("token", Activity.listening("/morpion"), ConsoleColor.BLUE.getColorString() + "IMorpion - " + ConsoleColor.RESET.getColorString());

    }

}
