package com.igexin.push.extension.distribution.basic.p068j;

import java.util.regex.Pattern;

/* renamed from: com.igexin.push.extension.distribution.basic.j.f */
public class C1438f {
    /* renamed from: a */
    public static int m2518a(String str, String str2) {
        try {
            Pattern compile = Pattern.compile("([a-zA-Z_-])*");
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            if (split == null || split.length < 4 || split2 == null || split2.length < 4) {
                return -1;
            }
            split[3] = compile.matcher(split[3]).replaceAll("");
            split2[3] = compile.matcher(split2[3]).replaceAll("");
            long j = 0;
            long j2 = 0;
            for (int i = 0; i < 4; i++) {
                long j3 = 1;
                for (int i2 = 0; i2 < 3 - i; i2++) {
                    j3 *= 100;
                }
                long parseLong = Long.parseLong(split[i]);
                Long.signum(parseLong);
                j2 += parseLong * j3;
            }
            for (int i3 = 0; i3 < 4; i3++) {
                long j4 = 1;
                for (int i4 = 0; i4 < 3 - i3; i4++) {
                    j4 *= 100;
                }
                j += Long.parseLong(split2[i3]) * j4;
            }
            if (j2 > j) {
                return 1;
            }
            return j2 == j ? 0 : -1;
        } catch (Exception unused) {
            return -1;
        }
    }
}
