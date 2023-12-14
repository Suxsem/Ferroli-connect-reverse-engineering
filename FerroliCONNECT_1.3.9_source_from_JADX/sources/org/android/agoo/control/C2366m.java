package org.android.agoo.control;

import com.taobao.accs.ACCSManager;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UTMini;
import java.net.URL;
import java.util.HashMap;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.Config;
import org.json.JSONObject;

/* renamed from: org.android.agoo.control.m */
/* compiled from: Taobao */
class C2366m implements Runnable {

    /* renamed from: a */
    final /* synthetic */ String f4093a;

    /* renamed from: b */
    final /* synthetic */ String f4094b;

    /* renamed from: c */
    final /* synthetic */ String f4095c;

    /* renamed from: d */
    final /* synthetic */ String f4096d;

    /* renamed from: e */
    final /* synthetic */ boolean f4097e;

    /* renamed from: f */
    final /* synthetic */ NotifManager f4098f;

    C2366m(NotifManager notifManager, String str, String str2, String str3, String str4, boolean z) {
        this.f4098f = notifManager;
        this.f4093a = str;
        this.f4094b = str2;
        this.f4095c = str3;
        this.f4096d = str4;
        this.f4097e = z;
    }

    public void run() {
        String str;
        try {
            HashMap hashMap = new HashMap();
            if (this.f4093a != null) {
                hashMap.put("sdkVer", this.f4093a);
            }
            hashMap.put("thirdTokenType", this.f4094b);
            hashMap.put("token", this.f4095c);
            hashMap.put("appkey", Config.m3894b(NotifManager.mContext));
            hashMap.put("utdid", AdapterUtilityImpl.getDeviceId(NotifManager.mContext));
            if (this.f4096d != null) {
                hashMap.put("vendorSdkVersion", this.f4096d);
            }
            ALog.m3725d("NotifManager", "report,utdid=" + AdapterUtilityImpl.getDeviceId(NotifManager.mContext) + ",regId=" + this.f4095c + ",type=" + this.f4094b + " sdkVer=" + this.f4093a + " thirdVer=" + this.f4096d, new Object[0]);
            ACCSManager.AccsRequest accsRequest = new ACCSManager.AccsRequest((String) null, "agooTokenReport", new JSONObject(hashMap).toString().getBytes("UTF-8"), (String) null, (String) null, (URL) null, (String) null);
            IACCSManager accsInstance = ACCSManager.getAccsInstance(NotifManager.mContext, Config.m3894b(NotifManager.mContext), Config.m3896c(NotifManager.mContext));
            if (this.f4097e) {
                str = accsInstance.sendData(NotifManager.mContext, accsRequest);
            } else {
                str = accsInstance.sendPushResponse(NotifManager.mContext, accsRequest, new TaoBaseService.ExtraInfo());
            }
            if (ALog.isPrintLog(ALog.Level.D)) {
                ALog.m3728i("NotifManager", "reportThirdPushToken,dataId=" + str + ",regId=" + this.f4095c + ",type=" + this.f4094b, new Object[0]);
            }
        } catch (Throwable th) {
            UTMini.getInstance().commitEvent(AgooConstants.AGOO_EVENT_ID, "reportThirdPushToken", AdapterUtilityImpl.getDeviceId(NotifManager.mContext), th.toString());
            if (ALog.isPrintLog(ALog.Level.E)) {
                ALog.m3726e("NotifManager", "[report] is error", th, new Object[0]);
            }
        }
    }
}
