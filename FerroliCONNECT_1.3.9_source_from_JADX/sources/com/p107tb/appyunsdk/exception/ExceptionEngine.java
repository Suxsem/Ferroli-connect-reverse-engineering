package com.p107tb.appyunsdk.exception;

import android.support.p000v4.app.NotificationCompat;
import com.contrarywind.timer.MessageHandler;
import com.google.android.gms.common.ConnectionResult;
import com.p107tb.appyunsdk.utils.YunLog;
import org.json.JSONObject;
import retrofit2.HttpException;

/* renamed from: com.tb.appyunsdk.exception.ExceptionEngine */
public class ExceptionEngine {
    private static final String LANGUAGE_TYPE_EN = "en";
    private static final String LANGUAGE_TYPE_ZH = "zh";
    private static ExceptionEngine exceptionEngine;
    private String language = "zh";

    public static String getErrorMsg(int i) {
        switch (i) {
            case -1:
                return "网络错误";
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case 412:
            case 413:
            case 414:
            case 415:
            case 416:
            case 417:
                return "";
            case 1200:
                return "验证码类型参数错误";
            case ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED:
                return "密码长度不在[6, 16]";
            case 1800:
                return "mac地址必填";
            case 1801:
                return "mac地址不匹配";
            case 1900:
                return "pass_code必填";
            case 1999:
                return "请求IP异常";
            case MessageHandler.WHAT_SMOOTH_SCROLL:
                return "type类型不正确";
            case 2100:
                return "uri参数必填";
            case 2200:
                return "二维码不存在";
            case 2201:
                return "二维码slug必填";
            case 2300:
                return "设备编号必填";
            case 2301:
                return "设备不存在";
            case 2302:
                return "设备已经被其他用户绑定";
            case 2303:
                return "设备slug必填";
            case 2304:
                return "请求设备固件版本参数不全";
            case 2305:
                return "设备升级规则不存在";
            case 2306:
                return "请求设备升级包参数之版本不存在";
            case 2307:
                return "子设备id必填";
            case 2308:
                return "重复绑定";
            case 2309:
                return "设备绑定关系不存在";
            case 2310:
                return "获取不存在的子设备field";
            case 2311:
                return "设备token必填";
            case 2312:
                return "获取不存在的设备field";
            case 2313:
                return "设备版本必填";
            case 2314:
                return "子设备名称必填";
            case 2400:
                return "app slug 必填";
            case 2401:
                return "app 不存在";
            case 2402:
                return "请求App版本参数不全 ";
            case 2403:
                return "App升级规则不存在";
            case 2404:
                return "请求App升级包参数之版本不存在";
            case 2500:
                return "cityname必填";
            case 2501:
                return "经度必填";
            case 2502:
                return "纬度必填";
            case 2503:
                return "经度格式错误";
            case 2504:
                return "纬度格式错误";
            case 2505:
                return "经纬度不支持或范围错误";
            case 2600:
                return "partner不存在";
            case 2700:
                return "类型不匹配 ";
            case 2701:
                return "起始值大于截止值";
            case 2702:
                return "目标值大于截止值";
            case 2703:
                return "存在规则范围被包含";
            case 2704:
                return "存在规则包含了截止值";
            case 2705:
            case 2706:
                return "存在规则包含了起始值";
            case 2707:
                return "存在目标值包含在规则内";
            case 2708:
                return "升级系统类型必填";
            case 2709:
                return "升级是否为beta必填";
            case 2710:
                return "升级目标版本slug必填";
            case 2711:
                return "升级目标版本不存在";
            case 2712:
                return "升级起始版本slug必填";
            case 2713:
                return "升级起始标版本不存在";
            case 2714:
                return "升级截止版本slug必填";
            case 2715:
                return "升级截止版本不存在";
            case 2800:
                return "app类型必填";
            case 2900:
                return "必须提交文件";
            case 3000:
                return "装机经历的时间（单位：年）必填";
            default:
                switch (i) {
                    case 400:
                    case 401:
                    case 402:
                    case 403:
                    case 404:
                        break;
                    default:
                        switch (i) {
                            case 500:
                            case 501:
                            case 502:
                            case 503:
                            case 504:
                            case 505:
                                break;
                            default:
                                switch (i) {
                                    case 1100:
                                        return "提交的手机号不规范";
                                    case 1101:
                                        return "手机号被占用";
                                    case 1102:
                                        return "手机号不一致";
                                    case 1103:
                                        return "新手机号不规范";
                                    default:
                                        switch (i) {
                                            case 1230:
                                                return "用户已存在，但尚未激活";
                                            case 1231:
                                                return "用户名已被使用";
                                            case 1232:
                                                return "邮箱地址已被注册";
                                            case 1233:
                                                return "邮箱地址不存在";
                                            case 1234:
                                                return "无效的邮箱地址";
                                            case 1235:
                                                return "无效的用户名";
                                            case 1236:
                                                return "前后输入邮箱不一致";
                                            case 1237:
                                                return "邮箱验证类型不存在";
                                            case 1238:
                                                return "发送验证邮件失败";
                                            case 1239:
                                                return "创建邮件记录失败";
                                            default:
                                                switch (i) {
                                                    case 1250:
                                                        return "请求参数不全";
                                                    case 1251:
                                                        return "用户名不规范";
                                                    case 1252:
                                                        return "注册信息已被占用";
                                                    default:
                                                        switch (i) {
                                                            case 1301:
                                                                return "用户不存在";
                                                            case 1302:
                                                                return "用户已被禁止登陆";
                                                            case 1303:
                                                                return "用户名或密码错误";
                                                            case 1304:
                                                                return "用户名和密码必填";
                                                            case 1305:
                                                                return "用户token已过期";
                                                            case 1306:
                                                                return "用户名必填";
                                                            case 1307:
                                                                return "用户密码必填";
                                                            case 1308:
                                                                return "用户token必填";
                                                            case 1309:
                                                                return "用户邮箱必填";
                                                            case 1310:
                                                                return "用户密码错误";
                                                            default:
                                                                switch (i) {
                                                                    case 1400:
                                                                        return "验证码已发送过";
                                                                    case 1401:
                                                                        return "验证码发送失败";
                                                                    case 1402:
                                                                        return "用户已存在";
                                                                    default:
                                                                        switch (i) {
                                                                            case 1600:
                                                                                return "验证码错误";
                                                                            case 1601:
                                                                                return "验证码超时";
                                                                            case 1602:
                                                                                return "验证码输错次数超限，请重新点击发送验证码";
                                                                            case 1603:
                                                                                return "验证码已被使用";
                                                                            case 1604:
                                                                                return "验证码格式错误";
                                                                            case 1605:
                                                                                return "邀请码不存在";
                                                                            default:
                                                                                switch (i) {
                                                                                    case 1700:
                                                                                        return "产品编号必填";
                                                                                    case 1701:
                                                                                        return "产品不存在";
                                                                                    case 1702:
                                                                                        return "产品编号不匹配";
                                                                                    case 1703:
                                                                                        return "产品不匹配";
                                                                                    case 1704:
                                                                                        return "产品标识必填";
                                                                                    case 1705:
                                                                                        return "产品标识参数是一个列表";
                                                                                    case 1706:
                                                                                        return "产品密钥必填";
                                                                                    case 1707:
                                                                                        return "产品密钥不匹配";
                                                                                    default:
                                                                                        return "网络错误";
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
                return "";
        }
    }

    private ExceptionEngine() {
    }

    public static ExceptionEngine getInstance() {
        if (exceptionEngine == null) {
            exceptionEngine = new ExceptionEngine();
        }
        return exceptionEngine;
    }

    public static int getErrorCode(Throwable th) {
        if (!(th instanceof HttpException)) {
            return -1;
        }
        try {
            JSONObject jSONObject = new JSONObject(((HttpException) th).response().errorBody().string());
            if (!jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                return -1;
            }
            if (jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) != 400) {
                return jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
            }
            if (!jSONObject.has("detail")) {
                return -1;
            }
            JSONObject jSONObject2 = (JSONObject) jSONObject.getJSONArray("detail").get(0);
            if (jSONObject2.has("code")) {
                return jSONObject2.getInt("code");
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
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

    public ErrorInfoResponse getErrorInfo(Throwable th) {
        String str;
        ErrorInfoResponse errorInfoResponse = new ErrorInfoResponse();
        String str2 = "网络错误";
        errorInfoResponse.setMsg(str2);
        errorInfoResponse.setCode(-1);
        if (th instanceof HttpException) {
            try {
                JSONObject jSONObject = new JSONObject(((HttpException) th).response().errorBody().string());
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    int i = 400;
                    if (jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) != 400) {
                        int i2 = jSONObject.getInt(NotificationCompat.CATEGORY_STATUS);
                        if (this.language.equals("en")) {
                            str2 = "Network error";
                        }
                        errorInfoResponse.setCode(i2);
                        errorInfoResponse.setMsg(str2);
                        return errorInfoResponse;
                    } else if (jSONObject.has("detail")) {
                        JSONObject jSONObject2 = (JSONObject) jSONObject.getJSONArray("detail").get(0);
                        if (jSONObject2.has("code")) {
                            i = jSONObject2.getInt("code");
                        }
                        if (jSONObject2.has("detail")) {
                            if (this.language.equals("en")) {
                                str = jSONObject2.getString("detail");
                            } else {
                                str = getErrorMsg(i);
                            }
                            str2 = str;
                        }
                        errorInfoResponse.setCode(i);
                        errorInfoResponse.setMsg(str2);
                        return errorInfoResponse;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorInfoResponse;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }
}
