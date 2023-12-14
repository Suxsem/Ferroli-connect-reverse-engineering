package com.github.mikephil.charting.data;

public class BarEntry extends Entry {
    private float mNegativeSum;
    private float mPositiveSum;
    private float[] mVals;

    public BarEntry(float[] fArr, int i) {
        super(calcSum(fArr), i);
        this.mVals = fArr;
        calcPosNegSum();
    }

    public BarEntry(float f, int i) {
        super(f, i);
    }

    public BarEntry(float[] fArr, int i, String str) {
        super(calcSum(fArr), i, str);
        this.mVals = fArr;
        calcPosNegSum();
    }

    public BarEntry(float f, int i, Object obj) {
        super(f, i, obj);
    }

    public BarEntry copy() {
        BarEntry barEntry = new BarEntry(getVal(), getXIndex(), getData());
        barEntry.setVals(this.mVals);
        return barEntry;
    }

    public float[] getVals() {
        return this.mVals;
    }

    public void setVals(float[] fArr) {
        setVal(calcSum(fArr));
        this.mVals = fArr;
        calcPosNegSum();
    }

    public float getVal() {
        return super.getVal();
    }

    public boolean isStacked() {
        return this.mVals != null;
    }

    public float getBelowSum(int i) {
        float[] fArr = this.mVals;
        float f = 0.0f;
        if (fArr == null) {
            return 0.0f;
        }
        int length = fArr.length - 1;
        while (length > i && length >= 0) {
            f += this.mVals[length];
            length--;
        }
        return f;
    }

    public float getPositiveSum() {
        return this.mPositiveSum;
    }

    public float getNegativeSum() {
        return this.mNegativeSum;
    }

    private void calcPosNegSum() {
        float[] fArr = this.mVals;
        if (fArr == null) {
            this.mNegativeSum = 0.0f;
            this.mPositiveSum = 0.0f;
            return;
        }
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 : fArr) {
            if (f3 <= 0.0f) {
                f += Math.abs(f3);
            } else {
                f2 += f3;
            }
        }
        this.mNegativeSum = f;
        this.mPositiveSum = f2;
    }

    private static float calcSum(float[] fArr) {
        float f = 0.0f;
        if (fArr == null) {
            return 0.0f;
        }
        for (float f2 : fArr) {
            f += f2;
        }
        return f;
    }
}
