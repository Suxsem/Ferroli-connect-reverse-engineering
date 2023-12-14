package org.eclipse.jetty.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MultiMap<K> implements ConcurrentMap<K, Object>, Serializable {
    private static final long serialVersionUID = -6878723138353851005L;
    ConcurrentMap<K, Object> _cmap;
    Map<K, Object> _map;

    public MultiMap() {
        this._map = new HashMap();
    }

    public MultiMap(Map<K, Object> map) {
        if (map instanceof ConcurrentMap) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(map);
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap(map);
    }

    public MultiMap(MultiMap<K> multiMap) {
        ConcurrentMap<K, Object> concurrentMap = multiMap._cmap;
        if (concurrentMap != null) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(concurrentMap);
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap(multiMap._map);
    }

    public MultiMap(int i) {
        this._map = new HashMap(i);
    }

    public MultiMap(boolean z) {
        if (z) {
            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
            this._cmap = concurrentHashMap;
            this._map = concurrentHashMap;
            return;
        }
        this._map = new HashMap();
    }

    public List getValues(Object obj) {
        return LazyList.getList(this._map.get(obj), true);
    }

    public Object getValue(Object obj, int i) {
        Object obj2 = this._map.get(obj);
        if (i == 0 && LazyList.size(obj2) == 0) {
            return null;
        }
        return LazyList.get(obj2, i);
    }

    public String getString(Object obj) {
        Object obj2 = this._map.get(obj);
        int size = LazyList.size(obj2);
        if (size == 0) {
            return null;
        }
        if (size != 1) {
            StringBuilder sb = new StringBuilder(128);
            for (int i = 0; i < LazyList.size(obj2); i++) {
                Object obj3 = LazyList.get(obj2, i);
                if (obj3 != null) {
                    if (sb.length() > 0) {
                        sb.append(',');
                    }
                    sb.append(obj3.toString());
                }
            }
            return sb.toString();
        }
        Object obj4 = LazyList.get(obj2, 0);
        if (obj4 == null) {
            return null;
        }
        return obj4.toString();
    }

    public Object get(Object obj) {
        Object obj2 = this._map.get(obj);
        int size = LazyList.size(obj2);
        if (size == 0) {
            return null;
        }
        if (size != 1) {
            return LazyList.getList(obj2, true);
        }
        return LazyList.get(obj2, 0);
    }

    public Object put(K k, Object obj) {
        return this._map.put(k, LazyList.add((Object) null, obj));
    }

    public Object putValues(K k, List<? extends Object> list) {
        return this._map.put(k, list);
    }

    public Object putValues(K k, String... strArr) {
        Object obj = null;
        for (String add : strArr) {
            obj = LazyList.add(obj, add);
        }
        return this._map.put(k, obj);
    }

    public void add(K k, Object obj) {
        Object obj2 = this._map.get(k);
        Object add = LazyList.add(obj2, obj);
        if (obj2 != add) {
            this._map.put(k, add);
        }
    }

    public void addValues(K k, List<? extends Object> list) {
        Object obj = this._map.get(k);
        Object addCollection = LazyList.addCollection(obj, list);
        if (obj != addCollection) {
            this._map.put(k, addCollection);
        }
    }

    public void addValues(K k, String[] strArr) {
        Object obj = this._map.get(k);
        Object addCollection = LazyList.addCollection(obj, Arrays.asList(strArr));
        if (obj != addCollection) {
            this._map.put(k, addCollection);
        }
    }

    public boolean removeValue(K k, Object obj) {
        Object obj2 = this._map.get(k);
        int size = LazyList.size(obj2);
        if (size > 0) {
            obj2 = LazyList.remove(obj2, obj);
            if (obj2 == null) {
                this._map.remove(k);
            } else {
                this._map.put(k, obj2);
            }
        }
        return LazyList.size(obj2) != size;
    }

    public void putAll(Map<? extends K, ? extends Object> map) {
        if (map instanceof MultiMap) {
            for (Map.Entry next : map.entrySet()) {
                this._map.put(next.getKey(), LazyList.clone(next.getValue()));
            }
            return;
        }
        this._map.putAll(map);
    }

    public Map<K, String[]> toStringArrayMap() {
        C24411 r0 = new HashMap<K, String[]>((this._map.size() * 3) / 2) {
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append('{');
                for (Object next : keySet()) {
                    if (sb.length() > 1) {
                        sb.append(',');
                    }
                    sb.append(next);
                    sb.append('=');
                    sb.append(Arrays.asList((Object[]) get(next)));
                }
                sb.append('}');
                return sb.toString();
            }
        };
        for (Map.Entry next : this._map.entrySet()) {
            r0.put(next.getKey(), LazyList.toStringArray(next.getValue()));
        }
        return r0;
    }

    public String toString() {
        Object obj = this._cmap;
        if (obj == null) {
            obj = this._map;
        }
        return obj.toString();
    }

    public void clear() {
        this._map.clear();
    }

    public boolean containsKey(Object obj) {
        return this._map.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return this._map.containsValue(obj);
    }

    public Set<Map.Entry<K, Object>> entrySet() {
        return this._map.entrySet();
    }

    public boolean equals(Object obj) {
        return this._map.equals(obj);
    }

    public int hashCode() {
        return this._map.hashCode();
    }

    public boolean isEmpty() {
        return this._map.isEmpty();
    }

    public Set<K> keySet() {
        return this._map.keySet();
    }

    public Object remove(Object obj) {
        return this._map.remove(obj);
    }

    public int size() {
        return this._map.size();
    }

    public Collection<Object> values() {
        return this._map.values();
    }

    public Object putIfAbsent(K k, Object obj) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.putIfAbsent(k, obj);
        }
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object obj, Object obj2) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.remove(obj, obj2);
        }
        throw new UnsupportedOperationException();
    }

    public boolean replace(K k, Object obj, Object obj2) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.replace(k, obj, obj2);
        }
        throw new UnsupportedOperationException();
    }

    public Object replace(K k, Object obj) {
        ConcurrentMap<K, Object> concurrentMap = this._cmap;
        if (concurrentMap != null) {
            return concurrentMap.replace(k, obj);
        }
        throw new UnsupportedOperationException();
    }
}
