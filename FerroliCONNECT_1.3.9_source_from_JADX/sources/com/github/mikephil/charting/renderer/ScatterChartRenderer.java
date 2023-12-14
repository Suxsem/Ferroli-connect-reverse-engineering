package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.ScatterBuffer;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class ScatterChartRenderer extends LineScatterCandleRadarRenderer {
    protected ScatterDataProvider mChart;
    protected ScatterBuffer[] mScatterBuffers;

    public void drawExtras(Canvas canvas) {
    }

    public ScatterChartRenderer(ScatterDataProvider scatterDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = scatterDataProvider;
    }

    public void initBuffers() {
        ScatterData scatterData = this.mChart.getScatterData();
        this.mScatterBuffers = new ScatterBuffer[scatterData.getDataSetCount()];
        for (int i = 0; i < this.mScatterBuffers.length; i++) {
            this.mScatterBuffers[i] = new ScatterBuffer(((IScatterDataSet) scatterData.getDataSetByIndex(i)).getEntryCount() * 2);
        }
    }

    public void drawData(Canvas canvas) {
        for (IScatterDataSet iScatterDataSet : this.mChart.getScatterData().getDataSets()) {
            if (iScatterDataSet.isVisible()) {
                drawDataSet(canvas, iScatterDataSet);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IScatterDataSet iScatterDataSet) {
        int i;
        Canvas canvas2 = canvas;
        IScatterDataSet iScatterDataSet2 = iScatterDataSet;
        Transformer transformer = this.mChart.getTransformer(iScatterDataSet.getAxisDependency());
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        float convertDpToPixel = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeSize());
        float f = convertDpToPixel / 2.0f;
        float convertDpToPixel2 = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeHoleRadius());
        float f2 = convertDpToPixel2 * 2.0f;
        int scatterShapeHoleColor = iScatterDataSet.getScatterShapeHoleColor();
        float f3 = (convertDpToPixel - f2) / 2.0f;
        float f4 = f3 / 2.0f;
        ScatterChart.ScatterShape scatterShape = iScatterDataSet.getScatterShape();
        ScatterBuffer scatterBuffer = this.mScatterBuffers[this.mChart.getScatterData().getIndexOfDataSet(iScatterDataSet2)];
        scatterBuffer.setPhases(max, phaseY);
        scatterBuffer.feed(iScatterDataSet2);
        transformer.pointValuesToPixel(scatterBuffer.buffer);
        int i2 = C10001.f1489x9beb7303[scatterShape.ordinal()];
        int i3 = 0;
        if (i2 == 1) {
            int i4 = 0;
            while (i4 < scatterBuffer.size() && this.mViewPortHandler.isInBoundsRight(scatterBuffer.buffer[i4])) {
                if (this.mViewPortHandler.isInBoundsLeft(scatterBuffer.buffer[i4])) {
                    int i5 = i4 + 1;
                    if (this.mViewPortHandler.isInBoundsY(scatterBuffer.buffer[i5])) {
                        this.mRenderPaint.setColor(iScatterDataSet2.getColor(i4 / 2));
                        if (((double) f2) > 0.0d) {
                            this.mRenderPaint.setStyle(Paint.Style.STROKE);
                            this.mRenderPaint.setStrokeWidth(f3);
                            i = i4;
                            canvas.drawRect((scatterBuffer.buffer[i4] - convertDpToPixel2) - f4, (scatterBuffer.buffer[i5] - convertDpToPixel2) - f4, scatterBuffer.buffer[i4] + convertDpToPixel2 + f4, scatterBuffer.buffer[i5] + convertDpToPixel2 + f4, this.mRenderPaint);
                            if (scatterShapeHoleColor != 1122867) {
                                this.mRenderPaint.setStyle(Paint.Style.FILL);
                                this.mRenderPaint.setColor(scatterShapeHoleColor);
                                canvas.drawRect(scatterBuffer.buffer[i] - convertDpToPixel2, scatterBuffer.buffer[i5] - convertDpToPixel2, scatterBuffer.buffer[i] + convertDpToPixel2, scatterBuffer.buffer[i5] + convertDpToPixel2, this.mRenderPaint);
                            }
                        } else {
                            i = i4;
                            this.mRenderPaint.setStyle(Paint.Style.FILL);
                            canvas.drawRect(scatterBuffer.buffer[i] - f, scatterBuffer.buffer[i5] - f, scatterBuffer.buffer[i] + f, scatterBuffer.buffer[i5] + f, this.mRenderPaint);
                        }
                        i4 = i + 2;
                        Canvas canvas3 = canvas;
                    }
                }
                i = i4;
                i4 = i + 2;
                Canvas canvas32 = canvas;
            }
        } else if (i2 == 2) {
            while (i3 < scatterBuffer.size() && this.mViewPortHandler.isInBoundsRight(scatterBuffer.buffer[i3])) {
                if (this.mViewPortHandler.isInBoundsLeft(scatterBuffer.buffer[i3])) {
                    int i6 = i3 + 1;
                    if (this.mViewPortHandler.isInBoundsY(scatterBuffer.buffer[i6])) {
                        this.mRenderPaint.setColor(iScatterDataSet2.getColor(i3 / 2));
                        if (((double) f2) > 0.0d) {
                            this.mRenderPaint.setStyle(Paint.Style.STROKE);
                            this.mRenderPaint.setStrokeWidth(f3);
                            Canvas canvas4 = canvas;
                            canvas4.drawCircle(scatterBuffer.buffer[i3], scatterBuffer.buffer[i6], convertDpToPixel2 + f4, this.mRenderPaint);
                            if (scatterShapeHoleColor != 1122867) {
                                this.mRenderPaint.setStyle(Paint.Style.FILL);
                                this.mRenderPaint.setColor(scatterShapeHoleColor);
                                canvas4.drawCircle(scatterBuffer.buffer[i3], scatterBuffer.buffer[i6], convertDpToPixel2, this.mRenderPaint);
                            }
                        } else {
                            this.mRenderPaint.setStyle(Paint.Style.FILL);
                            canvas.drawCircle(scatterBuffer.buffer[i3], scatterBuffer.buffer[i6], f, this.mRenderPaint);
                        }
                    }
                }
                i3 += 2;
            }
        } else if (i2 == 3) {
            this.mRenderPaint.setStyle(Paint.Style.FILL);
            Path path = new Path();
            while (i3 < scatterBuffer.size() && this.mViewPortHandler.isInBoundsRight(scatterBuffer.buffer[i3])) {
                if (this.mViewPortHandler.isInBoundsLeft(scatterBuffer.buffer[i3])) {
                    int i7 = i3 + 1;
                    if (this.mViewPortHandler.isInBoundsY(scatterBuffer.buffer[i7])) {
                        this.mRenderPaint.setColor(iScatterDataSet2.getColor(i3 / 2));
                        path.moveTo(scatterBuffer.buffer[i3], scatterBuffer.buffer[i7] - f);
                        path.lineTo(scatterBuffer.buffer[i3] + f, scatterBuffer.buffer[i7] + f);
                        path.lineTo(scatterBuffer.buffer[i3] - f, scatterBuffer.buffer[i7] + f);
                        double d = (double) f2;
                        if (d > 0.0d) {
                            path.lineTo(scatterBuffer.buffer[i3], scatterBuffer.buffer[i7] - f);
                            path.moveTo((scatterBuffer.buffer[i3] - f) + f3, (scatterBuffer.buffer[i7] + f) - f3);
                            path.lineTo((scatterBuffer.buffer[i3] + f) - f3, (scatterBuffer.buffer[i7] + f) - f3);
                            path.lineTo(scatterBuffer.buffer[i3], (scatterBuffer.buffer[i7] - f) + f3);
                            path.lineTo((scatterBuffer.buffer[i3] - f) + f3, (scatterBuffer.buffer[i7] + f) - f3);
                        }
                        path.close();
                        canvas2.drawPath(path, this.mRenderPaint);
                        path.reset();
                        if (d > 0.0d && scatterShapeHoleColor != 1122867) {
                            this.mRenderPaint.setColor(scatterShapeHoleColor);
                            path.moveTo(scatterBuffer.buffer[i3], (scatterBuffer.buffer[i7] - f) + f3);
                            path.lineTo((scatterBuffer.buffer[i3] + f) - f3, (scatterBuffer.buffer[i7] + f) - f3);
                            path.lineTo((scatterBuffer.buffer[i3] - f) + f3, (scatterBuffer.buffer[i7] + f) - f3);
                            path.close();
                            canvas2.drawPath(path, this.mRenderPaint);
                            path.reset();
                        }
                    }
                }
                i3 += 2;
            }
        } else if (i2 == 4) {
            this.mRenderPaint.setStyle(Paint.Style.STROKE);
            this.mRenderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
            int i8 = 0;
            while (i8 < scatterBuffer.size() && this.mViewPortHandler.isInBoundsRight(scatterBuffer.buffer[i8])) {
                if (this.mViewPortHandler.isInBoundsLeft(scatterBuffer.buffer[i8])) {
                    int i9 = i8 + 1;
                    if (this.mViewPortHandler.isInBoundsY(scatterBuffer.buffer[i9])) {
                        this.mRenderPaint.setColor(iScatterDataSet2.getColor(i8 / 2));
                        canvas.drawLine(scatterBuffer.buffer[i8] - f, scatterBuffer.buffer[i9], scatterBuffer.buffer[i8] + f, scatterBuffer.buffer[i9], this.mRenderPaint);
                        canvas.drawLine(scatterBuffer.buffer[i8], scatterBuffer.buffer[i9] - f, scatterBuffer.buffer[i8], scatterBuffer.buffer[i9] + f, this.mRenderPaint);
                    }
                }
                i8 += 2;
            }
        } else if (i2 == 5) {
            this.mRenderPaint.setStyle(Paint.Style.STROKE);
            this.mRenderPaint.setStrokeWidth(Utils.convertDpToPixel(1.0f));
            int i10 = 0;
            while (i10 < scatterBuffer.size() && this.mViewPortHandler.isInBoundsRight(scatterBuffer.buffer[i10])) {
                if (this.mViewPortHandler.isInBoundsLeft(scatterBuffer.buffer[i10])) {
                    int i11 = i10 + 1;
                    if (this.mViewPortHandler.isInBoundsY(scatterBuffer.buffer[i11])) {
                        this.mRenderPaint.setColor(iScatterDataSet2.getColor(i10 / 2));
                        canvas.drawLine(scatterBuffer.buffer[i10] - f, scatterBuffer.buffer[i11] - f, scatterBuffer.buffer[i10] + f, scatterBuffer.buffer[i11] + f, this.mRenderPaint);
                        canvas.drawLine(scatterBuffer.buffer[i10] + f, scatterBuffer.buffer[i11] - f, scatterBuffer.buffer[i10] - f, scatterBuffer.buffer[i11] + f, this.mRenderPaint);
                    }
                }
                i10 += 2;
            }
        }
    }

    /* renamed from: com.github.mikephil.charting.renderer.ScatterChartRenderer$1 */
    static /* synthetic */ class C10001 {

        /* renamed from: $SwitchMap$com$github$mikephil$charting$charts$ScatterChart$ScatterShape */
        static final /* synthetic */ int[] f1489x9beb7303 = new int[ScatterChart.ScatterShape.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape[] r0 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1489x9beb7303 = r0
                int[] r0 = f1489x9beb7303     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape r1 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.SQUARE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1489x9beb7303     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape r1 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.CIRCLE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1489x9beb7303     // Catch:{ NoSuchFieldError -> 0x002a }
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape r1 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.TRIANGLE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f1489x9beb7303     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape r1 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.CROSS     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f1489x9beb7303     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.github.mikephil.charting.charts.ScatterChart$ScatterShape r1 = com.github.mikephil.charting.charts.ScatterChart.ScatterShape.X     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.ScatterChartRenderer.C10001.<clinit>():void");
        }
    }

    public void drawValues(Canvas canvas) {
        int i;
        if (((float) this.mChart.getScatterData().getYValCount()) < ((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleX()) {
            List dataSets = this.mChart.getScatterData().getDataSets();
            for (int i2 = 0; i2 < this.mChart.getScatterData().getDataSetCount(); i2++) {
                IScatterDataSet iScatterDataSet = (IScatterDataSet) dataSets.get(i2);
                if (iScatterDataSet.isDrawValuesEnabled() && iScatterDataSet.getEntryCount() != 0) {
                    applyValueTextStyle(iScatterDataSet);
                    float[] generateTransformedValuesScatter = this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).generateTransformedValuesScatter(iScatterDataSet, this.mAnimator.getPhaseY());
                    float convertDpToPixel = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeSize());
                    int i3 = 0;
                    while (((float) i3) < ((float) generateTransformedValuesScatter.length) * this.mAnimator.getPhaseX() && this.mViewPortHandler.isInBoundsRight(generateTransformedValuesScatter[i3])) {
                        if (this.mViewPortHandler.isInBoundsLeft(generateTransformedValuesScatter[i3])) {
                            int i4 = i3 + 1;
                            if (this.mViewPortHandler.isInBoundsY(generateTransformedValuesScatter[i4])) {
                                int i5 = i3 / 2;
                                Entry entryForIndex = iScatterDataSet.getEntryForIndex(i5);
                                i = i3;
                                drawValue(canvas, iScatterDataSet.getValueFormatter(), entryForIndex.getVal(), entryForIndex, i2, generateTransformedValuesScatter[i3], generateTransformedValuesScatter[i4] - convertDpToPixel, iScatterDataSet.getValueTextColor(i5));
                                i3 = i + 2;
                            }
                        }
                        i = i3;
                        i3 = i + 2;
                    }
                }
            }
        }
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        int i2;
        ScatterData scatterData = this.mChart.getScatterData();
        for (Highlight highlight : highlightArr) {
            if (highlight.getDataSetIndex() == -1) {
                i = 0;
            } else {
                i = highlight.getDataSetIndex();
            }
            if (highlight.getDataSetIndex() == -1) {
                i2 = scatterData.getDataSetCount();
            } else {
                i2 = highlight.getDataSetIndex() + 1;
            }
            if (i2 - i >= 1) {
                while (i < i2) {
                    IScatterDataSet iScatterDataSet = (IScatterDataSet) scatterData.getDataSetByIndex(i);
                    if (iScatterDataSet != null && iScatterDataSet.isHighlightEnabled()) {
                        int xIndex = highlight.getXIndex();
                        float f = (float) xIndex;
                        if (f <= this.mChart.getXChartMax() * this.mAnimator.getPhaseX()) {
                            float yValForXIndex = iScatterDataSet.getYValForXIndex(xIndex);
                            if (!Float.isNaN(yValForXIndex)) {
                                float[] fArr = {f, yValForXIndex * this.mAnimator.getPhaseY()};
                                this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).pointValuesToPixel(fArr);
                                drawHighlightLines(canvas, fArr, iScatterDataSet);
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }
}
