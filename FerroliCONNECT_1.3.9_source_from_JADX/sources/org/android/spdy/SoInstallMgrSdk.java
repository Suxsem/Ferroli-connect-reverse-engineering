package org.android.spdy;

import android.content.Context;
import android.os.Build;
import java.io.File;
import java.lang.reflect.Method;

public class SoInstallMgrSdk {
    private static final String ARMEABI = "armeabi";
    private static final int EventID_SO_INIT = 21033;
    static final String LOGTAG = "INIT_SO";
    private static final String MIPS = "mips";
    private static final String X86 = "x86";
    static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static boolean initSo(String str, int i) {
        return initSo(str, i, (ClassLoader) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x003d A[SYNTHETIC, Splitter:B:13:0x003d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean initSo(java.lang.String r7, int r8, java.lang.ClassLoader r9) {
        /*
            r0 = 1
            r1 = 0
            if (r9 != 0) goto L_0x0008
            java.lang.System.loadLibrary(r7)     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            goto L_0x003b
        L_0x0008:
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            r3 = 2
            java.lang.Class[] r4 = new java.lang.Class[r3]     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r1] = r5     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            java.lang.Class<java.lang.ClassLoader> r5 = java.lang.ClassLoader.class
            r4[r0] = r5     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            java.lang.Class<java.lang.Runtime> r5 = java.lang.Runtime.class
            java.lang.String r6 = "loadLibrary"
            java.lang.reflect.Method r4 = r5.getDeclaredMethod(r6, r4)     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            r4.setAccessible(r0)     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            r3[r1] = r7     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            r3[r0] = r9     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            r4.invoke(r2, r3)     // Catch:{ Exception -> 0x0036, UnsatisfiedLinkError -> 0x0031, Error -> 0x002c }
            goto L_0x003b
        L_0x002c:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x003a
        L_0x0031:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x003a
        L_0x0036:
            r0 = move-exception
            r0.printStackTrace()
        L_0x003a:
            r0 = 0
        L_0x003b:
            if (r0 != 0) goto L_0x0081
            boolean r2 = isExist(r7, r8)     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            if (r2 == 0) goto L_0x004d
            boolean r2 = _loadUnzipSo(r7, r8, r9)     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            if (r2 == 0) goto L_0x004a
            return r2
        L_0x004a:
            removeSoIfExit(r7, r8)     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
        L_0x004d:
            java.lang.String r2 = _cpuType()     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            java.lang.String r3 = "mips"
            boolean r3 = r2.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            if (r3 != 0) goto L_0x0081
            java.lang.String r3 = "x86"
            boolean r2 = r2.equalsIgnoreCase(r3)     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            if (r2 == 0) goto L_0x0062
            goto L_0x0081
        L_0x0062:
            boolean r7 = unZipSelectedFiles(r7, r8, r9)     // Catch:{ ZipException -> 0x006d, IOException -> 0x0068 }
            r1 = r7
            goto L_0x0082
        L_0x0068:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            goto L_0x0081
        L_0x006d:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Exception -> 0x007c, UnsatisfiedLinkError -> 0x0077, Error -> 0x0072 }
            goto L_0x0081
        L_0x0072:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x0082
        L_0x0077:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x0082
        L_0x007c:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x0082
        L_0x0081:
            r1 = r0
        L_0x0082:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SoInstallMgrSdk.initSo(java.lang.String, int, java.lang.ClassLoader):boolean");
    }

    private static String _getFieldReflectively(Build build, String str) {
        try {
            return Build.class.getField(str).get(build).toString();
        } catch (Exception unused) {
            return "Unknown";
        }
    }

    private static String _cpuType() {
        String _getFieldReflectively = _getFieldReflectively(new Build(), "CPU_ABI");
        if (_getFieldReflectively == null || _getFieldReflectively.length() == 0 || _getFieldReflectively.equals("Unknown")) {
            _getFieldReflectively = ARMEABI;
        }
        return _getFieldReflectively.toLowerCase();
    }

    static String _targetSoFile(String str, int i) {
        Context context = mContext;
        if (context == null) {
            return "";
        }
        String str2 = "/data/data/" + context.getPackageName() + "/files";
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            str2 = filesDir.getPath();
        }
        return str2 + "/lib" + str + "bk" + i + ".so";
    }

    static void removeSoIfExit(String str, int i) {
        File file = new File(_targetSoFile(str, i));
        if (file.exists()) {
            file.delete();
        }
    }

    static boolean isExist(String str, int i) {
        return new File(_targetSoFile(str, i)).exists();
    }

    static boolean _loadUnzipSo(String str, int i, ClassLoader classLoader) {
        try {
            if (isExist(str, i)) {
                if (classLoader == null) {
                    System.load(_targetSoFile(str, i));
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    Method declaredMethod = Runtime.class.getDeclaredMethod("load", new Class[]{String.class, ClassLoader.class});
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(runtime, new Object[]{_targetSoFile(str, i), classLoader});
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            return false;
        } catch (Error e3) {
            e3.printStackTrace();
            return false;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.nio.channels.FileChannel} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.io.FileOutputStream} */
    /* JADX WARNING: type inference failed for: r1v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00d6 A[SYNTHETIC, Splitter:B:60:0x00d6] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00e0 A[SYNTHETIC, Splitter:B:66:0x00e0] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00ea A[SYNTHETIC, Splitter:B:72:0x00ea] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean unZipSelectedFiles(java.lang.String r9, int r10, java.lang.ClassLoader r11) throws java.util.zip.ZipException, java.io.IOException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "lib/armeabi/lib"
            r0.append(r1)
            r0.append(r9)
            java.lang.String r1 = ".so"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r2 = 0
            java.lang.String r3 = ""
            android.content.Context r4 = mContext     // Catch:{ IOException -> 0x00f7 }
            if (r4 != 0) goto L_0x001e
            return r2
        L_0x001e:
            android.content.pm.ApplicationInfo r5 = r4.getApplicationInfo()     // Catch:{ IOException -> 0x00f7 }
            if (r5 == 0) goto L_0x0026
            java.lang.String r3 = r5.sourceDir     // Catch:{ IOException -> 0x00f7 }
        L_0x0026:
            java.util.zip.ZipFile r5 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x00f7 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x00f7 }
            java.util.Enumeration r3 = r5.entries()     // Catch:{ IOException -> 0x00f7 }
        L_0x002f:
            boolean r6 = r3.hasMoreElements()     // Catch:{ IOException -> 0x00f7 }
            if (r6 == 0) goto L_0x00fb
            java.lang.Object r6 = r3.nextElement()     // Catch:{ IOException -> 0x00f7 }
            java.util.zip.ZipEntry r6 = (java.util.zip.ZipEntry) r6     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r7 = r6.getName()     // Catch:{ IOException -> 0x00f7 }
            java.lang.String r8 = ".."
            boolean r8 = r7.contains(r8)     // Catch:{ IOException -> 0x00f7 }
            if (r8 != 0) goto L_0x00f6
            java.lang.String r8 = "\\"
            boolean r8 = r7.contains(r8)     // Catch:{ IOException -> 0x00f7 }
            if (r8 != 0) goto L_0x00f6
            java.lang.String r8 = "%"
            boolean r7 = r7.contains(r8)     // Catch:{ IOException -> 0x00f7 }
            if (r7 == 0) goto L_0x0059
            goto L_0x00f6
        L_0x0059:
            java.lang.String r7 = r6.getName()     // Catch:{ IOException -> 0x00f7 }
            boolean r7 = r7.startsWith(r0)     // Catch:{ IOException -> 0x00f7 }
            if (r7 == 0) goto L_0x002f
            r0 = 0
            removeSoIfExit(r9, r10)     // Catch:{ all -> 0x00d1 }
            java.io.InputStream r3 = r5.getInputStream(r6)     // Catch:{ all -> 0x00d1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ce }
            r6.<init>()     // Catch:{ all -> 0x00ce }
            java.lang.String r7 = "lib"
            r6.append(r7)     // Catch:{ all -> 0x00ce }
            r6.append(r9)     // Catch:{ all -> 0x00ce }
            java.lang.String r7 = "bk"
            r6.append(r7)     // Catch:{ all -> 0x00ce }
            r6.append(r10)     // Catch:{ all -> 0x00ce }
            r6.append(r1)     // Catch:{ all -> 0x00ce }
            java.lang.String r1 = r6.toString()     // Catch:{ all -> 0x00ce }
            java.io.FileOutputStream r1 = r4.openFileOutput(r1, r2)     // Catch:{ all -> 0x00ce }
            java.nio.channels.FileChannel r0 = r1.getChannel()     // Catch:{ all -> 0x00cc }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x00cc }
            r6 = 0
        L_0x0094:
            int r7 = r3.read(r4)     // Catch:{ all -> 0x00cc }
            if (r7 <= 0) goto L_0x00a3
            java.nio.ByteBuffer r8 = java.nio.ByteBuffer.wrap(r4, r2, r7)     // Catch:{ all -> 0x00cc }
            r0.write(r8)     // Catch:{ all -> 0x00cc }
            int r6 = r6 + r7
            goto L_0x0094
        L_0x00a3:
            if (r3 == 0) goto L_0x00ad
            r3.close()     // Catch:{ Exception -> 0x00a9 }
            goto L_0x00ad
        L_0x00a9:
            r3 = move-exception
            r3.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00ad:
            if (r0 == 0) goto L_0x00b7
            r0.close()     // Catch:{ Exception -> 0x00b3 }
            goto L_0x00b7
        L_0x00b3:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00b7:
            if (r1 == 0) goto L_0x00c1
            r1.close()     // Catch:{ Exception -> 0x00bd }
            goto L_0x00c1
        L_0x00bd:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00c1:
            r5.close()     // Catch:{ IOException -> 0x00f7 }
            if (r6 <= 0) goto L_0x00cb
            boolean r9 = _loadUnzipSo(r9, r10, r11)     // Catch:{ IOException -> 0x00f7 }
            return r9
        L_0x00cb:
            return r2
        L_0x00cc:
            r9 = move-exception
            goto L_0x00d4
        L_0x00ce:
            r9 = move-exception
            r1 = r0
            goto L_0x00d4
        L_0x00d1:
            r9 = move-exception
            r1 = r0
            r3 = r1
        L_0x00d4:
            if (r3 == 0) goto L_0x00de
            r3.close()     // Catch:{ Exception -> 0x00da }
            goto L_0x00de
        L_0x00da:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00de:
            if (r0 == 0) goto L_0x00e8
            r0.close()     // Catch:{ Exception -> 0x00e4 }
            goto L_0x00e8
        L_0x00e4:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00e8:
            if (r1 == 0) goto L_0x00f2
            r1.close()     // Catch:{ Exception -> 0x00ee }
            goto L_0x00f2
        L_0x00ee:
            r10 = move-exception
            r10.printStackTrace()     // Catch:{ IOException -> 0x00f7 }
        L_0x00f2:
            r5.close()     // Catch:{ IOException -> 0x00f7 }
            throw r9     // Catch:{ IOException -> 0x00f7 }
        L_0x00f6:
            return r2
        L_0x00f7:
            r9 = move-exception
            r9.printStackTrace()
        L_0x00fb:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SoInstallMgrSdk.unZipSelectedFiles(java.lang.String, int, java.lang.ClassLoader):boolean");
    }
}
