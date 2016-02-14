package com.neversink.gank.view.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.neversink.gank.R;
import com.neversink.gank.widget.MultiSwipeRefreshLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by never on 16/1/25.
 */
public abstract class SwipeRefreshBaseActivity extends ToolbarActivity {

    @Bind(R.id.swipe_refresh_layout)
    public MultiSwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefreshLayout();
    }

    private void trySetupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestDataRefresh();
            }
        });
    }

    abstract public void requestDataRefresh();

    public void setRequestDataRefresh(boolean requestDataRefresh) {

        if (!requestDataRefresh) {
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        } else

        {
            mSwipeRefreshLayout.setRefreshing(true);
        }

    }
}
