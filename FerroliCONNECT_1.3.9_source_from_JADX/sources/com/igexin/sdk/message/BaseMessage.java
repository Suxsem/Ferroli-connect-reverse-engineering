package com.igexin.sdk.message;

import com.igexin.push.core.C1343f;
import java.io.Serializable;

public class BaseMessage implements Serializable {

    /* renamed from: a */
    private String f3071a = C1343f.f2135a;

    /* renamed from: b */
    private String f3072b = C1343f.f2168e;

    /* renamed from: c */
    private String f3073c = C1343f.f2182s;

    public String getAppid() {
        return this.f3071a;
    }

    public String getClientId() {
        return this.f3073c;
    }

    public String getPkgName() {
        return this.f3072b;
    }

    public void setAppid(String str) {
        this.f3071a = str;
    }

    public void setClientId(String str) {
        this.f3073c = str;
    }

    public void setPkgName(String str) {
        this.f3072b = str;
    }
}
