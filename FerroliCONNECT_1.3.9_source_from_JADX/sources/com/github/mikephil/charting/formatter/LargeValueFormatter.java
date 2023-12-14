package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class LargeValueFormatter implements ValueFormatter, YAxisValueFormatter {
    private static final int MAX_LENGTH = 4;
    private static String[] SUFFIX = {"", "k", "m", "b", "t"};
    private DecimalFormat mFormat;
    private String mText;

    public LargeValueFormatter() {
        this.mText = "";
        this.mFormat = new DecimalFormat("###E0");
    }

    public LargeValueFormatter(String str) {
        this();
        this.mText = str;
    }

    public String getFormattedValue(float f, Entry entry, int i, ViewPortHandler viewPortHandler) {
        return makePretty((double) f) + this.mText;
    }

    public String getFormattedValue(float f, YAxis yAxis) {
        return makePretty((double) f) + this.mText;
    }

    public void setAppendix(String str) {
        this.mText = str;
    }

    public void setSuffix(String[] strArr) {
        if (strArr.length == 5) {
            SUFFIX = strArr;
        }
    }

    private String makePretty(double d) {
        String format = this.mFormat.format(d);
        String replaceAll = format.replaceAll("E[0-9]", SUFFIX[Character.getNumericValue(format.charAt(format.length() - 1)) / 3]);
        while (true) {
            if (replaceAll.length() <= 4 && !replaceAll.matches("[0-9]+\\.[a-z]")) {
                return replaceAll;
            }
            replaceAll = replaceAll.substring(0, replaceAll.length() - 2) + replaceAll.substring(replaceAll.length() - 1);
        }
    }
}
