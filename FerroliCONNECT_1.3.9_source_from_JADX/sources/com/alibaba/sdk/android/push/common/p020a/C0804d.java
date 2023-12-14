package com.alibaba.sdk.android.push.common.p020a;

import com.alibaba.sdk.android.error.CodeGenerator;
import com.alibaba.sdk.android.error.ErrorBuilder;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.error.ErrorDefine;
import com.taobao.agoo.p105a.p106a.C2125b;

/* renamed from: com.alibaba.sdk.android.push.common.a.d */
public class C0804d {

    /* renamed from: A */
    private static final ErrorDefine f1090A = new ErrorDefine("PUSH", new C0806a());

    /* renamed from: a */
    public static final ErrorCode f1091a = f1090A.defineSdkError("00000").msg(C2125b.JSON_SUCCESS).build();

    /* renamed from: b */
    public static final ErrorCode f1092b = f1090A.defineServerError("10101").msg("参数缺失").solution("请检查请求参数是否正确").build();

    /* renamed from: c */
    public static final ErrorCode f1093c = f1090A.defineServerError("10102").msg("参数无效").solution("请检查请求参数是否正确").build();

    /* renamed from: d */
    public static final ErrorCode f1094d = f1090A.defineServerError("10103").msg("服务端签名与客户端不匹配").solution("请检查推送配置是否正确").build();

    /* renamed from: e */
    public static final ErrorCode f1095e = f1090A.defineServerError("10104").msg("Tag相关错误").solution("请根据具体错误信息排查，如果不能解决，请联系阿里云技术支持").build();

    /* renamed from: f */
    public static final ErrorCode f1096f = f1090A.defineServerError("10105").msg("Alias相关错误").solution("请根据具体错误信息排查，如果不能解决，请联系阿里云技术支持").build();

    /* renamed from: g */
    public static final ErrorCode f1097g = f1090A.defineServerError("10106").msg("服务端内部错误").solution("请根据具体错误信息联系阿里云技术支持").build();

    /* renamed from: h */
    public static final ErrorCode f1098h = f1090A.defineAndroidError("10107").msg("网络IO错误").solution("请检查网络是否可用").solution("请根据具体错误信息排查，如果不能解决，请联系阿里云技术支持").build();

    /* renamed from: i */
    public static final ErrorCode f1099i = f1090A.defineSdkError("10108").msg("返回结果解析错误").solution("请保留具体错误信息，联系阿里云技术支持排查").build();

    /* renamed from: j */
    public static final ErrorCode f1100j = f1090A.defineSdkError("10109").msg("网络连接失败,请检查网络配置").solution("请检查网络是否可用").build();

    /* renamed from: k */
    public static final ErrorCode f1101k = f1090A.defineSdkError("10114").msg("内部错误").solution("请保留具体错误信息，联系阿里云技术支持排查").build();

    /* renamed from: l */
    public static final ErrorCode f1102l = f1090A.defineSdkError("10115").msg("通道注册状态异常").solution("请保留具体错误信息，联系阿里云技术支持排查").build();

    /* renamed from: m */
    public static final ErrorCode f1103m = f1090A.defineServerError("10118").msg("其它接口错误").solution("请根据具体错误信息联系阿里云技术支持").build();

    /* renamed from: n */
    public static final ErrorCode f1104n = f1090A.defineSdkError("10119").msg("非主进程不用初始化").solution("在非主进程执行初始化时触发，可以忽略").build();

    /* renamed from: o */
    public static final ErrorCode f1105o = f1090A.defineSdkError("10120").msg("推送注册超时").solution("请保留具体错误信息，联系阿里云技术支持排查").build();

    /* renamed from: p */
    public static final ErrorCode f1106p = f1090A.defineAndroidError("10121").msg("网络请求失败，请检查网络是否可用").solution("请检查网络是否可用").solution("请根据具体错误信息排查，如果不能解决，请联系阿里云技术支持").build();

    /* renamed from: q */
    public static final ErrorCode f1107q = f1090A.defineSdkError("20101").msg("参数输入非法").solution("请检查请求的输入参数是否正确").build();

    /* renamed from: r */
    public static final ErrorCode f1108r = f1090A.defineSdkError("20102").msg("静默连接进程名设置错误,进程名不能为空且必须与manifest文件配置相符。系统自动设置为manifest所配置进程名").solution("开启debug会检查此错误，目前进程名不支持修改，请不要修改组件进程配置").build();

    /* renamed from: s */
    public static final ErrorCode f1109s = f1090A.defineSdkError("20103").msg("appversion参数错误,请检查您的版本号,版本号不能为null或长度不能超过32位").solution("开启debug会检查此错误，请检查应用版本号是否过长").build();

    /* renamed from: t */
    public static final ErrorCode f1110t = f1090A.defineSdkError("20105").msg("ChannelService未设置辅助进程").solution("开启debug会检查此错误，如果不是特殊场景，请检查是否修改了推送组件的进程配置").build();

    /* renamed from: u */
    public static final ErrorCode f1111u = f1090A.defineSdkError("20106").msg("核心组件未配置").solution("开启debug会检查此错误，请检查是否删除了推送组件的声明").build();

