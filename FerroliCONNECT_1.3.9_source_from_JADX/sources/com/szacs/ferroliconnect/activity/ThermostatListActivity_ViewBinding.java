package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.Utils;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.szacs.ferroliconnect.C1683R;

public class ThermostatListActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private ThermostatListActivity target;

    @UiThread
    public ThermostatListActivity_ViewBinding(ThermostatListActivity thermostatListActivity) {
        this(thermostatListActivity, thermostatListActivity.getWindow().getDecorView());
    }

    @UiThread
    public ThermostatListActivity_ViewBinding(ThermostatListActivity thermostatListActivity, View view) {
        super(thermostatListActivity, view);
        this.target = thermostatListActivity;
        thermostatListActivity.listViewThermostat = (SwipeMenuListView) Utils.findRequiredViewAsType(view, C1683R.C1685id.listViewThermostat, "field 'listViewThermostat'", SwipeMenuListView.class);
        thermostatListActivity.refreshLayout = (PullRefreshLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.swipeRefreshLayout, "field 'refreshLayout'", PullRefreshLayout.class);
    }

    public void unbind() {
        ThermostatListActivity thermostatListActivity = this.target;
        if (thermostatListActivity != null) {
            this.target = null;
            thermostatListActivity.listViewThermostat = null;
            thermostatListActivity.refreshLayout = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
