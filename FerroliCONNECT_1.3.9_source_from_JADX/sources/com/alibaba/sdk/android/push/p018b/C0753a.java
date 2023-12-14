package com.alibaba.sdk.android.push.p018b;

import android.content.Context;
import android.graphics.Bitmap;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import com.alibaba.sdk.android.error.ErrorCode;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.common.p020a.C0803c;
import com.alibaba.sdk.android.push.common.p020a.C0804d;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.alibaba.sdk.android.push.p027g.C0842g;
import com.p108ut.device.UTDevice;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.TaobaoRegister;

/* renamed from: com.alibaba.sdk.android.push.b.a */
public class C0753a {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static AmsLogger f967b = AmsLogger.getLogger("MPS:CloudPushService");
    /* access modifiers changed from: private */

    /* renamed from: a */
    public Context f968a;

    public C0753a(Context context) {
        this.f968a = context;
        C0842g.m865a(context);
    }

    /* renamed from: a */
    public String mo9818a() {
        return C0673c.m489a().mo9528b();
    }

    /* renamed from: a */
    public String mo9819a(Context context) {
        return UTDevice.getUtdid(context);
    }

    /* renamed from: a */
    public void mo9820a(int i) {
        C0803c.m770a(i);
    }

    /* renamed from: a */
    public void mo9821a(int i, int i2, int i3, int i4, final CommonCallback commonCallback) {
        AmsLogger amsLogger = f967b;
        amsLogger.mo9538d("setDoNotDisturb " + i + ":" + i2 + "-" + i3 + ":" + i4);
        TaobaoRegister.setDoNotDisturb(i, i2, i3, i4, new com.aliyun.ams.emas.push.CommonCallback() {
            public void onFailed(String str, String str2) {
                ErrorCode build = C0804d.m775a(str, str2).build();
                commonCallback.onFailed(build.getCode(), build.getMsg());
            }

            public void onSuccess(String str) {
                commonCallback.onSuccess(str);
            }
        });
    }

    /* renamed from: a */
    public void mo9822a(int i, CommonCallback commonCallback) {
        C0842g.m861a().mo9931a(i, commonCallback);
    }

    /* renamed from: a */
    public void mo9823a(int i, String[] strArr, String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9932a(i, strArr, str, commonCallback);
    }

    /* renamed from: a */
    public void mo9824a(Bitmap bitmap) {
        C0803c.m771a(bitmap);
    }

    /* renamed from: a */
    public void mo9825a(CommonCallback commonCallback) {
        C0842g.m861a().mo9933a(commonCallback);
    }

    /* renamed from: a */
    public void mo9826a(CPushMessage cPushMessage) {
        TaobaoRegister.clickMessage(CPushMessage.m904to(cPushMessage));
    }

    /* renamed from: a */
    public void mo9827a(Class cls) {
        TaobaoRegister.setPushMsgReceiveService(cls);
    }

    /* renamed from: a */
    public void mo9828a(String str) {
        C0803c.m772a(str);
    }

    /* renamed from: a */
    public void mo9829a(String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9934a(str, commonCallback);
    }

    /* renamed from: a */
    public void mo9830a(boolean z) {
        TaobaoRegister.setDoNotDisturbMode(z);
    }

    /* renamed from: b */
    public void mo9831b() {
        TaobaoRegister.clearNotificationCreatedByAliyun(this.f968a);
    }

    /* renamed from: b */
    public void mo9832b(int i, String[] strArr, String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9936b(i, strArr, str, commonCallback);
    }

    /* renamed from: b */
    public void mo9833b(Context context) {
        C0842g.m861a().mo9937b(context);
    }

    /* renamed from: b */
    public void mo9834b(CommonCallback commonCallback) {
        C0842g.m861a().mo9938b(commonCallback);
    }

    /* renamed from: b */
    public void mo9835b(CPushMessage cPushMessage) {
        TaobaoRegister.dismissMessage(CPushMessage.m904to(cPushMessage));
    }

    /* renamed from: b */
    public void mo9836b(String str) {
        C0673c.m489a().mo9533d(str);
    }

    /* renamed from: b */
    public void mo9837b(String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9939b(str, commonCallback);
    }

