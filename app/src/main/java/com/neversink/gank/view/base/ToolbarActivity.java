package com.neversink.gank.view.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.neversink.gank.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by never on 16/1/25.
 */
public abstract class ToolbarActivity extends BaseActivity {

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    protected boolean mIsHiden = false;



    public void onToolbarClick() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar.setOnClickListener(v -> onToolbarClick());
        setSupportActionBar(mToolbar);

        if (canBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            mAppBarLayout.setElevation(10.6f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else return super.onOptionsItemSelected(item);
    }

    public boolean canBack() {
        return false;
    }

    protected void setAppBarAlpha(float alpha) {
        mAppBarLayout.setAlpha(alpha);
    }

    protected void showOrHideToolbar() {
        mAppBarLayout.animate().translationY(mIsHiden ? 0 : -mAppBarLayout.getHeight()).setInterpolator(new DecelerateInterpolator(2)).start();
        mIsHiden = !mIsHiden;
    }
}
