package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.SelectionDetail;
import java.util.ArrayList;
import java.util.List;

public class CombinedHighlighter extends ChartHighlighter<BarLineScatterCandleBubbleDataProvider> {
    public CombinedHighlighter(BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider) {
        super(barLineScatterCandleBubbleDataProvider);
    }

    /* access modifiers changed from: protected */
    public List<SelectionDetail> getSelectionDetailsAtIndex(int i, int i2) {
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        float[] fArr = new float[2];
        List<ChartData> allData = ((CombinedData) this.mChart.getData()).getAllData();
        for (int i5 = 0; i5 < allData.size(); i5++) {
            for (int i6 = 0; i6 < allData.get(i5).getDataSetCount(); i6++) {
                IDataSet dataSetByIndex = allData.get(i5).getDataSetByIndex(i6);
                if (!dataSetByIndex.isHighlightEnabled()) {
                    int i7 = i;
                } else {
                    float[] yValsForXIndex = dataSetByIndex.getYValsForXIndex(i);
                    int length = yValsForXIndex.length;
                    int i8 = 0;
                    while (i8 < length) {
                        float f = yValsForXIndex[i8];
                        fArr[1] = f;
                        this.mChart.getTransformer(dataSetByIndex.getAxisDependency()).pointValuesToPixel(fArr);
                        if (!Float.isNaN(fArr[1])) {
                            SelectionDetail selectionDetail = r5;
                            i4 = i8;
                            i3 = length;
                            SelectionDetail selectionDetail2 = new SelectionDetail(fArr[1], f, i5, i6, dataSetByIndex);
                            arrayList.add(selectionDetail);
                        } else {
                            i4 = i8;
                            i3 = length;
                        }
                        i8 = i4 + 1;
                        length = i3;
                    }
                }
            }
            int i9 = i;
        }
        return arrayList;
    }
}
