package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.net.C2049b;
import com.taobao.accs.p103ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.C2090g;
import com.taobao.accs.utl.JsonUtility;
import com.taobao.accs.utl.RomInfoCollecter;
import com.taobao.accs.utl.UtilityImpl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.http.HttpTokens;
import org.json.JSONException;

/* compiled from: Taobao */
public class Message {
    public static final int EXT_HEADER_VALUE_MAX_LEN = 1023;
    public static final int FLAG_ACK_TYPE = 32;
    public static final int FLAG_BIZ_RET = 64;
    public static final int FLAG_DATA_TYPE = 32768;
    public static final int FLAG_ERR = 4096;
    public static final int FLAG_REQ_BIT1 = 16384;
    public static final int FLAG_REQ_BIT2 = 8192;
    public static final int FLAG_RET = 2048;
    public static final String KEY_BINDAPP = "ctrl_bindapp";
    public static final String KEY_BINDSERVICE = "ctrl_bindservice";
    public static final String KEY_BINDUSER = "ctrl_binduser";
    public static final String KEY_UNBINDAPP = "ctrl_unbindapp";
    public static final String KEY_UNBINDSERVICE = "ctrl_unbindservice";
    public static final String KEY_UNBINDUSER = "ctrl_unbinduser";
    public static final int MAX_RETRY_TIMES = 3;

    /* renamed from: a */
    public static int f3240a = 5;

    /* renamed from: b */
    static long f3241b = 1;

    /* renamed from: A */
    String f3242A = null;

    /* renamed from: B */
    String f3243B = null;

    /* renamed from: C */
    String f3244C = null;

    /* renamed from: D */
    Integer f3245D = null;

    /* renamed from: E */
    String f3246E = null;

    /* renamed from: F */
    String f3247F = null;

    /* renamed from: G */
    public String f3248G = null;

    /* renamed from: H */
    public String f3249H = null;

    /* renamed from: I */
    String f3250I = null;

    /* renamed from: J */
    String f3251J = null;

    /* renamed from: K */
    String f3252K = null;

    /* renamed from: L */
    String f3253L = null;

    /* renamed from: M */
    String f3254M = null;

    /* renamed from: N */
    byte[] f3255N;

    /* renamed from: O */
    public String f3256O;

    /* renamed from: P */
    int f3257P;

    /* renamed from: Q */
    public long f3258Q = 0;

    /* renamed from: R */
    public int f3259R = 0;

    /* renamed from: S */
    public int f3260S = C2049b.ACCS_RECEIVE_TIMEOUT;

    /* renamed from: T */
    public long f3261T;

    /* renamed from: U */
    long f3262U;

    /* renamed from: V */
    public String f3263V = null;

    /* renamed from: W */
    transient NetPerformanceMonitor f3264W;

    /* renamed from: X */
    String f3265X = null;

    /* renamed from: Y */
    C2024a f3266Y;

    /* renamed from: c */
    public boolean f3267c = false;

    /* renamed from: d */
    public boolean f3268d = false;

    /* renamed from: e */
    public boolean f3269e = false;

    /* renamed from: f */
    public URL f3270f;

    /* renamed from: g */
    byte f3271g = 0;

    /* renamed from: h */
    byte f3272h = 0;

    /* renamed from: i */
    short f3273i;

    /* renamed from: j */
    short f3274j;

    /* renamed from: k */
    short f3275k;

    /* renamed from: l */
    byte f3276l;

    /* renamed from: m */
    byte f3277m;

    /* renamed from: n */
    String f3278n;

    /* renamed from: o */
    String f3279o;

    /* renamed from: p */
    int f3280p = -1;

    /* renamed from: q */
    public String f3281q;

    /* renamed from: r */
    Map<Integer, String> f3282r;

    /* renamed from: s */
    String f3283s = null;

    /* renamed from: t */
    public Integer f3284t = null;

    /* renamed from: u */
    Integer f3285u = 0;

    /* renamed from: v */
    String f3286v = null;

    /* renamed from: w */
    public String f3287w = null;

    /* renamed from: x */
    Integer f3288x = null;

    /* renamed from: y */
    String f3289y = null;

    /* renamed from: z */
    String f3290z = null;

