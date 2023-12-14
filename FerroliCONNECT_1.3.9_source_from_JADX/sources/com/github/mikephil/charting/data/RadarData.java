package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import java.util.ArrayList;
import java.util.List;

public class RadarData extends ChartData<IRadarDataSet> {
    public RadarData() {
    }

    public RadarData(List<String> list) {
        super(list);
    }

    public RadarData(String[] strArr) {
        super(strArr);
    }

    public RadarData(List<String> list, List<IRadarDataSet> list2) {
        super(list, list2);
    }

    public RadarData(String[] strArr, List<IRadarDataSet> list) {
        super(strArr, list);
    }

    public RadarData(List<String> list, IRadarDataSet iRadarDataSet) {
        super(list, toList(iRadarDataSet));
    }

    public RadarData(String[] strArr, IRadarDataSet iRadarDataSet) {
        super(strArr, toList(iRadarDataSet));
    }

    private static List<IRadarDataSet> toList(IRadarDataSet iRadarDataSet) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(iRadarDataSet);
        return arrayList;
    }
}
