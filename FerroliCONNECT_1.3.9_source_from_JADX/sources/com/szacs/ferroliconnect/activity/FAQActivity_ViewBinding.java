package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class FAQActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private FAQActivity target;

    @UiThread
    public FAQActivity_ViewBinding(FAQActivity fAQActivity) {
        this(fAQActivity, fAQActivity.getWindow().getDecorView());
    }

    @UiThread
    public FAQActivity_ViewBinding(FAQActivity fAQActivity, View view) {
        super(fAQActivity, view);
        this.target = fAQActivity;
        fAQActivity.wvFAQ = (WebView) Utils.findRequiredViewAsType(view, C1683R.C1685id.wvFAQ, "field 'wvFAQ'", WebView.class);
        fAQActivity.progressBar = (ProgressBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.progressBar, "field 'progressBar'", ProgressBar.class);
    }

    public void unbind() {
        FAQActivity fAQActivity = this.target;
        if (fAQActivity != null) {
            this.target = null;
            fAQActivity.wvFAQ = null;
            fAQActivity.progressBar = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
