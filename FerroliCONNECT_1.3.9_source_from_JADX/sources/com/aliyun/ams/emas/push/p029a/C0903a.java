package com.aliyun.ams.emas.push.p029a;

import android.app.NotificationManager;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.aliyun.ams.emas.push.a.a */
/* compiled from: Taobao */
public class C0903a {

    /* renamed from: a */
    private static C0903a f1414a;

    /* renamed from: b */
    private static List<Integer> f1415b;

    private C0903a() {
        f1415b = new ArrayList();
    }

    /* renamed from: a */
    public static C0903a m1057a() {
        if (f1414a == null) {
            f1414a = new C0903a();
        }
        return f1414a;
    }

    /* renamed from: a */
    public void mo10171a(int i) {
        f1415b.add(Integer.valueOf(i));
    }

    /* renamed from: a */
    public void mo10172a(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        while (!f1415b.isEmpty()) {
            List<Integer> list = f1415b;
            notificationManager.cancel(list.get(list.size() - 1).intValue());
            List<Integer> list2 = f1415b;
            list2.remove(list2.size() - 1);
        }
    }
}
