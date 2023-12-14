package org.android.agoo.control;

import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import org.android.agoo.common.Config;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: org.android.agoo.control.a */
/* compiled from: Taobao */
class C2354a implements Runnable {

    /* renamed from: a */
    final /* synthetic */ byte[] f4066a;

    /* renamed from: b */
    final /* synthetic */ String f4067b;

    /* renamed from: c */
    final /* synthetic */ AgooFactory f4068c;

    C2354a(AgooFactory agooFactory, byte[] bArr, String str) {
        this.f4068c = agooFactory;
        this.f4066a = bArr;
        this.f4067b = str;
    }

    public void run() {
        try {
            String str = new String(this.f4066a, "utf-8");
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            if (length == 1) {
                String str2 = null;
                String str3 = null;
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject != null) {
                        str2 = jSONObject.getString("i");
                        str3 = jSONObject.getString("p");
                    }
                }
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.m3728i("AgooFactory", "saveMsg msgId:" + str2 + ",message=" + str + ",currentPack=" + str3 + ",reportTimes=" + Config.m3899f(AgooFactory.mContext), new Object[0]);
                }
                if (!TextUtils.isEmpty(str3) && TextUtils.equals(str3, AgooFactory.mContext.getPackageName())) {
                    if (TextUtils.isEmpty(this.f4067b)) {
                        this.f4068c.messageService.mo25530a(str2, str, "0");
                    } else {
                        this.f4068c.messageService.mo25530a(str2, str, this.f4067b);
                    }
                }
            }
        } catch (Throwable th) {
            ALog.m3727e("AgooFactory", "saveMsg fail:" + th.toString(), new Object[0]);
        }
    }
}
