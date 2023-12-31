package com.github.barteksc.pdfviewer.util;

import android.content.Context;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    private FileUtils() {
    }

    public static File fileFromAsset(Context context, String str) throws IOException {
        File cacheDir = context.getCacheDir();
        File file = new File(cacheDir, str + "-pdfview.pdf");
        if (str.contains("/")) {
            file.getParentFile().mkdirs();
        }
        copy(context.getAssets().open(str), file);
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x002b A[SYNTHETIC, Splitter:B:21:0x002b] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copy(java.io.InputStream r3, java.io.File r4) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x0027 }
            r1.<init>(r4)     // Catch:{ all -> 0x0027 }
            r4 = 1024(0x400, float:1.435E-42)
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0025 }
        L_0x000a:
            int r0 = r3.read(r4)     // Catch:{ all -> 0x0025 }
            r2 = -1
            if (r0 == r2) goto L_0x0016
            r2 = 0
            r1.write(r4, r2, r0)     // Catch:{ all -> 0x0025 }
            goto L_0x000a
        L_0x0016:
            if (r3 == 0) goto L_0x0021
            r3.close()     // Catch:{ all -> 0x001c }
            goto L_0x0021
        L_0x001c:
            r3 = move-exception
            r1.close()
            throw r3
        L_0x0021:
            r1.close()
            return
        L_0x0025:
            r4 = move-exception
            goto L_0x0029
        L_0x0027:
            r4 = move-exception
            r1 = r0
        L_0x0029:
            if (r3 == 0) goto L_0x0036
            r3.close()     // Catch:{ all -> 0x002f }
            goto L_0x0036
        L_0x002f:
            r3 = move-exception
            if (r1 == 0) goto L_0x0035
            r1.close()
        L_0x0035:
            throw r3
        L_0x0036:
            if (r1 == 0) goto L_0x003b
            r1.close()
        L_0x003b:
            goto L_0x003d
        L_0x003c:
            throw r4
        L_0x003d:
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.barteksc.pdfviewer.util.FileUtils.copy(java.io.InputStream, java.io.File):void");
    }
}
