package com.szacs.ferroliconnect.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.activity.ConfigWiFiActivity;

public class EnterKeyFragment extends Fragment {
    private static final String TAG = "EnterKeyFragment";
    private CheckBox cbApConfig;
    private CheckBox cbShowPwd;
    ConfigWiFiActivity configWiFiActivity;
    /* access modifiers changed from: private */
    public EditText etPassword;
    private String globalSsid = "";
    boolean isWiFiConnected = true;
    private EditText tvSSID;

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        String str;
        View inflate = layoutInflater.inflate(C1683R.C1686layout.fragment_config_wifi_enter_key, viewGroup, false);
        this.tvSSID = (EditText) inflate.findViewById(C1683R.C1685id.tvSSID);
        this.etPassword = (EditText) inflate.findViewById(C1683R.C1685id.etPassword);
        this.cbShowPwd = (CheckBox) inflate.findViewById(C1683R.C1685id.cbShowPwd);
        this.cbApConfig = (CheckBox) inflate.findViewById(C1683R.C1685id.cb_ap);
        this.cbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    EnterKeyFragment.this.etPassword.setInputType(1);
                } else {
                    EnterKeyFragment.this.etPassword.setInputType(129);
                }
            }
        });
        this.configWiFiActivity = (ConfigWiFiActivity) getActivity();
        if (!this.configWiFiActivity.getSSID().startsWith("EasyLink_")) {
            this.globalSsid = this.configWiFiActivity.getSSID();
        }
        SharedPreferences sharedPreferences = this.configWiFiActivity.getSharedPreferences("ferroli_user", 0);
        if (this.configWiFiActivity.getSSID().startsWith("EasyLink_")) {
            str = sharedPreferences.getString("config_wifi_ssid", "");
            if (str.equals("")) {
                str = this.globalSsid;
            }
        } else {
            str = this.configWiFiActivity.getSSID();
        }
        this.tvSSID.setText(str);
        setWiFiConnected(this.isWiFiConnected);
        if (!TextUtils.isEmpty(str)) {
            this.etPassword.setText(sharedPreferences.getString(str, ""));
        }
        return inflate;
    }

    public void onResume() {
        super.onResume();
        setWiFiConnected(this.isWiFiConnected);
    }

    public String getPassword() {
        return this.etPassword.getText().toString().trim();
    }

    public void setWiFiConnected(boolean z) {
        String str;
        int i = 0;
        SharedPreferences sharedPreferences = this.configWiFiActivity.getSharedPreferences("ferroli_user", 0);
        this.isWiFiConnected = z;
        if (this.tvSSID != null && getActivity() != null) {
            if (this.configWiFiActivity.getSSID().startsWith("EasyLink_")) {
                str = sharedPreferences.getString("config_wifi_ssid", "");
                if (str.equals("")) {
                    str = this.globalSsid;
                }
            } else if (this.configWiFiActivity.getSSID().equals(getActivity().getResources().getString(C1683R.string.enter_ssid))) {
                str = this.globalSsid;
            } else {
                str = this.configWiFiActivity.getSSID();
            }
            this.tvSSID.setText(this.isWiFiConnected ? str : getString(C1683R.string.configure_wifi_wifi_not_connected));
            this.etPassword.setText(sharedPreferences.getString(str, ""));
            Log.d(TAG, " IS START WITH EasyLink_" + this.configWiFiActivity.getSSID().startsWith("EasyLink_"));
            CheckBox checkBox = this.cbApConfig;
            if (!this.configWiFiActivity.getSSID().startsWith("EasyLink_")) {
                i = 8;
            }
            checkBox.setVisibility(i);
            this.cbApConfig.setChecked(this.configWiFiActivity.getSSID().startsWith("EasyLink_"));
        }
    }

    public boolean isApConfig() {
        return this.cbApConfig.isChecked();
    }

    public String getInputSSID() {
        return this.tvSSID.getText().toString().trim();
    }
}
