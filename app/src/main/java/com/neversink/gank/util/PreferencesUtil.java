package com.neversink.gank.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.neversink.gank.App;

/**
 * Created by never on 16/2/2.
 */
public class PreferencesUtil {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PreferencesUtil(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void saveBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void saveBoolean(int key, boolean value) {
        saveBoolean(mContext.getString(key), value);
    }

    public Boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }


    public Boolean getBoolean(String key, boolean def) {
        return mSharedPreferences.getBoolean(key, def);
    }

    public Boolean getBoolean(int keyResId) {
        return mSharedPreferences.getBoolean(mContext.getString(keyResId), false);
    }


    public Boolean getBoolean(int keyResId, boolean def) {
        return mSharedPreferences.getBoolean(mContext.getString(keyResId), def);
    }


    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }


    public void saveInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

}
