package com.p092ta.utdid2.device;

import android.content.Context;
import com.p092ta.p093a.C1964a;

/* renamed from: com.ta.utdid2.device.UTDevice */
public class UTDevice {
    @Deprecated
    public static String getUtdid(Context context) {
        if (context == null) {
            return "ffffffffffffffffffffffff";
        }
        C1964a.mo18112a().mo18114a(context);
        return C1992a.m3386a().getUtdid(context);
    }
}
