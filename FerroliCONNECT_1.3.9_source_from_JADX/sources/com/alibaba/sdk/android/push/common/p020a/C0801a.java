package com.alibaba.sdk.android.push.common.p020a;

import android.support.p000v4.app.NotificationCompat;

/* renamed from: com.alibaba.sdk.android.push.common.a.a */
public enum C0801a {
    CHANNEL_SERVICE("com.taobao.accs.ChannelService", NotificationCompat.CATEGORY_SERVICE, true),
    KERNEL_SERVICE("com.taobao.accs.ChannelService$KernelService", NotificationCompat.CATEGORY_SERVICE, true),
    ACCS_JOB_SERVICE("com.taobao.accs.internal.AccsJobService", NotificationCompat.CATEGORY_SERVICE, true),
    MSG_DISTRIBUTE_SERVICE("com.taobao.accs.data.MsgDistributeService", NotificationCompat.CATEGORY_SERVICE, true),
    EVENT_RECEIVER("com.taobao.accs.EventReceiver", "receiver", false),
    SERVICE_RECEIVER("com.taobao.accs.ServiceReceiver", "receiver", true),
    AGOO_SERVICE("org.android.agoo.accs.AgooService", NotificationCompat.CATEGORY_SERVICE, true),
    ALIYUN_PUSH_INTENT_SERVICE("com.aliyun.ams.emas.push.AgooInnerService", NotificationCompat.CATEGORY_SERVICE, true),
    MSG_SERVICE("com.aliyun.ams.emas.push.MsgService", NotificationCompat.CATEGORY_SERVICE, true);
    

    /* renamed from: j */
    private String f1078j;

    /* renamed from: k */
    private String f1079k;

    /* renamed from: l */
    private boolean f1080l;

    private C0801a(String str, String str2, boolean z) {
        this.f1078j = str;
        this.f1079k = str2;
        this.f1080l = z;
    }

    /* renamed from: a */
    public String mo9882a() {
        return this.f1078j;
    }

    /* renamed from: b */
    public String mo9883b() {
        return this.f1079k;
    }

    /* renamed from: c */
    public boolean mo9884c() {
        return this.f1080l;
    }
}
