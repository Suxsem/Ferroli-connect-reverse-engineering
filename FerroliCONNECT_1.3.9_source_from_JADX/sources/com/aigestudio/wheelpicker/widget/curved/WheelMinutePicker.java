package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.aigestudio.wheelpicker.widget.IDigital;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WheelMinutePicker extends WheelCurvedPicker implements IDigital {
    private static final List<String> MINUTES_DIGITAL_DOUBLE = new ArrayList();
    private static final List<String> MINUTES_DIGITAL_SINGLE = new ArrayList();
    private int minute;
    private List<String> minutes = MINUTES_DIGITAL_SINGLE;

    static {
        for (int i = 0; i < 60; i++) {
            MINUTES_DIGITAL_SINGLE.add(String.valueOf(i));
        }
        for (int i2 = 0; i2 < 60; i2++) {
            String valueOf = String.valueOf(i2);
            if (valueOf.length() == 1) {
                valueOf = "0" + valueOf;
            }
            MINUTES_DIGITAL_DOUBLE.add(valueOf);
        }
    }

    public WheelMinutePicker(Context context) {
        super(context);
        init();
    }

    public WheelMinutePicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        super.setData(this.minutes);
        setCurrentMinute(Calendar.getInstance().get(12));
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentMinute(int i) {
        int min = Math.min(Math.max(i, 0), 59);
        this.minute = min;
        setItemIndex(min);
    }

    public void setDigitType(int i) {
        if (i == 1) {
            this.minutes = MINUTES_DIGITAL_SINGLE;
        } else {
            this.minutes = MINUTES_DIGITAL_DOUBLE;
        }
        super.setData(this.minutes);
    }
}
