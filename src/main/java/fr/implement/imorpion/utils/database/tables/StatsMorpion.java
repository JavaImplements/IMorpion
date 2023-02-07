package fr.implement.imorpion.utils.database.tables;


import fr.implement.imorpion.utils.Utils;
import fr.implement.imorpion.utils.database.mysql.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsMorpion extends MySQL {

    public StatsMorpion(String usedTab) {
        super(usedTab);
    }


    public void createAccount(String user_id, String username) {
        if (!hasAccount(user_id)) {
            try {
                PreparedStatement q = c.prepareStatement("INSERT INTO " + getUsedTab() + "(user_id, username, win) VALUES (?,?,?)");
                q.setString(1, user_id);
                q.setString(2, username);
                q.setInt(3, 0);
                q.execute();
                q.close();

                Utils.logSucces("Les data du joueur " + username + " ont été crée avec succée.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasAccount(String user_id) {
        try {
            PreparedStatement q = c.prepareStatement("SELECT user_id from " + getUsedTab() + " WHERE user_id = ?");
            q.setString(1, user_id);
            ResultSet result = q.executeQuery();
            boolean hasAccount = result.next();
            q.close();

            return hasAccount;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


}
