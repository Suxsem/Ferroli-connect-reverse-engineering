package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class BubbleDataSet extends BarLineScatterCandleBubbleDataSet<BubbleEntry> implements IBubbleDataSet {
    private float mHighlightCircleWidth = 2.5f;
    protected float mMaxSize;
    protected boolean mNormalizeSize = true;
    protected float mXMax;
    protected float mXMin;

    public BubbleDataSet(List<BubbleEntry> list, String str) {
        super(list, str);
    }

    public void setHighlightCircleWidth(float f) {
        this.mHighlightCircleWidth = Utils.convertDpToPixel(f);
    }

    public float getHighlightCircleWidth() {
        return this.mHighlightCircleWidth;
    }

    public void calcMinMax(int i, int i2) {
        if (this.mYVals != null && this.mYVals.size() != 0) {
            if (i2 == 0 || i2 >= this.mYVals.size()) {
                i2 = this.mYVals.size() - 1;
            }
            this.mYMin = yMin((BubbleEntry) this.mYVals.get(i));
            this.mYMax = yMax((BubbleEntry) this.mYVals.get(i));
            while (i <= i2) {
                BubbleEntry bubbleEntry = (BubbleEntry) this.mYVals.get(i);
                float yMin = yMin(bubbleEntry);
                float yMax = yMax(bubbleEntry);
                if (yMin < this.mYMin) {
                    this.mYMin = yMin;
                }
                if (yMax > this.mYMax) {
                    this.mYMax = yMax;
                }
                float xMin = xMin(bubbleEntry);
                float xMax = xMax(bubbleEntry);
                if (xMin < this.mXMin) {
                    this.mXMin = xMin;
                }
                if (xMax > this.mXMax) {
                    this.mXMax = xMax;
                }
                float largestSize = largestSize(bubbleEntry);
                if (largestSize > this.mMaxSize) {
                    this.mMaxSize = largestSize;
                }
                i++;
            }
        }
    }

    public DataSet<BubbleEntry> copy() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mYVals.size(); i++) {
            arrayList.add(((BubbleEntry) this.mYVals.get(i)).copy());
        }
        BubbleDataSet bubbleDataSet = new BubbleDataSet(arrayList, getLabel());
        bubbleDataSet.mColors = this.mColors;
        bubbleDataSet.mHighLightColor = this.mHighLightColor;
        return bubbleDataSet;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getMaxSize() {
        return this.mMaxSize;
    }

    public boolean isNormalizeSizeEnabled() {
        return this.mNormalizeSize;
    }

    public void setNormalizeSizeEnabled(boolean z) {
        this.mNormalizeSize = z;
    }

    private float yMin(BubbleEntry bubbleEntry) {
        return bubbleEntry.getVal();
    }

    private float yMax(BubbleEntry bubbleEntry) {
        return bubbleEntry.getVal();
    }

    private float xMin(BubbleEntry bubbleEntry) {
        return (float) bubbleEntry.getXIndex();
    }

    private float xMax(BubbleEntry bubbleEntry) {
        return (float) bubbleEntry.getXIndex();
    }

    private float largestSize(BubbleEntry bubbleEntry) {
        return bubbleEntry.getSize();
    }
}
