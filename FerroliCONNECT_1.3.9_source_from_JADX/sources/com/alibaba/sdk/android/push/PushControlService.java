package com.alibaba.sdk.android.push;

public interface PushControlService {

    public interface ConnectionChangeListener {
        void onConnect();

        void onDisconnect(String str, String str2);
    }

    void disconnect();

    boolean isConnected();

    void reconnect();

    void reset();

    void setConnectionChangeListener(ConnectionChangeListener connectionChangeListener);
}
