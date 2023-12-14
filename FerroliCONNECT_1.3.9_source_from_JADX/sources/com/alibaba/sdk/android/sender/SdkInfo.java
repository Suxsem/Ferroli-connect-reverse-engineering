package com.alibaba.sdk.android.sender;

import java.util.Map;

public class SdkInfo {

    /* renamed from: a */
    Map<String, String> f1323a;

    /* renamed from: b */
    private String f1324b;

    /* renamed from: c */
    private String f1325c;

    /* renamed from: d */
    private String f1326d;

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public String mo10107a() {
        return this.f1324b;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public String mo10108b() {
        return this.f1325c;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public String mo10109c() {
        return this.f1326d;
    }

    public SdkInfo setAppKey(String str) {
        this.f1326d = str;
        return this;
    }

    public SdkInfo setExt(Map<String, String> map) {
        this.f1323a = map;
        return this;
    }

    public SdkInfo setSdkId(String str) {
        this.f1324b = str;
        return this;
    }

    public SdkInfo setSdkVersion(String str) {
        this.f1325c = str;
        return this;
    }
}
