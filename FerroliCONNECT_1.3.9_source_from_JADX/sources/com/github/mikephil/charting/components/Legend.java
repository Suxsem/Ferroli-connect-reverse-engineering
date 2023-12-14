package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend extends ComponentBase {
    private Boolean[] mCalculatedLabelBreakPoints;
    private FSize[] mCalculatedLabelSizes;
    private FSize[] mCalculatedLineSizes;
    private int[] mColors;
    private LegendDirection mDirection;
    private boolean mDrawInside;
    private int[] mExtraColors;
    private String[] mExtraLabels;
    private float mFormSize;
    private float mFormToTextSpace;
    private LegendHorizontalAlignment mHorizontalAlignment;
    private boolean mIsLegendCustom;
    private String[] mLabels;
    private float mMaxSizePercent;
    public float mNeededHeight;
    public float mNeededWidth;
    private LegendOrientation mOrientation;
    private LegendForm mShape;
    private float mStackSpace;
    public float mTextHeightMax;
    public float mTextWidthMax;
    private LegendVerticalAlignment mVerticalAlignment;
    private boolean mWordWrapEnabled;
    private float mXEntrySpace;
    private float mYEntrySpace;

    public enum LegendDirection {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    public enum LegendForm {
        SQUARE,
        CIRCLE,
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum LegendOrientation {
        HORIZONTAL,
        VERTICAL
    }

    public enum LegendPosition {
        RIGHT_OF_CHART,
        RIGHT_OF_CHART_CENTER,
        RIGHT_OF_CHART_INSIDE,
        LEFT_OF_CHART,
        LEFT_OF_CHART_CENTER,
        LEFT_OF_CHART_INSIDE,
        BELOW_CHART_LEFT,
        BELOW_CHART_RIGHT,
        BELOW_CHART_CENTER,
        ABOVE_CHART_LEFT,
        ABOVE_CHART_RIGHT,
        ABOVE_CHART_CENTER,
        PIECHART_CENTER
    }

    public enum LegendVerticalAlignment {
        TOP,
        CENTER,
        BOTTOM
    }

    public Legend() {
        this.mIsLegendCustom = false;
        this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
        this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
        this.mOrientation = LegendOrientation.HORIZONTAL;
        this.mDrawInside = false;
        this.mDirection = LegendDirection.LEFT_TO_RIGHT;
        this.mShape = LegendForm.SQUARE;
        this.mFormSize = 8.0f;
        this.mXEntrySpace = 6.0f;
        this.mYEntrySpace = 0.0f;
        this.mFormToTextSpace = 5.0f;
        this.mStackSpace = 3.0f;
        this.mMaxSizePercent = 0.95f;
        this.mNeededWidth = 0.0f;
        this.mNeededHeight = 0.0f;
        this.mTextHeightMax = 0.0f;
        this.mTextWidthMax = 0.0f;
        this.mWordWrapEnabled = false;
        this.mCalculatedLabelSizes = new FSize[0];
        this.mCalculatedLabelBreakPoints = new Boolean[0];
        this.mCalculatedLineSizes = new FSize[0];
        this.mFormSize = Utils.convertDpToPixel(8.0f);
        this.mXEntrySpace = Utils.convertDpToPixel(6.0f);
        this.mYEntrySpace = Utils.convertDpToPixel(0.0f);
        this.mFormToTextSpace = Utils.convertDpToPixel(5.0f);
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mStackSpace = Utils.convertDpToPixel(3.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    public Legend(int[] iArr, String[] strArr) {
        this();
        if (iArr == null || strArr == null) {
            throw new IllegalArgumentException("colors array or labels array is NULL");
        } else if (iArr.length == strArr.length) {
            this.mColors = iArr;
            this.mLabels = strArr;
        } else {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        }
    }

    public Legend(List<Integer> list, List<String> list2) {
        this();
        if (list == null || list2 == null) {
            throw new IllegalArgumentException("colors array or labels array is NULL");
        } else if (list.size() == list2.size()) {
            this.mColors = Utils.convertIntegers(list);
            this.mLabels = Utils.convertStrings(list2);
        } else {
            throw new IllegalArgumentException("colors array and labels array need to be of same size");
        }
    }

    public void setComputedColors(List<Integer> list) {
        this.mColors = Utils.convertIntegers(list);
    }

    public void setComputedLabels(List<String> list) {
        this.mLabels = Utils.convertStrings(list);
    }

    public float getMaximumEntryWidth(Paint paint) {
        float f = 0.0f;
        int i = 0;
        while (true) {
            String[] strArr = this.mLabels;
            if (i >= strArr.length) {
                return f + this.mFormSize + this.mFormToTextSpace;
            }
            if (strArr[i] != null) {
                float calcTextWidth = (float) Utils.calcTextWidth(paint, strArr[i]);
                if (calcTextWidth > f) {
                    f = calcTextWidth;
                }
            }
            i++;
        }
    }

    public float getMaximumEntryHeight(Paint paint) {
        float f = 0.0f;
        int i = 0;
        while (true) {
            String[] strArr = this.mLabels;
            if (i >= strArr.length) {
                return f;
            }
            if (strArr[i] != null) {
                float calcTextHeight = (float) Utils.calcTextHeight(paint, strArr[i]);
                if (calcTextHeight > f) {
                    f = calcTextHeight;
                }
            }
            i++;
        }
    }

    public int[] getColors() {
        return this.mColors;
    }

    public String[] getLabels() {
        return this.mLabels;
    }

    public String getLabel(int i) {
        return this.mLabels[i];
    }

    public int[] getExtraColors() {
        return this.mExtraColors;
    }

    public String[] getExtraLabels() {
        return this.mExtraLabels;
    }

    public void setExtra(List<Integer> list, List<String> list2) {
        this.mExtraColors = Utils.convertIntegers(list);
        this.mExtraLabels = Utils.convertStrings(list2);
    }

    public void setExtra(int[] iArr, String[] strArr) {
        this.mExtraColors = iArr;
        this.mExtraLabels = strArr;
    }

    public void setCustom(int[] iArr, String[] strArr) {
        if (iArr.length == strArr.length) {
            this.mLabels = strArr;
            this.mColors = iArr;
            this.mIsLegendCustom = true;
            return;
        }
        throw new IllegalArgumentException("colors array and labels array need to be of same size");
    }

    public void setCustom(List<Integer> list, List<String> list2) {
        if (list.size() == list2.size()) {
            this.mColors = Utils.convertIntegers(list);
            this.mLabels = Utils.convertStrings(list2);
            this.mIsLegendCustom = true;
            return;
        }
        throw new IllegalArgumentException("colors array and labels array need to be of same size");
    }

    public void resetCustom() {
        this.mIsLegendCustom = false;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public LegendPosition getPosition() {
        if (this.mOrientation == LegendOrientation.VERTICAL && this.mHorizontalAlignment == LegendHorizontalAlignment.CENTER && this.mVerticalAlignment == LegendVerticalAlignment.CENTER) {
            return LegendPosition.PIECHART_CENTER;
        }
        if (this.mOrientation == LegendOrientation.HORIZONTAL) {
            if (this.mVerticalAlignment == LegendVerticalAlignment.TOP) {
                if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
                    return LegendPosition.ABOVE_CHART_LEFT;
                }
                return this.mHorizontalAlignment == LegendHorizontalAlignment.RIGHT ? LegendPosition.ABOVE_CHART_RIGHT : LegendPosition.ABOVE_CHART_CENTER;
            } else if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
                return LegendPosition.BELOW_CHART_LEFT;
            } else {
                return this.mHorizontalAlignment == LegendHorizontalAlignment.RIGHT ? LegendPosition.BELOW_CHART_RIGHT : LegendPosition.BELOW_CHART_CENTER;
            }
        } else if (this.mHorizontalAlignment == LegendHorizontalAlignment.LEFT) {
            if (this.mVerticalAlignment != LegendVerticalAlignment.TOP || !this.mDrawInside) {
                return this.mVerticalAlignment == LegendVerticalAlignment.CENTER ? LegendPosition.LEFT_OF_CHART_CENTER : LegendPosition.LEFT_OF_CHART;
            }
            return LegendPosition.LEFT_OF_CHART_INSIDE;
        } else if (this.mVerticalAlignment != LegendVerticalAlignment.TOP || !this.mDrawInside) {
            return this.mVerticalAlignment == LegendVerticalAlignment.CENTER ? LegendPosition.RIGHT_OF_CHART_CENTER : LegendPosition.RIGHT_OF_CHART;
        } else {
            return LegendPosition.RIGHT_OF_CHART_INSIDE;
        }
    }

    public void setPosition(LegendPosition legendPosition) {
        switch (legendPosition) {
            case LEFT_OF_CHART:
            case LEFT_OF_CHART_INSIDE:
            case LEFT_OF_CHART_CENTER:
                this.mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
                this.mVerticalAlignment = legendPosition == LegendPosition.LEFT_OF_CHART_CENTER ? LegendVerticalAlignment.CENTER : LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
            case RIGHT_OF_CHART:
            case RIGHT_OF_CHART_INSIDE:
            case RIGHT_OF_CHART_CENTER:
                this.mHorizontalAlignment = LegendHorizontalAlignment.RIGHT;
                this.mVerticalAlignment = legendPosition == LegendPosition.RIGHT_OF_CHART_CENTER ? LegendVerticalAlignment.CENTER : LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
            case ABOVE_CHART_LEFT:
            case ABOVE_CHART_CENTER:
            case ABOVE_CHART_RIGHT:
                this.mHorizontalAlignment = legendPosition == LegendPosition.ABOVE_CHART_LEFT ? LegendHorizontalAlignment.LEFT : legendPosition == LegendPosition.ABOVE_CHART_RIGHT ? LegendHorizontalAlignment.RIGHT : LegendHorizontalAlignment.CENTER;
                this.mVerticalAlignment = LegendVerticalAlignment.TOP;
                this.mOrientation = LegendOrientation.HORIZONTAL;
                break;
            case BELOW_CHART_LEFT:
            case BELOW_CHART_CENTER:
            case BELOW_CHART_RIGHT:
                this.mHorizontalAlignment = legendPosition == LegendPosition.BELOW_CHART_LEFT ? LegendHorizontalAlignment.LEFT : legendPosition == LegendPosition.BELOW_CHART_RIGHT ? LegendHorizontalAlignment.RIGHT : LegendHorizontalAlignment.CENTER;
                this.mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
                this.mOrientation = LegendOrientation.HORIZONTAL;
                break;
            case PIECHART_CENTER:
                this.mHorizontalAlignment = LegendHorizontalAlignment.CENTER;
                this.mVerticalAlignment = LegendVerticalAlignment.CENTER;
                this.mOrientation = LegendOrientation.VERTICAL;
                break;
        }
        this.mDrawInside = legendPosition == LegendPosition.LEFT_OF_CHART_INSIDE || legendPosition == LegendPosition.RIGHT_OF_CHART_INSIDE;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public void setHorizontalAlignment(LegendHorizontalAlignment legendHorizontalAlignment) {
        this.mHorizontalAlignment = legendHorizontalAlignment;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public void setVerticalAlignment(LegendVerticalAlignment legendVerticalAlignment) {
        this.mVerticalAlignment = legendVerticalAlignment;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(LegendOrientation legendOrientation) {
        this.mOrientation = legendOrientation;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public void setDrawInside(boolean z) {
        this.mDrawInside = z;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public void setDirection(LegendDirection legendDirection) {
        this.mDirection = legendDirection;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public void setForm(LegendForm legendForm) {
        this.mShape = legendForm;
    }

    public void setFormSize(float f) {
        this.mFormSize = Utils.convertDpToPixel(f);
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public void setXEntrySpace(float f) {
        this.mXEntrySpace = Utils.convertDpToPixel(f);
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public void setYEntrySpace(float f) {
        this.mYEntrySpace = Utils.convertDpToPixel(f);
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public void setFormToTextSpace(float f) {
        this.mFormToTextSpace = Utils.convertDpToPixel(f);
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public void setStackSpace(float f) {
        this.mStackSpace = f;
    }

    public float getFullWidth(Paint paint) {
        float f;
        float f2 = 0.0f;
        int i = 0;
        while (true) {
            String[] strArr = this.mLabels;
            if (i >= strArr.length) {
                return f2;
            }
            if (strArr[i] != null) {
                if (this.mColors[i] != 1122868) {
                    f2 += this.mFormSize + this.mFormToTextSpace;
                }
                f2 += (float) Utils.calcTextWidth(paint, this.mLabels[i]);
                if (i < this.mLabels.length - 1) {
                    f = this.mXEntrySpace;
                } else {
                    i++;
                }
            } else {
                f2 += this.mFormSize;
                if (i < strArr.length - 1) {
                    f = this.mStackSpace;
                } else {
                    i++;
                }
            }
            f2 += f;
            i++;
        }
    }

    public float getFullHeight(Paint paint) {
        float f = 0.0f;
        int i = 0;
        while (true) {
            String[] strArr = this.mLabels;
            if (i >= strArr.length) {
                return f;
            }
            if (strArr[i] != null) {
                f += (float) Utils.calcTextHeight(paint, strArr[i]);
                if (i < this.mLabels.length - 1) {
                    f += this.mYEntrySpace;
                }
            }
            i++;
        }
    }

    public void setWordWrapEnabled(boolean z) {
        this.mWordWrapEnabled = z;
    }

    public boolean isWordWrapEnabled() {
        return this.mWordWrapEnabled;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    public void setMaxSizePercent(float f) {
        this.mMaxSizePercent = f;
    }

    public FSize[] getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public Boolean[] getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public FSize[] getCalculatedLineSizes() {
        return this.mCalculatedLineSizes;
    }

    public void calculateDimensions(Paint paint, ViewPortHandler viewPortHandler) {
        float f;
        float f2;
        float f3;
        float f4;
        Paint paint2 = paint;
        this.mTextWidthMax = getMaximumEntryWidth(paint);
        this.mTextHeightMax = getMaximumEntryHeight(paint);
        int i = C09941.f1476x9c9dbef[this.mOrientation.ordinal()];
        int i2 = ColorTemplate.COLOR_SKIP;
        boolean z = false;
        if (i == 1) {
            float lineHeight = Utils.getLineHeight(paint);
            int length = this.mLabels.length;
            float f5 = 0.0f;
            float f6 = 0.0f;
            boolean z2 = false;
            float f7 = 0.0f;
            for (int i3 = 0; i3 < length; i3++) {
                boolean z3 = this.mColors[i3] != 1122868;
                if (!z2) {
                    f7 = 0.0f;
                }
                if (z3) {
                    if (z2) {
                        f7 += this.mStackSpace;
                    }
                    f7 += this.mFormSize;
                }
                if (this.mLabels[i3] != null) {
                    if (z3 && !z2) {
                        f7 += this.mFormToTextSpace;
                    } else if (z2) {
                        f5 = Math.max(f5, f7);
                        f6 += this.mYEntrySpace + lineHeight;
                        z2 = false;
                        f7 = 0.0f;
                    }
                    f7 += (float) Utils.calcTextWidth(paint, this.mLabels[i3]);
                    if (i3 < length - 1) {
                        f6 += this.mYEntrySpace + lineHeight;
                    }
                } else {
                    Paint paint3 = paint;
                    f7 += this.mFormSize;
                    if (i3 < length - 1) {
                        f7 += this.mStackSpace;
                    }
                    z2 = true;
                }
                f5 = Math.max(f5, f7);
            }
            this.mNeededWidth = f5;
            this.mNeededHeight = f6;
        } else if (i == 2) {
            int length2 = this.mLabels.length;
            float lineHeight2 = Utils.getLineHeight(paint);
            float lineSpacing = Utils.getLineSpacing(paint) + this.mYEntrySpace;
            float contentWidth = viewPortHandler.contentWidth() * this.mMaxSizePercent;
            ArrayList arrayList = new ArrayList(length2);
            ArrayList arrayList2 = new ArrayList(length2);
            ArrayList arrayList3 = new ArrayList();
            int i4 = -1;
            int i5 = -1;
            int i6 = 0;
            float f8 = 0.0f;
            float f9 = 0.0f;
            float f10 = 0.0f;
            while (i6 < length2) {
                boolean z4 = this.mColors[i6] != i2;
                arrayList2.add(Boolean.valueOf(z));
                if (i5 == i4) {
                    f = 0.0f;
                } else {
                    f = f9 + this.mStackSpace;
                }
                String[] strArr = this.mLabels;
                if (strArr[i6] != null) {
                    arrayList.add(Utils.calcTextSize(paint2, strArr[i6]));
                    f2 = f + (z4 ? this.mFormSize + this.mFormToTextSpace : 0.0f) + ((FSize) arrayList.get(i6)).width;
                } else {
                    arrayList.add(new FSize(0.0f, 0.0f));
                    f2 = f + (z4 ? this.mFormSize : 0.0f);
                    if (i5 == -1) {
                        i5 = i6;
                    }
                }
                if (this.mLabels[i6] != null || i6 == length2 - 1) {
                    float f11 = f10;
                    if (f11 == 0.0f) {
                        f3 = 0.0f;
                    } else {
                        f3 = this.mXEntrySpace;
                    }
                    if (!this.mWordWrapEnabled || f11 == 0.0f || contentWidth - f11 >= f3 + f2) {
                        f4 = f11 + f3 + f2;
                    } else {
                        arrayList3.add(new FSize(f11, lineHeight2));
                        f8 = Math.max(f8, f11);
                        arrayList2.set(i5 > -1 ? i5 : i6, true);
                        f4 = f2;
                    }
                    if (i6 == length2 - 1) {
                        arrayList3.add(new FSize(f4, lineHeight2));
                        f10 = f4;
                        f8 = Math.max(f8, f4);
                    } else {
                        f10 = f4;
                    }
                }
                if (this.mLabels[i6] != null) {
                    i5 = -1;
                }
                i6++;
                paint2 = paint;
                f9 = f2;
                i2 = ColorTemplate.COLOR_SKIP;
                z = false;
                i4 = -1;
            }
            this.mCalculatedLabelSizes = (FSize[]) arrayList.toArray(new FSize[arrayList.size()]);
            this.mCalculatedLabelBreakPoints = (Boolean[]) arrayList2.toArray(new Boolean[arrayList2.size()]);
            this.mCalculatedLineSizes = (FSize[]) arrayList3.toArray(new FSize[arrayList3.size()]);
            this.mNeededWidth = f8;
            FSize[] fSizeArr = this.mCalculatedLineSizes;
            this.mNeededHeight = (lineHeight2 * ((float) fSizeArr.length)) + (lineSpacing * ((float) (fSizeArr.length == 0 ? 0 : fSizeArr.length - 1)));
        }
    }

    /* renamed from: com.github.mikephil.charting.components.Legend$1 */
    static /* synthetic */ class C09941 {

        /* renamed from: $SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation */
        static final /* synthetic */ int[] f1476x9c9dbef = new int[LegendOrientation.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(30:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|(3:33|34|36)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(31:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|(3:33|34|36)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(33:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|36) */
        /* JADX WARNING: Can't wrap try/catch for region: R(34:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|36) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0047 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x005d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0068 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0073 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x007f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x008b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0097 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00a3 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00af */
        static {
            /*
                com.github.mikephil.charting.components.Legend$LegendOrientation[] r0 = com.github.mikephil.charting.components.Legend.LegendOrientation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1476x9c9dbef = r0
                r0 = 1
                int[] r1 = f1476x9c9dbef     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.github.mikephil.charting.components.Legend$LegendOrientation r2 = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f1476x9c9dbef     // Catch:{ NoSuchFieldError -> 0x001f }
                com.github.mikephil.charting.components.Legend$LegendOrientation r3 = com.github.mikephil.charting.components.Legend.LegendOrientation.HORIZONTAL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                com.github.mikephil.charting.components.Legend$LegendPosition[] r2 = com.github.mikephil.charting.components.Legend.LegendPosition.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f1477x7d277f6a = r2
                int[] r2 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0032 }
                com.github.mikephil.charting.components.Legend$LegendPosition r3 = com.github.mikephil.charting.components.Legend.LegendPosition.LEFT_OF_CHART     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x003c }
                com.github.mikephil.charting.components.Legend$LegendPosition r2 = com.github.mikephil.charting.components.Legend.LegendPosition.LEFT_OF_CHART_INSIDE     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0047 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.LEFT_OF_CHART_CENTER     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0052 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x005d }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART_INSIDE     // Catch:{ NoSuchFieldError -> 0x005d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005d }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005d }
            L_0x005d:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0068 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.RIGHT_OF_CHART_CENTER     // Catch:{ NoSuchFieldError -> 0x0068 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0068 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0068 }
            L_0x0068:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0073 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.ABOVE_CHART_LEFT     // Catch:{ NoSuchFieldError -> 0x0073 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0073 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0073 }
            L_0x0073:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x007f }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.ABOVE_CHART_CENTER     // Catch:{ NoSuchFieldError -> 0x007f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x007f }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x007f }
            L_0x007f:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x008b }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.ABOVE_CHART_RIGHT     // Catch:{ NoSuchFieldError -> 0x008b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x008b }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x008b }
            L_0x008b:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x0097 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.BELOW_CHART_LEFT     // Catch:{ NoSuchFieldError -> 0x0097 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0097 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0097 }
            L_0x0097:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x00a3 }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.BELOW_CHART_CENTER     // Catch:{ NoSuchFieldError -> 0x00a3 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a3 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00a3 }
            L_0x00a3:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x00af }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.BELOW_CHART_RIGHT     // Catch:{ NoSuchFieldError -> 0x00af }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00af }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00af }
            L_0x00af:
                int[] r0 = f1477x7d277f6a     // Catch:{ NoSuchFieldError -> 0x00bb }
                com.github.mikephil.charting.components.Legend$LegendPosition r1 = com.github.mikephil.charting.components.Legend.LegendPosition.PIECHART_CENTER     // Catch:{ NoSuchFieldError -> 0x00bb }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00bb }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00bb }
            L_0x00bb:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.github.mikephil.charting.components.Legend.C09941.<clinit>():void");
        }
    }
}
