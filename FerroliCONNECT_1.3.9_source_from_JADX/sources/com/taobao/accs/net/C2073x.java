package com.taobao.accs.net;

import com.taobao.accs.data.Message;

/* renamed from: com.taobao.accs.net.x */
/* compiled from: Taobao */
class C2073x implements Runnable {

    /* renamed from: a */
    final /* synthetic */ Message f3479a;

    /* renamed from: b */
    final /* synthetic */ boolean f3480b;

    /* renamed from: c */
    final /* synthetic */ C2071w f3481c;

    C2073x(C2071w wVar, Message message, boolean z) {
        this.f3481c = wVar;
        this.f3479a = message;
        this.f3480b = z;
    }

    public void run() {
        synchronized (this.f3481c.f3468t) {
            this.f3481c.m3669a(this.f3479a);
            if (this.f3481c.f3468t.size() == 0) {
                this.f3481c.f3468t.add(this.f3479a);
            } else {
                Message message = (Message) this.f3481c.f3468t.getFirst();
                if (this.f3479a.mo18386a() != 1) {
                    if (this.f3479a.mo18386a() != 0) {
                        if (this.f3479a.mo18386a() != 2 || message.mo18386a() != 2) {
                            this.f3481c.f3468t.addLast(this.f3479a);
                        } else if (!message.f3268d && this.f3479a.f3268d) {
                            this.f3481c.f3468t.removeFirst();
                            this.f3481c.f3468t.addFirst(this.f3479a);
                        }
                    }
                }
                this.f3481c.f3468t.addLast(this.f3479a);
                if (message.mo18386a() == 2) {
                    this.f3481c.f3468t.removeFirst();
                }
            }
            if (this.f3480b || this.f3481c.f3467s == 3) {
                try {
                    this.f3481c.f3468t.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
