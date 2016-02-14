package com.neversink.gank.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.neversink.gank.R;
import com.neversink.gank.util.NotificationManagerUtil;
import com.neversink.gank.view.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerUtil.show(context, MainActivity.class,
                context.getString(R.string.headsup_title),
                context.getString(R.string.headsup_content),
                R.mipmap.ic_meizhi_150602, R.mipmap.ic_female, 123123);
    }
}
