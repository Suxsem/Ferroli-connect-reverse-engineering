package anet.channel.security;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.util.HMacUtil;

/* renamed from: anet.channel.security.a */
/* compiled from: Taobao */
class C0543a implements ISecurity {

    /* renamed from: a */
    private String f363a = null;

    public byte[] decrypt(Context context, String str, String str2, byte[] bArr) {
        return null;
    }

    public byte[] getBytes(Context context, String str) {
        return null;
    }

    public boolean isSecOff() {
        return true;
    }

    public boolean saveBytes(Context context, String str, byte[] bArr) {
        return false;
    }

    C0543a(String str) {
        this.f363a = str;
    }

    public String sign(Context context, String str, String str2, String str3) {
        if (!TextUtils.isEmpty(this.f363a) && ISecurity.SIGN_ALGORITHM_HMAC_SHA1.equalsIgnoreCase(str)) {
            return HMacUtil.hmacSha1Hex(this.f363a.getBytes(), str3.getBytes());
        }
        return null;
    }
}
