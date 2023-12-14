package com.szacs.ferroliconnect.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.util.LogUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyService extends Service {
    public static final String ACTION_MQTT_START = "Service.MQTT_START";
    private static final String TAG = "MyService";
    private SharedPreferences preferences;

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " MyService onCreate");
        this.preferences = getSharedPreferences("ferroli_user", 0);
        EventBus.getDefault().register(this);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            Log.w(TAG, "onStartCommand intent = null");
            return super.onStartCommand(new Intent(), i, i2);
        }
        String action = intent.getAction();
        if (action == null || action.equals("")) {
            Log.w(TAG, " onStartCommand no action");
            return super.onStartCommand(new Intent(), i, i2);
        }
        Log.w(TAG, "onStartCommand action = " + action);
        char c = 65535;
        if (action.hashCode() == -1824800672 && action.equals(ACTION_MQTT_START)) {
            c = 0;
        }
        if (c == 0) {
            startMqtt();
        }
        return super.onStartCommand(intent, i, i2);
    }

    private void startMqtt() {
        setMqttClientId();
        AppYunManager.getInstance().initMqtt();
    }

    private void doLoginByToken() {
        AppYunManager.getInstance().loginByToken(this.preferences.getString("token", ""), new IYunManagerLoginListener() {
            public void userLoginSuccess(String str, UserResponse userResponse) {
                UserCenter.setUserInfo(userResponse);
                MyService.this.setMqttClientId();
                AppYunManager.getInstance().initMqtt();
            }

            public void userLoginFailure(int i, String str) {
                LogUtil.m3315d(MyService.TAG, "code: " + i + "   msg: " + str);
            }
        });
    }

    public void setMqttClientId() {
        String string = getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        Log.d(TAG, " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttEvent mqttEvent) {
        if (mqttEvent.getState() == 2) {
            Log.d(TAG, " myservice onEvent mqtt connect lost");
            startMqtt();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " Myservice gg");
    }
}
