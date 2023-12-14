package com.alibaba.sdk.android.tbrest.request;

import android.content.Context;
import android.content.pm.PackageManager;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import com.alibaba.sdk.android.tbrest.utils.ByteUtils;
import com.alibaba.sdk.android.tbrest.utils.GzipUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.p108ut.device.UTDevice;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class BizRequest {
    private static final byte FLAGS_GET_CONFIG = 32;
    private static final byte FLAGS_GZIP = 1;
    private static final byte FLAGS_GZIP_FLUSH_DIC = 2;
    private static final byte FLAGS_KEEP_ALIVE = 8;
    private static final byte FLAGS_NO_GZIP = 0;
    private static final byte FLAGS_REAL_TIME_DEBUG = 16;
    private static final int HEAD_LENGTH = 8;
    private static final int PAYLOAD_MAX_LENGTH = 16777216;
    private static long mReceivedDataLen = 0;
    static String mResponseAdditionalData = null;
    static boolean needConfigByResponse = false;

    public static byte[] getPackRequest(Context context, SendService sendService, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, sendService.appKey, context, map, 1);
    }

    static byte[] getPackRequestByRealtime(Context context, SendService sendService, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, sendService.appKey, context, map, 2);
    }

    public static byte[] getPackRequest(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, str, context, map, 1);
    }

    static byte[] getPackRequestByRealtime(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        return getPackRequest(sendService, str, context, map, 2);
    }

    static byte[] getPackRequest(SendService sendService, String str, Context context, Map<String, String> map, int i) throws Exception {
        byte[] gzip = GzipUtils.gzip(getPayload(sendService, str, context, map));
        if (gzip == null || gzip.length >= 16777216) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(1);
        byteArrayOutputStream.write(ByteUtils.intToBytes3(gzip.length));
        byteArrayOutputStream.write(i);
        byte b = (byte) 9;
        if (needConfigByResponse) {
            b = (byte) (b | 32);
        }
        byteArrayOutputStream.write(b);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(gzip);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            LogUtil.m1030e(e.toString());
        }
        return byteArray;
    }

    private static byte[] getPayload(SendService sendService, String str, Context context, Map<String, String> map) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String head = getHead(sendService, str, context);
        if (head == null || head.length() <= 0) {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(0));
        } else {
            byteArrayOutputStream.write(ByteUtils.intToBytes2(head.getBytes().length));
            byteArrayOutputStream.write(head.getBytes());
        }
        if (map != null && map.size() > 0) {
            for (String next : map.keySet()) {
                byteArrayOutputStream.write(ByteUtils.intToBytes4(Integer.valueOf(next).intValue()));
                String str2 = map.get(next);
                if (str2 != null) {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(str2.getBytes().length));
                    byteArrayOutputStream.write(str2.getBytes());
                } else {
                    byteArrayOutputStream.write(ByteUtils.intToBytes4(0));
                }
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            LogUtil.m1030e(e.toString());
        }
        return byteArray;
    }

    public static String getHead(SendService sendService, String str, Context context) {
        String str2;
        String str3 = sendService.appVersion;
        if (str3 == null) {
            str3 = "";
        }
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            str2 = "Unknown";
        }
        String str4 = sendService.channel;
        if (str4 == null) {
            str4 = "";
        }
        String format = String.format("ak=%s&av=%s&avsys=%s&c=%s&d=%s&sv=%s", new Object[]{str, str3, str2, str4, UTDevice.getUtdid(context), RestConstants.UT_SDK_VRESION});
        LogUtil.m1032i("send url :" + format);
        return format;
    }

    static int parseResult(byte[] bArr) {
        int i = -1;
        if (bArr == null || bArr.length < 12) {
            LogUtil.m1030e("recv errCode UNKNOWN_ERROR");
        } else {
            mReceivedDataLen = (long) bArr.length;
            boolean z = true;
            if (ByteUtils.bytesToInt(bArr, 1, 3) + 8 != bArr.length) {
                LogUtil.m1030e("recv len error");
            } else {
                if (1 != (bArr[5] & 1)) {
                    z = false;
                }
                i = ByteUtils.bytesToInt(bArr, 8, 4);
                int length = bArr.length - 12 >= 0 ? bArr.length - 12 : 0;
                if (length <= 0) {
                    mResponseAdditionalData = null;
                } else if (z) {
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 12, bArr2, 0, length);
                    byte[] unGzip = GzipUtils.unGzip(bArr2);
                    mResponseAdditionalData = new String(unGzip, 0, unGzip.length);
                } else {
                    mResponseAdditionalData = new String(bArr, 12, length);
                }
            }
        }
        LogUtil.m1029d("errCode:" + i);
        return i;
    }
}
