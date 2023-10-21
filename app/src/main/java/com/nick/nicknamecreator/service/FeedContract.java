package com.nick.nicknamecreator.service;

import android.provider.BaseColumns;

public class FeedContract {
    private FeedContract(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "nick_table";
        public static final String COLUMN_MEMO = "nickname";
    }

    public static final String SQL_CREATE = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            FeedEntry.COLUMN_MEMO + " TEXT)";

    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

}
