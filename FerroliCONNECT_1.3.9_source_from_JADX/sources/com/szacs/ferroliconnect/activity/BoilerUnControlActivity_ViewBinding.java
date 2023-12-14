package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class BoilerUnControlActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private BoilerUnControlActivity target;

    @UiThread
    public BoilerUnControlActivity_ViewBinding(BoilerUnControlActivity boilerUnControlActivity) {
        this(boilerUnControlActivity, boilerUnControlActivity.getWindow().getDecorView());
    }

    @UiThread
    public BoilerUnControlActivity_ViewBinding(BoilerUnControlActivity boilerUnControlActivity, View view) {
        super(boilerUnControlActivity, view);
        this.target = boilerUnControlActivity;
        boilerUnControlActivity.ivWeather = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_weather, "field 'ivWeather'", ImageView.class);
        boilerUnControlActivity.ivHeart = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_heart, "field 'ivHeart'", ImageView.class);
        boilerUnControlActivity.ivDhw = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_dhw, "field 'ivDhw'", ImageView.class);
        boilerUnControlActivity.ivFlame = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_flame, "field 'ivFlame'", ImageView.class);
        boilerUnControlActivity.ivError = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_error, "field 'ivError'", ImageView.class);
        boilerUnControlActivity.tvHeart = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_heart, "field 'tvHeart'", TextView.class);
        boilerUnControlActivity.tvDhw = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_dhw, "field 'tvDhw'", TextView.class);
        boilerUnControlActivity.tvFlame = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_flame, "field 'tvFlame'", TextView.class);
        boilerUnControlActivity.tvErrorInfo = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_error_info, "field 'tvErrorInfo'", TextView.class);
        boilerUnControlActivity.tvTemp = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_temp, "field 'tvTemp'", TextView.class);
        boilerUnControlActivity.tvLocal = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tv_local, "field 'tvLocal'", TextView.class);
        boilerUnControlActivity.rlTempLocal = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rl_temp_local, "field 'rlTempLocal'", RelativeLayout.class);
        boilerUnControlActivity.ivSetHeatTemp = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_set_heat_temp, "field 'ivSetHeatTemp'", ImageView.class);
        boilerUnControlActivity.ivSetDhwTemp = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_set_dhw_temp, "field 'ivSetDhwTemp'", ImageView.class);
        boilerUnControlActivity.ivWifiSignal = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.iv_wifi, "field 'ivWifiSignal'", ImageView.class);
    }

    public void unbind() {
        BoilerUnControlActivity boilerUnControlActivity = this.target;
        if (boilerUnControlActivity != null) {
            this.target = null;
            boilerUnControlActivity.ivWeather = null;
            boilerUnControlActivity.ivHeart = null;
            boilerUnControlActivity.ivDhw = null;
            boilerUnControlActivity.ivFlame = null;
            boilerUnControlActivity.ivError = null;
            boilerUnControlActivity.tvHeart = null;
            boilerUnControlActivity.tvDhw = null;
            boilerUnControlActivity.tvFlame = null;
            boilerUnControlActivity.tvErrorInfo = null;
            boilerUnControlActivity.tvTemp = null;
            boilerUnControlActivity.tvLocal = null;
            boilerUnControlActivity.rlTempLocal = null;
            boilerUnControlActivity.ivSetHeatTemp = null;
            boilerUnControlActivity.ivSetDhwTemp = null;
            boilerUnControlActivity.ivWifiSignal = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
