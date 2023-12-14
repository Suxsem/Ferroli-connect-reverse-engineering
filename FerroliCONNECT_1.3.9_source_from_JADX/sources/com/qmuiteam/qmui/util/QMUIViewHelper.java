package com.qmuiteam.qmui.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.p003v7.appcompat.C0359R;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class QMUIViewHelper {
    private static final int[] APPCOMPAT_CHECK_ATTRS = {C0359R.attr.colorPrimary};
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static void checkAppCompatTheme(Context context) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(APPCOMPAT_CHECK_ATTRS);
        boolean z = !obtainStyledAttributes.hasValue(0);
        obtainStyledAttributes.recycle();
        if (z) {
            throw new IllegalArgumentException("You need to use a Theme.AppCompat theme (or descendant) with the design library.");
        }
    }

    public static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(16908290)).getChildAt(0);
    }

    public static void requestApplyInsets(Window window) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            window.getDecorView().requestFitSystemWindows();
        } else if (Build.VERSION.SDK_INT >= 21) {
            window.getDecorView().requestApplyInsets();
        }
    }

    public static void expendTouchArea(final View view, final int i) {
        if (view != null) {
            final View view2 = (View) view.getParent();
            view2.post(new Runnable() {
                public void run() {
                    Rect rect = new Rect();
                    view.getHitRect(rect);
                    rect.left -= i;
                    rect.top -= i;
                    rect.right += i;
                    rect.bottom += i;
                    view2.setTouchDelegate(new TouchDelegate(rect, view));
                }
            });
        }
    }

    @TargetApi(16)
    public static void setBackgroundKeepingPadding(View view, Drawable drawable) {
        int[] iArr = {view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()};
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        view.setPadding(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public static void setBackgroundKeepingPadding(View view, int i) {
        setBackgroundKeepingPadding(view, view.getResources().getDrawable(i));
    }

    public static void setBackgroundColorKeepPadding(View view, @ColorInt int i) {
        int[] iArr = {view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()};
        view.setBackgroundColor(i);
        view.setPadding(iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public static void playBackgroundBlinkAnimation(View view, @ColorInt int i) {
        if (view != null) {
            playViewBackgroundAnimation(view, i, new int[]{0, 255, 0}, 300);
        }
    }

    public static Animator playViewBackgroundAnimation(final View view, @ColorInt int i, int[] iArr, int i2, final Runnable runnable) {
        int length = iArr.length - 1;
        ColorDrawable colorDrawable = new ColorDrawable(i);
        final Drawable background = view.getBackground();
        setBackgroundKeepingPadding(view, (Drawable) colorDrawable);
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < length) {
            i3++;
            arrayList.add(ObjectAnimator.ofInt(view.getBackground(), "alpha", new int[]{iArr[i3], iArr[i3]}));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration((long) i2);
        animatorSet.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                QMUIViewHelper.setBackgroundKeepingPadding(view, background);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        animatorSet.playSequentially(arrayList);
        animatorSet.start();
        return animatorSet;
    }

    public static void playViewBackgroundAnimation(View view, @ColorInt int i, int[] iArr, int i2) {
        playViewBackgroundAnimation(view, i, iArr, i2, (Runnable) null);
    }

    public static void playViewBackgroundAnimation(final View view, @ColorInt int i, @ColorInt int i2, long j, int i3, int i4, final Runnable runnable) {
        final Drawable background = view.getBackground();
        setBackgroundColorKeepPadding(view, i);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(new int[]{i, i2});
        valueAnimator.setDuration(j / ((long) (i3 + 1)));
        valueAnimator.setRepeatCount(i3);
        valueAnimator.setRepeatMode(2);
        valueAnimator.setEvaluator(new ArgbEvaluator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                QMUIViewHelper.setBackgroundColorKeepPadding(view, ((Integer) valueAnimator.getAnimatedValue()).intValue());
            }
        });
        if (i4 != 0) {
            view.setTag(i4, valueAnimator);
        }
        valueAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                QMUIViewHelper.setBackgroundKeepingPadding(view, background);
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
        valueAnimator.start();
    }

    public static void playViewBackgroundAnimation(View view, int i, int i2, long j) {
        playViewBackgroundAnimation(view, i, i2, j, 0, 0, (Runnable) null);
    }

    public static int generateViewId() {
        int i;
        int i2;
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        do {
            i = sNextGeneratedId.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(i, i2));
        return i;
    }

    public static AlphaAnimation fadeIn(View view, int i, Animation.AnimationListener animationListener, boolean z) {
        if (view == null) {
            return null;
        }
        if (z) {
            view.setVisibility(0);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());
            alphaAnimation.setDuration((long) i);
            alphaAnimation.setFillAfter(true);
            if (animationListener != null) {
                alphaAnimation.setAnimationListener(animationListener);
            }
            view.startAnimation(alphaAnimation);
            return alphaAnimation;
        }
        view.setAlpha(1.0f);
        view.setVisibility(0);
        return null;
    }

    public static AlphaAnimation fadeOut(final View view, int i, final Animation.AnimationListener animationListener, boolean z) {
        if (view == null) {
            return null;
        }
        if (z) {
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setInterpolator(new DecelerateInterpolator());
            alphaAnimation.setDuration((long) i);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    Animation.AnimationListener animationListener = animationListener;
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(8);
                    Animation.AnimationListener animationListener = animationListener;
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                    Animation.AnimationListener animationListener = animationListener;
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }
            });
            view.startAnimation(alphaAnimation);
            return alphaAnimation;
        }
        view.setVisibility(8);
        return null;
    }

    public static void clearValueAnimator(Animator animator) {
        if (animator != null) {
            animator.removeAllListeners();
            if (animator instanceof ValueAnimator) {
                ((ValueAnimator) animator).removeAllUpdateListeners();
            }
            if (Build.VERSION.SDK_INT >= 19) {
                animator.pause();
            }
            animator.cancel();
        }
    }

    public static Rect calcViewScreenLocation(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return new Rect(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
    }

    /* renamed from: com.qmuiteam.qmui.util.QMUIViewHelper$7 */
    static /* synthetic */ class C16367 {
        static final /* synthetic */ int[] $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection = new int[QMUIDirection.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.qmuiteam.qmui.util.QMUIDirection[] r0 = com.qmuiteam.qmui.util.QMUIDirection.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection = r0
                int[] r0 = $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.qmuiteam.qmui.util.QMUIDirection r1 = com.qmuiteam.qmui.util.QMUIDirection.LEFT_TO_RIGHT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection     // Catch:{ NoSuchFieldError -> 0x001f }
                com.qmuiteam.qmui.util.QMUIDirection r1 = com.qmuiteam.qmui.util.QMUIDirection.TOP_TO_BOTTOM     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection     // Catch:{ NoSuchFieldError -> 0x002a }
                com.qmuiteam.qmui.util.QMUIDirection r1 = com.qmuiteam.qmui.util.QMUIDirection.RIGHT_TO_LEFT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$qmuiteam$qmui$util$QMUIDirection     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.qmuiteam.qmui.util.QMUIDirection r1 = com.qmuiteam.qmui.util.QMUIDirection.BOTTOM_TO_TOP     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.util.QMUIViewHelper.C16367.<clinit>():void");
        }
    }

    @Nullable
    public static TranslateAnimation slideIn(View view, int i, Animation.AnimationListener animationListener, boolean z, QMUIDirection qMUIDirection) {
        View view2 = view;
        Animation.AnimationListener animationListener2 = animationListener;
        TranslateAnimation translateAnimation = null;
        if (view2 == null) {
            return null;
        }
        if (z) {
            int i2 = C16367.$SwitchMap$com$qmuiteam$qmui$util$QMUIDirection[qMUIDirection.ordinal()];
            if (i2 == 1) {
                translateAnimation = new TranslateAnimation(1, -1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            } else if (i2 == 2) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
            } else if (i2 == 3) {
                translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            } else if (i2 == 4) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            }
            translateAnimation.setInterpolator(new DecelerateInterpolator());
            translateAnimation.setDuration((long) i);
            translateAnimation.setFillAfter(true);
            if (animationListener2 != null) {
                translateAnimation.setAnimationListener(animationListener2);
            }
            view2.setVisibility(0);
            view2.startAnimation(translateAnimation);
            return translateAnimation;
        }
        view.clearAnimation();
        view2.setVisibility(0);
        return null;
    }

    @Nullable
    public static TranslateAnimation slideOut(View view, int i, Animation.AnimationListener animationListener, boolean z, QMUIDirection qMUIDirection) {
        final View view2 = view;
        TranslateAnimation translateAnimation = null;
        if (view2 == null) {
            return null;
        }
        if (z) {
            int i2 = C16367.$SwitchMap$com$qmuiteam$qmui$util$QMUIDirection[qMUIDirection.ordinal()];
            if (i2 == 1) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
            } else if (i2 == 2) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
            } else if (i2 == 3) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, -1.0f, 1, 0.0f, 1, 0.0f);
            } else if (i2 == 4) {
                translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
            }
            translateAnimation.setInterpolator(new DecelerateInterpolator());
            translateAnimation.setDuration((long) i);
            final Animation.AnimationListener animationListener2 = animationListener;
            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    Animation.AnimationListener animationListener = animationListener2;
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                public void onAnimationEnd(Animation animation) {
                    view2.setVisibility(8);
                    Animation.AnimationListener animationListener = animationListener2;
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }

                public void onAnimationRepeat(Animation animation) {
                    Animation.AnimationListener animationListener = animationListener2;
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }
            });
            view2.startAnimation(translateAnimation);
            return translateAnimation;
        }
        view.clearAnimation();
        view2.setVisibility(8);
        return null;
    }

    public static void setPaddingLeft(View view, int i) {
        view.setPadding(i, view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setPaddingTop(View view, int i) {
        view.setPadding(view.getPaddingLeft(), i, view.getPaddingRight(), view.getPaddingBottom());
    }

    public static void setPaddingRight(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), i, view.getPaddingBottom());
    }

    public static void setPaddingBottom(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    public static boolean getIsLastLineSpacingExtraError() {
        return Build.VERSION.SDK_INT < 21;
    }

    public static View findViewFromViewStub(View view, int i, int i2) {
        if (view == null) {
            return null;
        }
        View findViewById = view.findViewById(i2);
        if (findViewById != null) {
            return findViewById;
        }
        ViewStub viewStub = (ViewStub) view.findViewById(i);
        if (viewStub == null) {
            return null;
        }
        View inflate = viewStub.inflate();
        return inflate != null ? inflate.findViewById(i2) : inflate;
    }

    public static View findViewFromViewStub(View view, int i, int i2, int i3) {
        if (view == null) {
            return null;
        }
        View findViewById = view.findViewById(i2);
        if (findViewById != null) {
            return findViewById;
        }
        ViewStub viewStub = (ViewStub) view.findViewById(i);
        if (viewStub == null) {
            return null;
        }
        if (viewStub.getLayoutResource() < 1 && i3 > 0) {
            viewStub.setLayoutResource(i3);
        }
        View inflate = viewStub.inflate();
        return inflate != null ? inflate.findViewById(i2) : inflate;
    }

    public static void safeSetImageViewSelected(ImageView imageView, boolean z) {
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            imageView.setSelected(z);
            if (drawable.getIntrinsicWidth() != intrinsicWidth || drawable.getIntrinsicHeight() != intrinsicHeight) {
                imageView.requestLayout();
            }
        }
    }

    public static ColorFilter setImageViewTintColor(ImageView imageView, @ColorInt int i) {
        LightingColorFilter lightingColorFilter = new LightingColorFilter(Color.argb(255, 0, 0, 0), i);
        imageView.setColorFilter(lightingColorFilter);
        return lightingColorFilter;
    }

    public static boolean isListViewAlreadyAtBottom(ListView listView) {
        View childAt;
        if (listView.getAdapter() == null || listView.getHeight() == 0 || listView.getLastVisiblePosition() != listView.getAdapter().getCount() - 1 || (childAt = listView.getChildAt(listView.getChildCount() - 1)) == null || childAt.getBottom() != listView.getHeight()) {
            return false;
        }
        return true;
    }

    public static void getDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ViewGroupHelper.offsetDescendantRect(viewGroup, view, rect);
    }

    private static class ViewGroupHelper {
        private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<>();
        private static final ThreadLocal<RectF> sRectF = new ThreadLocal<>();

        private ViewGroupHelper() {
        }

        public static void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
            Matrix matrix = sMatrix.get();
            if (matrix == null) {
                matrix = new Matrix();
                sMatrix.set(matrix);
            } else {
                matrix.reset();
            }
            offsetDescendantMatrix(viewGroup, view, matrix);
            RectF rectF = sRectF.get();
            if (rectF == null) {
                rectF = new RectF();
                sRectF.set(rectF);
            }
            rectF.set(rect);
            matrix.mapRect(rectF);
            rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
        }

        static void offsetDescendantMatrix(ViewParent viewParent, View view, Matrix matrix) {
            ViewParent parent = view.getParent();
            if ((parent instanceof View) && parent != viewParent) {
                View view2 = (View) parent;
                offsetDescendantMatrix(viewParent, view2, matrix);
                matrix.preTranslate((float) (-view2.getScrollX()), (float) (-view2.getScrollY()));
            }
            matrix.preTranslate((float) view.getLeft(), (float) view.getTop());
            if (!view.getMatrix().isIdentity()) {
                matrix.preConcat(view.getMatrix());
            }
        }
    }
}
