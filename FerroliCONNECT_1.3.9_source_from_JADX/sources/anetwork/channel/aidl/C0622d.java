package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anetwork.channel.aidl.d */
/* compiled from: Taobao */
final class C0622d implements Parcelable.Creator<ParcelableHeader> {
    C0622d() {
    }

    /* renamed from: a */
    public ParcelableHeader createFromParcel(Parcel parcel) {
        return ParcelableHeader.m368a(parcel);
    }

    /* renamed from: a */
    public ParcelableHeader[] newArray(int i) {
        return new ParcelableHeader[i];
    }
}
