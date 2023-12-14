package anetwork.channel.cache;

import anet.channel.util.HttpHelper;
import anetwork.channel.cache.Cache;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.http.HttpHeaders;

/* renamed from: anetwork.channel.cache.a */
/* compiled from: Taobao */
public class C0625a {

    /* renamed from: a */
    private static final TimeZone f643a = TimeZone.getTimeZone("GMT");

    /* renamed from: b */
    private static final ThreadLocal<SimpleDateFormat> f644b = new ThreadLocal<>();

    /* renamed from: a */
    public static String m387a(long j) {
        return m388a().format(new Date(j));
    }

    /* renamed from: a */
    private static long m385a(String str) {
        if (str.length() == 0) {
            return 0;
        }
        try {
            ParsePosition parsePosition = new ParsePosition(0);
            Date parse = m388a().parse(str, parsePosition);
            if (parsePosition.getIndex() == str.length()) {
                return parse.getTime();
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    /* renamed from: a */
    public static Cache.Entry m386a(Map<String, List<String>> map) {
        long j;
        long j2;
        Map<String, List<String>> map2 = map;
        long currentTimeMillis = System.currentTimeMillis();
        String singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map2, "Cache-Control");
        boolean z = true;
        int i = 0;
        if (singleHeaderFieldByKey != null) {
            String[] split = singleHeaderFieldByKey.split(",");
            j = 0;
            while (true) {
                if (i >= split.length) {
                    break;
                }
                String trim = split[i].trim();
                if (trim.equals("no-store")) {
                    return null;
                }
                if (trim.equals(HttpHeaderValues.NO_CACHE)) {
                    j = 0;
                    break;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j = Long.parseLong(trim.substring(8));
                    } catch (Exception unused) {
                    }
                }
                i++;
            }
        } else {
            j = 0;
            z = false;
        }
        String singleHeaderFieldByKey2 = HttpHelper.getSingleHeaderFieldByKey(map2, HttpHeaders.DATE);
        long a = singleHeaderFieldByKey2 != null ? m385a(singleHeaderFieldByKey2) : 0;
        String singleHeaderFieldByKey3 = HttpHelper.getSingleHeaderFieldByKey(map2, HttpHeaders.EXPIRES);
        long a2 = singleHeaderFieldByKey3 != null ? m385a(singleHeaderFieldByKey3) : 0;
        String singleHeaderFieldByKey4 = HttpHelper.getSingleHeaderFieldByKey(map2, HttpHeaders.LAST_MODIFIED);
        long a3 = singleHeaderFieldByKey4 != null ? m385a(singleHeaderFieldByKey4) : 0;
        String singleHeaderFieldByKey5 = HttpHelper.getSingleHeaderFieldByKey(map2, HttpHeaders.ETAG);
        if (z) {
            currentTimeMillis += j * 1000;
        } else if (a <= 0 || a2 < a) {
            j2 = a3;
            if (j2 <= 0) {
                currentTimeMillis = 0;
            }
            if (currentTimeMillis != 0 && singleHeaderFieldByKey5 == null) {
                return null;
            }
            Cache.Entry entry = new Cache.Entry();
            entry.etag = singleHeaderFieldByKey5;
            entry.ttl = currentTimeMillis;
            entry.serverDate = a;
            entry.lastModified = j2;
            entry.responseHeaders = map2;
            return entry;
        } else {
            currentTimeMillis += a2 - a;
        }
        j2 = a3;
        if (currentTimeMillis != 0) {
        }
        Cache.Entry entry2 = new Cache.Entry();
        entry2.etag = singleHeaderFieldByKey5;
        entry2.ttl = currentTimeMillis;
        entry2.serverDate = a;
        entry2.lastModified = j2;
        entry2.responseHeaders = map2;
        return entry2;
    }

    /* renamed from: a */
    private static SimpleDateFormat m388a() {
        SimpleDateFormat simpleDateFormat = f644b.get();
        if (simpleDateFormat != null) {
            return simpleDateFormat;
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        simpleDateFormat2.setTimeZone(f643a);
        f644b.set(simpleDateFormat2);
        return simpleDateFormat2;
    }
}
