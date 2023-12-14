package com.github.mikephil.charting.data.realm.implementation;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.realm.base.RealmUtils;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.List;

public class RealmBarData extends BarData {
    public RealmBarData(RealmResults<? extends RealmObject> realmResults, String str, List<IBarDataSet> list) {
        super(RealmUtils.toXVals(realmResults, str), list);
    }
}
