package com.igexin.p030a;

import android.content.Context;
import android.util.Log;
import com.igexin.p030a.p031a.C1127j;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/* renamed from: com.igexin.a.h */
public class C1138h {

    /* renamed from: a */
    protected final Set<String> f1516a;

    /* renamed from: b */
    protected final C1135e f1517b;

    /* renamed from: c */
    protected final C1134d f1518c;

    /* renamed from: d */
    protected boolean f1519d;

    /* renamed from: e */
    protected boolean f1520e;

    /* renamed from: f */
    protected C1137g f1521f;

    protected C1138h() {
        this(new C1140j(), new C1117a());
    }

    protected C1138h(C1135e eVar, C1134d dVar) {
        this.f1516a = new HashSet();
        if (eVar == null) {
            throw new IllegalArgumentException("Cannot pass null library loader");
        } else if (dVar != null) {
            this.f1517b = eVar;
            this.f1518c = dVar;
        } else {
            throw new IllegalArgumentException("Cannot pass null library installer");
        }
    }

    /* renamed from: c */
    private void m1188c(Context context, String str, String str2) {
        if (!this.f1516a.contains(str) || this.f1519d) {
            try {
                this.f1517b.mo14164a(str);
                this.f1516a.add(str);
                mo14179a("%s (%s) was loaded normally!", str, str2);
            } catch (UnsatisfiedLinkError e) {
                mo14179a("Loading the library normally failed: %s", Log.getStackTraceString(e));
                mo14179a("%s (%s) was not loaded normally, re-linking...", str, str2);
                File a = mo14175a(context, str, str2);
                if (!a.exists() || this.f1519d) {
                    if (this.f1519d) {
                        mo14179a("Forcing a re-link of %s (%s)...", str, str2);
                    }
                    mo14181b(context, str, str2);
                    this.f1518c.mo14151a(context, this.f1517b.mo14165a(), this.f1517b.mo14167c(str), a, this);
                }
                try {
                    if (this.f1520e) {
                        for (String d : new C1127j(a).mo14159b()) {
                            mo14176a(context, this.f1517b.mo14168d(d));
                        }
                    }
                } catch (IOException unused) {
                }
                this.f1517b.mo14166b(a.getAbsolutePath());
                this.f1516a.add(str);
                mo14179a("%s (%s) was re-linked!", str, str2);
            }
        } else {
            mo14179a("%s already loaded previously!", str);
        }
    }

    /* renamed from: a */
    public C1138h mo14172a() {
        this.f1519d = true;
        return this;
    }

    /* renamed from: a */
    public C1138h mo14173a(C1137g gVar) {
        this.f1521f = gVar;
        return this;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public File mo14174a(Context context) {
        return context.getDir("lib", 0);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public File mo14175a(Context context, String str, String str2) {
        String c = this.f1517b.mo14167c(str);
        if (C1141k.m1204a(str2)) {
            return new File(mo14174a(context), c);
        }
        File a = mo14174a(context);
        return new File(a, c + "." + str2);
    }

    /* renamed from: a */
    public void mo14176a(Context context, String str) {
        mo14177a(context, str, (String) null, (C1136f) null);
    }

    /* renamed from: a */
    public void mo14177a(Context context, String str, String str2, C1136f fVar) {
        if (context == null) {
            throw new IllegalArgumentException("Given context is null");
        } else if (!C1141k.m1204a(str)) {
            mo14179a("Beginning load of %s...", str);
            if (fVar == null) {
                m1188c(context, str, str2);
                return;
            }
            try {
                m1188c(context, str, str2);
                fVar.mo14169a();
            } catch (UnsatisfiedLinkError e) {
                fVar.mo14170a(e);
            }
        } else {
            throw new IllegalArgumentException("Given library is either null or empty");
        }
    }

    /* renamed from: a */
    public void mo14178a(String str) {
        C1137g gVar = this.f1521f;
        if (gVar != null) {
            gVar.mo14171a(str);
        }
    }

    /* renamed from: a */
    public void mo14179a(String str, Object... objArr) {
        mo14178a(String.format(Locale.getDefault(), str, objArr));
    }

    /* renamed from: b */
    public C1138h mo14180b() {
        this.f1520e = true;
        return this;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo14181b(Context context, String str, String str2) {
        File a = mo14174a(context);
        File a2 = mo14175a(context, str, str2);
        File[] listFiles = a.listFiles(new C1139i(this, this.f1517b.mo14167c(str)));
        if (listFiles != null) {
            for (File file : listFiles) {
                if (this.f1519d || !file.getAbsolutePath().equals(a2.getAbsolutePath())) {
                    file.delete();
                }
            }
        }
    }
}
