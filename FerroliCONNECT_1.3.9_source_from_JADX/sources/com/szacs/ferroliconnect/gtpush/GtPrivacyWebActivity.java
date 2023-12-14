package com.szacs.ferroliconnect.gtpush;

import android.os.Bundle;
import android.support.p003v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.activity.MyAppCompatActivity;

public class GtPrivacyWebActivity extends MyAppCompatActivity {
    private WebView webView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_gt_web);
        this.myToolbar = (Toolbar) findViewById(C1683R.C1685id.my_toolbar);
        setSupportActionBar(this.myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GtPrivacyWebActivity.this.finish();
            }
        });
        setTitle(getString(C1683R.string.app_info_privacy_policy));
        this.webView = (WebView) findViewById(C1683R.C1685id.wv_gt);
        this.webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        this.webView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView webView, String str) {
                GtPrivacyWebActivity.this.setTitle(str);
            }
        });
        this.webView.loadUrl("https://legal.igexin.com/privacy_en.html");
    }

    public void onBackPressed() {
        WebView webView2 = this.webView;
        if (webView2 == null || !webView2.canGoBack()) {
            super.onBackPressed();
        } else {
            this.webView.goBack();
        }
    }
}
