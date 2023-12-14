package anet.channel.util;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.StrategyStatObject;
import java.io.File;
import java.io.Serializable;

/* compiled from: Taobao */
public class SerializeHelper {
    private static final String TAG = "awcn.SerializeHelper";
    private static File cacheDir;

    public static File getCacheFiles(String str) {
        Context context;
        if (cacheDir == null && (context = GlobalAppRuntimeInfo.getContext()) != null) {
            cacheDir = context.getCacheDir();
        }
        return new File(cacheDir, str);
    }

    public static synchronized void persist(Serializable serializable, File file) {
        synchronized (SerializeHelper.class) {
            persist(serializable, file, (StrategyStatObject) null);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:68|(2:70|71)|72|73) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0053, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0054, code lost:
        r11 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0057, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0058, code lost:
        r10 = null;
        r11 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:72:0x0108 */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0053 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:8:0x0017] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006f A[Catch:{ all -> 0x0102 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0076 A[SYNTHETIC, Splitter:B:31:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0081 A[Catch:{ Exception -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0098 A[Catch:{ Exception -> 0x00f7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0105 A[SYNTHETIC, Splitter:B:70:0x0105] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:72:0x0108=Splitter:B:72:0x0108, B:35:0x007a=Splitter:B:35:0x007a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void persist(java.io.Serializable r17, java.io.File r18, anet.channel.statist.StrategyStatObject r19) {
        /*
            r0 = r17
            r1 = r18
            r2 = r19
            java.lang.Class<anet.channel.util.SerializeHelper> r3 = anet.channel.util.SerializeHelper.class
            monitor-enter(r3)
            r4 = 0
            r5 = 0
            if (r0 == 0) goto L_0x0109
            if (r1 != 0) goto L_0x0011
            goto L_0x0109
        L_0x0011:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0114 }
            r8 = 2
            r9 = 1
            java.util.UUID r10 = java.util.UUID.randomUUID()     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            java.lang.String r11 = "-"
            java.lang.String r12 = ""
            java.lang.String r10 = r10.replace(r11, r12)     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            java.io.File r10 = getCacheFiles(r10)     // Catch:{ Exception -> 0x0057, all -> 0x0053 }
            r10.createNewFile()     // Catch:{ Exception -> 0x0050, all -> 0x0053 }
            r10.setReadable(r9)     // Catch:{ Exception -> 0x0050, all -> 0x0053 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0050, all -> 0x0053 }
            r11.<init>(r10)     // Catch:{ Exception -> 0x0050, all -> 0x0053 }
            java.io.ObjectOutputStream r12 = new java.io.ObjectOutputStream     // Catch:{ Exception -> 0x004e }
            java.io.BufferedOutputStream r13 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x004e }
            r13.<init>(r11)     // Catch:{ Exception -> 0x004e }
            r12.<init>(r13)     // Catch:{ Exception -> 0x004e }
            r12.writeObject(r0)     // Catch:{ Exception -> 0x004e }
            r12.flush()     // Catch:{ Exception -> 0x004e }
            r12.close()     // Catch:{ Exception -> 0x004e }
            r11.close()     // Catch:{ IOException -> 0x004c }
        L_0x004c:
            r0 = 1
            goto L_0x007a
        L_0x004e:
            r0 = move-exception
            goto L_0x005a
        L_0x0050:
            r0 = move-exception
            r11 = r4
            goto L_0x005a
        L_0x0053:
            r0 = move-exception
            r11 = r4
            goto L_0x0103
        L_0x0057:
            r0 = move-exception
            r10 = r4
            r11 = r10
        L_0x005a:
            java.lang.String r12 = "awcn.SerializeHelper"
            java.lang.String r13 = "persist fail. "
            java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch:{ all -> 0x0102 }
            java.lang.String r15 = "file"
            r14[r5] = r15     // Catch:{ all -> 0x0102 }
            java.lang.String r15 = r18.getName()     // Catch:{ all -> 0x0102 }
            r14[r9] = r15     // Catch:{ all -> 0x0102 }
            anet.channel.util.ALog.m326e(r12, r13, r4, r0, r14)     // Catch:{ all -> 0x0102 }
            if (r2 == 0) goto L_0x0074
            java.lang.String r12 = "SerializeHelper.persist()"
            r2.appendErrorTrace(r12, r0)     // Catch:{ all -> 0x0102 }
        L_0x0074:
            if (r11 == 0) goto L_0x0079
            r11.close()     // Catch:{ IOException -> 0x0079 }
        L_0x0079:
            r0 = 0
        L_0x007a:
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0114 }
            long r11 = r11 - r6
            if (r2 == 0) goto L_0x0096
            java.lang.String r6 = java.lang.String.valueOf(r10)     // Catch:{ all -> 0x0114 }
            r2.writeTempFilePath = r6     // Catch:{ all -> 0x0114 }
            java.lang.String r6 = java.lang.String.valueOf(r18)     // Catch:{ all -> 0x0114 }
            r2.writeStrategyFilePath = r6     // Catch:{ all -> 0x0114 }
            if (r0 == 0) goto L_0x0091
            r6 = 1
            goto L_0x0092
        L_0x0091:
            r6 = 0
        L_0x0092:
            r2.isTempWriteSucceed = r6     // Catch:{ all -> 0x0114 }
            r2.writeCostTime = r11     // Catch:{ all -> 0x0114 }
        L_0x0096:
            if (r0 == 0) goto L_0x00ee
            boolean r6 = r10.renameTo(r1)     // Catch:{ all -> 0x0114 }
            if (r6 == 0) goto L_0x00ce
            java.lang.String r7 = "awcn.SerializeHelper"
            java.lang.String r13 = "persist end."
            r14 = 6
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ all -> 0x0114 }
            java.lang.String r15 = "file"
            r14[r5] = r15     // Catch:{ all -> 0x0114 }
            java.io.File r15 = r18.getAbsoluteFile()     // Catch:{ all -> 0x0114 }
            r14[r9] = r15     // Catch:{ all -> 0x0114 }
            java.lang.String r15 = "size"
            r14[r8] = r15     // Catch:{ all -> 0x0114 }
            r8 = 3
            long r15 = r18.length()     // Catch:{ all -> 0x0114 }
            java.lang.Long r1 = java.lang.Long.valueOf(r15)     // Catch:{ all -> 0x0114 }
            r14[r8] = r1     // Catch:{ all -> 0x0114 }
            r1 = 4
            java.lang.String r8 = "cost"
            r14[r1] = r8     // Catch:{ all -> 0x0114 }
            r1 = 5
            java.lang.Long r8 = java.lang.Long.valueOf(r11)     // Catch:{ all -> 0x0114 }
            r14[r1] = r8     // Catch:{ all -> 0x0114 }
            anet.channel.util.ALog.m328i(r7, r13, r4, r14)     // Catch:{ all -> 0x0114 }
            goto L_0x00d7
        L_0x00ce:
            java.lang.String r1 = "awcn.SerializeHelper"
            java.lang.String r7 = "rename failed."
            java.lang.Object[] r8 = new java.lang.Object[r5]     // Catch:{ all -> 0x0114 }
            anet.channel.util.ALog.m327e(r1, r7, r4, r8)     // Catch:{ all -> 0x0114 }
        L_0x00d7:
            if (r2 == 0) goto L_0x00ef
            if (r6 == 0) goto L_0x00dd
            r1 = 1
            goto L_0x00de
        L_0x00dd:
            r1 = 0
        L_0x00de:
            r2.isRenameSucceed = r1     // Catch:{ all -> 0x0114 }
            if (r6 == 0) goto L_0x00e3
            goto L_0x00e4
        L_0x00e3:
            r9 = 0
        L_0x00e4:
            r2.isSucceed = r9     // Catch:{ all -> 0x0114 }
            anet.channel.appmonitor.IAppMonitor r1 = anet.channel.appmonitor.AppMonitor.getInstance()     // Catch:{ all -> 0x0114 }
            r1.commitStat(r2)     // Catch:{ all -> 0x0114 }
            goto L_0x00ef
        L_0x00ee:
            r6 = 0
        L_0x00ef:
            if (r0 == 0) goto L_0x00f3
            if (r6 != 0) goto L_0x0100
        L_0x00f3:
            r10.delete()     // Catch:{ Exception -> 0x00f7 }
            goto L_0x0100
        L_0x00f7:
            java.lang.String r0 = "awcn.SerializeHelper"
            java.lang.String r1 = "delete failed."
            java.lang.Object[] r2 = new java.lang.Object[r5]     // Catch:{ all -> 0x0114 }
            anet.channel.util.ALog.m327e(r0, r1, r4, r2)     // Catch:{ all -> 0x0114 }
        L_0x0100:
            monitor-exit(r3)
            return
        L_0x0102:
            r0 = move-exception
        L_0x0103:
            if (r11 == 0) goto L_0x0108
            r11.close()     // Catch:{ IOException -> 0x0108 }
        L_0x0108:
            throw r0     // Catch:{ all -> 0x0114 }
        L_0x0109:
            java.lang.String r0 = "awcn.SerializeHelper"
            java.lang.String r1 = "persist fail. Invalid parameter"
            java.lang.Object[] r2 = new java.lang.Object[r5]     // Catch:{ all -> 0x0114 }
            anet.channel.util.ALog.m327e(r0, r1, r4, r2)     // Catch:{ all -> 0x0114 }
            monitor-exit(r3)
            return
        L_0x0114:
            r0 = move-exception
            monitor-exit(r3)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.SerializeHelper.persist(java.io.Serializable, java.io.File, anet.channel.statist.StrategyStatObject):void");
    }

    public static synchronized <T> T restore(File file) {
        T restore;
        synchronized (SerializeHelper.class) {
            restore = restore(file, (StrategyStatObject) null);
        }
        return restore;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:45|(0)|49|50) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b1, code lost:
        if (r4 != null) goto L_0x008c;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00bc */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a1 A[Catch:{ all -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ac A[Catch:{ all -> 0x00b6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00b9 A[SYNTHETIC, Splitter:B:47:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized <T> T restore(java.io.File r14, anet.channel.statist.StrategyStatObject r15) {
        /*
            java.lang.Class<anet.channel.util.SerializeHelper> r0 = anet.channel.util.SerializeHelper.class
            monitor-enter(r0)
            if (r15 == 0) goto L_0x000f
            java.lang.String r1 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x000c }
            r15.readStrategyFilePath = r1     // Catch:{ all -> 0x000c }
            goto L_0x000f
        L_0x000c:
            r14 = move-exception
            goto L_0x00bd
        L_0x000f:
            r1 = 0
            r2 = 3
            r3 = 0
            boolean r4 = r14.exists()     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            r5 = 2
            r6 = 1
            if (r4 != 0) goto L_0x0035
            boolean r4 = anet.channel.util.ALog.isPrintLog(r2)     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            if (r4 == 0) goto L_0x0033
            java.lang.String r4 = "awcn.SerializeHelper"
            java.lang.String r7 = "file not exist."
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            java.lang.String r8 = "file"
            r5[r1] = r8     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            java.lang.String r14 = r14.getName()     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            r5[r6] = r14     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            anet.channel.util.ALog.m330w(r4, r7, r3, r5)     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
        L_0x0033:
            monitor-exit(r0)
            return r3
        L_0x0035:
            if (r15 == 0) goto L_0x0039
            r15.isFileExists = r6     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
        L_0x0039:
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            r4.<init>(r14)     // Catch:{ Throwable -> 0x0098, all -> 0x0095 }
            java.io.ObjectInputStream r9 = new java.io.ObjectInputStream     // Catch:{ Throwable -> 0x0092 }
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0092 }
            r10.<init>(r4)     // Catch:{ Throwable -> 0x0092 }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0092 }
            java.lang.Object r10 = r9.readObject()     // Catch:{ Throwable -> 0x0092 }
            r9.close()     // Catch:{ Throwable -> 0x0090 }
            long r11 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0090 }
            long r11 = r11 - r7
            if (r15 == 0) goto L_0x005e
            r15.isReadObjectSucceed = r6     // Catch:{ Throwable -> 0x0090 }
            r15.readCostTime = r11     // Catch:{ Throwable -> 0x0090 }
        L_0x005e:
            java.lang.String r7 = "awcn.SerializeHelper"
            java.lang.String r8 = "restore end."
            r9 = 6
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Throwable -> 0x0090 }
            java.lang.String r13 = "file"
            r9[r1] = r13     // Catch:{ Throwable -> 0x0090 }
            java.io.File r13 = r14.getAbsoluteFile()     // Catch:{ Throwable -> 0x0090 }
            r9[r6] = r13     // Catch:{ Throwable -> 0x0090 }
            java.lang.String r6 = "size"
            r9[r5] = r6     // Catch:{ Throwable -> 0x0090 }
            long r5 = r14.length()     // Catch:{ Throwable -> 0x0090 }
            java.lang.Long r14 = java.lang.Long.valueOf(r5)     // Catch:{ Throwable -> 0x0090 }
            r9[r2] = r14     // Catch:{ Throwable -> 0x0090 }
            r14 = 4
            java.lang.String r5 = "cost"
            r9[r14] = r5     // Catch:{ Throwable -> 0x0090 }
            r14 = 5
            java.lang.Long r5 = java.lang.Long.valueOf(r11)     // Catch:{ Throwable -> 0x0090 }
            r9[r14] = r5     // Catch:{ Throwable -> 0x0090 }
            anet.channel.util.ALog.m328i(r7, r8, r3, r9)     // Catch:{ Throwable -> 0x0090 }
        L_0x008c:
            r4.close()     // Catch:{ IOException -> 0x00b4 }
            goto L_0x00b4
        L_0x0090:
            r14 = move-exception
            goto L_0x009b
        L_0x0092:
            r14 = move-exception
            r10 = r3
            goto L_0x009b
        L_0x0095:
            r14 = move-exception
            r4 = r3
            goto L_0x00b7
        L_0x0098:
            r14 = move-exception
            r4 = r3
            r10 = r4
        L_0x009b:
            boolean r2 = anet.channel.util.ALog.isPrintLog(r2)     // Catch:{ all -> 0x00b6 }
            if (r2 == 0) goto L_0x00aa
            java.lang.String r2 = "awcn.SerializeHelper"
            java.lang.String r5 = "restore file fail."
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x00b6 }
            anet.channel.util.ALog.m329w(r2, r5, r3, r14, r1)     // Catch:{ all -> 0x00b6 }
        L_0x00aa:
            if (r15 == 0) goto L_0x00b1
            java.lang.String r1 = "SerializeHelper.restore()"
            r15.appendErrorTrace(r1, r14)     // Catch:{ all -> 0x00b6 }
        L_0x00b1:
            if (r4 == 0) goto L_0x00b4
            goto L_0x008c
        L_0x00b4:
            monitor-exit(r0)
            return r10
        L_0x00b6:
            r14 = move-exception
        L_0x00b7:
            if (r4 == 0) goto L_0x00bc
            r4.close()     // Catch:{ IOException -> 0x00bc }
        L_0x00bc:
            throw r14     // Catch:{ all -> 0x000c }
        L_0x00bd:
            monitor-exit(r0)
            goto L_0x00c0
        L_0x00bf:
            throw r14
        L_0x00c0:
            goto L_0x00bf
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.SerializeHelper.restore(java.io.File, anet.channel.statist.StrategyStatObject):java.lang.Object");
    }
}
