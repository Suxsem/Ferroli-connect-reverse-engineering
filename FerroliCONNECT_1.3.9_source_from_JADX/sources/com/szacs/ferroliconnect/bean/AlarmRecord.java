package com.szacs.ferroliconnect.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\b¨\u0006\u000f"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/bean/AlarmRecord;", "", "()V", "code", "", "getCode", "()Ljava/lang/String;", "setCode", "(Ljava/lang/String;)V", "date", "getDate", "setDate", "time", "getTime", "setTime", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: AlarmRecord.kt */
public final class AlarmRecord {
    @Nullable
    private String code;
    @Nullable
    private String date;
    @Nullable
    private String time;

    @Nullable
    public final String getDate() {
        return this.date;
    }

    public final void setDate(@Nullable String str) {
        this.date = str;
    }

    @Nullable
    public final String getTime() {
        return this.time;
    }

    public final void setTime(@Nullable String str) {
        this.time = str;
    }

    @Nullable
    public final String getCode() {
        return this.code;
    }

    public final void setCode(@Nullable String str) {
        this.code = str;
    }
}
