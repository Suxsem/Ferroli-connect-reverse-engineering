package com.github.mikephil.charting.data.realm.base;

import android.graphics.DashPathEffect;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class RealmLineScatterCandleRadarDataSet<T extends RealmObject, S extends Entry> extends RealmBarLineScatterCandleBubbleDataSet<T, S> implements ILineScatterCandleRadarDataSet<S> {
    protected boolean mDrawHorizontalHighlightIndicator = true;
    protected boolean mDrawVerticalHighlightIndicator = true;
    protected DashPathEffect mHighlightDashPathEffect = null;
    protected float mHighlightLineWidth = 0.5f;

    public RealmLineScatterCandleRadarDataSet(RealmResults<T> realmResults, String str) {
        super(realmResults, str);
    }

    public RealmLineScatterCandleRadarDataSet(RealmResults<T> realmResults, String str, String str2) {
        super(realmResults, str, str2);
    }

    public void setDrawHorizontalHighlightIndicator(boolean z) {
        this.mDrawHorizontalHighlightIndicator = z;
    }

    public void setDrawVerticalHighlightIndicator(boolean z) {
        this.mDrawVerticalHighlightIndicator = z;
    }

    public void setDrawHighlightIndicators(boolean z) {
        setDrawVerticalHighlightIndicator(z);
        setDrawHorizontalHighlightIndicator(z);
    }

    public boolean isVerticalHighlightIndicatorEnabled() {
        return this.mDrawVerticalHighlightIndicator;
    }

    public boolean isHorizontalHighlightIndicatorEnabled() {
        return this.mDrawHorizontalHighlightIndicator;
    }

    public void setHighlightLineWidth(float f) {
        this.mHighlightLineWidth = Utils.convertDpToPixel(f);
    }

    public float getHighlightLineWidth() {
        return this.mHighlightLineWidth;
    }

    public void enableDashedHighlightLine(float f, float f2, float f3) {
        this.mHighlightDashPathEffect = new DashPathEffect(new float[]{f, f2}, f3);
    }

    public void disableDashedHighlightLine() {
        this.mHighlightDashPathEffect = null;
    }

    public boolean isDashedHighlightLineEnabled() {
        return this.mHighlightDashPathEffect != null;
    }

    public DashPathEffect getDashPathEffectHighlight() {
        return this.mHighlightDashPathEffect;
    }
}
