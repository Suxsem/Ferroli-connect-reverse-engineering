package p110io.fogcloud.sdk.easylink.plus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import com.taobao.accs.utl.UtilityImpl;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import p110io.fogcloud.sdk.easylink.helper.ProbeReqData;

/* renamed from: io.fogcloud.sdk.easylink.plus.EasyLink_minus */
public class EasyLink_minus {
    private Thread mCallbackThread;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public int mErrorId;
    private IntentFilter mIntentFilter;
    private List<Integer> mNetId;
    private BroadcastReceiver mReceiver;
    /* access modifiers changed from: private */
    public boolean mScanning;
    boolean stopSending;

    public boolean isScanning() {
        return this.mScanning;
    }

    public EasyLink_minus(Context context, Thread thread) {
        this(context);
        this.mCallbackThread = thread;
    }

    public EasyLink_minus(Context context) {
        this.stopSending = false;
        this.mIntentFilter = null;
        this.mErrorId = 0;
        this.mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                boolean unused = EasyLink_minus.this.mScanning = false;
                EasyLink_minus.this.mContext.unregisterReceiver(this);
                if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS")) {
                    System.out.println("SCAN_RESULTS_AVAILABLE");
                    boolean unused2 = EasyLink_minus.this.mScanning = false;
                }
                if (intent.getAction().equals("android.net.wifi.STATE_CHANGE")) {
                    try {
                        Parcelable parcelableExtra = intent.getParcelableExtra("networkInfo");
                        if (parcelableExtra != null && !((NetworkInfo) parcelableExtra).isAvailable()) {
                            int unused3 = EasyLink_minus.this.mErrorId = 102;
                            boolean unused4 = EasyLink_minus.this.mScanning = false;
                            EasyLink_minus.this.clearNetList();
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                try {
                    if (intent.getIntExtra("wifi_state", 0) != 0) {
                        int unused5 = EasyLink_minus.this.mErrorId = 101;
                        boolean unused6 = EasyLink_minus.this.mScanning = false;
                        return;
                    }
                    EasyLink_minus.this.clearNetList();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        };
        this.mNetId = new ArrayList();
        this.mContext = context;
        this.mIntentFilter = new IntentFilter();
        this.mIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        this.mIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        this.mIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.mContext.registerReceiver(this.mReceiver, this.mIntentFilter);
    }

    public void transmitSettings(final String str, final String str2, final int i) {
        new Thread() {
            public void run() {
                EasyLink_minus.this.startTransmit(str, str2, i);
            }
        }.start();
    }

    public boolean startTransmit(String str, String str2, int i) {
        String[] strArr;
        ArrayList arrayList;
        this.stopSending = false;
        try {
            strArr = new ProbeReqData().bgProtocol(str, str2, i);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            strArr = null;
        }
        WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.BSSID = null;
        wifiConfiguration.preSharedKey = null;
        wifiConfiguration.wepKeys = new String[4];
        wifiConfiguration.wepTxKeyIndex = 0;
        wifiConfiguration.priority = 0;
        wifiConfiguration.hiddenSSID = true;
        wifiConfiguration.allowedKeyManagement.set(0);
        wifiConfiguration.allowedGroupCiphers.set(0);
        wifiConfiguration.allowedGroupCiphers.set(1);
        wifiConfiguration.allowedGroupCiphers.set(2);
        wifiConfiguration.allowedGroupCiphers.set(3);
        wifiConfiguration.allowedPairwiseCiphers.set(1);
        wifiConfiguration.allowedPairwiseCiphers.set(2);
        wifiConfiguration.allowedProtocols.set(0);
        wifiConfiguration.allowedProtocols.set(1);
        while (!this.stopSending) {
            for (int i2 = 1; i2 < strArr.length; i2++) {
                wifiConfiguration.SSID = String.format("\"%s\"", new Object[]{strArr[i2]});
                wifiManager.addNetwork(wifiConfiguration);
                wifiManager.saveConfiguration();
                for (WifiConfiguration next : wifiManager.getConfiguredNetworks()) {
                    if (next.SSID.equals(wifiConfiguration.SSID)) {
                        this.mNetId.add(Integer.valueOf(next.networkId));
                    }
                }
                try {
                    for (Integer intValue : this.mNetId) {
                        int intValue2 = intValue.intValue();
                        wifiManager.disableNetwork(intValue2);
                        wifiManager.enableNetwork(intValue2, false);
                        wifiManager.startScan();
                        Thread.sleep(50);
                    }
                    arrayList = new ArrayList();
                } catch (Exception unused) {
                    arrayList = new ArrayList();
                } catch (Throwable th) {
                    this.mNetId = new ArrayList();
                    throw th;
                }
                this.mNetId = arrayList;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0004 A[Catch:{ Exception -> 0x0037, all -> 0x002e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendProbeRequest(android.net.wifi.WifiManager r4, java.util.List<java.lang.Integer> r5) {
        /*
            r3 = this;
        L_0x0000:
            boolean r0 = r3.stopSending     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            if (r0 != 0) goto L_0x0028
            java.util.Iterator r0 = r5.iterator()     // Catch:{ Exception -> 0x0037, all -> 0x002e }
        L_0x0008:
            boolean r1 = r0.hasNext()     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            if (r1 == 0) goto L_0x0000
            java.lang.Object r1 = r0.next()     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            java.lang.Integer r1 = (java.lang.Integer) r1     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            int r1 = r1.intValue()     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            r4.disableNetwork(r1)     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            r2 = 0
            r4.enableNetwork(r1, r2)     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            r4.startScan()     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            r1 = 50
            java.lang.Thread.sleep(r1)     // Catch:{ Exception -> 0x0037, all -> 0x002e }
            goto L_0x0008
        L_0x0028:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            goto L_0x003c
        L_0x002e:
            r4 = move-exception
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r3.mNetId = r5
            throw r4
        L_0x0037:
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L_0x003c:
            r3.mNetId = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p110io.fogcloud.sdk.easylink.plus.EasyLink_minus.sendProbeRequest(android.net.wifi.WifiManager, java.util.List):void");
    }

    /* access modifiers changed from: private */
    public void clearNetList() {
        WifiManager wifiManager = (WifiManager) this.mContext.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        if (wifiManager != null && wifiManager.getConfiguredNetworks() != null) {
            for (WifiConfiguration next : wifiManager.getConfiguredNetworks()) {
                for (byte b : next.SSID.replaceAll("\"", "").getBytes()) {
                    if (b == 1) {
                        wifiManager.removeNetwork(next.networkId);
                        wifiManager.saveConfiguration();
                    }
                }
            }
        }
    }

    public void stopTransmitting() {
        this.stopSending = true;
        clearNetList();
    }
}
