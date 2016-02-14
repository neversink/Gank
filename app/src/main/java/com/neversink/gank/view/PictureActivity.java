package com.neversink.gank.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.neversink.gank.R;
import com.neversink.gank.view.base.ToolbarActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by never on 16/2/1.
 */
public class PictureActivity extends ToolbarActivity {

    @Bind(R.id.pic)
    ImageView mImageView;
    @Bind(R.id.ts)
    TextSwitcher mTextSwitcher;

    private String url;
    private String title;
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_DESC = "image_desc";
    public static final String TRANSIT_PIC = "picture";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent i = new Intent(context, PictureActivity.class);
        i.putExtra(EXTRA_IMAGE_URL, url);
        i.putExtra(EXTRA_IMAGE_DESC, desc);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mTextSwitcher.setFactory(() -> {
            TextView tv = new TextView(PictureActivity.this);
            tv.setTextAppearance(this, R.style.textswitcher_textappearence);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.postDelayed(() -> tv.setSelected(true), 2000);
            return tv;
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);


        url = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        title = getIntent().getStringExtra(EXTRA_IMAGE_DESC);
        ViewCompat.setTransitionName(mImageView, TRANSIT_PIC);
        Picasso.with(this).load(url).into(mImageView);
        setAppBarAlpha(0.7f);
        setTitle(title);

    }

    public void setTitle(String title) {
        mTextSwitcher.setText(title);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
