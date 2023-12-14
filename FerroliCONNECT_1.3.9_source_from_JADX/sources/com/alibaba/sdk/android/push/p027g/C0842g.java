package com.alibaba.sdk.android.push.p027g;

import android.content.Context;
import anetwork.channel.util.RequestConstant;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p009a.C0669a;
import com.alibaba.sdk.android.ams.common.p010b.C0672b;
import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.common.util.C0813b;
import com.alibaba.sdk.android.push.common.util.p021a.C0812d;
import com.alibaba.sdk.android.push.p018b.C0794c;
import com.alibaba.sdk.android.push.p018b.C0795d;
import com.alibaba.sdk.android.push.p023d.C0817a;
import com.alibaba.sdk.android.push.p027g.C0840f;
import com.taobao.accs.common.Constants;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.push.g.g */
public class C0842g {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static AmsLogger f1214a = AmsLogger.getLogger("MPS:VipRequestManager");

    /* renamed from: b */
    private static C0842g f1215b = null;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static Context f1216c;

    /* renamed from: d */
    private C0840f f1217d = new C0840f();

    private C0842g() {
    }

    /* renamed from: a */
    public static C0842g m861a() {
        if (f1215b == null) {
            f1215b = new C0842g();
        }
        return f1215b;
    }

    /* renamed from: a */
    private String m862a(int i) {
        C0840f fVar = this.f1217d;
        C0840f.C0841a a = fVar != null ? fVar.mo9927a(i) : null;
        if (a == null) {
            return null;
        }
        return a.mo9929a();
    }

    /* renamed from: a */
    private Map<String, String> m863a(String str, String str2, String[] strArr, Map<String, String> map) {
        String str3;
        String f;
        String str4 = "deviceId";
        if (str.equals(str4)) {
            f = m879e();
            if (StringUtil.isEmpty(f)) {
                throw new C0794c("deviceId is empty.");
            }
        } else {
            str4 = "account";
            if (str.equals(str4)) {
                f = m880f();
                if (StringUtil.isEmpty(f)) {
                    throw new C0794c("account is empty");
                }
            } else {
                if (str.equals("alias")) {
                    if (!StringUtil.isEmpty(str2)) {
                        map.put("alias", str2);
                    } else {
                        throw new C0794c("alias is empty");
                    }
                } else if (str.equals("tags")) {
                    if (strArr != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < strArr.length; i++) {
                            if (i == strArr.length - 1 || StringUtil.isEmpty(strArr[i])) {
                                if (i == strArr.length - 1 && !StringUtil.isEmpty(strArr[i])) {
                                    str3 = strArr[i];
                                }
                            } else {
                                sb.append(strArr[i]);
                                str3 = ",";
                            }
                            sb.append(str3);
                        }
                        if (!StringUtil.isEmpty(sb.toString())) {
                            map.put("tags", sb.toString());
                        } else {
                            throw new C0794c("tags array is empty");
                        }
                    } else {
                        throw new C0794c("tags array is empty");
                    }
                }
                return map;
            }
        }
        map.put(str4, f);
        return map;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m864a(int i, String str) {
        C0840f fVar = this.f1217d;
        if (fVar != null) {
            fVar.mo9928a(i, str);
        }
    }

    /* renamed from: a */
    public static void m865a(Context context) {
        f1216c = context;
        if (f1215b == null) {
            f1215b = m861a();
        }
    }

    /* renamed from: a */
    private void m866a(C0794c cVar, String str, CommonCallback commonCallback) {
        m875a((Throwable) cVar, str, commonCallback);
    }

