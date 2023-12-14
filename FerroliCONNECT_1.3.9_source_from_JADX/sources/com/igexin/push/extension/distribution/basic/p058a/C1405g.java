package com.igexin.push.extension.distribution.basic.p058a;

import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p090b.C1575h;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: com.igexin.push.extension.distribution.basic.a.g */
class C1405g extends C1575h {

    /* renamed from: a */
    final /* synthetic */ Map f2366a;

    /* renamed from: b */
    final /* synthetic */ String f2367b;

    /* renamed from: c */
    final /* synthetic */ C1404f f2368c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1405g(C1404f fVar, long j, Map map, String str) {
        super(j);
        this.f2368c = fVar;
        this.f2366a = map;
        this.f2367b = str;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14406a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(C1343f.f2169f.getPackageName());
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(this.f2368c.m2311c((String) this.f2366a.get("pkgName")));
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append((String) this.f2366a.get("pkgName"));
            sb.append("/");
            sb.append((String) this.f2366a.get("serviceName"));
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(C1404f.m2309b((String) this.f2366a.get("pkgName"), (String) this.f2366a.get("serviceName")) ? "1" : "0");
            this.f2368c.m2307b(this.f2367b, sb.toString(), (String) this.f2366a.get("messageId"), (String) this.f2366a.get("taskId"), (String) this.f2366a.get(AgooConstants.MESSAGE_ID));
            C1179b.m1354a("feedback actionId=" + this.f2367b + " result=" + sb.toString());
        } catch (Exception unused) {
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
