package anet.channel.monitor;

/* renamed from: anet.channel.monitor.f */
/* compiled from: Taobao */
public class C0538f {

    /* renamed from: a */
    boolean f318a = false;

    /* renamed from: b */
    protected long f319b;

    /* renamed from: c */
    private final double f320c = 40.0d;

    /* renamed from: d */
    private boolean f321d = true;

    /* renamed from: a */
    public int mo8850a() {
        return 0;
    }

    /* renamed from: a */
    public boolean mo8851a(double d) {
        return d < 40.0d;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final boolean mo8852b() {
        if (!this.f321d) {
            return false;
        }
        if (System.currentTimeMillis() - this.f319b <= ((long) (mo8850a() * 1000))) {
            return true;
        }
        this.f321d = false;
        return false;
    }
}
