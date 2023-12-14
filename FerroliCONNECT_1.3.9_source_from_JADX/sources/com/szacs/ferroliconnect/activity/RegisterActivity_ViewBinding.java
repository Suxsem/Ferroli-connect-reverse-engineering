package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class RegisterActivity_ViewBinding implements Unbinder {
    private RegisterActivity target;

    @UiThread
    public RegisterActivity_ViewBinding(RegisterActivity registerActivity) {
        this(registerActivity, registerActivity.getWindow().getDecorView());
    }

    @UiThread
    public RegisterActivity_ViewBinding(RegisterActivity registerActivity, View view) {
        this.target = registerActivity;
        registerActivity.editTextUsername = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextUsername, "field 'editTextUsername'", EditText.class);
        registerActivity.editTextPassword = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextPassword, "field 'editTextPassword'", EditText.class);
        registerActivity.editTextPasswordConfirm = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextPasswordConfirm, "field 'editTextPasswordConfirm'", EditText.class);
        registerActivity.editTextEmail = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextEmail, "field 'editTextEmail'", EditText.class);
        registerActivity.editTextCode = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextCode, "field 'editTextCode'", EditText.class);
        registerActivity.editTextPhone = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextPhone, "field 'editTextPhone'", EditText.class);
        registerActivity.editTextCity = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextCity, "field 'editTextCity'", EditText.class);
        registerActivity.llMain = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llMain, "field 'llMain'", LinearLayout.class);
        registerActivity.titleTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.title, "field 'titleTv'", TextView.class);
        registerActivity.CodeTV = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.btnCode, "field 'CodeTV'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        RegisterActivity registerActivity = this.target;
        if (registerActivity != null) {
            this.target = null;
            registerActivity.editTextUsername = null;
            registerActivity.editTextPassword = null;
            registerActivity.editTextPasswordConfirm = null;
            registerActivity.editTextEmail = null;
            registerActivity.editTextCode = null;
            registerActivity.editTextPhone = null;
            registerActivity.editTextCity = null;
            registerActivity.llMain = null;
            registerActivity.titleTv = null;
            registerActivity.CodeTV = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
