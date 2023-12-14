package com.szacs.ferroliconnect.widget.wheel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.DensityUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class BarViewBeSmart196 extends View {
    private int BACKGROUND_COLOR;
    private float BAR_SIDE_MARGIN;
    private int BAR_TEXT_COLOR;
    private int BOTTOM_TEXT_COLOR;
    private int CANVAS_COLOR;
    private int DASH_COLOR;
    private float DefaultBarHeight;
    private float DefaultBarWidth;
    private float LeftTextWidth;
    private int NORMAL_BAR_COLOR;
    private float Pading;
    private int SIDE_TEXT_COLOR;
    private float TextMargin;
    private float TotalBarHeight;
    private float TotalBarWidth;
    private float XaxisMargin;
    private float XaxisPosition;
    private float YaxisPosition;
    private TextPaint barTextPaint;
    private ArrayList<Boolean> barTextVisibility;
    private Paint bgPaint;
    private float bottomTextHeight;
    private ArrayList<String> bottomTextList;
    private TextPaint bottomtextPaint;
    private int[] colors;
    private Paint dashPaint;
    private int downx;
    private int downy;
    private Paint fgPaint;
    private boolean isShowTextInFirstBar;
    private float leftIconWidth;
    private Bitmap mComfBitmap;
    private Context mContext;
    private Bitmap mEconBitmap;
    private Bitmap mFrostBitmap;
    private OnBarClickListener mOnBarClickListener;
    private OnCanvasClickListener mOnCanvasClickListener;
    private Path path;
    private ArrayList<Float> percentList;
    private ArrayList<RectF> rectFlists;
    private float scale;
    private Paint sideIconPaint;
    private ArrayList<Float> targetPercentList;
    private Paint textPaint;
    private float textSize;
    private String[] titles;

    public interface OnBarClickListener {
        void onBarClick(int i, int i2, int i3);
    }

    public interface OnCanvasClickListener {
        void onCanvasClick();
    }

    private int caculateIndex(float f) {
        float f2 = f * 10.0f;
        float f3 = f2 % 10.0f;
        int i = (int) (f2 / 10.0f);
        return (int) ((float) (f3 == 5.0f ? (i * 2) + 1 : i * 2));
    }

    private float indexTotime(float f) {
        return (1.0f + f) % 2.0f == 0.0f ? f / 2.0f : f / 2.0f;
    }

    private boolean isWithin24Hours(float f) {
        return f <= 24.0f && f >= 0.0f;
    }

    private boolean isWithinRange(float f, float f2, float f3) {
        return f3 >= f && f3 <= f2;
    }

    public BarViewBeSmart196(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.downx = 0;
        this.downy = 0;
        this.CANVAS_COLOR = Color.parseColor("#00000000");
        this.NORMAL_BAR_COLOR = getResources().getColor(C1683R.color.ferroli_green);
        this.BOTTOM_TEXT_COLOR = Color.parseColor("#666666");
        this.BAR_TEXT_COLOR = Color.parseColor("#666666");
        this.SIDE_TEXT_COLOR = Color.parseColor("#666666");
        this.DASH_COLOR = Color.parseColor("#666666");
        init(context);
    }

    public BarViewBeSmart196(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public BarViewBeSmart196(Context context) {
        this(context, (AttributeSet) null);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawColor(this.CANVAS_COLOR);
        drawBackgroup(canvas);
        drawBars(canvas);
        drawBottomText(canvas);
    }

    public void setCanvasColor(int i) {
        this.CANVAS_COLOR = i;
        postInvalidate();
    }

    public void setDataList(ArrayList<Integer> arrayList, int i) {
        this.targetPercentList = new ArrayList<>();
        if (i == 0) {
            i = 1;
        }
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            this.targetPercentList.add(Float.valueOf(1.0f - (((float) it.next().intValue()) / ((float) i))));
        }
        if (this.percentList == null) {
            this.percentList = new ArrayList<>();
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.targetPercentList.size(); i3++) {
            this.percentList.add(Float.valueOf(((float) arrayList.get(i3).intValue()) / ((float) i)));
        }
        if (this.percentList.isEmpty() || this.percentList.size() < this.targetPercentList.size()) {
            int size = this.targetPercentList.size() - this.percentList.size();
            while (i2 < size) {
                this.percentList.add(Float.valueOf(1.0f));
                i2++;
            }
        } else if (this.percentList.size() > this.targetPercentList.size()) {
            int size2 = this.percentList.size() - this.targetPercentList.size();
            while (i2 < size2) {
                ArrayList<Float> arrayList2 = this.percentList;
                arrayList2.remove(arrayList2.size() - 1);
                i2++;
            }
        }
        setMinimumWidth(2);
    }

    public void setBottomTextColor(int i) {
        this.bottomtextPaint.setColor(i);
        postInvalidate();
    }

    public void setSideTextColor(int i) {
        this.textPaint.setColor(i);
        postInvalidate();
    }

    public void setSideTextSize(float f) {
        this.textPaint.setTextSize(f);
        this.LeftTextWidth = this.textPaint.measureText("T1 19.5");
        if (Math.abs(this.textPaint.ascent()) > ((float) DensityUtil.dip2px(this.mContext, 10.0f))) {
            this.Pading = Math.abs(this.textPaint.ascent()) + 3.0f;
        } else {
            this.Pading = (float) DensityUtil.dip2px(this.mContext, 10.0f);
        }
        postInvalidate();
    }

    public void setSideText(String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            this.titles[i] = strArr[i];
        }
        postInvalidate();
    }

    public ArrayList<Float> getData() {
        ArrayList<Float> arrayList = new ArrayList<>();
        for (int i = 0; i < this.percentList.size(); i++) {
            arrayList.add(Float.valueOf(this.percentList.get(i).floatValue() * 40.0f));
        }
        return arrayList;
    }

    public void setBarWidth(float f) {
        this.DefaultBarWidth = f;
    }

    private void updataRectflist() {
        if (!this.rectFlists.isEmpty()) {
            this.rectFlists.clear();
        }
        float f = (this.TotalBarWidth - (this.BAR_SIDE_MARGIN * 49.0f)) / 48.0f;
        for (int i = 1; i < this.percentList.size() + 1; i++) {
            RectF rectF = new RectF();
            int i2 = i - 1;
            float floatValue = this.percentList.get(i2).floatValue();
            if (floatValue > 1.0f) {
                floatValue = 1.0f;
            }
            float f2 = this.XaxisPosition;
            float f3 = this.XaxisMargin;
            rectF.top = (f2 - f3) - (this.TotalBarHeight * floatValue);
            rectF.bottom = f2 - f3;
            rectF.left = this.YaxisPosition + (this.BAR_SIDE_MARGIN * ((float) i)) + (((float) i2) * f);
            rectF.right = rectF.left + f;
            this.rectFlists.add(rectF);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ArrayList unused = savedState.percentList = this.percentList;
        ArrayList unused2 = savedState.rectFlists = this.rectFlists;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.percentList = savedState.percentList;
            this.rectFlists = savedState.rectFlists;
            super.onRestoreInstanceState(savedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    private int[] findBar(float f, float f2) {
        int i;
        new RectF();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = 2;
            if (i2 < this.rectFlists.size()) {
                RectF rectF = this.rectFlists.get(i2);
                rectF.top = (this.XaxisPosition - this.XaxisMargin) - this.TotalBarHeight;
                if (f < rectF.right && rectF.left < f && f2 < rectF.bottom && f2 > rectF.top) {
                    if (((double) this.percentList.get(i2).floatValue()) < 0.4d) {
                        i = 0;
                    } else {
                        i = ((double) this.percentList.get(i2).floatValue()) < 0.8d ? 1 : 2;
                    }
                    float f3 = this.XaxisPosition;
                    float f4 = this.XaxisMargin;
                    float f5 = this.TotalBarHeight;
                    if (f2 >= (f3 - f4) - (0.6666667f * f5)) {
                        i6 = f2 > (f3 - f4) - (f5 * 0.33333334f) ? 0 : 1;
                    }
                    i4 = i;
                    i5 = i6;
                    i3 = i2;
                }
                i2++;
            } else {
                return new int[]{i3, i4, i5};
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downx = (int) motionEvent.getX();
            this.downy = (int) motionEvent.getY();
        } else if (action == 1) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            Region region = new Region(x - 5, y - 5, x + 5, y + 5);
            if (this.mOnBarClickListener != null && region.contains(this.downx, this.downy)) {
                float f = (float) x;
                float f2 = (float) y;
                if (isWithinBar(f, f2)) {
                    int[] findBar = findBar(f, f2);
                    int i = findBar[0];
                    int i2 = findBar[1];
                    int i3 = findBar[2];
                    if (i3 == 2) {
                        if (i2 == 2) {
                            setTemperature(i, 1);
                        } else {
                            setTemperature(i, 2);
                        }
                    } else if (i3 == 1) {
                        if (i2 == 2 || i2 == 0) {
                            setTemperature(i, 1);
                        } else {
                            setTemperature(i, 0);
                        }
                    } else if (i3 == 0) {
                        if (i2 == 2 || i2 == 1) {
                            setTemperature(i, 0);
                        } else {
                            setTemperature(i, 1);
                        }
                    }
                    this.mOnBarClickListener.onBarClick(i, i2, i3);
                }
            }
            if (this.mOnCanvasClickListener != null && region.contains(this.downx, this.downy)) {
                this.mOnCanvasClickListener.onCanvasClick();
            }
        }
        return true;
    }

    private boolean isWithinBar(float f, float f2) {
        for (int i = 0; i < this.rectFlists.size(); i++) {
            RectF rectF = this.rectFlists.get(i);
            rectF.top = (this.XaxisPosition - this.XaxisMargin) - this.TotalBarHeight;
            if (rectF.bottom < rectF.top) {
                rectF.bottom = this.XaxisPosition - this.XaxisMargin;
            }
            if (f < rectF.right && rectF.left < f && f2 < rectF.bottom && f2 > rectF.top) {
                return true;
            }
        }
        return false;
    }

    private void drawBars(Canvas canvas) {
        float f = (this.TotalBarWidth - (this.BAR_SIDE_MARGIN * 49.0f)) / 48.0f;
        RectF rectF = new RectF();
        ArrayList<Float> arrayList = this.percentList;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<Float> it = this.percentList.iterator();
            int i = 1;
            while (it.hasNext()) {
                int i2 = i - 1;
                this.fgPaint.setColor(this.colors[i2]);
                float f2 = (float) i;
                rectF.set(this.YaxisPosition + (this.BAR_SIDE_MARGIN * f2) + (((float) i2) * f), (this.XaxisPosition - this.XaxisMargin) - (this.TotalBarHeight * it.next().floatValue()), this.YaxisPosition + ((this.BAR_SIDE_MARGIN + f) * f2), this.XaxisPosition - this.XaxisMargin);
                canvas.drawRect(rectF, this.fgPaint);
                this.barTextVisibility.get(i2).booleanValue();
                i++;
            }
        }
    }

    public void setBarTextSize(float f) {
        this.barTextPaint.setTextSize(f);
        postInvalidate();
    }

    public void setBarTextColor(int i) {
        this.barTextPaint.setColor(i);
        postInvalidate();
    }

    private void drawBottomText(Canvas canvas) {
        float f = (this.TotalBarWidth - (this.BAR_SIDE_MARGIN * 49.0f)) / 48.0f;
        ArrayList<String> arrayList = this.bottomTextList;
        if (arrayList != null && !arrayList.isEmpty()) {
            Iterator<String> it = this.bottomTextList.iterator();
            int i = 1;
            while (it.hasNext()) {
                String next = it.next();
                if (i != 0 && Integer.parseInt(next) % 2 == 0) {
                    float f2 = this.YaxisPosition;
                    float f3 = (float) i;
                    float f4 = this.BAR_SIDE_MARGIN;
                    canvas.drawText(this.bottomTextList.get((i / 2) - 1), f2 + (f3 * f4) + (f3 * f) + (f4 / 2.0f), ((float) getHeight()) - this.Pading, this.bottomtextPaint);
                }
                i++;
            }
        }
    }

    private void init(Context context) {
        int i;
        this.mContext = context;
        initTextPaint();
        initSideIconPaint();
        initDashPaint();
        initBgPaint();
        initData();
        initBarTextPaint();
        initBottomTextPaint();
        this.barTextVisibility = new ArrayList<>();
        this.titles = new String[]{"T1", "T2", "T3"};
        this.colors = new int[48];
        int i2 = 0;
        while (true) {
            if (i2 >= 48) {
                break;
            }
            this.barTextVisibility.add(true);
            this.colors[i2] = this.NORMAL_BAR_COLOR;
            i2++;
        }
        this.bottomTextList = new ArrayList<>();
        for (i = 1; i < 49; i++) {
            if (i < 10) {
                ArrayList<String> arrayList = this.bottomTextList;
                arrayList.add("0" + i);
            } else {
                ArrayList<String> arrayList2 = this.bottomTextList;
                arrayList2.add(i + "");
            }
        }
        this.TextMargin = (float) DensityUtil.dip2px(context, 0.0f);
        this.XaxisMargin = (float) DensityUtil.dip2px(context, 3.0f);
        this.Pading = (float) DensityUtil.dip2px(context, 10.0f);
        this.leftIconWidth = (float) DensityUtil.dip2px(context, 15.0f);
        this.LeftTextWidth = this.textPaint.measureText("T1 19.5");
        this.bottomTextHeight = Math.abs(this.bottomtextPaint.descent()) + Math.abs(this.bottomtextPaint.ascent());
        this.DefaultBarHeight = (float) DensityUtil.dip2px(this.mContext, 20.0f);
        this.DefaultBarWidth = (float) DensityUtil.dip2px(this.mContext, 20.0f);
        this.BAR_SIDE_MARGIN = (float) DensityUtil.dip2px(context, 5.0f);
        this.rectFlists = new ArrayList<>();
        this.path = new Path();
    }

    private void initBottomTextPaint() {
        this.bottomtextPaint = new TextPaint();
        this.bottomtextPaint.setColor(this.BOTTOM_TEXT_COLOR);
        this.bottomtextPaint.setTextSize((float) DensityUtil.sp2px(this.mContext, 12.0f));
        this.bottomtextPaint.setAntiAlias(true);
        this.bottomtextPaint.setTextAlign(Paint.Align.CENTER);
        this.bottomtextPaint.setDither(true);
    }

    private void initBarTextPaint() {
        this.barTextPaint = new TextPaint();
        this.barTextPaint.setColor(this.BAR_TEXT_COLOR);
        this.barTextPaint.setTextSize((float) DensityUtil.sp2px(this.mContext, 10.0f));
        this.barTextPaint.setAntiAlias(true);
        this.barTextPaint.setTextAlign(Paint.Align.CENTER);
        this.barTextPaint.setDither(true);
    }

    private void initBgPaint() {
        this.BACKGROUND_COLOR = Color.parseColor("#F6F6F6");
        this.bgPaint = new Paint();
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setColor(this.BACKGROUND_COLOR);
        this.fgPaint = new Paint(this.bgPaint);
        this.fgPaint.setColor(this.NORMAL_BAR_COLOR);
    }

    private void initTextPaint() {
        this.textSize = (float) DensityUtil.sp2px(this.mContext, 12.0f);
        this.textPaint = new Paint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(this.SIDE_TEXT_COLOR);
        this.textPaint.setTextSize(this.textSize);
    }

    private void initDashPaint() {
        this.dashPaint = new Paint();
        this.dashPaint.setAntiAlias(true);
        this.dashPaint.setColor(this.DASH_COLOR);
        this.dashPaint.setStyle(Paint.Style.STROKE);
        this.dashPaint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f}, 2.0f));
    }

    private void initSideIconPaint() {
        this.mFrostBitmap = ((BitmapDrawable) getResources().getDrawable(C1683R.C1684drawable.f3110t1)).getBitmap();
        this.mEconBitmap = ((BitmapDrawable) getResources().getDrawable(C1683R.C1684drawable.f3111t2)).getBitmap();
        this.mComfBitmap = ((BitmapDrawable) getResources().getDrawable(C1683R.C1684drawable.f3112t3)).getBitmap();
        this.sideIconPaint = new Paint();
        this.sideIconPaint.setAntiAlias(true);
    }

    public void setDashColor(int i) {
        this.dashPaint.setColor(i);
        postInvalidate();
    }

    private void drawBackgroup(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();
        float f = this.Pading;
        float f2 = (((float) height) - (f * 2.0f)) - this.bottomTextHeight;
        float f3 = this.TextMargin;
        this.TotalBarHeight = (f2 - f3) - this.XaxisMargin;
        double d = (double) this.TotalBarHeight;
        Double.isNaN(d);
        this.scale = (float) (d / 3.0d);
        this.YaxisPosition = f + f3 + this.LeftTextWidth + this.leftIconWidth;
        float f4 = this.Pading;
        this.XaxisPosition = ((((float) getHeight()) - f4) - this.bottomTextHeight) - this.TextMargin;
        float f5 = (float) width;
        float f6 = this.YaxisPosition;
        this.TotalBarWidth = ((f5 - f4) - f6) - this.LeftTextWidth;
        this.path.moveTo(f6, f4);
        this.path.lineTo(this.YaxisPosition, this.XaxisPosition);
        this.path.lineTo((f5 - this.Pading) - this.LeftTextWidth, this.XaxisPosition);
        canvas.drawPath(this.path, this.dashPaint);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - this.scale);
        this.path.lineTo((f5 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - this.scale);
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[0], (this.Pading + this.leftIconWidth) - 10.0f, ((this.XaxisPosition - this.XaxisMargin) - this.scale) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[0], ((f5 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - this.scale) + 5.0f, this.textPaint);
        new Rect(0, 0, this.mFrostBitmap.getWidth(), this.mFrostBitmap.getHeight());
        float f7 = this.Pading;
        float f8 = this.XaxisPosition;
        float f9 = this.XaxisMargin;
        float f10 = this.scale;
        float height2 = (this.leftIconWidth * (((float) this.mFrostBitmap.getHeight()) / ((float) this.mFrostBitmap.getWidth()))) / 2.0f;
        new RectF(f7, (((f8 - f9) - f10) - height2) - 10.0f, this.leftIconWidth + f7, (((f8 - f9) - f10) + height2) - 10.0f);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f));
        this.path.lineTo((f5 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f));
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[1], (this.Pading + this.leftIconWidth) - 10.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f)) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[1], ((f5 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f)) + 5.0f, this.textPaint);
        new Rect(0, 0, this.mEconBitmap.getWidth(), this.mEconBitmap.getHeight());
        float f11 = this.Pading;
        float f12 = this.XaxisPosition;
        float f13 = this.XaxisMargin;
        float f14 = this.scale;
        float height3 = (this.leftIconWidth * (((float) this.mEconBitmap.getHeight()) / ((float) this.mEconBitmap.getWidth()))) / 2.0f;
        new RectF(f11, (((f12 - f13) - (f14 * 2.0f)) - height3) - 10.0f, this.leftIconWidth + f11, (((f12 - f13) - (f14 * 2.0f)) + height3) - 10.0f);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f));
        this.path.lineTo((f5 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f));
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[2], (this.Pading + this.leftIconWidth) - 10.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f)) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[2], ((f5 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f)) + 5.0f, this.textPaint);
        new Rect(0, 0, this.mComfBitmap.getWidth(), this.mComfBitmap.getHeight());
        float f15 = this.Pading;
        float f16 = this.XaxisPosition;
        float f17 = this.XaxisMargin;
        float f18 = this.scale;
        float height4 = (this.leftIconWidth * (((float) this.mComfBitmap.getHeight()) / ((float) this.mComfBitmap.getWidth()))) / 2.0f;
        new RectF(f15, (((f16 - f17) - (f18 * 3.0f)) - height4) - 10.0f, this.leftIconWidth + f15, (((f16 - f17) - (f18 * 3.0f)) + height4) - 10.0f);
        this.path.reset();
        Path path2 = this.path;
        float f19 = this.Pading;
        path2.moveTo((f5 - f19) - this.LeftTextWidth, f19);
        this.path.lineTo((f5 - this.Pading) - this.LeftTextWidth, this.XaxisPosition);
        canvas.drawPath(this.path, this.dashPaint);
        this.path.reset();
        if (this.rectFlists.isEmpty() || this.rectFlists.get(0).bottom < this.rectFlists.get(0).top || this.rectFlists.get(0).right < this.rectFlists.get(0).left) {
            updataRectflist();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    private int measureHeight(int i) {
        float f = (this.Pading * 2.0f) + this.bottomTextHeight + this.TextMargin + this.XaxisMargin;
        setMinimumHeight((int) f);
        return getMeasurement(i, (int) (f + this.DefaultBarHeight));
    }

    private int measureWidth(int i) {
        float f = this.YaxisPosition + this.Pading + (this.BAR_SIDE_MARGIN * 49.0f);
        setMinimumWidth((int) f);
        return getMeasurement(i, (int) (f + (this.DefaultBarWidth * 48.0f)));
    }

    private int getMeasurement(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            return Math.min(i2, size);
        }
        if (mode == 1073741824 && size > i2) {
            return size;
        }
        return i2;
    }

    private void initData() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 48; i++) {
            arrayList.add(30);
        }
        setDataList(arrayList, 30);
    }

    public void setAllBarTextVisible(boolean z) {
        for (int i = 0; i < 48; i++) {
            this.barTextVisibility.set(i, Boolean.valueOf(z));
        }
    }

    public void setBarTextHidden(float f, float f2, boolean z) {
        if (isWithin24Hours(f) && isWithin24Hours(f2)) {
            setBarTextHidden(caculateIndex(f), caculateIndex(f2), z);
        }
    }

    public void setBarTextHidden(float f, boolean z) {
        if (isWithin24Hours(f)) {
            setBarTextHidden(caculateIndex(f), z);
        }
    }

    public void setBarTextHidden(int i, boolean z) {
        if (i >= 0 && i <= 47) {
            this.barTextVisibility.set(i, Boolean.valueOf(z));
        }
    }

    public boolean setBarTextHidden(int i, int i2, boolean z) {
        if (i < 0 || i > 47 || i2 < 0 || i2 > 47) {
            return false;
        }
        while (i < i2 + 1) {
            setBarTextHidden(i, z);
            i++;
        }
        return true;
    }

    public boolean isShowTextInFirstBar() {
        return this.isShowTextInFirstBar;
    }

    public void setShowTextInFirstBar(boolean z) {
        this.isShowTextInFirstBar = z;
    }

    public void setTempleture(float f, float f2, float f3) {
        if (isWithin24Hours(f) && isWithin24Hours(f2)) {
            setTempleture(caculateIndex(f), caculateIndex(f2), f3);
            updataRectflist();
            postInvalidate();
        }
    }

    public void setTempleture(int i, int i2, float f) {
        if (isWithinRange(0.0f, 47.0f, (float) i) && isWithinRange(0.0f, 47.0f, (float) i2)) {
            for (int i3 = i; i3 < i2 + 1; i3++) {
                float f2 = f / 40.0f;
                this.targetPercentList.set(i3, Float.valueOf(1.0f - f2));
                this.percentList.set(i3, Float.valueOf(f2));
                if (isShowTextInFirstBar()) {
                    this.barTextVisibility.set(i3, false);
                    if (i3 == i) {
                        this.barTextVisibility.set(i3, true);
                    }
                }
            }
            updataRectflist();
            postInvalidate();
        }
    }

    public void setTemperature(int i, int i2) {
        if (isWithinRange(0.0f, 47.0f, (float) i)) {
            if (i2 > 2) {
                i2 = 2;
            }
            float f = ((float) (i2 + 1)) / 3.0f;
            this.targetPercentList.set(i, Float.valueOf(1.0f - f));
            this.percentList.set(i, Float.valueOf(f));
            updataRectflist();
            postInvalidate();
        }
    }

    public boolean setBarColors(float f, float f2, int i) {
        if (!isWithin24Hours(f) || !isWithin24Hours(f2)) {
            return false;
        }
        int caculateIndex = caculateIndex(f2);
        for (int caculateIndex2 = caculateIndex(f); caculateIndex2 < caculateIndex + 1; caculateIndex2++) {
            this.colors[caculateIndex2] = i;
        }
        return true;
    }

    public boolean setBarColors(float f, int i) {
        if (!isWithin24Hours(f)) {
            return false;
        }
        int caculateIndex = caculateIndex(f);
        Log.i("ThermostatActivity", "fromIndex: " + caculateIndex);
        this.colors[caculateIndex] = i;
        return true;
    }

    public void setBottomTextSize(float f) {
        this.bottomtextPaint.setTextSize(f);
        this.bottomTextHeight = Math.abs(this.bottomtextPaint.descent()) + Math.abs(this.bottomtextPaint.ascent());
        postInvalidate();
    }

    public void setOnBarClickListener(OnBarClickListener onBarClickListener) {
        this.mOnBarClickListener = onBarClickListener;
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private boolean mConfigurationChanged;
        private boolean mIndeterminateProgressMode;
        private int mProgress;
        /* access modifiers changed from: private */
        public ArrayList<Float> percentList;
        /* access modifiers changed from: private */
        public ArrayList<RectF> rectFlists;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mProgress = parcel.readInt();
            boolean z = false;
            this.mIndeterminateProgressMode = parcel.readInt() == 1;
            this.mConfigurationChanged = parcel.readInt() == 1 ? true : z;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mProgress);
            parcel.writeInt(this.mIndeterminateProgressMode ? 1 : 0);
            parcel.writeInt(this.mConfigurationChanged ? 1 : 0);
        }
    }

    public void clearBarStatus() {
        int length = this.colors.length;
        for (int i = 0; i < length; i++) {
            this.colors[i] = this.NORMAL_BAR_COLOR;
        }
        postInvalidate();
    }

    public void setOnCanvasClickListener(OnCanvasClickListener onCanvasClickListener) {
        this.mOnCanvasClickListener = onCanvasClickListener;
    }
}
