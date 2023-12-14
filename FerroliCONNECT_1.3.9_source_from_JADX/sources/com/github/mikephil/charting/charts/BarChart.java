package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererBarChart;

public class BarChart extends BarLineChartBase<BarData> implements BarDataProvider {
    private boolean mDrawBarShadow = false;
    private boolean mDrawHighlightArrow = false;
    private boolean mDrawValueAboveBar = true;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BarChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.mRenderer = new BarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mXAxisRenderer = new XAxisRendererBarChart(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer, this);
        setHighlighter(new BarHighlighter(this));
        this.mXAxis.mAxisMinimum = -0.5f;
    }

    /* access modifiers changed from: protected */
    public void calcMinMax() {
        super.calcMinMax();
        this.mXAxis.mAxisRange += 0.5f;
        this.mXAxis.mAxisRange *= (float) ((BarData) this.mData).getDataSetCount();
        float groupSpace = ((BarData) this.mData).getGroupSpace();
        this.mXAxis.mAxisRange += ((float) ((BarData) this.mData).getXValCount()) * groupSpace;
        this.mXAxis.mAxisMaximum = this.mXAxis.mAxisRange - this.mXAxis.mAxisMinimum;
    }

    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData != null) {
            return getHighlighter().getHighlight(f, f2);
        }
        Log.e(Chart.LOG_TAG, "Can't select by touch. No data set.");
        return null;
    }

    public RectF getBarBounds(BarEntry barEntry) {
        IBarDataSet iBarDataSet = (IBarDataSet) ((BarData) this.mData).getDataSetForEntry(barEntry);
        if (iBarDataSet == null) {
            return null;
        }
        float barSpace = iBarDataSet.getBarSpace();
        float val = barEntry.getVal();
        float xIndex = (float) barEntry.getXIndex();
        float f = barSpace / 2.0f;
        float f2 = (xIndex - 0.5f) + f;
        float f3 = (xIndex + 0.5f) - f;
        float f4 = 0.0f;
        float f5 = val >= 0.0f ? val : 0.0f;
        if (val <= 0.0f) {
            f4 = val;
        }
        RectF rectF = new RectF(f2, f5, f3, f4);
        getTransformer(iBarDataSet.getAxisDependency()).rectValueToPixel(rectF);
        return rectF;
    }

    public void setDrawHighlightArrow(boolean z) {
        this.mDrawHighlightArrow = z;
    }

    public boolean isDrawHighlightArrowEnabled() {
        return this.mDrawHighlightArrow;
    }

    public void setDrawValueAboveBar(boolean z) {
        this.mDrawValueAboveBar = z;
    }

    public boolean isDrawValueAboveBarEnabled() {
        return this.mDrawValueAboveBar;
    }

    public void setDrawBarShadow(boolean z) {
        this.mDrawBarShadow = z;
    }

    public boolean isDrawBarShadowEnabled() {
        return this.mDrawBarShadow;
    }

    public BarData getBarData() {
        return (BarData) this.mData;
    }

    public int getLowestVisibleXIndex() {
        float f;
        float dataSetCount = (float) ((BarData) this.mData).getDataSetCount();
        if (dataSetCount <= 1.0f) {
            f = 1.0f;
        } else {
            f = dataSetCount + ((BarData) this.mData).getGroupSpace();
        }
        float[] fArr = {this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom()};
        getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
        return (int) (fArr[0] <= getXChartMin() ? 0.0f : (fArr[0] / f) + 1.0f);
    }

    public int getHighestVisibleXIndex() {
        float dataSetCount = (float) ((BarData) this.mData).getDataSetCount();
        float f = 1.0f;
        if (dataSetCount > 1.0f) {
            f = ((BarData) this.mData).getGroupSpace() + dataSetCount;
        }
        float[] fArr = {this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom()};
        getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(fArr);
        return (int) ((fArr[0] >= getXChartMax() ? getXChartMax() : fArr[0]) / f);
    }
}
