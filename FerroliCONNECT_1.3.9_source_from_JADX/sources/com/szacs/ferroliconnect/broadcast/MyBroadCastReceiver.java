package com.szacs.ferroliconnect.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.LaunchUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import java.util.Arrays;

public class MyBroadCastReceiver extends BroadcastReceiver {
    public static final String BROADCAST_SHOW_GT_TOAST = "android.show.gt.toast";
    private static final String TAG = "MyBroadCastReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (((action.hashCode() == 2058391672 && action.equals(BROADCAST_SHOW_GT_TOAST)) ? (char) 0 : 65535) == 0) {
            Log.d(TAG, " <MyBroadCastReceiver> receive pendingIntent click");
            LaunchUtil.setTopApp(context);
            int intExtra = intent.getIntExtra("error_code", -1);
            int intExtra2 = intent.getIntExtra("error_type", -1);
            Log.d(TAG, " <MyBroadCastReceiver> errorCode = " + intExtra + " errorType = " + intExtra2);
            String binaryString = Integer.toBinaryString(intExtra2);
            int length = 8 - binaryString.length();
            StringBuilder sb = new StringBuilder(binaryString);
            for (int i = 0; i < length; i++) {
                sb.insert(0, "0");
            }
            char[] charArray = sb.toString().toCharArray();
            int length2 = charArray.length;
            String str = charArray[length2 - 2] + "";
            LogUtil.m3315d(TAG, " 错误类型 length = " + length2 + " 类型Char = " + str + " 补齐后二进制字符串 = " + Arrays.toString(charArray));
            ToastUtil.showCenterToast(context, String.format("%s %s%s", new Object[]{context.getResources().getString(C1683R.string.boiler_gt_failure_toast), Integer.parseInt(str) == 1 ? "A" : "F", Integer.valueOf(intExtra)}));
        }
    }
}
