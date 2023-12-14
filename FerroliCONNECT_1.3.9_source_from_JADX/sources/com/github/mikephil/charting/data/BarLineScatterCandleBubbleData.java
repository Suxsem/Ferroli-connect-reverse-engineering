package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public abstract class BarLineScatterCandleBubbleData<T extends IBarLineScatterCandleBubbleDataSet<? extends Entry>> extends ChartData<T> {
    public BarLineScatterCandleBubbleData() {
    }

    public BarLineScatterCandleBubbleData(List<String> list) {
        super(list);
    }

    public BarLineScatterCandleBubbleData(String[] strArr) {
        super(strArr);
    }

    public BarLineScatterCandleBubbleData(List<String> list, List<T> list2) {
        super(list, list2);
    }

    public BarLineScatterCandleBubbleData(String[] strArr, List<T> list) {
        super(strArr, list);
    }
}
