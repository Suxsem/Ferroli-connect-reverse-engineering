package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.SelectionDetail;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class ChartHighlighter<T extends BarLineScatterCandleBubbleDataProvider> {
    protected T mChart;

    public ChartHighlighter(T t) {
        this.mChart = t;
    }

    public Highlight getHighlight(float f, float f2) {
        int xIndex = getXIndex(f);
        SelectionDetail selectionDetail = getSelectionDetail(xIndex, f2, -1);
        if (selectionDetail == null) {
            return null;
        }
        return new Highlight(xIndex, selectionDetail.value, selectionDetail.dataIndex, selectionDetail.dataSetIndex);
    }

    /* access modifiers changed from: protected */
    public int getXIndex(float f) {
        float[] fArr = new float[2];
        fArr[0] = f;
        this.mChart.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
        return Math.round(fArr[0]);
    }

    /* access modifiers changed from: protected */
    public SelectionDetail getSelectionDetail(int i, float f, int i2) {
        List<SelectionDetail> selectionDetailsAtIndex = getSelectionDetailsAtIndex(i, i2);
        return Utils.getClosestSelectionDetailByPixelY(selectionDetailsAtIndex, f, Utils.getMinimumDistance(selectionDetailsAtIndex, f, YAxis.AxisDependency.LEFT) < Utils.getMinimumDistance(selectionDetailsAtIndex, f, YAxis.AxisDependency.RIGHT) ? YAxis.AxisDependency.LEFT : YAxis.AxisDependency.RIGHT);
    }

    /* access modifiers changed from: protected */
    public List<SelectionDetail> getSelectionDetailsAtIndex(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (this.mChart.getData() == null) {
            return arrayList;
        }
        float[] fArr = new float[2];
        int dataSetCount = this.mChart.getData().getDataSetCount();
        for (int i3 = 0; i3 < dataSetCount; i3++) {
            if (i2 <= -1 || i2 == i3) {
                IDataSet dataSetByIndex = this.mChart.getData().getDataSetByIndex(i3);
                if (dataSetByIndex.isHighlightEnabled()) {
                    for (float f : dataSetByIndex.getYValsForXIndex(i)) {
                        if (!Float.isNaN(f)) {
                            fArr[1] = f;
                            this.mChart.getTransformer(dataSetByIndex.getAxisDependency()).pointValuesToPixel(fArr);
                            if (!Float.isNaN(fArr[1])) {
                                arrayList.add(new SelectionDetail(fArr[1], f, i3, dataSetByIndex));
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }
}
