package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.p000v4.view.ViewCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;

public class PieChartRenderer extends DataRenderer {
    protected Canvas mBitmapCanvas;
    private RectF mCenterTextLastBounds = new RectF();
    private CharSequence mCenterTextLastValue;
    private StaticLayout mCenterTextLayout;
    private TextPaint mCenterTextPaint;
    protected PieChart mChart;
    protected WeakReference<Bitmap> mDrawBitmap;
    private Path mHoleCirclePath = new Path();
    protected Paint mHolePaint;
    private RectF mInnerRectBuffer = new RectF();
    private Path mPathBuffer = new Path();
    private RectF[] mRectBuffer = {new RectF(), new RectF(), new RectF()};
    protected Paint mTransparentCirclePaint;
    protected Paint mValueLinePaint;

    public void initBuffers() {
    }

    public PieChartRenderer(PieChart pieChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = pieChart;
        this.mHolePaint = new Paint(1);
        this.mHolePaint.setColor(-1);
        this.mHolePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint = new Paint(1);
        this.mTransparentCirclePaint.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
        this.mTransparentCirclePaint.setAlpha(105);
        this.mCenterTextPaint = new TextPaint(1);
        this.mCenterTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mCenterTextPaint.setTextSize(Utils.convertDpToPixel(12.0f));
        this.mValuePaint.setTextSize(Utils.convertDpToPixel(13.0f));
        this.mValuePaint.setColor(-1);
        this.mValuePaint.setTextAlign(Paint.Align.CENTER);
        this.mValueLinePaint = new Paint(1);
        this.mValueLinePaint.setStyle(Paint.Style.STROKE);
    }

    public Paint getPaintHole() {
        return this.mHolePaint;
    }

    public Paint getPaintTransparentCircle() {
        return this.mTransparentCirclePaint;
    }

    public TextPaint getPaintCenterText() {
        return this.mCenterTextPaint;
    }

