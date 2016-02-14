package com.neversink.gank.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neversink.gank.R;
import com.neversink.gank.func.OnMMTouchListener;
import com.neversink.gank.model.db.Gank;
import com.neversink.gank.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by never on 16/1/25.
 */
public class MMListAdapter extends RecyclerView.Adapter<MMListAdapter.ViewHolder> {

    private OnMMTouchListener mOnMMTouchListener;
    private Context mContext;
    private List<Gank> mMMList;

    public MMListAdapter(Context context, List<Gank> mmList) {
        mContext = context;
        mMMList = mmList;
    }

    @Override
    public MMListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_mm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MMListAdapter.ViewHolder holder, int position) {
        Gank mm = mMMList.get(position);
        holder.mm = mm;
        holder.titleView.setText(mm.desc);
        Picasso.with(mContext).load(mm.url).into(holder.mmView);
    }

    @Override
    public int getItemCount() {
        return mMMList.size();
    }

    public void setOnMMTouchListener(OnMMTouchListener listener) {
        mOnMMTouchListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_mm)
        RatioImageView mmView;
        @Bind(R.id.tv_title)
        TextView titleView;
        View card;
        Gank mm;

        public ViewHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, card);
            mmView.setOnClickListener(this);
            titleView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnMMTouchListener != null) {
                mOnMMTouchListener.onTouch(v, mmView, titleView, mm);
            }
        }
    }
}
