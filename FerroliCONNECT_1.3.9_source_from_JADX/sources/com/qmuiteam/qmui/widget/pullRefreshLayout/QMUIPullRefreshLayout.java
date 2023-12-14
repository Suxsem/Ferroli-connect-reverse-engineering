package com.qmuiteam.qmui.widget.pullRefreshLayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.MotionEventCompat;
import android.support.p000v4.view.NestedScrollingParent;
import android.support.p000v4.view.NestedScrollingParentHelper;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.widget.CircularProgressDrawable;
import android.support.p003v7.widget.AppCompatImageView;
import android.support.p003v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Scroller;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIResHelper;

public class QMUIPullRefreshLayout extends ViewGroup implements NestedScrollingParent {
    private static final int FLAG_NEED_DELIVER_VELOCITY = 8;
    private static final int FLAG_NEED_DO_REFRESH = 4;
    private static final int FLAG_NEED_SCROLL_TO_INIT_POSITION = 1;
    private static final int FLAG_NEED_SCROLL_TO_REFRESH_POSITION = 2;
    private static final int INVALID_POINTER = -1;
    private static final String TAG = "QMUIPullRefreshLayout";
    private int mActivePointerId;
    private boolean mAutoCalculateRefreshEndOffset;
    private boolean mAutoCalculateRefreshInitOffset;
    private int mAutoScrollToRefreshMinOffset;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private float mDragRate;
    private boolean mEnableOverPull;
    private boolean mEqualTargetRefreshOffsetToRefreshViewHeight;
    private IRefreshView mIRefreshView;
    private float mInitialDownX;
    private float mInitialDownY;
    private float mInitialMotionY;
    private boolean mIsDragging;
    boolean mIsRefreshing;
    private float mLastMotionY;
    private OnPullListener mListener;
    private float mMaxVelocity;
    private float mMiniVelocity;
    private boolean mNestScrollDurationRefreshing;
    private boolean mNestedScrollInProgress;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int mRefreshCurrentOffset;
    private int mRefreshEndOffset;
    private int mRefreshInitOffset;
    private RefreshOffsetCalculator mRefreshOffsetCalculator;
    private View mRefreshView;
    private int mRefreshZIndex;
    private int mScrollFlag;
    private Scroller mScroller;
    private int mSystemTouchSlop;
    private int mTargetCurrentOffset;
    private int mTargetInitOffset;
    private int mTargetRefreshOffset;
    private View mTargetView;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public interface IRefreshView {
        void doRefresh();

        void onPull(int i, int i2, int i3);

        void stop();
    }

    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(QMUIPullRefreshLayout qMUIPullRefreshLayout, @Nullable View view);
    }

    public interface OnPullListener {
        void onMoveRefreshView(int i);

        void onMoveTarget(int i);

        void onRefresh();
    }

    public interface RefreshOffsetCalculator {
        int calculateRefreshOffset(int i, int i2, int i3, int i4, int i5, int i6);
    }

    private void info(String str) {
    }

    /* access modifiers changed from: protected */
    public void onFinishPull(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
    }

    /* access modifiers changed from: protected */
    public void onMoveRefreshView(int i) {
    }

    /* access modifiers changed from: protected */
    public void onMoveTargetView(int i) {
    }

    /* access modifiers changed from: protected */
    public void onSureTargetView(View view) {
    }

    public QMUIPullRefreshLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIPullRefreshLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUIPullRefreshLayoutStyle);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x00ab A[Catch:{ all -> 0x00cb }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public QMUIPullRefreshLayout(android.content.Context r5, android.util.AttributeSet r6, int r7) {
        /*
            r4 = this;
            r4.<init>(r5, r6, r7)
            r0 = 0
            r4.mIsRefreshing = r0
            r1 = -1
            r4.mRefreshZIndex = r1
            r2 = 1
            r4.mAutoCalculateRefreshInitOffset = r2
            r4.mAutoCalculateRefreshEndOffset = r2
            r4.mEqualTargetRefreshOffsetToRefreshViewHeight = r0
            r4.mAutoScrollToRefreshMinOffset = r1
            r4.mEnableOverPull = r2
            r4.mActivePointerId = r1
            r1 = 1059481190(0x3f266666, float:0.65)
            r4.mDragRate = r1
            r4.mScrollFlag = r0
            r4.mNestScrollDurationRefreshing = r0
            r4.setWillNotDraw(r0)
            android.view.ViewConfiguration r1 = android.view.ViewConfiguration.get(r5)
            int r3 = r1.getScaledMaximumFlingVelocity()
            float r3 = (float) r3
            r4.mMaxVelocity = r3
            int r3 = r1.getScaledMinimumFlingVelocity()
            float r3 = (float) r3
            r4.mMiniVelocity = r3
            int r1 = r1.getScaledTouchSlop()
            r4.mSystemTouchSlop = r1
            int r1 = r4.mSystemTouchSlop
            int r1 = com.qmuiteam.qmui.util.QMUIDisplayHelper.px2dp(r5, r1)
            r4.mTouchSlop = r1
            android.widget.Scroller r1 = new android.widget.Scroller
            android.content.Context r3 = r4.getContext()
            r1.<init>(r3)
            r4.mScroller = r1
            android.widget.Scroller r1 = r4.mScroller
            float r3 = r4.getScrollerFriction()
            r1.setFriction(r3)
            r4.addRefreshView()
            android.support.p000v4.view.ViewCompat.setChildrenDrawingOrderEnabled(r4, r2)
            android.support.v4.view.NestedScrollingParentHelper r1 = new android.support.v4.view.NestedScrollingParentHelper
            r1.<init>(r4)
            r4.mNestedScrollingParentHelper = r1
            int[] r1 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout
            android.content.res.TypedArray r5 = r5.obtainStyledAttributes(r6, r1, r7, r0)
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_refresh_init_offset     // Catch:{ all -> 0x00cb }
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            int r6 = r5.getDimensionPixelSize(r6, r7)     // Catch:{ all -> 0x00cb }
            r4.mRefreshInitOffset = r6     // Catch:{ all -> 0x00cb }
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_refresh_end_offset     // Catch:{ all -> 0x00cb }
            int r6 = r5.getDimensionPixelSize(r6, r7)     // Catch:{ all -> 0x00cb }
            r4.mRefreshEndOffset = r6     // Catch:{ all -> 0x00cb }
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_target_init_offset     // Catch:{ all -> 0x00cb }
            int r6 = r5.getDimensionPixelSize(r6, r0)     // Catch:{ all -> 0x00cb }
            r4.mTargetInitOffset = r6     // Catch:{ all -> 0x00cb }
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_target_refresh_offset     // Catch:{ all -> 0x00cb }
            android.content.Context r1 = r4.getContext()     // Catch:{ all -> 0x00cb }
            r3 = 72
            int r1 = com.qmuiteam.qmui.util.QMUIDisplayHelper.dp2px(r1, r3)     // Catch:{ all -> 0x00cb }
            int r6 = r5.getDimensionPixelSize(r6, r1)     // Catch:{ all -> 0x00cb }
            r4.mTargetRefreshOffset = r6     // Catch:{ all -> 0x00cb }
            int r6 = r4.mRefreshInitOffset     // Catch:{ all -> 0x00cb }
            if (r6 == r7) goto L_0x00a4
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_auto_calculate_refresh_init_offset     // Catch:{ all -> 0x00cb }
            boolean r6 = r5.getBoolean(r6, r0)     // Catch:{ all -> 0x00cb }
            if (r6 == 0) goto L_0x00a2
            goto L_0x00a4
        L_0x00a2:
            r6 = 0
            goto L_0x00a5
        L_0x00a4:
            r6 = 1
        L_0x00a5:
            r4.mAutoCalculateRefreshInitOffset = r6     // Catch:{ all -> 0x00cb }
            int r6 = r4.mRefreshEndOffset     // Catch:{ all -> 0x00cb }
            if (r6 == r7) goto L_0x00b5
            int r6 = com.qmuiteam.qmui.C1614R.styleable.QMUIPullRefreshLayout_qmui_auto_calculate_refresh_end_offset     // Catch:{ all -> 0x00cb }
            boolean r6 = r5.getBoolean(r6, r0)     // Catch:{ all -> 0x00cb }
            if (r6 == 0) goto L_0x00b4
            goto L_0x00b5
        L_0x00b4:
            r2 = 0
        L_0x00b5:
            r4.mAutoCalculateRefreshEndOffset = r2     // Catch:{ all -> 0x00cb }
            int r6 = com.qmuiteam.qmui.C1614R.styleable.f3103xe8f0a21e     // Catch:{ all -> 0x00cb }
            boolean r6 = r5.getBoolean(r6, r0)     // Catch:{ all -> 0x00cb }
            r4.mEqualTargetRefreshOffsetToRefreshViewHeight = r6     // Catch:{ all -> 0x00cb }
            r5.recycle()
            int r5 = r4.mRefreshInitOffset
            r4.mRefreshCurrentOffset = r5
            int r5 = r4.mTargetInitOffset
            r4.mTargetCurrentOffset = r5
            return
        L_0x00cb:
            r6 = move-exception
            r5.recycle()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public static boolean defaultCanScrollUp(View view) {
        if (view == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(view, -1);
        }
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;
            if (absListView.getChildCount() <= 0) {
                return false;
            }
            if (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop()) {
                return true;
            }
            return false;
        } else if (ViewCompat.canScrollVertically(view, -1) || view.getScrollY() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.mListener = onPullListener;
    }

    public void setChildScrollUpCallback(OnChildScrollUpCallback onChildScrollUpCallback) {
        this.mChildScrollUpCallback = onChildScrollUpCallback;
    }

    /* access modifiers changed from: protected */
    public float getScrollerFriction() {
        return ViewConfiguration.getScrollFriction();
    }

    public void setAutoScrollToRefreshMinOffset(int i) {
        this.mAutoScrollToRefreshMinOffset = i;
    }

    /* access modifiers changed from: protected */
    public View createRefreshView() {
        return new RefreshView(getContext());
    }

    private void addRefreshView() {
        if (this.mRefreshView == null) {
            this.mRefreshView = createRefreshView();
        }
        View view = this.mRefreshView;
        if (view instanceof IRefreshView) {
            this.mIRefreshView = (IRefreshView) view;
            if (view.getLayoutParams() == null) {
                this.mRefreshView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            addView(this.mRefreshView);
            return;
        }
        throw new RuntimeException("refreshView must be a instance of IRefreshView");
    }

    public void setRefreshOffsetCalculator(RefreshOffsetCalculator refreshOffsetCalculator) {
        this.mRefreshOffsetCalculator = refreshOffsetCalculator;
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        int i3 = this.mRefreshZIndex;
        if (i3 < 0) {
            return i2;
        }
        if (i2 == i3) {
            return i - 1;
        }
        return i2 > i3 ? i2 - 1 : i2;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (Build.VERSION.SDK_INT >= 21 || !(this.mTargetView instanceof AbsListView)) {
            View view = this.mTargetView;
            if (view == null || ViewCompat.isNestedScrollingEnabled(view)) {
                super.requestDisallowInterceptTouchEvent(z);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        super.onMeasure(i, i2);
        ensureTargetView();
        if (this.mTargetView == null) {
            Log.d(TAG, "onMeasure: mTargetView == null");
            return;
        }
        this.mTargetView.measure(View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824));
        measureChild(this.mRefreshView, i, i2);
        this.mRefreshZIndex = -1;
        int i4 = 0;
        while (true) {
            if (i4 >= getChildCount()) {
                break;
            } else if (getChildAt(i4) == this.mRefreshView) {
                this.mRefreshZIndex = i4;
                break;
            } else {
                i4++;
            }
        }
        int measuredHeight = this.mRefreshView.getMeasuredHeight();
        if (this.mAutoCalculateRefreshInitOffset && this.mRefreshInitOffset != (i3 = -measuredHeight)) {
            this.mRefreshInitOffset = i3;
            this.mRefreshCurrentOffset = this.mRefreshInitOffset;
        }
        if (this.mEqualTargetRefreshOffsetToRefreshViewHeight) {
            this.mTargetRefreshOffset = measuredHeight;
        }
        if (this.mAutoCalculateRefreshEndOffset) {
            this.mRefreshEndOffset = (this.mTargetRefreshOffset - measuredHeight) / 2;
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (getChildCount() != 0) {
            ensureTargetView();
            if (this.mTargetView == null) {
                Log.d(TAG, "onLayout: mTargetView == null");
                return;
            }
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingLeft2 = (measuredWidth - getPaddingLeft()) - getPaddingRight();
            int paddingTop2 = (measuredHeight - getPaddingTop()) - getPaddingBottom();
            View view = this.mTargetView;
            int i5 = this.mTargetCurrentOffset;
            view.layout(paddingLeft, paddingTop + i5, paddingLeft2 + paddingLeft, paddingTop + paddingTop2 + i5);
            int measuredWidth2 = this.mRefreshView.getMeasuredWidth();
            int measuredHeight2 = this.mRefreshView.getMeasuredHeight();
            int i6 = measuredWidth / 2;
            int i7 = measuredWidth2 / 2;
            int i8 = this.mRefreshCurrentOffset;
            this.mRefreshView.layout(i6 - i7, i8, i6 + i7, measuredHeight2 + i8);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        ensureTargetView();
        int action = motionEvent.getAction();
        if (!isEnabled() || canChildScrollUp() || this.mNestedScrollInProgress) {
            return false;
        }
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex < 0) {
                        Log.e(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                        return false;
                    }
                    startDragging(motionEvent.getX(findPointerIndex), motionEvent.getY(findPointerIndex));
                } else if (action != 3) {
                    if (action == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            this.mIsDragging = false;
            this.mActivePointerId = -1;
        } else {
            this.mIsDragging = false;
            this.mActivePointerId = motionEvent.getPointerId(0);
            int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
            if (findPointerIndex2 < 0) {
                return false;
            }
            this.mInitialDownX = motionEvent.getX(findPointerIndex2);
            this.mInitialDownY = motionEvent.getY(findPointerIndex2);
        }
        return this.mIsDragging;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (!isEnabled() || canChildScrollUp() || this.mNestedScrollInProgress) {
            Log.d(TAG, "fast end onTouchEvent: isEnabled = " + isEnabled() + "; canChildScrollUp = " + canChildScrollUp() + " ; mNestedScrollInProgress = " + this.mNestedScrollInProgress);
            return false;
        }
        acquireVelocityTracker(motionEvent);
        if (action == 0) {
            this.mIsDragging = false;
            this.mScrollFlag = 0;
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action != 1) {
            if (action == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex < 0) {
                    Log.e(TAG, "onTouchEvent Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                float x = motionEvent.getX(findPointerIndex);
                float y = motionEvent.getY(findPointerIndex);
                startDragging(x, y);
                if (this.mIsDragging) {
                    float f = (y - this.mLastMotionY) * this.mDragRate;
                    if (f >= 0.0f) {
                        moveTargetView(f, true);
                    } else {
                        float abs = Math.abs(f) - ((float) Math.abs(moveTargetView(f, true)));
                        if (abs > 0.0f) {
                            motionEvent.setAction(0);
                            float f2 = (float) (this.mSystemTouchSlop + 1);
                            if (abs <= f2) {
                                abs = f2;
                            }
                            motionEvent.offsetLocation(0.0f, abs);
                            dispatchTouchEvent(motionEvent);
                            motionEvent.setAction(action);
                            motionEvent.offsetLocation(0.0f, -abs);
                            dispatchTouchEvent(motionEvent);
                        }
                    }
                    this.mLastMotionY = y;
                }
            } else if (action == 3) {
                releaseVelocityTracker();
                return false;
            } else if (action == 5) {
                int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                if (actionIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return false;
                }
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            } else if (action == 6) {
                onSecondaryPointerUp(motionEvent);
            }
        } else if (motionEvent.findPointerIndex(this.mActivePointerId) < 0) {
            Log.e(TAG, "Got ACTION_UP event but don't have an active pointer id.");
            return false;
        } else {
            if (this.mIsDragging) {
                this.mIsDragging = false;
                this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
                float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(yVelocity) < this.mMiniVelocity) {
                    yVelocity = 0.0f;
                }
                finishPull((int) yVelocity);
            }
            this.mActivePointerId = -1;
            releaseVelocityTracker();
            return false;
        }
        return true;
    }

    private void ensureTargetView() {
        if (this.mTargetView == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (!childAt.equals(this.mRefreshView)) {
                    onSureTargetView(childAt);
                    this.mTargetView = childAt;
                    return;
                }
            }
        }
    }

    private void finishPull(int i) {
        int i2 = i;
        info("finishPull: vy = " + i2 + " ; mTargetCurrentOffset = " + this.mTargetCurrentOffset + " ; mTargetRefreshOffset = " + this.mTargetRefreshOffset + " ; mTargetInitOffset = " + this.mTargetInitOffset + " ; mScroller.isFinished() = " + this.mScroller.isFinished());
        int i3 = i2 / 1000;
        onFinishPull(i3, this.mRefreshInitOffset, this.mRefreshEndOffset, this.mRefreshView.getHeight(), this.mTargetCurrentOffset, this.mTargetInitOffset, this.mTargetRefreshOffset);
        int i4 = this.mTargetCurrentOffset;
        int i5 = this.mTargetRefreshOffset;
        if (i4 >= i5) {
            if (i3 > 0) {
                this.mScrollFlag = 6;
                this.mScroller.fling(0, i4, 0, i3, 0, 0, this.mTargetInitOffset, Integer.MAX_VALUE);
                invalidate();
            } else if (i3 < 0) {
                this.mScroller.fling(0, i4, 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (this.mScroller.getFinalY() < this.mTargetInitOffset) {
                    this.mScrollFlag = 8;
                } else if (this.mScroller.getFinalY() < this.mTargetRefreshOffset) {
                    int i6 = this.mTargetInitOffset;
                    int i7 = this.mTargetCurrentOffset;
                    this.mScroller.startScroll(0, i7, 0, i6 - i7);
                } else {
                    int finalY = this.mScroller.getFinalY();
                    int i8 = this.mTargetRefreshOffset;
                    if (finalY == i8) {
                        this.mScrollFlag = 4;
                    } else {
                        Scroller scroller = this.mScroller;
                        int i9 = this.mTargetCurrentOffset;
                        scroller.startScroll(0, i9, 0, i8 - i9);
                        this.mScrollFlag = 4;
                    }
                }
                invalidate();
            } else {
                if (i4 > i5) {
                    this.mScroller.startScroll(0, i4, 0, i5 - i4);
                }
                this.mScrollFlag = 4;
                invalidate();
            }
        } else if (i3 > 0) {
            this.mScroller.fling(0, i4, 0, i3, 0, 0, this.mTargetInitOffset, Integer.MAX_VALUE);
            if (this.mScroller.getFinalY() > this.mTargetRefreshOffset) {
                this.mScrollFlag = 6;
            } else if (this.mAutoScrollToRefreshMinOffset < 0 || this.mScroller.getFinalY() <= this.mAutoScrollToRefreshMinOffset) {
                this.mScrollFlag = 1;
            } else {
                Scroller scroller2 = this.mScroller;
                int i10 = this.mTargetCurrentOffset;
                scroller2.startScroll(0, i10, 0, this.mTargetRefreshOffset - i10);
                this.mScrollFlag = 4;
            }
            invalidate();
        } else if (i3 < 0) {
            this.mScrollFlag = 0;
            this.mScroller.fling(0, i4, 0, i, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
            int finalY2 = this.mScroller.getFinalY();
            int i11 = this.mTargetInitOffset;
            if (finalY2 < i11) {
                this.mScrollFlag = 8;
            } else {
                Scroller scroller3 = this.mScroller;
                int i12 = this.mTargetCurrentOffset;
                scroller3.startScroll(0, i12, 0, i11 - i12);
                this.mScrollFlag = 0;
            }
            invalidate();
        } else if (i4 != this.mTargetInitOffset) {
            int i13 = this.mAutoScrollToRefreshMinOffset;
            if (i13 < 0 || i4 < i13) {
                Scroller scroller4 = this.mScroller;
                int i14 = this.mTargetCurrentOffset;
                scroller4.startScroll(0, i14, 0, this.mTargetInitOffset - i14);
                this.mScrollFlag = 0;
            } else {
                this.mScroller.startScroll(0, i4, 0, i5 - i4);
                this.mScrollFlag = 4;
            }
            invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onRefresh() {
        if (!this.mIsRefreshing) {
            this.mIsRefreshing = true;
            this.mIRefreshView.doRefresh();
            OnPullListener onPullListener = this.mListener;
            if (onPullListener != null) {
                onPullListener.onRefresh();
            }
        }
    }

    public void finishRefresh() {
        this.mIsRefreshing = false;
        this.mIRefreshView.stop();
        this.mScrollFlag = 1;
        this.mScroller.forceFinished(true);
        invalidate();
    }

    public void setEnableOverPull(boolean z) {
        this.mEnableOverPull = z;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }

    public void reset() {
        moveTargetViewTo(this.mTargetInitOffset, false);
        this.mIRefreshView.stop();
        this.mIsRefreshing = false;
        this.mScroller.forceFinished(true);
        this.mScrollFlag = 0;
    }

    /* access modifiers changed from: protected */
    public void startDragging(float f, float f2) {
        float f3 = f - this.mInitialDownX;
        float f4 = f2 - this.mInitialDownY;
        if (isYDrag(f3, f4)) {
            int i = this.mTouchSlop;
            if ((f4 > ((float) i) || (f4 < ((float) (-i)) && this.mTargetCurrentOffset > this.mTargetInitOffset)) && !this.mIsDragging) {
                this.mInitialMotionY = this.mInitialDownY + ((float) this.mTouchSlop);
                this.mLastMotionY = this.mInitialMotionY;
                this.mIsDragging = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isYDrag(float f, float f2) {
        return Math.abs(f2) > Math.abs(f);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (!z) {
            reset();
            invalidate();
        }
    }

    public boolean canChildScrollUp() {
        OnChildScrollUpCallback onChildScrollUpCallback = this.mChildScrollUpCallback;
        if (onChildScrollUpCallback != null) {
            return onChildScrollUpCallback.canChildScrollUp(this, this.mTargetView);
        }
        return defaultCanScrollUp(this.mTargetView);
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        info("onStartNestedScroll: nestedScrollAxes = " + i);
        return isEnabled() && (i & 2) != 0;
    }

    public void onNestedScrollAccepted(View view, View view2, int i) {
        info("onNestedScrollAccepted: axes = " + i);
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i);
        this.mNestedScrollInProgress = true;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        info("onNestedPreScroll: dx = " + i + " ; dy = " + i2);
        int i3 = this.mTargetCurrentOffset;
        int i4 = this.mTargetInitOffset;
        int i5 = i3 - i4;
        if (i2 > 0 && i5 > 0) {
            if (i2 >= i5) {
                iArr[1] = i5;
                moveTargetViewTo(i4, true);
                return;
            }
            iArr[1] = i2;
            moveTargetView((float) (-i2), true);
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        info("onNestedScroll: dxConsumed = " + i + " ; dyConsumed = " + i2 + " ; dxUnconsumed = " + i3 + " ; dyUnconsumed = " + i4);
        if (i4 < 0 && !canChildScrollUp()) {
            moveTargetView((float) (-i4), true);
        }
    }

    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    public void onStopNestedScroll(View view) {
        info("onStopNestedScroll: mNestedScrollInProgress = " + this.mNestedScrollInProgress);
        this.mNestedScrollingParentHelper.onStopNestedScroll(view);
        if (this.mNestedScrollInProgress) {
            this.mNestedScrollInProgress = false;
            finishPull(0);
        }
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        info("onNestedPreFling: mTargetCurrentOffset = " + this.mTargetCurrentOffset + " ; velocityX = " + f + " ; velocityY = " + f2);
        if (this.mTargetCurrentOffset <= this.mTargetInitOffset) {
            return false;
        }
        this.mNestedScrollInProgress = false;
        finishPull((int) (-f2));
        return true;
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        try {
            return super.onNestedFling(view, f, f2, z);
        } catch (Throwable unused) {
            return false;
        }
    }

    private int moveTargetView(float f, boolean z) {
        return moveTargetViewTo((int) (((float) this.mTargetCurrentOffset) + f), z);
    }

    private int moveTargetViewTo(int i, boolean z) {
        return moveTargetViewTo(i, z, false);
    }

    private int moveTargetViewTo(int i, boolean z, boolean z2) {
        int max = Math.max(i, this.mTargetInitOffset);
        if (!this.mEnableOverPull) {
            max = Math.min(max, this.mTargetRefreshOffset);
        }
        int i2 = 0;
        if (max != this.mTargetCurrentOffset || z2) {
            i2 = max - this.mTargetCurrentOffset;
            ViewCompat.offsetTopAndBottom(this.mTargetView, i2);
            this.mTargetCurrentOffset = max;
            int i3 = this.mTargetRefreshOffset;
            int i4 = this.mTargetInitOffset;
            int i5 = i3 - i4;
            if (z) {
                this.mIRefreshView.onPull(Math.min(this.mTargetCurrentOffset - i4, i5), i5, this.mTargetCurrentOffset - this.mTargetRefreshOffset);
            }
            onMoveTargetView(this.mTargetCurrentOffset);
            OnPullListener onPullListener = this.mListener;
            if (onPullListener != null) {
                onPullListener.onMoveTarget(this.mTargetCurrentOffset);
            }
            if (this.mRefreshOffsetCalculator == null) {
                this.mRefreshOffsetCalculator = new QMUIDefaultRefreshOffsetCalculator();
            }
            int calculateRefreshOffset = this.mRefreshOffsetCalculator.calculateRefreshOffset(this.mRefreshInitOffset, this.mRefreshEndOffset, this.mRefreshView.getHeight(), this.mTargetCurrentOffset, this.mTargetInitOffset, this.mTargetRefreshOffset);
            int i6 = this.mRefreshCurrentOffset;
            if (calculateRefreshOffset != i6) {
                ViewCompat.offsetTopAndBottom(this.mRefreshView, calculateRefreshOffset - i6);
                this.mRefreshCurrentOffset = calculateRefreshOffset;
                onMoveRefreshView(this.mRefreshCurrentOffset);
                OnPullListener onPullListener2 = this.mListener;
                if (onPullListener2 != null) {
                    onPullListener2.onMoveRefreshView(this.mRefreshCurrentOffset);
                }
            }
        }
        return i2;
    }

    private void acquireVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    private void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.clear();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public int getRefreshInitOffset() {
        return this.mRefreshInitOffset;
    }

    public int getRefreshEndOffset() {
        return this.mRefreshEndOffset;
    }

    public int getTargetInitOffset() {
        return this.mTargetInitOffset;
    }

    public int getTargetRefreshOffset() {
        return this.mTargetRefreshOffset;
    }

    public void setTargetRefreshOffset(int i) {
        this.mEqualTargetRefreshOffsetToRefreshViewHeight = false;
        this.mTargetRefreshOffset = i;
    }

    public View getTargetView() {
        return this.mTargetView;
    }

    private boolean hasFlag(int i) {
        return (this.mScrollFlag & i) == i;
    }

    private void removeFlag(int i) {
        this.mScrollFlag = (i ^ -1) & this.mScrollFlag;
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int currY = this.mScroller.getCurrY();
            moveTargetViewTo(currY, false);
            if (currY <= 0 && hasFlag(8)) {
                deliverVelocity();
                this.mScroller.forceFinished(true);
            }
            invalidate();
        } else if (hasFlag(1)) {
            removeFlag(1);
            int i = this.mTargetCurrentOffset;
            int i2 = this.mTargetInitOffset;
            if (i != i2) {
                this.mScroller.startScroll(0, i, 0, i2 - i);
            }
            invalidate();
        } else if (hasFlag(2)) {
            removeFlag(2);
            int i3 = this.mTargetCurrentOffset;
            int i4 = this.mTargetRefreshOffset;
            if (i3 != i4) {
                this.mScroller.startScroll(0, i3, 0, i4 - i3);
            } else {
                moveTargetViewTo(i4, false, true);
            }
            invalidate();
        } else if (hasFlag(4)) {
            removeFlag(4);
            onRefresh();
            moveTargetViewTo(this.mTargetRefreshOffset, false, true);
        } else {
            deliverVelocity();
        }
    }

    private void deliverVelocity() {
        if (hasFlag(8)) {
            removeFlag(8);
            if (this.mScroller.getCurrVelocity() > this.mMiniVelocity) {
                info("deliver velocity: " + this.mScroller.getCurrVelocity());
                View view = this.mTargetView;
                if (view instanceof RecyclerView) {
                    ((RecyclerView) view).fling(0, (int) this.mScroller.getCurrVelocity());
                } else if ((view instanceof AbsListView) && Build.VERSION.SDK_INT >= 21) {
                    ((AbsListView) this.mTargetView).fling((int) this.mScroller.getCurrVelocity());
                }
            }
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mNestScrollDurationRefreshing = this.mIsRefreshing;
        } else if (this.mNestScrollDurationRefreshing) {
            if (action != 2) {
                this.mNestScrollDurationRefreshing = false;
            } else if (!this.mIsRefreshing) {
                this.mNestScrollDurationRefreshing = false;
                motionEvent.setAction(0);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public static class RefreshView extends AppCompatImageView implements IRefreshView {
        static final int CIRCLE_DIAMETER = 40;
        static final int CIRCLE_DIAMETER_LARGE = 56;
        private static final int MAX_ALPHA = 255;
        private static final float TRIM_OFFSET = 0.4f;
        private static final float TRIM_RATE = 0.85f;
        private int mCircleDiameter = ((int) (getResources().getDisplayMetrics().density * 40.0f));
        private CircularProgressDrawable mProgress;

        public RefreshView(Context context) {
            super(context);
            this.mProgress = new CircularProgressDrawable(context);
            setColorSchemeColors(QMUIResHelper.getAttrColor(context, C1614R.attr.qmui_config_color_blue));
            this.mProgress.setStyle(0);
            this.mProgress.setAlpha(255);
            this.mProgress.setArrowScale(0.8f);
            setImageDrawable(this.mProgress);
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            int i3 = this.mCircleDiameter;
            setMeasuredDimension(i3, i3);
        }

        public void onPull(int i, int i2, int i3) {
            if (!this.mProgress.isRunning()) {
                Log.i("cgine", "=========");
                float f = (float) i;
                float f2 = (float) i2;
                float f3 = (TRIM_RATE * f) / f2;
                float f4 = (f * TRIM_OFFSET) / f2;
                if (i3 > 0) {
                    f4 += (((float) i3) * TRIM_OFFSET) / f2;
                }
                this.mProgress.setArrowEnabled(true);
                this.mProgress.setStartEndTrim(0.0f, f3);
                this.mProgress.setProgressRotation(f4);
            }
        }

        public void setSize(int i) {
            if (i == 0 || i == 1) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                if (i == 0) {
                    this.mCircleDiameter = (int) (displayMetrics.density * 56.0f);
                } else {
                    this.mCircleDiameter = (int) (displayMetrics.density * 40.0f);
                }
                setImageDrawable((Drawable) null);
                this.mProgress.setStyle(i);
                setImageDrawable(this.mProgress);
            }
        }

        public void stop() {
            this.mProgress.stop();
        }

        public void doRefresh() {
            this.mProgress.start();
        }

        public void setColorSchemeResources(@ColorRes int... iArr) {
            Context context = getContext();
            int[] iArr2 = new int[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                iArr2[i] = ContextCompat.getColor(context, iArr[i]);
            }
            setColorSchemeColors(iArr2);
        }

        public void setColorSchemeColors(@ColorInt int... iArr) {
            this.mProgress.setColorSchemeColors(iArr);
        }
    }
}
