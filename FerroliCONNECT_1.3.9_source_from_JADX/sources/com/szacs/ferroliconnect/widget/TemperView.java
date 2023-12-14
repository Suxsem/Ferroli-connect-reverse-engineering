package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.p000v4.view.InputDeviceCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;

public class TemperView extends View {
    public static int TYPE_HOMEWATER = 200;
    public static int TYPE_HOTWATER = 100;
    private double Angle;
    private float MaxValue;
    private float MinValue;
    private String TAG;
    private float TapRadius;
    private boolean canTouchAble;
    private float centerX;
    private float centerY;
    private int icon;
    private Bitmap iconScaleBitmap;
    private Bitmap iconbitmap;
    private boolean isOnTouch;
    private onValueChangeListenner listenner;
    private Bitmap mCacheBitmap;
    private Paint mPointerPaint;
    private RectF rectF;
    private Bitmap scalebitmap;
    private Bitmap tapBlank;
    private float tapRadius;
    private Bitmap tapScaleBitmap;
    private float tapWith;
    private int textColor;
    private TextPaint textPaint;
    private float textSzie;
    private TextPaint tipPaint;
    private int type;
    private float wheelRadius;

    public interface onValueChangeListenner {
        void onEndValueChange(String str);

        void onValueChange(String str);
    }

