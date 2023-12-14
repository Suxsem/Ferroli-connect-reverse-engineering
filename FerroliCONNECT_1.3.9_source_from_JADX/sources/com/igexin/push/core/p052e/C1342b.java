package com.igexin.push.core.p052e;

import android.content.Intent;
import com.igexin.push.core.C1343f;
import com.igexin.sdk.PushActivity;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* renamed from: com.igexin.push.core.e.b */
public class C1342b {

    /* renamed from: b */
    private static C1342b f2107b;

    /* renamed from: a */
    private Map<Long, C1341a> f2108a = new HashMap();

    private C1342b() {
    }

    /* renamed from: a */
    public static C1342b m2068a() {
        if (f2107b == null) {
            f2107b = new C1342b();
        }
        return f2107b;
    }

    /* renamed from: d */
    private void m2069d(C1341a aVar) {
        if (aVar != null) {
            this.f2108a.put(aVar.mo14714a(), aVar);
        }
    }

    /* renamed from: a */
    public C1341a mo14730a(Long l) {
        return this.f2108a.get(l);
    }

    /* renamed from: a */
    public void mo14731a(C1341a aVar) {
        if (aVar != null) {
            m2069d(aVar);
            Intent intent = new Intent(C1343f.f2169f, PushActivity.class);
            intent.putExtra("activityid", aVar.mo14714a());
            intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
            C1343f.f2169f.startActivity(intent);
        }
    }

    /* renamed from: b */
    public void mo14732b(C1341a aVar) {
        if (aVar != null) {
            aVar.mo14729i();
            mo14733c(aVar);
        }
    }

    /* renamed from: c */
    public void mo14733c(C1341a aVar) {
        if (aVar != null) {
            this.f2108a.remove(aVar.mo14714a());
        }
    }
}
