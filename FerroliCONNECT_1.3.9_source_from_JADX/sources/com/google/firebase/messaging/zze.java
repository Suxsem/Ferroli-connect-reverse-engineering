package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_messaging.zzk;
import com.google.android.gms.internal.firebase_messaging.zzn;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.io.Closeable;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;

final class zze implements Closeable {
    private final URL url;
    @Nullable
    private Task<Bitmap> zzef;
    @Nullable
    private volatile InputStream zzeg;

    @Nullable
    public static zze zzo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new zze(new URL(str));
        } catch (MalformedURLException unused) {
            String valueOf = String.valueOf(str);
            Log.w("FirebaseMessaging", valueOf.length() != 0 ? "Not downloading image, bad URL: ".concat(valueOf) : new String("Not downloading image, bad URL: "));
            return null;
        }
    }

    private zze(URL url2) {
        this.url = url2;
    }

    public final void zza(Executor executor) {
        this.zzef = Tasks.call(executor, new zzd(this));
    }

    public final Task<Bitmap> getTask() {
        return (Task) Preconditions.checkNotNull(this.zzef);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009e, code lost:
        r3 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009f, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a3, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a4, code lost:
        r8 = r5;
        r5 = r3;
        r3 = r8;
     */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:28:0x00a7=Splitter:B:28:0x00a7, B:12:0x006b=Splitter:B:12:0x006b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Bitmap zzat() throws java.io.IOException {
        /*
            r9 = this;
            java.net.URL r0 = r9.url
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r1 = java.lang.String.valueOf(r0)
            int r1 = r1.length()
            int r1 = r1 + 22
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>(r1)
            java.lang.String r1 = "Starting download of: "
            r2.append(r1)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            java.lang.String r1 = "FirebaseMessaging"
            android.util.Log.i(r1, r0)
            java.net.URL r0 = r9.url     // Catch:{ IOException -> 0x00b6 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ IOException -> 0x00b6 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ IOException -> 0x00b6 }
            r2 = 1048576(0x100000, double:5.180654E-318)
            r4 = 0
            java.io.InputStream r2 = com.google.android.gms.internal.firebase_messaging.zzj.zza(r0, r2)     // Catch:{ Throwable -> 0x00ad }
            r9.zzeg = r0     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r2)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            if (r3 == 0) goto L_0x0074
            r5 = 3
            boolean r5 = android.util.Log.isLoggable(r1, r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            if (r5 == 0) goto L_0x006b
            java.net.URL r5 = r9.url     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r6 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            int r6 = r6.length()     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            int r6 = r6 + 31
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            r7.<init>(r6)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r6 = "Successfully downloaded image: "
            r7.append(r6)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            r7.append(r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r5 = r7.toString()     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            android.util.Log.d(r1, r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
        L_0x006b:
            zza(r4, r2)     // Catch:{ Throwable -> 0x00ad }
            if (r0 == 0) goto L_0x0073
            zza(r4, r0)     // Catch:{ IOException -> 0x00b6 }
        L_0x0073:
            return r3
        L_0x0074:
            java.net.URL r3 = r9.url     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r5 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            int r5 = r5.length()     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            int r5 = r5 + 24
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r5 = "Failed to decode image: "
            r6.append(r5)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            r6.append(r3)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.lang.String r3 = r6.toString()     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            android.util.Log.w(r1, r3)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            java.io.IOException r5 = new java.io.IOException     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            r5.<init>(r3)     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
            throw r5     // Catch:{ Throwable -> 0x00a1, all -> 0x009e }
        L_0x009e:
            r3 = move-exception
            r5 = r4
            goto L_0x00a7
        L_0x00a1:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x00a3 }
        L_0x00a3:
            r5 = move-exception
            r8 = r5
            r5 = r3
            r3 = r8
        L_0x00a7:
            zza(r5, r2)     // Catch:{ Throwable -> 0x00ad }
            throw r3     // Catch:{ Throwable -> 0x00ad }
        L_0x00ab:
            r2 = move-exception
            goto L_0x00b0
        L_0x00ad:
            r2 = move-exception
            r4 = r2
            throw r4     // Catch:{ all -> 0x00ab }
        L_0x00b0:
            if (r0 == 0) goto L_0x00b5
            zza(r4, r0)     // Catch:{ IOException -> 0x00b6 }
        L_0x00b5:
            throw r2     // Catch:{ IOException -> 0x00b6 }
        L_0x00b6:
            r0 = move-exception
            java.net.URL r2 = r9.url
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 26
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "Failed to download image: "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            android.util.Log.w(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zze.zzat():android.graphics.Bitmap");
    }

    public final void close() {
        zzk.zza(this.zzeg);
    }

    private static /* synthetic */ void zza(Throwable th, InputStream inputStream) {
        if (th != null) {
            try {
                inputStream.close();
            } catch (Throwable th2) {
                zzn.zza(th, th2);
            }
        } else {
            inputStream.close();
        }
    }
}
