package com.szacs.ferroliconnect.bean;

import com.taobao.accs.common.Constants;
import com.topband.sdk.boiler.Message;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/bean/Device2AppMsg;", "", "productCode", "", "deviceCode", "message", "Lcom/topband/sdk/boiler/Message;", "(Ljava/lang/String;Ljava/lang/String;Lcom/topband/sdk/boiler/Message;)V", "getDeviceCode", "()Ljava/lang/String;", "getMessage", "()Lcom/topband/sdk/boiler/Message;", "getProductCode", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: Device2AppMsg.kt */
public final class Device2AppMsg {
    @NotNull
    private final String deviceCode;
    @NotNull
    private final Message message;
    @NotNull
    private final String productCode;

    public Device2AppMsg(@NotNull String str, @NotNull String str2, @NotNull Message message2) {
        Intrinsics.checkParameterIsNotNull(str, "productCode");
        Intrinsics.checkParameterIsNotNull(str2, "deviceCode");
        Intrinsics.checkParameterIsNotNull(message2, Constants.SHARED_MESSAGE_ID_FILE);
        this.productCode = str;
        this.deviceCode = str2;
        this.message = message2;
    }

    @NotNull
    public final String getDeviceCode() {
        return this.deviceCode;
    }

    @NotNull
    public final Message getMessage() {
        return this.message;
    }

    @NotNull
    public final String getProductCode() {
        return this.productCode;
    }
}
