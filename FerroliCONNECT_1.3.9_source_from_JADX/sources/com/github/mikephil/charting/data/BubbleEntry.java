package com.github.mikephil.charting.data;

public class BubbleEntry extends Entry {
    private float mSize = 0.0f;

    public BubbleEntry(int i, float f, float f2) {
        super(f, i);
        this.mSize = f2;
    }

    public BubbleEntry(int i, float f, float f2, Object obj) {
        super(f, i, obj);
        this.mSize = f2;
    }

    public BubbleEntry copy() {
        return new BubbleEntry(getXIndex(), getVal(), this.mSize, getData());
    }

    public float getSize() {
        return this.mSize;
    }

    public void setSize(float f) {
        this.mSize = f;
    }
}
