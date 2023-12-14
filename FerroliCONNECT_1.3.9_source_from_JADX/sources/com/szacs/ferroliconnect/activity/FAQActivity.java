package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.NavigationBarUtil;

public class FAQActivity extends MyNavigationActivity {
    @BindView(2131296648)
    ProgressBar progressBar;
    @BindView(2131296966)
    WebView wvFAQ;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_faq;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        ButterKnife.bind((Activity) this);
        this.wvFAQ.getSettings().setJavaScriptEnabled(true);
        this.wvFAQ.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (!FAQActivity.this.isFinishing() && FAQActivity.this.progressBar != null) {
                    if (i == 100) {
                        FAQActivity.this.progressBar.setVisibility(8);
                        return;
                    }
                    FAQActivity.this.progressBar.setVisibility(0);
                    FAQActivity.this.progressBar.setProgress(i);
                }
            }
        });
        this.wvFAQ.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        setTitle(getString(C1683R.string.menu_help));
        this.drawer.setDrawerLockMode(1);
    }
}
