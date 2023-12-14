package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import java.util.List;

public class Transformer {
    private Matrix mMBuffer1 = new Matrix();
    private Matrix mMBuffer2 = new Matrix();
    protected Matrix mMatrixOffset = new Matrix();
    protected Matrix mMatrixValueToPx = new Matrix();
    protected ViewPortHandler mViewPortHandler;

    public Transformer(ViewPortHandler viewPortHandler) {
        this.mViewPortHandler = viewPortHandler;
    }

    public void prepareMatrixValuePx(float f, float f2, float f3, float f4) {
        float contentWidth = this.mViewPortHandler.contentWidth() / f2;
        float contentHeight = this.mViewPortHandler.contentHeight() / f3;
        if (Float.isInfinite(contentWidth)) {
            contentWidth = 0.0f;
        }
        if (Float.isInfinite(contentHeight)) {
            contentHeight = 0.0f;
        }
        this.mMatrixValueToPx.reset();
        this.mMatrixValueToPx.postTranslate(-f, -f4);
        this.mMatrixValueToPx.postScale(contentWidth, -contentHeight);
    }

    public void prepareMatrixOffset(boolean z) {
        this.mMatrixOffset.reset();
        if (!z) {
            this.mMatrixOffset.postTranslate(this.mViewPortHandler.offsetLeft(), this.mViewPortHandler.getChartHeight() - this.mViewPortHandler.offsetBottom());
            return;
        }
        this.mMatrixOffset.setTranslate(this.mViewPortHandler.offsetLeft(), -this.mViewPortHandler.offsetTop());
        this.mMatrixOffset.postScale(1.0f, -1.0f);
    }

