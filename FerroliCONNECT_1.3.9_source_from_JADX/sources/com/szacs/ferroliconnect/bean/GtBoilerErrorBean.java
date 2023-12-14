package com.szacs.ferroliconnect.bean;

import java.io.Serializable;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/bean/GtBoilerErrorBean;", "Ljava/io/Serializable;", "()V", "bfc", "", "getBfc", "()Ljava/lang/String;", "setBfc", "(Ljava/lang/String;)V", "bfi", "getBfi", "setBfi", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: GtBoilerErrorBean.kt */
public final class GtBoilerErrorBean implements Serializable {
    @Nullable
    private String bfc;
    @Nullable
    private String bfi;

    @Nullable
    public final String getBfc() {
        return this.bfc;
    }

    public final void setBfc(@Nullable String str) {
        this.bfc = str;
    }

    @Nullable
    public final String getBfi() {
        return this.bfi;
    }

    public final void setBfi(@Nullable String str) {
        this.bfi = str;
    }
}
