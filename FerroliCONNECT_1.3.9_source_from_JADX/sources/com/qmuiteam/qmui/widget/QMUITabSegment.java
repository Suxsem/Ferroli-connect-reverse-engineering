package com.qmuiteam.qmui.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.PagerAdapter;
import android.support.p000v4.view.ViewPager;
import android.support.p003v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.QMUIInterpolatorStaticHolder;
import com.qmuiteam.qmui.util.QMUIColorHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.android.agoo.message.MessageService;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class QMUITabSegment extends HorizontalScrollView {
    public static final int ICON_POSITION_BOTTOM = 3;
    public static final int ICON_POSITION_LEFT = 0;
    public static final int ICON_POSITION_RIGHT = 2;
    public static final int ICON_POSITION_TOP = 1;
    public static final int MODE_FIXED = 1;
    public static final int MODE_SCROLLABLE = 0;
    private static final int STATUS_NORMAL = 0;
    private static final int STATUS_PROGRESS = 1;
    private static final int STATUS_SELECTED = 2;
    private Container mContentLayout;
    private int mDefaultNormalColor;
    private int mDefaultSelectedColor;
    private int mDefaultTabIconPosition;
    /* access modifiers changed from: private */
    public boolean mForceIndicatorNotDoLayoutWhenParentLayout;
    private boolean mHasIndicator;
    /* access modifiers changed from: private */
    public Drawable mIndicatorDrawable;
    /* access modifiers changed from: private */
    public int mIndicatorHeight;
    /* access modifiers changed from: private */
    public boolean mIndicatorTop;
    /* access modifiers changed from: private */
    public View mIndicatorView;
    /* access modifiers changed from: private */
    public boolean mIsAnimating;
    private boolean mIsInSelectTab;
    /* access modifiers changed from: private */
    public boolean mIsIndicatorWidthFollowContent;
    /* access modifiers changed from: private */
    public int mItemSpaceInScrollMode;
    /* access modifiers changed from: private */
    public int mMode;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    /* access modifiers changed from: private */
    public OnTabClickListener mOnTabClickListener;
    private PagerAdapter mPagerAdapter;
    private DataSetObserver mPagerAdapterObserver;
    /* access modifiers changed from: private */
    public int mPendingSelectedIndex;
    /* access modifiers changed from: private */
    public int mSelectedIndex;
    /* access modifiers changed from: private */
    public final ArrayList<OnTabSelectedListener> mSelectedListeners;
    protected View.OnClickListener mTabOnClickListener;
    /* access modifiers changed from: private */
    public int mTabTextSize;
    private TypefaceProvider mTypefaceProvider;
    private ViewPager mViewPager;
    /* access modifiers changed from: private */
    public int mViewPagerScrollState;
    private OnTabSelectedListener mViewPagerSelectedListener;

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconPosition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public interface OnTabClickListener {
        void onTabClick(int i);
    }

    public interface OnTabSelectedListener {
        void onDoubleTap(int i);

        void onTabReselected(int i);

        void onTabSelected(int i);

        void onTabUnselected(int i);
    }

    public interface TypefaceProvider {
        boolean isNormalTabBold();

        boolean isSelectedTabBold();
    }

    public QMUITabSegment(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUITabSegment(Context context, boolean z) {
        this(context, (AttributeSet) null);
        this.mHasIndicator = z;
    }

    public QMUITabSegment(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUITabSegmentStyle);
    }

    public QMUITabSegment(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedListeners = new ArrayList<>();
        this.mSelectedIndex = Integer.MIN_VALUE;
        this.mPendingSelectedIndex = Integer.MIN_VALUE;
        this.mHasIndicator = true;
        this.mIndicatorTop = false;
        this.mIsIndicatorWidthFollowContent = true;
        this.mMode = 1;
        this.mViewPagerScrollState = 0;
        this.mForceIndicatorNotDoLayoutWhenParentLayout = false;
        this.mTabOnClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                if (!QMUITabSegment.this.mIsAnimating && QMUITabSegment.this.mViewPagerScrollState == 0) {
                    int intValue = ((Integer) view.getTag()).intValue();
                    Tab tab = (Tab) QMUITabSegment.this.getAdapter().getItem(intValue);
                    if (tab != null) {
                        QMUITabSegment.this.selectTab(intValue, !tab.isDynamicChangeIconColor());
                    }
                    if (QMUITabSegment.this.mOnTabClickListener != null) {
                        QMUITabSegment.this.mOnTabClickListener.onTabClick(intValue);
                    }
                }
            }
        };
        this.mIsInSelectTab = false;
        init(context, attributeSet, i);
        setHorizontalScrollBarEnabled(false);
        setClipToPadding(false);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        this.mDefaultSelectedColor = QMUIResHelper.getAttrColor(context, C1614R.attr.qmui_config_color_blue);
        this.mDefaultNormalColor = ContextCompat.getColor(context, C1614R.color.qmui_config_color_gray_5);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUITabSegment, i, 0);
        this.mHasIndicator = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUITabSegment_qmui_tab_has_indicator, true);
        this.mIndicatorHeight = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUITabSegment_qmui_tab_indicator_height, getResources().getDimensionPixelSize(C1614R.dimen.qmui_tab_segment_indicator_height));
        this.mTabTextSize = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUITabSegment_android_textSize, getResources().getDimensionPixelSize(C1614R.dimen.qmui_tab_segment_text_size));
        this.mIndicatorTop = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUITabSegment_qmui_tab_indicator_top, false);
        this.mDefaultTabIconPosition = obtainStyledAttributes.getInt(C1614R.styleable.QMUITabSegment_qmui_tab_icon_position, 0);
        this.mMode = obtainStyledAttributes.getInt(C1614R.styleable.QMUITabSegment_qmui_tab_mode, 1);
        this.mItemSpaceInScrollMode = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUITabSegment_qmui_tab_space, QMUIDisplayHelper.dp2px(context, 10));
        String string = obtainStyledAttributes.getString(C1614R.styleable.QMUITabSegment_qmui_tab_typeface_provider);
        obtainStyledAttributes.recycle();
        this.mContentLayout = new Container(context);
        addView(this.mContentLayout, new FrameLayout.LayoutParams(-2, -1));
        if (this.mHasIndicator) {
            createIndicatorView();
        }
        createTypefaceProvider(context, string);
    }

    private void createTypefaceProvider(Context context, String str) {
        ClassLoader classLoader;
        if (!QMUILangHelper.isNullOrEmpty(str)) {
            String trim = str.trim();
            if (trim.length() != 0) {
                String fullClassName = getFullClassName(context, trim);
                try {
                    if (isInEditMode()) {
                        classLoader = getClass().getClassLoader();
                    } else {
                        classLoader = context.getClassLoader();
                    }
                    Constructor<? extends U> constructor = classLoader.loadClass(fullClassName).asSubclass(TypefaceProvider.class).getConstructor(new Class[0]);
                    constructor.setAccessible(true);
                    this.mTypefaceProvider = (TypefaceProvider) constructor.newInstance(new Object[0]);
                } catch (NoSuchMethodException e) {
                    throw new IllegalStateException("Error creating TypefaceProvider " + fullClassName, e);
                } catch (ClassNotFoundException e2) {
                    throw new IllegalStateException("Unable to find TypefaceProvider " + fullClassName, e2);
                } catch (InvocationTargetException e3) {
                    throw new IllegalStateException("Could not instantiate the TypefaceProvider: " + fullClassName, e3);
                } catch (InstantiationException e4) {
                    throw new IllegalStateException("Could not instantiate the TypefaceProvider: " + fullClassName, e4);
                } catch (IllegalAccessException e5) {
                    throw new IllegalStateException("Cannot access non-public constructor " + fullClassName, e5);
                } catch (ClassCastException e6) {
                    throw new IllegalStateException("Class is not a TypefaceProvider " + fullClassName, e6);
                }
            }
        }
    }

    private String getFullClassName(Context context, String str) {
        if (str.charAt(0) != '.') {
            return str;
        }
        return context.getPackageName() + str;
    }

    public void setTypefaceProvider(TypefaceProvider typefaceProvider) {
        this.mTypefaceProvider = typefaceProvider;
    }

    public QMUITabSegment addTab(Tab tab) {
        this.mContentLayout.getTabAdapter().addItem(tab);
        return this;
    }

    /* access modifiers changed from: private */
    public TabAdapter getAdapter() {
        return this.mContentLayout.getTabAdapter();
    }

    private void createIndicatorView() {
        if (this.mIndicatorView == null) {
            this.mIndicatorView = new View(getContext());
            this.mIndicatorView.setLayoutParams(new FrameLayout.LayoutParams(-2, this.mIndicatorHeight));
            Drawable drawable = this.mIndicatorDrawable;
            if (drawable != null) {
                QMUIViewHelper.setBackgroundKeepingPadding(this.mIndicatorView, drawable);
            } else {
                this.mIndicatorView.setBackgroundColor(this.mDefaultSelectedColor);
            }
            this.mContentLayout.addView(this.mIndicatorView);
        }
    }

    public void setTabTextSize(int i) {
        this.mTabTextSize = i;
    }

    public void reset() {
        this.mContentLayout.getTabAdapter().clear();
    }

    public void notifyDataChanged() {
        getAdapter().setup();
    }

    public void addOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        if (!this.mSelectedListeners.contains(onTabSelectedListener)) {
            this.mSelectedListeners.add(onTabSelectedListener);
        }
    }

    public void setItemSpaceInScrollMode(int i) {
        this.mItemSpaceInScrollMode = i;
    }

    public void setIndicatorDrawable(Drawable drawable) {
        this.mIndicatorDrawable = drawable;
        if (drawable != null) {
            this.mIndicatorHeight = drawable.getIntrinsicHeight();
        }
        this.mContentLayout.invalidate();
    }

    public void setIndicatorWidthAdjustContent(boolean z) {
        this.mIsIndicatorWidthFollowContent = z;
    }

    public void setIndicatorPosition(boolean z) {
        this.mIndicatorTop = z;
    }

    public void setHasIndicator(boolean z) {
        if (this.mHasIndicator != z) {
            this.mHasIndicator = z;
            if (this.mHasIndicator) {
                createIndicatorView();
                return;
            }
            this.mContentLayout.removeView(this.mIndicatorView);
            this.mIndicatorView = null;
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public void setMode(int i) {
        if (this.mMode != i) {
            this.mMode = i;
            this.mContentLayout.invalidate();
        }
    }

    public void removeOnTabSelectedListener(@NonNull OnTabSelectedListener onTabSelectedListener) {
        this.mSelectedListeners.remove(onTabSelectedListener);
    }

    public void clearOnTabSelectedListeners() {
        this.mSelectedListeners.clear();
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z) {
        setupWithViewPager(viewPager, z, true);
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager, boolean z, boolean z2) {
        ViewPager.OnPageChangeListener onPageChangeListener;
        ViewPager viewPager2 = this.mViewPager;
        if (!(viewPager2 == null || (onPageChangeListener = this.mOnPageChangeListener) == null)) {
            viewPager2.removeOnPageChangeListener(onPageChangeListener);
        }
        OnTabSelectedListener onTabSelectedListener = this.mViewPagerSelectedListener;
        if (onTabSelectedListener != null) {
            removeOnTabSelectedListener(onTabSelectedListener);
            this.mViewPagerSelectedListener = null;
        }
        if (viewPager != null) {
            this.mViewPager = viewPager;
            if (this.mOnPageChangeListener == null) {
                this.mOnPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            viewPager.addOnPageChangeListener(this.mOnPageChangeListener);
            this.mViewPagerSelectedListener = new ViewPagerOnTabSelectedListener(viewPager);
            addOnTabSelectedListener(this.mViewPagerSelectedListener);
            PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                setPagerAdapter(adapter, z, z2);
                return;
            }
            return;
        }
        this.mViewPager = null;
        setPagerAdapter((PagerAdapter) null, false, false);
    }

    /* access modifiers changed from: private */
    public void dispatchTabSelected(int i) {
        for (int size = this.mSelectedListeners.size() - 1; size >= 0; size--) {
            this.mSelectedListeners.get(size).onTabSelected(i);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchTabUnselected(int i) {
        for (int size = this.mSelectedListeners.size() - 1; size >= 0; size--) {
            this.mSelectedListeners.get(size).onTabUnselected(i);
        }
    }

    private void dispatchTabReselected(int i) {
        for (int size = this.mSelectedListeners.size() - 1; size >= 0; size--) {
            this.mSelectedListeners.get(size).onTabReselected(i);
        }
    }

    /* access modifiers changed from: private */
    public void dispatchTabDoubleTap(int i) {
        for (int size = this.mSelectedListeners.size() - 1; size >= 0; size--) {
            this.mSelectedListeners.get(size).onDoubleTap(i);
        }
    }

    public void setDefaultNormalColor(@ColorInt int i) {
        this.mDefaultNormalColor = i;
    }

    public void setDefaultSelectedColor(@ColorInt int i) {
        this.mDefaultSelectedColor = i;
    }

    public void setDefaultTabIconPosition(int i) {
        this.mDefaultTabIconPosition = i;
    }

    /* access modifiers changed from: private */
    public void preventLayoutToChangeTabColor(TextView textView, int i, Tab tab, int i2) {
        this.mForceIndicatorNotDoLayoutWhenParentLayout = true;
        changeTabColor(textView, i, tab, i2);
        this.mForceIndicatorNotDoLayoutWhenParentLayout = false;
    }

    /* access modifiers changed from: private */
    public void changeTabColor(TextView textView, int i, Tab tab, int i2) {
        changeTabColor(textView, i, tab, i2, false);
    }

    private void changeTabColor(TextView textView, int i, Tab tab, int i2, boolean z) {
        Drawable drawable;
        if (!z) {
            textView.setTextColor(i);
        }
        if (!tab.isDynamicChangeIconColor()) {
            if (i2 == 0 || tab.getSelectedIcon() == null) {
                setDrawable(textView, tab.getNormalIcon(), getTabIconPosition(tab));
            } else if (i2 == 2) {
                setDrawable(textView, tab.getSelectedIcon(), getTabIconPosition(tab));
            }
        } else if (!z && (drawable = textView.getCompoundDrawables()[getTabIconPosition(tab)]) != null) {
            QMUIDrawableHelper.setDrawableTintColor(drawable, i);
            setDrawable(textView, tab.getNormalIcon(), getTabIconPosition(tab));
        }
    }

    public void selectTab(int i) {
        selectTab(i, true);
    }

    /* access modifiers changed from: private */
    public void selectTab(int i, boolean z) {
        int i2 = i;
        if (!this.mIsInSelectTab) {
            this.mIsInSelectTab = true;
            if (this.mContentLayout.getTabAdapter().getSize() == 0 || this.mContentLayout.getTabAdapter().getSize() <= i2) {
                this.mIsInSelectTab = false;
            } else if (this.mSelectedIndex == i2) {
                dispatchTabReselected(i);
                this.mIsInSelectTab = false;
            } else if (this.mIsAnimating) {
                this.mPendingSelectedIndex = i2;
                this.mIsInSelectTab = false;
            } else {
                TabAdapter adapter = getAdapter();
                final List views = adapter.getViews();
                int i3 = this.mSelectedIndex;
                if (i3 == Integer.MIN_VALUE) {
                    adapter.setup();
                    Tab tab = (Tab) adapter.getItem(i2);
                    if (this.mIndicatorView != null && views.size() > 1) {
                        Drawable drawable = this.mIndicatorDrawable;
                        if (drawable != null) {
                            QMUIViewHelper.setBackgroundKeepingPadding(this.mIndicatorView, drawable);
                        } else {
                            this.mIndicatorView.setBackgroundColor(getTabSelectedColor(tab));
                        }
                    }
                    TextView textView = ((TabItemView) views.get(i2)).getTextView();
                    setTextViewTypeface(textView, true);
                    changeTabColor(textView, getTabSelectedColor(tab), tab, 2);
                    dispatchTabSelected(i);
                    this.mSelectedIndex = i2;
                    this.mIsInSelectTab = false;
                    return;
                }
                Tab tab2 = (Tab) adapter.getItem(i3);
                TabItemView tabItemView = (TabItemView) views.get(i3);
                Tab tab3 = (Tab) adapter.getItem(i2);
                TabItemView tabItemView2 = (TabItemView) views.get(i2);
                if (z) {
                    dispatchTabUnselected(i3);
                    dispatchTabSelected(i);
                    setTextViewTypeface(tabItemView.getTextView(), false);
                    setTextViewTypeface(tabItemView2.getTextView(), true);
                    changeTabColor(tabItemView.getTextView(), getTabNormalColor(tab2), tab2, 0, this.mViewPagerScrollState != 0);
                    changeTabColor(tabItemView2.getTextView(), getTabSelectedColor(tab3), tab3, 2, this.mViewPagerScrollState != 0);
                    if (getScrollX() > tabItemView2.getLeft()) {
                        smoothScrollTo(tabItemView2.getLeft(), 0);
                    } else {
                        int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
                        if (getScrollX() + width < tabItemView2.getRight()) {
                            smoothScrollBy((tabItemView2.getRight() - width) - getScrollX(), 0);
                        }
                    }
                    this.mSelectedIndex = i2;
                    this.mIsInSelectTab = false;
                    return;
                }
                int contentLeft = tab3.getContentLeft() - tab2.getContentLeft();
                int contentWidth = tab3.getContentWidth() - tab2.getContentWidth();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.setInterpolator(QMUIInterpolatorStaticHolder.LINEAR_INTERPOLATOR);
                final Tab tab4 = tab2;
                final int i4 = contentLeft;
                final int i5 = contentWidth;
                final Tab tab5 = tab3;
                C16532 r11 = r0;
                final TabItemView tabItemView3 = tabItemView;
                ValueAnimator valueAnimator = ofFloat;
                final TabItemView tabItemView4 = tabItemView2;
                C16532 r0 = new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        if (QMUITabSegment.this.mIndicatorView != null && views.size() > 1) {
                            int contentLeft = (int) (((float) tab4.getContentLeft()) + (((float) i4) * floatValue));
                            int contentWidth = (int) (((float) tab4.getContentWidth()) + (((float) i5) * floatValue));
                            if (QMUITabSegment.this.mIndicatorDrawable == null) {
                                QMUITabSegment.this.mIndicatorView.setBackgroundColor(QMUIColorHelper.computeColor(QMUITabSegment.this.getTabSelectedColor(tab4), QMUITabSegment.this.getTabSelectedColor(tab5), floatValue));
                            }
                            QMUITabSegment.this.mIndicatorView.layout(contentLeft, QMUITabSegment.this.mIndicatorView.getTop(), contentWidth + contentLeft, QMUITabSegment.this.mIndicatorView.getBottom());
                        }
                        int computeColor = QMUIColorHelper.computeColor(QMUITabSegment.this.getTabSelectedColor(tab4), QMUITabSegment.this.getTabNormalColor(tab4), floatValue);
                        int computeColor2 = QMUIColorHelper.computeColor(QMUITabSegment.this.getTabNormalColor(tab5), QMUITabSegment.this.getTabSelectedColor(tab5), floatValue);
                        QMUITabSegment.this.preventLayoutToChangeTabColor(tabItemView3.getTextView(), computeColor, tab4, 1);
                        QMUITabSegment.this.preventLayoutToChangeTabColor(tabItemView4.getTextView(), computeColor2, tab5, 1);
                    }
                };
                valueAnimator.addUpdateListener(r11);
                final TabItemView tabItemView5 = tabItemView2;
                final Tab tab6 = tab3;
                final int i6 = i;
                final int i7 = i3;
                final TabItemView tabItemView6 = tabItemView;
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    public void onAnimationRepeat(Animator animator) {
                    }

                    public void onAnimationStart(Animator animator) {
                        boolean unused = QMUITabSegment.this.mIsAnimating = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        boolean unused = QMUITabSegment.this.mIsAnimating = false;
                        QMUITabSegment.this.changeTabColor(tabItemView5.getTextView(), QMUITabSegment.this.getTabSelectedColor(tab6), tab6, 2);
                        QMUITabSegment.this.dispatchTabSelected(i6);
                        QMUITabSegment.this.dispatchTabUnselected(i7);
                        QMUITabSegment.this.setTextViewTypeface(tabItemView6.getTextView(), false);
                        QMUITabSegment.this.setTextViewTypeface(tabItemView5.getTextView(), true);
                        int unused2 = QMUITabSegment.this.mSelectedIndex = i6;
                        if (QMUITabSegment.this.mPendingSelectedIndex != Integer.MIN_VALUE && QMUITabSegment.this.mPendingSelectedIndex != QMUITabSegment.this.mSelectedIndex) {
                            QMUITabSegment.this.selectTab(i6, false);
                        }
                    }

                    public void onAnimationCancel(Animator animator) {
                        QMUITabSegment.this.changeTabColor(tabItemView5.getTextView(), QMUITabSegment.this.getTabSelectedColor(tab6), tab6, 2);
                        boolean unused = QMUITabSegment.this.mIsAnimating = false;
                    }
                });
                valueAnimator.setDuration(200);
                valueAnimator.start();
                this.mIsInSelectTab = false;
            }
        }
    }

    /* access modifiers changed from: private */
    public void setTextViewTypeface(TextView textView, boolean z) {
        TypefaceProvider typefaceProvider = this.mTypefaceProvider;
        if (typefaceProvider != null && textView != null) {
            textView.setTypeface((Typeface) null, z ? typefaceProvider.isSelectedTabBold() : typefaceProvider.isNormalTabBold() ? 1 : 0);
        }
    }

    public void updateIndicatorPosition(int i, float f) {
        int i2;
        if (!this.mIsAnimating && !this.mIsInSelectTab && f != 0.0f) {
            if (f < 0.0f) {
                i2 = i - 1;
                f = -f;
            } else {
                i2 = i + 1;
            }
            TabAdapter adapter = getAdapter();
            List views = adapter.getViews();
            if (views.size() > i && views.size() > i2) {
                Tab tab = (Tab) adapter.getItem(i);
                Tab tab2 = (Tab) adapter.getItem(i2);
                TextView textView = ((TabItemView) views.get(i)).getTextView();
                TextView textView2 = ((TabItemView) views.get(i2)).getTextView();
                int computeColor = QMUIColorHelper.computeColor(getTabSelectedColor(tab), getTabNormalColor(tab), f);
                int computeColor2 = QMUIColorHelper.computeColor(getTabNormalColor(tab2), getTabSelectedColor(tab2), f);
                preventLayoutToChangeTabColor(textView, computeColor, tab, 1);
                preventLayoutToChangeTabColor(textView2, computeColor2, tab2, 1);
                this.mForceIndicatorNotDoLayoutWhenParentLayout = false;
                if (this.mIndicatorView != null && views.size() > 1) {
                    int contentLeft = tab2.getContentLeft() - tab.getContentLeft();
                    int contentWidth = tab2.getContentWidth() - tab.getContentWidth();
                    int contentLeft2 = (int) (((float) tab.getContentLeft()) + (((float) contentLeft) * f));
                    int contentWidth2 = (int) (((float) tab.getContentWidth()) + (((float) contentWidth) * f));
                    if (this.mIndicatorDrawable == null) {
                        this.mIndicatorView.setBackgroundColor(QMUIColorHelper.computeColor(getTabSelectedColor(tab), getTabSelectedColor(tab2), f));
                    }
                    View view = this.mIndicatorView;
                    view.layout(contentLeft2, view.getTop(), contentWidth2 + contentLeft2, this.mIndicatorView.getBottom());
                }
            }
        }
    }

    public void updateTabText(int i, String str) {
        Tab tab = (Tab) getAdapter().getItem(i);
        if (tab != null) {
            tab.setText(str);
            notifyDataChanged();
        }
    }

    public void replaceTab(int i, Tab tab) {
        try {
            getAdapter().replaceItem(i, tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.mOnTabClickListener = onTabClickListener;
    }

    /* access modifiers changed from: private */
    public void setDrawable(TextView textView, Drawable drawable, int i) {
        Drawable drawable2 = i == 0 ? drawable : null;
        Drawable drawable3 = i == 1 ? drawable : null;
        Drawable drawable4 = i == 2 ? drawable : null;
        if (i != 3) {
            drawable = null;
        }
        textView.setCompoundDrawables(drawable2, drawable3, drawable4, drawable);
    }

    /* access modifiers changed from: private */
    public int getTabNormalColor(Tab tab) {
        int normalColor = tab.getNormalColor();
        return normalColor == Integer.MIN_VALUE ? this.mDefaultNormalColor : normalColor;
    }

    /* access modifiers changed from: private */
    public int getTabIconPosition(Tab tab) {
        int iconPosition = tab.getIconPosition();
        return iconPosition == Integer.MIN_VALUE ? this.mDefaultTabIconPosition : iconPosition;
    }

    /* access modifiers changed from: private */
    public int getTabSelectedColor(Tab tab) {
        int selectedColor = tab.getSelectedColor();
        return selectedColor == Integer.MIN_VALUE ? this.mDefaultSelectedColor : selectedColor;
    }

    /* access modifiers changed from: package-private */
    public void populateFromPagerAdapter(boolean z) {
        int currentItem;
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
            if (z) {
                reset();
                for (int i = 0; i < count; i++) {
                    addTab(new Tab(this.mPagerAdapter.getPageTitle(i)));
                }
                notifyDataChanged();
            }
            ViewPager viewPager = this.mViewPager;
            if (viewPager != null && count > 0 && (currentItem = viewPager.getCurrentItem()) != this.mSelectedIndex && currentItem < count) {
                selectTab(currentItem);
            }
        } else if (z) {
            reset();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int paddingLeft = getPaddingLeft() + getPaddingRight();
            childAt.measure(View.MeasureSpec.makeMeasureSpec(size - paddingLeft, 1073741824), i2);
            if (mode == Integer.MIN_VALUE) {
                setMeasuredDimension(Math.min(size, childAt.getMeasuredWidth() + paddingLeft), i2);
                return;
            }
        }
        setMeasuredDimension(i, i2);
    }

    /* access modifiers changed from: package-private */
    public void setPagerAdapter(@Nullable PagerAdapter pagerAdapter, boolean z, boolean z2) {
        DataSetObserver dataSetObserver;
        PagerAdapter pagerAdapter2 = this.mPagerAdapter;
        if (!(pagerAdapter2 == null || (dataSetObserver = this.mPagerAdapterObserver) == null)) {
            pagerAdapter2.unregisterDataSetObserver(dataSetObserver);
        }
        this.mPagerAdapter = pagerAdapter;
        if (z2 && pagerAdapter != null) {
            if (this.mPagerAdapterObserver == null) {
                this.mPagerAdapterObserver = new PagerAdapterObserver(z);
            }
            pagerAdapter.registerDataSetObserver(this.mPagerAdapterObserver);
        }
        populateFromPagerAdapter(z);
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }

    /* access modifiers changed from: private */
    public int getTabCount() {
        return getAdapter().getSize();
    }

    public Tab getTab(int i) {
        return (Tab) getAdapter().getItem(i);
    }

    public void showSignCountView(Context context, int i, int i2) {
        ((Tab) getAdapter().getItem(i)).showSignCountView(context, i2);
        notifyDataChanged();
    }

    public void hideSignCountView(int i) {
        ((Tab) getAdapter().getItem(i)).hideSignCountView();
    }

    public int getSignCount(int i) {
        return ((Tab) getAdapter().getItem(i)).getSignCount();
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<QMUITabSegment> mTabSegmentRef;

        public TabLayoutOnPageChangeListener(QMUITabSegment qMUITabSegment) {
            this.mTabSegmentRef = new WeakReference<>(qMUITabSegment);
        }

        public void onPageScrollStateChanged(int i) {
            QMUITabSegment qMUITabSegment = (QMUITabSegment) this.mTabSegmentRef.get();
            if (qMUITabSegment != null) {
                int unused = qMUITabSegment.mViewPagerScrollState = i;
            }
        }

        public void onPageScrolled(int i, float f, int i2) {
            QMUITabSegment qMUITabSegment = (QMUITabSegment) this.mTabSegmentRef.get();
            if (qMUITabSegment != null) {
                qMUITabSegment.updateIndicatorPosition(i, f);
            }
        }

        public void onPageSelected(int i) {
            QMUITabSegment qMUITabSegment = (QMUITabSegment) this.mTabSegmentRef.get();
            if (qMUITabSegment != null && qMUITabSegment.getSelectedIndex() != i && i < qMUITabSegment.getTabCount()) {
                qMUITabSegment.selectTab(i);
            }
        }
    }

    private static class ViewPagerOnTabSelectedListener implements OnTabSelectedListener {
        private final ViewPager mViewPager;

        public void onDoubleTap(int i) {
        }

        public void onTabReselected(int i) {
        }

        public void onTabUnselected(int i) {
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            this.mViewPager = viewPager;
        }

        public void onTabSelected(int i) {
            this.mViewPager.setCurrentItem(i, false);
        }
    }

    public static class Tab {
        public static final int USE_TAB_SEGMENT = Integer.MIN_VALUE;
        private int contentLeft;
        private int contentWidth;
        private boolean dynamicChangeIconColor;
        private int gravity;
        private int iconPosition;
        private List<View> mCustomViews;
        private int mSignCountDigits;
        private int mSignCountMarginRight;
        private int mSignCountMarginTop;
        private TextView mSignCountTextView;
        private int normalColor;
        private Drawable normalIcon;
        private int selectedColor;
        private Drawable selectedIcon;
        private CharSequence text;
        private int textSize;

        public Tab(CharSequence charSequence) {
            this.textSize = Integer.MIN_VALUE;
            this.normalColor = Integer.MIN_VALUE;
            this.selectedColor = Integer.MIN_VALUE;
            this.normalIcon = null;
            this.selectedIcon = null;
            this.contentWidth = 0;
            this.contentLeft = 0;
            this.iconPosition = Integer.MIN_VALUE;
            this.gravity = 17;
            this.mSignCountDigits = 2;
            this.mSignCountMarginRight = 0;
            this.mSignCountMarginTop = 0;
            this.dynamicChangeIconColor = true;
            this.text = charSequence;
        }

        public Tab(Drawable drawable, Drawable drawable2, CharSequence charSequence, boolean z) {
            this(drawable, drawable2, charSequence, z, true);
        }

        public Tab(Drawable drawable, Drawable drawable2, CharSequence charSequence, boolean z, boolean z2) {
            this.textSize = Integer.MIN_VALUE;
            this.normalColor = Integer.MIN_VALUE;
            this.selectedColor = Integer.MIN_VALUE;
            this.normalIcon = null;
            this.selectedIcon = null;
            this.contentWidth = 0;
            this.contentLeft = 0;
            this.iconPosition = Integer.MIN_VALUE;
            this.gravity = 17;
            this.mSignCountDigits = 2;
            this.mSignCountMarginRight = 0;
            this.mSignCountMarginTop = 0;
            this.dynamicChangeIconColor = true;
            this.normalIcon = drawable;
            Drawable drawable3 = this.normalIcon;
            if (drawable3 != null && z2) {
                drawable3.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            this.selectedIcon = drawable2;
            Drawable drawable4 = this.selectedIcon;
            if (drawable4 != null && z2) {
                drawable4.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
            }
            this.text = charSequence;
            this.dynamicChangeIconColor = z;
        }

        public void setmSignCountDigits(int i) {
            this.mSignCountDigits = i;
        }

        public void setTextColor(@ColorInt int i, @ColorInt int i2) {
            this.normalColor = i;
            this.selectedColor = i2;
        }

        public int getTextSize() {
            return this.textSize;
        }

        public void setTextSize(int i) {
            this.textSize = i;
        }

        public CharSequence getText() {
            return this.text;
        }

        public void setText(CharSequence charSequence) {
            this.text = charSequence;
        }

        public int getContentLeft() {
            return this.contentLeft;
        }

        public void setContentLeft(int i) {
            this.contentLeft = i;
        }

        public int getContentWidth() {
            return this.contentWidth;
        }

        public void setContentWidth(int i) {
            this.contentWidth = i;
        }

        public int getIconPosition() {
            return this.iconPosition;
        }

        public void setIconPosition(int i) {
            this.iconPosition = i;
        }

        public int getGravity() {
            return this.gravity;
        }

        public void setGravity(int i) {
            this.gravity = i;
        }

        public int getNormalColor() {
            return this.normalColor;
        }

        public Drawable getNormalIcon() {
            return this.normalIcon;
        }

        public int getSelectedColor() {
            return this.selectedColor;
        }

        public Drawable getSelectedIcon() {
            return this.selectedIcon;
        }

        public boolean isDynamicChangeIconColor() {
            return this.dynamicChangeIconColor;
        }

        public void addCustomView(@NonNull View view) {
            if (this.mCustomViews == null) {
                this.mCustomViews = new ArrayList();
            }
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(getDefaultCustomLayoutParam());
            }
            this.mCustomViews.add(view);
        }

        public List<View> getCustomViews() {
            return this.mCustomViews;
        }

        public void setSignCountMargin(int i, int i2) {
            this.mSignCountMarginRight = i;
            this.mSignCountMarginTop = i2;
            TextView textView = this.mSignCountTextView;
            if (textView != null && textView.getLayoutParams() != null) {
                ((ViewGroup.MarginLayoutParams) this.mSignCountTextView.getLayoutParams()).rightMargin = i;
                ((ViewGroup.MarginLayoutParams) this.mSignCountTextView.getLayoutParams()).topMargin = i2;
            }
        }

        private TextView ensureSignCountView(Context context) {
            if (this.mSignCountTextView == null) {
                this.mSignCountTextView = new TextView(context, (AttributeSet) null, C1614R.attr.qmui_tab_sign_count_view);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, QMUIResHelper.getAttrDimen(context, C1614R.attr.qmui_tab_sign_count_view_minSize));
                layoutParams.addRule(6, C1614R.C1616id.qmui_tab_segment_item_id);
                layoutParams.addRule(1, C1614R.C1616id.qmui_tab_segment_item_id);
                this.mSignCountTextView.setLayoutParams(layoutParams);
                addCustomView(this.mSignCountTextView);
            }
            setSignCountMargin(this.mSignCountMarginRight, this.mSignCountMarginTop);
            return this.mSignCountTextView;
        }

        public void showSignCountView(Context context, int i) {
            ensureSignCountView(context);
            this.mSignCountTextView.setVisibility(0);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mSignCountTextView.getLayoutParams();
            if (i != 0) {
                layoutParams.height = QMUIResHelper.getAttrDimen(this.mSignCountTextView.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize_with_text);
                this.mSignCountTextView.setLayoutParams(layoutParams);
                TextView textView = this.mSignCountTextView;
                textView.setMinHeight(QMUIResHelper.getAttrDimen(textView.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize_with_text));
                TextView textView2 = this.mSignCountTextView;
                textView2.setMinWidth(QMUIResHelper.getAttrDimen(textView2.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize_with_text));
                this.mSignCountTextView.setText(getNumberDigitsFormattingValue(i));
                return;
            }
            layoutParams.height = QMUIResHelper.getAttrDimen(this.mSignCountTextView.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize);
            this.mSignCountTextView.setLayoutParams(layoutParams);
            TextView textView3 = this.mSignCountTextView;
            textView3.setMinHeight(QMUIResHelper.getAttrDimen(textView3.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize));
            TextView textView4 = this.mSignCountTextView;
            textView4.setMinWidth(QMUIResHelper.getAttrDimen(textView4.getContext(), C1614R.attr.qmui_tab_sign_count_view_minSize));
            this.mSignCountTextView.setText((CharSequence) null);
        }

        public void hideSignCountView() {
            TextView textView = this.mSignCountTextView;
            if (textView != null) {
                textView.setVisibility(8);
            }
        }

        public int getSignCount() {
            TextView textView = this.mSignCountTextView;
            if (textView == null || QMUILangHelper.isNullOrEmpty(textView.getText())) {
                return 0;
            }
            return Integer.parseInt(this.mSignCountTextView.getText().toString());
        }

        private RelativeLayout.LayoutParams getDefaultCustomLayoutParam() {
            return new RelativeLayout.LayoutParams(-2, -2);
        }

        private String getNumberDigitsFormattingValue(int i) {
            if (QMUILangHelper.getNumberDigits(i) <= this.mSignCountDigits) {
                return String.valueOf(i);
            }
            String str = "";
            for (int i2 = 1; i2 <= this.mSignCountDigits; i2++) {
                str = str + MessageService.MSG_ACCS_NOTIFY_DISMISS;
            }
            return str + MqttTopic.SINGLE_LEVEL_WILDCARD;
        }
    }

    public class TabAdapter extends QMUIItemViewsAdapter<Tab, TabItemView> {
        public TabAdapter(ViewGroup viewGroup) {
            super(viewGroup);
        }

        /* access modifiers changed from: protected */
        public TabItemView createView(ViewGroup viewGroup) {
            QMUITabSegment qMUITabSegment = QMUITabSegment.this;
            return new TabItemView(qMUITabSegment.getContext());
        }

        /* access modifiers changed from: protected */
        public void bind(Tab tab, TabItemView tabItemView, int i) {
            TextView textView = tabItemView.getTextView();
            QMUITabSegment.this.setTextViewTypeface(textView, false);
            List<View> customViews = tab.getCustomViews();
            if (customViews != null && customViews.size() > 0) {
                tabItemView.setTag(C1614R.C1616id.qmui_view_can_not_cache_tag, true);
                for (View next : customViews) {
                    if (next.getParent() == null) {
                        tabItemView.addView(next);
                    }
                }
            }
            if (QMUITabSegment.this.mMode == 1) {
                int gravity = tab.getGravity();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                int i2 = -1;
                layoutParams.addRule(9, (gravity & 3) == 3 ? -1 : 0);
                layoutParams.addRule(14, (gravity & 17) == 17 ? -1 : 0);
                if ((gravity & 5) != 5) {
                    i2 = 0;
                }
                layoutParams.addRule(11, i2);
                textView.setLayoutParams(layoutParams);
            }
            textView.setText(tab.getText());
            if (tab.getNormalIcon() == null) {
                textView.setCompoundDrawablePadding(0);
                textView.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
            } else {
                Drawable normalIcon = tab.getNormalIcon();
                if (normalIcon != null) {
                    Drawable mutate = normalIcon.mutate();
                    QMUITabSegment qMUITabSegment = QMUITabSegment.this;
                    qMUITabSegment.setDrawable(textView, mutate, qMUITabSegment.getTabIconPosition(tab));
                    textView.setCompoundDrawablePadding(QMUIDisplayHelper.dp2px(QMUITabSegment.this.getContext(), 4));
                } else {
                    textView.setCompoundDrawables((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                }
            }
            int textSize = tab.getTextSize();
            if (textSize == Integer.MIN_VALUE) {
                textSize = QMUITabSegment.this.mTabTextSize;
            }
            textView.setTextSize(0, (float) textSize);
            if (i == QMUITabSegment.this.mSelectedIndex) {
                if (QMUITabSegment.this.mIndicatorView != null && getViews().size() > 1) {
                    if (QMUITabSegment.this.mIndicatorDrawable != null) {
                        QMUIViewHelper.setBackgroundKeepingPadding(QMUITabSegment.this.mIndicatorView, QMUITabSegment.this.mIndicatorDrawable);
                    } else {
                        QMUITabSegment.this.mIndicatorView.setBackgroundColor(QMUITabSegment.this.getTabSelectedColor(tab));
                    }
                }
                QMUITabSegment.this.changeTabColor(tabItemView.getTextView(), QMUITabSegment.this.getTabSelectedColor(tab), tab, 2);
            } else {
                QMUITabSegment.this.changeTabColor(tabItemView.getTextView(), QMUITabSegment.this.getTabNormalColor(tab), tab, 0);
            }
            tabItemView.setTag(Integer.valueOf(i));
            tabItemView.setOnClickListener(QMUITabSegment.this.mTabOnClickListener);
        }
    }

    public class InnerTextView extends AppCompatTextView {
        public InnerTextView(Context context) {
            super(context);
        }

        public InnerTextView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public void requestLayout() {
            if (!QMUITabSegment.this.mForceIndicatorNotDoLayoutWhenParentLayout) {
                super.requestLayout();
            }
        }
    }

    public class TabItemView extends RelativeLayout {
        private GestureDetector mGestureDetector = null;
        private InnerTextView mTextView;

        public TabItemView(Context context) {
            super(context);
            this.mTextView = new InnerTextView(getContext());
            this.mTextView.setSingleLine(true);
            this.mTextView.setGravity(17);
            this.mTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            this.mTextView.setId(C1614R.C1616id.qmui_tab_segment_item_id);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(15, -1);
            addView(this.mTextView, layoutParams);
            this.mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener(QMUITabSegment.this) {
                public boolean onDoubleTap(MotionEvent motionEvent) {
                    if (QMUITabSegment.this.mSelectedListeners == null || QMUITabSegment.this.mIsAnimating) {
                        return false;
                    }
                    int intValue = ((Integer) TabItemView.this.getTag()).intValue();
                    if (((Tab) QMUITabSegment.this.getAdapter().getItem(intValue)) == null) {
                        return false;
                    }
                    QMUITabSegment.this.dispatchTabDoubleTap(intValue);
                    return true;
                }
            });
        }

        public TextView getTextView() {
            return this.mTextView;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mSelectedIndex != Integer.MIN_VALUE && this.mMode == 0) {
            TabItemView tabItemView = (TabItemView) getAdapter().getViews().get(this.mSelectedIndex);
            if (getScrollX() > tabItemView.getLeft()) {
                scrollTo(tabItemView.getLeft(), 0);
                return;
            }
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            if (getScrollX() + width < tabItemView.getRight()) {
                scrollBy((tabItemView.getRight() - width) - getScrollX(), 0);
            }
        }
    }

    private class PagerAdapterObserver extends DataSetObserver {
        private final boolean mUseAdapterTitle;

        PagerAdapterObserver(boolean z) {
            this.mUseAdapterTitle = z;
        }

        public void onChanged() {
            QMUITabSegment.this.populateFromPagerAdapter(this.mUseAdapterTitle);
        }

        public void onInvalidated() {
            QMUITabSegment.this.populateFromPagerAdapter(this.mUseAdapterTitle);
        }
    }

    private final class Container extends ViewGroup {
        private int mLastSelectedIndex = -1;
        private TabAdapter mTabAdapter;

        public Container(Context context) {
            super(context);
            this.mTabAdapter = new TabAdapter(this);
        }

        public TabAdapter getTabAdapter() {
            return this.mTabAdapter;
        }

        /* access modifiers changed from: protected */
        public void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            List views = this.mTabAdapter.getViews();
            int size3 = views.size();
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size3; i5++) {
                if (((View) views.get(i5)).getVisibility() == 0) {
                    i4++;
                }
            }
            if (size3 == 0 || i4 == 0) {
                setMeasuredDimension(size, size2);
                return;
            }
            int paddingTop = (size2 - getPaddingTop()) - getPaddingBottom();
            if (QMUITabSegment.this.mMode == 1) {
                int i6 = size / i4;
                while (i3 < size3) {
                    View view = (View) views.get(i3);
                    if (view.getVisibility() == 0) {
                        view.measure(View.MeasureSpec.makeMeasureSpec(i6, 1073741824), View.MeasureSpec.makeMeasureSpec(paddingTop, 1073741824));
                    }
                    i3++;
                }
            } else {
                int i7 = 0;
                while (i3 < size3) {
                    View view2 = (View) views.get(i3);
                    if (view2.getVisibility() == 0) {
                        view2.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(paddingTop, 1073741824));
                        i7 += view2.getMeasuredWidth() + QMUITabSegment.this.mItemSpaceInScrollMode;
                    }
                    i3++;
                }
                size = i7 - QMUITabSegment.this.mItemSpaceInScrollMode;
            }
            if (QMUITabSegment.this.mIndicatorView != null) {
                ViewGroup.LayoutParams layoutParams = QMUITabSegment.this.mIndicatorView.getLayoutParams();
                QMUITabSegment.this.mIndicatorView.measure(View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
            }
            setMeasuredDimension(size, size2);
        }

        /* access modifiers changed from: protected */
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            List views = this.mTabAdapter.getViews();
            int size = views.size();
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                if (((View) views.get(i6)).getVisibility() == 0) {
                    i5++;
                }
            }
            if (size != 0 && i5 != 0) {
                int paddingLeft = getPaddingLeft();
                for (int i7 = 0; i7 < size; i7++) {
                    TabItemView tabItemView = (TabItemView) views.get(i7);
                    if (tabItemView.getVisibility() == 0) {
                        int measuredWidth = tabItemView.getMeasuredWidth();
                        int i8 = paddingLeft + measuredWidth;
                        tabItemView.layout(paddingLeft, getPaddingTop(), i8, (i4 - i2) - getPaddingBottom());
                        Tab tab = (Tab) this.mTabAdapter.getItem(i7);
                        int contentLeft = tab.getContentLeft();
                        int contentWidth = tab.getContentWidth();
                        if (QMUITabSegment.this.mMode == 1 && QMUITabSegment.this.mIsIndicatorWidthFollowContent) {
                            TextView textView = tabItemView.getTextView();
                            paddingLeft += textView.getLeft();
                            measuredWidth = textView.getWidth();
                        }
                        if (!(contentLeft == paddingLeft && contentWidth == measuredWidth)) {
                            tab.setContentLeft(paddingLeft);
                            tab.setContentWidth(measuredWidth);
                        }
                        paddingLeft = i8 + (QMUITabSegment.this.mMode == 0 ? QMUITabSegment.this.mItemSpaceInScrollMode : 0);
                    }
                }
                int access$1400 = QMUITabSegment.this.mSelectedIndex == Integer.MIN_VALUE ? 0 : QMUITabSegment.this.mSelectedIndex;
                Tab tab2 = (Tab) this.mTabAdapter.getItem(access$1400);
                int contentLeft2 = tab2.getContentLeft();
                int contentWidth2 = tab2.getContentWidth();
                if (QMUITabSegment.this.mIndicatorView != null) {
                    if (i5 > 1) {
                        QMUITabSegment.this.mIndicatorView.setVisibility(0);
                        if (QMUITabSegment.this.mIndicatorTop) {
                            QMUITabSegment.this.mIndicatorView.layout(contentLeft2, 0, contentWidth2 + contentLeft2, QMUITabSegment.this.mIndicatorHeight);
                        } else {
                            int i9 = i4 - i2;
                            QMUITabSegment.this.mIndicatorView.layout(contentLeft2, i9 - QMUITabSegment.this.mIndicatorHeight, contentWidth2 + contentLeft2, i9);
                        }
                    } else {
                        QMUITabSegment.this.mIndicatorView.setVisibility(8);
                    }
                }
                this.mLastSelectedIndex = access$1400;
            }
        }
    }
}