    /* renamed from: v */
    public static final ErrorCode f1112v = f1090A.defineSdkError("20107").msg("连续crash，推送服务关闭").solution("应用初始化推送后崩溃，会在下次启动关闭推送服务。请检查应用的崩溃记录").solution("开发测试场景下，人为触发的，请清除应用数据恢复").solution("线上场景会尝试自动恢复，如果仍然崩溃，需要升级应用版本才会恢复").build();

    /* renamed from: w */
    public static final ErrorCode f1113w = f1090A.defineSdkError("20108").msg("未初始化，请先调用 PushServiceFactory的init方法").solution("请确认是否正常初始化").build();

    /* renamed from: x */
    public static final ErrorCode f1114x = f1090A.defineSdkError("20109").msg("废弃接口").solution("请查看文档，使用合适的api").build();

    /* renamed from: y */
    public static final ErrorCode f1115y = f1090A.defineSdkError("20110").msg("已经调用注册，重复调用无效").solution("register方法如果失败了，会自动重试，一般情况下不需要重复调用").solution("如果希望内部重试失败的情况，由外部重新调用register，请至少在上一次register失败回调两次（确认内部重试还是失败）的情况下，先调用PushControlService的reset方法，然后再调用下一次register方法").build();

    /* renamed from: z */
    public static final ErrorCode[] f1116z = {f1091a, f1092b, f1093c, f1094d, f1095e, f1096f, f1097g, f1098h, f1099i, f1100j, f1101k, f1102l, f1103m, f1104n, f1105o, f1106p, f1107q, f1108r, f1109s, f1110t, f1111u, f1112v, f1113w, f1114x, f1115y, m774a(123, "accs错误信息").solution("格式ACCS_123, 123为accs错误码，请结合accs错误码排查").build(), m775a("xxx", "agoo错误信息").solution("格式AGOO_xxx, xxx为agoo错误码，请结合agoo错误码排查").build()};

    /* renamed from: com.alibaba.sdk.android.push.common.a.d$a */
    private static class C0806a extends CodeGenerator {
        private C0806a() {
        }

        public String generateCodeStr(String str, String str2, String str3) {
            return str + "_" + str3;
        }
    }

    /* renamed from: a */
    public static ErrorBuilder m774a(int i, String str) {
        ErrorDefine errorDefine = f1090A;
        return errorDefine.defineSdkError("ACCS_" + i).msg(str);
    }

    /* renamed from: a */
    public static ErrorBuilder m775a(String str, String str2) {
        return f1090A.defineSdkError(str).msg(str2);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.sdk.android.error.ErrorCode m776b(java.lang.String r2, java.lang.String r3) {
        /*
            int r0 = r2.hashCode()
            switch(r0) {
                case -1693386453: goto L_0x0044;
                case -996611353: goto L_0x003a;
                case -723241298: goto L_0x0030;
                case -265907281: goto L_0x0026;
                case 2524: goto L_0x001c;
                case 39557560: goto L_0x0012;
                case 677129462: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x004e
        L_0x0008:
            java.lang.String r0 = "InvalidParam"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 2
            goto L_0x004f
        L_0x0012:
            java.lang.String r0 = "AliasError"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 5
            goto L_0x004f
        L_0x001c:
            java.lang.String r0 = "OK"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 0
            goto L_0x004f
        L_0x0026:
            java.lang.String r0 = "SignNotMatch"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 3
            goto L_0x004f
        L_0x0030:
            java.lang.String r0 = "TagError"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 4
            goto L_0x004f
        L_0x003a:
            java.lang.String r0 = "MissingParam"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 1
            goto L_0x004f
        L_0x0044:
            java.lang.String r0 = "InternalError"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x004e
            r0 = 6
            goto L_0x004f
        L_0x004e:
            r0 = -1
        L_0x004f:
            switch(r0) {
                case 0: goto L_0x008f;
                case 1: goto L_0x008c;
                case 2: goto L_0x0089;
                case 3: goto L_0x0086;
                case 4: goto L_0x0083;
                case 5: goto L_0x0080;
                case 6: goto L_0x0075;
                default: goto L_0x0052;
            }
        L_0x0052:
            com.alibaba.sdk.android.error.ErrorCode r0 = f1103m
            com.alibaba.sdk.android.error.ErrorBuilder r0 = r0.copy()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            java.lang.String r2 = ":"
            r1.append(r2)
            r1.append(r3)
            java.lang.String r2 = r1.toString()
            com.alibaba.sdk.android.error.ErrorBuilder r2 = r0.msg(r2)
        L_0x0070:
            com.alibaba.sdk.android.error.ErrorCode r2 = r2.build()
            return r2
        L_0x0075:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1097g
        L_0x0077:
            com.alibaba.sdk.android.error.ErrorBuilder r2 = r2.copy()
            com.alibaba.sdk.android.error.ErrorBuilder r2 = r2.msg(r3)
            goto L_0x0070
        L_0x0080:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1096f
            goto L_0x0077
        L_0x0083:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1095e
            goto L_0x0077
        L_0x0086:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1094d
            goto L_0x0077
        L_0x0089:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1093c
            goto L_0x0077
        L_0x008c:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1092b
            goto L_0x0077
        L_0x008f:
            com.alibaba.sdk.android.error.ErrorCode r2 = f1091a
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.push.common.p020a.C0804d.m776b(java.lang.String, java.lang.String):com.alibaba.sdk.android.error.ErrorCode");
    }
}
