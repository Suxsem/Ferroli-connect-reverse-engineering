package com.chad.library.adapter.base.listener;

import android.os.Build;
import android.support.p000v4.view.GestureDetectorCompat;
import android.support.p003v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.HashSet;
import java.util.Set;

public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {
    public static String TAG = "SimpleClickListener";
    protected BaseQuickAdapter baseQuickAdapter;
    private GestureDetectorCompat mGestureDetector;
    /* access modifiers changed from: private */
    public boolean mIsPrepressed = false;
    /* access modifiers changed from: private */
    public boolean mIsShowPress = false;
    /* access modifiers changed from: private */
    public View mPressedView = null;
    private RecyclerView recyclerView;

    private boolean isHeaderOrFooterView(int i) {
        return i == 1365 || i == 273 || i == 819 || i == 546;
    }

    public abstract void onItemChildClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public abstract void onItemLongClick(BaseQuickAdapter baseQuickAdapter2, View view, int i);

    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
        BaseViewHolder baseViewHolder;
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            this.recyclerView = recyclerView2;
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        } else if (recyclerView3 != recyclerView2) {
            this.recyclerView = recyclerView2;
            this.baseQuickAdapter = (BaseQuickAdapter) this.recyclerView.getAdapter();
            this.mGestureDetector = new GestureDetectorCompat(this.recyclerView.getContext(), new ItemTouchHelperGestureListener(this.recyclerView));
        }
        if (!this.mGestureDetector.onTouchEvent(motionEvent) && motionEvent.getActionMasked() == 1 && this.mIsShowPress) {
            View view = this.mPressedView;
            if (view != null && ((baseViewHolder = (BaseViewHolder) this.recyclerView.getChildViewHolder(view)) == null || !isHeaderOrFooterView(baseViewHolder.getItemViewType()))) {
                this.mPressedView.setPressed(false);
            }
            this.mIsShowPress = false;
            this.mIsPrepressed = false;
        }
        return false;
    }

    public void onTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
    }

    private class ItemTouchHelperGestureListener implements GestureDetector.OnGestureListener {
        private RecyclerView recyclerView;

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        public boolean onDown(MotionEvent motionEvent) {
            boolean unused = SimpleClickListener.this.mIsPrepressed = true;
            View unused2 = SimpleClickListener.this.mPressedView = this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            return false;
        }

        public void onShowPress(MotionEvent motionEvent) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                boolean unused = SimpleClickListener.this.mIsShowPress = true;
            }
        }

        ItemTouchHelperGestureListener(RecyclerView recyclerView2) {
            this.recyclerView = recyclerView2;
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (SimpleClickListener.this.mIsPrepressed && SimpleClickListener.this.mPressedView != null) {
                if (this.recyclerView.getScrollState() != 0) {
                    return false;
                }
                View access$100 = SimpleClickListener.this.mPressedView;
                BaseViewHolder baseViewHolder = (BaseViewHolder) this.recyclerView.getChildViewHolder(access$100);
                if (SimpleClickListener.this.isHeaderOrFooterPosition(baseViewHolder.getLayoutPosition())) {
                    return false;
                }
                HashSet<Integer> childClickViewIds = baseViewHolder.getChildClickViewIds();
                Set<Integer> nestViews = baseViewHolder.getNestViews();
                if (childClickViewIds == null || childClickViewIds.size() <= 0) {
                    SimpleClickListener.this.setPressViewHotSpot(motionEvent, access$100);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    if (childClickViewIds != null && childClickViewIds.size() > 0) {
                        for (Integer intValue : childClickViewIds) {
                            View findViewById = access$100.findViewById(intValue.intValue());
                            if (findViewById != null) {
                                findViewById.setPressed(false);
                            }
                        }
                    }
                    SimpleClickListener simpleClickListener = SimpleClickListener.this;
                    simpleClickListener.onItemClick(simpleClickListener.baseQuickAdapter, access$100, baseViewHolder.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                } else {
                    for (Integer next : childClickViewIds) {
                        View findViewById2 = access$100.findViewById(next.intValue());
                        if (findViewById2 != null) {
                            if (!SimpleClickListener.this.inRangeOfView(findViewById2, motionEvent) || !findViewById2.isEnabled()) {
                                findViewById2.setPressed(false);
                            } else if (nestViews != null && nestViews.contains(next)) {
                                return false;
                            } else {
                                SimpleClickListener.this.setPressViewHotSpot(motionEvent, findViewById2);
                                findViewById2.setPressed(true);
                                SimpleClickListener simpleClickListener2 = SimpleClickListener.this;
                                simpleClickListener2.onItemChildClick(simpleClickListener2.baseQuickAdapter, findViewById2, baseViewHolder.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                                resetPressedView(findViewById2);
                                return true;
                            }
                        }
                    }
                    SimpleClickListener.this.setPressViewHotSpot(motionEvent, access$100);
                    SimpleClickListener.this.mPressedView.setPressed(true);
                    for (Integer intValue2 : childClickViewIds) {
                        View findViewById3 = access$100.findViewById(intValue2.intValue());
                        if (findViewById3 != null) {
                            findViewById3.setPressed(false);
                        }
                    }
                    SimpleClickListener simpleClickListener3 = SimpleClickListener.this;
                    simpleClickListener3.onItemClick(simpleClickListener3.baseQuickAdapter, access$100, baseViewHolder.getLayoutPosition() - SimpleClickListener.this.baseQuickAdapter.getHeaderLayoutCount());
                }
                resetPressedView(access$100);
            }
            return true;
        }

        private void resetPressedView(final View view) {
            if (view != null) {
                view.postDelayed(new Runnable() {
                    public void run() {
                        View view = view;
                        if (view != null) {
                            view.setPressed(false);
                        }
                    }
                }, 50);
            }
            boolean unused = SimpleClickListener.this.mIsPrepressed = false;
            View unused2 = SimpleClickListener.this.mPressedView = null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x00a9  */
        /* JADX WARNING: Removed duplicated region for block: B:49:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onLongPress(android.view.MotionEvent r10) {
            /*
                r9 = this;
                android.support.v7.widget.RecyclerView r0 = r9.recyclerView
                int r0 = r0.getScrollState()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean r0 = r0.mIsPrepressed
                if (r0 == 0) goto L_0x0100
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r0 = r0.mPressedView
                if (r0 == 0) goto L_0x0100
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r0 = r0.mPressedView
                r1 = 0
                r0.performHapticFeedback(r1)
                android.support.v7.widget.RecyclerView r0 = r9.recyclerView
                com.chad.library.adapter.base.listener.SimpleClickListener r2 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r2 = r2.mPressedView
                android.support.v7.widget.RecyclerView$ViewHolder r0 = r0.getChildViewHolder(r2)
                com.chad.library.adapter.base.BaseViewHolder r0 = (com.chad.library.adapter.base.BaseViewHolder) r0
                com.chad.library.adapter.base.listener.SimpleClickListener r2 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                int r3 = r0.getLayoutPosition()
                boolean r2 = r2.isHeaderOrFooterPosition(r3)
                if (r2 != 0) goto L_0x0100
                java.util.HashSet r2 = r0.getItemChildLongClickViewIds()
                java.util.Set r3 = r0.getNestViews()
                r4 = 1
                if (r2 == 0) goto L_0x00a6
                int r5 = r2.size()
                if (r5 <= 0) goto L_0x00a6
                java.util.Iterator r5 = r2.iterator()
            L_0x0052:
                boolean r6 = r5.hasNext()
                if (r6 == 0) goto L_0x00a6
                java.lang.Object r6 = r5.next()
                java.lang.Integer r6 = (java.lang.Integer) r6
                com.chad.library.adapter.base.listener.SimpleClickListener r7 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r7 = r7.mPressedView
                int r8 = r6.intValue()
                android.view.View r7 = r7.findViewById(r8)
                com.chad.library.adapter.base.listener.SimpleClickListener r8 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean r8 = r8.inRangeOfView(r7, r10)
                if (r8 == 0) goto L_0x0052
                boolean r8 = r7.isEnabled()
                if (r8 == 0) goto L_0x0052
                if (r3 == 0) goto L_0x0083
                boolean r3 = r3.contains(r6)
                if (r3 == 0) goto L_0x0083
                goto L_0x00a4
            L_0x0083:
                com.chad.library.adapter.base.listener.SimpleClickListener r3 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                r3.setPressViewHotSpot(r10, r7)
                com.chad.library.adapter.base.listener.SimpleClickListener r3 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r5 = r3.baseQuickAdapter
                int r6 = r0.getLayoutPosition()
                com.chad.library.adapter.base.listener.SimpleClickListener r8 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r8 = r8.baseQuickAdapter
                int r8 = r8.getHeaderLayoutCount()
                int r6 = r6 - r8
                r3.onItemChildLongClick(r5, r7, r6)
                r7.setPressed(r4)
                com.chad.library.adapter.base.listener.SimpleClickListener r3 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean unused = r3.mIsShowPress = r4
            L_0x00a4:
                r3 = 1
                goto L_0x00a7
            L_0x00a6:
                r3 = 0
            L_0x00a7:
                if (r3 != 0) goto L_0x0100
                com.chad.library.adapter.base.listener.SimpleClickListener r3 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r5 = r3.baseQuickAdapter
                com.chad.library.adapter.base.listener.SimpleClickListener r6 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r6 = r6.mPressedView
                int r0 = r0.getLayoutPosition()
                com.chad.library.adapter.base.listener.SimpleClickListener r7 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                com.chad.library.adapter.base.BaseQuickAdapter r7 = r7.baseQuickAdapter
                int r7 = r7.getHeaderLayoutCount()
                int r0 = r0 - r7
                r3.onItemLongClick(r5, r6, r0)
                com.chad.library.adapter.base.listener.SimpleClickListener r0 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r3 = r0.mPressedView
                r0.setPressViewHotSpot(r10, r3)
                com.chad.library.adapter.base.listener.SimpleClickListener r10 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r10 = r10.mPressedView
                r10.setPressed(r4)
                if (r2 == 0) goto L_0x00fb
                java.util.Iterator r10 = r2.iterator()
            L_0x00db:
                boolean r0 = r10.hasNext()
                if (r0 == 0) goto L_0x00fb
                java.lang.Object r0 = r10.next()
                java.lang.Integer r0 = (java.lang.Integer) r0
                com.chad.library.adapter.base.listener.SimpleClickListener r2 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                android.view.View r2 = r2.mPressedView
                int r0 = r0.intValue()
                android.view.View r0 = r2.findViewById(r0)
                if (r0 == 0) goto L_0x00db
                r0.setPressed(r1)
                goto L_0x00db
            L_0x00fb:
                com.chad.library.adapter.base.listener.SimpleClickListener r10 = com.chad.library.adapter.base.listener.SimpleClickListener.this
                boolean unused = r10.mIsShowPress = r4
            L_0x0100:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.chad.library.adapter.base.listener.SimpleClickListener.ItemTouchHelperGestureListener.onLongPress(android.view.MotionEvent):void");
        }
    }

    /* access modifiers changed from: private */
    public void setPressViewHotSpot(MotionEvent motionEvent, View view) {
        if (Build.VERSION.SDK_INT >= 21 && view != null && view.getBackground() != null) {
            view.getBackground().setHotspot(motionEvent.getRawX(), motionEvent.getY() - view.getY());
        }
    }

    public boolean inRangeOfView(View view, MotionEvent motionEvent) {
        int[] iArr = new int[2];
        if (view != null && view.isShown()) {
            view.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            if (motionEvent.getRawX() < ((float) i) || motionEvent.getRawX() > ((float) (i + view.getWidth())) || motionEvent.getRawY() < ((float) i2) || motionEvent.getRawY() > ((float) (i2 + view.getHeight()))) {
                return false;
            }
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isHeaderOrFooterPosition(int i) {
        if (this.baseQuickAdapter == null) {
            RecyclerView recyclerView2 = this.recyclerView;
            if (recyclerView2 == null) {
                return false;
            }
            this.baseQuickAdapter = (BaseQuickAdapter) recyclerView2.getAdapter();
        }
        int itemViewType = this.baseQuickAdapter.getItemViewType(i);
        if (itemViewType == 1365 || itemViewType == 273 || itemViewType == 819 || itemViewType == 546) {
            return true;
        }
        return false;
    }
}