    public float[] generateTransformedValuesScatter(IScatterDataSet iScatterDataSet, float f) {
        float[] fArr = new float[(iScatterDataSet.getEntryCount() * 2)];
        for (int i = 0; i < fArr.length; i += 2) {
            Entry entryForIndex = iScatterDataSet.getEntryForIndex(i / 2);
            if (entryForIndex != null) {
                fArr[i] = (float) entryForIndex.getXIndex();
                fArr[i + 1] = entryForIndex.getVal() * f;
            }
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public float[] generateTransformedValuesBubble(IBubbleDataSet iBubbleDataSet, float f, float f2, int i, int i2) {
        int ceil = ((int) Math.ceil((double) (i2 - i))) * 2;
        float[] fArr = new float[ceil];
        for (int i3 = 0; i3 < ceil; i3 += 2) {
            Entry entryForIndex = iBubbleDataSet.getEntryForIndex((i3 / 2) + i);
            if (entryForIndex != null) {
                fArr[i3] = (((float) (entryForIndex.getXIndex() - i)) * f) + ((float) i);
                fArr[i3 + 1] = entryForIndex.getVal() * f2;
            }
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public float[] generateTransformedValuesLine(ILineDataSet iLineDataSet, float f, float f2, int i, int i2) {
        int ceil = ((int) Math.ceil((double) (((float) (i2 - i)) * f))) * 2;
        float[] fArr = new float[ceil];
        for (int i3 = 0; i3 < ceil; i3 += 2) {
            Entry entryForIndex = iLineDataSet.getEntryForIndex((i3 / 2) + i);
            if (entryForIndex != null) {
                fArr[i3] = (float) entryForIndex.getXIndex();
                fArr[i3 + 1] = entryForIndex.getVal() * f2;
            }
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public float[] generateTransformedValuesCandle(ICandleDataSet iCandleDataSet, float f, float f2, int i, int i2) {
        int ceil = ((int) Math.ceil((double) (((float) (i2 - i)) * f))) * 2;
        float[] fArr = new float[ceil];
        for (int i3 = 0; i3 < ceil; i3 += 2) {
            CandleEntry candleEntry = (CandleEntry) iCandleDataSet.getEntryForIndex((i3 / 2) + i);
            if (candleEntry != null) {
                fArr[i3] = (float) candleEntry.getXIndex();
                fArr[i3 + 1] = candleEntry.getHigh() * f2;
            }
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public float[] generateTransformedValuesBarChart(IBarDataSet iBarDataSet, int i, BarData barData, float f) {
        float[] fArr = new float[(iBarDataSet.getEntryCount() * 2)];
        int dataSetCount = barData.getDataSetCount();
        float groupSpace = barData.getGroupSpace();
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            Entry entryForIndex = iBarDataSet.getEntryForIndex(i2 / 2);
            int xIndex = entryForIndex.getXIndex();
            float val = entryForIndex.getVal();
            fArr[i2] = ((float) (entryForIndex.getXIndex() + ((dataSetCount - 1) * xIndex) + i)) + (((float) xIndex) * groupSpace) + (groupSpace / 2.0f);
            fArr[i2 + 1] = val * f;
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public float[] generateTransformedValuesHorizontalBarChart(IBarDataSet iBarDataSet, int i, BarData barData, float f) {
        float[] fArr = new float[(iBarDataSet.getEntryCount() * 2)];
        int dataSetCount = barData.getDataSetCount();
        float groupSpace = barData.getGroupSpace();
        for (int i2 = 0; i2 < fArr.length; i2 += 2) {
            Entry entryForIndex = iBarDataSet.getEntryForIndex(i2 / 2);
            int xIndex = entryForIndex.getXIndex();
            fArr[i2] = entryForIndex.getVal() * f;
            fArr[i2 + 1] = ((float) (((dataSetCount - 1) * xIndex) + xIndex + i)) + (((float) xIndex) * groupSpace) + (groupSpace / 2.0f);
        }
        getValueToPixelMatrix().mapPoints(fArr);
        return fArr;
    }

    public void pathValueToPixel(Path path) {
        path.transform(this.mMatrixValueToPx);
        path.transform(this.mViewPortHandler.getMatrixTouch());
        path.transform(this.mMatrixOffset);
    }

    public void pathValuesToPixel(List<Path> list) {
        for (int i = 0; i < list.size(); i++) {
            pathValueToPixel(list.get(i));
        }
    }

    public void pointValuesToPixel(float[] fArr) {
        this.mMatrixValueToPx.mapPoints(fArr);
        this.mViewPortHandler.getMatrixTouch().mapPoints(fArr);
        this.mMatrixOffset.mapPoints(fArr);
    }

    public void rectValueToPixel(RectF rectF) {
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectValueToPixel(RectF rectF, float f) {
        rectF.top *= f;
        rectF.bottom *= f;
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectValueToPixelHorizontal(RectF rectF) {
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectValueToPixelHorizontal(RectF rectF, float f) {
        rectF.left *= f;
        rectF.right *= f;
        this.mMatrixValueToPx.mapRect(rectF);
        this.mViewPortHandler.getMatrixTouch().mapRect(rectF);
        this.mMatrixOffset.mapRect(rectF);
    }

    public void rectValuesToPixel(List<RectF> list) {
        Matrix valueToPixelMatrix = getValueToPixelMatrix();
        for (int i = 0; i < list.size(); i++) {
            valueToPixelMatrix.mapRect(list.get(i));
        }
    }

    public void pixelsToValue(float[] fArr) {
        Matrix matrix = new Matrix();
        this.mMatrixOffset.invert(matrix);
        matrix.mapPoints(fArr);
        this.mViewPortHandler.getMatrixTouch().invert(matrix);
        matrix.mapPoints(fArr);
        this.mMatrixValueToPx.invert(matrix);
        matrix.mapPoints(fArr);
    }

    public PointD getValuesByTouchPoint(float f, float f2) {
        float[] fArr = {f, f2};
        pixelsToValue(fArr);
        return new PointD((double) fArr[0], (double) fArr[1]);
    }

    public Matrix getValueMatrix() {
        return this.mMatrixValueToPx;
    }

    public Matrix getOffsetMatrix() {
        return this.mMatrixOffset;
    }

    public Matrix getValueToPixelMatrix() {
        this.mMBuffer1.set(this.mMatrixValueToPx);
        this.mMBuffer1.postConcat(this.mViewPortHandler.mMatrixTouch);
        this.mMBuffer1.postConcat(this.mMatrixOffset);
        return this.mMBuffer1;
    }

    public Matrix getPixelToValueMatrix() {
        getValueToPixelMatrix().invert(this.mMBuffer2);
        return this.mMBuffer2;
    }
}
