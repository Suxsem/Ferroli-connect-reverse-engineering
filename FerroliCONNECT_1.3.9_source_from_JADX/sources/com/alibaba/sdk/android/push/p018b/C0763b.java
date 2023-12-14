package com.alibaba.sdk.android.push.p018b;

import android.content.Context;
import android.graphics.Bitmap;
import anet.channel.util.ALog;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p009a.C0669a;
import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import com.alibaba.sdk.android.crashdefend.CrashDefendApi;
import com.alibaba.sdk.android.crashdefend.CrashDefendCallback;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.logger.LogLevel;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.alibaba.sdk.android.push.p017a.C0749a;
import com.alibaba.sdk.android.push.p017a.C0752b;
import com.alibaba.sdk.android.push.p023d.C0817a;
import com.alibaba.sdk.android.push.p027g.C0827a;
import com.alibaba.sdk.android.sender.AlicloudSender;
import com.alibaba.sdk.android.sender.SdkInfo;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.utl.AccsLogger;
import com.taobao.agoo.p105a.p106a.C2124a;

/* renamed from: com.alibaba.sdk.android.push.b.b */
public class C0763b implements CloudPushService {

    /* renamed from: a */
    private static C0763b f985a = new C0763b();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public C0753a f986b;

    /* renamed from: c */
    private Context f987c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f988d = true;

