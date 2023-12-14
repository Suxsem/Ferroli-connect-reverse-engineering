package anet.channel.strategy.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: Taobao */
public class SerialLruCache<K, V> extends LinkedHashMap<K, V> {

    /* renamed from: a */
    private int f551a;

    public boolean entryRemoved(Map.Entry<K, V> entry) {
        return true;
    }

    public SerialLruCache(LinkedHashMap<K, V> linkedHashMap, int i) {
        super(linkedHashMap);
        this.f551a = i;
    }

    @Deprecated
    public SerialLruCache(LinkedHashMap<K, V> linkedHashMap) {
        this(linkedHashMap, 256);
    }

    public SerialLruCache(int i) {
        super(i + 1, 1.0f, true);
        this.f551a = i;
    }

    /* access modifiers changed from: protected */
    public boolean removeEldestEntry(Map.Entry<K, V> entry) {
        if (size() > this.f551a) {
            return entryRemoved(entry);
        }
        return false;
    }
}
