package com.szacs.ferroliconnect.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.taobao.accs.utl.UtilityImpl;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jetty.util.security.Constraint;

public class WifiHelper {
    public static final int DISABLED = 1;
    public static final int DISABLING = 2;
    public static final int ENABLE = 3;
    public static final int ENABLING = 4;
    public static final int UNKNOWN = 5;
    private static WifiHelper singleton;
    /* access modifiers changed from: private */
    public int STATE = 1;
    private final String TAG = "WifiHelper";
    /* access modifiers changed from: private */
    public boolean bAutoConnect = false;
    /* access modifiers changed from: private */
    public boolean bConnect = false;
    /* access modifiers changed from: private */
    public boolean bDisConnect = false;
    /* access modifiers changed from: private */
    public boolean isStartScanMode = true;
    /* access modifiers changed from: private */
    public OnWifiConnectFailedListener mConnectFailedListener;
    /* access modifiers changed from: private */
    public String mConnectingSSid;
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    /* access modifiers changed from: private */
    public List<String> mFailedConnectList;
    private OnRemoveWifiListener mOnRemoveWifiListener;
    /* access modifiers changed from: private */
    public OnWifiDisConnectListener mOnWifiDisConnectListener;
    /* access modifiers changed from: private */
    public boolean mWasAssociated;
    /* access modifiers changed from: private */
    public boolean mWasAssociating;
    /* access modifiers changed from: private */
    public boolean mWasHandshaking;
    /* access modifiers changed from: private */
    public WifiManager mWifiManager;
    private WifiBroadcast mWifiReceiver;
    /* access modifiers changed from: private */
    public OnWifiConnectSuccessListener onWifiConnectSuccessListener;
    /* access modifiers changed from: private */
    public OnWifiConnectintListener onWifiConnectintListener;
    /* access modifiers changed from: private */
    public OnWifiIDLEListener onWifiIDLEListener;
    /* access modifiers changed from: private */
    public OnWifiPWDErrorListener onWifiPWDErrorListener;
    /* access modifiers changed from: private */
    public OnWifiScanResultsListener onWifiScanResultsListener;
    /* access modifiers changed from: private */
    public OnWifiStateChangeListener onWifiStateChangeListener;
    /* access modifiers changed from: private */
    public Long sleepTime = 8000L;

    public interface OnRemoveWifiListener {
        void onRemoveResult(boolean z);
    }

    public interface OnWifiConnectFailedListener {
        void onWifiConnectFiled(boolean z);
    }

    public interface OnWifiConnectSuccessListener {
        void onWifiSuccess();
    }

    public interface OnWifiConnectintListener {
        void onWifiConnecting(WifiInfo wifiInfo, boolean z);
    }

    public interface OnWifiDisConnectListener {
        void onWifiDisResult(boolean z);
    }

    public interface OnWifiIDLEListener {
        void onWifiIDLE();
    }

    public interface OnWifiPWDErrorListener {
        void onWifiPWDError(boolean z);
    }

    public interface OnWifiScanResultsListener {
        void onWifiScanResults(List<ScanResult> list, List<WifiConfiguration> list2);
    }

    public interface OnWifiStateChangeListener {
        void onWifiStatesChange(int i);
    }

    @SuppressLint({"WifiManagerPotentialLeak"})
    public WifiHelper(Context context) {
        this.mContext = context;
        this.mFailedConnectList = new ArrayList();
        this.mWifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    }

