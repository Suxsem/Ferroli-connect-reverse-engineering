package com.alibaba.sdk.android.push.notification;

import android.os.Build;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.aliyun.ams.emas.push.notification.C0913c;
import java.util.ArrayList;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.notification.b */
public class C0861b {

    /* renamed from: a */
    static AmsLogger f1266a = AmsLogger.getLogger(C0913c.TAG);

    /* renamed from: b */
    public static ArrayList<Integer> f1267b = new ArrayList<>();

    /* renamed from: A */
    private boolean f1268A;

    /* renamed from: c */
    private Map<String, String> f1269c;

    /* renamed from: d */
    private String f1270d;

    /* renamed from: e */
    private String f1271e;

    /* renamed from: f */
    private String f1272f;

    /* renamed from: g */
    private String f1273g;

    /* renamed from: h */
    private String f1274h;

    /* renamed from: i */
    private int f1275i = 0;

    /* renamed from: j */
    private String f1276j;

    /* renamed from: k */
    private String f1277k;

    /* renamed from: l */
    private String f1278l;

    /* renamed from: m */
    private String f1279m;

    /* renamed from: n */
    private String f1280n;

    /* renamed from: o */
    private boolean f1281o;

    /* renamed from: p */
    private int f1282p;

    /* renamed from: q */
    private int f1283q;

    /* renamed from: r */
    private int f1284r;

    /* renamed from: s */
    private int f1285s;

    /* renamed from: t */
    private int f1286t;

    /* renamed from: u */
    private int f1287u;

    /* renamed from: v */
    private int f1288v;

    /* renamed from: w */
    private int f1289w;

    /* renamed from: x */
    private int f1290x;

    /* renamed from: y */
    private int f1291y;

    /* renamed from: z */
    private int f1292z;

    static {
        int i = Build.VERSION.SDK_INT;
        f1267b.add(-2);
        f1267b.add(0);
        f1267b.add(1);
        f1267b.add(-1);
        f1267b.add(2);
    }

    public C0861b() {
        int i = Build.VERSION.SDK_INT;
        this.f1283q = 0;
        this.f1284r = 0;
        this.f1285s = 1;
        this.f1286t = 0;
        this.f1287u = 3;
        this.f1288v = 0;
        this.f1289w = 0;
        this.f1290x = 0;
        this.f1291y = 0;
        this.f1292z = 0;
        this.f1268A = false;
    }

    /* renamed from: a */
    public String mo10026a() {
        return this.f1273g;
    }

    /* renamed from: a */
    public void mo10027a(int i) {
        this.f1282p = i;
    }

    /* renamed from: a */
    public void mo10028a(String str) {
        this.f1273g = str;
    }

    /* renamed from: a */
    public void mo10029a(Map<String, String> map) {
        this.f1269c = map;
    }

    /* renamed from: a */
    public void mo10030a(boolean z) {
        this.f1268A = z;
    }

    /* renamed from: b */
    public String mo10031b() {
        return this.f1270d;
    }

    /* renamed from: b */
    public void mo10032b(int i) {
        this.f1284r = i;
    }

    /* renamed from: b */
    public void mo10033b(String str) {
        this.f1270d = str;
    }

    /* renamed from: c */
    public String mo10034c() {
        return this.f1271e;
    }

    /* renamed from: c */
    public void mo10035c(int i) {
        this.f1285s = i;
    }

    /* renamed from: c */
    public void mo10036c(String str) {
        this.f1271e = str;
    }

    /* renamed from: d */
    public int mo10037d() {
        return this.f1282p;
    }

    /* renamed from: d */
    public void mo10038d(int i) {
        this.f1286t = i;
    }

    /* renamed from: d */
    public void mo10039d(String str) {
        this.f1272f = str;
    }

    /* renamed from: e */
    public void mo10040e(int i) {
        this.f1287u = i;
    }

    /* renamed from: e */
    public void mo10041e(String str) {
        try {
            if (f1267b.contains(Integer.valueOf(Integer.parseInt(str)))) {
                this.f1283q = Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            f1266a.mo9542e("formar error:数字格式错误", e);
        }
    }

    /* renamed from: e */
    public boolean mo10042e() {
        return this.f1281o;
    }

    /* renamed from: f */
    public int mo10043f() {
        return this.f1284r;
    }

    /* renamed from: f */
    public void mo10044f(int i) {
        this.f1288v = i;
    }

    /* renamed from: f */
    public void mo10045f(String str) {
        this.f1280n = str;
    }

    /* renamed from: g */
    public int mo10046g() {
        return this.f1285s;
    }

    /* renamed from: g */
    public void mo10047g(int i) {
        this.f1289w = i;
    }

    /* renamed from: g */
    public void mo10048g(String str) {
        this.f1274h = str;
    }

    /* renamed from: h */
    public int mo10049h() {
        return this.f1286t;
    }

    /* renamed from: h */
    public void mo10050h(int i) {
        this.f1290x = i;
    }

    /* renamed from: h */
    public void mo10051h(String str) {
        this.f1276j = str;
    }

    /* renamed from: i */
    public int mo10052i() {
        return this.f1287u;
    }

    /* renamed from: i */
    public void mo10053i(int i) {
        this.f1291y = i;
    }

    /* renamed from: i */
    public void mo10054i(String str) {
        this.f1277k = str;
    }

    /* renamed from: j */
    public int mo10055j() {
        return this.f1288v;
    }

    /* renamed from: j */
    public void mo10056j(int i) {
        this.f1292z = i;
    }

    /* renamed from: j */
    public void mo10057j(String str) {
        this.f1278l = str;
    }

    /* renamed from: k */
    public int mo10058k() {
        return this.f1289w;
    }

    /* renamed from: k */
    public void mo10059k(int i) {
        this.f1275i = i;
    }

    /* renamed from: k */
    public void mo10060k(String str) {
        this.f1279m = str;
    }

    /* renamed from: l */
    public int mo10061l() {
        return this.f1290x;
    }

    /* renamed from: m */
    public int mo10062m() {
        return this.f1291y;
    }

    /* renamed from: n */
    public int mo10063n() {
        return this.f1292z;
    }

    /* renamed from: o */
    public boolean mo10064o() {
        return this.f1268A;
    }

    /* renamed from: p */
    public int mo10065p() {
        return this.f1283q;
    }

    /* renamed from: q */
    public String mo10066q() {
        return this.f1280n;
    }

    /* renamed from: r */
    public String mo10067r() {
        return this.f1274h;
    }

    /* renamed from: s */
    public int mo10068s() {
        return this.f1275i;
    }

    /* renamed from: t */
    public String mo10069t() {
        return this.f1276j;
    }

    /* renamed from: u */
    public String mo10070u() {
        return this.f1277k;
    }

    /* renamed from: v */
    public String mo10071v() {
        return this.f1278l;
    }

    /* renamed from: w */
    public String mo10072w() {
        return this.f1279m;
    }
}
