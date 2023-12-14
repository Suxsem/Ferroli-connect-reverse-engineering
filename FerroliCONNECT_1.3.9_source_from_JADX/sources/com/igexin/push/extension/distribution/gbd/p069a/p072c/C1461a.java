package com.igexin.push.extension.distribution.gbd.p069a.p072c;

import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import com.igexin.push.extension.distribution.gbd.p076b.C1483c;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p077c.C1490c;
import com.igexin.push.extension.distribution.gbd.p078d.C1494d;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1507f;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.distribution.gbd.p086i.C1541i;
import com.p107tb.appyunsdk.Constant;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.gbd.a.c.a */
public class C1461a {

    /* renamed from: a */
    protected Location f2524a;

    /* renamed from: b */
    private Context f2525b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public C1463c f2526c;

    /* renamed from: d */
    private LocationManager f2527d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public long f2528e = 0;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public long f2529f = 0;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public int f2530g = 0;

    /* renamed from: h */
    private GpsStatus.Listener f2531h = new C1462b(this);

    public C1461a(Context context) {
        try {
            this.f2525b = context;
            this.f2527d = (LocationManager) C1490c.f2747a.getSystemService(Constant.WEATHER_LOCATION);
            boolean e = C1494d.m2778a().mo15091e();
            C1540h.m2997b("GBD_LA", "LocationAction init checkSafeStatus = " + e);
            if (e && this.f2527d != null && m2629b()) {
                this.f2527d.addGpsStatusListener(this.f2531h);
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
    }

    /* renamed from: a */
    private boolean m2626a(Location location) {
        return location != null && location.getLatitude() == 0.0d && location.getLongitude() == 0.0d && location.getTime() == 0 && !location.hasAccuracy();
    }

    /* renamed from: b */
    private boolean m2629b() {
        if (m2631c()) {
            if (C1541i.m3019c("com.huawei.android.hwouc", this.f2525b)) {
                long currentTimeMillis = System.currentTimeMillis() - C1490c.f2757k;
                long j = (long) (C1488a.f2631O * 24 * 60 * 60 * 1000);
                if (C1488a.f2630N && currentTimeMillis > j) {
                    if (m2626a(this.f2527d.getLastKnownLocation("network"))) {
                        C1507f.m2840a().mo15132j(System.currentTimeMillis());
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* renamed from: c */
    private boolean m2631c() {
        return C1541i.m3006a(this.f2525b, "android.permission.ACCESS_FINE_LOCATION") && C1541i.m3006a(this.f2525b, "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* renamed from: d */
    static /* synthetic */ int m2632d(C1461a aVar) {
        int i = aVar.f2530g;
        aVar.f2530g = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Location mo15010a(boolean z) {
        LocationManager locationManager;
        String str;
        try {
            if (this.f2527d == null || !m2629b()) {
                return null;
            }
            if (z) {
                locationManager = this.f2527d;
                str = "gps";
            } else {
                locationManager = this.f2527d;
                str = "network";
            }
            return locationManager.getLastKnownLocation(str);
        } catch (Throwable th) {
            C1540h.m2996a(th);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public C1483c mo15011a() {
        int i;
        int i2;
        int i3;
        List list;
        int i4;
        try {
            if (m2629b()) {
                TelephonyManager telephonyManager = (TelephonyManager) this.f2525b.getSystemService(Constant.PHONE);
                int i5 = 0;
                if (telephonyManager.getSimState() == 5) {
                    String networkOperator = telephonyManager.getNetworkOperator();
                    if (networkOperator == null || networkOperator.length() < 3) {
                        i4 = 0;
                        i2 = 0;
                    } else {
                        i2 = Integer.parseInt(networkOperator.substring(0, 3));
                        i4 = Integer.parseInt(networkOperator.substring(3));
                    }
                    try {
                        CellLocation cellLocation = telephonyManager.getCellLocation();
                        if (cellLocation instanceof GsmCellLocation) {
                            i = ((GsmCellLocation) cellLocation).getLac();
                            try {
                                i5 = ((GsmCellLocation) cellLocation).getCid();
                            } catch (Exception e) {
                                e = e;
                                C1540h.m2996a(e);
                                i5 = i4;
                                i3 = 0;
                                list = telephonyManager.getNeighboringCellInfo();
                                C1483c cVar = new C1483c();
                                cVar.mo15045a(i2);
                                cVar.mo15048b(i5);
                                cVar.mo15050c(i);
                                cVar.mo15052d(i3);
                                cVar.mo15046a((List<NeighboringCellInfo>) list);
                                return cVar;
                            }
                        } else if (cellLocation instanceof CdmaCellLocation) {
                            i = ((CdmaCellLocation) cellLocation).getNetworkId();
                            i4 = ((CdmaCellLocation) cellLocation).getSystemId();
                            i5 = ((CdmaCellLocation) cellLocation).getBaseStationId();
                        } else {
                            i = 0;
                        }
                        int i6 = i5;
                        i5 = i4;
                        i3 = i6;
                    } catch (Exception e2) {
                        e = e2;
                        i = 0;
                        C1540h.m2996a(e);
                        i5 = i4;
                        i3 = 0;
                        list = telephonyManager.getNeighboringCellInfo();
                        C1483c cVar2 = new C1483c();
                        cVar2.mo15045a(i2);
                        cVar2.mo15048b(i5);
                        cVar2.mo15050c(i);
                        cVar2.mo15052d(i3);
                        cVar2.mo15046a((List<NeighboringCellInfo>) list);
                        return cVar2;
                    }
                    list = telephonyManager.getNeighboringCellInfo();
                } else {
                    list = null;
                    i3 = 0;
                    i2 = 0;
                    i = 0;
                }
                C1483c cVar22 = new C1483c();
                cVar22.mo15045a(i2);
                cVar22.mo15048b(i5);
                cVar22.mo15050c(i);
                cVar22.mo15052d(i3);
                cVar22.mo15046a((List<NeighboringCellInfo>) list);
                return cVar22;
            }
        } catch (Throwable th) {
            C1540h.m2996a(th);
        }
        return null;
    }

    /* renamed from: a */
    public void mo15012a(C1463c cVar) {
        this.f2526c = cVar;
    }
}
