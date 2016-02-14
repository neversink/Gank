package com.neversink.gank.util;

import android.util.Log;

/**
 * Created by never on 16/1/29.
 */
public class LogUtil {

    private static String tagName = "NeverLog";
    private static boolean ifLog = true;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public static void w(String s) {
        if (ifLog) {
            Log.w(tagName, s);
        }
    }

    public static void w(String tag, String s) {
        if (ifLog) {
            Log.w(tag, s);
        }
    }
}
