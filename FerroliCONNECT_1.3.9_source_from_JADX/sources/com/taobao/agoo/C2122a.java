package com.taobao.agoo;

import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.error.ErrorDefine;
import com.taobao.agoo.p105a.p106a.C2125b;

/* renamed from: com.taobao.agoo.a */
/* compiled from: Taobao */
public class C2122a {
    public static final ErrorCode ACCS_CHECK_ERROR = f3575a.defineSdkError("accs_disabled").msg("accs检查不通过").solution("请检查初始化是否成功").solution("请检查配置是否正确").solution("请检查请求是否是在主进程").build();
    public static final ErrorCode AGOO_NOT_BIND = f3575a.defineSdkError("agoo_not_bind").msg("请先注册初始化agoo").solution("请检查初始化是否成功").build();
    public static final ErrorCode INVALID_ARG = f3575a.defineSdkError("invalid_arg").msg("请求参数错误").solution("请检查输入参数").build();
    public static final ErrorCode REGISTER_DATA_ERROR = f3575a.defineSdkError("register_data_error").msg("构造注册数据失败").solution("请检查配置参数是否正确，初始化是否成功").build();
    public static final ErrorCode REMOVE_ALIAS_FAIL_NO_ALIAS = f3575a.defineSdkError("remove_alias_fail_no_alias").msg("移除别名失败，本地没有别名记录").solution("请检查输入的别名是否正确").solution("低版本推送有概率出现，添加别名后，应用的数据被清除，导致sdk内部存储的别名信息丢失，无法移除").build();
    public static final ErrorCode REMOVE_ALIAS_FAIL_NO_TOKEN = f3575a.defineSdkError("remove_alias_fail_no_token").msg("移除别名失败，本地没有别名记录").solution("请检查输入的别名是否正确").solution("低版本推送有概率出现，添加别名后，应用的数据被清除，导致sdk内部存储的别名信息丢失，无法移除").build();
    public static final ErrorCode SUCCESS = f3575a.defineSdkError(C2125b.JSON_SUCCESS).msg(C2125b.JSON_SUCCESS).build();

    /* renamed from: a */
    private static final ErrorDefine f3575a = new ErrorDefine("EAGOO");
    public static final ErrorCode[] codes = {SUCCESS, REMOVE_ALIAS_FAIL_NO_TOKEN, REMOVE_ALIAS_FAIL_NO_ALIAS, INVALID_ARG, ACCS_CHECK_ERROR, AGOO_NOT_BIND, m3818a(123, "accs 错误信息").solution("格式EAGOO_ACCS_123, 123为accs错误码，请结合accs错误码排查").build(), m3819a("XXX", "服务错误信息").solution("格式EAGOO_SERVER_XXX, XXX为agoo服务错误码，请联系阿里云技术支持排查").build()};

    /* renamed from: a */
    public static ErrorBuilder m3818a(int i, String str) {
        return f3575a.defineError("ACCS", String.valueOf(i)).msg(str).solution("accs底层错误，需要根据错误码排查");
    }

    /* renamed from: a */
    public static ErrorBuilder m3819a(String str, String str2) {
        return f3575a.defineServerError(str).msg(str2).solution("agoo 服务报错，请联系技术支持排查");
    }
}
