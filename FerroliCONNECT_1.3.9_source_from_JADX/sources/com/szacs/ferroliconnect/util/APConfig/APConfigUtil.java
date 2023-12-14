package com.szacs.ferroliconnect.util.APConfig;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import com.taobao.accs.utl.UtilityImpl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APConfigUtil {
    public static byte[] getWifiBroadcastIP(Context context) {
        DhcpInfo dhcpInfo = ((WifiManager) context.getApplicationContext().getSystemService(UtilityImpl.NET_TYPE_WIFI)).getDhcpInfo();
        int i = dhcpInfo.ipAddress | (dhcpInfo.netmask ^ -1);
        return new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)};
    }

    public static String getNum(String str) {
        Matcher matcher;
        if (str == null || (matcher = Pattern.compile("[^0-9]").matcher(str)) == null) {
            return null;
        }
        return matcher.replaceAll("").trim();
    }
}
