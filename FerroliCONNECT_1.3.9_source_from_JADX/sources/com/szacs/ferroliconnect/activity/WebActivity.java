package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.szacs.ferroliconnect.C1683R;

public class WebActivity extends MyAppCompatActivity {
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(-1, -1);
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private FrameLayout fullscreenContainer;
    @BindView(2131296648)
    ProgressBar progressBar;
    @BindView(2131296956)
    WebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.weblayout);
        ButterKnife.bind((Activity) this);
        String stringExtra = getIntent().getStringExtra("url");
        String stringExtra2 = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(stringExtra)) {
            this.webView.loadUrl(stringExtra);
        }
        if (!TextUtils.isEmpty(stringExtra2)) {
            setTitle(stringExtra2);
        }
        initToolbar();
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setAppCacheEnabled(true);
        this.webView.setWebViewClient(new webViewClient());
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(2);
        this.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (!WebActivity.this.isFinishing() && WebActivity.this.progressBar != null) {
                    if (i == 100) {
                        WebActivity.this.progressBar.setVisibility(8);
                        return;
                    }
                    WebActivity.this.progressBar.setVisibility(0);
                    WebActivity.this.progressBar.setProgress(i);
                }
            }

            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(WebActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                return frameLayout;
            }

            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback) {
                WebActivity.this.showCustomView(view, customViewCallback);
            }

            public void onHideCustomView() {
                WebActivity.this.hideCustomView();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WebActivity.this.finish();
            }
        });
    }

    private class webViewClient extends WebViewClient {
        private webViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }
    }

    public void onBackPressed() {
        if (this.customView != null) {
            hideCustomView();
        } else if (this.webView.canGoBack()) {
            this.webView.goBack();
        } else {
            finish();
            overridePendingTransition(C1683R.anim.in_fromleft, C1683R.anim.out_toright);
        }
    }

    /* access modifiers changed from: private */
    public void showCustomView(View view, WebChromeClient.CustomViewCallback customViewCallback2) {
        if (this.customView != null) {
            customViewCallback2.onCustomViewHidden();
            return;
        }
        getWindow().getDecorView();
        this.fullscreenContainer = new FullscreenHolder(this);
        this.fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        ((FrameLayout) getWindow().getDecorView()).addView(this.fullscreenContainer, COVER_SCREEN_PARAMS);
        this.customView = view;
        setStatusBarVisibility(false);
        this.customViewCallback = customViewCallback2;
        setRequestedOrientation(0);
    }

    /* access modifiers changed from: private */
    public void hideCustomView() {
        if (this.customView != null) {
            setStatusBarVisibility(true);
            ((FrameLayout) getWindow().getDecorView()).removeView(this.fullscreenContainer);
            this.fullscreenContainer = null;
            this.customView = null;
            this.customViewCallback.onCustomViewHidden();
            this.webView.setVisibility(0);
            setRequestedOrientation(1);
        }
    }

    static class FullscreenHolder extends FrameLayout {
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return true;
        }

        public FullscreenHolder(Context context) {
            super(context);
            setBackgroundColor(context.getResources().getColor(17170444));
        }
    }

    private void setStatusBarVisibility(boolean z) {
        getWindow().setFlags(z ? 0 : 1024, 1024);
    }
}
