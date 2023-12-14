package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anetwork.channel.NetworkEvent;

/* compiled from: Taobao */
public class DefaultProgressEvent implements Parcelable, NetworkEvent.ProgressEvent {
    public static final Parcelable.Creator<DefaultProgressEvent> CREATOR = new C0620b();

    /* renamed from: a */
    int f604a;

    /* renamed from: b */
    int f605b;

    /* renamed from: c */
    int f606c;

    /* renamed from: d */
    Object f607d;

    /* renamed from: e */
    byte[] f608e;

    public int describeContents() {
        return 0;
    }

    public String getDesc() {
        return "";
    }

    public DefaultProgressEvent() {
    }

    public DefaultProgressEvent(int i, int i2, int i3, byte[] bArr) {
        this.f604a = i;
        this.f605b = i2;
        this.f606c = i3;
        this.f608e = bArr;
    }

    public int getSize() {
        return this.f605b;
    }

    public int getTotal() {
        return this.f606c;
    }

    public Object getContext() {
        return this.f607d;
    }

    public void setContext(Object obj) {
        this.f607d = obj;
    }

    public byte[] getBytedata() {
        return this.f608e;
    }

    public int getIndex() {
        return this.f604a;
    }

    public String toString() {
        return "DefaultProgressEvent [index=" + this.f604a + ", size=" + this.f605b + ", total=" + this.f606c + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f604a);
        parcel.writeInt(this.f605b);
        parcel.writeInt(this.f606c);
        byte[] bArr = this.f608e;
        parcel.writeInt(bArr != null ? bArr.length : 0);
        parcel.writeByteArray(this.f608e);
    }

    public static DefaultProgressEvent readFromParcel(Parcel parcel) {
        DefaultProgressEvent defaultProgressEvent = new DefaultProgressEvent();
        try {
            defaultProgressEvent.f604a = parcel.readInt();
            defaultProgressEvent.f605b = parcel.readInt();
            defaultProgressEvent.f606c = parcel.readInt();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                byte[] bArr = new byte[readInt];
                parcel.readByteArray(bArr);
                defaultProgressEvent.f608e = bArr;
            }
        } catch (Exception unused) {
        }
        return defaultProgressEvent;
    }
}
