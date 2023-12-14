package com.igexin.push.p054e.p055a;

import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.p032b.p033a.p035b.C1173b;
import com.igexin.p032b.p033a.p035b.C1174c;
import com.igexin.p032b.p033a.p035b.C1175d;
import com.igexin.p032b.p033a.p035b.C1176e;
import com.igexin.p032b.p033a.p035b.C1177f;
import com.igexin.p032b.p033a.p035b.p036a.p037a.C1170o;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.p032b.p033a.p040d.p041a.C1185e;
import com.igexin.p032b.p042b.C1196a;
import com.igexin.push.p054e.p057c.C1373b;
import com.igexin.push.p054e.p057c.C1378g;
import com.igexin.push.p054e.p057c.C1379h;
import com.igexin.push.util.EncryptUtils;
import kotlin.UByte;

/* renamed from: com.igexin.push.e.a.b */
public class C1367b extends C1173b {

    /* renamed from: a */
    public static final String f2237a = "com.igexin.push.e.a.b";

    /* renamed from: b */
    public static int f2238b = -1;

    /* renamed from: g */
    private byte[] f2239g;

    C1367b(String str) {
        super(str, true);
    }

    /* renamed from: a */
    private byte m2181a(C1170o oVar) {
        return (byte) m2186b(oVar, 1);
    }

