package anet.channel.heartbeat;

/* compiled from: Taobao */
public class HeartbeatManager {
    public static IHeartbeat getDefaultHeartbeat() {
        return new C0527b();
    }

    public static IHeartbeat getDefaultBackgroundAccsHeartbeat() {
        return new C0526a();
    }
}
