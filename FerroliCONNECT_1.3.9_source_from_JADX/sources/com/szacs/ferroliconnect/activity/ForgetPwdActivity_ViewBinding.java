package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class ForgetPwdActivity_ViewBinding implements Unbinder {
    private ForgetPwdActivity target;

    @UiThread
    public ForgetPwdActivity_ViewBinding(ForgetPwdActivity forgetPwdActivity) {
        this(forgetPwdActivity, forgetPwdActivity.getWindow().getDecorView());
    }

    @UiThread
    public ForgetPwdActivity_ViewBinding(ForgetPwdActivity forgetPwdActivity, View view) {
        this.target = forgetPwdActivity;
        forgetPwdActivity.editTextPassword = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextPassword, "field 'editTextPassword'", EditText.class);
        forgetPwdActivity.editTextEmail = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextEmail, "field 'editTextEmail'", EditText.class);
        forgetPwdActivity.editTextCode = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextCode, "field 'editTextCode'", EditText.class);
        forgetPwdActivity.CodeTV = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.btnCode, "field 'CodeTV'", TextView.class);
        forgetPwdActivity.etConfirmPassword = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.et_confirm_password, "field 'etConfirmPassword'", EditText.class);
    }

    @CallSuper
    public void unbind() {
        ForgetPwdActivity forgetPwdActivity = this.target;
        if (forgetPwdActivity != null) {
            this.target = null;
            forgetPwdActivity.editTextPassword = null;
            forgetPwdActivity.editTextEmail = null;
            forgetPwdActivity.editTextCode = null;
            forgetPwdActivity.CodeTV = null;
            forgetPwdActivity.etConfirmPassword = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
