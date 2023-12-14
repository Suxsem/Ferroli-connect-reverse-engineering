package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import java.util.ArrayList;
import java.util.List;

public class CandleData extends BarLineScatterCandleBubbleData<ICandleDataSet> {
    public CandleData() {
    }

    public CandleData(List<String> list) {
        super(list);
    }

    public CandleData(String[] strArr) {
        super(strArr);
    }

    public CandleData(List<String> list, List<ICandleDataSet> list2) {
        super(list, list2);
    }

    public CandleData(String[] strArr, List<ICandleDataSet> list) {
        super(strArr, list);
    }

    public CandleData(List<String> list, ICandleDataSet iCandleDataSet) {
        super(list, toList(iCandleDataSet));
    }

    public CandleData(String[] strArr, ICandleDataSet iCandleDataSet) {
        super(strArr, toList(iCandleDataSet));
    }

    private static List<ICandleDataSet> toList(ICandleDataSet iCandleDataSet) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iCandleDataSet);
        return arrayList;
    }
}
