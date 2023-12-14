package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.List;

public class WifiUtil {
    private static WifiUtil mInstance;
    private WifiManager mWifiManager;

    public boolean is24GHzWifi(int i) {
        return i > 2400 && i < 2500;
    }

    public boolean is5GHzWifi(int i) {
        return i > 4900 && i < 5900;
    }

    private WifiUtil(Context context) {
        this.mWifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
    }

    public static WifiUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (WifiUtil.class) {
                if (mInstance == null) {
                    mInstance = new WifiUtil(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    public void openWifi() {
        if (!this.mWifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(true);
        }
    }

    public void closeWifi() {
        if (this.mWifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(false);
        }
    }

    public List<ScanResult> getWifiScanResult() {
        ArrayList arrayList = new ArrayList();
        List<ScanResult> scanResults = this.mWifiManager.getScanResults();
        if (scanResults != null && scanResults.size() > 0) {
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult scanResult = scanResults.get(i);
                if (scanResult != null && !TextUtils.isEmpty(scanResult.SSID)) {
                    arrayList.add(scanResult);
                }
            }
        }
        return arrayList;
    }

    public int getWifiSignal(int i) {
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        WifiManager wifiManager = this.mWifiManager;
        return WifiManager.calculateSignalLevel(i, 4);
    }
}
