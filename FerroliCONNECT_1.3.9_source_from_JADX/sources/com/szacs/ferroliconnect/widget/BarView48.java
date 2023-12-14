package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p000v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import java.util.ArrayList;
import java.util.Iterator;

public class BarView48 extends View {
    private int BACKGROUND_COLOR;
    private float BAR_SIDE_MARGIN;
    private int CANVAS_COLOR = Color.parseColor("#00000000");
    private float DefaultBarHeight;
    private float DefaultBarWidth;
    private float LeftTextWidth;
    private float Pading;
    private int TEXT_COLOR;
    private float TextMargin;
    private float TotalBarHeight;
    private float TotalBarWidth;
    private float XaxisMargin;
    private float XaxisPosition;
    private float YaxisPosition;
    private Runnable animator = new Runnable() {
        /* JADX WARNING: Removed duplicated region for block: B:12:0x00c2  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x00d5 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                r0 = 0
                r1 = 0
            L_0x0002:
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.targetPercentList
                int r2 = r2.size()
                if (r0 >= r2) goto L_0x00d9
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.percentList
                java.lang.Object r2 = r2.get(r0)
                java.lang.Float r2 = (java.lang.Float) r2
                float r2 = r2.floatValue()
                com.szacs.ferroliconnect.widget.BarView48 r3 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r3 = r3.targetPercentList
                java.lang.Object r3 = r3.get(r0)
                java.lang.Float r3 = (java.lang.Float) r3
                float r3 = r3.floatValue()
                r4 = 1
                r5 = 1017370378(0x3ca3d70a, float:0.02)
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 >= 0) goto L_0x0056
                com.szacs.ferroliconnect.widget.BarView48 r1 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r1 = r1.percentList
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.percentList
                java.lang.Object r2 = r2.get(r0)
                java.lang.Float r2 = (java.lang.Float) r2
                float r2 = r2.floatValue()
                float r2 = r2 + r5
                java.lang.Float r2 = java.lang.Float.valueOf(r2)
                r1.set(r0, r2)
            L_0x0054:
                r1 = 1
                goto L_0x0099
            L_0x0056:
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.percentList
                java.lang.Object r2 = r2.get(r0)
                java.lang.Float r2 = (java.lang.Float) r2
                float r2 = r2.floatValue()
                com.szacs.ferroliconnect.widget.BarView48 r3 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r3 = r3.targetPercentList
                java.lang.Object r3 = r3.get(r0)
                java.lang.Float r3 = (java.lang.Float) r3
                float r3 = r3.floatValue()
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 <= 0) goto L_0x0099
                com.szacs.ferroliconnect.widget.BarView48 r1 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r1 = r1.percentList
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.percentList
                java.lang.Object r2 = r2.get(r0)
                java.lang.Float r2 = (java.lang.Float) r2
                float r2 = r2.floatValue()
                float r2 = r2 - r5
                java.lang.Float r2 = java.lang.Float.valueOf(r2)
                r1.set(r0, r2)
                goto L_0x0054
            L_0x0099:
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.targetPercentList
                java.lang.Object r2 = r2.get(r0)
                java.lang.Float r2 = (java.lang.Float) r2
                float r2 = r2.floatValue()
                com.szacs.ferroliconnect.widget.BarView48 r3 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r3 = r3.percentList
                java.lang.Object r3 = r3.get(r0)
                java.lang.Float r3 = (java.lang.Float) r3
                float r3 = r3.floatValue()
                float r2 = r2 - r3
                float r2 = java.lang.Math.abs(r2)
                int r2 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                if (r2 >= 0) goto L_0x00d5
                com.szacs.ferroliconnect.widget.BarView48 r2 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r2 = r2.percentList
                com.szacs.ferroliconnect.widget.BarView48 r3 = com.szacs.ferroliconnect.widget.BarView48.this
                java.util.ArrayList r3 = r3.targetPercentList
                java.lang.Object r3 = r3.get(r0)
                r2.set(r0, r3)
            L_0x00d5:
                int r0 = r0 + 1
                goto L_0x0002
            L_0x00d9:
                if (r1 == 0) goto L_0x00e2
                com.szacs.ferroliconnect.widget.BarView48 r0 = com.szacs.ferroliconnect.widget.BarView48.this
                r1 = 20
                r0.postDelayed(r6, r1)
            L_0x00e2:
                com.szacs.ferroliconnect.widget.BarView48 r0 = com.szacs.ferroliconnect.widget.BarView48.this
                r0.invalidate()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.widget.BarView48.C19501.run():void");
        }
    };
    private TextPaint barTextPaint;
    private ArrayList<Boolean> barTextVisibility;
    private Paint bgPaint;
    private float bottomTextHeight;
    private ArrayList<String> bottomTextList;
    private TextPaint bottomtextPaint;
    private int[] colors;
    private Paint dashPaint;
    private int downx = 0;
    private int downy = 0;
    private Paint fgPaint;
    private boolean isShowTextInFirstBar;
    private Context mContext;
    private OnBarClickListener mOnBarClickListener;
    private OnCanvasClickListener mOnCanvasClickListener;
    private WindowManager manager;
    private Point outSize;
    private Path path;
    /* access modifiers changed from: private */
    public ArrayList<Float> percentList;
    private ArrayList<RectF> rectFlists;
    private float scale;
    /* access modifiers changed from: private */
    public ArrayList<Float> targetPercentList;
    private Paint textPaint;
    private float textSize;
    private String[] titles;

    public interface OnBarClickListener {
        void onBarClick(float f, float f2, float f3);
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

    public BarView48(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public BarView48(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public BarView48(Context context) {
        super(context);
        init(context);
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

    public void setLeftTextColor(int i) {
        this.textPaint.setColor(i);
        postInvalidate();
    }

    public void setLeftTextSize(float f) {
        this.textPaint.setTextSize(f);
        this.LeftTextWidth = this.textPaint.measureText(this.titles[0]);
        if (Math.abs(this.textPaint.ascent()) > ((float) MyUtils.dip2px(this.mContext, 10.0f))) {
            this.Pading = Math.abs(this.textPaint.ascent()) + 3.0f;
        } else {
            this.Pading = (float) MyUtils.dip2px(this.mContext, 10.0f);
        }
        postInvalidate();
    }

    public void setLeftText(String[] strArr) {
        for (int i = 0; i < 4; i++) {
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

    /* access modifiers changed from: private */
    public void updataRectflist() {
        ArrayList<RectF> arrayList = this.rectFlists;
        if (arrayList != null && !arrayList.isEmpty()) {
            this.rectFlists.clear();
        }
        float f = (this.TotalBarWidth - (this.BAR_SIDE_MARGIN * 49.0f)) / 48.0f;
        for (int i = 1; i < this.percentList.size() + 1; i++) {
            RectF rectF = new RectF();
            int i2 = i - 1;
            float floatValue = this.percentList.get(i2).floatValue();
            float f2 = this.YaxisPosition;
            float f3 = this.BAR_SIDE_MARGIN;
            float f4 = (float) i;
            float f5 = (f3 * f4) + f2 + (((float) i2) * f);
            float f6 = this.XaxisPosition;
            float f7 = this.XaxisMargin;
            rectF.set(f5, (f6 - f7) - (this.TotalBarHeight * floatValue), f2 + ((f3 + f) * f4), f6 - f7);
            this.rectFlists.add(rectF);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        ArrayList unused = savedState.percentList = this.percentList;
        ArrayList unused2 = savedState.rectFlists = this.rectFlists;
        Log.i("BarView48", "onSaveInstanceState");
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            SavedState savedState = (SavedState) parcelable;
            this.percentList = savedState.percentList;
            this.rectFlists = savedState.rectFlists;
            Log.i("BarView48", "onRestoreInstanceState");
            System.out.println(this.percentList);
            System.out.println(this.rectFlists);
            super.onRestoreInstanceState(savedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    private float[] findBar(float f, float f2) {
        int i;
        new RectF();
        int i2 = 0;
        float f3 = 0.0f;
        for (int i3 = 0; i3 < this.rectFlists.size(); i3++) {
            RectF rectF = this.rectFlists.get(i3);
            if (f < rectF.right && rectF.left < f && f2 < rectF.bottom && f2 > rectF.top) {
                f3 = this.percentList.get(i3).floatValue();
                i2 = i3;
            }
        }
        Log.i("findBar", "index==" + i2);
        int i4 = i2;
        int i5 = 0;
        while (i4 < this.percentList.size() && this.percentList.get(i4).floatValue() == f3) {
            i5 = i4;
            i4++;
        }
        while (true) {
            if (i2 <= 0) {
                i = 0;
                break;
            } else if (this.percentList.get(i2).floatValue() != f3) {
                i = i2 + 1;
                break;
            } else {
                i2--;
            }
        }
        return new float[]{(float) i, (float) i5, f3};
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
            Log.i("MotionEventup", "upx==" + x + ":upy==" + y + ":");
            if (this.mOnBarClickListener != null && region.contains(this.downx, this.downy)) {
                float f = (float) x;
                float f2 = (float) y;
                if (isWithinBar(f, f2)) {
                    float[] findBar = findBar(f, f2);
                    float f3 = findBar[0];
                    float f4 = findBar[1];
                    float f5 = findBar[2] * 40.0f;
                    Log.i("MotionEvent", f3 + ":" + f4 + ":" + f5);
                    this.mOnBarClickListener.onBarClick(((f3 + 1.0f) / 2.0f) - 0.5f, (f4 + 1.0f) / 2.0f, f5);
                }
            }
            if (this.mOnCanvasClickListener != null && region.contains(this.downx, this.downy)) {
                this.mOnCanvasClickListener.onCanvasClick();
            }
        }
        return true;
    }

    private boolean isWithinBar(float f, float f2) {
        new RectF();
        for (int i = 0; i < this.rectFlists.size(); i++) {
            RectF rectF = this.rectFlists.get(i);
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
                Float next = it.next();
                int i2 = i - 1;
                this.fgPaint.setColor(this.colors[i2]);
                float f2 = (float) i;
                float f3 = ((float) i2) * f;
                rectF.set(this.YaxisPosition + (this.BAR_SIDE_MARGIN * f2) + f3, (this.XaxisPosition - this.XaxisMargin) - (this.TotalBarHeight * next.floatValue()), this.YaxisPosition + ((this.BAR_SIDE_MARGIN + f) * f2), this.XaxisPosition - this.XaxisMargin);
                canvas.drawRect(rectF, this.fgPaint);
                if (!this.barTextVisibility.get(i2).booleanValue()) {
                    canvas.drawText((((float) Math.round((next.floatValue() * 40.0f) * 100.0f)) / 100.0f) + "", this.YaxisPosition + (this.BAR_SIDE_MARGIN * f2) + (f / 2.0f) + f3, ((this.XaxisPosition - this.XaxisMargin) - ((this.TotalBarHeight * next.floatValue()) * 1.0f)) - 5.0f, this.barTextPaint);
                }
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
        initDashPaint();
        initBgPaint();
        initData();
        initBarTextPaint();
        initBottomTextPaint();
        this.barTextVisibility = new ArrayList<>();
        this.titles = "10° 20° 30° 40°".split(" ");
        this.colors = new int[48];
        int i2 = 0;
        while (true) {
            if (i2 >= 48) {
                break;
            }
            this.barTextVisibility.add(true);
            this.colors[i2] = ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_orange_alpha);
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
        this.TextMargin = (float) MyUtils.dip2px(context, 5.0f);
        this.XaxisMargin = (float) MyUtils.dip2px(context, 3.0f);
        this.Pading = (float) MyUtils.dip2px(context, 10.0f);
        this.LeftTextWidth = this.textPaint.measureText(this.titles[0]);
        this.bottomTextHeight = Math.abs(this.bottomtextPaint.descent()) + Math.abs(this.bottomtextPaint.ascent());
        this.DefaultBarHeight = (float) MyUtils.dip2px(this.mContext, 20.0f);
        this.DefaultBarWidth = (float) MyUtils.dip2px(this.mContext, 20.0f);
        this.BAR_SIDE_MARGIN = (float) MyUtils.dip2px(context, 5.0f);
        this.rectFlists = new ArrayList<>();
        this.path = new Path();
        post(new Runnable() {
            public void run() {
                BarView48.this.updataRectflist();
            }
        });
    }

    private void initBottomTextPaint() {
        this.bottomtextPaint = new TextPaint();
        this.bottomtextPaint.setColor(ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_green_text));
        this.bottomtextPaint.setTextSize((float) MyUtils.sp2px(this.mContext, 10.0f));
        this.bottomtextPaint.setAntiAlias(true);
        this.bottomtextPaint.setFakeBoldText(true);
        this.bottomtextPaint.setTextAlign(Paint.Align.CENTER);
        this.bottomtextPaint.setTypeface(MainApplication.typeface);
    }

    private void initBarTextPaint() {
        this.barTextPaint = new TextPaint();
        this.barTextPaint.setColor(ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_green_text));
        this.barTextPaint.setTextSize((float) MyUtils.sp2px(this.mContext, 10.0f));
        this.barTextPaint.setAlpha(254);
        this.barTextPaint.setAntiAlias(true);
        this.barTextPaint.setTextAlign(Paint.Align.CENTER);
        this.barTextPaint.setStrokeWidth(3.0f);
        this.barTextPaint.setFakeBoldText(true);
        this.barTextPaint.setDither(true);
        this.barTextPaint.setTypeface(MainApplication.typeface);
    }

    private void initBgPaint() {
        this.BACKGROUND_COLOR = Color.parseColor("#F6F6F6");
        this.bgPaint = new Paint();
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setColor(this.BACKGROUND_COLOR);
        this.fgPaint = new Paint(this.bgPaint);
        this.fgPaint.setColor(ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_orange_alpha));
    }

    private void initTextPaint() {
        this.TEXT_COLOR = ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_green_text);
        this.textSize = (float) MyUtils.sp2px(this.mContext, 10.0f);
        this.textPaint = new Paint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(this.TEXT_COLOR);
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setTypeface(MainApplication.typeface);
    }

    private void initDashPaint() {
        this.dashPaint = new Paint();
        this.dashPaint.setAntiAlias(true);
        this.dashPaint.setColor(ContextCompat.getColor(getContext(), C1683R.color.cloudwarm_green_text));
        this.dashPaint.setStyle(Paint.Style.STROKE);
        this.dashPaint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f, 10.0f, 5.0f}, 2.0f));
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
        this.scale = (float) (d / 4.0d);
        this.YaxisPosition = f + f3 + this.LeftTextWidth;
        this.XaxisPosition = ((((float) getHeight()) - this.Pading) - this.bottomTextHeight) - this.TextMargin;
        Log.i("ondraw", this.Pading + ":" + this.bottomTextHeight + ":" + this.TextMargin);
        float f4 = (float) width;
        float f5 = this.Pading;
        float f6 = this.YaxisPosition;
        this.TotalBarWidth = ((f4 - f5) - f6) - this.LeftTextWidth;
        this.path.moveTo(f6, f5);
        this.path.lineTo(this.YaxisPosition, this.XaxisPosition);
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, this.XaxisPosition);
        canvas.drawPath(this.path, this.dashPaint);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - this.scale);
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - this.scale);
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[0], this.Pading, ((this.XaxisPosition - this.XaxisMargin) - this.scale) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[0], ((f4 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - this.scale) + 5.0f, this.textPaint);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f));
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f));
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[1], this.Pading, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f)) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[1], ((f4 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 2.0f)) + 5.0f, this.textPaint);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f));
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f));
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[2], this.Pading, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f)) + 5.0f, this.textPaint);
        canvas.drawText(this.titles[2], ((f4 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 3.0f)) + 5.0f, this.textPaint);
        this.path.reset();
        this.path.moveTo(this.YaxisPosition, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 4.0f));
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, (this.XaxisPosition - this.XaxisMargin) - (this.scale * 4.0f));
        canvas.drawPath(this.path, this.dashPaint);
        canvas.drawText(this.titles[3], this.Pading, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 4.0f)) + this.textPaint.descent(), this.textPaint);
        canvas.drawText(this.titles[3], ((f4 - this.Pading) - this.LeftTextWidth) + 3.0f, ((this.XaxisPosition - this.XaxisMargin) - (this.scale * 4.0f)) + this.textPaint.descent(), this.textPaint);
        this.path.reset();
        Path path2 = this.path;
        float f7 = this.Pading;
        path2.moveTo((f4 - f7) - this.LeftTextWidth, f7);
        this.path.lineTo((f4 - this.Pading) - this.LeftTextWidth, this.XaxisPosition);
        canvas.drawPath(this.path, this.dashPaint);
        this.path.reset();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.i("BarView48", "onConfigurationChanged");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Log.i("BarView48", "onLayout;" + z + ":" + i + ":" + i2 + ":" + i3 + ":" + i4);
        updataRectflist();
        super.onLayout(z, i, i2, i3, i4);
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
            arrayList.add(25);
        }
        setDataList(arrayList, 40);
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
            Log.i("setTemperature", i + ":" + i2 + "temp" + f);
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
        this.colors[caculateIndex(f)] = i;
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

    public void setOnCanvasClickListener(OnCanvasClickListener onCanvasClickListener) {
        this.mOnCanvasClickListener = onCanvasClickListener;
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
}
