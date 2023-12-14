package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.List;

public abstract class DataSet<T extends Entry> extends BaseDataSet<T> {
    protected float mYMax = 0.0f;
    protected float mYMin = 0.0f;
    protected List<T> mYVals = null;

    public enum Rounding {
        UP,
        DOWN,
        CLOSEST
    }

    public abstract DataSet<T> copy();

    public DataSet(List<T> list, String str) {
        super(str);
        this.mYVals = list;
        if (this.mYVals == null) {
            this.mYVals = new ArrayList();
        }
        calcMinMax(0, this.mYVals.size());
    }

    public void calcMinMax(int i, int i2) {
        int size;
        List<T> list = this.mYVals;
        if (list != null && (size = list.size()) != 0) {
            if (i2 == 0 || i2 >= size) {
                i2 = size - 1;
            }
            this.mYMin = Float.MAX_VALUE;
            this.mYMax = -3.4028235E38f;
            while (i <= i2) {
                Entry entry = (Entry) this.mYVals.get(i);
                if (entry != null && !Float.isNaN(entry.getVal())) {
                    if (entry.getVal() < this.mYMin) {
                        this.mYMin = entry.getVal();
                    }
                    if (entry.getVal() > this.mYMax) {
                        this.mYMax = entry.getVal();
                    }
                }
                i++;
            }
            if (this.mYMin == Float.MAX_VALUE) {
                this.mYMin = 0.0f;
                this.mYMax = 0.0f;
            }
        }
    }

    public int getEntryCount() {
        return this.mYVals.size();
    }

    public List<T> getYVals() {
        return this.mYVals;
    }

    public void setYVals(List<T> list) {
        this.mYVals = list;
        notifyDataSetChanged();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(toSimpleString());
        for (int i = 0; i < this.mYVals.size(); i++) {
            stringBuffer.append(((Entry) this.mYVals.get(i)).toString() + " ");
        }
        return stringBuffer.toString();
    }

    public String toSimpleString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        sb.append("DataSet, label: ");
        sb.append(getLabel() == null ? "" : getLabel());
        sb.append(", entries: ");
        sb.append(this.mYVals.size());
        sb.append("\n");
        stringBuffer.append(sb.toString());
        return stringBuffer.toString();
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public void addEntryOrdered(T t) {
        if (t != null) {
            float val = t.getVal();
            if (this.mYVals == null) {
                this.mYVals = new ArrayList();
            }
            if (this.mYVals.size() == 0) {
                this.mYMax = val;
                this.mYMin = val;
            } else {
                if (this.mYMax < val) {
                    this.mYMax = val;
                }
                if (this.mYMin > val) {
                    this.mYMin = val;
                }
            }
            if (this.mYVals.size() > 0) {
                List<T> list = this.mYVals;
                if (((Entry) list.get(list.size() - 1)).getXIndex() > t.getXIndex()) {
                    this.mYVals.add(getEntryIndex(t.getXIndex(), Rounding.UP), t);
                    return;
                }
            }
            this.mYVals.add(t);
        }
    }

    public void clear() {
        this.mYVals.clear();
        notifyDataSetChanged();
    }

    public boolean addEntry(T t) {
        if (t == null) {
            return false;
        }
        float val = t.getVal();
        List yVals = getYVals();
        if (yVals == null) {
            yVals = new ArrayList();
        }
        if (yVals.size() == 0) {
            this.mYMax = val;
            this.mYMin = val;
        } else {
            if (this.mYMax < val) {
                this.mYMax = val;
            }
            if (this.mYMin > val) {
                this.mYMin = val;
            }
        }
        yVals.add(t);
        return true;
    }

    public boolean removeEntry(T t) {
        List<T> list;
        if (t == null || (list = this.mYVals) == null) {
            return false;
        }
        boolean remove = list.remove(t);
        if (remove) {
            calcMinMax(0, this.mYVals.size());
        }
        return remove;
    }

    public int getEntryIndex(Entry entry) {
        return this.mYVals.indexOf(entry);
    }

    public T getEntryForXIndex(int i, Rounding rounding) {
        int entryIndex = getEntryIndex(i, rounding);
        if (entryIndex > -1) {
            return (Entry) this.mYVals.get(entryIndex);
        }
        return null;
    }

    public T getEntryForXIndex(int i) {
        return getEntryForXIndex(i, Rounding.CLOSEST);
    }

    public T getEntryForIndex(int i) {
        return (Entry) this.mYVals.get(i);
    }

    public int getEntryIndex(int i, Rounding rounding) {
        int size = this.mYVals.size() - 1;
        int i2 = 0;
        int i3 = -1;
        while (i2 <= size) {
            i3 = (size + i2) / 2;
            if (i == ((Entry) this.mYVals.get(i3)).getXIndex()) {
                while (i3 > 0 && ((Entry) this.mYVals.get(i3 - 1)).getXIndex() == i) {
                    i3--;
                }
                return i3;
            } else if (i > ((Entry) this.mYVals.get(i3)).getXIndex()) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        if (i3 == -1) {
            return i3;
        }
        int xIndex = ((Entry) this.mYVals.get(i3)).getXIndex();
        if (rounding != Rounding.UP) {
            return (rounding != Rounding.DOWN || xIndex <= i || i3 <= 0) ? i3 : i3 - 1;
        }
        if (xIndex >= i || i3 >= this.mYVals.size() - 1) {
            return i3;
        }
        return i3 + 1;
    }

    public float getYValForXIndex(int i) {
        Entry entryForXIndex = getEntryForXIndex(i);
        if (entryForXIndex == null || entryForXIndex.getXIndex() != i) {
            return Float.NaN;
        }
        return entryForXIndex.getVal();
    }

    public float[] getYValsForXIndex(int i) {
        List<Entry> entriesForXIndex = getEntriesForXIndex(i);
        float[] fArr = new float[entriesForXIndex.size()];
        int i2 = 0;
        for (Entry val : entriesForXIndex) {
            fArr[i2] = val.getVal();
            i2++;
        }
        return fArr;
    }

    public List<T> getEntriesForXIndex(int i) {
        ArrayList arrayList = new ArrayList();
        int size = this.mYVals.size() - 1;
        int i2 = 0;
        while (true) {
            if (i2 > size) {
                break;
            }
            int i3 = (size + i2) / 2;
            Entry entry = (Entry) this.mYVals.get(i3);
            if (i == entry.getXIndex()) {
                while (i3 > 0 && ((Entry) this.mYVals.get(i3 - 1)).getXIndex() == i) {
                    i3--;
                }
                int size2 = this.mYVals.size();
                while (i3 < size2) {
                    Entry entry2 = (Entry) this.mYVals.get(i3);
                    if (entry2.getXIndex() != i) {
                        break;
                    }
                    arrayList.add(entry2);
                    i3++;
                }
            } else if (i > entry.getXIndex()) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        return arrayList;
    }
}
