package com.igexin.sdk.p091a;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* renamed from: com.igexin.sdk.a.d */
public class C1601d {

    /* renamed from: a */
    private String f3065a;

    public C1601d(Context context) {
        if (context != null) {
            this.f3065a = context.getFilesDir().getPath() + "/" + "run.pid";
        }
    }

    /* renamed from: a */
    public void mo15296a() {
        if (!mo15297b()) {
            try {
                new File(this.f3065a).createNewFile();
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: b */
    public boolean mo15297b() {
        return new File(this.f3065a).exists();
    }
}
