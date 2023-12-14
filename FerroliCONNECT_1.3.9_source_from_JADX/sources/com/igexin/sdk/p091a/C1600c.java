package com.igexin.sdk.p091a;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* renamed from: com.igexin.sdk.a.c */
public class C1600c {

    /* renamed from: a */
    private String f3064a;

    public C1600c(Context context) {
        if (context != null) {
            this.f3064a = context.getFilesDir().getPath() + "/" + "push.pid";
        }
    }

    /* renamed from: a */
    public void mo15293a() {
        if (!mo15295c()) {
            try {
                new File(this.f3064a).createNewFile();
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: b */
    public void mo15294b() {
        if (mo15295c()) {
            new File(this.f3064a).delete();
        }
    }

    /* renamed from: c */
    public boolean mo15295c() {
        return new File(this.f3064a).exists();
    }
}
