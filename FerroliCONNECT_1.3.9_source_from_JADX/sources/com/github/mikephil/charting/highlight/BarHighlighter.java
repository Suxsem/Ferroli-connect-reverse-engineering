package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.SelectionDetail;

public class BarHighlighter extends ChartHighlighter<BarDataProvider> {
    public BarHighlighter(BarDataProvider barDataProvider) {
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
            fArr[1] = f2;
            ((BarDataProvider) this.mChart).getTransformer(iBarDataSet.getAxisDependency()).pixelsToValue(fArr);
            return getStackedHighlight(selectionDetail, iBarDataSet, xIndex, (double) fArr[1]);
        }
        return new Highlight(xIndex, selectionDetail.value, selectionDetail.dataIndex, selectionDetail.dataSetIndex, -1);
    }

    /* access modifiers changed from: protected */
    public int getXIndex(float f) {
        if (!((BarDataProvider) this.mChart).getBarData().isGrouped()) {
            return super.getXIndex(f);
        }
        int base = ((int) getBase(f)) / ((BarDataProvider) this.mChart).getBarData().getDataSetCount();
        int xValCount = ((BarDataProvider) this.mChart).getData().getXValCount();
        if (base < 0) {
            return 0;
        }
        return base >= xValCount ? xValCount - 1 : base;
    }

    /* access modifiers changed from: protected */
    public SelectionDetail getSelectionDetail(int i, float f, int i2) {
        int max = Math.max(i2, 0);
        BarData barData = ((BarDataProvider) this.mChart).getBarData();
        IBarDataSet iBarDataSet = barData.getDataSetCount() > max ? (IBarDataSet) barData.getDataSetByIndex(max) : null;
        if (iBarDataSet == null) {
            return null;
        }
        float yValForXIndex = iBarDataSet.getYValForXIndex(i);
        if (((double) yValForXIndex) == Double.NaN) {
            return null;
        }
        return new SelectionDetail(yValForXIndex, max, iBarDataSet);
    }

    /* access modifiers changed from: protected */
    public Highlight getStackedHighlight(SelectionDetail selectionDetail, IBarDataSet iBarDataSet, int i, double d) {
        BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForXIndex(i);
        if (barEntry == null) {
            return null;
        }
        if (barEntry.getVals() == null) {
            return new Highlight(i, barEntry.getVal(), selectionDetail.dataIndex, selectionDetail.dataSetIndex);
        }
        Range[] ranges = getRanges(barEntry);
        if (ranges.length <= 0) {
            return null;
        }
        int closestStackIndex = getClosestStackIndex(ranges, (float) d);
        return new Highlight(i, barEntry.getPositiveSum() - barEntry.getNegativeSum(), selectionDetail.dataIndex, selectionDetail.dataSetIndex, closestStackIndex, ranges[closestStackIndex]);
    }

    /* access modifiers changed from: protected */
    public int getClosestStackIndex(Range[] rangeArr, float f) {
        if (rangeArr == null || rangeArr.length == 0) {
            return 0;
        }
        int i = 0;
        for (Range contains : rangeArr) {
            if (contains.contains(f)) {
                return i;
            }
            i++;
        }
        int max = Math.max(rangeArr.length - 1, 0);
        if (f > rangeArr[max].f1480to) {
            return max;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public float getBase(float f) {
        float[] fArr = new float[2];
        fArr[0] = f;
        ((BarDataProvider) this.mChart).getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
        float f2 = fArr[0];
        return f2 - (((BarDataProvider) this.mChart).getBarData().getGroupSpace() * ((float) ((int) (f2 / (((float) ((BarDataProvider) this.mChart).getBarData().getDataSetCount()) + ((BarDataProvider) this.mChart).getBarData().getGroupSpace())))));
    }

    /* access modifiers changed from: protected */
    public Range[] getRanges(BarEntry barEntry) {
        float[] vals = barEntry.getVals();
        if (vals == null || vals.length == 0) {
            return new Range[0];
        }
        Range[] rangeArr = new Range[vals.length];
        float f = -barEntry.getNegativeSum();
        float f2 = 0.0f;
        for (int i = 0; i < rangeArr.length; i++) {
            float f3 = vals[i];
            if (f3 < 0.0f) {
                rangeArr[i] = new Range(f, Math.abs(f3) + f);
                f += Math.abs(f3);
            } else {
                float f4 = f3 + f2;
                rangeArr[i] = new Range(f2, f4);
                f2 = f4;
            }
        }
        return rangeArr;
    }
}
