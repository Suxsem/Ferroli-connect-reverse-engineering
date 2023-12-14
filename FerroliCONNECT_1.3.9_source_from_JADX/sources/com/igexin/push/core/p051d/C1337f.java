package com.igexin.push.core.p051d;

import com.alibaba.sdk.android.tbrest.rest.RestUrlWrapper;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.C1343f;
import com.igexin.push.p088g.p089a.C1563b;
import com.igexin.push.util.C1581f;
import com.igexin.sdk.PushBuildConfig;
import com.igexin.sdk.PushConsts;
import org.json.JSONObject;

/* renamed from: com.igexin.push.core.d.f */
public class C1337f extends C1563b {

    /* renamed from: a */
    public static final String f2085a = "com.igexin.push.core.d.f";

    public C1337f(String str) {
        super(str);
        mo14700a();
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x021b A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x023f A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x024f A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x025f A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0283 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x02a7 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x02b7 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x02cf A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x02ea A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0302 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x031a A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x032e A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0346 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0358  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x037a A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:181:0x039e A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x03b8 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x03d2 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x03f6 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x041a A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x043e A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:219:0x0462 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x0486 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x01d3 A[Catch:{ Throwable -> 0x04aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01f7 A[Catch:{ Throwable -> 0x04aa }] */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2021c(byte[] r17) {
        /*
            r16 = this;
            java.lang.String r0 = "sdk.guard.enable"
            java.lang.String r1 = "sdk.feature.setsockettimeout.enable"
            java.lang.String r2 = "sdk.feature.setheartbeatinterval.enable"
            java.lang.String r3 = "sdk.feature.settag.enable"
            java.lang.String r4 = "sdk.feature.setsilenttime.enable"
            java.lang.String r5 = "sdk.domainbackup.enable"
            java.lang.String r6 = "sdk.feature.sendmessage.enable"
            java.lang.String r7 = "sdk.uploadapplist.enable"
            java.lang.String r8 = "sdk.startservice.limit"
            java.lang.String r9 = "tag"
            java.lang.String r10 = "config"
            java.lang.String r11 = "result"
            java.lang.String r12 = new java.lang.String     // Catch:{ Throwable -> 0x04af }
            r13 = r17
            r12.<init>(r13)     // Catch:{ Throwable -> 0x04af }
            org.json.JSONObject r13 = new org.json.JSONObject     // Catch:{ Throwable -> 0x04af }
            r13.<init>(r12)     // Catch:{ Throwable -> 0x04af }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x04af }
            r12.<init>()     // Catch:{ Throwable -> 0x04af }
            java.lang.String r14 = f2085a     // Catch:{ Throwable -> 0x04af }
            r12.append(r14)     // Catch:{ Throwable -> 0x04af }
            java.lang.String r14 = "|parse sdk config from server resp = "
            r12.append(r14)     // Catch:{ Throwable -> 0x04af }
            r12.append(r13)     // Catch:{ Throwable -> 0x04af }
            java.lang.String r12 = r12.toString()     // Catch:{ Throwable -> 0x04af }
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r12)     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.core.c.h r12 = com.igexin.push.core.p050c.C1312h.m1937a()     // Catch:{ Throwable -> 0x04af }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x04af }
            r12.mo14686e((long) r14)     // Catch:{ Throwable -> 0x04af }
            boolean r12 = r13.has(r11)     // Catch:{ Throwable -> 0x04af }
            if (r12 == 0) goto L_0x04ac
            java.lang.String r12 = "ok"
            java.lang.String r11 = r13.getString(r11)     // Catch:{ Throwable -> 0x04af }
            boolean r11 = r12.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r11 == 0) goto L_0x04ac
            boolean r11 = r13.has(r10)     // Catch:{ Throwable -> 0x04af }
            if (r11 != 0) goto L_0x0062
            goto L_0x04ac
        L_0x0062:
            boolean r11 = r13.has(r9)     // Catch:{ Throwable -> 0x04af }
            if (r11 == 0) goto L_0x006e
            java.lang.String r9 = r13.getString(r9)     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1821C = r9     // Catch:{ Throwable -> 0x04af }
        L_0x006e:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Throwable -> 0x04af }
            java.lang.String r10 = r13.getString(r10)     // Catch:{ Throwable -> 0x04af }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x04af }
            boolean r10 = r9.has(r8)     // Catch:{ Throwable -> 0x04af }
            if (r10 == 0) goto L_0x0083
            java.lang.String r8 = r9.getString(r8)     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1839U = r8     // Catch:{ Throwable -> 0x04af }
        L_0x0083:
            boolean r8 = r9.has(r7)     // Catch:{ Throwable -> 0x04af }
            java.lang.String r10 = "false"
            java.lang.String r11 = "true"
            if (r8 == 0) goto L_0x00a7
            java.lang.String r7 = r9.getString(r7)     // Catch:{ Throwable -> 0x04af }
            boolean r8 = r7.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r8 != 0) goto L_0x009d
            boolean r8 = r7.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r8 == 0) goto L_0x00a7
        L_0x009d:
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x04af }
            boolean r7 = r7.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1846g = r7     // Catch:{ Throwable -> 0x04af }
        L_0x00a7:
            boolean r7 = r9.has(r6)     // Catch:{ Throwable -> 0x04af }
            if (r7 == 0) goto L_0x00c7
            java.lang.String r6 = r9.getString(r6)     // Catch:{ Throwable -> 0x04af }
            boolean r7 = r6.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r7 != 0) goto L_0x00bd
            boolean r7 = r6.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r7 == 0) goto L_0x00c7
        L_0x00bd:
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Throwable -> 0x04af }
            boolean r6 = r6.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1847h = r6     // Catch:{ Throwable -> 0x04af }
        L_0x00c7:
            boolean r6 = r9.has(r5)     // Catch:{ Throwable -> 0x04af }
            if (r6 == 0) goto L_0x00e7
            java.lang.String r5 = r9.getString(r5)     // Catch:{ Throwable -> 0x04af }
            boolean r6 = r5.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r6 != 0) goto L_0x00dd
            boolean r6 = r5.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r6 == 0) goto L_0x00e7
        L_0x00dd:
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ Throwable -> 0x04af }
            boolean r5 = r5.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1845f = r5     // Catch:{ Throwable -> 0x04af }
        L_0x00e7:
            boolean r5 = r9.has(r4)     // Catch:{ Throwable -> 0x04af }
            if (r5 == 0) goto L_0x011b
            java.lang.String r4 = r9.getString(r4)     // Catch:{ Throwable -> 0x04af }
            boolean r5 = r4.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r5 != 0) goto L_0x00fd
            boolean r5 = r4.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r5 == 0) goto L_0x011b
        L_0x00fd:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x04af }
            boolean r4 = r4.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1849j = r4     // Catch:{ Throwable -> 0x04af }
            boolean r4 = com.igexin.push.config.C1234k.f1849j     // Catch:{ Throwable -> 0x04af }
            if (r4 != 0) goto L_0x011b
            int r4 = com.igexin.push.config.C1234k.f1841b     // Catch:{ Throwable -> 0x04af }
            if (r4 == 0) goto L_0x011b
            com.igexin.push.core.q r4 = com.igexin.push.core.C1354q.m2102a()     // Catch:{ Throwable -> 0x04af }
            r5 = 12
            r6 = 0
            java.lang.String r7 = "server"
            r4.mo14752a((int) r5, (int) r6, (java.lang.String) r7)     // Catch:{ Throwable -> 0x04af }
        L_0x011b:
            boolean r4 = r9.has(r3)     // Catch:{ Throwable -> 0x04af }
            if (r4 == 0) goto L_0x013b
            java.lang.String r3 = r9.getString(r3)     // Catch:{ Throwable -> 0x04af }
            boolean r4 = r3.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r4 != 0) goto L_0x0131
            boolean r4 = r3.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r4 == 0) goto L_0x013b
        L_0x0131:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ Throwable -> 0x04af }
            boolean r3 = r3.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1848i = r3     // Catch:{ Throwable -> 0x04af }
        L_0x013b:
            boolean r3 = r9.has(r2)     // Catch:{ Throwable -> 0x04af }
            if (r3 == 0) goto L_0x015b
            java.lang.String r2 = r9.getString(r2)     // Catch:{ Throwable -> 0x04af }
            boolean r3 = r2.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r3 != 0) goto L_0x0151
            boolean r3 = r2.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r3 == 0) goto L_0x015b
        L_0x0151:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Throwable -> 0x04af }
            boolean r2 = r2.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1850k = r2     // Catch:{ Throwable -> 0x04af }
        L_0x015b:
            boolean r2 = r9.has(r1)     // Catch:{ Throwable -> 0x04af }
            if (r2 == 0) goto L_0x017b
            java.lang.String r1 = r9.getString(r1)     // Catch:{ Throwable -> 0x04af }
            boolean r2 = r1.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r2 != 0) goto L_0x0171
            boolean r2 = r1.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r2 == 0) goto L_0x017b
        L_0x0171:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ Throwable -> 0x04af }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1851l = r1     // Catch:{ Throwable -> 0x04af }
        L_0x017b:
            boolean r1 = r9.has(r0)     // Catch:{ Throwable -> 0x04af }
            if (r1 == 0) goto L_0x019b
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04af }
            boolean r1 = r0.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r1 != 0) goto L_0x0191
            boolean r1 = r0.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r1 == 0) goto L_0x019b
        L_0x0191:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04af }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1852m = r0     // Catch:{ Throwable -> 0x04af }
        L_0x019b:
            java.lang.String r0 = "sdk.needlook.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04af }
            if (r0 == 0) goto L_0x01c9
            java.lang.String r0 = "sdk.needlook.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04af }
            boolean r1 = r0.equals(r11)     // Catch:{ Throwable -> 0x04af }
            if (r1 != 0) goto L_0x01b5
            boolean r1 = r0.equals(r10)     // Catch:{ Throwable -> 0x04af }
            if (r1 == 0) goto L_0x01c9
        L_0x01b5:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04af }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x04af }
            com.igexin.push.config.C1234k.f1859t = r1     // Catch:{ Throwable -> 0x04af }
            byte[] r0 = r0.getBytes()     // Catch:{ Throwable -> 0x04af }
            r1 = r16
            r1.m2022d(r0)     // Catch:{ Throwable -> 0x04aa }
            goto L_0x01cb
        L_0x01c9:
            r1 = r16
        L_0x01cb:
            java.lang.String r0 = "sdk.report.initialize.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x01ef
            java.lang.String r0 = "sdk.report.initialize.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x01e5
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x01ef
        L_0x01e5:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1860u = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x01ef:
            java.lang.String r0 = "sdk.wakeupsdk.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0213
            java.lang.String r0 = "sdk.wakeupsdk.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0209
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x0213
        L_0x0209:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1853n = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0213:
            java.lang.String r0 = "sdk.feature.feedback.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0237
            java.lang.String r0 = "sdk.feature.feedback.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x022d
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x0237
        L_0x022d:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1854o = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0237:
            java.lang.String r0 = "sdk.watchout.app"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0247
            java.lang.String r0 = "sdk.watchout.app"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1855p = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0247:
            java.lang.String r0 = "sdk.watchout.service"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0257
            java.lang.String r0 = "sdk.watchout.service"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1856q = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0257:
            java.lang.String r0 = "sdk.daemon.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x027b
            java.lang.String r0 = "sdk.daemon.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0271
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x027b
        L_0x0271:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1858s = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x027b:
            java.lang.String r0 = "sdk.guardactivity.first"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x029f
            java.lang.String r0 = "sdk.guardactivity.first"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0295
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x029f
        L_0x0295:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1822D = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x029f:
            java.lang.String r0 = "sdk.guard.blacklist"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x02af
            java.lang.String r0 = "sdk.guard.blacklist"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1823E = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x02af:
            java.lang.String r0 = "sdk.polling.dis.cnt"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x02c7
            java.lang.String r0 = "sdk.polling.dis.cnt"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1824F = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x02c7:
            java.lang.String r0 = "sdk.polling.login.interval"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x02e2
            java.lang.String r0 = "sdk.polling.login.interval"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x04aa }
            int r0 = r0 * 1000
            long r2 = (long) r0     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1825G = r2     // Catch:{ Throwable -> 0x04aa }
        L_0x02e2:
            java.lang.String r0 = "sdk.polling.exit.heartbeat.cnt"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x02fa
            java.lang.String r0 = "sdk.polling.exit.heartbeat.cnt"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1826H = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x02fa:
            java.lang.String r0 = "sdk.reset.reconnect.delay"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0312
            java.lang.String r0 = "sdk.reset.reconnect.delay"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            long r2 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0312 }
            r4 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            com.igexin.push.config.C1234k.f1861v = r2     // Catch:{ Exception -> 0x0312 }
        L_0x0312:
            java.lang.String r0 = "sdk.guard.maxcnt"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0326
            java.lang.String r0 = "sdk.guard.maxcnt"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ Exception -> 0x0326 }
            com.igexin.push.config.C1234k.f1820B = r0     // Catch:{ Exception -> 0x0326 }
        L_0x0326:
            java.lang.String r0 = "sdk.httpdata.maxsize"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x033e
            java.lang.String r0 = "sdk.httpdata.maxsize"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1827I = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x033e:
            java.lang.String r0 = "sdk.hide.righticon.blacklist"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x034e
            java.lang.String r0 = "sdk.hide.righticon.blacklist"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1828J = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x034e:
            java.lang.String r0 = "sdk.taskid.blacklist"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            java.lang.String r2 = "none"
            if (r0 == 0) goto L_0x0372
            java.lang.String r0 = "sdk.taskid.blacklist"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1829K = r0     // Catch:{ Throwable -> 0x04aa }
            java.lang.String r0 = com.igexin.push.config.C1234k.f1829K     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 != 0) goto L_0x0370
            com.igexin.push.core.r r0 = com.igexin.push.core.C1355r.m2114a()     // Catch:{ Throwable -> 0x04aa }
            r0.mo14775d()     // Catch:{ Throwable -> 0x04aa }
            goto L_0x0372
        L_0x0370:
            com.igexin.push.config.C1234k.f1829K = r2     // Catch:{ Throwable -> 0x04aa }
        L_0x0372:
            java.lang.String r0 = "sdk.applink.feedback.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0396
            java.lang.String r0 = "sdk.applink.feedback.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r3 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r3 != 0) goto L_0x038c
            boolean r3 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r3 == 0) goto L_0x0396
        L_0x038c:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1830L = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0396:
            java.lang.String r0 = "sdk.applink.domains"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x03b0
            java.lang.String r0 = "sdk.applink.domains"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1831M = r0     // Catch:{ Throwable -> 0x04aa }
            java.lang.String r0 = com.igexin.push.config.C1234k.f1831M     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x03b0
            com.igexin.push.config.C1234k.f1831M = r2     // Catch:{ Throwable -> 0x04aa }
        L_0x03b0:
            java.lang.String r0 = "sdk.del.alarm.brand"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x03ca
            java.lang.String r0 = "sdk.del.alarm.brand"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1832N = r0     // Catch:{ Throwable -> 0x04aa }
            java.lang.String r0 = com.igexin.push.config.C1234k.f1832N     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x03ca
            com.igexin.push.config.C1234k.f1832N = r2     // Catch:{ Throwable -> 0x04aa }
        L_0x03ca:
            java.lang.String r0 = "sdk.miuipush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x03ee
            java.lang.String r0 = "sdk.miuipush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x03e4
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x03ee
        L_0x03e4:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1833O = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x03ee:
            java.lang.String r0 = "sdk.flymepush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0412
            java.lang.String r0 = "sdk.flymepush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0408
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x0412
        L_0x0408:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1834P = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0412:
            java.lang.String r0 = "sdk.hmspush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x0436
            java.lang.String r0 = "sdk.hmspush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x042c
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x0436
        L_0x042c:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1835Q = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x0436:
            java.lang.String r0 = "sdk.colorospush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x045a
            java.lang.String r0 = "sdk.colorospush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0450
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x045a
        L_0x0450:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1836R = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x045a:
            java.lang.String r0 = "sdk.vivopush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x047e
            java.lang.String r0 = "sdk.vivopush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0474
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x047e
        L_0x0474:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1838T = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x047e:
            java.lang.String r0 = "sdk.fcmpush.enable"
            boolean r0 = r9.has(r0)     // Catch:{ Throwable -> 0x04aa }
            if (r0 == 0) goto L_0x04a2
            java.lang.String r0 = "sdk.fcmpush.enable"
            java.lang.String r0 = r9.getString(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r2 = r0.equals(r11)     // Catch:{ Throwable -> 0x04aa }
            if (r2 != 0) goto L_0x0498
            boolean r2 = r0.equals(r10)     // Catch:{ Throwable -> 0x04aa }
            if (r2 == 0) goto L_0x04a2
        L_0x0498:
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)     // Catch:{ Throwable -> 0x04aa }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x04aa }
            com.igexin.push.config.C1234k.f1837S = r0     // Catch:{ Throwable -> 0x04aa }
        L_0x04a2:
            com.igexin.push.config.a r0 = com.igexin.push.config.C1224a.m1601a()     // Catch:{ Throwable -> 0x04aa }
            r0.mo14451f()     // Catch:{ Throwable -> 0x04aa }
            goto L_0x04cf
        L_0x04aa:
            r0 = move-exception
            goto L_0x04b2
        L_0x04ac:
            r1 = r16
            return
        L_0x04af:
            r0 = move-exception
            r1 = r16
        L_0x04b2:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = f2085a
            r2.append(r3)
            java.lang.String r3 = "|"
            r2.append(r3)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r0)
        L_0x04cf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.core.p051d.C1337f.m2021c(byte[]):void");
    }

    /* renamed from: d */
    private void m2022d(byte[] bArr) {
        String str = C1343f.f2169f.getFilesDir().getPath() + "/conf_n.pid";
        C1179b.m1354a(f2085a + "|writeNeedLook " + str);
        C1581f.m3232a(bArr, str, false);
    }

    /* renamed from: a */
    public void mo14700a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PushConsts.CMD_ACTION, "sdkconfig");
            jSONObject.put("cid", C1343f.f2182s);
            jSONObject.put("appid", C1343f.f2135a);
            jSONObject.put(RestUrlWrapper.FIELD_SDK_VERSION, PushBuildConfig.sdk_conf_version);
            jSONObject.put("tag", C1234k.f1821C);
            mo15202b(jSONObject.toString().getBytes());
        } catch (Exception unused) {
        }
    }

    /* renamed from: a */
    public void mo14696a(byte[] bArr) {
        if (bArr != null) {
            m2021c(bArr);
        }
    }

    /* renamed from: b */
    public int mo14231b() {
        return 0;
    }
}
