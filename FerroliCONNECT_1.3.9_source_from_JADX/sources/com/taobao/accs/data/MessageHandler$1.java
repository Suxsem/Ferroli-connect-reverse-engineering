package com.taobao.accs.data;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: Taobao */
class MessageHandler$1 extends LinkedHashMap<String, String> {

    /* renamed from: a */
    final /* synthetic */ C2030d f3293a;

    MessageHandler$1(C2030d dVar) {
        this.f3293a = dVar;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry<String, String> entry) {
        return size() > 50;
    }
}