    /* renamed from: a */
    public static C0763b m746a() {
        return f985a;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public synchronized void m747a(final Context context, final CommonCallback commonCallback) {
        AmsLogger.getImportantLogger().mo9544i("call register");
        C0827a.m825a().mo9904a((CommonCallback) new CommonCallback() {
            public void onFailed(String str, String str2) {
                commonCallback.onFailed(str, str2);
            }

            public void onSuccess(String str) {
                commonCallback.onSuccess(str);
                C0763b.this.f986b.mo9833b(context);
            }
        });
        C0817a.m792a(context).mo9895a(C0673c.m489a().mo9524a());
        m752b();
        m748a(context, C0673c.m489a().mo9524a(), C0673c.m489a().mo9532d());
    }

    /* renamed from: a */
    private synchronized void m748a(Context context, String str, String str2) {
        C0749a.m706a().mo9815a(context, str, str2, "3.7.6");
        C0749a.m706a().mo9816a((C0752b) new C0752b() {
            /* renamed from: a */
            public void mo9817a(boolean z) {
                C0817a.m791a().mo9900a(z);
            }
        });
    }

    /* renamed from: a */
    private boolean m751a(String str, CommonCallback commonCallback, Runnable runnable) {
        if (this.f987c == null) {
            AmsLogger.getImportantLogger().mo9541e("please call PushServiceFactory.init first");
            if (commonCallback != null) {
                ErrorCode build = C0804d.f1113w.copy().detail(str).build();
                commonCallback.onFailed(build.getCode(), build.getMsg());
            }
            return false;
        }
        m753c();
        if (!this.f988d) {
            AmsLogger.getImportantLogger().mo9541e("push disabled");
            if (commonCallback != null) {
                ErrorCode build2 = C0804d.f1112v.copy().detail(str).build();
                commonCallback.onFailed(build2.getCode(), build2.getMsg());
            }
            return false;
        } else if (runnable == null) {
            return true;
        } else {
            runnable.run();
            return true;
        }
    }

    /* renamed from: b */
    private void m752b() {
        SdkInfo sdkInfo = new SdkInfo();
        sdkInfo.setSdkId("push");
        sdkInfo.setSdkVersion("3.7.6");
        sdkInfo.setAppKey(C0673c.m489a().mo9524a());
        if (C0669a.m465a() != null) {
            AlicloudSender.asyncSend(C0669a.m465a(), sdkInfo);
        } else {
            AlicloudSender.asyncSend(C0669a.m467b(), sdkInfo);
        }
    }

    /* renamed from: c */
    private void m753c() {
        Context context;
        if (this.f986b == null && (context = this.f987c) != null) {
            this.f986b = new C0753a(context);
        }
    }

    /* renamed from: a */
    public void mo9850a(Context context) {
        AmsLogger.getImportantLogger().mo9544i("Initialize Mobile Push service...");
        this.f987c = context;
        if (this.f986b == null) {
            this.f986b = new C0753a(context);
        }
        CrashDefendApi.registerCrashDefendSdk(context, "push", "3.7.6", 10, 5, new CrashDefendCallback() {
            public void onSdkClosed(int i) {
                AmsLogger.getImportantLogger().mo9541e("crash limit exceeds, close forever");
                boolean unused = C0763b.this.f988d = false;
            }

            public void onSdkStart(int i, int i2, int i3) {
                boolean unused = C0763b.this.f988d = true;
            }

            public void onSdkStop(int i, int i2, int i3, long j) {
                AmsLogger.getImportantLogger().mo9541e("crash limit exceeds");
                boolean unused = C0763b.this.f988d = false;
            }
        });
    }

    public void addAlias(final String str, final CommonCallback commonCallback) {
        m751a("addAlias", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9837b(str, commonCallback);
            }
        });
    }

    public void bindAccount(final String str, final CommonCallback commonCallback) {
        m751a("bindAccount", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9829a(str, commonCallback);
            }
        });
    }

    public void bindPhoneNumber(final String str, final CommonCallback commonCallback) {
        m751a("bindPhoneNumber", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9843d(str, commonCallback);
            }
        });
    }

    public void bindTag(int i, String[] strArr, String str, CommonCallback commonCallback) {
        final int i2 = i;
        final String[] strArr2 = strArr;
        final String str2 = str;
        final CommonCallback commonCallback2 = commonCallback;
        m751a("bindTag", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9823a(i2, strArr2, str2, commonCallback2);
            }
        });
    }

    public void checkPushChannelStatus(final CommonCallback commonCallback) {
        m751a("checkPushChannelStatus", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9845f(commonCallback);
            }
        });
    }

    public void clearNotifications() {
        m751a("clearNotifications", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9831b();
            }
        });
    }

    public void clickMessage(final CPushMessage cPushMessage) {
        m751a("clickMessage", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9826a(cPushMessage);
            }
        });
    }

    public void closeDoNotDisturbMode() {
        m751a("closeDoNotDisturbMode", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9830a(false);
            }
        });
    }

    public void dismissMessage(final CPushMessage cPushMessage) {
        m751a("dismissMessage", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9835b(cPushMessage);
            }
        });
    }

    public String getDeviceId() {
        if (m751a("getDeviceId", (CommonCallback) null, (Runnable) null)) {
            return this.f986b.mo9818a();
        }
        return null;
    }

    public String getUTDeviceId() {
        if (m751a("getUTDeviceId", (CommonCallback) null, (Runnable) null)) {
            return this.f986b.mo9819a(this.f987c);
        }
        return null;
    }

    public void listAliases(final CommonCallback commonCallback) {
        m751a("listAlias", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9834b(commonCallback);
            }
        });
    }

    public void listTags(final int i, final CommonCallback commonCallback) {
        m751a("listTags", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9822a(i, commonCallback);
            }
        });
    }

    public void onAppStart() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void register(final android.content.Context r3, final com.alibaba.sdk.android.push.CommonCallback r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 != 0) goto L_0x002b
            com.alibaba.sdk.android.ams.common.logger.AmsLogger r3 = com.alibaba.sdk.android.ams.common.logger.AmsLogger.getImportantLogger()     // Catch:{ all -> 0x0037 }
            java.lang.String r0 = "context null"
            r3.mo9541e(r0)     // Catch:{ all -> 0x0037 }
            if (r4 == 0) goto L_0x0029
            com.alibaba.sdk.android.error.ErrorCode r3 = com.alibaba.sdk.android.push.common.p020a.C0804d.f1107q     // Catch:{ all -> 0x0037 }
            com.alibaba.sdk.android.error.ErrorBuilder r3 = r3.copy()     // Catch:{ all -> 0x0037 }
            java.lang.String r0 = "register context null"
            com.alibaba.sdk.android.error.ErrorBuilder r3 = r3.detail(r0)     // Catch:{ all -> 0x0037 }
            com.alibaba.sdk.android.error.ErrorCode r3 = r3.build()     // Catch:{ all -> 0x0037 }
            java.lang.String r0 = r3.getCode()     // Catch:{ all -> 0x0037 }
            java.lang.String r3 = r3.getMsg()     // Catch:{ all -> 0x0037 }
            r4.onFailed(r0, r3)     // Catch:{ all -> 0x0037 }
        L_0x0029:
            monitor-exit(r2)
            return
        L_0x002b:
            java.lang.String r0 = "register"
            com.alibaba.sdk.android.push.b.b$12 r1 = new com.alibaba.sdk.android.push.b.b$12     // Catch:{ all -> 0x0037 }
            r1.<init>(r3, r4)     // Catch:{ all -> 0x0037 }
            r2.m751a((java.lang.String) r0, (com.alibaba.sdk.android.push.CommonCallback) r4, (java.lang.Runnable) r1)     // Catch:{ all -> 0x0037 }
            monitor-exit(r2)
            return
        L_0x0037:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.p018b.C0763b.register(android.content.Context, com.alibaba.sdk.android.push.CommonCallback):void");
    }

    public void register(Context context, String str, String str2, CommonCallback commonCallback) {
        if (commonCallback != null) {
            ErrorCode build = C0804d.f1114x.copy().msg("请使用PushServiceFactory.init(Context appContext, String appKey, String appSecret)动态设置appKey appSecret").build();
            commonCallback.onFailed(build.getCode(), build.getMsg());
        }
    }

    public void removeAlias(final String str, final CommonCallback commonCallback) {
        m751a(C2124a.JSON_CMD_REMOVEALIAS, commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9841c(str, commonCallback);
            }
        });
    }

    public void setAppSecret(final String str) {
        m751a("setAppSecret", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9840c(str);
            }
        });
    }

    public void setAppkey(final String str) {
        m751a("setAppKey", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9836b(str);
            }
        });
    }

    public void setDebug(final boolean z) {
        m751a("setDebug", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9838b(z);
            }
        });
    }

    public void setDoNotDisturb(int i, int i2, int i3, int i4, CommonCallback commonCallback) {
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        final CommonCallback commonCallback2 = commonCallback;
        m751a("setDoNotDisturb", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9821a(i5, i6, i7, i8, commonCallback2);
            }
        });
    }

    public void setLogLevel(final int i) {
        m751a("setLogLevel", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                AmsLogger.log_level = i;
                int i = 0;
                ALog.setUseTlog(false);
                ACCSClient.changeNetworkSdkLoggerToAccs();
                int i2 = i;
                if (i2 == -1) {
                    AccsLogger.enable(false);
                    i = 5;
                } else if (i2 == 0 || i2 == 2 || i2 == 1) {
                    AccsLogger.enable(true);
                    int i3 = i;
                    if (i3 == 0) {
                        AccsLogger.setLevel(LogLevel.WARN);
                        i = 3;
                    } else if (i3 != 1) {
                        AccsLogger.setLevel(LogLevel.DEBUG);
                    } else {
                        AccsLogger.setLevel(LogLevel.INFO);
                        ALog.setLevel(2);
                        return;
                    }
                } else {
                    return;
                }
                ALog.setLevel(i);
            }
        });
    }

    public void setNotificationLargeIcon(final Bitmap bitmap) {
        m751a("setNotificationLargeIcon", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9824a(bitmap);
            }
        });
    }

    public void setNotificationSmallIcon(final int i) {
        m751a("setNotificationSmallIcon", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9820a(i);
            }
        });
    }

    public void setNotificationSoundFilePath(final String str) {
        m751a("setNotificationSoundFilePath", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9828a(str);
            }
        });
    }

    public void setPushIntentService(final Class cls) {
        m751a("setPushIntentService", (CommonCallback) null, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9827a(cls);
            }
        });
    }

    public void turnOffPushChannel(final CommonCallback commonCallback) {
        m751a("turnOffPushChannel", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9842d(commonCallback);
            }
        });
    }

    public void turnOnPushChannel(final CommonCallback commonCallback) {
        m751a("turnOnPushChannel", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9844e(commonCallback);
            }
        });
    }

    public void unbindAccount(final CommonCallback commonCallback) {
        m751a("unbindAccount", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9825a(commonCallback);
            }
        });
    }

    public void unbindPhoneNumber(final CommonCallback commonCallback) {
        m751a("unbindPhoneNumber", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9839c(commonCallback);
            }
        });
    }

    public void unbindTag(int i, String[] strArr, String str, CommonCallback commonCallback) {
        final int i2 = i;
        final String[] strArr2 = strArr;
        final String str2 = str;
        final CommonCallback commonCallback2 = commonCallback;
        m751a("unBindTag", commonCallback, (Runnable) new Runnable() {
            public void run() {
                C0763b.this.f986b.mo9832b(i2, strArr2, str2, commonCallback2);
            }
        });
    }
}
