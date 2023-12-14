package com.szacs.ferroliconnect.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class InputUtil {
    public static boolean isUsername(CharSequence charSequence) {
        return Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{3,24}").matcher(charSequence).matches();
    }

    public static boolean isThermostatNameValid(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence) && charSequence.length() >= 5 && charSequence.length() <= 19) {
            return Pattern.compile("^[a-zA-Z][a-zA-Z0-9]*\\s?[a-zA-Z0-9]*").matcher(charSequence).matches();
        }
        return false;
    }

    public static boolean isNameValid(CharSequence charSequence) {
        return Pattern.compile("^[a-zA-Z][a-zA-Z0-9 ]{4,18}").matcher(charSequence).matches();
    }

    public static boolean isMobileNO(CharSequence charSequence) {
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(charSequence).matches();
    }

    public static boolean isEmail(CharSequence charSequence) {
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(charSequence).matches();
    }

    public static boolean isPasswordValid(CharSequence charSequence) {
        return Pattern.compile("^[a-zA-Z0-9]{6,40}$").matcher(charSequence).find();
    }
}
