package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anetwork.channel.aidl.b */
/* compiled from: Taobao */
final class C0620b implements Parcelable.Creator<DefaultProgressEvent> {
    C0620b() {
    }

    /* renamed from: a */
    public DefaultProgressEvent createFromParcel(Parcel parcel) {
        return DefaultProgressEvent.readFromParcel(parcel);
    }

    /* renamed from: a */
    public DefaultProgressEvent[] newArray(int i) {
        return new DefaultProgressEvent[i];
    }
}
