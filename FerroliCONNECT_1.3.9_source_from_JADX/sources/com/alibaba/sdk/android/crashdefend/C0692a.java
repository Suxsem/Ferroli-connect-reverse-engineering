package com.alibaba.sdk.android.crashdefend;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.sdk.android.crashdefend.p012a.C0694a;
import com.alibaba.sdk.android.crashdefend.p012a.C0695b;
import com.alibaba.sdk.android.crashdefend.p013b.C0696a;
import com.alibaba.sdk.android.crashdefend.p014c.C0698a;
import com.alibaba.sdk.android.crashdefend.p014c.C0699b;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/* renamed from: com.alibaba.sdk.android.crashdefend.a */
public class C0692a {

    /* renamed from: a */
    private static volatile C0692a f851a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Context f852b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final C0694a f853c = new C0694a();

    /* renamed from: d */
    private C0695b f854d;

    /* renamed from: e */
    private final ExecutorService f855e;

    /* renamed from: f */
    private final Map<String, String> f856f = new HashMap();

    /* renamed from: g */
    private final int[] f857g = new int[5];
    /* access modifiers changed from: private */

    /* renamed from: h */
    public final List<C0695b> f858h = new ArrayList();

    /* renamed from: com.alibaba.sdk.android.crashdefend.a$a */
    private class C0693a implements Runnable {

        /* renamed from: b */
        private C0695b f860b;

        /* renamed from: c */
        private int f861c;

