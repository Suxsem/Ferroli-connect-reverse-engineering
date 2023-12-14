package com.contrarywind.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.interfaces.IPickerViewData;
import com.contrarywind.listener.LoopViewGestureListener;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.timer.InertiaTimerTask;
import com.contrarywind.timer.MessageHandler;
import com.contrarywind.timer.SmoothScrollTimerTask;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WheelView extends View {
    private static final float SCALE_CONTENT = 0.8f;
    private static final int VELOCITY_FLING = 5;
    private float CENTER_CONTENT_OFFSET;
    private final float DEFAULT_TEXT_TARGET_SKEWX;
    private WheelAdapter adapter;
    private float centerY;
    private int change;
    private Context context;
    private int dividerColor;
    private DividerType dividerType;
    private int drawCenterContentStart;
    private int drawOutContentStart;
    private float firstLineY;
    private GestureDetector gestureDetector;
    private Handler handler;
    private int initPosition;
    private boolean isCenterLabel;
    private boolean isLoop;
    private boolean isOptions;
    private float itemHeight;
    private int itemsVisible;
    private String label;
    private float lineSpacingMultiplier;
    private ScheduledExecutorService mExecutor;
    private ScheduledFuture<?> mFuture;
    private int mGravity;
    private int mOffset;
    private int maxTextHeight;
    private int maxTextWidth;
    private int measuredHeight;
    private int measuredWidth;
    /* access modifiers changed from: private */
    public OnItemSelectedListener onItemSelectedListener;
    private Paint paintCenterText;
    private Paint paintIndicator;
    private Paint paintOuterText;
    private int preCurrentIndex;
    private float previousY;
    private int radius;
    private float secondLineY;
    private int selectedItem;
    private long startTime;
    private int textColorCenter;
    private int textColorOut;
    private int textSize;
    private int textXOffset;
    private float totalScrollY;
    private Typeface typeface;
    private int widthMeasureSpec;

    public enum ACTION {
        CLICK,
        FLING,
        DAGGLE
    }

    public enum DividerType {
        FILL,
        WRAP
    }

    public WheelView(Context context2) {
        this(context2, (AttributeSet) null);
    }

    public WheelView(Context context2, AttributeSet attributeSet) {
        super(context2, attributeSet);
        this.isOptions = false;
        this.isCenterLabel = true;
        this.mExecutor = Executors.newSingleThreadScheduledExecutor();
        this.typeface = Typeface.MONOSPACE;
        this.lineSpacingMultiplier = 1.6f;
        this.itemsVisible = 11;
        this.mOffset = 0;
        this.previousY = 0.0f;
        this.startTime = 0;
        this.mGravity = 17;
        this.drawCenterContentStart = 0;
        this.drawOutContentStart = 0;
        this.DEFAULT_TEXT_TARGET_SKEWX = 0.5f;
        this.textSize = getResources().getDimensionPixelSize(C0950R.dimen.pickerview_textsize);
        float f = getResources().getDisplayMetrics().density;
        if (f < 1.0f) {
            this.CENTER_CONTENT_OFFSET = 2.4f;
        } else if (1.0f <= f && f < 2.0f) {
            this.CENTER_CONTENT_OFFSET = 3.6f;
        } else if (1.0f <= f && f < 2.0f) {
            this.CENTER_CONTENT_OFFSET = 4.5f;
        } else if (2.0f <= f && f < 3.0f) {
            this.CENTER_CONTENT_OFFSET = 6.0f;
        } else if (f >= 3.0f) {
            this.CENTER_CONTENT_OFFSET = f * 2.5f;
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, C0950R.styleable.pickerview, 0, 0);
            this.mGravity = obtainStyledAttributes.getInt(C0950R.styleable.pickerview_wheelview_gravity, 17);
            this.textColorOut = obtainStyledAttributes.getColor(C0950R.styleable.pickerview_wheelview_textColorOut, -5723992);
            this.textColorCenter = obtainStyledAttributes.getColor(C0950R.styleable.pickerview_wheelview_textColorCenter, -14013910);
            this.dividerColor = obtainStyledAttributes.getColor(C0950R.styleable.pickerview_wheelview_dividerColor, -2763307);
            this.textSize = obtainStyledAttributes.getDimensionPixelOffset(C0950R.styleable.pickerview_wheelview_textSize, this.textSize);
            this.lineSpacingMultiplier = obtainStyledAttributes.getFloat(C0950R.styleable.pickerview_wheelview_lineSpacingMultiplier, this.lineSpacingMultiplier);
            obtainStyledAttributes.recycle();
        }
        judgeLineSpace();
        initLoopView(context2);
    }

    private void judgeLineSpace() {
        float f = this.lineSpacingMultiplier;
        if (f < 1.0f) {
            this.lineSpacingMultiplier = 1.0f;
        } else if (f > 4.0f) {
            this.lineSpacingMultiplier = 4.0f;
        }
    }

    private void initLoopView(Context context2) {
        this.context = context2;
        this.handler = new MessageHandler(this);
        this.gestureDetector = new GestureDetector(context2, new LoopViewGestureListener(this));
        this.gestureDetector.setIsLongpressEnabled(false);
        this.isLoop = true;
        this.totalScrollY = 0.0f;
        this.initPosition = -1;
        initPaints();
    }

    private void initPaints() {
        this.paintOuterText = new Paint();
        this.paintOuterText.setColor(this.textColorOut);
        this.paintOuterText.setAntiAlias(true);
        this.paintOuterText.setTypeface(this.typeface);
        this.paintOuterText.setTextSize((float) this.textSize);
        this.paintCenterText = new Paint();
        this.paintCenterText.setColor(this.textColorCenter);
        this.paintCenterText.setAntiAlias(true);
        this.paintCenterText.setTextScaleX(1.1f);
        this.paintCenterText.setTypeface(this.typeface);
        this.paintCenterText.setTextSize((float) this.textSize);
        this.paintIndicator = new Paint();
        this.paintIndicator.setColor(this.dividerColor);
        this.paintIndicator.setAntiAlias(true);
        setLayerType(1, (Paint) null);
    }

    private void remeasure() {
        if (this.adapter != null) {
            measureTextWidthHeight();
            int i = (int) (this.itemHeight * ((float) (this.itemsVisible - 1)));
            double d = (double) (i * 2);
            Double.isNaN(d);
            this.measuredHeight = (int) (d / 3.141592653589793d);
            double d2 = (double) i;
            Double.isNaN(d2);
            this.radius = (int) (d2 / 3.141592653589793d);
            this.measuredWidth = View.MeasureSpec.getSize(this.widthMeasureSpec);
            int i2 = this.measuredHeight;
            float f = this.itemHeight;
            this.firstLineY = (((float) i2) - f) / 2.0f;
            this.secondLineY = (((float) i2) + f) / 2.0f;
            this.centerY = (this.secondLineY - ((f - ((float) this.maxTextHeight)) / 2.0f)) - this.CENTER_CONTENT_OFFSET;
            if (this.initPosition == -1) {
                if (this.isLoop) {
                    this.initPosition = (this.adapter.getItemsCount() + 1) / 2;
                } else {
                    this.initPosition = 0;
                }
            }
            this.preCurrentIndex = this.initPosition;
        }
    }

    private void measureTextWidthHeight() {
        Rect rect = new Rect();
        for (int i = 0; i < this.adapter.getItemsCount(); i++) {
            String contentText = getContentText(this.adapter.getItem(i));
            this.paintCenterText.getTextBounds(contentText, 0, contentText.length(), rect);
            int width = rect.width();
            if (width > this.maxTextWidth) {
                this.maxTextWidth = width;
            }
            this.paintCenterText.getTextBounds("星期", 0, 2, rect);
            this.maxTextHeight = rect.height() + 2;
        }
        this.itemHeight = this.lineSpacingMultiplier * ((float) this.maxTextHeight);
    }

    public void smoothScroll(ACTION action) {
        cancelFuture();
        if (action == ACTION.FLING || action == ACTION.DAGGLE) {
            float f = this.totalScrollY;
            float f2 = this.itemHeight;
            this.mOffset = (int) (((f % f2) + f2) % f2);
            int i = this.mOffset;
            if (((float) i) > f2 / 2.0f) {
                this.mOffset = (int) (f2 - ((float) i));
            } else {
                this.mOffset = -i;
            }
        }
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new SmoothScrollTimerTask(this, this.mOffset), 0, 10, TimeUnit.MILLISECONDS);
    }

    public final void scrollBy(float f) {
        cancelFuture();
        this.mFuture = this.mExecutor.scheduleWithFixedDelay(new InertiaTimerTask(this, f), 0, 5, TimeUnit.MILLISECONDS);
    }

    public void cancelFuture() {
        ScheduledFuture<?> scheduledFuture = this.mFuture;
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            this.mFuture.cancel(true);
            this.mFuture = null;
        }
    }

    public final void setCyclic(boolean z) {
        this.isLoop = z;
    }

    public final void setTypeface(Typeface typeface2) {
        this.typeface = typeface2;
        this.paintOuterText.setTypeface(this.typeface);
        this.paintCenterText.setTypeface(this.typeface);
    }

    public final void setTextSize(float f) {
        if (f > 0.0f) {
            this.textSize = (int) (this.context.getResources().getDisplayMetrics().density * f);
            this.paintOuterText.setTextSize((float) this.textSize);
            this.paintCenterText.setTextSize((float) this.textSize);
        }
    }

    public final void setCurrentItem(int i) {
        this.selectedItem = i;
        this.initPosition = i;
        this.totalScrollY = 0.0f;
        invalidate();
    }

    public final void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener2) {
        this.onItemSelectedListener = onItemSelectedListener2;
    }

    public final void setAdapter(WheelAdapter wheelAdapter) {
        this.adapter = wheelAdapter;
        remeasure();
        invalidate();
    }

    public final WheelAdapter getAdapter() {
        return this.adapter;
    }

    public final int getCurrentItem() {
        int i;
        WheelAdapter wheelAdapter = this.adapter;
        if (wheelAdapter == null) {
            return 0;
        }
        if (!this.isLoop || ((i = this.selectedItem) >= 0 && i < wheelAdapter.getItemsCount())) {
            return Math.max(0, Math.min(this.selectedItem, this.adapter.getItemsCount() - 1));
        }
        return Math.max(0, Math.min(Math.abs(Math.abs(this.selectedItem) - this.adapter.getItemsCount()), this.adapter.getItemsCount() - 1));
    }

    public final void onItemSelected() {
        if (this.onItemSelectedListener != null) {
            postDelayed(new Runnable() {
                public void run() {
                    WheelView.this.onItemSelectedListener.onItemSelected(WheelView.this.getCurrentItem());
                }
            }, 200);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        String str;
        int i;
        Canvas canvas2 = canvas;
        if (this.adapter != null) {
            this.initPosition = Math.min(Math.max(0, this.initPosition), this.adapter.getItemsCount() - 1);
            Object[] objArr = new Object[this.itemsVisible];
            this.change = (int) (this.totalScrollY / this.itemHeight);
            try {
                this.preCurrentIndex = this.initPosition + (this.change % this.adapter.getItemsCount());
            } catch (ArithmeticException unused) {
                Log.e("WheelView", "出错了！adapter.getItemsCount() == 0，联动数据不匹配");
            }
            if (!this.isLoop) {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = 0;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex = this.adapter.getItemsCount() - 1;
                }
            } else {
                if (this.preCurrentIndex < 0) {
                    this.preCurrentIndex = this.adapter.getItemsCount() + this.preCurrentIndex;
                }
                if (this.preCurrentIndex > this.adapter.getItemsCount() - 1) {
                    this.preCurrentIndex -= this.adapter.getItemsCount();
                }
            }
            float f = this.totalScrollY % this.itemHeight;
            int i2 = 0;
            while (true) {
                int i3 = this.itemsVisible;
                if (i2 >= i3) {
                    break;
                }
                int i4 = this.preCurrentIndex - ((i3 / 2) - i2);
                if (this.isLoop) {
                    objArr[i2] = this.adapter.getItem(getLoopMappingIndex(i4));
                } else if (i4 < 0) {
                    objArr[i2] = "";
                } else if (i4 > this.adapter.getItemsCount() - 1) {
                    objArr[i2] = "";
                } else {
                    objArr[i2] = this.adapter.getItem(i4);
                }
                i2++;
            }
            if (this.dividerType == DividerType.WRAP) {
                if (TextUtils.isEmpty(this.label)) {
                    i = (this.measuredWidth - this.maxTextWidth) / 2;
                } else {
                    i = (this.measuredWidth - this.maxTextWidth) / 4;
                }
                float f2 = (float) (i - 12);
                float f3 = f2 <= 0.0f ? 10.0f : f2;
                float f4 = ((float) this.measuredWidth) - f3;
                float f5 = this.firstLineY;
                Canvas canvas3 = canvas;
                float f6 = f3;
                float f7 = f4;
                canvas3.drawLine(f6, f5, f7, f5, this.paintIndicator);
                float f8 = this.secondLineY;
                canvas3.drawLine(f6, f8, f7, f8, this.paintIndicator);
            } else {
                float f9 = this.firstLineY;
                canvas.drawLine(0.0f, f9, (float) this.measuredWidth, f9, this.paintIndicator);
                float f10 = this.secondLineY;
                canvas.drawLine(0.0f, f10, (float) this.measuredWidth, f10, this.paintIndicator);
            }
            if (!TextUtils.isEmpty(this.label) && this.isCenterLabel) {
                canvas2.drawText(this.label, ((float) (this.measuredWidth - getTextWidth(this.paintCenterText, this.label))) - this.CENTER_CONTENT_OFFSET, this.centerY, this.paintCenterText);
            }
            for (int i5 = 0; i5 < this.itemsVisible; i5++) {
                canvas.save();
                double d = (double) (((this.itemHeight * ((float) i5)) - f) / ((float) this.radius));
                Double.isNaN(d);
                float f11 = (float) (90.0d - ((d / 3.141592653589793d) * 180.0d));
                if (f11 >= 90.0f || f11 <= -90.0f) {
                    canvas.restore();
                } else {
                    float pow = (float) Math.pow((double) (Math.abs(f11) / 90.0f), 2.2d);
                    if (this.isCenterLabel || TextUtils.isEmpty(this.label) || TextUtils.isEmpty(getContentText(objArr[i5]))) {
                        str = getContentText(objArr[i5]);
                    } else {
                        str = getContentText(objArr[i5]) + this.label;
                    }
                    reMeasureTextSize(str);
                    measuredCenterContentStart(str);
                    measuredOutContentStart(str);
                    double d2 = (double) this.radius;
                    double cos = Math.cos(d);
                    double d3 = (double) this.radius;
                    Double.isNaN(d3);
                    Double.isNaN(d2);
                    double d4 = d2 - (cos * d3);
                    double sin = Math.sin(d);
                    double d5 = (double) this.maxTextHeight;
                    Double.isNaN(d5);
                    float f12 = (float) (d4 - ((sin * d5) / 2.0d));
                    canvas2.translate(0.0f, f12);
                    float f13 = this.firstLineY;
                    if (f12 > f13 || ((float) this.maxTextHeight) + f12 < f13) {
                        float f14 = this.secondLineY;
                        if (f12 > f14 || ((float) this.maxTextHeight) + f12 < f14) {
                            if (f12 >= this.firstLineY) {
                                int i6 = this.maxTextHeight;
                                if (((float) i6) + f12 <= this.secondLineY) {
                                    canvas2.drawText(str, (float) this.drawCenterContentStart, ((float) i6) - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                                    this.selectedItem = this.preCurrentIndex - ((this.itemsVisible / 2) - i5);
                                }
                            }
                            canvas.save();
                            canvas2.clipRect(0, 0, this.measuredWidth, (int) this.itemHeight);
                            canvas2.scale(1.0f, ((float) Math.sin(d)) * SCALE_CONTENT);
                            Paint paint = this.paintOuterText;
                            int i7 = this.textXOffset;
                            int i8 = -1;
                            int i9 = i7 == 0 ? 0 : i7 > 0 ? 1 : -1;
                            if (f11 <= 0.0f) {
                                i8 = 1;
                            }
                            paint.setTextSkewX(((float) (i9 * i8)) * 0.5f * pow);
                            this.paintOuterText.setAlpha((int) ((1.0f - pow) * 255.0f));
                            canvas2.drawText(str, ((float) this.drawOutContentStart) + (((float) this.textXOffset) * pow), (float) this.maxTextHeight, this.paintOuterText);
                            canvas.restore();
                            canvas.restore();
                            this.paintCenterText.setTextSize((float) this.textSize);
                        } else {
                            canvas.save();
                            canvas2.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.secondLineY - f12);
                            canvas2.scale(1.0f, ((float) Math.sin(d)) * 1.0f);
                            canvas2.drawText(str, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                            canvas.restore();
                            canvas.save();
                            canvas2.clipRect(0.0f, this.secondLineY - f12, (float) this.measuredWidth, (float) ((int) this.itemHeight));
                            canvas2.scale(1.0f, ((float) Math.sin(d)) * SCALE_CONTENT);
                            canvas2.drawText(str, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                            canvas.restore();
                        }
                    } else {
                        canvas.save();
                        canvas2.clipRect(0.0f, 0.0f, (float) this.measuredWidth, this.firstLineY - f12);
                        canvas2.scale(1.0f, ((float) Math.sin(d)) * SCALE_CONTENT);
                        canvas2.drawText(str, (float) this.drawOutContentStart, (float) this.maxTextHeight, this.paintOuterText);
                        canvas.restore();
                        canvas.save();
                        canvas2.clipRect(0.0f, this.firstLineY - f12, (float) this.measuredWidth, (float) ((int) this.itemHeight));
                        canvas2.scale(1.0f, ((float) Math.sin(d)) * 1.0f);
                        canvas2.drawText(str, (float) this.drawCenterContentStart, ((float) this.maxTextHeight) - this.CENTER_CONTENT_OFFSET, this.paintCenterText);
                        canvas.restore();
                    }
                    canvas.restore();
                    this.paintCenterText.setTextSize((float) this.textSize);
                }
            }
        }
    }

    private void reMeasureTextSize(String str) {
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        int i = this.textSize;
        for (int width = rect.width(); width > this.measuredWidth; width = rect.width()) {
            i--;
            this.paintCenterText.setTextSize((float) i);
            this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        }
        this.paintOuterText.setTextSize((float) i);
    }

    private int getLoopMappingIndex(int i) {
        if (i < 0) {
            return getLoopMappingIndex(i + this.adapter.getItemsCount());
        }
        return i > this.adapter.getItemsCount() + -1 ? getLoopMappingIndex(i - this.adapter.getItemsCount()) : i;
    }

    private String getContentText(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof IPickerViewData) {
            return ((IPickerViewData) obj).getPickerViewText();
        }
        if (!(obj instanceof Integer)) {
            return obj.toString();
        }
        return String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(((Integer) obj).intValue())});
    }

    private void measuredCenterContentStart(String str) {
        String str2;
        Rect rect = new Rect();
        this.paintCenterText.getTextBounds(str, 0, str.length(), rect);
        int i = this.mGravity;
        if (i == 3) {
            this.drawCenterContentStart = 0;
        } else if (i == 5) {
            this.drawCenterContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
        } else if (i == 17) {
            if (this.isOptions || (str2 = this.label) == null || str2.equals("") || !this.isCenterLabel) {
                double width = (double) (this.measuredWidth - rect.width());
                Double.isNaN(width);
                this.drawCenterContentStart = (int) (width * 0.5d);
                return;
            }
            double width2 = (double) (this.measuredWidth - rect.width());
            Double.isNaN(width2);
            this.drawCenterContentStart = (int) (width2 * 0.25d);
        }
    }

    private void measuredOutContentStart(String str) {
        String str2;
        Rect rect = new Rect();
        this.paintOuterText.getTextBounds(str, 0, str.length(), rect);
        int i = this.mGravity;
        if (i == 3) {
            this.drawOutContentStart = 0;
        } else if (i == 5) {
            this.drawOutContentStart = (this.measuredWidth - rect.width()) - ((int) this.CENTER_CONTENT_OFFSET);
        } else if (i == 17) {
            if (this.isOptions || (str2 = this.label) == null || str2.equals("") || !this.isCenterLabel) {
                double width = (double) (this.measuredWidth - rect.width());
                Double.isNaN(width);
                this.drawOutContentStart = (int) (width * 0.5d);
                return;
            }
            double width2 = (double) (this.measuredWidth - rect.width());
            Double.isNaN(width2);
            this.drawOutContentStart = (int) (width2 * 0.25d);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.widthMeasureSpec = i;
        remeasure();
        setMeasuredDimension(this.measuredWidth, this.measuredHeight);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = this.gestureDetector.onTouchEvent(motionEvent);
        float f = ((float) (-this.initPosition)) * this.itemHeight;
        float itemsCount = ((float) ((this.adapter.getItemsCount() - 1) - this.initPosition)) * this.itemHeight;
        int action = motionEvent.getAction();
        boolean z = false;
        if (action == 0) {
            this.startTime = System.currentTimeMillis();
            cancelFuture();
            this.previousY = motionEvent.getRawY();
        } else if (action == 2) {
            float rawY = this.previousY - motionEvent.getRawY();
            this.previousY = motionEvent.getRawY();
            this.totalScrollY += rawY;
            if (!this.isLoop && ((this.totalScrollY - (this.itemHeight * 0.25f) < f && rawY < 0.0f) || (this.totalScrollY + (this.itemHeight * 0.25f) > itemsCount && rawY > 0.0f))) {
                this.totalScrollY -= rawY;
                z = true;
            }
        } else if (!onTouchEvent) {
            float y = motionEvent.getY();
            int i = this.radius;
            double acos = Math.acos((double) ((((float) i) - y) / ((float) i)));
            double d = (double) this.radius;
            Double.isNaN(d);
            double d2 = acos * d;
            float f2 = this.itemHeight;
            double d3 = (double) (f2 / 2.0f);
            Double.isNaN(d3);
            double d4 = d2 + d3;
            double d5 = (double) f2;
            Double.isNaN(d5);
            this.mOffset = (int) ((((float) (((int) (d4 / d5)) - (this.itemsVisible / 2))) * f2) - (((this.totalScrollY % f2) + f2) % f2));
            if (System.currentTimeMillis() - this.startTime > 120) {
                smoothScroll(ACTION.DAGGLE);
            } else {
                smoothScroll(ACTION.CLICK);
            }
        }
        if (!z && motionEvent.getAction() != 0) {
            invalidate();
        }
        return true;
    }

    public int getItemsCount() {
        WheelAdapter wheelAdapter = this.adapter;
        if (wheelAdapter != null) {
            return wheelAdapter.getItemsCount();
        }
        return 0;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public void isCenterLabel(boolean z) {
        this.isCenterLabel = z;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public int getTextWidth(Paint paint, String str) {
        if (str == null || str.length() <= 0) {
            return 0;
        }
        int length = str.length();
        float[] fArr = new float[length];
        paint.getTextWidths(str, fArr);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i += (int) Math.ceil((double) fArr[i2]);
        }
        return i;
    }

    public void setIsOptions(boolean z) {
        this.isOptions = z;
    }

    public void setTextColorOut(int i) {
        this.textColorOut = i;
        this.paintOuterText.setColor(this.textColorOut);
    }

    public void setTextColorCenter(int i) {
        this.textColorCenter = i;
        this.paintCenterText.setColor(this.textColorCenter);
    }

    public void setTextXOffset(int i) {
        this.textXOffset = i;
        if (i != 0) {
            this.paintCenterText.setTextScaleX(1.0f);
        }
    }

    public void setDividerColor(int i) {
        this.dividerColor = i;
        this.paintIndicator.setColor(i);
    }

    public void setDividerType(DividerType dividerType2) {
        this.dividerType = dividerType2;
    }

    public void setLineSpacingMultiplier(float f) {
        if (f != 0.0f) {
            this.lineSpacingMultiplier = f;
            judgeLineSpace();
        }
    }

    public boolean isLoop() {
        return this.isLoop;
    }

    public float getTotalScrollY() {
        return this.totalScrollY;
    }

    public void setTotalScrollY(float f) {
        this.totalScrollY = f;
    }

    public float getItemHeight() {
        return this.itemHeight;
    }

    public int getInitPosition() {
        return this.initPosition;
    }

    public Handler getHandler() {
        return this.handler;
    }
}
