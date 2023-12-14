package com.szacs.ferroliconnect.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSHA1Util {
    private static String TIANQI_API_SECRET_KEY = "YOUR API KEY";
    private static String TIANQI_API_USER_ID = "YOUR USER ID";
    private static String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private static String generateSignature(String str, String str2) throws SignatureException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("UTF-8"), "HmacSHA1");
            Mac instance = Mac.getInstance("HmacSHA1");
            instance.init(secretKeySpec);
            return encodeBase64(instance.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }

    private static String encodeBase64(byte[] bArr) throws Exception {
        Method method = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64").getMethod("encode", new Class[]{byte[].class});
        method.setAccessible(true);
        return (String) method.invoke((Object) null, new Object[]{bArr});
    }

    public static String generateGetDiaryWeatherURL(String str, String str2, String str3, String str4, String str5) throws SignatureException, UnsupportedEncodingException {
        String str6 = "ts=" + String.valueOf(new Date().getTime()) + "&ttl=30&uid=" + TIANQI_API_USER_ID;
        return TIANQI_DAILY_WEATHER_URL + "?" + str6 + "&sig=" + URLEncoder.encode(generateSignature(str6, TIANQI_API_SECRET_KEY), "UTF-8") + "&location=" + str + "&language=" + str2 + "&unit=" + str3 + "&start=" + str4 + "&days=" + str5;
    }
}
