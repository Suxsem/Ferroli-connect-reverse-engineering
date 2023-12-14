package com.igexin.push.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.igexin.assist.sdk.AssistPushConsts;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.sdk.PushBuildConfig;
import com.taobao.accs.common.Constants;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* renamed from: com.igexin.push.util.c */
public class C1578c {
    /* renamed from: a */
    public static void m3224a(C1580e eVar, Context context) {
        new Thread(new C1579d(context, eVar)).start();
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public static boolean m3227c(Context context) {
        try {
            byte[] a = C1581f.m3234a(context.getFilesDir().getPath() + "/" + "init_er.pid");
            return a == null || System.currentTimeMillis() - Long.valueOf(new String(a)).longValue() > Constants.CLIENT_FLUSH_INTERVAL;
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public static String m3228d(Context context) {
        String packageName = context.getPackageName();
        String str = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                str = applicationInfo.metaData.getString(AssistPushConsts.GETUI_APPID);
            }
        } catch (Exception unused) {
        }
        String str2 = Build.MODEL;
        String str3 = Build.VERSION.SDK;
        String str4 = Build.VERSION.RELEASE;
        File file = new File(context.getApplicationInfo().nativeLibraryDir + File.separator + "libgetuiext3.so");
        StringBuilder sb = new StringBuilder();
        sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        sb.append("|");
        sb.append(str);
        sb.append("|");
        sb.append(PushBuildConfig.sdk_conf_version);
        sb.append("|");
        sb.append(file.exists());
        sb.append("|");
        sb.append(C1589n.m3258a(context));
        sb.append("|");
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        sb.append("|");
        sb.append(str4);
        sb.append("|");
        sb.append(C1589n.m3259b(context));
        sb.append("|");
        sb.append(C1589n.m3257a());
        sb.append("|");
        sb.append(packageName);
        if (EncryptUtils.errorMsg != null) {
            sb.append("|");
            sb.append(EncryptUtils.errorMsg);
        }
        C1179b.m1354a("ErrorReport|" + sb.toString());
        return sb.toString();
    }
}
