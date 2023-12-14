package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelMonthPicker extends WheelCurvedPicker {
    private static final List<String> MONTHS = new ArrayList();
    private int month;
    private List<String> months = MONTHS;

    static {
        for (int i = 1; i <= 12; i++) {
            MONTHS.add(String.valueOf(i));
        }
    }

    public WheelMonthPicker(Context context) {
        super(context);
        init();
    }

    public WheelMonthPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        super.setData(this.months);
        setCurrentMonth(Calendar.getInstance().get(2) + 1);
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentMonth(int i) {
        int min = Math.min(Math.max(i, 1), 12);
        this.month = min;
        setItemIndex(min - 1);
    }
}