    public void drawData(Canvas canvas) {
        int chartWidth = (int) this.mViewPortHandler.getChartWidth();
        int chartHeight = (int) this.mViewPortHandler.getChartHeight();
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (!(weakReference != null && ((Bitmap) weakReference.get()).getWidth() == chartWidth && ((Bitmap) this.mDrawBitmap.get()).getHeight() == chartHeight)) {
            if (chartWidth > 0 && chartHeight > 0) {
                this.mDrawBitmap = new WeakReference<>(Bitmap.createBitmap(chartWidth, chartHeight, Bitmap.Config.ARGB_4444));
                this.mBitmapCanvas = new Canvas((Bitmap) this.mDrawBitmap.get());
            } else {
                return;
            }
        }
        ((Bitmap) this.mDrawBitmap.get()).eraseColor(0);
        for (IPieDataSet iPieDataSet : ((PieData) this.mChart.getData()).getDataSets()) {
            if (iPieDataSet.isVisible() && iPieDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iPieDataSet);
            }
        }
    }

    /* access modifiers changed from: protected */
    public float calculateMinimumRadiusForSpacedSlice(PointF pointF, float f, float f2, float f3, float f4, float f5, float f6) {
        PointF pointF2 = pointF;
        double d = (double) ((f5 + f6) * 0.017453292f);
        float cos = pointF2.x + (((float) Math.cos(d)) * f);
        float sin = pointF2.y + (((float) Math.sin(d)) * f);
        double d2 = (double) ((f5 + (f6 / 2.0f)) * 0.017453292f);
        float cos2 = pointF2.x + (((float) Math.cos(d2)) * f);
        float sin2 = pointF2.y + (((float) Math.sin(d2)) * f);
        double d3 = (double) f2;
        Double.isNaN(d3);
        double sqrt = (double) (f - ((float) ((Math.sqrt(Math.pow((double) (cos - f3), 2.0d) + Math.pow((double) (sin - f4), 2.0d)) / 2.0d) * Math.tan(((180.0d - d3) / 2.0d) * 0.017453292519943295d))));
        double sqrt2 = Math.sqrt(Math.pow((double) (cos2 - ((cos + f3) / 2.0f)), 2.0d) + Math.pow((double) (sin2 - ((sin + f4) / 2.0f)), 2.0d));
        Double.isNaN(sqrt);
        return (float) (sqrt - sqrt2);
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IPieDataSet iPieDataSet) {
        float f;
        float f2;
        float f3;
        float f4;
        int i;
        float[] fArr;
        RectF rectF;
        float f5;
        PointF pointF;
        float f6;
        int i2;
        int i3;
        int i4;
        int i5;
        float f7;
        float f8;
        float f9;
        int i6;
        PointF pointF2;
        IPieDataSet iPieDataSet2 = iPieDataSet;
        float rotationAngle = this.mChart.getRotationAngle();
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        RectF circleBox = this.mChart.getCircleBox();
        int entryCount = iPieDataSet.getEntryCount();
        float[] drawAngles = this.mChart.getDrawAngles();
        PointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean z = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float holeRadius = z ? (this.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        int i7 = 0;
        for (int i8 = 0; i8 < entryCount; i8++) {
            if (((double) Math.abs(iPieDataSet2.getEntryForIndex(i8).getVal())) > 1.0E-6d) {
                i7++;
            }
        }
        if (i7 <= 1) {
            f = 0.0f;
        } else {
            f = iPieDataSet.getSliceSpace();
        }
        int i9 = 0;
        float f10 = 0.0f;
        while (i9 < entryCount) {
            float f11 = drawAngles[i9];
            Entry entryForIndex = iPieDataSet2.getEntryForIndex(i9);
            float f12 = radius;
            if (((double) Math.abs(entryForIndex.getVal())) <= 1.0E-6d || this.mChart.needsHighlight(entryForIndex.getXIndex(), ((PieData) this.mChart.getData()).getIndexOfDataSet(iPieDataSet2))) {
                f5 = f12;
                f2 = rotationAngle;
                f4 = phaseX;
                f3 = phaseY;
                rectF = circleBox;
                i = entryCount;
                fArr = drawAngles;
                i3 = i9;
                i2 = i7;
                f6 = holeRadius;
                pointF = centerCircleBox;
            } else {
                boolean z2 = f > 0.0f && f11 <= 180.0f;
                this.mRenderPaint.setColor(iPieDataSet2.getColor(i9));
                float f13 = i7 == 1 ? 0.0f : f / (f12 * 0.017453292f);
                float f14 = ((f10 + (f13 / 2.0f)) * phaseY) + rotationAngle;
                float f15 = (f11 - f13) * phaseY;
                if (f15 < 0.0f) {
                    f15 = 0.0f;
                }
                this.mPathBuffer.reset();
                float f16 = f15 % 360.0f;
                if (f16 == 0.0f) {
                    i5 = i9;
                    i4 = i7;
                    i = entryCount;
                    f7 = f12;
                    this.mPathBuffer.addCircle(centerCircleBox.x, centerCircleBox.y, f7, Path.Direction.CW);
                    f2 = rotationAngle;
                    f4 = phaseX;
                    f3 = phaseY;
                    f9 = 0.0f;
                    f8 = 0.0f;
                } else {
                    i5 = i9;
                    i4 = i7;
                    i = entryCount;
                    f7 = f12;
                    f2 = rotationAngle;
                    double d = (double) (f14 * 0.017453292f);
                    f4 = phaseX;
                    f3 = phaseY;
                    float cos = centerCircleBox.x + (((float) Math.cos(d)) * f7);
                    float sin = centerCircleBox.y + (((float) Math.sin(d)) * f7);
                    this.mPathBuffer.moveTo(cos, sin);
                    this.mPathBuffer.arcTo(circleBox, f14, f15);
                    f9 = cos;
                    f8 = sin;
                }
                this.mInnerRectBuffer.set(centerCircleBox.x - holeRadius, centerCircleBox.y - holeRadius, centerCircleBox.x + holeRadius, centerCircleBox.y + holeRadius);
                if (!z || (holeRadius <= 0.0f && !z2)) {
                    float f17 = f15;
                    f5 = f7;
                    i3 = i5;
                    i2 = i4;
                    pointF = centerCircleBox;
                    rectF = circleBox;
                    fArr = drawAngles;
                    f6 = holeRadius;
                    if (f16 != 0.0f) {
                        if (z2) {
                            float calculateMinimumRadiusForSpacedSlice = calculateMinimumRadiusForSpacedSlice(pointF, f5, f11 * f3, f9, f8, f14, f17);
                            double d2 = (double) ((f14 + (f17 / 2.0f)) * 0.017453292f);
                            this.mPathBuffer.lineTo(pointF.x + (((float) Math.cos(d2)) * calculateMinimumRadiusForSpacedSlice), pointF.y + (calculateMinimumRadiusForSpacedSlice * ((float) Math.sin(d2))));
                        } else {
                            this.mPathBuffer.lineTo(pointF.x, pointF.y);
                        }
                    }
                } else {
                    if (z2) {
                        i3 = i5;
                        float f18 = f7;
                        i2 = i4;
                        rectF = circleBox;
                        f6 = holeRadius;
                        float f19 = f9;
                        float f20 = f7;
                        i6 = 1;
                        float f21 = f8;
                        f5 = f20;
                        pointF2 = centerCircleBox;
                        float calculateMinimumRadiusForSpacedSlice2 = calculateMinimumRadiusForSpacedSlice(centerCircleBox, f18, f11 * f3, f19, f21, f14, f15);
                        if (calculateMinimumRadiusForSpacedSlice2 < 0.0f) {
                            calculateMinimumRadiusForSpacedSlice2 = -calculateMinimumRadiusForSpacedSlice2;
                        }
                        holeRadius = Math.max(f6, calculateMinimumRadiusForSpacedSlice2);
                    } else {
                        pointF2 = centerCircleBox;
                        f5 = f7;
                        i3 = i5;
                        i2 = i4;
                        i6 = 1;
                        rectF = circleBox;
                        f6 = holeRadius;
                    }
                    float f22 = (i2 == i6 || holeRadius == 0.0f) ? 0.0f : f / (holeRadius * 0.017453292f);
                    float f23 = f2 + ((f10 + (f22 / 2.0f)) * f3);
                    float f24 = (f11 - f22) * f3;
                    if (f24 < 0.0f) {
                        f24 = 0.0f;
                    }
                    float f25 = f23 + f24;
                    if (f16 == 0.0f) {
                        this.mPathBuffer.addCircle(pointF2.x, pointF2.y, holeRadius, Path.Direction.CCW);
                        fArr = drawAngles;
                    } else {
                        double d3 = (double) (f25 * 0.017453292f);
                        fArr = drawAngles;
                        this.mPathBuffer.lineTo(pointF2.x + (((float) Math.cos(d3)) * holeRadius), pointF2.y + (holeRadius * ((float) Math.sin(d3))));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f25, -f24);
                    }
                    pointF = pointF2;
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            }
            f10 += f11 * f4;
            i9 = i3 + 1;
            rotationAngle = f2;
            iPieDataSet2 = iPieDataSet;
            i7 = i2;
            holeRadius = f6;
            centerCircleBox = pointF;
            radius = f5;
            circleBox = rectF;
            drawAngles = fArr;
            entryCount = i;
            phaseX = f4;
            phaseY = f3;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x014e  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x018a  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0192  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01df  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01f2  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x020b  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x027b  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x02a3  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0311  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0333  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawValues(android.graphics.Canvas r50) {
        /*
            r49 = this;
            r9 = r49
            r10 = r50
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            android.graphics.PointF r11 = r0.getCenterCircleBox()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            float r12 = r0.getRadius()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            float r13 = r0.getRotationAngle()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            float[] r14 = r0.getDrawAngles()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            float[] r15 = r0.getAbsoluteAngles()
            com.github.mikephil.charting.animation.ChartAnimator r0 = r9.mAnimator
            float r16 = r0.getPhaseX()
            com.github.mikephil.charting.animation.ChartAnimator r0 = r9.mAnimator
            float r17 = r0.getPhaseY()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            float r0 = r0.getHoleRadius()
            r18 = 1120403456(0x42c80000, float:100.0)
            float r19 = r0 / r18
            r0 = 1092616192(0x41200000, float:10.0)
            float r0 = r12 / r0
            r1 = 1080452710(0x40666666, float:3.6)
            float r0 = r0 * r1
            com.github.mikephil.charting.charts.PieChart r1 = r9.mChart
            boolean r1 = r1.isDrawHoleEnabled()
            r20 = 1073741824(0x40000000, float:2.0)
            if (r1 == 0) goto L_0x0051
            float r0 = r12 * r19
            float r0 = r12 - r0
            float r0 = r0 / r20
        L_0x0051:
            float r21 = r12 - r0
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            com.github.mikephil.charting.data.ChartData r0 = r0.getData()
            r22 = r0
            com.github.mikephil.charting.data.PieData r22 = (com.github.mikephil.charting.data.PieData) r22
            java.util.List r8 = r22.getDataSets()
            float r23 = r22.getYValueSum()
            com.github.mikephil.charting.charts.PieChart r0 = r9.mChart
            boolean r24 = r0.isDrawSliceTextEnabled()
            r50.save()
            r25 = 0
            r0 = 0
            r7 = 0
        L_0x0072:
            int r1 = r8.size()
            if (r7 >= r1) goto L_0x0381
            java.lang.Object r1 = r8.get(r7)
            r6 = r1
            com.github.mikephil.charting.interfaces.datasets.IPieDataSet r6 = (com.github.mikephil.charting.interfaces.datasets.IPieDataSet) r6
            boolean r26 = r6.isDrawValuesEnabled()
            if (r26 != 0) goto L_0x0095
            if (r24 != 0) goto L_0x0095
            r34 = r7
            r33 = r8
            r40 = r11
            r38 = r12
            r35 = r13
            r36 = r14
            goto L_0x0373
        L_0x0095:
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r5 = r6.getXValuePosition()
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r4 = r6.getYValuePosition()
            r9.applyValueTextStyle(r6)
            android.graphics.Paint r1 = r9.mValuePaint
            java.lang.String r2 = "Q"
            int r1 = com.github.mikephil.charting.utils.Utils.calcTextHeight(r1, r2)
            float r1 = (float) r1
            r2 = 1082130432(0x40800000, float:4.0)
            float r2 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r2)
            float r27 = r1 + r2
            com.github.mikephil.charting.formatter.ValueFormatter r28 = r6.getValueFormatter()
            int r3 = r6.getEntryCount()
            android.graphics.Paint r1 = r9.mValueLinePaint
            int r2 = r6.getValueLineColor()
            r1.setColor(r2)
            android.graphics.Paint r1 = r9.mValueLinePaint
            float r2 = r6.getValueLineWidth()
            float r2 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r2)
            r1.setStrokeWidth(r2)
            r29 = r0
            r2 = 0
        L_0x00d2:
            if (r2 >= r3) goto L_0x0365
            com.github.mikephil.charting.data.Entry r30 = r6.getEntryForIndex(r2)
            if (r29 != 0) goto L_0x00dc
            r0 = 0
            goto L_0x00e2
        L_0x00dc:
            int r0 = r29 + -1
            r0 = r15[r0]
            float r0 = r0 * r16
        L_0x00e2:
            r1 = r14[r29]
            float r31 = r6.getSliceSpace()
            r32 = 1016003125(0x3c8efa35, float:0.017453292)
            float r33 = r21 * r32
            float r31 = r31 / r33
            float r31 = r31 / r20
            float r1 = r1 - r31
            float r1 = r1 / r20
            float r0 = r0 + r1
            float r0 = r0 * r17
            float r0 = r0 + r13
            com.github.mikephil.charting.charts.PieChart r1 = r9.mChart
            boolean r1 = r1.isUsePercentValuesEnabled()
            if (r1 == 0) goto L_0x010a
            float r1 = r30.getVal()
            float r1 = r1 / r23
            float r1 = r1 * r18
            goto L_0x010e
        L_0x010a:
            float r1 = r30.getVal()
        L_0x010e:
            r31 = r1
            float r1 = r0 * r32
            r32 = r2
            double r1 = (double) r1
            r34 = r7
            r33 = r8
            double r7 = java.lang.Math.cos(r1)
            float r8 = (float) r7
            r35 = r13
            r36 = r14
            double r13 = java.lang.Math.sin(r1)
            float r13 = (float) r13
            if (r24 == 0) goto L_0x012f
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r14 = com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE
            if (r5 != r14) goto L_0x012f
            r14 = 1
            goto L_0x0130
        L_0x012f:
            r14 = 0
        L_0x0130:
            if (r26 == 0) goto L_0x0138
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r7 = com.github.mikephil.charting.data.PieDataSet.ValuePosition.OUTSIDE_SLICE
            if (r4 != r7) goto L_0x0138
            r7 = 1
            goto L_0x0139
        L_0x0138:
            r7 = 0
        L_0x0139:
            if (r24 == 0) goto L_0x0144
            r38 = r3
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r3 = com.github.mikephil.charting.data.PieDataSet.ValuePosition.INSIDE_SLICE
            if (r5 != r3) goto L_0x0146
            r39 = 1
            goto L_0x0148
        L_0x0144:
            r38 = r3
        L_0x0146:
            r39 = 0
        L_0x0148:
            if (r26 == 0) goto L_0x0151
            com.github.mikephil.charting.data.PieDataSet$ValuePosition r3 = com.github.mikephil.charting.data.PieDataSet.ValuePosition.INSIDE_SLICE
            if (r4 != r3) goto L_0x0151
            r37 = 1
            goto L_0x0153
        L_0x0151:
            r37 = 0
        L_0x0153:
            if (r14 != 0) goto L_0x0169
            if (r7 == 0) goto L_0x0158
            goto L_0x0169
        L_0x0158:
            r42 = r4
            r41 = r5
            r14 = r6
            r40 = r8
            r48 = r38
            r38 = r12
            r12 = r32
            r32 = r48
            goto L_0x02bd
        L_0x0169:
            float r3 = r6.getValueLinePart1Length()
            float r40 = r6.getValueLinePart2Length()
            float r41 = r6.getValueLinePart1OffsetPercentage()
            float r41 = r41 / r18
            r42 = r4
            com.github.mikephil.charting.charts.PieChart r4 = r9.mChart
            boolean r4 = r4.isDrawHoleEnabled()
            if (r4 == 0) goto L_0x018a
            float r4 = r12 * r19
            float r43 = r12 - r4
            float r43 = r43 * r41
            float r43 = r43 + r4
            goto L_0x018c
        L_0x018a:
            float r43 = r12 * r41
        L_0x018c:
            boolean r4 = r6.isValueLineVariableLength()
            if (r4 == 0) goto L_0x01a0
            float r40 = r40 * r21
            double r1 = java.lang.Math.sin(r1)
            double r1 = java.lang.Math.abs(r1)
            float r1 = (float) r1
            float r40 = r40 * r1
            goto L_0x01a2
        L_0x01a0:
            float r40 = r40 * r21
        L_0x01a2:
            float r1 = r43 * r8
            float r2 = r11.x
            float r1 = r1 + r2
            float r43 = r43 * r13
            float r2 = r11.y
            float r2 = r43 + r2
            r4 = 1065353216(0x3f800000, float:1.0)
            float r3 = r3 + r4
            float r3 = r3 * r21
            float r4 = r3 * r8
            r41 = r5
            float r5 = r11.x
            float r43 = r4 + r5
            float r3 = r3 * r13
            float r4 = r11.y
            float r44 = r3 + r4
            double r3 = (double) r0
            r45 = 4645040803167600640(0x4076800000000000, double:360.0)
            java.lang.Double.isNaN(r3)
            double r3 = r3 % r45
            r45 = 4636033603912859648(0x4056800000000000, double:90.0)
            r0 = 1084227584(0x40a00000, float:5.0)
            int r5 = (r3 > r45 ? 1 : (r3 == r45 ? 0 : -1))
            if (r5 < 0) goto L_0x01f2
            r45 = 4643457506423603200(0x4070e00000000000, double:270.0)
            int r5 = (r3 > r45 ? 1 : (r3 == r45 ? 0 : -1))
            if (r5 > 0) goto L_0x01f2
            float r3 = r43 - r40
            android.graphics.Paint r4 = r9.mValuePaint
            android.graphics.Paint$Align r5 = android.graphics.Paint.Align.RIGHT
            r4.setTextAlign(r5)
            float r0 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r0)
            float r0 = r3 - r0
            r5 = r0
            r40 = r3
            goto L_0x0202
        L_0x01f2:
            float r40 = r43 + r40
            android.graphics.Paint r3 = r9.mValuePaint
            android.graphics.Paint$Align r4 = android.graphics.Paint.Align.LEFT
            r3.setTextAlign(r4)
            float r0 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r0)
            float r0 = r40 + r0
            r5 = r0
        L_0x0202:
            int r0 = r6.getValueLineColor()
            r3 = 1122867(0x112233, float:1.573472E-39)
            if (r0 == r3) goto L_0x022f
            android.graphics.Paint r4 = r9.mValueLinePaint
            r0 = r50
            r3 = r32
            r32 = r38
            r38 = r12
            r12 = r3
            r3 = r43
            r45 = r4
            r4 = r44
            r46 = r5
            r5 = r45
            r0.drawLine(r1, r2, r3, r4, r5)
            android.graphics.Paint r5 = r9.mValueLinePaint
            r1 = r43
            r2 = r44
            r3 = r40
            r0.drawLine(r1, r2, r3, r4, r5)
            goto L_0x0239
        L_0x022f:
            r46 = r5
            r48 = r38
            r38 = r12
            r12 = r32
            r32 = r48
        L_0x0239:
            if (r14 == 0) goto L_0x0273
            if (r7 == 0) goto L_0x0273
            r5 = 0
            int r14 = r6.getValueTextColor(r12)
            r0 = r49
            r1 = r50
            r2 = r28
            r3 = r31
            r4 = r30
            r7 = r6
            r6 = r46
            r47 = r7
            r7 = r44
            r40 = r8
            r8 = r14
            r0.drawValue(r1, r2, r3, r4, r5, r6, r7, r8)
            int r0 = r22.getXValCount()
            if (r12 >= r0) goto L_0x02a0
            java.util.List r0 = r22.getXVals()
            java.lang.Object r0 = r0.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            float r1 = r44 + r27
            android.graphics.Paint r2 = r9.mValuePaint
            r6 = r46
            r10.drawText(r0, r6, r1, r2)
            goto L_0x02a0
        L_0x0273:
            r47 = r6
            r40 = r8
            r6 = r46
            if (r14 == 0) goto L_0x02a3
            int r0 = r22.getXValCount()
            if (r12 >= r0) goto L_0x02a0
            android.graphics.Paint r0 = r9.mValuePaint
            r14 = r47
            int r1 = r14.getValueTextColor(r12)
            r0.setColor(r1)
            java.util.List r0 = r22.getXVals()
            java.lang.Object r0 = r0.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            float r1 = r27 / r20
            float r1 = r44 + r1
            android.graphics.Paint r2 = r9.mValuePaint
            r10.drawText(r0, r6, r1, r2)
            goto L_0x02bd
        L_0x02a0:
            r14 = r47
            goto L_0x02bd
        L_0x02a3:
            r14 = r47
            if (r7 == 0) goto L_0x02bd
            r5 = 0
            float r0 = r27 / r20
            float r7 = r44 + r0
            int r8 = r14.getValueTextColor(r12)
            r0 = r49
            r1 = r50
            r2 = r28
            r3 = r31
            r4 = r30
            r0.drawValue(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x02bd:
            if (r39 != 0) goto L_0x02c6
            if (r37 == 0) goto L_0x02c2
            goto L_0x02c6
        L_0x02c2:
            r40 = r11
            goto L_0x034c
        L_0x02c6:
            float r8 = r21 * r40
            float r0 = r11.x
            float r8 = r8 + r0
            float r13 = r13 * r21
            float r0 = r11.y
            float r13 = r13 + r0
            android.graphics.Paint r0 = r9.mValuePaint
            android.graphics.Paint$Align r1 = android.graphics.Paint.Align.CENTER
            r0.setTextAlign(r1)
            if (r39 == 0) goto L_0x030c
            if (r37 == 0) goto L_0x030c
            r5 = 0
            int r37 = r14.getValueTextColor(r12)
            r0 = r49
            r1 = r50
            r2 = r28
            r3 = r31
            r4 = r30
            r6 = r8
            r7 = r13
            r40 = r11
            r11 = r8
            r8 = r37
            r0.drawValue(r1, r2, r3, r4, r5, r6, r7, r8)
            int r0 = r22.getXValCount()
            if (r12 >= r0) goto L_0x034c
            java.util.List r0 = r22.getXVals()
            java.lang.Object r0 = r0.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            float r13 = r13 + r27
            android.graphics.Paint r1 = r9.mValuePaint
            r10.drawText(r0, r11, r13, r1)
            goto L_0x034c
        L_0x030c:
            r40 = r11
            r11 = r8
            if (r39 == 0) goto L_0x0333
            int r0 = r22.getXValCount()
            if (r12 >= r0) goto L_0x034c
            android.graphics.Paint r0 = r9.mValuePaint
            int r1 = r14.getValueTextColor(r12)
            r0.setColor(r1)
            java.util.List r0 = r22.getXVals()
            java.lang.Object r0 = r0.get(r12)
            java.lang.String r0 = (java.lang.String) r0
            float r1 = r27 / r20
            float r13 = r13 + r1
            android.graphics.Paint r1 = r9.mValuePaint
            r10.drawText(r0, r11, r13, r1)
            goto L_0x034c
        L_0x0333:
            if (r37 == 0) goto L_0x034c
            r5 = 0
            float r0 = r27 / r20
            float r7 = r13 + r0
            int r8 = r14.getValueTextColor(r12)
            r0 = r49
            r1 = r50
            r2 = r28
            r3 = r31
            r4 = r30
            r6 = r11
            r0.drawValue(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x034c:
            int r29 = r29 + 1
            int r2 = r12 + 1
            r6 = r14
            r3 = r32
            r8 = r33
            r7 = r34
            r13 = r35
            r14 = r36
            r12 = r38
            r11 = r40
            r5 = r41
            r4 = r42
            goto L_0x00d2
        L_0x0365:
            r34 = r7
            r33 = r8
            r40 = r11
            r38 = r12
            r35 = r13
            r36 = r14
            r0 = r29
        L_0x0373:
            int r7 = r34 + 1
            r8 = r33
            r13 = r35
            r14 = r36
            r12 = r38
            r11 = r40
            goto L_0x0072
        L_0x0381:
            r50.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.PieChartRenderer.drawValues(android.graphics.Canvas):void");
    }

    public void drawExtras(Canvas canvas) {
        drawHole(canvas);
        canvas.drawBitmap((Bitmap) this.mDrawBitmap.get(), 0.0f, 0.0f, (Paint) null);
        drawCenterText(canvas);
    }

    /* access modifiers changed from: protected */
    public void drawHole(Canvas canvas) {
        if (this.mChart.isDrawHoleEnabled()) {
            float radius = this.mChart.getRadius();
            float holeRadius = (this.mChart.getHoleRadius() / 100.0f) * radius;
            PointF centerCircleBox = this.mChart.getCenterCircleBox();
            if (Color.alpha(this.mHolePaint.getColor()) > 0) {
                this.mBitmapCanvas.drawCircle(centerCircleBox.x, centerCircleBox.y, holeRadius, this.mHolePaint);
            }
            if (Color.alpha(this.mTransparentCirclePaint.getColor()) > 0 && this.mChart.getTransparentCircleRadius() > this.mChart.getHoleRadius()) {
                int alpha = this.mTransparentCirclePaint.getAlpha();
                float transparentCircleRadius = radius * (this.mChart.getTransparentCircleRadius() / 100.0f);
                this.mTransparentCirclePaint.setAlpha((int) (((float) alpha) * this.mAnimator.getPhaseX() * this.mAnimator.getPhaseY()));
                this.mHoleCirclePath.reset();
                this.mHoleCirclePath.addCircle(centerCircleBox.x, centerCircleBox.y, transparentCircleRadius, Path.Direction.CW);
                this.mHoleCirclePath.addCircle(centerCircleBox.x, centerCircleBox.y, holeRadius, Path.Direction.CCW);
                this.mBitmapCanvas.drawPath(this.mHoleCirclePath, this.mTransparentCirclePaint);
                this.mTransparentCirclePaint.setAlpha(alpha);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawCenterText(Canvas canvas) {
        float f;
        CharSequence centerText = this.mChart.getCenterText();
        if (this.mChart.isDrawCenterTextEnabled() && centerText != null) {
            PointF centerCircleBox = this.mChart.getCenterCircleBox();
            if (!this.mChart.isDrawHoleEnabled() || this.mChart.isDrawSlicesUnderHoleEnabled()) {
                f = this.mChart.getRadius();
            } else {
                f = this.mChart.getRadius() * (this.mChart.getHoleRadius() / 100.0f);
            }
            RectF rectF = this.mRectBuffer[0];
            rectF.left = centerCircleBox.x - f;
            rectF.top = centerCircleBox.y - f;
            rectF.right = centerCircleBox.x + f;
            rectF.bottom = centerCircleBox.y + f;
            RectF rectF2 = this.mRectBuffer[1];
            rectF2.set(rectF);
            float centerTextRadiusPercent = this.mChart.getCenterTextRadiusPercent() / 100.0f;
            if (((double) centerTextRadiusPercent) > 0.0d) {
                rectF2.inset((rectF2.width() - (rectF2.width() * centerTextRadiusPercent)) / 2.0f, (rectF2.height() - (rectF2.height() * centerTextRadiusPercent)) / 2.0f);
            }
            if (!centerText.equals(this.mCenterTextLastValue) || !rectF2.equals(this.mCenterTextLastBounds)) {
                this.mCenterTextLastBounds.set(rectF2);
                this.mCenterTextLastValue = centerText;
                this.mCenterTextLayout = new StaticLayout(centerText, 0, centerText.length(), this.mCenterTextPaint, (int) Math.max(Math.ceil((double) this.mCenterTextLastBounds.width()), 1.0d), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            }
            float height = (float) this.mCenterTextLayout.getHeight();
            canvas.save();
            if (Build.VERSION.SDK_INT >= 18) {
                Path path = new Path();
                path.addOval(rectF, Path.Direction.CW);
                canvas.clipPath(path);
            }
            canvas.translate(rectF2.left, rectF2.top + ((rectF2.height() - height) / 2.0f));
            this.mCenterTextLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        RectF rectF;
        float f;
        float[] fArr;
        float[] fArr2;
        float f2;
        float f3;
        float f4;
        int i;
        PointF pointF;
        IPieDataSet dataSetByIndex;
        float f5;
        int i2;
        float f6;
        int i3;
        float f7;
        int i4;
        int i5;
        float f8;
        float f9;
        Highlight[] highlightArr2 = highlightArr;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        float rotationAngle = this.mChart.getRotationAngle();
        float[] drawAngles = this.mChart.getDrawAngles();
        float[] absoluteAngles = this.mChart.getAbsoluteAngles();
        PointF centerCircleBox = this.mChart.getCenterCircleBox();
        float radius = this.mChart.getRadius();
        boolean z = this.mChart.isDrawHoleEnabled() && !this.mChart.isDrawSlicesUnderHoleEnabled();
        float holeRadius = z ? (this.mChart.getHoleRadius() / 100.0f) * radius : 0.0f;
        RectF rectF2 = new RectF();
        int i6 = 0;
        while (i6 < highlightArr2.length) {
            int xIndex = highlightArr2[i6].getXIndex();
            if (xIndex < drawAngles.length && (dataSetByIndex = ((PieData) this.mChart.getData()).getDataSetByIndex(highlightArr2[i6].getDataSetIndex())) != null && dataSetByIndex.isHighlightEnabled()) {
                int entryCount = dataSetByIndex.getEntryCount();
                int i7 = 0;
                int i8 = 0;
                while (i7 < entryCount) {
                    int i9 = entryCount;
                    float f10 = phaseY;
                    float f11 = rotationAngle;
                    if (((double) Math.abs(dataSetByIndex.getEntryForIndex(i7).getVal())) > 1.0E-6d) {
                        i8++;
                    }
                    i7++;
                    phaseY = f10;
                    entryCount = i9;
                    rotationAngle = f11;
                }
                f3 = phaseY;
                f2 = rotationAngle;
                if (xIndex == 0) {
                    i2 = 1;
                    f5 = 0.0f;
                } else {
                    f5 = absoluteAngles[xIndex - 1] * phaseX;
                    i2 = 1;
                }
                if (i8 <= i2) {
                    f6 = 0.0f;
                } else {
                    f6 = dataSetByIndex.getSliceSpace();
                }
                float f12 = drawAngles[xIndex];
                float selectionShift = dataSetByIndex.getSelectionShift();
                float f13 = radius + selectionShift;
                int i10 = i6;
                rectF2.set(this.mChart.getCircleBox());
                float f14 = -selectionShift;
                rectF2.inset(f14, f14);
                boolean z2 = f6 > 0.0f && f12 <= 180.0f;
                this.mRenderPaint.setColor(dataSetByIndex.getColor(xIndex));
                float f15 = i8 == 1 ? 0.0f : f6 / (radius * 0.017453292f);
                float f16 = i8 == 1 ? 0.0f : f6 / (f13 * 0.017453292f);
                float f17 = f2 + (((f15 / 2.0f) + f5) * f3);
                float f18 = (f12 - f15) * f3;
                float f19 = f18 < 0.0f ? 0.0f : f18;
                float f20 = f2 + (((f16 / 2.0f) + f5) * f3);
                float f21 = (f12 - f16) * f3;
                if (f21 < 0.0f) {
                    f21 = 0.0f;
                }
                this.mPathBuffer.reset();
                float f22 = f19 % 360.0f;
                if (f22 == 0.0f) {
                    this.mPathBuffer.addCircle(centerCircleBox.x, centerCircleBox.y, f13, Path.Direction.CW);
                    f7 = holeRadius;
                    i3 = i8;
                    f = phaseX;
                } else {
                    f7 = holeRadius;
                    i3 = i8;
                    double d = (double) (f20 * 0.017453292f);
                    f = phaseX;
                    this.mPathBuffer.moveTo(centerCircleBox.x + (((float) Math.cos(d)) * f13), centerCircleBox.y + (f13 * ((float) Math.sin(d))));
                    this.mPathBuffer.arcTo(rectF2, f20, f21);
                }
                if (z2) {
                    double d2 = (double) (f17 * 0.017453292f);
                    i = i10;
                    rectF = rectF2;
                    f4 = f7;
                    fArr2 = drawAngles;
                    fArr = absoluteAngles;
                    i5 = i3;
                    i4 = 1;
                    f8 = calculateMinimumRadiusForSpacedSlice(centerCircleBox, radius, f12 * f3, (((float) Math.cos(d2)) * radius) + centerCircleBox.x, centerCircleBox.y + (((float) Math.sin(d2)) * radius), f17, f19);
                } else {
                    rectF = rectF2;
                    i = i10;
                    f4 = f7;
                    fArr2 = drawAngles;
                    fArr = absoluteAngles;
                    i5 = i3;
                    i4 = 1;
                    f8 = 0.0f;
                }
                this.mInnerRectBuffer.set(centerCircleBox.x - f4, centerCircleBox.y - f4, centerCircleBox.x + f4, centerCircleBox.y + f4);
                if (!z || (f4 <= 0.0f && !z2)) {
                    pointF = centerCircleBox;
                    if (f22 != 0.0f) {
                        if (z2) {
                            double d3 = (double) ((f17 + (f19 / 2.0f)) * 0.017453292f);
                            this.mPathBuffer.lineTo(pointF.x + (((float) Math.cos(d3)) * f8), pointF.y + (f8 * ((float) Math.sin(d3))));
                        } else {
                            this.mPathBuffer.lineTo(pointF.x, pointF.y);
                        }
                    }
                } else {
                    if (z2) {
                        if (f8 < 0.0f) {
                            f8 = -f8;
                        }
                        f9 = Math.max(f4, f8);
                    } else {
                        f9 = f4;
                    }
                    float f23 = (i5 == i4 || f9 == 0.0f) ? 0.0f : f6 / (f9 * 0.017453292f);
                    float f24 = f2 + ((f5 + (f23 / 2.0f)) * f3);
                    float f25 = (f12 - f23) * f3;
                    if (f25 < 0.0f) {
                        f25 = 0.0f;
                    }
                    float f26 = f24 + f25;
                    if (f22 == 0.0f) {
                        this.mPathBuffer.addCircle(centerCircleBox.x, centerCircleBox.y, f9, Path.Direction.CCW);
                        pointF = centerCircleBox;
                    } else {
                        double d4 = (double) (f26 * 0.017453292f);
                        pointF = centerCircleBox;
                        this.mPathBuffer.lineTo(centerCircleBox.x + (((float) Math.cos(d4)) * f9), pointF.y + (f9 * ((float) Math.sin(d4))));
                        this.mPathBuffer.arcTo(this.mInnerRectBuffer, f26, -f25);
                    }
                }
                this.mPathBuffer.close();
                this.mBitmapCanvas.drawPath(this.mPathBuffer, this.mRenderPaint);
            } else {
                i = i6;
                rectF = rectF2;
                f = phaseX;
                f3 = phaseY;
                f2 = rotationAngle;
                fArr2 = drawAngles;
                fArr = absoluteAngles;
                pointF = centerCircleBox;
                f4 = holeRadius;
            }
            i6 = i + 1;
            highlightArr2 = highlightArr;
            centerCircleBox = pointF;
            holeRadius = f4;
            phaseY = f3;
            rotationAngle = f2;
            drawAngles = fArr2;
            absoluteAngles = fArr;
            phaseX = f;
            rectF2 = rectF;
        }
    }

    /* access modifiers changed from: protected */
    public void drawRoundedSlices(Canvas canvas) {
        float f;
        float f2;
        float[] fArr;
        if (this.mChart.isDrawRoundedSlicesEnabled()) {
            IPieDataSet dataSet = ((PieData) this.mChart.getData()).getDataSet();
            if (dataSet.isVisible()) {
                float phaseX = this.mAnimator.getPhaseX();
                float phaseY = this.mAnimator.getPhaseY();
                PointF centerCircleBox = this.mChart.getCenterCircleBox();
                float radius = this.mChart.getRadius();
                float holeRadius = (radius - ((this.mChart.getHoleRadius() * radius) / 100.0f)) / 2.0f;
                float[] drawAngles = this.mChart.getDrawAngles();
                float rotationAngle = this.mChart.getRotationAngle();
                int i = 0;
                while (i < dataSet.getEntryCount()) {
                    float f3 = drawAngles[i];
                    if (((double) Math.abs(dataSet.getEntryForIndex(i).getVal())) > 1.0E-6d) {
                        double d = (double) (radius - holeRadius);
                        double d2 = (double) ((rotationAngle + f3) * phaseY);
                        double cos = Math.cos(Math.toRadians(d2));
                        Double.isNaN(d);
                        f = phaseY;
                        fArr = drawAngles;
                        f2 = rotationAngle;
                        double d3 = (double) centerCircleBox.x;
                        Double.isNaN(d3);
                        float f4 = (float) (d3 + (cos * d));
                        double sin = Math.sin(Math.toRadians(d2));
                        Double.isNaN(d);
                        double d4 = d * sin;
                        double d5 = (double) centerCircleBox.y;
                        Double.isNaN(d5);
                        this.mRenderPaint.setColor(dataSet.getColor(i));
                        this.mBitmapCanvas.drawCircle(f4, (float) (d4 + d5), holeRadius, this.mRenderPaint);
                    } else {
                        f = phaseY;
                        fArr = drawAngles;
                        f2 = rotationAngle;
                    }
                    rotationAngle = f2 + (f3 * phaseX);
                    i++;
                    phaseY = f;
                    drawAngles = fArr;
                }
            }
        }
    }

    public void releaseBitmap() {
        Canvas canvas = this.mBitmapCanvas;
        if (canvas != null) {
            canvas.setBitmap((Bitmap) null);
            this.mBitmapCanvas = null;
        }
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (weakReference != null) {
            ((Bitmap) weakReference.get()).recycle();
            this.mDrawBitmap.clear();
            this.mDrawBitmap = null;
        }
    }
}
