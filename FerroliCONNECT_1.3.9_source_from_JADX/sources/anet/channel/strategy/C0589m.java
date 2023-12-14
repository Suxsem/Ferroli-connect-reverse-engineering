package anet.channel.strategy;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.StrategyStatObject;
import anet.channel.util.ALog;
import anet.channel.util.SerializeHelper;
import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/* renamed from: anet.channel.strategy.m */
/* compiled from: Taobao */
class C0589m {

    /* renamed from: a */
    private static File f548a = null;

    /* renamed from: b */
    private static volatile boolean f549b = false;

    /* renamed from: c */
    private static Comparator<File> f550c = new C0590n();

    C0589m() {
    }

    /* renamed from: a */
    public static void m306a(Context context) {
        if (context != null) {
            try {
                f548a = new File(context.getFilesDir(), "awcn_strategy");
                if (!m308a(f548a)) {
                    ALog.m327e("awcn.StrategySerializeHelper", "create directory failed!!!", (String) null, "dir", f548a.getAbsolutePath());
                }
                if (!GlobalAppRuntimeInfo.isTargetProcess()) {
                    String currentProcess = GlobalAppRuntimeInfo.getCurrentProcess();
                    f548a = new File(f548a, currentProcess.substring(currentProcess.indexOf(58) + 1));
                    if (!m308a(f548a)) {
                        ALog.m327e("awcn.StrategySerializeHelper", "create directory failed!!!", (String) null, "dir", f548a.getAbsolutePath());
                    }
                }
                ALog.m328i("awcn.StrategySerializeHelper", "StrateyFolder", (String) null, "path", f548a.getAbsolutePath());
                if (f549b) {
                    m305a();
                    f549b = false;
                    return;
                }
                m310c();
            } catch (Throwable th) {
                ALog.m326e("awcn.StrategySerializeHelper", "StrategySerializeHelper initialize failed!!!", (String) null, th, new Object[0]);
            }
        }
    }

    /* renamed from: a */
    private static boolean m308a(File file) {
        if (file == null || file.exists()) {
            return true;
        }
        return file.mkdir();
    }

    /* renamed from: a */
    public static File m303a(String str) {
        m308a(f548a);
        return new File(f548a, str);
    }

    /* renamed from: a */
    static synchronized void m305a() {
        synchronized (C0589m.class) {
            ALog.m328i("awcn.StrategySerializeHelper", "clear start.", (String) null, new Object[0]);
            if (f548a == null) {
                ALog.m330w("awcn.StrategySerializeHelper", "folder path not initialized, wait to clear", (String) null, new Object[0]);
                f549b = true;
                return;
            }
            File[] listFiles = f548a.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
                ALog.m328i("awcn.StrategySerializeHelper", "clear end.", (String) null, new Object[0]);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0018, code lost:
        return r1;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static synchronized java.io.File[] m309b() {
        /*
            java.lang.Class<anet.channel.strategy.m> r0 = anet.channel.strategy.C0589m.class
            monitor-enter(r0)
            java.io.File r1 = f548a     // Catch:{ all -> 0x0019 }
            if (r1 != 0) goto L_0x000a
            r1 = 0
            monitor-exit(r0)
            return r1
        L_0x000a:
            java.io.File r1 = f548a     // Catch:{ all -> 0x0019 }
            java.io.File[] r1 = r1.listFiles()     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x0017
            java.util.Comparator<java.io.File> r2 = f550c     // Catch:{ all -> 0x0019 }
            java.util.Arrays.sort(r1, r2)     // Catch:{ all -> 0x0019 }
        L_0x0017:
            monitor-exit(r0)
            return r1
        L_0x0019:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.C0589m.m309b():java.io.File[]");
    }

    /* renamed from: c */
    static synchronized void m310c() {
        synchronized (C0589m.class) {
            File[] b = m309b();
            if (b != null) {
                int i = 0;
                for (File file : b) {
                    if (!file.isDirectory()) {
                        if (System.currentTimeMillis() - file.lastModified() > 172800000) {
                            file.delete();
                        } else if (file.getName().startsWith("WIFI")) {
                            int i2 = i + 1;
                            if (((long) i) > 10) {
                                file.delete();
                            }
                            i = i2;
                        }
                    }
                }
            }
        }
    }

    /* renamed from: a */
    static synchronized void m307a(Serializable serializable, String str, StrategyStatObject strategyStatObject) {
        synchronized (C0589m.class) {
            SerializeHelper.persist(serializable, m303a(str), strategyStatObject);
        }
    }

    /* renamed from: a */
    static synchronized <T> T m304a(String str, StrategyStatObject strategyStatObject) {
        T restore;
        synchronized (C0589m.class) {
            restore = SerializeHelper.restore(m303a(str), strategyStatObject);
        }
        return restore;
    }
}
