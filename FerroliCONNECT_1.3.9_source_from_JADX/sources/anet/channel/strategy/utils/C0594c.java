package anet.channel.strategy.utils;

import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* renamed from: anet.channel.strategy.utils.c */
/* compiled from: Taobao */
public class C0594c {
    /* renamed from: d */
    public static String m322d(String str) {
        return str == null ? "" : str;
    }

    /* renamed from: a */
    public static boolean m319a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 7 || charArray.length > 15) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (char c : charArray) {
            if (c >= '0' && c <= '9') {
                i2 = ((i2 * 10) + c) - 48;
                if (i2 > 255) {
                    return false;
                }
            } else if (c != '.' || (i = i + 1) > 3) {
                return false;
            } else {
                i2 = 0;
            }
        }
        return true;
    }

    /* renamed from: b */
    public static boolean m320b(String str) {
        int i;
        boolean z;
        int i2;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length < 2) {
            return false;
        }
        if (charArray[0] != ':') {
            i2 = 0;
            z = false;
            i = 0;
        } else if (charArray[1] != ':') {
            return false;
        } else {
            i2 = 1;
            z = false;
            i = 1;
        }
        int i3 = 0;
        boolean z2 = true;
        while (i2 < charArray.length) {
            char c = charArray[i2];
            int digit = Character.digit(c, 16);
            if (digit != -1) {
                i3 = (i3 << 4) + digit;
                if (i3 > 65535) {
                    return false;
                }
                z2 = false;
            } else if (c != ':' || (i = i + 1) > 7) {
                return false;
            } else {
                if (!z2) {
                    i3 = 0;
                    z2 = true;
                } else if (z) {
                    return false;
                } else {
                    z = true;
                }
            }
            i2++;
        }
        if (z || i >= 7) {
            return true;
        }
        return false;
    }

    /* renamed from: c */
    public static boolean m321c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        if (charArray.length <= 0 || charArray.length > 255) {
            return false;
        }
        boolean z = false;
        for (int i = 0; i < charArray.length; i++) {
            if ((charArray[i] >= 'A' && charArray[i] <= 'Z') || ((charArray[i] >= 'a' && charArray[i] <= 'z') || charArray[i] == '*')) {
                z = true;
            } else if (!((charArray[i] >= '0' && charArray[i] <= '9') || charArray[i] == '.' || charArray[i] == '-')) {
                return false;
            }
        }
        return z;
    }

    /* renamed from: a */
    public static String m317a(long j) {
        StringBuilder sb = new StringBuilder(16);
        long j2 = 1000000000;
        do {
            sb.append(j / j2);
            sb.append('.');
            j %= j2;
            j2 /= 1000;
        } while (j2 > 0);
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /* renamed from: a */
    public static String m318a(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(64);
        try {
            for (Map.Entry next : map.entrySet()) {
                if (next.getKey() != null) {
                    sb.append(URLEncoder.encode((String) next.getKey(), str));
                    sb.append("=");
                    sb.append(URLEncoder.encode(m322d((String) next.getValue()), str).replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%20"));
                    sb.append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (UnsupportedEncodingException e) {
            ALog.m326e("Request", "format params failed", (String) null, e, new Object[0]);
        }
        return sb.toString();
    }
}
