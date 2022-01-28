package com.thanguit.tiushop.local;

import android.content.Context;

public class DataLocalManager {
    private static DataLocalManager instance;
    private SharedPreferencesManager sharedPreferencesManager;

    private static final String FIRST_RUN = "FIRST_RUN";
    private static final String THEME = "THEME";
    private static final String LANGUAGE = "LANGUAGE";

    private DataLocalManager() {
    }

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.sharedPreferencesManager = new SharedPreferencesManager(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            synchronized (DataLocalManager.class) {
                if (instance == null) {
                    instance = new DataLocalManager();
                }
            }
        }
        return instance;
    }

//    public static void setTheme(boolean theme) {
//        DataLocalManager.getInstance().sharedPreferencesManager.putBooleanValue(THEME.trim(), theme);
//    }
//
//    public static boolean getTheme() {
//        return DataLocalManager.getInstance().sharedPreferencesManager.getBooleanValue(THEME.trim());
//    }
//
//    public static void setLanguage(String code) {
//        DataLocalManager.getInstance().sharedPreferencesManager.putStringValue(LANGUAGE.trim(), code);
//    }
//
//    public static String getLanguage() {
//        return DataLocalManager.getInstance().sharedPreferencesManager.getStringValue(LANGUAGE.trim());
//    }

    public static void setFirstRun(boolean firstRun) {
        DataLocalManager.getInstance().sharedPreferencesManager.putBooleanValue(FIRST_RUN, firstRun);
    }

    public static boolean getFirstRun() {
        return DataLocalManager.getInstance().sharedPreferencesManager.getBooleanValue(FIRST_RUN);
    }
}
