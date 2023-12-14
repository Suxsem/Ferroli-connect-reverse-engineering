package org.android.agoo.control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Messenger;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.OrangeAdapter;
import com.taobao.accs.utl.Utils;
import org.android.agoo.common.Config;
import org.android.agoo.message.MessageService;

/* compiled from: Taobao */
public abstract class BaseIntentService extends Service {
    private static final String TAG = "BaseIntentService";
    private static final String msgStatus = "4";
    /* access modifiers changed from: private */
    public AgooFactory agooFactory;
    private Context mContext = null;
    /* access modifiers changed from: private */
    public MessageService messageService;
    private Messenger messenger = new Messenger(new C2361h(this));
    /* access modifiers changed from: private */
    public NotifManager notifyManager;

    /* access modifiers changed from: protected */
    @Deprecated
    public abstract void onError(Context context, String str);

    /* access modifiers changed from: protected */
    public abstract void onMessage(Context context, Intent intent);

    /* access modifiers changed from: protected */
    @Deprecated
    public abstract void onRegistered(Context context, String str);

    public IBinder onBind(Intent intent) {
        if (OrangeAdapter.isBindService(this) && Utils.isTarget26(this)) {
            getApplicationContext().bindService(new Intent(this, getClass()), new C2363j(this), 1);
        }
        return this.messenger.getBinder();
    }

