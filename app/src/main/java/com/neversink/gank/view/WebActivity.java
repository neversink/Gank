package com.neversink.gank.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.neversink.gank.R;
import com.neversink.gank.view.base.ToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by never on 16/2/2.
 */
public class WebActivity extends ToolbarActivity {

    public final static String EXTRA_URL = "extra_url";
    public final static String EXTRA_TITLE = "extra_title";

    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.ts)
    TextSwitcher mTextSwitcher;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    public static Intent newIntent(Context context, String url, String title) {
        Intent i = new Intent(context, WebActivity.class);
        i.putExtra(EXTRA_URL, url);
        i.putExtra(EXTRA_TITLE, title);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent i = getIntent();
        String url = i.getStringExtra(EXTRA_URL);
        String title = i.getStringExtra(EXTRA_TITLE);


        mTextSwitcher.setFactory(() -> {
            TextView tv = new TextView(WebActivity.this);
            tv.setTextAppearance(this, R.style.textswitcher_textappearence);
            tv.setSingleLine(true);
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.postDelayed(() -> tv.setSelected(true), 2000);
            return tv;
        });

        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setSupportZoom(true);
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new GankClient());

        mWebView.loadUrl(url);
        setTitle(title);

    }

    public void setTitle(String title) {
        mTextSwitcher.setText(title);
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            setTitle(title);
        }
    }

    private class GankClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
}
