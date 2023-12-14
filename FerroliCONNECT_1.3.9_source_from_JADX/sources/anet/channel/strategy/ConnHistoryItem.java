package anet.channel.strategy;

import com.taobao.accs.common.Constants;
import java.io.Serializable;
import kotlin.UByte;

/* compiled from: Taobao */
class ConnHistoryItem implements Serializable {

    /* renamed from: a */
    byte f439a = 0;

    /* renamed from: b */
    long f440b = 0;

    /* renamed from: c */
    long f441c = 0;

    ConnHistoryItem() {
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public void mo8970a(boolean z) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - (z ? this.f440b : this.f441c) > 10000) {
            this.f439a = (this.f439a << 1) | (z ^ true) ? (byte) 1 : 0;
            if (z) {
                this.f440b = currentTimeMillis;
            } else {
                this.f441c = currentTimeMillis;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public int mo8969a() {
        int i = 0;
        for (int i2 = this.f439a & UByte.MAX_VALUE; i2 > 0; i2 >>= 1) {
            i += i2 & 1;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public boolean mo8971b() {
        return (this.f439a & 1) == 1;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public boolean mo8972c() {
        if (mo8969a() >= 3 && System.currentTimeMillis() - this.f441c <= 300000) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: d */
    public boolean mo8973d() {
        long j = this.f440b;
        long j2 = this.f441c;
        if (j <= j2) {
            j = j2;
        }
        return j != 0 && System.currentTimeMillis() - j > Constants.CLIENT_FLUSH_INTERVAL;
    }
}
