package com.alibaba.sdk.android.ams.common.p011c;

import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.push.p024e.p025a.C0820c;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.alibaba.sdk.android.ams.common.c.d */
public class C0677d {

    /* renamed from: a */
    private static final AmsLogger f809a = AmsLogger.getLogger("ServiceLoader");

    /* renamed from: b */
    private static final String f810b = C0820c.class.getName();

    /* renamed from: a */
    public static <T> List<T> m494a(Class<T> cls, ClassLoader classLoader) {
        String name = cls.getName();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(cls.cast(classLoader.loadClass(f810b).newInstance()));
            return arrayList;
        } catch (Exception e) {
            throw new IllegalStateException("Fail to load ams-spi-services for " + name, e);
        }
    }
}
