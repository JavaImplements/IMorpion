package fr.implement.imorpion.utils.database;

import fr.implement.imorpion.utils.Utils;
import fr.implement.imorpion.utils.database.mysql.MySQL;

import java.util.Timer;
import java.util.TimerTask;

public class TaskBDD extends TimerTask {

    private Timer t;

    public TaskBDD() {
        this.t = new Timer();
        t.schedule(this, 1000 * 60 * 5, 1000 * 60 * 5);
    }

    @Override
    public void run() {
        MySQL.connect(new DatabaseUser("localhost", 3306, "morpion", "root", "123456"), true);
        Utils.logSucces("Reconnection à la base de donner effectuer.");
    }
}
