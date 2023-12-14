package com.igexin.sdk;

import android.app.Activity;
import com.igexin.push.core.C1356s;

public class GTServiceManager {
    private GTServiceManager() {
    }

    public static GTServiceManager getInstance() {
        return C1605e.f3069a;
    }

    public void onActivityCreate(Activity activity) {
        C1356s.m2138a().mo14780a(activity);
    }
}
