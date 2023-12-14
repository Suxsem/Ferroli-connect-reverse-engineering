package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.Group;
import android.support.constraint.Guideline;
import android.support.p003v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class LoginActivity_ViewBinding implements Unbinder {
    private LoginActivity target;

    @UiThread
    public LoginActivity_ViewBinding(LoginActivity loginActivity) {
        this(loginActivity, loginActivity.getWindow().getDecorView());
    }

    @UiThread
    public LoginActivity_ViewBinding(LoginActivity loginActivity, View view) {
        this.target = loginActivity;
        loginActivity.myToolbar = (Toolbar) Utils.findRequiredViewAsType(view, C1683R.C1685id.my_toolbar, "field 'myToolbar'", Toolbar.class);
        loginActivity.glOne = (Guideline) Utils.findRequiredViewAsType(view, C1683R.C1685id.gl_one, "field 'glOne'", Guideline.class);
        loginActivity.glTwo = (Guideline) Utils.findRequiredViewAsType(view, C1683R.C1685id.gl_two, "field 'glTwo'", Guideline.class);
        loginActivity.glThree = (Guideline) Utils.findRequiredViewAsType(view, C1683R.C1685id.gl_three, "field 'glThree'", Guideline.class);
        loginActivity.ivUserName = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_user_name, "field 'ivUserName'", ImageView.class);
        loginActivity.editTextUsername = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextUsername, "field 'editTextUsername'", EditText.class);
        loginActivity.view1 = Utils.findRequiredView(view, C1683R.C1685id.view1, "field 'view1'");
        loginActivity.ivPassword = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_password, "field 'ivPassword'", ImageView.class);
        loginActivity.editTextPassword = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.editTextPassword, "field 'editTextPassword'", EditText.class);
        loginActivity.view2 = Utils.findRequiredView(view, C1683R.C1685id.view2, "field 'view2'");
        loginActivity.checkBoxSignedIn = (CheckBox) Utils.findRequiredViewAsType(view, C1683R.C1685id.checkBoxSignedIn, "field 'checkBoxSignedIn'", CheckBox.class);
        loginActivity.textViewForget = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.textViewForget, "field 'textViewForget'", TextView.class);
        loginActivity.buttonLogin = (Button) Utils.findRequiredViewAsType(view, C1683R.C1685id.buttonLogin, "field 'buttonLogin'", Button.class);
        loginActivity.buttonRegister = (Button) Utils.findRequiredViewAsType(view, C1683R.C1685id.buttonRegister, "field 'buttonRegister'", Button.class);
        loginActivity.gpWelcome = (Group) Utils.findRequiredViewAsType(view, C1683R.C1685id.gp_welcome, "field 'gpWelcome'", Group.class);
        loginActivity.tvVersionNumber = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvVersionNumber, "field 'tvVersionNumber'", TextView.class);
        loginActivity.vBg = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.v_bg, "field 'vBg'", ImageView.class);
    }

    @CallSuper
    public void unbind() {
        LoginActivity loginActivity = this.target;
        if (loginActivity != null) {
            this.target = null;
            loginActivity.myToolbar = null;
            loginActivity.glOne = null;
            loginActivity.glTwo = null;
            loginActivity.glThree = null;
            loginActivity.ivUserName = null;
            loginActivity.editTextUsername = null;
            loginActivity.view1 = null;
            loginActivity.ivPassword = null;
            loginActivity.editTextPassword = null;
            loginActivity.view2 = null;
            loginActivity.checkBoxSignedIn = null;
            loginActivity.textViewForget = null;
            loginActivity.buttonLogin = null;
            loginActivity.buttonRegister = null;
            loginActivity.gpWelcome = null;
            loginActivity.tvVersionNumber = null;
            loginActivity.vBg = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
