package anet.channel.util;

import anet.channel.request.Request;
import anet.channel.thread.ThreadPoolExecutorFactory;
import java.util.HashMap;
import java.util.Map;

/* renamed from: anet.channel.util.h */
/* compiled from: Taobao */
public class C0609h {

    /* renamed from: a */
    private static Map<String, Integer> f593a = new HashMap();

    static {
        f593a.put("tpatch", 3);
        f593a.put("so", 3);
        f593a.put("json", 3);
        f593a.put("html", 4);
        f593a.put("htm", 4);
        f593a.put("css", 5);
        f593a.put("js", 5);
        f593a.put("webp", 6);
        f593a.put("png", 6);
        f593a.put("jpg", 6);
        f593a.put("do", 6);
        f593a.put("zip", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
        f593a.put("bin", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
        f593a.put("apk", Integer.valueOf(ThreadPoolExecutorFactory.Priority.LOW));
    }

    /* renamed from: a */
    public static int m363a(Request request) {
        Integer num;
        if (request == null) {
            throw new NullPointerException("url is null!");
        } else if (request.getHeaders().containsKey(HttpConstant.X_PV)) {
            return 1;
        } else {
            String trySolveFileExtFromUrlPath = HttpHelper.trySolveFileExtFromUrlPath(request.getHttpUrl().path());
            if (trySolveFileExtFromUrlPath == null || (num = f593a.get(trySolveFileExtFromUrlPath)) == null) {
                return 6;
            }
            return num.intValue();
        }
    }
}
