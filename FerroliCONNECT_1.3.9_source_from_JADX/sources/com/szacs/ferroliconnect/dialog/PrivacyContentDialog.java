package com.szacs.ferroliconnect.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;

public class PrivacyContentDialog extends DialogFragment implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    private static final String TAG = "PrivacyContentDialog";
    /* access modifiers changed from: private */
    public AlertDialog dialog;
    private PDFView pdf;
    private QMUIRoundButton readBtn;
    private View view;

    public void loadComplete(int i) {
    }

    public void onLayerDrawn(Canvas canvas, float f, float f2, int i) {
    }

    public void onPageChanged(int i, int i2) {
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = LayoutInflater.from(getActivity()).inflate(C1683R.C1686layout.dialog_privacy_content, (ViewGroup) null, false);
        this.dialog = new AlertDialog.Builder(getActivity()).setView(this.view).create();
        this.pdf = (PDFView) this.view.findViewById(C1683R.C1685id.pdf_view);
        this.readBtn = (QMUIRoundButton) this.view.findViewById(C1683R.C1685id.btn_read);
        this.readBtn.setChangeAlphaWhenPress(true);
        this.readBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PrivacyContentDialog.this.dialog.cancel();
            }
        });
        setPdf();
        return this.dialog;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void setPdf() {
        /*
            r4 = this;
            android.app.Activity r0 = r4.getActivity()
            java.util.Locale r0 = com.szacs.ferroliconnect.util.LanguageUtil.getSetLanguageLocale(r0)
            java.lang.String r0 = r0.getLanguage()
            int r1 = r0.hashCode()
            java.lang.String r2 = "en"
            switch(r1) {
                case 3241: goto L_0x007f;
                case 3246: goto L_0x0075;
                case 3276: goto L_0x006b;
                case 3371: goto L_0x0061;
                case 3518: goto L_0x0056;
                case 3580: goto L_0x004b;
                case 3588: goto L_0x0040;
                case 3645: goto L_0x0036;
                case 3651: goto L_0x002c;
                case 3710: goto L_0x0022;
                case 3734: goto L_0x0017;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x0087
        L_0x0017:
            java.lang.String r1 = "uk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 7
            goto L_0x0088
        L_0x0022:
            java.lang.String r1 = "tr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 6
            goto L_0x0088
        L_0x002c:
            java.lang.String r1 = "ru"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 5
            goto L_0x0088
        L_0x0036:
            java.lang.String r1 = "ro"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 4
            goto L_0x0088
        L_0x0040:
            java.lang.String r1 = "pt"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 10
            goto L_0x0088
        L_0x004b:
            java.lang.String r1 = "pl"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 9
            goto L_0x0088
        L_0x0056:
            java.lang.String r1 = "nl"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 8
            goto L_0x0088
        L_0x0061:
            java.lang.String r1 = "it"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 3
            goto L_0x0088
        L_0x006b:
            java.lang.String r1 = "fr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 2
            goto L_0x0088
        L_0x0075:
            java.lang.String r1 = "es"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0087
            r0 = 1
            goto L_0x0088
        L_0x007f:
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0087
            r0 = 0
            goto L_0x0088
        L_0x0087:
            r0 = -1
        L_0x0088:
            java.lang.String r1 = "PrivacyContentDialog"
            switch(r0) {
                case 0: goto L_0x008e;
                case 1: goto L_0x008e;
                case 2: goto L_0x008e;
                case 3: goto L_0x008e;
                case 4: goto L_0x008e;
                case 5: goto L_0x008e;
                case 6: goto L_0x008e;
                case 7: goto L_0x008e;
                case 8: goto L_0x008e;
                case 9: goto L_0x008e;
                case 10: goto L_0x008e;
                default: goto L_0x008d;
            }
        L_0x008d:
            goto L_0x00ae
        L_0x008e:
            android.app.Activity r0 = r4.getActivity()
            java.util.Locale r0 = com.szacs.ferroliconnect.util.LanguageUtil.getSetLanguageLocale(r0)
            java.lang.String r2 = r0.getLanguage()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = " select pdf language = "
            r0.append(r3)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
        L_0x00ae:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r3 = "privacy_policy/app_privacy_policy_"
            r0.append(r3)
            r0.append(r2)
            java.lang.String r2 = ".pdf"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = " PDF name is "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r2 = r2.toString()
            android.util.Log.d(r1, r2)
            r4.displayFromAssets(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.dialog.PrivacyContentDialog.setPdf():void");
    }

    private void displayFromAssets(String str) {
        this.pdf.fromAsset(str).defaultPage(0).onPageChange(this).onLoad(this).onDraw(this).enableSwipe(true).load();
    }
}
