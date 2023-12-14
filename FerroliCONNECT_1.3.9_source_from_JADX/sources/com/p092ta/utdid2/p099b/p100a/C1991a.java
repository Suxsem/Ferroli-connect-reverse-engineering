package com.p092ta.utdid2.p099b.p100a;

import android.content.Context;
import android.content.SharedPreferences;
import com.p092ta.p093a.p096c.C1982f;

/* renamed from: com.ta.utdid2.b.a.a */
public class C1991a {

    /* renamed from: a */
    private SharedPreferences f3183a = null;

    public C1991a(Context context, String str, String str2) {
        if (context != null) {
            this.f3183a = context.getSharedPreferences(str2, 0);
        }
    }

    /* renamed from: k */
    public String mo18143k() {
        SharedPreferences sharedPreferences = this.f3183a;
        String str = "";
        if (sharedPreferences != null) {
            str = sharedPreferences.getString("UTDID2", str);
        }
        C1982f.m3366a("PersistentConfiguration", "getUtdidFromSp utdid", str);
        return str;
    }

    /* renamed from: a */
    public int mo18141a() {
        SharedPreferences sharedPreferences = this.f3183a;
        int i = sharedPreferences != null ? sharedPreferences.getInt("type", 0) : 0;
        C1982f.m3366a("PersistentConfiguration", "getTypeFromSp type", Integer.valueOf(i));
        return i;
    }

    /* renamed from: a */
    public void mo18142a(String str, int i) {
        if (this.f3183a != null) {
            C1982f.m3366a("PersistentConfiguration", "writeUtdidToSp utdid", str);
            SharedPreferences.Editor edit = this.f3183a.edit();
            edit.putString("UTDID2", str);
            edit.putInt("type", i);
            if (this.f3183a.getLong("t2", 0) == 0) {
                edit.putLong("t2", System.currentTimeMillis());
            }
            try {
                edit.commit();
            } catch (Exception unused) {
            }
        }
    }
}
