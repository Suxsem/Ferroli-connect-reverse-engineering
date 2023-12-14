package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.net.Socket;

/* renamed from: com.igexin.b.a.b.a.a.d */
public class C1159d extends Handler {
    public C1159d(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            switch (C1160e.f1553a[C1172q.m1304a()[message.what].ordinal()]) {
                case 1:
                    C1161f.m1252a().mo14245g();
                    return;
                case 2:
                    return;
                case 3:
                    C1161f.m1252a().mo14239a((Socket) message.obj);
                    return;
                case 4:
                    C1161f.m1252a().mo14246h();
                    return;
                case 5:
                    C1161f.m1252a().mo14244f();
                    return;
                case 6:
                    C1161f.m1252a().mo14240b();
                    return;
                default:
                    return;
            }
        } catch (Throwable unused) {
        }
    }
}
