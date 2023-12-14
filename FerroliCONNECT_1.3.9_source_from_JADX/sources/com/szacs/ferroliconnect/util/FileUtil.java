package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import p110io.reactivex.Single;
import p110io.reactivex.SingleEmitter;
import p110io.reactivex.SingleOnSubscribe;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class FileUtil {
    public static final String APP_CACHE_DIR = (Environment.getExternalStorageDirectory() + File.separator + "ferroli");
    public static final String CARSH_CACHE_DIR = (APP_CACHE_DIR + File.separator + "crash");
    public static final String CLOUDWARM_ROOT_PATH = (Environment.getExternalStorageDirectory() + "/Ferroli/");
    private static final String TAG = "FileUtil";

    public static String saveFile(Context context, String str, String str2, Bitmap bitmap) {
        return saveFile(context, "", str, str2, bitmap);
    }

    public static String saveFile(Context context, String str, String str2, String str3, Bitmap bitmap) {
        return saveFile(context, str, str2, str3, bitmapToBytes(bitmap));
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x006c A[Catch:{ Exception -> 0x00ae }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b4 A[SYNTHETIC, Splitter:B:30:0x00b4] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00bb A[SYNTHETIC, Splitter:B:35:0x00bb] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String saveFile(android.content.Context r4, java.lang.String r5, java.lang.String r6, java.lang.String r7, byte[] r8) {
        /*
            java.lang.String r4 = ""
            if (r6 == 0) goto L_0x000e
            java.lang.String r0 = r6.trim()
            int r0 = r0.length()
            if (r0 != 0) goto L_0x0020
        L_0x000e:
            java.text.SimpleDateFormat r6 = new java.text.SimpleDateFormat
            java.util.Locale r0 = java.util.Locale.CHINA
            java.lang.String r1 = "yyyyMMdd"
            r6.<init>(r1, r0)
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            java.lang.String r6 = r6.format(r0)
        L_0x0020:
            r0 = 0
            java.lang.String r1 = "/"
            if (r5 == 0) goto L_0x004d
            java.lang.String r2 = r5.trim()     // Catch:{ Exception -> 0x00ae }
            int r2 = r2.length()     // Catch:{ Exception -> 0x00ae }
            if (r2 != 0) goto L_0x0030
            goto L_0x004d
        L_0x0030:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ae }
            r2.<init>()     // Catch:{ Exception -> 0x00ae }
            java.io.File r3 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x00ae }
            r2.append(r3)     // Catch:{ Exception -> 0x00ae }
            r2.append(r1)     // Catch:{ Exception -> 0x00ae }
            r2.append(r5)     // Catch:{ Exception -> 0x00ae }
            r2.append(r1)     // Catch:{ Exception -> 0x00ae }
            r2.append(r6)     // Catch:{ Exception -> 0x00ae }
            java.lang.String r5 = r2.toString()     // Catch:{ Exception -> 0x00ae }
            goto L_0x0061
        L_0x004d:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ae }
            r5.<init>()     // Catch:{ Exception -> 0x00ae }
            java.lang.String r2 = CLOUDWARM_ROOT_PATH     // Catch:{ Exception -> 0x00ae }
            r5.append(r2)     // Catch:{ Exception -> 0x00ae }
            r5.append(r6)     // Catch:{ Exception -> 0x00ae }
            r5.append(r1)     // Catch:{ Exception -> 0x00ae }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00ae }
        L_0x0061:
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x00ae }
            r6.<init>(r5)     // Catch:{ Exception -> 0x00ae }
            boolean r1 = r6.exists()     // Catch:{ Exception -> 0x00ae }
            if (r1 != 0) goto L_0x006f
            r6.mkdirs()     // Catch:{ Exception -> 0x00ae }
        L_0x006f:
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x00ae }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ae }
            r1.<init>()     // Catch:{ Exception -> 0x00ae }
            r1.append(r7)     // Catch:{ Exception -> 0x00ae }
            r1.append(r4)     // Catch:{ Exception -> 0x00ae }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00ae }
            r6.<init>(r5, r1)     // Catch:{ Exception -> 0x00ae }
            java.lang.String r6 = r6.getPath()     // Catch:{ Exception -> 0x00ae }
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00ae }
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00ae }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00ae }
            r3.<init>()     // Catch:{ Exception -> 0x00ae }
            r3.append(r7)     // Catch:{ Exception -> 0x00ae }
            r3.append(r4)     // Catch:{ Exception -> 0x00ae }
            java.lang.String r7 = r3.toString()     // Catch:{ Exception -> 0x00ae }
            r2.<init>(r5, r7)     // Catch:{ Exception -> 0x00ae }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00ae }
            r1.write(r8)     // Catch:{ Exception -> 0x00ab, all -> 0x00a8 }
            r1.close()     // Catch:{ IOException -> 0x00b7 }
            r4 = r6
            goto L_0x00b7
        L_0x00a8:
            r4 = move-exception
            r0 = r1
            goto L_0x00b9
        L_0x00ab:
            r5 = move-exception
            r0 = r1
            goto L_0x00af
        L_0x00ae:
            r5 = move-exception
        L_0x00af:
            r5.printStackTrace()     // Catch:{ all -> 0x00b8 }
            if (r0 == 0) goto L_0x00b7
            r0.close()     // Catch:{ IOException -> 0x00b7 }
        L_0x00b7:
            return r4
        L_0x00b8:
            r4 = move-exception
        L_0x00b9:
            if (r0 == 0) goto L_0x00be
            r0.close()     // Catch:{ IOException -> 0x00be }
        L_0x00be:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.FileUtil.saveFile(android.content.Context, java.lang.String, java.lang.String, java.lang.String, byte[]):java.lang.String");
    }

    public static String getFileMD5(File file) {
        if (file != null && file.exists() && file.isFile()) {
            byte[] bArr = new byte[1024];
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                FileInputStream fileInputStream = new FileInputStream(file);
                while (true) {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read != -1) {
                        instance.update(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        return new BigInteger(1, instance.digest()).toString(16);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getBitmapMD5(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            while (true) {
                int read = byteArrayInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    byteArrayInputStream.close();
                    byteArrayOutputStream.close();
                    return new BigInteger(1, instance.digest()).toString(16);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getFilesNames(String str, String str2) {
        File[] listFiles;
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                if (!file2.isDirectory()) {
                    String name = file2.getName();
                    if (name.endsWith(str2)) {
                        arrayList.add(name);
                    }
                }
            }
        }
        return arrayList;
    }

    public static File getFile(String str, String str2) {
        File[] listFiles;
        File file = new File(CLOUDWARM_ROOT_PATH + str);
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                if (!file2.isDirectory() && file2.getName().equalsIgnoreCase(str2)) {
                    return file2;
                }
            }
        }
        return null;
    }

    public static File getFile(String str) {
        Log.d("get file", "file path : " + CLOUDWARM_ROOT_PATH + str);
        return new File(CLOUDWARM_ROOT_PATH + str);
    }

    public static File getLatestFile(String str, String str2) {
        File[] listFiles;
        File file = new File(str);
        if (!file.isDirectory() || (listFiles = file.listFiles()) == null || listFiles.length == 0) {
            return null;
        }
        int i = 0;
        for (int i2 = 0; i2 < listFiles.length; i2++) {
            if (!listFiles[i2].isDirectory() && listFiles[i2].getName().endsWith(str2) && listFiles[i2].lastModified() > listFiles[i].lastModified()) {
                i = i2;
            }
        }
        return listFiles[i];
    }

    public static File getFolderLatestImageFile(String str) {
        File latestFile = getLatestFile(CLOUDWARM_ROOT_PATH + str, "jpg");
        if (latestFile == null || !latestFile.exists()) {
            return null;
        }
        return latestFile;
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                for (File deleteFile : file.listFiles()) {
                    deleteFile(deleteFile);
                }
            }
            file.delete();
            return;
        }
        Log.d("delete file", "file doesn't exist");
    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        try {
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void mkdirs(String str) {
        File file = new File(APP_CACHE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(str);
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    public static void deleteFile(final String str, final int i) {
        Single.create(new SingleOnSubscribe<Object>() {
            public void subscribe(SingleEmitter<Object> singleEmitter) throws Exception {
                List access$000 = FileUtil.queryMyFile(str);
                if (access$000 != null && access$000.size() > i) {
                    for (int i = 0; i < access$000.size() - i; i++) {
                        Log.i(FileUtil.TAG, ((File) access$000.get(i)).getName() + "已经删除");
                        ((File) access$000.get(i)).delete();
                    }
                }
            }
        }).subscribeOn(Schedulers.m3877io()).subscribe(new Consumer<Object>() {
            public void accept(Object obj) throws Exception {
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    /* access modifiers changed from: private */
    public static List<File> queryMyFile(String str) {
        File[] listFiles;
        File file = new File(str);
        if (!file.exists() || (listFiles = file.listFiles()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(listFiles));
        Collections.sort(arrayList, new Comparator<File>() {
            public int compare(File file, File file2) {
                if (file.lastModified() > file2.lastModified()) {
                    return 1;
                }
                return file.lastModified() < file2.lastModified() ? -1 : 0;
            }
        });
        return arrayList;
    }
}
