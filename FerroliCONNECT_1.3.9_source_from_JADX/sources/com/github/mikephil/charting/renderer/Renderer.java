package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class Renderer {
    protected int mMaxX = 0;
    protected int mMinX = 0;
    protected ViewPortHandler mViewPortHandler;

    /* access modifiers changed from: protected */
    public boolean fitsBounds(float f, float f2, float f3) {
        return f >= f2 && f <= f3;
    }

    public Renderer(ViewPortHandler viewPortHandler) {
        this.mViewPortHandler = viewPortHandler;
    }

    public void calcXBounds(BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider, int i) {
        int lowestVisibleXIndex = barLineScatterCandleBubbleDataProvider.getLowestVisibleXIndex();
        int highestVisibleXIndex = barLineScatterCandleBubbleDataProvider.getHighestVisibleXIndex();
        this.mMinX = Math.max(((lowestVisibleXIndex / i) * i) - (lowestVisibleXIndex % i == 0 ? i : 0), 0);
        this.mMaxX = Math.min(((highestVisibleXIndex / i) * i) + i, (int) barLineScatterCandleBubbleDataProvider.getXChartMax());
    }
}
