package com.igexin.sdk.p091a;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* renamed from: com.igexin.sdk.a.b */
public class C1599b {

    /* renamed from: a */
    private String f3063a;

    public C1599b(Context context) {
        if (context != null) {
            this.f3063a = context.getFilesDir().getPath() + "/" + "init.pid";
        }
    }

    /* renamed from: a */
    public void mo15291a() {
        if (!mo15292b()) {
            try {
                new File(this.f3063a).createNewFile();
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: b */
    public boolean mo15292b() {
        return new File(this.f3063a).exists();
    }
}
