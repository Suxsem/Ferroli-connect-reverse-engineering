package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConnectionInfoCreator")
public final class zzb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();
    @SafeParcelable.Field(mo12890id = 1)
    Bundle zzcz;
    @SafeParcelable.Field(mo12890id = 2)
    Feature[] zzda;

    @SafeParcelable.Constructor
    zzb(@SafeParcelable.Param(mo12893id = 1) Bundle bundle, @SafeParcelable.Param(mo12893id = 2) Feature[] featureArr) {
        this.zzcz = bundle;
        this.zzda = featureArr;
    }

    public zzb() {
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 1, this.zzcz, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzda, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
