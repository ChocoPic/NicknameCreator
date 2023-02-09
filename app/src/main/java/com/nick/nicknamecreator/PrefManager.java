package com.nick.nicknamecreator;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static int size;
    private static SharedPreferences pref;

    public static SharedPreferences getPreferences(Context context){
        pref = context.getSharedPreferences("nick_preference", Context.MODE_PRIVATE);
        return pref;
    }

    public static void setString(Context context, String key, String value) {
        size++;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "");
        return value;
    }

    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}
