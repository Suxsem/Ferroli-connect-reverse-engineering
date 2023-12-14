package com.qmuiteam.qmui.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcelable;
import android.support.p000v4.view.PagerAdapter;
import android.support.p000v4.view.ViewPager;
import android.support.p000v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.qmuiteam.qmui.util.QMUIWindowInsetHelper;

public class QMUIViewPager extends ViewPager implements IWindowInsetLayout {
    private static final int DEFAULT_INFINITE_RATIO = 100;
    /* access modifiers changed from: private */
    public boolean mEnableLoop;
    /* access modifiers changed from: private */
    public int mInfiniteRatio;
    private boolean mIsInMeasure;
    private boolean mIsSwipeable;
    private QMUIWindowInsetHelper mQMUIWindowInsetHelper;

    public QMUIViewPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSwipeable = true;
        this.mIsInMeasure = false;
        this.mEnableLoop = false;
        this.mInfiniteRatio = 100;
        this.mQMUIWindowInsetHelper = new QMUIWindowInsetHelper(this, this);
    }

    public void setSwipeable(boolean z) {
        this.mIsSwipeable = z;
    }

    public int getInfiniteRatio() {
        return this.mInfiniteRatio;
    }

    public void setInfiniteRatio(int i) {
        this.mInfiniteRatio = i;
    }

    public boolean isEnableLoop() {
        return this.mEnableLoop;
    }

    public void setEnableLoop(boolean z) {
        if (this.mEnableLoop != z) {
            this.mEnableLoop = z;
            if (getAdapter() != null) {
                getAdapter().notifyDataSetChanged();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mIsSwipeable && super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsSwipeable && super.onInterceptTouchEvent(motionEvent);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.mIsInMeasure = true;
        super.onMeasure(i, i2);
        this.mIsInMeasure = false;
    }

    public boolean isInMeasure() {
        return this.mIsInMeasure;
    }

    /* access modifiers changed from: protected */
    public boolean fitSystemWindows(Rect rect) {
        if (Build.VERSION.SDK_INT < 19 || Build.VERSION.SDK_INT >= 21) {
            return super.fitSystemWindows(rect);
        }
        return applySystemWindowInsets19(rect);
    }

    public boolean applySystemWindowInsets19(Rect rect) {
        return this.mQMUIWindowInsetHelper.defaultApplySystemWindowInsets19(this, rect);
    }

    public boolean applySystemWindowInsets21(WindowInsetsCompat windowInsetsCompat) {
        return this.mQMUIWindowInsetHelper.defaultApplySystemWindowInsets21(this, windowInsetsCompat);
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter instanceof QMUIPagerAdapter) {
            super.setAdapter(new WrapperPagerAdapter((QMUIPagerAdapter) pagerAdapter));
        } else {
            super.setAdapter(pagerAdapter);
        }
    }

    class WrapperPagerAdapter extends PagerAdapter {
        private QMUIPagerAdapter mAdapter;

        public WrapperPagerAdapter(QMUIPagerAdapter qMUIPagerAdapter) {
            this.mAdapter = qMUIPagerAdapter;
        }

        public int getCount() {
            if (!QMUIViewPager.this.mEnableLoop) {
                return this.mAdapter.getCount();
            }
            if (this.mAdapter.getCount() == 0) {
                return 0;
            }
            return this.mAdapter.getCount() * QMUIViewPager.this.mInfiniteRatio;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            if (QMUIViewPager.this.mEnableLoop && this.mAdapter.getCount() != 0) {
                i %= this.mAdapter.getCount();
            }
            return this.mAdapter.instantiateItem(viewGroup, i);
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (QMUIViewPager.this.mEnableLoop && this.mAdapter.getCount() != 0) {
                i %= this.mAdapter.getCount();
            }
            this.mAdapter.destroyItem(viewGroup, i, obj);
        }

        public boolean isViewFromObject(View view, Object obj) {
            return this.mAdapter.isViewFromObject(view, obj);
        }

        public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
            this.mAdapter.restoreState(parcelable, classLoader);
        }

        public Parcelable saveState() {
            return this.mAdapter.saveState();
        }

        public void startUpdate(ViewGroup viewGroup) {
            this.mAdapter.startUpdate(viewGroup);
        }

        public void finishUpdate(ViewGroup viewGroup) {
            this.mAdapter.finishUpdate(viewGroup);
        }

        public CharSequence getPageTitle(int i) {
            return this.mAdapter.getPageTitle(i % this.mAdapter.getCount());
        }

        public float getPageWidth(int i) {
            return this.mAdapter.getPageWidth(i);
        }

        public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
            this.mAdapter.setPrimaryItem(viewGroup, i, obj);
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }

        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            this.mAdapter.notifyDataSetChanged();
        }

        public int getItemPosition(Object obj) {
            return this.mAdapter.getItemPosition(obj);
        }
    }
}
