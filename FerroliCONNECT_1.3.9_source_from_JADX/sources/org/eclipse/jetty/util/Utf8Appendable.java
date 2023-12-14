package org.eclipse.jetty.util;

import java.io.IOException;
import kotlin.UByte;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

public abstract class Utf8Appendable {
    private static final byte[] BYTE_TABLE = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 3, 3, MqttWireMessage.MESSAGE_TYPE_UNSUBACK, 6, 6, 6, 5, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};
    protected static final Logger LOG = Log.getLogger((Class<?>) Utf8Appendable.class);
    public static final char REPLACEMENT = '�';
    private static final byte[] TRANS_TABLE = {0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, 36, 60, 96, 84, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 48, 72, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 0, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 24, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, 36, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ, MqttWireMessage.MESSAGE_TYPE_PINGREQ};
    private static final int UTF8_ACCEPT = 0;
    private static final int UTF8_REJECT = 12;
    protected final Appendable _appendable;
    private int _codep;
    protected int _state = 0;

    public abstract int length();

    public Utf8Appendable(Appendable appendable) {
        this._appendable = appendable;
    }

    /* access modifiers changed from: protected */
    public void reset() {
        this._state = 0;
    }

    public void append(byte b) {
        try {
            appendByte(b);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void append(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            try {
                appendByte(bArr[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean append(byte[] bArr, int i, int i2, int i3) {
        int i4 = i2 + i;
        while (i < i4) {
            try {
                if (length() > i3) {
                    return false;
                }
                appendByte(bArr[i]);
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void appendByte(byte b) throws IOException {
        if (b <= 0 || this._state != 0) {
            byte b2 = b & UByte.MAX_VALUE;
            byte b3 = BYTE_TABLE[b2];
            this._codep = this._state == 0 ? (255 >> b3) & b2 : (b2 & 63) | (this._codep << 6);
            byte b4 = TRANS_TABLE[this._state + b3];
            if (b4 == 0) {
                this._state = b4;
                int i = this._codep;
                if (i < 55296) {
                    this._appendable.append((char) i);
                    return;
                }
                for (char append : Character.toChars(i)) {
                    this._appendable.append(append);
                }
            } else if (b4 != 12) {
                this._state = b4;
            } else {
                String str = "byte " + TypeUtil.toHexString(b) + " in state " + (this._state / 12);
                this._codep = 0;
                this._state = 0;
                this._appendable.append(REPLACEMENT);
                throw new NotUtf8Exception(str);
            }
        } else {
            this._appendable.append((char) (b & UByte.MAX_VALUE));
        }
    }

    public boolean isUtf8SequenceComplete() {
        return this._state == 0;
    }

    public static class NotUtf8Exception extends IllegalArgumentException {
        public NotUtf8Exception(String str) {
            super("Not valid UTF8! " + str);
        }
    }

    /* access modifiers changed from: protected */
    public void checkState() {
        if (!isUtf8SequenceComplete()) {
            this._codep = 0;
            this._state = 0;
            try {
                this._appendable.append(REPLACEMENT);
                throw new NotUtf8Exception("incomplete UTF8 sequence");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String toReplacedString() {
        if (!isUtf8SequenceComplete()) {
            this._codep = 0;
            this._state = 0;
            try {
                this._appendable.append(REPLACEMENT);
                NotUtf8Exception notUtf8Exception = new NotUtf8Exception("incomplete UTF8 sequence");
                LOG.warn(notUtf8Exception.toString(), new Object[0]);
                LOG.debug(notUtf8Exception);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return this._appendable.toString();
    }
}
