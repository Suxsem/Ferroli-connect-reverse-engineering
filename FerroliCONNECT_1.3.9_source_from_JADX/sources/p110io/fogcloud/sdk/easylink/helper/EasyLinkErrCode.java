package p110io.fogcloud.sdk.easylink.helper;

import com.taobao.agoo.p105a.p106a.C2125b;

/* renamed from: io.fogcloud.sdk.easylink.helper.EasyLinkErrCode */
public class EasyLinkErrCode {
    public static String BUSY = toJsonM("easylink busy");
    public static int BUSY_CODE = 4003;
    public static int CALLBACK_CODE = 4005;
    public static String CLOSED = toJsonM("easylink closed");
    public static int CLOSED_CODE = 4004;
    public static String CONTEXT = toJsonM("invalid context");
    public static int CONTEXT_CODE = 4002;
    public static int EXCEPTION_CODE = 4006;
    public static String INVALID = toJsonM("invalid param");
    public static int INVALID_CODE = 4001;
    public static int START_CODE = 0;
    public static int STOP_CODE = 4000;
    public static String SUCCESS = toJsonM(C2125b.JSON_SUCCESS);

    private static String toJsonM(String str) {
        return "{\"message\":\"" + str + "\"}";
    }
}
