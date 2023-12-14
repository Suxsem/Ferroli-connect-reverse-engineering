package com.taobao.accs.net;

import com.taobao.accs.data.Message;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: Taobao */
class BaseConnection$1 extends LinkedHashMap<Integer, Message> {

    /* renamed from: a */
    final /* synthetic */ C2049b f3369a;

    BaseConnection$1(C2049b bVar) {
        this.f3369a = bVar;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry<Integer, Message> entry) {
        return size() > 10;
    }
}
