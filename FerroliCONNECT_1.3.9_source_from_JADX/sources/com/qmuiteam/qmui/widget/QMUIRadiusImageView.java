package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.p003v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.qmuiteam.qmui.C1614R;

public class QMUIRadiusImageView extends AppCompatImageView {
    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLOR_DRAWABLE_DIMEN = 2;
    private static final int DEFAULT_BORDER_COLOR = -7829368;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private BitmapShader mBitmapShader;
    private int mBorderColor;
    private Paint mBorderPaint;
    private int mBorderWidth;
    private ColorFilter mColorFilter;
    private int mCornerRadius;
    private int mHeight;
    private boolean mIsCircle;
    private boolean mIsOval;
    private boolean mIsSelected;
    private boolean mIsTouchSelectModeEnabled;
    private Matrix mMatrix;
    private boolean mNeedResetShader;
    private RectF mRectF;
    private int mSelectedBorderColor;
    private int mSelectedBorderWidth;
    private ColorFilter mSelectedColorFilter;
    private int mSelectedMaskColor;
    private int mWidth;

    public QMUIRadiusImageView(Context context) {
        this(context, (AttributeSet) null, C1614R.attr.QMUIRadiusImageViewStyle);
    }

    public QMUIRadiusImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUIRadiusImageViewStyle);
    }

    public QMUIRadiusImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsSelected = false;
        this.mIsOval = false;
        this.mIsCircle = false;
        this.mIsTouchSelectModeEnabled = true;
        this.mNeedResetShader = false;
        this.mRectF = new RectF();
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setStyle(Paint.Style.STROKE);
        this.mMatrix = new Matrix();
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIRadiusImageView, i, 0);
        this.mBorderWidth = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRadiusImageView_qmui_border_width, 0);
        this.mBorderColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIRadiusImageView_qmui_border_color, DEFAULT_BORDER_COLOR);
        this.mSelectedBorderWidth = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRadiusImageView_qmui_selected_border_width, this.mBorderWidth);
        this.mSelectedBorderColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIRadiusImageView_qmui_selected_border_color, this.mBorderColor);
        this.mSelectedMaskColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIRadiusImageView_qmui_selected_mask_color, 0);
        int i2 = this.mSelectedMaskColor;
        if (i2 != 0) {
            this.mSelectedColorFilter = new PorterDuffColorFilter(i2, PorterDuff.Mode.DARKEN);
        }
        this.mIsTouchSelectModeEnabled = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIRadiusImageView_qmui_is_touch_select_mode_enabled, true);
        this.mIsCircle = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIRadiusImageView_qmui_is_circle, false);
        if (!this.mIsCircle) {
            this.mIsOval = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIRadiusImageView_qmui_is_oval, false);
        }
        if (!this.mIsOval) {
            this.mCornerRadius = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIRadiusImageView_qmui_corner_radius, 0);
        }
        obtainStyledAttributes.recycle();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            super.setScaleType(scaleType);
        } else {
            throw new IllegalArgumentException(String.format("不支持ScaleType %s", new Object[]{scaleType}));
        }
    }

    public void setAdjustViewBounds(boolean z) {
        if (z) {
            throw new IllegalArgumentException("不支持adjustViewBounds");
        }
    }

    public void setBorderWidth(int i) {
        if (this.mBorderWidth != i) {
            this.mBorderWidth = i;
            invalidate();
        }
    }

    public void setBorderColor(@ColorInt int i) {
        if (this.mBorderColor != i) {
            this.mBorderColor = i;
            invalidate();
        }
    }

    public void setCornerRadius(int i) {
        if (this.mCornerRadius != i) {
            this.mCornerRadius = i;
            if (!this.mIsCircle && !this.mIsOval) {
                invalidate();
            }
        }
    }

    public void setSelectedBorderColor(@ColorInt int i) {
        if (this.mSelectedBorderColor != i) {
            this.mSelectedBorderColor = i;
            if (this.mIsSelected) {
                invalidate();
            }
        }
    }

    public void setSelectedBorderWidth(int i) {
        if (this.mSelectedBorderWidth != i) {
            this.mSelectedBorderWidth = i;
            if (this.mIsSelected) {
                invalidate();
            }
        }
    }

    public void setSelectedMaskColor(@ColorInt int i) {
        if (this.mSelectedMaskColor != i) {
            this.mSelectedMaskColor = i;
            int i2 = this.mSelectedMaskColor;
            if (i2 != 0) {
                this.mSelectedColorFilter = new PorterDuffColorFilter(i2, PorterDuff.Mode.DARKEN);
            } else {
                this.mSelectedColorFilter = null;
            }
            if (this.mIsSelected) {
                invalidate();
            }
        }
        this.mSelectedMaskColor = i;
    }

    public void setCircle(boolean z) {
        if (this.mIsCircle != z) {
            this.mIsCircle = z;
            requestLayout();
            invalidate();
        }
    }

    public void setOval(boolean z) {
        boolean z2 = false;
        if (z && this.mIsCircle) {
            this.mIsCircle = false;
            z2 = true;
        }
        if (this.mIsOval != z || z2) {
            this.mIsOval = z;
            requestLayout();
            invalidate();
        }
    }

    public int getBorderColor() {
        return this.mBorderColor;
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public int getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getSelectedBorderColor() {
        return this.mSelectedBorderColor;
    }

    public int getSelectedBorderWidth() {
        return this.mSelectedBorderWidth;
    }

    public int getSelectedMaskColor() {
        return this.mSelectedMaskColor;
    }

    public boolean isCircle() {
        return this.mIsCircle;
    }

    public boolean isOval() {
        return !this.mIsCircle && this.mIsOval;
    }

    public boolean isSelected() {
        return this.mIsSelected;
    }

    public void setSelected(boolean z) {
        if (this.mIsSelected != z) {
            this.mIsSelected = z;
            invalidate();
        }
    }

    public void setTouchSelectModeEnabled(boolean z) {
        this.mIsTouchSelectModeEnabled = z;
    }

    public boolean isTouchSelectModeEnabled() {
        return this.mIsTouchSelectModeEnabled;
    }

    public void setSelectedColorFilter(ColorFilter colorFilter) {
        if (this.mSelectedColorFilter != colorFilter) {
            this.mSelectedColorFilter = colorFilter;
            if (this.mIsSelected) {
                invalidate();
            }
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mColorFilter != colorFilter) {
            this.mColorFilter = colorFilter;
            if (!this.mIsSelected) {
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (this.mIsCircle) {
            int min = Math.min(measuredWidth, measuredHeight);
            setMeasuredDimension(min, min);
            return;
        }
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.mBitmap != null) {
            boolean z = false;
            boolean z2 = mode == Integer.MIN_VALUE || mode == 0;
            if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
                z = true;
            }
            float width = (float) this.mBitmap.getWidth();
            float height = (float) this.mBitmap.getHeight();
            float f = ((float) measuredWidth) / width;
            float f2 = ((float) measuredHeight) / height;
            if (!z2 || !z) {
                if (z2) {
                    setMeasuredDimension((int) (width * f2), measuredHeight);
                } else if (z) {
                    setMeasuredDimension(measuredWidth, (int) (height * f));
                }
            } else if (f >= 1.0f && f2 >= 1.0f) {
                setMeasuredDimension((int) width, (int) height);
            } else if (f >= 1.0f) {
                setMeasuredDimension((int) (height * f2), measuredHeight);
            } else if (f2 >= 1.0f) {
                setMeasuredDimension(measuredWidth, (int) (height * f));
            } else if (f < f2) {
                setMeasuredDimension(measuredWidth, (int) (height * f));
            } else {
                setMeasuredDimension((int) (width * f2), measuredHeight);
            }
        }
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        setupBitmap();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setupBitmap();
    }

    private Bitmap getBitmap() {
        Bitmap bitmap;
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap2 = ((BitmapDrawable) drawable).getBitmap();
            float width = (float) bitmap2.getWidth();
            float height = (float) bitmap2.getHeight();
            if (width == 0.0f || height == 0.0f) {
                return null;
            }
            float minimumWidth = ((float) getMinimumWidth()) / width;
            float minimumHeight = ((float) getMinimumHeight()) / height;
            if (minimumWidth <= 1.0f && minimumHeight <= 1.0f) {
                return bitmap2;
            }
            float max = Math.max(minimumWidth, minimumHeight);
            Matrix matrix = new Matrix();
            matrix.postScale(max, max);
            return Bitmap.createBitmap(bitmap2, 0, 0, (int) width, (int) height, matrix, false);
        }
        try {
            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setupBitmap() {
        Bitmap bitmap = getBitmap();
        if (bitmap != this.mBitmap) {
            this.mBitmap = bitmap;
            Bitmap bitmap2 = this.mBitmap;
            if (bitmap2 == null) {
                this.mBitmapShader = null;
                invalidate();
                return;
            }
            this.mNeedResetShader = true;
            this.mBitmapShader = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            if (this.mBitmapPaint == null) {
                this.mBitmapPaint = new Paint();
                this.mBitmapPaint.setAntiAlias(true);
            }
            this.mBitmapPaint.setShader(this.mBitmapShader);
            requestLayout();
            invalidate();
        }
    }

    private void updateBitmapShader() {
        Bitmap bitmap;
        this.mMatrix.reset();
        this.mNeedResetShader = false;
        if (this.mBitmapShader != null && (bitmap = this.mBitmap) != null) {
            float width = (float) bitmap.getWidth();
            float height = (float) this.mBitmap.getHeight();
            float max = Math.max(((float) this.mWidth) / width, ((float) this.mHeight) / height);
            this.mMatrix.setScale(max, max);
            this.mMatrix.postTranslate((-((width * max) - ((float) this.mWidth))) / 2.0f, (-((max * height) - ((float) this.mHeight))) / 2.0f);
            this.mBitmapShader.setLocalMatrix(this.mMatrix);
            this.mBitmapPaint.setShader(this.mBitmapShader);
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        if (width > 0 && height > 0 && this.mBitmap != null && this.mBitmapShader != null) {
            if (!(this.mWidth == width && this.mHeight == height && !this.mNeedResetShader)) {
                this.mWidth = width;
                this.mHeight = height;
                updateBitmapShader();
            }
            this.mBorderPaint.setColor(this.mIsSelected ? this.mSelectedBorderColor : this.mBorderColor);
            this.mBitmapPaint.setColorFilter(this.mIsSelected ? this.mSelectedColorFilter : this.mColorFilter);
            int i = this.mIsSelected ? this.mSelectedBorderWidth : this.mBorderWidth;
            float f = (float) i;
            this.mBorderPaint.setStrokeWidth(f);
            float f2 = (f * 1.0f) / 2.0f;
            if (this.mIsCircle) {
                float width2 = (float) (getWidth() / 2);
                canvas.drawCircle(width2, width2, width2, this.mBitmapPaint);
                if (i > 0) {
                    canvas.drawCircle(width2, width2, width2 - f2, this.mBorderPaint);
                    return;
                }
                return;
            }
            RectF rectF = this.mRectF;
            rectF.left = f2;
            rectF.top = f2;
            rectF.right = ((float) width) - f2;
            rectF.bottom = ((float) height) - f2;
            if (this.mIsOval) {
                canvas.drawOval(rectF, this.mBitmapPaint);
                if (i > 0) {
                    canvas.drawOval(this.mRectF, this.mBorderPaint);
                    return;
                }
                return;
            }
            int i2 = this.mCornerRadius;
            canvas.drawRoundRect(rectF, (float) i2, (float) i2, this.mBitmapPaint);
            if (i > 0) {
                RectF rectF2 = this.mRectF;
                int i3 = this.mCornerRadius;
                canvas.drawRoundRect(rectF2, (float) i3, (float) i3, this.mBorderPaint);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isClickable()) {
            setSelected(false);
            return super.onTouchEvent(motionEvent);
        } else if (!this.mIsTouchSelectModeEnabled) {
            return super.onTouchEvent(motionEvent);
        } else {
            int action = motionEvent.getAction();
            if (action == 0) {
                setSelected(true);
            } else if (action == 1 || action == 3 || action == 4 || action == 8) {
                setSelected(false);
            }
            return super.onTouchEvent(motionEvent);
        }
    }
}
