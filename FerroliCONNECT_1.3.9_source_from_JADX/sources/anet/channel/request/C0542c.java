package anet.channel.request;

import anet.channel.util.ALog;
import com.taobao.accs.common.Constants;
import org.android.spdy.SpdyErrorException;
import org.android.spdy.SpdySession;

/* renamed from: anet.channel.request.c */
/* compiled from: Taobao */
public class C0542c implements Cancelable {
    public static final C0542c NULL = new C0542c((SpdySession) null, 0, (String) null);

    /* renamed from: a */
    private final int f360a;

    /* renamed from: b */
    private final SpdySession f361b;

    /* renamed from: c */
    private final String f362c;

    public C0542c(SpdySession spdySession, int i, String str) {
        this.f361b = spdySession;
        this.f360a = i;
        this.f362c = str;
    }

    public void cancel() {
        try {
            if (this.f361b != null && this.f360a != 0) {
                ALog.m328i("awcn.TnetCancelable", "cancel tnet request", this.f362c, "streamId", Integer.valueOf(this.f360a));
                this.f361b.streamReset((long) this.f360a, 5);
            }
        } catch (SpdyErrorException e) {
            ALog.m326e("awcn.TnetCancelable", "request cancel failed.", this.f362c, e, Constants.KEY_ERROR_CODE, Integer.valueOf(e.SpdyErrorGetCode()));
        }
    }
}
