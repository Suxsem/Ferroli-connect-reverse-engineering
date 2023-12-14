package org.eclipse.jetty.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HostMap<TYPE> extends HashMap<String, TYPE> {
    public HostMap() {
        super(11);
    }

    public HostMap(int i) {
        super(i);
    }

    public TYPE put(String str, TYPE type) throws IllegalArgumentException {
        return super.put(str, type);
    }

    public TYPE get(Object obj) {
        return super.get(obj);
    }

    public Object getLazyMatches(String str) {
        int indexOf;
        if (str == null) {
            return LazyList.getList(super.entrySet());
        }
        String trim = str.trim();
        HashSet hashSet = new HashSet();
        do {
            hashSet.add(trim);
            indexOf = trim.indexOf(46);
            if (indexOf > 0) {
                trim = trim.substring(indexOf + 1);
                continue;
            }
        } while (indexOf > 0);
        Object obj = null;
        for (Map.Entry entry : super.entrySet()) {
            if (hashSet.contains(entry.getKey())) {
                obj = LazyList.add(obj, entry);
            }
        }
        return obj;
    }
}
