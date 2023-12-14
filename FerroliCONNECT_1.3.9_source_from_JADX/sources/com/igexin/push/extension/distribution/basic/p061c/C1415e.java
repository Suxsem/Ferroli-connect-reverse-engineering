package com.igexin.push.extension.distribution.basic.p061c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import org.android.agoo.common.AgooConstants;

/* renamed from: com.igexin.push.extension.distribution.basic.c.e */
public class C1415e extends BroadcastReceiver {

    /* renamed from: a */
    private static C1415e f2422a;

    /* renamed from: a */
    public static C1415e m2422a() {
        if (f2422a == null) {
            f2422a = new C1415e();
        }
        return f2422a;
    }

    public void onReceive(Context context, Intent intent) {
        String action;
        StringBuilder sb;
        String stringExtra;
        if (intent != null && (action = intent.getAction()) != null && action.equals("com.igexin.sdk.action.notification.burying.point")) {
            String stringExtra2 = intent.getStringExtra("checkpackage");
            String stringExtra3 = intent.getStringExtra("accesstoken");
            if (stringExtra2 != null && stringExtra3 != null && stringExtra2.equals(C1343f.f2169f.getPackageName()) && stringExtra3.equals(C1416f.f2426d)) {
                intent.setAction("com.igexin.sdk.action.doaction");
                intent.putExtra("accesstoken", C1343f.f2153ao);
                C1343f.f2169f.sendBroadcast(intent);
                PushTaskBean pushTaskBean = new PushTaskBean();
                pushTaskBean.setAppid(intent.getStringExtra("appid"));
                pushTaskBean.setMessageId(intent.getStringExtra("messageid"));
                pushTaskBean.setTaskId(intent.getStringExtra("taskid"));
                pushTaskBean.setId(intent.getStringExtra(AgooConstants.MESSAGE_ID));
                pushTaskBean.setAppKey(C1343f.f2165b);
                try {
                    int parseInt = Integer.parseInt(intent.getStringExtra("feedbackid")) + 30010;
                    pushTaskBean.setCurrentActionid(parseInt);
                    if (intent.getBooleanExtra("isFloat", false)) {
                        sb = new StringBuilder();
                        sb.append("notifyFloat:");
                        stringExtra = intent.getStringExtra("bigStyle");
                    } else {
                        sb = new StringBuilder();
                        sb.append("notifyStyle:");
                        stringExtra = intent.getStringExtra("notifyStyle");
                    }
                    sb.append(stringExtra);
                    String sb2 = sb.toString();
                    C1257f a = C1257f.m1711a();
                    a.mo14477a(pushTaskBean, parseInt + "", sb2);
                } catch (Exception unused) {
                }
            }
        }
    }
}
