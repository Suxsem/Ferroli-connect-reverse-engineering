package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;

public class BoilerInfoActivity_ViewBinding implements Unbinder {
    private BoilerInfoActivity target;

    @UiThread
    public BoilerInfoActivity_ViewBinding(BoilerInfoActivity boilerInfoActivity) {
        this(boilerInfoActivity, boilerInfoActivity.getWindow().getDecorView());
    }

    @UiThread
    public BoilerInfoActivity_ViewBinding(BoilerInfoActivity boilerInfoActivity, View view) {
        this.target = boilerInfoActivity;
        boilerInfoActivity.edBrand = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.EdtextBrand, "field 'edBrand'", EditText.class);
        boilerInfoActivity.edModel = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.EdtextModel, "field 'edModel'", EditText.class);
        boilerInfoActivity.edSerial = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.EdtextSerial, "field 'edSerial'", EditText.class);
        boilerInfoActivity.edInstallTime = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.EdtextInstallTime, "field 'edInstallTime'", TextView.class);
        boilerInfoActivity.button = (QMUIRoundButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.btn_forget, "field 'button'", QMUIRoundButton.class);
        boilerInfoActivity.install_layout = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.layout_install_time, "field 'install_layout'", LinearLayout.class);
        boilerInfoActivity.edCity = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.EdtextCity, "field 'edCity'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        BoilerInfoActivity boilerInfoActivity = this.target;
        if (boilerInfoActivity != null) {
            this.target = null;
            boilerInfoActivity.edBrand = null;
            boilerInfoActivity.edModel = null;
            boilerInfoActivity.edSerial = null;
            boilerInfoActivity.edInstallTime = null;
            boilerInfoActivity.button = null;
            boilerInfoActivity.install_layout = null;
            boilerInfoActivity.edCity = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
