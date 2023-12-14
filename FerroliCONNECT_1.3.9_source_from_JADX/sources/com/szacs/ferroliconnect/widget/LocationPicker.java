package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.util.LanguageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocationPicker extends View {
    public static final float MARGIN_ALPHA = 2.8f;
    public static final float SPEED = 2.0f;
    public static final String TAG = "PickerView";
    private boolean isInit = false;
    private String language = "";
    private int mColorText = 3355443;
    private int mCurrentSelected;
    private List<LocationGroup> mDataList;
    private float mLastDownY;
    private float mMaxTextAlpha = 255.0f;
    private float mMaxTextSize = 20.0f;
    private float mMinTextAlpha = 120.0f;
    private float mMinTextSize = 10.0f;
    /* access modifiers changed from: private */
    public float mMoveLen = 0.0f;
    private TextPaint mPaint;
    private onSelectListener mSelectListener;
    /* access modifiers changed from: private */
    public MyTimerTask mTask;
    private int mViewHeight;
    private int mViewWidth;
    private Timer timer;
    Handler updateHandler = new Handler() {
        public void handleMessage(Message message) {
            if (Math.abs(LocationPicker.this.mMoveLen) < 2.0f) {
                float unused = LocationPicker.this.mMoveLen = 0.0f;
                if (LocationPicker.this.mTask != null) {
                    LocationPicker.this.mTask.cancel();
                    MyTimerTask unused2 = LocationPicker.this.mTask = null;
                    LocationPicker.this.performSelect();
                }
            } else {
                LocationPicker locationPicker = LocationPicker.this;
                float unused3 = locationPicker.mMoveLen = locationPicker.mMoveLen - ((LocationPicker.this.mMoveLen / Math.abs(LocationPicker.this.mMoveLen)) * 2.0f);
            }
            LocationPicker.this.invalidate();
        }
    };

    public interface onSelectListener {
        void onSelect(LocationGroup locationGroup);
    }

    public LocationPicker(Context context) {
        super(context);
        init();
    }

    public LocationPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public void setOnSelectListener(onSelectListener onselectlistener) {
        this.mSelectListener = onselectlistener;
    }

    /* access modifiers changed from: private */
    public void performSelect() {
        onSelectListener onselectlistener = this.mSelectListener;
        if (onselectlistener != null) {
            onselectlistener.onSelect(this.mDataList.get(this.mCurrentSelected));
        }
    }

    public void setData(List<LocationGroup> list) {
        this.mDataList = list;
        this.mCurrentSelected = list.size() / 2;
        this.mSelectListener.onSelect(list.get(this.mCurrentSelected));
        invalidate();
    }

    public void setSelected(int i) {
        this.mCurrentSelected = i;
        int size = (this.mDataList.size() / 2) - this.mCurrentSelected;
        int i2 = 0;
        if (size < 0) {
            while (i2 < (-size)) {
                moveHeadToTail();
                this.mCurrentSelected--;
                i2++;
            }
        } else if (size > 0) {
            while (i2 < size) {
                moveTailToHead();
                this.mCurrentSelected++;
                i2++;
            }
        }
        invalidate();
    }

    public void setSelected(String str) {
        for (int i = 0; i < this.mDataList.size(); i++) {
            if (this.mDataList.get(i).equals(str)) {
                setSelected(i);
                return;
            }
        }
    }

    private void moveHeadToTail() {
        this.mDataList.remove(0);
        this.mDataList.add(this.mDataList.get(0));
    }

    private void moveTailToHead() {
        List<LocationGroup> list = this.mDataList;
        List<LocationGroup> list2 = this.mDataList;
        list2.remove(list2.size() - 1);
        this.mDataList.add(0, list.get(list.size() - 1));
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mViewHeight = getMeasuredHeight();
        this.mViewWidth = getMeasuredWidth();
        this.mMaxTextSize = ((float) this.mViewWidth) / 8.0f;
        this.mMinTextSize = this.mMaxTextSize / 2.0f;
        this.isInit = true;
        invalidate();
    }

    private void init() {
        this.timer = new Timer();
        this.mDataList = new ArrayList();
        this.mPaint = new TextPaint(1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mColorText);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<LocationGroup> list = this.mDataList;
        if (list != null && list.size() != 0 && this.isInit) {
            drawData(canvas);
        }
    }

    private void drawData(Canvas canvas) {
        String str;
        float parabola = parabola(((float) this.mViewHeight) / 4.0f, this.mMoveLen);
        float f = this.mMinTextSize;
        this.mPaint.setTextSize(f);
        this.mPaint.setColor(getResources().getColor(C1683R.color.cloudwarm_orange));
        TextPaint textPaint = this.mPaint;
        float f2 = this.mMaxTextAlpha;
        float f3 = this.mMinTextAlpha;
        textPaint.setAlpha((int) (((f2 - f3) * parabola) + f3));
        double d = (double) this.mViewWidth;
        Double.isNaN(d);
        float f4 = (float) (d / 2.0d);
        double d2 = (double) this.mViewHeight;
        Double.isNaN(d2);
        double d3 = (double) this.mMoveLen;
        Double.isNaN(d3);
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        double d4 = (double) ((float) ((d2 / 2.0d) + d3));
        double d5 = (double) fontMetricsInt.bottom;
        Double.isNaN(d5);
        double d6 = (double) fontMetricsInt.top;
        Double.isNaN(d6);
        Double.isNaN(d4);
        float f5 = (float) (d4 - ((d5 / 2.0d) + (d6 / 2.0d)));
        int i = this.mCurrentSelected;
        if (LanguageUtil.getSetLanguageLocale(getContext()).getLanguage().equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            str = this.mDataList.get(i).getName().getZh();
        } else {
            str = this.mDataList.get(i).getName().getEn();
        }
        lineFeed(canvas, str, f5, f4, f);
        for (int i2 = 1; this.mCurrentSelected - i2 >= 0; i2++) {
            drawOtherText(canvas, i2, -1);
        }
        for (int i3 = 1; this.mCurrentSelected + i3 < this.mDataList.size(); i3++) {
            drawOtherText(canvas, i3, 1);
        }
    }

    private void drawOtherText(Canvas canvas, int i, int i2) {
        String str;
        float f = (float) i2;
        float f2 = (this.mMinTextSize * 2.8f * ((float) i)) + (this.mMoveLen * f);
        float parabola = parabola(((float) this.mViewHeight) / 4.0f, f2);
        float f3 = this.mMinTextSize;
        this.mPaint.setTextSize(f3);
        this.mPaint.setColor(getResources().getColor(C1683R.color.cloudwarm_green));
        TextPaint textPaint = this.mPaint;
        float f4 = this.mMaxTextAlpha;
        float f5 = this.mMinTextAlpha;
        textPaint.setAlpha((int) (((f4 - f5) * parabola) + f5));
        double d = (double) this.mViewHeight;
        Double.isNaN(d);
        double d2 = (double) (f * f2);
        Double.isNaN(d2);
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        double d3 = (double) ((float) ((d / 2.0d) + d2));
        double d4 = (double) fontMetricsInt.bottom;
        Double.isNaN(d4);
        double d5 = (double) fontMetricsInt.top;
        Double.isNaN(d5);
        Double.isNaN(d3);
        float f6 = (float) (d3 - ((d4 / 2.0d) + (d5 / 2.0d)));
        int i3 = this.mCurrentSelected + (i2 * i);
        if (LanguageUtil.getSetLanguageLocale(getContext()).getLanguage().equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            str = this.mDataList.get(i3).getName().getZh();
        } else {
            str = this.mDataList.get(i3).getName().getEn();
        }
        double d6 = (double) this.mViewWidth;
        Double.isNaN(d6);
        lineFeed(canvas, str, f6, (float) (d6 / 2.0d), f3);
    }

    private void lineFeed(Canvas canvas, String str, float f, float f2, float f3) {
        String str2 = str;
        int breakText = this.mPaint.breakText(str2, 0, str.length(), true, (float) this.mViewWidth, (float[]) null);
        canvas.drawText(str.substring(0, breakText), f2, f, this.mPaint);
        String substring = str.substring(breakText, str.length());
        if (substring.length() > 0) {
            lineFeed(canvas, substring, f + f3 + 5.0f, f2, f3);
        }
    }

    private float parabola(float f, float f2) {
        float pow = (float) (1.0d - Math.pow((double) (f2 / f), 2.0d));
        if (pow < 0.0f) {
            return 0.0f;
        }
        return pow;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            doDown(motionEvent);
        } else if (actionMasked == 1) {
            doUp(motionEvent);
        } else if (actionMasked == 2) {
            doMove(motionEvent);
        }
        return true;
    }

    private void doDown(MotionEvent motionEvent) {
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        this.mLastDownY = motionEvent.getY();
    }

    private void doMove(MotionEvent motionEvent) {
        this.mMoveLen += motionEvent.getY() - this.mLastDownY;
        float f = this.mMoveLen;
        float f2 = this.mMinTextSize;
        if (f > (f2 * 2.8f) / 2.0f) {
            moveTailToHead();
            this.mMoveLen -= this.mMinTextSize * 2.8f;
        } else if (f < (f2 * -2.8f) / 2.0f) {
            moveHeadToTail();
            this.mMoveLen += this.mMinTextSize * 2.8f;
        }
        this.mLastDownY = motionEvent.getY();
        invalidate();
    }

    private void doUp(MotionEvent motionEvent) {
        if (((double) Math.abs(this.mMoveLen)) < 1.0E-4d) {
            this.mMoveLen = 0.0f;
            return;
        }
        MyTimerTask myTimerTask = this.mTask;
        if (myTimerTask != null) {
            myTimerTask.cancel();
            this.mTask = null;
        }
        this.mTask = new MyTimerTask(this.updateHandler);
        this.timer.schedule(this.mTask, 0, 10);
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler2) {
            this.handler = handler2;
        }

        public void run() {
            Handler handler2 = this.handler;
            handler2.sendMessage(handler2.obtainMessage());
        }
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }
}
