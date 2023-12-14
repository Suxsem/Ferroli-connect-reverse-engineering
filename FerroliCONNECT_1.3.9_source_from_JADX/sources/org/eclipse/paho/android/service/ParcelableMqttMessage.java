package org.eclipse.paho.android.service;

import android.os.Parcel;
import android.os.Parcelable;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class ParcelableMqttMessage extends MqttMessage implements Parcelable {
    public static final Parcelable.Creator<ParcelableMqttMessage> CREATOR = new Parcelable.Creator<ParcelableMqttMessage>() {
        public ParcelableMqttMessage createFromParcel(Parcel parcel) {
            return new ParcelableMqttMessage(parcel);
        }

        public ParcelableMqttMessage[] newArray(int i) {
            return new ParcelableMqttMessage[i];
        }
    };
    String messageId = null;

    public int describeContents() {
        return 0;
    }

    ParcelableMqttMessage(MqttMessage mqttMessage) {
        super(mqttMessage.getPayload());
        setQos(mqttMessage.getQos());
        setRetained(mqttMessage.isRetained());
        setDuplicate(mqttMessage.isDuplicate());
    }

    ParcelableMqttMessage(Parcel parcel) {
        super(parcel.createByteArray());
        setQos(parcel.readInt());
        boolean[] createBooleanArray = parcel.createBooleanArray();
        setRetained(createBooleanArray[0]);
        setDuplicate(createBooleanArray[1]);
        this.messageId = parcel.readString();
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByteArray(getPayload());
        parcel.writeInt(getQos());
        parcel.writeBooleanArray(new boolean[]{isRetained(), isDuplicate()});
        parcel.writeString(this.messageId);
    }
}
