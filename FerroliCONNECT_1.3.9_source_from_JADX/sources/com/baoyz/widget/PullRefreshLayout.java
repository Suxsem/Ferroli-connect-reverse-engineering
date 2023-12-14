package com.baoyz.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;
import android.widget.ImageView;
import java.security.InvalidParameterException;

public class PullRefreshLayout extends ViewGroup {
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2.0f;
    private static final int DRAG_MAX_DISTANCE = 64;
    private static final float DRAG_RATE = 0.5f;
    private static final int INVALID_POINTER = -1;
    public static final int STYLE_CIRCLES = 1;
    public static final int STYLE_MATERIAL = 0;
    public static final int STYLE_RING = 3;
    public static final int STYLE_SMARTISAN = 4;
    public static final int STYLE_WATER_DROP = 2;
    private int mActivePointerId;
    private final Animation mAnimateToCorrectPosition;
    private final Animation mAnimateToStartPosition;
    private int[] mColorSchemeColors;
    /* access modifiers changed from: private */
    public int mCurrentOffsetTop;
    private Interpolator mDecelerateInterpolator;
    private boolean mDispatchTargetTouchDown;
    private float mDragPercent;
    public int mDurationToCorrectPosition;
    public int mDurationToStartPosition;
    /* access modifiers changed from: private */
    public int mFrom;
    private float mInitialMotionY;
    private int mInitialOffsetTop;
    private boolean mIsBeingDragged;
    /* access modifiers changed from: private */
    public OnRefreshListener mListener;
    /* access modifiers changed from: private */
    public boolean mNotify;
    /* access modifiers changed from: private */
    public RefreshDrawable mRefreshDrawable;
    private Animation.AnimationListener mRefreshListener;
    /* access modifiers changed from: private */
    public ImageView mRefreshView;
    /* access modifiers changed from: private */
    public boolean mRefreshing;
    /* access modifiers changed from: private */
    public int mSpinnerFinalOffset;
    /* access modifiers changed from: private */
    public View mTarget;
    private Animation.AnimationListener mToStartListener;
    private int mTotalDragDistance;
    private int mTouchSlop;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public PullRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public PullRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimateToStartPosition = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                PullRefreshLayout.this.moveToStart(f);
            }
        };
        this.mAnimateToCorrectPosition = new Animation() {
            public void applyTransformation(float f, Transformation transformation) {
                PullRefreshLayout.this.setTargetOffsetTop((PullRefreshLayout.this.mFrom + ((int) (((float) (PullRefreshLayout.this.mSpinnerFinalOffset - PullRefreshLayout.this.mFrom)) * f))) - PullRefreshLayout.this.mTarget.getTop(), false);
            }
        };
        this.mRefreshListener = new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                PullRefreshLayout.this.mRefreshView.setVisibility(0);
            }

            public void onAnimationEnd(Animation animation) {
                if (PullRefreshLayout.this.mRefreshing) {
                    PullRefreshLayout.this.mRefreshDrawable.start();
                    if (PullRefreshLayout.this.mNotify && PullRefreshLayout.this.mListener != null) {
                        PullRefreshLayout.this.mListener.onRefresh();
                    }
                } else {
                    PullRefreshLayout.this.mRefreshDrawable.stop();
                    PullRefreshLayout.this.mRefreshView.setVisibility(8);
                    PullRefreshLayout.this.animateOffsetToStartPosition();
                }
                PullRefreshLayout pullRefreshLayout = PullRefreshLayout.this;
                int unused = pullRefreshLayout.mCurrentOffsetTop = pullRefreshLayout.mTarget.getTop();
            }
        };
        this.mToStartListener = new Animation.AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
                PullRefreshLayout.this.mRefreshDrawable.stop();
            }

            public void onAnimationEnd(Animation animation) {
                PullRefreshLayout.this.mRefreshView.setVisibility(8);
                PullRefreshLayout pullRefreshLayout = PullRefreshLayout.this;
                int unused = pullRefreshLayout.mCurrentOffsetTop = pullRefreshLayout.mTarget.getTop();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0932R.styleable.refresh_PullRefreshLayout);
        int integer = obtainStyledAttributes.getInteger(C0932R.styleable.refresh_PullRefreshLayout_refreshType, 0);
        int resourceId = obtainStyledAttributes.getResourceId(C0932R.styleable.refresh_PullRefreshLayout_refreshColors, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(C0932R.styleable.refresh_PullRefreshLayout_refreshColor, 0);
        obtainStyledAttributes.recycle();
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        int integer2 = getResources().getInteger(17694721);
        this.mDurationToStartPosition = integer2;
        this.mDurationToCorrectPosition = integer2;
        int dp2px = dp2px(64);
        this.mTotalDragDistance = dp2px;
        this.mSpinnerFinalOffset = dp2px;
        if (resourceId > 0) {
            this.mColorSchemeColors = context.getResources().getIntArray(resourceId);
        } else {
            this.mColorSchemeColors = new int[]{Color.rgb(201, 52, 55), Color.rgb(55, 91, 241), Color.rgb(247, 210, 62), Color.rgb(52, 163, 80)};
        }
        if (resourceId2 > 0) {
            this.mColorSchemeColors = new int[]{context.getResources().getColor(resourceId2)};
        }
        this.mRefreshView = new ImageView(context);
        setRefreshStyle(integer);
        this.mRefreshView.setVisibility(8);
        addView(this.mRefreshView, 0);
        setWillNotDraw(false);
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    public void setColorSchemeColors(int... iArr) {
        this.mColorSchemeColors = iArr;
        this.mRefreshDrawable.setColorSchemeColors(iArr);
    }

    public void setColor(int i) {
        setColorSchemeColors(i);
    }

    public void setRefreshStyle(int i) {
        setRefreshing(false);
        if (i == 0) {
            this.mRefreshDrawable = new MaterialDrawable(getContext(), this);
        } else if (i == 1) {
            this.mRefreshDrawable = new CirclesDrawable(getContext(), this);
        } else if (i == 2) {
            this.mRefreshDrawable = new WaterDropDrawable(getContext(), this);
        } else if (i == 3) {
            this.mRefreshDrawable = new RingDrawable(getContext(), this);
        } else if (i == 4) {
            this.mRefreshDrawable = new SmartisanDrawable(getContext(), this);
        } else {
            throw new InvalidParameterException("Type does not exist");
        }
        this.mRefreshDrawable.setColorSchemeColors(this.mColorSchemeColors);
        this.mRefreshView.setImageDrawable(this.mRefreshDrawable);
    }

    public void setRefreshDrawable(RefreshDrawable refreshDrawable) {
        setRefreshing(false);
        this.mRefreshDrawable = refreshDrawable;
        this.mRefreshDrawable.setColorSchemeColors(this.mColorSchemeColors);
        this.mRefreshView.setImageDrawable(this.mRefreshDrawable);
    }

    public int getFinalOffset() {
        return this.mSpinnerFinalOffset;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        ensureTarget();
        if (this.mTarget != null) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingRight()) - getPaddingLeft(), 1073741824);
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824);
            this.mTarget.measure(makeMeasureSpec, makeMeasureSpec2);
            this.mRefreshView.measure(makeMeasureSpec, makeMeasureSpec2);
        }
    }

    private void ensureTarget() {
        if (this.mTarget == null && getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt != this.mRefreshView) {
                    this.mTarget = childAt;
                }
            }
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (!isEnabled() || (canChildScrollUp() && !this.mRefreshing)) {
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i = this.mActivePointerId;
                    if (i == -1) {
                        return false;
                    }
                    float motionEventY = getMotionEventY(motionEvent, i);
                    if (motionEventY == -1.0f) {
                        return false;
                    }
                    float f = motionEventY - this.mInitialMotionY;
                    if (this.mRefreshing) {
                        if (f >= 0.0f || this.mCurrentOffsetTop > 0) {
                            z = true;
                        }
                        this.mIsBeingDragged = z;
                    } else if (f > ((float) this.mTouchSlop) && !this.mIsBeingDragged) {
                        this.mIsBeingDragged = true;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
        } else {
            if (!this.mRefreshing) {
                setTargetOffsetTop(0, true);
            }
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
            this.mIsBeingDragged = false;
            float motionEventY2 = getMotionEventY(motionEvent, this.mActivePointerId);
            if (motionEventY2 == -1.0f) {
                return false;
            }
            this.mInitialMotionY = motionEventY2;
            this.mInitialOffsetTop = this.mCurrentOffsetTop;
            this.mDispatchTargetTouchDown = false;
            this.mDragPercent = 0.0f;
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsBeingDragged) {
            return super.onTouchEvent(motionEvent);
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        int i = -1;
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                if (findPointerIndex < 0) {
                    return false;
                }
                float y = MotionEventCompat.getY(motionEvent, findPointerIndex);
                float f = y - this.mInitialMotionY;
                if (this.mRefreshing) {
                    int i2 = (int) (((float) this.mInitialOffsetTop) + f);
                    if (canChildScrollUp()) {
                        this.mInitialMotionY = y;
                        this.mInitialOffsetTop = 0;
                        if (this.mDispatchTargetTouchDown) {
                            this.mTarget.dispatchTouchEvent(motionEvent);
                        } else {
                            MotionEvent obtain = MotionEvent.obtain(motionEvent);
                            obtain.setAction(0);
                            this.mDispatchTargetTouchDown = true;
                            this.mTarget.dispatchTouchEvent(obtain);
                        }
                    } else if (i2 < 0) {
                        if (this.mDispatchTargetTouchDown) {
                            this.mTarget.dispatchTouchEvent(motionEvent);
                        } else {
                            MotionEvent obtain2 = MotionEvent.obtain(motionEvent);
                            obtain2.setAction(0);
                            this.mDispatchTargetTouchDown = true;
                            this.mTarget.dispatchTouchEvent(obtain2);
                        }
                        i = 0;
                    } else {
                        i = this.mTotalDragDistance;
                        if (i2 <= i) {
                            if (this.mDispatchTargetTouchDown) {
                                MotionEvent obtain3 = MotionEvent.obtain(motionEvent);
                                obtain3.setAction(3);
                                this.mDispatchTargetTouchDown = false;
                                this.mTarget.dispatchTouchEvent(obtain3);
                            }
                            i = i2;
                        }
                    }
                } else {
                    float f2 = f * DRAG_RATE;
                    float f3 = f2 / ((float) this.mTotalDragDistance);
                    if (f3 < 0.0f) {
                        return false;
                    }
                    this.mDragPercent = Math.min(1.0f, Math.abs(f3));
                    float abs = Math.abs(f2) - ((float) this.mTotalDragDistance);
                    float f4 = (float) this.mSpinnerFinalOffset;
                    double max = (double) (Math.max(0.0f, Math.min(abs, f4 * 2.0f) / f4) / 4.0f);
                    double pow = Math.pow(max, 2.0d);
                    Double.isNaN(max);
                    i = (int) ((f4 * this.mDragPercent) + (((float) (max - pow)) * 2.0f * f4 * 2.0f));
                    if (this.mRefreshView.getVisibility() != 0) {
                        this.mRefreshView.setVisibility(0);
                    }
                    if (f2 < ((float) this.mTotalDragDistance)) {
                        this.mRefreshDrawable.setPercent(this.mDragPercent);
                    }
                }
                setTargetOffsetTop(i - this.mCurrentOffsetTop, true);
            } else if (actionMasked != 3) {
                if (actionMasked == 5) {
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, MotionEventCompat.getActionIndex(motionEvent));
                } else if (actionMasked == 6) {
                    onSecondaryPointerUp(motionEvent);
                }
            }
            return true;
        }
        int i3 = this.mActivePointerId;
        if (i3 == -1) {
            return false;
        }
        if (this.mRefreshing) {
            if (this.mDispatchTargetTouchDown) {
                this.mTarget.dispatchTouchEvent(motionEvent);
                this.mDispatchTargetTouchDown = false;
            }
            return false;
        }
        float y2 = (MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, i3)) - this.mInitialMotionY) * DRAG_RATE;
        this.mIsBeingDragged = false;
        if (y2 > ((float) this.mTotalDragDistance)) {
            setRefreshing(true, true);
        } else {
            this.mRefreshing = false;
            animateOffsetToStartPosition();
        }
        this.mActivePointerId = -1;
        return false;
    }

    public void setDurations(int i, int i2) {
        this.mDurationToStartPosition = i;
        this.mDurationToCorrectPosition = i2;
    }

    /* access modifiers changed from: private */
    public void animateOffsetToStartPosition() {
        this.mFrom = this.mCurrentOffsetTop;
        this.mAnimateToStartPosition.reset();
        this.mAnimateToStartPosition.setDuration((long) this.mDurationToStartPosition);
        this.mAnimateToStartPosition.setInterpolator(this.mDecelerateInterpolator);
        this.mAnimateToStartPosition.setAnimationListener(this.mToStartListener);
        this.mRefreshView.clearAnimation();
        this.mRefreshView.startAnimation(this.mAnimateToStartPosition);
    }

    private void animateOffsetToCorrectPosition() {
        this.mFrom = this.mCurrentOffsetTop;
        this.mAnimateToCorrectPosition.reset();
        this.mAnimateToCorrectPosition.setDuration((long) this.mDurationToCorrectPosition);
        this.mAnimateToCorrectPosition.setInterpolator(this.mDecelerateInterpolator);
        this.mAnimateToCorrectPosition.setAnimationListener(this.mRefreshListener);
        this.mRefreshView.clearAnimation();
        this.mRefreshView.startAnimation(this.mAnimateToCorrectPosition);
    }

    /* access modifiers changed from: private */
    public void moveToStart(float f) {
        int i = this.mFrom;
        setTargetOffsetTop((i - ((int) (((float) i) * f))) - this.mTarget.getTop(), false);
        this.mRefreshDrawable.setPercent(this.mDragPercent * (1.0f - f));
    }

    public void setRefreshing(boolean z) {
        if (this.mRefreshing != z) {
            setRefreshing(z, false);
        }
    }

    private void setRefreshing(boolean z, boolean z2) {
        if (this.mRefreshing != z) {
            this.mNotify = z2;
            ensureTarget();
            this.mRefreshing = z;
            if (this.mRefreshing) {
                this.mRefreshDrawable.setPercent(1.0f);
                animateOffsetToCorrectPosition();
                return;
            }
            animateOffsetToStartPosition();
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mActivePointerId) {
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex == 0 ? 1 : 0);
        }
    }

    private float getMotionEventY(MotionEvent motionEvent, int i) {
        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, i);
        if (findPointerIndex < 0) {
            return -1.0f;
        }
        return MotionEventCompat.getY(motionEvent, findPointerIndex);
    }

    /* access modifiers changed from: private */
    public void setTargetOffsetTop(int i, boolean z) {
        this.mTarget.offsetTopAndBottom(i);
        this.mCurrentOffsetTop = this.mTarget.getTop();
        this.mRefreshDrawable.offsetTopAndBottom(i);
        if (z && Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTarget, -1);
        }
        View view = this.mTarget;
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            if (absListView.getChildCount() <= 0 || (absListView.getFirstVisiblePosition() <= 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop())) {
                return false;
            }
            return true;
        } else if (view.getScrollY() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ensureTarget();
        if (this.mTarget != null) {
            int measuredHeight = getMeasuredHeight();
            int measuredWidth = getMeasuredWidth();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            View view = this.mTarget;
            int i5 = (measuredWidth + paddingLeft) - paddingRight;
            int i6 = (measuredHeight + paddingTop) - paddingBottom;
            view.layout(paddingLeft, view.getTop() + paddingTop, i5, this.mTarget.getTop() + i6);
            this.mRefreshView.layout(paddingLeft, paddingTop, i5, i6);
        }
    }

    private int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getContext().getResources().getDisplayMetrics());
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mListener = onRefreshListener;
    }
}
