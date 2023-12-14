package com.p092ta.utdid2.p097a.p098a;

import android.content.Context;
import android.text.TextUtils;
import java.util.Random;

/* renamed from: com.ta.utdid2.a.a.c */
public class C1987c {
    /* renamed from: h */
    public static String m3376h() {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nanoTime = (int) System.nanoTime();
        int nextInt = new Random().nextInt();
        int nextInt2 = new Random().nextInt();
        byte[] bytes = C1986b.getBytes(currentTimeMillis);
        byte[] bytes2 = C1986b.getBytes(nanoTime);
        byte[] bytes3 = C1986b.getBytes(nextInt);
        byte[] bytes4 = C1986b.getBytes(nextInt2);
        byte[] bArr = new byte[16];
        System.arraycopy(bytes, 0, bArr, 0, 4);
        System.arraycopy(bytes2, 0, bArr, 4, 4);
        System.arraycopy(bytes3, 0, bArr, 8, 4);
        System.arraycopy(bytes4, 0, bArr, 12, 4);
        return C1983a.encodeToString(bArr, 2);
    }

    /* renamed from: b */
    public static String m3375b(Context context) {
        String str = null;
        if (C1988d.m3381b((String) null)) {
            str = m3377i();
        }
        return C1988d.m3381b(str) ? m3376h() : str;
    }

    /* renamed from: i */
    private static String m3377i() {
        String str = C1990e.get("ro.aliyun.clouduuid", "");
        if (TextUtils.isEmpty(str)) {
            str = C1990e.get("ro.sys.aliyun.clouduuid", "");
        }
        return TextUtils.isEmpty(str) ? m3378j() : str;
    }

    /* renamed from: j */
    private static String m3378j() {
        try {
            return (String) Class.forName("com.yunos.baseservice.clouduuid.CloudUUID").getMethod("getCloudUUID", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception unused) {
            return "";
        }
    }
}
