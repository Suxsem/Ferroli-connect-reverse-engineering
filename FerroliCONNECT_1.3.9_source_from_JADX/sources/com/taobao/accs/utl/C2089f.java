package com.taobao.accs.utl;

import android.content.SharedPreferences;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;

/* renamed from: com.taobao.accs.utl.f */
/* compiled from: Taobao */
public class C2089f {
    public static final int MAX_FAIL_TIMES = 3;

    /* renamed from: a */
    public static void m3779a() {
        try {
            int c = m3781c();
            if (c > 0) {
                SharedPreferences.Editor edit = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).edit();
                edit.clear();
                edit.apply();
                ALog.m3728i("LoadSoFailUtil", "loadSoSuccess", "fail times", Integer.valueOf(c));
            }
        } catch (Throwable th) {
            ALog.m3726e("LoadSoFailUtil", "loadSoSuccess", th, new Object[0]);
        }
    }

    /* renamed from: b */
    public static void m3780b() {
        try {
            SharedPreferences sharedPreferences = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0);
            int i = sharedPreferences.getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0) + 1;
            if (i > 0) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putInt(Constants.SP_KEY_LOAD_SO_TIMES, i);
                edit.apply();
            }
            ALog.m3727e("LoadSoFailUtil", "loadSoFail", "times", Integer.valueOf(i));
        } catch (Throwable th) {
            ALog.m3726e("LoadSoFailUtil", "loadSoFail", th, new Object[0]);
        }
    }

    /* renamed from: c */
    public static int m3781c() {
        int i;
        try {
            i = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0);
            try {
                ALog.m3728i("LoadSoFailUtil", "getSoFailTimes", "times", Integer.valueOf(i));
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            i = 0;
            ALog.m3726e("LoadSoFailUtil", "getSoFailTimes", th, new Object[0]);
            return i;
        }
        return i;
    }
}
