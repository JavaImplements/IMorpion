package fr.implement.imorpion.utils.database.mysql;


import fr.implement.imorpion.utils.Utils;
import fr.implement.imorpion.utils.database.DatabaseUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class MySQL {

    public static Connection c;
    private final String usedTab;

    public MySQL(String usedTab) {
        this.usedTab = usedTab;
    }

    public static void connect(String host, int port, String database, String user, String password, boolean autoReconnect) {

        if (!isConnected()) {
            try {

                c = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=" + autoReconnect, user, password);

                Utils.logSucces("[Mysql-LOG] La liaison entre le Bot et la base de donner a été effectuée avec succès");
            } catch (SQLException e) {
                Utils.logError("[Mysql-LOG] La liaison entre le Bot et la base de donner à échouer. (Faire les modification et restart le bot)");
                e.printStackTrace();
            }
        }

    }

    public static void connect(DatabaseUser user, boolean autoReconnect) {
        connect(user.getHost(), user.getPort(), user.getDatabase(), user.getUser(), user.getPassword(), autoReconnect);
    }

    public static boolean isConnected() {
        try {
            if (c == null || c.isClosed() || c.isValid(5)) return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public Object get(String objectNameEntry, String objectNameExit, String column) {
        try {
            PreparedStatement q = c.prepareStatement("SELECT " + column + " FROM " + usedTab + " WHERE " + objectNameEntry + " = ?");
            q.setString(1, objectNameExit);

            Object ob = null;
            ResultSet rs = q.executeQuery();

            while (rs.next()) ob = rs.getObject(column);

            q.close();

            return ob;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void set(String objectNameEntry, String objectNameExit, String column, Object object) {
        try {
            PreparedStatement q = c.prepareStatement("UPDATE " + usedTab + " SET " + column + " = ? WHERE " + objectNameEntry + " = ?");
            q.setObject(1, object);
            q.setString(2, objectNameExit);
            q.executeUpdate();
            q.close();

            Utils.log("Changement dans la colone '" + column + "' => '" + object + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object> takeAll(String column) {
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM " + usedTab);

            List<Object> list = new ArrayList<>();

            while (rs.next()) list.add(rs.getObject(column));

            rs.close();
            s.close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public HashMap<String, Integer> takeAllH(String column) {
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM " + usedTab);

            HashMap<String, Integer> list = new HashMap<>();

            while (rs.next()) list.put(rs.getString("username"), rs.getInt("win"));

            rs.close();
            s.close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void trim(String column) {
        try {
            PreparedStatement q = c.prepareStatement("UPDATE " + usedTab + " SET " + column + " = TRIM(CHAR(9) FROM TRIM(0))");
            q.executeUpdate();
            q.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getUsedTab() {
        return usedTab;
    }

}
