package com.taobao.accs.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import java.util.HashMap;

/* renamed from: com.taobao.accs.messenger.a */
/* compiled from: Taobao */
public class C2041a {

    /* renamed from: a */
    private Context f3352a;

    /* renamed from: b */
    private HashMap<String, C2044d> f3353b = new HashMap<>();

    public C2041a(Context context) {
        this.f3352a = context;
    }

    /* renamed from: a */
    public C2044d mo18452a(String str) {
        C2044d dVar = this.f3353b.get(str);
        if (dVar == null || !dVar.mo18459a()) {
            return null;
        }
        return dVar;
    }

    /* renamed from: a */
    public void mo18454a(String str, C2044d dVar) {
        mo18455b(str, dVar);
        this.f3352a.unbindService(dVar);
    }

    /* renamed from: a */
    public void mo18453a(String str, Intent intent) {
        C2044d dVar = this.f3353b.get(str);
        C2044d dVar2 = null;
        if (dVar == null) {
            dVar2 = dVar;
        } else if (!dVar.mo18460b()) {
            this.f3353b.remove(str);
        } else if (dVar.mo18461c()) {
            mo18454a(str, dVar);
        } else {
            return;
        }
        if (dVar2 == null) {
            C2044d dVar3 = new C2044d(this.f3352a, str, this);
            this.f3353b.put(str, dVar3);
            this.f3352a.bindService(m3575a(intent), dVar3, 1);
        }
    }

    /* renamed from: a */
    private static Intent m3575a(Intent intent) {
        Intent intent2 = (Intent) intent.clone();
        intent2.replaceExtras(new Bundle());
        return intent2;
    }

    /* renamed from: b */
    public void mo18455b(String str, C2044d dVar) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.f3353b.remove(str, dVar);
        } else if (this.f3353b.get(str) == dVar) {
            this.f3353b.remove(str);
        }
    }
}
