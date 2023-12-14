package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;

public class EmailActivity_ViewBinding implements Unbinder {
    private EmailActivity target;

    @UiThread
    public EmailActivity_ViewBinding(EmailActivity emailActivity) {
        this(emailActivity, emailActivity.getWindow().getDecorView());
    }

    @UiThread
    public EmailActivity_ViewBinding(EmailActivity emailActivity, View view) {
        this.target = emailActivity;
        emailActivity.etEmail = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextEmail, "field 'etEmail'", EditText.class);
        emailActivity.etCode = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextCode, "field 'etCode'", EditText.class);
        emailActivity.codeLayout = (QMUILinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.code_layout, "field 'codeLayout'", QMUILinearLayout.class);
        emailActivity.button = (QMUIRoundButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.btn_forget, "field 'button'", QMUIRoundButton.class);
        emailActivity.btnCode = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.btnCode, "field 'btnCode'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        EmailActivity emailActivity = this.target;
        if (emailActivity != null) {
            this.target = null;
            emailActivity.etEmail = null;
            emailActivity.etCode = null;
            emailActivity.codeLayout = null;
            emailActivity.button = null;
            emailActivity.btnCode = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
