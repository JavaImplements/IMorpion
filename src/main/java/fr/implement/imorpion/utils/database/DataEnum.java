package fr.implement.imorpion.utils.database;


import fr.implement.imorpion.utils.database.tables.StatsMorpion;

public enum DataEnum {

    STATS(new StatsMorpion("stats")),
    ;


    private final Object object;

    DataEnum(Object object) {
        this.object = object;
    }

    public Object getDatabase() {
        return this.object;
    }
}
