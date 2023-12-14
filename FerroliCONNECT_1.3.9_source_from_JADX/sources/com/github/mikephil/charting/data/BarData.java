package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import java.util.ArrayList;
import java.util.List;

public class BarData extends BarLineScatterCandleBubbleData<IBarDataSet> {
    private float mGroupSpace = 0.8f;

    public BarData() {
    }

    public BarData(List<String> list) {
        super(list);
    }

    public BarData(String[] strArr) {
        super(strArr);
    }

    public BarData(List<String> list, List<IBarDataSet> list2) {
        super(list, list2);
    }

    public BarData(String[] strArr, List<IBarDataSet> list) {
        super(strArr, list);
    }

    public BarData(List<String> list, IBarDataSet iBarDataSet) {
        super(list, toList(iBarDataSet));
    }

    public BarData(String[] strArr, IBarDataSet iBarDataSet) {
        super(strArr, toList(iBarDataSet));
    }

    private static List<IBarDataSet> toList(IBarDataSet iBarDataSet) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iBarDataSet);
        return arrayList;
    }

    public float getGroupSpace() {
        if (this.mDataSets.size() <= 1) {
            return 0.0f;
        }
        return this.mGroupSpace;
    }

    public void setGroupSpace(float f) {
        this.mGroupSpace = f / 100.0f;
    }

    public boolean isGrouped() {
        return this.mDataSets.size() > 1;
    }
}
