package anetwork.channel.interceptor;

import anet.channel.util.ALog;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: Taobao */
public class InterceptorManager {

    /* renamed from: a */
    private static final CopyOnWriteArrayList<Interceptor> f741a = new CopyOnWriteArrayList<>();

    private InterceptorManager() {
    }

    public static void addInterceptor(Interceptor interceptor) {
        if (!f741a.contains(interceptor)) {
            f741a.add(interceptor);
            ALog.m328i("anet.InterceptorManager", "[addInterceptor]", (String) null, "interceptors", f741a.toString());
        }
    }

    public static void removeInterceptor(Interceptor interceptor) {
        f741a.remove(interceptor);
        ALog.m328i("anet.InterceptorManager", "[remoteInterceptor]", (String) null, "interceptors", f741a.toString());
    }

    public static Interceptor getInterceptor(int i) {
        return f741a.get(i);
    }

    public static boolean contains(Interceptor interceptor) {
        return f741a.contains(interceptor);
    }

    public static int getSize() {
        return f741a.size();
    }
}
