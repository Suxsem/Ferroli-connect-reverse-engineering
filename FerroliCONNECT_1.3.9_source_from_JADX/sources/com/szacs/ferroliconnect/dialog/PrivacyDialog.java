package com.szacs.ferroliconnect.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;

public class PrivacyDialog extends Dialog implements View.OnClickListener {
    private TextView agreeTv;
    private TextView contentTv;
    private AlertDialog dialog;
    private PrivacyDialogListener listener;
    private TextView notAgreeTv;

    public interface PrivacyDialogListener {
        void onAgreePrivacyClick();

        void onPrivacyContentClick();
    }

    public PrivacyDialog(@NonNull Context context) {
        super(context, C1683R.style.dialog_NoTitle);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
        setContentView(C1683R.C1686layout.dialog_privacy);
        this.agreeTv = (TextView) findViewById(C1683R.C1685id.btn_agree);
        this.notAgreeTv = (TextView) findViewById(C1683R.C1685id.btn_not_agree);
        this.contentTv = (TextView) findViewById(C1683R.C1685id.tv_privacy_content);
        this.notAgreeTv.setEnabled(false);
        this.agreeTv.setOnClickListener(this);
        this.notAgreeTv.setOnClickListener(this);
        this.contentTv.setOnClickListener(this);
    }

    public void onClick(View view) {
        PrivacyDialogListener privacyDialogListener;
        int id = view.getId();
        if (id == C1683R.C1685id.btn_agree) {
            PrivacyDialogListener privacyDialogListener2 = this.listener;
            if (privacyDialogListener2 != null) {
                privacyDialogListener2.onAgreePrivacyClick();
            }
            dismiss();
        } else if (id == C1683R.C1685id.tv_privacy_content && (privacyDialogListener = this.listener) != null) {
            privacyDialogListener.onPrivacyContentClick();
        }
    }

    public void setPrivacyDialogListener(PrivacyDialogListener privacyDialogListener) {
        this.listener = privacyDialogListener;
    }
}
