package anet.channel.bytes;

import java.util.Random;
import java.util.TreeSet;

/* renamed from: anet.channel.bytes.a */
/* compiled from: Taobao */
public class C0485a {
    public static final int MAX_POOL_SIZE = 524288;
    public static final String TAG = "awcn.ByteArrayPool";

    /* renamed from: a */
    private final TreeSet<ByteArray> f167a = new TreeSet<>();

    /* renamed from: b */
    private final ByteArray f168b = ByteArray.create(0);

    /* renamed from: c */
    private final Random f169c = new Random();

    /* renamed from: d */
    private long f170d = 0;

    /* renamed from: anet.channel.bytes.a$a */
    /* compiled from: Taobao */
    static class C0486a {

        /* renamed from: a */
        public static C0485a f171a = new C0485a();

        C0486a() {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo8743a(anet.channel.bytes.ByteArray r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 == 0) goto L_0x0047
            int r0 = r5.bufferLength     // Catch:{ all -> 0x0044 }
            r1 = 524288(0x80000, float:7.34684E-40)
            if (r0 < r1) goto L_0x000a
            goto L_0x0047
        L_0x000a:
            long r0 = r4.f170d     // Catch:{ all -> 0x0044 }
            int r2 = r5.bufferLength     // Catch:{ all -> 0x0044 }
            long r2 = (long) r2     // Catch:{ all -> 0x0044 }
            long r0 = r0 + r2
            r4.f170d = r0     // Catch:{ all -> 0x0044 }
            java.util.TreeSet<anet.channel.bytes.ByteArray> r0 = r4.f167a     // Catch:{ all -> 0x0044 }
            r0.add(r5)     // Catch:{ all -> 0x0044 }
        L_0x0017:
            long r0 = r4.f170d     // Catch:{ all -> 0x0044 }
            r2 = 524288(0x80000, double:2.590327E-318)
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 <= 0) goto L_0x0042
            java.util.Random r5 = r4.f169c     // Catch:{ all -> 0x0044 }
            boolean r5 = r5.nextBoolean()     // Catch:{ all -> 0x0044 }
            if (r5 == 0) goto L_0x0031
            java.util.TreeSet<anet.channel.bytes.ByteArray> r5 = r4.f167a     // Catch:{ all -> 0x0044 }
            java.lang.Object r5 = r5.pollFirst()     // Catch:{ all -> 0x0044 }
            anet.channel.bytes.ByteArray r5 = (anet.channel.bytes.ByteArray) r5     // Catch:{ all -> 0x0044 }
            goto L_0x0039
        L_0x0031:
            java.util.TreeSet<anet.channel.bytes.ByteArray> r5 = r4.f167a     // Catch:{ all -> 0x0044 }
            java.lang.Object r5 = r5.pollLast()     // Catch:{ all -> 0x0044 }
            anet.channel.bytes.ByteArray r5 = (anet.channel.bytes.ByteArray) r5     // Catch:{ all -> 0x0044 }
        L_0x0039:
            long r0 = r4.f170d     // Catch:{ all -> 0x0044 }
            int r5 = r5.bufferLength     // Catch:{ all -> 0x0044 }
            long r2 = (long) r5     // Catch:{ all -> 0x0044 }
            long r0 = r0 - r2
            r4.f170d = r0     // Catch:{ all -> 0x0044 }
            goto L_0x0017
        L_0x0042:
            monitor-exit(r4)
            return
        L_0x0044:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        L_0x0047:
            monitor-exit(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.bytes.C0485a.mo8743a(anet.channel.bytes.ByteArray):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0036, code lost:
        return r0;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized anet.channel.bytes.ByteArray mo8741a(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 524288(0x80000, float:7.34684E-40)
            if (r6 < r0) goto L_0x000b
            anet.channel.bytes.ByteArray r6 = anet.channel.bytes.ByteArray.create(r6)     // Catch:{ all -> 0x0037 }
            monitor-exit(r5)
            return r6
        L_0x000b:
            anet.channel.bytes.ByteArray r0 = r5.f168b     // Catch:{ all -> 0x0037 }
            r0.bufferLength = r6     // Catch:{ all -> 0x0037 }
            java.util.TreeSet<anet.channel.bytes.ByteArray> r0 = r5.f167a     // Catch:{ all -> 0x0037 }
            anet.channel.bytes.ByteArray r1 = r5.f168b     // Catch:{ all -> 0x0037 }
            java.lang.Object r0 = r0.ceiling(r1)     // Catch:{ all -> 0x0037 }
            anet.channel.bytes.ByteArray r0 = (anet.channel.bytes.ByteArray) r0     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x0020
            anet.channel.bytes.ByteArray r0 = anet.channel.bytes.ByteArray.create(r6)     // Catch:{ all -> 0x0037 }
            goto L_0x0035
        L_0x0020:
            byte[] r6 = r0.buffer     // Catch:{ all -> 0x0037 }
            r1 = 0
            java.util.Arrays.fill(r6, r1)     // Catch:{ all -> 0x0037 }
            r0.dataLength = r1     // Catch:{ all -> 0x0037 }
            java.util.TreeSet<anet.channel.bytes.ByteArray> r6 = r5.f167a     // Catch:{ all -> 0x0037 }
            r6.remove(r0)     // Catch:{ all -> 0x0037 }
            long r1 = r5.f170d     // Catch:{ all -> 0x0037 }
            int r6 = r0.bufferLength     // Catch:{ all -> 0x0037 }
            long r3 = (long) r6     // Catch:{ all -> 0x0037 }
            long r1 = r1 - r3
            r5.f170d = r1     // Catch:{ all -> 0x0037 }
        L_0x0035:
            monitor-exit(r5)
            return r0
        L_0x0037:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.bytes.C0485a.mo8741a(int):anet.channel.bytes.ByteArray");
    }

    /* renamed from: a */
    public ByteArray mo8742a(byte[] bArr, int i) {
        ByteArray a = mo8741a(i);
        System.arraycopy(bArr, 0, a.buffer, 0, i);
        a.dataLength = i;
        return a;
    }
}
