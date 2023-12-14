package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;

public class BubbleChart extends BarLineChartBase<BubbleData> implements BubbleDataProvider {
    public BubbleChart(Context context) {
        super(context);
    }

    public BubbleChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BubbleChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.mRenderer = new BubbleChartRenderer(this, this.mAnimator, this.mViewPortHandler);
    }

    /* access modifiers changed from: protected */
    public void calcMinMax() {
        super.calcMinMax();
        if (this.mXAxis.mAxisRange == 0.0f && ((BubbleData) this.mData).getYValCount() > 0) {
            this.mXAxis.mAxisRange = 1.0f;
        }
        this.mXAxis.mAxisMinimum = -0.5f;
        this.mXAxis.mAxisMaximum = ((float) ((BubbleData) this.mData).getXValCount()) - 0.5f;
        if (this.mRenderer != null) {
            for (IBubbleDataSet iBubbleDataSet : ((BubbleData) this.mData).getDataSets()) {
                float xMin = iBubbleDataSet.getXMin();
                float xMax = iBubbleDataSet.getXMax();
                if (xMin < this.mXAxis.mAxisMinimum) {
                    this.mXAxis.mAxisMinimum = xMin;
                }
                if (xMax > this.mXAxis.mAxisMaximum) {
                    this.mXAxis.mAxisMaximum = xMax;
                }
            }
        }
        this.mXAxis.mAxisRange = Math.abs(this.mXAxis.mAxisMaximum - this.mXAxis.mAxisMinimum);
    }

    public BubbleData getBubbleData() {
        return (BubbleData) this.mData;
    }
}
