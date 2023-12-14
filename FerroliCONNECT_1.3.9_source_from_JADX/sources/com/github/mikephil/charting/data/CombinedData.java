package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.ArrayList;
import java.util.List;

public class CombinedData extends BarLineScatterCandleBubbleData<IBarLineScatterCandleBubbleDataSet<?>> {
    private BarData mBarData;
    private BubbleData mBubbleData;
    private CandleData mCandleData;
    private LineData mLineData;
    private ScatterData mScatterData;

    public CombinedData() {
    }

    public CombinedData(List<String> list) {
        super(list);
    }

    public CombinedData(String[] strArr) {
        super(strArr);
    }

    public void setData(LineData lineData) {
        this.mLineData = lineData;
        this.mDataSets.addAll(lineData.getDataSets());
        init();
    }

    public void setData(BarData barData) {
        this.mBarData = barData;
        this.mDataSets.addAll(barData.getDataSets());
        init();
    }

    public void setData(ScatterData scatterData) {
        this.mScatterData = scatterData;
        this.mDataSets.addAll(scatterData.getDataSets());
        init();
    }

    public void setData(CandleData candleData) {
        this.mCandleData = candleData;
        this.mDataSets.addAll(candleData.getDataSets());
        init();
    }

    public void setData(BubbleData bubbleData) {
        this.mBubbleData = bubbleData;
        this.mDataSets.addAll(bubbleData.getDataSets());
        init();
    }

    public BubbleData getBubbleData() {
        return this.mBubbleData;
    }

    public LineData getLineData() {
        return this.mLineData;
    }

    public BarData getBarData() {
        return this.mBarData;
    }

    public ScatterData getScatterData() {
        return this.mScatterData;
    }

    public CandleData getCandleData() {
        return this.mCandleData;
    }

    public List<ChartData> getAllData() {
        ArrayList arrayList = new ArrayList();
        LineData lineData = this.mLineData;
        if (lineData != null) {
            arrayList.add(lineData);
        }
        BarData barData = this.mBarData;
        if (barData != null) {
            arrayList.add(barData);
        }
        ScatterData scatterData = this.mScatterData;
        if (scatterData != null) {
            arrayList.add(scatterData);
        }
        CandleData candleData = this.mCandleData;
        if (candleData != null) {
            arrayList.add(candleData);
        }
        BubbleData bubbleData = this.mBubbleData;
        if (bubbleData != null) {
            arrayList.add(bubbleData);
        }
        return arrayList;
    }

    public void notifyDataChanged() {
        LineData lineData = this.mLineData;
        if (lineData != null) {
            lineData.notifyDataChanged();
        }
        BarData barData = this.mBarData;
        if (barData != null) {
            barData.notifyDataChanged();
        }
        CandleData candleData = this.mCandleData;
        if (candleData != null) {
            candleData.notifyDataChanged();
        }
        ScatterData scatterData = this.mScatterData;
        if (scatterData != null) {
            scatterData.notifyDataChanged();
        }
        BubbleData bubbleData = this.mBubbleData;
        if (bubbleData != null) {
            bubbleData.notifyDataChanged();
        }
        init();
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.github.mikephil.charting.data.Entry getEntryForHighlight(com.github.mikephil.charting.highlight.Highlight r6) {
        /*
            r5 = this;
            java.util.List r0 = r5.getAllData()
            int r1 = r6.getDataIndex()
            int r2 = r0.size()
            r3 = 0
            if (r1 < r2) goto L_0x0010
            return r3
        L_0x0010:
            int r1 = r6.getDataIndex()
            java.lang.Object r0 = r0.get(r1)
            com.github.mikephil.charting.data.ChartData r0 = (com.github.mikephil.charting.data.ChartData) r0
            int r1 = r6.getDataSetIndex()
            int r2 = r0.getDataSetCount()
            if (r1 < r2) goto L_0x0025
            return r3
        L_0x0025:
            int r1 = r6.getDataSetIndex()
            com.github.mikephil.charting.interfaces.datasets.IDataSet r0 = r0.getDataSetByIndex(r1)
            int r1 = r6.getXIndex()
            java.util.List r0 = r0.getEntriesForXIndex(r1)
            java.util.Iterator r0 = r0.iterator()
        L_0x0039:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005c
            java.lang.Object r1 = r0.next()
            com.github.mikephil.charting.data.Entry r1 = (com.github.mikephil.charting.data.Entry) r1
            float r2 = r1.getVal()
            float r4 = r6.getValue()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 == 0) goto L_0x005b
            float r2 = r6.getValue()
            boolean r2 = java.lang.Float.isNaN(r2)
            if (r2 == 0) goto L_0x0039
        L_0x005b:
            return r1
        L_0x005c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.data.CombinedData.getEntryForHighlight(com.github.mikephil.charting.highlight.Highlight):com.github.mikephil.charting.data.Entry");
    }
}
