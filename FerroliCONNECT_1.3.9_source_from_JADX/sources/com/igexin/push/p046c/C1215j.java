package com.igexin.push.p046c;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.igexin.p032b.p033a.p039c.C1179b;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.igexin.push.c.j */
public class C1215j {

    /* renamed from: a */
    private static final String f1759a = "com.igexin.push.c.j";

    /* renamed from: b */
    private String f1760b;

    /* renamed from: c */
    private String f1761c;

    /* renamed from: d */
    private int f1762d;

    /* renamed from: e */
    private long f1763e = 2147483647L;

    /* renamed from: f */
    private long f1764f = -1;

    /* renamed from: g */
    private boolean f1765g = true;

    /* renamed from: h */
    private int f1766h;

    /* renamed from: i */
    private int f1767i;

    /* renamed from: j */
    private int f1768j = 3;

    public C1215j() {
    }

    public C1215j(String str, int i) {
        this.f1760b = str;
        this.f1762d = i;
    }

    /* renamed from: i */
    private void m1508i() {
        this.f1761c = null;
        this.f1766h = 0;
        this.f1765g = true;
    }

    /* renamed from: j */
    private boolean m1509j() {
        return this.f1761c != null && System.currentTimeMillis() - this.f1764f <= C1211f.f1747b && this.f1766h < this.f1768j;
    }

    /* renamed from: a */
    public synchronized String mo14389a() {
        return this.f1760b;
    }

    /* renamed from: a */
    public void mo14390a(int i) {
        this.f1762d = i;
    }

    /* renamed from: a */
    public void mo14391a(long j) {
        this.f1763e = j;
    }

    /* renamed from: a */
    public synchronized void mo14392a(String str) {
        this.f1760b = str;
    }

    /* renamed from: a */
    public synchronized void mo14393a(String str, long j, long j2) {
        this.f1761c = str;
        this.f1763e = j;
        this.f1764f = j2;
        this.f1766h = 0;
        this.f1767i = 0;
        this.f1765g = false;
    }

    /* renamed from: a */
    public void mo14394a(boolean z) {
        this.f1765g = z;
    }

    /* renamed from: b */
    public synchronized String mo14395b(boolean z) {
        if (m1509j()) {
            if (z) {
                this.f1766h++;
            }
            this.f1765g = false;
            return this.f1761c;
        }
        m1508i();
        C1179b.m1354a(f1759a + "|disc, ip is invalid, use domain = " + this.f1760b);
        if (z) {
            this.f1767i++;
        }
        return this.f1760b;
    }

    /* renamed from: b */
    public synchronized void mo14396b() {
        this.f1761c = null;
        this.f1763e = 2147483647L;
        this.f1764f = -1;
        this.f1765g = true;
        this.f1766h = 0;
    }

    /* renamed from: b */
    public synchronized void mo14397b(int i) {
        if (i < 1) {
            i = 1;
        }
        this.f1768j = i;
    }

    /* renamed from: b */
    public void mo14398b(long j) {
        this.f1764f = j;
    }

    /* renamed from: b */
    public void mo14399b(String str) {
        this.f1761c = str;
    }

    /* renamed from: c */
    public String mo14400c() {
        return this.f1761c;
    }

    /* renamed from: d */
    public int mo14401d() {
        return this.f1762d;
    }

    /* renamed from: e */
    public synchronized long mo14402e() {
        return this.f1763e;
    }

    /* renamed from: f */
    public synchronized boolean mo14403f() {
        if (m1509j()) {
            return true;
        }
        if (this.f1767i < this.f1768j) {
            return true;
        }
        this.f1767i = 0;
        return false;
    }

    /* renamed from: g */
    public synchronized void mo14404g() {
        this.f1766h = 0;
        this.f1767i = 0;
    }

    /* renamed from: h */
    public JSONObject mo14405h() {
        if (!(this.f1760b == null || this.f1761c == null)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(DispatchConstants.DOMAIN, this.f1760b);
                jSONObject.put("ip", this.f1761c);
                if (this.f1763e != 2147483647L) {
                    jSONObject.put("consumeTime", this.f1763e);
                }
                jSONObject.put("port", this.f1762d);
                if (this.f1764f != -1) {
                    jSONObject.put("detectSuccessTime", this.f1764f);
                }
                jSONObject.put("isDomain", this.f1765g);
                jSONObject.put("connectTryCnt", this.f1768j);
                return jSONObject;
            } catch (JSONException e) {
                C1179b.m1354a(f1759a + e.toString());
            }
        }
        return null;
    }
}
