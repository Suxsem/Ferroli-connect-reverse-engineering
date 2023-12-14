package com.aigestudio.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class WheelDayPicker extends WheelCurvedPicker {

    /* renamed from: C */
    private static final Calendar f802C = Calendar.getInstance();
    private static final HashMap<Integer, List<String>> DAYS = new HashMap<>();
    private int day = f802C.get(5);
    private List<String> days = new ArrayList();
    private int maxDay;
    private int month = (f802C.get(2) + 1);
    private int year = f802C.get(1);

    public WheelDayPicker(Context context) {
        super(context);
        init();
    }

    public WheelDayPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        initData();
        setCurrentDay();
    }

    private void initData() {
        List<String> list;
        int actualMaximum = f802C.getActualMaximum(5);
        if (actualMaximum != this.maxDay) {
            this.maxDay = actualMaximum;
            if (DAYS.containsKey(Integer.valueOf(actualMaximum))) {
                list = DAYS.get(Integer.valueOf(actualMaximum));
            } else {
                ArrayList arrayList = new ArrayList();
                for (int i = 1; i <= actualMaximum; i++) {
                    arrayList.add(String.valueOf(i));
                }
                DAYS.put(Integer.valueOf(actualMaximum), arrayList);
                list = arrayList;
            }
            this.days = list;
            super.setData(list);
        }
    }

    public void setData(List<String> list) {
        throw new RuntimeException("Set data will not allow here!");
    }

    public void setCurrentDay(int i) {
        this.day = Math.min(Math.max(i, 1), this.maxDay);
        setCurrentDay();
    }

    private void setCurrentDay() {
        setItemIndex(this.day - 1);
    }

    public void setCurrentMonth(int i) {
        setMonth(i);
        initData();
    }

    private void setMonth(int i) {
        int min = Math.min(Math.max(i, 1), 12);
        this.month = min;
        f802C.set(2, min - 1);
    }

    public void setCurrentYear(int i) {
        setYear(i);
        initData();
    }

    private void setYear(int i) {
        int min = Math.min(Math.max(i, 1), 2147483646);
        this.year = min;
        f802C.set(1, min);
    }

    public void setCurrentYearAndMonth(int i, int i2) {
        setYear(i);
        setMonth(i2);
        initData();
        checkScrollState();
    }
}
