package com.alibaba.sdk.android.ams.common.util;

import com.alibaba.sdk.android.ams.common.p010b.C0673c;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.alibaba.sdk.android.ams.common.util.c */
public class C0683c {
    /* renamed from: a */
    public static Map<String, String> m536a(Map<String, String> map) {
        HashMap hashMap = new HashMap();
        hashMap.putAll(map);
        hashMap.put("sign", C0673c.m489a().mo9525a((Map<String, String>) hashMap, "TMP_SEED_KEY"));
        return hashMap;
    }
}
