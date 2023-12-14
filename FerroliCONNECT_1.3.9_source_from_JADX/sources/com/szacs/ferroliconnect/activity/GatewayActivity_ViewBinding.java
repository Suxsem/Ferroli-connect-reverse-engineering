package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class GatewayActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private GatewayActivity target;

    @UiThread
    public GatewayActivity_ViewBinding(GatewayActivity gatewayActivity) {
        this(gatewayActivity, gatewayActivity.getWindow().getDecorView());
    }

    @UiThread
    public GatewayActivity_ViewBinding(GatewayActivity gatewayActivity, View view) {
        super(gatewayActivity, view);
        this.target = gatewayActivity;
        gatewayActivity.llRoom = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llRoom, "field 'llRoom'", LinearLayout.class);
        gatewayActivity.llBoiler = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llBoiler, "field 'llBoiler'", LinearLayout.class);
        gatewayActivity.tvRoom = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvRoom, "field 'tvRoom'", TextView.class);
        gatewayActivity.tvBoiler = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvBoiler, "field 'tvBoiler'", TextView.class);
    }

    public void unbind() {
        GatewayActivity gatewayActivity = this.target;
        if (gatewayActivity != null) {
            this.target = null;
            gatewayActivity.llRoom = null;
            gatewayActivity.llBoiler = null;
            gatewayActivity.tvRoom = null;
            gatewayActivity.tvBoiler = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
