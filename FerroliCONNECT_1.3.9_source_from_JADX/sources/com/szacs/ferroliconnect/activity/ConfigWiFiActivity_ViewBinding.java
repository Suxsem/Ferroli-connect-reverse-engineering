package com.szacs.ferroliconnect.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class ConfigWiFiActivity_ViewBinding implements Unbinder {
    private ConfigWiFiActivity target;

    @UiThread
    public ConfigWiFiActivity_ViewBinding(ConfigWiFiActivity configWiFiActivity) {
        this(configWiFiActivity, configWiFiActivity.getWindow().getDecorView());
    }

    @UiThread
    public ConfigWiFiActivity_ViewBinding(ConfigWiFiActivity configWiFiActivity, View view) {
        this.target = configWiFiActivity;
        configWiFiActivity.flFragment = (FrameLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.flFragment, "field 'flFragment'", FrameLayout.class);
        configWiFiActivity.ivBack = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivBack, "field 'ivBack'", ImageView.class);
        configWiFiActivity.ivNext = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.ivNext, "field 'ivNext'", ImageView.class);
        configWiFiActivity.llMain = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llMain, "field 'llMain'", RelativeLayout.class);
        configWiFiActivity.TitleTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.title, "field 'TitleTv'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        ConfigWiFiActivity configWiFiActivity = this.target;
        if (configWiFiActivity != null) {
            this.target = null;
            configWiFiActivity.flFragment = null;
            configWiFiActivity.ivBack = null;
            configWiFiActivity.ivNext = null;
            configWiFiActivity.llMain = null;
            configWiFiActivity.TitleTv = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
