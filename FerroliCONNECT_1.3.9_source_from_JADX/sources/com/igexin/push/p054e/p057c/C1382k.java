package com.igexin.push.p054e.p057c;

import com.igexin.p032b.p033a.p035b.C1177f;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.UByte;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

/* renamed from: com.igexin.push.e.c.k */
public class C1382k extends C1376e {

    /* renamed from: a */
    public long f2308a;

    /* renamed from: b */
    public byte f2309b;

    /* renamed from: c */
    public int f2310c;

    /* renamed from: d */
    public String f2311d;

    /* renamed from: e */
    public List<C1383l> f2312e;

    public C1382k() {
        this.f2278i = 4;
        this.f2279j = 20;
    }

    /* renamed from: a */
    private String m2239a(byte[] bArr, int i, int i2) {
        try {
            return new String(bArr, i, i2, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
        int i;
        this.f2308a = C1177f.m1343e(bArr, 0);
        this.f2309b = bArr[8];
        this.f2310c = C1177f.m1341d(bArr, 9) & -1;
        if (bArr.length > 13) {
            i = 14;
            byte b = bArr[13] & UByte.MAX_VALUE;
            if (b > 0) {
                this.f2312e = new ArrayList();
                int i2 = b + MqttWireMessage.MESSAGE_TYPE_DISCONNECT;
                while (i < i2) {
                    C1383l lVar = new C1383l();
                    this.f2312e.add(lVar);
                    int i3 = i + 1;
                    int a = C1177f.m1326a(bArr, i) & 255;
                    int i4 = i3 + 1;
                    int a2 = C1177f.m1326a(bArr, i3) & 255;
                    lVar.f2313a = (byte) a;
                    if ((a == 1 || a == 4) && a2 > 0) {
                        try {
                            lVar.f2314b = new String(bArr, i4, a2, "UTF-8");
                        } catch (Exception unused) {
                        }
                    }
                    i = i4 + a2;
                }
            }
        } else {
            i = 13;
        }
        if (bArr.length > i) {
            this.f2311d = m2239a(bArr, i + 1, bArr[i] & UByte.MAX_VALUE);
        }
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        int i;
        int i2;
        List<C1383l> list = this.f2312e;
        byte[] bArr = null;
        if (list != null && list.size() > 0) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (C1383l d : this.f2312e) {
                try {
                    byteArrayOutputStream.write(d.mo14829d());
                    bArr = byteArrayOutputStream.toByteArray();
                } catch (IOException unused) {
                }
            }
            try {
                byteArrayOutputStream.close();
            } catch (IOException unused2) {
            }
        }
        if (bArr != null) {
            i2 = bArr.length;
            i = i2 + 1;
        } else {
            i2 = 0;
            i = 1;
        }
        byte[] bArr2 = new byte[(i + 12 + this.f2311d.getBytes().length + 1)];
        int a = C1177f.m1325a(this.f2308a, bArr2, 0);
        int a2 = a + C1177f.m1324a(((this.f2309b & UByte.MAX_VALUE) << 24) | this.f2310c, bArr2, a);
        int c = a2 + C1177f.m1338c(i2, bArr2, a2);
        if (i2 > 0) {
            c += C1177f.m1327a(bArr, 0, bArr2, c, i2);
        }
        byte[] bytes = this.f2311d.getBytes();
        C1177f.m1338c(bytes.length, bArr2, c);
        System.arraycopy(bytes, 0, bArr2, c + 1, bytes.length);
        int length = bytes.length;
        return bArr2;
    }
}
