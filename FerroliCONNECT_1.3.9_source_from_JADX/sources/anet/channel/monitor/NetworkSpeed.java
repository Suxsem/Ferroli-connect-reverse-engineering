package anet.channel.monitor;

/* compiled from: Taobao */
public enum NetworkSpeed {
    Slow("弱网络", 1),
    Fast("强网络", 5);
    

    /* renamed from: a */
    private final String f282a;

    /* renamed from: b */
    private final int f283b;

    private NetworkSpeed(String str, int i) {
        this.f282a = str;
        this.f283b = i;
    }

    public String getDesc() {
        return this.f282a;
    }

    public int getCode() {
        return this.f283b;
    }

    public static NetworkSpeed valueOfCode(int i) {
        return i == 1 ? Slow : Fast;
    }
}
