package p110io.fogcloud.sdk.easylink.helper;

/* renamed from: io.fogcloud.sdk.easylink.helper.ComHelper */
public class ComHelper {
    public static boolean checkPara(String... strArr) {
        if (strArr == null || strArr.equals("") || strArr.length <= 0) {
            return false;
        }
        for (String str : strArr) {
            if (str == null || str.equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            return parseInt > 999 && parseInt < 10000;
        } catch (NumberFormatException unused) {
        }
    }

    public void successCBEasyLink(int i, String str, EasyLinkCallBack easyLinkCallBack) {
        if (easyLinkCallBack != null) {
            easyLinkCallBack.onSuccess(i, str);
        }
    }

    public void failureCBEasyLink(int i, String str, EasyLinkCallBack easyLinkCallBack) {
        if (easyLinkCallBack != null) {
            easyLinkCallBack.onFailure(i, str);
        }
    }
}
