package com.example.notepad.db;

public class MyConstants {
    public static final String LIST_ITEM_INTENT = "edit_state";
    public static final String EDIT_STATE = "list_item_intent";
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DISC = "disc";
    public static final String URL = "url";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 2;
    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT," + DISC + " TEXT," +
            URL + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
