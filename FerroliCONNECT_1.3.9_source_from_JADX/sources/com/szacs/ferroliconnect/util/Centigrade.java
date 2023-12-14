package com.szacs.ferroliconnect.util;

import java.math.BigDecimal;

public class Centigrade {
    private float cenF = 0.0f;
    private boolean significantFigures;

    public Centigrade(String str) {
        this.significantFigures = isOneDigitDecimalOrInteger(str);
        if (this.significantFigures) {
            this.cenF = Float.parseFloat(str);
        }
    }

    public Centigrade(String str, String str2) {
        this.significantFigures = isNumeric(str2) && isNumeric(str);
        if (this.significantFigures) {
            this.cenF = Float.parseFloat(str + "." + str2);
        }
    }

    public Centigrade(String str, boolean z) {
        if (z) {
            this.significantFigures = isOneDigitDecimalOrInteger(str);
            if (this.significantFigures) {
                this.cenF = Float.parseFloat(str);
                return;
            }
            return;
        }
        this.significantFigures = isOneDigitDecimalOrInteger(str);
        if (this.significantFigures) {
            this.cenF = new Fahrenheit(str).getCen();
        }
    }

    public void setCenF(String str, boolean z) {
        if (z) {
            this.significantFigures = isOneDigitDecimalOrInteger(str);
            if (this.significantFigures) {
                this.cenF = Float.parseFloat(str);
                return;
            }
            return;
        }
        this.significantFigures = isOneDigitDecimalOrInteger(str);
        if (this.significantFigures) {
            this.cenF = new Fahrenheit(str).getCen();
        }
    }

    public void setCenF(int i, boolean z) {
        if (z) {
            this.significantFigures = isOneDigitDecimalOrInteger(i + "");
            if (this.significantFigures) {
                this.cenF = Float.parseFloat(i + "");
                return;
            }
            return;
        }
        this.significantFigures = isOneDigitDecimalOrInteger(i + "");
        if (this.significantFigures) {
            this.cenF = new Fahrenheit(i + "").getCen();
        }
    }

    public float getFah() {
        return new BigDecimal((double) ((this.cenF * 1.8f) + 32.0f)).setScale(1, 4).floatValue();
    }

    public String[] getFahArray() {
        float fah = getFah();
        return (fah + "").split("\\.");
    }

    public String getFahString(boolean z) {
        float fah = getFah();
        if (z) {
            return ((int) fah) + "";
        }
        return fah + "";
    }

    public float getCenFloat() {
        return this.cenF;
    }

    public String getCenString(boolean z) {
        if (z) {
            return ((int) this.cenF) + "";
        }
        return this.cenF + "";
    }

    public String[] getCenArray() {
        return (this.cenF + "").split("\\.");
    }

    public boolean isSignificant() {
        return this.significantFigures;
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
