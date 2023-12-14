package com.github.mikephil.charting.renderer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.lang.ref.WeakReference;
import java.util.List;

public class LineChartRenderer extends LineRadarRenderer {
    protected Path cubicFillPath = new Path();
    protected Path cubicPath = new Path();
    protected Canvas mBitmapCanvas;
    protected Bitmap.Config mBitmapConfig = Bitmap.Config.ARGB_8888;
    protected LineDataProvider mChart;
    protected Paint mCirclePaintInner;
    private Path mCirclePathBuffer = new Path();
    protected WeakReference<Bitmap> mDrawBitmap;
    private float[] mLineBuffer = new float[4];

    public void initBuffers() {
    }

    public LineChartRenderer(LineDataProvider lineDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = lineDataProvider;
        this.mCirclePaintInner = new Paint(1);
        this.mCirclePaintInner.setStyle(Paint.Style.FILL);
        this.mCirclePaintInner.setColor(-1);
    }

    public void drawData(Canvas canvas) {
        int chartWidth = (int) this.mViewPortHandler.getChartWidth();
        int chartHeight = (int) this.mViewPortHandler.getChartHeight();
        WeakReference<Bitmap> weakReference = this.mDrawBitmap;
        if (!(weakReference != null && ((Bitmap) weakReference.get()).getWidth() == chartWidth && ((Bitmap) this.mDrawBitmap.get()).getHeight() == chartHeight)) {
            if (chartWidth > 0 && chartHeight > 0) {
                this.mDrawBitmap = new WeakReference<>(Bitmap.createBitmap(chartWidth, chartHeight, this.mBitmapConfig));
                this.mBitmapCanvas = new Canvas((Bitmap) this.mDrawBitmap.get());
            } else {
                return;
            }
        }
        ((Bitmap) this.mDrawBitmap.get()).eraseColor(0);
        for (ILineDataSet iLineDataSet : this.mChart.getLineData().getDataSets()) {
            if (iLineDataSet.isVisible() && iLineDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iLineDataSet);
            }
        }
        canvas.drawBitmap((Bitmap) this.mDrawBitmap.get(), 0.0f, 0.0f, this.mRenderPaint);
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, ILineDataSet iLineDataSet) {
        if (iLineDataSet.getEntryCount() >= 1) {
            this.mRenderPaint.setStrokeWidth(iLineDataSet.getLineWidth());
            this.mRenderPaint.setPathEffect(iLineDataSet.getDashPathEffect());
            int i = C09991.$SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode[iLineDataSet.getMode().ordinal()];
            if (i == 3) {
                drawCubicBezier(canvas, iLineDataSet);
            } else if (i != 4) {
                drawLinear(canvas, iLineDataSet);
            } else {
                drawHorizontalBezier(canvas, iLineDataSet);
            }
            this.mRenderPaint.setPathEffect((PathEffect) null);
        }
    }

    /* renamed from: com.github.mikephil.charting.renderer.LineChartRenderer$1 */
    static /* synthetic */ class C09991 {
        static final /* synthetic */ int[] $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode = new int[LineDataSet.Mode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.github.mikephil.charting.data.LineDataSet$Mode[] r0 = com.github.mikephil.charting.data.LineDataSet.Mode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode = r0
                int[] r0 = $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.LINEAR     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.STEPPED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode     // Catch:{ NoSuchFieldError -> 0x002a }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$github$mikephil$charting$data$LineDataSet$Mode     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.github.mikephil.charting.data.LineDataSet$Mode r1 = com.github.mikephil.charting.data.LineDataSet.Mode.HORIZONTAL_BEZIER     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LineChartRenderer.C09991.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public void drawHorizontalBezier(Canvas canvas, ILineDataSet iLineDataSet) {
        ILineDataSet iLineDataSet2 = iLineDataSet;
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        int entryCount = iLineDataSet.getEntryCount();
        Entry entryForXIndex = iLineDataSet2.getEntryForXIndex(this.mMinX < 0 ? 0 : this.mMinX, DataSet.Rounding.DOWN);
        Entry entryForXIndex2 = iLineDataSet2.getEntryForXIndex(this.mMaxX, DataSet.Rounding.UP);
        int max = Math.max(iLineDataSet2.getEntryIndex(entryForXIndex) - (entryForXIndex == entryForXIndex2 ? 1 : 0), 0);
        int min = Math.min(Math.max(max + 2, iLineDataSet2.getEntryIndex(entryForXIndex2) + 1), entryCount);
        float max2 = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        this.cubicPath.reset();
        int ceil = (int) Math.ceil((double) ((((float) (min - max)) * max2) + ((float) max)));
        if (ceil - max >= 2) {
            Entry entryForIndex = iLineDataSet2.getEntryForIndex(max);
            this.cubicPath.moveTo((float) entryForIndex.getXIndex(), entryForIndex.getVal() * phaseY);
            int min2 = Math.min(ceil, entryCount);
            for (int i = max + 1; i < min2; i++) {
                Entry entryForIndex2 = iLineDataSet2.getEntryForIndex(i - 1);
                Entry entryForIndex3 = iLineDataSet2.getEntryForIndex(i);
                float xIndex = ((float) entryForIndex2.getXIndex()) + (((float) (entryForIndex3.getXIndex() - entryForIndex2.getXIndex())) / 2.0f);
                this.cubicPath.cubicTo(xIndex, entryForIndex2.getVal() * phaseY, xIndex, entryForIndex3.getVal() * phaseY, (float) entryForIndex3.getXIndex(), entryForIndex3.getVal() * phaseY);
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, max, ceil);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect((PathEffect) null);
    }

    /* access modifiers changed from: protected */
    public void drawCubicBezier(Canvas canvas, ILineDataSet iLineDataSet) {
        ILineDataSet iLineDataSet2 = iLineDataSet;
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        int entryCount = iLineDataSet.getEntryCount();
        Entry entryForXIndex = iLineDataSet2.getEntryForXIndex(this.mMinX < 0 ? 0 : this.mMinX, DataSet.Rounding.DOWN);
        Entry entryForXIndex2 = iLineDataSet2.getEntryForXIndex(this.mMaxX, DataSet.Rounding.UP);
        int i = 1;
        int max = Math.max((iLineDataSet2.getEntryIndex(entryForXIndex) - (entryForXIndex == entryForXIndex2 ? 1 : 0)) - 1, 0);
        int min = Math.min(Math.max(max + 2, iLineDataSet2.getEntryIndex(entryForXIndex2) + 1), entryCount);
        float max2 = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        float cubicIntensity = iLineDataSet.getCubicIntensity();
        this.cubicPath.reset();
        int ceil = (int) Math.ceil((double) ((((float) (min - max)) * max2) + ((float) max)));
        if (ceil - max >= 2) {
            Entry entryForIndex = iLineDataSet2.getEntryForIndex(max);
            int i2 = max + 1;
            iLineDataSet2.getEntryForIndex(i2);
            this.cubicPath.moveTo((float) entryForIndex.getXIndex(), entryForIndex.getVal() * phaseY);
            int min2 = Math.min(ceil, entryCount);
            while (i2 < min2) {
                Entry entryForIndex2 = iLineDataSet2.getEntryForIndex(i2 == i ? 0 : i2 - 2);
                Entry entryForIndex3 = iLineDataSet2.getEntryForIndex(i2 - 1);
                Entry entryForIndex4 = iLineDataSet2.getEntryForIndex(i2);
                i2++;
                Entry entryForIndex5 = entryCount > i2 ? iLineDataSet2.getEntryForIndex(i2) : entryForIndex4;
                this.cubicPath.cubicTo(((float) entryForIndex3.getXIndex()) + (((float) (entryForIndex4.getXIndex() - entryForIndex2.getXIndex())) * cubicIntensity), (entryForIndex3.getVal() + ((entryForIndex4.getVal() - entryForIndex2.getVal()) * cubicIntensity)) * phaseY, ((float) entryForIndex4.getXIndex()) - (((float) (entryForIndex5.getXIndex() - entryForIndex3.getXIndex())) * cubicIntensity), (entryForIndex4.getVal() - ((entryForIndex5.getVal() - entryForIndex3.getVal()) * cubicIntensity)) * phaseY, (float) entryForIndex4.getXIndex(), entryForIndex4.getVal() * phaseY);
                entryCount = entryCount;
                i = 1;
            }
        }
        if (iLineDataSet.isDrawFilledEnabled()) {
            this.cubicFillPath.reset();
            this.cubicFillPath.addPath(this.cubicPath);
            drawCubicFill(this.mBitmapCanvas, iLineDataSet, this.cubicFillPath, transformer, max, ceil);
        }
        this.mRenderPaint.setColor(iLineDataSet.getColor());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        transformer.pathValueToPixel(this.cubicPath);
        this.mBitmapCanvas.drawPath(this.cubicPath, this.mRenderPaint);
        this.mRenderPaint.setPathEffect((PathEffect) null);
    }

    /* access modifiers changed from: protected */
    public void drawCubicFill(Canvas canvas, ILineDataSet iLineDataSet, Path path, Transformer transformer, int i, int i2) {
        float f;
        if (i2 - i > 1) {
            float fillLinePosition = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
            Entry entryForIndex = iLineDataSet.getEntryForIndex(i2 - 1);
            Entry entryForIndex2 = iLineDataSet.getEntryForIndex(i);
            float f2 = 0.0f;
            if (entryForIndex == null) {
                f = 0.0f;
            } else {
                f = (float) entryForIndex.getXIndex();
            }
            if (entryForIndex2 != null) {
                f2 = (float) entryForIndex2.getXIndex();
            }
            path.lineTo(f, fillLinePosition);
            path.lineTo(f2, fillLinePosition);
            path.close();
            transformer.pathValueToPixel(path);
            Drawable fillDrawable = iLineDataSet.getFillDrawable();
            if (fillDrawable != null) {
                drawFilledPath(canvas, path, fillDrawable);
            } else {
                drawFilledPath(canvas, path, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawLinear(Canvas canvas, ILineDataSet iLineDataSet) {
        boolean z;
        int i;
        char c;
        ILineDataSet iLineDataSet2 = iLineDataSet;
        int entryCount = iLineDataSet.getEntryCount();
        boolean isDrawSteppedEnabled = iLineDataSet.isDrawSteppedEnabled();
        int i2 = isDrawSteppedEnabled ? 4 : 2;
        Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        Canvas canvas2 = iLineDataSet.isDashedLineEnabled() ? this.mBitmapCanvas : canvas;
        Entry entryForXIndex = iLineDataSet2.getEntryForXIndex(this.mMinX < 0 ? 0 : this.mMinX, DataSet.Rounding.DOWN);
        Entry entryForXIndex2 = iLineDataSet2.getEntryForXIndex(this.mMaxX, DataSet.Rounding.UP);
        int i3 = 1;
        int max2 = Math.max(iLineDataSet2.getEntryIndex(entryForXIndex) - (entryForXIndex == entryForXIndex2 ? 1 : 0), 0);
        int min = Math.min(Math.max(max2 + 2, iLineDataSet2.getEntryIndex(entryForXIndex2) + 1), entryCount);
        int i4 = min;
        int ceil = (int) Math.ceil((double) ((((float) (min - max2)) * max) + ((float) max2)));
        if (iLineDataSet.getColors().size() > 1) {
            int i5 = i2 * 2;
            if (this.mLineBuffer.length != i5) {
                this.mLineBuffer = new float[i5];
            }
            int i6 = max2;
            while (i6 < ceil && (ceil <= i3 || i6 != ceil - 1)) {
                Entry entryForIndex = iLineDataSet2.getEntryForIndex(i6);
                if (entryForIndex != null) {
                    this.mLineBuffer[0] = (float) entryForIndex.getXIndex();
                    this.mLineBuffer[i3] = entryForIndex.getVal() * phaseY;
                    int i7 = i6 + 1;
                    if (i7 < ceil) {
                        Entry entryForIndex2 = iLineDataSet2.getEntryForIndex(i7);
                        if (entryForIndex2 == null) {
                            break;
                        }
                        if (isDrawSteppedEnabled) {
                            this.mLineBuffer[2] = (float) entryForIndex2.getXIndex();
                            float[] fArr = this.mLineBuffer;
                            fArr[3] = fArr[i3];
                            fArr[4] = fArr[2];
                            fArr[5] = fArr[3];
                            fArr[6] = (float) entryForIndex2.getXIndex();
                            this.mLineBuffer[7] = entryForIndex2.getVal() * phaseY;
                        } else {
                            this.mLineBuffer[2] = (float) entryForIndex2.getXIndex();
                            this.mLineBuffer[3] = entryForIndex2.getVal() * phaseY;
                        }
                        c = 0;
                    } else {
                        float[] fArr2 = this.mLineBuffer;
                        c = 0;
                        fArr2[2] = fArr2[0];
                        fArr2[3] = fArr2[1];
                    }
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    if (!this.mViewPortHandler.isInBoundsRight(this.mLineBuffer[c])) {
                        break;
                    } else if (this.mViewPortHandler.isInBoundsLeft(this.mLineBuffer[2]) && ((this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) || this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])) && (this.mViewPortHandler.isInBoundsTop(this.mLineBuffer[1]) || this.mViewPortHandler.isInBoundsBottom(this.mLineBuffer[3])))) {
                        this.mRenderPaint.setColor(iLineDataSet2.getColor(i6));
                        canvas2.drawLines(this.mLineBuffer, 0, i5, this.mRenderPaint);
                    }
                }
                i6++;
                i3 = 1;
            }
        } else {
            int i8 = (entryCount - 1) * i2;
            if (this.mLineBuffer.length != Math.max(i8, i2) * 2) {
                this.mLineBuffer = new float[(Math.max(i8, i2) * 2)];
            }
            if (iLineDataSet2.getEntryForIndex(max2) != null) {
                int i9 = ceil > 1 ? max2 + 1 : max2;
                int i10 = 0;
                while (i9 < ceil) {
                    Entry entryForIndex3 = iLineDataSet2.getEntryForIndex(i9 == 0 ? 0 : i9 - 1);
                    Entry entryForIndex4 = iLineDataSet2.getEntryForIndex(i9);
                    if (entryForIndex3 == null || entryForIndex4 == null) {
                        z = isDrawSteppedEnabled;
                    } else {
                        int i11 = i10 + 1;
                        this.mLineBuffer[i10] = (float) entryForIndex3.getXIndex();
                        int i12 = i11 + 1;
                        this.mLineBuffer[i11] = entryForIndex3.getVal() * phaseY;
                        if (isDrawSteppedEnabled) {
                            int i13 = i12 + 1;
                            z = isDrawSteppedEnabled;
                            this.mLineBuffer[i12] = (float) entryForIndex4.getXIndex();
                            int i14 = i13 + 1;
                            this.mLineBuffer[i13] = entryForIndex3.getVal() * phaseY;
                            int i15 = i14 + 1;
                            this.mLineBuffer[i14] = (float) entryForIndex4.getXIndex();
                            i = i15 + 1;
                            this.mLineBuffer[i15] = entryForIndex3.getVal() * phaseY;
                        } else {
                            z = isDrawSteppedEnabled;
                            i = i12;
                        }
                        int i16 = i + 1;
                        this.mLineBuffer[i] = (float) entryForIndex4.getXIndex();
                        this.mLineBuffer[i16] = entryForIndex4.getVal() * phaseY;
                        i10 = i16 + 1;
                    }
                    i9++;
                    isDrawSteppedEnabled = z;
                }
                if (i10 > 0) {
                    transformer.pointValuesToPixel(this.mLineBuffer);
                    this.mRenderPaint.setColor(iLineDataSet.getColor());
                    canvas2.drawLines(this.mLineBuffer, 0, Math.max(((ceil - max2) - 1) * i2, i2) * 2, this.mRenderPaint);
                }
            }
        }
        this.mRenderPaint.setPathEffect((PathEffect) null);
        if (iLineDataSet.isDrawFilledEnabled() && entryCount > 0) {
            drawLinearFill(canvas, iLineDataSet, max2, i4, transformer);
        }
    }

    /* access modifiers changed from: protected */
    public void drawLinearFill(Canvas canvas, ILineDataSet iLineDataSet, int i, int i2, Transformer transformer) {
        Path generateFilledPath = generateFilledPath(iLineDataSet, i, i2);
        transformer.pathValueToPixel(generateFilledPath);
        Drawable fillDrawable = iLineDataSet.getFillDrawable();
        if (fillDrawable != null) {
            drawFilledPath(canvas, generateFilledPath, fillDrawable);
        } else {
            drawFilledPath(canvas, generateFilledPath, iLineDataSet.getFillColor(), iLineDataSet.getFillAlpha());
        }
    }

    private Path generateFilledPath(ILineDataSet iLineDataSet, int i, int i2) {
        float fillLinePosition = iLineDataSet.getFillFormatter().getFillLinePosition(iLineDataSet, this.mChart);
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        boolean isDrawSteppedEnabled = iLineDataSet.isDrawSteppedEnabled();
        Path path = new Path();
        Entry entryForIndex = iLineDataSet.getEntryForIndex(i);
        path.moveTo((float) entryForIndex.getXIndex(), fillLinePosition);
        path.lineTo((float) entryForIndex.getXIndex(), entryForIndex.getVal() * phaseY);
        double d = (double) ((((float) (i2 - i)) * max) + ((float) i));
        int ceil = (int) Math.ceil(d);
        for (int i3 = i + 1; i3 < ceil; i3++) {
            Entry entryForIndex2 = iLineDataSet.getEntryForIndex(i3);
            if (isDrawSteppedEnabled) {
                Entry entryForIndex3 = iLineDataSet.getEntryForIndex(i3 - 1);
                if (entryForIndex3 == null) {
                } else {
                    path.lineTo((float) entryForIndex2.getXIndex(), entryForIndex3.getVal() * phaseY);
                }
            }
            path.lineTo((float) entryForIndex2.getXIndex(), entryForIndex2.getVal() * phaseY);
        }
        path.lineTo((float) iLineDataSet.getEntryForIndex(Math.max(Math.min(((int) Math.ceil(d)) - 1, iLineDataSet.getEntryCount() - 1), 0)).getXIndex(), fillLinePosition);
        path.close();
        return path;
    }

    public void drawValues(Canvas canvas) {
        int i;
        float[] fArr;
        if (((float) this.mChart.getLineData().getYValCount()) < ((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleX()) {
            List dataSets = this.mChart.getLineData().getDataSets();
            for (int i2 = 0; i2 < dataSets.size(); i2++) {
                ILineDataSet iLineDataSet = (ILineDataSet) dataSets.get(i2);
                if (iLineDataSet.isDrawValuesEnabled() && iLineDataSet.getEntryCount() != 0) {
                    applyValueTextStyle(iLineDataSet);
                    Transformer transformer = this.mChart.getTransformer(iLineDataSet.getAxisDependency());
                    int circleRadius = (int) (iLineDataSet.getCircleRadius() * 1.75f);
                    if (!iLineDataSet.isDrawCirclesEnabled()) {
                        circleRadius /= 2;
                    }
                    int i3 = circleRadius;
                    int entryCount = iLineDataSet.getEntryCount();
                    Entry entryForXIndex = iLineDataSet.getEntryForXIndex(this.mMinX < 0 ? 0 : this.mMinX, DataSet.Rounding.DOWN);
                    Entry entryForXIndex2 = iLineDataSet.getEntryForXIndex(this.mMaxX, DataSet.Rounding.UP);
                    int i4 = entryForXIndex == entryForXIndex2 ? 1 : 0;
                    if (iLineDataSet.getMode() == LineDataSet.Mode.CUBIC_BEZIER) {
                        i4++;
                    }
                    int max = Math.max(iLineDataSet.getEntryIndex(entryForXIndex) - i4, 0);
                    float[] generateTransformedValuesLine = transformer.generateTransformedValuesLine(iLineDataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), max, Math.min(Math.max(max + 2, iLineDataSet.getEntryIndex(entryForXIndex2) + 1), entryCount));
                    int i5 = 0;
                    while (i5 < generateTransformedValuesLine.length) {
                        float f = generateTransformedValuesLine[i5];
                        float f2 = generateTransformedValuesLine[i5 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f)) {
                            break;
                        }
                        if (!this.mViewPortHandler.isInBoundsLeft(f) || !this.mViewPortHandler.isInBoundsY(f2)) {
                            i = i5;
                            fArr = generateTransformedValuesLine;
                        } else {
                            int i6 = i5 / 2;
                            Entry entryForIndex = iLineDataSet.getEntryForIndex(i6 + max);
                            i = i5;
                            fArr = generateTransformedValuesLine;
                            drawValue(canvas, iLineDataSet.getValueFormatter(), entryForIndex.getVal(), entryForIndex, i2, f, f2 - ((float) i3), iLineDataSet.getValueTextColor(i6));
                        }
                        i5 = i + 2;
                        generateTransformedValuesLine = fArr;
                    }
                }
            }
        }
    }

    public void drawExtras(Canvas canvas) {
        drawCircles(canvas);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00cc  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ce  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drawCircles(android.graphics.Canvas r22) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            android.graphics.Paint r2 = r0.mRenderPaint
            android.graphics.Paint$Style r3 = android.graphics.Paint.Style.FILL
            r2.setStyle(r3)
            com.github.mikephil.charting.animation.ChartAnimator r2 = r0.mAnimator
            float r2 = r2.getPhaseX()
            r3 = 1065353216(0x3f800000, float:1.0)
            float r2 = java.lang.Math.min(r3, r2)
            r3 = 0
            float r2 = java.lang.Math.max(r3, r2)
            com.github.mikephil.charting.animation.ChartAnimator r4 = r0.mAnimator
            float r4 = r4.getPhaseY()
            r5 = 2
            float[] r5 = new float[r5]
            com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider r6 = r0.mChart
            com.github.mikephil.charting.data.LineData r6 = r6.getLineData()
            java.util.List r6 = r6.getDataSets()
            r7 = 0
            r8 = 0
        L_0x0031:
            int r9 = r6.size()
            if (r8 >= r9) goto L_0x0196
            java.lang.Object r9 = r6.get(r8)
            com.github.mikephil.charting.interfaces.datasets.ILineDataSet r9 = (com.github.mikephil.charting.interfaces.datasets.ILineDataSet) r9
            boolean r10 = r9.isVisible()
            if (r10 == 0) goto L_0x0185
            boolean r10 = r9.isDrawCirclesEnabled()
            if (r10 == 0) goto L_0x0185
            int r10 = r9.getEntryCount()
            if (r10 != 0) goto L_0x0051
            goto L_0x0185
        L_0x0051:
            android.graphics.Paint r10 = r0.mCirclePaintInner
            int r11 = r9.getCircleHoleColor()
            r10.setColor(r11)
            com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider r10 = r0.mChart
            com.github.mikephil.charting.components.YAxis$AxisDependency r11 = r9.getAxisDependency()
            com.github.mikephil.charting.utils.Transformer r10 = r10.getTransformer(r11)
            int r11 = r9.getEntryCount()
            int r12 = r0.mMinX
            if (r12 >= 0) goto L_0x006e
            r12 = 0
            goto L_0x0070
        L_0x006e:
            int r12 = r0.mMinX
        L_0x0070:
            com.github.mikephil.charting.data.DataSet$Rounding r13 = com.github.mikephil.charting.data.DataSet.Rounding.DOWN
            com.github.mikephil.charting.data.Entry r12 = r9.getEntryForXIndex(r12, r13)
            int r13 = r0.mMaxX
            com.github.mikephil.charting.data.DataSet$Rounding r14 = com.github.mikephil.charting.data.DataSet.Rounding.UP
            com.github.mikephil.charting.data.Entry r13 = r9.getEntryForXIndex(r13, r14)
            if (r12 != r13) goto L_0x0082
            r15 = 1
            goto L_0x0083
        L_0x0082:
            r15 = 0
        L_0x0083:
            com.github.mikephil.charting.data.LineDataSet$Mode r3 = r9.getMode()
            com.github.mikephil.charting.data.LineDataSet$Mode r14 = com.github.mikephil.charting.data.LineDataSet.Mode.CUBIC_BEZIER
            if (r3 != r14) goto L_0x008d
            int r15 = r15 + 1
        L_0x008d:
            int r3 = r9.getEntryIndex(r12)
            int r3 = r3 - r15
            int r3 = java.lang.Math.max(r3, r7)
            int r12 = r3 + 2
            int r13 = r9.getEntryIndex(r13)
            r14 = 1
            int r13 = r13 + r14
            int r12 = java.lang.Math.max(r12, r13)
            int r11 = java.lang.Math.min(r12, r11)
            float r12 = r9.getCircleRadius()
            float r13 = r9.getCircleHoleRadius()
            boolean r14 = r9.isDrawCircleHoleEnabled()
            if (r14 == 0) goto L_0x00bf
            int r14 = (r13 > r12 ? 1 : (r13 == r12 ? 0 : -1))
            if (r14 >= 0) goto L_0x00bf
            r14 = 0
            int r15 = (r13 > r14 ? 1 : (r13 == r14 ? 0 : -1))
            if (r15 <= 0) goto L_0x00c0
            r15 = 1
            goto L_0x00c1
        L_0x00bf:
            r14 = 0
        L_0x00c0:
            r15 = 0
        L_0x00c1:
            if (r15 == 0) goto L_0x00ce
            int r14 = r9.getCircleHoleColor()
            r7 = 1122867(0x112233, float:1.573472E-39)
            if (r14 != r7) goto L_0x00ce
            r7 = 1
            goto L_0x00cf
        L_0x00ce:
            r7 = 0
        L_0x00cf:
            int r11 = r11 - r3
            float r11 = (float) r11
            float r11 = r11 * r2
            float r14 = (float) r3
            float r11 = r11 + r14
            r14 = r2
            r18 = r3
            double r2 = (double) r11
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            r3 = r18
        L_0x00e0:
            if (r3 >= r2) goto L_0x0186
            com.github.mikephil.charting.data.Entry r11 = r9.getEntryForIndex(r3)
            if (r11 != 0) goto L_0x00ea
            goto L_0x0186
        L_0x00ea:
            r18 = r2
            int r2 = r11.getXIndex()
            float r2 = (float) r2
            r17 = 0
            r5[r17] = r2
            float r2 = r11.getVal()
            float r2 = r2 * r4
            r11 = 1
            r5[r11] = r2
            r10.pointValuesToPixel(r5)
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r0.mViewPortHandler
            r11 = r5[r17]
            boolean r2 = r2.isInBoundsRight(r11)
            if (r2 != 0) goto L_0x010d
            goto L_0x0186
        L_0x010d:
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r0.mViewPortHandler
            r11 = r5[r17]
            boolean r2 = r2.isInBoundsLeft(r11)
            if (r2 == 0) goto L_0x0174
            com.github.mikephil.charting.utils.ViewPortHandler r2 = r0.mViewPortHandler
            r19 = r4
            r11 = 1
            r4 = r5[r11]
            boolean r2 = r2.isInBoundsY(r4)
            if (r2 != 0) goto L_0x0125
            goto L_0x0176
        L_0x0125:
            android.graphics.Paint r2 = r0.mRenderPaint
            int r4 = r9.getCircleColor(r3)
            r2.setColor(r4)
            if (r7 == 0) goto L_0x015a
            android.graphics.Path r2 = r0.mCirclePathBuffer
            r2.reset()
            android.graphics.Path r2 = r0.mCirclePathBuffer
            r4 = 0
            r11 = r5[r4]
            r16 = 1
            r4 = r5[r16]
            r20 = r6
            android.graphics.Path$Direction r6 = android.graphics.Path.Direction.CW
            r2.addCircle(r11, r4, r12, r6)
            android.graphics.Path r2 = r0.mCirclePathBuffer
            r4 = 0
            r6 = r5[r4]
            r11 = r5[r16]
            android.graphics.Path$Direction r4 = android.graphics.Path.Direction.CCW
            r2.addCircle(r6, r11, r13, r4)
            android.graphics.Path r2 = r0.mCirclePathBuffer
            android.graphics.Paint r4 = r0.mRenderPaint
            r1.drawPath(r2, r4)
            r2 = 0
            goto L_0x017b
        L_0x015a:
            r20 = r6
            r2 = 0
            r16 = 1
            r4 = r5[r2]
            r6 = r5[r16]
            android.graphics.Paint r11 = r0.mRenderPaint
            r1.drawCircle(r4, r6, r12, r11)
            if (r15 == 0) goto L_0x017b
            r4 = r5[r2]
            r6 = r5[r16]
            android.graphics.Paint r11 = r0.mCirclePaintInner
            r1.drawCircle(r4, r6, r13, r11)
            goto L_0x017b
        L_0x0174:
            r19 = r4
        L_0x0176:
            r20 = r6
            r2 = 0
            r16 = 1
        L_0x017b:
            int r3 = r3 + 1
            r2 = r18
            r4 = r19
            r6 = r20
            goto L_0x00e0
        L_0x0185:
            r14 = r2
        L_0x0186:
            r19 = r4
            r20 = r6
            r2 = 0
            int r8 = r8 + 1
            r2 = r14
            r4 = r19
            r6 = r20
            r3 = 0
            r7 = 0
            goto L_0x0031
        L_0x0196:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.renderer.LineChartRenderer.drawCircles(android.graphics.Canvas):void");
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        int i2;
        LineData lineData = this.mChart.getLineData();
        for (Highlight highlight : highlightArr) {
            if (highlight.getDataSetIndex() == -1) {
                i = 0;
            } else {
                i = highlight.getDataSetIndex();
            }
            if (highlight.getDataSetIndex() == -1) {
                i2 = lineData.getDataSetCount();
            } else {
                i2 = highlight.getDataSetIndex() + 1;
            }
            if (i2 - i >= 1) {
                while (i < i2) {
                    ILineDataSet iLineDataSet = (ILineDataSet) lineData.getDataSetByIndex(i);
                    if (iLineDataSet != null && iLineDataSet.isHighlightEnabled()) {
                        int xIndex = highlight.getXIndex();
                        float f = (float) xIndex;
                        if (f <= this.mChart.getXChartMax() * this.mAnimator.getPhaseX()) {
                            float yValForXIndex = iLineDataSet.getYValForXIndex(xIndex);
                            if (!Float.isNaN(yValForXIndex)) {
                                float[] fArr = {f, yValForXIndex * this.mAnimator.getPhaseY()};
                                this.mChart.getTransformer(iLineDataSet.getAxisDependency()).pointValuesToPixel(fArr);
                                drawHighlightLines(canvas, fArr, iLineDataSet);
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void setBitmapConfig(Bitmap.Config config) {
        this.mBitmapConfig = config;
        releaseBitmap();
    }

    public Bitmap.Config getBitmapConfig() {
        return this.mBitmapConfig;
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
