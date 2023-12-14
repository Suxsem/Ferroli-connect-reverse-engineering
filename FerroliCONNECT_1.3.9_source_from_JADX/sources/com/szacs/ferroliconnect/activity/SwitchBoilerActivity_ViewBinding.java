package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class SwitchBoilerActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private SwitchBoilerActivity target;

    @UiThread
    public SwitchBoilerActivity_ViewBinding(SwitchBoilerActivity switchBoilerActivity) {
        this(switchBoilerActivity, switchBoilerActivity.getWindow().getDecorView());
    }

    @UiThread
    public SwitchBoilerActivity_ViewBinding(SwitchBoilerActivity switchBoilerActivity, View view) {
        super(switchBoilerActivity, view);
        this.target = switchBoilerActivity;
        switchBoilerActivity.ivBoilerSwitch = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_boiler_switch, "field 'ivBoilerSwitch'", ImageView.class);
        switchBoilerActivity.tvBoiler = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvBoiler, "field 'tvBoiler'", TextView.class);
        switchBoilerActivity.llBoiler = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llBoiler, "field 'llBoiler'", LinearLayout.class);
        switchBoilerActivity.ivWifiSignal = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_wifi, "field 'ivWifiSignal'", ImageView.class);
    }

    public void unbind() {
        SwitchBoilerActivity switchBoilerActivity = this.target;
        if (switchBoilerActivity != null) {
            this.target = null;
            switchBoilerActivity.ivBoilerSwitch = null;
            switchBoilerActivity.tvBoiler = null;
            switchBoilerActivity.llBoiler = null;
            switchBoilerActivity.ivWifiSignal = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
