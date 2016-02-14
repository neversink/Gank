package com.neversink.gank.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.neversink.gank.R;
import com.neversink.gank.model.db.Gank;
import com.neversink.gank.util.DateUtil;
import com.neversink.gank.view.adapter.GankPagerAdapter;
import com.neversink.gank.view.base.ToolbarActivity;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by never on 16/2/1.
 */
public class GankActivity extends ToolbarActivity{

    public static final String EXTRA_GANK_DATE = "gank_date";
    public static final String EXTRA_LATEST_DATE = "latest_date";

    @Bind(R.id.pager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.ts)
    TextSwitcher mTextSwitcher;

    private Date mDate;
    private Date mLatestDate;
    private GankPagerAdapter mAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextSwitcher.setFactory(() -> {
            TextView tv = new TextView(GankActivity.this);
            tv.setTextAppearance(this, R.style.textswitcher_textappearence);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.postDelayed(() -> tv.setSelected(true), 2000);
            return tv;
        });


        mDate = (Date) getIntent().getSerializableExtra(EXTRA_GANK_DATE);
        mLatestDate = (Date) getIntent().getSerializableExtra(EXTRA_LATEST_DATE);
        initViews();
    }

    public void setTitle(String title) {
        mTextSwitcher.setText(title);
    }


    private void initViews() {
        setTitle(DateUtil.toDate(mDate));
        mAdapter = new GankPagerAdapter(getSupportFragmentManager(), mLatestDate);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(DateUtil.calDays(mLatestDate, mDate));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(DateUtil.toDate(mLatestDate, -position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static Intent newIntent(Context context, Date date) {
        Intent i = new Intent(context, GankActivity.class);
        i.putExtra(EXTRA_GANK_DATE, date);
        return i;
    }


    public static Intent newIntent(Context context, Date clickDate, Date latestDate) {
        Intent i = new Intent(context, GankActivity.class);
        i.putExtra(EXTRA_GANK_DATE, clickDate);
        i.putExtra(EXTRA_LATEST_DATE, latestDate);
        return i;
    }
}
