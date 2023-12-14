package com.igexin.assist.sdk;

import android.content.Context;
import com.igexin.assist.control.AbstractPushManager;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.p050c.C1307c;
import com.igexin.push.core.p050c.C1312h;

public class AssistPushManager {

    /* renamed from: a */
    private AbstractPushManager f1535a;

    private AssistPushManager() {
    }

    public static boolean checkSupportDevice(Context context) {
        return C1146a.m1225c(context);
    }

    public static AssistPushManager getInstance() {
        return C1148c.f1537a;
    }

    public static String getToken() {
        return C1343f.f2188y;
    }

    public AbstractPushManager getPushManager() {
        return this.f1535a;
    }

    public String getSpToken() {
        try {
            return new C1307c(C1343f.f2169f).mo14659e();
        } catch (Throwable unused) {
            return null;
        }
    }

    public void initialize(Context context) {
        this.f1535a = C1146a.m1223a(context);
    }

    public void register(Context context) {
        AbstractPushManager abstractPushManager = this.f1535a;
        if (abstractPushManager != null) {
            abstractPushManager.register(context);
        }
    }

    public void saveToken(String str) {
        C1312h.m1937a().mo14684d(str);
    }

    public void setSilentTime(Context context, int i, int i2) {
        AbstractPushManager abstractPushManager = this.f1535a;
        if (abstractPushManager != null) {
            abstractPushManager.setSilentTime(context, i, i2);
        }
    }

    public void turnOffPush(Context context) {
        AbstractPushManager abstractPushManager = this.f1535a;
        if (abstractPushManager != null) {
            abstractPushManager.turnOffPush(context);
            new C1307c(context).mo14660f();
        }
    }

    public void turnOnPush(Context context) {
        AbstractPushManager abstractPushManager = this.f1535a;
        if (abstractPushManager != null) {
            abstractPushManager.turnOnPush(context);
        }
    }

    public void unregister(Context context) {
        AbstractPushManager abstractPushManager = this.f1535a;
        if (abstractPushManager != null) {
            abstractPushManager.unregister(context);
            new C1307c(context).mo14660f();
        }
    }
}
