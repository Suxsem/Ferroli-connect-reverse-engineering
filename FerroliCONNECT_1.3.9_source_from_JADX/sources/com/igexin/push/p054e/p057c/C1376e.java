package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p040d.C1180a;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* renamed from: com.igexin.push.e.c.e */
public abstract class C1376e extends C1180a {

    /* renamed from: i */
    public int f2278i;

    /* renamed from: j */
    public byte f2279j;

    /* renamed from: k */
    public byte f2280k = MqttWireMessage.MESSAGE_TYPE_UNSUBACK;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public int mo14837a(String str) {
        if (str.equals("UTF-8")) {
            return 1;
        }
        if (str.equals(StringUtil.__UTF16)) {
            return 2;
        }
        if (str.equals("UTF-16BE")) {
            return 16;
        }
        if (str.equals("UTF-16LE")) {
            return 17;
        }
        if (str.equals("GBK")) {
            return 25;
        }
        if (str.equals("GB2312")) {
            return 26;
        }
        if (str.equals("GB18030")) {
            return 27;
        }
        return str.equals(StringUtil.__ISO_8859_1) ? 33 : 1;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String mo14838a(byte b) {
        byte b2 = b & 63;
        if (b2 == 1) {
            return "UTF-8";
        }
        if (b2 == 2) {
            return StringUtil.__UTF16;
        }
        if (b2 == 16) {
            return "UTF-16BE";
        }
        if (b2 == 17) {
            return "UTF-16LE";
        }
        if (b2 == 33) {
            return StringUtil.__ISO_8859_1;
        }
        switch (b2) {
            case 25:
                return "GBK";
            case 26:
                return "GB2312";
            case 27:
                return "GB18030";
            default:
                return "UTF-8";
        }
    }

    /* renamed from: a */
    public abstract void mo14826a(byte[] bArr);

    /* renamed from: b */
    public int mo14231b() {
        return this.f2278i;
    }

    /* renamed from: d */
    public abstract byte[] mo14829d();
}
