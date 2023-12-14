package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.interfaces.datasets.IDataSet;

public class SelectionDetail {
    public int dataIndex;
    public IDataSet dataSet;
    public int dataSetIndex;
    public float value;

    /* renamed from: y */
    public float f1492y;

    public SelectionDetail(float f, float f2, int i, int i2, IDataSet iDataSet) {
        this.f1492y = f;
        this.value = f2;
        this.dataIndex = i;
        this.dataSetIndex = i2;
        this.dataSet = iDataSet;
    }

    public SelectionDetail(float f, float f2, int i, IDataSet iDataSet) {
        this(f, f2, 0, i, iDataSet);
    }

    public SelectionDetail(float f, int i, IDataSet iDataSet) {
        this(Float.NaN, f, 0, i, iDataSet);
    }
}
