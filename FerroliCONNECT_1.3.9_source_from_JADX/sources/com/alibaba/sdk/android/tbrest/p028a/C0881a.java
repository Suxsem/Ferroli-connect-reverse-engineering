package com.alibaba.sdk.android.tbrest.p028a;

import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.alibaba.sdk.android.tbrest.utils.MD5Utils;
import com.alibaba.sdk.android.tbrest.utils.RC4;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.alibaba.sdk.android.tbrest.a.a */
/* compiled from: RestBaseRequestAuthentication */
public class C0881a {

    /* renamed from: b */
    private String f1332b = null;

    /* renamed from: b */
    private byte[] f1333b = null;

    /* renamed from: f */
    private boolean f1334f = false;

    /* renamed from: l */
    private String f1335l = null;

    public C0881a(String str, String str2, boolean z) {
        this.f1332b = str;
        this.f1335l = str2;
        this.f1334f = z;
    }

    /* renamed from: a */
    public static String m1010a(byte[] bArr, byte[] bArr2) throws Exception {
        Mac instance = Mac.getInstance("HmacSHA1");
        instance.init(new SecretKeySpec(bArr, instance.getAlgorithm()));
        return MD5Utils.toHexString(instance.doFinal(bArr2));
    }

    /* renamed from: b */
    public String mo10128b(String str) {
        String str2;
        if (this.f1332b == null || (str2 = this.f1335l) == null) {
            LogUtil.m1030e("There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            try {
                if (this.f1334f) {
                    return m1010a(str2.getBytes(), str.getBytes());
                }
                return m1010a(m1011a(), str.getBytes());
            } catch (Exception unused) {
                return "";
            }
        }
    }

    /* renamed from: a */
    private byte[] m1011a() {
        if (this.f1333b == null) {
            this.f1333b = RC4.rc4(new byte[]{66, 37, 42, -119, 118, -104, -30, 4, -95, 15, -26, -12, -75, -102, 71, 23, -3, -120, -1, -57, 42, 99, -16, -101, 103, -74, 93, -114, 112, -26, -24, -24});
        }
        return this.f1333b;
    }
}
