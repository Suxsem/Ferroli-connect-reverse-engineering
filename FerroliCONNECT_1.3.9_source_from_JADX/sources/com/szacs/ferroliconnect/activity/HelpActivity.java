package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.szacs.ferroliconnect.C1683R;

public class HelpActivity extends MyNavigationActivity implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {
    private static final String TAG = "HelpActivity";
    public static final int TYPE_HELP = 0;
    public static final int TYPE_PRIVACY = 1;
    @BindView(2131296590)
    LinearLayout llRoot;
    @BindView(2131296641)
    PDFView pdfView;
    private int type = 0;

    public void loadComplete(int i) {
    }

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_help;
    }

    public void onLayerDrawn(Canvas canvas, float f, float f2, int i) {
    }

    public void onPageChanged(int i, int i2) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        this.type = getIntent().getIntExtra("PDF_TYPE", 0);
        ButterKnife.bind((Activity) this);
        if (this.type == 0) {
            str = getString(C1683R.string.menu_help);
        } else {
            str = getString(C1683R.string.app_info_privacy_policy);
        }
        setTitle(str);
        selectDisplayPdf();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void selectDisplayPdf() {
        /*
            r5 = this;
            java.util.Locale r0 = com.szacs.ferroliconnect.util.LanguageUtil.getSetLanguageLocale(r5)
            java.lang.String r0 = r0.getLanguage()
            int r1 = r0.hashCode()
            java.lang.String r2 = "en"
            switch(r1) {
                case 3241: goto L_0x007b;
                case 3246: goto L_0x0071;
                case 3276: goto L_0x0067;
                case 3371: goto L_0x005d;
                case 3518: goto L_0x0052;
                case 3580: goto L_0x0047;
                case 3588: goto L_0x003c;
                case 3645: goto L_0x0032;
                case 3651: goto L_0x0028;
                case 3710: goto L_0x001e;
                case 3734: goto L_0x0013;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x0083
        L_0x0013:
            java.lang.String r1 = "uk"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 7
            goto L_0x0084
        L_0x001e:
            java.lang.String r1 = "tr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 6
            goto L_0x0084
        L_0x0028:
            java.lang.String r1 = "ru"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 5
            goto L_0x0084
        L_0x0032:
            java.lang.String r1 = "ro"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 4
            goto L_0x0084
        L_0x003c:
            java.lang.String r1 = "pt"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 9
            goto L_0x0084
        L_0x0047:
            java.lang.String r1 = "pl"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 8
            goto L_0x0084
        L_0x0052:
            java.lang.String r1 = "nl"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 10
            goto L_0x0084
        L_0x005d:
            java.lang.String r1 = "it"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 3
            goto L_0x0084
        L_0x0067:
            java.lang.String r1 = "fr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 2
            goto L_0x0084
        L_0x0071:
            java.lang.String r1 = "es"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0083
            r0 = 1
            goto L_0x0084
        L_0x007b:
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0083
            r0 = 0
            goto L_0x0084
        L_0x0083:
            r0 = -1
        L_0x0084:
            switch(r0) {
                case 0: goto L_0x0088;
                case 1: goto L_0x0088;
                case 2: goto L_0x0088;
                case 3: goto L_0x0088;
                case 4: goto L_0x0088;
                case 5: goto L_0x0088;
                case 6: goto L_0x0088;
                case 7: goto L_0x0088;
                case 8: goto L_0x0088;
                case 9: goto L_0x0088;
                case 10: goto L_0x0088;
                default: goto L_0x0087;
            }
        L_0x0087:
            goto L_0x00a6
        L_0x0088:
            java.util.Locale r0 = com.szacs.ferroliconnect.util.LanguageUtil.getSetLanguageLocale(r5)
            java.lang.String r2 = r0.getLanguage()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = " select pdf language = "
            r0.append(r1)
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "HelpActivity"
            android.util.Log.d(r1, r0)
        L_0x00a6:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "help/HELP_CONNECT_"
            r0.append(r1)
            r0.append(r2)
            java.lang.String r1 = ".pdf"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "privacy_policy/app_privacy_policy_"
            r3.append(r4)
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            int r2 = r5.type
            if (r2 != 0) goto L_0x00d5
            goto L_0x00d6
        L_0x00d5:
            r0 = r1
        L_0x00d6:
            r5.displayFromAssets(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.activity.HelpActivity.selectDisplayPdf():void");
    }

    private void displayFromAssets(String str) {
        this.pdfView.fromAsset(str).defaultPage(0).onPageChange(this).onLoad(this).onDraw(this).enableSwipe(true).load();
    }
}
