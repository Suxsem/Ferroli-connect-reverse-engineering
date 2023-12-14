package com.alibaba.sdk.android.tbrest.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.p092ta.utdid2.device.UTDevice;
import com.p107tb.appyunsdk.Constant;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Random;
import org.eclipse.jetty.util.security.Constraint;

public class DeviceUtils {
    private static final String[] ARRAY_OF_STRING = {NETWORK_CLASS_UNKNOWN, NETWORK_CLASS_UNKNOWN};
    private static final String NETWORK_CLASS_2_G = "2G";
    private static final String NETWORK_CLASS_3_G = "3G";
    private static final String NETWORK_CLASS_4_G = "4G";
    private static final String NETWORK_CLASS_5_G = "5G";
    private static final String NETWORK_CLASS_UNKNOWN = "Unknown";
    public static final String NETWORK_CLASS_WIFI = "Wi-Fi";
    private static String carrier;
    private static String cpuName;
    private static String imei = null;
    private static String imsi = null;

    private static String getNetworkClass(int i) {
        if (i == 20) {
            return NETWORK_CLASS_5_G;
        }
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return NETWORK_CLASS_2_G;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return NETWORK_CLASS_3_G;
            case 13:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x005a, code lost:
        if (r0 != null) goto L_0x0037;
     */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x004a A[SYNTHETIC, Splitter:B:29:0x004a] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x004f A[Catch:{ Exception -> 0x0052 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0057 A[SYNTHETIC, Splitter:B:39:0x0057] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getCpuName() {
        /*
            java.lang.String r0 = cpuName
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            java.lang.String r0 = "/proc/cpuinfo"
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ IOException -> 0x0053, all -> 0x0044 }
            r2.<init>(r0)     // Catch:{ IOException -> 0x0053, all -> 0x0044 }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0042, all -> 0x003d }
            r0.<init>(r2)     // Catch:{ IOException -> 0x0042, all -> 0x003d }
        L_0x0012:
            java.lang.String r3 = r0.readLine()     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            if (r3 == 0) goto L_0x0034
            java.lang.String r4 = "Hardware"
            boolean r4 = r3.contains(r4)     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            if (r4 == 0) goto L_0x0012
            java.lang.String r4 = ":"
            java.lang.String[] r3 = r3.split(r4)     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            r4 = 1
            r3 = r3[r4]     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            cpuName = r3     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            java.lang.String r1 = cpuName     // Catch:{ IOException -> 0x0055, all -> 0x003b }
            r2.close()     // Catch:{ Exception -> 0x0033 }
            r0.close()     // Catch:{ Exception -> 0x0033 }
        L_0x0033:
            return r1
        L_0x0034:
            r2.close()     // Catch:{ Exception -> 0x005d }
        L_0x0037:
            r0.close()     // Catch:{ Exception -> 0x005d }
            goto L_0x005d
        L_0x003b:
            r1 = move-exception
            goto L_0x0048
        L_0x003d:
            r0 = move-exception
            r5 = r1
            r1 = r0
            r0 = r5
            goto L_0x0048
        L_0x0042:
            r0 = r1
            goto L_0x0055
        L_0x0044:
            r0 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x0052 }
        L_0x004d:
            if (r0 == 0) goto L_0x0052
            r0.close()     // Catch:{ Exception -> 0x0052 }
        L_0x0052:
            throw r1
        L_0x0053:
            r0 = r1
            r2 = r0
        L_0x0055:
            if (r2 == 0) goto L_0x005a
            r2.close()     // Catch:{ Exception -> 0x005d }
        L_0x005a:
            if (r0 == 0) goto L_0x005d
            goto L_0x0037
        L_0x005d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.utils.DeviceUtils.getCpuName():java.lang.String");
    }

    public static String getCarrier(Context context) {
        try {
            if (carrier != null) {
                return carrier;
            }
            carrier = ((TelephonyManager) context.getSystemService(Constant.PHONE)).getNetworkOperatorName();
            return carrier;
        } catch (Exception unused) {
            return null;
        }
    }

    @SuppressLint({"WrongConstant"})
    public static String[] getNetworkType(Context context) {
        if (context == null) {
            return ARRAY_OF_STRING;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
                return ARRAY_OF_STRING;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return ARRAY_OF_STRING;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return ARRAY_OF_STRING;
            }
            if (activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    ARRAY_OF_STRING[0] = NETWORK_CLASS_WIFI;
                    return ARRAY_OF_STRING;
                } else if (activeNetworkInfo.getType() == 0) {
                    if (isNRConnected((TelephonyManager) context.getSystemService(Constant.PHONE))) {
                        ARRAY_OF_STRING[0] = NETWORK_CLASS_5_G;
                    } else {
                        ARRAY_OF_STRING[0] = getNetworkClass(activeNetworkInfo.getSubtype());
                    }
                    ARRAY_OF_STRING[1] = activeNetworkInfo.getSubtypeName();
                    return ARRAY_OF_STRING;
                }
            }
            return ARRAY_OF_STRING;
        } catch (Throwable unused) {
        }
    }

    private static boolean isNRConnected(TelephonyManager telephonyManager) {
        try {
            Object invoke = Class.forName(telephonyManager.getClass().getName()).getDeclaredMethod("getServiceState", new Class[0]).invoke(telephonyManager, new Object[0]);
            Method[] declaredMethods = Class.forName(invoke.getClass().getName()).getDeclaredMethods();
            int length = declaredMethods.length;
            int i = 0;
            while (i < length) {
                Method method = declaredMethods[i];
                if (!method.getName().equals("getNrStatus")) {
                    if (!method.getName().equals("getNrState")) {
                        i++;
                    }
                }
                method.setAccessible(true);
                if (((Integer) method.invoke(invoke, new Object[0])).intValue() == 3) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    public static String getLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e) {
            LogUtil.m1031e("get country error ", e);
            return null;
        }
    }

    public static String getCountry() {
        try {
            return Locale.getDefault().getCountry();
        } catch (Exception e) {
            LogUtil.m1031e("get country error ", e);
            return null;
        }
    }

    public static String getResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (i > i2) {
                int i3 = i ^ i2;
                i2 ^= i3;
                i = i3 ^ i2;
            }
            return i2 + Constraint.ANY_ROLE + i;
        } catch (Exception e) {
            LogUtil.m1031e("DeviceUtils getResolution: error", e);
            return NETWORK_CLASS_UNKNOWN;
        }
    }

    public static String getUtdid(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Exception e) {
            LogUtil.m1031e("get utdid error ", e);
            return null;
        }
    }

    public static String getImsi(Context context) {
        String str = imsi;
        if (str != null) {
            return str;
        }
        StringUtils.isEmpty(str);
        imsi = getUniqueID();
        return imsi;
    }

    public static String getImei(Context context) {
        String str = imei;
        if (str != null) {
            return str;
        }
        imei = getUniqueID();
        return imei;
    }

    public static byte[] IntGetBytes(int i) {
        byte[] bArr = new byte[4];
        bArr[3] = (byte) (i % 256);
        int i2 = i >> 8;
        bArr[2] = (byte) (i2 % 256);
        int i3 = i2 >> 8;
        bArr[1] = (byte) (i3 % 256);
        bArr[0] = (byte) ((i3 >> 8) % 256);
        return bArr;
    }

    public static String getUniqueID() {
        try {
            int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
            int nanoTime = (int) System.nanoTime();
            int nextInt = new Random().nextInt();
            int nextInt2 = new Random().nextInt();
            byte[] IntGetBytes = IntGetBytes(currentTimeMillis);
            byte[] IntGetBytes2 = IntGetBytes(nanoTime);
            byte[] IntGetBytes3 = IntGetBytes(nextInt);
            byte[] IntGetBytes4 = IntGetBytes(nextInt2);
            byte[] bArr = new byte[16];
            System.arraycopy(IntGetBytes, 0, bArr, 0, 4);
            System.arraycopy(IntGetBytes2, 0, bArr, 4, 4);
            System.arraycopy(IntGetBytes3, 0, bArr, 8, 4);
            System.arraycopy(IntGetBytes4, 0, bArr, 12, 4);
            return Base64.encodeBase64String(bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
