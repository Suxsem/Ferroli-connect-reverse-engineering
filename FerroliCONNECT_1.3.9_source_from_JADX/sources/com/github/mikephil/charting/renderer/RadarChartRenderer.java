package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RadarChartRenderer extends LineRadarRenderer {
    protected RadarChart mChart;
    protected Paint mHighlightCirclePaint;
    protected Paint mWebPaint = new Paint(1);

    public void initBuffers() {
    }

    public RadarChartRenderer(RadarChart radarChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = radarChart;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb(255, 187, 115));
        this.mWebPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightCirclePaint = new Paint(1);
    }

    public Paint getWebPaint() {
        return this.mWebPaint;
    }

    public void drawData(Canvas canvas) {
        RadarData radarData = (RadarData) this.mChart.getData();
        int i = 0;
        for (IRadarDataSet iRadarDataSet : radarData.getDataSets()) {
            if (iRadarDataSet.getEntryCount() > i) {
                i = iRadarDataSet.getEntryCount();
            }
        }
        for (IRadarDataSet iRadarDataSet2 : radarData.getDataSets()) {
            if (iRadarDataSet2.isVisible() && iRadarDataSet2.getEntryCount() > 0) {
                drawDataSet(canvas, iRadarDataSet2, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IRadarDataSet iRadarDataSet, int i) {
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        PointF centerOffsets = this.mChart.getCenterOffsets();
        Path path = new Path();
        boolean z = false;
        for (int i2 = 0; i2 < iRadarDataSet.getEntryCount(); i2++) {
            this.mRenderPaint.setColor(iRadarDataSet.getColor(i2));
            PointF position = Utils.getPosition(centerOffsets, (iRadarDataSet.getEntryForIndex(i2).getVal() - this.mChart.getYChartMin()) * factor * phaseY, (((float) i2) * sliceAngle * phaseX) + this.mChart.getRotationAngle());
            if (!Float.isNaN(position.x)) {
                if (!z) {
                    path.moveTo(position.x, position.y);
                    z = true;
                } else {
                    path.lineTo(position.x, position.y);
                }
            }
        }
        if (iRadarDataSet.getEntryCount() > i) {
            path.lineTo(centerOffsets.x, centerOffsets.y);
        }
        path.close();
        if (iRadarDataSet.isDrawFilledEnabled()) {
            Drawable fillDrawable = iRadarDataSet.getFillDrawable();
            if (fillDrawable != null) {
                drawFilledPath(canvas, path, fillDrawable);
            } else {
                drawFilledPath(canvas, path, iRadarDataSet.getFillColor(), iRadarDataSet.getFillAlpha());
            }
        }
        this.mRenderPaint.setStrokeWidth(iRadarDataSet.getLineWidth());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        if (!iRadarDataSet.isDrawFilledEnabled() || iRadarDataSet.getFillAlpha() < 255) {
            canvas.drawPath(path, this.mRenderPaint);
        }
    }

    public void drawValues(Canvas canvas) {
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        PointF centerOffsets = this.mChart.getCenterOffsets();
        float convertDpToPixel = Utils.convertDpToPixel(5.0f);
        int i = 0;
        while (i < ((RadarData) this.mChart.getData()).getDataSetCount()) {
            IRadarDataSet iRadarDataSet = (IRadarDataSet) ((RadarData) this.mChart.getData()).getDataSetByIndex(i);
            if (iRadarDataSet.isDrawValuesEnabled() && iRadarDataSet.getEntryCount() != 0) {
                applyValueTextStyle(iRadarDataSet);
                int i2 = 0;
                while (i2 < iRadarDataSet.getEntryCount()) {
                    Entry entryForIndex = iRadarDataSet.getEntryForIndex(i2);
                    PointF position = Utils.getPosition(centerOffsets, (entryForIndex.getVal() - this.mChart.getYChartMin()) * factor * phaseY, (((float) i2) * sliceAngle * phaseX) + this.mChart.getRotationAngle());
                    ValueFormatter valueFormatter = iRadarDataSet.getValueFormatter();
                    float val = entryForIndex.getVal();
                    float f = position.x;
                    float f2 = position.y - convertDpToPixel;
                    int valueTextColor = iRadarDataSet.getValueTextColor(i2);
                    int i3 = i2;
                    float f3 = f;
                    IRadarDataSet iRadarDataSet2 = iRadarDataSet;
                    float f4 = f2;
                    drawValue(canvas, valueFormatter, val, entryForIndex, i, f3, f4, valueTextColor);
                    i2 = i3 + 1;
                    i = i;
                    iRadarDataSet = iRadarDataSet2;
                }
            }
            i++;
        }
    }

    public void drawExtras(Canvas canvas) {
        drawWeb(canvas);
    }

    /* access modifiers changed from: protected */
    public void drawWeb(Canvas canvas) {
        float sliceAngle = this.mChart.getSliceAngle();
        float factor = this.mChart.getFactor();
        float rotationAngle = this.mChart.getRotationAngle();
        PointF centerOffsets = this.mChart.getCenterOffsets();
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidth());
        this.mWebPaint.setColor(this.mChart.getWebColor());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        int skipWebLineCount = this.mChart.getSkipWebLineCount() + 1;
        for (int i = 0; i < ((RadarData) this.mChart.getData()).getXValCount(); i += skipWebLineCount) {
            PointF position = Utils.getPosition(centerOffsets, this.mChart.getYRange() * factor, (((float) i) * sliceAngle) + rotationAngle);
            canvas.drawLine(centerOffsets.x, centerOffsets.y, position.x, position.y, this.mWebPaint);
        }
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidthInner());
        this.mWebPaint.setColor(this.mChart.getWebColorInner());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        int i2 = this.mChart.getYAxis().mEntryCount;
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = 0;
            while (i4 < ((RadarData) this.mChart.getData()).getXValCount()) {
                float yChartMin = (this.mChart.getYAxis().mEntries[i3] - this.mChart.getYChartMin()) * factor;
                PointF position2 = Utils.getPosition(centerOffsets, yChartMin, (((float) i4) * sliceAngle) + rotationAngle);
                i4++;
                PointF position3 = Utils.getPosition(centerOffsets, yChartMin, (((float) i4) * sliceAngle) + rotationAngle);
                canvas.drawLine(position2.x, position2.y, position3.x, position3.y, this.mWebPaint);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0045, code lost:
        r1 = r9[r7].getXIndex();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawHighlighted(android.graphics.Canvas r19, com.github.mikephil.charting.highlight.Highlight[] r20) {
        /*
            r18 = this;
            r8 = r18
            r9 = r20
            com.github.mikephil.charting.animation.ChartAnimator r0 = r8.mAnimator
            float r10 = r0.getPhaseX()
            com.github.mikephil.charting.animation.ChartAnimator r0 = r8.mAnimator
            float r11 = r0.getPhaseY()
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            float r12 = r0.getSliceAngle()
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            float r13 = r0.getFactor()
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            android.graphics.PointF r14 = r0.getCenterOffsets()
            r15 = 0
            r7 = 0
        L_0x0024:
            int r0 = r9.length
            if (r7 >= r0) goto L_0x00ee
            com.github.mikephil.charting.charts.RadarChart r0 = r8.mChart
            com.github.mikephil.charting.data.ChartData r0 = r0.getData()
            com.github.mikephil.charting.data.RadarData r0 = (com.github.mikephil.charting.data.RadarData) r0
            r1 = r9[r7]
            int r1 = r1.getDataSetIndex()
            com.github.mikephil.charting.interfaces.datasets.IDataSet r0 = r0.getDataSetByIndex(r1)
            com.github.mikephil.charting.interfaces.datasets.IRadarDataSet r0 = (com.github.mikephil.charting.interfaces.datasets.IRadarDataSet) r0
            if (r0 == 0) goto L_0x00e8
            boolean r1 = r0.isHighlightEnabled()
            if (r1 != 0) goto L_0x0045
            goto L_0x00e8
        L_0x0045:
            r1 = r9[r7]
            int r1 = r1.getXIndex()
            com.github.mikephil.charting.data.Entry r2 = r0.getEntryForXIndex(r1)
            if (r2 == 0) goto L_0x00e8
            int r3 = r2.getXIndex()
            if (r3 == r1) goto L_0x0059
            goto L_0x00e8
        L_0x0059:
            int r1 = r0.getEntryIndex(r2)
            float r2 = r2.getVal()
            com.github.mikephil.charting.charts.RadarChart r3 = r8.mChart
            float r3 = r3.getYChartMin()
            float r2 = r2 - r3
            boolean r3 = java.lang.Float.isNaN(r2)
            if (r3 == 0) goto L_0x0070
            goto L_0x00e8
        L_0x0070:
            float r2 = r2 * r13
            float r2 = r2 * r11
            float r1 = (float) r1
            float r1 = r1 * r12
            float r1 = r1 * r10
            com.github.mikephil.charting.charts.RadarChart r3 = r8.mChart
            float r3 = r3.getRotationAngle()
            float r1 = r1 + r3
            android.graphics.PointF r2 = com.github.mikephil.charting.utils.Utils.getPosition(r14, r2, r1)
            r1 = 2
            float[] r1 = new float[r1]
            float r3 = r2.x
            r1[r15] = r3
            float r3 = r2.y
            r4 = 1
            r1[r4] = r3
            r6 = r19
            r8.drawHighlightLines(r6, r1, r0)
            boolean r3 = r0.isDrawHighlightCircleEnabled()
            if (r3 == 0) goto L_0x00e8
            r3 = r1[r15]
            boolean r3 = java.lang.Float.isNaN(r3)
            if (r3 != 0) goto L_0x00e8
            r1 = r1[r4]
            boolean r1 = java.lang.Float.isNaN(r1)
            if (r1 != 0) goto L_0x00e8
            int r1 = r0.getHighlightCircleStrokeColor()
            r3 = 1122867(0x112233, float:1.573472E-39)
            if (r1 != r3) goto L_0x00b8
            int r1 = r0.getColor(r15)
        L_0x00b8:
            int r3 = r0.getHighlightCircleStrokeAlpha()
            r4 = 255(0xff, float:3.57E-43)
            if (r3 >= r4) goto L_0x00c8
            int r3 = r0.getHighlightCircleStrokeAlpha()
            int r1 = com.github.mikephil.charting.utils.ColorTemplate.getColorWithAlphaComponent(r1, r3)
        L_0x00c8:
            r16 = r1
            float r3 = r0.getHighlightCircleInnerRadius()
            float r4 = r0.getHighlightCircleOuterRadius()
            int r5 = r0.getHighlightCircleFillColor()
            float r17 = r0.getHighlightCircleStrokeWidth()
            r0 = r18
            r1 = r19
            r6 = r16
            r16 = r7
            r7 = r17
            r0.drawHighlightCircle(r1, r2, r3, r4, r5, r6, r7)
            goto L_0x00ea
        L_0x00e8:
            r16 = r7
        L_0x00ea:
            int r7 = r16 + 1
            goto L_0x0024
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.RadarChartRenderer.drawHighlighted(android.graphics.Canvas, com.github.mikephil.charting.highlight.Highlight[]):void");
    }

    public void drawHighlightCircle(Canvas canvas, PointF pointF, float f, float f2, int i, int i2, float f3) {
        canvas.save();
        float convertDpToPixel = Utils.convertDpToPixel(f2);
        float convertDpToPixel2 = Utils.convertDpToPixel(f);
        if (i != 1122867) {
            Path path = new Path();
            path.addCircle(pointF.x, pointF.y, convertDpToPixel, Path.Direction.CW);
            if (convertDpToPixel2 > 0.0f) {
                path.addCircle(pointF.x, pointF.y, convertDpToPixel2, Path.Direction.CCW);
            }
            this.mHighlightCirclePaint.setColor(i);
            this.mHighlightCirclePaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, this.mHighlightCirclePaint);
        }
        if (i2 != 1122867) {
            this.mHighlightCirclePaint.setColor(i2);
            this.mHighlightCirclePaint.setStyle(Paint.Style.STROKE);
            this.mHighlightCirclePaint.setStrokeWidth(Utils.convertDpToPixel(f3));
            canvas.drawCircle(pointF.x, pointF.y, convertDpToPixel, this.mHighlightCirclePaint);
        }
        canvas.restore();
    }
}
