package com.igexin.p032b.p033a.p035b.p036a.p037a;

import android.os.Message;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.p036a.p037a.p038a.C1156d;
import com.igexin.p032b.p033a.p039c.C1179b;
import java.net.Socket;

/* renamed from: com.igexin.b.a.b.a.a.g */
class C1162g implements C1156d {

    /* renamed from: a */
    final /* synthetic */ C1161f f1570a;

    C1162g(C1161f fVar) {
        this.f1570a = fVar;
    }

    /* renamed from: a */
    public void mo14224a(C1176e eVar) {
        this.f1570a.f1567n.sendEmptyMessage(C1172q.INTERRUPT_SUCCESS.ordinal());
    }

    /* renamed from: a */
    public void mo14228a(Exception exc) {
        C1179b.m1354a("GS-M|c ex = " + exc.toString());
        this.f1570a.m1258i();
    }

    /* renamed from: a */
    public void mo14229a(String str) {
        this.f1570a.f1567n.sendEmptyMessage(C1172q.TCP_CREATE_SUCCESS.ordinal());
    }

    /* renamed from: a */
    public void mo14230a(Socket socket) {
        Message obtain = Message.obtain();
        obtain.obj = socket;
        obtain.what = C1172q.TCP_CONNECT_SUCCESS.ordinal();
        this.f1570a.f1567n.sendMessage(obtain);
    }
}
