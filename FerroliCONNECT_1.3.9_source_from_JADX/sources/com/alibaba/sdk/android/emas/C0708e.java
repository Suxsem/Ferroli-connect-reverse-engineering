package com.alibaba.sdk.android.emas;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.tbrest.utils.AppUtils;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.taobao.accs.common.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

/* renamed from: com.alibaba.sdk.android.emas.e */
/* compiled from: DiskCacheManager */
public class C0708e implements Cache<C0711f> {

    /* renamed from: a */
    private long f900a;

    /* renamed from: a */
    private final String f901a;

    /* renamed from: a */
    private Set<String> f902a;

    /* renamed from: b */
    private final String f903b;
    private int diskCacheLimitCapacity;
    private int diskCacheLimitCount;

    public C0708e(Context context, String str, String str2, String str3) {
        this.f903b = str2;
        this.f901a = context.getFilesDir() + File.separator + "emas-rest-log" + File.separator + (str + "_" + str2) + File.separator + (TextUtils.isEmpty(str3) ? "common" : str3);
        File file = new File(this.f901a);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /* renamed from: a */
    public void mo9636a(int i, int i2, int i3) {
        this.diskCacheLimitCount = i;
        this.diskCacheLimitCapacity = i2;
        this.f900a = ((long) i3) * Constants.CLIENT_FLUSH_INTERVAL;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00a9, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00d1, code lost:
        return;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void remove(com.alibaba.sdk.android.emas.C0711f r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 == 0) goto L_0x00ac
            com.alibaba.sdk.android.emas.d r0 = r6.mo9643a()     // Catch:{ all -> 0x00aa }
            com.alibaba.sdk.android.emas.d r1 = com.alibaba.sdk.android.emas.C0707d.DISK_CACHE     // Catch:{ all -> 0x00aa }
            if (r0 != r1) goto L_0x000d
            goto L_0x00ac
        L_0x000d:
            java.util.List r6 = r6.mo9643a()     // Catch:{ all -> 0x00aa }
            if (r6 == 0) goto L_0x00a8
            boolean r0 = r6.isEmpty()     // Catch:{ all -> 0x00aa }
            if (r0 != 0) goto L_0x00a8
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ all -> 0x00aa }
            r0.<init>()     // Catch:{ all -> 0x00aa }
            r1 = 0
        L_0x001f:
            int r2 = r6.size()     // Catch:{ all -> 0x00aa }
            if (r1 == r2) goto L_0x0037
            java.lang.Object r2 = r6.get(r1)     // Catch:{ all -> 0x00aa }
            com.alibaba.sdk.android.emas.g r2 = (com.alibaba.sdk.android.emas.C0712g) r2     // Catch:{ all -> 0x00aa }
            org.json.JSONObject r2 = r2.mo9645a()     // Catch:{ all -> 0x00aa }
            if (r2 == 0) goto L_0x0034
            r0.put(r2)     // Catch:{ all -> 0x00aa }
        L_0x0034:
            int r1 = r1 + 1
            goto L_0x001f
        L_0x0037:
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x00aa }
            java.lang.String r0 = r5.f903b     // Catch:{ all -> 0x00aa }
            java.lang.String r6 = com.alibaba.sdk.android.emas.C0713h.aesEncrypt(r0, r6)     // Catch:{ all -> 0x00aa }
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x00aa }
            if (r0 != 0) goto L_0x00a3
            java.lang.String r0 = "DiskCacheManager putting into cache."
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r0)     // Catch:{ all -> 0x00aa }
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x00aa }
            java.lang.String r1 = r5.f901a     // Catch:{ all -> 0x00aa }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
            r2.<init>()     // Catch:{ all -> 0x00aa }
            java.lang.String r3 = "UTF-8"
            java.nio.charset.Charset r3 = java.nio.charset.Charset.forName(r3)     // Catch:{ all -> 0x00aa }
            byte[] r3 = r6.getBytes(r3)     // Catch:{ all -> 0x00aa }
            java.lang.String r3 = com.alibaba.sdk.android.tbrest.utils.MD5Utils.getMd5Hex(r3)     // Catch:{ all -> 0x00aa }
            r2.append(r3)     // Catch:{ all -> 0x00aa }
            java.lang.String r3 = "_"
            r2.append(r3)     // Catch:{ all -> 0x00aa }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00aa }
            r2.append(r3)     // Catch:{ all -> 0x00aa }
            java.lang.String r3 = ".log"
            r2.append(r3)     // Catch:{ all -> 0x00aa }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00aa }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x00aa }
            boolean r1 = r0.exists()     // Catch:{ all -> 0x00aa }
            if (r1 == 0) goto L_0x0087
            r0.delete()     // Catch:{ all -> 0x00aa }
        L_0x0087:
            r5.m624a((java.io.File) r0, (java.lang.String) r6)     // Catch:{ all -> 0x00aa }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
            r6.<init>()     // Catch:{ all -> 0x00aa }
            java.lang.String r1 = "DiskCacheManager success put into "
            r6.append(r1)     // Catch:{ all -> 0x00aa }
            java.lang.String r0 = r0.getAbsolutePath()     // Catch:{ all -> 0x00aa }
            r6.append(r0)     // Catch:{ all -> 0x00aa }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x00aa }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r6)     // Catch:{ all -> 0x00aa }
            goto L_0x00a8
        L_0x00a3:
            java.lang.String r6 = "DiskCacheManager failed put into cache."
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r6)     // Catch:{ all -> 0x00aa }
        L_0x00a8:
            monitor-exit(r5)
            return
        L_0x00aa:
            r6 = move-exception
            goto L_0x00d2
        L_0x00ac:
            if (r6 != 0) goto L_0x00b4
            java.lang.String r6 = "DiskCacheManager add failed. data is null"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r6)     // Catch:{ all -> 0x00aa }
            goto L_0x00d0
        L_0x00b4:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00aa }
            r0.<init>()     // Catch:{ all -> 0x00aa }
            java.lang.String r1 = "DiskCacheManager add failed. cache type: "
            r0.append(r1)     // Catch:{ all -> 0x00aa }
            com.alibaba.sdk.android.emas.d r6 = r6.mo9643a()     // Catch:{ all -> 0x00aa }
            java.lang.String r6 = r6.name()     // Catch:{ all -> 0x00aa }
            r0.append(r6)     // Catch:{ all -> 0x00aa }
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x00aa }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r6)     // Catch:{ all -> 0x00aa }
        L_0x00d0:
            monitor-exit(r5)
            return
        L_0x00d2:
            monitor-exit(r5)
            goto L_0x00d5
        L_0x00d4:
            throw r6
        L_0x00d5:
            goto L_0x00d4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.emas.C0708e.remove(com.alibaba.sdk.android.emas.f):void");
    }

    /* renamed from: a */
    public synchronized C0711f get() {
        File file;
        File file2 = new File(this.f901a);
        if (!file2.exists()) {
            return null;
        }
        if (!file2.isDirectory()) {
            file2.delete();
            return null;
        }
        List<File> a = m623a(file2, (List<File>) new ArrayList());
        if (a.size() <= 0) {
            return null;
        }
        Collections.sort(a, new Comparator<File>() {
            /* renamed from: a */
            public int compare(File file, File file2) {
                if (file == null && file2 == null) {
                    return 0;
                }
                if (file == null) {
                    return -1;
                }
                if (file2 == null) {
                    return 1;
                }
                if (file == file2) {
                    return 0;
                }
                if (file.lastModified() < file2.lastModified()) {
                    return -1;
                }
                return file.lastModified() == file2.lastModified() ? 0 : 1;
            }
        });
        if (this.f902a != null && !this.f902a.isEmpty()) {
            int i = 0;
            while (true) {
                if (i == a.size()) {
                    file = null;
                    break;
                } else if (!this.f902a.contains(a.get(i).getAbsolutePath())) {
                    file = a.get(i);
                    break;
                } else {
                    LogUtil.m1029d("DiskCacheManager disk cache is in the Sending Queue. skip location: " + a.get(i).getAbsolutePath());
                    i++;
                }
            }
        } else {
            file = a.get(0);
        }
        if (file == null) {
            return null;
        }
        String aesDecrypt = C0713h.aesDecrypt(this.f903b, m622a(file));
        if (TextUtils.isEmpty(aesDecrypt)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(aesDecrypt);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 != jSONArray.length(); i2++) {
                C0712g a2 = C0712g.m634a(jSONArray.getJSONObject(i2));
                if (a2 != null) {
                    arrayList.add(a2);
                }
            }
            if (this.f902a == null) {
                this.f902a = new HashSet();
            }
            this.f902a.add(file.getAbsolutePath());
            return new C0711f(arrayList, C0707d.DISK_CACHE, file.getAbsolutePath());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x007e, code lost:
        return false;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean m628a(com.alibaba.sdk.android.emas.C0711f r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            if (r4 == 0) goto L_0x0059
            com.alibaba.sdk.android.emas.d r1 = r4.mo9643a()     // Catch:{ all -> 0x0057 }
            com.alibaba.sdk.android.emas.d r2 = com.alibaba.sdk.android.emas.C0707d.DISK_CACHE     // Catch:{ all -> 0x0057 }
            if (r1 != r2) goto L_0x0059
            java.lang.String r1 = r4.getLocation()     // Catch:{ all -> 0x0057 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0017
            goto L_0x0059
        L_0x0017:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            r1.<init>()     // Catch:{ all -> 0x0057 }
            java.lang.String r2 = "DiskCacheManager removing. cache type: "
            r1.append(r2)     // Catch:{ all -> 0x0057 }
            com.alibaba.sdk.android.emas.d r2 = r4.mo9643a()     // Catch:{ all -> 0x0057 }
            java.lang.String r2 = r2.name()     // Catch:{ all -> 0x0057 }
            r1.append(r2)     // Catch:{ all -> 0x0057 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0057 }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r1)     // Catch:{ all -> 0x0057 }
            java.util.Set<java.lang.String> r1 = r3.f902a     // Catch:{ all -> 0x0057 }
            if (r1 == 0) goto L_0x0040
            java.util.Set<java.lang.String> r1 = r3.f902a     // Catch:{ all -> 0x0057 }
            java.lang.String r2 = r4.getLocation()     // Catch:{ all -> 0x0057 }
            r1.remove(r2)     // Catch:{ all -> 0x0057 }
        L_0x0040:
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r4.getLocation()     // Catch:{ all -> 0x0057 }
            r1.<init>(r4)     // Catch:{ all -> 0x0057 }
            boolean r4 = r1.exists()     // Catch:{ all -> 0x0057 }
            if (r4 == 0) goto L_0x0055
            boolean r4 = r1.delete()     // Catch:{ all -> 0x0057 }
            monitor-exit(r3)
            return r4
        L_0x0055:
            monitor-exit(r3)
            return r0
        L_0x0057:
            r4 = move-exception
            goto L_0x007f
        L_0x0059:
            if (r4 != 0) goto L_0x0061
            java.lang.String r4 = "DiskCacheManager remove failed. data is null"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r4)     // Catch:{ all -> 0x0057 }
            goto L_0x007d
        L_0x0061:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0057 }
            r1.<init>()     // Catch:{ all -> 0x0057 }
            java.lang.String r2 = "DiskCacheManager remove failed. cache type: "
            r1.append(r2)     // Catch:{ all -> 0x0057 }
            com.alibaba.sdk.android.emas.d r4 = r4.mo9643a()     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r4.name()     // Catch:{ all -> 0x0057 }
            r1.append(r4)     // Catch:{ all -> 0x0057 }
            java.lang.String r4 = r1.toString()     // Catch:{ all -> 0x0057 }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r4)     // Catch:{ all -> 0x0057 }
        L_0x007d:
            monitor-exit(r3)
            return r0
        L_0x007f:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.emas.C0708e.m628a(com.alibaba.sdk.android.emas.f):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c9, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void clear() {
        /*
            r14 = this;
            monitor-enter(r14)
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x00ca }
            java.lang.String r1 = r14.f901a     // Catch:{ all -> 0x00ca }
            r0.<init>(r1)     // Catch:{ all -> 0x00ca }
            boolean r1 = r0.exists()     // Catch:{ all -> 0x00ca }
            if (r1 != 0) goto L_0x0010
            monitor-exit(r14)
            return
        L_0x0010:
            boolean r1 = r0.isDirectory()     // Catch:{ all -> 0x00ca }
            if (r1 != 0) goto L_0x001b
            r0.delete()     // Catch:{ all -> 0x00ca }
            monitor-exit(r14)
            return
        L_0x001b:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x00ca }
            r1.<init>()     // Catch:{ all -> 0x00ca }
            java.util.List r0 = r14.m623a((java.io.File) r0, (java.util.List<java.io.File>) r1)     // Catch:{ all -> 0x00ca }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ca }
            r1.<init>()     // Catch:{ all -> 0x00ca }
            java.lang.String r2 = "DiskCacheManager num: "
            r1.append(r2)     // Catch:{ all -> 0x00ca }
            int r2 = r0.size()     // Catch:{ all -> 0x00ca }
            r1.append(r2)     // Catch:{ all -> 0x00ca }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ca }
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r1)     // Catch:{ all -> 0x00ca }
            int r1 = r0.size()     // Catch:{ all -> 0x00ca }
            if (r1 <= 0) goto L_0x00c8
            com.alibaba.sdk.android.emas.e$2 r1 = new com.alibaba.sdk.android.emas.e$2     // Catch:{ all -> 0x00ca }
            r1.<init>()     // Catch:{ all -> 0x00ca }
            java.util.Collections.sort(r0, r1)     // Catch:{ all -> 0x00ca }
            long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00ca }
            r3 = 0
            java.util.Iterator r5 = r0.iterator()     // Catch:{ all -> 0x00ca }
            r6 = 0
            r7 = r3
            r3 = 0
        L_0x0057:
            boolean r4 = r5.hasNext()     // Catch:{ all -> 0x00ca }
            if (r4 == 0) goto L_0x007e
            java.lang.Object r4 = r5.next()     // Catch:{ all -> 0x00ca }
            java.io.File r4 = (java.io.File) r4     // Catch:{ all -> 0x00ca }
            long r9 = r4.lastModified()     // Catch:{ all -> 0x00ca }
            long r9 = r1 - r9
            long r11 = r14.f900a     // Catch:{ all -> 0x00ca }
            int r13 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r13 < 0) goto L_0x0076
            r5.remove()     // Catch:{ all -> 0x00ca }
            r4.delete()     // Catch:{ all -> 0x00ca }
            goto L_0x0057
        L_0x0076:
            int r3 = r3 + 1
            long r9 = r4.length()     // Catch:{ all -> 0x00ca }
            long r7 = r7 + r9
            goto L_0x0057
        L_0x007e:
            int r1 = r14.diskCacheLimitCount     // Catch:{ all -> 0x00ca }
            if (r3 > r1) goto L_0x0089
            int r1 = r14.diskCacheLimitCapacity     // Catch:{ all -> 0x00ca }
            long r1 = (long) r1     // Catch:{ all -> 0x00ca }
            int r4 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r4 <= 0) goto L_0x00c8
        L_0x0089:
            java.lang.String r1 = "DiskCacheManager exceed limit. start clear."
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1029d(r1)     // Catch:{ all -> 0x00ca }
            int r1 = r14.diskCacheLimitCount     // Catch:{ all -> 0x00ca }
            double r1 = (double) r1
            r4 = 4605380978949069210(0x3fe999999999999a, double:0.8)
            java.lang.Double.isNaN(r1)
            double r1 = r1 * r4
            int r1 = (int) r1
            int r2 = r14.diskCacheLimitCapacity     // Catch:{ all -> 0x00ca }
            double r9 = (double) r2
            java.lang.Double.isNaN(r9)
            double r9 = r9 * r4
            int r2 = (int) r9
        L_0x00a5:
            if (r3 > r1) goto L_0x00ac
            long r4 = (long) r2
            int r9 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r9 <= 0) goto L_0x00c8
        L_0x00ac:
            int r4 = r0.size()     // Catch:{ all -> 0x00ca }
            if (r6 >= r4) goto L_0x00c8
            java.lang.Object r4 = r0.get(r6)     // Catch:{ all -> 0x00ca }
            java.io.File r4 = (java.io.File) r4     // Catch:{ all -> 0x00ca }
            boolean r5 = r4.delete()     // Catch:{ all -> 0x00ca }
            if (r5 == 0) goto L_0x00c5
            int r3 = r3 + -1
            long r4 = r4.length()     // Catch:{ all -> 0x00ca }
            long r7 = r7 - r4
        L_0x00c5:
            int r6 = r6 + 1
            goto L_0x00a5
        L_0x00c8:
            monitor-exit(r14)
            return
        L_0x00ca:
            r0 = move-exception
            monitor-exit(r14)
            goto L_0x00ce
        L_0x00cd:
            throw r0
        L_0x00ce:
            goto L_0x00cd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.emas.C0708e.clear():void");
    }

    /* renamed from: a */
    private List<File> m623a(File file, List<File> list) {
        File[] listFiles;
        if (file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    m623a(file2, list);
                } else if (!list.contains(file2)) {
                    list.add(file2);
                }
            }
        }
        return list;
    }

    /* renamed from: a */
    private String m622a(File file) {
        BufferedInputStream bufferedInputStream;
        StringBuilder sb = new StringBuilder();
        byte[] bArr = new byte[4096];
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            while (true) {
                try {
                    int read = bufferedInputStream.read(bArr);
                    if (read != -1) {
                        sb.append(new String(bArr, 0, read));
                    } else {
                        String sb2 = sb.toString();
                        AppUtils.closeQuietly(bufferedInputStream);
                        return sb2;
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    e.printStackTrace();
                    AppUtils.closeQuietly(bufferedInputStream);
                    return null;
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    AppUtils.closeQuietly(bufferedInputStream);
                    return null;
                }
            }
        } catch (FileNotFoundException e3) {
            e = e3;
            bufferedInputStream = null;
            e.printStackTrace();
            AppUtils.closeQuietly(bufferedInputStream);
            return null;
        } catch (IOException e4) {
            e = e4;
            bufferedInputStream = null;
            e.printStackTrace();
            AppUtils.closeQuietly(bufferedInputStream);
            return null;
        } catch (Throwable th) {
            th = th;
            AppUtils.closeQuietly(bufferedInputStream);
            throw th;
        }
    }

    /* renamed from: a */
    private void m624a(File file, String str) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
            try {
                bufferedOutputStream2.write(str.getBytes("utf-8"));
                AppUtils.closeQuietly(bufferedOutputStream2);
            } catch (FileNotFoundException e) {
                e = e;
                bufferedOutputStream = bufferedOutputStream2;
                e.printStackTrace();
                AppUtils.closeQuietly(bufferedOutputStream);
            } catch (UnsupportedEncodingException e2) {
                e = e2;
                bufferedOutputStream = bufferedOutputStream2;
                e.printStackTrace();
                AppUtils.closeQuietly(bufferedOutputStream);
            } catch (IOException e3) {
                e = e3;
                bufferedOutputStream = bufferedOutputStream2;
                e.printStackTrace();
                AppUtils.closeQuietly(bufferedOutputStream);
            } catch (Throwable th) {
                th = th;
                bufferedOutputStream = bufferedOutputStream2;
                AppUtils.closeQuietly(bufferedOutputStream);
                throw th;
            }
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            AppUtils.closeQuietly(bufferedOutputStream);
        } catch (UnsupportedEncodingException e5) {
            e = e5;
            e.printStackTrace();
            AppUtils.closeQuietly(bufferedOutputStream);
        } catch (IOException e6) {
            e = e6;
            e.printStackTrace();
            AppUtils.closeQuietly(bufferedOutputStream);
        } catch (Throwable th2) {
            th = th2;
            AppUtils.closeQuietly(bufferedOutputStream);
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0026, code lost:
        return;
     */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void mo9638b(com.alibaba.sdk.android.emas.C0711f r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.Set<java.lang.String> r0 = r1.f902a     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x0025
            java.util.Set<java.lang.String> r0 = r1.f902a     // Catch:{ all -> 0x0027 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x000e
            goto L_0x0025
        L_0x000e:
            java.lang.String r0 = r2.getLocation()     // Catch:{ all -> 0x0027 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x0027 }
            if (r0 == 0) goto L_0x001a
            monitor-exit(r1)
            return
        L_0x001a:
            java.util.Set<java.lang.String> r0 = r1.f902a     // Catch:{ all -> 0x0027 }
            java.lang.String r2 = r2.getLocation()     // Catch:{ all -> 0x0027 }
            r0.remove(r2)     // Catch:{ all -> 0x0027 }
            monitor-exit(r1)
            return
        L_0x0025:
            monitor-exit(r1)
            return
        L_0x0027:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.emas.C0708e.mo9638b(com.alibaba.sdk.android.emas.f):void");
    }
}
