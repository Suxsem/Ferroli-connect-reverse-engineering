package com.github.mikephil.charting.data;

public class CandleEntry extends Entry {
    private float mClose = 0.0f;
    private float mOpen = 0.0f;
    private float mShadowHigh = 0.0f;
    private float mShadowLow = 0.0f;

    public CandleEntry(int i, float f, float f2, float f3, float f4) {
        super((f + f2) / 2.0f, i);
        this.mShadowHigh = f;
        this.mShadowLow = f2;
        this.mOpen = f3;
        this.mClose = f4;
    }

    public CandleEntry(int i, float f, float f2, float f3, float f4, Object obj) {
        super((f + f2) / 2.0f, i, obj);
        this.mShadowHigh = f;
        this.mShadowLow = f2;
        this.mOpen = f3;
        this.mClose = f4;
    }

    public float getShadowRange() {
        return Math.abs(this.mShadowHigh - this.mShadowLow);
    }

    public float getBodyRange() {
        return Math.abs(this.mOpen - this.mClose);
    }

    public float getVal() {
        return super.getVal();
    }

    public CandleEntry copy() {
        return new CandleEntry(getXIndex(), this.mShadowHigh, this.mShadowLow, this.mOpen, this.mClose, getData());
    }

    public float getHigh() {
        return this.mShadowHigh;
    }

    public void setHigh(float f) {
        this.mShadowHigh = f;
    }

    public float getLow() {
        return this.mShadowLow;
    }

    public void setLow(float f) {
        this.mShadowLow = f;
    }

    public float getClose() {
        return this.mClose;
    }

    public void setClose(float f) {
        this.mClose = f;
    }

    public float getOpen() {
        return this.mOpen;
    }

    public void setOpen(float f) {
        this.mOpen = f;
    }
}
