package br.com.code4u.minharesidenciainteligente.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ilegra on 9/5/16.
 */
public class ApplicationSession {

    public static void store(String key, String value) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    public static void remove(String key) {
        SharedPreferences sharedPref = getContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        sharedPref.edit().remove(key).commit();
    }

    private static Context getContext(){
        return ApplicationBase.getAppContext();
    }
}
