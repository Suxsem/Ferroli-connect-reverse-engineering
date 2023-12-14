package anet.channel.security;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.util.ALog;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import java.util.HashMap;
import java.util.Map;

/* renamed from: anet.channel.security.b */
/* compiled from: Taobao */
class C0544b implements ISecurity {

    /* renamed from: a */
    private static String f364a = "awcn.DefaultSecurityGuard";

    /* renamed from: b */
    private static boolean f365b = false;

    /* renamed from: c */
    private static Map<String, Integer> f366c;

    /* renamed from: d */
    private String f367d = null;

    public boolean isSecOff() {
        return false;
    }

    static {
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            f365b = true;
            f366c = new HashMap();
            f366c.put(ISecurity.SIGN_ALGORITHM_HMAC_SHA1, 3);
            f366c.put(ISecurity.CIPHER_ALGORITHM_AES128, 16);
        } catch (Throwable unused) {
            f365b = false;
        }
    }

    C0544b(String str) {
        this.f367d = str;
    }

    public String sign(Context context, String str, String str2, String str3) {
        if (f365b && context != null && !TextUtils.isEmpty(str2) && f366c.containsKey(str)) {
            try {
                ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
                if (secureSignatureComp != null) {
                    SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                    securityGuardParamContext.appKey = str2;
                    securityGuardParamContext.paramMap.put("INPUT", str3);
                    securityGuardParamContext.requestType = f366c.get(str).intValue();
                    return secureSignatureComp.signRequest(securityGuardParamContext, this.f367d);
                }
            } catch (Throwable th) {
                ALog.m326e(f364a, "Securityguard sign request failed.", (String) null, th, new Object[0]);
            }
        }
        return null;
    }

    public byte[] decrypt(Context context, String str, String str2, byte[] bArr) {
        Integer num;
        IStaticDataEncryptComponent staticDataEncryptComp;
        if (!f365b || context == null || bArr == null || TextUtils.isEmpty(str2) || !f366c.containsKey(str) || (num = f366c.get(str)) == null) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (!(instance == null || (staticDataEncryptComp = instance.getStaticDataEncryptComp()) == null)) {
                return staticDataEncryptComp.staticBinarySafeDecryptNoB64(num.intValue(), str2, bArr, this.f367d);
            }
        } catch (Throwable th) {
            ALog.m326e(f364a, "staticBinarySafeDecryptNoB64", (String) null, th, new Object[0]);
        }
        return null;
    }

    public boolean saveBytes(Context context, String str, byte[] bArr) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        if (context == null || bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance == null || (dynamicDataStoreComp = instance.getDynamicDataStoreComp()) == null || dynamicDataStoreComp.putByteArray(str, bArr) == 0) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            ALog.m326e(f364a, "saveBytes", (String) null, th, new Object[0]);
            return false;
        }
    }

    public byte[] getBytes(Context context, String str) {
        IDynamicDataStoreComponent dynamicDataStoreComp;
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance == null || (dynamicDataStoreComp = instance.getDynamicDataStoreComp()) == null) {
                return null;
            }
            return dynamicDataStoreComp.getByteArray(str);
        } catch (Throwable th) {
            ALog.m326e(f364a, "getBytes", (String) null, th, new Object[0]);
            return null;
        }
    }
}
