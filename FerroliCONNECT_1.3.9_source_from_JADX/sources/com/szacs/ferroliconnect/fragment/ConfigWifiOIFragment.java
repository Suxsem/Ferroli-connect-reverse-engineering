package com.szacs.ferroliconnect.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import java.util.Timer;
import java.util.TimerTask;

public class ConfigWifiOIFragment extends Fragment {
    private boolean bApConfig = false;
    boolean bright = true;
    Runnable flashRunnable = new Runnable() {
        public void run() {
            if (ConfigWifiOIFragment.this.bright) {
                ConfigWifiOIFragment configWifiOIFragment = ConfigWifiOIFragment.this;
                configWifiOIFragment.bright = false;
                configWifiOIFragment.vWiFi.setBackgroundColor(Color.parseColor("#EAEAEA"));
                return;
            }
            ConfigWifiOIFragment configWifiOIFragment2 = ConfigWifiOIFragment.this;
            configWifiOIFragment2.bright = true;
            configWifiOIFragment2.vWiFi.setBackgroundColor(-1);
        }
    };
    private ImageView ivConfigTip;
    Handler mHandler;
    Timer timer;
    TimerTask timerTask;
    private TextView tvConfigTip;
    /* access modifiers changed from: private */
    public View vWiFi;

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C1683R.C1686layout.fragment_config_wifi_oi, viewGroup, false);
        this.vWiFi = inflate.findViewById(C1683R.C1685id.vWiFi);
        this.tvConfigTip = (TextView) inflate.findViewById(C1683R.C1685id.tvStep2);
        this.ivConfigTip = (ImageView) inflate.findViewById(C1683R.C1685id.iv_config_tip);
        setTip();
        this.mHandler = new Handler();
        this.timerTask = new TimerTask() {
            public void run() {
                ConfigWifiOIFragment.this.mHandler.post(ConfigWifiOIFragment.this.flashRunnable);
            }
        };
        this.timer = new Timer();
        this.timer.schedule(this.timerTask, 0, 150);
        return inflate;
    }

    private void setTip() {
        String str;
        TextView textView = this.tvConfigTip;
        if (this.bApConfig) {
            str = getString(C1683R.string.configure_wifi_ap_type_press_set_button);
        } else {
            str = getString(C1683R.string.configure_wifi_press_set_button);
        }
        textView.setText(str);
        this.ivConfigTip.setImageResource(this.bApConfig ? C1683R.C1684drawable.ic_ap_config_tip : C1683R.C1684drawable.ic_distribution_net_step);
    }

    public void onResume() {
        super.onResume();
        setTip();
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.timer.cancel();
        this.timer = null;
        this.timerTask = null;
    }

    public boolean isbApConfig() {
        return this.bApConfig;
    }

    public void setbApConfig(boolean z) {
        this.bApConfig = z;
    }
}
