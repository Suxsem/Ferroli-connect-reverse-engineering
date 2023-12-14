package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anetwork.channel.aidl.a */
/* compiled from: Taobao */
final class C0613a implements Parcelable.Creator<DefaultFinishEvent> {
    C0613a() {
    }

    /* renamed from: a */
    public DefaultFinishEvent createFromParcel(Parcel parcel) {
        return DefaultFinishEvent.m365a(parcel);
    }

    /* renamed from: a */
    public DefaultFinishEvent[] newArray(int i) {
        return new DefaultFinishEvent[i];
    }
}