    /* renamed from: com.taobao.accs.data.Message$b */
    /* compiled from: Taobao */
    public static class C2025b {
        public static final int INVALID = -1;
        public static final int NEED_ACK = 1;
        public static final int NO_ACK = 0;

        /* renamed from: a */
        public static int m3487a(int i) {
            if (i == 0) {
                return 0;
            }
            if (i != 1) {
            }
            return 1;
        }

        /* renamed from: b */
        public static String m3488b(int i) {
            return i != 0 ? i != 1 ? "INVALID" : "NEED_ACK" : "NO_ACK";
        }
    }

    /* renamed from: com.taobao.accs.data.Message$c */
    /* compiled from: Taobao */
    public static class C2026c {
        public static final int CONTROL = 0;
        public static final int DATA = 1;
        public static final int HANDSHAKE = 3;
        public static final int INVALID = -1;
        public static final int PING = 2;

        /* renamed from: a */
        public static int m3489a(int i) {
            if (i == 0) {
                return 0;
            }
            int i2 = 1;
            if (i != 1) {
                i2 = 2;
                if (i != 2) {
                    i2 = 3;
                    if (i != 3) {
                        return 0;
                    }
                }
            }
            return i2;
        }

        /* renamed from: b */
        public static String m3490b(int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? "INVALID" : "HANDSHAKE" : "PING" : "DATA" : "CONTROL";
        }
    }

    /* compiled from: Taobao */
    public enum ReqType {
        DATA,
        ACK,
        REQ,
        RES;

        public static ReqType valueOf(int i) {
            if (i == 0) {
                return DATA;
            }
            if (i == 1) {
                return ACK;
            }
            if (i == 2) {
                return REQ;
            }
            if (i != 3) {
                return DATA;
            }
            return RES;
        }
    }

    /* renamed from: com.taobao.accs.data.Message$a */
    /* compiled from: Taobao */
    public static class C2024a {

        /* renamed from: a */
        private int f3291a;

        /* renamed from: b */
        private String f3292b;

        public C2024a(int i, String str) {
            this.f3291a = i;
            this.f3292b = str;
        }

        /* renamed from: a */
        public int mo18398a() {
            return this.f3291a;
        }

        /* renamed from: b */
        public String mo18399b() {
            return this.f3292b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            C2024a aVar = (C2024a) obj;
            if (this.f3291a == aVar.mo18398a() || this.f3292b.equals(aVar.mo18399b())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.f3292b.hashCode();
        }
    }

    private Message() {
        synchronized (Message.class) {
            this.f3261T = System.currentTimeMillis();
            this.f3281q = String.valueOf(this.f3261T) + "." + String.valueOf(f3241b);
            long j = f3241b;
            f3241b = 1 + j;
            this.f3266Y = new C2024a((int) j, this.f3281q);
        }
    }

    /* renamed from: a */
    public int mo18386a() {
        return this.f3280p;
    }

    /* renamed from: b */
    public String mo18390b() {
        return this.f3281q;
    }

    /* renamed from: c */
    public boolean mo18391c() {
        return Constants.TARGET_CONTROL.equals(this.f3278n);
    }

    /* renamed from: d */
    public C2024a mo18392d() {
        return this.f3266Y;
    }

    /* renamed from: a */
    public void mo18388a(long j) {
        this.f3262U = j;
    }

    /* renamed from: e */
    public NetPerformanceMonitor mo18393e() {
        return this.f3264W;
    }

    /* renamed from: j */
    private String m3472j() {
        return "Msg" + "_" + this.f3265X;
    }

    /* renamed from: f */
    public String mo18394f() {
        String str = this.f3283s;
        return str == null ? "" : str;
    }

    /* renamed from: g */
    public boolean mo18395g() {
        boolean z = (System.currentTimeMillis() - this.f3261T) + this.f3258Q >= ((long) this.f3260S);
        if (z) {
            String j = m3472j();
            ALog.m3727e(j, "delay time:" + this.f3258Q + " beforeSendTime:" + (System.currentTimeMillis() - this.f3261T) + " timeout" + this.f3260S, new Object[0]);
        }
        return z;
    }

