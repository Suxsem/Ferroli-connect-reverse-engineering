package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;

public class PhoneActivity_ViewBinding implements Unbinder {
    private PhoneActivity target;

    @UiThread
    public PhoneActivity_ViewBinding(PhoneActivity phoneActivity) {
        this(phoneActivity, phoneActivity.getWindow().getDecorView());
    }

    @UiThread
    public PhoneActivity_ViewBinding(PhoneActivity phoneActivity, View view) {
        this.target = phoneActivity;
        phoneActivity.etPhone = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextEmail, "field 'etPhone'", EditText.class);
        phoneActivity.etCode = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextCode, "field 'etCode'", EditText.class);
        phoneActivity.codeLayout = (QMUILinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.code_layout, "field 'codeLayout'", QMUILinearLayout.class);
        phoneActivity.button = (QMUIRoundButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.btn_forget, "field 'button'", QMUIRoundButton.class);
    }

    @CallSuper
    public void unbind() {
        PhoneActivity phoneActivity = this.target;
        if (phoneActivity != null) {
            this.target = null;
            phoneActivity.etPhone = null;
            phoneActivity.etCode = null;
            phoneActivity.codeLayout = null;
            phoneActivity.button = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
