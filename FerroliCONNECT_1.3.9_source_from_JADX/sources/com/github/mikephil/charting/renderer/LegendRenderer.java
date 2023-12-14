package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class LegendRenderer extends Renderer {
    protected Legend mLegend;
    protected Paint mLegendFormPaint;
    protected Paint mLegendLabelPaint = new Paint(1);

    public LegendRenderer(ViewPortHandler viewPortHandler, Legend legend) {
        super(viewPortHandler);
        this.mLegend = legend;
        this.mLegendLabelPaint.setTextSize(Utils.convertDpToPixel(9.0f));
        this.mLegendLabelPaint.setTextAlign(Paint.Align.LEFT);
        this.mLegendFormPaint = new Paint(1);
        this.mLegendFormPaint.setStyle(Paint.Style.FILL);
        this.mLegendFormPaint.setStrokeWidth(3.0f);
    }

    public Paint getLabelPaint() {
        return this.mLegendLabelPaint;
    }

    public Paint getFormPaint() {
        return this.mLegendFormPaint;
    }

    /* JADX WARNING: type inference failed for: r12v0, types: [com.github.mikephil.charting.data.ChartData, com.github.mikephil.charting.data.ChartData<?>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void computeLegend(com.github.mikephil.charting.data.ChartData<?> r12) {
        /*
            r11 = this;
            com.github.mikephil.charting.components.Legend r0 = r11.mLegend
            boolean r0 = r0.isLegendCustom()
            if (r0 != 0) goto L_0x014a
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 0
            r3 = 0
        L_0x0014:
            int r4 = r12.getDataSetCount()
            if (r3 >= r4) goto L_0x0112
            com.github.mikephil.charting.interfaces.datasets.IDataSet r4 = r12.getDataSetByIndex(r3)
            java.util.List r5 = r4.getColors()
            int r6 = r4.getEntryCount()
            boolean r7 = r4 instanceof com.github.mikephil.charting.interfaces.datasets.IBarDataSet
            r8 = 1122868(0x112234, float:1.573473E-39)
            if (r7 == 0) goto L_0x006f
            r7 = r4
            com.github.mikephil.charting.interfaces.datasets.IBarDataSet r7 = (com.github.mikephil.charting.interfaces.datasets.IBarDataSet) r7
            boolean r9 = r7.isStacked()
            if (r9 == 0) goto L_0x006f
            java.lang.String[] r4 = r7.getStackLabels()
            r6 = 0
        L_0x003b:
            int r9 = r5.size()
            if (r6 >= r9) goto L_0x0059
            int r9 = r7.getStackSize()
            if (r6 >= r9) goto L_0x0059
            int r9 = r4.length
            int r9 = r6 % r9
            r9 = r4[r9]
            r0.add(r9)
            java.lang.Object r9 = r5.get(r6)
            r1.add(r9)
            int r6 = r6 + 1
            goto L_0x003b
        L_0x0059:
            java.lang.String r4 = r7.getLabel()
            if (r4 == 0) goto L_0x010e
            java.lang.Integer r4 = java.lang.Integer.valueOf(r8)
            r1.add(r4)
            java.lang.String r4 = r7.getLabel()
            r0.add(r4)
            goto L_0x010e
        L_0x006f:
            boolean r7 = r4 instanceof com.github.mikephil.charting.interfaces.datasets.IPieDataSet
            if (r7 == 0) goto L_0x00ae
            java.util.List r7 = r12.getXVals()
            com.github.mikephil.charting.interfaces.datasets.IPieDataSet r4 = (com.github.mikephil.charting.interfaces.datasets.IPieDataSet) r4
            r9 = 0
        L_0x007a:
            int r10 = r5.size()
            if (r9 >= r10) goto L_0x0099
            if (r9 >= r6) goto L_0x0099
            int r10 = r7.size()
            if (r9 >= r10) goto L_0x0099
            java.lang.Object r10 = r7.get(r9)
            r0.add(r10)
            java.lang.Object r10 = r5.get(r9)
            r1.add(r10)
            int r9 = r9 + 1
            goto L_0x007a
        L_0x0099:
            java.lang.String r5 = r4.getLabel()
            if (r5 == 0) goto L_0x010e
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r1.add(r5)
            java.lang.String r4 = r4.getLabel()
            r0.add(r4)
            goto L_0x010e
        L_0x00ae:
            boolean r7 = r4 instanceof com.github.mikephil.charting.interfaces.datasets.ICandleDataSet
            r8 = 0
            if (r7 == 0) goto L_0x00e0
            r7 = r4
            com.github.mikephil.charting.interfaces.datasets.ICandleDataSet r7 = (com.github.mikephil.charting.interfaces.datasets.ICandleDataSet) r7
            int r9 = r7.getDecreasingColor()
            r10 = 1122867(0x112233, float:1.573472E-39)
            if (r9 == r10) goto L_0x00e0
            int r5 = r7.getDecreasingColor()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r1.add(r5)
            int r5 = r7.getIncreasingColor()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r1.add(r5)
            r0.add(r8)
            java.lang.String r4 = r4.getLabel()
            r0.add(r4)
            goto L_0x010e
        L_0x00e0:
            r4 = 0
        L_0x00e1:
            int r7 = r5.size()
            if (r4 >= r7) goto L_0x010e
            if (r4 >= r6) goto L_0x010e
            int r7 = r5.size()
            int r7 = r7 + -1
            if (r4 >= r7) goto L_0x00f9
            int r7 = r6 + -1
            if (r4 >= r7) goto L_0x00f9
            r0.add(r8)
            goto L_0x0104
        L_0x00f9:
            com.github.mikephil.charting.interfaces.datasets.IDataSet r7 = r12.getDataSetByIndex(r3)
            java.lang.String r7 = r7.getLabel()
            r0.add(r7)
        L_0x0104:
            java.lang.Object r7 = r5.get(r4)
            r1.add(r7)
            int r4 = r4 + 1
            goto L_0x00e1
        L_0x010e:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x0112:
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            int[] r12 = r12.getExtraColors()
            if (r12 == 0) goto L_0x0140
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            java.lang.String[] r12 = r12.getExtraLabels()
            if (r12 == 0) goto L_0x0140
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            int[] r12 = r12.getExtraColors()
            int r3 = r12.length
        L_0x0129:
            if (r2 >= r3) goto L_0x0137
            r4 = r12[r2]
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r1.add(r4)
            int r2 = r2 + 1
            goto L_0x0129
        L_0x0137:
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            java.lang.String[] r12 = r12.getExtraLabels()
            java.util.Collections.addAll(r0, r12)
        L_0x0140:
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            r12.setComputedColors(r1)
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            r12.setComputedLabels(r0)
        L_0x014a:
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            android.graphics.Typeface r12 = r12.getTypeface()
            if (r12 == 0) goto L_0x0157
            android.graphics.Paint r0 = r11.mLegendLabelPaint
            r0.setTypeface(r12)
        L_0x0157:
            android.graphics.Paint r12 = r11.mLegendLabelPaint
            com.github.mikephil.charting.components.Legend r0 = r11.mLegend
            float r0 = r0.getTextSize()
            r12.setTextSize(r0)
            android.graphics.Paint r12 = r11.mLegendLabelPaint
            com.github.mikephil.charting.components.Legend r0 = r11.mLegend
            int r0 = r0.getTextColor()
            r12.setColor(r0)
            com.github.mikephil.charting.components.Legend r12 = r11.mLegend
            android.graphics.Paint r0 = r11.mLegendLabelPaint
            com.github.mikephil.charting.utils.ViewPortHandler r1 = r11.mViewPortHandler
            r12.calculateDimensions(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LegendRenderer.computeLegend(com.github.mikephil.charting.data.ChartData):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x025d  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0168  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void renderLegend(android.graphics.Canvas r36) {
        /*
            r35 = this;
            r6 = r35
            r7 = r36
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            boolean r0 = r0.isEnabled()
            if (r0 != 0) goto L_0x000d
            return
        L_0x000d:
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            android.graphics.Typeface r0 = r0.getTypeface()
            if (r0 == 0) goto L_0x001a
            android.graphics.Paint r1 = r6.mLegendLabelPaint
            r1.setTypeface(r0)
        L_0x001a:
            android.graphics.Paint r0 = r6.mLegendLabelPaint
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.getTextSize()
            r0.setTextSize(r1)
            android.graphics.Paint r0 = r6.mLegendLabelPaint
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            int r1 = r1.getTextColor()
            r0.setColor(r1)
            android.graphics.Paint r0 = r6.mLegendLabelPaint
            float r8 = com.github.mikephil.charting.utils.Utils.getLineHeight(r0)
            android.graphics.Paint r0 = r6.mLegendLabelPaint
            float r0 = com.github.mikephil.charting.utils.Utils.getLineSpacing(r0)
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.getYEntrySpace()
            float r9 = r0 + r1
            android.graphics.Paint r0 = r6.mLegendLabelPaint
            java.lang.String r1 = "ABC"
            int r0 = com.github.mikephil.charting.utils.Utils.calcTextHeight(r0, r1)
            float r0 = (float) r0
            r10 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r10
            float r11 = r8 - r0
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            java.lang.String[] r12 = r0.getLabels()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            int[] r13 = r0.getColors()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            float r14 = r0.getFormToTextSpace()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            float r15 = r0.getXEntrySpace()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            com.github.mikephil.charting.components.Legend$LegendOrientation r0 = r0.getOrientation()
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r5 = r1.getHorizontalAlignment()
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r1 = r1.getVerticalAlignment()
            com.github.mikephil.charting.components.Legend r2 = r6.mLegend
            com.github.mikephil.charting.components.Legend$LegendDirection r4 = r2.getDirection()
            com.github.mikephil.charting.components.Legend r2 = r6.mLegend
            float r16 = r2.getFormSize()
            com.github.mikephil.charting.components.Legend r2 = r6.mLegend
            float r3 = r2.getStackSpace()
            com.github.mikephil.charting.components.Legend r2 = r6.mLegend
            float r2 = r2.getYOffset()
            com.github.mikephil.charting.components.Legend r10 = r6.mLegend
            float r10 = r10.getXOffset()
            int[] r18 = com.github.mikephil.charting.renderer.LegendRenderer.C09981.f1486x2787f53e
            int r19 = r5.ordinal()
            r20 = r3
            r3 = r18[r19]
            r18 = r15
            r15 = 2
            r21 = 0
            r22 = r9
            r9 = 1
            if (r3 == r9) goto L_0x0140
            if (r3 == r15) goto L_0x011f
            r15 = 3
            if (r3 == r15) goto L_0x00ba
            r28 = r8
            r27 = r14
            r10 = 0
            goto L_0x015a
        L_0x00ba:
            com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r3) goto L_0x00c8
            com.github.mikephil.charting.utils.ViewPortHandler r3 = r6.mViewPortHandler
            float r3 = r3.getChartWidth()
            r15 = 1073741824(0x40000000, float:2.0)
            float r3 = r3 / r15
            goto L_0x00d8
        L_0x00c8:
            r15 = 1073741824(0x40000000, float:2.0)
            com.github.mikephil.charting.utils.ViewPortHandler r3 = r6.mViewPortHandler
            float r3 = r3.contentLeft()
            com.github.mikephil.charting.utils.ViewPortHandler r9 = r6.mViewPortHandler
            float r9 = r9.contentWidth()
            float r9 = r9 / r15
            float r3 = r3 + r9
        L_0x00d8:
            com.github.mikephil.charting.components.Legend$LegendDirection r9 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r4 != r9) goto L_0x00de
            r9 = r10
            goto L_0x00df
        L_0x00de:
            float r9 = -r10
        L_0x00df:
            float r3 = r3 + r9
            com.github.mikephil.charting.components.Legend$LegendOrientation r9 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r9) goto L_0x011a
            r9 = r8
            double r7 = (double) r3
            com.github.mikephil.charting.components.Legend$LegendDirection r3 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            r25 = 4611686018427387904(0x4000000000000000, double:2.0)
            if (r4 != r3) goto L_0x0101
            com.github.mikephil.charting.components.Legend r3 = r6.mLegend
            float r3 = r3.mNeededWidth
            float r3 = -r3
            r27 = r14
            double r14 = (double) r3
            java.lang.Double.isNaN(r14)
            double r14 = r14 / r25
            r28 = r9
            double r9 = (double) r10
            java.lang.Double.isNaN(r9)
            double r14 = r14 + r9
            goto L_0x0114
        L_0x0101:
            r28 = r9
            r27 = r14
            com.github.mikephil.charting.components.Legend r3 = r6.mLegend
            float r3 = r3.mNeededWidth
            double r14 = (double) r3
            java.lang.Double.isNaN(r14)
            double r14 = r14 / r25
            double r9 = (double) r10
            java.lang.Double.isNaN(r9)
            double r14 = r14 - r9
        L_0x0114:
            java.lang.Double.isNaN(r7)
            double r7 = r7 + r14
            float r3 = (float) r7
            goto L_0x013e
        L_0x011a:
            r28 = r8
            r27 = r14
            goto L_0x013e
        L_0x011f:
            r28 = r8
            r27 = r14
            com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r3) goto L_0x012e
            com.github.mikephil.charting.utils.ViewPortHandler r3 = r6.mViewPortHandler
            float r3 = r3.getChartWidth()
            goto L_0x0134
        L_0x012e:
            com.github.mikephil.charting.utils.ViewPortHandler r3 = r6.mViewPortHandler
            float r3 = r3.contentRight()
        L_0x0134:
            float r3 = r3 - r10
            com.github.mikephil.charting.components.Legend$LegendDirection r7 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r4 != r7) goto L_0x013e
            com.github.mikephil.charting.components.Legend r7 = r6.mLegend
            float r7 = r7.mNeededWidth
            float r3 = r3 - r7
        L_0x013e:
            r10 = r3
            goto L_0x015a
        L_0x0140:
            r28 = r8
            r27 = r14
            com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
            if (r0 != r3) goto L_0x0149
            goto L_0x0150
        L_0x0149:
            com.github.mikephil.charting.utils.ViewPortHandler r3 = r6.mViewPortHandler
            float r3 = r3.contentLeft()
            float r10 = r10 + r3
        L_0x0150:
            com.github.mikephil.charting.components.Legend$LegendDirection r3 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r4 != r3) goto L_0x015a
            com.github.mikephil.charting.components.Legend r3 = r6.mLegend
            float r3 = r3.mNeededWidth
            float r3 = r3 + r10
            goto L_0x013e
        L_0x015a:
            int[] r3 = com.github.mikephil.charting.renderer.LegendRenderer.C09981.f1487x9c9dbef
            int r0 = r0.ordinal()
            r0 = r3[r0]
            r7 = 1122868(0x112234, float:1.573473E-39)
            r3 = 1
            if (r0 == r3) goto L_0x025d
            r8 = 2
            if (r0 == r8) goto L_0x016d
            goto L_0x039a
        L_0x016d:
            int[] r0 = com.github.mikephil.charting.renderer.LegendRenderer.C09981.f1488xc926f1ec
            int r1 = r1.ordinal()
            r0 = r0[r1]
            if (r0 == r3) goto L_0x01ad
            if (r0 == r8) goto L_0x0195
            r1 = 3
            if (r0 == r1) goto L_0x017e
            r0 = 0
            goto L_0x01ba
        L_0x017e:
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.getChartHeight()
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            com.github.mikephil.charting.components.Legend r2 = r6.mLegend
            float r2 = r2.mNeededHeight
            float r2 = r2 / r1
            float r0 = r0 - r2
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.getYOffset()
            float r0 = r0 + r1
            goto L_0x01ba
        L_0x0195:
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r0 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r5 != r0) goto L_0x01a0
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.getChartHeight()
            goto L_0x01a6
        L_0x01a0:
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.contentBottom()
        L_0x01a6:
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.mNeededHeight
            float r1 = r1 + r2
            float r0 = r0 - r1
            goto L_0x01ba
        L_0x01ad:
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r0 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r5 != r0) goto L_0x01b3
            r0 = 0
            goto L_0x01b9
        L_0x01b3:
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.contentTop()
        L_0x01b9:
            float r0 = r0 + r2
        L_0x01ba:
            r15 = r0
            r8 = 0
            r14 = 0
            r17 = 0
        L_0x01bf:
            int r0 = r12.length
            if (r8 >= r0) goto L_0x039a
            r0 = r13[r8]
            if (r0 == r7) goto L_0x01c8
            r0 = 1
            goto L_0x01c9
        L_0x01c8:
            r0 = 0
        L_0x01c9:
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r0)
            boolean r0 = r18.booleanValue()
            if (r0 == 0) goto L_0x01fb
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r4 != r0) goto L_0x01da
            float r0 = r10 + r14
            goto L_0x01de
        L_0x01da:
            float r0 = r16 - r14
            float r0 = r10 - r0
        L_0x01de:
            r19 = r0
            float r3 = r15 + r11
            com.github.mikephil.charting.components.Legend r5 = r6.mLegend
            r0 = r35
            r1 = r36
            r2 = r19
            r9 = r20
            r7 = r4
            r4 = r8
            r0.drawForm(r1, r2, r3, r4, r5)
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r7 != r0) goto L_0x01f8
            float r0 = r19 + r16
            goto L_0x01ff
        L_0x01f8:
            r0 = r19
            goto L_0x01ff
        L_0x01fb:
            r7 = r4
            r9 = r20
            r0 = r10
        L_0x01ff:
            r1 = r12[r8]
            if (r1 == 0) goto L_0x0248
            boolean r1 = r18.booleanValue()
            if (r1 == 0) goto L_0x0218
            if (r17 != 0) goto L_0x0218
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r7 != r1) goto L_0x0213
            r4 = r27
            r14 = r4
            goto L_0x0216
        L_0x0213:
            r4 = r27
            float r14 = -r4
        L_0x0216:
            float r0 = r0 + r14
            goto L_0x021d
        L_0x0218:
            r4 = r27
            if (r17 == 0) goto L_0x021d
            r0 = r10
        L_0x021d:
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r1) goto L_0x022b
            android.graphics.Paint r1 = r6.mLegendLabelPaint
            r2 = r12[r8]
            int r1 = com.github.mikephil.charting.utils.Utils.calcTextWidth(r1, r2)
            float r1 = (float) r1
            float r0 = r0 - r1
        L_0x022b:
            if (r17 != 0) goto L_0x0237
            float r1 = r15 + r28
            r2 = r12[r8]
            r3 = r36
            r6.drawLabel(r3, r0, r1, r2)
            goto L_0x0243
        L_0x0237:
            r3 = r36
            float r1 = r28 + r22
            float r15 = r15 + r1
            float r1 = r15 + r28
            r2 = r12[r8]
            r6.drawLabel(r3, r0, r1, r2)
        L_0x0243:
            float r0 = r28 + r22
            float r15 = r15 + r0
            r14 = 0
            goto L_0x0251
        L_0x0248:
            r3 = r36
            r4 = r27
            float r0 = r16 + r9
            float r14 = r14 + r0
            r17 = 1
        L_0x0251:
            int r8 = r8 + 1
            r27 = r4
            r4 = r7
            r20 = r9
            r7 = 1122868(0x112234, float:1.573473E-39)
            goto L_0x01bf
        L_0x025d:
            r3 = r36
            r7 = r4
            r9 = r20
            r4 = r27
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            com.github.mikephil.charting.utils.FSize[] r8 = r0.getCalculatedLineSizes()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            com.github.mikephil.charting.utils.FSize[] r14 = r0.getCalculatedLabelSizes()
            com.github.mikephil.charting.components.Legend r0 = r6.mLegend
            java.lang.Boolean[] r15 = r0.getCalculatedLabelBreakPoints()
            int[] r0 = com.github.mikephil.charting.renderer.LegendRenderer.C09981.f1488xc926f1ec
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 1
            if (r0 == r1) goto L_0x02a6
            r1 = 2
            if (r0 == r1) goto L_0x0299
            r1 = 3
            if (r0 == r1) goto L_0x0289
            r2 = 0
            goto L_0x02a6
        L_0x0289:
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.getChartHeight()
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.mNeededHeight
            float r0 = r0 - r1
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            float r2 = r2 + r0
            goto L_0x02a6
        L_0x0299:
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r6.mViewPortHandler
            float r0 = r0.getChartHeight()
            float r0 = r0 - r2
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            float r1 = r1.mNeededHeight
            float r2 = r0 - r1
        L_0x02a6:
            int r1 = r12.length
            r0 = r2
            r19 = r10
            r2 = 0
            r3 = 0
        L_0x02ac:
            if (r2 >= r1) goto L_0x039a
            r21 = r1
            int r1 = r15.length
            if (r2 >= r1) goto L_0x02c3
            r1 = r15[r2]
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x02c3
            float r1 = r28 + r22
            float r0 = r0 + r1
            r23 = r0
            r19 = r10
            goto L_0x02c5
        L_0x02c3:
            r23 = r0
        L_0x02c5:
            int r0 = (r19 > r10 ? 1 : (r19 == r10 ? 0 : -1))
            if (r0 != 0) goto L_0x02e7
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r0 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER
            if (r5 != r0) goto L_0x02e7
            int r0 = r8.length
            if (r3 >= r0) goto L_0x02e7
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r0) goto L_0x02d9
            r0 = r8[r3]
            float r0 = r0.width
            goto L_0x02de
        L_0x02d9:
            r0 = r8[r3]
            float r0 = r0.width
            float r0 = -r0
        L_0x02de:
            r17 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r17
            float r19 = r19 + r0
            int r3 = r3 + 1
            goto L_0x02e9
        L_0x02e7:
            r17 = 1073741824(0x40000000, float:2.0)
        L_0x02e9:
            r26 = r3
            r0 = r13[r2]
            r3 = 1122868(0x112234, float:1.573473E-39)
            if (r0 == r3) goto L_0x02f5
            r20 = 1
            goto L_0x02f7
        L_0x02f5:
            r20 = 0
        L_0x02f7:
            r0 = r12[r2]
            if (r0 != 0) goto L_0x02fe
            r27 = 1
            goto L_0x0300
        L_0x02fe:
            r27 = 0
        L_0x0300:
            if (r20 == 0) goto L_0x0334
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r0) goto L_0x0308
            float r19 = r19 - r16
        L_0x0308:
            float r29 = r23 + r11
            com.github.mikephil.charting.components.Legend r1 = r6.mLegend
            r0 = r35
            r24 = r1
            r30 = 1
            r1 = r36
            r31 = r2
            r2 = r19
            r32 = r8
            r33 = 1122868(0x112234, float:1.573473E-39)
            r8 = r36
            r3 = r29
            r29 = r10
            r10 = r4
            r4 = r31
            r34 = r5
            r5 = r24
            r0.drawForm(r1, r2, r3, r4, r5)
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r7 != r0) goto L_0x0344
            float r19 = r19 + r16
            goto L_0x0344
        L_0x0334:
            r31 = r2
            r34 = r5
            r32 = r8
            r29 = r10
            r30 = 1
            r33 = 1122868(0x112234, float:1.573473E-39)
            r8 = r36
            r10 = r4
        L_0x0344:
            if (r27 != 0) goto L_0x037c
            if (r20 == 0) goto L_0x0351
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r0) goto L_0x034e
            float r0 = -r10
            goto L_0x034f
        L_0x034e:
            r0 = r10
        L_0x034f:
            float r19 = r19 + r0
        L_0x0351:
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r0) goto L_0x035b
            r0 = r14[r31]
            float r0 = r0.width
            float r19 = r19 - r0
        L_0x035b:
            r0 = r19
            float r1 = r23 + r28
            r2 = r12[r31]
            r6.drawLabel(r8, r0, r1, r2)
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.LEFT_TO_RIGHT
            if (r7 != r1) goto L_0x036d
            r1 = r14[r31]
            float r1 = r1.width
            float r0 = r0 + r1
        L_0x036d:
            com.github.mikephil.charting.components.Legend$LegendDirection r1 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r1) goto L_0x0375
            r1 = r18
            float r2 = -r1
            goto L_0x0378
        L_0x0375:
            r1 = r18
            r2 = r1
        L_0x0378:
            float r0 = r0 + r2
            r19 = r0
            goto L_0x0387
        L_0x037c:
            r1 = r18
            com.github.mikephil.charting.components.Legend$LegendDirection r0 = com.github.mikephil.charting.components.Legend.LegendDirection.RIGHT_TO_LEFT
            if (r7 != r0) goto L_0x0384
            float r3 = -r9
            goto L_0x0385
        L_0x0384:
            r3 = r9
        L_0x0385:
            float r19 = r19 + r3
        L_0x0387:
            int r2 = r31 + 1
            r18 = r1
            r4 = r10
            r1 = r21
            r0 = r23
            r3 = r26
            r10 = r29
            r8 = r32
            r5 = r34
            goto L_0x02ac
        L_0x039a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LegendRenderer.renderLegend(android.graphics.Canvas):void");
    }

    /* access modifiers changed from: protected */
    public void drawForm(Canvas canvas, float f, float f2, int i, Legend legend) {
        if (legend.getColors()[i] != 1122868) {
            this.mLegendFormPaint.setColor(legend.getColors()[i]);
            float formSize = legend.getFormSize();
            float f3 = formSize / 2.0f;
            int i2 = C09981.f1485xfbec3b85[legend.getForm().ordinal()];
            if (i2 == 1) {
                canvas.drawCircle(f + f3, f2, f3, this.mLegendFormPaint);
            } else if (i2 == 2) {
                canvas.drawRect(f, f2 - f3, f + formSize, f2 + f3, this.mLegendFormPaint);
            } else if (i2 == 3) {
                canvas.drawLine(f, f2, f + formSize, f2, this.mLegendFormPaint);
            }
        }
    }

    /* renamed from: com.github.mikephil.charting.renderer.LegendRenderer$1 */
    static /* synthetic */ class C09981 {

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendForm */
        static final /* synthetic */ int[] f1485xfbec3b85 = new int[Legend.LegendForm.values().length];

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment */
        static final /* synthetic */ int[] f1486x2787f53e = new int[Legend.LegendHorizontalAlignment.values().length];

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation */
        static final /* synthetic */ int[] f1487x9c9dbef = new int[Legend.LegendOrientation.values().length];

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment */
        static final /* synthetic */ int[] f1488xc926f1ec = new int[Legend.LegendVerticalAlignment.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(27:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|16|17|19|20|21|22|23|24|25|27|28|29|30|31|32|34) */
        /* JADX WARNING: Can't wrap try/catch for region: R(28:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|16|17|19|20|21|22|23|24|25|27|28|29|30|31|32|34) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x005a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0064 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0081 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x008b */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendForm[] r0 = com.github.mikephil.charting.components.Legend.LegendForm.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1485xfbec3b85 = r0
                r0 = 1
                int[] r1 = f1485xfbec3b85     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.components.Legend$LegendForm r2 = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f1485xfbec3b85     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.components.Legend$LegendForm r3 = com.github.mikephil.charting.components.Legend.LegendForm.SQUARE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = f1485xfbec3b85     // Catch:{ NoSuchFieldError -> 0x002a }
                com.github.mikephil.charting.components.Legend$LegendForm r4 = com.github.mikephil.charting.components.Legend.LegendForm.LINE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                f1487x9c9dbef = r3
                int[] r3 = f1487x9c9dbef     // Catch:{ NoSuchFieldError -> 0x003d }
                com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x003d }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r3 = f1487x9c9dbef     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.github.mikephil.charting.components.Legend$LegendOrientation r4 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment[] r3 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                f1488xc926f1ec = r3
                int[] r3 = f1488xc926f1ec     // Catch:{ NoSuchFieldError -> 0x005a }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP     // Catch:{ NoSuchFieldError -> 0x005a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x005a }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x005a }
            L_0x005a:
                int[] r3 = f1488xc926f1ec     // Catch:{ NoSuchFieldError -> 0x0064 }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                int[] r3 = f1488xc926f1ec     // Catch:{ NoSuchFieldError -> 0x006e }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x006e }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x006e }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x006e }
            L_0x006e:
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment[] r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                f1486x2787f53e = r3
                int[] r3 = f1486x2787f53e     // Catch:{ NoSuchFieldError -> 0x0081 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r4 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT     // Catch:{ NoSuchFieldError -> 0x0081 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0081 }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x0081 }
            L_0x0081:
                int[] r0 = f1486x2787f53e     // Catch:{ NoSuchFieldError -> 0x008b }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT     // Catch:{ NoSuchFieldError -> 0x008b }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x008b }
                r0[r3] = r1     // Catch:{ NoSuchFieldError -> 0x008b }
            L_0x008b:
                int[] r0 = f1486x2787f53e     // Catch:{ NoSuchFieldError -> 0x0095 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r1 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x0095 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0095 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0095 }
            L_0x0095:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LegendRenderer.C09981.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public void drawLabel(Canvas canvas, float f, float f2, String str) {
        canvas.drawText(str, f, f2, this.mLegendLabelPaint);
    }
}
