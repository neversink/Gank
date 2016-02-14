package com.neversink.gank.view.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.neversink.gank.R;
import com.neversink.gank.model.GankApi;
import com.neversink.gank.model.db.Gank;
import com.neversink.gank.model.json.GankTodayData;
import com.neversink.gank.view.GankActivity;
import com.neversink.gank.view.adapter.GankListAdapter;
import com.neversink.gank.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by never on 16/2/1.
 */
public class GankFragment extends Fragment {

    public final static String ARGS_YEAR = "year";
    public final static String ARGS_MONTH = "month";
    public final static String ARGS_DAY = "day";

    @Bind(R.id.stub_empty_view)
    ViewStub mEmptyViewStub;
    @Bind(R.id.rv_gank)
    RecyclerView mRecyclerView;
    TextView emptyTextView;

    private View rootView;
    private int mYear, mMonth, mDay;
    private List<Gank> mGankList;
    private GankListAdapter mAdapter;
    private Subscription mSubscription;
    private Typeface mTypeface;


    public static Fragment newInstance(int year, int month, int day) {
        GankFragment gankFragment = new GankFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_YEAR, year);
        args.putInt(ARGS_MONTH, month);
        args.putInt(ARGS_DAY, day);
        gankFragment.setArguments(args);
        return gankFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mYear = args.getInt(ARGS_YEAR);
        mMonth = args.getInt(ARGS_MONTH);
        mDay = args.getInt(ARGS_DAY);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, rootView);
        mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "font.TTF");
        setupRecycleView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mGankList.size() == 0) loadData();

    }

    private void loadData() {
        mSubscription = BaseActivity.mGankApi.getGankData(mYear, mMonth, mDay)
                .map(datas -> datas.results)
                .map(this::addAllResults)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mList -> {
                            if (mList.isEmpty()) {
                                mEmptyViewStub.inflate();
                                emptyTextView = (TextView) rootView.findViewById(R.id.tv_empty);
                                emptyTextView.setTypeface(mTypeface);
                            } else mAdapter.notifyDataSetChanged();
                        }, Throwable::printStackTrace
                );
    }

    private List<Gank> addAllResults(GankTodayData.Result results) {
        if (results.androidList != null) mGankList.addAll(results.androidList);
        if (results.iOSList != null) mGankList.addAll(results.iOSList);
        if (results.appList != null) mGankList.addAll(results.appList);
        if (results.拓展资源List != null) mGankList.addAll(results.拓展资源List);
        if (results.瞎推荐List != null) mGankList.addAll(results.瞎推荐List);
        if (results.休息视频List != null) mGankList.addAll(0, results.休息视频List);
        return mGankList;
    }

    private void setupRecycleView() {
        mGankList = new ArrayList<>();
        mAdapter = new GankListAdapter(mGankList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
