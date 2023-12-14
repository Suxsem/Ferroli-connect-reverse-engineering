package com.szacs.ferroliconnect.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/bean/DeviceBean;", "", "()V", "device_code", "", "getDevice_code", "()Ljava/lang/String;", "setDevice_code", "(Ljava/lang/String;)V", "mac_address", "getMac_address", "setMac_address", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: DeviceBean.kt */
public final class DeviceBean {
    @Nullable
    private String device_code;
    @Nullable
    private String mac_address;

    @Nullable
    public final String getDevice_code() {
        return this.device_code;
    }

    public final void setDevice_code(@Nullable String str) {
        this.device_code = str;
    }

    @Nullable
    public final String getMac_address() {
        return this.mac_address;
    }

    public final void setMac_address(@Nullable String str) {
        this.mac_address = str;
    }
}
