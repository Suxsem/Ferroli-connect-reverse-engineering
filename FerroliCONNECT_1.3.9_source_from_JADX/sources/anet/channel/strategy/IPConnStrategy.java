package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.C0583l;
import java.io.Serializable;

/* compiled from: Taobao */
class IPConnStrategy implements IConnStrategy, Serializable {
    public static final int SOURCE_AMDC = 0;
    public static final int SOURCE_CUSTOMIZED = 2;
    public static final int SOURCE_LOCAL_DNS = 1;
    public static final int TYPE_IP_TO_HOST = -1;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_STATIC_BANDWITDH = 0;

    /* renamed from: a */
    volatile int f442a = 1;

    /* renamed from: b */
    volatile int f443b = 1;

    /* renamed from: c */
    transient boolean f444c;
    public volatile int cto;
    public volatile int heartbeat;

    /* renamed from: ip */
    public final String f445ip;
    public final int port;
    public final ConnProtocol protocol;
    public volatile int retry;
    public volatile int rto;

    /* renamed from: a */
    static IPConnStrategy m244a(String str, C0583l.C0584a aVar) {
        ConnProtocol valueOf = ConnProtocol.valueOf(aVar);
        if (valueOf == null) {
            return null;
        }
        return m243a(str, aVar.f515a, valueOf, aVar.f517c, aVar.f518d, aVar.f519e, aVar.f520f);
    }

    /* renamed from: a */
    static IPConnStrategy m243a(String str, int i, ConnProtocol connProtocol, int i2, int i3, int i4, int i5) {
        if (TextUtils.isEmpty(str) || connProtocol == null || i <= 0) {
            return null;
        }
        return new IPConnStrategy(str, i, connProtocol, i2, i3, i4, i5);
    }

    private IPConnStrategy(String str, int i, ConnProtocol connProtocol, int i2, int i3, int i4, int i5) {
        this.f445ip = str;
        this.port = i;
        this.protocol = connProtocol;
        this.cto = i2;
        this.rto = i3;
        this.retry = i4;
        this.heartbeat = i5;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append('{');
        sb.append(this.f445ip);
        if (this.f442a == 0) {
            sb.append("(*)");
        }
        sb.append(' ');
        sb.append(this.port);
        sb.append(' ');
        sb.append(this.protocol);
        sb.append('}');
        return sb.toString();
    }

    public String getIp() {
        return this.f445ip;
    }

    public int getIpType() {
        return this.f442a;
    }

    public int getIpSource() {
        return this.f443b;
    }

    public int getPort() {
        return this.port;
    }

    public ConnProtocol getProtocol() {
        return this.protocol;
    }

    public int getConnectionTimeout() {
        return this.cto;
    }

    public int getReadTimeout() {
        return this.rto;
    }

    public int getRetryTimes() {
        return this.retry;
    }

    public int getHeartbeat() {
        return this.heartbeat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IPConnStrategy)) {
            return false;
        }
        IPConnStrategy iPConnStrategy = (IPConnStrategy) obj;
        if (this.port != iPConnStrategy.port || !this.f445ip.equals(iPConnStrategy.f445ip) || !this.protocol.equals(iPConnStrategy.protocol)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((((527 + this.f445ip.hashCode()) * 31) + this.port) * 31) + this.protocol.hashCode();
    }

    public int getUniqueId() {
        return hashCode();
    }
}