    /* renamed from: a */
    private void m867a(C0795d dVar, String str, CommonCallback commonCallback) {
        m875a((Throwable) dVar, str, commonCallback);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m872a(String str) {
        C0673c.m489a().mo9527a("mps_account", str);
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m873a(String str, long j) {
        C0817a a = C0817a.m791a();
        C0672b a2 = C0673c.m489a();
        if (a != null && a2 != null) {
            a.mo9897a(str, a2.mo9528b(), j);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m874a(String str, String str2, String str3) {
        C0817a a = C0817a.m791a();
        C0672b a2 = C0673c.m489a();
        if (a != null && a2 != null) {
            a.mo9899a(str, str2, a2.mo9528b(), str3);
        }
    }

    /* renamed from: a */
    private void m875a(Throwable th, String str, CommonCallback commonCallback) {
        ErrorCode build = C0804d.f1107q.copy().msg(th.getMessage()).build();
        AmsLogger amsLogger = f1214a;
        amsLogger.mo9542e(str + " Fail: errorCode:" + build, th);
        if (commonCallback != null) {
            commonCallback.onFailed(build.getCode(), build.getMsg());
        }
        m874a(build.getCode(), build.getMsg(), str);
    }

    /* renamed from: c */
    private static boolean m877c(Context context) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(C0813b.m788a(context, "KEY_LAUNCH_MARK"));
        Calendar instance2 = Calendar.getInstance();
        return instance.get(6) == instance2.get(6) && instance.get(1) == instance2.get(1);
    }

    /* renamed from: e */
    private String m879e() {
        return C0673c.m489a().mo9528b();
    }

    /* renamed from: f */
    private String m880f() {
        return C0673c.m489a().mo9531c("mps_account");
    }

    /* renamed from: g */
    private Map<String, String> m881g() {
        String b = mo9935b();
        HashMap hashMap = new HashMap();
        hashMap.put(Constants.KEY_APP_KEY, b);
        hashMap.put(Constants.KEY_OS_VERSION, "2");
        hashMap.put(Constants.SP_KEY_VERSION, "3.7.6");
        return hashMap;
    }

    /* renamed from: a */
    public void mo9931a(int i, CommonCallback commonCallback) {
        String a;
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("listTags");
        if (1 != i || (a = m862a(2)) == null) {
            try {
                Context context = f1216c;
                final int i2 = i;
                final CommonCallback commonCallback2 = commonCallback;
                C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/list-tag", new CommonCallback() {
                    public void onFailed(String str, String str2) {
                        C0842g.this.m874a(str, str2, "/list-tag");
                        CommonCallback commonCallback = commonCallback2;
                        if (commonCallback != null) {
                            commonCallback.onFailed(str, str2);
                        }
                    }

                    public void onSuccess(String str) {
                        C0842g.this.m873a("/list-tag", System.currentTimeMillis() - currentTimeMillis);
                        if (1 == i2) {
                            C0842g.f1214a.mo9538d("store cache");
                            C0842g.this.m864a(2, str);
                        }
                        CommonCallback commonCallback = commonCallback2;
                        if (commonCallback != null) {
                            commonCallback.onSuccess(str);
                        }
                    }
                });
                Map<String, String> g = m881g();
                if (i == 1) {
                    Map<String, String> a2 = m863a("deviceId", (String) null, (String[]) null, g);
                    a2.put(Constants.KEY_TARGET, String.valueOf(i));
                    String str = C0812d.f1148u;
                    a2.put(str, C0812d.LIST_TAGS.mo9894a() + "");
                    hVar.execute(new Map[]{a2});
                    return;
                }
                throw new C0795d("target is invalid.");
            } catch (C0794c e) {
                m866a(e, "/list-tag", commonCallback);
            } catch (C0795d e2) {
                m867a(e2, "/list-tag", commonCallback);
            }
        } else {
            f1214a.mo9538d("get from cache");
            if (commonCallback != null) {
                commonCallback.onSuccess(a);
            }
        }
    }

    /* renamed from: a */
    public void mo9932a(int i, String[] strArr, String str, final CommonCallback commonCallback) {
        Map<String, String> a;
        String str2;
        String str3;
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/bind-tag", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/bind-tag");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/bind-tag", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            if (strArr == null || strArr.length == 0) {
                throw new C0795d("tags is empty.");
            }
            Map<String, String> g = m881g();
            if (i == 1) {
                f1214a.mo9538d("Binding tag to device.");
                a = m863a("deviceId", (String) null, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.BIND_TAG_TO_DEVICE.mo9894a() + "";
            } else if (i == 2) {
                f1214a.mo9538d("Binding tag to account.");
                a = m863a("account", (String) null, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.BIND_TAG_TO_ACCOUNT.mo9894a() + "";
            } else if (i == 3) {
                a = m863a("alias", str, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.BIND_TAG_TO_ALIAS.mo9894a() + "";
            } else {
                throw new C0795d("target is invalid.");
            }
            a.put(str2, str3);
            Map<String, String> a2 = m863a("tags", (String) null, strArr, a);
            a2.put(Constants.KEY_TARGET, String.valueOf(i));
            hVar.execute(new Map[]{a2});
        } catch (C0794c e) {
            m866a(e, "/bind-tag", commonCallback);
        } catch (C0795d e2) {
            m867a(e2, "/bind-tag", commonCallback);
        }
    }

    /* renamed from: a */
    public void mo9933a(final CommonCallback commonCallback) {
        f1214a.mo9538d("unbinding account");
        final long currentTimeMillis = System.currentTimeMillis();
        Context context = f1216c;
        C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/unbind-account", new CommonCallback() {
            public void onFailed(String str, String str2) {
                C0842g.this.m874a(str, str2, "/unbind-account");
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onFailed(str, str2);
                }
            }

            public void onSuccess(String str) {
                C0842g.this.m872a("");
                C0842g.this.m873a("/unbind-account", System.currentTimeMillis() - currentTimeMillis);
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onSuccess(str);
                }
            }
        });
        try {
            Map<String, String> g = m881g();
            g.put("account", "");
            g.put(C0812d.f1148u, String.valueOf(C0812d.UNBIND_ACCOUNT.mo9894a()));
            hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
        } catch (C0794c e) {
            m866a(e, "/unbind-account", commonCallback);
        }
    }

    /* renamed from: a */
    public void mo9934a(String str, CommonCallback commonCallback) {
        AmsLogger amsLogger = f1214a;
        amsLogger.mo9538d("binding account" + str);
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            Context context = f1216c;
            final String str2 = str;
            final CommonCallback commonCallback2 = commonCallback;
            C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/bind-account", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/bind-account");
                    CommonCallback commonCallback = commonCallback2;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m872a(str2);
                    C0842g.this.m873a("/bind-account", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback2;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> g = m881g();
            if (!StringUtil.isEmpty(str)) {
                g.put("account", str);
                g.put(C0812d.f1148u, String.valueOf(C0812d.BIND_ACCOUNT.mo9894a()));
                hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
                return;
            }
            throw new C0795d("account input is empty!");
        } catch (C0794c e) {
            m866a(e, "/bind-account", commonCallback);
        } catch (C0795d e2) {
            m867a(e2, "/bind-account", commonCallback);
        }
    }

    /* renamed from: b */
    public String mo9935b() {
        return C0673c.m489a().mo9524a();
    }

    /* renamed from: b */
    public void mo9936b(int i, String[] strArr, String str, final CommonCallback commonCallback) {
        Map<String, String> a;
        String str2;
        String str3;
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/unbind-tag", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/unbind-tag");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    if (commonCallback != null) {
                        C0842g.this.m873a("/unbind-tag", System.currentTimeMillis() - currentTimeMillis);
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> g = m881g();
            if (i == 1) {
                f1214a.mo9538d("Unbinding tag from device.");
                a = m863a("deviceId", (String) null, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.UNBIND_TAG_TO_DEVICE.mo9894a() + "";
            } else if (i == 2) {
                f1214a.mo9538d("Unbinding tag from account.");
                a = m863a("account", (String) null, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.UNBIND_TAG_TO_ACCOUNT.mo9894a() + "";
            } else if (i == 3) {
                f1214a.mo9538d("Unbinding tag from alias.");
                a = m863a("alias", str, (String[]) null, g);
                str2 = C0812d.f1148u;
                str3 = C0812d.UNBIND_TAG_TO_ALIAS.mo9894a() + "";
            } else {
                throw new C0795d("target is invalid.");
            }
            a.put(str2, str3);
            Map<String, String> a2 = m863a("tags", (String) null, strArr, a);
            a2.put(Constants.KEY_TARGET, String.valueOf(i));
            hVar.execute(new Map[]{a2});
        } catch (C0794c e) {
            m866a(e, "/unbind-tag", commonCallback);
        } catch (C0795d e2) {
            m867a(e2, "/unbind-tag", commonCallback);
        }
    }

    /* renamed from: b */
    public void mo9937b(Context context) {
        if (m877c(context)) {
            f1214a.mo9541e("onAppStart has already sent today");
            return;
        }
        f1214a.mo9538d("onAppStart");
        Context context2 = f1216c;
        C0857h hVar = new C0857h(context2, "https://" + C0669a.m469d() + "/active", new CommonCallback() {
            public void onFailed(String str, String str2) {
                AmsLogger c = C0842g.f1214a;
                c.mo9541e("onAppStart failed. errorCode:" + str + " errorMsg:" + str2);
            }

            public void onSuccess(String str) {
                C0842g.f1214a.mo9538d("onAppStart success");
                try {
                    C0813b.m789a(C0842g.f1216c, "KEY_LAUNCH_MARK", System.currentTimeMillis());
                } catch (Throwable th) {
                    C0842g.f1214a.mo9542e("onAppStart success", th);
                }
            }
        });
        try {
            Map<String, String> g = m881g();
            g.put(C0812d.f1148u, String.valueOf(C0812d.ON_APP_START.mo9894a()));
            hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
        } catch (C0794c e) {
            m866a(e, "/active", (CommonCallback) null);
        }
    }

    /* renamed from: b */
    public void mo9938b(final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("listAliases");
        String a = m862a(1);
        if (a != null) {
            f1214a.mo9538d("get from cache");
            if (commonCallback != null) {
                commonCallback.onSuccess(a);
                return;
            }
            return;
        }
        try {
            Context context = f1216c;
            C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/list-alias", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/list-alias");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.f1214a.mo9538d("store cache");
                    C0842g.this.m864a(1, str);
                    C0842g.this.m873a("/list-alias", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> g = m881g();
            String str = C0812d.f1148u;
            g.put(str, C0812d.LIST_ALIASES.mo9894a() + "");
            hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
        } catch (C0794c e) {
            m866a(e, "/list-alias", commonCallback);
        }
    }

    /* renamed from: b */
    public void mo9939b(String str, final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("Adding alias to device");
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/add-alias", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/add-alias");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/add-alias", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> a = m863a("alias", str, (String[]) null, m863a("deviceId", (String) null, (String[]) null, m881g()));
            a.put(C0812d.f1148u, String.valueOf(C0812d.BIND_ALIAS.mo9894a()));
            hVar.execute(new Map[]{a});
        } catch (C0794c e) {
            m866a(e, "/add-alias", commonCallback);
        }
    }

    /* renamed from: c */
    public void mo9940c(final CommonCallback commonCallback) {
        f1214a.mo9538d("check vip push status");
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/push-status", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/push-status");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/push-status", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> a = m863a("deviceId", (String) null, (String[]) null, m881g());
            a.put(C0812d.f1148u, String.valueOf(C0812d.CHECK_PUSH_STATUS.mo9894a()));
            hVar.execute(new Map[]{a});
        } catch (C0794c e) {
            m866a(e, "/push-status", commonCallback);
        }
    }

    /* renamed from: c */
    public void mo9941c(String str, final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("Removing alias from device");
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/remove-alias", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/remove-alias");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/remove-alias", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> a = m863a("deviceId", (String) null, (String[]) null, m881g());
            if (StringUtil.isEmpty(str)) {
                str = "";
            }
            a.put("alias", str);
            a.put(C0812d.f1148u, String.valueOf(C0812d.UNBIND_ALIAS.mo9894a()));
            hVar.execute(new Map[]{a});
        } catch (C0794c e) {
            m866a(e, "/remove-alias", commonCallback);
        }
    }

    /* renamed from: d */
    public void mo9942d(final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("unbinding vip");
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/push-switch", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/push-switch");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/push-switch", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> a = m863a("deviceId", (String) null, (String[]) null, m881g());
            a.put(C0812d.f1148u, String.valueOf(C0812d.TURN_OFF_PUSH.mo9894a()));
            a.put("enable", RequestConstant.FALSE);
            hVar.execute(new Map[]{a});
        } catch (C0794c e) {
            m866a(e, "/push-switch false", commonCallback);
        }
    }

    /* renamed from: d */
    public void mo9943d(String str, final CommonCallback commonCallback) {
        AmsLogger amsLogger = f1214a;
        amsLogger.mo9538d("binding phoneNumber:" + str);
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            Context context = f1216c;
            C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/set-phone", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/set-phone");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/set-phone", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> g = m881g();
            if (!StringUtil.isEmpty(str)) {
                g.put("mob", str);
                g.put(C0812d.f1148u, String.valueOf(C0812d.BIND_PHONE_NUMBER.mo9894a()));
                hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
                return;
            }
            throw new C0795d("account input is empty!");
        } catch (C0794c e) {
            m866a(e, "/set-phone", commonCallback);
        } catch (C0795d e2) {
            m867a(e2, "/set-phone", commonCallback);
        }
    }

    /* renamed from: e */
    public void mo9944e(final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("binding vip push");
        try {
            C0857h hVar = new C0857h(f1216c, "https://" + C0669a.m469d() + "/push-switch", new CommonCallback() {
                public void onFailed(String str, String str2) {
                    C0842g.this.m874a(str, str2, "/push-switch");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onFailed(str, str2);
                    }
                }

                public void onSuccess(String str) {
                    C0842g.this.m873a("/push-switch", System.currentTimeMillis() - currentTimeMillis);
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                    }
                }
            });
            Map<String, String> a = m863a("deviceId", (String) null, (String[]) null, m881g());
            a.put(C0812d.f1148u, String.valueOf(C0812d.TURN_ON_PUSH.mo9894a()));
            a.put("enable", RequestConstant.TRUE);
            hVar.execute(new Map[]{a});
        } catch (C0794c e) {
            m866a(e, "/push-switch true", commonCallback);
        }
    }

    /* renamed from: f */
    public void mo9945f(final CommonCallback commonCallback) {
        final long currentTimeMillis = System.currentTimeMillis();
        f1214a.mo9538d("unbinding phone number");
        Context context = f1216c;
        C0857h hVar = new C0857h(context, "https://" + C0669a.m469d() + "/unset-phone", new CommonCallback() {
            public void onFailed(String str, String str2) {
                C0842g.this.m874a(str, str2, "/unset-phone");
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onFailed(str, str2);
                }
            }

            public void onSuccess(String str) {
                C0842g.this.m873a("/unset-phone", System.currentTimeMillis() - currentTimeMillis);
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onSuccess(str);
                }
            }
        });
        try {
            Map<String, String> g = m881g();
            g.put(C0812d.f1148u, String.valueOf(C0812d.UNBIND_PHONE_NUMBER.mo9894a()));
            hVar.execute(new Map[]{m863a("deviceId", (String) null, (String[]) null, g)});
        } catch (C0794c e) {
            m866a(e, "/unset-phone", commonCallback);
        }
    }
}
