package com.taobao.accs.client;

import android.content.Context;
import com.taobao.accs.utl.ALog;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* renamed from: com.taobao.accs.client.c */
/* compiled from: Taobao */
public class C2021c {

    /* renamed from: a */
    private Context f3235a;

    /* renamed from: b */
    private ConcurrentMap<String, Integer> f3236b = new ConcurrentHashMap();

    /* renamed from: c */
    private String f3237c = "ClientManager_";

    public C2021c(Context context, String str, String str2, String str3) {
        if (context != null) {
            this.f3237c += str;
            this.f3235a = context.getApplicationContext();
            return;
        }
        throw new RuntimeException("Context is null!!");
    }

    /* renamed from: a */
    public void mo18378a(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        if (num == null || num.intValue() != 2) {
            this.f3236b.put(str, 2);
        }
    }

    /* renamed from: b */
    public void mo18379b(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        if (num == null || num.intValue() != 4) {
            this.f3236b.put(str, 4);
        }
    }

    /* renamed from: c */
    public void mo18380c(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        if (num == null || num.intValue() != 1) {
            this.f3236b.put(str, 1);
        }
    }

    /* renamed from: d */
    public boolean mo18381d(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        ALog.m3728i(this.f3237c, "isAppBinded", "appStatus", num, "mBindStatus", this.f3236b);
        if (num == null || num.intValue() != 2) {
            return false;
        }
        return true;
    }

    /* renamed from: e */
    public boolean mo18382e(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        return num != null && num.intValue() == 4;
    }

    /* renamed from: f */
    public boolean mo18383f(String str) {
        Integer num = (Integer) this.f3236b.get(str);
        if (num == null || num.intValue() != 1) {
            return false;
        }
        return true;
    }

    /* renamed from: a */
    public void mo18377a() {
        try {
            this.f3236b.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
