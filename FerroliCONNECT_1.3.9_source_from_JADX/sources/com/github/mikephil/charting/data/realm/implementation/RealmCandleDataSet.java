package com.github.mikephil.charting.data.realm.implementation;

import android.graphics.Paint;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.realm.base.RealmLineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import io.realm.DynamicRealmObject;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmCandleDataSet<T extends RealmObject> extends RealmLineScatterCandleRadarDataSet<T, CandleEntry> implements ICandleDataSet {
    private float mBarSpace = 0.1f;
    private String mCloseField;
    protected int mDecreasingColor = ColorTemplate.COLOR_NONE;
    protected Paint.Style mDecreasingPaintStyle = Paint.Style.FILL;
    private String mHighField;
    protected int mIncreasingColor = ColorTemplate.COLOR_NONE;
    protected Paint.Style mIncreasingPaintStyle = Paint.Style.STROKE;
    private String mLowField;
    protected int mNeutralColor = ColorTemplate.COLOR_NONE;
    private String mOpenField;
    protected int mShadowColor = ColorTemplate.COLOR_NONE;
    private boolean mShadowColorSameAsCandle = false;
    private float mShadowWidth = 3.0f;
    private boolean mShowCandleBar = true;

    public RealmCandleDataSet(RealmResults<T> realmResults, String str, String str2, String str3, String str4) {
        super(realmResults, (String) null);
        this.mHighField = str;
        this.mLowField = str2;
        this.mOpenField = str3;
        this.mCloseField = str4;
        build(this.results);
        calcMinMax(0, this.results.size());
    }

    public RealmCandleDataSet(RealmResults<T> realmResults, String str, String str2, String str3, String str4, String str5) {
        super(realmResults, (String) null, str5);
        this.mHighField = str;
        this.mLowField = str2;
        this.mOpenField = str3;
        this.mCloseField = str4;
        build(this.results);
        calcMinMax(0, this.results.size());
    }

    public CandleEntry buildEntryFromResultObject(T t, int i) {
        DynamicRealmObject dynamicRealmObject = new DynamicRealmObject(t);
        if (this.mIndexField != null) {
            i = dynamicRealmObject.getInt(this.mIndexField);
        }
        return new CandleEntry(i, dynamicRealmObject.getFloat(this.mHighField), dynamicRealmObject.getFloat(this.mLowField), dynamicRealmObject.getFloat(this.mOpenField), dynamicRealmObject.getFloat(this.mCloseField));
    }

    public void calcMinMax(int i, int i2) {
        if (this.mValues != null && this.mValues.size() != 0) {
            if (i2 == 0 || i2 >= this.mValues.size()) {
                i2 = this.mValues.size() - 1;
            }
            this.mYMin = Float.MAX_VALUE;
            this.mYMax = -3.4028235E38f;
            while (i <= i2) {
                CandleEntry candleEntry = (CandleEntry) this.mValues.get(i);
                if (candleEntry.getLow() < this.mYMin) {
                    this.mYMin = candleEntry.getLow();
                }
                if (candleEntry.getHigh() > this.mYMax) {
                    this.mYMax = candleEntry.getHigh();
                }
                i++;
            }
        }
    }

    public void setBarSpace(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 0.45f) {
            f = 0.45f;
        }
        this.mBarSpace = f;
    }

    public float getBarSpace() {
        return this.mBarSpace;
    }

    public void setShadowWidth(float f) {
        this.mShadowWidth = Utils.convertDpToPixel(f);
    }

    public float getShadowWidth() {
        return this.mShadowWidth;
    }

    public void setShowCandleBar(boolean z) {
        this.mShowCandleBar = z;
    }

    public boolean getShowCandleBar() {
        return this.mShowCandleBar;
    }

    public void setNeutralColor(int i) {
        this.mNeutralColor = i;
    }

    public int getNeutralColor() {
        return this.mNeutralColor;
    }

    public void setIncreasingColor(int i) {
        this.mIncreasingColor = i;
    }

    public int getIncreasingColor() {
        return this.mIncreasingColor;
    }

    public void setDecreasingColor(int i) {
        this.mDecreasingColor = i;
    }

    public int getDecreasingColor() {
        return this.mDecreasingColor;
    }

    public Paint.Style getIncreasingPaintStyle() {
        return this.mIncreasingPaintStyle;
    }

    public void setIncreasingPaintStyle(Paint.Style style) {
        this.mIncreasingPaintStyle = style;
    }

    public Paint.Style getDecreasingPaintStyle() {
        return this.mDecreasingPaintStyle;
    }

    public void setDecreasingPaintStyle(Paint.Style style) {
        this.mDecreasingPaintStyle = style;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public void setShadowColor(int i) {
        this.mShadowColor = i;
    }

    public boolean getShadowColorSameAsCandle() {
        return this.mShadowColorSameAsCandle;
    }

    public void setShadowColorSameAsCandle(boolean z) {
        this.mShadowColorSameAsCandle = z;
    }
}
