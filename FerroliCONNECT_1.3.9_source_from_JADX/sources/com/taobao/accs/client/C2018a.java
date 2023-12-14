package com.taobao.accs.client;

import android.text.TextUtils;
import com.taobao.accs.IAppReceiver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.taobao.accs.client.a */
/* compiled from: Taobao */
public class C2018a {

    /* renamed from: a */
    private ConcurrentHashMap<String, HashSet<IAppReceiver>> f3233a;

    /* renamed from: com.taobao.accs.client.a$a */
    /* compiled from: Taobao */
    private static class C2019a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final C2018a f3234a = new C2018a();

        private C2019a() {
        }
    }

    /* renamed from: a */
    public static C2018a m3435a() {
        return C2019a.f3234a;
    }

    private C2018a() {
        this.f3233a = new ConcurrentHashMap<>(2);
    }

    /* renamed from: a */
    public void mo18374a(String str, IAppReceiver iAppReceiver) {
        if (iAppReceiver != null) {
            HashSet hashSet = this.f3233a.get(str);
            if (hashSet == null) {
                hashSet = new HashSet();
                this.f3233a.put(str, hashSet);
            }
            if (!hashSet.contains(iAppReceiver)) {
                hashSet.add(iAppReceiver);
            }
        }
    }

    /* renamed from: a */
    public ArrayList<IAppReceiver> mo18373a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        HashSet hashSet = this.f3233a.get(str);
        if (hashSet == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(hashSet);
    }

    /* renamed from: b */
    public ArrayList<IAppReceiver> mo18375b() {
        HashSet hashSet = new HashSet();
        for (HashSet<IAppReceiver> addAll : this.f3233a.values()) {
            hashSet.addAll(addAll);
        }
        return new ArrayList<>(hashSet);
    }

    /* renamed from: b */
    public void mo18376b(String str) {
        try {
            this.f3233a.remove(str);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
