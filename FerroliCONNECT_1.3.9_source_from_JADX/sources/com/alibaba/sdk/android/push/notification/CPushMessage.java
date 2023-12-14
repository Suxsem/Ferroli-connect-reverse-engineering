package com.alibaba.sdk.android.push.notification;

import android.os.Parcel;
import android.os.Parcelable;

public class CPushMessage implements Parcelable {
    public static final Parcelable.Creator<CPushMessage> CREATOR = new Parcelable.Creator<CPushMessage>() {
        /* renamed from: a */
        public CPushMessage createFromParcel(Parcel parcel) {
            return new CPushMessage(parcel);
        }

        /* renamed from: a */
        public CPushMessage[] newArray(int i) {
            return new CPushMessage[i];
        }
    };
    private String appId;
    private String content;
    private String messageId;
    private String title;
    private String traceInfo;

    public CPushMessage() {
    }

    private CPushMessage(Parcel parcel) {
        this.appId = parcel.readString();
        this.messageId = parcel.readString();
        this.title = parcel.readString();
        this.content = parcel.readString();
        this.traceInfo = parcel.readString();
    }

    public static CPushMessage from(com.aliyun.ams.emas.push.notification.CPushMessage cPushMessage) {
        CPushMessage cPushMessage2 = new CPushMessage();
        cPushMessage2.setAppId(cPushMessage.getAppId());
        cPushMessage2.setMessageId(cPushMessage.getMessageId());
        cPushMessage2.setTitle(cPushMessage.getTitle());
        cPushMessage2.setContent(cPushMessage.getContent());
        cPushMessage2.setTraceInfo(cPushMessage.getTraceInfo());
        return cPushMessage2;
    }

    /* renamed from: to */
    public static com.aliyun.ams.emas.push.notification.CPushMessage m904to(CPushMessage cPushMessage) {
        com.aliyun.ams.emas.push.notification.CPushMessage cPushMessage2 = new com.aliyun.ams.emas.push.notification.CPushMessage();
        cPushMessage2.setAppId(cPushMessage.getAppId());
        cPushMessage2.setMessageId(cPushMessage.getMessageId());
        cPushMessage2.setTitle(cPushMessage.getTitle());
        cPushMessage2.setContent(cPushMessage.getContent());
        cPushMessage2.setTraceInfo(cPushMessage.getTraceInfo());
        return cPushMessage2;
    }

    public int describeContents() {
        return 0;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getContent() {
        return this.content;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTraceInfo() {
        return this.traceInfo;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public void setTitle(String str) {
        this.title = str;
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
}
