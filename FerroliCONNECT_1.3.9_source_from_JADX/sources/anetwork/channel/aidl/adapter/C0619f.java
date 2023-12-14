package anetwork.channel.aidl.adapter;

import anet.channel.util.ALog;

/* renamed from: anetwork.channel.aidl.adapter.f */
/* compiled from: Taobao */
final class C0619f implements Runnable {
    C0619f() {
    }

    public void run() {
        if (C0617d.f632c) {
            C0617d.f632c = false;
            ALog.m327e("anet.RemoteGetter", "binding service timeout. reset status!", (String) null, new Object[0]);
        }
    }
}
