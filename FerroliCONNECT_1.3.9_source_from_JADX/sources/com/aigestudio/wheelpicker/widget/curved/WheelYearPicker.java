package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelYearPicker extends WheelCurvedPicker {
    private static final int FROM = 1900;

    /* renamed from: TO */
    private static final int f803TO = 2100;
    private static final List<String> YEARS = new ArrayList();
    private int from = FROM;

    /* renamed from: to */
    private int f804to = f803TO;
    private int year;
    private List<String> years = YEARS;

    static {
        for (int i = FROM; i <= f803TO; i++) {
            YEARS.add(String.valueOf(i));
        }
    }

    public WheelYearPicker(Context context) {
        super(context);
        init();
    }

    public WheelYearPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        super.setData(this.years);
        setCurrentYear(Calendar.getInstance().get(1));
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setYearRange(int i, int i2) {
        this.from = i;
        this.f804to = i2;
        this.years.clear();
        while (i <= i2) {
            this.years.add(String.valueOf(i));
            i++;
        }
        super.setData(this.years);
    }

    public void setCurrentYear(int i) {
        int min = Math.min(Math.max(i, this.from), this.f804to);
        this.year = min;
        setItemIndex(min - this.from);
    }
}