    /* renamed from: a */
    public byte[] mo18389a(Context context, int i) {
        String str;
        byte[] bArr;
        try {
            mo18397i();
        } catch (JSONException e) {
            ALog.m3726e(m3472j(), "build1", e, new Object[0]);
        } catch (UnsupportedEncodingException e2) {
            ALog.m3726e(m3472j(), "build2", e2, new Object[0]);
        }
        byte[] bArr2 = this.f3255N;
        if (bArr2 != null) {
            str = new String(bArr2);
        } else {
            str = "";
        }
        mo18396h();
        if (!this.f3267c) {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilityImpl.getDeviceId(context));
            sb.append("|");
            sb.append(this.f3283s);
            sb.append("|");
            String str2 = this.f3249H;
            if (str2 == null) {
                str2 = "";
            }
            sb.append(str2);
            sb.append("|");
            String str3 = this.f3248G;
            if (str3 == null) {
                str3 = "";
            }
            sb.append(str3);
            this.f3279o = sb.toString();
        }
        try {
            bArr = (this.f3281q + "").getBytes("utf-8");
            this.f3277m = (byte) this.f3279o.getBytes("utf-8").length;
            this.f3276l = (byte) this.f3278n.getBytes("utf-8").length;
        } catch (Exception e3) {
            e3.printStackTrace();
            ALog.m3726e(m3472j(), "build3", e3, new Object[0]);
            bArr = (this.f3281q + "").getBytes();
            this.f3277m = (byte) this.f3279o.getBytes().length;
            this.f3276l = (byte) this.f3278n.getBytes().length;
        }
        short a = mo18387a(this.f3282r);
        int length = this.f3276l + 3 + 1 + this.f3277m + 1 + bArr.length;
        byte[] bArr3 = this.f3255N;
        this.f3274j = (short) (length + (bArr3 == null ? 0 : bArr3.length) + a + 2);
        this.f3273i = (short) (this.f3274j + 2);
        C2090g gVar = new C2090g(this.f3273i + 2 + 4);
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.m3725d(m3472j(), "Build Message", Constants.KEY_DATA_ID, new String(bArr));
        }
        try {
            gVar.mo18597a((byte) (this.f3271g | HttpTokens.SPACE));
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j = m3472j();
                ALog.m3725d(j, "\tversion:2 compress:" + this.f3271g, new Object[0]);
            }
            if (i == 0) {
                gVar.mo18597a(Byte.MIN_VALUE);
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d(m3472j(), "\tflag: 0x80", new Object[0]);
                }
            } else {
                gVar.mo18597a((byte) 64);
                if (ALog.isPrintLog(ALog.Level.D)) {
                    ALog.m3725d(m3472j(), "\tflag: 0x40", new Object[0]);
                }
            }
            gVar.mo18598a(this.f3273i);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j2 = m3472j();
                ALog.m3725d(j2, "\ttotalLength:" + this.f3273i, new Object[0]);
            }
            gVar.mo18598a(this.f3274j);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j3 = m3472j();
                ALog.m3725d(j3, "\tdataLength:" + this.f3274j, new Object[0]);
            }
            gVar.mo18598a(this.f3275k);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j4 = m3472j();
                ALog.m3725d(j4, "\tflags:" + Integer.toHexString(this.f3275k), new Object[0]);
            }
            gVar.mo18597a(this.f3276l);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j5 = m3472j();
                ALog.m3725d(j5, "\ttargetLength:" + this.f3276l, new Object[0]);
            }
            gVar.write(this.f3278n.getBytes("utf-8"));
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j6 = m3472j();
                ALog.m3725d(j6, "\ttarget:" + this.f3278n, new Object[0]);
            }
            gVar.mo18597a(this.f3277m);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j7 = m3472j();
                ALog.m3725d(j7, "\tsourceLength:" + this.f3277m, new Object[0]);
            }
            gVar.write(this.f3279o.getBytes("utf-8"));
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j8 = m3472j();
                ALog.m3725d(j8, "\tsource:" + this.f3279o, new Object[0]);
            }
            gVar.mo18597a((byte) bArr.length);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j9 = m3472j();
                ALog.m3725d(j9, "\tdataIdLength:" + bArr.length, new Object[0]);
            }
            gVar.write(bArr);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j10 = m3472j();
                ALog.m3725d(j10, "\tdataId:" + new String(bArr), new Object[0]);
            }
            gVar.mo18598a(a);
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j11 = m3472j();
                ALog.m3725d(j11, "\textHeader len:" + a, new Object[0]);
            }
            if (this.f3282r != null) {
                for (Integer intValue : this.f3282r.keySet()) {
                    int intValue2 = intValue.intValue();
                    String str4 = this.f3282r.get(Integer.valueOf(intValue2));
                    if (!TextUtils.isEmpty(str4)) {
                        gVar.mo18598a((short) ((((short) intValue2) << 10) | ((short) (str4.getBytes("utf-8").length & EXT_HEADER_VALUE_MAX_LEN))));
                        gVar.write(str4.getBytes("utf-8"));
                        if (ALog.isPrintLog(ALog.Level.D)) {
                            String j12 = m3472j();
                            ALog.m3725d(j12, "\textHeader key:" + intValue2 + " value:" + str4, new Object[0]);
                        }
                    }
                }
            }
            if (this.f3255N != null) {
                gVar.write(this.f3255N);
            }
            if (ALog.isPrintLog(ALog.Level.D)) {
                String j13 = m3472j();
                ALog.m3725d(j13, "\toriData:" + str, new Object[0]);
            }
            gVar.flush();
        } catch (IOException e4) {
            ALog.m3726e(m3472j(), "build4", e4, new Object[0]);
        }
        byte[] byteArray = gVar.toByteArray();
        try {
            gVar.close();
        } catch (IOException e5) {
            ALog.m3726e(m3472j(), "build5", e5, new Object[0]);
        }
        return byteArray;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public short mo18387a(Map<Integer, String> map) {
        short s = 0;
        if (map != null) {
            try {
                for (Integer intValue : map.keySet()) {
                    String str = map.get(Integer.valueOf(intValue.intValue()));
                    if (!TextUtils.isEmpty(str)) {
                        s = (short) (s + ((short) (str.getBytes("utf-8").length & EXT_HEADER_VALUE_MAX_LEN)) + 2);
                    }
                }
            } catch (Exception e) {
                e.toString();
            }
        }
        return s;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0053 A[SYNTHETIC, Splitter:B:29:0x0053] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x005d A[SYNTHETIC, Splitter:B:35:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0062 A[Catch:{ Exception -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* renamed from: h */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo18396h() {
        /*
            r7 = this;
            r0 = 0
            byte[] r1 = r7.f3255N     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            if (r1 != 0) goto L_0x0006
            return
        L_0x0006:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            r1.<init>()     // Catch:{ Throwable -> 0x0041, all -> 0x003c }
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x0037, all -> 0x0032 }
            r2.<init>(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x0032 }
            byte[] r0 = r7.f3255N     // Catch:{ Throwable -> 0x0030 }
            r2.write(r0)     // Catch:{ Throwable -> 0x0030 }
            r2.finish()     // Catch:{ Throwable -> 0x0030 }
            byte[] r0 = r1.toByteArray()     // Catch:{ Throwable -> 0x0030 }
            if (r0 == 0) goto L_0x0029
            int r3 = r0.length     // Catch:{ Throwable -> 0x0030 }
            byte[] r4 = r7.f3255N     // Catch:{ Throwable -> 0x0030 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0030 }
            if (r3 >= r4) goto L_0x0029
            r7.f3255N = r0     // Catch:{ Throwable -> 0x0030 }
            r0 = 1
            r7.f3271g = r0     // Catch:{ Throwable -> 0x0030 }
        L_0x0029:
            r2.close()     // Catch:{ Exception -> 0x0059 }
        L_0x002c:
            r1.close()     // Catch:{ Exception -> 0x0059 }
            goto L_0x0059
        L_0x0030:
            r0 = move-exception
            goto L_0x0045
        L_0x0032:
            r2 = move-exception
            r6 = r2
            r2 = r0
            r0 = r6
            goto L_0x005b
        L_0x0037:
            r2 = move-exception
            r6 = r2
            r2 = r0
            r0 = r6
            goto L_0x0045
        L_0x003c:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
            goto L_0x005b
        L_0x0041:
            r1 = move-exception
            r2 = r0
            r0 = r1
            r1 = r2
        L_0x0045:
            java.lang.String r3 = r7.m3472j()     // Catch:{ all -> 0x005a }
            java.lang.String r4 = "compressData fail"
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x005a }
            com.taobao.accs.utl.ALog.m3730w(r3, r4, r0, r5)     // Catch:{ all -> 0x005a }
            if (r2 == 0) goto L_0x0056
            r2.close()     // Catch:{ Exception -> 0x0059 }
        L_0x0056:
            if (r1 == 0) goto L_0x0059
            goto L_0x002c
        L_0x0059:
            return
        L_0x005a:
            r0 = move-exception
        L_0x005b:
            if (r2 == 0) goto L_0x0060
            r2.close()     // Catch:{ Exception -> 0x0065 }
        L_0x0060:
            if (r1 == 0) goto L_0x0065
            r1.close()     // Catch:{ Exception -> 0x0065 }
        L_0x0065:
            goto L_0x0067
        L_0x0066:
            throw r0
        L_0x0067:
            goto L_0x0066
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.Message.mo18396h():void");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: i */
    public void mo18397i() throws JSONException, UnsupportedEncodingException {
        Integer num = this.f3284t;
        if (num != null && num.intValue() != 100 && this.f3284t.intValue() != 102) {
            this.f3255N = new JsonUtility.JsonObjectBuilder().put("command", this.f3284t.intValue() == 100 ? null : this.f3284t).put(Constants.KEY_APP_KEY, this.f3286v).put(Constants.KEY_OS_TYPE, this.f3288x).put("sign", this.f3287w).put(Constants.KEY_SDK_VERSION, this.f3245D).put("appVersion", this.f3244C).put(Constants.KEY_TTID, this.f3246E).put(Constants.KEY_MODEL, this.f3250I).put(Constants.KEY_BRAND, this.f3251J).put(Constants.KEY_IMEI, this.f3252K).put(Constants.KEY_IMSI, this.f3253L).put(Constants.KEY_OS_VERSION, this.f3289y).put(Constants.KEY_EXTS, this.f3243B).build().toString().getBytes("utf-8");
        }
    }

    /* renamed from: a */
    public static Message m3460a(boolean z, int i) {
        Message message = new Message();
        message.f3280p = 2;
        message.f3284t = 201;
        message.f3268d = z;
        message.f3258Q = (long) i;
        return message;
    }

    /* renamed from: a */
    public static Message m3449a(C2049b bVar, Context context, Intent intent) {
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_APP_KEY);
            String stringExtra3 = intent.getStringExtra(Constants.KEY_TTID);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            String stringExtra4 = intent.getStringExtra("appVersion");
            Context context2 = context;
            message = m3448a(context2, bVar.f3385m, stringExtra2, intent.getStringExtra("app_sercet"), stringExtra, stringExtra3, stringExtra4);
            m3463a(bVar, message);
            return message;
        } catch (Exception e) {
            ALog.m3727e("Msg", "buildBindApp", e.getMessage());
            return message;
        }
    }

    /* renamed from: a */
    public static Message m3448a(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        if (TextUtils.isEmpty(str4)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3288x = 1;
        message.f3289y = Build.VERSION.SDK_INT + "";
        message.f3283s = str4;
        message.f3278n = Constants.TARGET_CONTROL;
        message.f3284t = 1;
        message.f3286v = str2;
        message.f3287w = UtilityImpl.m3738a(str2, str3, UtilityImpl.getDeviceId(context));
        message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.f3244C = str6;
        message.f3283s = str4;
        message.f3246E = str5;
        message.f3250I = Build.MODEL;
        message.f3251J = Build.BRAND;
        message.f3256O = KEY_BINDAPP;
        message.f3265X = str;
        message.f3243B = new JsonUtility.JsonObjectBuilder().put("notifyEnable", UtilityImpl.m3769k(context)).put("romInfo", RomInfoCollecter.getCollecter().collect()).build().toString();
        return message;
    }

    /* renamed from: a */
    public static Message m3453a(C2049b bVar, Intent intent) {
        ALog.m3727e("Msg", "buildUnbindApp1" + UtilityImpl.m3739a((Throwable) new Exception()), new Object[0]);
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            message = m3454a(bVar, stringExtra);
            m3463a(bVar, message);
            return message;
        } catch (Exception e) {
            ALog.m3727e("Msg", "buildUnbindApp1", e.getMessage());
            return message;
        }
    }

    /* renamed from: a */
    public static Message m3454a(C2049b bVar, String str) {
        Message message;
        try {
            ALog.m3727e("Msg", "buildUnbindApp" + UtilityImpl.m3739a((Throwable) new Exception()), new Object[0]);
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            message = new Message();
            try {
                message.f3257P = 1;
                message.m3461a(1, ReqType.DATA, 1);
                message.f3283s = str;
                message.f3278n = Constants.TARGET_CONTROL;
                message.f3284t = 2;
                message.f3283s = str;
                message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
                message.f3256O = KEY_UNBINDAPP;
                m3463a(bVar, message);
            } catch (Exception e) {
                e = e;
            }
            return message;
        } catch (Exception e2) {
            e = e2;
            message = null;
            ALog.m3727e("Msg", "buildUnbindApp", e.getMessage());
            return message;
        }
    }

    /* renamed from: b */
    public static Message m3466b(C2049b bVar, Intent intent) {
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            message = m3458a(stringExtra, stringExtra2);
            message.f3265X = bVar.f3385m;
            m3463a(bVar, message);
            return message;
        } catch (Throwable th) {
            ALog.m3726e("Msg", "buildBindService", th, new Object[0]);
            th.printStackTrace();
            return message;
        }
    }

    /* renamed from: a */
    public static Message m3458a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3283s = str;
        message.f3249H = str2;
        message.f3278n = Constants.TARGET_CONTROL;
        message.f3284t = 5;
        message.f3283s = str;
        message.f3249H = str2;
        message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.f3256O = KEY_BINDSERVICE;
        return message;
    }

    /* renamed from: c */
    public static Message m3468c(C2049b bVar, Intent intent) {
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            message = m3467b(stringExtra, stringExtra2);
            message.f3265X = bVar.f3385m;
            m3463a(bVar, message);
            return message;
        } catch (Exception e) {
            ALog.m3726e("Msg", "buildUnbindService", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
    }

    /* renamed from: b */
    public static Message m3467b(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3283s = str;
        message.f3249H = str2;
        message.f3278n = Constants.TARGET_CONTROL;
        message.f3284t = 6;
        message.f3283s = str;
        message.f3249H = str2;
        message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.f3256O = KEY_UNBINDSERVICE;
        return message;
    }

    /* renamed from: d */
    public static Message m3470d(C2049b bVar, Intent intent) {
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            message = m3469c(stringExtra, stringExtra2);
            if (message != null) {
                message.f3265X = bVar.f3385m;
                m3463a(bVar, message);
            }
        } catch (Exception e) {
            ALog.m3726e("Msg", "buildBindUser", e, new Object[0]);
            e.printStackTrace();
        }
        return message;
    }

    /* renamed from: c */
    public static Message m3469c(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3283s = str;
        message.f3248G = str2;
        message.f3278n = Constants.TARGET_CONTROL;
        message.f3284t = 3;
        message.f3283s = str;
        message.f3248G = str2;
        message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.f3256O = KEY_BINDUSER;
        return message;
    }

    /* renamed from: a */
    public static Message m3459a(String str, String str2, String str3, int i) {
        Message message = new Message();
        try {
            message.f3270f = new URL(str3);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        message.f3278n = Constants.TARGET_SERVICE_ST;
        message.m3461a(1, ReqType.DATA, 0);
        message.f3284t = 100;
        message.f3255N = ("0|" + i + "|" + str + "|" + AdapterUtilityImpl.getDeviceId(GlobalClientInfo.getContext()) + "|" + str2).getBytes();
        return message;
    }

    /* renamed from: e */
    public static Message m3471e(C2049b bVar, Intent intent) {
        Message message = null;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            message = m3456a(stringExtra);
            message.f3265X = bVar.f3385m;
            m3463a(bVar, message);
            return message;
        } catch (Exception e) {
            ALog.m3726e("Msg", "buildUnbindUser", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
    }

    /* renamed from: a */
    public static Message m3456a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3283s = str;
        message.f3278n = Constants.TARGET_CONTROL;
        message.f3284t = 4;
        message.f3245D = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.f3256O = KEY_UNBINDUSER;
        return message;
    }

    /* renamed from: a */
    public static Message m3450a(C2049b bVar, Context context, String str, ACCSManager.AccsRequest accsRequest) {
        return m3451a(bVar, context, str, accsRequest, true);
    }

    /* renamed from: a */
    public static Message m3451a(C2049b bVar, Context context, String str, ACCSManager.AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.DATA, 1);
        message.f3284t = 100;
        message.f3283s = str;
        message.f3249H = accsRequest.serviceId;
        message.f3248G = accsRequest.userId;
        message.f3255N = accsRequest.data;
        String str2 = TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName;
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.TARGET_SERVICE_PRE);
        sb.append(str2);
        sb.append("|");
        String str3 = "";
        sb.append(accsRequest.target == null ? str3 : accsRequest.target);
        message.f3278n = sb.toString();
        message.f3256O = accsRequest.dataId;
        message.f3263V = accsRequest.businessId;
        if (accsRequest.timeout > 0) {
            message.f3260S = accsRequest.timeout;
        }
        if (z) {
            m3464a(bVar, message, accsRequest);
        } else {
            message.f3270f = accsRequest.host;
        }
        m3462a(message, GlobalClientInfo.getInstance(context).getSid(bVar.f3385m), GlobalClientInfo.getInstance(context).getUserId(bVar.f3385m), bVar.f3381i.getStoreId(), GlobalClientInfo.f3224b, accsRequest.businessId, accsRequest.tag);
        message.f3264W = new NetPerformanceMonitor();
        message.f3264W.setMsgType(0);
        message.f3264W.setDataId(accsRequest.dataId);
        message.f3264W.setServiceId(accsRequest.serviceId);
        NetPerformanceMonitor netPerformanceMonitor = message.f3264W;
        URL url = message.f3270f;
        if (url != null) {
            str3 = url.toString();
        }
        netPerformanceMonitor.setHost(str3);
        message.f3265X = bVar.f3385m;
        return message;
    }

    /* renamed from: a */
    public static Message m3452a(C2049b bVar, Context context, String str, String str2, ACCSManager.AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.f3257P = 1;
        message.m3461a(1, ReqType.REQ, 1);
        message.f3284t = 100;
        message.f3283s = str;
        message.f3249H = accsRequest.serviceId;
        message.f3248G = accsRequest.userId;
        message.f3255N = accsRequest.data;
        String str3 = TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName;
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(str3);
        sb.append("|");
        String str4 = "";
        sb.append(accsRequest.target == null ? str4 : accsRequest.target);
        message.f3278n = sb.toString();
        message.f3256O = accsRequest.dataId;
        message.f3263V = accsRequest.businessId;
        message.f3265X = bVar.f3385m;
        if (accsRequest.timeout > 0) {
            message.f3260S = accsRequest.timeout;
        }
        if (z) {
            m3464a(bVar, message, accsRequest);
        } else {
            message.f3270f = accsRequest.host;
        }
        m3462a(message, GlobalClientInfo.getInstance(context).getSid(bVar.f3385m), GlobalClientInfo.getInstance(context).getUserId(bVar.f3385m), bVar.f3381i.getStoreId(), GlobalClientInfo.f3224b, accsRequest.businessId, accsRequest.tag);
        message.f3264W = new NetPerformanceMonitor();
        message.f3264W.setDataId(accsRequest.dataId);
        message.f3264W.setServiceId(accsRequest.serviceId);
        NetPerformanceMonitor netPerformanceMonitor = message.f3264W;
        URL url = message.f3270f;
        if (url != null) {
            str4 = url.toString();
        }
        netPerformanceMonitor.setHost(str4);
        message.f3265X = bVar.f3385m;
        return message;
    }

    /* renamed from: a */
    private static void m3464a(C2049b bVar, Message message, ACCSManager.AccsRequest accsRequest) {
        if (accsRequest.host == null) {
            try {
                message.f3270f = new URL(bVar.mo18478b((String) null));
            } catch (MalformedURLException e) {
                ALog.m3726e("Msg", "setUnit", e, new Object[0]);
                e.printStackTrace();
            }
        } else {
            message.f3270f = accsRequest.host;
        }
    }

    /* renamed from: a */
    private static void m3463a(C2049b bVar, Message message) {
        try {
            message.f3270f = new URL(bVar.mo18478b((String) null));
        } catch (Exception e) {
            ALog.m3726e("Msg", "setControlHost", e, new Object[0]);
        }
    }

    /* renamed from: a */
    public static Message m3455a(C2049b bVar, String str, String str2, String str3, boolean z, short s, String str4, Map<Integer, String> map) {
        Message message = new Message();
        message.f3257P = 1;
        message.m3465a(s, z);
        message.f3279o = str;
        message.f3278n = str2;
        message.f3281q = str3;
        message.f3267c = true;
        message.f3282r = map;
        try {
            if (TextUtils.isEmpty(str4)) {
                message.f3270f = new URL(bVar.mo18478b((String) null));
            } else {
                message.f3270f = new URL(str4);
            }
            message.f3265X = bVar.f3385m;
            if (message.f3270f == null) {
                try {
                    message.f3270f = new URL(bVar.mo18478b((String) null));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (message.f3270f == null) {
                try {
                    message.f3270f = new URL(bVar.mo18478b((String) null));
                } catch (MalformedURLException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
        return message;
    }

    /* renamed from: a */
    public static Message m3457a(String str, int i) {
        Message message = new Message();
        message.m3461a(1, ReqType.ACK, 0);
        message.f3284t = Integer.valueOf(i);
        message.f3283s = str;
        return message;
    }

    /* renamed from: a */
    private static void m3462a(Message message, String str, String str2, String str3, String str4, String str5, String str6) {
        if (!TextUtils.isEmpty(str5) || !TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str6) || str4 != null) {
            message.f3282r = new HashMap();
            if (str5 != null && UtilityImpl.m3734a(str5) <= 1023) {
                message.f3282r.put(Integer.valueOf(TaoBaseService.ExtHeaderType.TYPE_BUSINESS.ordinal()), str5);
            }
            if (str != null && UtilityImpl.m3734a(str) <= 1023) {
                message.f3282r.put(Integer.valueOf(TaoBaseService.ExtHeaderType.TYPE_SID.ordinal()), str);
            }
            if (str2 != null && UtilityImpl.m3734a(str2) <= 1023) {
                message.f3282r.put(Integer.valueOf(TaoBaseService.ExtHeaderType.TYPE_USERID.ordinal()), str2);
            }
            if (str6 != null && UtilityImpl.m3734a(str6) <= 1023) {
                message.f3282r.put(Integer.valueOf(TaoBaseService.ExtHeaderType.TYPE_TAG.ordinal()), str6);
            }
            if (str4 != null && UtilityImpl.m3734a(str4) <= 1023) {
                message.f3282r.put(Integer.valueOf(TaoBaseService.ExtHeaderType.TYPE_COOKIE.ordinal()), str4);
            }
            if (str3 != null && UtilityImpl.m3734a(str3) <= 1023) {
                message.f3282r.put(19, str3);
            }
        }
    }

    /* renamed from: a */
    private void m3461a(int i, ReqType reqType, int i2) {
        this.f3280p = i;
        if (i != 2) {
            this.f3275k = (short) (((((i & 1) << 4) | (reqType.ordinal() << 2)) | i2) << 11);
        }
    }

    /* renamed from: a */
    private void m3465a(short s, boolean z) {
        this.f3280p = 1;
        this.f3275k = s;
        this.f3275k = (short) (this.f3275k & -16385);
        this.f3275k = (short) (this.f3275k | com.topband.sdk.boiler.Message.FLAG_WARMING_WATER_REAL_TEMPERATURE);
        this.f3275k = (short) (this.f3275k & -2049);
        this.f3275k = (short) (this.f3275k & -65);
        if (z) {
            this.f3275k = (short) (this.f3275k | 32);
        }
    }
}
