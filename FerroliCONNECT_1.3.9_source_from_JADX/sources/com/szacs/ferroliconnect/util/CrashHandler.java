package com.szacs.ferroliconnect.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import p110io.reactivex.Single;
import p110io.reactivex.SingleEmitter;
import p110io.reactivex.SingleOnSubscribe;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public final class CrashHandler implements Thread.UncaughtExceptionHandler {
    @SuppressLint({"StaticFieldLeak"})
    private static CrashHandler INSTANCE = new CrashHandler();
    public static final String TAG = "CrashHandler";
    private Map<String, String> infos;
    private Context mContext;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        if (this.mContext == null) {
            this.mContext = context;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    @SuppressLint({"CheckResult"})
    @RequiresApi(api = 24)
    private void exitApp() {
        Single.create(new SingleOnSubscribe<Boolean>() {
            public void subscribe(SingleEmitter<Boolean> singleEmitter) throws Exception {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Log.e(CrashHandler.TAG, "error:", e);
                }
                Process.killProcess(Process.myPid());
                System.exit(1);
                singleEmitter.onSuccess(true);
            }
        }).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            public void accept(Boolean bool) throws Exception {
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
            }
        });
    }

    private void collectDeviceInfo() {
        if (this.infos == null) {
            this.infos = new HashMap(16);
        }
        this.infos.put("versionName", PackageUtil.getVersionName(this.mContext));
        this.infos.put("versionCode", PackageUtil.getVersionCode(this.mContext) + "");
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.infos.put(field.getName(), field.get((Object) null).toString());
            } catch (Exception e) {
                this.infos.put(this.mContext.getString(2131690752), e.getMessage());
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a6 A[Catch:{ Exception -> 0x00bd }] */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveCrashInfo2File(java.lang.Throwable r6) {
        /*
            r5 = this;
            java.lang.String r0 = "CrashHandler"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.util.Map<java.lang.String, java.lang.String> r2 = r5.infos
            java.util.Set r2 = r2.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0011:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x003a
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r3 = r3.getValue()
            java.lang.String r3 = (java.lang.String) r3
            r1.append(r4)
            java.lang.String r4 = "="
            r1.append(r4)
            r1.append(r3)
            java.lang.String r3 = "\n"
            r1.append(r3)
            goto L_0x0011
        L_0x003a:
            java.io.StringWriter r2 = new java.io.StringWriter
            r2.<init>()
            java.io.PrintWriter r3 = new java.io.PrintWriter
            r3.<init>(r2)
            r6.printStackTrace(r3)
        L_0x0047:
            java.lang.Throwable r6 = r6.getCause()
            if (r6 == 0) goto L_0x0051
            r6.printStackTrace(r3)
            goto L_0x0047
        L_0x0051:
            r3.close()
            java.lang.String r6 = r2.toString()
            r2.close()     // Catch:{ IOException -> 0x005c }
            goto L_0x0062
        L_0x005c:
            r2 = move-exception
            java.lang.String r3 = "error:"
            android.util.Log.e(r0, r3, r2)
        L_0x0062:
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            android.util.Log.e(r0, r6)
            java.lang.String r6 = com.szacs.ferroliconnect.util.FileUtil.CARSH_CACHE_DIR     // Catch:{ Exception -> 0x00bd }
            com.szacs.ferroliconnect.util.FileUtil.mkdirs(r6)     // Catch:{ Exception -> 0x00bd }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00bd }
            r6.<init>()     // Catch:{ Exception -> 0x00bd }
            java.lang.String r2 = "crash-"
            r6.append(r2)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r2 = "yyyy-MM-dd-HH-mm-ss"
            java.lang.String r2 = com.szacs.ferroliconnect.util.DateTimeUtil.getSystemDateTime(r2)     // Catch:{ Exception -> 0x00bd }
            r6.append(r2)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r2 = ".txt"
            r6.append(r2)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00bd }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00bd }
            java.lang.String r3 = com.szacs.ferroliconnect.util.FileUtil.CARSH_CACHE_DIR     // Catch:{ Exception -> 0x00bd }
            r2.<init>(r3, r6)     // Catch:{ Exception -> 0x00bd }
            boolean r6 = r2.exists()     // Catch:{ Exception -> 0x00bd }
            if (r6 != 0) goto L_0x00a3
            boolean r6 = r2.createNewFile()     // Catch:{ Exception -> 0x00bd }
            if (r6 == 0) goto L_0x00a1
            goto L_0x00a3
        L_0x00a1:
            r6 = 0
            goto L_0x00a4
        L_0x00a3:
            r6 = 1
        L_0x00a4:
            if (r6 == 0) goto L_0x00c3
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00bd }
            r6.<init>(r2)     // Catch:{ Exception -> 0x00bd }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00bd }
            byte[] r1 = r1.getBytes()     // Catch:{ Exception -> 0x00bd }
            r6.write(r1)     // Catch:{ Exception -> 0x00bd }
            r6.flush()     // Catch:{ Exception -> 0x00bd }
            r6.close()     // Catch:{ Exception -> 0x00bd }
            goto L_0x00c3
        L_0x00bd:
            r6 = move-exception
            java.lang.String r1 = "an error occured while writing file"
            android.util.Log.e(r0, r1, r6)
        L_0x00c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.CrashHandler.saveCrashInfo2File(java.lang.Throwable):void");
    }

    @RequiresApi(api = 24)
    public void uncaughtException(Thread thread, Throwable th) {
        if (th == null) {
            th = new Throwable(this.mContext.getString(2131690992));
        }
        collectDeviceInfo();
        saveCrashInfo2File(th);
        exitApp();
    }
}
