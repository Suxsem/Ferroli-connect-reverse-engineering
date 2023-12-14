package org.android.agoo.control;

import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.android.agoo.message.MessageService;
import org.json.JSONObject;

/* renamed from: org.android.agoo.control.d */
/* compiled from: Taobao */
class C2357d implements Runnable {

    /* renamed from: a */
    final /* synthetic */ byte[] f4074a;

    /* renamed from: b */
    final /* synthetic */ boolean f4075b;

    /* renamed from: c */
    final /* synthetic */ AgooFactory f4076c;

    C2357d(AgooFactory agooFactory, byte[] bArr, boolean z) {
        this.f4076c = agooFactory;
        this.f4074a = bArr;
        this.f4075b = z;
    }

    public void run() {
        try {
            String str = new String(this.f4074a, "utf-8");
            if (TextUtils.isEmpty(str)) {
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "msg==null", 0.0d);
                return;
            }
            ALog.m3728i("AgooFactory", "message = " + str, new Object[0]);
            JSONObject jSONObject = new JSONObject(str);
            String str2 = null;
            String string = jSONObject.getString("api");
            String string2 = jSONObject.getString(AgooConstants.MESSAGE_ID);
            if (TextUtils.equals(string, "agooReport")) {
                str2 = jSONObject.getString(NotificationCompat.CATEGORY_STATUS);
            }
            if (TextUtils.equals(string, AgooConstants.AGOO_SERVICE_AGOOACK)) {
                AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, "handlerACKMessage", 0.0d);
            }
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                if (!TextUtils.isEmpty(str2)) {
                    if (ALog.isPrintLog(ALog.Level.I)) {
                        ALog.m3728i("AgooFactory", "updateMsg data begin,api=" + string + ",id=" + string2 + ",status=" + str2 + ",reportTimes=" + Config.m3899f(AgooFactory.mContext), new Object[0]);
                    }
                    if (TextUtils.equals(string, "agooReport")) {
                        if (TextUtils.equals(str2, "4") && this.f4075b) {
                            this.f4076c.messageService.mo25529a(string2, "1");
                        } else if ((TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_CLICK) || TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_DISMISS)) && this.f4075b) {
                            this.f4076c.messageService.mo25529a(string2, MessageService.MSG_DB_COMPLETE);
                        }
                        AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_SUCCESS_ACK, str2, 0.0d);
                        return;
                    }
                    return;
                }
            }
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json key null", 0.0d);
        } catch (Throwable th) {
            ALog.m3727e("AgooFactory", "updateMsg get data error,e=" + th, new Object[0]);
            AppMonitorAdapter.commitCount("accs", BaseMonitor.COUNT_AGOO_FAIL_ACK, "json exception", 0.0d);
        }
    }
}
