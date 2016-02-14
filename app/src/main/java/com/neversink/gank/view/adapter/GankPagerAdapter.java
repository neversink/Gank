package com.neversink.gank.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.neversink.gank.view.fragment.GankFragment;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by never on 16/2/1.
 */
public class GankPagerAdapter extends FragmentPagerAdapter {

    Date mDate;

    public GankPagerAdapter(FragmentManager fm, Date date) {
        super(fm);
        mDate = date;
    }

    @Override
    public Fragment getItem(int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, -position);
        return GankFragment.newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public int getCount() {
//        return 5;
        return Integer.MAX_VALUE;
    }
}
