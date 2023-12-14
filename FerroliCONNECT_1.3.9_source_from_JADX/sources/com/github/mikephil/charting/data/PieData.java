package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import java.util.ArrayList;
import java.util.List;

public class PieData extends ChartData<IPieDataSet> {
    public PieData() {
    }

    public PieData(List<String> list) {
        super(list);
    }

    public PieData(String[] strArr) {
        super(strArr);
    }

    public PieData(List<String> list, IPieDataSet iPieDataSet) {
        super(list, toList(iPieDataSet));
    }

    public PieData(String[] strArr, IPieDataSet iPieDataSet) {
        super(strArr, toList(iPieDataSet));
    }

    private static List<IPieDataSet> toList(IPieDataSet iPieDataSet) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iPieDataSet);
        return arrayList;
    }

    public void setDataSet(IPieDataSet iPieDataSet) {
        this.mDataSets.clear();
        this.mDataSets.add(iPieDataSet);
        init();
    }

    public IPieDataSet getDataSet() {
        return (IPieDataSet) this.mDataSets.get(0);
    }

    public IPieDataSet getDataSetByIndex(int i) {
        if (i == 0) {
            return getDataSet();
        }
        return null;
    }

    public IPieDataSet getDataSetByLabel(String str, boolean z) {
        if (z) {
            if (str.equalsIgnoreCase(((IPieDataSet) this.mDataSets.get(0)).getLabel())) {
                return (IPieDataSet) this.mDataSets.get(0);
            }
            return null;
        } else if (str.equals(((IPieDataSet) this.mDataSets.get(0)).getLabel())) {
            return (IPieDataSet) this.mDataSets.get(0);
        } else {
            return null;
        }
    }

    public float getYValueSum() {
        float f = 0.0f;
        for (int i = 0; i < getDataSet().getEntryCount(); i++) {
            f += getDataSet().getEntryForIndex(i).getVal();
        }
        return f;
    }
}