    /* renamed from: a */
    public static C1173b m2182a() {
        C1367b bVar = new C1367b("socketProtocol");
        new C1366a("command", bVar);
        return bVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b3, code lost:
        if (r12.f2291g == 0) goto L_0x00b5;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.igexin.p032b.p033a.p040d.p041a.C1185e m2183a(com.igexin.p032b.p033a.p035b.C1176e r9, com.igexin.p032b.p033a.p035b.C1175d r10, com.igexin.p032b.p033a.p035b.p036a.p037a.C1170o r11, com.igexin.push.p054e.p057c.C1378g r12) {
        /*
            r8 = this;
            byte r0 = r12.f2292h
            r1 = 48
            r2 = 0
            if (r0 != r1) goto L_0x0008
            return r2
        L_0x0008:
            byte r0 = r8.m2181a((com.igexin.p032b.p033a.p035b.p036a.p037a.C1170o) r11)
            if (r0 <= 0) goto L_0x0011
            r8.m2185a(r11, r0)
        L_0x0011:
            byte r0 = r8.m2181a((com.igexin.p032b.p033a.p035b.p036a.p037a.C1170o) r11)
            r12.f2290f = r0
            byte r0 = r8.m2181a((com.igexin.p032b.p033a.p035b.p036a.p037a.C1170o) r11)
            r12.f2299o = r0
            int r0 = r12.f2299o
            if (r0 <= 0) goto L_0x0029
            int r0 = r12.f2299o
            byte[] r0 = r8.m2185a(r11, r0)
            r12.f2298n = r0
        L_0x0029:
            int r0 = r12.f2289e
            if (r0 != 0) goto L_0x0041
            com.igexin.b.a.b.c r9 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()
            com.igexin.push.e.c.h r10 = new com.igexin.push.e.c.h
            r10.<init>()
            r9.mo14319a((java.lang.Object) r10)
            com.igexin.b.a.b.c r9 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()
            r9.mo14268c()
            return r2
        L_0x0041:
            r0 = 11
            byte[] r0 = r8.m2185a(r11, r0)
            r3 = 0
            int r3 = com.igexin.p032b.p033a.p035b.C1177f.m1341d(r0, r3)
            int r4 = f2238b
            if (r3 <= r4) goto L_0x0114
            f2238b = r3
            r4 = 4
            int r4 = com.igexin.p032b.p033a.p035b.C1177f.m1341d(r0, r4)
            r5 = 8
            short r5 = com.igexin.p032b.p033a.p035b.C1177f.m1335b(r0, r5)
            r6 = 10
            int r0 = com.igexin.p032b.p033a.p035b.C1177f.m1326a((byte[]) r0, (int) r6)
            com.igexin.push.e.c.b r6 = new com.igexin.push.e.c.b
            r6.<init>()
            r6.f2256a = r5
            byte r7 = (byte) r0
            r6.f2257b = r7
            int r7 = r12.f2287c
            r6.f2261f = r7
            byte r7 = r12.f2292h
            r6.f2262g = r7
            if (r5 <= 0) goto L_0x00e0
            byte[] r11 = r8.m2185a(r11, r5)
            byte r5 = r12.f2292h
            r7 = 16
            if (r5 != r7) goto L_0x008e
            byte[] r0 = com.igexin.p032b.p033a.p035b.C1177f.m1336b((int) r4)
            byte[] r0 = com.igexin.push.util.EncryptUtils.getIV(r0)
            byte[] r11 = com.igexin.push.util.EncryptUtils.aesDecSocket(r11, r0)
            goto L_0x00a6
        L_0x008e:
            byte r5 = r12.f2292h
            r7 = 32
            if (r5 != r7) goto L_0x00a2
            r1 = 26
            if (r0 == r1) goto L_0x0099
            return r2
        L_0x0099:
            byte[] r0 = com.igexin.p032b.p033a.p035b.C1177f.m1336b((int) r4)
            byte[] r11 = com.igexin.push.util.EncryptUtils.altAesDecSocket(r11, r0)
            goto L_0x00a6
        L_0x00a2:
            byte r0 = r12.f2292h
            if (r0 != 0) goto L_0x00db
        L_0x00a6:
            byte r0 = r12.f2291g
            r1 = -128(0xffffffffffffff80, float:NaN)
            if (r0 != r1) goto L_0x00b1
            byte[] r11 = com.igexin.p032b.p033a.p035b.C1177f.m1342d(r11)
            goto L_0x00b5
        L_0x00b1:
            byte r0 = r12.f2291g
            if (r0 != 0) goto L_0x00da
        L_0x00b5:
            r6.mo14830a(r11)
            byte[] r11 = r12.f2298n
            byte[] r12 = com.igexin.push.util.EncryptUtils.getSocketSignature(r6, r3, r4)
            boolean r11 = java.util.Arrays.equals(r11, r12)
            if (r11 != 0) goto L_0x00fb
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = f2237a
            r9.append(r10)
            java.lang.String r10 = "|decode signature error!!!!"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r9)
        L_0x00da:
            return r2
        L_0x00db:
            byte r9 = r12.f2292h
            if (r9 != r1) goto L_0x00df
        L_0x00df:
            return r2
        L_0x00e0:
            int r11 = r6.f2256a
            if (r11 >= 0) goto L_0x00fb
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = f2237a
            r9.append(r10)
            java.lang.String r10 = "|data len < 0, error"
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            com.igexin.p032b.p033a.p039c.C1179b.m1354a(r9)
            return r2
        L_0x00fb:
            com.igexin.b.a.b.b r11 = r8.f1595d
            if (r11 == 0) goto L_0x010c
            com.igexin.b.a.b.c r11 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()
            com.igexin.b.a.b.b r12 = r8.f1595d
            java.lang.Object r9 = r12.mo14258c(r9, r10, r6)
            r11.mo14319a((java.lang.Object) r9)
        L_0x010c:
            com.igexin.b.a.b.c r9 = com.igexin.p032b.p033a.p035b.C1174c.m1310b()
            r9.mo14268c()
            return r2
        L_0x0114:
            r9 = -1
            f2238b = r9
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.String r10 = "server packetId can't be less than previous"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.igexin.push.p054e.p055a.C1367b.m2183a(com.igexin.b.a.b.e, com.igexin.b.a.b.d, com.igexin.b.a.b.a.a.o, com.igexin.push.e.c.g):com.igexin.b.a.d.a.e");
    }

    /* renamed from: a */
    static C1378g m2184a(C1373b bVar) {
        C1378g gVar = new C1378g();
        gVar.f2285a = 1944742139;
        gVar.mo14840a(bVar.f2258c);
        gVar.f2289e = bVar.f2257b > 0 ? 1 : 0;
        gVar.f2287c = 7;
        gVar.f2286b = 11;
        gVar.f2290f = bVar.f2259d;
        gVar.f2286b += EncryptUtils.getRSAKeyId().length;
        if (bVar.f2256a > 0) {
            gVar.f2300p = EncryptUtils.getPacketId();
            gVar.f2301q = (int) (System.currentTimeMillis() / 1000);
            gVar.f2298n = EncryptUtils.getSocketSignature(bVar, gVar.f2300p, gVar.f2301q);
            gVar.f2299o = gVar.f2298n.length;
        } else {
            if (gVar.f2292h == 0) {
                gVar.f2299o = 0;
            }
            C1174c.m1311d();
            return gVar;
        }
        gVar.f2286b += gVar.f2299o;
        C1174c.m1311d();
        return gVar;
    }

    /* renamed from: a */
    private byte[] m2185a(C1170o oVar, int i) {
        byte[] bArr = new byte[i];
        oVar.mo14253a(bArr);
        return bArr;
    }

    /* renamed from: b */
    private int m2186b(C1170o oVar, int i) {
        byte[] a = m2185a(oVar, i);
        if (i == 1) {
            return C1177f.m1326a(a, 0);
        }
        if (i == 2) {
            return C1177f.m1335b(a, 0);
        }
        if (i == 4) {
            return C1177f.m1341d(a, 0);
        }
        return 0;
    }

    /* renamed from: b */
    private C1185e m2187b(C1176e eVar, C1175d dVar, C1170o oVar, C1378g gVar) {
        byte a;
        if (gVar.f2292h == 48 && (a = m2181a(oVar)) > 0) {
            this.f2239g = m2185a(oVar, a);
        }
        if (gVar.f2289e == 0) {
            C1174c.m1310b().mo14319a((Object) new C1379h());
        } else {
            byte[] a2 = m2185a(oVar, 3);
            short b = C1177f.m1335b(a2, 0);
            int a3 = C1177f.m1326a(a2, 2);
            C1373b bVar = new C1373b();
            bVar.f2256a = b;
            bVar.f2257b = (byte) a3;
            bVar.f2261f = gVar.f2287c;
            if (a3 != 26) {
                return null;
            }
            if (bVar.f2256a > 0) {
                byte[] a4 = m2185a(oVar, b);
                if (gVar.f2292h == 48) {
                    byte[] bArr = this.f2239g;
                    a4 = C1150a.m1232a(a4, bArr == null ? C1174c.m1310b().mo14267a() : C1196a.m1437a(bArr));
                } else {
                    byte b2 = gVar.f2292h;
                }
                if (gVar.f2291g == Byte.MIN_VALUE) {
                    a4 = C1177f.m1342d(a4);
                } else if (gVar.f2291g != 0) {
                    return null;
                }
                bVar.mo14830a(a4);
            }
            if (this.f1595d != null) {
                C1174c.m1310b().mo14319a(this.f1595d.mo14258c(eVar, dVar, bVar));
            }
        }
        C1174c.m1310b().mo14268c();
        return null;
    }

    /* renamed from: a */
    public Object mo14255a(C1176e eVar, C1175d dVar, Object obj) {
        int i;
        String str;
        byte[] bArr = null;
        if (obj instanceof C1373b) {
            C1373b bVar = (C1373b) obj;
            C1378g a = m2184a(bVar);
            if (bVar.f2257b > 0 && bVar.f2256a > 0) {
                if ((a.f2291g & 192) == 128) {
                    bVar.mo14830a(C1177f.m1340c(bVar.f2260e));
                }
                if ((a.f2292h & 48) == 16) {
                    byte[] iv = EncryptUtils.getIV(C1177f.m1336b(a.f2301q));
                    if ((a.f2290f & 16) != 16) {
                        bVar.mo14830a(EncryptUtils.aesEncSocket(bVar.f2260e, iv));
                    }
                } else if ((a.f2292h & 48) != 0) {
                    if ((a.f2292h & 48) == 48) {
                        str = f2237a + "|encry type = 0x30 not support";
                    } else if ((a.f2292h & 48) == 32) {
                        C1179b.m1354a(f2237a + "|encry type = 0x20 reserved");
                    } else {
                        str = f2237a + "|encry type = " + (a.f2292h & 48) + " not support";
                    }
                    C1179b.m1354a(str);
                    return null;
                }
            }
            bArr = new byte[(a.f2286b + (bVar.f2257b > 0 ? bVar.f2256a + 11 : 0))];
            int a2 = C1177f.m1324a(1944742139, bArr, 0);
            int c = a2 + C1177f.m1338c(a.f2286b, bArr, a2);
            int c2 = c + C1177f.m1338c(a.f2287c, bArr, c);
            int c3 = c2 + C1177f.m1338c(a.mo14839a(), bArr, c2);
            int c4 = c3 + C1177f.m1338c(a.f2289e, bArr, c3);
            byte[] rSAKeyId = EncryptUtils.getRSAKeyId();
            int c5 = c4 + C1177f.m1338c(rSAKeyId.length, bArr, c4);
            int a3 = c5 + C1177f.m1327a(rSAKeyId, 0, bArr, c5, rSAKeyId.length);
            int c6 = a3 + C1177f.m1338c(a.mo14841b(), bArr, a3);
            if (bVar.f2256a > 0) {
                c6 += C1177f.m1338c(a.f2299o, bArr, c6);
                i = C1177f.m1327a(a.f2298n, 0, bArr, c6, a.f2299o);
            } else {
                i = C1177f.m1338c(0, bArr, c6);
            }
            int i2 = c6 + i;
            if (bVar.f2257b > 0) {
                int a4 = i2 + C1177f.m1324a(a.f2300p, bArr, i2);
                int a5 = a4 + C1177f.m1324a(a.f2301q, bArr, a4);
                int b = a5 + C1177f.m1334b(bVar.f2256a, bArr, a5);
                int c7 = b + C1177f.m1338c(bVar.f2257b, bArr, b);
                if (bVar.f2256a > 0) {
                    C1177f.m1327a(bVar.f2260e, 0, bArr, c7, bVar.f2256a);
                }
            }
        }
        return bArr;
    }

    /* renamed from: b */
    public C1185e mo14258c(C1176e eVar, C1175d dVar, Object obj) {
        StringBuilder sb;
        String str;
        C1170o oVar = obj instanceof C1170o ? (C1170o) obj : null;
        if (oVar == null) {
            sb = new StringBuilder();
            sb.append(f2237a);
            str = "|syncIns is null";
        } else {
            byte[] a = m2185a(oVar, 8);
            if (C1177f.m1341d(a, 0) != 1944742139) {
                return null;
            }
            C1378g gVar = new C1378g();
            gVar.f2286b = a[4] & UByte.MAX_VALUE;
            gVar.f2287c = a[5] & UByte.MAX_VALUE;
            gVar.mo14840a(a[6]);
            gVar.f2289e = a[7] & UByte.MAX_VALUE;
            if (gVar.f2287c == 7) {
                return m2183a(eVar, dVar, oVar, gVar);
            }
            if (gVar.f2287c == 1) {
                return m2187b(eVar, dVar, oVar, gVar);
            }
            sb = new StringBuilder();
            sb.append(f2237a);
            sb.append("|server socket resp version = ");
            sb.append(gVar.f2287c);
            str = ", not support !!!";
        }
        sb.append(str);
        C1179b.m1354a(sb.toString());
        return null;
    }
}
