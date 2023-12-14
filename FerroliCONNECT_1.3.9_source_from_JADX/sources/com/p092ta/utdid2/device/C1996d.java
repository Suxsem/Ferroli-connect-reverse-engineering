package com.p092ta.utdid2.device;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.p092ta.p093a.C1964a;
import com.p092ta.p093a.p096c.C1979e;
import com.p092ta.p093a.p096c.C1982f;
import com.p092ta.utdid2.p097a.p098a.C1983a;
import com.p092ta.utdid2.p097a.p098a.C1986b;
import com.p092ta.utdid2.p097a.p098a.C1987c;
import com.p092ta.utdid2.p097a.p098a.C1988d;
import com.p092ta.utdid2.p099b.p100a.C1991a;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* renamed from: com.ta.utdid2.device.d */
public class C1996d {

    /* renamed from: a */
    private static C1996d f3191a;

    /* renamed from: a */
    private static final Object f3192a = new Object();

    /* renamed from: b */
    private static Pattern f3193b = Pattern.compile("[^0-9a-zA-Z=/+]+");

    /* renamed from: e */
    private static int f3194e = 0;

    /* renamed from: f */
    private static final String f3195f = (".UTSystemConfig" + File.separator + "Global");

    /* renamed from: g */
    private static String f3196g = "";

    /* renamed from: a */
    private C1991a f3197a = null;

    /* renamed from: d */
    private String f3198d = null;
    private Context mContext = null;

    public static void setExtendFactor(String str) {
        f3196g = str;
    }

    private C1996d(Context context) {
        this.mContext = context;
        C1964a.mo18112a().mo18114a(context);
        this.f3197a = new C1991a(context, f3195f, "Alvin2");
    }

    /* renamed from: a */
    public static C1996d m3400a(Context context) {
        if (context != null && f3191a == null) {
            synchronized (f3192a) {
                if (f3191a == null) {
                    f3191a = new C1996d(context);
                }
            }
        }
        return f3191a;
    }

    static void setType(int i) {
        f3194e = i;
    }

    public synchronized String getValue() {
        if (this.f3198d != null) {
            return this.f3198d;
        }
        return m3405p();
    }

    /* renamed from: p */
    private String m3405p() {
        String q = m3406q();
        if (m3403c(q)) {
            if (TextUtils.isEmpty(q) || !q.endsWith("\n")) {
                this.f3198d = q;
            } else {
                this.f3198d = q.substring(0, q.length() - 1);
            }
            return this.f3198d;
        }
        try {
            byte[] a = m3401a();
            if (a == null) {
                return null;
            }
            this.f3198d = C1983a.encodeToString(a, 2);
            f3194e = 6;
            m3403c(this.f3198d);
            return this.f3198d;
        } catch (Exception e) {
            C1982f.m3367a("", e, new Object[0]);
            return null;
        }
    }

    /* renamed from: c */
    private void m3403c(String str) {
        if (m3403c(str)) {
            f3194e = 6;
            C1982f.m3366a("UTUtdid", "utdid type:", Integer.valueOf(f3194e));
            this.f3197a.mo18142a(str, f3194e);
        }
    }

    /* renamed from: q */
    private String m3406q() {
        String k = this.f3197a.mo18143k();
        if (m3403c(k)) {
            int a = this.f3197a.mo18141a();
            if (a == 0) {
                f3194e = 1;
            } else {
                f3194e = a;
            }
            C1982f.m3366a("UTUtdid", "get utdid from sp. type", Integer.valueOf(f3194e));
            return k;
        }
        C1982f.m3366a("UTUtdid", "read utdid is null");
        Log.d("UTUtdid", "read utdid is null");
        return null;
    }

    /* renamed from: c */
    static boolean m3404c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.endsWith("\n")) {
            str = str.substring(0, str.length() - 1);
        }
        if (24 == str.length()) {
            return !f3193b.matcher(str).find();
        }
        return false;
    }

    /* renamed from: a */
    private byte[] m3401a() throws Exception {
        String str;
        C1982f.m3366a("UTUtdid", "generateUtdid");
        Log.d("UTUtdid", "generateUtdid");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nextInt = new Random().nextInt();
        byte[] bytes = C1986b.getBytes(currentTimeMillis);
        byte[] bytes2 = C1986b.getBytes(nextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            str = f3196g + C1987c.m3375b(this.mContext);
        } catch (Exception unused) {
            str = f3196g + new Random().nextInt();
        }
        byteArrayOutputStream.write(C1986b.getBytes(C1988d.m3379a(str)), 0, 4);
        byteArrayOutputStream.write(C1986b.getBytes(C1988d.m3379a(m3402b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    /* renamed from: b */
    private static String m3402b(byte[] bArr) throws Exception {
        byte[] bArr2 = {69, 114, 116, -33, 125, -54, -31, 86, -11, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, -78, -96, -17, -99, 64, 23, -95, -126, -82, -64, 113, 116, -16, -103, 49, -30, 9, -39, 33, -80, -68, -78, -117, 53, 30, -122, 64, -104, 74, -49, 106, 85, -38, -93};
        Mac instance = Mac.getInstance("HmacSHA1");
        instance.init(new SecretKeySpec(C1979e.m3363b(bArr2), instance.getAlgorithm()));
        return C1983a.encodeToString(instance.doFinal(bArr), 2);
    }
}
