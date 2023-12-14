package com.github.mikephil.charting.data.realm.base;

import io.realm.DynamicRealmObject;
import io.realm.RealmObject;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class RealmUtils {
    public static List<String> toXVals(RealmResults<? extends RealmObject> realmResults, String str) {
        ArrayList arrayList = new ArrayList();
        Iterator it = realmResults.iterator();
        while (it.hasNext()) {
            arrayList.add(new DynamicRealmObject((RealmObject) it.next()).getString(str));
        }
        return arrayList;
    }
}
