package com.igexin.push.extension.distribution.gbd.p078d;

import android.os.Handler;
import android.os.Message;
import com.igexin.push.extension.distribution.gbd.p069a.p070a.C1448a;
import com.igexin.push.extension.distribution.gbd.p069a.p070a.C1449b;
import com.igexin.push.extension.distribution.gbd.p069a.p071b.C1453d;
import com.igexin.push.extension.distribution.gbd.p069a.p071b.C1457h;
import com.igexin.push.extension.distribution.gbd.p069a.p072c.C1463c;
import com.igexin.push.extension.distribution.gbd.p069a.p074e.C1479a;
import com.igexin.push.extension.distribution.gbd.p069a.p075f.C1480a;
import com.igexin.push.extension.distribution.gbd.p077c.C1488a;
import com.igexin.push.extension.distribution.gbd.p079e.p080a.C1503b;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;

/* renamed from: com.igexin.push.extension.distribution.gbd.d.a */
public class C1491a extends Handler {
    public void handleMessage(Message message) {
        C1463c a;
        try {
            int i = message.what;
            if (i == 1) {
                int i2 = message.arg1;
                int i3 = 11;
                if (i2 != 11) {
                    i3 = 12;
                    if (i2 == 12) {
                        a = C1463c.m2637a();
                    } else {
                        return;
                    }
                } else {
                    a = C1463c.m2637a();
                }
                a.mo15014a(i3);
            } else if (i == 2) {
                int i4 = message.arg1;
                if (i4 == 21) {
                    C1503b.m2819a().mo15112c();
                } else if (i4 == 22) {
                    if (C1488a.f2716v) {
                        C1503b.m2819a().mo15108a(0);
                    }
                    if (C1488a.f2717w || C1488a.f2660aQ) {
                        C1503b.m2819a().mo15108a(1);
                        C1503b.m2819a().mo15108a(2);
                        C1503b.m2819a().mo15108a(3);
                    }
                }
            } else if (i != 4) {
                if (i == 5) {
                    int i5 = message.arg1;
                    if (i5 == 51) {
                        C1453d.m2573a().mo15005d();
                        postDelayed(new C1492b(this), 5000);
                        if (C1488a.f2628L) {
                            C1449b.m2546a().mo14997b();
                        }
                    } else if (i5 == 52) {
                        C1457h.m2610a().mo15008c();
                        if (C1488a.f2628L) {
                            C1449b.m2546a().mo14998c();
                        }
                    }
                } else if (i == 6) {
                    C1453d.m2573a().mo15004c();
                    postDelayed(new C1493c(this), 5000);
                } else if (i == 10) {
                    C1448a.m2541a().mo14995b();
                } else if (i == 13) {
                    C1480a.m2722a().mo15034b();
                } else if (i == 16) {
                } else {
                    if (i == 17) {
                        C1479a.m2716a().mo15032b();
                    }
                }
            } else if (message.arg1 == 41) {
                C1449b.m2546a().mo14999d();
            }
        } catch (Exception e) {
            C1540h.m2996a(e);
            C1540h.m2997b("GBD_Handler", e.toString());
        }
    }
}
