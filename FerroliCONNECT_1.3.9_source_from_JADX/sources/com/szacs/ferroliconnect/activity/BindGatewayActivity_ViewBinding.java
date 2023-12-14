package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;

public class BindGatewayActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private BindGatewayActivity target;

    @UiThread
    public BindGatewayActivity_ViewBinding(BindGatewayActivity bindGatewayActivity) {
        this(bindGatewayActivity, bindGatewayActivity.getWindow().getDecorView());
    }

    @UiThread
    public BindGatewayActivity_ViewBinding(BindGatewayActivity bindGatewayActivity, View view) {
        super(bindGatewayActivity, view);
        this.target = bindGatewayActivity;
        bindGatewayActivity.tvAddGatewayStatus = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvAddGatewayStatus, "field 'tvAddGatewayStatus'", TextView.class);
        bindGatewayActivity.etGatewayId = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.etGatewayId, "field 'etGatewayId'", EditText.class);
        bindGatewayActivity.etGatewayName = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.etGatewayName, "field 'etGatewayName'", EditText.class);
        bindGatewayActivity.mainLinearLayout = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.mainLinearLayout, "field 'mainLinearLayout'", RelativeLayout.class);
        bindGatewayActivity.bindBtn = (QMUIRoundButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.btAddGateway, "field 'bindBtn'", QMUIRoundButton.class);
    }

    public void unbind() {
        BindGatewayActivity bindGatewayActivity = this.target;
        if (bindGatewayActivity != null) {
            this.target = null;
            bindGatewayActivity.tvAddGatewayStatus = null;
            bindGatewayActivity.etGatewayId = null;
            bindGatewayActivity.etGatewayName = null;
            bindGatewayActivity.mainLinearLayout = null;
            bindGatewayActivity.bindBtn = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
