package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.support.p000v4.util.SimpleArrayMap;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.Queue;
import javax.annotation.concurrent.GuardedBy;

public final class zzau {
    private static zzau zzdd;
    @GuardedBy("serviceClassNames")
    private final SimpleArrayMap<String, String> zzde = new SimpleArrayMap<>();
    private Boolean zzdf = null;
    private Boolean zzdg = null;
    final Queue<Intent> zzdh = new ArrayDeque();
    private final Queue<Intent> zzdi = new ArrayDeque();

    public static synchronized zzau zzai() {
        zzau zzau;
        synchronized (zzau.class) {
            if (zzdd == null) {
                zzdd = new zzau();
            }
            zzau = zzdd;
        }
        return zzau;
    }

    private zzau() {
    }

    public static void zzb(Context context, Intent intent) {
        context.sendBroadcast(zza(context, "com.google.firebase.INSTANCE_ID_EVENT", intent));
    }

    public static void zzc(Context context, Intent intent) {
        context.sendBroadcast(zza(context, "com.google.firebase.MESSAGING_EVENT", intent));
    }

    private static Intent zza(Context context, String str, Intent intent) {
        Intent intent2 = new Intent(context, FirebaseInstanceIdReceiver.class);
        intent2.setAction(str);
        intent2.putExtra("wrapped_intent", intent);
        return intent2;
    }

    public final Intent zzaj() {
        return this.zzdi.poll();
    }

