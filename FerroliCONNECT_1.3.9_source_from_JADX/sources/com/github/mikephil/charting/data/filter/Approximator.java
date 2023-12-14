package com.github.mikephil.charting.data.filter;

import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.List;

public class Approximator {
    private boolean[] keep;
    private float mDeltaRatio;
    private float mScaleRatio;
    private double mTolerance;
    private ApproximatorType mType;

    public enum ApproximatorType {
        NONE,
        DOUGLAS_PEUCKER
    }

    public Approximator() {
        this.mType = ApproximatorType.DOUGLAS_PEUCKER;
        this.mTolerance = 0.0d;
        this.mScaleRatio = 1.0f;
        this.mDeltaRatio = 1.0f;
        this.mType = ApproximatorType.NONE;
    }

    public Approximator(ApproximatorType approximatorType, double d) {
        this.mType = ApproximatorType.DOUGLAS_PEUCKER;
        this.mTolerance = 0.0d;
        this.mScaleRatio = 1.0f;
        this.mDeltaRatio = 1.0f;
        setup(approximatorType, d);
    }

    public void setup(ApproximatorType approximatorType, double d) {
        this.mType = approximatorType;
        this.mTolerance = d;
    }

    public void setTolerance(double d) {
        this.mTolerance = d;
    }

    public void setType(ApproximatorType approximatorType) {
        this.mType = approximatorType;
    }

    public void setRatios(float f, float f2) {
        this.mDeltaRatio = f;
        this.mScaleRatio = f2;
    }

    public List<Entry> filter(List<Entry> list) {
        return filter(list, this.mTolerance);
    }

    public List<Entry> filter(List<Entry> list, double d) {
        if (d <= 0.0d) {
            return list;
        }
        this.keep = new boolean[list.size()];
        int i = C09961.f1479xc71afc41[this.mType.ordinal()];
        if (i == 1) {
            return reduceWithDouglasPeuker(list, d);
        }
        if (i != 2) {
        }
        return list;
    }

    /* renamed from: com.github.mikephil.charting.data.filter.Approximator$1 */
    static /* synthetic */ class C09961 {

        /* renamed from: $SwitchMap$com$github$mikephil$charting$data$filter$Approximator$ApproximatorType */
        static final /* synthetic */ int[] f1479xc71afc41 = new int[ApproximatorType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.github.mikephil.charting.data.filter.Approximator$ApproximatorType[] r0 = com.github.mikephil.charting.data.filter.Approximator.ApproximatorType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1479xc71afc41 = r0
                int[] r0 = f1479xc71afc41     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.data.filter.Approximator$ApproximatorType r1 = com.github.mikephil.charting.data.filter.Approximator.ApproximatorType.DOUGLAS_PEUCKER     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1479xc71afc41     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.data.filter.Approximator$ApproximatorType r1 = com.github.mikephil.charting.data.filter.Approximator.ApproximatorType.NONE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.data.filter.Approximator.C09961.<clinit>():void");
        }
    }

    private List<Entry> reduceWithDouglasPeuker(List<Entry> list, double d) {
        if (d <= 0.0d || list.size() < 3) {
            return list;
        }
        boolean[] zArr = this.keep;
        zArr[0] = true;
        zArr[list.size() - 1] = true;
        algorithmDouglasPeucker(list, d, 0, list.size() - 1);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (this.keep[i]) {
                Entry entry = list.get(i);
                arrayList.add(new Entry(entry.getVal(), entry.getXIndex()));
            }
        }
        return arrayList;
    }

    private void algorithmDouglasPeucker(List<Entry> list, double d, int i, int i2) {
        List<Entry> list2 = list;
        int i3 = i;
        int i4 = i2;
        int i5 = i3 + 1;
        if (i4 > i5) {
            double d2 = 0.0d;
            Entry entry = list.get(i3);
            Entry entry2 = list.get(i4);
            int i6 = 0;
            while (i5 < i4) {
                double calcAngleBetweenLines = calcAngleBetweenLines(entry, entry2, entry, list.get(i5));
                if (calcAngleBetweenLines > d2) {
                    i6 = i5;
                    d2 = calcAngleBetweenLines;
                }
                i5++;
            }
            if (d2 > d) {
                this.keep[i6] = true;
                List<Entry> list3 = list;
                double d3 = d;
                algorithmDouglasPeucker(list3, d3, i, i6);
                algorithmDouglasPeucker(list3, d3, i6, i2);
            }
        }
    }

    public double calcPointToLineDistance(Entry entry, Entry entry2, Entry entry3) {
        float xIndex = ((float) entry2.getXIndex()) - ((float) entry.getXIndex());
        float xIndex2 = ((float) entry3.getXIndex()) - ((float) entry.getXIndex());
        double sqrt = Math.sqrt((double) ((xIndex * xIndex) + ((entry2.getVal() - entry.getVal()) * (entry2.getVal() - entry.getVal()))));
        double abs = (double) Math.abs((xIndex2 * (entry2.getVal() - entry.getVal())) - ((entry3.getVal() - entry.getVal()) * xIndex));
        Double.isNaN(abs);
        return abs / sqrt;
    }

    public double calcAngleBetweenLines(Entry entry, Entry entry2, Entry entry3, Entry entry4) {
        return Math.abs(calcAngleWithRatios(entry, entry2) - calcAngleWithRatios(entry3, entry4));
    }

    public double calcAngleWithRatios(Entry entry, Entry entry2) {
        return (Math.atan2((double) ((entry2.getVal() * this.mScaleRatio) - (entry.getVal() * this.mScaleRatio)), (double) ((((float) entry2.getXIndex()) * this.mDeltaRatio) - (((float) entry.getXIndex()) * this.mDeltaRatio))) * 180.0d) / 3.141592653589793d;
    }

    public double calcAngle(Entry entry, Entry entry2) {
        return (Math.atan2((double) (entry2.getVal() - entry.getVal()), (double) ((float) (entry2.getXIndex() - entry.getXIndex()))) * 180.0d) / 3.141592653589793d;
    }
}
