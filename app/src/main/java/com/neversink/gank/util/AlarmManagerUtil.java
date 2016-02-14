package com.neversink.gank.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.neversink.gank.service.AlarmReceiver;
import com.neversink.gank.view.MainActivity;

import java.util.Calendar;

/**
 * Created by never on 16/2/14.
 */
public class AlarmManagerUtil {

    private static final int ALARM_REQUEST_CODE = 666;

    public static void register(Context context) {
        Calendar today = Calendar.getInstance();
        Calendar now = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 30);
        today.set(Calendar.SECOND, 0);

        Intent intent = new Intent("com.neversink.gank.alarm");
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), 24 * 60 * 60 * 1000, broadcast);

    }

    public static void unRegister(Context context) {
        Intent intent = new Intent("com.neversink.gank.alarm");
        intent.setClass(context, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(broadcast);
    }
}
