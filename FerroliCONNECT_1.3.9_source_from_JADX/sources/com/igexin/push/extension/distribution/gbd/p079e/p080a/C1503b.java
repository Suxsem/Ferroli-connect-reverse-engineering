package com.igexin.push.extension.distribution.gbd.p079e.p080a;

import android.database.Cursor;
import android.support.p000v4.app.NotificationCompat;
import android.text.TextUtils;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.distribution.gbd.p076b.C1484d;
import com.igexin.push.extension.distribution.gbd.p076b.C1486f;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.C1501a;
import com.igexin.push.extension.distribution.gbd.p081f.C1509a;
import com.igexin.push.extension.distribution.gbd.p081f.C1515c;
import com.igexin.push.extension.distribution.gbd.p081f.p082a.C1513d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1550r;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: com.igexin.push.extension.distribution.gbd.e.a.b */
public class C1503b {

    /* renamed from: a */
    private static C1503b f2889a;

    /* renamed from: d */
    private static final Object f2890d = new Object();

    /* renamed from: b */
    private List<C1486f> f2891b = new ArrayList();

    /* renamed from: c */
    private boolean f2892c = false;

    /* renamed from: e */
    private Comparator<C1486f> f2893e = new C1504c(this);

    /* renamed from: f */
    private C1515c f2894f = new C1505d(this);

    /* renamed from: g */
    private C1515c f2895g = new C1506e(this);

    /* renamed from: a */
    public static C1503b m2819a() {
        if (f2889a == null) {
            f2889a = new C1503b();
        }
        return f2889a;
    }

    /* renamed from: a */
    private List<C1486f> m2820a(List<C1486f> list, int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (C1486f next : list) {
            if (i2 < 10 && next.mo15071b() == i) {
                arrayList.add(next);
                i2++;
            }
        }
        return arrayList;
    }

