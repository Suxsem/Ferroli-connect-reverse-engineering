package com.igexin.sdk;

import java.io.Serializable;

public class Tag implements Serializable {

    /* renamed from: a */
    private String f3059a;

    /* renamed from: a */
    private boolean m3292a(String str) {
        boolean z = false;
        for (int length = str.length() - 1; length >= 0; length--) {
            char charAt = str.charAt(length);
            z = (charAt >= 19968 && charAt <= 40869) || (charAt >= 'A' && charAt <= 'Z') || ((charAt >= 'a' && charAt <= 'z') || ((charAt >= '0' && charAt <= '9') || charAt == '+' || charAt == '-' || charAt == '*' || charAt == '_' || charAt == ' ' || charAt == ':'));
            if (!z) {
                break;
            }
        }
        return z;
    }

    public String getName() {
        return this.f3059a;
    }

    public boolean isValidTagValue(String str) {
        return m3292a(str);
    }

    public Tag setName(String str) {
        this.f3059a = str;
        return this;
    }
}
