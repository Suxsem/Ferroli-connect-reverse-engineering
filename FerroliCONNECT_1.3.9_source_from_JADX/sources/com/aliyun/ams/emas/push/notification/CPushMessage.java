package com.aliyun.ams.emas.push.notification;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: Taobao */
public class CPushMessage implements Parcelable {
    public static final Parcelable.Creator<CPushMessage> CREATOR = new C0912b();
    private String appId;
    private String content;
    private String messageId;
    private String title;
    private String traceInfo;

    public int describeContents() {
        return 0;
    }

    /* synthetic */ CPushMessage(Parcel parcel, C0912b bVar) {
        this(parcel);
    }

    public CPushMessage() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getTraceInfo() {
        return this.traceInfo;
    }

    public void setTraceInfo(String str) {
        this.traceInfo = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.appId);
        parcel.writeString(this.messageId);
        parcel.writeString(this.title);
        parcel.writeString(this.content);
        parcel.writeString(this.traceInfo);
    }

    private CPushMessage(Parcel parcel) {
        this.appId = parcel.readString();
        this.messageId = parcel.readString();
        this.title = parcel.readString();
        this.content = parcel.readString();
        this.traceInfo = parcel.readString();
    }
}
