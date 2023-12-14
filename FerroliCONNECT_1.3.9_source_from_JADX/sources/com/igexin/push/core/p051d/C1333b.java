package com.igexin.push.core.p051d;

import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1355r;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.p054e.p057c.C1372a;
import com.igexin.push.p054e.p057c.C1374c;
import com.igexin.push.p087f.C1560a;
import com.igexin.push.p088g.p089a.C1563b;
import com.igexin.push.util.C1594s;
import com.igexin.sdk.PushConsts;
import java.util.Timer;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.d.b */
public class C1333b extends C1563b {

    /* renamed from: a */
    private String f2076a;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public String f2077g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public C1372a f2078h;
    /* access modifiers changed from: private */

    /* renamed from: i */
    public PushTaskBean f2079i;

    public C1333b(String str, C1372a aVar, PushTaskBean pushTaskBean) {
        super(str);
        this.f2077g = str;
        this.f2076a = pushTaskBean.getMessageId();
        this.f2078h = aVar;
        this.f2079i = pushTaskBean;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14694a(PushTaskBean pushTaskBean, C1372a aVar) {
        C1374c cVar = new C1374c();
        cVar.mo14831a();
        cVar.f2265c = "RTV" + pushTaskBean.getMessageId() + "@" + pushTaskBean.getTaskId();
        cVar.f2266d = C1343f.f2182s;
        cVar.f2263a = (int) System.currentTimeMillis();
        C1560a g = C1340e.m2032a().mo14710g();
        g.mo15186a("C-" + C1343f.f2182s, cVar);
        C1179b.m1354a("cdnRetrieve|" + pushTaskBean.getMessageId() + "|" + pushTaskBean.getTaskId());
        if (aVar.mo14828c() < 2) {
            long a = C1594s.m3271a();
            Timer timer = new Timer();
            timer.schedule(new C1335d(this, pushTaskBean, aVar), a);
            C1343f.f2145ag.put(pushTaskBean.getTaskId(), timer);
        }
    }

    /* renamed from: a */
    public void mo14695a(Exception exc) {
        if (this.f2078h.mo14824a() < 2) {
            new Timer().schedule(new C1334c(this), C1594s.m3271a());
            return;
        }
        mo14694a(this.f2079i, this.f2078h);
    }

    /* renamed from: a */
    public void mo14696a(byte[] bArr) {
        if (bArr != null) {
            byte[] d = C1177f.m1342d(C1150a.m1234c(bArr, C1343f.f2166c));
            if (d != null) {
                JSONObject jSONObject = new JSONObject(new String(d, "utf-8"));
                jSONObject.put(AgooConstants.MESSAGE_ID, this.f2076a);
                jSONObject.put("messageid", this.f2076a);
                jSONObject.put("cdnType", true);
                byte[] bArr2 = null;
                try {
                    if ("pushmessage".equals(jSONObject.getString(PushConsts.CMD_ACTION))) {
                        if (jSONObject.has("extraData")) {
                            bArr2 = jSONObject.getString("extraData").getBytes();
                        }
                        C1355r.m2114a().mo14768a(jSONObject, bArr2, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("Get error CDNData, can not UnGzip it...");
            }
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