    public final int zzb(Context context, String str, Intent intent) {
        if (Log.isLoggable("FirebaseInstanceId", 3)) {
            String valueOf = String.valueOf(str);
            Log.d("FirebaseInstanceId", valueOf.length() != 0 ? "Starting service: ".concat(valueOf) : new String("Starting service: "));
        }
        char c = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -842411455) {
            if (hashCode == 41532704 && str.equals("com.google.firebase.MESSAGING_EVENT")) {
                c = 1;
            }
        } else if (str.equals("com.google.firebase.INSTANCE_ID_EVENT")) {
            c = 0;
        }
        if (c == 0) {
            this.zzdh.offer(intent);
        } else if (c != 1) {
            String valueOf2 = String.valueOf(str);
            Log.w("FirebaseInstanceId", valueOf2.length() != 0 ? "Unknown service action: ".concat(valueOf2) : new String("Unknown service action: "));
            return 500;
        } else {
            this.zzdi.offer(intent);
        }
        Intent intent2 = new Intent(str);
        intent2.setPackage(context.getPackageName());
        return zzd(context, intent2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00de A[Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e3 A[Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f0 A[Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00fa A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzd(android.content.Context r5, android.content.Intent r6) {
        /*
            r4 = this;
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.lang.String> r0 = r4.zzde
            monitor-enter(r0)
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.lang.String> r1 = r4.zzde     // Catch:{ all -> 0x012f }
            java.lang.String r2 = r6.getAction()     // Catch:{ all -> 0x012f }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x012f }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x012f }
            monitor-exit(r0)     // Catch:{ all -> 0x012f }
            if (r1 != 0) goto L_0x00ac
            android.content.pm.PackageManager r0 = r5.getPackageManager()
            r1 = 0
            android.content.pm.ResolveInfo r0 = r0.resolveService(r6, r1)
            if (r0 == 0) goto L_0x00a4
            android.content.pm.ServiceInfo r1 = r0.serviceInfo
            if (r1 != 0) goto L_0x0023
            goto L_0x00a4
        L_0x0023:
            android.content.pm.ServiceInfo r0 = r0.serviceInfo
            java.lang.String r1 = r5.getPackageName()
            java.lang.String r2 = r0.packageName
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x006e
            java.lang.String r1 = r0.name
            if (r1 != 0) goto L_0x0036
            goto L_0x006e
        L_0x0036:
            java.lang.String r0 = r0.name
            java.lang.String r1 = "."
            boolean r1 = r0.startsWith(r1)
            if (r1 == 0) goto L_0x005c
            java.lang.String r1 = r5.getPackageName()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r2 = r0.length()
            if (r2 == 0) goto L_0x0057
            java.lang.String r0 = r1.concat(r0)
            goto L_0x005c
        L_0x0057:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r1)
        L_0x005c:
            r1 = r0
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.lang.String> r2 = r4.zzde
            monitor-enter(r2)
            android.support.v4.util.SimpleArrayMap<java.lang.String, java.lang.String> r0 = r4.zzde     // Catch:{ all -> 0x006b }
            java.lang.String r3 = r6.getAction()     // Catch:{ all -> 0x006b }
            r0.put(r3, r1)     // Catch:{ all -> 0x006b }
            monitor-exit(r2)     // Catch:{ all -> 0x006b }
            goto L_0x00ac
        L_0x006b:
            r5 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x006b }
            throw r5
        L_0x006e:
            java.lang.String r1 = r0.packageName
            java.lang.String r0 = r0.name
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 94
            java.lang.String r3 = java.lang.String.valueOf(r0)
            int r3 = r3.length()
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "Error resolving target intent service, skipping classname enforcement. Resolved service was: "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = "/"
            r3.append(r1)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            java.lang.String r1 = "FirebaseInstanceId"
            android.util.Log.e(r1, r0)
            goto L_0x00d8
        L_0x00a4:
            java.lang.String r0 = "FirebaseInstanceId"
            java.lang.String r1 = "Failed to resolve target intent service, skipping classname enforcement"
            android.util.Log.e(r0, r1)
            goto L_0x00d8
        L_0x00ac:
            r0 = 3
            java.lang.String r2 = "FirebaseInstanceId"
            boolean r0 = android.util.Log.isLoggable(r2, r0)
            if (r0 == 0) goto L_0x00d1
            java.lang.String r0 = "Restricting intent to a specific service: "
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r3 = r2.length()
            if (r3 == 0) goto L_0x00c6
            java.lang.String r0 = r0.concat(r2)
            goto L_0x00cc
        L_0x00c6:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r0)
            r0 = r2
        L_0x00cc:
            java.lang.String r2 = "FirebaseInstanceId"
            android.util.Log.d(r2, r0)
        L_0x00d1:
            java.lang.String r0 = r5.getPackageName()
            r6.setClassName(r0, r1)
        L_0x00d8:
            boolean r0 = r4.zzd(r5)     // Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }
            if (r0 == 0) goto L_0x00e3
            android.content.ComponentName r5 = android.support.p000v4.content.WakefulBroadcastReceiver.startWakefulService(r5, r6)     // Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }
            goto L_0x00ee
        L_0x00e3:
            android.content.ComponentName r5 = r5.startService(r6)     // Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }
            java.lang.String r6 = "FirebaseInstanceId"
            java.lang.String r0 = "Missing wake lock permission, service start may be delayed"
            android.util.Log.d(r6, r0)     // Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }
        L_0x00ee:
            if (r5 != 0) goto L_0x00fa
            java.lang.String r5 = "FirebaseInstanceId"
            java.lang.String r6 = "Error while delivering the message: ServiceIntent not found."
            android.util.Log.e(r5, r6)     // Catch:{ SecurityException -> 0x0124, IllegalStateException -> 0x00fc }
            r5 = 404(0x194, float:5.66E-43)
            return r5
        L_0x00fa:
            r5 = -1
            return r5
        L_0x00fc:
            r5 = move-exception
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r6 = java.lang.String.valueOf(r5)
            int r6 = r6.length()
            int r6 = r6 + 45
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r6)
            java.lang.String r6 = "Failed to start service while in background: "
            r0.append(r6)
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            java.lang.String r6 = "FirebaseInstanceId"
            android.util.Log.e(r6, r5)
            r5 = 402(0x192, float:5.63E-43)
            return r5
        L_0x0124:
            r5 = move-exception
            java.lang.String r6 = "FirebaseInstanceId"
            java.lang.String r0 = "Error while delivering the message to the serviceIntent"
            android.util.Log.e(r6, r0, r5)
            r5 = 401(0x191, float:5.62E-43)
            return r5
        L_0x012f:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x012f }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzau.zzd(android.content.Context, android.content.Intent):int");
    }

    /* access modifiers changed from: package-private */
    public final boolean zzd(Context context) {
        if (this.zzdf == null) {
            this.zzdf = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0);
        }
        if (!this.zzdf.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.WAKE_LOCK this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.zzdf.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public final boolean zze(Context context) {
        if (this.zzdg == null) {
            this.zzdg = Boolean.valueOf(context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0);
        }
        if (!this.zzdf.booleanValue() && Log.isLoggable("FirebaseInstanceId", 3)) {
            Log.d("FirebaseInstanceId", "Missing Permission: android.permission.ACCESS_NETWORK_STATE this should normally be included by the manifest merger, but may needed to be manually added to your manifest");
        }
        return this.zzdg.booleanValue();
    }
}
