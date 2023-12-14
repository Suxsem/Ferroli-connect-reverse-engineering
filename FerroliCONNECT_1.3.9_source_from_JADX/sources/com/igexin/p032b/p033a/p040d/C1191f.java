package com.igexin.p032b.p033a.p040d;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.support.p000v4.app.NotificationCompat;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1161f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.p041a.C1182b;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.push.p054e.p056b.C1369a;
import com.igexin.push.p054e.p056b.C1370b;
import com.igexin.push.p054e.p057c.C1386o;
import com.igexin.push.p054e.p057c.C1387p;
import com.igexin.push.util.C1589n;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* renamed from: com.igexin.b.a.d.f */
public class C1191f extends BroadcastReceiver implements Comparator<C1190e> {

    /* renamed from: g */
    public static final String f1661g = "com.igexin.b.a.d.f";

    /* renamed from: u */
    public static final long f1662u = TimeUnit.SECONDS.toMillis(2);

    /* renamed from: a */
    private boolean f1663a = false;

    /* renamed from: h */
    final C1195j f1664h = new C1195j(this);

    /* renamed from: i */
    final HashMap<Long, C1182b> f1665i = new HashMap<>(7);

    /* renamed from: j */
    final C1188c f1666j = new C1188c();

    /* renamed from: k */
    final C1189d<C1190e> f1667k = new C1189d<>(this, this);

    /* renamed from: l */
    final ReentrantLock f1668l = new ReentrantLock();

    /* renamed from: m */
    PowerManager f1669m;

    /* renamed from: n */
    AlarmManager f1670n;

    /* renamed from: o */
    Intent f1671o;

    /* renamed from: p */
    PendingIntent f1672p;

    /* renamed from: q */
    Intent f1673q;

    /* renamed from: r */
    PendingIntent f1674r;

    /* renamed from: s */
    String f1675s;

    /* renamed from: t */
    volatile boolean f1676t;

    protected C1191f() {
        C1190e.f1634E = this;
    }

