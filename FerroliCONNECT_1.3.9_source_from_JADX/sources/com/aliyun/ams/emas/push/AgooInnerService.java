package com.aliyun.ams.emas.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.TaobaoBaseIntentService;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class AgooInnerService extends TaobaoBaseIntentService {
    public static final String AGOO_RECEIVE_ACTION = "com.alibaba.sdk.android.push.RECEIVE";

    /* renamed from: a */
    private static final String m1056a(Bundle bundle, int i) {
        StringBuilder sb = new StringBuilder();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            for (int i2 = 0; i2 < i; i2++) {
                sb.append(9);
            }
            if (obj instanceof String) {
                sb.append("String\t");
                sb.append(str);
                sb.append(9);
                sb.append(obj);
                sb.append(10);
            } else if (obj instanceof Integer) {
                sb.append("int\t");
                sb.append(str);
                sb.append(9);
                sb.append(obj);
                sb.append(10);
            } else if (obj instanceof Long) {
                sb.append("long\t");
                sb.append(str);
                sb.append(9);
                sb.append(obj);
                sb.append(10);
            } else if (obj instanceof Boolean) {
                sb.append("boolean\t");
                sb.append(str);
                sb.append(9);
                sb.append(obj);
                sb.append(10);
            } else if (obj instanceof Bundle) {
                sb.append("Bundle\t");
                sb.append(str);
                sb.append(9);
                sb.append(10);
                sb.append(m1056a((Bundle) obj, i + 1));
            } else {
                sb.append("unknown\t");
                sb.append(str);
                sb.append(9);
                sb.append(obj);
                sb.append(10);
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void onMessage(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        String stringExtra2 = intent.getStringExtra(AgooConstants.MESSAGE_ID);
        String stringExtra3 = intent.getStringExtra("task_id");
        ALog.m3728i("AgooInnerService", "onMessage:id:" + stringExtra2 + ", task_id:" + stringExtra3 + ", body:" + stringExtra, new Object[0]);
        ALog.m3728i("AgooInnerService", m1056a(intent.getExtras(), 1), new Object[0]);
        Intent intent2 = new Intent();
        intent2.putExtras(intent.getExtras());
        intent2.setAction(AGOO_RECEIVE_ACTION);
        intent2.setPackage(context.getPackageName());
        try {
            Class a = C0910h.m1072a();
            if (a == null) {
                ALog.m3725d("AgooInnerService", "Send broadcast", new Object[0]);
                context.sendBroadcast(intent2, context.getPackageName() + ".AGOO");
                return;
            }
            ALog.m3725d("AgooInnerService", "Start service:" + a.getName(), new Object[0]);
            intent2.setClass(context, a);
            IntentDispatch.dispatchIntent(context, intent2, a.getName());
        } catch (Throwable th) {
            ALog.m3726e("AgooInnerService", "Send message failed.", th, new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void onError(Context context, String str) {
        ALog.m3728i("AgooInnerService", "onError:" + str, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onRegistered(Context context, String str) {
        ALog.m3728i("AgooInnerService", "onRegistered:" + str, new Object[0]);
    }

    /* access modifiers changed from: protected */
    public void onUnregistered(Context context, String str) {
        ALog.m3728i("AgooInnerService", "onUnregistered:" + str, new Object[0]);
    }
}
