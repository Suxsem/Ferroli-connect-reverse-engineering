package com.szacs.ferroliconnect.bean;

import com.p107tb.appyunsdk.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\b\u0010\r\u001a\u00020\u0004H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\n¨\u0006\u000e"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/bean/District;", "", "()V", "name", "", "zipcode", "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "getZipcode", "setZipcode", "toString", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: District.kt */
public final class District {
    @Nullable
    private String name;
    @Nullable
    private String zipcode;

    @Nullable
    public final String getName() {
        return this.name;
    }

    public final void setName(@Nullable String str) {
        this.name = str;
    }

    @Nullable
    public final String getZipcode() {
        return this.zipcode;
    }

    public final void setZipcode(@Nullable String str) {
        this.zipcode = str;
    }

    public District() {
    }

    public District(@NotNull String str, @NotNull String str2) {
        Intrinsics.checkParameterIsNotNull(str, Constant.NAME);
        Intrinsics.checkParameterIsNotNull(str2, "zipcode");
        this.name = str;
        this.zipcode = str2;
    }

    @NotNull
    public String toString() {
        return "DistrictModel [name=" + this.name + ", zipcode=" + this.zipcode + ']';
    }
}
