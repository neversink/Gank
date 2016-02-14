package com.neversink.gank.func;

import android.view.View;

import com.neversink.gank.model.db.Gank;

/**
 * Created by never on 16/1/25.
 */
public interface OnMMTouchListener {
    void onTouch(View v, View mmView, View cardView, Gank mm);
}
