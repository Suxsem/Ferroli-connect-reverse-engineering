package com.p092ta.p093a.p096c;

import com.p092ta.p093a.p095b.C1971e;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/* renamed from: com.ta.a.c.c */
public class C1977c {

    /* renamed from: a */
    private static File f3161a;

    /* renamed from: a */
    private static FileChannel f3162a;

    /* renamed from: a */
    private static FileLock f3163a;

    /* renamed from: b */
    private static File f3164b;

    /* renamed from: c */
    public static synchronized void m3358c() {
        synchronized (C1977c.class) {
            C1982f.m3372e();
            if (f3161a == null) {
                f3161a = new File(C1971e.m3343c());
            }
            if (!f3161a.exists()) {
                try {
                    f3161a.createNewFile();
                } catch (Exception unused) {
                    return;
                }
            }
            if (f3162a == null) {
                try {
                    f3162a = new RandomAccessFile(f3161a, "rw").getChannel();
                } catch (Exception unused2) {
                    return;
                }
            }
            try {
                f3163a = f3162a.lock();
            } catch (Throwable unused3) {
            }
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0020 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0010 */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m3359d() {
        /*
            java.lang.Class<com.ta.a.c.c> r0 = com.p092ta.p093a.p096c.C1977c.class
            monitor-enter(r0)
            com.p092ta.p093a.p096c.C1982f.m3372e()     // Catch:{ all -> 0x0029 }
            java.nio.channels.FileLock r1 = f3163a     // Catch:{ all -> 0x0029 }
            r2 = 0
            if (r1 == 0) goto L_0x0017
            java.nio.channels.FileLock r1 = f3163a     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
            r1.release()     // Catch:{ Exception -> 0x0010, all -> 0x0013 }
        L_0x0010:
            f3163a = r2     // Catch:{ all -> 0x0029 }
            goto L_0x0017
        L_0x0013:
            r1 = move-exception
            f3163a = r2     // Catch:{ all -> 0x0029 }
            throw r1     // Catch:{ all -> 0x0029 }
        L_0x0017:
            java.nio.channels.FileChannel r1 = f3162a     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x0027
            java.nio.channels.FileChannel r1 = f3162a     // Catch:{ Exception -> 0x0020, all -> 0x0023 }
            r1.close()     // Catch:{ Exception -> 0x0020, all -> 0x0023 }
        L_0x0020:
            f3162a = r2     // Catch:{ all -> 0x0029 }
            goto L_0x0027
        L_0x0023:
            r1 = move-exception
            f3162a = r2     // Catch:{ all -> 0x0029 }
            throw r1     // Catch:{ all -> 0x0029 }
        L_0x0027:
            monitor-exit(r0)
            return
        L_0x0029:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.p092ta.p093a.p096c.C1977c.m3359d():void");
    }
}
