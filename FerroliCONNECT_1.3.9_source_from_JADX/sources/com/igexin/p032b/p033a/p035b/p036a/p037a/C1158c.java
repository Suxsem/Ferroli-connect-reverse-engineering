package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.text.TextUtils;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1156d;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.SDKUrlConfig;
import com.igexin.push.p046c.C1214i;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.android.spdy.TnetStatusCode;

/* renamed from: com.igexin.b.a.b.a.a.c */
public final class C1158c extends C1152a {

    /* renamed from: i */
    private C1156d f1551i;

    /* renamed from: j */
    private Socket f1552j;

    public C1158c(C1156d dVar) {
        super(TnetStatusCode.EASY_REASON_INVALID_DATA, (String) null, (C1173b) null);
        this.f1551i = dVar;
    }

    /* renamed from: b */
    public final int mo14231b() {
        return TnetStatusCode.EASY_REASON_INVALID_DATA;
    }

    /* renamed from: b_ */
    public void mo14232b_() {
        super.mo14232b_();
        C1214i.m1500a().mo14385e().mo14363a();
        String cmAddress = SDKUrlConfig.getCmAddress();
        try {
            String[] a = C1177f.m1333a(cmAddress);
            String str = a[1];
            int parseInt = Integer.parseInt(a[2]);
            C1179b.m1354a("GS-C|start connect :  " + cmAddress + " *********");
            C1156d dVar = this.f1551i;
            if (dVar != null) {
                dVar.mo14229a(cmAddress);
            }
            this.f1552j = new Socket();
            try {
                this.f1552j.connect(new InetSocketAddress(str, parseInt), RestConstants.G_MAX_CONNECTION_TIME_OUT);
                C1179b.m1354a("GS-C|connected :  " + cmAddress + " #########");
                C1179b.m1354a("GS-C|local-" + this.f1552j.getLocalAddress() + ":" + this.f1552j.getLocalPort());
                if (this.f1544f != C1157b.INTERRUPT) {
                    this.f1544f = C1157b.NORMAL;
                }
            } catch (Exception e) {
                if (this.f1544f != C1157b.INTERRUPT) {
                    this.f1544f = C1157b.EXCEPTION;
                    this.f1545g = e.toString();
                }
            }
            this.f1543e = true;
        } catch (Exception e2) {
            C1179b.m1354a("GS-C|ips invalid, " + e2.toString());
            throw e2;
        }
    }

    /* renamed from: f */
    public void mo14233f() {
        Socket socket;
        super.mo14233f();
        C1179b.m1354a("GS-C|sc dispose");
        if (this.f1551i != null) {
            if (this.f1544f == C1157b.INTERRUPT) {
                this.f1551i.mo14224a(this);
            } else if (this.f1544f == C1157b.EXCEPTION) {
                if (!TextUtils.isEmpty(this.f1545g)) {
                    this.f1551i.mo14228a(new Exception(this.f1545g));
                }
            } else if (this.f1544f == C1157b.NORMAL && (socket = this.f1552j) != null) {
                this.f1551i.mo14230a(socket);
            }
        }
        this.f1551i = null;
    }

    /* renamed from: j */
    public void mo14234j() {
        this.f1544f = C1157b.INTERRUPT;
    }
}
