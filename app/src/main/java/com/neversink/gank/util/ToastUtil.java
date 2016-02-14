package com.neversink.gank.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by never on 16/1/25.
 */
public class ToastUtil {

    private static Context mContext;

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void showShort(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(int s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }

    public static void showLong(int s) {
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }

    public static void showLongX2(String s) {
        showLong(s);
        showLong(s);
    }

    public static void showLongX2(int s) {
        showLong(s);
        showLong(s);
    }

}
