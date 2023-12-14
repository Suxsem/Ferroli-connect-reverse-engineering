package com.qmuiteam.qmui.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.p003v7.content.res.AppCompatResources;
import android.view.View;
import android.widget.ImageView;
import com.qmuiteam.qmui.QMUILog;

public class QMUIDrawableHelper {
    private static final String TAG = "QMUIDrawableHelper";
    private static final Canvas sCanvas = new Canvas();

    public static Bitmap createBitmapFromView(View view, float f) {
        Drawable drawable;
        if ((view instanceof ImageView) && (drawable = ((ImageView) view).getDrawable()) != null && (drawable instanceof BitmapDrawable)) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        view.clearFocus();
        Bitmap createBitmapSafely = createBitmapSafely((int) (((float) view.getWidth()) * f), (int) (((float) view.getHeight()) * f), Bitmap.Config.ARGB_8888, 1);
        if (createBitmapSafely != null) {
            synchronized (sCanvas) {
                Canvas canvas = sCanvas;
                canvas.setBitmap(createBitmapSafely);
                canvas.save();
                canvas.drawColor(-1);
                canvas.scale(f, f);
                view.draw(canvas);
                canvas.restore();
                canvas.setBitmap((Bitmap) null);
            }
        }
        return createBitmapSafely;
    }

    public static Bitmap createBitmapFromView(View view) {
        return createBitmapFromView(view, 1.0f);
    }

    public static Bitmap createBitmapFromView(View view, int i, int i2, int i3, int i4) {
        Bitmap createBitmapSafely;
        Bitmap createBitmapFromView = createBitmapFromView(view);
        if (createBitmapFromView == null || (createBitmapSafely = createBitmapSafely((view.getWidth() - i3) - i, (view.getHeight() - i2) - i4, Bitmap.Config.ARGB_8888, 1)) == null) {
            return null;
        }
        Canvas canvas = new Canvas(createBitmapSafely);
        Rect rect = new Rect(i, i2, view.getWidth() - i3, view.getHeight() - i4);
        Rect rect2 = new Rect(0, 0, (view.getWidth() - i3) - i, (view.getHeight() - i2) - i4);
        canvas.drawColor(-1);
        canvas.drawBitmap(createBitmapFromView, rect, rect2, (Paint) null);
        createBitmapFromView.recycle();
        return createBitmapSafely;
    }

    public static Bitmap createBitmapSafely(int i, int i2, Bitmap.Config config, int i3) {
        try {
            return Bitmap.createBitmap(i, i2, config);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (i3 <= 0) {
                return null;
            }
            System.gc();
            return createBitmapSafely(i, i2, config, i3 - 1);
        }
    }

    public static BitmapDrawable createDrawableWithSize(Resources resources, int i, int i2, int i3, @ColorInt int i4) {
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (i4 == 0) {
            i4 = 0;
        }
        if (i3 > 0) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(i4);
            RectF rectF = new RectF(0.0f, 0.0f, (float) i, (float) i2);
            float f = (float) i3;
            canvas.drawRoundRect(rectF, f, f, paint);
        } else {
            canvas.drawColor(i4);
        }
        return new BitmapDrawable(resources, createBitmap);
    }

    public static ColorFilter setDrawableTintColor(Drawable drawable, @ColorInt int i) {
        LightingColorFilter lightingColorFilter = new LightingColorFilter(Color.argb(255, 0, 0, 0), i);
        drawable.setColorFilter(lightingColorFilter);
        return lightingColorFilter;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth > 0 && intrinsicHeight > 0) {
            try {
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return createBitmap;
            } catch (OutOfMemoryError unused) {
            }
        }
        return null;
    }

    @TargetApi(16)
    public static GradientDrawable createCircleGradientDrawable(@ColorInt int i, @ColorInt int i2, int i3, @FloatRange(from = 0.0d, mo101to = 1.0d) float f, @FloatRange(from = 0.0d, mo101to = 1.0d) float f2) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{i, i2});
        gradientDrawable.setGradientType(1);
        gradientDrawable.setGradientRadius((float) i3);
        gradientDrawable.setGradientCenter(f, f2);
        return gradientDrawable;
    }

    public static LayerDrawable createItemSeparatorBg(@ColorInt int i, @ColorInt int i2, int i3, boolean z) {
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable.getPaint().setColor(i);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable();
        shapeDrawable2.getPaint().setStyle(Paint.Style.FILL);
        shapeDrawable2.getPaint().setColor(i2);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shapeDrawable, shapeDrawable2});
        layerDrawable.setLayerInset(1, 0, z ? i3 : 0, 0, z ? 0 : i3);
        return layerDrawable;
    }

    @Nullable
    public static Drawable getVectorDrawable(Context context, @DrawableRes int i) {
        try {
            return AppCompatResources.getDrawable(context, i);
        } catch (Exception e) {
            String str = TAG;
            QMUILog.m3306d(str, "Error in getVectorDrawable. resVector=" + i + ", resName=" + context.getResources().getResourceName(i) + e.getMessage(), new Object[0]);
            return null;
        }
    }

    public static Bitmap vectorDrawableToBitmap(Context context, @DrawableRes int i) {
        Drawable vectorDrawable = getVectorDrawable(context, i);
        if (vectorDrawable == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return createBitmap;
    }
}
