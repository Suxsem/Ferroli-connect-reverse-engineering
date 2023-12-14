package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

public class ACache {
    private static final int MAX_COUNT = Integer.MAX_VALUE;
    private static final int MAX_SIZE = 50000000;
    public static final int TIME_DAY = 86400;
    public static final int TIME_HOUR = 3600;
    private static Map<String, ACache> mInstanceMap = new HashMap();
    private ACacheManager mCache;

    public static ACache get(Context context) {
        return get(context, "ACache");
    }

    public static ACache get(Context context, String str) {
        return get(new File(context.getCacheDir(), str), 50000000, Integer.MAX_VALUE);
    }

    public static ACache get(File file) {
        return get(file, 50000000, Integer.MAX_VALUE);
    }

    public static ACache get(Context context, long j, int i) {
        return get(new File(context.getCacheDir(), "ACache"), j, i);
    }

    public static ACache get(File file, long j, int i) {
        Map<String, ACache> map = mInstanceMap;
        ACache aCache = map.get(file.getAbsoluteFile() + myPid());
        if (aCache != null) {
            return aCache;
        }
        ACache aCache2 = new ACache(file, j, i);
        Map<String, ACache> map2 = mInstanceMap;
        map2.put(file.getAbsolutePath() + myPid(), aCache2);
        return aCache2;
    }

    private static String myPid() {
        return "_" + Process.myPid();
    }

