package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.buffer.HorizontalBarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;
import org.android.agoo.common.AgooConstants;

public class HorizontalBarChartRenderer extends BarChartRenderer {
    public HorizontalBarChartRenderer(BarDataProvider barDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(barDataProvider, chartAnimator, viewPortHandler);
        this.mValuePaint.setTextAlign(Paint.Align.LEFT);
    }

    public void initBuffers() {
        BarData barData = this.mChart.getBarData();
        this.mBarBuffers = new HorizontalBarBuffer[barData.getDataSetCount()];
        for (int i = 0; i < this.mBarBuffers.length; i++) {
            IBarDataSet iBarDataSet = (IBarDataSet) barData.getDataSetByIndex(i);
            this.mBarBuffers[i] = new HorizontalBarBuffer(iBarDataSet.getEntryCount() * 4 * (iBarDataSet.isStacked() ? iBarDataSet.getStackSize() : 1), barData.getGroupSpace(), barData.getDataSetCount(), iBarDataSet.isStacked());
        }
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IBarDataSet iBarDataSet, int i) {
        Transformer transformer = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
        this.mShadowPaint.setColor(iBarDataSet.getBarShadowColor());
        this.mBarBorderPaint.setColor(iBarDataSet.getBarBorderColor());
        this.mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(iBarDataSet.getBarBorderWidth()));
        int i2 = 0;
        boolean z = iBarDataSet.getBarBorderWidth() > 0.0f;
        float phaseX = this.mAnimator.getPhaseX();
        float phaseY = this.mAnimator.getPhaseY();
        BarBuffer barBuffer = this.mBarBuffers[i];
        barBuffer.setPhases(phaseX, phaseY);
        barBuffer.setBarSpace(iBarDataSet.getBarSpace());
        barBuffer.setDataSet(i);
        barBuffer.setInverted(this.mChart.isInverted(iBarDataSet.getAxisDependency()));
        barBuffer.feed(iBarDataSet);
        transformer.pointValuesToPixel(barBuffer.buffer);
        while (i2 < barBuffer.size()) {
            int i3 = i2 + 3;
            if (this.mViewPortHandler.isInBoundsTop(barBuffer.buffer[i3])) {
                int i4 = i2 + 1;
                if (this.mViewPortHandler.isInBoundsBottom(barBuffer.buffer[i4])) {
                    if (this.mChart.isDrawBarShadowEnabled()) {
                        canvas.drawRect(this.mViewPortHandler.contentLeft(), barBuffer.buffer[i4], this.mViewPortHandler.contentRight(), barBuffer.buffer[i3], this.mShadowPaint);
                    }
                    this.mRenderPaint.setColor(iBarDataSet.getColor(i2 / 4));
                    int i5 = i2 + 2;
                    canvas.drawRect(barBuffer.buffer[i2], barBuffer.buffer[i4], barBuffer.buffer[i5], barBuffer.buffer[i3], this.mRenderPaint);
                    if (z) {
                        canvas.drawRect(barBuffer.buffer[i2], barBuffer.buffer[i4], barBuffer.buffer[i5], barBuffer.buffer[i3], this.mBarBorderPaint);
                    }
                }
                i2 += 4;
            } else {
                return;
            }
        }
    }

    public void drawValues(Canvas canvas) {
        boolean z;
        List list;
        Transformer transformer;
        String str;
        float f;
        BarEntry barEntry;
        float f2;
        float[] fArr;
        Transformer transformer2;
        int i;
        float f3;
        Transformer transformer3;
        float f4;
        boolean z2;
        List list2;
        float[] fArr2;
        int i2;
        float f5;
        float f6;
        if (passesCheck()) {
            List dataSets = this.mChart.getBarData().getDataSets();
            float convertDpToPixel = Utils.convertDpToPixel(5.0f);
            boolean isDrawValueAboveBarEnabled = this.mChart.isDrawValueAboveBarEnabled();
            int i3 = 0;
            while (i3 < this.mChart.getBarData().getDataSetCount()) {
                IBarDataSet iBarDataSet = (IBarDataSet) dataSets.get(i3);
                if (iBarDataSet.isDrawValuesEnabled() && iBarDataSet.getEntryCount() != 0) {
                    boolean isInverted = this.mChart.isInverted(iBarDataSet.getAxisDependency());
                    applyValueTextStyle(iBarDataSet);
                    float calcTextHeight = ((float) Utils.calcTextHeight(this.mValuePaint, AgooConstants.ACK_REMOVE_PACKAGE)) / 2.0f;
                    ValueFormatter valueFormatter = iBarDataSet.getValueFormatter();
                    Transformer transformer4 = this.mChart.getTransformer(iBarDataSet.getAxisDependency());
                    float[] transformedValues = getTransformedValues(transformer4, iBarDataSet, i3);
                    if (!iBarDataSet.isStacked()) {
                        int i4 = 0;
                        while (((float) i4) < ((float) transformedValues.length) * this.mAnimator.getPhaseX()) {
                            int i5 = i4 + 1;
                            if (!this.mViewPortHandler.isInBoundsTop(transformedValues[i5])) {
                                break;
                            }
                            if (this.mViewPortHandler.isInBoundsX(transformedValues[i4]) && this.mViewPortHandler.isInBoundsBottom(transformedValues[i5])) {
                                int i6 = i4 / 2;
                                BarEntry barEntry2 = (BarEntry) iBarDataSet.getEntryForIndex(i6);
                                float val = barEntry2.getVal();
                                String formattedValue = valueFormatter.getFormattedValue(val, barEntry2, i3, this.mViewPortHandler);
                                float calcTextWidth = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue);
                                if (isDrawValueAboveBarEnabled) {
                                    list2 = dataSets;
                                    f5 = convertDpToPixel;
                                } else {
                                    list2 = dataSets;
                                    f5 = -(calcTextWidth + convertDpToPixel);
                                }
                                if (isDrawValueAboveBarEnabled) {
                                    z2 = isDrawValueAboveBarEnabled;
                                    f6 = -(calcTextWidth + convertDpToPixel);
                                } else {
                                    z2 = isDrawValueAboveBarEnabled;
                                    f6 = convertDpToPixel;
                                }
                                if (isInverted) {
                                    f5 = (-f5) - calcTextWidth;
                                    f6 = (-f6) - calcTextWidth;
                                }
                                float f7 = transformedValues[i4];
                                if (val < 0.0f) {
                                    f5 = f6;
                                }
                                float f8 = f7 + f5;
                                fArr2 = transformedValues;
                                float f9 = transformedValues[i5] + calcTextHeight;
                                i2 = i4;
                                drawValue(canvas, formattedValue, f8, f9, iBarDataSet.getValueTextColor(i6));
                            } else {
                                fArr2 = transformedValues;
                                list2 = dataSets;
                                z2 = isDrawValueAboveBarEnabled;
                                i2 = i4;
                            }
                            i4 = i2 + 2;
                            transformedValues = fArr2;
                            dataSets = list2;
                            isDrawValueAboveBarEnabled = z2;
                        }
                    } else {
                        float[] fArr3 = transformedValues;
                        list = dataSets;
                        z = isDrawValueAboveBarEnabled;
                        int i7 = 0;
                        while (((float) i7) < ((float) (fArr3.length - 1)) * this.mAnimator.getPhaseX()) {
                            int i8 = i7 / 2;
                            BarEntry barEntry3 = (BarEntry) iBarDataSet.getEntryForIndex(i8);
                            float[] vals = barEntry3.getVals();
                            if (vals == null) {
                                int i9 = i7 + 1;
                                if (!this.mViewPortHandler.isInBoundsTop(fArr3[i9])) {
                                    break;
                                } else if (this.mViewPortHandler.isInBoundsX(fArr3[i7]) && this.mViewPortHandler.isInBoundsBottom(fArr3[i9])) {
                                    String formattedValue2 = valueFormatter.getFormattedValue(barEntry3.getVal(), barEntry3, i3, this.mViewPortHandler);
                                    float calcTextWidth2 = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue2);
                                    float f10 = z ? convertDpToPixel : -(calcTextWidth2 + convertDpToPixel);
                                    if (z) {
                                        transformer3 = transformer4;
                                        f4 = -(calcTextWidth2 + convertDpToPixel);
                                    } else {
                                        transformer3 = transformer4;
                                        f4 = convertDpToPixel;
                                    }
                                    if (isInverted) {
                                        f10 = (-f10) - calcTextWidth2;
                                        f4 = (-f4) - calcTextWidth2;
                                    }
                                    float f11 = fArr3[i7];
                                    if (barEntry3.getVal() < 0.0f) {
                                        f10 = f4;
                                    }
                                    drawValue(canvas, formattedValue2, f10 + f11, fArr3[i9] + calcTextHeight, iBarDataSet.getValueTextColor(i8));
                                    transformer = transformer3;
                                    i7 += 2;
                                    transformer4 = transformer;
                                }
                            } else {
                                float[] fArr4 = new float[(vals.length * 2)];
                                float f12 = -barEntry3.getNegativeSum();
                                int i10 = i8;
                                int i11 = 0;
                                int i12 = 0;
                                float f13 = 0.0f;
                                while (i11 < fArr4.length) {
                                    float f14 = vals[i12];
                                    if (f14 >= 0.0f) {
                                        f13 += f14;
                                        f3 = f12;
                                        f12 = f13;
                                    } else {
                                        f3 = f12 - f14;
                                    }
                                    fArr4[i11] = f12 * this.mAnimator.getPhaseY();
                                    i11 += 2;
                                    i12++;
                                    f12 = f3;
                                }
                                transformer4.pointValuesToPixel(fArr4);
                                int i13 = 0;
                                while (i13 < fArr4.length) {
                                    float f15 = vals[i13 / 2];
                                    String formattedValue3 = valueFormatter.getFormattedValue(f15, barEntry3, i3, this.mViewPortHandler);
                                    float[] fArr5 = vals;
                                    float calcTextWidth3 = (float) Utils.calcTextWidth(this.mValuePaint, formattedValue3);
                                    if (z) {
                                        str = formattedValue3;
                                        f = convertDpToPixel;
                                    } else {
                                        str = formattedValue3;
                                        f = -(calcTextWidth3 + convertDpToPixel);
                                    }
                                    if (z) {
                                        barEntry = barEntry3;
                                        f2 = -(calcTextWidth3 + convertDpToPixel);
                                    } else {
                                        barEntry = barEntry3;
                                        f2 = convertDpToPixel;
                                    }
                                    if (isInverted) {
                                        f = (-f) - calcTextWidth3;
                                        f2 = (-f2) - calcTextWidth3;
                                    }
                                    float f16 = fArr4[i13];
                                    if (f15 < 0.0f) {
                                        f = f2;
                                    }
                                    float f17 = f16 + f;
                                    float f18 = fArr3[i7 + 1];
                                    if (!this.mViewPortHandler.isInBoundsTop(f18)) {
                                        break;
                                    }
                                    if (this.mViewPortHandler.isInBoundsX(f17) && this.mViewPortHandler.isInBoundsBottom(f18)) {
                                        int i14 = i10;
                                        String str2 = str;
                                        i = i14;
                                        fArr = fArr4;
                                        String str3 = str2;
                                        transformer2 = transformer4;
                                        drawValue(canvas, str3, f17, f18 + calcTextHeight, iBarDataSet.getValueTextColor(i14));
                                    } else {
                                        fArr = fArr4;
                                        transformer2 = transformer4;
                                        i = i10;
                                    }
                                    i13 += 2;
                                    vals = fArr5;
                                    i10 = i;
                                    barEntry3 = barEntry;
                                    transformer4 = transformer2;
                                    fArr4 = fArr;
                                }
                            }
                            transformer = transformer4;
                            i7 += 2;
                            transformer4 = transformer;
                        }
                        i3++;
                        dataSets = list;
                        isDrawValueAboveBarEnabled = z;
                    }
                }
                list = dataSets;
                z = isDrawValueAboveBarEnabled;
                i3++;
                dataSets = list;
                isDrawValueAboveBarEnabled = z;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawValue(Canvas canvas, String str, float f, float f2, int i) {
        this.mValuePaint.setColor(i);
        canvas.drawText(str, f, f2, this.mValuePaint);
    }

    /* access modifiers changed from: protected */
    public void prepareBarHighlight(float f, float f2, float f3, float f4, Transformer transformer) {
        this.mBarRect.set(f2, (f - 0.5f) + f4, f3, (f + 0.5f) - f4);
        transformer.rectValueToPixelHorizontal(this.mBarRect, this.mAnimator.getPhaseY());
    }

    public float[] getTransformedValues(Transformer transformer, IBarDataSet iBarDataSet, int i) {
        return transformer.generateTransformedValuesHorizontalBarChart(iBarDataSet, i, this.mChart.getBarData(), this.mAnimator.getPhaseY());
    }

    /* access modifiers changed from: protected */
    public boolean passesCheck() {
        return ((float) this.mChart.getBarData().getYValCount()) < ((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleY();
    }
}
