package anet.channel.strategy;

import java.util.concurrent.ConcurrentHashMap;

/* renamed from: anet.channel.strategy.c */
/* compiled from: Taobao */
public class C0566c {

    /* renamed from: a */
    private final ConcurrentHashMap<String, String> f480a = new ConcurrentHashMap<>();

    /* renamed from: b */
    private boolean f481b = true;

    /* renamed from: anet.channel.strategy.c$a */
    /* compiled from: Taobao */
    private static class C0567a {

        /* renamed from: a */
        public static C0566c f482a = new C0566c();

        private C0567a() {
        }
    }

    /* renamed from: a */
    public void mo9041a(boolean z) {
        this.f481b = z;
    }

    /* renamed from: a */
    public String mo9040a(String str) {
        if (!this.f481b) {
            return null;
        }
        String str2 = this.f480a.get(str);
        if (str2 != null) {
            return str2;
        }
        this.f480a.put(str, "https");
        return "https";
    }

    /* renamed from: b */
    public void mo9042b(String str) {
        this.f480a.put(str, "http");
    }
}
