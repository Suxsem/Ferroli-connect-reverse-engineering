package com.taobao.agoo;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.aliyun.ams.emas.push.AgooInnerService;
import com.aliyun.ams.emas.push.C0910h;
import com.aliyun.ams.emas.push.CommonCallback;
import com.aliyun.ams.emas.push.IReportPushArrive;
import com.aliyun.ams.emas.push.notification.CPushMessage;
import com.aliyun.ams.emas.push.p029a.C0903a;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsException;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.p105a.C2128b;
import com.taobao.agoo.p105a.p106a.C2124a;
import com.taobao.agoo.p105a.p106a.C2126c;
import com.taobao.agoo.p105a.p106a.C2127d;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.control.AgooFactory;

/* compiled from: Taobao */
public final class TaobaoRegister {
    private static final int EVENT_ID = 66001;
    static final String PREFERENCES = "Agoo_AppStore";
    static final String PROPERTY_APP_NOTIFICATION_CUSTOM_SOUND = "app_notification_custom_sound";
    static final String PROPERTY_APP_NOTIFICATION_ICON = "app_notification_icon";
    static final String PROPERTY_APP_NOTIFICATION_SOUND = "app_notification_sound";
    static final String PROPERTY_APP_NOTIFICATION_VIBRATE = "app_notification_vibrate";
    private static final String SERVICEID = "agooSend";
    protected static final String TAG = "TaobaoRegister";
    /* access modifiers changed from: private */
    public static C2128b mRequestListener;

    /* renamed from: com.taobao.agoo.TaobaoRegister$b */
    /* compiled from: Taobao */
    private interface C2115b {
        /* renamed from: a */
        byte[] mo18616a(String str, String str2);
    }

    @Deprecated
    public static void setBuilderSound(Context context, String str) {
    }

    @Deprecated
    public static void setNotificationIcon(Context context, int i) {
    }

    @Deprecated
    public static void setNotificationSound(Context context, boolean z) {
    }

    @Deprecated
    public static void setNotificationVibrate(Context context, boolean z) {
    }

    private TaobaoRegister() {
        throw new UnsupportedOperationException();
    }

