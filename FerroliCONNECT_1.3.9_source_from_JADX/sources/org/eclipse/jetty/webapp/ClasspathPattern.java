package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClasspathPattern {
    private final List<Entry> _entries = new ArrayList();
    private final List<String> _patterns = new ArrayList();

    private static class Entry {
        public String classpath;
        public boolean partial;
        public boolean result;

        private Entry() {
            this.classpath = null;
            this.result = false;
            this.partial = false;
        }
    }

    public ClasspathPattern() {
    }

    public ClasspathPattern(String[] strArr) {
        setPatterns(strArr);
    }

    public ClasspathPattern(String str) {
        setPattern(str);
    }

    private void setPatterns(String[] strArr) {
        this._patterns.clear();
        this._entries.clear();
        addPatterns(strArr);
    }

    private void addPatterns(String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                Entry createEntry = createEntry(str);
                if (createEntry != null) {
                    this._patterns.add(str);
                    this._entries.add(createEntry);
                }
            }
        }
    }

    private Entry createEntry(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() <= 0) {
            return null;
        }
        Entry entry = new Entry();
        entry.result = !trim.startsWith("-");
        entry.partial = trim.endsWith(".");
        if (!entry.result) {
            trim = trim.substring(1).trim();
        }
        entry.classpath = trim;
        return entry;
    }

    public void setPattern(String str) {
        this._patterns.clear();
        this._entries.clear();
        addPattern(str);
    }

    public void addPattern(String str) {
        ArrayList arrayList = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":,");
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        addPatterns((String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public String[] getPatterns() {
        List<String> list = this._patterns;
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<String> list2 = this._patterns;
        return (String[]) list2.toArray(new String[list2.size()]);
    }

    public boolean match(String str) {
        if (this._entries == null) {
            return false;
        }
        String replace = str.replace('/', '.');
        int i = 0;
        while (i < replace.length() && replace.charAt(i) == '.') {
            i++;
        }
        int indexOf = replace.indexOf("$");
        if (indexOf == -1) {
            indexOf = replace.length();
        }
        for (Entry next : this._entries) {
            if (next != null) {
                if (!next.partial) {
                    int i2 = indexOf - i;
                    if (i2 == next.classpath.length() && replace.regionMatches(i, next.classpath, 0, i2)) {
                        return next.result;
                    }
                } else if (replace.regionMatches(i, next.classpath, 0, next.classpath.length())) {
                    return next.result;
                }
            }
        }
        return false;
    }
}
