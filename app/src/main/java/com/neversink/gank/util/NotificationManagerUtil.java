package com.neversink.gank.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by never on 16/2/14.
 */
public class NotificationManagerUtil {

    public static final int NOTIFICATION_REQUEST_CODE = 1;

    public static void show(Context context, Class targetActivity, String title, String content, int largeIcon, int smallIcon, int code) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE ,new Intent(context, targetActivity), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent).setSmallIcon(smallIcon).setLargeIcon(BitmapFactory.decodeResource(context
                .getResources(), largeIcon)).setWhen(System.currentTimeMillis()).setAutoCancel(false)
                .setContentTitle(title).setContentText(content);

        Notification notification = builder.getNotification();
        notificationManager.notify(code, notification);
    }
}
