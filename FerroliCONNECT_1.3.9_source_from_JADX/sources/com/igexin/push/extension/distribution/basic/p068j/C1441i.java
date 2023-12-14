package com.igexin.push.extension.distribution.basic.p068j;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/* renamed from: com.igexin.push.extension.distribution.basic.j.i */
public class C1441i {
    /* renamed from: a */
    public static Bitmap m2527a(String str) {
        if (str != null) {
            try {
                Bitmap decodeFile = BitmapFactory.decodeFile(str);
                if (decodeFile != null) {
                    return decodeFile;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
