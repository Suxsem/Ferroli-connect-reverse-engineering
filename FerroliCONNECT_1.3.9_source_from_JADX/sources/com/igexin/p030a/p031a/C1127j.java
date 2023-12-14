package com.igexin.p030a.p031a;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.UByte;
import kotlin.UShort;

/* renamed from: com.igexin.a.a.j */
public class C1127j implements C1120c, Closeable {

    /* renamed from: a */
    private final int f1514a = 1179403647;

    /* renamed from: b */
    private final FileChannel f1515b;

    public C1127j(File file) {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.f1515b = new FileInputStream(file).getChannel();
    }

    /* renamed from: a */
    private long m1169a(C1122e eVar, long j, long j2) {
        for (long j3 = 0; j3 < j; j3++) {
            C1123f a = eVar.mo14153a(j3);
            if (a.f1507a == 1 && a.f1509c <= j2 && j2 <= a.f1509c + a.f1510d) {
                return (j2 - a.f1509c) + a.f1508b;
            }
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    /* renamed from: a */
    public C1122e mo14155a() {
        this.f1515b.position(0);
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        if (mo14160c(allocate, 0) == 1179403647) {
            short e = mo14163e(allocate, 4);
            boolean z = mo14163e(allocate, 5) == 2;
            if (e == 1) {
                return new C1125h(z, this);
            }
            if (e == 2) {
                return new C1126i(z, this);
            }
            throw new IllegalStateException("Invalid class type!");
        }
        throw new IllegalArgumentException("Invalid ELF Magic!");
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public String mo14156a(ByteBuffer byteBuffer, long j) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short e = mo14163e(byteBuffer, j);
            if (e == 0) {
                return sb.toString();
            }
            sb.append((char) e);
            j = j2;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo14157a(ByteBuffer byteBuffer, long j, int i) {
        byteBuffer.position(0);
        byteBuffer.limit(i);
        long j2 = 0;
        while (j2 < ((long) i)) {
            int read = this.f1515b.read(byteBuffer, j + j2);
            if (read != -1) {
                j2 += (long) read;
            } else {
                throw new EOFException();
            }
        }
        byteBuffer.position(0);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public long mo14158b(ByteBuffer byteBuffer, long j) {
        mo14157a(byteBuffer, j, 8);
        return byteBuffer.getLong();
    }

    /* renamed from: b */
    public List<String> mo14159b() {
        long j;
        this.f1515b.position(0);
        ArrayList arrayList = new ArrayList();
        C1122e a = mo14155a();
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(a.f1498a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = (long) a.f1503f;
        int i = 0;
        if (j2 == 65535) {
            j2 = a.mo14154a(0).f1511a;
        }
        long j3 = 0;
        while (true) {
            if (j3 >= j2) {
                j = 0;
                break;
            }
            C1123f a2 = a.mo14153a(j3);
            if (a2.f1507a == 2) {
                j = a2.f1508b;
                break;
            }
            j3++;
        }
        if (j == 0) {
            return Collections.unmodifiableList(arrayList);
        }
        ArrayList<Long> arrayList2 = new ArrayList<>();
        long j4 = 0;
        while (true) {
            C1121d a3 = a.mo14152a(j, i);
            long j5 = j;
            if (a3.f1496a == 1) {
                arrayList2.add(Long.valueOf(a3.f1497b));
            } else if (a3.f1496a == 5) {
                j4 = a3.f1497b;
            }
            i++;
            if (a3.f1496a == 0) {
                break;
            }
            j = j5;
        }
        if (j4 != 0) {
            long a4 = m1169a(a, j2, j4);
            for (Long longValue : arrayList2) {
                arrayList.add(mo14156a(allocate, longValue.longValue() + a4));
            }
            return arrayList;
        }
        throw new IllegalStateException("String table offset not found!");
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public long mo14160c(ByteBuffer byteBuffer, long j) {
        mo14157a(byteBuffer, j, 4);
        return ((long) byteBuffer.getInt()) & 4294967295L;
    }

    public void close() {
        this.f1515b.close();
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public int mo14162d(ByteBuffer byteBuffer, long j) {
        mo14157a(byteBuffer, j, 2);
        return byteBuffer.getShort() & UShort.MAX_VALUE;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public short mo14163e(ByteBuffer byteBuffer, long j) {
        mo14157a(byteBuffer, j, 1);
        return (short) (byteBuffer.get() & UByte.MAX_VALUE);
    }
}
