package com.szacs.ferroliconnect.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;

public class GtPrivacyDialog extends Dialog {
    private TextView agreeTv;
    /* access modifiers changed from: private */
    public GtPrivacyDialogListener listener;
    private TextView refuseTv;
    private TextView titleTv;
    private TextView urlTv;

    public interface GtPrivacyDialogListener {
        void onGtPrivacyAgreeBtnClick();

        void onGtPrivacyRefuseBtnClick();

        void onGtPrivacyUrlClick();
    }

    public GtPrivacyDialog(@NonNull Context context) {
        super(context, C1683R.style.dialog_NoTitle);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1683R.C1686layout.pri_dialog);
        this.agreeTv = (TextView) findViewById(C1683R.C1685id.tv_agree);
        this.refuseTv = (TextView) findViewById(C1683R.C1685id.tv_cancel);
        this.urlTv = (TextView) findViewById(C1683R.C1685id.tv_content);
        this.titleTv = (TextView) findViewById(C1683R.C1685id.tv_title);
        this.titleTv.setText(getContext().getString(C1683R.string.gt_privacy_dialog_title));
        String string = getContext().getString(C1683R.string.gt_privacy_dialog_tip_3);
        String str = "《" + getContext().getString(C1683R.string.app_name) + " " + getContext().getString(C1683R.string.gt_privacy_dialog_tip_4) + "》";
        SpannableString spannableString = new SpannableString(string + str);
        spannableString.setSpan(new URLSpan("") {
            public void onClick(View view) {
                if (GtPrivacyDialog.this.listener != null) {
                    GtPrivacyDialog.this.listener.onGtPrivacyUrlClick();
                }
            }
        }, string.length(), string.length() + str.length(), 17);
        this.urlTv.setText(spannableString);
        this.urlTv.setPadding(25, 15, 25, 10);
        this.urlTv.setMovementMethod(LinkMovementMethod.getInstance());
        this.agreeTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (GtPrivacyDialog.this.listener != null) {
                    GtPrivacyDialog.this.listener.onGtPrivacyAgreeBtnClick();
                }
                GtPrivacyDialog.this.dismiss();
            }
        });
        this.refuseTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (GtPrivacyDialog.this.listener != null) {
                    GtPrivacyDialog.this.listener.onGtPrivacyRefuseBtnClick();
                }
                GtPrivacyDialog.this.dismiss();
            }
        });
    }

    public void setGtPrivacyDialogListener(GtPrivacyDialogListener gtPrivacyDialogListener) {
        this.listener = gtPrivacyDialogListener;
    }
}
