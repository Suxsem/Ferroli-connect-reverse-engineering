package com.aigestudio.wheelpicker.core;

import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import java.util.List;

public interface IWheelPicker {
    void clearCache();

    void setCurrentTextColor(int i);

    void setData(List<String> list);

    void setItemCount(int i);

    void setItemIndex(int i);

    void setItemSpace(int i);

    void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener onWheelChangeListener);

    void setTextColor(int i);

    void setTextSize(int i);

    void setWheelDecor(boolean z, AbstractWheelDecor abstractWheelDecor);
}
