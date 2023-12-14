package com.github.mikephil.charting.data.realm.base;

import com.github.mikephil.charting.data.BaseDataSet;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import io.realm.DynamicRealmObject;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class RealmBaseDataSet<T extends RealmObject, S extends Entry> extends BaseDataSet<S> {
    protected String mIndexField;
    protected List<S> mValues;
    protected String mValuesField;
    protected float mYMax = 0.0f;
    protected float mYMin = 0.0f;
    protected RealmResults<T> results;

    public RealmBaseDataSet(RealmResults<T> realmResults, String str) {
        this.results = realmResults;
        this.mValuesField = str;
        this.mValues = new ArrayList();
        String str2 = this.mIndexField;
        if (str2 != null) {
            this.results.sort(str2, Sort.ASCENDING);
        }
    }

    public RealmBaseDataSet(RealmResults<T> realmResults, String str, String str2) {
        this.results = realmResults;
        this.mValuesField = str;
        this.mIndexField = str2;
        this.mValues = new ArrayList();
        String str3 = this.mIndexField;
        if (str3 != null) {
            this.results.sort(str3, Sort.ASCENDING);
        }
    }

    public void build(RealmResults<T> realmResults) {
        Iterator it = realmResults.iterator();
        int i = 0;
        while (it.hasNext()) {
            this.mValues.add(buildEntryFromResultObject((RealmObject) it.next(), i));
            i++;
        }
    }

    public S buildEntryFromResultObject(T t, int i) {
        DynamicRealmObject dynamicRealmObject = new DynamicRealmObject(t);
        float f = dynamicRealmObject.getFloat(this.mValuesField);
        String str = this.mIndexField;
        if (str != null) {
            i = dynamicRealmObject.getInt(str);
        }
        return new Entry(f, i);
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getYMax() {
        return this.mYMax;
    }

    public int getEntryCount() {
        return this.mValues.size();
    }

    public void calcMinMax(int i, int i2) {
        int size;
        List<S> list = this.mValues;
        if (list != null && (size = list.size()) != 0) {
            if (i2 == 0 || i2 >= size) {
                i2 = size - 1;
            }
            this.mYMin = Float.MAX_VALUE;
            this.mYMax = -3.4028235E38f;
            while (i <= i2) {
                Entry entry = (Entry) this.mValues.get(i);
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

    public S getEntryForXIndex(int i) {
        return getEntryForXIndex(i, DataSet.Rounding.CLOSEST);
    }

    public S getEntryForXIndex(int i, DataSet.Rounding rounding) {
        int entryIndex = getEntryIndex(i, rounding);
        if (entryIndex > -1) {
            return (Entry) this.mValues.get(entryIndex);
        }
        return null;
    }

    public List<S> getEntriesForXIndex(int i) {
        ArrayList arrayList = new ArrayList();
        if (this.mIndexField == null) {
            RealmObject realmObject = this.results.get(i);
            if (realmObject != null) {
                arrayList.add(buildEntryFromResultObject(realmObject, i));
            }
        } else {
            Iterator it = this.results.where().equalTo(this.mIndexField, Integer.valueOf(i)).findAll().iterator();
            while (it.hasNext()) {
                arrayList.add(buildEntryFromResultObject((RealmObject) it.next(), i));
            }
        }
        return arrayList;
    }

    public S getEntryForIndex(int i) {
        return (Entry) this.mValues.get(i);
    }

    public int getEntryIndex(int i, DataSet.Rounding rounding) {
        int size = this.mValues.size() - 1;
        int i2 = 0;
        int i3 = -1;
        while (i2 <= size) {
            i3 = (size + i2) / 2;
            Entry entry = (Entry) this.mValues.get(i3);
            if (i == entry.getXIndex()) {
                while (i3 > 0 && ((Entry) this.mValues.get(i3 - 1)).getXIndex() == i) {
                    i3--;
                }
                return i3;
            } else if (i > entry.getXIndex()) {
                i2 = i3 + 1;
            } else {
                size = i3 - 1;
            }
        }
        if (i3 == -1) {
            return i3;
        }
        int xIndex = ((Entry) this.mValues.get(i3)).getXIndex();
        if (rounding != DataSet.Rounding.UP) {
            return (rounding != DataSet.Rounding.DOWN || xIndex <= i || i3 <= 0) ? i3 : i3 - 1;
        }
        if (xIndex >= i || i3 >= this.mValues.size() - 1) {
            return i3;
        }
        return i3 + 1;
    }

    public int getEntryIndex(S s) {
        return this.mValues.indexOf(s);
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

    public boolean addEntry(S s) {
        if (s == null) {
            return false;
        }
        float val = s.getVal();
        if (this.mValues == null) {
            this.mValues = new ArrayList();
        }
        if (this.mValues.size() == 0) {
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
        this.mValues.add(s);
        return true;
    }

    public boolean removeEntry(S s) {
        List<S> list;
        if (s == null || (list = this.mValues) == null) {
            return false;
        }
        boolean remove = list.remove(s);
        if (remove) {
            calcMinMax(0, this.mValues.size());
        }
        return remove;
    }

    public void addEntryOrdered(S s) {
        if (s != null) {
            float val = s.getVal();
            if (this.mValues == null) {
                this.mValues = new ArrayList();
            }
            if (this.mValues.size() == 0) {
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
            if (this.mValues.size() > 0) {
                List<S> list = this.mValues;
                if (((Entry) list.get(list.size() - 1)).getXIndex() > s.getXIndex()) {
                    this.mValues.add(getEntryIndex(s.getXIndex(), DataSet.Rounding.UP), s);
                    return;
                }
            }
            this.mValues.add(s);
        }
    }

    public List<S> getValues() {
        return this.mValues;
    }

    public void clear() {
        this.mValues.clear();
        notifyDataSetChanged();
    }

    public RealmResults<T> getResults() {
        return this.results;
    }

    public String getValuesField() {
        return this.mValuesField;
    }

    public void setValuesField(String str) {
        this.mValuesField = str;
    }

    public String getIndexField() {
        return this.mIndexField;
    }

    public void setIndexField(String str) {
        this.mIndexField = str;
    }
}
