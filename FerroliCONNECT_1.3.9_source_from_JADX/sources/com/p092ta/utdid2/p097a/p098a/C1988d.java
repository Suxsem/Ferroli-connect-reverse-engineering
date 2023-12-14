package com.p092ta.utdid2.p097a.p098a;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/* renamed from: com.ta.utdid2.a.a.d */
public class C1988d {

    /* renamed from: a */
    private static final Pattern f3182a = Pattern.compile("([\t\r\n])+");

    /* renamed from: b */
    public static boolean m3381b(String str) {
        return str == null || str.length() <= 0;
    }

    /* renamed from: a */
    public static int m3379a(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i = 0;
        for (char c : str.toCharArray()) {
            i = (i * 31) + c;
        }
        return i;
    }

    /* renamed from: a */
    public static Map<String, String> m3380a(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        TreeMap treeMap = new TreeMap(new Comparator<String>() {
            public int compare(String str, String str2) {
                return str.compareTo(str2);
            }
        });
        treeMap.putAll(map);
        return treeMap;
    }

    /* renamed from: e */
    public static String m3382e(String str) {
        return (str == null || "".equals(str)) ? str : f3182a.matcher(str).replaceAll("");
    }
}
