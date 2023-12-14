package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class CandleStickChartRenderer extends LineScatterCandleRadarRenderer {
    private float[] mBodyBuffers = new float[4];
    protected CandleDataProvider mChart;
    private float[] mCloseBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mShadowBuffers = new float[8];

    public void drawExtras(Canvas canvas) {
    }

    public void initBuffers() {
    }

    public CandleStickChartRenderer(CandleDataProvider candleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = candleDataProvider;
    }

    public void drawData(Canvas canvas) {
        for (ICandleDataSet iCandleDataSet : this.mChart.getCandleData().getDataSets()) {
            if (iCandleDataSet.isVisible() && iCandleDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iCandleDataSet);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, ICandleDataSet iCandleDataSet) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        ICandleDataSet iCandleDataSet2 = iCandleDataSet;
        Transformer transformer = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        float barSpace = iCandleDataSet.getBarSpace();
        boolean showCandleBar = iCandleDataSet.getShowCandleBar();
        char c = 0;
        int max2 = Math.max(this.mMinX, 0);
        int min = Math.min(this.mMaxX + 1, iCandleDataSet.getEntryCount());
        this.mRenderPaint.setStrokeWidth(iCandleDataSet.getShadowWidth());
        int ceil = (int) Math.ceil((double) ((((float) (min - max2)) * max) + ((float) max2)));
        int i6 = max2;
        while (i6 < ceil) {
            CandleEntry candleEntry = (CandleEntry) iCandleDataSet2.getEntryForIndex(i6);
            int xIndex = candleEntry.getXIndex();
            if (xIndex >= max2 && xIndex < min) {
                float open = candleEntry.getOpen();
                float close = candleEntry.getClose();
                float high = candleEntry.getHigh();
                float low = candleEntry.getLow();
                if (showCandleBar) {
                    float[] fArr = this.mShadowBuffers;
                    float f = (float) xIndex;
                    fArr[c] = f;
                    fArr[2] = f;
                    fArr[4] = f;
                    fArr[6] = f;
                    if (open > close) {
                        fArr[1] = high * phaseY;
                        fArr[3] = open * phaseY;
                        fArr[5] = low * phaseY;
                        fArr[7] = close * phaseY;
                    } else if (open < close) {
                        fArr[1] = high * phaseY;
                        fArr[3] = close * phaseY;
                        fArr[5] = low * phaseY;
                        fArr[7] = open * phaseY;
                    } else {
                        fArr[1] = high * phaseY;
                        fArr[3] = open * phaseY;
                        fArr[5] = low * phaseY;
                        fArr[7] = fArr[3];
                    }
                    transformer.pointValuesToPixel(this.mShadowBuffers);
                    if (!iCandleDataSet.getShadowColorSameAsCandle()) {
                        Paint paint = this.mRenderPaint;
                        if (iCandleDataSet.getShadowColor() == 1122867) {
                            i2 = iCandleDataSet2.getColor(i6);
                        } else {
                            i2 = iCandleDataSet.getShadowColor();
                        }
                        paint.setColor(i2);
                    } else if (open > close) {
                        Paint paint2 = this.mRenderPaint;
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            i5 = iCandleDataSet2.getColor(i6);
                        } else {
                            i5 = iCandleDataSet.getDecreasingColor();
                        }
                        paint2.setColor(i5);
                    } else if (open < close) {
                        Paint paint3 = this.mRenderPaint;
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            i4 = iCandleDataSet2.getColor(i6);
                        } else {
                            i4 = iCandleDataSet.getIncreasingColor();
                        }
                        paint3.setColor(i4);
                    } else {
                        Paint paint4 = this.mRenderPaint;
                        if (iCandleDataSet.getNeutralColor() == 1122867) {
                            i3 = iCandleDataSet2.getColor(i6);
                        } else {
                            i3 = iCandleDataSet.getNeutralColor();
                        }
                        paint4.setColor(i3);
                    }
                    this.mRenderPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawLines(this.mShadowBuffers, this.mRenderPaint);
                    float[] fArr2 = this.mBodyBuffers;
                    fArr2[0] = (f - 0.5f) + barSpace;
                    fArr2[1] = close * phaseY;
                    fArr2[2] = (f + 0.5f) - barSpace;
                    fArr2[3] = open * phaseY;
                    transformer.pointValuesToPixel(fArr2);
                    if (open > close) {
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet2.getColor(i6));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getDecreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getDecreasingPaintStyle());
                        float[] fArr3 = this.mBodyBuffers;
                        canvas.drawRect(fArr3[0], fArr3[3], fArr3[2], fArr3[1], this.mRenderPaint);
                    } else if (open < close) {
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet2.getColor(i6));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getIncreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getIncreasingPaintStyle());
                        float[] fArr4 = this.mBodyBuffers;
                        canvas.drawRect(fArr4[0], fArr4[1], fArr4[2], fArr4[3], this.mRenderPaint);
                    } else {
                        if (iCandleDataSet.getNeutralColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet2.getColor(i6));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getNeutralColor());
                        }
                        float[] fArr5 = this.mBodyBuffers;
                        canvas.drawLine(fArr5[0], fArr5[1], fArr5[2], fArr5[3], this.mRenderPaint);
                    }
                } else {
                    float[] fArr6 = this.mRangeBuffers;
                    float f2 = (float) xIndex;
                    fArr6[0] = f2;
                    fArr6[1] = high * phaseY;
                    fArr6[2] = f2;
                    fArr6[3] = low * phaseY;
                    float[] fArr7 = this.mOpenBuffers;
                    fArr7[0] = (f2 - 0.5f) + barSpace;
                    float f3 = open * phaseY;
                    fArr7[1] = f3;
                    fArr7[2] = f2;
                    fArr7[3] = f3;
                    float[] fArr8 = this.mCloseBuffers;
                    fArr8[0] = (f2 + 0.5f) - barSpace;
                    float f4 = close * phaseY;
                    fArr8[1] = f4;
                    fArr8[2] = f2;
                    fArr8[3] = f4;
                    transformer.pointValuesToPixel(fArr6);
                    transformer.pointValuesToPixel(this.mOpenBuffers);
                    transformer.pointValuesToPixel(this.mCloseBuffers);
                    if (open > close) {
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            i = iCandleDataSet2.getColor(i6);
                        } else {
                            i = iCandleDataSet.getDecreasingColor();
                        }
                    } else if (open < close) {
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            i = iCandleDataSet2.getColor(i6);
                        } else {
                            i = iCandleDataSet.getIncreasingColor();
                        }
                    } else if (iCandleDataSet.getNeutralColor() == 1122867) {
                        i = iCandleDataSet2.getColor(i6);
                    } else {
                        i = iCandleDataSet.getNeutralColor();
                    }
                    this.mRenderPaint.setColor(i);
                    float[] fArr9 = this.mRangeBuffers;
                    Canvas canvas2 = canvas;
                    canvas2.drawLine(fArr9[0], fArr9[1], fArr9[2], fArr9[3], this.mRenderPaint);
                    float[] fArr10 = this.mOpenBuffers;
                    canvas2.drawLine(fArr10[0], fArr10[1], fArr10[2], fArr10[3], this.mRenderPaint);
                    float[] fArr11 = this.mCloseBuffers;
                    canvas2.drawLine(fArr11[0], fArr11[1], fArr11[2], fArr11[3], this.mRenderPaint);
                    i6++;
                    c = 0;
                }
            }
            i6++;
            c = 0;
        }
    }

    public void drawValues(Canvas canvas) {
        int i;
        if (((float) this.mChart.getCandleData().getYValCount()) < ((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleX()) {
            List dataSets = this.mChart.getCandleData().getDataSets();
            for (int i2 = 0; i2 < dataSets.size(); i2++) {
                ICandleDataSet iCandleDataSet = (ICandleDataSet) dataSets.get(i2);
                if (iCandleDataSet.isDrawValuesEnabled() && iCandleDataSet.getEntryCount() != 0) {
                    applyValueTextStyle(iCandleDataSet);
                    Transformer transformer = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
                    int max = Math.max(this.mMinX, 0);
                    ICandleDataSet iCandleDataSet2 = iCandleDataSet;
                    float[] generateTransformedValuesCandle = transformer.generateTransformedValuesCandle(iCandleDataSet2, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), max, Math.min(this.mMaxX + 1, iCandleDataSet.getEntryCount()));
                    float convertDpToPixel = Utils.convertDpToPixel(5.0f);
                    int i3 = 0;
                    while (i3 < generateTransformedValuesCandle.length) {
                        float f = generateTransformedValuesCandle[i3];
                        float f2 = generateTransformedValuesCandle[i3 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f)) {
                            break;
                        }
                        if (!this.mViewPortHandler.isInBoundsLeft(f) || !this.mViewPortHandler.isInBoundsY(f2)) {
                            i = i3;
                        } else {
                            int i4 = i3 / 2;
                            CandleEntry candleEntry = (CandleEntry) iCandleDataSet.getEntryForIndex(i4 + max);
                            i = i3;
                            drawValue(canvas, iCandleDataSet.getValueFormatter(), candleEntry.getHigh(), candleEntry, i2, f, f2 - convertDpToPixel, iCandleDataSet.getValueTextColor(i4));
                        }
                        i3 = i + 2;
                    }
                }
            }
        }
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        int i2;
        CandleEntry candleEntry;
        CandleData candleData = this.mChart.getCandleData();
        for (Highlight highlight : highlightArr) {
            if (highlight.getDataSetIndex() == -1) {
                i = 0;
            } else {
                i = highlight.getDataSetIndex();
            }
            if (highlight.getDataSetIndex() == -1) {
                i2 = candleData.getDataSetCount();
            } else {
                i2 = highlight.getDataSetIndex() + 1;
            }
            if (i2 - i >= 1) {
                while (i < i2) {
                    int xIndex = highlight.getXIndex();
                    ICandleDataSet iCandleDataSet = (ICandleDataSet) this.mChart.getCandleData().getDataSetByIndex(i);
                    if (iCandleDataSet != null && iCandleDataSet.isHighlightEnabled() && (candleEntry = (CandleEntry) iCandleDataSet.getEntryForXIndex(xIndex)) != null && candleEntry.getXIndex() == xIndex) {
                        float[] fArr = {(float) xIndex, ((candleEntry.getLow() * this.mAnimator.getPhaseY()) + (candleEntry.getHigh() * this.mAnimator.getPhaseY())) / 2.0f};
                        this.mChart.getTransformer(iCandleDataSet.getAxisDependency()).pointValuesToPixel(fArr);
                        drawHighlightLines(canvas, fArr, iCandleDataSet);
                    }
                    i++;
                }
            }
        }
    }
}