    public static synchronized void setAccsConfigTag(Context context, String str) {
        synchronized (TaobaoRegister.class) {
            Config.f4060a = str;
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag != null) {
                ALog.m3728i(TAG, "setAccsConfigTag", "config", configByTag.toString());
                AdapterGlobalClientInfo.mAuthCode = configByTag.getAuthCode();
                Config.setAgooAppKey(context, configByTag.getAppKey());
                AdapterUtilityImpl.mAgooAppSecret = configByTag.getAppSecret();
                if (!TextUtils.isEmpty(AdapterUtilityImpl.mAgooAppSecret)) {
                    AdapterGlobalClientInfo.mSecurityType = 2;
                }
                C0910h.m1074a(context);
            } else {
                throw new RuntimeException("accs config not exist!! please set accs config first!!");
            }
        }
    }

    @Deprecated
    public static synchronized void register(Context context, String str, String str2, String str3, IRegister iRegister) throws AccsException {
        synchronized (TaobaoRegister.class) {
            register(context, str, str, str2, str3, iRegister);
        }
    }

    public static synchronized void register(Context context, String str, String str2, String str3, String str4, IRegister iRegister) throws AccsException {
        Context context2 = context;
        String str5 = str;
        String str6 = str2;
        String str7 = str3;
        synchronized (TaobaoRegister.class) {
            if (context2 != null) {
                if (!TextUtils.isEmpty(str2)) {
                    if (!TextUtils.isEmpty(str)) {
                        ALog.m3728i(TAG, C2126c.JSON_CMD_REGISTER, Constants.KEY_APP_KEY, str6, Constants.KEY_CONFIG_TAG, str5);
                        Context applicationContext = context.getApplicationContext();
                        Config.f4060a = str5;
                        Config.setAgooAppKey(context, str2);
                        AdapterUtilityImpl.mAgooAppSecret = str7;
                        if (!TextUtils.isEmpty(str3)) {
                            AdapterGlobalClientInfo.mSecurityType = 2;
                        }
                        C0910h.m1074a(context);
                        AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
                        if (configByTag == null) {
                            new AccsClientConfig.Builder().setAppKey(str2).setAppSecret(str7).setTag(str).build();
                        } else {
                            AdapterGlobalClientInfo.mAuthCode = configByTag.getAuthCode();
                        }
                        IACCSManager accsInstance = ACCSManager.getAccsInstance(context, str2, str);
                        accsInstance.bindApp(applicationContext, str2, str3, str4, new C2130c(iRegister, applicationContext, accsInstance, str2, str4));
                        return;
                    }
                }
            }
            ALog.m3727e(TAG, "register params null", "appkey", str6, Constants.KEY_CONFIG_TAG, str5);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$c */
    /* compiled from: Taobao */
    private static class C2116c implements C2115b {
        private C2116c() {
        }

        /* synthetic */ C2116c(C2130c cVar) {
            this();
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3828b(str, str2);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$a */
    /* compiled from: Taobao */
    private static class C2114a implements C2115b {

        /* renamed from: a */
        private final String f3569a;

        /* synthetic */ C2114a(String str, C2130c cVar) {
            this(str);
        }

        private C2114a(String str) {
            this.f3569a = str;
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3829b(str, str2, this.f3569a);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$d */
    /* compiled from: Taobao */
    private static class C2117d implements C2115b {

        /* renamed from: a */
        private final String f3570a;

        /* renamed from: b */
        private final String f3571b;

        /* synthetic */ C2117d(String str, String str2, C2130c cVar) {
            this(str, str2);
        }

        private C2117d(String str, String str2) {
            this.f3570a = str;
            this.f3571b = str2;
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3827a(str, str2, this.f3570a, this.f3571b);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$e */
    /* compiled from: Taobao */
    private static class C2118e implements C2115b {
        private C2118e() {
        }

        /* synthetic */ C2118e(C2130c cVar) {
            this();
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3825a(str, str2);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$f */
    /* compiled from: Taobao */
    private static class C2119f implements C2115b {

        /* renamed from: a */
        private final String f3572a;

        public C2119f(String str) {
            this.f3572a = str;
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3826a(str, str2, this.f3572a);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$h */
    /* compiled from: Taobao */
    private static class C2121h implements C2115b {

        /* renamed from: a */
        private final String f3574a;

        public C2121h(String str) {
            this.f3574a = str;
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3830c(str, str2, this.f3574a);
        }
    }

    /* renamed from: com.taobao.agoo.TaobaoRegister$g */
    /* compiled from: Taobao */
    private static class C2120g implements C2115b {

        /* renamed from: a */
        private final String f3573a;

        public C2120g(String str) {
            this.f3573a = str;
        }

        /* renamed from: a */
        public byte[] mo18616a(String str, String str2) {
            return C2124a.m3831d(str, str2, this.f3573a);
        }
    }

    /* access modifiers changed from: private */
    public static void doAliasOperation(String str, Context context, ICallback iCallback, C2115b bVar) {
        ErrorCode errorCode;
        ALog.m3728i(TAG, str, new Object[0]);
        String deviceToken = Config.getDeviceToken(context);
        String b = Config.m3894b(context);
        if (TextUtils.isEmpty(b) || TextUtils.isEmpty(deviceToken) || context == null) {
            if (iCallback != null) {
                if (context == null) {
                    ErrorBuilder copy = C2122a.INVALID_ARG.copy();
                    errorCode = copy.detail(str + " context is null").build();
                } else if (TextUtils.isEmpty(deviceToken)) {
                    ErrorBuilder copy2 = C2122a.INVALID_ARG.copy();
                    errorCode = copy2.detail(str + " deviceId is null").build();
                } else {
                    ErrorBuilder copy3 = C2122a.INVALID_ARG.copy();
                    errorCode = copy3.detail(str + " appKey is null").build();
                }
                iCallback.onFailure(errorCode.getCode(), errorCode.getMsg());
            }
            ALog.m3727e(TAG, str + " param null", "appkey", b, "deviceId", deviceToken, "context", context);
            return;
        }
        try {
            if (mRequestListener == null) {
                mRequestListener = new C2128b(context.getApplicationContext());
            }
            IACCSManager accsInstance = ACCSManager.getAccsInstance(context, b, Config.m3896c(context));
            if (C2128b.f3603b.mo18619b(context.getPackageName())) {
                accsInstance.registerDataListener(context, "AgooDeviceCmd", mRequestListener);
                String sendRequest = accsInstance.sendRequest(context, new ACCSManager.AccsRequest((String) null, "AgooDeviceCmd", bVar.mo18616a(b, deviceToken), (String) null));
                if (TextUtils.isEmpty(sendRequest)) {
                    if (iCallback != null) {
                        iCallback.onFailure(C2122a.ACCS_CHECK_ERROR.getCode(), C2122a.ACCS_CHECK_ERROR.getMsg());
                    }
                } else if (iCallback != null) {
                    mRequestListener.f3604a.put(sendRequest, iCallback);
                }
            } else if (iCallback != null) {
                iCallback.onFailure(C2122a.AGOO_NOT_BIND.getCode(), C2122a.AGOO_NOT_BIND.getMsg());
            }
        } catch (Throwable th) {
            ALog.m3726e(TAG, str, th, new Object[0]);
        }
    }

    /* access modifiers changed from: private */
    public static void removeAliasInList(Context context, Map<String, String> map, final ICallback iCallback) {
        if (map == null || map.size() == 0) {
            iCallback.onSuccess();
            return;
        }
        final ArrayList arrayList = new ArrayList(map.keySet());
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        for (final String next : map.keySet()) {
            doAliasOperation(C2124a.JSON_CMD_REMOVEALIAS, context, new ICallback() {
                public void onSuccess() {
                    arrayList.remove(next);
                    if (!atomicBoolean.get() && arrayList.size() == 0) {
                        iCallback.onSuccess();
                    }
                }

                public void onFailure(String str, String str2) {
                    if (atomicBoolean.compareAndSet(false, true)) {
                        iCallback.onFailure(str, str2);
                    }
                }
            }, new C2117d(next, map.get(next), (C2130c) null));
        }
    }

    private static ICallback checkNull(ICallback iCallback) {
        return iCallback == null ? new ICallback() {
            public void onFailure(String str, String str2) {
            }

            public void onSuccess() {
            }
        } : iCallback;
    }

    public static synchronized void setAlias(final Context context, final String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "setAlias " + str, new Object[0]);
            final ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation("listAlias", context, new IListAliasCallbackInner() {
                        public void onSuccess(Map<String, String> map) {
                            TaobaoRegister.removeAliasInList(context, map, new ICallback() {
                                public void onSuccess() {
                                    TaobaoRegister.doAliasOperation(C2124a.JSON_CMD_ADDALIAS, context, checkNull, new C2114a(str, (C2130c) null));
                                }

                                public void onFailure(String str, String str2) {
                                    checkNull.onFailure(str, str2);
                                }
                            });
                        }

                        public void onFailure(String str, String str2) {
                            ICallback iCallback = checkNull;
                            String str3 = str;
                            iCallback.extra = str3;
                            TaobaoRegister.doAliasOperation(C2124a.JSON_CMD_ADDALIAS, context, iCallback, new C2114a(str3, (C2130c) null));
                        }
                    }, new C2116c((C2130c) null));
                    return;
                }
            }
            ErrorBuilder copy = C2122a.INVALID_ARG.copy();
            ErrorCode build = copy.detail("setAlias " + context + " " + str).build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    public static synchronized void removeAlias(final Context context, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, C2124a.JSON_CMD_REMOVEALIAS, new Object[0]);
            final ICallback checkNull = checkNull(iCallback);
            if (context == null) {
                ErrorCode build = C2122a.INVALID_ARG.copy().detail("removeAlias before 2.4.x context is null").build();
                checkNull.onFailure(build.getCode(), build.getMsg());
                return;
            }
            doAliasOperation("removeAllAlias", context, new ICallback() {
                public void onSuccess() {
                    ICallback.this.onSuccess();
                }

                public void onFailure(String str, String str2) {
                    TaobaoRegister.doAliasOperation("listAlias", context, new IListAliasCallbackInner() {
                        public void onSuccess(Map<String, String> map) {
                            TaobaoRegister.removeAliasInList(context, map, ICallback.this);
                        }

                        public void onFailure(String str, String str2) {
                            ArrayList<String> a = C2129b.m3840a(context);
                            if (a == null || a.size() <= 0) {
                                ICallback.this.onFailure(C2122a.REMOVE_ALIAS_FAIL_NO_ALIAS.getCode(), C2122a.REMOVE_ALIAS_FAIL_NO_ALIAS.getMsg());
                                return;
                            }
                            String str3 = a.get(0);
                            String a2 = C2129b.m3838a(context, str3);
                            if (a2 == null || a2.isEmpty() || str3 == null) {
                                ICallback.this.onFailure(C2122a.REMOVE_ALIAS_FAIL_NO_TOKEN.getCode(), C2122a.REMOVE_ALIAS_FAIL_NO_TOKEN.getMsg());
                                return;
                            }
                            ICallback.this.extra = str3;
                            TaobaoRegister.doAliasOperation(C2124a.JSON_CMD_REMOVEALIAS, context, ICallback.this, new C2117d(str3, a2, (C2130c) null));
                        }
                    }, new C2116c((C2130c) null));
                }
            }, new C2118e((C2130c) null));
        }
    }

    public static synchronized void listAlias(Context context, IListAliasCallback iListAliasCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "listAlias", new Object[0]);
            ICallback checkNull = checkNull(iListAliasCallback);
            if (context == null) {
                ErrorCode build = C2122a.INVALID_ARG.copy().detail("listAlias context is null").build();
                checkNull.onFailure(build.getCode(), build.getMsg());
                return;
            }
            doAliasOperation("listAlias", context, checkNull, new C2116c((C2130c) null));
        }
    }

    public static synchronized void addAlias(Context context, String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "addAlias", "alias", str);
            ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation("addAlias", context, checkNull, new C2114a(str, (C2130c) null));
                    return;
                }
            }
            ErrorBuilder copy = C2122a.INVALID_ARG.copy();
            ErrorCode build = copy.detail("addAlias " + context + " " + str).build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    public static synchronized void removeAlias(final Context context, final String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "removeAlias " + str, new Object[0]);
            final ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation(C2124a.JSON_CMD_REMOVEALIAS, context, new ICallback() {
                        public void onSuccess() {
                            ICallback.this.onSuccess();
                        }

                        public void onFailure(final String str, final String str2) {
                            TaobaoRegister.doAliasOperation("listAlias", context, new IListAliasCallbackInner() {
                                public void onSuccess(Map<String, String> map) {
                                    String str = map.get(str);
                                    if (str != null) {
                                        TaobaoRegister.doAliasOperation(C2124a.JSON_CMD_REMOVEALIAS, context, ICallback.this, new C2117d(str, str, (C2130c) null));
                                    } else {
                                        ICallback.this.onFailure(str, str2);
                                    }
                                }

                                public void onFailure(String str, String str2) {
                                    ICallback.this.onFailure(str, str2);
                                }
                            }, new C2116c((C2130c) null));
                        }
                    }, new C2117d(str, (String) null, (C2130c) null));
                    return;
                }
            }
            ErrorBuilder copy = C2122a.INVALID_ARG.copy();
            ErrorCode build = copy.detail("removeAlias " + context + " " + str).build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    public static synchronized void removeAllAliasOnCurrentDevice(Context context, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "removeAllAliasOnCurrentDevice ", new Object[0]);
            ICallback checkNull = checkNull(iCallback);
            if (context == null) {
                ErrorBuilder copy = C2122a.INVALID_ARG.copy();
                ErrorCode build = copy.detail("removeAllAliasOnCurrentDevice " + context).build();
                checkNull.onFailure(build.getCode(), build.getMsg());
                return;
            }
            doAliasOperation("removeAllAliasOnCurrentDevice", context, checkNull, new C2118e((C2130c) null));
        }
    }

    public static synchronized void removeAllDeviceOnThisAliasAndBindCurrentDevice(Context context, String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "removeAllDeviceOnThisAliasAndBindCurrentDevice alias : " + str, new Object[0]);
            ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation("removeAllDeviceOnThisAliasAndBindCurrentDevice", context, checkNull, new C2119f(str));
                    return;
                }
            }
            ErrorCode build = C2122a.INVALID_ARG.copy().detail("removeAllDeviceOnThisAliasAndBindCurrentDevice context is null").build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    public static synchronized void removeAllAliasOnCurrentDeviceAndAddThisAlias(Context context, String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "removeAllAliasOnCurrentDeviceAndAddThisAlias alias : " + str, new Object[0]);
            ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation("removeAllAliasOnCurrentDeviceAndAddThisAlias", context, checkNull, new C2121h(str));
                    return;
                }
            }
            ErrorCode build = C2122a.INVALID_ARG.copy().detail("removeAllAliasOnCurrentDeviceAndAddThisAlias context is null").build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    public static synchronized void resetDeviceAndAliasToOne2One(Context context, String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.m3728i(TAG, "resetDeviceAndAliasToOne2One alias : " + str, new Object[0]);
            ICallback checkNull = checkNull(iCallback);
            if (context != null) {
                if (str != null) {
                    doAliasOperation("resetDeviceAndAliasToOne2One", context, checkNull, new C2120g(str));
                    return;
                }
            }
            ErrorBuilder copy = C2122a.INVALID_ARG.copy();
            ErrorCode build = copy.detail("resetDeviceAndAliasToOne2One " + context + " " + str).build();
            checkNull.onFailure(build.getCode(), build.getMsg());
        }
    }

    @Deprecated
    public static void bindAgoo(Context context, String str, String str2, CallBack callBack) {
        bindAgoo(context, (ICallback) null);
    }

    @Deprecated
    public static void unBindAgoo(Context context, String str, String str2, CallBack callBack) {
        unbindAgoo(context, (ICallback) null);
    }

    private static synchronized void sendSwitch(Context context, ICallback iCallback, boolean z) {
        synchronized (TaobaoRegister.class) {
            try {
                String deviceToken = Config.getDeviceToken(context);
                String b = Config.m3894b(context);
                String deviceId = UtilityImpl.getDeviceId(context);
                if (!TextUtils.isEmpty(b) && context != null) {
                    if (!TextUtils.isEmpty(deviceToken) || !TextUtils.isEmpty(deviceId)) {
                        IACCSManager accsInstance = ACCSManager.getAccsInstance(context, b, Config.m3896c(context));
                        if (mRequestListener == null) {
                            mRequestListener = new C2128b(context.getApplicationContext());
                        }
                        accsInstance.registerDataListener(context, "AgooDeviceCmd", mRequestListener);
                        String sendRequest = accsInstance.sendRequest(context, new ACCSManager.AccsRequest((String) null, "AgooDeviceCmd", C2127d.m3835a(b, deviceToken, deviceId, z), (String) null));
                        if (TextUtils.isEmpty(sendRequest)) {
                            if (iCallback != null) {
                                iCallback.onFailure(C2122a.ACCS_CHECK_ERROR.getCode(), C2122a.ACCS_CHECK_ERROR.getMsg());
                            }
                        } else if (iCallback != null) {
                            mRequestListener.f3604a.put(sendRequest, iCallback);
                        }
                    }
                }
                if (iCallback != null) {
                    ErrorBuilder copy = C2122a.INVALID_ARG.copy();
                    ErrorCode build = copy.detail("sendSwitch " + context + " " + b + " " + deviceToken + " " + deviceId).build();
                    iCallback.onFailure(build.getCode(), build.getMsg());
                }
                ALog.m3727e(TAG, "sendSwitch param null", "appkey", b, "deviceId", deviceToken, "context", context, C2127d.JSON_CMD_ENABLEPUSH, Boolean.valueOf(z));
                return;
            } catch (Throwable th) {
                ALog.m3726e(TAG, "sendSwitch", th, new Object[0]);
            }
        }
        return;
    }

    public static void bindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, true);
        UTMini.getInstance().commitEvent(EVENT_ID, "bindAgoo", UtilityImpl.getDeviceId(context));
    }

    public static void unbindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, false);
        UTMini.getInstance().commitEvent(EVENT_ID, "unregister", UtilityImpl.getDeviceId(context));
    }

    public static void clickMessage(Context context, String str, String str2) {
        AgooFactory.getInstance(context).clickMessage(context, str, str2);
    }

    public static void dismissMessage(Context context, String str, String str2) {
        AgooFactory.getInstance(context).dismissMessage(context, str, str2);
    }

    public static void pingApp(Context context, String str, String str2, String str3, int i) {
        AgooFactory.getInstance(context).getNotifyManager().pingApp(str, str2, str3, i);
    }

    public static void setAgooMsgReceiveService(String str) {
        AdapterGlobalClientInfo.mAgooCustomServiceName = str;
    }

    public static void setEnv(Context context, @AccsClientConfig.ENV int i) {
        ACCSClient.setEnvironment(context, i);
    }

    @Deprecated
    public static void unregister(Context context, CallBack callBack) {
        unbindAgoo(context, (ICallback) null);
    }

    public static boolean isPushApi() {
        return AgooInnerService.class.getName().equals(AdapterGlobalClientInfo.mAgooCustomServiceName);
    }

    public static void setPushMsgReceiveService(Class cls) {
        AdapterGlobalClientInfo.mAgooCustomServiceName = AgooInnerService.class.getName();
        C0910h.m1078a(cls);
    }

    public static void setReportPushArrive(IReportPushArrive iReportPushArrive) {
        C0910h.m1076a(iReportPushArrive);
    }

    public static void setDoNotDisturbMode(boolean z) {
        C0910h.m1079a(z);
    }

    public static void setDoNotDisturb(int i, int i2, int i3, int i4, CommonCallback commonCallback) {
        C0910h.m1073a(i, i2, i3, i4, commonCallback);
    }

    public static void clickMessage(CPushMessage cPushMessage) {
        C0910h.m1077a(cPushMessage);
    }

    public static void dismissMessage(CPushMessage cPushMessage) {
        C0910h.m1080b(cPushMessage);
    }

    public static void clearNotificationCreatedByAliyun(Context context) {
        C0903a.m1057a().mo10172a(context);
    }

    public static void reset() {
        if (C2128b.f3603b != null) {
            C2128b.f3603b.mo18617a();
        }
        try {
            ACCSClient.getAccsClient(Config.f4060a).reset();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Config.m3890a(GlobalClientInfo.getContext());
    }
}
