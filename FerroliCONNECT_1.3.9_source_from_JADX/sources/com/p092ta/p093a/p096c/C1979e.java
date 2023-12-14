package com.p092ta.p093a.p096c;

/* renamed from: com.ta.a.c.e */
public class C1979e {

    /* renamed from: com.ta.a.c.e$a */
    private static class C1981a {

        /* renamed from: a */
        public int[] f3165a;

        /* renamed from: x */
        public int f3166x;

        /* renamed from: y */
        public int f3167y;

        private C1981a() {
            this.f3165a = new int[256];
        }
    }

    /* renamed from: b */
    public static byte[] m3363b(byte[] bArr) {
        C1981a a;
        if (bArr == null || (a = m3361a("QrMgt8GGYI6T52ZY5AnhtxkLzb8egpFn3j5JELI8H6wtACbUnZ5cc3aYTsTRbmkAkRJeYbtx92LPBWm7nBO9UIl7y5i5MQNmUZNf5QENurR5tGyo7yJ2G0MBjWvy6iAtlAbacKP0SwOUeUWx5dsBdyhxa7Id1APtybSdDgicBDuNjI0mlZFUzZSS9dmN8lBD0WTVOMz0pRZbR3cysomRXOO1ghqjJdTcyDIxzpNAEszN8RMGjrzyU7Hjbmwi6YNK")) == null) {
            return null;
        }
        return m3362a(bArr, a);
    }

    /* renamed from: a */
    private static C1981a m3361a(String str) {
        if (str == null) {
            return null;
        }
        C1981a aVar = new C1981a();
        int i = 0;
        for (int i2 = 0; i2 < 256; i2++) {
            aVar.f3165a[i2] = i2;
        }
        aVar.f3166x = 0;
        aVar.f3167y = 0;
        int i3 = 0;
        int i4 = 0;
        while (i < 256) {
            try {
                i4 = ((str.charAt(i3) + aVar.f3165a[i]) + i4) % 256;
                int i5 = aVar.f3165a[i];
                aVar.f3165a[i] = aVar.f3165a[i4];
                aVar.f3165a[i4] = i5;
                i3 = (i3 + 1) % str.length();
                i++;
            } catch (Exception unused) {
                return null;
            }
        }
        return aVar;
    }

    /* renamed from: a */
    private static byte[] m3362a(byte[] bArr, C1981a aVar) {
        if (bArr == null || aVar == null) {
            return null;
        }
        int i = aVar.f3166x;
        int i2 = aVar.f3167y;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) % 256;
            i2 = (aVar.f3165a[i] + i2) % 256;
            int i4 = aVar.f3165a[i];
            aVar.f3165a[i] = aVar.f3165a[i2];
            aVar.f3165a[i2] = i4;
            bArr[i3] = (byte) (aVar.f3165a[(aVar.f3165a[i] + aVar.f3165a[i2]) % 256] ^ bArr[i3]);
        }
        aVar.f3166x = i;
        aVar.f3167y = i2;
        return bArr;
    }
}
