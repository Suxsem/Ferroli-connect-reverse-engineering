package com.taobao.accs.utl;

import com.alibaba.sdk.android.error.ErrorCode;
import com.taobao.accs.AccsErrorCode;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.IAppReceiverV1;
import com.taobao.accs.IAppReceiverV2;
import java.util.Map;

/* renamed from: com.taobao.accs.utl.c */
/* compiled from: Taobao */
public class C2086c extends IAppReceiverV2 {

    /* renamed from: a */
    private IAppReceiver f3553a;

    /* renamed from: b */
    private boolean f3554b = false;

    /* renamed from: a */
    public static IAppReceiver m3773a(IAppReceiver iAppReceiver) {
        if (iAppReceiver == null) {
            return null;
        }
        return new C2086c(iAppReceiver);
    }

    private C2086c(IAppReceiver iAppReceiver) {
        this.f3553a = iAppReceiver;
    }

    public void onData(String str, String str2, byte[] bArr) {
        this.f3553a.onData(str, str2, bArr);
    }

    public void onBindApp(int i) {
        if (!this.f3554b) {
            this.f3554b = true;
            this.f3553a.onBindApp(i);
        }
    }

    public void onBindApp(int i, String str) {
        if (!this.f3554b) {
            this.f3554b = true;
            IAppReceiver iAppReceiver = this.f3553a;
            if (iAppReceiver instanceof IAppReceiverV1) {
                ((IAppReceiverV1) iAppReceiver).onBindApp(i, str);
            } else {
                iAppReceiver.onBindApp(i);
            }
        }
    }

    public void onBindApp(int i, String str, String str2) {
        if (!this.f3554b) {
            if (i == AccsErrorCode.SUCCESS.getCodeInt()) {
                this.f3554b = true;
            }
            IAppReceiver iAppReceiver = this.f3553a;
            if (iAppReceiver instanceof IAppReceiverV2) {
                ((IAppReceiverV2) iAppReceiver).onBindApp(i, str, str2);
            } else if (iAppReceiver instanceof IAppReceiverV1) {
                ((IAppReceiverV1) iAppReceiver).onBindApp(i, str2);
            } else {
                iAppReceiver.onBindApp(i);
            }
        }
    }

    public void onUnbindApp(int i) {
        if (this.f3554b) {
            this.f3554b = false;
            this.f3553a.onUnbindApp(i);
        }
    }

    public void onUnbindApp(int i, String str) {
        if (this.f3554b) {
            this.f3554b = false;
            IAppReceiver iAppReceiver = this.f3553a;
            if (iAppReceiver instanceof IAppReceiverV2) {
                ((IAppReceiverV2) iAppReceiver).onUnbindApp(i, str);
            } else {
                iAppReceiver.onUnbindApp(i);
            }
        }
    }

    public void onBindUser(String str, int i) {
        this.f3553a.onBindUser(str, i);
    }

    public void onBindUser(String str, int i, String str2) {
        IAppReceiver iAppReceiver = this.f3553a;
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onBindUser(str, i, str2);
        } else {
            iAppReceiver.onBindUser(str, i);
        }
    }

    public void onUnbindUser(int i) {
        this.f3553a.onUnbindUser(i);
    }

    public void onUnbindUser(int i, String str) {
        IAppReceiver iAppReceiver = this.f3553a;
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onUnbindUser(i, str);
        } else {
            iAppReceiver.onUnbindUser(i);
        }
    }

    public void onSendData(String str, int i) {
        this.f3553a.onSendData(str, i);
    }

    public String getService(String str) {
        return this.f3553a.getService(str);
    }

    public Map<String, String> getAllServices() {
        return this.f3553a.getAllServices();
    }

    public boolean equals(Object obj) {
        if (obj instanceof C2086c) {
            return this.f3553a.equals(((C2086c) obj).f3553a);
        }
        return this.f3553a.equals(obj);
    }

    public int hashCode() {
        return this.f3553a.hashCode();
    }

    /* renamed from: a */
    public static void m3775a(ErrorCode errorCode, IAppReceiver iAppReceiver, String str) {
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onBindApp(errorCode.getCodeInt(), errorCode.getMsg(), str);
        } else if (iAppReceiver instanceof IAppReceiverV1) {
            ((IAppReceiverV1) iAppReceiver).onBindApp(errorCode.getCodeInt(), str);
        } else {
            iAppReceiver.onBindApp(errorCode.getCodeInt());
        }
    }

    /* renamed from: a */
    public static void m3774a(ErrorCode errorCode, IAppReceiver iAppReceiver) {
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onUnbindApp(errorCode.getCodeInt(), errorCode.getMsg());
        } else {
            iAppReceiver.onUnbindApp(errorCode.getCodeInt());
        }
    }

    /* renamed from: b */
    public static void m3777b(ErrorCode errorCode, IAppReceiver iAppReceiver, String str) {
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onBindUser(str, errorCode.getCodeInt(), errorCode.getMsg());
        } else {
            iAppReceiver.onBindUser(str, errorCode.getCodeInt());
        }
    }

    /* renamed from: b */
    public static void m3776b(ErrorCode errorCode, IAppReceiver iAppReceiver) {
        if (iAppReceiver instanceof IAppReceiverV2) {
            ((IAppReceiverV2) iAppReceiver).onUnbindUser(errorCode.getCodeInt(), errorCode.getMsg());
        } else {
            iAppReceiver.onUnbindUser(errorCode.getCodeInt());
        }
    }
}
