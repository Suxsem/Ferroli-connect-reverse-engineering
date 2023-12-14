package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.p000v4.app.ActivityCompat;
import com.p107tb.appyunsdk.Constant;
import java.util.List;

public class GPSUtils {
    private static GPSUtils instance;
    private LocationManager locationManager;
    private Context mContext;

    private GPSUtils(Context context) {
        this.mContext = context;
    }

    public static GPSUtils getInstance(Context context) {
        if (instance == null) {
            instance = new GPSUtils(context);
        }
        return instance;
    }

    public Location getLocation() {
        this.locationManager = (LocationManager) this.mContext.getSystemService(Constant.WEATHER_LOCATION);
        List<String> providers = this.locationManager.getProviders(true);
        String str = "gps";
        if (!providers.contains(str)) {
            if (providers.contains("network")) {
                str = "network";
            } else {
                Intent intent = new Intent();
                intent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                this.mContext.startActivity(intent);
                return null;
            }
        }
        if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            return this.locationManager.getLastKnownLocation(str);
        }
        return null;
    }
}
