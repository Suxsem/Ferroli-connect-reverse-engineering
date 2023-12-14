package com.github.mikephil.charting.data.realm.implementation;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.realm.base.RealmUtils;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmPieData extends PieData {
    public RealmPieData(RealmResults<? extends RealmObject> realmResults, String str, IPieDataSet iPieDataSet) {
        super(RealmUtils.toXVals(realmResults, str), iPieDataSet);
    }
}
