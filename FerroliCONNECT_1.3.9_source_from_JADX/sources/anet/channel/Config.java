package anet.channel;

import anet.channel.entity.ENV;
import anet.channel.security.ISecurity;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public final class Config {
    public static final Config DEFAULT_CONFIG = new Builder().setTag("[default]").setAppkey("[default]").setEnv(ENV.ONLINE).build();
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static Map<String, Config> f66a = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public String f67b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public String f68c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public ENV f69d = ENV.ONLINE;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ISecurity f70e;

    protected Config() {
    }

    public static Config getConfigByTag(String str) {
        Config config;
        synchronized (f66a) {
            config = f66a.get(str);
        }
        return config;
    }

    public static Config getConfig(String str, ENV env) {
        synchronized (f66a) {
            for (Config next : f66a.values()) {
                if (next.f69d == env && next.f68c.equals(str)) {
                    return next;
                }
            }
            return null;
        }
    }

    public String getTag() {
        return this.f67b;
    }

    public String getAppkey() {
        return this.f68c;
    }

    public ENV getEnv() {
        return this.f69d;
    }

    public ISecurity getSecurity() {
        return this.f70e;
    }

    public String toString() {
        return this.f67b;
    }

    /* compiled from: Taobao */
    public static class Builder {

        /* renamed from: a */
        private String f71a;

        /* renamed from: b */
        private String f72b;

        /* renamed from: c */
        private ENV f73c = ENV.ONLINE;

        /* renamed from: d */
        private String f74d;

        /* renamed from: e */
        private String f75e;

        public Builder setTag(String str) {
            this.f71a = str;
            return this;
        }

        public Builder setAppkey(String str) {
            this.f72b = str;
            return this;
        }

        public Builder setEnv(ENV env) {
            this.f73c = env;
            return this;
        }

        public Builder setAuthCode(String str) {
            this.f74d = str;
            return this;
        }

        public Builder setAppSecret(String str) {
            this.f75e = str;
            return this;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:16:0x006a, code lost:
            return r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x006c, code lost:
            r0 = new anet.channel.Config();
            anet.channel.Config.m16a(r0, r8.f72b);
            anet.channel.Config.m14a(r0, r8.f73c);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0081, code lost:
            if (android.text.TextUtils.isEmpty(r8.f71a) == false) goto L_0x0095;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0083, code lost:
            anet.channel.Config.m19b(r0, anet.channel.util.StringUtils.concatString(r8.f72b, "$", r8.f73c.toString()));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0095, code lost:
            anet.channel.Config.m19b(r0, r8.f71a);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00a0, code lost:
            if (android.text.TextUtils.isEmpty(r8.f75e) != false) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a2, code lost:
            anet.channel.Config.m15a(r0, anet.channel.security.C0545c.m193a().createNonSecurity(r8.f75e));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x00b0, code lost:
            anet.channel.Config.m15a(r0, anet.channel.security.C0545c.m193a().createSecurity(r8.f74d));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x00bd, code lost:
            r1 = anet.channel.Config.m17a();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c1, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            anet.channel.Config.m17a().put(anet.channel.Config.m20c(r0), r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00cd, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x00ce, code lost:
            return r0;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public anet.channel.Config build() {
            /*
                r8 = this;
                java.lang.String r0 = r8.f72b
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                if (r0 != 0) goto L_0x00d5
                java.util.Map r0 = anet.channel.Config.f66a
                monitor-enter(r0)
                java.util.Map r1 = anet.channel.Config.f66a     // Catch:{ all -> 0x00d2 }
                java.util.Collection r1 = r1.values()     // Catch:{ all -> 0x00d2 }
                java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x00d2 }
            L_0x0019:
                boolean r2 = r1.hasNext()     // Catch:{ all -> 0x00d2 }
                if (r2 == 0) goto L_0x006b
                java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x00d2 }
                anet.channel.Config r2 = (anet.channel.Config) r2     // Catch:{ all -> 0x00d2 }
                anet.channel.entity.ENV r3 = r2.f69d     // Catch:{ all -> 0x00d2 }
                anet.channel.entity.ENV r4 = r8.f73c     // Catch:{ all -> 0x00d2 }
                if (r3 != r4) goto L_0x0019
                java.lang.String r3 = r2.f68c     // Catch:{ all -> 0x00d2 }
                java.lang.String r4 = r8.f72b     // Catch:{ all -> 0x00d2 }
                boolean r3 = r3.equals(r4)     // Catch:{ all -> 0x00d2 }
                if (r3 == 0) goto L_0x0019
                java.lang.String r1 = "awcn.Config"
                java.lang.String r3 = "duplicated config exist!"
                r4 = 0
                r5 = 4
                java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00d2 }
                r6 = 0
                java.lang.String r7 = "appkey"
                r5[r6] = r7     // Catch:{ all -> 0x00d2 }
                r6 = 1
                java.lang.String r7 = r8.f72b     // Catch:{ all -> 0x00d2 }
                r5[r6] = r7     // Catch:{ all -> 0x00d2 }
                r6 = 2
                java.lang.String r7 = "env"
                r5[r6] = r7     // Catch:{ all -> 0x00d2 }
                r6 = 3
                anet.channel.entity.ENV r7 = r8.f73c     // Catch:{ all -> 0x00d2 }
                r5[r6] = r7     // Catch:{ all -> 0x00d2 }
                anet.channel.util.ALog.m330w(r1, r3, r4, r5)     // Catch:{ all -> 0x00d2 }
                java.lang.String r1 = r8.f71a     // Catch:{ all -> 0x00d2 }
                boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x00d2 }
                if (r1 != 0) goto L_0x0069
                java.util.Map r1 = anet.channel.Config.f66a     // Catch:{ all -> 0x00d2 }
                java.lang.String r3 = r8.f71a     // Catch:{ all -> 0x00d2 }
                r1.put(r3, r2)     // Catch:{ all -> 0x00d2 }
            L_0x0069:
                monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
                return r2
            L_0x006b:
                monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
                anet.channel.Config r0 = new anet.channel.Config
                r0.<init>()
                java.lang.String r1 = r8.f72b
                java.lang.String unused = r0.f68c = r1
                anet.channel.entity.ENV r1 = r8.f73c
                anet.channel.entity.ENV unused = r0.f69d = r1
                java.lang.String r1 = r8.f71a
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 == 0) goto L_0x0095
                java.lang.String r1 = r8.f72b
                anet.channel.entity.ENV r2 = r8.f73c
                java.lang.String r2 = r2.toString()
                java.lang.String r3 = "$"
                java.lang.String r1 = anet.channel.util.StringUtils.concatString(r1, r3, r2)
                java.lang.String unused = r0.f67b = r1
                goto L_0x009a
            L_0x0095:
                java.lang.String r1 = r8.f71a
                java.lang.String unused = r0.f67b = r1
            L_0x009a:
                java.lang.String r1 = r8.f75e
                boolean r1 = android.text.TextUtils.isEmpty(r1)
                if (r1 != 0) goto L_0x00b0
                anet.channel.security.ISecurityFactory r1 = anet.channel.security.C0545c.m193a()
                java.lang.String r2 = r8.f75e
                anet.channel.security.ISecurity r1 = r1.createNonSecurity(r2)
                anet.channel.security.ISecurity unused = r0.f70e = r1
                goto L_0x00bd
            L_0x00b0:
                anet.channel.security.ISecurityFactory r1 = anet.channel.security.C0545c.m193a()
                java.lang.String r2 = r8.f74d
                anet.channel.security.ISecurity r1 = r1.createSecurity(r2)
                anet.channel.security.ISecurity unused = r0.f70e = r1
            L_0x00bd:
                java.util.Map r1 = anet.channel.Config.f66a
                monitor-enter(r1)
                java.util.Map r2 = anet.channel.Config.f66a     // Catch:{ all -> 0x00cf }
                java.lang.String r3 = r0.f67b     // Catch:{ all -> 0x00cf }
                r2.put(r3, r0)     // Catch:{ all -> 0x00cf }
                monitor-exit(r1)     // Catch:{ all -> 0x00cf }
                return r0
            L_0x00cf:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x00cf }
                throw r0
            L_0x00d2:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00d2 }
                throw r1
            L_0x00d5:
                java.lang.RuntimeException r0 = new java.lang.RuntimeException
                java.lang.String r1 = "appkey can not be null or empty!"
                r0.<init>(r1)
                goto L_0x00de
            L_0x00dd:
                throw r0
            L_0x00de:
                goto L_0x00dd
            */
            throw new UnsupportedOperationException("Method not decompiled: anet.channel.Config.Builder.build():anet.channel.Config");
        }
    }
}
