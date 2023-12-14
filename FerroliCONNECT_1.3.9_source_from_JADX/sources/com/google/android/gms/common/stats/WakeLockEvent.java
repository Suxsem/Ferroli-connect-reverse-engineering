package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@SafeParcelable.Class(creator = "WakeLockEventCreator")
public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zza();
    @SafeParcelable.Field(getter = "getTimeout", mo12890id = 16)
    private final long mTimeout;
    @SafeParcelable.Field(getter = "getTimeMillis", mo12890id = 2)
    private final long zzfo;
    @SafeParcelable.Field(getter = "getEventType", mo12890id = 11)
    private int zzfp;
    @SafeParcelable.Field(getter = "getWakeLockName", mo12890id = 4)
    private final String zzfq;
    @SafeParcelable.Field(getter = "getSecondaryWakeLockName", mo12890id = 10)
    private final String zzfr;
    @SafeParcelable.Field(getter = "getCodePackage", mo12890id = 17)
    private final String zzfs;
    @SafeParcelable.Field(getter = "getWakeLockType", mo12890id = 5)
    private final int zzft;
    @SafeParcelable.Field(getter = "getCallingPackages", mo12890id = 6)
    private final List<String> zzfu;
    @SafeParcelable.Field(getter = "getEventKey", mo12890id = 12)
    private final String zzfv;
    @SafeParcelable.Field(getter = "getElapsedRealtime", mo12890id = 8)
    private final long zzfw;
    @SafeParcelable.Field(getter = "getDeviceState", mo12890id = 14)
    private int zzfx;
    @SafeParcelable.Field(getter = "getHostPackage", mo12890id = 13)
    private final String zzfy;
    @SafeParcelable.Field(getter = "getBeginPowerPercentage", mo12890id = 15)
    private final float zzfz;
    @SafeParcelable.VersionField(mo12896id = 1)
    private final int zzg;
    private long zzga;

    @SafeParcelable.Constructor
    WakeLockEvent(@SafeParcelable.Param(mo12893id = 1) int i, @SafeParcelable.Param(mo12893id = 2) long j, @SafeParcelable.Param(mo12893id = 11) int i2, @SafeParcelable.Param(mo12893id = 4) String str, @SafeParcelable.Param(mo12893id = 5) int i3, @SafeParcelable.Param(mo12893id = 6) List<String> list, @SafeParcelable.Param(mo12893id = 12) String str2, @SafeParcelable.Param(mo12893id = 8) long j2, @SafeParcelable.Param(mo12893id = 14) int i4, @SafeParcelable.Param(mo12893id = 10) String str3, @SafeParcelable.Param(mo12893id = 13) String str4, @SafeParcelable.Param(mo12893id = 15) float f, @SafeParcelable.Param(mo12893id = 16) long j3, @SafeParcelable.Param(mo12893id = 17) String str5) {
        this.zzg = i;
        this.zzfo = j;
        this.zzfp = i2;
        this.zzfq = str;
        this.zzfr = str3;
        this.zzfs = str5;
        this.zzft = i3;
        this.zzga = -1;
        this.zzfu = list;
        this.zzfv = str2;
        this.zzfw = j2;
        this.zzfx = i4;
        this.zzfy = str4;
        this.zzfz = f;
        this.mTimeout = j3;
    }

    public WakeLockEvent(long j, int i, String str, int i2, List<String> list, String str2, long j2, int i3, String str3, String str4, float f, long j3, String str5) {
        this(2, j, i, str, i2, list, str2, j2, i3, str3, str4, f, j3, str5);
    }

    public final long getTimeMillis() {
        return this.zzfo;
    }

    public final int getEventType() {
        return this.zzfp;
    }

    public final long zzu() {
        return this.zzga;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzg);
        SafeParcelWriter.writeLong(parcel, 2, getTimeMillis());
        SafeParcelWriter.writeString(parcel, 4, this.zzfq, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzft);
        SafeParcelWriter.writeStringList(parcel, 6, this.zzfu, false);
        SafeParcelWriter.writeLong(parcel, 8, this.zzfw);
        SafeParcelWriter.writeString(parcel, 10, this.zzfr, false);
        SafeParcelWriter.writeInt(parcel, 11, getEventType());
        SafeParcelWriter.writeString(parcel, 12, this.zzfv, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzfy, false);
        SafeParcelWriter.writeInt(parcel, 14, this.zzfx);
        SafeParcelWriter.writeFloat(parcel, 15, this.zzfz);
        SafeParcelWriter.writeLong(parcel, 16, this.mTimeout);
        SafeParcelWriter.writeString(parcel, 17, this.zzfs, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String zzv() {
        String str;
        String str2 = this.zzfq;
        int i = this.zzft;
        List<String> list = this.zzfu;
        String str3 = "";
        if (list == null) {
            str = str3;
        } else {
            str = TextUtils.join(",", list);
        }
        int i2 = this.zzfx;
        String str4 = this.zzfr;
        if (str4 == null) {
            str4 = str3;
        }
        String str5 = this.zzfy;
        if (str5 == null) {
            str5 = str3;
        }
        float f = this.zzfz;
        String str6 = this.zzfs;
        if (str6 != null) {
            str3 = str6;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 45 + String.valueOf(str).length() + String.valueOf(str4).length() + String.valueOf(str5).length() + String.valueOf(str3).length());
        sb.append("\t");
        sb.append(str2);
        sb.append("\t");
        sb.append(i);
        sb.append("\t");
        sb.append(str);
        sb.append("\t");
        sb.append(i2);
        sb.append("\t");
        sb.append(str4);
        sb.append("\t");
        sb.append(str5);
        sb.append("\t");
        sb.append(f);
        sb.append("\t");
        sb.append(str3);
        return sb.toString();
    }
}
