package com.igexin.p032b.p033a.p035b;

import com.igexin.p032b.p033a.p040d.C1190e;
import com.igexin.p032b.p033a.p040d.C1191f;
import com.igexin.p032b.p033a.p040d.p041a.C1181a;
import com.igexin.p032b.p033a.p040d.p041a.C1183c;
import com.igexin.p032b.p033a.p040d.p041a.C1186f;
import com.igexin.p032b.p042b.C1196a;
import java.util.concurrent.TimeUnit;

/* renamed from: com.igexin.b.a.b.c */
public class C1174c extends C1191f {

    /* renamed from: a */
    static C1174c f1598a;

    /* renamed from: b */
    public volatile long f1599b;

    /* renamed from: c */
    public volatile long f1600c;

    /* renamed from: d */
    public volatile long f1601d;

    /* renamed from: e */
    public volatile long f1602e;

    /* renamed from: f */
    C1181a<String, Integer, C1173b, C1176e> f1603f;

    /* renamed from: v */
    private byte[] f1604v;

    /* renamed from: w */
    private byte[] f1605w;

    /* renamed from: b */
    public static C1174c m1310b() {
        if (f1598a == null) {
            f1598a = new C1174c();
        }
        return f1598a;
    }

    /* renamed from: d */
    public static void m1311d() {
        C1174c cVar = f1598a;
        cVar.f1599b = 0;
        cVar.f1601d = 0;
        cVar.f1600c = 0;
        cVar.f1602e = 0;
    }

    /* renamed from: a */
    public C1176e mo14260a(String str, int i, C1173b bVar, Object obj, boolean z) {
        return mo14261a(str, i, bVar, obj, z, -1, -1, (byte) 0, (Object) null, (C1183c) null);
    }

    /* renamed from: a */
    public C1176e mo14261a(String str, int i, C1173b bVar, Object obj, boolean z, int i2, long j, byte b, Object obj2, C1183c cVar) {
        return mo14262a(str, i, bVar, obj, z, i2, j, b, obj2, cVar, 0, (C1186f) null);
    }

    /* renamed from: a */
    public C1176e mo14262a(String str, int i, C1173b bVar, Object obj, boolean z, int i2, long j, byte b, Object obj2, C1183c cVar, int i3, C1186f fVar) {
        C1186f fVar2 = fVar;
        C1181a<String, Integer, C1173b, C1176e> aVar = this.f1603f;
        if (aVar == null) {
            return null;
        }
        String str2 = str;
        C1173b bVar2 = bVar;
        C1176e a = aVar.mo14272a(str, Integer.valueOf(i), bVar);
        if (a == null || a.mo14307r()) {
            return null;
        }
        if (fVar2 != null) {
            a.mo14297a(i3, fVar2);
        }
        mo14266a(a, obj, z, i2, j, b, obj2, cVar);
        return a;
    }

    /* renamed from: a */
    public C1176e mo14263a(String str, int i, C1173b bVar, Object obj, boolean z, int i2, C1186f fVar) {
        return mo14262a(str, i, bVar, obj, z, -1, -1, (byte) 0, (Object) null, (C1183c) null, i2, fVar);
    }

    /* renamed from: a */
    public void mo14264a(C1181a<String, Integer, C1173b, C1176e> aVar) {
        this.f1603f = aVar;
    }

    /* renamed from: a */
    public void mo14265a(byte[] bArr) {
        this.f1604v = bArr;
        this.f1605w = C1196a.m1437a(bArr);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public boolean mo14266a(C1176e eVar, Object obj, boolean z, int i, long j, byte b, Object obj2, C1183c cVar) {
        eVar.f1608c = obj;
        eVar.mo14294a(j, TimeUnit.MILLISECONDS);
        eVar.f1658x = i;
        eVar.mo14296a((int) b);
        eVar.f1637C = obj2;
        eVar.mo14298a(cVar);
        return mo14316a((C1190e) eVar, z);
    }

    /* renamed from: a */
    public byte[] mo14267a() {
        return this.f1605w;
    }

    /* renamed from: c */
    public final void mo14268c() {
        mo14323f();
    }
}
