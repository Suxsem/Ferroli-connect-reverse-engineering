package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import java.util.List;

public class LineData extends BarLineScatterCandleBubbleData<ILineDataSet> {
    public LineData() {
    }

    public LineData(List<String> list) {
        super(list);
    }

    public LineData(String[] strArr) {
        super(strArr);
    }

    public LineData(List<String> list, List<ILineDataSet> list2) {
        super(list, list2);
    }

    public LineData(String[] strArr, List<ILineDataSet> list) {
        super(strArr, list);
    }

    public LineData(List<String> list, ILineDataSet iLineDataSet) {
        super(list, toList(iLineDataSet));
    }

    public LineData(String[] strArr, ILineDataSet iLineDataSet) {
        super(strArr, toList(iLineDataSet));
    }

    private static List<ILineDataSet> toList(ILineDataSet iLineDataSet) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iLineDataSet);
        return arrayList;
    }
}
