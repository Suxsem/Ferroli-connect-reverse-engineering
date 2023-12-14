package com.taobao.accs;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.SessionCenter;
import anet.channel.entity.ENV;
import anet.channel.util.ALog;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.logger.ILog;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ChannelService;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.internal.AccsJobService;
import com.taobao.accs.utl.AccsLogger;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.C2086c;
import com.taobao.accs.utl.C2092i;
import com.taobao.accs.utl.C2097k;
import com.taobao.accs.utl.C2099l;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.Utils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
public class ACCSClient {
    private static ILog defaultLog = AccsLogger.getLogger("ACCSClient");
    public static Map<String, ACCSClient> mACCSClients = new ConcurrentHashMap(2);
    private static Context mContext;
    private HashSet<ConnectionListener> listeners = new HashSet<>();
    private ILog log;
    protected IACCSManager mAccsManager;
    private AccsClientConfig mConfig;

    public ACCSClient(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        this.log = AccsLogger.getLogger("ACCSClient" + accsClientConfig.getTag());
        this.mAccsManager = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
    }

    public static void changeNetworkSdkLoggerToAccs() {
        defaultLog.mo9706d("changeNetworkSdkLoggerToAccs");
        ALog.setLog(new C2097k(new C2099l(), C2092i.m3788a()));
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:5|6|7|8|9|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0039 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:9:0x0039=Splitter:B:9:0x0039, B:13:0x003f=Splitter:B:13:0x003f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.String init(android.content.Context r8, com.taobao.accs.AccsClientConfig r9) throws com.taobao.accs.AccsException {
        /*
            java.lang.Class<com.taobao.accs.ACCSClient> r0 = com.taobao.accs.ACCSClient.class
            monitor-enter(r0)
            r1 = 3
            r2 = 2
            r3 = 1
            r4 = 0
            if (r8 == 0) goto L_0x003f
            if (r9 == 0) goto L_0x003f
            com.taobao.accs.client.GlobalClientInfo.getInstance(r8)     // Catch:{ all -> 0x0060 }
            android.content.Context r5 = r8.getApplicationContext()     // Catch:{ all -> 0x0060 }
            mContext = r5     // Catch:{ all -> 0x0060 }
            setCurrentProcessName(r8)     // Catch:{ all -> 0x0060 }
            com.alibaba.sdk.android.logger.ILog r8 = defaultLog     // Catch:{ all -> 0x0060 }
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0060 }
            java.lang.String r5 = "init"
            r1[r4] = r5     // Catch:{ all -> 0x0060 }
            java.lang.String r5 = "config"
            r1[r3] = r5     // Catch:{ all -> 0x0060 }
            r1[r2] = r9     // Catch:{ all -> 0x0060 }
            r8.mo9707d((java.lang.Object[]) r1)     // Catch:{ all -> 0x0060 }
            com.taobao.accs.AccsState r8 = com.taobao.accs.AccsState.getInstance()     // Catch:{ all -> 0x0060 }
            java.lang.String r1 = "sv"
            java.lang.String r2 = "4.6.2-emas"
            r8.mo18226a((java.lang.String) r1, (java.lang.Object) r2)     // Catch:{ all -> 0x0060 }
            changeNetworkSdkLoggerToAccs()     // Catch:{ all -> 0x0060 }
            anet.channel.AwcnConfig.setAccsSessionCreateForbiddenInBg(r4)     // Catch:{ Throwable -> 0x0039 }
        L_0x0039:
            java.lang.String r8 = r9.getTag()     // Catch:{ all -> 0x0060 }
            monitor-exit(r0)
            return r8
        L_0x003f:
            com.alibaba.sdk.android.logger.ILog r5 = defaultLog     // Catch:{ all -> 0x0060 }
            r6 = 5
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0060 }
            java.lang.String r7 = "init AccsClient params error"
            r6[r4] = r7     // Catch:{ all -> 0x0060 }
            java.lang.String r4 = "context"
            r6[r3] = r4     // Catch:{ all -> 0x0060 }
            r6[r2] = r8     // Catch:{ all -> 0x0060 }
            java.lang.String r8 = "config"
            r6[r1] = r8     // Catch:{ all -> 0x0060 }
            r8 = 4
            r6[r8] = r9     // Catch:{ all -> 0x0060 }
            r5.mo9710e((java.lang.Object[]) r6)     // Catch:{ all -> 0x0060 }
            com.taobao.accs.AccsException r8 = new com.taobao.accs.AccsException     // Catch:{ all -> 0x0060 }
            java.lang.String r9 = "init AccsClient params error"
            r8.<init>((java.lang.String) r9)     // Catch:{ all -> 0x0060 }
            throw r8     // Catch:{ all -> 0x0060 }
        L_0x0060:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.ACCSClient.init(android.content.Context, com.taobao.accs.AccsClientConfig):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0064, code lost:
        return r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.taobao.accs.ACCSClient getAccsClient(java.lang.String r9) throws com.taobao.accs.AccsException {
        /*
            java.lang.Class<com.taobao.accs.ACCSClient> r0 = com.taobao.accs.ACCSClient.class
            monitor-enter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x007e }
            if (r1 == 0) goto L_0x0012
            java.lang.String r9 = "default"
            com.alibaba.sdk.android.logger.ILog r1 = defaultLog     // Catch:{ all -> 0x007e }
            java.lang.String r2 = "getAccsClient with null tag, use default"
            r1.mo9713w((java.lang.String) r2)     // Catch:{ all -> 0x007e }
        L_0x0012:
            com.taobao.accs.AccsClientConfig r1 = com.taobao.accs.AccsClientConfig.getConfigByTag(r9)     // Catch:{ all -> 0x007e }
            r2 = 2
            r3 = 1
            r4 = 0
            r5 = 3
            if (r1 == 0) goto L_0x0065
            java.util.Map<java.lang.String, com.taobao.accs.ACCSClient> r6 = mACCSClients     // Catch:{ all -> 0x007e }
            java.lang.Object r6 = r6.get(r9)     // Catch:{ all -> 0x007e }
            com.taobao.accs.ACCSClient r6 = (com.taobao.accs.ACCSClient) r6     // Catch:{ all -> 0x007e }
            if (r6 != 0) goto L_0x003c
            com.alibaba.sdk.android.logger.ILog r2 = defaultLog     // Catch:{ all -> 0x007e }
            java.lang.String r3 = "getAccsClient create client"
            r2.mo9706d((java.lang.String) r3)     // Catch:{ all -> 0x007e }
            com.taobao.accs.ACCSClient r2 = new com.taobao.accs.ACCSClient     // Catch:{ all -> 0x007e }
            r2.<init>(r1)     // Catch:{ all -> 0x007e }
            java.util.Map<java.lang.String, com.taobao.accs.ACCSClient> r3 = mACCSClients     // Catch:{ all -> 0x007e }
            r3.put(r9, r2)     // Catch:{ all -> 0x007e }
            r2.updateConfig(r1)     // Catch:{ all -> 0x007e }
            monitor-exit(r0)
            return r2
        L_0x003c:
            com.taobao.accs.AccsClientConfig r9 = r6.mConfig     // Catch:{ all -> 0x007e }
            boolean r9 = r1.equals(r9)     // Catch:{ all -> 0x007e }
            if (r9 == 0) goto L_0x0045
            goto L_0x0063
        L_0x0045:
            com.alibaba.sdk.android.logger.ILog r9 = defaultLog     // Catch:{ all -> 0x007e }
            r7 = 5
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x007e }
            java.lang.String r8 = "getAccsClient update config"
            r7[r4] = r8     // Catch:{ all -> 0x007e }
            java.lang.String r4 = "old"
            r7[r3] = r4     // Catch:{ all -> 0x007e }
            com.taobao.accs.AccsClientConfig r3 = r6.mConfig     // Catch:{ all -> 0x007e }
            r7[r2] = r3     // Catch:{ all -> 0x007e }
            java.lang.String r2 = "new"
            r7[r5] = r2     // Catch:{ all -> 0x007e }
            r2 = 4
            r7[r2] = r1     // Catch:{ all -> 0x007e }
            r9.mo9715w((java.lang.Object[]) r7)     // Catch:{ all -> 0x007e }
            r6.updateConfig(r1)     // Catch:{ all -> 0x007e }
        L_0x0063:
            monitor-exit(r0)
            return r6
        L_0x0065:
            com.alibaba.sdk.android.logger.ILog r1 = defaultLog     // Catch:{ all -> 0x007e }
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x007e }
            java.lang.String r6 = "getAccsClient with null config, please init config first"
            r5[r4] = r6     // Catch:{ all -> 0x007e }
            java.lang.String r4 = "configTag"
            r5[r3] = r4     // Catch:{ all -> 0x007e }
            r5[r2] = r9     // Catch:{ all -> 0x007e }
            r1.mo9710e((java.lang.Object[]) r5)     // Catch:{ all -> 0x007e }
            com.taobao.accs.AccsException r9 = new com.taobao.accs.AccsException     // Catch:{ all -> 0x007e }
            java.lang.String r1 = "configTag not exist"
            r9.<init>((java.lang.String) r1)     // Catch:{ all -> 0x007e }
            throw r9     // Catch:{ all -> 0x007e }
        L_0x007e:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.ACCSClient.getAccsClient(java.lang.String):com.taobao.accs.ACCSClient");
    }

    public static void enableChannelProcess(Context context, boolean z) {
        UtilityImpl.m3745a(context, z);
        if (z) {
            try {
                UtilityImpl.m3742a(context, ChannelService.class.getName());
            } catch (Throwable unused) {
            }
            try {
                UtilityImpl.m3742a(context, ChannelService.KernelService.class.getName());
            } catch (Throwable unused2) {
            }
        } else {
            try {
                UtilityImpl.m3751b(context, ChannelService.class.getName());
            } catch (Throwable unused3) {
            }
            UtilityImpl.m3751b(context, ChannelService.KernelService.class.getName());
        }
    }

    public static void enableChannelProcessHeartbeat(Context context, boolean z) {
        if (z) {
            try {
                UtilityImpl.m3742a(context, EventReceiver.class.getName());
            } catch (Throwable unused) {
            }
            try {
                UtilityImpl.m3742a(context, ServiceReceiver.class.getName());
            } catch (Throwable unused2) {
            }
            try {
                UtilityImpl.m3742a(context, AccsJobService.class.getName());
            } catch (Throwable unused3) {
            }
        } else {
            try {
                UtilityImpl.m3751b(context, EventReceiver.class.getName());
            } catch (Throwable unused4) {
            }
            try {
                UtilityImpl.m3751b(context, ServiceReceiver.class.getName());
            } catch (Throwable unused5) {
            }
            UtilityImpl.m3751b(context, AccsJobService.class.getName());
        }
    }

    public static void setCurrentProcessName(Context context) {
        try {
            GlobalAppRuntimeInfo.setCurrentProcess(AdapterUtilityImpl.getProcessName(context.getApplicationContext()));
        } catch (Throwable th) {
            defaultLog.mo9709e("setCurrentProcess", th);
        }
    }

    private void updateConfig(AccsClientConfig accsClientConfig) {
        this.mConfig = accsClientConfig;
        this.mAccsManager = ACCSManager.getAccsInstance(mContext, accsClientConfig.getAppKey(), accsClientConfig.getTag());
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            iACCSManager.updateConfig(accsClientConfig);
        }
    }

    public static synchronized void setEnvironment(Context context, @AccsClientConfig.ENV int i) {
        synchronized (ACCSClient.class) {
            if (i < 0 || i > 2) {
                try {
                    defaultLog.mo9715w("env invalid, reset to release", "env", Integer.valueOf(i));
                    i = 0;
                } catch (AccsException e) {
                    defaultLog.mo9709e("setEnvironment update client", e);
                } catch (Throwable th) {
                    try {
                        defaultLog.mo9709e("setEnvironment", th);
                    } catch (Throwable th2) {
                        Utils.setMode(context, i);
                        throw th2;
                    }
                }
            }
            int i2 = AccsClientConfig.mEnv;
            AccsClientConfig.mEnv = i;
            if (i2 != i && Utils.isMainProcess(context)) {
                defaultLog.mo9712i("setEnvironment", RequestConstant.ENV_PRE, Integer.valueOf(i2), "to", Integer.valueOf(i));
                Utils.clearAllSharePreferences(context);
                Utils.clearAgooBindCache(context);
                Utils.killService(context);
                if (i == 2) {
                    SessionCenter.switchEnvironment(ENV.TEST);
                } else if (i == 1) {
                    SessionCenter.switchEnvironment(ENV.PREPARE);
                }
                for (Map.Entry key : mACCSClients.entrySet()) {
                    getAccsClient((String) key.getKey());
                }
            }
            Utils.setMode(context, i);
        }
    }

    public void bindApp(String str, IAppReceiver iAppReceiver) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("bindApp mAccsManager null");
            C2086c.m3775a(AccsErrorCode.ERROR_SHOULD_NEVER_HAPPEN.copy().detail("bindApp accs is null").build(), iAppReceiver, (String) null);
            return;
        }
        iACCSManager.bindApp(mContext, this.mConfig.getAppKey(), this.mConfig.getAppSecret(), str, iAppReceiver);
    }

    public void cleanLocalBindInfo() {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("cleanLocalBindInfo mAccsManager null");
        } else {
            iACCSManager.cleanLocalBindInfo();
        }
    }

    public void bindUser(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("bindUser mAccsManager null");
        } else {
            iACCSManager.bindUser(mContext, str);
        }
    }

    public void bindUser(String str, boolean z) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("bindUser mAccsManager null");
        } else {
            iACCSManager.bindUser(mContext, str, z);
        }
    }

    public void unbindUser() {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("unbindUser mAccsManager null");
        } else {
            iACCSManager.unbindUser(mContext);
        }
    }

    public void bindService(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("bindService mAccsManager null");
        } else {
            iACCSManager.bindService(mContext, str);
        }
    }

    public void unbindService(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("unbindService mAccsManager null");
        } else {
            iACCSManager.unbindService(mContext, str);
        }
    }

    public String sendData(ACCSManager.AccsRequest accsRequest) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.sendData(mContext, accsRequest);
        }
        this.log.mo9708e("sendData mAccsManager null");
        return null;
    }

    public String sendRequest(ACCSManager.AccsRequest accsRequest) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.sendRequest(mContext, accsRequest);
        }
        this.log.mo9708e("sendRequest mAccsManager null");
        return null;
    }

    public String sendPushResponse(ACCSManager.AccsRequest accsRequest, TaoBaseService.ExtraInfo extraInfo) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.sendPushResponse(mContext, accsRequest, extraInfo);
        }
        this.log.mo9708e("sendPushResponse mAccsManager null");
        return null;
    }

    public boolean isNetworkReachable() {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.isNetworkReachable(mContext);
        }
        this.log.mo9708e("isNetworkReachable mAccsManager null");
        return false;
    }

    public void startInAppConnection(String str, IAppReceiver iAppReceiver) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("startInAppConnection mAccsManager null");
        } else {
            iACCSManager.startInAppConnection(mContext, this.mConfig.getAppKey(), this.mConfig.getAppSecret(), str, iAppReceiver);
        }
    }

    public void setLoginInfo(ILoginInfo iLoginInfo) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("setLoginInfo mAccsManager null");
        } else {
            iACCSManager.setLoginInfo(mContext, iLoginInfo);
        }
    }

    public void clearLoginInfo() {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("clearLoginInfo mAccsManager null");
        } else {
            iACCSManager.clearLoginInfo(mContext);
        }
    }

    public boolean cancel(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.cancel(mContext, str);
        }
        this.log.mo9708e("cancel mAccsManager null");
        return false;
    }

    public boolean isChannelError(int i) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.isChannelError(i);
        }
        this.log.mo9708e("isChannelError mAccsManager null");
        return true;
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.getChannelState();
        }
        this.log.mo9708e("getChannelState mAccsManager null");
        return null;
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager != null) {
            return iACCSManager.forceReConnectChannel();
        }
        this.log.mo9708e("forceReConnectChannel mAccsManager null");
        return null;
    }

    public void registerSerivce(String str, String str2) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("registerSerivce mAccsManager null");
        } else {
            iACCSManager.registerSerivce(mContext, str, str2);
        }
    }

    public void unRegisterSerivce(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("unRegisterSerivce mAccsManager null");
        } else {
            iACCSManager.unRegisterSerivce(mContext, str);
        }
    }

    public void registerDataListener(String str, AccsAbstractDataListener accsAbstractDataListener) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("registerDataListener mAccsManager null");
        } else {
            iACCSManager.registerDataListener(mContext, str, accsAbstractDataListener);
        }
    }

    public void unRegisterDataListener(String str) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("unRegisterDataListener mAccsManager null");
        } else {
            iACCSManager.unRegisterDataListener(mContext, str);
        }
    }

    public void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map) {
        IACCSManager iACCSManager = this.mAccsManager;
        if (iACCSManager == null) {
            this.log.mo9708e("sendBusinessAck mAccsManager null");
        } else {
            iACCSManager.sendBusinessAck(str, str2, str3, s, str4, map);
        }
    }

    public void addConnectionListener(ConnectionListener connectionListener) {
        if (connectionListener != null) {
            this.listeners.add(connectionListener);
        }
    }

    public void removeConnectionListener(ConnectionListener connectionListener) {
        if (connectionListener != null) {
            this.listeners.remove(connectionListener);
        }
    }

    public List<ConnectionListener> getConnectionListeners() {
        return new ArrayList(this.listeners);
    }

    public boolean isConnected() {
        return this.mAccsManager.isConnected();
    }

    public int getLastConnectErrorCode() {
        return this.mAccsManager.getLastConnectErrorCode();
    }

    public void disconnect() {
        this.mAccsManager.disconnect();
    }

    public void reconnect() {
        this.mAccsManager.reconnect();
    }

    public void reset() {
        this.mAccsManager.reset();
    }
}
