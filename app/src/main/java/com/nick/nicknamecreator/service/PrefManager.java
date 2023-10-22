package com.nick.nicknamecreator.service;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static int size;

    public static SharedPreferences getPreferences(Context context){
        return context.getSharedPreferences("nick_preference", Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        size++;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        return prefs.getString(key, "");
    }

    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();
    }
}