        C0693a(C0695b bVar, int i) {
            this.f860b = bVar;
            this.f861c = i;
        }

        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    this.f861c--;
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e) {
                    Log.d("CrashDefend", e.getMessage(), e);
                    return;
                }
            } while (this.f861c > 0);
            if (this.f861c <= 0) {
                C0692a.this.m591c(this.f860b);
                C0698a.m596a(C0692a.this.f852b, C0692a.this.f853c, C0692a.this.f858h);
            }
        }
    }

    private C0692a(Context context) {
        this.f852b = context.getApplicationContext();
        this.f855e = new C0696a().mo9593a();
        for (int i = 0; i < 5; i++) {
            this.f857g[i] = (i * 5) + 5;
        }
        this.f856f.put("sdkId", "crashdefend");
        this.f856f.put(Constants.KEY_SDK_VERSION, "0.0.6");
        try {
            m582a();
            m588b();
        } catch (Exception e) {
            Log.d("CrashDefend", e.getMessage(), e);
        }
    }

    /* renamed from: a */
    public static C0692a m581a(Context context) {
        if (f851a == null) {
            synchronized (C0692a.class) {
                if (f851a == null) {
                    f851a = new C0692a(context);
                }
            }
        }
        return f851a;
    }

    /* renamed from: a */
    private void m582a() {
        if (C0698a.m599b(this.f852b, this.f853c, this.f858h)) {
            this.f853c.f862a++;
            return;
        }
        this.f853c.f862a = 1;
    }

    /* renamed from: a */
    private boolean m584a(C0695b bVar) {
        if (bVar.f866d >= bVar.f865c) {
            C0695b bVar2 = this.f854d;
            if (bVar2 == null || !bVar2.f863a.equals(bVar.f863a)) {
                return false;
            }
            bVar.f866d = bVar.f865c - 1;
        }
        bVar.f869g = bVar.f868f;
        return true;
    }

    /* renamed from: a */
    private boolean m585a(C0695b bVar, CrashDefendCallback crashDefendCallback) {
        String str;
        C0695b bVar2 = bVar;
        CrashDefendCallback crashDefendCallback2 = crashDefendCallback;
        if (!(bVar2 == null || crashDefendCallback2 == null)) {
            try {
                if (!TextUtils.isEmpty(bVar2.f864b)) {
                    if (!TextUtils.isEmpty(bVar2.f863a)) {
                        C0695b b = m587b(bVar, crashDefendCallback);
                        if (b == null) {
                            return false;
                        }
                        boolean a = m584a(b);
                        b.f866d++;
                        C0698a.m596a(this.f852b, this.f853c, this.f858h);
                        if (a) {
                            m589b(b);
                            str = "START:" + b.f863a + " --- limit:" + b.f865c + "  count:" + (b.f866d - 1) + "  restore:" + b.f870h + "  startSerialNumber:" + b.f869g + "  registerSerialNumber:" + b.f868f;
                        } else if (b.f870h >= 5) {
                            crashDefendCallback2.onSdkClosed(b.f870h);
                            str = "CLOSED: " + b.f863a + " --- restored " + b.f870h + ", has more than retry limit, so closed it";
                        } else {
                            crashDefendCallback.onSdkStop(b.f865c, b.f866d - 1, b.f870h, b.f871i);
                            str = "STOP:" + b.f863a + " --- limit:" + b.f865c + "  count:" + (b.f866d - 1) + "  restore:" + b.f870h + "  startSerialNumber:" + b.f869g + "  registerSerialNumber:" + b.f868f;
                        }
                        C0699b.m604b("CrashDefend", str);
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                Log.d("CrashDefend", e.getMessage(), e);
            }
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.alibaba.sdk.android.crashdefend.a.b} */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        if (r4.f864b.equals(r8.f864b) != false) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0034, code lost:
        r4.f864b = r8.f864b;
        r4.f865c = r8.f865c;
        r4.f867e = r8.f867e;
        r4.f866d = 0;
        r4.f870h = 0;
        r4.f871i = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004a, code lost:
        if (r4.f872j == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        com.alibaba.sdk.android.crashdefend.p014c.C0699b.m604b("CrashDefend", "SDK " + r8.f863a + " has been registered");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x006a, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r4.f872j = true;
        r4.f873k = r9;
        r4.f868f = r7.f853c.f862a;
        r2 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0091, code lost:
        return r2;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized com.alibaba.sdk.android.crashdefend.p012a.C0695b m587b(com.alibaba.sdk.android.crashdefend.p012a.C0695b r8, com.alibaba.sdk.android.crashdefend.CrashDefendCallback r9) {
        /*
            r7 = this;
            monitor-enter(r7)
            java.util.List<com.alibaba.sdk.android.crashdefend.a.b> r0 = r7.f858h     // Catch:{ all -> 0x0092 }
            int r0 = r0.size()     // Catch:{ all -> 0x0092 }
            r1 = 1
            r2 = 0
            r3 = 0
            if (r0 <= 0) goto L_0x0076
            java.util.List<com.alibaba.sdk.android.crashdefend.a.b> r0 = r7.f858h     // Catch:{ all -> 0x0092 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0092 }
        L_0x0012:
            boolean r4 = r0.hasNext()     // Catch:{ all -> 0x0092 }
            if (r4 == 0) goto L_0x0076
            java.lang.Object r4 = r0.next()     // Catch:{ all -> 0x0092 }
            com.alibaba.sdk.android.crashdefend.a.b r4 = (com.alibaba.sdk.android.crashdefend.p012a.C0695b) r4     // Catch:{ all -> 0x0092 }
            if (r4 == 0) goto L_0x0012
            java.lang.String r5 = r4.f863a     // Catch:{ all -> 0x0092 }
            java.lang.String r6 = r8.f863a     // Catch:{ all -> 0x0092 }
            boolean r5 = r5.equals(r6)     // Catch:{ all -> 0x0092 }
            if (r5 == 0) goto L_0x0012
            java.lang.String r0 = r4.f864b     // Catch:{ all -> 0x0092 }
            java.lang.String r5 = r8.f864b     // Catch:{ all -> 0x0092 }
            boolean r0 = r0.equals(r5)     // Catch:{ all -> 0x0092 }
            if (r0 != 0) goto L_0x0048
            java.lang.String r0 = r8.f864b     // Catch:{ all -> 0x0092 }
            r4.f864b = r0     // Catch:{ all -> 0x0092 }
            int r0 = r8.f865c     // Catch:{ all -> 0x0092 }
            r4.f865c = r0     // Catch:{ all -> 0x0092 }
            int r0 = r8.f867e     // Catch:{ all -> 0x0092 }
            r4.f867e = r0     // Catch:{ all -> 0x0092 }
            r4.f866d = r3     // Catch:{ all -> 0x0092 }
            r4.f870h = r3     // Catch:{ all -> 0x0092 }
            r5 = 0
            r4.f871i = r5     // Catch:{ all -> 0x0092 }
        L_0x0048:
            boolean r0 = r4.f872j     // Catch:{ all -> 0x0092 }
            if (r0 == 0) goto L_0x006b
            java.lang.String r9 = "CrashDefend"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0092 }
            r0.<init>()     // Catch:{ all -> 0x0092 }
            java.lang.String r1 = "SDK "
            r0.append(r1)     // Catch:{ all -> 0x0092 }
            java.lang.String r8 = r8.f863a     // Catch:{ all -> 0x0092 }
            r0.append(r8)     // Catch:{ all -> 0x0092 }
            java.lang.String r8 = " has been registered"
            r0.append(r8)     // Catch:{ all -> 0x0092 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x0092 }
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m604b(r9, r8)     // Catch:{ all -> 0x0092 }
            monitor-exit(r7)
            return r2
        L_0x006b:
            r4.f872j = r1     // Catch:{ all -> 0x0092 }
            r4.f873k = r9     // Catch:{ all -> 0x0092 }
            com.alibaba.sdk.android.crashdefend.a.a r0 = r7.f853c     // Catch:{ all -> 0x0092 }
            long r5 = r0.f862a     // Catch:{ all -> 0x0092 }
            r4.f868f = r5     // Catch:{ all -> 0x0092 }
            r2 = r4
        L_0x0076:
            if (r2 != 0) goto L_0x0090
            java.lang.Object r8 = r8.clone()     // Catch:{ all -> 0x0092 }
            r2 = r8
            com.alibaba.sdk.android.crashdefend.a.b r2 = (com.alibaba.sdk.android.crashdefend.p012a.C0695b) r2     // Catch:{ all -> 0x0092 }
            r2.f872j = r1     // Catch:{ all -> 0x0092 }
            r2.f873k = r9     // Catch:{ all -> 0x0092 }
            r2.f866d = r3     // Catch:{ all -> 0x0092 }
            com.alibaba.sdk.android.crashdefend.a.a r8 = r7.f853c     // Catch:{ all -> 0x0092 }
            long r8 = r8.f862a     // Catch:{ all -> 0x0092 }
            r2.f868f = r8     // Catch:{ all -> 0x0092 }
            java.util.List<com.alibaba.sdk.android.crashdefend.a.b> r8 = r7.f858h     // Catch:{ all -> 0x0092 }
            r8.add(r2)     // Catch:{ all -> 0x0092 }
        L_0x0090:
            monitor-exit(r7)
            return r2
        L_0x0092:
            r8 = move-exception
            monitor-exit(r7)
            goto L_0x0096
        L_0x0095:
            throw r8
        L_0x0096:
            goto L_0x0095
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.crashdefend.C0692a.m587b(com.alibaba.sdk.android.crashdefend.a.b, com.alibaba.sdk.android.crashdefend.CrashDefendCallback):com.alibaba.sdk.android.crashdefend.a.b");
    }

    /* renamed from: b */
    private void m588b() {
        String str;
        String str2;
        this.f854d = null;
        ArrayList arrayList = new ArrayList();
        synchronized (this.f858h) {
            for (C0695b next : this.f858h) {
                if (next.f866d >= next.f865c) {
                    arrayList.add(next);
                }
            }
            Iterator it = arrayList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                C0695b bVar = (C0695b) it.next();
                if (bVar.f870h < 5) {
                    long j = this.f853c.f862a - ((long) this.f857g[bVar.f870h]);
                    long j2 = (bVar.f869g - j) + 1;
                    C0699b.m602a("CrashDefend", "after restart " + j2 + " times, sdk will be restore");
                    bVar.f871i = j2;
                    if (bVar.f869g < j) {
                        this.f854d = bVar;
                        break;
                    }
                } else {
                    C0699b.m604b("CrashDefend", "SDK " + bVar.f863a + " has been closed");
                }
            }
            if (this.f854d == null) {
                str = "CrashDefend";
                str2 = "NO SDK restore";
            } else {
                this.f854d.f870h++;
                str = "CrashDefend";
                str2 = this.f854d.f863a + " will restore --- startSerialNumber:" + this.f854d.f869g + "   crashCount:" + this.f854d.f866d;
            }
            C0699b.m604b(str, str2);
        }
    }

    /* renamed from: b */
    private void m589b(C0695b bVar) {
        if (bVar != null) {
            m592d(bVar);
            if (bVar.f873k != null) {
                bVar.f873k.onSdkStart(bVar.f865c, bVar.f866d - 1, bVar.f870h);
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m591c(C0695b bVar) {
        if (bVar != null) {
            bVar.f866d = 0;
            bVar.f870h = 0;
        }
    }

    /* renamed from: d */
    private void m592d(C0695b bVar) {
        if (bVar != null) {
            this.f855e.execute(new C0693a(bVar, bVar.f867e));
        }
    }

    /* renamed from: a */
    public boolean mo9590a(String str, String str2, int i, int i2, CrashDefendCallback crashDefendCallback) {
        C0695b bVar = new C0695b();
        bVar.f863a = str;
        bVar.f864b = str2;
        bVar.f865c = i;
        bVar.f867e = i2;
        return m585a(bVar, crashDefendCallback);
    }
}
