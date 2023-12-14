package p110io.fogcloud.sdk.easylink.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.taobao.accs.utl.UtilityImpl;
import java.net.NetworkInterface;
import java.net.SocketException;
import p110io.fogcloud.sdk.easylink.helper.ComHelper;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkErrCode;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkParams;
import p110io.fogcloud.sdk.easylink.jetty.EasyServer;

/* renamed from: io.fogcloud.sdk.easylink.api.EasyLink */
public class EasyLink {
    /* access modifiers changed from: private */
    public static EasyServer mEasyServer = null;
    public static final int mPort = 8000;
    /* access modifiers changed from: private */
    public ComHelper comfunc = new ComHelper();
    /* access modifiers changed from: private */
    public boolean eltag = false;
    private Context mContext;
    /* access modifiers changed from: private */
    public EasyLink_plus mEasylinkPlus;
    private WifiInfo mWifiInfo;
    private WifiManager mWifiManager;
    /* access modifiers changed from: private */
    public Thread workThread = null;

    public EasyLink(Context context) {
        this.mContext = context;
    }

    public String getSSID() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        this.mWifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        this.mWifiInfo = this.mWifiManager.getConnectionInfo();
        String ssid = this.mWifiInfo.getSSID();
        return ssid.substring(1, ssid.length() - 1);
    }

    public boolean isAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (connectivityManager.getActiveNetworkInfo() != null) {
            return connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public boolean isWifiEnabled() {
        Context context = this.mContext;
        return context != null && 3 == ((WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI)).getWifiState();
    }

    public boolean is3rd() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 0;
    }

    public void startEasyLink(EasyLinkParams easyLinkParams, EasyLinkCallBack easyLinkCallBack) {
        if (!ComHelper.checkPara(easyLinkParams.ssid)) {
            this.comfunc.failureCBEasyLink(EasyLinkErrCode.INVALID_CODE, EasyLinkErrCode.INVALID, easyLinkCallBack);
        } else if (this.mContext != null) {
            startEasyLink(easyLinkParams.ssid, easyLinkParams.password, easyLinkParams.isSendIP, easyLinkParams.runSecond, easyLinkParams.sleeptime, easyLinkParams.extraData, easyLinkParams.rc4key, easyLinkParams.isSmallMTU, easyLinkCallBack);
        } else {
            this.comfunc.failureCBEasyLink(EasyLinkErrCode.CONTEXT_CODE, EasyLinkErrCode.CONTEXT, easyLinkCallBack);
        }
    }

    private void startEasyLink(String str, String str2, boolean z, int i, int i2, String str3, String str4, boolean z2, EasyLinkCallBack easyLinkCallBack) {
        final EasyLinkCallBack easyLinkCallBack2 = easyLinkCallBack;
        if (!this.eltag) {
            if (this.workThread == null) {
                final int i3 = i;
                this.workThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep((long) i3);
                            if (EasyLink.this.eltag) {
                                EasyLink.this.stopEasyLink(easyLinkCallBack2);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                this.workThread.start();
            }
            try {
                startEasylink(str, str2, z, i2, str3, str4, z2, easyLinkCallBack);
                this.eltag = true;
                if (z) {
                    try {
                        mEasyServer = new EasyServer(mPort);
                        mEasyServer.start(easyLinkCallBack2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.comfunc.successCBEasyLink(EasyLinkErrCode.START_CODE, EasyLinkErrCode.SUCCESS, easyLinkCallBack2);
            } catch (Exception e2) {
                this.comfunc.failureCBEasyLink(EasyLinkErrCode.EXCEPTION_CODE, e2.getMessage(), easyLinkCallBack2);
            }
        } else {
            this.comfunc.failureCBEasyLink(EasyLinkErrCode.BUSY_CODE, EasyLinkErrCode.BUSY, easyLinkCallBack2);
        }
    }

    public void stopEasyLink(final EasyLinkCallBack easyLinkCallBack) {
        new Thread() {
            public void run() {
                super.run();
                if (EasyLink.this.mEasylinkPlus == null || !EasyLink.this.eltag) {
                    EasyLink.this.comfunc.failureCBEasyLink(EasyLinkErrCode.CLOSED_CODE, EasyLinkErrCode.CLOSED, easyLinkCallBack);
                    return;
                }
                if (EasyLink.this.workThread != null) {
                    Thread unused = EasyLink.this.workThread = null;
                }
                EasyLink.this.mEasylinkPlus.stopTransmitting();
                if (EasyLink.mEasyServer != null && EasyLink.mEasyServer.isStarted()) {
                    EasyLink.mEasyServer.stop();
                }
                boolean unused2 = EasyLink.this.eltag = false;
                EasyLink.this.comfunc.successCBEasyLink(EasyLinkErrCode.STOP_CODE, EasyLinkErrCode.SUCCESS, easyLinkCallBack);
            }
        }.start();
    }

    /* access modifiers changed from: protected */
    public void startEasylink(String str, String str2, boolean z, int i, String str3, String str4, boolean z2, EasyLinkCallBack easyLinkCallBack) {
        String str5;
        int i2;
        int parseInt;
        if (z) {
            parseInt = getNormalIP(this.mContext);
        } else if (ComHelper.isInteger(str3)) {
            parseInt = Integer.parseInt(str3);
            str3 = "";
        } else {
            str5 = str3;
            i2 = 1000;
            this.mEasylinkPlus = EasyLink_plus.getInstence(this.mContext);
            if (NetworkInterface.getByName("wlan0").getMTU() < 1500 || z2) {
                this.mEasylinkPlus.setSmallMtu(true);
            }
            this.mEasylinkPlus.transmitSettings(str, str2, i2, i, str5, str4);
        }
        i2 = parseInt;
        str5 = str3;
        this.mEasylinkPlus = EasyLink_plus.getInstence(this.mContext);
        try {
            this.mEasylinkPlus.setSmallMtu(true);
        } catch (SocketException e) {
            e.printStackTrace();
            this.comfunc.failureCBEasyLink(EasyLinkErrCode.EXCEPTION_CODE, e.getMessage(), easyLinkCallBack);
        }
        try {
            this.mEasylinkPlus.transmitSettings(str, str2, i2, i, str5, str4);
        } catch (Exception e2) {
            e2.printStackTrace();
            this.comfunc.failureCBEasyLink(EasyLinkErrCode.EXCEPTION_CODE, e2.getMessage(), easyLinkCallBack);
        }
    }

    private int getNormalIP(Context context) {
        this.mWifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        this.mWifiInfo = this.mWifiManager.getConnectionInfo();
        return this.mWifiInfo.getIpAddress();
    }
}