    public TemperView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TemperView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TemperView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        this.type = TYPE_HOMEWATER;
        this.Angle = 170.0d;
        this.canTouchAble = true;
        initPadding();
        inits(context, attributeSet);
    }

    private void inits(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1683R.styleable.TemperViewAttrs);
        this.MaxValue = (float) obtainStyledAttributes.getInteger(1, 45);
        this.MinValue = (float) obtainStyledAttributes.getInteger(2, 5);
        this.Angle = (double) obtainStyledAttributes.getInteger(0, 180);
        this.textSzie = obtainStyledAttributes.getDimension(4, 50.0f);
        this.textColor = obtainStyledAttributes.getColor(3, Color.parseColor("#FFFFFF"));
        this.icon = obtainStyledAttributes.getInteger(5, C1683R.C1684drawable.target_temp);
        double d = this.Angle;
        if (d > 90.0d && d < 170.0d) {
            if (d <= 90.0d || d >= 170.0d) {
                double d2 = this.Angle;
                if (d2 <= 10.0d || d2 >= 90.0d) {
                    double d3 = this.Angle;
                    if (d3 > 0.0d && d3 < 10.0d) {
                        this.Angle = d3 + 360.0d;
                    }
                } else {
                    this.Angle = 370.0d;
                }
            } else {
                this.Angle = 170.0d;
            }
        }
        this.rectF = new RectF();
        this.mPointerPaint = new Paint(1);
        this.mPointerPaint.setColor(InputDeviceCompat.SOURCE_ANY);
        this.mPointerPaint.setStyle(Paint.Style.FILL);
        this.mCacheBitmap = BitmapFactory.decodeResource(getResources(), C1683R.C1684drawable.temper_bg);
        this.tapBlank = BitmapFactory.decodeResource(getResources(), C1683R.C1684drawable.tapicon);
        this.iconbitmap = BitmapFactory.decodeResource(getResources(), this.icon);
        this.textPaint = new TextPaint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setDither(true);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setTextSize(this.textSzie);
        this.textPaint.setColor(this.textColor);
        this.textPaint.setTypeface(MainApplication.typeface);
        this.tipPaint = new TextPaint(this.textPaint);
        this.tipPaint.setTextSize(this.textSzie / 3.0f);
    }

    private void initPadding() {
        int i;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 17) {
            i2 = getPaddingStart();
            i = getPaddingEnd();
        } else {
            i = 0;
        }
        int max = Math.max(paddingLeft, Math.max(paddingTop, Math.max(paddingRight, Math.max(paddingBottom, Math.max(i2, i)))));
        setPadding(max, max, max, max);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int min = Math.min(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
        setMeasuredDimension(min, min);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float paddingLeft = (float) getPaddingLeft();
        float paddingTop = (float) getPaddingTop();
        float width = (float) (getWidth() - getPaddingRight());
        float height = (float) (getHeight() - getPaddingBottom());
        this.centerX = (float) (getWidth() / 2);
        this.centerY = (float) (getHeight() / 2);
        this.wheelRadius = (float) (((canvas.getWidth() - getPaddingLeft()) - getPaddingRight()) / 2);
        float f = this.wheelRadius;
        this.tapRadius = f - (0.13f * f);
        if (this.scalebitmap == null) {
            this.scalebitmap = Bitmap.createScaledBitmap(this.mCacheBitmap, (int) (width - paddingLeft), (int) (height - paddingTop), true);
            this.tapWith = this.wheelRadius / 4.0f;
            float f2 = this.tapWith;
            this.TapRadius = f2 / 2.0f;
            this.tapScaleBitmap = Bitmap.createScaledBitmap(this.tapBlank, (int) f2, (int) f2, true);
            Bitmap bitmap = this.iconbitmap;
            float f3 = this.tapWith;
            this.iconScaleBitmap = Bitmap.createScaledBitmap(bitmap, (int) f3, (int) f3, true);
        }
        canvas.drawBitmap(this.scalebitmap, paddingLeft, paddingTop, this.mPointerPaint);
        if (this.canTouchAble) {
            canvas.drawBitmap(this.tapScaleBitmap, computPos(this.Angle).x, computPos(this.Angle).y, this.mPointerPaint);
        }
    }

    private void drawTempText(Canvas canvas) {
        float f = this.MaxValue;
        float f2 = this.MinValue;
        double d = (double) (f - f2);
        Double.isNaN(d);
        double d2 = (double) f2;
        Double.isNaN(d2);
        String replace = String.format("%.1f", new Object[]{Float.valueOf((float) ((((this.Angle - 135.0d) / 270.0d) * d) + d2))}).replace(",", ".");
        Rect rect = new Rect();
        this.textPaint.getTextBounds(replace, 0, replace.length(), rect);
        float height = ((float) rect.height()) / 2.0f;
        float abs = Math.abs(this.textPaint.ascent()) - height;
        float measureText = this.textPaint.measureText(replace);
        String[] split = replace.split("\\.");
        TextPaint textPaint2 = this.textPaint;
        float measureText2 = textPaint2.measureText(split[0] + ".");
        this.textPaint.setTextAlign(Paint.Align.LEFT);
        float f3 = measureText / 2.0f;
        canvas.drawText(split[0] + ".", this.centerX - f3, this.centerY + abs, this.textPaint);
        this.textPaint.setTextSize(this.textSzie / 2.0f);
        float measureText3 = this.textPaint.measureText(split[1]);
        canvas.drawText(split[1], (this.centerX - f3) + measureText2, this.centerY + abs, this.textPaint);
        this.textPaint.setTextSize(this.textSzie);
        canvas.drawText("°", (this.centerX - f3) + measureText2 + measureText3, this.centerY - height, this.textPaint);
    }

    private PointF computPos(double d) {
        double d2 = (double) this.centerX;
        double d3 = (double) this.tapRadius;
        double cos = Math.cos(Math.toRadians(d));
        Double.isNaN(d3);
        Double.isNaN(d2);
        double d4 = d2 + (d3 * cos);
        double d5 = (double) this.TapRadius;
        Double.isNaN(d5);
        double d6 = d4 - d5;
        double d7 = (double) this.centerY;
        double d8 = (double) this.tapRadius;
        double sin = Math.sin(Math.toRadians(d));
        Double.isNaN(d8);
        Double.isNaN(d7);
        double d9 = (double) this.TapRadius;
        Double.isNaN(d9);
        PointF pointF = new PointF((float) d6, (float) ((d7 + (d8 * sin)) - d9));
        this.rectF.set(pointF.x, pointF.y, pointF.x + this.tapWith, pointF.y + this.tapWith);
        return pointF;
    }

    private double computeAngle(float f, float f2) {
        float width = f - ((float) (getWidth() / 2));
        float height = f2 - ((float) (getHeight() / 2));
        double degrees = Math.toDegrees(Math.acos((double) (width / ((float) Math.sqrt((double) ((width * width) + (height * height)))))));
        if (height < 0.0f) {
            degrees = 360.0d - degrees;
        }
        if (degrees > 90.0d && degrees < 135.0d) {
            return 135.0d;
        }
        if (degrees <= 45.0d || degrees >= 90.0d) {
            return (degrees <= 0.0d || degrees >= 45.0d) ? degrees : degrees + 360.0d;
        }
        return 405.0d;
    }

    private boolean isInTouch(float f, float f2) {
        PointF computPos = computPos(this.Angle);
        return new Region((int) computPos.x, (int) computPos.y, (int) (computPos.x + this.tapWith), (int) (computPos.y + this.tapWith)).contains((int) f, (int) f2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.canTouchAble) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        double computeAngle = computeAngle(x, y);
        if (motionEvent.getAction() == 0 && isInTouch(x, y)) {
            this.isOnTouch = true;
            return true;
        } else if (motionEvent.getAction() == 2) {
            this.Angle = computeAngle;
            notifyListner();
            invalidate();
            return true;
        } else if (motionEvent.getAction() == 1) {
            this.Angle = computeAngle;
            onTouchUpValueChange();
            invalidate();
            this.isOnTouch = false;
            return true;
        } else {
            this.isOnTouch = false;
            return super.onTouchEvent(motionEvent);
        }
    }

    private void notifyListner() {
        String str;
        StringBuilder sb;
        if (this.listenner != null) {
            float f = this.MaxValue;
            float f2 = this.MinValue;
            double d = (double) (f - f2);
            Double.isNaN(d);
            double d2 = (double) f2;
            Double.isNaN(d2);
            String[] split = String.format("%.1f", new Object[]{Float.valueOf((float) ((((this.Angle - 135.0d) / 270.0d) * d) + d2))}).replace(",", ".").split("\\.");
            if (split.length == 2) {
                if (Integer.valueOf(split[1]).intValue() > 5) {
                    sb = new StringBuilder();
                    sb.append(split[0]);
                    sb.append(".5");
                } else {
                    sb = new StringBuilder();
                    sb.append(split[0]);
                    sb.append(".0");
                }
                str = sb.toString();
            } else {
                str = split[0] + ".0";
            }
            this.listenner.onValueChange(str);
        }
    }

    private void onTouchUpValueChange() {
        StringBuilder sb;
        String str;
        if (this.listenner != null) {
            float f = this.MaxValue;
            float f2 = this.MinValue;
            double d = (double) (f - f2);
            Double.isNaN(d);
            double d2 = (double) f2;
            Double.isNaN(d2);
            String[] split = String.format("%.1f", new Object[]{Float.valueOf((float) ((((this.Angle - 135.0d) / 270.0d) * d) + d2))}).replace(",", ".").split("\\.");
            if (Integer.valueOf(split[1]).intValue() > 5) {
                sb = new StringBuilder();
                sb.append(split[0]);
                str = ".5";
            } else {
                sb = new StringBuilder();
                sb.append(split[0]);
                str = ".0";
            }
            sb.append(str);
            this.listenner.onEndValueChange(sb.toString());
        }
    }

    public void setTemperType(int i) {
        if (i == TYPE_HOTWATER || i == TYPE_HOMEWATER) {
            this.type = i;
        }
    }

    public void setValue(String str) {
        if (!this.isOnTouch) {
            float floatValue = Float.valueOf(str).floatValue();
            float f = this.MinValue;
            if (floatValue >= f) {
                f = this.MaxValue;
                if (floatValue <= f) {
                    f = floatValue;
                }
            }
            float f2 = this.MinValue;
            double d = (double) ((f - f2) / (this.MaxValue - f2));
            Double.isNaN(d);
            this.Angle = (d * 270.0d) + 135.0d;
            postInvalidate();
        }
    }

    public RectF getTapRectF() {
        return this.rectF;
    }

    public void setMinValue(float f) {
        this.MinValue = f;
        invalidate();
    }

    public void setMaxValue(float f) {
        this.MaxValue = f;
        invalidate();
    }

    public void setOnValueChangeListener(onValueChangeListenner onvaluechangelistenner) {
        this.listenner = onvaluechangelistenner;
    }

    public boolean isCanTouchAble() {
        return this.canTouchAble;
    }

    public void setCanTouchAble(boolean z) {
        this.canTouchAble = z;
        invalidate();
    }
}
