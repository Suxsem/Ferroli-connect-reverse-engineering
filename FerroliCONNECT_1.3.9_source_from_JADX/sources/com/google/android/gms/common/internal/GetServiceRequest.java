package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@KeepForSdk
@SafeParcelable.Class(creator = "GetServiceRequestCreator")
@SafeParcelable.Reserved({9})
public class GetServiceRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzd();
    @SafeParcelable.VersionField(mo12896id = 1)
    private final int version;
    @SafeParcelable.Field(mo12890id = 2)
    private final int zzdf;
    @SafeParcelable.Field(mo12890id = 3)
    private int zzdg;
    @SafeParcelable.Field(mo12890id = 4)
    String zzdh;
    @SafeParcelable.Field(mo12890id = 5)
    IBinder zzdi;
    @SafeParcelable.Field(mo12890id = 6)
    Scope[] zzdj;
    @SafeParcelable.Field(mo12890id = 7)
    Bundle zzdk;
    @SafeParcelable.Field(mo12890id = 8)
    Account zzdl;
    @SafeParcelable.Field(mo12890id = 10)
    Feature[] zzdm;
    @SafeParcelable.Field(mo12890id = 11)
    Feature[] zzdn;
    @SafeParcelable.Field(mo12890id = 12)
    private boolean zzdo;

    public GetServiceRequest(int i) {
        this.version = 4;
        this.zzdg = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
        this.zzdf = i;
        this.zzdo = true;
    }

    @SafeParcelable.Constructor
    GetServiceRequest(@SafeParcelable.Param(mo12893id = 1) int i, @SafeParcelable.Param(mo12893id = 2) int i2, @SafeParcelable.Param(mo12893id = 3) int i3, @SafeParcelable.Param(mo12893id = 4) String str, @SafeParcelable.Param(mo12893id = 5) IBinder iBinder, @SafeParcelable.Param(mo12893id = 6) Scope[] scopeArr, @SafeParcelable.Param(mo12893id = 7) Bundle bundle, @SafeParcelable.Param(mo12893id = 8) Account account, @SafeParcelable.Param(mo12893id = 10) Feature[] featureArr, @SafeParcelable.Param(mo12893id = 11) Feature[] featureArr2, @SafeParcelable.Param(mo12893id = 12) boolean z) {
        this.version = i;
        this.zzdf = i2;
        this.zzdg = i3;
        if ("com.google.android.gms".equals(str)) {
            this.zzdh = "com.google.android.gms";
        } else {
            this.zzdh = str;
        }
        if (i < 2) {
            this.zzdl = iBinder != null ? AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(iBinder)) : null;
        } else {
            this.zzdi = iBinder;
            this.zzdl = account;
        }
        this.zzdj = scopeArr;
        this.zzdk = bundle;
        this.zzdm = featureArr;
        this.zzdn = featureArr2;
        this.zzdo = z;
    }

    @KeepForSdk
    public Bundle getExtraArgs() {
        return this.zzdk;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.version);
        SafeParcelWriter.writeInt(parcel, 2, this.zzdf);
        SafeParcelWriter.writeInt(parcel, 3, this.zzdg);
        SafeParcelWriter.writeString(parcel, 4, this.zzdh, false);
        SafeParcelWriter.writeIBinder(parcel, 5, this.zzdi, false);
        SafeParcelWriter.writeTypedArray(parcel, 6, this.zzdj, i, false);
        SafeParcelWriter.writeBundle(parcel, 7, this.zzdk, false);
        SafeParcelWriter.writeParcelable(parcel, 8, this.zzdl, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 10, this.zzdm, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 11, this.zzdn, i, false);
        SafeParcelWriter.writeBoolean(parcel, 12, this.zzdo);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
