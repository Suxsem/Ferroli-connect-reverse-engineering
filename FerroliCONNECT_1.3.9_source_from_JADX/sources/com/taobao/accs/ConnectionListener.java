package com.taobao.accs;

/* compiled from: Taobao */
public interface ConnectionListener {
    void onConnect();

    void onDisconnect(int i, String str);
}
