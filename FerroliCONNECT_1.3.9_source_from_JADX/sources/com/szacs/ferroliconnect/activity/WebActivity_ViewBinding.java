package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class WebActivity_ViewBinding implements Unbinder {
    private WebActivity target;

    @UiThread
    public WebActivity_ViewBinding(WebActivity webActivity) {
        this(webActivity, webActivity.getWindow().getDecorView());
    }

    @UiThread
    public WebActivity_ViewBinding(WebActivity webActivity, View view) {
        this.target = webActivity;
        webActivity.webView = (WebView) Utils.findRequiredViewAsType(view, C1683R.C1685id.web, "field 'webView'", WebView.class);
        webActivity.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.progressBar, "field 'progressBar'", ProgressBar.class);
    }

    @CallSuper
    public void unbind() {
        WebActivity webActivity = this.target;
        if (webActivity != null) {
            this.target = null;
            webActivity.webView = null;
            webActivity.progressBar = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
