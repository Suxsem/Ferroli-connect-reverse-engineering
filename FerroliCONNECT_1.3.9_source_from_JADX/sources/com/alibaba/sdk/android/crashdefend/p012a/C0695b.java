package com.alibaba.sdk.android.crashdefend.p012a;

import com.alibaba.sdk.android.crashdefend.CrashDefendCallback;
import com.alibaba.sdk.android.crashdefend.p014c.C0699b;

/* renamed from: com.alibaba.sdk.android.crashdefend.a.b */
public class C0695b implements Cloneable {

    /* renamed from: a */
    public String f863a;

    /* renamed from: b */
    public String f864b;

    /* renamed from: c */
    public int f865c;

    /* renamed from: d */
    public int f866d;

    /* renamed from: e */
    public int f867e;

    /* renamed from: f */
    public long f868f;

    /* renamed from: g */
    public long f869g;

    /* renamed from: h */
    public int f870h = 0;

    /* renamed from: i */
    public long f871i = 0;

    /* renamed from: j */
    public volatile boolean f872j = false;

    /* renamed from: k */
    public CrashDefendCallback f873k = null;

    public Object clone() {
        try {
            return (C0695b) super.clone();
        } catch (CloneNotSupportedException e) {
            C0699b.m603a("CrashSDK", "clone fail: ", e);
            return null;
        }
    }
}
