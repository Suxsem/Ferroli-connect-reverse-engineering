package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anetwork.channel.aidl.c */
/* compiled from: Taobao */
final class C0621c implements Parcelable.Creator<NetworkResponse> {
    C0621c() {
    }

    /* renamed from: a */
    public NetworkResponse createFromParcel(Parcel parcel) {
        return NetworkResponse.readFromParcel(parcel);
    }

    /* renamed from: a */
    public NetworkResponse[] newArray(int i) {
        return new NetworkResponse[i];
    }
}
