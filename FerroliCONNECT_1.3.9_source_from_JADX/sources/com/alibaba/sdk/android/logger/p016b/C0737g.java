package com.alibaba.sdk.android.logger.p016b;

/* renamed from: com.alibaba.sdk.android.logger.b.g */
public class C0737g {

    /* renamed from: a */
    private String f945a;

    public C0737g(String str) {
        this.f945a = str;
        if (str == null) {
            this.f945a = "default";
        }
    }

    /* renamed from: a */
    public String mo9733a(Object obj) {
        String str;
        if (obj == null) {
            str = "";
        } else if (obj instanceof Class) {
            str = ((Class) obj).getSimpleName();
        } else if (obj instanceof String) {
            str = (String) obj;
        } else {
            str = obj.getClass().getSimpleName() + "@" + obj.hashCode();
        }
        return this.f945a + "_" + str;
    }
}
