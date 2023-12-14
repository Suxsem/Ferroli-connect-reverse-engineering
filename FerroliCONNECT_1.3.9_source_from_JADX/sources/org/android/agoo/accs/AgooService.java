package org.android.agoo.accs;

import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.agoo.C2122a;
import java.nio.charset.Charset;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.control.AgooFactory;

/* compiled from: Taobao */
public class AgooService extends TaoBaseService {

    /* renamed from: a */
    public static CallBack f4057a;

    /* renamed from: b */
    public static CallBack f4058b;

    /* renamed from: c */
    private AgooFactory f4059c;

    public void onCreate() {
        super.onCreate();
        ALog.m3725d("AgooService", "into--[onCreate]", new Object[0]);
        this.f4059c = AgooFactory.getInstance(getApplicationContext());
    }

    public void onData(String str, String str2, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("AgooService", "into--[onData]:serviceId:" + str + ",dataId=" + str3, new Object[0]);
            StringBuilder sb = new StringBuilder();
            sb.append("push data:");
            sb.append(new String(bArr, Charset.forName("UTF-8")));
            ALog.m3725d("AgooService", sb.toString(), new Object[0]);
        }
        UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", AdapterUtilityImpl.getDeviceId(getApplicationContext()), str3);
        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_TOTAL_ARRIVE, "total_arrive", 0.0d);
        try {
            this.f4059c.saveMsg(bArr);
            this.f4059c.msgRecevie(bArr, "accs", extraInfo);
        } catch (Throwable th) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", "onDataError", th);
            ALog.m3727e("AgooService", "into--[onData,dealMessage]:error:" + th, new Object[0]);
        }
    }

    public void onBind(String str, int i, String str2, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.m3727e("AgooService", "into--[onBind]:serviceId:" + str + ",errorCode=" + i, new Object[0]);
        }
        if (f4057a != null && GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                f4057a.onSuccess();
            } else {
                ErrorCode build = C2122a.m3818a(i, str2).build();
                f4057a.onFailure(build.getCode(), build.getMsg());
            }
        }
        f4057a = null;
    }

    public void onUnbind(String str, int i, String str2, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.m3727e("AgooService", "into--[onUnbind]:serviceId:" + str + ",errorCode=" + i, new Object[0]);
        }
        if (f4058b != null && GlobalClientInfo.AGOO_SERVICE_ID.equals(str)) {
            if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                f4058b.onSuccess();
            } else {
                ErrorCode build = C2122a.m3818a(i, str2).build();
                f4058b.onFailure(build.getCode(), build.getMsg());
            }
        }
        f4058b = null;
    }

    public void onSendData(String str, String str2, int i, String str3, TaoBaseService.ExtraInfo extraInfo) {
        try {
            if (ALog.isPrintLog(ALog.Level.I)) {
                ALog.m3728i("AgooService", "onSendData,dataId=" + str2 + ",errorCode=" + i + ",errorMsg=" + str3 + ",serviceId=" + str, new Object[0]);
            }
            if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                if (TextUtils.equals(AgooConstants.AGOO_SERVICE_AGOOACK, str)) {
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "8/9", 0.0d);
                }
                if (TextUtils.isEmpty(str) || !TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK) || Long.parseLong(str2) <= 300000000 || Long.parseLong(str2) >= 600000000) {
                    if (!TextUtils.isEmpty(str) && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK) && Long.parseLong(str2) > 600000000 && ALog.isPrintLog(ALog.Level.I)) {
                        ALog.m3728i("AgooService", "onSendData,reportData=" + str2 + ",serviceId=" + str, new Object[0]);
                    }
                } else if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.m3728i("AgooService", "onSendData,AckData=" + str2 + ",serviceId=" + str, new Object[0]);
                }
            } else {
                if (TextUtils.equals(AgooConstants.AGOO_SERVICE_AGOOACK, str)) {
                    Config.m3891a(getApplicationContext(), 1);
                    AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i), 0.0d);
                }
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.m3728i("AgooService", "onSendData error,dataId=" + str2 + ",serviceId=" + str, new Object[0]);
                    ALog.m3727e("AgooService", "into--[parseError]", new Object[0]);
                }
                UTMini instance = UTMini.getInstance();
                String deviceId = AdapterUtilityImpl.getDeviceId(getApplicationContext());
                instance.commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", deviceId, Constants.KEY_ERROR_CODE, str2 + ",serviceId=" + str + ",errorCode=" + i);
            }
        } catch (Throwable th) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.m3727e("AgooService", "onSendData exception,e=" + th.getMessage() + ",e.getStackMsg=" + m3889a(th), new Object[0]);
            }
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "accs.agooService", AdapterUtilityImpl.getDeviceId(getApplicationContext()), "onSendDataException", m3889a(th));
        }
    }

    public void onResponse(String str, String str2, int i, String str3, byte[] bArr, TaoBaseService.ExtraInfo extraInfo) {
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i("AgooService", "onResponse,dataId=" + str2 + ",errorCode=" + i + ",errorMsg=" + str3 + ",data=" + bArr + ",serviceId=" + str, new Object[0]);
        }
        String str4 = null;
        if (bArr != null) {
            try {
                if (bArr.length > 0) {
                    str4 = new String(bArr, "utf-8");
                }
            } catch (Throwable th) {
                ALog.m3727e("AgooService", "onResponse get data error,e=" + th, new Object[0]);
            }
        }
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d("AgooService", "onResponse,message=" + str4, new Object[0]);
        }
        if (i == AccsErrorCode.SUCCESS.getCodeInt() && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK)) {
            if (ALog.isPrintLog(ALog.Level.I)) {
                ALog.m3728i("AgooService", "request is success", Constants.KEY_DATA_ID, str2);
            }
            this.f4059c.updateMsg(bArr, true);
        } else if (i != AccsErrorCode.SUCCESS.getCodeInt() && TextUtils.equals(str, AgooConstants.AGOO_SERVICE_AGOOACK)) {
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.m3727e("AgooService", "request is error", Constants.KEY_DATA_ID, str2, "errorid", Integer.valueOf(i));
            }
            Config.m3891a(getApplicationContext(), 1);
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, String.valueOf(i), 0.0d);
        } else if (ALog.isPrintLog(ALog.Level.E)) {
            ALog.m3727e("AgooService", "business request is error,message=" + str4, new Object[0]);
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /* renamed from: a */
    private String m3889a(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                stringBuffer.append(stackTraceElement.toString());
                stringBuffer.append("\n");
            }
        }
        return stringBuffer.toString();
    }
}
