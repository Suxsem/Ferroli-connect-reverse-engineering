package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.content.Context;
import android.net.wifi.ScanResult;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.igexin.push.extension.distribution.gbd.p086i.C1556x;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.f */
public class C1466f {

    /* renamed from: c */
    private static String f2556c = "GBD_WA";

    /* renamed from: a */
    protected C1465e f2557a = C1465e.SCAN_END;

    /* renamed from: b */
    protected int f2558b = C1463c.f2534b;

    /* renamed from: d */
    private C1463c f2559d;

    /* renamed from: e */
    private Comparator<ScanResult> f2560e;

    /* renamed from: f */
    private Comparator<Long> f2561f;

    /* renamed from: g */
    private C1556x<Long> f2562g;

    /* renamed from: h */
    private List<ScanResult> f2563h;

    /* renamed from: i */
    private Context f2564i;

    public C1466f(Context context) {
        this.f2564i = context;
        m2653a();
        m2655b();
    }

    /* renamed from: a */
    private void m2653a() {
        this.f2563h = new ArrayList();
        this.f2560e = new C1467g(this);
        this.f2561f = new C1468h(this);
        this.f2562g = new C1556x<>(this.f2561f);
    }

    /* renamed from: a */
    private void m2654a(Object obj, int i) {
        List<Long> a;
        int i2;
        this.f2557a = C1465e.SCAN_END;
        if (obj != null || i != 5) {
            if (obj == null && i == -1) {
                i2 = C1463c.f2542j;
            } else if (obj == null && i == 11) {
                i2 = C1463c.f2538f;
            } else {
                try {
                    ArrayList arrayList = new ArrayList();
                    if (obj != null) {
                        List list = (List) obj;
                        if (list.size() != 0) {
                            Collections.sort(list, this.f2560e);
                            int i3 = 0;
                            for (int i4 = 0; i4 < list.size(); i4++) {
                                ScanResult scanResult = (ScanResult) list.get(i4);
                                long parseLong = Long.parseLong(scanResult.BSSID.replaceAll(":", ""), 16);
                                if (parseLong != 0 && scanResult.level > C1488a.f2708n && !arrayList.contains(Long.valueOf(parseLong))) {
                                    if (i3 >= C1488a.f2710p) {
                                        break;
                                    }
                                    arrayList.add(Long.valueOf(parseLong));
                                    this.f2563h.add(scanResult);
                                    i3++;
                                }
                            }
                            if (!arrayList.isEmpty()) {
                                Collections.sort(arrayList, this.f2561f);
                                if (!C1490c.f2755i.isEmpty() && (a = this.f2562g.mo15184a(arrayList, C1490c.f2755i)) != null && !a.isEmpty()) {
                                    double size = (double) (arrayList.size() - a.size());
                                    double size2 = (double) arrayList.size();
                                    Double.isNaN(size);
                                    Double.isNaN(size2);
                                    if (((int) ((size / size2) * 100.0d)) < C1488a.f2711q && i == 11) {
                                        m2656b(C1463c.f2540h);
                                        return;
                                    }
                                }
                            } else if (i == 11) {
                                m2656b(C1463c.f2541i);
                                return;
                            }
                        } else if (i == 11) {
                            m2656b(C1463c.f2539g);
                            return;
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        C1507f.m2840a().mo15117a((List<Long>) arrayList);
                    }
                    this.f2559d.mo15016a(this.f2563h, C1463c.f2534b);
                    return;
                } catch (Exception e) {
                    C1540h.m2996a(e);
                    return;
                }
            }
            m2656b(i2);
        }
    }

    /* renamed from: b */
    private void m2655b() {
        if (!C1490c.f2755i.isEmpty()) {
            Collections.sort(C1490c.f2755i, this.f2561f);
        }
    }

    /* renamed from: b */
    private void m2656b(int i) {
        try {
            this.f2558b = i;
            this.f2563h.clear();
            this.f2559d.mo15016a(this.f2563h, i);
        } catch (Exception e) {
            C1540h.m2996a(e);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0061, code lost:
        if (r11 == 12) goto L_0x0063;
     */
    /* renamed from: c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m2657c(int r11) {
        /*
            r10 = this;
            boolean r0 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2752f
            r1 = 5
            r2 = 12
            r3 = 0
            if (r0 == 0) goto L_0x0061
            boolean r0 = r10.m2658c()
            if (r0 == 0) goto L_0x0061
            long r4 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2753g
            r6 = 0
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x001c
            long r4 = java.lang.System.currentTimeMillis()
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2753g = r4
        L_0x001c:
            long r4 = java.lang.System.currentTimeMillis()
            long r8 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2753g
            long r4 = r4 - r8
            int r0 = com.igexin.push.extension.distribution.gbd.p077c.C1488a.f2709o
            int r0 = r0 * 1000
            long r8 = (long) r0
            int r0 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1))
            if (r0 <= 0) goto L_0x004c
            java.lang.String r0 = f2556c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "timeout, type is "
            r4.append(r5)
            r4.append(r11)
            java.lang.String r4 = r4.toString()
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2997b(r0, r4)
            if (r11 != r2) goto L_0x0067
            java.lang.String r0 = f2556c
            java.lang.String r1 = "timeout report"
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2995a(r0, r1)
            goto L_0x0063
        L_0x004c:
            com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2753g = r6
            android.net.wifi.WifiManager r0 = com.igexin.push.extension.distribution.gbd.p077c.C1490c.f2750d     // Catch:{ Exception -> 0x0058 }
            java.util.List r0 = r0.getScanResults()     // Catch:{ Exception -> 0x0058 }
            r10.m2654a(r0, r11)     // Catch:{ Exception -> 0x0058 }
            goto L_0x006a
        L_0x0058:
            r11 = move-exception
            r0 = -1
            r10.m2654a(r3, r0)
            com.igexin.push.extension.distribution.gbd.p086i.C1540h.m2996a(r11)
            goto L_0x006a
        L_0x0061:
            if (r11 != r2) goto L_0x0067
        L_0x0063:
            r10.m2654a(r3, r11)
            goto L_0x006a
        L_0x0067:
            r10.m2654a(r3, r1)
        L_0x006a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.extension.distribution.gbd.p069a.p072c.C1466f.m2657c(int):void");
    }

    /* renamed from: c */
    private boolean m2658c() {
        return C1541i.m3006a(this.f2564i, "android.permission.ACCESS_FINE_LOCATION") && C1541i.m3006a(this.f2564i, "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo15020a(int i) {
        this.f2563h.clear();
        this.f2557a = C1465e.SCAN_START;
        this.f2558b = C1463c.f2534b;
        m2657c(i);
    }

    /* renamed from: a */
    public void mo15021a(C1463c cVar) {
        this.f2559d = cVar;
    }
}
