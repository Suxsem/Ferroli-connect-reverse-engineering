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
import java.util.List;

public class WheelDatePicker extends LinearLayout implements IWheelPicker {
    protected String day;
    protected int labelColor = ViewCompat.MEASURED_STATE_MASK;
    protected float labelTextSize;
    protected AbstractWheelPicker.OnWheelChangeListener listener;
    protected String month;
    protected WheelDayPicker pickerDay;
    protected WheelMonthPicker pickerMonth;
    protected WheelYearPicker pickerYear;
    protected int stateDay;
    protected int stateMonth;
    protected int stateYear;
    protected String year;

    public WheelDatePicker(Context context) {
        super(context);
        init();
    }

    public WheelDatePicker(Context context, AttributeSet attributeSet) {
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
        this.pickerYear = new WheelYearPicker(getContext());
        this.pickerMonth = new WheelMonthPicker(getContext());
        this.pickerDay = new WheelDayPicker(getContext());
        this.pickerYear.setPadding(0, dimensionPixelSize, i, dimensionPixelSize);
        this.pickerMonth.setPadding(0, dimensionPixelSize, i, dimensionPixelSize);
        this.pickerDay.setPadding(0, dimensionPixelSize, i, dimensionPixelSize);
        addLabel(this.pickerYear, "年");
        addLabel(this.pickerMonth, "月");
        addLabel(this.pickerDay, "日");
        addView(this.pickerYear, layoutParams);
        addView(this.pickerMonth, layoutParams);
        addView(this.pickerDay, layoutParams);
        initListener(this.pickerYear, 0);
        initListener(this.pickerMonth, 1);
        initListener(this.pickerDay, 2);
    }

    private void addLabel(WheelCrossPicker wheelCrossPicker, final String str) {
        wheelCrossPicker.setWheelDecor(true, new AbstractWheelDecor() {
            public void drawDecor(Canvas canvas, Rect rect, Rect rect2, Paint paint) {
                paint.setColor(WheelDatePicker.this.labelColor);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(WheelDatePicker.this.labelTextSize * 1.5f);
                canvas.drawText(str, (float) rect2.centerX(), ((float) rect2.centerY()) - ((paint.ascent() + paint.descent()) / 2.0f), paint);
            }
        });
    }

    private void initListener(WheelCrossPicker wheelCrossPicker, final int i) {
        wheelCrossPicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            public void onWheelScrolling(float f, float f2) {
                if (WheelDatePicker.this.listener != null) {
                    WheelDatePicker.this.listener.onWheelScrolling(f, f2);
                }
            }

            public void onWheelSelected(int i, String str) {
                if (i == 0) {
                    WheelDatePicker.this.year = str;
                }
                if (i == 1) {
                    WheelDatePicker.this.month = str;
                }
                if (i == 2) {
                    WheelDatePicker.this.day = str;
                }
                if (WheelDatePicker.this.isValidDate()) {
                    int i2 = i;
                    if (i2 == 0 || i2 == 1) {
                        WheelDatePicker.this.pickerDay.setCurrentYearAndMonth(Integer.valueOf(WheelDatePicker.this.year).intValue(), Integer.valueOf(WheelDatePicker.this.month).intValue());
                    }
                    if (WheelDatePicker.this.listener != null) {
                        AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener = WheelDatePicker.this.listener;
                        onWheelChangeListener.onWheelSelected(-1, WheelDatePicker.this.year + "-" + WheelDatePicker.this.month + "-" + WheelDatePicker.this.day);
                    }
                }
            }

            public void onWheelScrollStateChanged(int i) {
                if (i == 0) {
                    WheelDatePicker.this.stateYear = i;
                }
                if (i == 1) {
                    WheelDatePicker.this.stateMonth = i;
                }
                if (i == 2) {
                    WheelDatePicker.this.stateDay = i;
                }
                if (WheelDatePicker.this.listener != null) {
                    WheelDatePicker wheelDatePicker = WheelDatePicker.this;
                    wheelDatePicker.checkState(wheelDatePicker.listener);
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

    public void setCurrentDate(int i, int i2, int i3) {
        this.pickerYear.setCurrentYear(i);
        this.pickerMonth.setCurrentMonth(i2);
        this.pickerDay.setCurrentYearAndMonth(i, i2);
        this.pickerDay.setCurrentDay(i3);
    }

    public void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener) {
        this.listener = onWheelChangeListener;
    }

    /* access modifiers changed from: private */
    public void checkState(AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener) {
        if (this.stateYear == 0 && this.stateMonth == 0 && this.stateDay == 0) {
            onWheelChangeListener.onWheelScrollStateChanged(0);
        }
        if (this.stateYear == 2 || this.stateMonth == 2 || this.stateDay == 2) {
            onWheelChangeListener.onWheelScrollStateChanged(2);
        }
        if (this.stateYear + this.stateMonth + this.stateDay == 1) {
            onWheelChangeListener.onWheelScrollStateChanged(1);
        }
    }

    /* access modifiers changed from: private */
    public boolean isValidDate() {
        return !TextUtils.isEmpty(this.year) && !TextUtils.isEmpty(this.month) && !TextUtils.isEmpty(this.day);
    }

    public void setItemIndex(int i) {
        this.pickerYear.setItemIndex(i);
        this.pickerMonth.setItemIndex(i);
        this.pickerDay.setItemIndex(i);
    }

    public void setItemSpace(int i) {
        this.pickerYear.setItemSpace(i);
        this.pickerMonth.setItemSpace(i);
        this.pickerDay.setItemSpace(i);
    }

    public void setItemCount(int i) {
        this.pickerYear.setItemCount(i);
        this.pickerMonth.setItemCount(i);
        this.pickerDay.setItemCount(i);
    }

    public void setTextColor(int i) {
        this.pickerYear.setTextColor(i);
        this.pickerMonth.setTextColor(i);
        this.pickerDay.setTextColor(i);
    }

    public void setTextSize(int i) {
        this.pickerYear.setTextSize(i);
        this.pickerMonth.setTextSize(i);
        this.pickerDay.setTextSize(i);
    }

    public void clearCache() {
        this.pickerYear.clearCache();
        this.pickerMonth.clearCache();
        this.pickerDay.clearCache();
    }

    public void setCurrentTextColor(int i) {
        this.pickerYear.setCurrentTextColor(i);
        this.pickerMonth.setCurrentTextColor(i);
        this.pickerDay.setCurrentTextColor(i);
    }

    public void setWheelDecor(boolean z, AbstractWheelDecor abstractWheelDecor) {
        this.pickerYear.setWheelDecor(z, abstractWheelDecor);
        this.pickerMonth.setWheelDecor(z, abstractWheelDecor);
        this.pickerDay.setWheelDecor(z, abstractWheelDecor);
    }
}
