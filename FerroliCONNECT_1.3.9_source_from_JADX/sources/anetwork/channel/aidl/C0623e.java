package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anetwork.channel.aidl.e */
/* compiled from: Taobao */
final class C0623e implements Parcelable.Creator<ParcelableRequest> {
    C0623e() {
    }

    /* renamed from: a */
    public ParcelableRequest createFromParcel(Parcel parcel) {
        return ParcelableRequest.readFromParcel(parcel);
    }

    /* renamed from: a */
    public ParcelableRequest[] newArray(int i) {
        return new ParcelableRequest[i];
    }
}
