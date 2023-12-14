package com.topband.tsmart;

import android.os.Parcel;
import android.os.Parcelable;

public class TBPushMessage implements Parcelable {
    public static final Parcelable.Creator<TBPushMessage> CREATOR = new Parcelable.Creator<TBPushMessage>() {
        public TBPushMessage createFromParcel(Parcel parcel) {
            return new TBPushMessage(parcel);
        }

        public TBPushMessage[] newArray(int i) {
            return new TBPushMessage[i];
        }
    };
    public String appId;
    public String content;
    public String messageId;
    public String title;
    public String traceInfo;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.messageId);
        parcel.writeString(this.appId);
        parcel.writeString(this.title);
        parcel.writeString(this.content);
        parcel.writeString(this.traceInfo);
    }

    public TBPushMessage() {
    }

    protected TBPushMessage(Parcel parcel) {
        this.messageId = parcel.readString();
        this.appId = parcel.readString();
        this.title = parcel.readString();
        this.content = parcel.readString();
        this.traceInfo = parcel.readString();
    }
}
