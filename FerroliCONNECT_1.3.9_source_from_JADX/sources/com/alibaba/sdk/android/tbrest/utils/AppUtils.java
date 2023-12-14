package com.alibaba.sdk.android.tbrest.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import com.alibaba.sdk.android.tool.ProcessUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.android.agoo.common.AgooConstants;

public class AppUtils {

    public interface ReaderListener {
        boolean onReadLine(String str);
    }

    public static String getMyProcessNameByAppProcessInfo(Context context) {
        if (context == null) {
            return null;
        }
        try {
            return ProcessUtils.getProcessName(context);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String getGMT8Time(long j) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            return simpleDateFormat.format(new Date(j));
        } catch (Exception e) {
            LogUtil.m1031e("getGMT8Time", e);
            return "";
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogUtil.m1031e("close.", e);
            }
        }
    }

    public static String getMyProcessNameByCmdline() {
        try {
            return readLine("/proc/self/cmdline").trim();
        } catch (Exception e) {
            LogUtil.m1031e("get my process name by cmd line failure .", e);
            return null;
        }
    }

    public static String getMyStatus() {
        return readFully(new File("/proc/self/status")).trim();
    }

    public static String getMeminfo() {
        return readFully(new File("/proc/meminfo")).trim();
    }

    public static String dumpThread(Thread thread) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(String.format("Thread Name: '%s'\n", new Object[]{thread.getName()}));
            sb.append(String.format("\"%s\" prio=%d tid=%d %s\n", new Object[]{thread.getName(), Integer.valueOf(thread.getPriority()), Long.valueOf(thread.getId()), thread.getState()}));
            StackTraceElement[] stackTrace = thread.getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("\tat %s\n", new Object[]{stackTrace[i].toString()}));
            }
        } catch (Exception e) {
            LogUtil.m1031e("dumpThread", e);
        }
        return sb.toString();
    }

    public static String dumpMeminfo(Context context) {
        String str;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(AgooConstants.OPEN_ACTIIVTY_NAME);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            Integer num = null;
            if (activityManager != null) {
                activityManager.getMemoryInfo(memoryInfo);
                num = Integer.valueOf((int) (memoryInfo.threshold >> 10));
            }
            StringBuilder sb = new StringBuilder();
            sb.append("JavaTotal:");
            sb.append(Runtime.getRuntime().totalMemory());
            sb.append(" JavaFree:");
            sb.append(Runtime.getRuntime().freeMemory());
            sb.append(" NativeHeap:");
            sb.append(Debug.getNativeHeapSize());
            sb.append(" NativeAllocated:");
            sb.append(Debug.getNativeHeapAllocatedSize());
            sb.append(" NativeFree:");
            sb.append(Debug.getNativeHeapFreeSize());
            sb.append(" threshold:");
            if (num != null) {
                str = num + " KB";
            } else {
                str = "not valid";
            }
            sb.append(str);
            return sb.toString();
        } catch (Exception e) {
            LogUtil.m1031e("dumpMeminfo", e);
            return "";
        }
    }

    private static long[] getSizes(String str) {
        long j;
        long j2;
        long j3;
        long j4;
        long[] jArr = {-1, -1, -1};
        try {
            StatFs statFs = new StatFs(str);
            if (Build.VERSION.SDK_INT < 18) {
                j4 = (long) statFs.getBlockSize();
                j3 = (long) statFs.getBlockCount();
                j2 = (long) statFs.getFreeBlocks();
                j = (long) statFs.getAvailableBlocks();
            } else {
                j4 = statFs.getBlockSizeLong();
                j3 = statFs.getBlockCountLong();
                j2 = statFs.getFreeBlocksLong();
                j = statFs.getAvailableBlocksLong();
            }
            jArr[0] = j3 * j4;
            jArr[1] = j2 * j4;
            jArr[2] = j4 * j;
        } catch (Exception e) {
            LogUtil.m1031e("getSizes", e);
        }
        return jArr;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x008d A[Catch:{ Exception -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00cb A[Catch:{ Exception -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0109 A[Catch:{ Exception -> 0x0142 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String dumpStorage(android.content.Context r11) {
        /*
            java.lang.String r0 = "TotalSize: %s FreeSize: %s AvailableSize: %s \n"
            java.lang.String r1 = " "
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = 0
            java.lang.String r4 = "mounted"
            java.lang.String r5 = android.os.Environment.getExternalStorageState()     // Catch:{ Exception -> 0x0015 }
            boolean r4 = r4.equals(r5)     // Catch:{ Exception -> 0x0015 }
            goto L_0x001c
        L_0x0015:
            r4 = move-exception
            java.lang.String r5 = "hasSDCard"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1033w(r5, r4)
            r4 = 0
        L_0x001c:
            r5 = 1
            android.content.pm.ApplicationInfo r11 = r11.getApplicationInfo()     // Catch:{ Exception -> 0x002a }
            int r11 = r11.flags     // Catch:{ Exception -> 0x002a }
            r6 = 262144(0x40000, float:3.67342E-40)
            r11 = r11 & r6
            if (r11 == 0) goto L_0x0030
            r11 = 1
            goto L_0x0031
        L_0x002a:
            r11 = move-exception
            java.lang.String r6 = "installSDCard"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1033w(r6, r11)
        L_0x0030:
            r11 = 0
        L_0x0031:
            java.lang.String r6 = "hasSDCard: "
            r2.append(r6)
            r2.append(r4)
            java.lang.String r4 = "\n"
            r2.append(r4)
            java.lang.String r6 = "installSDCard: "
            r2.append(r6)
            r2.append(r11)
            r2.append(r4)
            java.io.File r11 = android.os.Environment.getRootDirectory()     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = r11.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            long[] r4 = getSizes(r4)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r6 = "RootDirectory: "
            r2.append(r6)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r11 = r11.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            r2.append(r11)     // Catch:{ Exception -> 0x0142 }
            r2.append(r1)     // Catch:{ Exception -> 0x0142 }
            r11 = 3
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0142 }
            r7 = r4[r3]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x0142 }
            r6[r3] = r7     // Catch:{ Exception -> 0x0142 }
            r7 = r4[r5]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x0142 }
            r6[r5] = r7     // Catch:{ Exception -> 0x0142 }
            r7 = 2
            r8 = r4[r7]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r4 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x0142 }
            r6[r7] = r4     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = java.lang.String.format(r0, r6)     // Catch:{ Exception -> 0x0142 }
            r2.append(r4)     // Catch:{ Exception -> 0x0142 }
            java.io.File r4 = android.os.Environment.getDataDirectory()     // Catch:{ Exception -> 0x0142 }
            if (r4 == 0) goto L_0x00c5
            java.lang.String r6 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            long[] r6 = getSizes(r6)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r8 = "DataDirectory: "
            r2.append(r8)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r8 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            r2.append(r8)     // Catch:{ Exception -> 0x0142 }
            r2.append(r1)     // Catch:{ Exception -> 0x0142 }
            java.lang.Object[] r8 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0142 }
            r9 = r6[r3]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ Exception -> 0x0142 }
            r8[r3] = r9     // Catch:{ Exception -> 0x0142 }
            r9 = r6[r5]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ Exception -> 0x0142 }
            r8[r5] = r9     // Catch:{ Exception -> 0x0142 }
            r9 = r6[r7]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r6 = java.lang.Long.valueOf(r9)     // Catch:{ Exception -> 0x0142 }
            r8[r7] = r6     // Catch:{ Exception -> 0x0142 }
            java.lang.String r6 = java.lang.String.format(r0, r8)     // Catch:{ Exception -> 0x0142 }
            r2.append(r6)     // Catch:{ Exception -> 0x0142 }
        L_0x00c5:
            java.io.File r6 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0142 }
            if (r4 == 0) goto L_0x0103
            java.lang.String r4 = "ExternalStorageDirectory: "
            r2.append(r4)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            r2.append(r4)     // Catch:{ Exception -> 0x0142 }
            r2.append(r1)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = r6.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            long[] r4 = getSizes(r4)     // Catch:{ Exception -> 0x0142 }
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0142 }
            r8 = r4[r3]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x0142 }
            r6[r3] = r8     // Catch:{ Exception -> 0x0142 }
            r8 = r4[r5]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x0142 }
            r6[r5] = r8     // Catch:{ Exception -> 0x0142 }
            r8 = r4[r7]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r4 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x0142 }
            r6[r7] = r4     // Catch:{ Exception -> 0x0142 }
            java.lang.String r4 = java.lang.String.format(r0, r6)     // Catch:{ Exception -> 0x0142 }
            r2.append(r4)     // Catch:{ Exception -> 0x0142 }
        L_0x0103:
            java.io.File r4 = android.os.Environment.getDownloadCacheDirectory()     // Catch:{ Exception -> 0x0142 }
            if (r4 == 0) goto L_0x0148
            java.lang.String r6 = "DownloadCacheDirectory: "
            r2.append(r6)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r6 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            r2.append(r6)     // Catch:{ Exception -> 0x0142 }
            r2.append(r1)     // Catch:{ Exception -> 0x0142 }
            java.lang.String r1 = r4.getAbsolutePath()     // Catch:{ Exception -> 0x0142 }
            long[] r1 = getSizes(r1)     // Catch:{ Exception -> 0x0142 }
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0142 }
            r8 = r1[r3]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r4 = java.lang.Long.valueOf(r8)     // Catch:{ Exception -> 0x0142 }
            r11[r3] = r4     // Catch:{ Exception -> 0x0142 }
            r3 = r1[r5]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch:{ Exception -> 0x0142 }
            r11[r5] = r3     // Catch:{ Exception -> 0x0142 }
            r3 = r1[r7]     // Catch:{ Exception -> 0x0142 }
            java.lang.Long r1 = java.lang.Long.valueOf(r3)     // Catch:{ Exception -> 0x0142 }
            r11[r7] = r1     // Catch:{ Exception -> 0x0142 }
            java.lang.String r11 = java.lang.String.format(r0, r11)     // Catch:{ Exception -> 0x0142 }
            r2.append(r11)     // Catch:{ Exception -> 0x0142 }
            goto L_0x0148
        L_0x0142:
            r11 = move-exception
            java.lang.String r0 = "getSizes"
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r0, r11)
        L_0x0148:
            java.lang.String r11 = r2.toString()
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.AppUtils.dumpStorage(android.content.Context):java.lang.String");
    }

    public static Boolean isBackgroundRunning(Context context) {
        try {
            if (Integer.parseInt(readLine("/proc/self/oom_adj").trim()) == 0) {
                return false;
            }
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean writeFile(File file, String str) {
        return writeFile(file, str, false);
    }

    public static boolean writeFile(File file, String str, boolean z) {
        FileWriter fileWriter = null;
        try {
            FileWriter fileWriter2 = new FileWriter(file, z);
            try {
                fileWriter2.write(str);
                fileWriter2.flush();
                closeQuietly(fileWriter2);
                return true;
            } catch (IOException e) {
                e = e;
                fileWriter = fileWriter2;
                try {
                    LogUtil.m1031e("writeFile", e);
                    closeQuietly(fileWriter);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(fileWriter);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileWriter = fileWriter2;
                closeQuietly(fileWriter);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            LogUtil.m1031e("writeFile", e);
            closeQuietly(fileWriter);
            return false;
        }
    }

    public static String readLine(String str) {
        return readLine(new File(str));
    }

    public static String readLine(File file) {
        List<String> readLines = readLines(file, 1);
        return !readLines.isEmpty() ? readLines.get(0) : "";
    }

    public static List<String> readLines(File file, int i) {
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int i2 = 0;
            while (true) {
                try {
                    String readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    i2++;
                    arrayList.add(readLine);
                    if (i > 0 && i2 >= i) {
                        break;
                    }
                } catch (IOException e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    try {
                        LogUtil.m1031e("readLine", e);
                        closeQuietly(bufferedReader);
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        closeQuietly(bufferedReader);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    closeQuietly(bufferedReader);
                    throw th;
                }
            }
            closeQuietly(bufferedReader2);
        } catch (IOException e2) {
            e = e2;
            LogUtil.m1031e("readLine", e);
            closeQuietly(bufferedReader);
            return arrayList;
        }
        return arrayList;
    }

    public static String readLineAndDel(File file) {
        try {
            String readLine = readLine(file);
            file.delete();
            return readLine;
        } catch (Exception e) {
            LogUtil.m1031e("readLineAndDel", e);
            return null;
        }
    }

    public static void readLine(File file, ReaderListener readerListener) {
        String readLine;
        BufferedReader bufferedReader = null;
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            do {
                try {
                    readLine = bufferedReader2.readLine();
                    if (readLine == null) {
                        closeQuietly(bufferedReader2);
                        return;
                    }
                } catch (IOException e) {
                    e = e;
                    bufferedReader = bufferedReader2;
                    try {
                        LogUtil.m1031e("readLine", e);
                        closeQuietly(bufferedReader);
                    } catch (Throwable th) {
                        th = th;
                        closeQuietly(bufferedReader);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    closeQuietly(bufferedReader);
                    throw th;
                }
            } while (!readerListener.onReadLine(readLine));
            closeQuietly(bufferedReader2);
        } catch (IOException e2) {
            e = e2;
            LogUtil.m1031e("readLine", e);
            closeQuietly(bufferedReader);
        }
    }

    public static String readFully(File file) {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
            } catch (Exception e) {
                e = e;
                try {
                    LogUtil.m1031e("readFully.", e);
                    closeQuietly(inputStreamReader2);
                    closeQuietly(fileInputStream);
                    return sb.toString();
                } catch (Throwable th) {
                    th = th;
                    closeQuietly(inputStreamReader2);
                    closeQuietly(fileInputStream);
                    throw th;
                }
            }
            try {
                char[] cArr = new char[4096];
                while (true) {
                    int read = inputStreamReader.read(cArr);
                    if (-1 == read) {
                        break;
                    }
                    sb.append(cArr, 0, read);
                }
                closeQuietly(inputStreamReader);
            } catch (Exception e2) {
                Exception exc = e2;
                inputStreamReader2 = inputStreamReader;
                e = exc;
                LogUtil.m1031e("readFully.", e);
                closeQuietly(inputStreamReader2);
                closeQuietly(fileInputStream);
                return sb.toString();
            } catch (Throwable th2) {
                th = th2;
                inputStreamReader2 = inputStreamReader;
                closeQuietly(inputStreamReader2);
                closeQuietly(fileInputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileInputStream = null;
            LogUtil.m1031e("readFully.", e);
            closeQuietly(inputStreamReader2);
            closeQuietly(fileInputStream);
            return sb.toString();
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            closeQuietly(inputStreamReader2);
            closeQuietly(fileInputStream);
            throw th;
        }
        closeQuietly(fileInputStream);
        return sb.toString();
    }
}
