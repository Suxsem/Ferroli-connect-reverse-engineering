package com.szacs.ferroliconnect;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunManagerListener;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.TherListUpdateBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.broadcast.MyBroadCastReceiver;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.service.MyService;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.CrashHandler;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NetUtils;
import com.szacs.ferroliconnect.util.SpUtil;
import com.taobao.accs.AccsState;
import com.taobao.accs.common.Constants;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.MessagePacket;
import com.topband.sdk.boiler.PacketFormatException;
import com.topband.sdk.boiler.UnknownMessageException;
import com.topband.sdk.boiler.message.thermostat.ThermostatID2;
import com.topband.sdk.boiler.util.BinaryUtils;
import com.topband.tsmart.TBPushMessage;
import com.topband.tsmart.TSmartPush;
import com.topband.tsmart.interfaces.PushDataCallback;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import p110io.reactivex.Observable;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p115uk.p116co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    public static String Language = null;
    public static String appSlug = null;
    private static boolean bDebug = false;
    public static String baseUrl;
    private static MainApplication instance;
    public static boolean isForeground;
    public static Typeface typeface;
    final String TAG = "MainApplication";
    /* access modifiers changed from: private */
    public int activityNum = 0;
    private Disposable timerDisposable;

    private void initBugly() {
    }

    static /* synthetic */ int access$308(MainApplication mainApplication) {
        int i = mainApplication.activityNum;
        mainApplication.activityNum = i + 1;
        return i;
    }

    static /* synthetic */ int access$310(MainApplication mainApplication) {
        int i = mainApplication.activityNum;
        mainApplication.activityNum = i - 1;
        return i;
    }

    public void onCreate() {
        super.onCreate();
        initServer();
        instance = this;
        typeface = Typeface.createFromAsset(getAssets(), "fonts/YaHei_Light.ttf");
        Log.i("MainApplication", "flavor； ferroli");
        Language = SpUtil.getInstance(this).getString("language");
        LanguageUtil.changeAppLanguage(this, Language);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/YaHei_Light.ttf").setFontAttrId(C1683R.attr.fontPath).build());
        initForgroundListener();
        FileUtil.deleteFile(FileUtil.CARSH_CACHE_DIR, 20);
        CrashHandler.getInstance().init(this);
        initPush();
        AppYunManager.getInstance().init(instance, baseUrl, appSlug).openDebug(false);
        AppYunManager.getInstance().setAutoInitMqtt(false);
        AppYunManager.getInstance().setUseSsl(!bDebug);
        AppYunManager.getInstance().setErrorMsgLanguage(LanguageUtil.getSystemLocale(this).getLanguage().equals(LanguageUtil.LOGOGRAM_CHINESE) ? LanguageUtil.getSystemLocale(this).getLanguage() : "en");
        AppYunManager.getInstance().setYunManagerListener(new IAppYunManagerListener() {
            public void yunPrepareFailure(int i, String str) {
                Log.w("MainApplication", " yunPrepareFailure-----------");
                MainApplication.this.reConnectMqtt();
            }

            public void yunConnectLost(Throwable th) {
                LogUtil.m3315d("MainApplication", MainApplication.this.getString(C1683R.string.public_mqtt_lost_connect));
                MainApplication.this.reConnectMqtt();
            }

            public void yunConnectSuccess(IMqttToken iMqttToken) {
                LogUtil.m3315d("MainApplication", MainApplication.this.getString(C1683R.string.public_mqtt_connect_success));
                EventBus.getDefault().post(new MqttEvent(1));
                String mqttClientId = AppYunManager.getInstance().getMqttClientId();
                String slug = UserCenter.getUserInfo().getSlug();
                Log.d("MainApplication", " yunConnectSuccess SaveMqttClientId key = " + slug + " SaveMqttClientId = " + mqttClientId);
                SharedPreferences.Editor edit = MainApplication.this.getSharedPreferences("ferroli_user", 0).edit();
                edit.putString(slug, mqttClientId);
                edit.apply();
            }

            public void yunConnectFailed(IMqttToken iMqttToken, Throwable th) {
                LogUtil.m3315d("MainApplication", MainApplication.this.getString(C1683R.string.public_mqtt_connect_faile));
                MainApplication.this.reConnectMqtt();
            }

            public void serverResponse(MqttMessage mqttMessage) {
                Log.d("MainApplication", "serverResponse: " + BinaryUtils.bytes2String(mqttMessage.getPayload()));
            }

            public void serverToAppNotify(MqttMessage mqttMessage) {
                Log.d("MainApplication", "serverToAppNotify: " + BinaryUtils.bytes2String(mqttMessage.getPayload()));
            }

            public void serverToAppNotifyAll(MqttMessage mqttMessage) {
                Log.d("MainApplication", "serverToAppNotifyAll : " + BinaryUtils.bytes2String(mqttMessage.getPayload()));
            }

            public void deviceToAppMsg(String str, String str2, MqttMessage mqttMessage) {
                Log.d("MainApplication", "deviceToAppMsg product code : " + str + "; device code : " + str2);
                StringBuilder sb = new StringBuilder();
                sb.append("deviceToAppMsg : ");
                sb.append(BinaryUtils.bytes2String(mqttMessage.getPayload()));
                Log.d("MainApplication", sb.toString());
                try {
                    MessagePacket obtain = MessagePacket.obtain(mqttMessage.getPayload());
                    long wifiStamp = obtain.getWifiStamp();
                    LogUtil.m3315d("MainApplication", "  deviceToAppMsg messagePacket wifiStamp = " + wifiStamp);
                    LogUtil.m3315d("MainApplication", "messagePacket count == " + obtain.getMessages().size());
                    MsgBean msgBean = UserCenter.getMsgBean();
                    if (msgBean == null) {
                        msgBean = new MsgBean();
                    }
                    msgBean.setmDeviceCode(str2);
                    msgBean.setmProductCode(str);
                    msgBean.setmWifiStamp(wifiStamp);
                    if (Constant.PARSE_DATA) {
                        for (Message next : obtain.getMessages()) {
                            if (next instanceof ThermostatID2) {
                                String id = ((ThermostatID2) next).getId();
                                LogUtil.m3315d("MainApplication", "ssid: " + id + ",getSdid: " + UserCenter.getChildDevicesBean().getSdid());
                                if (UserCenter.getChildDevicesBean() != null && UserCenter.getChildDevicesBean().getSdid().equalsIgnoreCase(id)) {
                                    msgBean.parseData(obtain);
                                    EventBus.getDefault().post(msgBean);
                                }
                            }
                        }
                    } else {
                        msgBean.parseData(obtain);
                        EventBus.getDefault().post(msgBean);
                    }
                    EventBus.getDefault().post(new BaseEvent(Event.SERVER_RESPONSE_SUCCESS));
                    Log.d("MainApplication", "messagePacket version : " + obtain.getVersionCode() + " id: " + obtain.getMessageId() + " Time: " + obtain.getTime());
                    MainApplication mainApplication = MainApplication.this;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(obtain.getTime());
                    sb2.append("");
                    mainApplication.SendLog(str2, sb2.toString(), String.valueOf(System.currentTimeMillis()));
                } catch (MessageFormatException e) {
                    e.printStackTrace();
                    Log.e("MainApplication", "deviceToAppMsg MessageFormatException : " + e.getMessage());
                } catch (PacketFormatException e2) {
                    Log.e("MainApplication", "deviceToAppMsg PacketFormatException : " + e2.getMessage());
                    e2.printStackTrace();
                } catch (UnknownMessageException e3) {
                    Log.e("MainApplication", "deviceToAppMsg UnknownMessageException : " + e3.getMessage());
                    e3.printStackTrace();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }

            public void deviceStatus(String str, String str2, MqttMessage mqttMessage) {
                Log.d("MainApplication", "deviceStatus product code : " + str + "; device code : " + str2 + "; message : " + BinaryUtils.bytes2String(mqttMessage.getPayload()));
                try {
                    MessagePacket obtain = MessagePacket.obtain(mqttMessage.getPayload());
                    long wifiStamp = obtain.getWifiStamp();
                    LogUtil.m3315d("MainApplication", "  deviceStatus messagePacket wifiStamp = " + wifiStamp);
                    MsgBean msgBean = UserCenter.getMsgBean();
                    if (msgBean == null) {
                        msgBean = new MsgBean();
                    }
                    msgBean.setmDeviceCode(str2);
                    msgBean.setmProductCode(str);
                    msgBean.setmWifiStamp(wifiStamp);
                    if (Constant.PARSE_DATA) {
                        for (Message next : obtain.getMessages()) {
                            if (next instanceof ThermostatID2) {
                                String id = ((ThermostatID2) next).getId();
                                LogUtil.m3315d("MainApplication", "ssid: " + id + ",getSdid: " + UserCenter.getChildDevicesBean().getSdid());
                                if (UserCenter.getChildDevicesBean() != null && UserCenter.getChildDevicesBean().getSdid().equalsIgnoreCase(id)) {
                                    msgBean.parseData(obtain);
                                    EventBus.getDefault().post(msgBean);
                                }
                            }
                        }
                        return;
                    }
                    msgBean.parseData(obtain);
                    EventBus.getDefault().post(msgBean);
                } catch (MessageFormatException e) {
                    e.printStackTrace();
                    Log.d("MainApplication", "deviceToAppMsg MessageFormatException : " + e.getMessage());
                } catch (PacketFormatException e2) {
                    Log.d("MainApplication", "deviceToAppMsg PacketFormatException : " + e2.getMessage());
                    e2.printStackTrace();
                } catch (UnknownMessageException e3) {
                    Log.d("MainApplication", "deviceToAppMsg UnknownMessageException : " + e3.getMessage());
                    e3.printStackTrace();
                }
            }

            public void therListUpdate(String str, String str2, MqttMessage mqttMessage) {
                Log.d("MainApplication", "therListUpdate product code : " + str + "; device code : " + str2 + "; message : " + mqttMessage);
                EventBus.getDefault().postSticky(new TherListUpdateBean(mqttMessage));
            }
        });
    }

    private void initServer() {
        appSlug = "d970e7bd-35f6-46ee-8943-c32c77bc67aa";
        baseUrl = "https://eu-api.topband-cloud.com/";
    }

    private void initPush() {
        TSmartPush.instance().setChannelId("16845");
        TSmartPush.instance().initPush(this, true);
        TSmartPush.instance().setPushDataCallback(new PushDataCallback() {
            public void onMessage(Context context, TBPushMessage tBPushMessage) {
            }

            public void onNotificationOpened(Context context, String str, String str2, Map<String, String> map) {
            }

            public void onNotificationReceivedInApp(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4) {
            }

            public void onNotification(Context context, String str, String str2, Map<String, String> map) {
                String str3 = map.get(Constants.KEY_DATA);
                Log.e("MainApplication", "data:" + str3 + "---device_code:" + map.get(com.p107tb.appyunsdk.Constant.DEVICE_CODE));
                try {
                    JSONObject jSONObject = new JSONObject(str3);
                    MainApplication.this.sendBroadcast(MainApplication.this.getGtIntent(context, jSONObject.getString("bfi"), jSONObject.getString(AccsState.BIND_APP_FROM_CACHE)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        String loadMetaData = loadMetaData(this, "google_send_id");
        String loadMetaData2 = loadMetaData(this, "google_application_id");
        if (!TextUtils.isEmpty(loadMetaData) && !TextUtils.isEmpty(loadMetaData2)) {
            TSmartPush.instance().GcmRegister(this, loadMetaData, loadMetaData2);
        }
    }

    private String loadMetaData(Application application, String str) {
        String str2;
        try {
            ApplicationInfo applicationInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), 128);
            if (applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(str)) {
                printLog("AndroidManifest.xml need to add: <application > <meta-data  android:name=\"" + str + "\"  android:value=\"xxxx\"/>  </application>");
                return null;
            }
            Object obj = applicationInfo.metaData.get(str);
            if (obj == null) {
                return null;
            }
            if (obj instanceof Integer) {
                str2 = String.valueOf(applicationInfo.metaData.getInt(str));
            } else {
                str2 = obj.toString();
            }
            return str2;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void printLog(String str) {
        Log.e("MainApplication", "TSmartPush->" + str);
    }

    private void initService() {
        startService(new Intent(this, MyService.class));
    }

    private void initForgroundListener() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            public void onActivityDestroyed(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            public void onActivityStarted(Activity activity) {
                if (MainApplication.this.activityNum == 0) {
                    Log.d("MainApplication", " ActivityLifecycleCallbacks onActivityStarted App foreground");
                    MainApplication.isForeground = true;
                    AppYunManager.getInstance().setForeground(MainApplication.isForeground);
                    MainApplication.this.reConnectMqtt();
                }
                MainApplication.access$308(MainApplication.this);
            }

            public void onActivityStopped(Activity activity) {
                MainApplication.access$310(MainApplication.this);
                if (MainApplication.this.activityNum == 0) {
                    MainApplication.isForeground = false;
                    Log.d("MainApplication", " ActivityLifecycleCallbacks onActivityStarted App background");
                    AppYunManager.getInstance().setForeground(MainApplication.isForeground);
                }
            }
        });
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public String getVersionName() {
        return getPackageInfo().versionName;
    }

    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    private PackageInfo getPackageInfo() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 16384);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLanguage() {
        return Language;
    }

    public static void setLanguage(String str) {
        Language = str;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(String str) {
        baseUrl = str;
    }

    public static String getAppSlug() {
        return appSlug;
    }

    public static void setAppSlug(String str) {
        appSlug = str;
    }

    /* access modifiers changed from: private */
    public void SendLog(String str, String str2, String str3) {
        String replace = "{\"type\":\"RES_TO_DEV\",\"data\":{\"device_code\":\"devicecode\",\"loop_sn\":count,\"timestamp\":timer,\"status\":\"OK\"}}".replace("devicecode", str).replace("count", str2).replace("timer", str3);
        Log.d("MainApplication", " SendLog json = " + replace);
        AppYunManager.getInstance().pushLogToServer(replace.getBytes(), new IAppYunManagerActionListener() {
            public void onSuccess(IMqttToken iMqttToken) {
                LogUtil.m3315d("MainApplication", "log数据上报成功");
            }

            public void onFailure(IMqttToken iMqttToken, Throwable th) {
                LogUtil.m3315d("MainApplication", "log数据上报失败");
            }
        });
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageUtil.setLocal(context));
        MultiDex.install(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        LanguageUtil.setLocal(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043 A[SYNTHETIC, Splitter:B:19:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0050 A[SYNTHETIC, Splitter:B:27:0x0050] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getProcessName(int r5) {
        /*
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            r3.<init>()     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.lang.String r4 = "/proc/"
            r3.append(r4)     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            r3.append(r5)     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.lang.String r5 = "/cmdline"
            r3.append(r5)     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.lang.String r5 = r3.toString()     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            r2.<init>(r5)     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x003c, all -> 0x003a }
            java.lang.String r5 = r1.readLine()     // Catch:{ Throwable -> 0x0038 }
            boolean r2 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Throwable -> 0x0038 }
            if (r2 != 0) goto L_0x002f
            java.lang.String r5 = r5.trim()     // Catch:{ Throwable -> 0x0038 }
        L_0x002f:
            r1.close()     // Catch:{ IOException -> 0x0033 }
            goto L_0x0037
        L_0x0033:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0037:
            return r5
        L_0x0038:
            r5 = move-exception
            goto L_0x003e
        L_0x003a:
            r5 = move-exception
            goto L_0x004e
        L_0x003c:
            r5 = move-exception
            r1 = r0
        L_0x003e:
            r5.printStackTrace()     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x004b
            r1.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004b:
            return r0
        L_0x004c:
            r5 = move-exception
            r0 = r1
        L_0x004e:
            if (r0 == 0) goto L_0x0058
            r0.close()     // Catch:{ IOException -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0058:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.MainApplication.getProcessName(int):java.lang.String");
    }

    public static boolean isbDebug() {
        return bDebug;
    }

    public static void setbDebug(boolean z) {
        bDebug = z;
    }

    public void onTerminate() {
        super.onTerminate();
        Disposable disposable = this.timerDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.timerDisposable.dispose();
        }
    }

    /* access modifiers changed from: private */
    public void reConnectMqtt() {
        Observable.timer(5, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            public void accept(Long l) throws Exception {
                Log.d("MainApplication", " NetUtils.isNetworkConnected = " + NetUtils.isNetworkConnected(MainApplication.this) + " isForeground = " + MainApplication.isForeground + " mqtt is connect = " + AppYunManager.getInstance().isConnected());
                if (NetUtils.isNetworkConnected(MainApplication.this) && MainApplication.isForeground && !AppYunManager.getInstance().isConnected()) {
                    MainApplication.this.setMqttClientId();
                    AppYunManager.getInstance().initMqtt();
                }
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
            }
        });
    }

    public void setMqttClientId() {
        String string = getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        Log.d("MainApplication", " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }

    /* access modifiers changed from: private */
    public Intent getGtIntent(Context context, String str, String str2) {
        String[] split = str2.split("x");
        String[] split2 = str.split("x");
        int parseInt = Integer.parseInt(split[1], 16);
        int parseInt2 = Integer.parseInt(split2[1], 16);
        Log.i("MainApplication", "errorCode: " + parseInt + ",errorType: " + parseInt2);
        Intent intent = new Intent(context, MyBroadCastReceiver.class);
        intent.setAction(MyBroadCastReceiver.BROADCAST_SHOW_GT_TOAST);
        intent.putExtra("error_code", parseInt);
        intent.putExtra("error_type", parseInt2);
        return intent;
    }
}
