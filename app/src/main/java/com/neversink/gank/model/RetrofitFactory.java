package com.neversink.gank.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by never on 16/1/27.
 */
public class RetrofitFactory {
    private static final Object monitor = new Object();
    private static GankApi mGankApi = null;
    private static RestAdapter.Builder builder = null;
    public static final int gankSize = 10;

    static {
        OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(10, TimeUnit.SECONDS);
        builder = new RestAdapter.Builder();
        Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();
        builder.setClient(new OkClient(client)).setEndpoint("http://gank.avosapps.com/api").setConverter(new GsonConverter(GSON));
    }

    public static GankApi getGankApiSingleton() {
        synchronized (monitor) {
            if (mGankApi == null) {
                mGankApi = builder.build().create(GankApi.class);
            }
            return mGankApi;
        }
    }
}
