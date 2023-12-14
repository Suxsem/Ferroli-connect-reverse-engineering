package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.p000v4.content.ContextCompat;
import android.util.AttributeSet;
import com.github.mikephil.charting.charts.LineChart;
import com.szacs.ferroliconnect.C1683R;
import java.util.ArrayList;
import java.util.List;

public class MyHistoryChart extends LineChart {
    private List<ChangePoint> changePoints;
    private Paint mOnStatusPaint;

    public class ChangePoint {
        boolean isOn;
        float xPercent;

        public ChangePoint(float f, boolean z) {
            this.xPercent = f;
            this.isOn = z;
        }
    }

    public MyHistoryChart(Context context) {
        super(context);
    }

    public MyHistoryChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyHistoryChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public void init() {
        super.init();
        this.changePoints = new ArrayList();
        this.mOnStatusPaint = new Paint();
        this.mOnStatusPaint.setColor(ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_yellow));
    }

    public List<ChangePoint> getChangePoints() {
        return this.changePoints;
    }

    public void setChangePoints(List<ChangePoint> list) {
        this.changePoints = list;
    }

    /* access modifiers changed from: protected */
    public void drawGridBackground(Canvas canvas) {
        super.drawGridBackground(canvas);
        float contentWidth = this.mViewPortHandler.contentWidth();
        float contentLeft = this.mViewPortHandler.contentLeft();
        for (int i = 0; i < this.changePoints.size(); i += 2) {
            int i2 = i + 1;
            canvas.drawRect(new RectF((this.changePoints.get(i).xPercent * contentWidth) + contentLeft, this.mViewPortHandler.contentTop(), i2 < this.changePoints.size() ? (this.changePoints.get(i2).xPercent * contentWidth) + contentLeft : contentWidth + contentLeft, this.mViewPortHandler.contentBottom()), this.mOnStatusPaint);
        }
    }
}
