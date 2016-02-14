package com.neversink.gank.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neversink.gank.R;
import com.neversink.gank.model.db.Gank;
import com.neversink.gank.util.StringStyleUtil;
import com.neversink.gank.view.WebActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by never on 16/2/1.
 */
public class GankListAdapter extends AnimRecyclerViewAdapter<GankListAdapter.ViewHolder> {

    private List<Gank> mGankList;
    private Context mContext;
    public GankListAdapter(List<Gank> gankList) {
        mGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gank, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Gank gank = mGankList.get(position);
        if (position == 0) {
            showCategory(holder);
        } else {
            boolean isTheFirstItemInNewCategory = !gank.type.equals(mGankList.get(position - 1).type);
            if (isTheFirstItemInNewCategory) {
                showCategory(holder);
            } else hideCategory(holder);
        }

        holder.category.setText(gank.type);
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.desc)
                .append(StringStyleUtil.formatAppearance(mContext,  " (via. " + gank.who + ")", R.style.ViaTextAppearance));
        holder.title.setText(builder.subSequence(0, builder.length()));

//        showItemAnim(holder.title, position);
    }

    private void showCategory(ViewHolder holder) {
        if (!isVisibleOf(holder.category)) holder.category.setVisibility(View.VISIBLE);
    }

    private void hideCategory(ViewHolder holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(TextView category) {
        return category.getVisibility() == View.VISIBLE;
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_category)
        TextView category;
        @Bind(R.id.tv_title)
        TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ll_item)
        void onGank(View v) {
            Gank gank = mGankList.get(getLayoutPosition());
            Intent i = WebActivity.newIntent(mContext, gank.url, gank.desc);
            mContext.startActivity(i);
        }
    }
}
