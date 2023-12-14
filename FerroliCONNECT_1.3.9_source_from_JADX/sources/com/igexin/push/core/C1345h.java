package com.igexin.push.core;

import android.os.HandlerThread;

/* renamed from: com.igexin.push.core.h */
public class C1345h extends HandlerThread {
    public C1345h() {
        super("CoreThread");
    }

    /* access modifiers changed from: protected */
    public void onLooperPrepared() {
        C1340e.m2032a().mo14706c();
    }
}
