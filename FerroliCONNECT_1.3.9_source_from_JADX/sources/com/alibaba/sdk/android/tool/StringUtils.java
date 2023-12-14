package com.alibaba.sdk.android.tool;

import java.util.Map;

public class StringUtils {
    public static String convertMaoToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        boolean z = true;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (!(str == null || str2 == null)) {
                if (z) {
                    sb.append(str + "=" + str2);
                    z = false;
                } else {
                    sb.append(",");
                    sb.append(str + "=" + str2);
                }
            }
        }
        return sb.toString();
    }

    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Integer) {
            return "" + ((Integer) obj);
        } else if (obj instanceof Long) {
            return "" + ((Long) obj);
        } else if (obj instanceof Double) {
            return "" + ((Double) obj);
        } else if (obj instanceof Float) {
            return "" + ((Float) obj);
        } else if (obj instanceof Short) {
            return "" + ((Short) obj);
        } else if (!(obj instanceof Byte)) {
            return obj instanceof Boolean ? ((Boolean) obj).toString() : obj instanceof Character ? ((Character) obj).toString() : obj.toString();
        } else {
            return "" + ((Byte) obj);
        }
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (!(charSequence == null || (length = charSequence.length()) == 0)) {
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(charSequence.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }
}
