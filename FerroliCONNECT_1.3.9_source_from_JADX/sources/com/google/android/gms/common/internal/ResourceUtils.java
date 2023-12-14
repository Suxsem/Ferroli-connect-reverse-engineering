package com.google.android.gms.common.internal;

import android.net.Uri;
import com.alibaba.sdk.android.push.notification.CustomNotificationBuilder;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
public final class ResourceUtils {
    private static final Uri zzet = new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath(CustomNotificationBuilder.NOTIFICATION_ICON_RES_TYPE).build();

    private ResourceUtils() {
    }
}
