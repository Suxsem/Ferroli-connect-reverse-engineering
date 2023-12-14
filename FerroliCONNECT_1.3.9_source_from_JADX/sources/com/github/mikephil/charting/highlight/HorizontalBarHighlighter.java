package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.SelectionDetail;

public class HorizontalBarHighlighter extends BarHighlighter {
    public HorizontalBarHighlighter(BarDataProvider barDataProvider) {
        super(barDataProvider);
    }

    public Highlight getHighlight(float f, float f2) {
        BarData barData = ((BarDataProvider) this.mChart).getBarData();
        int xIndex = getXIndex(f);
        float base = getBase(f);
        int dataSetCount = barData.getDataSetCount();
        int i = ((int) base) % dataSetCount;
        if (i < 0) {
            i = 0;
        } else if (i >= dataSetCount) {
            i = dataSetCount - 1;
        }
        SelectionDetail selectionDetail = getSelectionDetail(xIndex, f2, i);
        if (selectionDetail == null) {
            return null;
        }
        IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
        if (iBarDataSet.isStacked()) {
            float[] fArr = new float[2];
            fArr[0] = f2;
            ((BarDataProvider) this.mChart).getTransformer(iBarDataSet.getAxisDependency()).pixelsToValue(fArr);
            return getStackedHighlight(selectionDetail, iBarDataSet, xIndex, (double) fArr[0]);
        }
        return new Highlight(xIndex, selectionDetail.value, selectionDetail.dataIndex, selectionDetail.dataSetIndex, -1);
    }

    /* access modifiers changed from: protected */
    public int getXIndex(float f) {
        if (!((BarDataProvider) this.mChart).getBarData().isGrouped()) {
            float[] fArr = new float[2];
            fArr[1] = f;
            ((BarDataProvider) this.mChart).getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
            return Math.round(fArr[1]);
        }
        int base = ((int) getBase(f)) / ((BarDataProvider) this.mChart).getBarData().getDataSetCount();
        int xValCount = ((BarDataProvider) this.mChart).getData().getXValCount();
        if (base < 0) {
            return 0;
        }
        return base >= xValCount ? xValCount - 1 : base;
    }

    /* access modifiers changed from: protected */
    public float getBase(float f) {
        float[] fArr = new float[2];
        fArr[1] = f;
        ((BarDataProvider) this.mChart).getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
        float f2 = fArr[1];
        return f2 - (((BarDataProvider) this.mChart).getBarData().getGroupSpace() * ((float) ((int) (f2 / (((float) ((BarDataProvider) this.mChart).getBarData().getDataSetCount()) + ((BarDataProvider) this.mChart).getBarData().getGroupSpace())))));
    }
}
