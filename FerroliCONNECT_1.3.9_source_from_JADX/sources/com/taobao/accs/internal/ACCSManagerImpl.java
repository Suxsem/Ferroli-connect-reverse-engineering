package com.taobao.accs.internal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import anet.channel.SessionCenter;
import com.alibaba.sdk.android.logger.ILog;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.C2018a;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.C2033g;
import com.taobao.accs.data.Message;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.net.C2057j;
import com.taobao.accs.utl.AccsLogger;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.C2086c;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class ACCSManagerImpl implements IACCSManager {

    /* renamed from: a */
    public C2049b f3332a;

    /* renamed from: b */
    private int f3333b = 0;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public boolean f3334c = false;

    /* renamed from: d */
    private String f3335d;

    /* renamed from: e */
    private ILog f3336e;

    public void forceDisableService(Context context) {
    }

    public void forceEnableService(Context context) {
    }

    public String getUserUnit() {
        return null;
    }

    public ACCSManagerImpl(Context context, String str) {
        GlobalClientInfo.f3223a = context.getApplicationContext();
        this.f3332a = new C2057j(GlobalClientInfo.f3223a, 1, str);
        this.f3335d = str;
        this.f3336e = AccsLogger.getLogger("ACCSMgrImpl_" + this.f3332a.f3385m);
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new C2037a(this, str, context), 64, TimeUnit.MILLISECONDS);
    }

    public void bindApp(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        bindApp(context, str, "accs", str2, iAppReceiver);
    }

    public void bindApp(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        if (context != null) {
            this.f3334c = true;
            this.f3336e.mo9707d("bindApp", Constants.KEY_APP_KEY, str);
            Message a = Message.m3457a(context.getPackageName(), 1);
            if (this.f3332a.mo18492k() && TextUtils.isEmpty(this.f3332a.f3381i.getAppSecret())) {
                this.f3336e.mo9713w("isSecurityOff and null secret");
                this.f3332a.mo18471a(a, AccsErrorCode.APPSECRET_NULL);
            } else if (TextUtils.isEmpty(str)) {
                this.f3336e.mo9713w("appkey null");
                this.f3332a.mo18471a(a, AccsErrorCode.APPKEY_NULL);
            } else {
                C2049b bVar = this.f3332a;
                bVar.f3373a = str3;
                bVar.f3374b = str;
                bVar.f3381i.getAppSecret();
                UtilityImpl.m3761e(context, str);
                if (iAppReceiver != null) {
                    C2018a.m3435a().mo18374a(this.f3335d, C2086c.m3773a(iAppReceiver));
                }
                m3553a(context, str, str3);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0023 A[Catch:{ Throwable -> 0x0096 }] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051 A[Catch:{ Throwable -> 0x0096 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0085 A[Catch:{ Throwable -> 0x0096 }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void m3553a(android.content.Context r7, java.lang.String r8, java.lang.String r9) {
        /*
            r6 = this;
            r0 = 1
            android.content.Intent r1 = r6.m3550a(r7, r0)
            com.taobao.accs.client.GlobalClientInfo r2 = com.taobao.accs.client.GlobalClientInfo.getInstance(r7)     // Catch:{ Throwable -> 0x0096 }
            android.content.pm.PackageInfo r2 = r2.getPackageInfo()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r2 = r2.versionName     // Catch:{ Throwable -> 0x0096 }
            boolean r3 = com.taobao.accs.utl.UtilityImpl.m3754c(r7)     // Catch:{ Throwable -> 0x0096 }
            if (r3 != 0) goto L_0x0020
            java.lang.String r3 = "EMAS_ACCS_SDK"
            boolean r3 = com.taobao.accs.utl.UtilityImpl.utdidChanged(r3, r7)     // Catch:{ Throwable -> 0x0096 }
            if (r3 == 0) goto L_0x001e
            goto L_0x0020
        L_0x001e:
            r3 = 0
            goto L_0x0021
        L_0x0020:
            r3 = 1
        L_0x0021:
            if (r3 == 0) goto L_0x002f
            com.alibaba.sdk.android.logger.ILog r4 = r6.f3336e     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r5 = "force bindApp"
            r4.mo9706d((java.lang.String) r5)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r4 = "fouce_bind"
            r1.putExtra(r4, r0)     // Catch:{ Throwable -> 0x0096 }
        L_0x002f:
            java.lang.String r4 = "appKey"
            r1.putExtra(r4, r8)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "ttid"
            r1.putExtra(r8, r9)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "appVersion"
            r1.putExtra(r8, r2)     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r8 = "app_sercet"
            com.taobao.accs.net.b r9 = r6.f3332a     // Catch:{ Throwable -> 0x0096 }
            com.taobao.accs.AccsClientConfig r9 = r9.f3381i     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r9 = r9.getAppSecret()     // Catch:{ Throwable -> 0x0096 }
            r1.putExtra(r8, r9)     // Catch:{ Throwable -> 0x0096 }
            boolean r8 = com.taobao.accs.utl.UtilityImpl.isMainProcess(r7)     // Catch:{ Throwable -> 0x0096 }
            if (r8 == 0) goto L_0x0085
            com.taobao.accs.net.b r8 = r6.f3332a     // Catch:{ Throwable -> 0x0096 }
            com.taobao.accs.data.Message r8 = com.taobao.accs.data.Message.m3449a((com.taobao.accs.net.C2049b) r8, (android.content.Context) r7, (android.content.Intent) r1)     // Catch:{ Throwable -> 0x0096 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r8.mo18393e()     // Catch:{ Throwable -> 0x0096 }
            if (r9 == 0) goto L_0x0081
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r8.mo18393e()     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r1 = r8.f3281q     // Catch:{ Throwable -> 0x0096 }
            r9.setDataId(r1)     // Catch:{ Throwable -> 0x0096 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r8.mo18393e()     // Catch:{ Throwable -> 0x0096 }
            r9.setMsgType(r0)     // Catch:{ Throwable -> 0x0096 }
            com.taobao.accs.ut.monitor.NetPerformanceMonitor r9 = r8.mo18393e()     // Catch:{ Throwable -> 0x0096 }
            java.net.URL r1 = r8.f3270f     // Catch:{ Throwable -> 0x0096 }
            if (r1 == 0) goto L_0x007c
            java.net.URL r1 = r8.f3270f     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0096 }
            goto L_0x007e
        L_0x007c:
            java.lang.String r1 = ""
        L_0x007e:
            r9.setHost(r1)     // Catch:{ Throwable -> 0x0096 }
        L_0x0081:
            r6.m3552a((android.content.Context) r7, (com.taobao.accs.data.Message) r8, (int) r0, (boolean) r3)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x008c
        L_0x0085:
            com.alibaba.sdk.android.logger.ILog r8 = r6.f3336e     // Catch:{ Throwable -> 0x0096 }
            java.lang.String r9 = "bindApp only allow in main process"
            r8.mo9713w((java.lang.String) r9)     // Catch:{ Throwable -> 0x0096 }
        L_0x008c:
            com.taobao.accs.net.b r8 = r6.f3332a     // Catch:{ Throwable -> 0x0096 }
            android.content.Context r7 = r7.getApplicationContext()     // Catch:{ Throwable -> 0x0096 }
            r8.mo18481b((android.content.Context) r7)     // Catch:{ Throwable -> 0x0096 }
            goto L_0x009e
        L_0x0096:
            r7 = move-exception
            com.alibaba.sdk.android.logger.ILog r8 = r6.f3336e
            java.lang.String r9 = "bindApp exception"
            r8.mo9709e(r9, r7)
        L_0x009e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.m3553a(android.content.Context, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void m3552a(android.content.Context r6, com.taobao.accs.data.Message r7, int r8, boolean r9) {
        /*
            r5 = this;
            com.taobao.accs.net.b r0 = r5.f3332a
            r0.mo18469a()
            if (r7 != 0) goto L_0x001f
            com.alibaba.sdk.android.logger.ILog r7 = r5.f3336e
            java.lang.String r9 = "message is null"
            r7.mo9708e((java.lang.String) r9)
            java.lang.String r6 = r6.getPackageName()
            com.taobao.accs.data.Message r6 = com.taobao.accs.data.Message.m3457a((java.lang.String) r6, (int) r8)
            com.taobao.accs.net.b r7 = r5.f3332a
            com.alibaba.sdk.android.error.ErrorCode r8 = com.taobao.accs.AccsErrorCode.PARAMETER_ERROR
            r7.mo18471a((com.taobao.accs.data.Message) r6, (com.alibaba.sdk.android.error.ErrorCode) r8)
            goto L_0x00ad
        L_0x001f:
            r6 = 2
            r0 = 0
            r1 = 1
            if (r8 == r1) goto L_0x0047
            if (r8 == r6) goto L_0x0027
            goto L_0x008f
        L_0x0027:
            com.taobao.accs.net.b r9 = r5.f3332a
            com.taobao.accs.client.c r9 = r9.mo18491j()
            java.lang.String r2 = r7.mo18394f()
            boolean r9 = r9.mo18382e(r2)
            if (r9 == 0) goto L_0x008f
            com.alibaba.sdk.android.logger.ILog r9 = r5.f3336e
            java.lang.String r2 = "unbind app, already unbind"
            r9.mo9711i((java.lang.String) r2)
            com.taobao.accs.net.b r9 = r5.f3332a
            com.alibaba.sdk.android.error.ErrorCode r2 = com.taobao.accs.AccsErrorCode.SUCCESS
            r9.mo18471a((com.taobao.accs.data.Message) r7, (com.alibaba.sdk.android.error.ErrorCode) r2)
        L_0x0045:
            r9 = 0
            goto L_0x0090
        L_0x0047:
            java.lang.String r2 = r7.mo18394f()
            com.taobao.accs.net.b r3 = r5.f3332a
            com.taobao.accs.client.c r3 = r3.mo18491j()
            boolean r3 = r3.mo18381d(r2)
            if (r3 == 0) goto L_0x0077
            if (r9 != 0) goto L_0x0077
            com.alibaba.sdk.android.logger.ILog r9 = r5.f3336e
            java.lang.String r2 = "bind app from cache"
            r9.mo9711i((java.lang.String) r2)
            com.taobao.accs.AccsState r9 = com.taobao.accs.AccsState.getInstance()
            java.lang.String r2 = r5.f3335d
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            java.lang.String r4 = "bfc"
            r9.mo18227a(r2, r4, r3)
            com.taobao.accs.net.b r9 = r5.f3332a
            com.alibaba.sdk.android.error.ErrorCode r2 = com.taobao.accs.AccsErrorCode.SUCCESS
            r9.mo18471a((com.taobao.accs.data.Message) r7, (com.alibaba.sdk.android.error.ErrorCode) r2)
            goto L_0x0045
        L_0x0077:
            com.taobao.accs.net.b r3 = r5.f3332a
            com.taobao.accs.client.c r3 = r3.mo18491j()
            boolean r3 = r3.mo18383f(r2)
            if (r3 == 0) goto L_0x0086
            if (r9 != 0) goto L_0x0086
            goto L_0x0045
        L_0x0086:
            com.taobao.accs.net.b r9 = r5.f3332a
            com.taobao.accs.client.c r9 = r9.mo18491j()
            r9.mo18380c(r2)
        L_0x008f:
            r9 = 1
        L_0x0090:
            if (r9 == 0) goto L_0x00ad
            com.alibaba.sdk.android.logger.ILog r9 = r5.f3336e
            r2 = 3
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "sendControlMessage"
            r2[r0] = r3
            java.lang.String r0 = "command"
            r2[r1] = r0
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            r2[r6] = r8
            r9.mo9712i((java.lang.Object[]) r2)
            com.taobao.accs.net.b r6 = r5.f3332a
            r6.mo18482b(r7, r1)
        L_0x00ad:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.ACCSManagerImpl.m3552a(android.content.Context, com.taobao.accs.data.Message, int, boolean):void");
    }

    public void unbindApp(Context context) {
        this.f3336e.mo9711i("unbindApp");
        this.f3334c = false;
        if (m3555a(context)) {
            m3551a(context, 2, (String) null, (String) null);
            return;
        }
        Intent a = m3550a(context, 2);
        if (UtilityImpl.isMainProcess(context)) {
            m3552a(context, Message.m3453a(this.f3332a, a), 2, false);
        }
    }

    public void bindUser(Context context, String str) {
        bindUser(context, str, false);
    }

    public void bindUser(Context context, String str, boolean z) {
        try {
            this.f3336e.mo9712i("bindUser", "userId", str, "force", Boolean.valueOf(z));
            if (m3555a(context)) {
                m3551a(context, 3, (String) null, (String) null);
                return;
            }
            Intent a = m3550a(context, 3);
            String i = this.f3332a.mo18490i();
            if (TextUtils.isEmpty(i)) {
                this.f3336e.mo9708e("appkey null");
                return;
            }
            if (UtilityImpl.m3754c(context) || z) {
                this.f3336e.mo9706d("force bind user");
                a.putExtra(Constants.KEY_FOUCE_BIND, true);
                z = true;
            }
            a.putExtra(Constants.KEY_APP_KEY, i);
            a.putExtra(Constants.KEY_USER_ID, str);
            if (UtilityImpl.isMainProcess(context)) {
                Message d = Message.m3470d(this.f3332a, a);
                if (d.mo18393e() != null) {
                    d.mo18393e().setDataId(d.f3281q);
                    d.mo18393e().setMsgType(2);
                    d.mo18393e().setHost(d.f3270f != null ? d.f3270f.toString() : "");
                }
                m3552a(context, d, 3, z);
            } else {
                this.f3336e.mo9713w("bindUser not main process, ignored");
            }
            this.f3332a.mo18481b(context.getApplicationContext());
        } catch (Throwable th) {
            this.f3336e.mo9709e("bindUser", th);
        }
    }

    public void unbindUser(Context context) {
        this.f3336e.mo9711i("unBindUse");
        if (m3555a(context)) {
            m3551a(context, 4, (String) null, (String) null);
            return;
        }
        Intent a = m3550a(context, 4);
        String i = this.f3332a.mo18490i();
        if (TextUtils.isEmpty(i)) {
            this.f3336e.mo9708e("appKey null");
            return;
        }
        a.putExtra(Constants.KEY_APP_KEY, i);
        if (UtilityImpl.isMainProcess(context)) {
            m3552a(context, Message.m3471e(this.f3332a, a), 4, false);
        } else {
            this.f3336e.mo9713w("unBindUser not main process, ignored");
        }
    }

    public void bindService(Context context, String str) {
        this.f3336e.mo9712i("bindService", Constants.KEY_SERVICE_ID, str);
        if (m3555a(context)) {
            m3551a(context, 5, str, (String) null);
            return;
        }
        Intent a = m3550a(context, 5);
        String i = this.f3332a.mo18490i();
        if (TextUtils.isEmpty(i)) {
            this.f3336e.mo9708e("appKey null");
            return;
        }
        a.putExtra(Constants.KEY_APP_KEY, i);
        a.putExtra(Constants.KEY_SERVICE_ID, str);
        if (UtilityImpl.isMainProcess(context)) {
            Message b = Message.m3466b(this.f3332a, a);
            if (b.mo18393e() != null) {
                b.mo18393e().setDataId(b.f3281q);
                b.mo18393e().setMsgType(3);
                b.mo18393e().setHost(b.f3270f != null ? b.f3270f.toString() : "");
            }
            m3552a(context, b, 5, false);
        } else {
            this.f3336e.mo9713w("bindService not main process, ignored");
        }
        this.f3332a.mo18481b(context.getApplicationContext());
    }

    public void unbindService(Context context, String str) {
        this.f3336e.mo9712i("unbindService", Constants.KEY_SERVICE_ID, str);
        if (m3555a(context)) {
            m3551a(context, 6, str, (String) null);
            return;
        }
        Intent a = m3550a(context, 6);
        String i = this.f3332a.mo18490i();
        if (TextUtils.isEmpty(i)) {
            this.f3336e.mo9708e("appKey null");
            return;
        }
        a.putExtra(Constants.KEY_APP_KEY, i);
        a.putExtra(Constants.KEY_SERVICE_ID, str);
        if (UtilityImpl.isMainProcess(context)) {
            m3552a(context, Message.m3468c(this.f3332a, a), 6, false);
        } else {
            this.f3336e.mo9713w("unbindService not main process, ignored");
        }
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3) {
        return sendData(context, str, str2, bArr, str3, (String) null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendData(context, str, str2, bArr, str3, str4, (URL) null);
    }

    public String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        Context context2 = context;
        return sendData(context, new ACCSManager.AccsRequest(str, str2, bArr, str3, str4, url, (String) null));
    }

    public String sendData(Context context, ACCSManager.AccsRequest accsRequest) {
        try {
            if (!UtilityImpl.isMainProcess(context)) {
                this.f3336e.mo9708e("sendData not in mainprocess");
                return null;
            } else if (accsRequest == null) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", "data null");
                this.f3336e.mo9708e("sendData dataInfo null");
                return null;
            } else {
                if (TextUtils.isEmpty(accsRequest.dataId)) {
                    synchronized (ACCSManagerImpl.class) {
                        this.f3333b++;
                        accsRequest.dataId = this.f3333b + "";
                    }
                }
                if (TextUtils.isEmpty(this.f3332a.mo18490i())) {
                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "data appkey null");
                    this.f3336e.mo9710e("sendData appkey null", Constants.KEY_DATA_ID, accsRequest.dataId);
                    return null;
                }
                this.f3332a.mo18469a();
                Message a = Message.m3450a(this.f3332a, context, context.getPackageName(), accsRequest);
                if (a.mo18393e() != null) {
                    a.mo18393e().onSend();
                }
                this.f3332a.mo18482b(a, true);
                return accsRequest.dataId;
            }
        } catch (Throwable th) {
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "data " + th.toString());
            this.f3336e.mo9710e("sendData", Constants.KEY_DATA_ID, accsRequest.dataId, th);
        }
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4) {
        return sendRequest(context, str, str2, bArr, str3, str4, (URL) null);
    }

    public String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url) {
        Context context2 = context;
        return sendRequest(context, new ACCSManager.AccsRequest(str, str2, bArr, str3, str4, url, (String) null));
    }

    public String sendRequest(Context context, ACCSManager.AccsRequest accsRequest, String str, boolean z) {
        if (accsRequest == null) {
            try {
                this.f3336e.mo9708e("sendRequest request null");
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, (String) null, "1", "request null");
                return null;
            } catch (Throwable th) {
                if (accsRequest != null) {
                    AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "request " + th.toString());
                    this.f3336e.mo9710e("sendRequest", Constants.KEY_DATA_ID, accsRequest.dataId, th);
                }
            }
        } else if (!UtilityImpl.isMainProcess(context)) {
            this.f3336e.mo9708e("sendRequest not in mainprocess");
            return null;
        } else {
            if (TextUtils.isEmpty(accsRequest.dataId)) {
                synchronized (ACCSManagerImpl.class) {
                    this.f3333b++;
                    accsRequest.dataId = this.f3333b + "";
                }
            }
            if (TextUtils.isEmpty(this.f3332a.mo18490i())) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "request appkey null");
                this.f3336e.mo9710e("sendRequest appkey null", Constants.KEY_DATA_ID, accsRequest.dataId);
                return null;
            }
            this.f3332a.mo18469a();
            if (str == null) {
                str = context.getPackageName();
            }
            Message a = Message.m3452a(this.f3332a, context, str, Constants.TARGET_SERVICE_PRE, accsRequest, z);
            if (a.mo18393e() != null) {
                a.mo18393e().onSend();
            }
            this.f3332a.mo18482b(a, true);
            return accsRequest.dataId;
        }
    }

    public String sendRequest(Context context, ACCSManager.AccsRequest accsRequest) {
        return sendRequest(context, accsRequest, (String) null, true);
    }

    public String sendPushResponse(Context context, ACCSManager.AccsRequest accsRequest, TaoBaseService.ExtraInfo extraInfo) {
        if (context == null || accsRequest == null) {
            this.f3336e.mo9710e("sendPushResponse input null", context, accsRequest, "extraInfo", extraInfo);
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "", "1", "sendPushResponse null");
            return null;
        }
        try {
            AppMonitorAdapter.commitAlarmSuccess("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, "push response total");
            if (TextUtils.isEmpty(this.f3332a.mo18490i())) {
                AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "sendPushResponse appkey null");
                this.f3336e.mo9710e("sendPushResponse appkey null", "dataid", accsRequest.dataId);
                return null;
            }
            if (TextUtils.isEmpty(accsRequest.dataId)) {
                synchronized (ACCSManagerImpl.class) {
                    this.f3333b++;
                    accsRequest.dataId = this.f3333b + "";
                }
            }
            if (extraInfo == null) {
                extraInfo = new TaoBaseService.ExtraInfo();
            }
            accsRequest.host = null;
            extraInfo.fromPackage = context.getPackageName();
            this.f3336e.mo9712i("sendPushResponse", Constants.KEY_HOST, extraInfo.fromHost, "pkg", extraInfo.fromPackage, Constants.KEY_DATA_ID, accsRequest.dataId);
            if (context.getPackageName().equals(extraInfo.fromPackage) && UtilityImpl.isMainProcess(context)) {
                sendRequest(context, accsRequest, context.getPackageName(), true);
            }
            return null;
        } catch (Throwable th) {
            AppMonitorAdapter.commitAlarmFail("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, accsRequest.serviceId, "1", "push response " + th.toString());
            this.f3336e.mo9710e("sendPushResponse", Constants.KEY_DATA_ID, accsRequest.dataId, th);
        }
    }

    public boolean isNetworkReachable(Context context) {
        return UtilityImpl.m3765g(context);
    }

    /* renamed from: a */
    private boolean m3555a(Context context) {
        C2049b bVar = this.f3332a;
        return bVar == null || !bVar.mo18491j().mo18381d(context.getPackageName());
    }

    /* renamed from: a */
    private Intent m3550a(Context context, int i) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_COMMAND);
        intent.setClassName(context.getPackageName(), AdapterUtilityImpl.channelService);
        intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
        intent.putExtra("command", i);
        intent.putExtra(Constants.KEY_APP_KEY, this.f3332a.f3374b);
        intent.putExtra(Constants.KEY_CONFIG_TAG, this.f3335d);
        return intent;
    }

    @Deprecated
    public void setMode(Context context, int i) {
        ACCSClient.setEnvironment(context, i);
    }

    /* renamed from: a */
    private void m3551a(Context context, int i, String str, String str2) {
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", i);
        intent.putExtra(Constants.KEY_SERVICE_ID, str);
        intent.putExtra(Constants.KEY_DATA_ID, str2);
        intent.putExtra(Constants.KEY_APP_KEY, this.f3332a.f3374b);
        intent.putExtra(Constants.KEY_CONFIG_TAG, this.f3335d);
        intent.putExtra(Constants.KEY_ERROR_OBJ, i == 2 ? AccsErrorCode.SUCCESS : AccsErrorCode.APP_NOT_BIND);
        C2033g.m3534a(context, intent);
    }

    public void setProxy(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
        if (!TextUtils.isEmpty(str)) {
            edit.putString(Constants.KEY_PROXY_HOST, str);
        }
        edit.putInt(Constants.KEY_PROXY_PORT, i);
        edit.apply();
    }

    public void startInAppConnection(Context context, String str, String str2, IAppReceiver iAppReceiver) {
        startInAppConnection(context, str, (String) null, str2, iAppReceiver);
    }

    public void startInAppConnection(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver) {
        if (iAppReceiver != null) {
            C2018a.m3435a().mo18374a(this.f3335d, C2086c.m3773a(iAppReceiver));
        }
        if (!UtilityImpl.isMainProcess(context)) {
            this.f3336e.mo9713w("inapp only init in main process!");
            return;
        }
        this.f3336e.mo9712i("startInAppConnection", str);
        if (!TextUtils.isEmpty(str)) {
            if (!TextUtils.equals(this.f3332a.mo18490i(), str)) {
                C2049b bVar = this.f3332a;
                bVar.f3373a = str3;
                bVar.f3374b = str;
                bVar.f3381i.getAppSecret();
                UtilityImpl.m3761e(context, str);
            }
            this.f3332a.mo18469a();
        }
    }

    public void setLoginInfo(Context context, ILoginInfo iLoginInfo) {
        GlobalClientInfo.getInstance(context).setLoginInfoImpl(this.f3332a.f3385m, iLoginInfo);
    }

    public void clearLoginInfo(Context context) {
        GlobalClientInfo.getInstance(context).clearLoginInfoImpl();
    }

    public boolean cancel(Context context, String str) {
        return this.f3332a.mo18477a(str);
    }

    public Map<String, Boolean> getChannelState() throws Exception {
        String b = this.f3332a.mo18478b((String) null);
        HashMap hashMap = new HashMap();
        hashMap.put(b, false);
        if (SessionCenter.getInstance(this.f3332a.f3381i.getAppKey()).getThrowsException(b, 60000) != null) {
            hashMap.put(b, true);
        }
        this.f3336e.mo9712i("getChannelState", hashMap);
        return hashMap;
    }

    public Map<String, Boolean> forceReConnectChannel() throws Exception {
        SessionCenter.getInstance(this.f3332a.f3381i.getAppKey()).forceRecreateAccsSession();
        return getChannelState();
    }

    public boolean isChannelError(int i) {
        return AccsErrorCode.isChannelError(i);
    }

    public void registerSerivce(Context context, String str, String str2) {
        GlobalClientInfo.getInstance(context).registerService(str, str2);
    }

    public void unRegisterSerivce(Context context, String str) {
        GlobalClientInfo.getInstance(context).unRegisterService(str);
    }

    public void registerDataListener(Context context, String str, AccsAbstractDataListener accsAbstractDataListener) {
        GlobalClientInfo.getInstance(context).registerListener(str, accsAbstractDataListener);
    }

    public void unRegisterDataListener(Context context, String str) {
        GlobalClientInfo.getInstance(context).unregisterListener(str);
    }

    public void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map) {
        this.f3332a.mo18469a();
        this.f3332a.mo18482b(Message.m3455a(this.f3332a, str, str2, str3, true, s, str4, map), true);
    }

    public void updateConfig(AccsClientConfig accsClientConfig) {
        C2049b bVar = this.f3332a;
        if (bVar instanceof C2057j) {
            ((C2057j) bVar).mo18515a(accsClientConfig);
        }
    }

    public void cleanLocalBindInfo() {
        this.f3332a.mo18491j().mo18377a();
    }

    public boolean isConnected() {
        return this.f3332a.mo18493l();
    }

    public int getLastConnectErrorCode() {
        return this.f3332a.mo18494m();
    }

    public void disconnect() {
        this.f3332a.mo18495n();
    }

    public void reconnect() {
        this.f3332a.mo18496o();
    }

    public void reset() {
        this.f3332a.mo18497p();
        try {
            SharedPreferences.Editor edit = GlobalClientInfo.f3223a.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.clear();
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        C2018a.m3435a().mo18376b(this.f3335d);
        this.f3334c = false;
    }
}
