package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.github.barteksc.pdfviewer.PDFView;
import com.szacs.ferroliconnect.C1683R;

public class HelpActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private HelpActivity target;

    @UiThread
    public HelpActivity_ViewBinding(HelpActivity helpActivity) {
        this(helpActivity, helpActivity.getWindow().getDecorView());
    }

    @UiThread
    public HelpActivity_ViewBinding(HelpActivity helpActivity, View view) {
        super(helpActivity, view);
        this.target = helpActivity;
        helpActivity.pdfView = (PDFView) Utils.findRequiredViewAsType(view, C1683R.C1685id.pdf_view, "field 'pdfView'", PDFView.class);
        helpActivity.llRoot = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.ll_root, "field 'llRoot'", LinearLayout.class);
    }

    public void unbind() {
        HelpActivity helpActivity = this.target;
        if (helpActivity != null) {
            this.target = null;
            helpActivity.pdfView = null;
            helpActivity.llRoot = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
