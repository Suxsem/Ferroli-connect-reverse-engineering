package anet.channel.p008e;

import android.content.SharedPreferences;
import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.C0583l;
import anet.channel.strategy.IStrategyListener;

/* renamed from: anet.channel.e.c */
/* compiled from: Taobao */
final class C0512c implements IStrategyListener {
    C0512c() {
    }

    public void onStrategyUpdated(C0583l.C0587d dVar) {
        if (dVar != null && dVar.f538b != null) {
            for (int i = 0; i < dVar.f538b.length; i++) {
                String str = dVar.f538b[i].f523a;
                C0583l.C0584a[] aVarArr = dVar.f538b[i].f530h;
                if (aVarArr != null && aVarArr.length > 0) {
                    for (C0583l.C0584a aVar : aVarArr) {
                        String str2 = aVar.f516b;
                        if (ConnType.HTTP3.equals(str2) || ConnType.HTTP3_PLAIN.equals(str2)) {
                            if (!str.equals(C0508a.f224b)) {
                                String unused = C0508a.f224b = str;
                                SharedPreferences.Editor edit = C0508a.f228f.edit();
                                edit.putString("http3_detector_host", C0508a.f224b);
                                edit.apply();
                            }
                            C0508a.m106a(NetworkStatusHelper.getStatus());
                            return;
                        }
                    }
                    continue;
                }
            }
        }
    }
}