    /* renamed from: a */
    private void m2821a(int i, StringBuilder sb, List<C1486f> list) {
        try {
            C1513d dVar = new C1513d(sb.toString().getBytes("UTF-8"), i, list);
            dVar.mo15142a(this.f2895g);
            if (!C1174c.m1310b().mo14317a(new C1509a(dVar), false, true)) {
                this.f2892c = false;
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
            this.f2892c = false;
        }
    }

    /* renamed from: a */
    private void m2823a(HashMap<String, C1484d> hashMap, int i, int i2, int i3) {
        String str;
        String str2;
        String str3;
        if (C1343f.f2182s != null) {
            String str4 = "";
            if (hashMap != null) {
                String str5 = str4;
                str3 = str5;
                str2 = str3;
                str = str2;
                for (String next : hashMap.keySet()) {
                    C1484d dVar = hashMap.get(next);
                    String str6 = C1490c.f2760n.get(next);
                    if (TextUtils.isEmpty(str6)) {
                        C1540h.m2995a("GBD_RALDataManager", "package name is empty");
                    } else if (!TextUtils.isEmpty(str5) || !TextUtils.isEmpty(str3) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str)) {
                        str5 = str5 + MqttTopic.MULTI_LEVEL_WILDCARD + str6;
                        str2 = str2 + "," + dVar.mo15054a();
                        str = str + "," + dVar.mo15058c();
                        str3 = str3 + "," + dVar.mo15056b();
                    } else {
                        str = dVar.mo15058c() + str4;
                        str2 = dVar.mo15054a() + str4;
                        str3 = dVar.mo15056b() + str4;
                        str5 = str6;
                    }
                }
                str4 = str5;
            } else {
                str3 = str4;
                str2 = str3;
                str = str2;
            }
            if (i != 0 || i2 != 0 || !TextUtils.isEmpty(str4) || !TextUtils.isEmpty(str3) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str)) {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(C1541i.m3034i()));
                StringBuilder sb = new StringBuilder();
                sb.append(format);
                String str7 = "|";
                sb.append(str7);
                sb.append(C1343f.f2182s);
                sb.append(str7);
                sb.append(C1343f.f2135a);
                sb.append(str7);
                sb.append(i3);
                sb.append(str7);
                sb.append(str4);
                sb.append(str7);
                sb.append(C1343f.f2184u);
                if (i3 == 1 || i3 == 3) {
                    sb.append(str7);
                    sb.append(str2);
                    sb.append(str7);
                    sb.append(str7);
                    sb.append(str3);
                    sb.append(str7);
                    sb.append(str7);
                    sb.append(str);
                    sb.append(str7);
                    sb.append(C1343f.f2187x);
                } else {
                    sb.append(str7);
                    sb.append(i2);
                    sb.append(str7);
                    sb.append(i);
                    sb.append(str7);
                    sb.append(str3);
                    sb.append(str7);
                    sb.append(str2);
                    sb.append(str7);
                    sb.append(str);
                    sb.append(str7);
                    sb.append(C1343f.f2187x);
                    sb.append(str7);
                    if (str4.contains("com.sina.weibo")) {
                        str7 = C1550r.m3044a().mo15182b();
                    }
                    mo15111b(sb.toString(), 19);
                }
                sb.append(str7);
                mo15111b(sb.toString(), 19);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2824a(java.util.List<com.igexin.push.extension.distribution.gbd.p076b.C1486f> r7) {
        /*
            r6 = this;
            java.lang.Object r0 = f2890d
            monitor-enter(r0)
            if (r7 == 0) goto L_0x0045
            boolean r1 = r7.isEmpty()     // Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x000c
            goto L_0x0045
        L_0x000c:
            int r1 = r7.size()     // Catch:{ all -> 0x0047 }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x0047 }
            r2 = 0
            java.util.Iterator r7 = r7.iterator()     // Catch:{ all -> 0x0047 }
        L_0x0017:
            boolean r3 = r7.hasNext()     // Catch:{ all -> 0x0047 }
            if (r3 == 0) goto L_0x0036
            java.lang.Object r3 = r7.next()     // Catch:{ all -> 0x0047 }
            com.igexin.push.extension.distribution.gbd.b.f r3 = (com.igexin.push.extension.distribution.gbd.p076b.C1486f) r3     // Catch:{ all -> 0x0047 }
            int r4 = r3.mo15067a()     // Catch:{ all -> 0x0047 }
            int r5 = r2 + 1
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0047 }
            r1[r2] = r4     // Catch:{ all -> 0x0047 }
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r2 = r6.f2891b     // Catch:{ all -> 0x0047 }
            r2.remove(r3)     // Catch:{ all -> 0x0047 }
            r2 = r5
            goto L_0x0017
        L_0x0036:
            com.igexin.push.extension.distribution.gbd.e.a r7 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ all -> 0x0047 }
            java.lang.String r2 = "ral"
            java.lang.String r3 = "id"
            java.lang.String[] r3 = new java.lang.String[]{r3}     // Catch:{ all -> 0x0047 }
            r7.mo15100a((java.lang.String) r2, (java.lang.String[]) r3, (java.lang.String[]) r1)     // Catch:{ all -> 0x0047 }
            monitor-exit(r0)     // Catch:{ all -> 0x0047 }
            return
        L_0x0045:
            monitor-exit(r0)     // Catch:{ all -> 0x0047 }
            return
        L_0x0047:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0047 }
            goto L_0x004b
        L_0x004a:
            throw r7
        L_0x004b:
            goto L_0x004a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b.m2824a(java.util.List):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m2825a(List<C1486f> list, boolean z) {
        if (list != null) {
            try {
                int b = list.get(0).mo15071b();
                if (z) {
                    int size = list.size();
                    m2824a(list);
                    if (size >= 10 || b != 6) {
                        if (size >= 10 || b != 11) {
                            if (size >= 10 || b != 14) {
                                if (size >= 10 || b != 19) {
                                    if (size >= 10 || b != 24) {
                                        if (size >= 10 || b != 27) {
                                            if (size >= 10 || b != 28) {
                                                if (size >= 10 || b != 31) {
                                                    if (size >= 10 || b != 34) {
                                                        if (size >= 10 || b != 43) {
                                                            m2826b(b);
                                                            return;
                                                        }
                                                    }
                                                    m2826b(43);
                                                    return;
                                                }
                                                m2826b(34);
                                                return;
                                            }
                                            m2826b(31);
                                            return;
                                        }
                                        m2826b(28);
                                        return;
                                    }
                                    m2826b(27);
                                    return;
                                }
                                m2826b(24);
                                return;
                            }
                            m2826b(19);
                            return;
                        }
                        m2826b(14);
                        return;
                    }
                } else {
                    m2827b(list);
                    if (b != 6) {
                        if (b == 11) {
                            m2826b(14);
                            return;
                        } else if (b == 14) {
                            m2826b(19);
                            return;
                        } else if (b == 19) {
                            m2826b(24);
                            return;
                        } else if (b == 24) {
                            m2826b(27);
                            return;
                        } else if (b == 27) {
                            m2826b(28);
                            return;
                        } else if (b == 28) {
                            m2826b(31);
                            return;
                        } else if (b == 31) {
                            m2826b(34);
                            return;
                        } else if (b == 34) {
                            m2826b(43);
                            return;
                        } else if (b != 43) {
                            return;
                        }
                    }
                }
                m2826b(11);
                return;
            } catch (Exception e) {
                C1540h.m2996a(e);
                return;
            }
        }
        this.f2892c = false;
    }

    /* renamed from: b */
    private void m2826b(int i) {
        String c;
        try {
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList();
            List<C1486f> a = m2820a(this.f2891b, i);
            if (!a.isEmpty()) {
                for (int i2 = 0; i2 < a.size(); i2++) {
                    C1486f fVar = a.get(i2);
                    if (i2 < a.size() - 1) {
                        sb.append(fVar.mo15073c());
                        c = "\n";
                    } else {
                        c = fVar.mo15073c();
                    }
                    sb.append(c);
                    arrayList.add(fVar);
                }
                m2821a(i, sb, (List<C1486f>) arrayList);
                return;
            }
            int i3 = 11;
            if (i != 6) {
                int i4 = 14;
                if (i != 11) {
                    i3 = 19;
                    if (i != 14) {
                        i4 = 24;
                        if (i != 19) {
                            i3 = 27;
                            if (i != 24) {
                                i4 = 28;
                                if (i != 27) {
                                    i3 = 31;
                                    if (i != 28) {
                                        i4 = 34;
                                        if (i != 31) {
                                            i3 = 43;
                                            if (i != 34) {
                                                if (i == 43) {
                                                    this.f2892c = false;
                                                    return;
                                                }
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                m2826b(i4);
                return;
            }
            m2826b(i3);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* renamed from: b */
    private void m2827b(List<C1486f> list) {
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList(list);
                    for (C1486f next : list) {
                        int d = next.mo15075d() + 1;
                        next.mo15074c(d);
                        C1540h.m2997b("GBD_RALDataManager", " id = " + next.mo15067a() + "  num = " + d);
                        if (d >= C1488a.f2654aK) {
                            arrayList.add(next);
                            arrayList2.remove(next);
                        }
                    }
                    if (arrayList2.size() > 0) {
                        m2829c(arrayList2);
                    }
                    if (arrayList.size() > 0) {
                        m2824a((List<C1486f>) arrayList);
                    }
                }
            } catch (Throwable th) {
                C1540h.m2996a(th);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.String[], android.database.Cursor] */
    /* renamed from: c */
    private int m2828c(String str, int i) {
        ? r1 = 0;
        int i2 = 0;
        try {
            Cursor a = C1490c.f2748b.mo15096a("select count(value) c from look where t < '" + str + "' and " + NotificationCompat.CATEGORY_STATUS + " = " + 4 + " and " + "type" + " = " + i, (String[]) r1);
            if (a != null && a.getCount() > 0 && a.moveToFirst()) {
                i2 = a.getInt(0);
            }
            if (a != null) {
                a.close();
            }
            return i2;
        } catch (Exception e) {
            C1540h.m2996a(e);
            if (r1 != 0) {
                r1.close();
            }
            return 0;
        } catch (Throwable th) {
            if (r1 != 0) {
                r1.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0081, code lost:
        return;
     */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2829c(java.util.List<com.igexin.push.extension.distribution.gbd.p076b.C1486f> r11) {
        /*
            r10 = this;
            java.lang.Object r0 = f2890d
            monitor-enter(r0)
            if (r11 == 0) goto L_0x0080
            boolean r1 = r11.isEmpty()     // Catch:{ all -> 0x0082 }
            if (r1 == 0) goto L_0x000c
            goto L_0x0080
        L_0x000c:
            int r1 = r11.size()     // Catch:{ all -> 0x0082 }
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x0082 }
            int r2 = r11.size()     // Catch:{ all -> 0x0082 }
            int[] r2 = new int[r2]     // Catch:{ all -> 0x0082 }
            java.util.Iterator r11 = r11.iterator()     // Catch:{ all -> 0x0082 }
            r3 = 0
            r4 = 0
        L_0x001e:
            boolean r5 = r11.hasNext()     // Catch:{ all -> 0x0082 }
            if (r5 == 0) goto L_0x0053
            java.lang.Object r5 = r11.next()     // Catch:{ all -> 0x0082 }
            com.igexin.push.extension.distribution.gbd.b.f r5 = (com.igexin.push.extension.distribution.gbd.p076b.C1486f) r5     // Catch:{ all -> 0x0082 }
            int r6 = r5.mo15067a()     // Catch:{ all -> 0x0082 }
            java.lang.String r7 = java.lang.String.valueOf(r6)     // Catch:{ all -> 0x0082 }
            r1[r4] = r7     // Catch:{ all -> 0x0082 }
            int r5 = r5.mo15075d()     // Catch:{ all -> 0x0082 }
            r2[r4] = r5     // Catch:{ all -> 0x0082 }
            int r4 = r4 + 1
            java.lang.String r5 = "GBD_RALDataManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0082 }
            r7.<init>()     // Catch:{ all -> 0x0082 }
            java.lang.String r8 = "update ral id = "
            r7.append(r8)     // Catch:{ all -> 0x0082 }
            r7.append(r6)     // Catch:{ all -> 0x0082 }
            java.lang.String r6 = r7.toString()     // Catch:{ all -> 0x0082 }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r5, r6)     // Catch:{ all -> 0x0082 }
            goto L_0x001e
        L_0x0053:
            r11 = 0
        L_0x0054:
            int r4 = r1.length     // Catch:{ all -> 0x0082 }
            if (r11 >= r4) goto L_0x007e
            r4 = r1[r11]     // Catch:{ all -> 0x0082 }
            android.content.ContentValues r5 = new android.content.ContentValues     // Catch:{ all -> 0x0082 }
            r5.<init>()     // Catch:{ all -> 0x0082 }
            java.lang.String r6 = "num"
            r7 = r2[r11]     // Catch:{ all -> 0x0082 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x0082 }
            r5.put(r6, r7)     // Catch:{ all -> 0x0082 }
            com.igexin.push.extension.distribution.gbd.e.a r6 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ all -> 0x0082 }
            java.lang.String r7 = "ral"
            java.lang.String r8 = "id"
            java.lang.String[] r8 = new java.lang.String[]{r8}     // Catch:{ all -> 0x0082 }
            r9 = 1
            java.lang.String[] r9 = new java.lang.String[r9]     // Catch:{ all -> 0x0082 }
            r9[r3] = r4     // Catch:{ all -> 0x0082 }
            r6.mo15097a(r7, r5, r8, r9)     // Catch:{ all -> 0x0082 }
            int r11 = r11 + 1
            goto L_0x0054
        L_0x007e:
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            return
        L_0x0080:
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            return
        L_0x0082:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            goto L_0x0086
        L_0x0085:
            throw r11
        L_0x0086:
            goto L_0x0085
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b.m2829c(java.util.List):void");
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* renamed from: d */
    private int m2830d(String str, int i) {
        ? r0 = 0;
        try {
            Cursor a = C1490c.f2748b.mo15096a("select count(value) c from look where  t < '" + str + "' and " + NotificationCompat.CATEGORY_STATUS + " = " + 3 + " and type = " + i, (String[]) r0);
            int i2 = (a == null || !a.moveToFirst()) ? 0 : a.getInt(0);
            C1540h.m2995a("GBD_RALDataManager", "screen notes count = " + i2);
            if (a != null) {
                a.close();
            }
            return i2;
        } catch (Exception e) {
            C1540h.m2996a(e);
            if (r0 != 0) {
                r0.close();
            }
            return 0;
        } catch (Throwable th) {
            if (r0 != 0) {
                r0.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00bb, code lost:
        if (r1 != null) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00c5, code lost:
        if (r1 != null) goto L_0x00c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00c7, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ca, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00cf  */
    /* renamed from: e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.HashMap<java.lang.String, com.igexin.push.extension.distribution.gbd.p076b.C1484d> m2831e(java.lang.String r8, int r9) {
        /*
            r7 = this;
            java.lang.String r0 = ","
            java.lang.String r1 = "status"
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.<init>()     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r4 = "select count(value) c, value, t, type, status from look where t < '"
            r3.append(r4)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r8 = "' and "
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r8 = "type"
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r8 = " = "
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r9)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r8 = " and "
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r1)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r8 = " in("
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r8 = 0
            r3.append(r8)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r0)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r9 = 1
            r3.append(r9)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r0)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r0 = 2
            r3.append(r0)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r4 = ") "
            r3.append(r4)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r4 = " group by "
            r3.append(r4)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r4 = "value"
            r3.append(r4)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r4 = ", "
            r3.append(r4)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            r3.append(r1)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            com.igexin.push.extension.distribution.gbd.e.a r3 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            android.database.Cursor r1 = r3.mo15096a((java.lang.String) r1, (java.lang.String[]) r2)     // Catch:{ Exception -> 0x00c0, all -> 0x00be }
            if (r1 == 0) goto L_0x00bb
            boolean r3 = r1.moveToFirst()     // Catch:{ Exception -> 0x00b9 }
            if (r3 == 0) goto L_0x00bb
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Exception -> 0x00b9 }
            r3.<init>()     // Catch:{ Exception -> 0x00b9 }
        L_0x0071:
            java.lang.String r4 = r1.getString(r9)     // Catch:{ Exception -> 0x00b9 }
            r5 = 4
            int r5 = r1.getInt(r5)     // Catch:{ Exception -> 0x00b9 }
            boolean r6 = r3.containsKey(r4)     // Catch:{ Exception -> 0x00b9 }
            if (r6 == 0) goto L_0x0087
            java.lang.Object r4 = r3.get(r4)     // Catch:{ Exception -> 0x00b9 }
            com.igexin.push.extension.distribution.gbd.b.d r4 = (com.igexin.push.extension.distribution.gbd.p076b.C1484d) r4     // Catch:{ Exception -> 0x00b9 }
            goto L_0x0090
        L_0x0087:
            com.igexin.push.extension.distribution.gbd.b.d r6 = new com.igexin.push.extension.distribution.gbd.b.d     // Catch:{ Exception -> 0x00b9 }
            r6.<init>()     // Catch:{ Exception -> 0x00b9 }
            r3.put(r4, r6)     // Catch:{ Exception -> 0x00b9 }
            r4 = r6
        L_0x0090:
            if (r5 != 0) goto L_0x009a
            int r5 = r1.getInt(r8)     // Catch:{ Exception -> 0x00b9 }
            r4.mo15055a(r5)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00ad
        L_0x009a:
            if (r5 != r9) goto L_0x00a4
            int r5 = r1.getInt(r8)     // Catch:{ Exception -> 0x00b9 }
            r4.mo15059c(r5)     // Catch:{ Exception -> 0x00b9 }
            goto L_0x00ad
        L_0x00a4:
            if (r5 != r0) goto L_0x00ad
            int r5 = r1.getInt(r8)     // Catch:{ Exception -> 0x00b9 }
            r4.mo15057b(r5)     // Catch:{ Exception -> 0x00b9 }
        L_0x00ad:
            boolean r4 = r1.moveToNext()     // Catch:{ Exception -> 0x00b9 }
            if (r4 != 0) goto L_0x0071
            if (r1 == 0) goto L_0x00b8
            r1.close()
        L_0x00b8:
            return r3
        L_0x00b9:
            r8 = move-exception
            goto L_0x00c2
        L_0x00bb:
            if (r1 == 0) goto L_0x00ca
            goto L_0x00c7
        L_0x00be:
            r8 = move-exception
            goto L_0x00cd
        L_0x00c0:
            r8 = move-exception
            r1 = r2
        L_0x00c2:
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r8)     // Catch:{ all -> 0x00cb }
            if (r1 == 0) goto L_0x00ca
        L_0x00c7:
            r1.close()
        L_0x00ca:
            return r2
        L_0x00cb:
            r8 = move-exception
            r2 = r1
        L_0x00cd:
            if (r2 == 0) goto L_0x00d2
            r2.close()
        L_0x00d2:
            goto L_0x00d4
        L_0x00d3:
            throw r8
        L_0x00d4:
            goto L_0x00d3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b.m2831e(java.lang.String, int):java.util.HashMap");
    }

    /* renamed from: a */
    public void mo15108a(int i) {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        try {
            m2823a(m2831e(format, i), m2828c(format, i), m2830d(format, i), i);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
        C1501a aVar = C1490c.f2748b;
        aVar.mo15098a("look", "t<'" + format + "' and " + "type" + " = " + i);
    }

    /* renamed from: a */
    public void mo15109a(String str, int i) {
        try {
            if (!C1541i.m3022d(C1490c.f2747a)) {
                C1540h.m2997b("GBD_RALDataManager", "ral instant r no network.");
                mo15111b(str, i);
                return;
            }
            C1486f fVar = new C1486f();
            fVar.mo15072b(i);
            fVar.mo15070a(str);
            ArrayList arrayList = new ArrayList();
            arrayList.add(fVar);
            C1513d dVar = new C1513d(str.getBytes("UTF-8"), i, arrayList);
            dVar.mo15142a(this.f2894f);
            if (!C1174c.m1310b().mo14317a(new C1509a(dVar), true, true)) {
                mo15111b(str, i);
                C1540h.m2997b("GBD_RALDataManager", "instant bir requestService false.");
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v1, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r0v2, types: [android.database.Cursor] */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo15110b() {
        /*
            r8 = this;
            r0 = 0
            java.lang.String r1 = "select id, key, value, t, num from ral order by id"
            com.igexin.push.extension.distribution.gbd.e.a r2 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ Exception -> 0x0052 }
            android.database.Cursor r0 = r2.mo15096a((java.lang.String) r1, (java.lang.String[]) r0)     // Catch:{ Exception -> 0x0052 }
            if (r0 == 0) goto L_0x004d
        L_0x000b:
            boolean r1 = r0.moveToNext()     // Catch:{ Exception -> 0x0052 }
            if (r1 == 0) goto L_0x004d
            r1 = 0
            int r1 = r0.getInt(r1)     // Catch:{ Exception -> 0x0052 }
            r2 = 1
            int r2 = r0.getInt(r2)     // Catch:{ Exception -> 0x0052 }
            r3 = 2
            byte[] r3 = r0.getBlob(r3)     // Catch:{ Exception -> 0x0052 }
            byte[] r3 = com.igexin.p032b.p042b.C1196a.m1439c(r3)     // Catch:{ Exception -> 0x0052 }
            r4 = 3
            long r4 = r0.getLong(r4)     // Catch:{ Exception -> 0x0052 }
            r6 = 4
            int r6 = r0.getInt(r6)     // Catch:{ Exception -> 0x0052 }
            com.igexin.push.extension.distribution.gbd.b.f r7 = new com.igexin.push.extension.distribution.gbd.b.f     // Catch:{ Exception -> 0x0052 }
            r7.<init>()     // Catch:{ Exception -> 0x0052 }
            r7.mo15068a((int) r1)     // Catch:{ Exception -> 0x0052 }
            r7.mo15072b(r2)     // Catch:{ Exception -> 0x0052 }
            java.lang.String r1 = new java.lang.String     // Catch:{ Exception -> 0x0052 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0052 }
            r7.mo15070a((java.lang.String) r1)     // Catch:{ Exception -> 0x0052 }
            r7.mo15069a((long) r4)     // Catch:{ Exception -> 0x0052 }
            r7.mo15074c(r6)     // Catch:{ Exception -> 0x0052 }
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r1 = r8.f2891b     // Catch:{ Exception -> 0x0052 }
            r1.add(r7)     // Catch:{ Exception -> 0x0052 }
            goto L_0x000b
        L_0x004d:
            if (r0 == 0) goto L_0x005b
            goto L_0x0058
        L_0x0050:
            r1 = move-exception
            goto L_0x005c
        L_0x0052:
            r1 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r1)     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x005b
        L_0x0058:
            r0.close()
        L_0x005b:
            return
        L_0x005c:
            if (r0 == 0) goto L_0x0061
            r0.close()
        L_0x0061:
            goto L_0x0063
        L_0x0062:
            throw r1
        L_0x0063:
            goto L_0x0062
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b.mo15110b():void");
    }

    /* JADX WARNING: type inference failed for: r4v2, types: [java.lang.String[], android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r4v3, types: [android.database.Cursor] */
    /* JADX WARNING: type inference failed for: r4v4, types: [android.database.Cursor] */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x00c7, code lost:
        if (r4 != 0) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00dc, code lost:
        if (r4 == 0) goto L_0x00e5;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo15111b(java.lang.String r10, int r11) {
        /*
            r9 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "save type = "
            r0.append(r1)
            r0.append(r11)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "GBD_RALDataManager"
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r1, r0)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "save type = "
            r0.append(r1)
            r0.append(r11)
            java.lang.String r1 = " value = "
            r0.append(r1)
            r0.append(r10)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "GBD_RALDataManager"
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r1, r0)
            java.lang.Object r0 = f2890d
            monitor-enter(r0)
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r1 = r9.f2891b     // Catch:{ all -> 0x00e7 }
            int r1 = r1.size()     // Catch:{ all -> 0x00e7 }
            int r2 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2715u     // Catch:{ all -> 0x00e7 }
            r3 = 0
            if (r1 < r2) goto L_0x005a
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r1 = r9.f2891b     // Catch:{ all -> 0x00e7 }
            java.util.Comparator<com.igexin.push.extension.distribution.gbd.b.f> r2 = r9.f2893e     // Catch:{ all -> 0x00e7 }
            java.util.Collections.sort(r1, r2)     // Catch:{ all -> 0x00e7 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x00e7 }
            r1.<init>()     // Catch:{ all -> 0x00e7 }
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r2 = r9.f2891b     // Catch:{ all -> 0x00e7 }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x00e7 }
            r1.add(r2)     // Catch:{ all -> 0x00e7 }
            r9.m2824a((java.util.List<com.igexin.push.extension.distribution.gbd.p076b.C1486f>) r1)     // Catch:{ all -> 0x00e7 }
        L_0x005a:
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00e7 }
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch:{ all -> 0x00e7 }
            r4.<init>()     // Catch:{ all -> 0x00e7 }
            java.lang.String r5 = "key"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r11)     // Catch:{ all -> 0x00e7 }
            r4.put(r5, r6)     // Catch:{ all -> 0x00e7 }
            java.lang.String r5 = "value"
            byte[] r6 = r10.getBytes()     // Catch:{ all -> 0x00e7 }
            byte[] r6 = com.igexin.p032b.p042b.C1196a.m1438b(r6)     // Catch:{ all -> 0x00e7 }
            r4.put(r5, r6)     // Catch:{ all -> 0x00e7 }
            java.lang.String r5 = "t"
            java.lang.Long r6 = java.lang.Long.valueOf(r1)     // Catch:{ all -> 0x00e7 }
            r4.put(r5, r6)     // Catch:{ all -> 0x00e7 }
            java.lang.String r5 = "num"
            java.lang.Integer r6 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x00e7 }
            r4.put(r5, r6)     // Catch:{ all -> 0x00e7 }
            com.igexin.push.extension.distribution.gbd.e.a r5 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ all -> 0x00e7 }
            java.lang.String r6 = "ral"
            long r4 = r5.mo15095a((java.lang.String) r6, (android.content.ContentValues) r4)     // Catch:{ all -> 0x00e7 }
            r6 = -1
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x00e5
            r4 = 0
            com.igexin.push.extension.distribution.gbd.e.a r5 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2748b     // Catch:{ Throwable -> 0x00cf }
            java.lang.String r6 = "select last_insert_rowid() from ral"
            android.database.Cursor r4 = r5.mo15096a((java.lang.String) r6, (java.lang.String[]) r4)     // Catch:{ Throwable -> 0x00cf }
            if (r4 == 0) goto L_0x00c7
            boolean r5 = r4.moveToFirst()     // Catch:{ Throwable -> 0x00cf }
            if (r5 == 0) goto L_0x00c7
            int r5 = r4.getInt(r3)     // Catch:{ Throwable -> 0x00cf }
            com.igexin.push.extension.distribution.gbd.b.f r6 = new com.igexin.push.extension.distribution.gbd.b.f     // Catch:{ Throwable -> 0x00cf }
            r6.<init>()     // Catch:{ Throwable -> 0x00cf }
            r6.mo15068a((int) r5)     // Catch:{ Throwable -> 0x00cf }
            r6.mo15072b(r11)     // Catch:{ Throwable -> 0x00cf }
            r6.mo15070a((java.lang.String) r10)     // Catch:{ Throwable -> 0x00cf }
            r6.mo15069a((long) r1)     // Catch:{ Throwable -> 0x00cf }
            r6.mo15074c(r3)     // Catch:{ Throwable -> 0x00cf }
            java.util.List<com.igexin.push.extension.distribution.gbd.b.f> r10 = r9.f2891b     // Catch:{ Throwable -> 0x00cf }
            r10.add(r6)     // Catch:{ Throwable -> 0x00cf }
        L_0x00c7:
            if (r4 == 0) goto L_0x00e5
        L_0x00c9:
            r4.close()     // Catch:{ all -> 0x00e7 }
            goto L_0x00e5
        L_0x00cd:
            r10 = move-exception
            goto L_0x00df
        L_0x00cf:
            r10 = move-exception
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r10)     // Catch:{ all -> 0x00cd }
            java.lang.String r11 = "GBD_RALDataManager"
            java.lang.String r10 = r10.getMessage()     // Catch:{ all -> 0x00cd }
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r11, r10)     // Catch:{ all -> 0x00cd }
            if (r4 == 0) goto L_0x00e5
            goto L_0x00c9
        L_0x00df:
            if (r4 == 0) goto L_0x00e4
            r4.close()     // Catch:{ all -> 0x00e7 }
        L_0x00e4:
            throw r10     // Catch:{ all -> 0x00e7 }
        L_0x00e5:
            monitor-exit(r0)     // Catch:{ all -> 0x00e7 }
            return
        L_0x00e7:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00e7 }
            goto L_0x00eb
        L_0x00ea:
            throw r10
        L_0x00eb:
            goto L_0x00ea
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b.mo15111b(java.lang.String, int):void");
    }

    /* renamed from: c */
    public void mo15112c() {
        C1540h.m2997b("GBD_RALDataManager", "init doReport isReporting = " + this.f2892c);
        if (!C1541i.m3022d(C1490c.f2747a)) {
            C1540h.m2997b("GBD_RALDataManager", "ral r no network.");
        } else if (!this.f2892c) {
            this.f2892c = true;
            m2826b(6);
        }
    }
}
