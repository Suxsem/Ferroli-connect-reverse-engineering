package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;
import org.android.agoo.message.MessageService;

public class BarChartRenderer extends DataRenderer {
    protected Paint mBarBorderPaint;
    protected BarBuffer[] mBarBuffers;
    protected RectF mBarRect = new RectF();
    protected BarDataProvider mChart;
    protected Paint mShadowPaint;

    public void drawExtras(Canvas canvas) {
    }

    public BarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = barDataProvider;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setColor(Color.rgb(0, 0, 0));
        this.mHighlightPaint.setAlpha(120);
        this.mShadowPaint = new Paint(1);
        this.mShadowPaint.setStyle(Paint.Style.FILL);
        this.mBarBorderPaint = new Paint(1);
        this.mBarBorderPaint.setStyle(Paint.Style.STROKE);
    }

    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new BarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new BarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getGroupSpace(), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    public void drawData(Canvas canvas) {
        BarData barData = this.mChart.getBarData();
        for (int i = 0; i < barData.getDataSetCount(); i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            if (iBarDataSet.isVisible() && iBarDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iBarDataSet, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int i) {
        IBarDataSet iBarDataSet2 = iBarDataSet;
        int i2 = i;
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        int i3 = 0;
        boolean z = iBarDataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        BarBuffer barBuffer = this.mBarBuffers[i2];
        barBuffer.setPhases(phaseX, phaseY);
        barBuffer.setBarSpace(iBarDataSet.getBarSpace());
        barBuffer.setDataSet(i2);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.feed(iBarDataSet2);
        transformer.pointValuesToPixel(barBuffer.buffer);
        if (this.mChart.isDrawBarShadowEnabled()) {
            for (int i4 = 0; i4 < barBuffer.size(); i4 += 4) {
                int i5 = i4 + 2;
                if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i5])) {
                    if (!this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i4])) {
                        break;
                    }
                    canvas.drawRect(barBuffer.buffer[i4], this.mViewPortHandler.contentTop(), barBuffer.buffer[i5], this.mViewPortHandler.contentBottom(), this.mShadowPaint);
                }
            }
        }
        if (iBarDataSet.getColors().size() > 1) {
            while (i3 < barBuffer.size()) {
                int i6 = i3 + 2;
                if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i6])) {
                    if (this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i3])) {
                        this.mRenderPaint.setColor(iBarDataSet2.getColor(i3 / 4));
                        int i7 = i3 + 1;
                        int i8 = i3 + 3;
                        canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i7], barBuffer.buffer[i6], barBuffer.buffer[i8], this.mRenderPaint);
                        if (z) {
                            canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i7], barBuffer.buffer[i6], barBuffer.buffer[i8], this.mBarBorderPaint);
                        }
                    } else {
                        return;
                    }
                }
                i3 += 4;
            }
            return;
        }
        this.mRenderPaint.setColor(iBarDataSet.getColor());
        while (i3 < barBuffer.size()) {
            int i9 = i3 + 2;
            if (this.mViewPortHandler.isInBoundsLeft(barBuffer.buffer[i9])) {
                if (this.mViewPortHandler.isInBoundsRight(barBuffer.buffer[i3])) {
                    int i10 = i3 + 1;
                    int i11 = i3 + 3;
                    canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i10], barBuffer.buffer[i9], barBuffer.buffer[i11], this.mRenderPaint);
                    if (z) {
                        canvas.drawRect(barBuffer.buffer[i3], barBuffer.buffer[i10], barBuffer.buffer[i9], barBuffer.buffer[i11], this.mBarBorderPaint);
                    }
                } else {
                    return;
                }
            }
            i3 += 4;
        }
    }

    /* access modifiers changed from: protected */
    public void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set((f - 0.5f) + f4, f2, (f + 0.5f) - f4, f3);
        transformer.rectValueToPixel(this.mBarRect, this.mAnimator.getPhaseY());
    }

    public void drawValues(Canvas canvas) {
        int i;
        List list;
        Transformer transformer;
        float[] fArr;
        int i2;
        float[] fArr2;
        float f;
        int i3;
        float[] fArr3;
        if (passesCheck()) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float convertDpToPixel = Utils.convertDpToPixel(4.5f);
            boolean isDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i4 = 0;
            while (i4 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i4);
                if (iBarDataSet.isDrawValuesEnabled() && iBarDataSet.getEntryCount() != 0) {
                    applyValueTextStyle(iBarDataSet);
                    boolean isInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    float calcTextHeight = (float) Utils.calcTextHeight(this.mValuePaint, MessageService.MSG_ACCS_NOTIFY_CLICK);
                    float f2 = isDrawValueAboveBarEnabled ? -convertDpToPixel : calcTextHeight + convertDpToPixel;
                    float f3 = isDrawValueAboveBarEnabled ? calcTextHeight + convertDpToPixel : -convertDpToPixel;
                    if (isInverted) {
                        f2 = (-f2) - calcTextHeight;
                        f3 = (-f3) - calcTextHeight;
                    }
                    float f4 = f2;
                    float f5 = f3;
                    Transformer transformer2 = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                    float[] transformedValues = getTransformedValues(transformer2, iBarDataSet, i4);
                    if (!iBarDataSet.isStacked()) {
                        int i5 = 0;
                        while (((float) i5) < ((float) transformedValues.length) * this.mAnimator.getPhaseX() && this.mViewPortHandler.isInBoundsRight(transformedValues[i5])) {
                            int i6 = i5 + 1;
                            if (!this.mViewPortHandler.isInBoundsY(transformedValues[i6]) || !this.mViewPortHandler.isInBoundsLeft(transformedValues[i5])) {
                                fArr3 = transformedValues;
                                i3 = i5;
                            } else {
                                int i7 = i5 / 2;
                                BarEntry barEntry = (BarEntry) iBarDataSet.getEntryForIndex(i7);
                                float val = barEntry.getVal();
                                fArr3 = transformedValues;
                                i3 = i5;
                                drawValue(canvas, iBarDataSet.getValueFormatter(), val, barEntry, i4, transformedValues[i5], transformedValues[i6] + (val >= 0.0f ? f4 : f5), iBarDataSet.getValueTextColor(i7));
                            }
                            i5 = i3 + 2;
                            transformedValues = fArr3;
                        }
                    } else {
                        float[] fArr4 = transformedValues;
                        int i8 = 0;
                        while (((float) i8) < ((float) (fArr4.length - 1)) * this.mAnimator.getPhaseX()) {
                            int i9 = i8 / 2;
                            BarEntry barEntry2 = (BarEntry) iBarDataSet.getEntryForIndex(i9);
                            float[] vals = barEntry2.getVals();
                            if (vals != null) {
                                i = i8;
                                list = dataSets;
                                transformer = transformer2;
                                int valueTextColor = iBarDataSet.getValueTextColor(i9);
                                float[] fArr5 = new float[(vals.length * 2)];
                                float f6 = -barEntry2.getNegativeSum();
                                int i10 = 0;
                                int i11 = 0;
                                float f7 = 0.0f;
                                while (i10 < fArr5.length) {
                                    float f8 = vals[i11];
                                    if (f8 >= 0.0f) {
                                        f7 += f8;
                                        f = f6;
                                        f6 = f7;
                                    } else {
                                        f = f6 - f8;
                                    }
                                    fArr5[i10 + 1] = f6 * this.mAnimator.getPhaseY();
                                    i10 += 2;
                                    i11++;
                                    f6 = f;
                                }
                                transformer.pointValuesToPixel(fArr5);
                                int i12 = 0;
                                while (i12 < fArr5.length) {
                                    float f9 = fArr4[i];
                                    int i13 = i12 / 2;
                                    float f10 = fArr5[i12 + 1] + (vals[i13] >= 0.0f ? f4 : f5);
                                    if (!this.mViewPortHandler.isInBoundsRight(f9)) {
                                        break;
                                    }
                                    if (!this.mViewPortHandler.isInBoundsY(f10) || !this.mViewPortHandler.isInBoundsLeft(f9)) {
                                        fArr = vals;
                                        i2 = i12;
                                        fArr2 = fArr5;
                                    } else {
                                        fArr = vals;
                                        i2 = i12;
                                        fArr2 = fArr5;
                                        drawValue(canvas, iBarDataSet.getValueFormatter(), vals[i13], barEntry2, i4, f9, f10, valueTextColor);
                                    }
                                    i12 = i2 + 2;
                                    fArr5 = fArr2;
                                    vals = fArr;
                                }
                            } else if (!this.mViewPortHandler.isInBoundsRight(fArr4[i8])) {
                                break;
                            } else {
                                int i14 = i8 + 1;
                                if (!this.mViewPortHandler.isInBoundsY(fArr4[i14]) || !this.mViewPortHandler.isInBoundsLeft(fArr4[i8])) {
                                    i = i8;
                                    list = dataSets;
                                    transformer = transformer2;
                                } else {
                                    i = i8;
                                    list = dataSets;
                                    transformer = transformer2;
                                    drawValue(canvas, iBarDataSet.getValueFormatter(), barEntry2.getVal(), barEntry2, i4, fArr4[i8], fArr4[i14] + (barEntry2.getVal() >= 0.0f ? f4 : f5), iBarDataSet.getValueTextColor(i9));
                                }
                            }
                            i8 = i + 2;
                            transformer2 = transformer;
                            dataSets = list;
                        }
                    }
                }
                i4++;
                dataSets = dataSets;
            }
        }
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        int i2;
        int i3;
        int i4;
        BarEntry barEntry;
        float f;
        float f2;
        Canvas canvas2 = canvas;
        BarData barData = this.mChart.getBarData();
        int dataSetCount = barData.getDataSetCount();
        for (Highlight highlight : highlightArr) {
            if (highlight.getDataSetIndex() == -1) {
                i = 0;
            } else {
                i = highlight.getDataSetIndex();
            }
            if (highlight.getDataSetIndex() == -1) {
                i2 = barData.getDataSetCount();
            } else {
                i2 = highlight.getDataSetIndex() + 1;
            }
            int i5 = i2;
            if (i5 - i >= 1) {
                int i6 = i;
                while (i6 < i5) {
                    IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i6);
                    if (iBarDataSet != null && iBarDataSet.isHighlightEnabled()) {
                        float barSpace = iBarDataSet.getBarSpace() / 2.0f;
                        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                        this.mHighlightPaint.setColor(iBarDataSet.getHighLightColor());
                        this.mHighlightPaint.setAlpha(iBarDataSet.getHighLightAlpha());
                        int xIndex = highlight.getXIndex();
                        if (xIndex >= 0) {
                            float f3 = (float) xIndex;
                            if (f3 < (this.mChart.getXChartMax() * this.mAnimator.getPhaseX()) / ((float) dataSetCount) && (barEntry = (BarEntry) iBarDataSet.getEntryForXIndex(xIndex)) != null && barEntry.getXIndex() == xIndex) {
                                float groupSpace = barData.getGroupSpace();
                                float f4 = (groupSpace * f3) + ((float) ((xIndex * dataSetCount) + i6)) + (groupSpace / 2.0f);
                                if (highlight.getStackIndex() >= 0) {
                                    f2 = highlight.getRange().from;
                                    f = highlight.getRange().f1480to;
                                } else {
                                    f2 = barEntry.getVal();
                                    f = 0.0f;
                                }
                                float f5 = f2;
                                float f6 = f;
                                Transformer transformer2 = transformer;
                                IBarDataSet iBarDataSet2 = iBarDataSet;
                                i3 = i6;
                                float f7 = barSpace;
                                i4 = i5;
                                prepareBarHighlight(f4, f5, f6, f7, transformer2);
                                canvas2.drawRect(this.mBarRect, this.mHighlightPaint);
                                if (this.mChart.isDrawHighlightArrowEnabled()) {
                                    this.mHighlightPaint.setAlpha(255);
                                    float[] fArr = new float[9];
                                    transformer2.getPixelToValueMatrix().getValues(fArr);
                                    float abs = Math.abs(fArr[4] / fArr[0]);
                                    float barSpace2 = iBarDataSet2.getBarSpace() / 2.0f;
                                    float f8 = abs * barSpace2;
                                    int i7 = (f5 > (-f6) ? 1 : (f5 == (-f6) ? 0 : -1));
                                    float phaseY = f5 * this.mAnimator.getPhaseY();
                                    Path path = new Path();
                                    float f9 = f4 + 0.4f;
                                    float phaseY2 = phaseY + (this.mAnimator.getPhaseY() * 0.07f);
                                    path.moveTo(f9, phaseY2);
                                    float f10 = f9 + barSpace2;
                                    path.lineTo(f10, phaseY2 - f8);
                                    path.lineTo(f10, phaseY2 + f8);
                                    transformer2.pathValueToPixel(path);
                                    canvas2.drawPath(path, this.mHighlightPaint);
                                    i6 = i3 + 1;
                                    i5 = i4;
                                }
                                i6 = i3 + 1;
                                i5 = i4;
                            }
                        }
                    }
                    i3 = i6;
                    i4 = i5;
                    i6 = i3 + 1;
                    i5 = i4;
                }
            }
        }
    }

    public float[] getTransformedValues(Transformer transformer, IBarDataSet iBarDataSet, int i) {
        return transformer.generateTransformedValuesBarChart(iBarDataSet, i, this.mChart.getBarData(), this.mAnimator.getPhaseY());
    }

    /* access modifiers changed from: protected */
    public boolean passesCheck() {
        return ((float) this.mChart.getBarData().getYValCount()) < ((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleX();
    }
}
