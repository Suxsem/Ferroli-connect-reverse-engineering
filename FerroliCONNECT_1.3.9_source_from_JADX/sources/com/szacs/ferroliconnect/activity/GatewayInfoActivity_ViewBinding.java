package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;
import p109de.hdodenhof.circleimageview.CircleImageView;

public class GatewayInfoActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private GatewayInfoActivity target;

    @UiThread
    public GatewayInfoActivity_ViewBinding(GatewayInfoActivity gatewayInfoActivity) {
        this(gatewayInfoActivity, gatewayInfoActivity.getWindow().getDecorView());
    }

    @UiThread
    public GatewayInfoActivity_ViewBinding(GatewayInfoActivity gatewayInfoActivity, View view) {
        super(gatewayInfoActivity, view);
        this.target = gatewayInfoActivity;
        gatewayInfoActivity.mainLayout = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.mainLayout, "field 'mainLayout'", RelativeLayout.class);
        gatewayInfoActivity.llWebWeatherLocation = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llWebWeatherLocation, "field 'llWebWeatherLocation'", LinearLayout.class);
        gatewayInfoActivity.llTimeZone = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llTimeZone, "field 'llTimeZone'", LinearLayout.class);
        gatewayInfoActivity.tvGatewayName = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvGatewayName, "field 'tvGatewayName'", TextView.class);
        gatewayInfoActivity.tvGatewayId = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvGatewayId, "field 'tvGatewayId'", TextView.class);
        gatewayInfoActivity.tvWebWeatherLocation = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvWebWeatherLocation, "field 'tvWebWeatherLocation'", TextView.class);
        gatewayInfoActivity.tvTimeZone = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvTimeZone, "field 'tvTimeZone'", TextView.class);
        gatewayInfoActivity.tvMAC = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvMAC, "field 'tvMAC'", TextView.class);
        gatewayInfoActivity.tvHost = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvHost, "field 'tvHost'", TextView.class);
        gatewayInfoActivity.tvBindGateway = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvBindGateway, "field 'tvBindGateway'", TextView.class);
        gatewayInfoActivity.tvUnbindGateway = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvUnbindGateway, "field 'tvUnbindGateway'", TextView.class);
        gatewayInfoActivity.ivEditGatewayName = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivEditGatewayName, "field 'ivEditGatewayName'", ImageView.class);
        gatewayInfoActivity.civHouse = (CircleImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.civHouse, "field 'civHouse'", CircleImageView.class);
        gatewayInfoActivity.rgOutdoorTempSource = (RadioGroup) Utils.findRequiredViewAsType(view, C1683R.C1685id.rgOutdoorTempSource, "field 'rgOutdoorTempSource'", RadioGroup.class);
        gatewayInfoActivity.rbInternet = (RadioButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.rbInternet, "field 'rbInternet'", RadioButton.class);
        gatewayInfoActivity.rbBoilerSensor = (RadioButton) Utils.findRequiredViewAsType(view, C1683R.C1685id.rbBoilerSensor, "field 'rbBoilerSensor'", RadioButton.class);
    }

    public void unbind() {
        GatewayInfoActivity gatewayInfoActivity = this.target;
        if (gatewayInfoActivity != null) {
            this.target = null;
            gatewayInfoActivity.mainLayout = null;
            gatewayInfoActivity.llWebWeatherLocation = null;
            gatewayInfoActivity.llTimeZone = null;
            gatewayInfoActivity.tvGatewayName = null;
            gatewayInfoActivity.tvGatewayId = null;
            gatewayInfoActivity.tvWebWeatherLocation = null;
            gatewayInfoActivity.tvTimeZone = null;
            gatewayInfoActivity.tvMAC = null;
            gatewayInfoActivity.tvHost = null;
            gatewayInfoActivity.tvBindGateway = null;
            gatewayInfoActivity.tvUnbindGateway = null;
            gatewayInfoActivity.ivEditGatewayName = null;
            gatewayInfoActivity.civHouse = null;
            gatewayInfoActivity.rgOutdoorTempSource = null;
            gatewayInfoActivity.rbInternet = null;
            gatewayInfoActivity.rbBoilerSensor = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
