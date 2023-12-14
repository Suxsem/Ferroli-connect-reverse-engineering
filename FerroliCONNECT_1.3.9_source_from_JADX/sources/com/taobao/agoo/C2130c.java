package com.taobao.agoo;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.p105a.C2128b;
import com.taobao.agoo.p105a.p106a.C2126c;
import org.android.agoo.common.Config;

/* renamed from: com.taobao.agoo.c */
/* compiled from: Taobao */
final class C2130c extends IAgooAppReceiver {

    /* renamed from: a */
    final /* synthetic */ IRegister f3605a;

    /* renamed from: b */
    final /* synthetic */ Context f3606b;

    /* renamed from: c */
    final /* synthetic */ IACCSManager f3607c;

    /* renamed from: d */
    final /* synthetic */ String f3608d;

    /* renamed from: e */
    final /* synthetic */ String f3609e;

    C2130c(IRegister iRegister, Context context, IACCSManager iACCSManager, String str, String str2) {
        this.f3605a = iRegister;
        this.f3606b = context;
        this.f3607c = iACCSManager;
        this.f3608d = str;
        this.f3609e = str2;
    }

    public void onBindApp(int i, String str, String str2) {
        if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
            onBindApp(i, str2);
        } else if (this.f3605a != null) {
            ErrorCode build = C2122a.m3818a(i, str).detail("bindApp").build();
            this.f3605a.onFailure(build.getCode(), build.getMsg());
        }
    }

    public void onBindApp(int i, String str) {
        try {
            ALog.m3728i("TaobaoRegister", "onBindApp", Constants.KEY_ERROR_CODE, Integer.valueOf(i));
            if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                if (TaobaoRegister.mRequestListener == null) {
                    C2128b unused = TaobaoRegister.mRequestListener = new C2128b(this.f3606b);
                }
                this.f3607c.registerDataListener(this.f3606b, "AgooDeviceCmd", TaobaoRegister.mRequestListener);
                if (!C2128b.f3603b.mo18619b(this.f3606b.getPackageName()) || Config.getDeviceToken(this.f3606b) == null) {
                    byte[] a = C2126c.m3833a(this.f3606b, this.f3608d, this.f3609e);
                    if (a != null) {
                        String sendRequest = this.f3607c.sendRequest(this.f3606b, new ACCSManager.AccsRequest((String) null, "AgooDeviceCmd", a, (String) null));
                        if (TextUtils.isEmpty(sendRequest)) {
                            if (this.f3605a != null) {
                                this.f3605a.onFailure(C2122a.ACCS_CHECK_ERROR.getCode(), C2122a.ACCS_CHECK_ERROR.getMsg());
                            }
                        } else if (this.f3605a != null) {
                            TaobaoRegister.mRequestListener.f3604a.put(sendRequest, this.f3605a);
                        }
                    } else if (this.f3605a != null) {
                        this.f3605a.onFailure(C2122a.REGISTER_DATA_ERROR.getCode(), C2122a.REGISTER_DATA_ERROR.getMsg());
                    }
                } else {
                    ALog.m3728i("TaobaoRegister", "agoo aready Registered return ", new Object[0]);
                    if (this.f3605a != null) {
                        this.f3605a.onSuccess(Config.getDeviceToken(this.f3606b));
                    }
                }
            } else if (this.f3605a != null) {
                ErrorCode build = C2122a.m3818a(i, "no error msg").detail("bindApp").build();
                this.f3605a.onFailure(build.getCode(), build.getMsg());
            }
        } catch (Throwable th) {
            ALog.m3726e("TaobaoRegister", "register onBindApp", th, new Object[0]);
        }
    }

    public String getAppkey() {
        return this.f3608d;
    }
}
