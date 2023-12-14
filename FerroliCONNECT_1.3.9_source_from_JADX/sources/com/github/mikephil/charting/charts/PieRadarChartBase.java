package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.utils.SelectionDetail;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>> extends Chart<T> {
    protected float mMinOffset = 0.0f;
    private float mRawRotationAngle = 270.0f;
    protected boolean mRotateEnabled = true;
    private float mRotationAngle = 270.0f;

    public abstract int getIndexForAngle(float f);

    public abstract float getRadius();

    /* access modifiers changed from: protected */
    public abstract float getRequiredBaseOffset();

    /* access modifiers changed from: protected */
    public abstract float getRequiredLegendOffset();

    public float getYChartMax() {
        return 0.0f;
    }

    public float getYChartMin() {
        return 0.0f;
    }

    public PieRadarChartBase(Context context) {
        super(context);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.mChartTouchListener = new PieRadarChartTouchListener(this);
    }

    /* access modifiers changed from: protected */
    public void calcMinMax() {
        this.mXAxis.mAxisRange = (float) (this.mData.getXVals().size() - 1);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mTouchEnabled || this.mChartTouchListener == null) {
            return super.onTouchEvent(motionEvent);
        }
        return this.mChartTouchListener.onTouch(this, motionEvent);
    }

    public void computeScroll() {
        if (this.mChartTouchListener instanceof PieRadarChartTouchListener) {
            ((PieRadarChartTouchListener) this.mChartTouchListener).computeScroll();
        }
    }

    public void notifyDataSetChanged() {
        if (this.mData != null) {
            calcMinMax();
            if (this.mLegend != null) {
                this.mLegendRenderer.computeLegend(this.mData);
            }
            calculateOffsets();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x008d, code lost:
        if (r2 != 2) goto L_0x008f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0184  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void calculateOffsets() {
        /*
            r9 = this;
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            r1 = 0
            if (r0 == 0) goto L_0x019c
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            boolean r0 = r0.isEnabled()
            if (r0 == 0) goto L_0x019c
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            boolean r0 = r0.isDrawInsideEnabled()
            if (r0 != 0) goto L_0x019c
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            float r0 = r0.mNeededWidth
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r9.mViewPortHandler
            float r2 = r2.getChartWidth()
            com.github.mikephil.charting.components.Legend r3 = r9.mLegend
            float r3 = r3.getMaxSizePercent()
            float r2 = r2 * r3
            float r0 = java.lang.Math.min(r0, r2)
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            float r2 = r2.getFormSize()
            float r0 = r0 + r2
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            float r2 = r2.getFormToTextSpace()
            float r0 = r0 + r2
            int[] r2 = com.github.mikephil.charting.charts.PieRadarChartBase.C09932.f1473x9c9dbef
            com.github.mikephil.charting.components.Legend r3 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendOrientation r3 = r3.getOrientation()
            int r3 = r3.ordinal()
            r2 = r2[r3]
            r3 = 2
            r4 = 1
            if (r2 == r4) goto L_0x009c
            if (r2 == r3) goto L_0x004e
            goto L_0x008f
        L_0x004e:
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r0 = r0.getVerticalAlignment()
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
            if (r0 == r2) goto L_0x0062
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r0 = r0.getVerticalAlignment()
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM
            if (r0 != r2) goto L_0x008f
        L_0x0062:
            float r0 = r9.getRequiredLegendOffset()
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            float r2 = r2.mNeededHeight
            float r2 = r2 + r0
            com.github.mikephil.charting.utils.ViewPortHandler r0 = r9.mViewPortHandler
            float r0 = r0.getChartHeight()
            com.github.mikephil.charting.components.Legend r5 = r9.mLegend
            float r5 = r5.getMaxSizePercent()
            float r0 = r0 * r5
            float r0 = java.lang.Math.min(r2, r0)
            int[] r2 = com.github.mikephil.charting.charts.PieRadarChartBase.C09932.f1474xc926f1ec
            com.github.mikephil.charting.components.Legend r5 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r5 = r5.getVerticalAlignment()
            int r5 = r5.ordinal()
            r2 = r2[r5]
            if (r2 == r4) goto L_0x0097
            if (r2 == r3) goto L_0x0094
        L_0x008f:
            r0 = 0
        L_0x0090:
            r2 = 0
        L_0x0091:
            r3 = 0
            goto L_0x0187
        L_0x0094:
            r2 = r0
            r0 = 0
            goto L_0x0091
        L_0x0097:
            r3 = r0
            r0 = 0
            r2 = 0
            goto L_0x0187
        L_0x009c:
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r2 = r2.getHorizontalAlignment()
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r5 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT
            if (r2 == r5) goto L_0x00b0
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r2 = r2.getHorizontalAlignment()
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r5 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
            if (r2 != r5) goto L_0x0129
        L_0x00b0:
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = r2.getVerticalAlignment()
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r5 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.CENTER
            if (r2 != r5) goto L_0x00c2
            r2 = 1095761920(0x41500000, float:13.0)
            float r2 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r2)
            float r0 = r0 + r2
            goto L_0x012a
        L_0x00c2:
            r2 = 1090519040(0x41000000, float:8.0)
            float r2 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r2)
            float r0 = r0 + r2
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            float r2 = r2.mNeededHeight
            com.github.mikephil.charting.components.Legend r5 = r9.mLegend
            float r5 = r5.mTextHeightMax
            float r2 = r2 + r5
            android.graphics.PointF r5 = r9.getCenter()
            com.github.mikephil.charting.components.Legend r6 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r6 = r6.getHorizontalAlignment()
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r7 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
            r8 = 1097859072(0x41700000, float:15.0)
            if (r6 != r7) goto L_0x00ea
            int r6 = r9.getWidth()
            float r6 = (float) r6
            float r6 = r6 - r0
            float r6 = r6 + r8
            goto L_0x00ec
        L_0x00ea:
            float r6 = r0 - r8
        L_0x00ec:
            float r2 = r2 + r8
            float r7 = r9.distanceToCenter(r6, r2)
            float r8 = r9.getRadius()
            float r6 = r9.getAngleForPoint(r6, r2)
            android.graphics.PointF r6 = r9.getPosition(r5, r8, r6)
            float r8 = r6.x
            float r6 = r6.y
            float r6 = r9.distanceToCenter(r8, r6)
            r8 = 1084227584(0x40a00000, float:5.0)
            float r8 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r8)
            float r5 = r5.y
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 < 0) goto L_0x0121
            int r2 = r9.getHeight()
            float r2 = (float) r2
            float r2 = r2 - r0
            int r5 = r9.getWidth()
            float r5 = (float) r5
            int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r2 <= 0) goto L_0x0121
            goto L_0x012a
        L_0x0121:
            int r0 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r0 >= 0) goto L_0x0129
            float r6 = r6 - r7
            float r0 = r8 + r6
            goto L_0x012a
        L_0x0129:
            r0 = 0
        L_0x012a:
            int[] r2 = com.github.mikephil.charting.charts.PieRadarChartBase.C09932.f1472x2787f53e
            com.github.mikephil.charting.components.Legend r5 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r5 = r5.getHorizontalAlignment()
            int r5 = r5.ordinal()
            r2 = r2[r5]
            if (r2 == r4) goto L_0x0184
            if (r2 == r3) goto L_0x0090
            r0 = 3
            if (r2 == r0) goto L_0x0140
            goto L_0x0152
        L_0x0140:
            int[] r0 = com.github.mikephil.charting.charts.PieRadarChartBase.C09932.f1474xc926f1ec
            com.github.mikephil.charting.components.Legend r2 = r9.mLegend
            com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = r2.getVerticalAlignment()
            int r2 = r2.ordinal()
            r0 = r0[r2]
            if (r0 == r4) goto L_0x016c
            if (r0 == r3) goto L_0x0154
        L_0x0152:
            goto L_0x008f
        L_0x0154:
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            float r0 = r0.mNeededHeight
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r9.mViewPortHandler
            float r2 = r2.getChartHeight()
            com.github.mikephil.charting.components.Legend r3 = r9.mLegend
            float r3 = r3.getMaxSizePercent()
            float r2 = r2 * r3
            float r0 = java.lang.Math.min(r0, r2)
            goto L_0x0094
        L_0x016c:
            com.github.mikephil.charting.components.Legend r0 = r9.mLegend
            float r0 = r0.mNeededHeight
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r9.mViewPortHandler
            float r2 = r2.getChartHeight()
            com.github.mikephil.charting.components.Legend r3 = r9.mLegend
            float r3 = r3.getMaxSizePercent()
            float r2 = r2 * r3
            float r0 = java.lang.Math.min(r0, r2)
            goto L_0x0097
        L_0x0184:
            r1 = r0
            goto L_0x008f
        L_0x0187:
            float r4 = r9.getRequiredBaseOffset()
            float r1 = r1 + r4
            float r4 = r9.getRequiredBaseOffset()
            float r0 = r0 + r4
            float r4 = r9.getRequiredBaseOffset()
            float r3 = r3 + r4
            float r4 = r9.getRequiredBaseOffset()
            float r2 = r2 + r4
            goto L_0x019f
        L_0x019c:
            r0 = 0
            r2 = 0
            r3 = 0
        L_0x019f:
            float r4 = r9.mMinOffset
            float r4 = com.github.mikephil.charting.utils.Utils.convertDpToPixel(r4)
            boolean r5 = r9 instanceof com.github.mikephil.charting.charts.RadarChart
            if (r5 == 0) goto L_0x01c0
            com.github.mikephil.charting.components.XAxis r5 = r9.getXAxis()
            boolean r6 = r5.isEnabled()
            if (r6 == 0) goto L_0x01c0
            boolean r6 = r5.isDrawLabelsEnabled()
            if (r6 == 0) goto L_0x01c0
            int r5 = r5.mLabelRotatedWidth
            float r5 = (float) r5
            float r4 = java.lang.Math.max(r4, r5)
        L_0x01c0:
            float r5 = r9.getExtraTopOffset()
            float r3 = r3 + r5
            float r5 = r9.getExtraRightOffset()
            float r0 = r0 + r5
            float r5 = r9.getExtraBottomOffset()
            float r2 = r2 + r5
            float r5 = r9.getExtraLeftOffset()
            float r1 = r1 + r5
            float r1 = java.lang.Math.max(r4, r1)
            float r3 = java.lang.Math.max(r4, r3)
            float r0 = java.lang.Math.max(r4, r0)
            float r5 = r9.getRequiredBaseOffset()
            float r2 = java.lang.Math.max(r5, r2)
            float r2 = java.lang.Math.max(r4, r2)
            com.github.mikephil.charting.utils.ViewPortHandler r4 = r9.mViewPortHandler
            r4.restrainViewPort(r1, r3, r0, r2)
            boolean r4 = r9.mLogEnabled
            if (r4 == 0) goto L_0x0223
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "offsetLeft: "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r1 = ", offsetTop: "
            r4.append(r1)
            r4.append(r3)
            java.lang.String r1 = ", offsetRight: "
            r4.append(r1)
            r4.append(r0)
            java.lang.String r0 = ", offsetBottom: "
            r4.append(r0)
            r4.append(r2)
            java.lang.String r0 = r4.toString()
            java.lang.String r1 = "MPAndroidChart"
            android.util.Log.i(r1, r0)
        L_0x0223:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.charts.PieRadarChartBase.calculateOffsets():void");
    }

    /* renamed from: com.github.mikephil.charting.charts.PieRadarChartBase$2 */
    static /* synthetic */ class C09932 {

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment */
        static final /* synthetic */ int[] f1472x2787f53e = new int[Legend.LegendHorizontalAlignment.values().length];

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation */
        static final /* synthetic */ int[] f1473x9c9dbef = new int[Legend.LegendOrientation.values().length];

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment */
        static final /* synthetic */ int[] f1474xc926f1ec = new int[Legend.LegendVerticalAlignment.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(17:0|1|2|3|5|6|7|9|10|11|12|13|14|15|17|18|(3:19|20|22)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x005a */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r0 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1473x9c9dbef = r0
                r0 = 1
                int[] r1 = f1473x9c9dbef     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.components.Legend$LegendOrientation r2 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f1473x9c9dbef     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment[] r2 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f1472x2787f53e = r2
                int[] r2 = f1472x2787f53e     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.LEFT     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r2 = f1472x2787f53e     // Catch:{ NoSuchFieldError -> 0x003c }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT     // Catch:{ NoSuchFieldError -> 0x003c }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                int[] r2 = f1472x2787f53e     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.github.mikephil.charting.components.Legend$LegendHorizontalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.CENTER     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r4 = 3
                r2[r3] = r4     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment[] r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f1474xc926f1ec = r2
                int[] r2 = f1474xc926f1ec     // Catch:{ NoSuchFieldError -> 0x005a }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r3 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP     // Catch:{ NoSuchFieldError -> 0x005a }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x005a }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x005a }
            L_0x005a:
                int[] r0 = f1474xc926f1ec     // Catch:{ NoSuchFieldError -> 0x0064 }
                com.github.mikephil.charting.components.Legend$LegendVerticalAlignment r2 = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.BOTTOM     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.charts.PieRadarChartBase.C09932.<clinit>():void");
        }
    }

    public float getAngleForPoint(float f, float f2) {
        PointF centerOffsets = getCenterOffsets();
        double d = (double) (f - centerOffsets.x);
        double d2 = (double) (f2 - centerOffsets.y);
        Double.isNaN(d);
        Double.isNaN(d);
        Double.isNaN(d2);
        Double.isNaN(d2);
        double sqrt = Math.sqrt((d * d) + (d2 * d2));
        Double.isNaN(d2);
        float degrees = (float) Math.toDegrees(Math.acos(d2 / sqrt));
        if (f > centerOffsets.x) {
            degrees = 360.0f - degrees;
        }
        float f3 = degrees + 90.0f;
        return f3 > 360.0f ? f3 - 360.0f : f3;
    }

    /* access modifiers changed from: protected */
    public PointF getPosition(PointF pointF, float f, float f2) {
        double d = (double) pointF.x;
        double d2 = (double) f;
        double d3 = (double) f2;
        double cos = Math.cos(Math.toRadians(d3));
        Double.isNaN(d2);
        Double.isNaN(d);
        double d4 = (double) pointF.y;
        double sin = Math.sin(Math.toRadians(d3));
        Double.isNaN(d2);
        Double.isNaN(d4);
        return new PointF((float) (d + (cos * d2)), (float) (d4 + (d2 * sin)));
    }

    public float distanceToCenter(float f, float f2) {
        float f3;
        float f4;
        PointF centerOffsets = getCenterOffsets();
        if (f > centerOffsets.x) {
            f3 = f - centerOffsets.x;
        } else {
            f3 = centerOffsets.x - f;
        }
        if (f2 > centerOffsets.y) {
            f4 = f2 - centerOffsets.y;
        } else {
            f4 = centerOffsets.y - f2;
        }
        return (float) Math.sqrt(Math.pow((double) f3, 2.0d) + Math.pow((double) f4, 2.0d));
    }

    public void setRotationAngle(float f) {
        this.mRawRotationAngle = f;
        this.mRotationAngle = Utils.getNormalizedAngle(this.mRawRotationAngle);
    }

    public float getRawRotationAngle() {
        return this.mRawRotationAngle;
    }

    public float getRotationAngle() {
        return this.mRotationAngle;
    }

    public void setRotationEnabled(boolean z) {
        this.mRotateEnabled = z;
    }

    public boolean isRotationEnabled() {
        return this.mRotateEnabled;
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public void setMinOffset(float f) {
        this.mMinOffset = f;
    }

    public float getDiameter() {
        RectF contentRect = this.mViewPortHandler.getContentRect();
        contentRect.left += getExtraLeftOffset();
        contentRect.top += getExtraTopOffset();
        contentRect.right -= getExtraRightOffset();
        contentRect.bottom -= getExtraBottomOffset();
        return Math.min(contentRect.width(), contentRect.height());
    }

    public List<SelectionDetail> getSelectionDetailsAtIndex(int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < this.mData.getDataSetCount(); i2++) {
            IDataSet dataSetByIndex = this.mData.getDataSetByIndex(i2);
            float yValForXIndex = dataSetByIndex.getYValForXIndex(i);
            if (!Float.isNaN(yValForXIndex)) {
                arrayList.add(new SelectionDetail(yValForXIndex, i2, dataSetByIndex));
            }
        }
        return arrayList;
    }

    @SuppressLint({"NewApi"})
    public void spin(int i, float f, float f2, Easing.EasingOption easingOption) {
        if (Build.VERSION.SDK_INT >= 11) {
            setRotationAngle(f);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "rotationAngle", new float[]{f, f2});
            ofFloat.setDuration((long) i);
            ofFloat.setInterpolator(Easing.getEasingFunctionFromOption(easingOption));
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    PieRadarChartBase.this.postInvalidate();
                }
            });
            ofFloat.start();
        }
    }
}
