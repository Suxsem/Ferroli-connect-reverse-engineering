package com.alibaba.sdk.android.tbrest.rest;

import android.content.Context;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.tbrest.rest.g */
/* compiled from: RestSecuritySDKRequestAuthentication */
public class C0892g {

    /* renamed from: a */
    private Class f1380a = null;

    /* renamed from: a */
    private Object f1381a = null;

    /* renamed from: a */
    private Field f1382a = null;

    /* renamed from: b */
    private Object f1383b = null;

    /* renamed from: b */
    private String f1384b = null;

    /* renamed from: b */
    private Field f1385b = null;

    /* renamed from: b */
    private Method f1386b = null;

    /* renamed from: c */
    private Field f1387c = null;

    /* renamed from: e */
    private boolean f1388e = false;

    /* renamed from: h */
    private int f1389h = 1;
    private Context mContext;

    public C0892g(Context context, String str) {
        this.mContext = context;
        this.f1384b = str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0043  */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void m1027g() {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.f1388e     // Catch:{ all -> 0x00c0 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r7)
            return
        L_0x0007:
            r0 = 0
            r1 = 1
            r2 = 0
            java.lang.String r3 = "com.taobao.wireless.security.sdk.SecurityGuardManager"
            java.lang.Class r3 = java.lang.Class.forName(r3)     // Catch:{ Throwable -> 0x003b }
            java.lang.String r4 = "getInstance"
            java.lang.Class[] r5 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x003c }
            java.lang.Class<android.content.Context> r6 = android.content.Context.class
            r5[r2] = r6     // Catch:{ Throwable -> 0x003c }
            java.lang.reflect.Method r4 = r3.getMethod(r4, r5)     // Catch:{ Throwable -> 0x003c }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x003c }
            android.content.Context r6 = r7.mContext     // Catch:{ Throwable -> 0x003c }
            r5[r2] = r6     // Catch:{ Throwable -> 0x003c }
            java.lang.Object r4 = r4.invoke(r0, r5)     // Catch:{ Throwable -> 0x003c }
            r7.f1381a = r4     // Catch:{ Throwable -> 0x003c }
            java.lang.String r4 = "getSecureSignatureComp"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x003c }
            java.lang.reflect.Method r4 = r3.getMethod(r4, r5)     // Catch:{ Throwable -> 0x003c }
            java.lang.Object r5 = r7.f1381a     // Catch:{ Throwable -> 0x003c }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x003c }
            java.lang.Object r4 = r4.invoke(r5, r6)     // Catch:{ Throwable -> 0x003c }
            r7.f1383b = r4     // Catch:{ Throwable -> 0x003c }
            goto L_0x0041
        L_0x003b:
            r3 = r0
        L_0x003c:
            java.lang.String r4 = "initSecurityCheck failure, It's ok "
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1032i(r4)     // Catch:{ all -> 0x00c0 }
        L_0x0041:
            if (r3 == 0) goto L_0x00bc
            java.lang.String r4 = "com.taobao.wireless.security.sdk.SecurityGuardParamContext"
            java.lang.Class r4 = java.lang.Class.forName(r4)     // Catch:{ Throwable -> 0x00b7 }
            r7.f1380a = r4     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Class r4 = r7.f1380a     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r5 = "appKey"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch:{ Throwable -> 0x00b7 }
            r7.f1382a = r4     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Class r4 = r7.f1380a     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r5 = "paramMap"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch:{ Throwable -> 0x00b7 }
            r7.f1385b = r4     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Class r4 = r7.f1380a     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r5 = "requestType"
            java.lang.reflect.Field r4 = r4.getDeclaredField(r5)     // Catch:{ Throwable -> 0x00b7 }
            r7.f1387c = r4     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r4 = "isOpen"
            java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch:{ Throwable -> 0x0072 }
            java.lang.reflect.Method r3 = r3.getMethod(r4, r5)     // Catch:{ Throwable -> 0x0072 }
            goto L_0x0078
        L_0x0072:
            java.lang.String r3 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1032i(r3)     // Catch:{ Throwable -> 0x00b7 }
            r3 = r0
        L_0x0078:
            if (r3 == 0) goto L_0x0089
            java.lang.Object r0 = r7.f1381a     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Object r0 = r3.invoke(r0, r4)     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ Throwable -> 0x00b7 }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x00b7 }
            goto L_0x009a
        L_0x0089:
            java.lang.String r3 = "com.taobao.wireless.security.sdk.securitybody.ISecurityBodyComponent"
            java.lang.Class r0 = java.lang.Class.forName(r3)     // Catch:{ Throwable -> 0x0090 }
            goto L_0x0095
        L_0x0090:
            java.lang.String r3 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1032i(r3)     // Catch:{ Throwable -> 0x00b7 }
        L_0x0095:
            if (r0 != 0) goto L_0x0099
            r0 = 1
            goto L_0x009a
        L_0x0099:
            r0 = 0
        L_0x009a:
            if (r0 == 0) goto L_0x009e
            r0 = 1
            goto L_0x00a0
        L_0x009e:
            r0 = 12
        L_0x00a0:
            r7.f1389h = r0     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r0 = "com.taobao.wireless.security.sdk.securesignature.ISecureSignatureComponent"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ Throwable -> 0x00b7 }
            java.lang.String r3 = "signRequest"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Throwable -> 0x00b7 }
            java.lang.Class r5 = r7.f1380a     // Catch:{ Throwable -> 0x00b7 }
            r4[r2] = r5     // Catch:{ Throwable -> 0x00b7 }
            java.lang.reflect.Method r0 = r0.getMethod(r3, r4)     // Catch:{ Throwable -> 0x00b7 }
            r7.f1386b = r0     // Catch:{ Throwable -> 0x00b7 }
            goto L_0x00bc
        L_0x00b7:
            java.lang.String r0 = "initSecurityCheck failure, It's ok"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1032i(r0)     // Catch:{ all -> 0x00c0 }
        L_0x00bc:
            r7.f1388e = r1     // Catch:{ all -> 0x00c0 }
            monitor-exit(r7)
            return
        L_0x00c0:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.rest.C0892g.m1027g():void");
    }

    /* renamed from: b */
    public String mo10146b(String str) {
        Class cls;
        if (!this.f1388e) {
            m1027g();
        }
        if (this.f1384b == null) {
            LogUtil.m1030e("RestSecuritySDKRequestAuthentication:getSign There is no appkey,please check it!");
            return null;
        } else if (str == null) {
            return null;
        } else {
            if (!(this.f1381a == null || (cls = this.f1380a) == null || this.f1382a == null || this.f1385b == null || this.f1387c == null || this.f1386b == null || this.f1383b == null)) {
                try {
                    Object newInstance = cls.newInstance();
                    this.f1382a.set(newInstance, this.f1384b);
                    ((Map) this.f1385b.get(newInstance)).put("INPUT", str);
                    this.f1387c.set(newInstance, Integer.valueOf(this.f1389h));
                    return (String) this.f1386b.invoke(this.f1383b, new Object[]{newInstance});
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                } catch (IllegalArgumentException e3) {
                    e3.printStackTrace();
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                }
            }
            return null;
        }
    }
}
