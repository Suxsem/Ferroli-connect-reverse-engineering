package android.support.p003v7.widget;

import java.util.ArrayList;

/* renamed from: android.support.v7.widget.PositionMap */
class PositionMap<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    static int idealByteArraySize(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                return i3;
            }
        }
        return i;
    }

    public void insertKeyRange(int i, int i2) {
    }

    public void removeKeyRange(ArrayList<E> arrayList, int i, int i2) {
    }

    PositionMap() {
        this(10);
    }

    PositionMap(int i) {
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int idealIntArraySize = idealIntArraySize(i);
            this.mKeys = new int[idealIntArraySize];
            this.mValues = new Object[idealIntArraySize];
        }
        this.mSize = 0;
    }

    public PositionMap<E> clone() {
        try {
            PositionMap<E> positionMap = (PositionMap) super.clone();
            try {
                positionMap.mKeys = (int[]) this.mKeys.clone();
                positionMap.mValues = (Object[]) this.mValues.clone();
                return positionMap;
            } catch (CloneNotSupportedException unused) {
                return positionMap;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public E get(int i) {
        return get(i, (Object) null);
    }

    public E get(int i, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            E[] eArr = this.mValues;
            if (eArr[binarySearch] != DELETED) {
                return eArr[binarySearch];
            }
        }
        return e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r0 = r3.mValues;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void delete(int r4) {
        /*
            r3 = this;
            int[] r0 = r3.mKeys
            int r1 = r3.mSize
            int r4 = android.support.p003v7.widget.PositionMap.ContainerHelpers.binarySearch(r0, r1, r4)
            if (r4 < 0) goto L_0x0017
            java.lang.Object[] r0 = r3.mValues
            r1 = r0[r4]
            java.lang.Object r2 = DELETED
            if (r1 == r2) goto L_0x0017
            r0[r4] = r2
            r4 = 1
            r3.mGarbage = r4
        L_0x0017:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.PositionMap.delete(int):void");
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        Object[] objArr = this.mValues;
        Object obj = objArr[i];
        Object obj2 = DELETED;
        if (obj != obj2) {
            objArr[i] = obj2;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int min = Math.min(this.mSize, i2 + i);
        while (i < min) {
            removeAt(i);
            i++;
        }
    }

    /* renamed from: gc */
    private void m9gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void put(int i, E e) {
        int binarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (binarySearch >= 0) {
            this.mValues[binarySearch] = e;
            return;
        }
        int i2 = binarySearch ^ -1;
        if (i2 < this.mSize) {
            Object[] objArr = this.mValues;
            if (objArr[i2] == DELETED) {
                this.mKeys[i2] = i;
                objArr[i2] = e;
                return;
            }
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            m9gc();
            i2 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i) ^ -1;
        }
        int i3 = this.mSize;
        if (i3 >= this.mKeys.length) {
            int idealIntArraySize = idealIntArraySize(i3 + 1);
            int[] iArr = new int[idealIntArraySize];
            Object[] objArr2 = new Object[idealIntArraySize];
            int[] iArr2 = this.mKeys;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            Object[] objArr3 = this.mValues;
            System.arraycopy(objArr3, 0, objArr2, 0, objArr3.length);
            this.mKeys = iArr;
            this.mValues = objArr2;
        }
        int i4 = this.mSize;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.mKeys;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            Object[] objArr4 = this.mValues;
            System.arraycopy(objArr4, i2, objArr4, i5, this.mSize - i2);
        }
        this.mKeys[i2] = i;
        this.mValues[i2] = e;
        this.mSize++;
    }

    public int size() {
        if (this.mGarbage) {
            m9gc();
        }
        return this.mSize;
    }

    public int keyAt(int i) {
        if (this.mGarbage) {
            m9gc();
        }
        return this.mKeys[i];
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            m9gc();
        }
        return this.mValues[i];
    }

    public void setValueAt(int i, E e) {
        if (this.mGarbage) {
            m9gc();
        }
        this.mValues[i] = e;
    }

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            m9gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            m9gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void append(int i, E e) {
        int i2 = this.mSize;
        if (i2 == 0 || i > this.mKeys[i2 - 1]) {
            if (this.mGarbage && this.mSize >= this.mKeys.length) {
                m9gc();
            }
            int i3 = this.mSize;
            if (i3 >= this.mKeys.length) {
                int idealIntArraySize = idealIntArraySize(i3 + 1);
                int[] iArr = new int[idealIntArraySize];
                Object[] objArr = new Object[idealIntArraySize];
                int[] iArr2 = this.mKeys;
                System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
                Object[] objArr2 = this.mValues;
                System.arraycopy(objArr2, 0, objArr, 0, objArr2.length);
                this.mKeys = iArr;
                this.mValues = objArr;
            }
            this.mKeys[i3] = i;
            this.mValues[i3] = e;
            this.mSize = i3 + 1;
            return;
        }
        put(i, e);
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            Object valueAt = valueAt(i);
            if (valueAt != this) {
                sb.append(valueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    static int idealBooleanArraySize(int i) {
        return idealByteArraySize(i);
    }

    static int idealShortArraySize(int i) {
        return idealByteArraySize(i * 2) / 2;
    }

    static int idealCharArraySize(int i) {
        return idealByteArraySize(i * 2) / 2;
    }

    static int idealIntArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealFloatArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealObjectArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealLongArraySize(int i) {
        return idealByteArraySize(i * 8) / 8;
    }

    /* renamed from: android.support.v7.widget.PositionMap$ContainerHelpers */
    static class ContainerHelpers {
        static final boolean[] EMPTY_BOOLEANS = new boolean[0];
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        ContainerHelpers() {
        }

        static int binarySearch(int[] iArr, int i, int i2) {
            int i3 = i - 1;
            int i4 = 0;
            while (i4 <= i3) {
                int i5 = (i4 + i3) >>> 1;
                int i6 = iArr[i5];
                if (i6 < i2) {
                    i4 = i5 + 1;
                } else if (i6 <= i2) {
                    return i5;
                } else {
                    i3 = i5 - 1;
                }
            }
            return i4 ^ -1;
        }
    }
}
