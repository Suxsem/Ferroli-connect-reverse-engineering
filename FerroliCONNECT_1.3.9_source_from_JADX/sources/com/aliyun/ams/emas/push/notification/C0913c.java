package com.aliyun.ams.emas.push.notification;

import android.os.Build;
import com.taobao.accs.utl.ALog;
import java.util.ArrayList;
import java.util.Map;

/* renamed from: com.aliyun.ams.emas.push.notification.c */
/* compiled from: Taobao */
public class C0913c {
    public static final String TAG = "MPS:CPushNotification";

    /* renamed from: a */
    public static ArrayList<Integer> f1442a = new ArrayList<>();

    /* renamed from: b */
    private Map<String, String> f1443b;

    /* renamed from: c */
    private String f1444c;

    /* renamed from: d */
    private String f1445d;

    /* renamed from: e */
    private String f1446e;

    /* renamed from: f */
    private String f1447f;

    /* renamed from: g */
    private String f1448g;

    /* renamed from: h */
    private String f1449h;

    /* renamed from: i */
    private String f1450i;

    /* renamed from: j */
    private int f1451j;

    /* renamed from: k */
    private int f1452k;

    /* renamed from: l */
    private int f1453l = 0;

    /* renamed from: m */
    private String f1454m;

    /* renamed from: n */
    private String f1455n;

    /* renamed from: o */
    private String f1456o;

    public C0913c() {
        int i = Build.VERSION.SDK_INT;
    }

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            f1442a.add(-2);
            f1442a.add(0);
            f1442a.add(1);
            f1442a.add(-1);
            f1442a.add(2);
            return;
        }
        f1442a.add(-2);
        f1442a.add(0);
        f1442a.add(1);
        f1442a.add(-1);
        f1442a.add(2);
    }

    /* renamed from: a */
    public int mo10198a() {
        return this.f1451j;
    }

    /* renamed from: a */
    public void mo10199a(int i) {
        this.f1451j = i;
    }

    /* renamed from: b */
    public String mo10202b() {
        return this.f1446e;
    }

    /* renamed from: a */
    public void mo10200a(String str) {
        this.f1446e = str;
    }

    /* renamed from: c */
    public String mo10205c() {
        return this.f1447f;
    }

    /* renamed from: b */
    public void mo10204b(String str) {
        this.f1447f = str;
    }

    /* renamed from: d */
    public String mo10207d() {
        return this.f1448g;
    }

    /* renamed from: c */
    public void mo10206c(String str) {
        this.f1448g = str;
    }

    /* renamed from: e */
    public Map<String, String> mo10209e() {
        return this.f1443b;
    }

    /* renamed from: a */
    public void mo10201a(Map<String, String> map) {
        this.f1443b = map;
    }

    /* renamed from: f */
    public String mo10211f() {
        return this.f1444c;
    }

    /* renamed from: d */
    public void mo10208d(String str) {
        this.f1444c = str;
    }

    /* renamed from: g */
    public String mo10213g() {
        return this.f1445d;
    }

    /* renamed from: e */
    public void mo10210e(String str) {
        this.f1445d = str;
    }

    /* renamed from: h */
    public String mo10215h() {
        return this.f1449h;
    }

    /* renamed from: f */
    public void mo10212f(String str) {
        this.f1449h = str;
    }

    /* renamed from: i */
    public int mo10217i() {
        return this.f1452k;
    }

    /* renamed from: b */
    public void mo10203b(int i) {
        if (i < 0) {
            this.f1452k = i * -1;
        } else {
            this.f1452k = i;
        }
    }

    /* renamed from: j */
    public int mo10219j() {
        return this.f1453l;
    }

    /* renamed from: g */
    public void mo10214g(String str) {
        try {
            if (f1442a.contains(Integer.valueOf(Integer.parseInt(str)))) {
                this.f1453l = Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            ALog.m3726e(TAG, "formar error:数字格式错误", e, new Object[0]);
        }
    }

    /* renamed from: k */
    public String mo10221k() {
        return this.f1454m;
    }

    /* renamed from: h */
    public void mo10216h(String str) {
        this.f1454m = str;
    }

    /* renamed from: l */
    public String mo10223l() {
        return this.f1455n;
    }

    /* renamed from: i */
    public void mo10218i(String str) {
        this.f1455n = str;
    }

    /* renamed from: m */
    public String mo10224m() {
        return this.f1450i;
    }

    /* renamed from: j */
    public void mo10220j(String str) {
        this.f1450i = str;
    }

    /* renamed from: n */
    public String mo10225n() {
        return this.f1456o;
    }

    /* renamed from: k */
    public void mo10222k(String str) {
        this.f1456o = str;
    }
}
