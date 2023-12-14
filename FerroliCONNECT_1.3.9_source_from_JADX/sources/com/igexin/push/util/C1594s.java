package com.igexin.push.util;

import java.util.Random;

/* renamed from: com.igexin.push.util.s */
public class C1594s {

    /* renamed from: a */
    private static char[] f3040a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`~!@#$%^&*()-_=+[{}];:'/?.>,<".toCharArray();

    /* renamed from: a */
    public static long m3271a() {
        return ((long) (new Random().nextInt(6) + 2)) * 60000;
    }

    /* renamed from: a */
    public static String m3272a(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(random.nextInt(62)));
        }
        return sb.toString();
    }
}
