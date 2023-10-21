package com.nick.nicknamecreator.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "memo.db";

    public static SQLHelper inst;

    private SQLiteDatabase db;
    private Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(FeedContract.SQL_DELETE);
        onCreate(db);
    }

    public static SQLHelper getInst(Context context) {
        if (inst == null)
            inst = new SQLHelper(context);
        return inst;
    }

    public Cursor getAllData() {
        String query = "SELECT * FROM " + FeedContract.FeedEntry.TABLE_NAME;
        Cursor c = db.rawQuery(query, null);
        return c;
    }


    public long insertMemo(String data) {
        ContentValues values = new ContentValues();
        values.put(FeedContract.FeedEntry.COLUMN_MEMO, data);
        return db.insert(FeedContract.FeedEntry.TABLE_NAME, null, values);
    }

    public void deleteMemo(String data) {
        long id = getID_fromData(data);
        db.delete(FeedContract.FeedEntry.TABLE_NAME, FeedContract.FeedEntry.COLUMN_MEMO + "=? and "
                + FeedContract.FeedEntry._ID + "=?", new String[]{data, String.valueOf(id)});
    }

    public long getID_fromData(String data) {
        Cursor cursor = db.query(FeedContract.FeedEntry.TABLE_NAME, new String[]{FeedContract.FeedEntry._ID}, FeedContract.FeedEntry.COLUMN_MEMO + "=?", new String[]{String.valueOf(data)}, null, null, null);
        long id = 0;
        while (cursor.moveToNext()) {
            int column = cursor.getColumnIndex(FeedContract.FeedEntry._ID);
            id = cursor.getLong(column);
        }
        cursor.close();
        return id;
    }

    public void clearMemo(Context context) {
        db.delete(FeedContract.FeedEntry.TABLE_NAME, null, null);
    }

}