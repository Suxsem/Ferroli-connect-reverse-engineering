package com.github.mikephil.charting.data.realm.implementation;

import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.realm.base.RealmUtils;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;

public class RealmBubbleData extends BubbleData {
    public RealmBubbleData(RealmResults<? extends RealmObject> realmResults, String str, List<IBubbleDataSet> list) {
        super(RealmUtils.toXVals(realmResults, str), list);
    }
}
