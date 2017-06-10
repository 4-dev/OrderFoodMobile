package br.com.orderFood.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

/**
 * Created by Ruan Alves
 */
public class EasySharedPreferences {

    public static String PREF_LATITUDE = "LATITUDE";
    public static String PREF_LONGITUDE = "LONGITUDE";
    public static String PREF_DATALOCALIZACAO = "DATALOCALIZACAO";

    public static String getStringFromKey(Context context, String key){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key,"");
    }

    public static void setStringFromKey(Context context, String key, String value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.commit();
    }
}
