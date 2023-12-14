package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class HorizontalBarBuffer extends BarBuffer {
    public HorizontalBarBuffer(int i, float f, int i2, boolean z) {
        super(i, f, i2, z);
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
                addBar(f, f11, val, f10);
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
                    addBar(f4 * this.phaseY, f18, f5 * this.phaseY, f17);
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
