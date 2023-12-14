package com.github.mikephil.charting.data.realm.base;

import android.graphics.Color;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import io.realm.RealmObject;
import io.realm.RealmResults;

public abstract class RealmBarLineScatterCandleBubbleDataSet<T extends RealmObject, S extends Entry> extends RealmBaseDataSet<T, S> implements IBarLineScatterCandleBubbleDataSet<S> {
    protected int mHighLightColor = Color.rgb(255, 187, 115);

    public RealmBarLineScatterCandleBubbleDataSet(RealmResults<T> realmResults, String str) {
        super(realmResults, str);
    }

    public RealmBarLineScatterCandleBubbleDataSet(RealmResults<T> realmResults, String str, String str2) {
        super(realmResults, str, str2);
    }

    public void setHighLightColor(int i) {
        this.mHighLightColor = i;
    }

    public int getHighLightColor() {
        return this.mHighLightColor;
    }
}
