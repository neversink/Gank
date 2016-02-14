package com.neversink.gank.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.neversink.gank.App;
import com.neversink.gank.R;
import com.neversink.gank.func.OnMMTouchListener;
import com.neversink.gank.model.RetrofitFactory;
import com.neversink.gank.model.db.Gank;
import com.neversink.gank.model.json.GankListData;

import com.neversink.gank.util.AlarmManagerUtil;
import com.neversink.gank.util.DateUtil;
import com.neversink.gank.util.LogUtil;
import com.neversink.gank.util.PreferencesUtil;
import com.neversink.gank.util.Stream;
import com.neversink.gank.util.ToastUtil;
import com.neversink.gank.view.adapter.MMListAdapter;
import com.neversink.gank.view.base.SwipeRefreshBaseActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by never on 16/1/25.
 */
public class MainActivity extends SwipeRefreshBaseActivity {


    private static final int PRELOAD_SIZE = 6;
    @Bind(R.id.rv_mm)
    RecyclerView mRecyclerView;

    private MMListAdapter mMMListAdapter;
    private boolean mMMBeTouched = false;
    private boolean isFirstTimeTouchBottom = true;
    private PreferencesUtil mPreferencesUtil;
    private List<Gank> mMMList;
    private int mPage = 1;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onToolbarClick() {
        mRecyclerView.smoothScrollToPosition(1);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMMList = new ArrayList<>();
        QueryBuilder<Gank> query = new QueryBuilder(Gank.class);
        query.appendOrderDescBy("publishedAt");
        query.limit(0, 10);
        mMMList.addAll(App.mDb.query(query));
        checkAlarmService();
        setupRecycleView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        setRequestDataRefresh(true);
        requestDataRefresh();

    }


    private void setupRecycleView() {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mMMListAdapter = new MMListAdapter(this, mMMList);
        mRecyclerView.setAdapter(mMMListAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));
        mMMListAdapter.setOnMMTouchListener(getOnMMTouchListener());
    }

    @Override
    public void requestDataRefresh() {
        loadData(true);
    }

    private void loadData(boolean clean) {
        setRequestDataRefresh(true);
//        new Handler().postDelayed(() -> setRequestDataRefresh(true), 100);
        if (clean) {
            mPage = 1;
            mMMList.clear();
        }
        Subscription s = Observable.zip(mGankApi.getMMData(mPage), mGankApi.get休息视频Data(mPage), this::createMeizhiDataWith休息视频Desc)
                .map(mmData -> mmData.results).flatMap(Observable::from)
                .toSortedList((mm1, mm2) -> mm2.publishedAt.compareTo(mm1.publishedAt))
                .doOnNext(this::saveMM).observeOn(AndroidSchedulers.mainThread())
                .subscribe(mms -> {
                    mPage += 1;
                    mMMList.addAll(mms);
                    mMMListAdapter.notifyItemRangeInserted(RetrofitFactory.gankSize * (mPage - 1), RetrofitFactory.gankSize * mPage);
                    setRequestDataRefresh(false);
                }, this::loadError);
        addSubscription(s);
    }

    public GankListData createMeizhiDataWith休息视频Desc(GankListData data, GankListData video) {
        Stream.from(data.results).forEach(mm -> mm.desc = mm.desc + " " + getFirstVideoDesc(mm.publishedAt, video.results));
        return data;
    }

    public String getFirstVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = 0; i < results.size(); i++) {
            Gank video = results.get(i);
            if (DateUtil.isTheSameDay(video.publishedAt, publishedAt)) {
                videoDesc = video.desc;
                break;
            }
        }
        return videoDesc;
    }

    public void saveMM(List<Gank> mms) {
        App.mDb.insert(mms, ConflictAlgorithm.Replace);
    }

    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        setRequestDataRefresh(false);
        Snackbar.make(mRecyclerView, R.string.snap_load_fail,
                Snackbar.LENGTH_LONG).setAction(R.string.retry, v -> {
            loadData(false);
        }).show();
    }

    private RecyclerView.OnScrollListener getOnBottomListener(StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom = layoutManager.findLastCompletelyVisibleItemPositions(null)[1] >= (mMMListAdapter.getItemCount() - PRELOAD_SIZE);
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!isFirstTimeTouchBottom) {
                        loadData(false);
                    } else isFirstTimeTouchBottom = false;
                }
            }
        };
    }

    private OnMMTouchListener getOnMMTouchListener() {
        return (v, mmView, titleView, mm) -> {
            if (mm == null) return;
            if (v == mmView && !mMMBeTouched) {
                mMMBeTouched = true;
                Picasso.with(this).load(mm.url).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        mMMBeTouched = false;
                        startPictureActivity(mm, mmView);
                    }

                    @Override
                    public void onError() {
                        mMMBeTouched = false;
                    }
                });
            } else if (v == titleView) {
                startGankActivity(mm);
            }
        };
    }

    private void startGankActivity(Gank mm) {
        Intent i = GankActivity.newIntent(this, mm.publishedAt, mMMList.get(0).publishedAt);
        startActivity(i);
    }

    private void startPictureActivity(Gank mm, View mmView) {
        Intent i = PictureActivity.newIntent(MainActivity.this, mm.url, mm.desc);
        try {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, mmView, PictureActivity.TRANSIT_PIC);
            ActivityCompat.startActivity(MainActivity.this, i, optionsCompat.toBundle());

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(i);
        }
    }

    private void checkAlarmService() {
        if (new PreferencesUtil(this).getBoolean(R.string.menu_notifiable)) {
            AlarmManagerUtil.register(this);
            LogUtil.w("register alarm");
        } else {
            AlarmManagerUtil.unRegister(this);
            LogUtil.w("unregister alarm");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mPreferencesUtil = new PreferencesUtil(this);
        menu.findItem(R.id.action_notifiable).setChecked(mPreferencesUtil.getBoolean(R.string.menu_notifiable, true));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notifiable:
                boolean isChecked = !item.isChecked();
                item.setChecked(isChecked);
                mPreferencesUtil.saveBoolean(R.string.menu_notifiable, isChecked);
                ToastUtil.showShort(isChecked
                        ? R.string.notifiable_on
                        : R.string.notifiable_off);
                checkAlarmService();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