    public static WifiHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new WifiHelper(context);
        }
        return singleton;
    }

    public void registerReceiver() {
        this.mWifiReceiver = new WifiBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        this.mContext.registerReceiver(this.mWifiReceiver, intentFilter);
    }

    public void closeReceiver() {
        WifiBroadcast wifiBroadcast = this.mWifiReceiver;
        if (wifiBroadcast != null) {
            this.mContext.unregisterReceiver(wifiBroadcast);
        }
    }

    public boolean connectNetwork(List<ScanResult> list, String str, String str2) {
        Log.i("WifiHelper", "to connect a new wifi:" + str);
        this.bConnect = true;
        this.mWasAssociating = false;
        this.mWasAssociated = false;
        this.mWasHandshaking = false;
        this.mConnectingSSid = str;
        disconnectWifi((Boolean) false);
        int addWifiConfig = addWifiConfig(list, str, str2);
        if (addWifiConfig == -1) {
            return false;
        }
        Log.i("WifiHelper", "to connect a new wifi:" + addWifiConfig);
        return this.mWifiManager.enableNetwork(addWifiConfig, true);
    }

    public boolean reConnectFreeNetwork(String str) {
        Log.i("WifiHelper", "to connect a save and free wifi:" + str);
        this.bConnect = true;
        this.mWasAssociating = false;
        this.mWasAssociated = false;
        this.mWasHandshaking = false;
        this.mConnectingSSid = str;
        disconnectWifi((Boolean) false);
        WifiConfiguration wifiConfiguration = getWifiConfiguration(str);
        if (wifiConfiguration != null) {
            removeNetWork(wifiConfiguration);
        }
        return connectNewNetwork(str, "");
    }

    public boolean reConnectNetwork(String str) {
        Log.i("WifiHelper", "to connect a saved wifi:" + str);
        this.bConnect = true;
        this.mWasAssociating = false;
        this.mWasAssociated = false;
        this.mWasHandshaking = false;
        this.mConnectingSSid = str;
        disconnectWifi((Boolean) false);
        WifiConfiguration wifiConfiguration = getWifiConfiguration(str);
        if (wifiConfiguration == null) {
            Log.e("WifiHelper", "is not save!");
            return false;
        }
        int i = wifiConfiguration.networkId;
        if (i == -1) {
            return false;
        }
        Log.i("WifiHelper", "to connect a saved wifi:" + i);
        return this.mWifiManager.enableNetwork(i, true);
    }

    public boolean connectNewNetwork(String str, String str2) {
        int addWifiConfig = addWifiConfig(str, str2);
        if (addWifiConfig != -1) {
            return this.mWifiManager.enableNetwork(addWifiConfig, true);
        }
        return false;
    }

    private int addWifiConfig(List<ScanResult> list, String str, String str2) {
        int i = -1;
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (list.get(i2).SSID.equals(str)) {
                i = addWifiConfig(str, str2);
            }
        }
        return i;
    }

    private int addWifiConfig(String str, String str2) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + str + "\"";
        if (str2 == null || str2.equals("")) {
            wifiConfiguration.allowedKeyManagement.set(0);
        } else {
            wifiConfiguration.preSharedKey = "\"" + str2 + "\"";
            wifiConfiguration.hiddenSSID = false;
            wifiConfiguration.status = 2;
        }
        int addNetwork = this.mWifiManager.addNetwork(wifiConfiguration);
        if (addNetwork != -1) {
        }
        return addNetwork;
    }

    public void startScan() {
        if (this.mWifiManager != null) {
            Log.i("WifiHelper", "to scan wifi!");
            this.mWifiManager.startScan();
        }
    }

    public void startScanMode() {
        if (this.isStartScanMode) {
            Log.i("WifiHelper", "to scan wifi!");
            this.isStartScanMode = false;
            new ScanThread().start();
        }
    }

    public void setScanCycle(Long l) {
        this.sleepTime = l;
    }

    public void closeScanMode() {
        this.isStartScanMode = true;
    }

    public boolean enableNetWork(int i) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.enableNetwork(i, true);
        }
        return false;
    }

    public int getIsConnected(String str) {
        if (str == null) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(str);
        sb.append("\"");
        return (!sb.toString().equals(getConnectWifiSSID()) || -1 == getConnectNetId()) ? 0 : 1;
    }

    public int getConnectSpeed(String str) {
        if (1 == getIsConnected(str)) {
            return getConnectSpeed();
        }
        return 0;
    }

    public String getConnectWifiSSID() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null ? wifiManager.getConnectionInfo().getSSID() : "";
    }

    public String getConnectWifiBSSID() {
        WifiManager wifiManager = this.mWifiManager;
        return wifiManager != null ? wifiManager.getConnectionInfo().getBSSID() : "";
    }

    public int getConnectIp() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo().getIpAddress();
        }
        return 0;
    }

    private int getConnectSpeed() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo().getLinkSpeed();
        }
        return 0;
    }

    public int getConnectNetId() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo().getNetworkId();
        }
        return -1;
    }

    public WifiInfo getConnectInfo() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            return wifiManager.getConnectionInfo();
        }
        return null;
    }

    public boolean disconnectWifi(Boolean bool) {
        Log.i("WifiHelper", "disconnectWifi!");
        this.bDisConnect = bool.booleanValue();
        this.mWasAssociating = false;
        this.mWasAssociated = false;
        this.mWasHandshaking = false;
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        int networkId = connectionInfo != null ? connectionInfo.getNetworkId() : -1;
        Log.i("WifiHelper", "start disconnectWifi:" + networkId);
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null || networkId == -1) {
            if (this.mOnWifiDisConnectListener != null) {
                this.bDisConnect = false;
                if (bool.booleanValue()) {
                    this.mOnWifiDisConnectListener.onWifiDisResult(false);
                }
            }
            return false;
        }
        wifiManager.disableNetwork(networkId);
        this.mWifiManager.disconnect();
        Log.i("WifiHelper", "disconnectWifi success!");
        return true;
    }

    public void disconnectWifi(int i) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager != null) {
            wifiManager.disableNetwork(i);
            this.mWifiManager.disconnect();
        }
    }

    public void removeNetWork(String str) {
        Log.i("WifiHelper", "to remove:" + str);
        WifiConfiguration wifiConfiguration = getWifiConfiguration(str);
        if (wifiConfiguration != null) {
            boolean removeNetWork = removeNetWork(wifiConfiguration);
            OnRemoveWifiListener onRemoveWifiListener = this.mOnRemoveWifiListener;
            if (onRemoveWifiListener != null) {
                onRemoveWifiListener.onRemoveResult(removeNetWork);
                return;
            }
            return;
        }
        OnRemoveWifiListener onRemoveWifiListener2 = this.mOnRemoveWifiListener;
        if (onRemoveWifiListener2 != null) {
            onRemoveWifiListener2.onRemoveResult(false);
        }
    }

    public boolean removeNetWork(WifiConfiguration wifiConfiguration) {
        if (this.mWifiManager == null || wifiConfiguration == null) {
            return false;
        }
        Log.i("WifiHelper", "to remove network id:" + wifiConfiguration.networkId);
        removeNetWork(wifiConfiguration.networkId);
        return true;
    }

    private void removeNetWork(int i) {
        boolean removeNetwork = this.mWifiManager.removeNetwork(i);
        Log.i("WifiHelper", "remove net ret = " + removeNetwork);
        boolean saveConfiguration = this.mWifiManager.saveConfiguration();
        Log.i("WifiHelper", "save config ret = " + saveConfiguration);
        this.mWifiManager.startScan();
    }

    public int getWifiState() {
        return this.STATE;
    }

    public boolean getWifiEnabled() {
        return this.mWifiManager.getWifiState() == 3;
    }

    public boolean setWifiEnabled(boolean z) {
        Log.i("WifiHelper", "set wifi enable:" + z);
        int i = this.STATE;
        if (i != 5) {
            if (z) {
                if (i == 1) {
                    this.mWasAssociating = false;
                    this.mWasAssociated = false;
                    this.mWasHandshaking = false;
                    this.mWifiManager.setWifiEnabled(z);
                    return true;
                }
            } else if (i == 3) {
                this.mWifiManager.setWifiEnabled(z);
                return true;
            }
        }
        return false;
    }

    public void setOnScanResultsListener(OnWifiScanResultsListener onWifiScanResultsListener2) {
        this.onWifiScanResultsListener = onWifiScanResultsListener2;
    }

    public void setOnWifiPWDErrorListener(OnWifiPWDErrorListener onWifiPWDErrorListener2) {
        this.onWifiPWDErrorListener = onWifiPWDErrorListener2;
    }

    public void setConnectFailedListener(OnWifiConnectFailedListener onWifiConnectFailedListener) {
        this.mConnectFailedListener = onWifiConnectFailedListener;
    }

    public void setOnWifiConnectSuccessListener(OnWifiConnectSuccessListener onWifiConnectSuccessListener2) {
        this.onWifiConnectSuccessListener = onWifiConnectSuccessListener2;
    }

    public void setOnWifiConnectingListener(OnWifiConnectintListener onWifiConnectintListener2) {
        this.onWifiConnectintListener = onWifiConnectintListener2;
    }

    public void setOnWifiIDLEListener(OnWifiIDLEListener onWifiIDLEListener2) {
        this.onWifiIDLEListener = onWifiIDLEListener2;
    }

    public void setOnWifiStateChangeListener(OnWifiStateChangeListener onWifiStateChangeListener2) {
        this.onWifiStateChangeListener = onWifiStateChangeListener2;
    }

    public void setOnWifiDisConnectSuccessListener(OnWifiDisConnectListener onWifiDisConnectListener) {
        this.mOnWifiDisConnectListener = onWifiDisConnectListener;
    }

    public void setOnRemoveWifiListener(OnRemoveWifiListener onRemoveWifiListener) {
        this.mOnRemoveWifiListener = onRemoveWifiListener;
    }

    /* access modifiers changed from: private */
    public WifiConfiguration getWifiConfiguration(String str) {
        List<WifiConfiguration> configuredNetworks;
        if ("".equals(str)) {
            Log.e("WifiHelper", "invalid param!");
            return null;
        }
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null || (configuredNetworks = wifiManager.getConfiguredNetworks()) == null) {
            return null;
        }
        for (WifiConfiguration next : configuredNetworks) {
            Log.i("WifiHelper", "save ssid:" + next.SSID + ", in:" + str);
            String str2 = next.SSID;
            if (str2.equals("\"" + str + "\"")) {
                return next;
            }
        }
        return null;
    }

    public boolean toConnectDifLockWifi(List<ScanResult> list, String str) {
        StringBuilder sb = new StringBuilder();
        WifiConfiguration wifiConfiguration = getWifiConfiguration(str);
        if (wifiConfiguration != null) {
            for (int i = 0; i < wifiConfiguration.allowedKeyManagement.size(); i++) {
                if (wifiConfiguration.allowedKeyManagement.get(i)) {
                    sb.append(" ");
                    if (i < WifiConfiguration.KeyMgmt.strings.length) {
                        sb.append(WifiConfiguration.KeyMgmt.strings[i]);
                    } else {
                        sb.append("??");
                    }
                }
            }
        }
        String sb2 = sb.toString();
        boolean isLock = isLock(list, str);
        Log.i("WifiHelper", "lock Type = " + sb2 + ", isLock = " + isLock);
        if (!isLock && !sb2.contains(Constraint.NONE)) {
            Log.i("WifiHelper", "to connect free ap");
            reConnectFreeNetwork(str);
            return true;
        } else if (!isLock || !sb2.contains(Constraint.NONE)) {
            return false;
        } else {
            Log.i("WifiHelper", "to remove network");
            WifiConfiguration wifiConfiguration2 = getWifiConfiguration(str);
            if (wifiConfiguration2 != null) {
                removeNetWork(wifiConfiguration2);
            }
            this.mWifiManager.startScan();
            if (this.bAutoConnect) {
                Log.i("WifiHelper", "to add failed connect:" + str);
                this.mFailedConnectList.add(str);
            }
            OnWifiPWDErrorListener onWifiPWDErrorListener2 = this.onWifiPWDErrorListener;
            if (onWifiPWDErrorListener2 != null) {
                onWifiPWDErrorListener2.onWifiPWDError(this.bAutoConnect);
            }
            return true;
        }
    }

    public boolean getIsWifiConfig(String str) {
        List<WifiConfiguration> configuredNetworks;
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null || (configuredNetworks = wifiManager.getConfiguredNetworks()) == null) {
            return false;
        }
        for (WifiConfiguration next : configuredNetworks) {
            String str2 = next.SSID;
            if (str2.equals("\"" + str + "\"") && next.networkId > -1) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public ScanResult getScanResult(String str) {
        List<ScanResult> scanResults;
        WifiManager wifiManager = this.mWifiManager;
        if (!(wifiManager == null || (scanResults = wifiManager.getScanResults()) == null)) {
            for (ScanResult next : scanResults) {
                if (next.SSID.equals(str.replace("\"", ""))) {
                    return next;
                }
            }
        }
        return null;
    }

    private static ScanResult getRecordByList(List<ScanResult> list, String str) {
        if (list == null) {
            return null;
        }
        for (ScanResult next : list) {
            if (str.equals(next.SSID)) {
                return next;
            }
        }
        return null;
    }

    public static boolean isLock(List<ScanResult> list, String str) {
        ScanResult recordByList;
        if (list == null || (recordByList = getRecordByList(list, str)) == null) {
            return false;
        }
        return isLock(recordByList.capabilities);
    }

    public static boolean isLock(String str) {
        return (getCapabilityType(str) == 0 || getCapabilityType(str) == 1) ? false : true;
    }

    public static int getCapabilityType(String str) {
        if (str.contains("WPA2")) {
            if (str.contains("WPA")) {
                return 3;
            }
            boolean contains = str.contains("WPS");
            return 3;
        } else if (str.contains("WPA")) {
            return 2;
        } else {
            return str.contains("WPS") ? 1 : 0;
        }
    }

    public static String getCapabilityTypeString(String str) {
        if (str.contains("WPA2")) {
            if (str.contains("WPA")) {
                return "WPA/WPA2 PSK";
            }
            boolean contains = str.contains("WPS");
            return "WPA2 PSK";
        } else if (str.contains("WPA")) {
            return "WPA PSK";
        } else {
            if (str.contains("WPS")) {
                return "WPS";
            }
            return "无";
        }
    }

    class ScanThread extends Thread {
        ScanThread() {
        }

        public void run() {
            super.run();
            while (!WifiHelper.this.isStartScanMode) {
                try {
                    Thread.sleep(WifiHelper.this.sleepTime.longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!WifiHelper.this.isStartScanMode && WifiHelper.this.mWifiManager != null && WifiHelper.this.getWifiState() == 3) {
                    WifiHelper.this.startScan();
                }
            }
        }
    }

    public class WifiBroadcast extends BroadcastReceiver {
        public WifiBroadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                if (WifiHelper.this.mWifiManager.getWifiState() == 1) {
                    int unused = WifiHelper.this.STATE = 1;
                    if (WifiHelper.this.onWifiStateChangeListener != null) {
                        WifiHelper.this.onWifiStateChangeListener.onWifiStatesChange(1);
                    }
                } else if (WifiHelper.this.mWifiManager.getWifiState() == 0) {
                    int unused2 = WifiHelper.this.STATE = 2;
                    if (WifiHelper.this.onWifiStateChangeListener != null) {
                        WifiHelper.this.onWifiStateChangeListener.onWifiStatesChange(2);
                    }
                } else if (WifiHelper.this.mWifiManager.getWifiState() == 2) {
                    int unused3 = WifiHelper.this.STATE = 4;
                    if (WifiHelper.this.onWifiStateChangeListener != null) {
                        WifiHelper.this.onWifiStateChangeListener.onWifiStatesChange(4);
                    }
                } else if (WifiHelper.this.mWifiManager.getWifiState() == 3) {
                    int unused4 = WifiHelper.this.STATE = 3;
                    if (WifiHelper.this.onWifiStateChangeListener != null) {
                        WifiHelper.this.mWifiManager.startScan();
                        WifiHelper.this.onWifiStateChangeListener.onWifiStatesChange(3);
                    }
                } else {
                    int unused5 = WifiHelper.this.STATE = 5;
                    if (WifiHelper.this.onWifiStateChangeListener != null) {
                        WifiHelper.this.onWifiStateChangeListener.onWifiStatesChange(5);
                    }
                }
            }
            if ("android.net.wifi.SCAN_RESULTS".equals(intent.getAction()) && WifiHelper.this.onWifiScanResultsListener != null) {
                WifiHelper.this.onWifiScanResultsListener.onWifiScanResults(WifiHelper.this.mWifiManager.getScanResults(), WifiHelper.this.mWifiManager.getConfiguredNetworks());
            }
            if ("android.net.wifi.supplicant.STATE_CHANGE".equals(intent.getAction())) {
                SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra("newState");
                Log.i("WifiHelper", "SupplicantState:" + supplicantState.toString());
                switch (C18771.$SwitchMap$android$net$wifi$SupplicantState[supplicantState.ordinal()]) {
                    case 1:
                        boolean unused6 = WifiHelper.this.mWasAssociating = true;
                        break;
                    case 2:
                        boolean unused7 = WifiHelper.this.mWasAssociated = true;
                        break;
                    case 4:
                    case 5:
                        if (WifiHelper.this.mWasAssociated || WifiHelper.this.mWasHandshaking) {
                            if (WifiHelper.this.mWasHandshaking) {
                                if (!WifiHelper.this.bConnect) {
                                    Log.i("WifiHelper", "not start connect");
                                    break;
                                } else {
                                    boolean unused8 = WifiHelper.this.bConnect = false;
                                    if (WifiHelper.this.bAutoConnect) {
                                        Log.i("WifiHelper", "add to failed list:" + WifiHelper.this.mConnectingSSid);
                                        WifiHelper.this.mFailedConnectList.add(WifiHelper.this.mConnectingSSid);
                                    }
                                    Log.i("WifiHelper", "on wifi pwd err, remove network：" + WifiHelper.this.mConnectingSSid);
                                    WifiHelper wifiHelper = WifiHelper.this;
                                    WifiHelper.this.removeNetWork(wifiHelper.getWifiConfiguration(wifiHelper.mConnectingSSid));
                                    if (WifiHelper.this.onWifiPWDErrorListener != null) {
                                        WifiHelper.this.onWifiPWDErrorListener.onWifiPWDError(WifiHelper.this.bAutoConnect);
                                        break;
                                    }
                                }
                            } else {
                                Log.i("WifiHelper", "on wifi connect err!");
                                if (WifiHelper.this.mConnectFailedListener != null && WifiHelper.this.bConnect) {
                                    boolean unused9 = WifiHelper.this.bConnect = false;
                                    if (WifiHelper.this.bAutoConnect) {
                                        Log.i("WifiHelper", "add to failed list:" + WifiHelper.this.mConnectingSSid);
                                        WifiHelper.this.mFailedConnectList.add(WifiHelper.this.mConnectingSSid);
                                    }
                                    WifiHelper.this.mConnectFailedListener.onWifiConnectFiled(WifiHelper.this.bAutoConnect);
                                    break;
                                }
                            }
                        }
                        break;
                    case 6:
                    case 7:
                        Log.i("WifiHelper", "UNINITIALIZED:on connect failed!");
                        if (WifiHelper.this.mConnectFailedListener != null && WifiHelper.this.bConnect) {
                            boolean unused10 = WifiHelper.this.bConnect = false;
                            if (WifiHelper.this.bAutoConnect) {
                                Log.i("WifiHelper", "add to failed list:" + WifiHelper.this.mConnectingSSid);
                                WifiHelper.this.mFailedConnectList.add(WifiHelper.this.mConnectingSSid);
                            }
                            WifiHelper.this.mConnectFailedListener.onWifiConnectFiled(WifiHelper.this.bAutoConnect);
                            break;
                        }
                    case 8:
                    case 9:
                        boolean unused11 = WifiHelper.this.mWasHandshaking = true;
                        break;
                    case 10:
                        if (WifiHelper.this.mWasAssociating && !WifiHelper.this.mWasAssociated) {
                            Log.i("WifiHelper", "INACTIVE:on connect failed!");
                            if (WifiHelper.this.mConnectFailedListener != null && WifiHelper.this.bConnect) {
                                boolean unused12 = WifiHelper.this.bConnect = false;
                                if (WifiHelper.this.bAutoConnect) {
                                    Log.i("WifiHelper", "add to failed list:" + WifiHelper.this.mConnectingSSid);
                                    WifiHelper.this.mFailedConnectList.add(WifiHelper.this.mConnectingSSid);
                                }
                                WifiHelper.this.mConnectFailedListener.onWifiConnectFiled(WifiHelper.this.bAutoConnect);
                                break;
                            }
                        }
                }
                if (WifiHelper.this.onWifiIDLEListener != null && WifiInfo.getDetailedStateOf(supplicantState).equals(NetworkInfo.DetailedState.IDLE)) {
                    WifiHelper.this.onWifiIDLEListener.onWifiIDLE();
                }
                if (WifiHelper.this.onWifiConnectintListener != null && WifiInfo.getDetailedStateOf(supplicantState).equals(NetworkInfo.DetailedState.CONNECTING)) {
                    Log.i("WifiHelper", "wifi connecting:" + WifiHelper.this.mWifiManager.getConnectionInfo().getSSID());
                    WifiHelper wifiHelper2 = WifiHelper.this;
                    ScanResult access$1800 = wifiHelper2.getScanResult(wifiHelper2.mWifiManager.getConnectionInfo().getSSID());
                    WifiHelper.this.onWifiConnectintListener.onWifiConnecting(WifiHelper.this.mWifiManager.getConnectionInfo(), access$1800 == null || !(WifiHelper.getCapabilityType(access$1800.capabilities) == 0 || WifiHelper.getCapabilityType(access$1800.capabilities) == 1));
                }
                if (WifiHelper.this.onWifiConnectSuccessListener != null && WifiInfo.getDetailedStateOf(supplicantState).equals(NetworkInfo.DetailedState.OBTAINING_IPADDR) && WifiHelper.this.bConnect) {
                    boolean unused13 = WifiHelper.this.bConnect = false;
                    boolean unused14 = WifiHelper.this.bAutoConnect = false;
                    WifiHelper wifiHelper3 = WifiHelper.this;
                    wifiHelper3.removeFromConnectFailed(wifiHelper3.mConnectingSSid);
                    WifiHelper.this.onWifiConnectSuccessListener.onWifiSuccess();
                }
                if (WifiHelper.this.mOnWifiDisConnectListener != null && WifiInfo.getDetailedStateOf(supplicantState).equals(NetworkInfo.DetailedState.DISCONNECTED) && WifiHelper.this.bDisConnect) {
                    boolean unused15 = WifiHelper.this.bDisConnect = false;
                    WifiHelper.this.mOnWifiDisConnectListener.onWifiDisResult(true);
                }
            }
        }
    }

    /* renamed from: com.szacs.ferroliconnect.broadcast.WifiHelper$1 */
    static /* synthetic */ class C18771 {
        static final /* synthetic */ int[] $SwitchMap$android$net$wifi$SupplicantState = new int[SupplicantState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|(3:23|24|26)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(26:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|26) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                android.net.wifi.SupplicantState[] r0 = android.net.wifi.SupplicantState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$android$net$wifi$SupplicantState = r0
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0014 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.ASSOCIATING     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x001f }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.ASSOCIATED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x002a }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.COMPLETED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0035 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.DISCONNECTED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0040 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.DORMANT     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x004b }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.INTERFACE_DISABLED     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0056 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.UNINITIALIZED     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0062 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.FOUR_WAY_HANDSHAKE     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x006e }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.GROUP_HANDSHAKE     // Catch:{ NoSuchFieldError -> 0x006e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x007a }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.INACTIVE     // Catch:{ NoSuchFieldError -> 0x007a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007a }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007a }
            L_0x007a:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0086 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.INVALID     // Catch:{ NoSuchFieldError -> 0x0086 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0086 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0086 }
            L_0x0086:
                int[] r0 = $SwitchMap$android$net$wifi$SupplicantState     // Catch:{ NoSuchFieldError -> 0x0092 }
                android.net.wifi.SupplicantState r1 = android.net.wifi.SupplicantState.SCANNING     // Catch:{ NoSuchFieldError -> 0x0092 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0092 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0092 }
            L_0x0092:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.broadcast.WifiHelper.C18771.<clinit>():void");
        }
    }

    /* access modifiers changed from: private */
    public void removeFromConnectFailed(String str) {
        List<String> list = this.mFailedConnectList;
        if (list != null && list.size() != 0) {
            int i = -1;
            for (String next : this.mFailedConnectList) {
                if (next.equals(str)) {
                    i = this.mFailedConnectList.indexOf(next);
                }
            }
            if (-1 != i) {
                this.mFailedConnectList.remove(i);
            }
        }
    }

    private boolean isConnectFailed(String str) {
        List<String> list = this.mFailedConnectList;
        if (!(list == null || list.size() == 0)) {
            for (String equals : this.mFailedConnectList) {
                if (equals.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getWifiApBand(WifiConfiguration wifiConfiguration) {
        Integer num = -1;
        try {
            num = Integer.valueOf(WifiConfiguration.class.getField("apBand").getInt(wifiConfiguration));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return num.intValue();
    }

    public void setWifiApBand(int i, WifiConfiguration wifiConfiguration) {
        try {
            WifiConfiguration.class.getField("apBand").setInt(wifiConfiguration, i);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public boolean isbAutoConnect() {
        return this.bAutoConnect;
    }

    public void setAutoConnect(boolean z) {
        this.bAutoConnect = z;
    }
}
