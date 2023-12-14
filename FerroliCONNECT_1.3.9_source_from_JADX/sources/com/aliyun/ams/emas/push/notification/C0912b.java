package com.aliyun.ams.emas.push.notification;

import android.os.Parcel;
import android.os.Parcelable;

/* renamed from: com.aliyun.ams.emas.push.notification.b */
/* compiled from: Taobao */
final class C0912b implements Parcelable.Creator<CPushMessage> {
    C0912b() {
    }

    /* renamed from: a */
    public CPushMessage createFromParcel(Parcel parcel) {
        return new CPushMessage(parcel, (C0912b) null);
    }

    /* renamed from: a */
    public CPushMessage[] newArray(int i) {
        return new CPushMessage[i];
    }
}
