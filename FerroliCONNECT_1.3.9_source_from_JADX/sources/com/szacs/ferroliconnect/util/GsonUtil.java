package com.szacs.ferroliconnect.util;

import com.google.gson.Gson;

public class GsonUtil {
    public static <T> T fromJson(String str, Class<T> cls) {
        return new Gson().fromJson(str, cls);
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
