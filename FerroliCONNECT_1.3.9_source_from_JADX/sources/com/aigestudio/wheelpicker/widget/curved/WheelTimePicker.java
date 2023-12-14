package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.p000v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.aigestudio.wheelpicker.C0662R;
import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.core.IWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCrossPicker;
import com.aigestudio.wheelpicker.widget.IDigital;
import java.util.List;

public class WheelTimePicker extends LinearLayout implements IWheelPicker, IDigital {
    protected String hour;
    protected int labelColor = ViewCompat.MEASURED_STATE_MASK;
    protected float labelTextSize;
    protected AbstractWheelPicker.OnWheelChangeListener listener;
    protected String minute;
    protected WheelHourPicker pickerHour;
    protected WheelMinutePicker pickerMinute;
    protected int stateHour;
    protected int stateMinute;

    public WheelTimePicker(Context context) {
        super(context);
        init();
    }

    public WheelTimePicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setGravity(17);
        setOrientation(0);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C0662R.dimen.WheelPadding);
        int i = dimensionPixelSize * 2;
        this.labelTextSize = (float) dimensionPixelSize;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        this.pickerHour = new WheelHourPicker(getContext());
        this.pickerMinute = new WheelMinutePicker(getContext());
        this.pickerHour.setPadding(0, dimensionPixelSize, i, dimensionPixelSize);
        this.pickerMinute.setPadding(0, dimensionPixelSize, i, dimensionPixelSize);
        addLabel(this.pickerHour, "时");
        addLabel(this.pickerMinute, "分");
        addView(this.pickerHour, layoutParams);
        addView(this.pickerMinute, layoutParams);
        initListener(this.pickerHour, 0);
        initListener(this.pickerMinute, 1);
    }

    private void addLabel(WheelCrossPicker wheelCrossPicker, final String str) {
        wheelCrossPicker.setWheelDecor(true, new AbstractWheelDecor() {
            public void drawDecor(Canvas canvas, Rect rect, Rect rect2, Paint paint) {
                paint.setColor(WheelTimePicker.this.labelColor);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(WheelTimePicker.this.labelTextSize * 1.5f);
                canvas.drawText(str, (float) rect2.centerX(), ((float) rect2.centerY()) - ((paint.ascent() + paint.descent()) / 2.0f), paint);
            }
        });
    }

    private void initListener(WheelCrossPicker wheelCrossPicker, final int i) {
        wheelCrossPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            public void onWheelScrolling(float f, float f2) {
                if (WheelTimePicker.this.listener != null) {
                    WheelTimePicker.this.listener.onWheelScrolling(f, f2);
                }
            }

            public void onWheelSelected(int i, String str) {
                if (i == 0) {
                    WheelTimePicker.this.hour = str;
                }
                if (i == 1) {
                    WheelTimePicker.this.minute = str;
                }
                if (WheelTimePicker.this.isValidDate() && WheelTimePicker.this.listener != null) {
                    AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener = WheelTimePicker.this.listener;
                    onWheelChangeListener.onWheelSelected(-1, WheelTimePicker.this.hour + ":" + WheelTimePicker.this.minute);
                }
            }

            public void onWheelScrollStateChanged(int i) {
                if (i == 0) {
                    WheelTimePicker.this.stateHour = i;
                }
                if (i == 1) {
                    WheelTimePicker.this.stateMinute = i;
                }
                if (WheelTimePicker.this.listener != null) {
                    WheelTimePicker wheelTimePicker = WheelTimePicker.this;
                    wheelTimePicker.checkState(wheelTimePicker.listener);
                }
            }
        });
    }

    public void setLabelColor(int i) {
        this.labelColor = i;
        invalidate();
    }

    public void setLabelTextSize(float f) {
        this.labelTextSize = f;
        invalidate();
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentTime(int i, int i2) {
        this.pickerHour.setCurrentHour(i);
        this.pickerMinute.setCurrentMinute(i2);
    }

    public void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener) {
        this.listener = onWheelChangeListener;
    }

    /* access modifiers changed from: private */
    public void checkState(AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener) {
        if (this.stateHour == 0 && this.stateMinute == 0) {
            onWheelChangeListener.onWheelScrollStateChanged(0);
        }
        if (this.stateHour == 2 || this.stateMinute == 2) {
            onWheelChangeListener.onWheelScrollStateChanged(2);
        }
        if (this.stateHour + this.stateMinute == 1) {
            onWheelChangeListener.onWheelScrollStateChanged(1);
        }
    }

    /* access modifiers changed from: private */
    public boolean isValidDate() {
        return !TextUtils.isEmpty(this.hour) && !TextUtils.isEmpty(this.minute);
    }

    public void setItemIndex(int i) {
        this.pickerHour.setItemIndex(i);
        this.pickerMinute.setItemIndex(i);
    }

    public void setItemSpace(int i) {
        this.pickerHour.setItemSpace(i);
        this.pickerMinute.setItemSpace(i);
    }

    public void setItemCount(int i) {
        this.pickerHour.setItemCount(i);
        this.pickerMinute.setItemCount(i);
    }

    public void setTextColor(int i) {
        this.pickerHour.setTextColor(i);
        this.pickerMinute.setTextColor(i);
    }

    public void setTextSize(int i) {
        this.pickerHour.setTextSize(i);
        this.pickerMinute.setTextSize(i);
    }

    public void clearCache() {
        this.pickerHour.clearCache();
        this.pickerMinute.clearCache();
    }

    public void setCurrentTextColor(int i) {
        this.pickerHour.setCurrentTextColor(i);
        this.pickerMinute.setCurrentTextColor(i);
    }

    public void setWheelDecor(boolean z, AbstractWheelDecor abstractWheelDecor) {
        this.pickerHour.setWheelDecor(z, abstractWheelDecor);
        this.pickerMinute.setWheelDecor(z, abstractWheelDecor);
    }

    public void setDigitType(int i) {
        this.pickerHour.setDigitType(i);
        this.pickerMinute.setDigitType(i);
    }
}
