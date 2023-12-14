package com.szacs.ferroliconnect.gtpush;

import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.SubmitGtClientIdResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.bean.GtBoilerErrorBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.broadcast.MyBroadCastReceiver;
import com.szacs.ferroliconnect.util.GsonUtil;
import com.szacs.ferroliconnect.util.LaunchUtil;
import com.szacs.ferroliconnect.util.NotificationUtil;

public class GtIntentService extends GTIntentService {
    private static final String TAG = "GtIntentService";

    public void onReceiveServicePid(Context context, int i) {
        Log.d(TAG, "<GtIntentService> onReceiveServicePid pid = " + i);
    }

    public void onReceiveClientId(Context context, String str) {
        Log.d(TAG, "<GtIntentService> clientId = " + str);
        submitClientId(str, context);
    }

    public void onReceiveMessageData(Context context, GTTransmitMessage gTTransmitMessage) {
        Log.d(TAG, "<GtIntentService> onReceiveMessageData gtTransmitMessage = " + gTTransmitMessage.toString());
        String appid = gTTransmitMessage.getAppid();
        String taskId = gTTransmitMessage.getTaskId();
        String messageId = gTTransmitMessage.getMessageId();
        byte[] payload = gTTransmitMessage.getPayload();
        gTTransmitMessage.getPkgName();
        gTTransmitMessage.getClientId();
        if (payload == null) {
            Log.e(TAG, "<GtIntentService> receiver payload = null");
            return;
        }
        String str = new String(payload);
        GtBoilerErrorBean gtBoilerErrorBean = (GtBoilerErrorBean) GsonUtil.fromJson(str, GtBoilerErrorBean.class);
        if (Integer.parseInt(gtBoilerErrorBean.getBfc().split("x")[1], 16) != 0) {
            if (!LaunchUtil.isRunningForeground(context) || isScreenOff(context)) {
                Log.d(TAG, " <GtIntentService> gt show notification");
                sendNotification(context, gtBoilerErrorBean);
            } else {
                Log.d(TAG, " <GtIntentService> gt show toast");
                sendBroadcast(getGtIntent(context, gtBoilerErrorBean));
            }
            Log.d(TAG, "<GtIntentService> receiver payload = " + str + " messageid = " + messageId + " taskid = " + taskId + " appid = " + appid + " bfc = " + gtBoilerErrorBean.getBfi() + " bfi = " + gtBoilerErrorBean.getBfi());
        }
    }

    public void onReceiveOnlineState(Context context, boolean z) {
        Log.d(TAG, "<GtIntentService> onReceiveOnlineState state = " + z);
    }

    public void onReceiveCommandResult(Context context, GTCmdMessage gTCmdMessage) {
        Log.d(TAG, "<GtIntentService> onReceiveCommandResult gtCmdMessage = " + gTCmdMessage.toString());
    }

    public void onNotificationMessageArrived(Context context, GTNotificationMessage gTNotificationMessage) {
        Log.d(TAG, "<GtIntentService> onNotificationMessageArrived gtNotificationMessage = " + gTNotificationMessage.toString());
    }

    public void onNotificationMessageClicked(Context context, GTNotificationMessage gTNotificationMessage) {
        Log.d(TAG, "<GtIntentService> onNotificationMessageClicked gtNotificationMessage = " + gTNotificationMessage.toString());
    }

    private void submitClientId(String str, final Context context) {
        AppYunManager.getInstance().submitGtClientId(str, new IAppYunResponseListener<SubmitGtClientIdResponse>() {
            public void onSuccess(SubmitGtClientIdResponse submitGtClientIdResponse) {
                Log.d(GtIntentService.TAG, "<GtIntentService> Submit GTClientId Success");
                String replace = UserCenter.getUserInfo().getSlug().replace("-", "_");
                String valueOf = String.valueOf(System.currentTimeMillis());
                Log.d(GtIntentService.TAG, "<GtIntentService> bindAlias user_slug = " + replace + "");
                boolean bindAlias = PushManager.getInstance().bindAlias(context, replace, valueOf);
                Log.d(GtIntentService.TAG, "<GtIntentService> bindAlias result = " + bindAlias);
            }

            public void onFailure(int i, String str) {
                Log.d(GtIntentService.TAG, "<GtIntentService> Submit GTClientId Failed");
            }
        });
    }

    private void sendNotification(Context context, GtBoilerErrorBean gtBoilerErrorBean) {
        NotificationUtil.getInstance(context).sendNotification("Notification", "Fault occurred,click me for detail", PendingIntent.getBroadcast(context, (int) (System.currentTimeMillis() / 1000), getGtIntent(context, gtBoilerErrorBean), 1073741824));
    }

    private Intent getGtIntent(Context context, GtBoilerErrorBean gtBoilerErrorBean) {
        String[] split = gtBoilerErrorBean.getBfc().split("x");
        String[] split2 = gtBoilerErrorBean.getBfi().split("x");
        int parseInt = Integer.parseInt(split[1], 16);
        int parseInt2 = Integer.parseInt(split2[1], 16);
        Intent intent = new Intent(context, MyBroadCastReceiver.class);
        intent.setAction(MyBroadCastReceiver.BROADCAST_SHOW_GT_TOAST);
        intent.putExtra("error_code", parseInt);
        intent.putExtra("error_type", parseInt2);
        return intent;
    }

    public static boolean isScreenOff(Context context) {
        return ((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " <GtIntentService> OnDestroy stopGtPush");
    }
}
