package com.alibaba.sdk.android.crashdefend.p014c;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.List;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.alibaba.sdk.android.crashdefend.c.a */
public class C0698a {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.String} */
    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1, types: [java.io.BufferedReader] */
    /* JADX WARNING: type inference failed for: r1v5 */
    /* JADX WARNING: type inference failed for: r1v6 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: type inference failed for: r1v10 */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0063 A[SYNTHETIC, Splitter:B:21:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006d A[SYNTHETIC, Splitter:B:27:0x006d] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String m595a() {
        /*
            int r0 = android.os.Process.myPid()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.<init>()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r4 = "/proc/"
            r3.append(r4)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.append(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r0 = "/cmdline"
            r3.append(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r0 = r3.toString()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            boolean r0 = r2.exists()     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            if (r0 == 0) goto L_0x003b
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            r0.<init>(r3)     // Catch:{ Exception -> 0x0049, all -> 0x0047 }
            java.lang.String r2 = r0.readLine()     // Catch:{ Exception -> 0x0039 }
            java.lang.String r1 = r2.trim()     // Catch:{ Exception -> 0x0039 }
            goto L_0x003c
        L_0x0039:
            r2 = move-exception
            goto L_0x004b
        L_0x003b:
            r0 = r1
        L_0x003c:
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0066
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0066
        L_0x0047:
            r0 = move-exception
            goto L_0x006b
        L_0x0049:
            r2 = move-exception
            r0 = r1
        L_0x004b:
            java.lang.String r3 = "CrashUtils"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0067 }
            r4.<init>()     // Catch:{ all -> 0x0067 }
            java.lang.String r5 = "getProcessNameByPid error: "
            r4.append(r5)     // Catch:{ all -> 0x0067 }
            r4.append(r2)     // Catch:{ all -> 0x0067 }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x0067 }
            android.util.Log.d(r3, r2)     // Catch:{ all -> 0x0067 }
            if (r0 == 0) goto L_0x0066
            r0.close()     // Catch:{ IOException -> 0x0042 }
        L_0x0066:
            return r1
        L_0x0067:
            r1 = move-exception
            r6 = r1
            r1 = r0
            r0 = r6
        L_0x006b:
            if (r1 == 0) goto L_0x0075
            r1.close()     // Catch:{ IOException -> 0x0071 }
            goto L_0x0075
        L_0x0071:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0075:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.crashdefend.p014c.C0698a.m595a():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0104, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void m596a(android.content.Context r8, com.alibaba.sdk.android.crashdefend.p012a.C0694a r9, java.util.List<com.alibaba.sdk.android.crashdefend.p012a.C0695b> r10) {
        /*
            java.lang.Class<com.alibaba.sdk.android.crashdefend.c.a> r0 = com.alibaba.sdk.android.crashdefend.p014c.C0698a.class
            monitor-enter(r0)
            if (r8 == 0) goto L_0x0103
            if (r10 != 0) goto L_0x0009
            goto L_0x0103
        L_0x0009:
            r1 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r2.<init>()     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            if (r9 == 0) goto L_0x0018
            java.lang.String r3 = "startSerialNumber"
            long r4 = r9.f862a     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r2.put(r3, r4)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
        L_0x0018:
            org.json.JSONArray r9 = new org.json.JSONArray     // Catch:{ JSONException -> 0x007e }
            r9.<init>()     // Catch:{ JSONException -> 0x007e }
            java.util.Iterator r10 = r10.iterator()     // Catch:{ JSONException -> 0x007e }
        L_0x0021:
            boolean r3 = r10.hasNext()     // Catch:{ JSONException -> 0x007e }
            if (r3 == 0) goto L_0x0078
            java.lang.Object r3 = r10.next()     // Catch:{ JSONException -> 0x007e }
            com.alibaba.sdk.android.crashdefend.a.b r3 = (com.alibaba.sdk.android.crashdefend.p012a.C0695b) r3     // Catch:{ JSONException -> 0x007e }
            if (r3 != 0) goto L_0x0030
            goto L_0x0021
        L_0x0030:
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x007e }
            r4.<init>()     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "sdkId"
            java.lang.String r6 = r3.f863a     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "sdkVersion"
            java.lang.String r6 = r3.f864b     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "crashLimit"
            int r6 = r3.f865c     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "crashCount"
            int r6 = r3.f866d     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "waitTime"
            int r6 = r3.f867e     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "registerSerialNumber"
            long r6 = r3.f868f     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "startSerialNumber"
            long r6 = r3.f869g     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "restoreCount"
            int r6 = r3.f870h     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            java.lang.String r5 = "nextRestoreInterval"
            long r6 = r3.f871i     // Catch:{ JSONException -> 0x007e }
            r4.put(r5, r6)     // Catch:{ JSONException -> 0x007e }
            r9.put(r4)     // Catch:{ JSONException -> 0x007e }
            goto L_0x0021
        L_0x0078:
            java.lang.String r10 = "sdkList"
            r2.put(r10, r9)     // Catch:{ JSONException -> 0x007e }
            goto L_0x0086
        L_0x007e:
            r9 = move-exception
            java.lang.String r10 = "CrashUtils"
            java.lang.String r3 = "save sdk json fail:"
            android.util.Log.e(r10, r3, r9)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
        L_0x0086:
            java.lang.String r9 = r2.toString()     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            boolean r10 = m597a(r8)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r2 = 0
            if (r10 == 0) goto L_0x0099
            java.lang.String r10 = "com_alibaba_aliyun_crash_defend_sdk_info"
        L_0x0093:
            java.io.FileOutputStream r8 = r8.openFileOutput(r10, r2)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r1 = r8
            goto L_0x00af
        L_0x0099:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r10.<init>()     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            java.lang.String r3 = "com_alibaba_aliyun_crash_defend_sdk_info_"
            r10.append(r3)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            java.lang.String r3 = m598b(r8)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r10.append(r3)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            java.lang.String r10 = r10.toString()     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            goto L_0x0093
        L_0x00af:
            byte[] r8 = r9.getBytes()     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            r1.write(r8)     // Catch:{ IOException -> 0x00db, Exception -> 0x00c7 }
            if (r1 == 0) goto L_0x00ef
            r1.close()     // Catch:{ IOException -> 0x00bc }
            goto L_0x00ef
        L_0x00bc:
            r8 = move-exception
            java.lang.String r9 = "CrashUtils"
            java.lang.String r10 = "save sdk io fail:"
        L_0x00c1:
            android.util.Log.e(r9, r10, r8)     // Catch:{ all -> 0x0100 }
            goto L_0x00ef
        L_0x00c5:
            r8 = move-exception
            goto L_0x00f1
        L_0x00c7:
            r8 = move-exception
            java.lang.String r9 = "CrashUtils"
            java.lang.String r10 = "save sdk exception:"
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r9, r10, r8)     // Catch:{ all -> 0x00c5 }
            if (r1 == 0) goto L_0x00ef
            r1.close()     // Catch:{ IOException -> 0x00d5 }
            goto L_0x00ef
        L_0x00d5:
            r8 = move-exception
            java.lang.String r9 = "CrashUtils"
            java.lang.String r10 = "save sdk io fail:"
            goto L_0x00c1
        L_0x00db:
            r8 = move-exception
            java.lang.String r9 = "CrashUtils"
            java.lang.String r10 = "save sdk io fail:"
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r9, r10, r8)     // Catch:{ all -> 0x00c5 }
            if (r1 == 0) goto L_0x00ef
            r1.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ef
        L_0x00e9:
            r8 = move-exception
            java.lang.String r9 = "CrashUtils"
            java.lang.String r10 = "save sdk io fail:"
            goto L_0x00c1
        L_0x00ef:
            monitor-exit(r0)
            return
        L_0x00f1:
            if (r1 == 0) goto L_0x00ff
            r1.close()     // Catch:{ IOException -> 0x00f7 }
            goto L_0x00ff
        L_0x00f7:
            r9 = move-exception
            java.lang.String r10 = "CrashUtils"
            java.lang.String r1 = "save sdk io fail:"
            android.util.Log.e(r10, r1, r9)     // Catch:{ all -> 0x0100 }
        L_0x00ff:
            throw r8     // Catch:{ all -> 0x0100 }
        L_0x0100:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        L_0x0103:
            monitor-exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.crashdefend.p014c.C0698a.m596a(android.content.Context, com.alibaba.sdk.android.crashdefend.a.a, java.util.List):void");
    }

    /* renamed from: a */
    private static boolean m597a(Context context) {
        return context.getPackageName().equalsIgnoreCase(m598b(context));
    }

    /* renamed from: b */
    private static String m598b(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                return Application.getProcessName();
            }
        } catch (Throwable th) {
            Log.e("CrashUtils", "Application gerProcessName error: " + Log.getStackTraceString(th));
        }
        String d = m601d(context);
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        String a = m595a();
        return !TextUtils.isEmpty(a) ? a : m600c(context);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:98:0x017f, code lost:
        return false;
     */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00d0 A[DONT_GENERATE] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00d2 A[SYNTHETIC, Splitter:B:68:0x00d2] */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean m599b(android.content.Context r10, com.alibaba.sdk.android.crashdefend.p012a.C0694a r11, java.util.List<com.alibaba.sdk.android.crashdefend.p012a.C0695b> r12) {
        /*
            java.lang.Class<com.alibaba.sdk.android.crashdefend.c.a> r0 = com.alibaba.sdk.android.crashdefend.p014c.C0698a.class
            monitor-enter(r0)
            r1 = 0
            if (r10 == 0) goto L_0x017e
            if (r12 != 0) goto L_0x000a
            goto L_0x017e
        L_0x000a:
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x017b }
            r3.<init>()     // Catch:{ all -> 0x017b }
            r4 = -1
            java.io.File r5 = r10.getFilesDir()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            boolean r6 = m597a(r10)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            if (r6 == 0) goto L_0x0023
            java.io.File r6 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r7 = "com_alibaba_aliyun_crash_defend_sdk_info"
            r6.<init>(r5, r7)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            goto L_0x003d
        L_0x0023:
            java.io.File r6 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r7.<init>()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r8 = "com_alibaba_aliyun_crash_defend_sdk_info_"
            r7.append(r8)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r8 = m598b(r10)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r7.append(r8)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r7 = r7.toString()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r6.<init>(r5, r7)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
        L_0x003d:
            boolean r5 = r6.exists()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            if (r5 != 0) goto L_0x0045
            monitor-exit(r0)
            return r1
        L_0x0045:
            boolean r5 = m597a(r10)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            if (r5 == 0) goto L_0x0053
            java.lang.String r5 = "com_alibaba_aliyun_crash_defend_sdk_info"
        L_0x004d:
            java.io.FileInputStream r10 = r10.openFileInput(r5)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r2 = r10
            goto L_0x0069
        L_0x0053:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r5.<init>()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r6 = "com_alibaba_aliyun_crash_defend_sdk_info_"
            r5.append(r6)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r6 = m598b(r10)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r5.append(r6)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            java.lang.String r5 = r5.toString()     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            goto L_0x004d
        L_0x0069:
            r10 = 512(0x200, float:7.175E-43)
            byte[] r10 = new byte[r10]     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
        L_0x006d:
            int r5 = r2.read(r10)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            if (r5 == r4) goto L_0x007c
            java.lang.String r6 = new java.lang.String     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r6.<init>(r10, r1, r5)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            r3.append(r6)     // Catch:{ FileNotFoundException -> 0x00b6, IOException -> 0x00a2, Exception -> 0x008e }
            goto L_0x006d
        L_0x007c:
            if (r2 == 0) goto L_0x00ca
            r2.close()     // Catch:{ IOException -> 0x0082 }
            goto L_0x00ca
        L_0x0082:
            r10 = move-exception
            java.lang.String r2 = "CrashUtils"
            java.lang.String r5 = "load sdk io fail:"
        L_0x0087:
            android.util.Log.e(r2, r5, r10)     // Catch:{ all -> 0x017b }
            goto L_0x00ca
        L_0x008b:
            r10 = move-exception
            goto L_0x016c
        L_0x008e:
            r10 = move-exception
            java.lang.String r5 = "CrashUtils"
            java.lang.String r6 = "load sdk exception:"
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r5, r6, r10)     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x00ca
            r2.close()     // Catch:{ IOException -> 0x009c }
            goto L_0x00ca
        L_0x009c:
            r10 = move-exception
            java.lang.String r2 = "CrashUtils"
            java.lang.String r5 = "load sdk io fail:"
            goto L_0x0087
        L_0x00a2:
            r10 = move-exception
            java.lang.String r5 = "CrashUtils"
            java.lang.String r6 = "load sdk io fail:"
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r5, r6, r10)     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x00ca
            r2.close()     // Catch:{ IOException -> 0x00b0 }
            goto L_0x00ca
        L_0x00b0:
            r10 = move-exception
            java.lang.String r2 = "CrashUtils"
            java.lang.String r5 = "load sdk io fail:"
            goto L_0x0087
        L_0x00b6:
            r10 = move-exception
            java.lang.String r5 = "CrashUtils"
            java.lang.String r6 = "load sdk file fail:"
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r5, r6, r10)     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x00ca
            r2.close()     // Catch:{ IOException -> 0x00c4 }
            goto L_0x00ca
        L_0x00c4:
            r10 = move-exception
            java.lang.String r2 = "CrashUtils"
            java.lang.String r5 = "load sdk io fail:"
            goto L_0x0087
        L_0x00ca:
            int r10 = r3.length()     // Catch:{ all -> 0x017b }
            if (r10 != 0) goto L_0x00d2
            monitor-exit(r0)
            return r1
        L_0x00d2:
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r2 = r3.toString()     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r10.<init>(r2)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r2 = "startSerialNumber"
            r5 = 1
            long r2 = r10.optLong(r2, r5)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r11.f862a = r2     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r11 = "sdkList"
            org.json.JSONArray r10 = r10.getJSONArray(r11)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r11 = 0
        L_0x00ec:
            int r2 = r10.length()     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            if (r11 >= r2) goto L_0x0169
            org.json.JSONObject r2 = r10.getJSONObject(r11)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            if (r2 == 0) goto L_0x0157
            com.alibaba.sdk.android.crashdefend.a.b r3 = new com.alibaba.sdk.android.crashdefend.a.b     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.<init>()     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "sdkId"
            java.lang.String r6 = ""
            java.lang.String r5 = r2.optString(r5, r6)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f863a = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "sdkVersion"
            java.lang.String r6 = ""
            java.lang.String r5 = r2.optString(r5, r6)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f864b = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "crashLimit"
            int r5 = r2.optInt(r5, r4)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f865c = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "crashCount"
            int r5 = r2.optInt(r5, r1)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f866d = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "waitTime"
            int r5 = r2.optInt(r5, r1)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f867e = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "registerSerialNumber"
            r6 = 0
            long r8 = r2.optLong(r5, r6)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f868f = r8     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "startSerialNumber"
            long r5 = r2.optLong(r5, r6)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f869g = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "restoreCount"
            int r5 = r2.optInt(r5, r1)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f870h = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r5 = "nextRestoreInterval"
            int r2 = r2.optInt(r5, r1)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            long r5 = (long) r2     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            r3.f871i = r5     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            java.lang.String r2 = r3.f863a     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
            if (r2 != 0) goto L_0x0157
            r12.add(r3)     // Catch:{ JSONException -> 0x0163, Exception -> 0x015a }
        L_0x0157:
            int r11 = r11 + 1
            goto L_0x00ec
        L_0x015a:
            r10 = move-exception
            java.lang.String r11 = "CrashUtils"
            java.lang.String r12 = "load sdk exception:"
        L_0x015f:
            com.alibaba.sdk.android.crashdefend.p014c.C0699b.m603a(r11, r12, r10)     // Catch:{ all -> 0x017b }
            goto L_0x0169
        L_0x0163:
            r10 = move-exception
            java.lang.String r11 = "CrashUtils"
            java.lang.String r12 = "load sdk json fail:"
            goto L_0x015f
        L_0x0169:
            monitor-exit(r0)
            r10 = 1
            return r10
        L_0x016c:
            if (r2 == 0) goto L_0x017a
            r2.close()     // Catch:{ IOException -> 0x0172 }
            goto L_0x017a
        L_0x0172:
            r11 = move-exception
            java.lang.String r12 = "CrashUtils"
            java.lang.String r1 = "load sdk io fail:"
            android.util.Log.e(r12, r1, r11)     // Catch:{ all -> 0x017b }
        L_0x017a:
            throw r10     // Catch:{ all -> 0x017b }
        L_0x017b:
            r10 = move-exception
            monitor-exit(r0)
            throw r10
        L_0x017e:
            monitor-exit(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.crashdefend.p014c.C0698a.m599b(android.content.Context, com.alibaba.sdk.android.crashdefend.a.a, java.util.List):boolean");
    }

    /* renamed from: c */
    private static String m600c(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return "";
        }
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return "";
    }

    /* renamed from: d */
    private static String m601d(Context context) {
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, context.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            return (String) declaredMethod.invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            Log.d("CrashUtils", "getProcessNameByActivityThread error: " + e);
            return null;
        }
    }
}
