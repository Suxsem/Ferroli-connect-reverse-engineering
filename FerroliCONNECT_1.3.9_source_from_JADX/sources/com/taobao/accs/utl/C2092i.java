package com.taobao.accs.utl;

import com.taobao.accs.utl.C2097k;

/* renamed from: com.taobao.accs.utl.i */
/* compiled from: Taobao */
public class C2092i implements C2097k.C2098a {

    /* renamed from: a */
    private C2095c[] f3558a;

    /* renamed from: b */
    private final int f3559b;

    /* renamed from: c */
    private String[] f3560c;

    /* renamed from: d */
    private long[] f3561d;

    /* renamed from: e */
    private int f3562e;

    /* renamed from: com.taobao.accs.utl.i$c */
    /* compiled from: Taobao */
    interface C2095c {
        /* renamed from: a */
        boolean mo18605a(String str);
    }

    /* renamed from: com.taobao.accs.utl.i$b */
    /* compiled from: Taobao */
    private static class C2094b {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C2092i f3564a = new C2092i();

        private C2094b() {
        }
    }

    /* renamed from: a */
    public static C2092i m3788a() {
        return C2094b.f3564a;
    }

    private C2092i() {
        this.f3559b = 5;
        this.f3560c = new String[5];
        this.f3561d = new long[5];
        this.f3562e = 0;
        for (int i = 0; i < 5; i++) {
            this.f3560c[i] = null;
            this.f3561d[i] = 0;
        }
        this.f3558a = new C2095c[]{new C2093a("send msg time out"), new C2093a("errorCode::"), new C2093a("errorId::"), new C2093a("TNET_JNI_ERR_LOAD_SO_FAIL")};
    }

    /* renamed from: a */
    public void mo18603a(String str) {
        try {
            if (m3789a(str, this.f3558a)) {
                m3790b(str);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* renamed from: b */
    private void m3790b(String str) {
        int i = this.f3562e % 5;
        this.f3560c[i] = str;
        this.f3561d[i] = System.currentTimeMillis() / 1000;
        this.f3562e = i + 1;
    }

    /* renamed from: a */
    private boolean m3789a(String str, C2095c[] cVarArr) {
        for (C2095c a : cVarArr) {
            if (a.mo18605a(str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b */
    public String mo18604b() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis() / 1000);
        sb.append(" ");
        try {
            int i = ((this.f3562e - 1) % 5) + 5;
            for (int i2 = 0; i2 < 5; i2++) {
                int i3 = (i - i2) % 5;
                if (this.f3560c[i3] == null) {
                    break;
                }
                sb.append(this.f3561d[i3]);
                sb.append(" ");
                sb.append(this.f3560c[i3]);
                sb.append(" ");
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return sb.toString();
    }

    /* renamed from: com.taobao.accs.utl.i$a */
    /* compiled from: Taobao */
    static class C2093a implements C2095c {

        /* renamed from: a */
        private String f3563a;

        public C2093a(String str) {
            this.f3563a = str;
        }

        /* renamed from: a */
        public boolean mo18605a(String str) {
            return str != null && str.contains(this.f3563a);
        }
    }
}
