package com.github.mikephil.charting.highlight;

public class Highlight {
    private int mDataIndex;
    private int mDataSetIndex;
    private Range mRange;
    private int mStackIndex;
    private float mValue;
    private int mXIndex;

    public Highlight(int i, float f, int i2, int i3) {
        this.mValue = Float.NaN;
        this.mStackIndex = -1;
        this.mXIndex = i;
        this.mValue = f;
        this.mDataIndex = i2;
        this.mDataSetIndex = i3;
    }

    public Highlight(int i, float f, int i2, int i3, int i4) {
        this(i, f, i2, i3);
        this.mStackIndex = i4;
    }

    public Highlight(int i, float f, int i2, int i3, int i4, Range range) {
        this(i, f, i2, i3, i4);
        this.mRange = range;
    }

    public Highlight(int i, int i2) {
        this(i, Float.NaN, 0, i2, -1);
    }

    public int getXIndex() {
        return this.mXIndex;
    }

    public float getValue() {
        return this.mValue;
    }

    public int getDataIndex() {
        return this.mDataIndex;
    }

    public int getDataSetIndex() {
        return this.mDataSetIndex;
    }

    public int getStackIndex() {
        return this.mStackIndex;
    }

    public Range getRange() {
        return this.mRange;
    }

    public boolean equalTo(Highlight highlight) {
        return highlight != null && this.mDataSetIndex == highlight.mDataSetIndex && this.mXIndex == highlight.mXIndex && this.mStackIndex == highlight.mStackIndex;
    }

    public String toString() {
        return "Highlight, xIndex: " + this.mXIndex + ", dataSetIndex: " + this.mDataSetIndex + ", stackIndex (only stacked barentry): " + this.mStackIndex;
    }
}
