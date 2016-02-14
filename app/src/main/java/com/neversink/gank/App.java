package com.neversink.gank;

import android.app.Application;
import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.neversink.gank.util.ToastUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by never on 16/1/25.
 */
public class App extends Application {

    private static final String DB_NAME = "gank.db";
    public static LiteOrm mDb;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.register(this);
        mDb = LiteOrm.newSingleInstance(this
                , DB_NAME);
        if (BuildConfig.DEBUG) {
            mDb.setDebugged(true);
            Picasso.with(this).setIndicatorsEnabled(true);
        }
    }
}
