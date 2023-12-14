package org.eclipse.jetty.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {
    private transient Set<E> _keys = this._map.keySet();
    private final Map<E, Boolean> _map = new ConcurrentHashMap();

    public boolean add(E e) {
        return this._map.put(e, Boolean.TRUE) == null;
    }

    public void clear() {
        this._map.clear();
    }

    public boolean contains(Object obj) {
        return this._map.containsKey(obj);
    }

    public boolean containsAll(Collection<?> collection) {
        return this._keys.containsAll(collection);
    }

    public boolean equals(Object obj) {
        return obj == this || this._keys.equals(obj);
    }

    public int hashCode() {
        return this._keys.hashCode();
    }

    public boolean isEmpty() {
        return this._map.isEmpty();
    }

    public Iterator<E> iterator() {
        return this._keys.iterator();
    }

    public boolean remove(Object obj) {
        return this._map.remove(obj) != null;
    }

    public boolean removeAll(Collection<?> collection) {
        return this._keys.removeAll(collection);
    }

    public boolean retainAll(Collection<?> collection) {
        return this._keys.retainAll(collection);
    }

    public int size() {
        return this._map.size();
    }

    public Object[] toArray() {
        return this._keys.toArray();
    }

    public <T> T[] toArray(T[] tArr) {
        return this._keys.toArray(tArr);
    }

    public String toString() {
        return this._keys.toString();
    }
}
