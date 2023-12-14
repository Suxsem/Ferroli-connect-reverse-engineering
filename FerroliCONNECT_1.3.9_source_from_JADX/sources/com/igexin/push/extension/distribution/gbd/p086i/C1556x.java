package com.igexin.push.extension.distribution.gbd.p086i;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.x */
public class C1556x<E> {

    /* renamed from: a */
    private Comparator<E> f2966a;

    /* renamed from: b */
    private int f2967b;

    public C1556x(Comparator<E> comparator) {
        mo15185a(comparator);
    }

    /* renamed from: a */
    private boolean m3073a(List<E> list, int i, int i2, E e) {
        boolean z;
        while (true) {
            z = true;
            if (i > i2) {
                z = false;
                break;
            }
            int i3 = (i + i2) >> 1;
            int compare = this.f2966a.compare(list.get(i3), e);
            if (compare == 0) {
                i = i3;
                break;
            } else if (compare < 0) {
                i = i3 + 1;
            } else {
                i2 = i3 - 1;
            }
        }
        this.f2967b = i;
        return z;
    }

    /* renamed from: a */
    public List<E> mo15184a(List<E> list, List<E> list2) {
        if (this.f2966a == null || list == null || list.isEmpty() || list2 == null || list2.isEmpty()) {
            return null;
        }
        int size = list.size();
        int size2 = list2.size();
        int i = size - 1;
        int i2 = size2 - 1;
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        int i4 = 0;
        while (i3 < size && i4 < size2) {
            E e = list.get(i3);
            E e2 = list2.get(i4);
            int compare = this.f2966a.compare(e, e2);
            if (compare == 0) {
                arrayList.add(e);
            } else if (compare >= 0) {
                if (m3073a(list2, i4 + 1, i2, e)) {
                    arrayList.add(e);
                    i4 = this.f2967b + 1;
                } else {
                    i4 = this.f2967b;
                }
                i3++;
            } else if (m3073a(list, i3 + 1, i, e2)) {
                arrayList.add(e2);
                i3 = this.f2967b;
            } else {
                i3 = this.f2967b;
                i4++;
            }
            i3++;
            i4++;
        }
        return arrayList;
    }

    /* renamed from: a */
    public void mo15185a(Comparator<E> comparator) {
        this.f2966a = comparator;
    }
}
