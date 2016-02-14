package com.neversink.gank.model;

import com.neversink.gank.model.json.GankListData;
import com.neversink.gank.model.json.GankTodayData;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by never on 16/1/27.
 */
public interface GankApi {

    @GET("/data/福利/" + RetrofitFactory.gankSize + "/{page}")
    Observable<GankListData> getMMData(@Path("page") int page);

    @GET("/day/{year}/{month}/{day}")
    Observable<GankTodayData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("/data/休息视频/" + RetrofitFactory.gankSize + "/{page}")
    Observable<GankListData> get休息视频Data(
            @Path("page") int page);
}
