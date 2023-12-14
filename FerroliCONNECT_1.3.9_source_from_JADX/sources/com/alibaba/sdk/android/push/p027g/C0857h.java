package com.alibaba.sdk.android.push.p027g;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.util.C0683c;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.common.util.p021a.C0810b;
import com.alibaba.sdk.android.push.common.util.p021a.C0811c;
import com.alibaba.sdk.android.push.p018b.C0799f;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.g.h */
public class C0857h extends C0811c {

    /* renamed from: c */
    private static AmsLogger f1260c = AmsLogger.getLogger("MPS:VipRequestTask");

    /* renamed from: d */
    private CommonCallback f1261d;

    public C0857h(Context context, String str, CommonCallback commonCallback) {
        super(context, str);
        this.f1261d = commonCallback;
    }

    /* renamed from: a */
    private void m897a(int i, C0810b bVar, CommonCallback commonCallback) {
        String code;
        ErrorCode a;
        if (commonCallback != null) {
            AmsLogger amsLogger = f1260c;
            amsLogger.mo9538d("requestType: " + i + ", errorCode:" + bVar.f1121c + ", httpcode: " + bVar.f1120b + ", content:" + bVar.f1119a);
            if (bVar.f1121c.getCode().equals(C0804d.f1091a.getCode())) {
                try {
                    String a2 = C0858i.m900a(i, bVar.f1120b, bVar.f1119a);
                    if (commonCallback != null) {
                        commonCallback.onSuccess(a2);
                    }
                } catch (C0799f e) {
                    f1260c.mo9542e("Vip call failed", e);
                    if (commonCallback != null) {
                        code = e.mo9878a().getCode();
                        a = e.mo9878a();
                        commonCallback.onFailed(code, a.getMsg());
                    }
                } catch (Throwable th) {
                    f1260c.mo9542e("Vip call faled.", th);
                    if (commonCallback != null) {
                        a = C0804d.f1101k.copy().msg(th.getMessage()).detail(Log.getStackTraceString(th)).build();
                        code = a.getCode();
                        commonCallback.onFailed(code, a.getMsg());
                    }
                }
            } else if (commonCallback != null) {
                commonCallback.onFailed(bVar.f1121c.getCode(), bVar.f1121c.getMsg());
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Map<String, String> mo9890a(Context context, Map<String, String> map) {
        return C0683c.m536a(map);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void onPostExecute(C0810b bVar) {
        super.onPostExecute(bVar);
        m897a(mo9887a(), bVar, this.f1261d);
    }
}
