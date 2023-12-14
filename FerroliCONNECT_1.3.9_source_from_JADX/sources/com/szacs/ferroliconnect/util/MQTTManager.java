package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.util.Log;
import com.p107tb.appyunsdk.AppYunManager;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.UserCenter;

public class MQTTManager {
    private static final String TAG = "MQTTManager";
    private static MQTTManager instance;
    private Context mContext;

    private MQTTManager(Context context) {
        this.mContext = context;
    }

    public static MQTTManager getInstance(Context context) {
        if (instance == null) {
            instance = new MQTTManager(context);
        }
        return instance;
    }

    public boolean checkMQTTStatus() {
        return AppYunManager.getInstance().isConnected();
    }

    public void reConnectMQTT() {
        Log.d(TAG, " reConnectMQTT NetUtils.isNetworkConnected = " + NetUtils.isNetworkConnected(this.mContext) + " MainApplication.isForeground = " + MainApplication.isForeground + " checkMQTTStatus() " + checkMQTTStatus());
        if (!NetUtils.isNetworkConnected(this.mContext)) {
            Log.d(TAG, " NETWORK NOT CONNECT");
        } else if (!MainApplication.isForeground) {
            Log.d(TAG, " App not foreground");
        } else if (checkMQTTStatus()) {
            Log.d(TAG, " mqtt is connect");
        } else {
            setMqttClientId();
            AppYunManager.getInstance().initMqtt();
        }
    }

    public void setMqttClientId() {
        String string = this.mContext.getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        Log.d(TAG, " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }
}
