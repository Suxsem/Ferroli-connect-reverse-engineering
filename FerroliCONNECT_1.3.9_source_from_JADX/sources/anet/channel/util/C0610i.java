package anet.channel.util;

import android.text.TextUtils;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: anet.channel.util.i */
/* compiled from: Taobao */
public class C0610i {

    /* renamed from: a */
    private static AtomicInteger f594a = new AtomicInteger();

    /* renamed from: a */
    public static String m364a(String str) {
        if (f594a.get() == Integer.MAX_VALUE) {
            f594a.set(0);
        }
        if (!TextUtils.isEmpty(str)) {
            return StringUtils.concatString(str, ".AWCN", String.valueOf(f594a.incrementAndGet()));
        }
        return StringUtils.concatString("AWCN", String.valueOf(f594a.incrementAndGet()));
    }
}
