package com.igexin.push.core.p050c;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p039c.C1179b;
import java.util.Iterator;
import kotlin.jvm.internal.LongCompanionObject;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.c.c */
public class C1307c {

    /* renamed from: b */
    private static final Object f2037b = new Object();

    /* renamed from: c */
    private static final Object f2038c = new Object();

    /* renamed from: a */
    private SharedPreferences f2039a;

    public C1307c(Context context) {
        if (context != null) {
            this.f2039a = context.getSharedPreferences("gx_msg_sp", 0);
        }
    }

    /* renamed from: a */
    private void m1910a(JSONObject jSONObject) {
        try {
            if (jSONObject.length() >= 150) {
                boolean z = false;
                long j = LongCompanionObject.MAX_VALUE;
                String str = null;
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    long j2 = jSONObject.getLong(next);
                    if (j > j2) {
                        str = next;
                        j = j2;
                    }
                    if (j2 < System.currentTimeMillis() - 432000000) {
                        keys.remove();
                        z = true;
                    }
                }
                if (!z && str != null) {
                    jSONObject.remove(str);
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: b */
    private void m1911b(JSONObject jSONObject) {
        try {
            if (jSONObject.length() >= 20) {
                boolean z = false;
                long j = LongCompanionObject.MAX_VALUE;
                String str = null;
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    long parseLong = Long.parseLong(jSONObject.getJSONObject(next).getString("timestamp"));
                    if (j > parseLong) {
                        str = next;
                        j = parseLong;
                    }
                    if (parseLong < System.currentTimeMillis() - 432000000) {
                        keys.remove();
                        z = true;
                    }
                }
                if (!z && str != null) {
                    jSONObject.remove(str);
                }
            }
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    public JSONObject mo14651a() {
        try {
            String string = this.f2039a.getString("taskIdList", "");
            if (!TextUtils.isEmpty(string)) {
                return new JSONObject(string);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x003c */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14652a(java.lang.String r4, org.json.JSONObject r5) {
        /*
            r3 = this;
            android.content.SharedPreferences r0 = r3.f2039a
            if (r0 == 0) goto L_0x0040
            if (r5 == 0) goto L_0x0040
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x000d
            goto L_0x0040
        L_0x000d:
            java.lang.Object r0 = f2037b
            monitor-enter(r0)
            org.json.JSONObject r1 = r3.mo14654b()     // Catch:{ Throwable -> 0x003c }
            if (r1 != 0) goto L_0x001b
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x003c }
            r1.<init>()     // Catch:{ Throwable -> 0x003c }
        L_0x001b:
            int r2 = r1.length()     // Catch:{ Throwable -> 0x003c }
            if (r2 <= 0) goto L_0x0024
            r3.m1911b((org.json.JSONObject) r1)     // Catch:{ Throwable -> 0x003c }
        L_0x0024:
            r1.put(r4, r5)     // Catch:{ Throwable -> 0x003c }
            android.content.SharedPreferences r4 = r3.f2039a     // Catch:{ Throwable -> 0x003c }
            android.content.SharedPreferences$Editor r4 = r4.edit()     // Catch:{ Throwable -> 0x003c }
            java.lang.String r5 = "usfdl"
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x003c }
            r4.putString(r5, r1)     // Catch:{ Throwable -> 0x003c }
            r4.apply()     // Catch:{ Throwable -> 0x003c }
            goto L_0x003c
        L_0x003a:
            r4 = move-exception
            goto L_0x003e
        L_0x003c:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            return
        L_0x003e:
            monitor-exit(r0)     // Catch:{ all -> 0x003a }
            throw r4
        L_0x0040:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1307c.mo14652a(java.lang.String, org.json.JSONObject):void");
    }

    /* renamed from: a */
    public boolean mo14653a(String str) {
        if (this.f2039a != null && !TextUtils.isEmpty(str)) {
            try {
                JSONObject a = mo14651a();
                if (a != null && a.has(str)) {
                    C1179b.m1354a("sp task " + str + " already exists");
                    return true;
                }
            } catch (Throwable unused) {
            }
        }
        return false;
    }

    /* renamed from: b */
    public JSONObject mo14654b() {
        try {
            String string = this.f2039a.getString("usfdl", "");
            if (!TextUtils.isEmpty(string)) {
                return new JSONObject(string);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003e */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14655b(java.lang.String r5) {
        /*
            r4 = this;
            android.content.SharedPreferences r0 = r4.f2039a
            if (r0 == 0) goto L_0x0042
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x000b
            goto L_0x0042
        L_0x000b:
            java.lang.Object r0 = f2038c
            monitor-enter(r0)
            org.json.JSONObject r1 = r4.mo14651a()     // Catch:{ Throwable -> 0x003e }
            if (r1 != 0) goto L_0x0019
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ Throwable -> 0x003e }
            r1.<init>()     // Catch:{ Throwable -> 0x003e }
        L_0x0019:
            int r2 = r1.length()     // Catch:{ Throwable -> 0x003e }
            if (r2 <= 0) goto L_0x0022
            r4.m1910a((org.json.JSONObject) r1)     // Catch:{ Throwable -> 0x003e }
        L_0x0022:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x003e }
            r1.put(r5, r2)     // Catch:{ Throwable -> 0x003e }
            android.content.SharedPreferences r5 = r4.f2039a     // Catch:{ Throwable -> 0x003e }
            android.content.SharedPreferences$Editor r5 = r5.edit()     // Catch:{ Throwable -> 0x003e }
            java.lang.String r2 = "taskIdList"
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x003e }
            r5.putString(r2, r1)     // Catch:{ Throwable -> 0x003e }
            r5.apply()     // Catch:{ Throwable -> 0x003e }
            goto L_0x003e
        L_0x003c:
            r5 = move-exception
            goto L_0x0040
        L_0x003e:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            return
        L_0x0040:
            monitor-exit(r0)     // Catch:{ all -> 0x003c }
            throw r5
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1307c.mo14655b(java.lang.String):void");
    }

    /* renamed from: c */
    public JSONObject mo14656c() {
        synchronized (f2037b) {
            try {
                String string = this.f2039a.getString("usfdl", "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                }
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> keys = jSONObject.keys();
                while (keys.hasNext()) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(keys.next());
                    if (jSONObject2.has("timestamp")) {
                        if (Long.parseLong(jSONObject2.getString("timestamp")) >= System.currentTimeMillis() - 432000000) {
                        }
                    }
                    keys.remove();
                }
                return jSONObject;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: c */
    public void mo14657c(String str) {
        try {
            SharedPreferences.Editor edit = this.f2039a.edit();
            edit.putString("gx_vendor_token", str);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001a */
    /* renamed from: d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo14658d() {
        /*
            r4 = this;
            java.lang.Object r0 = f2037b
            monitor-enter(r0)
            android.content.SharedPreferences r1 = r4.f2039a     // Catch:{ Throwable -> 0x001a }
            if (r1 == 0) goto L_0x001a
            android.content.SharedPreferences r1 = r4.f2039a     // Catch:{ Throwable -> 0x001a }
            android.content.SharedPreferences$Editor r1 = r1.edit()     // Catch:{ Throwable -> 0x001a }
            java.lang.String r2 = "usfdl"
            java.lang.String r3 = ""
            r1.putString(r2, r3)     // Catch:{ Throwable -> 0x001a }
            r1.apply()     // Catch:{ Throwable -> 0x001a }
            goto L_0x001a
        L_0x0018:
            r1 = move-exception
            goto L_0x001c
        L_0x001a:
            monitor-exit(r0)     // Catch:{ all -> 0x0018 }
            return
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x0018 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p050c.C1307c.mo14658d():void");
    }

    /* renamed from: e */
    public String mo14659e() {
        try {
            return this.f2039a.getString("gx_vendor_token", (String) null);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: f */
    public void mo14660f() {
        try {
            if (this.f2039a.contains("gx_vendor_token")) {
                SharedPreferences.Editor edit = this.f2039a.edit();
                edit.remove("gx_vendor_token");
                edit.apply();
            }
        } catch (Throwable unused) {
        }
    }
}
