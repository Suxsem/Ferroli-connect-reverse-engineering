package anet.channel;

import android.text.TextUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: anet.channel.c */
/* compiled from: Taobao */
class C0487c {

    /* renamed from: a */
    Map<String, Integer> f172a = new HashMap();

    /* renamed from: b */
    Map<String, SessionInfo> f173b = new ConcurrentHashMap();

    C0487c() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8746a(SessionInfo sessionInfo) {
        if (sessionInfo == null) {
            throw new NullPointerException("info is null");
        } else if (!TextUtils.isEmpty(sessionInfo.host)) {
            this.f173b.put(sessionInfo.host, sessionInfo);
        } else {
            throw new IllegalArgumentException("host cannot be null or empty");
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public SessionInfo mo8744a(String str) {
        return this.f173b.remove(str);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public SessionInfo mo8748b(String str) {
        return this.f173b.get(str);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public Collection<SessionInfo> mo8745a() {
        return this.f173b.values();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8747a(String str, int i) {
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f172a) {
                this.f172a.put(str, Integer.valueOf(i));
            }
            return;
        }
        throw new IllegalArgumentException("host cannot be null or empty");
    }

    /* renamed from: c */
    public int mo8749c(String str) {
        Integer num;
        synchronized (this.f172a) {
            num = this.f172a.get(str);
        }
        if (num == null) {
            return -1;
        }
        return num.intValue();
    }
}
