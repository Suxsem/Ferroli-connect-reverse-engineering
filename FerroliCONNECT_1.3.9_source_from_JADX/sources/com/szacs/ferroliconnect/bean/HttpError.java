package com.szacs.ferroliconnect.bean;

import android.content.Context;
import com.szacs.ferroliconnect.C1683R;

public class HttpError {
    public static String getMessage(Context context, int i) {
        if (i == -1) {
            return context.getResources().getString(C1683R.string.Network_failure);
        }
        return context.getResources().getString(context.getResources().getIdentifier("Error_code_" + i, "string", context.getPackageName()));
    }
}
