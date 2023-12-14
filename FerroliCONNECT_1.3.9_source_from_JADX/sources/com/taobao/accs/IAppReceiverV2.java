package com.taobao.accs;

/* compiled from: Taobao */
public abstract class IAppReceiverV2 extends IAppReceiverV1 {
    public abstract void onBindApp(int i, String str, String str2);

    public void onBindUser(String str, int i) {
    }

    public void onUnbindApp(int i) {
    }

    public void onUnbindUser(int i) {
    }

    @Deprecated
    public void onBindApp(int i, String str) {
        onBindApp(i, "", str);
    }

    public void onUnbindApp(int i, String str) {
        onUnbindApp(i);
    }

    public void onBindUser(String str, int i, String str2) {
        onBindUser(str, i);
    }

    public void onUnbindUser(int i, String str) {
        onUnbindUser(i);
    }
}
