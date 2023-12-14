package anet.channel.util;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: anet.channel.util.a */
/* compiled from: Taobao */
public class C0602a extends InputStream {

    /* renamed from: a */
    private InputStream f575a = null;

    /* renamed from: b */
    private long f576b = 0;

    public C0602a(InputStream inputStream) {
        if (inputStream != null) {
            this.f575a = inputStream;
            return;
        }
        throw new NullPointerException("input stream cannot be null");
    }

    /* renamed from: a */
    public long mo9102a() {
        return this.f576b;
    }

    public int read() throws IOException {
        this.f576b++;
        return this.f575a.read();
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.f575a.read(bArr, i, i2);
        if (read != -1) {
            this.f576b += (long) read;
        }
        return read;
    }
}
