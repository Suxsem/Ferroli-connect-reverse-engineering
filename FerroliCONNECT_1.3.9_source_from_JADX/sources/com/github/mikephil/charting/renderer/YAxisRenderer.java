package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.p000v4.view.ViewCompat;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.PointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRenderer extends AxisRenderer {
    protected YAxis mYAxis;
    protected Paint mZeroLinePaint = new Paint(1);

    public YAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, transformer);
        this.mYAxis = yAxis;
        this.mAxisLabelPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
        this.mZeroLinePaint.setColor(-7829368);
        this.mZeroLinePaint.setStrokeWidth(1.0f);
        this.mZeroLinePaint.setStyle(Paint.Style.STROKE);
    }

    public void computeAxis(float f, float f2) {
        if (this.mViewPortHandler.contentWidth() > 10.0f && !this.mViewPortHandler.isFullyZoomedOutY()) {
            PointD valuesByTouchPoint = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
            PointD valuesByTouchPoint2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
            if (!this.mYAxis.isInverted()) {
                float f3 = (float) valuesByTouchPoint2.f1491y;
                f2 = (float) valuesByTouchPoint.f1491y;
                f = f3;
            } else {
                f = (float) valuesByTouchPoint.f1491y;
                f2 = (float) valuesByTouchPoint2.f1491y;
            }
        }
        computeAxisValues(f, f2);
    }

    /* access modifiers changed from: protected */
    public void computeAxisValues(float f, float f2) {
        double d;
        double d2;
        int i;
        float f3 = f;
        float f4 = f2;
        int labelCount = this.mYAxis.getLabelCount();
        double abs = (double) Math.abs(f4 - f3);
        if (labelCount == 0 || abs <= 0.0d) {
            YAxis yAxis = this.mYAxis;
            yAxis.mEntries = new float[0];
            yAxis.mEntryCount = 0;
            return;
        }
        double d3 = (double) labelCount;
        Double.isNaN(abs);
        Double.isNaN(d3);
        double roundToNextSignificant = (double) Utils.roundToNextSignificant(abs / d3);
        if (this.mYAxis.isGranularityEnabled() && roundToNextSignificant < ((double) this.mYAxis.getGranularity())) {
            roundToNextSignificant = (double) this.mYAxis.getGranularity();
        }
        double roundToNextSignificant2 = (double) Utils.roundToNextSignificant(Math.pow(10.0d, (double) ((int) Math.log10(roundToNextSignificant))));
        Double.isNaN(roundToNextSignificant2);
        if (((int) (roundToNextSignificant / roundToNextSignificant2)) > 5) {
            Double.isNaN(roundToNextSignificant2);
            roundToNextSignificant = Math.floor(roundToNextSignificant2 * 10.0d);
        }
        if (this.mYAxis.isForceLabelsEnabled()) {
            float f5 = ((float) abs) / ((float) (labelCount - 1));
            YAxis yAxis2 = this.mYAxis;
            yAxis2.mEntryCount = labelCount;
            if (yAxis2.mEntries.length < labelCount) {
                this.mYAxis.mEntries = new float[labelCount];
            }
            float f6 = f3;
            for (int i2 = 0; i2 < labelCount; i2++) {
                this.mYAxis.mEntries[i2] = f6;
                f6 += f5;
            }
        } else if (this.mYAxis.isShowOnlyMinMaxEnabled()) {
            YAxis yAxis3 = this.mYAxis;
            yAxis3.mEntryCount = 2;
            yAxis3.mEntries = new float[2];
            yAxis3.mEntries[0] = f3;
            this.mYAxis.mEntries[1] = f4;
        } else {
            if (roundToNextSignificant == 0.0d) {
                d = 0.0d;
            } else {
                double d4 = (double) f3;
                Double.isNaN(d4);
                d = Math.ceil(d4 / roundToNextSignificant) * roundToNextSignificant;
            }
            if (roundToNextSignificant == 0.0d) {
                d2 = 0.0d;
            } else {
                double d5 = (double) f4;
                Double.isNaN(d5);
                d2 = Utils.nextUp(Math.floor(d5 / roundToNextSignificant) * roundToNextSignificant);
            }
            if (roundToNextSignificant != 0.0d) {
                i = 0;
                for (double d6 = d; d6 <= d2; d6 += roundToNextSignificant) {
                    i++;
                }
            } else {
                i = 0;
            }
            YAxis yAxis4 = this.mYAxis;
            yAxis4.mEntryCount = i;
            if (yAxis4.mEntries.length < i) {
                this.mYAxis.mEntries = new float[i];
            }
            for (int i3 = 0; i3 < i; i3++) {
                if (d == 0.0d) {
                    d = 0.0d;
                }
                this.mYAxis.mEntries[i3] = (float) d;
                d += roundToNextSignificant;
            }
        }
        if (roundToNextSignificant < 1.0d) {
            this.mYAxis.mDecimals = (int) Math.ceil(-Math.log10(roundToNextSignificant));
            return;
        }
        this.mYAxis.mDecimals = 0;
    }

    public void renderAxisLabels(Canvas canvas) {
        float f;
        float f2;
        float f3;
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            float[] fArr = new float[(this.mYAxis.mEntryCount * 2)];
            for (int i = 0; i < fArr.length; i += 2) {
                fArr[i + 1] = this.mYAxis.mEntries[i / 2];
            }
            this.mTrans.pointValuesToPixel(fArr);
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            float xOffset = this.mYAxis.getXOffset();
            float calcTextHeight = (((float) Utils.calcTextHeight(this.mAxisLabelPaint, "A")) / 2.5f) + this.mYAxis.getYOffset();
            YAxis.AxisDependency axisDependency = this.mYAxis.getAxisDependency();
            YAxis.YAxisLabelPosition labelPosition = this.mYAxis.getLabelPosition();
            if (axisDependency == YAxis.AxisDependency.LEFT) {
                if (labelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                    this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                    f2 = this.mViewPortHandler.offsetLeft();
                    f = f2 - xOffset;
                    drawYLabels(canvas, f, fArr, calcTextHeight);
                }
                this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                f3 = this.mViewPortHandler.offsetLeft();
            } else if (labelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
                f3 = this.mViewPortHandler.contentRight();
            } else {
                this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                f2 = this.mViewPortHandler.contentRight();
                f = f2 - xOffset;
                drawYLabels(canvas, f, fArr, calcTextHeight);
            }
            f = f3 + xOffset;
            drawYLabels(canvas, f, fArr, calcTextHeight);
        }
    }

    public void renderAxisLine(Canvas canvas) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawAxisLineEnabled()) {
            this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
            this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
            if (this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
                canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
                return;
            }
            canvas.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawYLabels(Canvas canvas, float f, float[] fArr, float f2) {
        int i = 0;
        while (i < this.mYAxis.mEntryCount) {
            String formattedLabel = this.mYAxis.getFormattedLabel(i);
            if (this.mYAxis.isDrawTopYLabelEntryEnabled() || i < this.mYAxis.mEntryCount - 1) {
                canvas.drawText(formattedLabel, f, fArr[(i * 2) + 1] + f2, this.mAxisLabelPaint);
                i++;
            } else {
                return;
            }
        }
    }

    public void renderGridLines(Canvas canvas) {
        if (this.mYAxis.isEnabled()) {
            float[] fArr = new float[2];
            if (this.mYAxis.isDrawGridLinesEnabled()) {
                this.mGridPaint.setColor(this.mYAxis.getGridColor());
                this.mGridPaint.setStrokeWidth(this.mYAxis.getGridLineWidth());
                this.mGridPaint.setPathEffect(this.mYAxis.getGridDashPathEffect());
                Path path = new Path();
                for (int i = 0; i < this.mYAxis.mEntryCount; i++) {
                    fArr[1] = this.mYAxis.mEntries[i];
                    this.mTrans.pointValuesToPixel(fArr);
                    path.moveTo(this.mViewPortHandler.offsetLeft(), fArr[1]);
                    path.lineTo(this.mViewPortHandler.contentRight(), fArr[1]);
                    canvas.drawPath(path, this.mGridPaint);
                    path.reset();
                }
            }
            if (this.mYAxis.isDrawZeroLineEnabled()) {
                fArr[1] = 0.0f;
                this.mTrans.pointValuesToPixel(fArr);
                drawZeroLine(canvas, this.mViewPortHandler.offsetLeft(), this.mViewPortHandler.contentRight(), fArr[1] - 1.0f, fArr[1] - 1.0f);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void drawZeroLine(Canvas canvas, float f, float f2, float f3, float f4) {
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path path = new Path();
        path.moveTo(f, f3);
        path.lineTo(f2, f4);
        canvas.drawPath(path, this.mZeroLinePaint);
    }

    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null && limitLines.size() > 0) {
            float[] fArr = new float[2];
            Path path = new Path();
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine limitLine = limitLines.get(i);
                if (limitLine.isEnabled()) {
                    this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                    this.mLimitLinePaint.setColor(limitLine.getLineColor());
                    this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                    this.mLimitLinePaint.setPathEffect(limitLine.getDashPathEffect());
                    fArr[1] = limitLine.getLimit();
                    this.mTrans.pointValuesToPixel(fArr);
                    path.moveTo(this.mViewPortHandler.contentLeft(), fArr[1]);
                    path.lineTo(this.mViewPortHandler.contentRight(), fArr[1]);
                    canvas.drawPath(path, this.mLimitLinePaint);
                    path.reset();
                    String label = limitLine.getLabel();
                    if (label != null && !label.equals("")) {
                        this.mLimitLinePaint.setStyle(limitLine.getTextStyle());
                        this.mLimitLinePaint.setPathEffect((PathEffect) null);
                        this.mLimitLinePaint.setColor(limitLine.getTextColor());
                        this.mLimitLinePaint.setTypeface(limitLine.getTypeface());
                        this.mLimitLinePaint.setStrokeWidth(0.5f);
                        this.mLimitLinePaint.setTextSize(limitLine.getTextSize());
                        float calcTextHeight = (float) Utils.calcTextHeight(this.mLimitLinePaint, label);
                        float convertDpToPixel = Utils.convertDpToPixel(4.0f) + limitLine.getXOffset();
                        float lineWidth = limitLine.getLineWidth() + calcTextHeight + limitLine.getYOffset();
                        LimitLine.LimitLabelPosition labelPosition = limitLine.getLabelPosition();
                        if (labelPosition == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(label, this.mViewPortHandler.contentRight() - convertDpToPixel, (fArr[1] - lineWidth) + calcTextHeight, this.mLimitLinePaint);
                        } else if (labelPosition == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                            canvas.drawText(label, this.mViewPortHandler.contentRight() - convertDpToPixel, fArr[1] + lineWidth, this.mLimitLinePaint);
                        } else if (labelPosition == LimitLine.LimitLabelPosition.LEFT_TOP) {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(label, this.mViewPortHandler.contentLeft() + convertDpToPixel, (fArr[1] - lineWidth) + calcTextHeight, this.mLimitLinePaint);
                        } else {
                            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                            canvas.drawText(label, this.mViewPortHandler.offsetLeft() + convertDpToPixel, fArr[1] + lineWidth, this.mLimitLinePaint);
                        }
                    }
                }
            }
        }
    }
}
