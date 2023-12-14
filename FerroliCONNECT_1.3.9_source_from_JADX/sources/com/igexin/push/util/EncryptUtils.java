package com.igexin.push.util;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1343f;
import com.igexin.push.extension.mod.SecurityUtils;
import com.igexin.push.p054e.p057c.C1373b;
import java.security.MessageDigest;

public class EncryptUtils {

    /* renamed from: a */
    private static final String f3003a = "com.igexin.push.util.EncryptUtils";

    /* renamed from: b */
    private static boolean f3004b = false;

    /* renamed from: c */
    private static int f3005c = 0;

    /* renamed from: d */
    private static byte[] f3006d = null;

    /* renamed from: e */
    private static byte[] f3007e = null;
    public static String errorMsg = "";

    static {
        try {
            if (SecurityUtils.f2970b) {
                f3006d = initSocketAESKey();
                f3007e = initHttpAESKey();
                f3004b = (f3006d == null || f3007e == null || getSocketAESKey() == null || getHttpAESKey() == null || getRSAKeyId() == null || getVersion() == null) ? false : true;
            }
        } catch (Throwable th) {
            C1179b.m1354a(f3003a + "|load so error = " + th.toString());
            f3004b = false;
            errorMsg = th.getMessage();
        }
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = SecurityUtils.f2971c;
        }
        if (!f3004b) {
            C1179b.m1354a(f3003a + "|load so error ++++++++");
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = "value = null, normal error";
                return;
            }
            return;
        }
        C1179b.m1354a(f3003a + "|load so success ~~~~~~~");
    }

    public static byte[] aesDecHttp(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3087c(f3007e, bArr, bArr2);
    }

    public static byte[] aesDecSocket(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3091g(f3006d, bArr, bArr2);
    }

    public static byte[] aesEncHttp(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3086b(f3007e, bArr, bArr2);
    }

    public static byte[] aesEncSocket(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3090f(f3006d, bArr, bArr2);
    }

    public static byte[] altAesDecSocket(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3097m(bArr, bArr2);
    }

    public static byte[] altAesEncSocket(byte[] bArr, byte[] bArr2) {
        return SecurityUtils.m3096l(bArr, bArr2);
    }

    public static byte[] getBytesEncrypted(byte[] bArr) {
        return C1150a.m1235d(bArr, C1343f.f2110B);
    }

    public static byte[] getHttpAESKey() {
        return SecurityUtils.m3088d(f3007e);
    }

    public static String getHttpGTCV() {
        byte[] httpAESKey = getHttpAESKey();
        byte[] bytes = C1594s.m3272a(16).getBytes();
        byte[] bArr = new byte[(bytes.length + httpAESKey.length)];
        C1177f.m1327a(httpAESKey, 0, bArr, C1177f.m1327a(bytes, 0, bArr, 0, bytes.length), httpAESKey.length);
        return C1584i.m3249b(bArr, 2);
    }

    public static String getHttpSignature(String str, byte[] bArr) {
        byte[] bytes = str.getBytes();
        byte[] bArr2 = new byte[(bytes.length + bArr.length)];
        int a = C1177f.m1327a(bytes, 0, bArr2, 0, bytes.length);
        if (bArr.length > 0) {
            C1177f.m1327a(bArr, 0, bArr2, a, bArr.length);
        }
        return C1584i.m3249b(sha1(bArr2), 2);
    }

    public static byte[] getIV(byte[] bArr) {
        return md5(bArr);
    }

    public static int getPacketId() {
        int i = f3005c;
        f3005c = i + 1;
        return i;
    }

    public static byte[] getRSAKeyId() {
        return SecurityUtils.m3094j();
    }

    public static byte[] getSocketAESKey() {
        return SecurityUtils.m3092h(f3006d);
    }

    public static byte[] getSocketSignature(C1373b bVar, int i, int i2) {
        byte[] bArr = new byte[(bVar.f2256a + 11)];
        int a = C1177f.m1324a(i, bArr, 0);
        int a2 = a + C1177f.m1324a(i2, bArr, a);
        int b = a2 + C1177f.m1334b((short) bVar.f2256a, bArr, a2);
        C1177f.m1327a(bVar.f2260e, 0, bArr, b + C1177f.m1338c(bVar.f2257b, bArr, b), bVar.f2256a);
        return sha1(bArr);
    }

    public static String getVersion() {
        byte[] k = SecurityUtils.m3095k();
        if (k == null) {
            return null;
        }
        String str = new String(k);
        C1179b.m1354a(f3003a + "| so version is " + str);
        return str;
    }

    public static byte[] initHttpAESKey() {
        return SecurityUtils.m3085a();
    }

    public static byte[] initSocketAESKey() {
        return SecurityUtils.m3089e();
    }

    public static boolean isLoadSuccess() {
        return f3004b;
    }

    public static byte[] md5(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            return instance.digest();
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean reset() {
        String str;
        StringBuilder sb;
        try {
            if (SecurityUtils.f2970b) {
                f3006d = initSocketAESKey();
                f3007e = initHttpAESKey();
                f3004b = (f3006d == null || f3007e == null || getSocketAESKey() == null || getHttpAESKey() == null || getRSAKeyId() == null || getVersion() == null) ? false : true;
            }
        } catch (Throwable th) {
            C1179b.m1354a(f3003a + "|load so error = " + th.toString());
            f3004b = false;
        }
        if (!f3004b) {
            sb = new StringBuilder();
            sb.append(f3003a);
            str = "|load so error ++++++++";
        } else {
            sb = new StringBuilder();
            sb.append(f3003a);
            str = "|load so success ~~~~~~~";
        }
        sb.append(str);
        C1179b.m1354a(sb.toString());
        return f3004b;
    }

    public static byte[] rsaEnc(byte[] bArr) {
        int length = bArr.length;
        if (length <= 214) {
            return SecurityUtils.m3093i(bArr);
        }
        int i = length % 200 == 0 ? length / 200 : (length / 200) + 1;
        byte[] bArr2 = new byte[(i * 256)];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            int i4 = i2 < i + -1 ? 200 : length - (i2 * 200);
            byte[] bArr3 = new byte[i4];
            C1177f.m1327a(bArr, i2 * 200, bArr3, 0, i4);
            byte[] i5 = SecurityUtils.m3093i(bArr3);
            i3 += C1177f.m1327a(i5, 0, bArr2, i3, i5.length);
            i2++;
        }
        return bArr2;
    }

    public static byte[] sha1(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
