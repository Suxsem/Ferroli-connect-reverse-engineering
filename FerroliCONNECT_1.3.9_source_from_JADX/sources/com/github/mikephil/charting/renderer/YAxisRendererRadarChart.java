package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererRadarChart extends YAxisRenderer {
    private RadarChart mChart;

    public YAxisRendererRadarChart(ViewPortHandler viewPortHandler, YAxis yAxis, RadarChart radarChart) {
        super(viewPortHandler, yAxis, (Transformer) null);
        this.mChart = radarChart;
    }

    public void computeAxis(float f, float f2) {
        computeAxisValues(f, f2);
    }

    /* access modifiers changed from: protected */
    public void computeAxisValues(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        int labelCount = this.mYAxis.getLabelCount();
        double abs = (double) Math.abs(f4 - f3);
        if (labelCount == 0 || abs <= 0.0d) {
            this.mYAxis.mEntries = new float[0];
            this.mYAxis.mEntryCount = 0;
            return;
        }
        double d = (double) labelCount;
        Double.isNaN(abs);
        Double.isNaN(d);
        double roundToNextSignificant = (double) Utils.roundToNextSignificant(abs / d);
        double pow = Math.pow(10.0d, (double) ((int) Math.log10(roundToNextSignificant)));
        Double.isNaN(roundToNextSignificant);
        if (((int) (roundToNextSignificant / pow)) > 5) {
            roundToNextSignificant = Math.floor(pow * 10.0d);
        }
        if (this.mYAxis.isForceLabelsEnabled()) {
            float f5 = ((float) abs) / ((float) (labelCount - 1));
            this.mYAxis.mEntryCount = labelCount;
            if (this.mYAxis.mEntries.length < labelCount) {
                this.mYAxis.mEntries = new float[labelCount];
            }
            float f6 = f3;
            for (int i = 0; i < labelCount; i++) {
                this.mYAxis.mEntries[i] = f6;
                f6 += f5;
            }
        } else if (this.mYAxis.isShowOnlyMinMaxEnabled()) {
            this.mYAxis.mEntryCount = 2;
            this.mYAxis.mEntries = new float[2];
            this.mYAxis.mEntries[0] = f3;
            this.mYAxis.mEntries[1] = f4;
        } else {
            double d2 = (double) f3;
            Double.isNaN(d2);
            double d3 = d2 / roundToNextSignificant;
            double floor = (d3 < 0.0d ? Math.floor(d3) : Math.ceil(d3)) * roundToNextSignificant;
            if (floor == 0.0d) {
                floor = 0.0d;
            }
            double d4 = (double) f4;
            Double.isNaN(d4);
            int i2 = 0;
            for (double d5 = floor; d5 <= Utils.nextUp(Math.floor(d4 / roundToNextSignificant) * roundToNextSignificant); d5 += roundToNextSignificant) {
                i2++;
            }
            if (!this.mYAxis.isAxisMaxCustom()) {
                i2++;
            }
            this.mYAxis.mEntryCount = i2;
            if (this.mYAxis.mEntries.length < i2) {
                this.mYAxis.mEntries = new float[i2];
            }
            double d6 = floor;
            for (int i3 = 0; i3 < i2; i3++) {
                this.mYAxis.mEntries[i3] = (float) d6;
                d6 += roundToNextSignificant;
            }
        }
        if (roundToNextSignificant < 1.0d) {
            this.mYAxis.mDecimals = (int) Math.ceil(-Math.log10(roundToNextSignificant));
        } else {
            this.mYAxis.mDecimals = 0;
        }
        if (this.mYAxis.mEntries[0] < f3) {
            this.mYAxis.mAxisMinimum = this.mYAxis.mEntries[0];
        }
        this.mYAxis.mAxisMaximum = this.mYAxis.mEntries[this.mYAxis.mEntryCount - 1];
        this.mYAxis.mAxisRange = Math.abs(this.mYAxis.mAxisMaximum - this.mYAxis.mAxisMinimum);
    }

    public void renderAxisLabels(Canvas canvas) {
        if (this.mYAxis.isEnabled() && this.mYAxis.isDrawLabelsEnabled()) {
            this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
            this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
            this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
            PointF centerOffsets = this.mChart.getCenterOffsets();
            float factor = this.mChart.getFactor();
            int i = this.mYAxis.mEntryCount;
            int i2 = 0;
            while (i2 < i) {
                if (i2 != i - 1 || this.mYAxis.isDrawTopYLabelEntryEnabled()) {
                    PointF position = Utils.getPosition(centerOffsets, (this.mYAxis.mEntries[i2] - this.mYAxis.mAxisMinimum) * factor, this.mChart.getRotationAngle());
                    canvas.drawText(this.mYAxis.getFormattedLabel(i2), position.x + 10.0f, position.y, this.mAxisLabelPaint);
                    i2++;
                } else {
                    return;
                }
            }
        }
    }

    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> limitLines = this.mYAxis.getLimitLines();
        if (limitLines != null) {
            float sliceAngle = this.mChart.getSliceAngle();
            float factor = this.mChart.getFactor();
            PointF centerOffsets = this.mChart.getCenterOffsets();
            for (int i = 0; i < limitLines.size(); i++) {
                LimitLine limitLine = limitLines.get(i);
                if (limitLine.isEnabled()) {
                    this.mLimitLinePaint.setColor(limitLine.getLineColor());
                    this.mLimitLinePaint.setPathEffect(limitLine.getDashPathEffect());
                    this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
                    float limit = (limitLine.getLimit() - this.mChart.getYChartMin()) * factor;
                    Path path = new Path();
                    for (int i2 = 0; i2 < ((RadarData) this.mChart.getData()).getXValCount(); i2++) {
                        PointF position = Utils.getPosition(centerOffsets, limit, (((float) i2) * sliceAngle) + this.mChart.getRotationAngle());
                        if (i2 == 0) {
                            path.moveTo(position.x, position.y);
                        } else {
                            path.lineTo(position.x, position.y);
                        }
                    }
                    path.close();
                    canvas.drawPath(path, this.mLimitLinePaint);
                }
            }
        }
    }
}
