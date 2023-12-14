package com.topband.sdk.boiler;

import com.topband.sdk.boiler.util.BinaryUtils;

class DefaultMessagePacketParser implements IMessagePacketParser {
    DefaultMessagePacketParser() {
    }

    public MessagePacket parsePacket(byte[] bArr) throws PacketFormatException, MessageFormatException, UnknownMessageException {
        if (bArr == null) {
            return null;
        }
        short bytes2shortHtL = BinaryUtils.bytes2shortHtL(bArr[0], bArr[1]);
        int byte2int = BinaryUtils.byte2int(bArr[2]);
        int byte2int2 = BinaryUtils.byte2int(bArr[3]);
        int byte2int3 = BinaryUtils.byte2int(bArr[4]);
        byte[] bArr2 = new byte[8];
        System.arraycopy(bArr, 5, bArr2, 0, 8);
        long bytesToLong = BinaryUtils.bytesToLong(bArr2);
        byte[] bArr3 = new byte[8];
        System.arraycopy(bArr, 13, bArr3, 0, 8);
        long bytesToLong2 = BinaryUtils.bytesToLong(bArr3);
        MessagePacket messagePacket = new MessagePacket(bytes2shortHtL, new Message[0]);
        messagePacket.setInfoLength(byte2int);
        messagePacket.setVersionCode(byte2int3);
        messagePacket.setMessageId(byte2int2);
        messagePacket.setWifiStamp(bytesToLong);
        messagePacket.setTime(bytesToLong2);
        messagePacket.setTimeZone(BinaryUtils.byte2int(bArr[21]) + 12);
        for (byte[] create : MessageDataExtractor.extractMessageData(bArr)) {
            messagePacket.getMessages().add(MessageFactory.create(create));
        }
        return messagePacket;
    }
}
