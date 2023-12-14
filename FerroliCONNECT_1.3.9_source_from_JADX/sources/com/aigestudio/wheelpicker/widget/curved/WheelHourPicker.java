package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aigestudio.wheelpicker.widget.IDigital;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelHourPicker extends WheelCurvedPicker implements IDigital {
    private static final List<String> HOURS_DIGITAL_DOUBLE = new ArrayList();
    private static final List<String> HOURS_DIGITAL_SINGLE = new ArrayList();
    private int hour;
    private List<String> hours = HOURS_DIGITAL_SINGLE;

    static {
        for (int i = 0; i < 24; i++) {
            HOURS_DIGITAL_SINGLE.add(String.valueOf(i));
        }
        for (int i2 = 0; i2 < 24; i2++) {
            String valueOf = String.valueOf(i2);
            if (valueOf.length() == 1) {
                valueOf = "0" + valueOf;
            }
            HOURS_DIGITAL_DOUBLE.add(valueOf);
        }
    }

    public WheelHourPicker(Context context) {
        super(context);
        init();
    }

    public WheelHourPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        super.setData(this.hours);
        setCurrentHour(Calendar.getInstance().get(11));
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentHour(int i) {
        int min = Math.min(Math.max(i, 0), 23);
        this.hour = min;
        setItemIndex(min);
    }

    public void setDigitType(int i) {
        if (i == 1) {
            this.hours = HOURS_DIGITAL_SINGLE;
        } else {
            this.hours = HOURS_DIGITAL_DOUBLE;
        }
        super.setData(this.hours);
    }
}
