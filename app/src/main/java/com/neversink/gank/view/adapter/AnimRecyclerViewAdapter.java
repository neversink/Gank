package com.neversink.gank.view.adapter;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by never on 16/2/1.
 */
public abstract class AnimRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private int mLastPosition = -1;

    public void showItemAnim(View v, int position) {
        Context c = v.getContext();
        if (position > mLastPosition) {
            v.setAlpha(0);
            v.postDelayed(() -> {
                Animation slide_anim = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
                v.startAnimation(slide_anim);
            }, 30 * mLastPosition);
            mLastPosition = position;
        }
    }

}
