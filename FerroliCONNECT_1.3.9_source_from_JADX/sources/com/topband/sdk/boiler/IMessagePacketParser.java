package com.topband.sdk.boiler;

public interface IMessagePacketParser {
    MessagePacket parsePacket(byte[] bArr) throws PacketFormatException, MessageFormatException, UnknownMessageException;
}
