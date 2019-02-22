package com.example.adrian.monmatest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class Funciones {

    public static void cambiarIdioma(Activity activity, String idioma) {
        if (idioma == null) {
            //recuperar valor de las Preferences
            idioma = getPreferenceString(activity, Constantes.ID_IDIOMA, Constantes.IDIOMA_DEFAULT);
        } else {
            //guardar en el momento la Preference
            setPreferenceString(activity, Constantes.ID_IDIOMA, idioma);
        }

        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        activity.getBaseContext().getResources().updateConfiguration(config,
                activity.getBaseContext().getResources().getDisplayMetrics());
    }

    public static String getPreferenceString(Context c, String key, String defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getString(key, defaultValue);
    }

    public static boolean getPreferenceBoolean(Context c, String key, boolean defaultValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(c);
        return preferences.getBoolean(key, defaultValue);
    }

    public static boolean setPreferenceString(Context c, String key, String value) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.commit();

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
