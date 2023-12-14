package com.igexin.push.core.p047a.p048a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p090b.C1575h;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: com.igexin.push.core.a.a.m */
class C1252m extends C1575h {

    /* renamed from: a */
    final /* synthetic */ Map f1878a;

    /* renamed from: b */
    final /* synthetic */ C1251l f1879b;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1252m(C1251l lVar, long j, Map map) {
        super(j);
        this.f1879b = lVar;
        this.f1878a = map;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(C1343f.f2169f.getPackageName());
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(this.f1879b.m1697b((String) this.f1878a.get("pkgName")));
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append((String) this.f1878a.get("pkgName"));
            sb.append("/");
            sb.append((String) this.f1878a.get("serviceName"));
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(C1251l.m1696a((String) this.f1878a.get("pkgName"), (String) this.f1878a.get("serviceName")) ? "1" : "0");
            this.f1879b.m1698b("30026", sb.toString(), (String) this.f1878a.get("messageId"), (String) this.f1878a.get("taskId"), (String) this.f1878a.get(AgooConstants.MESSAGE_ID));
            C1179b.m1354a("feedback actionId=30026 result=" + sb.toString());
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