    private ACache(File file, long j, int i) {
        if (file.exists() || file.mkdirs()) {
            this.mCache = new ACacheManager(file, j, i);
            return;
        }
        throw new RuntimeException("can't make dirs in " + file.getAbsolutePath());
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002d A[SYNTHETIC, Splitter:B:17:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0040 A[SYNTHETIC, Splitter:B:24:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            com.szacs.ferroliconnect.util.ACache$ACacheManager r0 = r4.mCache
            java.io.File r5 = r0.newFile(r5)
            r0 = 0
            java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ IOException -> 0x0027 }
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x0027 }
            r2.<init>(r5)     // Catch:{ IOException -> 0x0027 }
            r3 = 1024(0x400, float:1.435E-42)
            r1.<init>(r2, r3)     // Catch:{ IOException -> 0x0027 }
            r1.write(r6)     // Catch:{ IOException -> 0x0022, all -> 0x001f }
            r1.flush()     // Catch:{ IOException -> 0x001d }
            r1.close()     // Catch:{ IOException -> 0x001d }
            goto L_0x0038
        L_0x001d:
            r6 = move-exception
            goto L_0x0035
        L_0x001f:
            r6 = move-exception
            r0 = r1
            goto L_0x003e
        L_0x0022:
            r6 = move-exception
            r0 = r1
            goto L_0x0028
        L_0x0025:
            r6 = move-exception
            goto L_0x003e
        L_0x0027:
            r6 = move-exception
        L_0x0028:
            r6.printStackTrace()     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x0038
            r0.flush()     // Catch:{ IOException -> 0x0034 }
            r0.close()     // Catch:{ IOException -> 0x0034 }
            goto L_0x0038
        L_0x0034:
            r6 = move-exception
        L_0x0035:
            r6.printStackTrace()
        L_0x0038:
            com.szacs.ferroliconnect.util.ACache$ACacheManager r6 = r4.mCache
            r6.put(r5)
            return
        L_0x003e:
            if (r0 == 0) goto L_0x004b
            r0.flush()     // Catch:{ IOException -> 0x0047 }
            r0.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004b:
            com.szacs.ferroliconnect.util.ACache$ACacheManager r0 = r4.mCache
            r0.put(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.ACache.put(java.lang.String, java.lang.String):void");
    }

    public void put(String str, String str2, int i) {
        put(str, Utils.newStringWithDateInfo(i, str2));
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x005b A[SYNTHETIC, Splitter:B:32:0x005b] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0067 A[SYNTHETIC, Splitter:B:39:0x0067] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getAsString(java.lang.String r6) {
        /*
            r5 = this;
            com.szacs.ferroliconnect.util.ACache$ACacheManager r0 = r5.mCache
            java.io.File r0 = r0.get(r6)
            boolean r1 = r0.exists()
            r2 = 0
            if (r1 != 0) goto L_0x000e
            return r2
        L_0x000e:
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            r1.<init>(r3)     // Catch:{ IOException -> 0x0054, all -> 0x0051 }
            java.lang.String r0 = ""
        L_0x001a:
            java.lang.String r3 = r1.readLine()     // Catch:{ IOException -> 0x004f }
            if (r3 == 0) goto L_0x0030
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x004f }
            r4.<init>()     // Catch:{ IOException -> 0x004f }
            r4.append(r0)     // Catch:{ IOException -> 0x004f }
            r4.append(r3)     // Catch:{ IOException -> 0x004f }
            java.lang.String r0 = r4.toString()     // Catch:{ IOException -> 0x004f }
            goto L_0x001a
        L_0x0030:
            boolean r3 = com.szacs.ferroliconnect.util.ACache.Utils.isDue((java.lang.String) r0)     // Catch:{ IOException -> 0x004f }
            if (r3 != 0) goto L_0x0043
            java.lang.String r6 = com.szacs.ferroliconnect.util.ACache.Utils.clearDateInfo((java.lang.String) r0)     // Catch:{ IOException -> 0x004f }
            r1.close()     // Catch:{ IOException -> 0x003e }
            goto L_0x0042
        L_0x003e:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0042:
            return r6
        L_0x0043:
            r1.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004b:
            r5.remove(r6)
            return r2
        L_0x004f:
            r6 = move-exception
            goto L_0x0056
        L_0x0051:
            r6 = move-exception
            r1 = r2
            goto L_0x0065
        L_0x0054:
            r6 = move-exception
            r1 = r2
        L_0x0056:
            r6.printStackTrace()     // Catch:{ all -> 0x0064 }
            if (r1 == 0) goto L_0x0063
            r1.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0063:
            return r2
        L_0x0064:
            r6 = move-exception
        L_0x0065:
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006f:
            goto L_0x0071
        L_0x0070:
            throw r6
        L_0x0071:
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.ACache.getAsString(java.lang.String):java.lang.String");
    }

    public void put(String str, JSONObject jSONObject) {
        put(str, jSONObject.toString());
    }

    public void put(String str, JSONObject jSONObject, int i) {
        put(str, jSONObject.toString(), i);
    }

    public JSONObject getAsJSONObject(String str) {
        try {
            return new JSONObject(getAsString(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void put(String str, JSONArray jSONArray) {
        put(str, jSONArray.toString());
    }

    public void put(String str, JSONArray jSONArray, int i) {
        put(str, jSONArray.toString(), i);
    }

    public JSONArray getAsJSONArray(String str) {
        try {
            return new JSONArray(getAsString(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0026 A[SYNTHETIC, Splitter:B:17:0x0026] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0039 A[SYNTHETIC, Splitter:B:24:0x0039] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void put(java.lang.String r3, byte[] r4) {
        /*
            r2 = this;
            com.szacs.ferroliconnect.util.ACache$ACacheManager r0 = r2.mCache
            java.io.File r3 = r0.newFile(r3)
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0020 }
            r1.<init>(r3)     // Catch:{ Exception -> 0x0020 }
            r1.write(r4)     // Catch:{ Exception -> 0x001b, all -> 0x0018 }
            r1.flush()     // Catch:{ IOException -> 0x0016 }
            r1.close()     // Catch:{ IOException -> 0x0016 }
            goto L_0x0031
        L_0x0016:
            r4 = move-exception
            goto L_0x002e
        L_0x0018:
            r4 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x001b:
            r4 = move-exception
            r0 = r1
            goto L_0x0021
        L_0x001e:
            r4 = move-exception
            goto L_0x0037
        L_0x0020:
            r4 = move-exception
        L_0x0021:
            r4.printStackTrace()     // Catch:{ all -> 0x001e }
            if (r0 == 0) goto L_0x0031
            r0.flush()     // Catch:{ IOException -> 0x002d }
            r0.close()     // Catch:{ IOException -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r4 = move-exception
        L_0x002e:
            r4.printStackTrace()
        L_0x0031:
            com.szacs.ferroliconnect.util.ACache$ACacheManager r4 = r2.mCache
            r4.put(r3)
            return
        L_0x0037:
            if (r0 == 0) goto L_0x0044
            r0.flush()     // Catch:{ IOException -> 0x0040 }
            r0.close()     // Catch:{ IOException -> 0x0040 }
            goto L_0x0044
        L_0x0040:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0044:
            com.szacs.ferroliconnect.util.ACache$ACacheManager r0 = r2.mCache
            r0.put(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.ACache.put(java.lang.String, byte[]):void");
    }

    public void put(String str, byte[] bArr, int i) {
        put(str, Utils.newByteArrayWithDateInfo(i, bArr));
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0049 A[SYNTHETIC, Splitter:B:28:0x0049] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0056 A[SYNTHETIC, Splitter:B:36:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] getAsBinary(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            com.szacs.ferroliconnect.util.ACache$ACacheManager r1 = r5.mCache     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            java.io.File r1 = r1.get(r6)     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            if (r2 != 0) goto L_0x000e
            return r0
        L_0x000e:
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            java.lang.String r3 = "r"
            r2.<init>(r1, r3)     // Catch:{ Exception -> 0x0042, all -> 0x0040 }
            long r3 = r2.length()     // Catch:{ Exception -> 0x003e }
            int r1 = (int) r3     // Catch:{ Exception -> 0x003e }
            byte[] r1 = new byte[r1]     // Catch:{ Exception -> 0x003e }
            r2.read(r1)     // Catch:{ Exception -> 0x003e }
            boolean r3 = com.szacs.ferroliconnect.util.ACache.Utils.isDue((byte[]) r1)     // Catch:{ Exception -> 0x003e }
            if (r3 != 0) goto L_0x0032
            byte[] r6 = com.szacs.ferroliconnect.util.ACache.Utils.clearDateInfo((byte[]) r1)     // Catch:{ Exception -> 0x003e }
            r2.close()     // Catch:{ IOException -> 0x002d }
            goto L_0x0031
        L_0x002d:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0031:
            return r6
        L_0x0032:
            r2.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x003a
        L_0x0036:
            r1 = move-exception
            r1.printStackTrace()
        L_0x003a:
            r5.remove(r6)
            return r0
        L_0x003e:
            r6 = move-exception
            goto L_0x0044
        L_0x0040:
            r6 = move-exception
            goto L_0x0054
        L_0x0042:
            r6 = move-exception
            r2 = r0
        L_0x0044:
            r6.printStackTrace()     // Catch:{ all -> 0x0052 }
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ IOException -> 0x004d }
            goto L_0x0051
        L_0x004d:
            r6 = move-exception
            r6.printStackTrace()
        L_0x0051:
            return r0
        L_0x0052:
            r6 = move-exception
            r0 = r2
        L_0x0054:
            if (r0 == 0) goto L_0x005e
            r0.close()     // Catch:{ IOException -> 0x005a }
            goto L_0x005e
        L_0x005a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x005e:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.ACache.getAsBinary(java.lang.String):byte[]");
    }

    public void put(String str, Serializable serializable) {
        put(str, serializable, -1);
    }

    public void put(String str, Serializable serializable, int i) {
        ObjectOutputStream objectOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream2.writeObject(serializable);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                if (i != -1) {
                    put(str, byteArray, i);
                } else {
                    put(str, byteArray);
                }
                try {
                    objectOutputStream2.close();
                } catch (IOException unused) {
                }
            } catch (Exception e) {
                e = e;
                objectOutputStream = objectOutputStream2;
                try {
                    e.printStackTrace();
                    objectOutputStream.close();
                } catch (Throwable th) {
                    th = th;
                    try {
                        objectOutputStream.close();
                    } catch (IOException unused2) {
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                objectOutputStream = objectOutputStream2;
                objectOutputStream.close();
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            objectOutputStream.close();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0038 A[SYNTHETIC, Splitter:B:28:0x0038] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0042 A[SYNTHETIC, Splitter:B:33:0x0042] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0051 A[SYNTHETIC, Splitter:B:41:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x005b A[SYNTHETIC, Splitter:B:46:0x005b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getAsObject(java.lang.String r5) {
        /*
            r4 = this;
            byte[] r5 = r4.getAsBinary(r5)
            r0 = 0
            if (r5 == 0) goto L_0x0064
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            r1.<init>(r5)     // Catch:{ Exception -> 0x0030, all -> 0x002d }
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x002a, all -> 0x0028 }
            r5.<init>(r1)     // Catch:{ Exception -> 0x002a, all -> 0x0028 }
            java.lang.Object r0 = r5.readObject()     // Catch:{ Exception -> 0x0026 }
            r1.close()     // Catch:{ IOException -> 0x0019 }
            goto L_0x001d
        L_0x0019:
            r1 = move-exception
            r1.printStackTrace()
        L_0x001d:
            r5.close()     // Catch:{ IOException -> 0x0021 }
            goto L_0x0025
        L_0x0021:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0025:
            return r0
        L_0x0026:
            r2 = move-exception
            goto L_0x0033
        L_0x0028:
            r5 = move-exception
            goto L_0x004f
        L_0x002a:
            r2 = move-exception
            r5 = r0
            goto L_0x0033
        L_0x002d:
            r5 = move-exception
            r1 = r0
            goto L_0x004f
        L_0x0030:
            r2 = move-exception
            r5 = r0
            r1 = r5
        L_0x0033:
            r2.printStackTrace()     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x0040
            r1.close()     // Catch:{ IOException -> 0x003c }
            goto L_0x0040
        L_0x003c:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0040:
            if (r5 == 0) goto L_0x004a
            r5.close()     // Catch:{ IOException -> 0x0046 }
            goto L_0x004a
        L_0x0046:
            r5 = move-exception
            r5.printStackTrace()
        L_0x004a:
            return r0
        L_0x004b:
            r0 = move-exception
            r3 = r0
            r0 = r5
            r5 = r3
        L_0x004f:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0059:
            if (r0 == 0) goto L_0x0063
            r0.close()     // Catch:{ IOException -> 0x005f }
            goto L_0x0063
        L_0x005f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0063:
            throw r5
        L_0x0064:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.ACache.getAsObject(java.lang.String):java.lang.Object");
    }

    public void put(String str, Bitmap bitmap) {
        put(str, Utils.Bitmap2Bytes(bitmap));
    }

    public void put(String str, Bitmap bitmap, int i) {
        put(str, Utils.Bitmap2Bytes(bitmap), i);
    }

    public Bitmap getAsBitmap(String str) {
        if (getAsBinary(str) == null) {
            return null;
        }
        return Utils.Bytes2Bimap(getAsBinary(str));
    }

    public void put(String str, Drawable drawable) {
        put(str, Utils.drawable2Bitmap(drawable));
    }

    public void put(String str, Drawable drawable, int i) {
        put(str, Utils.drawable2Bitmap(drawable), i);
    }

    public Drawable getAsDrawable(String str) {
        if (getAsBinary(str) == null) {
            return null;
        }
        return Utils.bitmap2Drawable(Utils.Bytes2Bimap(getAsBinary(str)));
    }

    public File file(String str) {
        File access$100 = this.mCache.newFile(str);
        if (access$100.exists()) {
            return access$100;
        }
        return null;
    }

    public boolean remove(String str) {
        return this.mCache.remove(str);
    }

    public void clear() {
        this.mCache.clear();
    }

    public class ACacheManager {
        /* access modifiers changed from: private */
        public final AtomicInteger cacheCount;
        protected File cacheDir;
        /* access modifiers changed from: private */
        public final AtomicLong cacheSize;
        private final int countLimit;
        /* access modifiers changed from: private */
        public final Map<File, Long> lastUsageDates;
        private final long sizeLimit;

        private ACacheManager(File file, long j, int i) {
            this.lastUsageDates = Collections.synchronizedMap(new HashMap());
            this.cacheDir = file;
            this.sizeLimit = j;
            this.countLimit = i;
            this.cacheSize = new AtomicLong();
            this.cacheCount = new AtomicInteger();
            calculateCacheSizeAndCacheCount();
        }

        private void calculateCacheSizeAndCacheCount() {
            new Thread(new Runnable() {
                public void run() {
                    File[] listFiles = ACacheManager.this.cacheDir.listFiles();
                    if (listFiles != null) {
                        int i = 0;
                        int i2 = 0;
                        for (File file : listFiles) {
                            i = (int) (((long) i) + ACacheManager.this.calculateSize(file));
                            i2++;
                            ACacheManager.this.lastUsageDates.put(file, Long.valueOf(file.lastModified()));
                        }
                        ACacheManager.this.cacheSize.set((long) i);
                        ACacheManager.this.cacheCount.set(i2);
                    }
                }
            }).start();
        }

        /* access modifiers changed from: private */
        public void put(File file) {
            int i = this.cacheCount.get();
            while (i + 1 > this.countLimit) {
                this.cacheSize.addAndGet(-removeNext());
                i = this.cacheCount.addAndGet(-1);
            }
            this.cacheCount.addAndGet(1);
            long calculateSize = calculateSize(file);
            long j = this.cacheSize.get();
            while (j + calculateSize > this.sizeLimit) {
                j = this.cacheSize.addAndGet(-removeNext());
            }
            this.cacheSize.addAndGet(calculateSize);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            file.setLastModified(valueOf.longValue());
            this.lastUsageDates.put(file, valueOf);
        }

        /* access modifiers changed from: private */
        public File get(String str) {
            File newFile = newFile(str);
            Long valueOf = Long.valueOf(System.currentTimeMillis());
            newFile.setLastModified(valueOf.longValue());
            this.lastUsageDates.put(newFile, valueOf);
            return newFile;
        }

        /* access modifiers changed from: private */
        public File newFile(String str) {
            File file = this.cacheDir;
            return new File(file, str.hashCode() + "");
        }

        /* access modifiers changed from: private */
        public boolean remove(String str) {
            return get(str).delete();
        }

        /* access modifiers changed from: private */
        public void clear() {
            this.lastUsageDates.clear();
            this.cacheSize.set(0);
            File[] listFiles = this.cacheDir.listFiles();
            if (listFiles != null) {
                for (File delete : listFiles) {
                    delete.delete();
                }
            }
        }

        private long removeNext() {
            File file;
            if (this.lastUsageDates.isEmpty()) {
                return 0;
            }
            Set<Map.Entry<File, Long>> entrySet = this.lastUsageDates.entrySet();
            synchronized (this.lastUsageDates) {
                file = null;
                Long l = null;
                for (Map.Entry next : entrySet) {
                    if (file == null) {
                        file = (File) next.getKey();
                        l = (Long) next.getValue();
                    } else {
                        Long l2 = (Long) next.getValue();
                        if (l2.longValue() < l.longValue()) {
                            file = (File) next.getKey();
                            l = l2;
                        }
                    }
                }
            }
            long calculateSize = calculateSize(file);
            if (file.delete()) {
                this.lastUsageDates.remove(file);
            }
            return calculateSize;
        }

        /* access modifiers changed from: private */
        public long calculateSize(File file) {
            return file.length();
        }
    }

    private static class Utils {
        private static final char mSeparator = ' ';

        private Utils() {
        }

        /* access modifiers changed from: private */
        public static boolean isDue(String str) {
            return isDue(str.getBytes());
        }

        /* access modifiers changed from: private */
        public static boolean isDue(byte[] bArr) {
            String[] dateInfoFromDate = getDateInfoFromDate(bArr);
            if (dateInfoFromDate != null && dateInfoFromDate.length == 2) {
                String str = dateInfoFromDate[0];
                while (str.startsWith("0")) {
                    str = str.substring(1, str.length());
                }
                if (System.currentTimeMillis() > Long.valueOf(str).longValue() + (Long.valueOf(dateInfoFromDate[1]).longValue() * 1000)) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        public static String newStringWithDateInfo(int i, String str) {
            return createDateInfo(i) + str;
        }

        /* access modifiers changed from: private */
        public static byte[] newByteArrayWithDateInfo(int i, byte[] bArr) {
            byte[] bytes = createDateInfo(i).getBytes();
            byte[] bArr2 = new byte[(bytes.length + bArr.length)];
            System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
            System.arraycopy(bArr, 0, bArr2, bytes.length, bArr.length);
            return bArr2;
        }

        /* access modifiers changed from: private */
        public static String clearDateInfo(String str) {
            return (str == null || !hasDateInfo(str.getBytes())) ? str : str.substring(str.indexOf(32) + 1, str.length());
        }

        /* access modifiers changed from: private */
        public static byte[] clearDateInfo(byte[] bArr) {
            return hasDateInfo(bArr) ? copyOfRange(bArr, indexOf(bArr, mSeparator) + 1, bArr.length) : bArr;
        }

        private static boolean hasDateInfo(byte[] bArr) {
            return bArr != null && bArr.length > 15 && bArr[13] == 45 && indexOf(bArr, mSeparator) > 14;
        }

        private static String[] getDateInfoFromDate(byte[] bArr) {
            if (!hasDateInfo(bArr)) {
                return null;
            }
            return new String[]{new String(copyOfRange(bArr, 0, 13)), new String(copyOfRange(bArr, 14, indexOf(bArr, mSeparator)))};
        }

        private static int indexOf(byte[] bArr, char c) {
            for (int i = 0; i < bArr.length; i++) {
                if (bArr[i] == c) {
                    return i;
                }
            }
            return -1;
        }

        private static byte[] copyOfRange(byte[] bArr, int i, int i2) {
            int i3 = i2 - i;
            if (i3 >= 0) {
                byte[] bArr2 = new byte[i3];
                System.arraycopy(bArr, i, bArr2, 0, Math.min(bArr.length - i, i3));
                return bArr2;
            }
            throw new IllegalArgumentException(i + " > " + i2);
        }

        private static String createDateInfo(int i) {
            String str = System.currentTimeMillis() + "";
            while (str.length() < 13) {
                str = "0" + str;
            }
            return str + "-" + i + mSeparator;
        }

        /* access modifiers changed from: private */
        public static byte[] Bitmap2Bytes(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        /* access modifiers changed from: private */
        public static Bitmap Bytes2Bimap(byte[] bArr) {
            if (bArr.length == 0) {
                return null;
            }
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }

        /* access modifiers changed from: private */
        public static Bitmap drawable2Bitmap(Drawable drawable) {
            if (drawable == null) {
                return null;
            }
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
            drawable.draw(canvas);
            return createBitmap;
        }

        /* access modifiers changed from: private */
        public static Drawable bitmap2Drawable(Bitmap bitmap) {
            if (bitmap == null) {
                return null;
            }
            return new BitmapDrawable(bitmap);
        }
    }
}
