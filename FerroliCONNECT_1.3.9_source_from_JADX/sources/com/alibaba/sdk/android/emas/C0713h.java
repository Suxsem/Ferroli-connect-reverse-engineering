package com.alibaba.sdk.android.emas;

import android.os.Build;
import android.text.TextUtils;
import com.alibaba.sdk.android.tbrest.utils.Base64;

/* renamed from: com.alibaba.sdk.android.emas.h */
/* compiled from: EncrytUtils */
public final class C0713h {

    /* renamed from: a */
    private static final char[] f911a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String aesEncrypt(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return Base64.encodeBase64String(m637a(str, str2));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String aesDecrypt(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return m636a(Base64.decode(str2), str);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* renamed from: a */
    private static byte[] m637a(String str, String str2) throws Exception {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                byte[] a = C0705b.m612a().mo9630a(str2.getBytes());
                if (a != null) {
                    return a;
                }
            } catch (Exception unused) {
            }
        }
        return C0704a.m607a(str, str2).getBytes();
    }

    /* renamed from: a */
    private static String m636a(byte[] bArr, String str) throws Exception {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                byte[] b = C0705b.m612a().mo9631b(bArr);
                if (b != null) {
                    return new String(b);
                }
            } catch (Exception unused) {
            }
        }
        return C0704a.m610b(str, new String(bArr));
    }
}
