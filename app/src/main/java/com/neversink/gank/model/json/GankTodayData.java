package com.neversink.gank.model.json;

import com.google.gson.annotations.SerializedName;
import com.neversink.gank.model.db.Gank;

import java.util.List;

/**
 * Created by never on 16/1/27.
 */
public class GankTodayData extends BaseData {
    public Result results;

    public class Result {
        @SerializedName("Android")
        public List<Gank> androidList;
        @SerializedName("休息视频")
        public List<Gank> 休息视频List;
        @SerializedName("iOS")
        public List<Gank> iOSList;
        @SerializedName("福利")
        public List<Gank> 妹纸List;
        @SerializedName("拓展资源")
        public List<Gank> 拓展资源List;
        @SerializedName("瞎推荐")
        public List<Gank> 瞎推荐List;
        @SerializedName("App")
        public List<Gank> appList;
    }
}
