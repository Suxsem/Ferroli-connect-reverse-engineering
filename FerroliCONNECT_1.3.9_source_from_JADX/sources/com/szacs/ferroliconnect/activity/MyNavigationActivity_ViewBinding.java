package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.p000v4.widget.DrawerLayout;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class MyNavigationActivity_ViewBinding implements Unbinder {
    private MyNavigationActivity target;

    @UiThread
    public MyNavigationActivity_ViewBinding(MyNavigationActivity myNavigationActivity) {
        this(myNavigationActivity, myNavigationActivity.getWindow().getDecorView());
    }

    @UiThread
    public MyNavigationActivity_ViewBinding(MyNavigationActivity myNavigationActivity, View view) {
        this.target = myNavigationActivity;
        myNavigationActivity.drawer = (DrawerLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.drawer_layout, "field 'drawer'", DrawerLayout.class);
        myNavigationActivity.navigationView = (NavigationView) Utils.findRequiredViewAsType(view, C1683R.C1685id.nav_view, "field 'navigationView'", NavigationView.class);
    }

    @CallSuper
    public void unbind() {
        MyNavigationActivity myNavigationActivity = this.target;
        if (myNavigationActivity != null) {
            this.target = null;
            myNavigationActivity.drawer = null;
            myNavigationActivity.navigationView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
