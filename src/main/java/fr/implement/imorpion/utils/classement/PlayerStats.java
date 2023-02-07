package fr.implement.imorpion.utils.classement;

public class PlayerStats {

    private final String username;
    private final int win;

    public PlayerStats(String username, int win) {
        this.username = username;
        this.win = win;
    }

    public String getUsername() {
        return username;
    }

    public int getWin() {
        return win;
    }
}
