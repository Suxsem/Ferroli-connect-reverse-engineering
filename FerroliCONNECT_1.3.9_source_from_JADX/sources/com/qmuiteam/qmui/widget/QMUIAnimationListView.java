package com.qmuiteam.qmui.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.support.p000v4.util.LongSparseArray;
import android.support.p000v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QMUIAnimationListView extends ListView {
    private static final float DEFAULT_OFFSET_DURATION_UNIT = 0.5f;
    private static final long DURATION_ALPHA = 300;
    private static final long DURATION_OFFSET_MAX = 1000;
    private static final long DURATION_OFFSET_MIN = 0;
    private static final String TAG = "QMUIAnimationListView";
    protected final Set<Long> mAfterVisible;
    private int mAnimationManipulateDurationLimit;
    protected final Set<Long> mBeforeVisible;
    private ValueAnimator mChangeDisappearAnimator;
    /* access modifiers changed from: private */
    public long mChangeDisappearPlayTime;
    protected final LongSparseArray<View> mDetachViewsMap;
    /* access modifiers changed from: private */
    public boolean mIsAnimating;
    private long mLastManipulateTime;
    private float mOffsetDurationUnit;
    private Interpolator mOffsetInterpolator;
    private boolean mOpenChangeDisappearAnimation;
    private final List<Manipulator> mPendingManipulations;
    private final List<Manipulator> mPendingManipulationsWithoutAnimation;
    protected final LongSparseArray<Integer> mPositionMap;
    private ListAdapter mRealAdapter;
    protected final LongSparseArray<Integer> mTopMap;
    /* access modifiers changed from: private */
    public WrapperAdapter mWrapperAdapter;

    public interface Manipulator<T extends ListAdapter> {
        void manipulate(T t);
    }

    public QMUIAnimationListView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIAnimationListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTopMap = new LongSparseArray<>();
        this.mPositionMap = new LongSparseArray<>();
        this.mDetachViewsMap = new LongSparseArray<>();
        this.mBeforeVisible = new HashSet();
        this.mAfterVisible = new HashSet();
        this.mPendingManipulations = new ArrayList();
        this.mPendingManipulationsWithoutAnimation = new ArrayList();
        this.mChangeDisappearPlayTime = 0;
        this.mIsAnimating = false;
        this.mAnimationManipulateDurationLimit = 0;
        this.mLastManipulateTime = 0;
        this.mOffsetDurationUnit = DEFAULT_OFFSET_DURATION_UNIT;
        this.mOffsetInterpolator = new LinearInterpolator();
        this.mOpenChangeDisappearAnimation = false;
        init();
    }

    public QMUIAnimationListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTopMap = new LongSparseArray<>();
        this.mPositionMap = new LongSparseArray<>();
        this.mDetachViewsMap = new LongSparseArray<>();
        this.mBeforeVisible = new HashSet();
        this.mAfterVisible = new HashSet();
        this.mPendingManipulations = new ArrayList();
        this.mPendingManipulationsWithoutAnimation = new ArrayList();
        this.mChangeDisappearPlayTime = 0;
        this.mIsAnimating = false;
        this.mAnimationManipulateDurationLimit = 0;
        this.mLastManipulateTime = 0;
        this.mOffsetDurationUnit = DEFAULT_OFFSET_DURATION_UNIT;
        this.mOffsetInterpolator = new LinearInterpolator();
        this.mOpenChangeDisappearAnimation = false;
        init();
    }

    @TargetApi(21)
    public QMUIAnimationListView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTopMap = new LongSparseArray<>();
        this.mPositionMap = new LongSparseArray<>();
        this.mDetachViewsMap = new LongSparseArray<>();
        this.mBeforeVisible = new HashSet();
        this.mAfterVisible = new HashSet();
        this.mPendingManipulations = new ArrayList();
        this.mPendingManipulationsWithoutAnimation = new ArrayList();
        this.mChangeDisappearPlayTime = 0;
        this.mIsAnimating = false;
        this.mAnimationManipulateDurationLimit = 0;
        this.mLastManipulateTime = 0;
        this.mOffsetDurationUnit = DEFAULT_OFFSET_DURATION_UNIT;
        this.mOffsetInterpolator = new LinearInterpolator();
        this.mOpenChangeDisappearAnimation = false;
        init();
    }

    private void init() {
        setWillNotDraw(false);
    }

    public ListAdapter getRealAdapter() {
        return this.mRealAdapter;
    }

    public void setAdapter(ListAdapter listAdapter) {
        this.mRealAdapter = listAdapter;
        this.mWrapperAdapter = new WrapperAdapter(this.mRealAdapter);
        super.setAdapter(this.mWrapperAdapter);
    }

    public void setAnimationManipulateDurationLimit(int i) {
        this.mAnimationManipulateDurationLimit = i;
    }

    public <T extends ListAdapter> void manipulate(Manipulator<T> manipulator) {
        Log.i(TAG, "manipulate");
        if (!this.mWrapperAdapter.isAnimationEnabled()) {
            manipulateWithoutAnimation(manipulator);
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        boolean z = uptimeMillis - this.mLastManipulateTime > ((long) this.mAnimationManipulateDurationLimit);
        this.mLastManipulateTime = uptimeMillis;
        if (!this.mIsAnimating) {
            if (z) {
                this.mIsAnimating = true;
                prepareAnimation();
                manipulator.manipulate(this.mRealAdapter);
                doAnimation();
                return;
            }
            manipulator.manipulate(this.mRealAdapter);
            this.mWrapperAdapter.notifyDataSetChanged();
        } else if (z) {
            this.mPendingManipulations.add(manipulator);
        } else {
            this.mPendingManipulationsWithoutAnimation.add(manipulator);
        }
    }

    public <T extends ListAdapter> void manipulateWithoutAnimation(Manipulator<T> manipulator) {
        Log.i(TAG, "manipulateWithoutAnimation");
        if (!this.mIsAnimating) {
            manipulator.manipulate(this.mRealAdapter);
            this.mWrapperAdapter.notifyDataSetChanged();
            return;
        }
        this.mPendingManipulationsWithoutAnimation.add(manipulator);
    }

    public float getOffsetDurationUnit() {
        return this.mOffsetDurationUnit;
    }

    public void setOffsetDurationUnit(float f) {
        this.mOffsetDurationUnit = f;
    }

    private long getOffsetDuration(int i, int i2) {
        return Math.max(0, Math.min((long) (((float) Math.abs(i - i2)) * this.mOffsetDurationUnit), DURATION_OFFSET_MAX));
    }

    public void setOpenChangeDisappearAnimation(boolean z) {
        this.mOpenChangeDisappearAnimation = z;
    }

    public void setOffsetInterpolator(Interpolator interpolator) {
        this.mOffsetInterpolator = interpolator;
    }

    private void prepareAnimation() {
        this.mTopMap.clear();
        this.mPositionMap.clear();
        this.mBeforeVisible.clear();
        this.mAfterVisible.clear();
        this.mDetachViewsMap.clear();
        this.mWrapperAdapter.setShouldNotifyChange(false);
        int childCount = getChildCount();
        int firstVisiblePosition = getFirstVisiblePosition();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            long itemId = this.mWrapperAdapter.getItemId(firstVisiblePosition + i);
            this.mTopMap.put(itemId, Integer.valueOf(childAt.getTop()));
            this.mPositionMap.put(itemId, Integer.valueOf(i));
        }
        for (int i2 = 0; i2 < firstVisiblePosition; i2++) {
            this.mBeforeVisible.add(Long.valueOf(this.mWrapperAdapter.getItemId(i2)));
        }
        int count = this.mWrapperAdapter.getCount();
        for (int i3 = firstVisiblePosition + childCount; i3 < count; i3++) {
            this.mAfterVisible.add(Long.valueOf(this.mWrapperAdapter.getItemId(i3)));
        }
    }

    private void doAnimation() {
        setEnabled(false);
        setClickable(false);
        doPreLayoutAnimation(new ManipulateAnimatorListener() {
            public void onAnimationEnd(Animator animator) {
                QMUIAnimationListView.this.mWrapperAdapter.notifyDataSetChanged();
                QMUIAnimationListView.this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        QMUIAnimationListView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                        QMUIAnimationListView.this.doPostLayoutAnimation();
                        return true;
                    }
                });
            }
        });
    }

    private void doPreLayoutAnimation(Animator.AnimatorListener animatorListener) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mTopMap.size(); i++) {
            long keyAt = this.mTopMap.keyAt(i);
            if (getPositionForId(keyAt) < 0) {
                Animator deleteAnimator = getDeleteAnimator(getChildAt(this.mPositionMap.get(keyAt).intValue()));
                this.mPositionMap.remove(keyAt);
                animatorSet.play(deleteAnimator);
                arrayList.add(Long.valueOf(keyAt));
            }
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            this.mTopMap.remove(((Long) arrayList.get(i2)).longValue());
        }
        if (this.mOpenChangeDisappearAnimation) {
            for (int i3 = 0; i3 < this.mPositionMap.size(); i3++) {
                View childAt = getChildAt(this.mPositionMap.valueAt(i3).intValue());
                ViewCompat.setHasTransientState(childAt, true);
                this.mDetachViewsMap.put(this.mPositionMap.keyAt(i3), childAt);
            }
        }
        if (!animatorSet.getChildAnimations().isEmpty()) {
            animatorSet.addListener(animatorListener);
            animatorSet.start();
            return;
        }
        animatorListener.onAnimationEnd(animatorSet);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doPostLayoutAnimation() {
        /*
            r14 = this;
            android.animation.AnimatorSet r0 = new android.animation.AnimatorSet
            r0.<init>()
            int r1 = r14.getChildCount()
            int r2 = r14.getFirstVisiblePosition()
            boolean r3 = r14.mOpenChangeDisappearAnimation
            r4 = 0
            if (r3 == 0) goto L_0x0029
            r3 = 0
        L_0x0013:
            android.support.v4.util.LongSparseArray<android.view.View> r5 = r14.mDetachViewsMap
            int r5 = r5.size()
            if (r3 >= r5) goto L_0x0029
            android.support.v4.util.LongSparseArray<android.view.View> r5 = r14.mDetachViewsMap
            java.lang.Object r5 = r5.valueAt(r3)
            android.view.View r5 = (android.view.View) r5
            android.support.p000v4.view.ViewCompat.setHasTransientState(r5, r4)
            int r3 = r3 + 1
            goto L_0x0013
        L_0x0029:
            r3 = -1
            r5 = 0
            r6 = r5
            r5 = -1
            r7 = -1
        L_0x002e:
            if (r4 >= r1) goto L_0x00b4
            android.view.View r9 = r14.getChildAt(r4)
            r8 = 1065353216(0x3f800000, float:1.0)
            r9.setAlpha(r8)
            int r10 = r9.getTop()
            int r11 = r2 + r4
            com.qmuiteam.qmui.widget.QMUIAnimationListView$WrapperAdapter r8 = r14.mWrapperAdapter
            long r12 = r8.getItemId(r11)
            android.support.v4.util.LongSparseArray<java.lang.Integer> r8 = r14.mTopMap
            int r8 = r8.indexOfKey(r12)
            if (r8 <= r3) goto L_0x0073
            android.support.v4.util.LongSparseArray<java.lang.Integer> r5 = r14.mTopMap
            java.lang.Object r5 = r5.get(r12)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            android.support.v4.util.LongSparseArray<java.lang.Integer> r8 = r14.mTopMap
            r8.remove(r12)
            android.support.v4.util.LongSparseArray<java.lang.Integer> r8 = r14.mPositionMap
            r8.remove(r12)
            boolean r8 = r14.mOpenChangeDisappearAnimation
            if (r8 == 0) goto L_0x006c
            android.support.v4.util.LongSparseArray<android.view.View> r8 = r14.mDetachViewsMap
            r8.remove(r12)
        L_0x006c:
            if (r5 == r10) goto L_0x0089
            android.animation.Animator r6 = r14.getOffsetAnimator(r9, r5, r10)
            goto L_0x0089
        L_0x0073:
            java.util.Set<java.lang.Long> r6 = r14.mBeforeVisible
            java.lang.Long r8 = java.lang.Long.valueOf(r12)
            boolean r6 = r6.contains(r8)
            if (r6 == 0) goto L_0x008b
            int r5 = r9.getHeight()
            int r5 = -r5
            android.animation.Animator r5 = r14.getOffsetAnimator(r9, r5, r10)
        L_0x0088:
            r6 = r5
        L_0x0089:
            r5 = -1
            goto L_0x00ab
        L_0x008b:
            java.util.Set<java.lang.Long> r6 = r14.mAfterVisible
            java.lang.Long r8 = java.lang.Long.valueOf(r12)
            boolean r6 = r6.contains(r8)
            if (r6 == 0) goto L_0x00a0
            int r5 = r14.getHeight()
            android.animation.Animator r5 = r14.getOffsetAnimator(r9, r5, r10)
            goto L_0x0088
        L_0x00a0:
            if (r5 != r3) goto L_0x00a4
            r5 = r10
            r7 = r11
        L_0x00a4:
            r8 = r14
            r12 = r5
            r13 = r7
            android.animation.Animator r6 = r8.getAddAnimator(r9, r10, r11, r12, r13)
        L_0x00ab:
            if (r6 == 0) goto L_0x00b0
            r0.play(r6)
        L_0x00b0:
            int r4 = r4 + 1
            goto L_0x002e
        L_0x00b4:
            boolean r1 = r14.mOpenChangeDisappearAnimation
            if (r1 == 0) goto L_0x00ee
            android.support.v4.util.LongSparseArray<android.view.View> r1 = r14.mDetachViewsMap
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x00ee
            r1 = 2
            float[] r1 = new float[r1]
            r1 = {0, 1065353216} // fill-array
            android.animation.ValueAnimator r1 = android.animation.ValueAnimator.ofFloat(r1)
            r14.mChangeDisappearAnimator = r1
            android.animation.ValueAnimator r1 = r14.mChangeDisappearAnimator
            com.qmuiteam.qmui.widget.QMUIAnimationListView$2 r2 = new com.qmuiteam.qmui.widget.QMUIAnimationListView$2
            r2.<init>()
            r1.addUpdateListener(r2)
            android.animation.ValueAnimator r1 = r14.mChangeDisappearAnimator
            com.qmuiteam.qmui.widget.QMUIAnimationListView$3 r2 = new com.qmuiteam.qmui.widget.QMUIAnimationListView$3
            r2.<init>()
            r1.addListener(r2)
            android.animation.ValueAnimator r1 = r14.mChangeDisappearAnimator
            long r2 = r14.getChangeDisappearDuration()
            r1.setDuration(r2)
            android.animation.ValueAnimator r1 = r14.mChangeDisappearAnimator
            r1.start()
        L_0x00ee:
            com.qmuiteam.qmui.widget.QMUIAnimationListView$4 r1 = new com.qmuiteam.qmui.widget.QMUIAnimationListView$4
            r1.<init>()
            r0.addListener(r1)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.widget.QMUIAnimationListView.doPostLayoutAnimation():void");
    }

    /* access modifiers changed from: protected */
    public long getChangeDisappearDuration() {
        return (long) (((float) getHeight()) * this.mOffsetDurationUnit);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        ValueAnimator valueAnimator;
        int i;
        super.onDraw(canvas);
        if (this.mOpenChangeDisappearAnimation && (valueAnimator = this.mChangeDisappearAnimator) != null && valueAnimator.isStarted() && this.mDetachViewsMap.size() > 0 && this.mIsAnimating) {
            for (int i2 = 0; i2 < this.mDetachViewsMap.size(); i2++) {
                long keyAt = this.mDetachViewsMap.keyAt(i2);
                View valueAt = this.mDetachViewsMap.valueAt(i2);
                int positionForId = getPositionForId(keyAt);
                int i3 = (int) (((float) this.mChangeDisappearPlayTime) / this.mOffsetDurationUnit);
                if (positionForId < getFirstVisiblePosition()) {
                    i = this.mTopMap.get(keyAt).intValue() - i3;
                    if (i < (-valueAt.getHeight())) {
                    }
                } else {
                    i = this.mTopMap.get(keyAt).intValue() + i3;
                    if (i > getHeight()) {
                    }
                }
                valueAt.layout(0, i, valueAt.getWidth(), valueAt.getHeight() + i);
                valueAt.setAlpha(1.0f - ((((float) this.mChangeDisappearPlayTime) * 1.0f) / ((float) getChangeDisappearDuration())));
                drawChild(canvas, valueAt, getDrawingTime());
            }
        }
    }

    /* access modifiers changed from: private */
    public void finishAnimation() {
        this.mWrapperAdapter.setShouldNotifyChange(true);
        this.mChangeDisappearAnimator = null;
        if (this.mOpenChangeDisappearAnimation) {
            for (int i = 0; i < this.mDetachViewsMap.size(); i++) {
                this.mDetachViewsMap.valueAt(i).setAlpha(1.0f);
            }
            this.mDetachViewsMap.clear();
        }
        this.mIsAnimating = false;
        setEnabled(true);
        setClickable(true);
        manipulatePending();
    }

    /* access modifiers changed from: private */
    public void manipulatePending() {
        if (!this.mPendingManipulationsWithoutAnimation.isEmpty()) {
            this.mIsAnimating = true;
            for (Manipulator manipulate : this.mPendingManipulationsWithoutAnimation) {
                manipulate.manipulate(this.mRealAdapter);
            }
            this.mPendingManipulationsWithoutAnimation.clear();
            this.mWrapperAdapter.notifyDataSetChanged();
            post(new Runnable() {
                public void run() {
                    boolean unused = QMUIAnimationListView.this.mIsAnimating = false;
                    QMUIAnimationListView.this.manipulatePending();
                }
            });
        } else if (!this.mPendingManipulations.isEmpty()) {
            this.mIsAnimating = true;
            prepareAnimation();
            for (Manipulator manipulate2 : this.mPendingManipulations) {
                manipulate2.manipulate(this.mRealAdapter);
            }
            this.mPendingManipulations.clear();
            doAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public Animator getDeleteAnimator(View view) {
        return alphaObjectAnimator(view, false, DURATION_ALPHA, true);
    }

    /* access modifiers changed from: protected */
    public Animator getOffsetAnimator(View view, int i, int i2) {
        return getOffsetAnimator(view, i, i2, getOffsetDuration(i, i2));
    }

    /* access modifiers changed from: protected */
    public Animator getOffsetAnimator(View view, int i, int i2, long j) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "translationY", new float[]{(float) (i - i2), 0.0f});
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(this.mOffsetInterpolator);
        return ofFloat;
    }

    /* access modifiers changed from: protected */
    public Animator getAddAnimator(View view, int i, int i2, int i3, int i4) {
        view.setAlpha(0.0f);
        view.clearAnimation();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaObjectAnimator(view, true, 50, false));
        if (i3 != i) {
            animatorSet.play(getOffsetAnimator(view, i3, i));
        }
        animatorSet.setStartDelay((long) (((float) view.getHeight()) * this.mOffsetDurationUnit));
        return animatorSet;
    }

    /* access modifiers changed from: protected */
    public ObjectAnimator alphaObjectAnimator(View view, final boolean z, long j, boolean z2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", z ? new float[]{0.0f, 1.0f} : new float[]{1.0f, 0.0f});
        ofFloat.setDuration(j);
        if (z2) {
            final WeakReference weakReference = new WeakReference(view);
            ofFloat.addListener(new ManipulateAnimatorListener() {
                public void onAnimationEnd(Animator animator) {
                    if (weakReference.get() != null) {
                        ((View) weakReference.get()).setAlpha(z ? 0.0f : 1.0f);
                    }
                }
            });
        }
        return ofFloat;
    }

    /* access modifiers changed from: protected */
    public int getPositionForId(long j) {
        for (int i = 0; i < this.mWrapperAdapter.getCount(); i++) {
            if (this.mWrapperAdapter.getItemId(i) == j) {
                return i;
            }
        }
        return -1;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return isEnabled() && super.dispatchTouchEvent(motionEvent);
    }

    private static class WrapperAdapter extends BaseAdapter {
        private ListAdapter mAdapter;
        private boolean mIsAnimationEnabled = false;
        private final DataSetObserver mObserver = new DataSetObserver() {
            public void onChanged() {
                if (WrapperAdapter.this.mShouldNotifyChange) {
                    WrapperAdapter.this.notifyDataSetChanged();
                }
            }

            public void onInvalidated() {
                WrapperAdapter.this.notifyDataSetInvalidated();
            }
        };
        /* access modifiers changed from: private */
        public boolean mShouldNotifyChange = true;

        public WrapperAdapter(ListAdapter listAdapter) {
            this.mAdapter = listAdapter;
            this.mAdapter.registerDataSetObserver(this.mObserver);
        }

        public void setShouldNotifyChange(boolean z) {
            this.mShouldNotifyChange = z;
        }

        public boolean isAnimationEnabled() {
            return this.mIsAnimationEnabled;
        }

        public int getCount() {
            return this.mAdapter.getCount();
        }

        public int getItemViewType(int i) {
            return this.mAdapter.getItemViewType(i);
        }

        public int getViewTypeCount() {
            return this.mAdapter.getViewTypeCount();
        }

        public Object getItem(int i) {
            return this.mAdapter.getItem(i);
        }

        public long getItemId(int i) {
            return this.mAdapter.getItemId(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            return this.mAdapter.getView(i, view, viewGroup);
        }

        public boolean hasStableIds() {
            boolean hasStableIds = this.mAdapter.hasStableIds();
            this.mIsAnimationEnabled = hasStableIds;
            return hasStableIds;
        }
    }

    private abstract class ManipulateAnimatorListener implements Animator.AnimatorListener {
        public void onAnimationCancel(Animator animator) {
        }

        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
        }

        private ManipulateAnimatorListener() {
        }
    }
}
