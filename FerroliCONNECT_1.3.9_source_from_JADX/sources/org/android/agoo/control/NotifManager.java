package org.android.agoo.control;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.json.JSONObject;

/* compiled from: Taobao */
public class NotifManager {
    private static final String ACK_MESSAGE = "accs.ackMessage";
    private static final int EVENT_ID = 66001;
    private static final String TAG = "NotifManager";
    /* access modifiers changed from: private */
    public static Context mContext;

    public void pingApp(String str, String str2, String str3, int i) {
    }

    public void init(Context context) {
        mContext = context;
    }

    public void handlerACKMessage(MsgDO msgDO, TaoBaseService.ExtraInfo extraInfo) {
        MsgDO msgDO2 = msgDO;
        if (msgDO2 != null) {
            if (!TextUtils.isEmpty(msgDO2.msgIds) || !TextUtils.isEmpty(msgDO2.removePacks) || !TextUtils.isEmpty(msgDO2.errorCode)) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put("api", AgooConstants.AGOO_SERVICE_AGOOACK);
                    hashMap.put(AgooConstants.MESSAGE_ID, msgDO2.msgIds + "@" + msgDO2.messageSource);
                    if (!TextUtils.isEmpty(msgDO2.removePacks)) {
                        hashMap.put("del_pack", msgDO2.removePacks);
                    }
                    if (!TextUtils.isEmpty(msgDO2.errorCode)) {
                        hashMap.put("ec", msgDO2.errorCode);
                    }
                    if (!TextUtils.isEmpty(msgDO2.type)) {
                        hashMap.put("type", msgDO2.type);
                    }
                    if (!TextUtils.isEmpty(msgDO2.extData)) {
                        hashMap.put("ext", msgDO2.extData);
                    }
                    hashMap.put("appkey", Config.m3894b(mContext));
                    hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(mContext));
                    byte[] bytes = new JSONObject(hashMap).toString().getBytes("UTF-8");
                    UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, ACK_MESSAGE, AdapterUtilityImpl.getDeviceId(mContext), "handlerACKMessageSendData", msgDO2.msgIds);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, "handlerACKMessage", 0.0d);
                    ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest((String) null, AgooConstants.AGOO_SERVICE_AGOOACK, bytes, (String) null, (String) null, (URL) null, (String) null);
                    if (msgDO2 != null) {
                        accsRequest.setTag(msgDO2.msgIds);
                    }
                    String sendPushResponse = ACCSManager.getAccsInstance(mContext, Config.m3894b(mContext), Config.m3896c(mContext)).sendPushResponse(mContext, accsRequest, extraInfo);
                    ALog.m3728i(TAG, "handlerACKMessage,endRequest,dataId=" + sendPushResponse, new Object[0]);
                } catch (Throwable th) {
                    if (ALog.isPrintLog(ALog.Level.E)) {
                        ALog.m3727e(TAG, "handlerACKMessage Throwable,msgIds=" + msgDO2.msgIds + ",type=" + msgDO2.type + ",e=" + th.toString(), new Object[0]);
                    }
                    UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, ACK_MESSAGE, AdapterUtilityImpl.getDeviceId(mContext), "handlerACKMessageExceptionFailed", th.toString());
                }
            } else {
                UTMini instance = UTMini.getInstance();
                String deviceId = AdapterUtilityImpl.getDeviceId(mContext);
                instance.commitEvent(AgooConstants.AGOO_EVENT_ID, ACK_MESSAGE, deviceId, "handlerACKMessageRetuen", "msgids=" + msgDO2.msgIds + ",removePacks=" + msgDO2.removePacks + ",errorCode=" + msgDO2.errorCode);
            }
        }
    }

    public void report(MsgDO msgDO, TaoBaseService.ExtraInfo extraInfo) {
        if (!TextUtils.isEmpty(msgDO.reportStr)) {
            try {
                if (Integer.parseInt(msgDO.reportStr) >= -1) {
                    reportMethod(msgDO, extraInfo);
                    if (!msgDO.isFromCache) {
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, msgDO.msgStatus, 0.0d);
                    }
                }
            } catch (Throwable th) {
                ALog.m3726e(TAG, "[report] is error", th, new Object[0]);
            }
        }
    }

    public void reportNotifyMessage(MsgDO msgDO) {
        MsgDO msgDO2 = msgDO;
        if (msgDO2 != null) {
            try {
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_REPORT_ID, msgDO2.msgIds, 0.0d);
                String sendRequest = ACCSManager.getAccsInstance(mContext, Config.m3894b(mContext), Config.m3896c(mContext)).sendRequest(mContext, new ACCSManager.AccsRequest((String) null, AgooConstants.AGOO_SERVICE_AGOOACK, convertMsgToBytes(msgDO), (String) null, (String) null, (URL) null, (String) null));
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.m3728i(TAG, "reportNotifyMessage", Constants.KEY_DATA_ID, sendRequest, NotificationCompat.CATEGORY_STATUS, msgDO2.msgStatus);
                }
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_CLICK, msgDO2.msgStatus, 0.0d);
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_ACK, msgDO2.msgStatus, 0.0d);
            } catch (Throwable th) {
                ALog.m3726e(TAG, "[reportNotifyMessage] is error", th, new Object[0]);
                AppMonitorAdapter.commitCount("accs", "error", th.toString(), 0.0d);
            }
        }
    }

    private byte[] convertMsgToBytes(MsgDO msgDO) throws UnsupportedEncodingException {
        HashMap hashMap = new HashMap();
        hashMap.put("api", "agooReport");
        hashMap.put(AgooConstants.MESSAGE_ID, msgDO.msgIds + "@" + msgDO.messageSource);
        hashMap.put("ext", msgDO.extData);
        hashMap.put(NotificationCompat.CATEGORY_STATUS, msgDO.msgStatus);
        if (!TextUtils.isEmpty(msgDO.errorCode)) {
            hashMap.put("ec", msgDO.errorCode);
        }
        if (!TextUtils.isEmpty(msgDO.type)) {
            hashMap.put("type", msgDO.type);
        }
        if (!TextUtils.isEmpty(msgDO.fromPkg)) {
            hashMap.put("fromPkg", msgDO.fromPkg);
        }
        if (!TextUtils.isEmpty(msgDO.fromAppkey)) {
            hashMap.put(AgooConstants.MESSAGE_FROM_APPKEY, msgDO.fromAppkey);
        }
        if (!TextUtils.isEmpty(msgDO.notifyEnable)) {
            hashMap.put("notifyEnable", msgDO.notifyEnable);
        }
        if (!TextUtils.isEmpty(msgDO.extData)) {
            hashMap.put("ext", msgDO.extData);
        }
        hashMap.put("isStartProc", Boolean.toString(msgDO.isStartProc));
        hashMap.put("appkey", Config.m3894b(mContext));
        hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(mContext));
        return new JSONObject(hashMap).toString().getBytes("UTF-8");
    }

    private void reportMethod(MsgDO msgDO, TaoBaseService.ExtraInfo extraInfo) {
        if (msgDO == null) {
            try {
                ALog.m3727e(TAG, "reportMethod msg null", new Object[0]);
            } catch (Throwable th) {
                AppMonitorAdapter.commitCount("accs", "error", th.toString(), 0.0d);
            }
        } else {
            ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest((String) null, AgooConstants.AGOO_SERVICE_AGOOACK, convertMsgToBytes(msgDO), (String) null, (String) null, (URL) null, (String) null);
            accsRequest.setTag(msgDO.msgIds);
            String sendPushResponse = ACCSManager.getAccsInstance(mContext, Config.m3894b(mContext), Config.m3896c(mContext)).sendPushResponse(mContext, accsRequest, extraInfo);
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3725d(TAG, AgooConstants.MESSAGE_REPORT, Constants.KEY_DATA_ID, sendPushResponse, NotificationCompat.CATEGORY_STATUS, msgDO.msgStatus, "errorcode", msgDO.errorCode);
            }
        }
    }

    public void reportThirdPushToken(String str, String str2, boolean z) {
        reportThirdPushToken((String) null, str, str2, (String) null, z);
    }

    public void reportThirdPushToken(String str, String str2, String str3, String str4, boolean z) {
        ThreadPoolExecutorFactory.schedule(new C2366m(this, str, str3, str2, str4, z), 10, TimeUnit.SECONDS);
    }

    public void reportThirdPushToken(String str, String str2, String str3, boolean z) {
        reportThirdPushToken((String) null, str, str2, str3, z);
    }

    public void reportThirdPushToken(String str, String str2) {
        reportThirdPushToken((String) null, str, str2, (String) null, true);
    }

    public void doUninstall(String str, boolean z) {
        try {
            HashMap hashMap = new HashMap();
            hashMap.put("pack", str);
            hashMap.put("appkey", Config.m3894b(mContext));
            hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(mContext));
            ACCSManager.getAccsInstance(mContext, Config.m3894b(mContext), Config.m3896c(mContext)).sendPushResponse(mContext, new ACCSManager.AccsRequest((String) null, "agooKick", new JSONObject(hashMap).toString().getBytes("UTF-8"), (String) null, (String) null, (URL) null, (String) null), new TaoBaseService.ExtraInfo());
        } catch (Throwable th) {
            ALog.m3726e(TAG, "[doUninstall] is error", th, new Object[0]);
        }
    }

    private boolean isAppInstalled(String str) {
        PackageInfo packageInfo;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            packageInfo = mContext.getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return false;
            }
            ALog.m3728i(TAG, "isAppInstalled true..", new Object[0]);
            return true;
        } catch (Throwable unused) {
            packageInfo = null;
        }
    }

    private String getVersion(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "null";
            }
            String str2 = mContext.getPackageManager().getPackageInfo(str, 0).versionName;
            ALog.m3725d(TAG, "getVersion###版本号为 : " + str2, new Object[0]);
            return str2;
        } catch (Throwable unused) {
            return "null";
        }
    }
}
