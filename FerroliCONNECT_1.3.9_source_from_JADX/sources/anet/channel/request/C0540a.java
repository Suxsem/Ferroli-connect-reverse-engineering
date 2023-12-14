package anet.channel.request;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: anet.channel.request.a */
/* compiled from: Taobao */
final class C0540a implements Parcelable.Creator<ByteArrayEntry> {
    C0540a() {
    }

    /* renamed from: a */
    public ByteArrayEntry createFromParcel(Parcel parcel) {
        ByteArrayEntry byteArrayEntry = new ByteArrayEntry((C0540a) null);
        byte[] unused = byteArrayEntry.bytes = new byte[parcel.readInt()];
        parcel.readByteArray(byteArrayEntry.bytes);
        int unused2 = byteArrayEntry.offset = parcel.readInt();
        int unused3 = byteArrayEntry.count = parcel.readInt();
        String unused4 = byteArrayEntry.contentType = parcel.readString();
        return byteArrayEntry;
    }

    /* renamed from: a */
    public ByteArrayEntry[] newArray(int i) {
        return new ByteArrayEntry[i];
    }
}
