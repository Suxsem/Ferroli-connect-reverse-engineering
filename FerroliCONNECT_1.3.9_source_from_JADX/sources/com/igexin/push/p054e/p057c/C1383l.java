package com.igexin.push.p054e.p057c;

/* renamed from: com.igexin.push.e.c.l */
public class C1383l extends C1376e {

    /* renamed from: a */
    public byte f2313a;

    /* renamed from: b */
    public Object f2314b;

    /* renamed from: a */
    public void mo14826a(byte[] bArr) {
    }

    /* renamed from: d */
    public byte[] mo14829d() {
        byte b = this.f2313a;
        byte[] bytes = (b == 1 || b == 2 || (b != 3 && b == 4)) ? ((String) this.f2314b).getBytes() : null;
        if (bytes == null) {
            return null;
        }
        byte[] bArr = new byte[(bytes.length + 2)];
        bArr[0] = this.f2313a;
        bArr[1] = (byte) bytes.length;
        System.arraycopy(bytes, 0, bArr, 2, bytes.length);
        return bArr;
    }
}
