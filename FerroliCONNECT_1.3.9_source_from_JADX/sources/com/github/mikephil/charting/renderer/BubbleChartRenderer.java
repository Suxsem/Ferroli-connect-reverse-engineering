package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class BubbleChartRenderer extends DataRenderer {
    private float[] _hsvBuffer = new float[3];
    protected BubbleDataProvider mChart;
    private float[] pointBuffer = new float[2];
    private float[] sizeBuffer = new float[4];

    public void drawExtras(Canvas canvas) {
    }

    public void initBuffers() {
    }

    public BubbleChartRenderer(BubbleDataProvider bubbleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = bubbleDataProvider;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    public void drawData(Canvas canvas) {
        for (IBubbleDataSet iBubbleDataSet : this.mChart.getBubbleData().getDataSets()) {
            if (iBubbleDataSet.isVisible() && iBubbleDataSet.getEntryCount() > 0) {
                drawDataSet(canvas, iBubbleDataSet);
            }
        }
    }

    /* access modifiers changed from: protected */
    public float getShapeSize(float f, float f2, float f3, boolean z) {
        if (z) {
            f = f2 == 0.0f ? 1.0f : (float) Math.sqrt((double) (f / f2));
        }
        return f3 * f;
    }

    /* access modifiers changed from: protected */
    public void drawDataSet(Canvas canvas, IBubbleDataSet iBubbleDataSet) {
        IBubbleDataSet iBubbleDataSet2 = iBubbleDataSet;
        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        char c = 0;
        int max2 = Math.max(iBubbleDataSet2.getEntryIndex((BubbleEntry) iBubbleDataSet2.getEntryForXIndex(this.mMinX)), 0);
        int min = Math.min(iBubbleDataSet2.getEntryIndex((BubbleEntry) iBubbleDataSet2.getEntryForXIndex(this.mMaxX)) + 1, iBubbleDataSet.getEntryCount());
        float[] fArr = this.sizeBuffer;
        fArr[0] = 0.0f;
        fArr[2] = 1.0f;
        transformer.pointValuesToPixel(fArr);
        boolean isNormalizeSizeEnabled = iBubbleDataSet.isNormalizeSizeEnabled();
        float[] fArr2 = this.sizeBuffer;
        float min2 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(fArr2[2] - fArr2[0]));
        int i = max2;
        while (i < min) {
            BubbleEntry bubbleEntry = (BubbleEntry) iBubbleDataSet2.getEntryForIndex(i);
            this.pointBuffer[c] = (((float) (bubbleEntry.getXIndex() - max2)) * max) + ((float) max2);
            this.pointBuffer[1] = bubbleEntry.getVal() * phaseY;
            transformer.pointValuesToPixel(this.pointBuffer);
            float shapeSize = getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), min2, isNormalizeSizeEnabled) / 2.0f;
            if (!this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeSize) || !this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeSize) || !this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[c] + shapeSize)) {
                Canvas canvas2 = canvas;
            } else if (this.mViewPortHandler.isInBoundsRight(this.pointBuffer[c] - shapeSize)) {
                this.mRenderPaint.setColor(iBubbleDataSet2.getColor(bubbleEntry.getXIndex()));
                float[] fArr3 = this.pointBuffer;
                canvas.drawCircle(fArr3[c], fArr3[1], shapeSize, this.mRenderPaint);
            } else {
                return;
            }
            i++;
            c = 0;
        }
    }

    public void drawValues(Canvas canvas) {
        float[] fArr;
        int i;
        BubbleData bubbleData = this.mChart.getBubbleData();
        if (bubbleData != null && bubbleData.getYValCount() < ((int) Math.ceil((double) (((float) this.mChart.getMaxVisibleCount()) * this.mViewPortHandler.getScaleX())))) {
            List dataSets = bubbleData.getDataSets();
            float calcTextHeight = (float) Utils.calcTextHeight(this.mValuePaint, "1");
            for (int i2 = 0; i2 < dataSets.size(); i2++) {
                IBubbleDataSet iBubbleDataSet = (IBubbleDataSet) dataSets.get(i2);
                if (iBubbleDataSet.isDrawValuesEnabled() && iBubbleDataSet.getEntryCount() != 0) {
                    applyValueTextStyle(iBubbleDataSet);
                    float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                    float phaseY = this.mAnimator.getPhaseY();
                    int entryIndex = iBubbleDataSet.getEntryIndex((BubbleEntry) iBubbleDataSet.getEntryForXIndex(this.mMinX));
                    float[] generateTransformedValuesBubble = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency()).generateTransformedValuesBubble(iBubbleDataSet, max, phaseY, entryIndex, Math.min(iBubbleDataSet.getEntryIndex((BubbleEntry) iBubbleDataSet.getEntryForXIndex(this.mMaxX)) + 1, iBubbleDataSet.getEntryCount()));
                    float f = max == 1.0f ? phaseY : max;
                    int i3 = 0;
                    while (i3 < generateTransformedValuesBubble.length) {
                        int i4 = (i3 / 2) + entryIndex;
                        int valueTextColor = iBubbleDataSet.getValueTextColor(i4);
                        int argb = Color.argb(Math.round(255.0f * f), Color.red(valueTextColor), Color.green(valueTextColor), Color.blue(valueTextColor));
                        float f2 = generateTransformedValuesBubble[i3];
                        float f3 = generateTransformedValuesBubble[i3 + 1];
                        if (!this.mViewPortHandler.isInBoundsRight(f2)) {
                            break;
                        }
                        if (!this.mViewPortHandler.isInBoundsLeft(f2) || !this.mViewPortHandler.isInBoundsY(f3)) {
                            fArr = generateTransformedValuesBubble;
                            i = i3;
                        } else {
                            BubbleEntry bubbleEntry = (BubbleEntry) iBubbleDataSet.getEntryForIndex(i4);
                            fArr = generateTransformedValuesBubble;
                            float f4 = f2;
                            float f5 = f3 + (0.5f * calcTextHeight);
                            i = i3;
                            drawValue(canvas, iBubbleDataSet.getValueFormatter(), bubbleEntry.getSize(), bubbleEntry, i2, f4, f5, argb);
                        }
                        i3 = i + 2;
                        generateTransformedValuesBubble = fArr;
                    }
                }
            }
        }
    }

    public void drawHighlighted(Canvas canvas, Highlight[] highlightArr) {
        int i;
        int i2;
        float f;
        int i3;
        BubbleData bubbleData;
        BubbleEntry bubbleEntry;
        Highlight[] highlightArr2 = highlightArr;
        BubbleData bubbleData2 = this.mChart.getBubbleData();
        float max = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
        float phaseY = this.mAnimator.getPhaseY();
        int length = highlightArr2.length;
        int i4 = 0;
        while (i4 < length) {
            Highlight highlight = highlightArr2[i4];
            if (highlight.getDataSetIndex() == -1) {
                i = 0;
            } else {
                i = highlight.getDataSetIndex();
            }
            int i5 = 1;
            if (highlight.getDataSetIndex() == -1) {
                i2 = bubbleData2.getDataSetCount();
            } else {
                i2 = highlight.getDataSetIndex() + 1;
            }
            if (i2 - i >= 1) {
                while (true) {
                    if (i >= i2) {
                        break;
                    }
                    IBubbleDataSet iBubbleDataSet = (IBubbleDataSet) bubbleData2.getDataSetByIndex(i);
                    if (iBubbleDataSet == null || !iBubbleDataSet.isHighlightEnabled() || (bubbleEntry = (BubbleEntry) bubbleData2.getEntryForHighlight(highlight)) == null || bubbleEntry.getXIndex() != highlight.getXIndex()) {
                        Canvas canvas2 = canvas;
                        bubbleData = bubbleData2;
                        f = max;
                        i3 = length;
                    } else {
                        int entryIndex = iBubbleDataSet.getEntryIndex((BubbleEntry) iBubbleDataSet.getEntryForXIndex(this.mMinX));
                        int min = Math.min(iBubbleDataSet.getEntryIndex((BubbleEntry) iBubbleDataSet.getEntryForXIndex(this.mMaxX)) + i5, iBubbleDataSet.getEntryCount());
                        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
                        float[] fArr = this.sizeBuffer;
                        fArr[0] = 0.0f;
                        fArr[2] = 1.0f;
                        transformer.pointValuesToPixel(fArr);
                        boolean isNormalizeSizeEnabled = iBubbleDataSet.isNormalizeSizeEnabled();
                        float[] fArr2 = this.sizeBuffer;
                        bubbleData = bubbleData2;
                        i3 = length;
                        float min2 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), Math.abs(fArr2[2] - fArr2[0]));
                        f = max;
                        this.pointBuffer[0] = (((float) (bubbleEntry.getXIndex() - entryIndex)) * max) + ((float) entryIndex);
                        this.pointBuffer[1] = bubbleEntry.getVal() * phaseY;
                        transformer.pointValuesToPixel(this.pointBuffer);
                        float shapeSize = getShapeSize(bubbleEntry.getSize(), iBubbleDataSet.getMaxSize(), min2, isNormalizeSizeEnabled) / 2.0f;
                        if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + shapeSize) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - shapeSize) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + shapeSize)) {
                            if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - shapeSize)) {
                                Canvas canvas3 = canvas;
                                break;
                            } else if (highlight.getXIndex() >= entryIndex && highlight.getXIndex() < min) {
                                int color = iBubbleDataSet.getColor(bubbleEntry.getXIndex());
                                Color.RGBToHSV(Color.red(color), Color.green(color), Color.blue(color), this._hsvBuffer);
                                float[] fArr3 = this._hsvBuffer;
                                fArr3[2] = fArr3[2] * 0.5f;
                                this.mHighlightPaint.setColor(Color.HSVToColor(Color.alpha(color), this._hsvBuffer));
                                this.mHighlightPaint.setStrokeWidth(iBubbleDataSet.getHighlightCircleWidth());
                                float[] fArr4 = this.pointBuffer;
                                canvas.drawCircle(fArr4[0], fArr4[1], shapeSize, this.mHighlightPaint);
                            }
                        }
                        Canvas canvas4 = canvas;
                    }
                    i++;
                    Highlight[] highlightArr3 = highlightArr;
                    bubbleData2 = bubbleData;
                    length = i3;
                    max = f;
                    i5 = 1;
                }
            }
            Canvas canvas5 = canvas;
            bubbleData = bubbleData2;
            f = max;
            i3 = length;
            i4++;
            highlightArr2 = highlightArr;
            bubbleData2 = bubbleData;
            length = i3;
            max = f;
        }
    }
}
