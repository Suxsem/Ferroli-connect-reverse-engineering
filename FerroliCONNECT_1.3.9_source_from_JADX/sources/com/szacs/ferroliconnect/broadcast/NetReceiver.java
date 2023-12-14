package com.szacs.ferroliconnect.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.igexin.sdk.PushConsts;
import com.p107tb.appyunsdk.AppYunManager;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.util.NetUtils;

public class NetReceiver extends BroadcastReceiver {
    private static final String TAG = "NetReceiver";

    public void onReceive(Context context, Intent intent) {
        if (!PushConsts.ACTION_BROADCAST_NETWORK_CHANGE.equals(intent.getAction())) {
            return;
        }
        if (NetUtils.isNetworkConnected(context)) {
            Log.d(TAG, " NetReceiver ----- 网络已连接");
            reConnectMqtt(context);
            return;
        }
        Log.d(TAG, " NetReceiver ----- 网络已断开");
    }

    private void reConnectMqtt(Context context) {
        Log.d(TAG, " NetUtils.isNetworkConnected = " + NetUtils.isNetworkConnected(context) + " isForeground = " + MainApplication.isForeground + " mqtt is connect = " + AppYunManager.getInstance().isConnected());
        if (NetUtils.isNetworkConnected(context) && MainApplication.isForeground && !AppYunManager.getInstance().isConnected()) {
            setMqttClientId(context);
            AppYunManager.getInstance().initMqtt();
        }
    }

    public void setMqttClientId(Context context) {
        String string = context.getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        Log.d(TAG, " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }
}
