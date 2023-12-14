package com.taobao.accs.internal;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.igexin.sdk.PushConsts;
import com.taobao.accs.base.IBaseReceiver;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UtilityImpl;

/* compiled from: Taobao */
public class ReceiverImpl implements IBaseReceiver {
    public void onReceive(Context context, Intent intent) {
        ALog.m3725d("ReceiverImpl", "ReceiverImpl onReceive begin......", new Object[0]);
        if (intent == null || TextUtils.isEmpty(intent.getAction())) {
            intent = new Intent();
        } else if (!m3557a(intent)) {
            return;
        }
        if (!UtilityImpl.m3752b(context, true)) {
            try {
                intent.setClassName(context.getPackageName(), AdapterUtilityImpl.channelService);
                IntentDispatch.dispatchIntent(context.getApplicationContext(), intent, AdapterUtilityImpl.channelService);
                if (UtilityImpl.m3758d(context)) {
                    String agooCustomServiceName = AdapterGlobalClientInfo.getAgooCustomServiceName(context);
                    intent.setClassName(context, agooCustomServiceName);
                    IntentDispatch.dispatchIntent(context.getApplicationContext(), intent, agooCustomServiceName);
                }
            } catch (Throwable th) {
                ALog.m3727e("ReceiverImpl", "ReceiverImpl onReceive,exception,e=" + th.getMessage(), new Object[0]);
            }
        }
    }

    /* renamed from: a */
    private boolean m3557a(Intent intent) {
        String action = intent.getAction();
        return action.equals(PushConsts.ACTION_BROADCAST_USER_PRESENT) || action.equals(PushConsts.ACTION_BROADCAST_TO_BOOT) || action.equals("android.intent.action.PACKAGE_REMOVED") || action.equals(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE);
    }
}