    public void onCreate() {
        super.onCreate();
        ThreadPoolExecutorFactory.execute(new C2364k(this));
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        ThreadPoolExecutorFactory.execute(new C2365l(this, intent));
        return 2;
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        this.mContext = getApplicationContext();
        if (intent != null) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                ALog.m3728i(TAG, "onHandleIntent,action=" + action, new Object[0]);
                try {
                    if (action.equals("org.agoo.android.intent.action.RECEIVE")) {
                        handleRemoteMessage(this.mContext, intent);
                    } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        handleRemovePackage(this.mContext, intent);
                    } else if (TextUtils.equals(action, "org.agoo.android.intent.action.REPORT") || TextUtils.equals(action, PushConsts.ACTION_BROADCAST_NETWORK_CHANGE) || TextUtils.equals(action, PushConsts.ACTION_BROADCAST_TO_BOOT) || TextUtils.equals(action, "android.intent.action.PACKAGE_ADDED") || TextUtils.equals(action, "android.intent.action.PACKAGE_REPLACED") || TextUtils.equals(action, PushConsts.ACTION_BROADCAST_USER_PRESENT) || TextUtils.equals(action, "android.intent.action.ACTION_POWER_CONNECTED") || TextUtils.equals(action, "android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        ALog.m3728i(TAG, "is report cache msg,Config.isReportCacheMsg(mContext)=" + Config.m3897d(this.mContext), new Object[0]);
                        if (Config.m3897d(this.mContext) && AdapterUtilityImpl.isNetworkConnected(this.mContext)) {
                            Config.m3898e(this.mContext);
                            this.agooFactory.reportCacheMsg();
                            this.messageService.mo25527a();
                        }
                        long currentTimeMillis = System.currentTimeMillis();
                        if (ALog.isPrintLog(ALog.Level.I)) {
                            ALog.m3728i(TAG, "is clear all msg=" + Config.m3895b(this.mContext, currentTimeMillis), new Object[0]);
                        }
                        if (Config.m3895b(this.mContext, currentTimeMillis)) {
                            Config.m3892a(this.mContext, currentTimeMillis);
                            this.messageService.mo25527a();
                        }
                    }
                } catch (Throwable th) {
                    try {
                        if (ALog.isPrintLog(ALog.Level.E)) {
                            ALog.m3726e(TAG, "onHandleIntent deal error", th, new Object[0]);
                        }
                    } catch (Throwable th2) {
                        AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
                        throw th2;
                    }
                }
                AdapterGlobalClientInfo.mStartServiceTimes.incrementAndGet();
            }
        }
    }

    private final void handleRemovePackage(Context context, Intent intent) {
        if (intent != null && context != null) {
            String str = null;
            Uri data = intent.getData();
            if (data != null) {
                str = data.getSchemeSpecificPart();
            }
            if (!TextUtils.isEmpty(str)) {
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d(TAG, "handleRemovePackage---->[replacing:" + booleanExtra + "],uninstallPack=" + str, new Object[0]);
                }
                if (!booleanExtra) {
                    this.notifyManager.doUninstall(str, booleanExtra);
                }
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:122:0x02e6 */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x0290 A[Catch:{ Throwable -> 0x0345 }] */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02ca A[Catch:{ Throwable -> 0x02e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x02d9 A[Catch:{ Throwable -> 0x02e6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0337 A[Catch:{ Throwable -> 0x0345 }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c4 A[Catch:{ Throwable -> 0x0347 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x012a A[Catch:{ Throwable -> 0x0347 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0168  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x017a A[Catch:{ Throwable -> 0x0347 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01f9 A[Catch:{ Throwable -> 0x0345 }] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x022c A[Catch:{ Throwable -> 0x0345 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void handleRemoteMessage(android.content.Context r35, android.content.Intent r36) {
        /*
            r34 = this;
            r1 = r34
            r2 = r36
            java.lang.String r3 = "1"
            java.lang.String r4 = "messageId="
            java.lang.String r5 = "fromAppkey"
            java.lang.String r6 = "source"
            java.lang.String r7 = "body"
            java.lang.String r8 = "agoo_arrive"
            java.lang.String r9 = "accs"
            java.lang.String r10 = "BaseIntentService"
            java.lang.String r0 = "id"
            java.lang.String r13 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r14 = r2.getStringExtra(r7)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "type"
            java.lang.String r15 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "message_source"
            java.lang.String r11 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "report"
            java.lang.String r12 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "encrypted"
            r16 = r15
            java.lang.String r15 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "extData"
            r17 = r3
            java.lang.String r3 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x0352 }
            java.lang.String r0 = "oriData"
            r18 = r8
            java.lang.String r8 = r2.getStringExtra(r0)     // Catch:{ Throwable -> 0x034d }
            r19 = 0
            r20 = r9
            java.lang.String r0 = "trace"
            r21 = r10
            r9 = -1
            long r9 = r2.getLongExtra(r0, r9)     // Catch:{ Throwable -> 0x008f }
            java.lang.Long r0 = java.lang.Long.valueOf(r9)     // Catch:{ Throwable -> 0x008f }
            long r9 = r0.longValue()     // Catch:{ Throwable -> 0x008f }
            r23 = r4
            r4 = r35
            r1.getTrace(r4, r9)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r0 = "msg_agoo_bundle"
            android.os.Bundle r0 = r2.getBundleExtra(r0)     // Catch:{ Throwable -> 0x008d }
            java.lang.String r9 = r2.getStringExtra(r6)     // Catch:{ Throwable -> 0x008d }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x008b }
            if (r10 == 0) goto L_0x0077
            java.lang.String r9 = "oldsdk"
        L_0x0077:
            java.lang.String r10 = r2.getStringExtra(r5)     // Catch:{ Throwable -> 0x008b }
            if (r0 == 0) goto L_0x0086
            java.lang.String r4 = "accs_extra"
            java.io.Serializable r0 = r0.getSerializable(r4)     // Catch:{ Throwable -> 0x0086 }
            com.taobao.accs.base.TaoBaseService$ExtraInfo r0 = (com.taobao.accs.base.TaoBaseService.ExtraInfo) r0     // Catch:{ Throwable -> 0x0086 }
            goto L_0x0088
        L_0x0086:
            r0 = r19
        L_0x0088:
            r4 = r21
            goto L_0x00b6
        L_0x008b:
            r0 = move-exception
            goto L_0x009a
        L_0x008d:
            r0 = move-exception
            goto L_0x0098
        L_0x008f:
            r0 = move-exception
            r23 = r4
            goto L_0x0098
        L_0x0093:
            r0 = move-exception
            r23 = r4
            r21 = r10
        L_0x0098:
            r9 = r19
        L_0x009a:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0347 }
            r4.<init>()     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r10 = "_trace,t="
            r4.append(r10)     // Catch:{ Throwable -> 0x0347 }
            r4.append(r0)     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r0 = r4.toString()     // Catch:{ Throwable -> 0x0347 }
            r4 = 0
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0347 }
            r4 = r21
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r10)     // Catch:{ Throwable -> 0x0347 }
            r0 = r19
            r10 = r0
        L_0x00b6:
            com.taobao.accs.utl.ALog$Level r21 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0347 }
            boolean r21 = com.taobao.accs.utl.ALog.isPrintLog(r21)     // Catch:{ Throwable -> 0x0347 }
            r24 = 4
            r25 = r8
            r26 = 1
            if (r21 == 0) goto L_0x0101
            java.lang.String r8 = "handleRemoteMessage"
            r2 = 12
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r27 = "message"
            r22 = 0
            r2[r22] = r27     // Catch:{ Throwable -> 0x0347 }
            r2[r26] = r14     // Catch:{ Throwable -> 0x0347 }
            r21 = 2
            r2[r21] = r6     // Catch:{ Throwable -> 0x0347 }
            r6 = 3
            r2[r6] = r11     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r6 = "msgId"
            r2[r24] = r6     // Catch:{ Throwable -> 0x0347 }
            r6 = 5
            r2[r6] = r13     // Catch:{ Throwable -> 0x0347 }
            r6 = 6
            java.lang.String r27 = "utdid"
            r2[r6] = r27     // Catch:{ Throwable -> 0x0347 }
            r6 = 7
            java.lang.String r27 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r35)     // Catch:{ Throwable -> 0x0347 }
            r2[r6] = r27     // Catch:{ Throwable -> 0x0347 }
            r6 = 8
            java.lang.String r27 = "fromPkg"
            r2[r6] = r27     // Catch:{ Throwable -> 0x0347 }
            r6 = 9
            r2[r6] = r9     // Catch:{ Throwable -> 0x0347 }
            r6 = 10
            r2[r6] = r5     // Catch:{ Throwable -> 0x0347 }
            r5 = 11
            r2[r5] = r10     // Catch:{ Throwable -> 0x0347 }
            com.taobao.accs.utl.ALog.m3728i(r4, r8, r2)     // Catch:{ Throwable -> 0x0347 }
        L_0x0101:
            org.android.agoo.common.MsgDO r2 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x0347 }
            r2.<init>()     // Catch:{ Throwable -> 0x0347 }
            r2.msgIds = r13     // Catch:{ Throwable -> 0x0347 }
            r2.extData = r3     // Catch:{ Throwable -> 0x0347 }
            r2.messageSource = r11     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r3 = "4"
            r2.msgStatus = r3     // Catch:{ Throwable -> 0x0347 }
            r2.reportStr = r12     // Catch:{ Throwable -> 0x0347 }
            r2.fromPkg = r9     // Catch:{ Throwable -> 0x0347 }
            r2.fromAppkey = r10     // Catch:{ Throwable -> 0x0347 }
            boolean r3 = com.taobao.accs.client.AdapterGlobalClientInfo.isFirstStartProc()     // Catch:{ Throwable -> 0x0347 }
            r2.isStartProc = r3     // Catch:{ Throwable -> 0x0347 }
            android.content.Context r3 = r1.mContext     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.isNotificationEnabled(r3)     // Catch:{ Throwable -> 0x0347 }
            r2.notifyEnable = r3     // Catch:{ Throwable -> 0x0347 }
            boolean r3 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x0347 }
            if (r3 != 0) goto L_0x0162
            java.lang.String r3 = java.lang.Integer.toString(r24)     // Catch:{ Throwable -> 0x0347 }
            boolean r3 = r3.equals(r15)     // Catch:{ Throwable -> 0x0347 }
            if (r3 == 0) goto L_0x0150
            java.lang.String r3 = "message is encrypted, attemp to decrypt msg"
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0347 }
            com.taobao.accs.utl.ALog.m3728i(r4, r3, r6)     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r14 = org.android.agoo.control.AgooFactory.parseEncryptedMsg(r14)     // Catch:{ Throwable -> 0x0347 }
            boolean r3 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x0347 }
            if (r3 == 0) goto L_0x0162
            java.lang.String r3 = "22"
            r2.errorCode = r3     // Catch:{ Throwable -> 0x0347 }
            org.android.agoo.control.NotifManager r3 = r1.notifyManager     // Catch:{ Throwable -> 0x0347 }
            r3.handlerACKMessage(r2, r0)     // Catch:{ Throwable -> 0x0347 }
            return
        L_0x0150:
            java.lang.String r3 = "msg encrypted flag not exist~~"
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0347 }
            com.taobao.accs.utl.ALog.m3727e(r4, r3, r5)     // Catch:{ Throwable -> 0x0347 }
            java.lang.String r3 = "24"
            r2.errorCode = r3     // Catch:{ Throwable -> 0x0161 }
            org.android.agoo.control.NotifManager r3 = r1.notifyManager     // Catch:{ Throwable -> 0x0161 }
            r3.report(r2, r0)     // Catch:{ Throwable -> 0x0161 }
        L_0x0161:
            return
        L_0x0162:
            boolean r3 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x0347 }
            if (r3 == 0) goto L_0x017a
            java.lang.String r3 = "21"
            r2.errorCode = r3     // Catch:{ Throwable -> 0x0171 }
            org.android.agoo.control.NotifManager r3 = r1.notifyManager     // Catch:{ Throwable -> 0x0171 }
            r3.report(r2, r0)     // Catch:{ Throwable -> 0x0171 }
        L_0x0171:
            java.lang.String r0 = "handleMessage--->[null]"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0347 }
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r2)     // Catch:{ Throwable -> 0x0347 }
            return
        L_0x017a:
            r3 = r36
            r3.putExtra(r7, r14)     // Catch:{ Throwable -> 0x0347 }
            org.android.agoo.control.NotifManager r5 = r1.notifyManager     // Catch:{ Throwable -> 0x01cf }
            r5.report(r2, r0)     // Catch:{ Throwable -> 0x01cf }
            org.android.agoo.message.MessageService r0 = r1.messageService     // Catch:{ Throwable -> 0x01cf }
            java.lang.String r5 = "0"
            r6 = r25
            r0.mo25530a(r13, r6, r5)     // Catch:{ Throwable -> 0x01cf }
            com.taobao.accs.utl.UTMini r27 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x01cf }
            r28 = 19999(0x4e1f, float:2.8025E-41)
            java.lang.String r29 = "Page_Push"
            java.lang.String r30 = "agoo_arrive_id"
            r31 = 0
            r32 = 0
            r5 = 2
            java.lang.String[] r0 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x01cf }
            r5 = 0
            r0[r5] = r19     // Catch:{ Throwable -> 0x01cf }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01cf }
            r5.<init>()     // Catch:{ Throwable -> 0x01cf }
            r6 = r23
            r5.append(r6)     // Catch:{ Throwable -> 0x01c9 }
            java.lang.String r7 = r2.msgIds     // Catch:{ Throwable -> 0x01c9 }
            r5.append(r7)     // Catch:{ Throwable -> 0x01c9 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x01c9 }
            r0[r26] = r5     // Catch:{ Throwable -> 0x01c9 }
            r33 = r0
            r27.commitEvent((int) r28, (java.lang.String) r29, (java.lang.Object) r30, (java.lang.Object) r31, (java.lang.Object) r32, (java.lang.String[]) r33)     // Catch:{ Throwable -> 0x01c9 }
            java.lang.String r0 = "arrive"
            r7 = r18
            r5 = r20
            r8 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r0, r8)     // Catch:{ Throwable -> 0x01c7 }
            goto L_0x01f1
        L_0x01c7:
            r0 = move-exception
            goto L_0x01d6
        L_0x01c9:
            r0 = move-exception
            r7 = r18
            r5 = r20
            goto L_0x01d6
        L_0x01cf:
            r0 = move-exception
            r7 = r18
            r5 = r20
            r6 = r23
        L_0x01d6:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r8.<init>()     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r9 = "report message Throwable--->t="
            r8.append(r9)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0345 }
            r8.append(r0)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r8.toString()     // Catch:{ Throwable -> 0x0345 }
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r9)     // Catch:{ Throwable -> 0x0345 }
        L_0x01f1:
            org.android.agoo.message.MessageService r0 = r1.messageService     // Catch:{ Throwable -> 0x0345 }
            boolean r0 = r0.mo25532a((java.lang.String) r13)     // Catch:{ Throwable -> 0x0345 }
            if (r0 == 0) goto L_0x022c
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0345 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Throwable -> 0x0345 }
            if (r0 == 0) goto L_0x0224
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r0.<init>()     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = "handleRemoteMessage hasMessageDuplicate,messageId="
            r0.append(r2)     // Catch:{ Throwable -> 0x0345 }
            r0.append(r13)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = ",utdid="
            r0.append(r2)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r35)     // Catch:{ Throwable -> 0x0345 }
            r0.append(r2)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0345 }
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r2)     // Catch:{ Throwable -> 0x0345 }
        L_0x0224:
            java.lang.String r0 = "arrive_dup"
            r2 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r0, r2)     // Catch:{ Throwable -> 0x0345 }
            return
        L_0x022c:
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x0345 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Throwable -> 0x0345 }
            if (r0 == 0) goto L_0x0258
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r0.<init>()     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r8 = "handleMessage--->["
            r0.append(r8)     // Catch:{ Throwable -> 0x0345 }
            r0.append(r14)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r8 = "],["
            r0.append(r8)     // Catch:{ Throwable -> 0x0345 }
            r0.append(r11)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r8 = "]"
            r0.append(r8)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0345 }
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r9)     // Catch:{ Throwable -> 0x0345 }
        L_0x0258:
            java.lang.String r0 = "duplicate"
            java.lang.String r0 = r3.getStringExtra(r0)     // Catch:{ Throwable -> 0x0285 }
            boolean r8 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0285 }
            if (r8 != 0) goto L_0x0282
            r8 = r17
            boolean r0 = android.text.TextUtils.equals(r0, r8)     // Catch:{ Throwable -> 0x0280 }
            if (r0 == 0) goto L_0x02ab
            int r0 = r14.hashCode()     // Catch:{ Throwable -> 0x0280 }
            org.android.agoo.message.MessageService r9 = r1.messageService     // Catch:{ Throwable -> 0x0280 }
            boolean r0 = r9.mo25533a((java.lang.String) r13, (int) r0)     // Catch:{ Throwable -> 0x0280 }
            if (r0 == 0) goto L_0x02ab
            java.lang.String r0 = "arrive_dupbody"
            r9 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r0, r9)     // Catch:{ Throwable -> 0x0280 }
            return
        L_0x0280:
            r0 = move-exception
            goto L_0x0288
        L_0x0282:
            r8 = r17
            goto L_0x02ab
        L_0x0285:
            r0 = move-exception
            r8 = r17
        L_0x0288:
            com.taobao.accs.utl.ALog$Level r9 = com.taobao.accs.utl.ALog.Level.E     // Catch:{ Throwable -> 0x0345 }
            boolean r9 = com.taobao.accs.utl.ALog.isPrintLog(r9)     // Catch:{ Throwable -> 0x0345 }
            if (r9 == 0) goto L_0x02ab
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r9.<init>()     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r10 = "hasMessageDuplicate message,e="
            r9.append(r10)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0345 }
            r9.append(r0)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r9.toString()     // Catch:{ Throwable -> 0x0345 }
            r9 = 0
            java.lang.Object[] r10 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r10)     // Catch:{ Throwable -> 0x0345 }
        L_0x02ab:
            r0 = -1
            java.lang.String r4 = "notify"
            java.lang.String r4 = r3.getStringExtra(r4)     // Catch:{ Throwable -> 0x02b6 }
            int r0 = java.lang.Integer.parseInt(r4)     // Catch:{ Throwable -> 0x02b6 }
        L_0x02b6:
            java.lang.String r4 = ""
            java.lang.String r9 = "has_test"
            java.lang.String r9 = r3.getStringExtra(r9)     // Catch:{ Throwable -> 0x02e4 }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x02e4 }
            if (r10 != 0) goto L_0x02d9
            boolean r8 = android.text.TextUtils.equals(r9, r8)     // Catch:{ Throwable -> 0x02e4 }
            if (r8 == 0) goto L_0x02d9
            org.android.agoo.message.MessageService r8 = r1.messageService     // Catch:{ Throwable -> 0x02e4 }
            r9 = r16
            r8.mo25531a(r13, r14, r9, r0)     // Catch:{ Throwable -> 0x02e6 }
            java.lang.String r8 = "arrive_test"
            r10 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r8, r10)     // Catch:{ Throwable -> 0x02e6 }
            return
        L_0x02d9:
            r9 = r16
            java.lang.Class r8 = r34.getClass()     // Catch:{ Throwable -> 0x02e6 }
            java.lang.String r4 = r8.getName()     // Catch:{ Throwable -> 0x02e6 }
            goto L_0x02e6
        L_0x02e4:
            r9 = r16
        L_0x02e6:
            org.android.agoo.message.MessageService r8 = r1.messageService     // Catch:{ Throwable -> 0x0345 }
            r8.mo25531a(r13, r14, r9, r0)     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.utl.UTMini r27 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x0345 }
            r28 = 19999(0x4e1f, float:2.8025E-41)
            java.lang.String r29 = "Page_Push"
            java.lang.String r30 = "agoo_arrive_real_id"
            r31 = 0
            r32 = 0
            r8 = 2
            java.lang.String[] r0 = new java.lang.String[r8]     // Catch:{ Throwable -> 0x0345 }
            r8 = 0
            r0[r8] = r19     // Catch:{ Throwable -> 0x0345 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r8.<init>()     // Catch:{ Throwable -> 0x0345 }
            r8.append(r6)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = r2.msgIds     // Catch:{ Throwable -> 0x0345 }
            r8.append(r2)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = r8.toString()     // Catch:{ Throwable -> 0x0345 }
            r0[r26] = r2     // Catch:{ Throwable -> 0x0345 }
            r33 = r0
            r27.commitEvent((int) r28, (java.lang.String) r29, (java.lang.Object) r30, (java.lang.Object) r31, (java.lang.Object) r32, (java.lang.String[]) r33)     // Catch:{ Throwable -> 0x0345 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0345 }
            r0.<init>()     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r2 = "arrive_real_"
            r0.append(r2)     // Catch:{ Throwable -> 0x0345 }
            r0.append(r4)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0345 }
            r8 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r0, r8)     // Catch:{ Throwable -> 0x0345 }
            java.lang.String r0 = "monitor"
            java.io.Serializable r0 = r3.getSerializableExtra(r0)     // Catch:{ Throwable -> 0x0345 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r0 = (com.taobao.accs.p103ut.monitor.NetPerformanceMonitor) r0     // Catch:{ Throwable -> 0x0345 }
            if (r0 == 0) goto L_0x0341
            r0.onToAgooTime()     // Catch:{ Throwable -> 0x0345 }
            anet.channel.appmonitor.IAppMonitor r2 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ Throwable -> 0x0345 }
            r2.commitStat(r0)     // Catch:{ Throwable -> 0x0345 }
        L_0x0341:
            r34.onMessage(r35, r36)     // Catch:{ Throwable -> 0x0345 }
            goto L_0x036f
        L_0x0345:
            r0 = move-exception
            goto L_0x0355
        L_0x0347:
            r0 = move-exception
            r7 = r18
            r5 = r20
            goto L_0x0355
        L_0x034d:
            r0 = move-exception
            r5 = r9
            r7 = r18
            goto L_0x0355
        L_0x0352:
            r0 = move-exception
            r7 = r8
            r5 = r9
        L_0x0355:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "arrive_exception"
            r2.append(r3)
            java.lang.String r0 = r0.toString()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r2 = 0
            com.taobao.accs.utl.AppMonitorAdapter.commitCount(r5, r7, r0, r2)
        L_0x036f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.BaseIntentService.handleRemoteMessage(android.content.Context, android.content.Intent):void");
    }

    private final String getTrace(Context context, long j) {
        String str = null;
        String str2 = TextUtils.isEmpty((CharSequence) null) ? "unknow" : null;
        if (TextUtils.isEmpty((CharSequence) null)) {
            str = "unknow";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("appkey");
        stringBuffer.append("|");
        stringBuffer.append(j);
        stringBuffer.append("|");
        stringBuffer.append(System.currentTimeMillis());
        stringBuffer.append("|");
        stringBuffer.append(str2);
        stringBuffer.append("|");
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
