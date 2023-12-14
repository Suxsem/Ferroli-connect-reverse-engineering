package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarBuffer extends AbstractBuffer<IBarDataSet> {
    protected float mBarSpace = 0.0f;
    protected boolean mContainsStacks = false;
    protected int mDataSetCount = 1;
    protected int mDataSetIndex = 0;
    protected float mGroupSpace = 0.0f;
    protected boolean mInverted = false;

    public BarBuffer(int i, float f, int i2, boolean z) {
        super(i);
        this.mGroupSpace = f;
        this.mDataSetCount = i2;
        this.mContainsStacks = z;
    }

    public void setBarSpace(float f) {
        this.mBarSpace = f;
    }

    public void setDataSet(int i) {
        this.mDataSetIndex = i;
    }

    public void setInverted(boolean z) {
        this.mInverted = z;
    }

    /* access modifiers changed from: protected */
    public void addBar(float f, float f2, float f3, float f4) {
        float[] fArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        fArr[i] = f;
        float[] fArr2 = this.buffer;
        int i2 = this.index;
        this.index = i2 + 1;
        fArr2[i2] = f2;
        float[] fArr3 = this.buffer;
        int i3 = this.index;
        this.index = i3 + 1;
        fArr3[i3] = f3;
        float[] fArr4 = this.buffer;
        int i4 = this.index;
        this.index = i4 + 1;
        fArr4[i4] = f4;
    }

    public void feed(IBarDataSet iBarDataSet) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float entryCount = ((float) iBarDataSet.getEntryCount()) * this.phaseX;
        int i = this.mDataSetCount - 1;
        float f6 = this.mBarSpace / 2.0f;
        float f7 = this.mGroupSpace / 2.0f;
        for (int i2 = 0; ((float) i2) < entryCount; i2++) {
            BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForIndex(i2);
            float xIndex = ((float) (barEntry.getXIndex() + (barEntry.getXIndex() * i) + this.mDataSetIndex)) + (this.mGroupSpace * ((float) barEntry.getXIndex())) + f7;
            float val = barEntry.getVal();
            float[] vals = barEntry.getVals();
            float f8 = 0.0f;
            float f9 = 0.5f;
            if (!this.mContainsStacks || vals == null) {
                float f10 = (xIndex - 0.5f) + f6;
                float f11 = (xIndex + 0.5f) - f6;
                if (this.mInverted) {
                    f2 = 0.0f;
                    f = val >= 0.0f ? val : 0.0f;
                    if (val > 0.0f) {
                        val = 0.0f;
                    }
                } else {
                    f2 = 0.0f;
                    float f12 = val >= 0.0f ? val : 0.0f;
                    if (val > 0.0f) {
                        val = 0.0f;
                    }
                    float f13 = f12;
                    f = val;
                    val = f13;
                }
                if (val > f2) {
                    val *= this.phaseY;
                } else {
                    f *= this.phaseY;
                }
                addBar(f10, val, f11, f);
            } else {
                float f14 = -barEntry.getNegativeSum();
                int i3 = 0;
                float f15 = 0.0f;
                while (i3 < vals.length) {
                    float f16 = vals[i3];
                    if (f16 >= f8) {
                        f4 = f16 + f15;
                        f3 = f14;
                        f14 = f15;
                        f15 = f4;
                    } else {
                        float abs = f14 + Math.abs(f16);
                        f3 = Math.abs(f16) + f14;
                        f4 = abs;
                    }
                    float f17 = (xIndex - f9) + f6;
                    float f18 = (xIndex + f9) - f6;
                    if (this.mInverted) {
                        float f19 = f14 >= f4 ? f14 : f4;
                        if (f14 <= f4) {
                            f4 = f14;
                        }
                        float f20 = f4;
                        f4 = f19;
                        f5 = f20;
                    } else {
                        f5 = f14 >= f4 ? f14 : f4;
                        if (f14 <= f4) {
                            f4 = f14;
                        }
                    }
                    addBar(f17, f5 * this.phaseY, f18, f4 * this.phaseY);
                    i3++;
                    f14 = f3;
                    f8 = 0.0f;
                    f9 = 0.5f;
                }
            }
        }
        reset();
    }
}
