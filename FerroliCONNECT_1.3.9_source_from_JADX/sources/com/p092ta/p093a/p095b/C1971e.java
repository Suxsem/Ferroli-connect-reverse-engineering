package com.p092ta.p093a.p095b;

import android.content.Context;
import com.p092ta.p093a.C1964a;
import com.p092ta.p093a.p096c.C1975a;
import com.p092ta.p093a.p096c.C1982f;
import java.io.File;

/* renamed from: com.ta.a.b.e */
public class C1971e {
    /* renamed from: b */
    private static String m3342b() {
        Context context = C1964a.mo18112a().getContext();
        String str = m3339a(context) + File.separator + "4635b664f789000d";
        C1982f.m3371b("", str);
        return str;
    }

    /* renamed from: c */
    public static String m3343c() {
        Context context = C1964a.mo18112a().getContext();
        return m3339a(context) + File.separator + "9983c160aa044115";
    }

    /* renamed from: d */
    public static String m3344d() {
        try {
            return C1975a.m3351c(m3342b());
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: a */
    public static void m3340a(String str) {
        try {
            C1982f.m3372e();
            C1975a.m3350b(m3342b(), str);
        } catch (Throwable unused) {
        }
    }

    /* renamed from: a */
    private static String m3339a(Context context) {
        String str = context.getFilesDir().getAbsolutePath() + File.separator + ".7934039a7252be16";
        C1982f.m3371b("", "UtdidAppRoot dir:" + str);
        C1975a.m3349b(str);
        return str;
    }

    /* renamed from: a */
    public static boolean m3341a(Context context) {
        try {
            return !context.getFileStreamPath("3c9b584e65e6c983").exists();
        } catch (Exception unused) {
            return true;
        }
    }
}
