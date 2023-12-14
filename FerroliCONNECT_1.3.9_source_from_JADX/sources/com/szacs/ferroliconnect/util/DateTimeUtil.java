package com.szacs.ferroliconnect.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String getSystemDateTime(String str) {
        Date date = new Date();
        System.out.println(date);
        System.out.println(new SimpleDateFormat(str).format(date));
        return new SimpleDateFormat(str).format(date);
    }
}
