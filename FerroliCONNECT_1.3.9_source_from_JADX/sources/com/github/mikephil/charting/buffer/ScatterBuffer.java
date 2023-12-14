package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

public class ScatterBuffer extends AbstractBuffer<IScatterDataSet> {
    public ScatterBuffer(int i) {
        super(i);
    }

    /* access modifiers changed from: protected */
    public void addForm(float f, float f2) {
        float[] fArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        fArr[i] = f;
        float[] fArr2 = this.buffer;
        int i2 = this.index;
        this.index = i2 + 1;
        fArr2[i2] = f2;
    }

    public void feed(IScatterDataSet iScatterDataSet) {
        float entryCount = ((float) iScatterDataSet.getEntryCount()) * this.phaseX;
        for (int i = 0; ((float) i) < entryCount; i++) {
            Entry entryForIndex = iScatterDataSet.getEntryForIndex(i);
            addForm((float) entryForIndex.getXIndex(), entryForIndex.getVal() * this.phaseY);
        }
        reset();
    }
}
