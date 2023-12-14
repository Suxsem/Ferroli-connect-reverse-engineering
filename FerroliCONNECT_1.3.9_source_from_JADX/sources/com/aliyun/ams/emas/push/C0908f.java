package com.aliyun.ams.emas.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.aliyun.ams.emas.push.notification.C0916f;
import com.aliyun.ams.emas.push.notification.CPushMessage;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.C2122a;
import java.util.Calendar;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.aliyun.ams.emas.push.f */
/* compiled from: Taobao */
public class C0908f {

    /* renamed from: a */
    private Context f1427a;

    /* renamed from: b */
    private int f1428b;

    /* renamed from: c */
    private int f1429c;

    /* renamed from: d */
    private int f1430d;

    /* renamed from: e */
    private int f1431e;

    /* renamed from: f */
    private boolean f1432f = false;

    public C0908f(Context context) {
        this.f1427a = context;
    }

    /* renamed from: a */
    public void mo10178a(boolean z) {
        this.f1432f = z;
    }

    /* renamed from: a */
    public void mo10176a(int i, int i2, int i3, int i4, CommonCallback commonCallback) {
        ALog.m3725d("MPS:CloudPushService", "setDoNotDisturb " + i + ":" + i2 + "-" + i3 + ":" + i4, new Object[0]);
        if (i >= 0 && i <= 23 && i3 >= 0 && i3 <= 23 && i2 >= 0 && i2 <= 59 && i4 >= 0 && i4 <= 59) {
            this.f1432f = true;
            this.f1428b = i;
            this.f1429c = i2;
            this.f1430d = i3;
            this.f1431e = i4;
            if (commonCallback != null) {
                commonCallback.onSuccess("");
            }
        } else if (commonCallback != null) {
            commonCallback.onFailed(C2122a.INVALID_ARG.getCode(), C2122a.INVALID_ARG.getMsg());
        }
    }

    /* renamed from: a */
    public boolean mo10179a() {
        if (!this.f1432f) {
            return false;
        }
        Calendar instance = Calendar.getInstance();
        int i = (this.f1428b * 60) + this.f1429c;
        int i2 = (this.f1430d * 60) + this.f1431e;
        int i3 = (instance.get(11) * 60) + instance.get(12);
        if (i <= i2) {
            if (i3 < i || i3 > i2) {
                return false;
            }
            return true;
        } else if (i3 >= i || i3 <= i2) {
            return true;
        } else {
            return false;
        }
    }

    /* renamed from: a */
    public void mo10177a(CPushMessage cPushMessage) {
        if (cPushMessage == null || TextUtils.isEmpty(cPushMessage.getMessageId())) {
            ALog.m3727e("MPS:CloudPushService", "message is null", new Object[0]);
        } else if (this.f1427a == null) {
            ALog.m3727e("MPS:CloudPushService", "context is null", new Object[0]);
        } else {
            try {
                Intent intent = new Intent();
                intent.setAction(C0910h.f1433a);
                intent.setClassName(this.f1427a.getPackageName(), MsgService.class.getName());
                intent.putExtra(AgooConstants.ACTION_TYPE, AgooConstants.MESSAGE_TYPE_OPEN);
                intent.putExtra(C0916f.MSG_ID, cPushMessage.getMessageId());
                intent.putExtra("extData", cPushMessage.getTraceInfo());
                this.f1427a.startService(intent);
            } catch (Throwable th) {
                ALog.m3726e("MPS:CloudPushService", "Click message event upload failed.", th, new Object[0]);
            }
        }
    }

    /* renamed from: b */
    public void mo10180b(CPushMessage cPushMessage) {
        if (cPushMessage == null || TextUtils.isEmpty(cPushMessage.getMessageId())) {
            ALog.m3727e("MPS:CloudPushService", "message is null", new Object[0]);
        } else if (this.f1427a == null) {
            ALog.m3727e("MPS:CloudPushService", "context is null", new Object[0]);
        } else {
            try {
                Intent intent = new Intent();
                intent.setAction(C0910h.f1433a);
                intent.setClassName(this.f1427a.getPackageName(), MsgService.class.getName());
                intent.putExtra(AgooConstants.ACTION_TYPE, AgooConstants.MESSAGE_TYPE_DELETE);
                intent.putExtra(C0916f.MSG_ID, cPushMessage.getMessageId());
                intent.putExtra("extData", cPushMessage.getTraceInfo());
                this.f1427a.startService(intent);
            } catch (Throwable th) {
                ALog.m3726e("MPS:CloudPushService", "Dismiss message event upload failed.", th, new Object[0]);
            }
        }
    }
}
