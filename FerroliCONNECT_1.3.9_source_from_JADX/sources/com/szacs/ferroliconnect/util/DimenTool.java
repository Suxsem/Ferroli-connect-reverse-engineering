package com.szacs.ferroliconnect.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DimenTool {
    private static final String TAG = "DimenTool";

    /* JADX WARNING: Removed duplicated region for block: B:24:0x01c4 A[SYNTHETIC, Splitter:B:24:0x01c4] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x01d0 A[SYNTHETIC, Splitter:B:29:0x01d0] */
    /* JADX WARNING: Removed duplicated region for block: B:38:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void gen() {
        /*
            java.lang.String r0 = ">"
            java.lang.String r1 = "</dimen>"
            java.lang.String r2 = "\r\n"
            java.lang.String r3 = ""
            java.io.File r4 = new java.io.File
            java.lang.String r5 = "./app/src/main/res/values/dimens.xml"
            r4.<init>(r5)
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r11 = 0
            java.io.PrintStream r12 = java.lang.System.out     // Catch:{ IOException -> 0x01be }
            java.lang.String r13 = "生成不同分辨率："
            r12.println(r13)     // Catch:{ IOException -> 0x01be }
            java.io.BufferedReader r12 = new java.io.BufferedReader     // Catch:{ IOException -> 0x01be }
            java.io.FileReader r13 = new java.io.FileReader     // Catch:{ IOException -> 0x01be }
            r13.<init>(r4)     // Catch:{ IOException -> 0x01be }
            r12.<init>(r13)     // Catch:{ IOException -> 0x01be }
        L_0x003f:
            java.lang.String r4 = r12.readLine()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            if (r4 == 0) goto L_0x013b
            boolean r11 = r4.contains(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            if (r11 == 0) goto L_0x0111
            r11 = 0
            int r13 = r4.indexOf(r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            int r13 = r13 + 1
            java.lang.String r11 = r4.substring(r11, r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r13 = "<"
            int r13 = r4.lastIndexOf(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            int r13 = r13 + -2
            java.lang.String r13 = r4.substring(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            int r14 = r4.indexOf(r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            int r14 = r14 + 1
            int r15 = r4.indexOf(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            int r15 = r15 + -2
            java.lang.String r4 = r4.substring(r14, r15)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r14 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r15.<init>()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r16 = r0
            java.lang.String r0 = " numStr = "
            r15.append(r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r15.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r15.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r14.println(r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = java.lang.Double.parseDouble(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.Double r0 = java.lang.Double.valueOf(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r5.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4604930618986332160(0x3fe8000000000000, double:0.75)
            double r14 = r14 * r17
            r5.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r5.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r5.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r6.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4609434218613702656(0x3ff8000000000000, double:1.5)
            double r14 = r14 * r17
            r6.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r6.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r6.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r7.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4611100550475829740(0x3ffdeb851eb851ec, double:1.87)
            double r14 = r14 * r17
            r7.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r7.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r7.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r8.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4612248968380809216(0x4002000000000000, double:2.25)
            double r14 = r14 * r17
            r8.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r8.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r8.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r9.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4612811918334230528(0x4004000000000000, double:2.5)
            double r14 = r14 * r17
            r9.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r9.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r9.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r10.append(r11)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            double r14 = r0.doubleValue()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r17 = 4612947026323051643(0x40047ae147ae147b, double:2.56)
            double r14 = r14 * r17
            r10.append(r14)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r10.append(r13)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r10.append(r2)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            goto L_0x0137
        L_0x0111:
            r16 = r0
            r5.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r5.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r6.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r6.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r7.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r7.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r8.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r8.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r9.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r9.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r10.append(r4)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r10.append(r3)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
        L_0x0137:
            r0 = r16
            goto L_0x003f
        L_0x013b:
            r12.close()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r1 = "<!--  sw240 -->"
            r0.println(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r0.println(r5)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r1 = "<!--  sw480 -->"
            r0.println(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r0.println(r6)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r1 = "<!--  sw600 -->"
            r0.println(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r0.println(r7)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r1 = "<!--  sw720 -->"
            r0.println(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r0.println(r8)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r1 = "<!--  sw800 -->"
            r0.println(r1)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.io.PrintStream r0 = java.lang.System.out     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r0.println(r9)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = "./app/src/main/res/values-sw240dp/dimens.xml"
            java.lang.String r1 = "./app/src/main/res/values-sw480dp/dimens.xml"
            java.lang.String r2 = "./app/src/main/res/values-sw600dp/dimens.xml"
            java.lang.String r3 = "./app/src/main/res/values-sw720dp/dimens.xml"
            java.lang.String r4 = "./app/src/main/res/values-sw800dp/dimens.xml"
            java.lang.String r11 = "./app/src/main/res/values-w820dp/dimens.xml"
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r0, r5)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r6.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r1, r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r7.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r2, r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r8.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r3, r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r9.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r4, r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            java.lang.String r0 = r10.toString()     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            writeFile(r11, r0)     // Catch:{ IOException -> 0x01b7, all -> 0x01b4 }
            r12.close()     // Catch:{ IOException -> 0x01c8 }
            goto L_0x01cd
        L_0x01b4:
            r0 = move-exception
            r1 = r0
            goto L_0x01ce
        L_0x01b7:
            r0 = move-exception
            r11 = r12
            goto L_0x01bf
        L_0x01ba:
            r0 = move-exception
            r1 = r0
            r12 = r11
            goto L_0x01ce
        L_0x01be:
            r0 = move-exception
        L_0x01bf:
            r0.printStackTrace()     // Catch:{ all -> 0x01ba }
            if (r11 == 0) goto L_0x01cd
            r11.close()     // Catch:{ IOException -> 0x01c8 }
            goto L_0x01cd
        L_0x01c8:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
        L_0x01cd:
            return
        L_0x01ce:
            if (r12 == 0) goto L_0x01d9
            r12.close()     // Catch:{ IOException -> 0x01d4 }
            goto L_0x01d9
        L_0x01d4:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x01d9:
            goto L_0x01db
        L_0x01da:
            throw r1
        L_0x01db:
            goto L_0x01da
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.DimenTool.gen():void");
    }

    public static void writeFile(String str, String str2) {
        PrintWriter printWriter;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdir();
            System.out.println("file not exist....");
        }
        try {
            printWriter = new PrintWriter(new BufferedWriter(new FileWriter(str)));
            try {
                printWriter.println(str2);
            } catch (IOException e) {
                e = e;
            }
        } catch (IOException e2) {
            e = e2;
            printWriter = null;
            e.printStackTrace();
            printWriter.close();
        }
        printWriter.close();
    }

    public static void main(String[] strArr) {
        gen();
    }
}