    /* renamed from: b */
    public void mo9838b(boolean z) {
        C0803c.m773a(z);
    }

    /* renamed from: c */
    public void mo9839c(CommonCallback commonCallback) {
        C0842g.m861a().mo9945f(commonCallback);
    }

    /* renamed from: c */
    public void mo9840c(String str) {
        C0673c.m489a().mo9535e(str);
    }

    /* renamed from: c */
    public void mo9841c(String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9941c(str, commonCallback);
    }

    /* renamed from: d */
    public void mo9842d(final CommonCallback commonCallback) {
        mo9845f(new CommonCallback() {
            public void onFailed(String str, String str2) {
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onFailed(str, str2);
                }
            }

            public void onSuccess(String str) {
                if (str.equals("off")) {
                    C0753a.f967b.mo9538d("already off. return");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                        return;
                    }
                    return;
                }
                TaobaoRegister.unbindAgoo(C0753a.this.f968a, new ICallback() {
                    public void onFailure(String str, String str2) {
                        ErrorCode build = C0804d.m775a(str, str2).detail("turnOffPushChannel unbindAgoo").build();
                        if (commonCallback != null) {
                            commonCallback.onFailed(build.getCode(), build.getMsg());
                        }
                    }

                    public void onSuccess() {
                        C0842g.m861a().mo9942d(new CommonCallback() {
                            public void onFailed(final String str, final String str2) {
                                TaobaoRegister.bindAgoo(C0753a.this.f968a, new ICallback() {
                                    public void onFailure(String str, String str2) {
                                        ErrorCode build = C0804d.m775a(str, str2).detail("turnOffPushChannel bindAgoo").build();
                                        if (commonCallback != null) {
                                            commonCallback.onFailed(build.getCode(), build.getMsg());
                                        }
                                    }

                                    public void onSuccess() {
                                        if (commonCallback != null) {
                                            commonCallback.onFailed(str, str2);
                                        }
                                    }
                                });
                            }

                            public void onSuccess(String str) {
                                if (commonCallback != null) {
                                    commonCallback.onSuccess(str);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /* renamed from: d */
    public void mo9843d(String str, CommonCallback commonCallback) {
        C0842g.m861a().mo9943d(str, commonCallback);
    }

    /* renamed from: e */
    public void mo9844e(final CommonCallback commonCallback) {
        mo9845f(new CommonCallback() {
            public void onFailed(String str, String str2) {
                CommonCallback commonCallback = commonCallback;
                if (commonCallback != null) {
                    commonCallback.onFailed(str, str2);
                }
            }

            public void onSuccess(String str) {
                if (str.equals("on")) {
                    C0753a.f967b.mo9538d("already on. return");
                    CommonCallback commonCallback = commonCallback;
                    if (commonCallback != null) {
                        commonCallback.onSuccess(str);
                        return;
                    }
                    return;
                }
                TaobaoRegister.bindAgoo(C0753a.this.f968a, new ICallback() {
                    public void onFailure(String str, String str2) {
                        ErrorCode build = C0804d.m775a(str, str2).detail("turnOnPushChannel bindAgoo").build();
                        if (commonCallback != null) {
                            commonCallback.onFailed(build.getCode(), build.getMsg());
                        }
                    }

                    public void onSuccess() {
                        C0842g.m861a().mo9944e(new CommonCallback() {
                            public void onFailed(final String str, final String str2) {
                                TaobaoRegister.unbindAgoo(C0753a.this.f968a, new ICallback() {
                                    public void onFailure(String str, String str2) {
                                        ErrorCode build = C0804d.m775a(str, str2).detail("turnOnPushChannel unbindAgoo").build();
                                        if (commonCallback != null) {
                                            commonCallback.onFailed(build.getCode(), build.getMsg());
                                        }
                                    }

                                    public void onSuccess() {
                                        if (commonCallback != null) {
                                            commonCallback.onFailed(str, str2);
                                        }
                                    }
                                });
                            }

                            public void onSuccess(String str) {
                                if (commonCallback != null) {
                                    commonCallback.onSuccess(str);
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    /* renamed from: f */
    public void mo9845f(CommonCallback commonCallback) {
        C0842g.m861a().mo9940c(commonCallback);
    }
}