    /* renamed from: a */
    public final int compare(C1190e eVar, C1190e eVar2) {
        if (eVar.f1655u < eVar2.f1655u) {
            return -1;
        }
        if (eVar.f1655u > eVar2.f1655u) {
            return 1;
        }
        if (eVar.f1635A > eVar2.f1635A) {
            return -1;
        }
        if (eVar.f1635A < eVar2.f1635A) {
            return 1;
        }
        if (eVar.f1656v < eVar2.f1656v) {
            return -1;
        }
        if (eVar.f1656v > eVar2.f1656v) {
            return 1;
        }
        return eVar.hashCode() - eVar2.hashCode();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0054 */
    @android.annotation.TargetApi(19)
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo14312a(long r5) {
        /*
            r4 = this;
            boolean r0 = r4.f1676t
            if (r0 == 0) goto L_0x0072
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "setalarm|"
            r0.append(r1)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.String r3 = "yyyy-MM-dd HH:mm:ss"
            r1.<init>(r3, r2)
            java.util.Date r2 = new java.util.Date
            r2.<init>(r5)
            java.lang.String r1 = r1.format(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            r0 = 0
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x0039
            long r5 = java.lang.System.currentTimeMillis()
            long r0 = f1662u
            long r5 = r5 + r0
        L_0x0039:
            android.app.PendingIntent r0 = r4.f1672p     // Catch:{ Throwable -> 0x0059 }
            if (r0 == 0) goto L_0x0072
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0059 }
            r1 = 19
            r2 = 0
            if (r0 >= r1) goto L_0x004c
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Throwable -> 0x0059 }
            android.app.PendingIntent r1 = r4.f1672p     // Catch:{ Throwable -> 0x0059 }
        L_0x0048:
            r0.set(r2, r5, r1)     // Catch:{ Throwable -> 0x0059 }
            goto L_0x0072
        L_0x004c:
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Throwable -> 0x0054 }
            android.app.PendingIntent r1 = r4.f1672p     // Catch:{ Throwable -> 0x0054 }
            r0.setExact(r2, r5, r1)     // Catch:{ Throwable -> 0x0054 }
            goto L_0x0072
        L_0x0054:
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Throwable -> 0x0059 }
            android.app.PendingIntent r1 = r4.f1672p     // Catch:{ Throwable -> 0x0059 }
            goto L_0x0048
        L_0x0059:
            r5 = move-exception
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r0 = "TaskService"
            r6.append(r0)
            java.lang.String r5 = r5.toString()
            r6.append(r5)
            java.lang.String r5 = r6.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r5)
        L_0x0072:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p040d.C1191f.mo14312a(long):void");
    }

    /* renamed from: a */
    public final void mo14313a(Context context) {
        if (!this.f1663a) {
            if (!C1589n.m3262d()) {
                this.f1669m = (PowerManager) context.getSystemService("power");
                this.f1676t = true;
                this.f1670n = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
                context.registerReceiver(this, new IntentFilter("AlarmTaskSchedule." + context.getPackageName()));
                context.registerReceiver(this, new IntentFilter("AlarmTaskScheduleBak." + context.getPackageName()));
                context.registerReceiver(this, new IntentFilter("android.intent.action.SCREEN_OFF"));
                context.registerReceiver(this, new IntentFilter("android.intent.action.SCREEN_ON"));
                this.f1675s = "AlarmNioTaskSchedule." + context.getPackageName();
                context.registerReceiver(this, new IntentFilter(this.f1675s));
                this.f1671o = new Intent("AlarmTaskSchedule." + context.getPackageName());
                this.f1672p = PendingIntent.getBroadcast(context, hashCode(), this.f1671o, 134217728);
                this.f1673q = new Intent(this.f1675s);
                this.f1674r = PendingIntent.getBroadcast(context, hashCode() + 2, this.f1673q, 134217728);
            }
            this.f1664h.start();
            try {
                Thread.yield();
            } catch (Throwable unused) {
            }
            this.f1663a = true;
        }
    }

    /* renamed from: a */
    public final boolean mo14314a(C1182b bVar) {
        if (bVar != null) {
            ReentrantLock reentrantLock = this.f1668l;
            if (reentrantLock.tryLock()) {
                try {
                    if (this.f1665i.keySet().contains(Long.valueOf(bVar.mo14276l()))) {
                        return false;
                    }
                    this.f1665i.put(Long.valueOf(bVar.mo14276l()), bVar);
                    reentrantLock.unlock();
                    return true;
                } catch (Throwable th) {
                    C1179b.m1354a("TaskService|" + th.toString());
                } finally {
                    reentrantLock.unlock();
                }
            }
            return false;
        }
        throw new NullPointerException();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final boolean mo14315a(C1185e eVar, C1182b bVar) {
        int b = eVar.mo14231b();
        if (b > Integer.MIN_VALUE && b < 0) {
            C1190e eVar2 = (C1190e) eVar;
            boolean a = eVar2.f1654t ? bVar.mo14274a(eVar2, this) : bVar.mo14273a(eVar, this);
            if (a) {
                eVar2.mo14300c();
            }
            return a;
        } else if (b < 0 || b >= Integer.MAX_VALUE) {
            return false;
        } else {
            return bVar.mo14273a(eVar, this);
        }
    }

    /* renamed from: a */
    public final boolean mo14316a(C1190e eVar, boolean z) {
        if (eVar != null) {
            int i = 0;
            if (eVar.f1650p || eVar.f1646k) {
                return false;
            }
            C1189d<C1190e> dVar = this.f1667k;
            if ((eVar instanceof C1176e) && (((C1176e) eVar).f1608c instanceof C1387p)) {
                if (z) {
                    i = Integer.MAX_VALUE;
                }
            } else if (z) {
                i = dVar.f1630e.incrementAndGet();
            }
            eVar.f1635A = i;
            return dVar.mo14289a(eVar);
        }
        throw new NullPointerException();
    }

    /* renamed from: a */
    public final boolean mo14317a(C1190e eVar, boolean z, boolean z2) {
        if (eVar == null) {
            throw new NullPointerException();
        } else if (eVar.f1647m) {
            return false;
        } else {
            boolean z3 = true;
            if (!z || z2) {
                if (!z2 || !z) {
                    z3 = false;
                }
                return mo14316a(eVar, z3);
            }
            eVar.mo14221d();
            try {
                eVar.mo14232b_();
                eVar.mo14301g();
                eVar.mo14302g_();
                if (!eVar.f1654t) {
                    eVar.mo14300c();
                }
                return true;
            } catch (Exception e) {
                eVar.f1654t = true;
                eVar.f1636B = e;
                eVar.mo14305p();
                eVar.mo14310u();
                mo14319a((Object) eVar);
                mo14323f();
                if (!eVar.f1654t) {
                    eVar.mo14300c();
                }
                return false;
            } catch (Throwable th) {
                if (!eVar.f1654t) {
                    eVar.mo14300c();
                }
                throw th;
            }
        }
    }

    /* renamed from: a */
    public final boolean mo14318a(Class cls) {
        C1189d<C1190e> dVar = this.f1667k;
        return dVar != null && dVar.mo14290a(cls);
    }

    /* renamed from: a */
    public final boolean mo14319a(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            if (obj instanceof C1386o) {
                C1386o oVar = (C1386o) obj;
            }
        } catch (Exception unused) {
        }
        C1179b.m1354a("TaskService|responseQueue ++ task = " + obj.getClass().getName() + "@" + obj.hashCode());
        if (obj instanceof C1185e) {
            C1185e eVar = (C1185e) obj;
            if (eVar.mo14270l()) {
                return false;
            }
            eVar.mo14269d(false);
            if ((obj instanceof C1369a) || (obj instanceof C1370b)) {
                this.f1666j.mo14282a();
                C1179b.m1354a("TaskService|scheduleQueue_response_change_queue primaryResponseQueue");
            }
            this.f1666j.mo14283a(eVar);
            return true;
        }
        throw new ClassCastException("response Obj is not a TaskResult ");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0053 */
    @android.annotation.TargetApi(19)
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo14320b(long r5) {
        /*
            r4 = this;
            boolean r0 = com.igexin.push.util.C1589n.m3262d()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "setnioalarm|"
            r0.append(r1)
            java.text.SimpleDateFormat r1 = new java.text.SimpleDateFormat
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.String r3 = "yyyy-MM-dd HH:mm:ss"
            r1.<init>(r3, r2)
            java.util.Date r2 = new java.util.Date
            r2.<init>(r5)
            java.lang.String r1 = r1.format(r2)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            r0 = 0
            int r2 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r2 >= 0) goto L_0x003c
            long r5 = java.lang.System.currentTimeMillis()
            long r0 = f1662u
            long r5 = r5 + r0
        L_0x003c:
            int r0 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x0056 }
            r1 = 19
            r2 = 0
            if (r0 >= r1) goto L_0x004b
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Throwable -> 0x0056 }
        L_0x0045:
            android.app.PendingIntent r1 = r4.f1674r     // Catch:{ Throwable -> 0x0056 }
            r0.set(r2, r5, r1)     // Catch:{ Throwable -> 0x0056 }
            goto L_0x0056
        L_0x004b:
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Exception -> 0x0053 }
            android.app.PendingIntent r1 = r4.f1674r     // Catch:{ Exception -> 0x0053 }
            r0.setExact(r2, r5, r1)     // Catch:{ Exception -> 0x0053 }
            goto L_0x0056
        L_0x0053:
            android.app.AlarmManager r0 = r4.f1670n     // Catch:{ Throwable -> 0x0056 }
            goto L_0x0045
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p040d.C1191f.mo14320b(long):void");
    }

    /* renamed from: e */
    public final void mo14322e() {
        try {
            if (this.f1674r != null) {
                this.f1670n.cancel(this.f1674r);
            }
        } catch (Throwable unused) {
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public final void mo14323f() {
        C1195j jVar = this.f1664h;
        if (jVar != null && !jVar.isInterrupted()) {
            this.f1664h.interrupt();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0087, code lost:
        if (r1 < 0) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0089, code lost:
        ((com.igexin.p032b.p033a.p040d.C1190e) r0).mo14300c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00b6, code lost:
        if (r1 < 0) goto L_0x0089;
     */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo14324g() {
        /*
            r9 = this;
        L_0x0000:
            com.igexin.b.a.d.c r0 = r9.f1666j
            boolean r0 = r0.mo14285c()
            if (r0 != 0) goto L_0x00dc
            com.igexin.b.a.d.c r0 = r9.f1666j
            com.igexin.b.a.d.a.e r0 = r0.mo14286d()
            if (r0 != 0) goto L_0x0011
            return
        L_0x0011:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "TaskService|notifyObserver responseQueue -- task = "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r1)
            r1 = 1
            r0.mo14269d(r1)
            r1 = 0
            java.util.concurrent.locks.ReentrantLock r2 = r9.f1668l
            r2.lock()
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            java.util.HashMap<java.lang.Long, com.igexin.b.a.d.a.b> r4 = r9.f1665i     // Catch:{ Throwable -> 0x0095 }
            boolean r4 = r4.isEmpty()     // Catch:{ Throwable -> 0x0095 }
            if (r4 != 0) goto L_0x007f
            long r4 = r0.mo14271m()     // Catch:{ Throwable -> 0x0095 }
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x005c
            java.util.HashMap<java.lang.Long, com.igexin.b.a.d.a.b> r6 = r9.f1665i     // Catch:{ Throwable -> 0x0095 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0095 }
            java.lang.Object r4 = r6.get(r4)     // Catch:{ Throwable -> 0x0095 }
            com.igexin.b.a.d.a.b r4 = (com.igexin.p032b.p033a.p040d.p041a.C1182b) r4     // Catch:{ Throwable -> 0x0095 }
            if (r4 == 0) goto L_0x007f
            boolean r5 = r4.mo14275k()     // Catch:{ Throwable -> 0x0095 }
            if (r5 == 0) goto L_0x007f
            boolean r1 = r9.mo14315a((com.igexin.p032b.p033a.p040d.p041a.C1185e) r0, (com.igexin.p032b.p033a.p040d.p041a.C1182b) r4)     // Catch:{ Throwable -> 0x0095 }
            goto L_0x007f
        L_0x005c:
            java.util.HashMap<java.lang.Long, com.igexin.b.a.d.a.b> r4 = r9.f1665i     // Catch:{ Throwable -> 0x0095 }
            java.util.Collection r4 = r4.values()     // Catch:{ Throwable -> 0x0095 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Throwable -> 0x0095 }
        L_0x0066:
            boolean r5 = r4.hasNext()     // Catch:{ Throwable -> 0x0095 }
            if (r5 == 0) goto L_0x007f
            java.lang.Object r5 = r4.next()     // Catch:{ Throwable -> 0x0095 }
            com.igexin.b.a.d.a.b r5 = (com.igexin.p032b.p033a.p040d.p041a.C1182b) r5     // Catch:{ Throwable -> 0x0095 }
            boolean r6 = r5.mo14275k()     // Catch:{ Throwable -> 0x0095 }
            if (r6 != 0) goto L_0x0079
            goto L_0x0066
        L_0x0079:
            boolean r1 = r9.mo14315a((com.igexin.p032b.p033a.p040d.p041a.C1185e) r0, (com.igexin.p032b.p033a.p040d.p041a.C1182b) r5)     // Catch:{ Throwable -> 0x0095 }
            if (r1 == 0) goto L_0x0066
        L_0x007f:
            if (r1 != 0) goto L_0x008f
            int r1 = r0.mo14231b()
            if (r1 <= r3) goto L_0x008f
            if (r1 >= 0) goto L_0x008f
        L_0x0089:
            r1 = r0
            com.igexin.b.a.d.e r1 = (com.igexin.p032b.p033a.p040d.C1190e) r1
            r1.mo14300c()
        L_0x008f:
            r2.unlock()
            goto L_0x00b9
        L_0x0093:
            r4 = move-exception
            goto L_0x00c9
        L_0x0095:
            r4 = move-exception
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0093 }
            r5.<init>()     // Catch:{ all -> 0x0093 }
            java.lang.String r6 = "TaskService|"
            r5.append(r6)     // Catch:{ all -> 0x0093 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0093 }
            r5.append(r4)     // Catch:{ all -> 0x0093 }
            java.lang.String r4 = r5.toString()     // Catch:{ all -> 0x0093 }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r4)     // Catch:{ all -> 0x0093 }
            if (r1 != 0) goto L_0x008f
            int r1 = r0.mo14231b()
            if (r1 <= r3) goto L_0x008f
            if (r1 >= 0) goto L_0x008f
            goto L_0x0089
        L_0x00b9:
            boolean r0 = r0 instanceof com.igexin.push.p054e.p057c.C1384m
            if (r0 == 0) goto L_0x0000
            com.igexin.b.a.d.c r0 = r9.f1666j
            r0.mo14284b()
            java.lang.String r0 = "TaskService|queue -> secondRespQueue"
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
            goto L_0x0000
        L_0x00c9:
            if (r1 != 0) goto L_0x00d8
            int r1 = r0.mo14231b()
            if (r1 <= r3) goto L_0x00d8
            if (r1 >= 0) goto L_0x00d8
            com.igexin.b.a.d.e r0 = (com.igexin.p032b.p033a.p040d.C1190e) r0
            r0.mo14300c()
        L_0x00d8:
            r2.unlock()
            throw r4
        L_0x00dc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.p032b.p033a.p040d.C1191f.mo14324g():void");
    }

    public final void onReceive(Context context, Intent intent) {
        if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
            this.f1676t = true;
            C1179b.m1354a("screenoff");
            if (this.f1667k.f1633h.get() > 0) {
                mo14312a(this.f1667k.f1633h.get());
            }
        } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction())) {
            this.f1676t = false;
            C1179b.m1354a("screenon");
        } else if (intent.getAction().startsWith("AlarmTaskSchedule.") || intent.getAction().startsWith("AlarmTaskScheduleBak.")) {
            C1179b.m1354a("receivealarm|" + this.f1676t);
            mo14323f();
        } else if (this.f1675s.equals(intent.getAction())) {
            C1179b.m1354a("receive nioalarm");
            try {
                C1179b.m1354a("TaskService|alarm time out #######");
                C1161f.m1252a().mo14243e();
            } catch (Exception unused) {
            }
        }
    }
}
