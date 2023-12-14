package com.topband.sdk.boiler;

import com.topband.sdk.boiler.message.boiler.BoilerID;
import com.topband.sdk.boiler.message.thermostat.ThermostatID2;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagePacket {
    public static short COUNT = 0;
    public static final short TYPE_CONTROL = 2;
    public static final short TYPE_DELETE = 5;
    public static final short TYPE_LIST = 4;
    public static final short TYPE_QUERY = 1;
    public static final short TYPE_RESPONSE = 3;
    public static final int VERSION_1 = 1;
    private static IMessagePacketParser mParser = new DefaultMessagePacketParser();
    protected long mAppStamp;
    protected int mInfoLength;
    protected int mMessageId;
    protected List<Message> mMessages;
    protected short mPacketType;
    protected int mTimeZone;
    protected int mVersionCode;
    protected long mWifiStamp;

    public MessagePacket(Message... messageArr) {
        this(1, messageArr);
    }

    public MessagePacket setTypeQuery() {
        this.mPacketType = 1;
        return this;
    }

    public MessagePacket setTypeControl() {
        this.mPacketType = 2;
        return this;
    }

    public MessagePacket setTypeDelete() {
        this.mPacketType = 5;
        return this;
    }

    public boolean isTypeResponse() {
        return this.mPacketType == 3;
    }

    public MessagePacket(short s, Message... messageArr) {
        this.mInfoLength = 19;
        this.mVersionCode = 1;
        this.mPacketType = s;
        this.mMessages = new ArrayList();
        for (Message add : messageArr) {
            this.mMessages.add(add);
        }
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void setVersionCode(int i) {
        this.mVersionCode = i;
    }

    public short getPacketType() {
        return this.mPacketType;
    }

    public void setPacketType(short s) {
        this.mPacketType = s;
    }

    public List<Message> getMessages() {
        return this.mMessages;
    }

    public void addMessage(Message message) {
        this.mMessages.add(message);
    }

    public int getInfoLength() {
        return this.mInfoLength;
    }

    public void setInfoLength(int i) {
        this.mInfoLength = i;
    }

    public int getMessageId() {
        return this.mMessageId;
    }

    public void setMessageId(int i) {
        this.mMessageId = i;
    }

    public long getWifiStamp() {
        return this.mWifiStamp;
    }

    public void setWifiStamp(long j) {
        this.mWifiStamp = j;
    }

    public long getTime() {
        return this.mAppStamp;
    }

    public void setTime(long j) {
        this.mAppStamp = j;
    }

    public int getTimeZone() {
        return this.mTimeZone;
    }

    public void setTimeZone(int i) {
        this.mTimeZone = i;
    }

    public int getLength() {
        int i = 2;
        for (Message length : this.mMessages) {
            i += length.getLength();
        }
        return i;
    }

    public byte[] toBytes() {
        byte[] short2BytesHtL = BinaryUtils.short2BytesHtL(this.mPacketType);
        byte int2byte = BinaryUtils.int2byte(this.mInfoLength);
        byte int2byte2 = BinaryUtils.int2byte(this.mMessageId);
        byte int2byte3 = BinaryUtils.int2byte(this.mVersionCode);
        byte[] long2bytes = BinaryUtils.long2bytes(this.mWifiStamp);
        byte[] long2bytes2 = BinaryUtils.long2bytes(this.mAppStamp);
        byte int2byte4 = BinaryUtils.int2byte(this.mTimeZone);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(short2BytesHtL);
            byteArrayOutputStream.write(int2byte);
            byteArrayOutputStream.write(int2byte2);
            byteArrayOutputStream.write(int2byte3);
            byteArrayOutputStream.write(long2bytes);
            byteArrayOutputStream.write(long2bytes2);
            byteArrayOutputStream.write(int2byte4);
            for (Message bytes : this.mMessages) {
                byteArrayOutputStream.write(bytes.toBytes());
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return byteArray;
        } catch (IOException e2) {
            e2.printStackTrace();
            try {
                byteArrayOutputStream.close();
                return null;
            } catch (IOException e3) {
                e3.printStackTrace();
                return null;
            }
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type : " + this.mPacketType);
        sb.append(" ; messages : \n");
        for (Message message : this.mMessages) {
            sb.append(message.toString() + "\n");
        }
        return sb.toString();
    }

    public static MessagePacket obtain(byte[] bArr) throws MessageFormatException, PacketFormatException, UnknownMessageException {
        return mParser.parsePacket(bArr);
    }

    public boolean isBoilerPacket() {
        if (this.mMessages == null) {
            return false;
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            if (this.mMessages.get(i) instanceof BoilerID) {
                return true;
            }
        }
        return false;
    }

    public boolean isThermostatPacket() {
        if (this.mMessages == null) {
            return false;
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            if (this.mMessages.get(i) instanceof ThermostatID2) {
                return true;
            }
        }
        return false;
    }
}
