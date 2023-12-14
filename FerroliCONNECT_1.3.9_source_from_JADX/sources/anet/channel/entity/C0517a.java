package anet.channel.entity;

import anet.channel.strategy.IConnStrategy;
import com.szacs.ferroliconnect.util.APConfig.UDPSocket;

/* renamed from: anet.channel.entity.a */
/* compiled from: Taobao */
public class C0517a {

    /* renamed from: a */
    public final IConnStrategy f241a;

    /* renamed from: b */
    public int f242b = 0;

    /* renamed from: c */
    public int f243c = 0;

    /* renamed from: d */
    private String f244d;

    /* renamed from: e */
    private String f245e;

    public C0517a(String str, String str2, IConnStrategy iConnStrategy) {
        this.f241a = iConnStrategy;
        this.f244d = str;
        this.f245e = str2;
    }

    /* renamed from: a */
    public String mo8814a() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy != null) {
            return iConnStrategy.getIp();
        }
        return null;
    }

    /* renamed from: b */
    public int mo8815b() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy != null) {
            return iConnStrategy.getPort();
        }
        return 0;
    }

    /* renamed from: c */
    public ConnType mo8816c() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy != null) {
            return ConnType.valueOf(iConnStrategy.getProtocol());
        }
        return ConnType.HTTP;
    }

    /* renamed from: d */
    public int mo8817d() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy == null || iConnStrategy.getConnectionTimeout() == 0) {
            return UDPSocket.CLIENT_PORT;
        }
        return this.f241a.getConnectionTimeout();
    }

    /* renamed from: e */
    public int mo8818e() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy == null || iConnStrategy.getReadTimeout() == 0) {
            return UDPSocket.CLIENT_PORT;
        }
        return this.f241a.getReadTimeout();
    }

    /* renamed from: f */
    public String mo8819f() {
        return this.f244d;
    }

    /* renamed from: g */
    public int mo8820g() {
        IConnStrategy iConnStrategy = this.f241a;
        if (iConnStrategy != null) {
            return iConnStrategy.getHeartbeat();
        }
        return 45000;
    }

    /* renamed from: h */
    public String mo8821h() {
        return this.f245e;
    }

    public String toString() {
        return "ConnInfo [ip=" + mo8814a() + ",port=" + mo8815b() + ",type=" + mo8816c() + ",hb" + mo8820g() + "]";
    }
}
