package com.github.mikephil.charting.formatter;

import java.text.DecimalFormat;

public class StackedValueFormatter implements ValueFormatter {
    private String mAppendix;
    private boolean mDrawWholeStack;
    private DecimalFormat mFormat;

    public StackedValueFormatter(boolean z, String str, int i) {
        this.mDrawWholeStack = z;
        this.mAppendix = str;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
        r4 = (com.github.mikephil.charting.data.BarEntry) r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getFormattedValue(float r3, com.github.mikephil.charting.data.Entry r4, int r5, com.github.mikephil.charting.utils.ViewPortHandler r6) {
        /*
            r2 = this;
            boolean r5 = r2.mDrawWholeStack
            if (r5 != 0) goto L_0x0039
            boolean r5 = r4 instanceof com.github.mikephil.charting.data.BarEntry
            if (r5 == 0) goto L_0x0039
            com.github.mikephil.charting.data.BarEntry r4 = (com.github.mikephil.charting.data.BarEntry) r4
            float[] r5 = r4.getVals()
            if (r5 == 0) goto L_0x0039
            int r6 = r5.length
            int r6 = r6 + -1
            r5 = r5[r6]
            int r3 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r3 != 0) goto L_0x0036
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.text.DecimalFormat r5 = r2.mFormat
            float r4 = r4.getVal()
            double r0 = (double) r4
            java.lang.String r4 = r5.format(r0)
            r3.append(r4)
            java.lang.String r4 = r2.mAppendix
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            return r3
        L_0x0036:
            java.lang.String r3 = ""
            return r3
        L_0x0039:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.text.DecimalFormat r5 = r2.mFormat
            double r0 = (double) r3
            java.lang.String r3 = r5.format(r0)
            r4.append(r3)
            java.lang.String r3 = r2.mAppendix
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.formatter.StackedValueFormatter.getFormattedValue(float, com.github.mikephil.charting.data.Entry, int, com.github.mikephil.charting.utils.ViewPortHandler):java.lang.String");
    }
}
