package com.github.mikephil.charting.components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public abstract class MarkerView extends RelativeLayout {
    public abstract int getXOffset(float f);

    public abstract int getYOffset(float f);

    public abstract void refreshContent(Entry entry, Highlight highlight);

    public MarkerView(Context context, int i) {
        super(context);
        setupLayoutResource(i);
    }

    private void setupLayoutResource(int i) {
        View inflate = LayoutInflater.from(getContext()).inflate(i, this);
        inflate.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        inflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
    }

    public void draw(Canvas canvas, float f, float f2) {
        float xOffset = f + ((float) getXOffset(f));
        float yOffset = f2 + ((float) getYOffset(f2));
        canvas.translate(xOffset, yOffset);
        draw(canvas);
        canvas.translate(-xOffset, -yOffset);
    }
}
