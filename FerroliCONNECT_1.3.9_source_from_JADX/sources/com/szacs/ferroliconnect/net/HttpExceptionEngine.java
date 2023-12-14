package com.szacs.ferroliconnect.net;

import android.support.p000v4.app.NotificationCompat;
import com.p107tb.appyunsdk.exception.ErrorInfoResponse;
import com.p107tb.appyunsdk.utils.YunLog;
import org.json.JSONObject;
import retrofit2.HttpException;

public class HttpExceptionEngine {
    public static ErrorInfoResponse getErrorInfo(Throwable th) {
        ErrorInfoResponse errorInfoResponse = new ErrorInfoResponse();
        String str = "网络错误";
        errorInfoResponse.setMsg(str);
        int i = -1;
        errorInfoResponse.setCode(-1);
        if (th instanceof HttpException) {
            try {
                JSONObject jSONObject = new JSONObject(((HttpException) th).response().errorBody().string());
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    if (jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) != 400) {
                        errorInfoResponse.setCode(jSONObject.getInt(NotificationCompat.CATEGORY_STATUS));
                        errorInfoResponse.setMsg("Network error");
                        return errorInfoResponse;
                    } else if (jSONObject.has("detail")) {
                        JSONObject jSONObject2 = (JSONObject) jSONObject.getJSONArray("detail").get(0);
                        if (jSONObject2.has("code")) {
                            i = jSONObject2.getInt("code");
                        }
                        if (jSONObject2.has("detail")) {
                            str = jSONObject2.getString("detail");
                        }
                        errorInfoResponse.setCode(i);
                        errorInfoResponse.setMsg(str);
                        return errorInfoResponse;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorInfoResponse;
    }

    public static String getErrorMsg(Throwable th) {
        if (th instanceof HttpException) {
            try {
                String string = ((HttpException) th).response().errorBody().string();
                YunLog.m3843d("ExceptionEngine", "getErrorMsg errorData = " + string);
                JSONObject jSONObject = new JSONObject(string);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS) && jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) == 400 && jSONObject.has("detail")) {
                    JSONObject jSONObject2 = (JSONObject) jSONObject.getJSONArray("detail").get(0);
                    if (jSONObject2.has("detail")) {
                        return jSONObject2.getString("detail");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "网络错误";
    }
}
