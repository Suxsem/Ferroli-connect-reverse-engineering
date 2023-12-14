package com.szacs.ferroliconnect.util;

import java.math.BigDecimal;

public class Fahrenheit {
    private float fahF = 0.0f;
    private boolean significantFigures;

    public Fahrenheit(String str) {
        this.significantFigures = isOneDigitDecimalOrInteger(str);
        if (this.significantFigures) {
            this.fahF = Float.parseFloat(str);
        }
    }

    public Fahrenheit(String str, String str2) {
        this.significantFigures = isNumeric(str2) && isNumeric(str);
        if (this.significantFigures) {
            this.fahF = Float.parseFloat(str + "." + str2);
        }
    }

    public float getCen() {
        return new BigDecimal((double) ((this.fahF - 32.0f) / 1.8f)).setScale(1, 4).floatValue();
    }

    public String[] getCenArray() {
        float cen = getCen();
        return (cen + "").split("\\.");
    }

    public String getCenString(boolean z) {
        float cen = getCen();
        if (z) {
            return ((int) cen) + "";
        }
        return cen + "";
    }

    public float getFahFloat() {
        return this.fahF;
    }

    private static boolean isOneDigitDecimalOrInteger(String str) {
        if (!str.equals("")) {
            String[] split = str.split("\\.");
            if (split.length == 2) {
                if (split[1].length() != 1 || !isNumeric(split[0]) || !isNumeric(split[1])) {
                    return false;
                }
                return true;
            } else if (isNumeric(split[0])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNumeric(String str) {
        int length = str.length();
        do {
            length--;
            if (length < 0) {
                return true;
            }
        } while (Character.isDigit(str.charAt(length)));
        return false;
    }
}
