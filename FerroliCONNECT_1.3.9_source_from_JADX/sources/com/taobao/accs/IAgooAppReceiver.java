package com.taobao.accs;

import java.util.Map;

/* compiled from: Taobao */
public abstract class IAgooAppReceiver extends IAppReceiverV2 {
    public Map<String, String> getAllServices() {
        return null;
    }

    public abstract String getAppkey();

    public String getService(String str) {
        return null;
    }

    public void onBindUser(String str, int i) {
    }

    public void onUnbindApp(int i) {
    }

    public void onUnbindUser(int i) {
    }
}
