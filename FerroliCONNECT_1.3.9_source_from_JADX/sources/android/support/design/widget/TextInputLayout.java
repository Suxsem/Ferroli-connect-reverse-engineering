package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.C0030R;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.AbsSavedState;
import android.support.p000v4.view.AccessibilityDelegateCompat;
import android.support.p000v4.view.ViewCompat;
import android.support.p000v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p000v4.widget.Space;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p000v4.widget.ViewGroupUtils;
import android.support.p003v7.appcompat.C0359R;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.widget.AppCompatDrawableManager;
import android.support.p003v7.widget.AppCompatTextView;
import android.support.p003v7.widget.DrawableUtils;
import android.support.p003v7.widget.TintTypedArray;
import android.support.p003v7.widget.WithHint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextInputLayout extends LinearLayout implements WithHint {
    private static final int ANIMATION_DURATION = 200;
    private static final int INVALID_MAX_LENGTH = -1;
    private static final String LOG_TAG = "TextInputLayout";
    private ValueAnimator mAnimator;
    final CollapsingTextHelper mCollapsingTextHelper;
    boolean mCounterEnabled;
    private int mCounterMaxLength;
    private int mCounterOverflowTextAppearance;
    private boolean mCounterOverflowed;
    private int mCounterTextAppearance;
    private TextView mCounterView;
    private ColorStateList mDefaultTextColor;
    EditText mEditText;
    private CharSequence mError;
    private boolean mErrorEnabled;
    private boolean mErrorShown;
    private int mErrorTextAppearance;
    TextView mErrorView;
    private ColorStateList mFocusedTextColor;
    private boolean mHasPasswordToggleTintList;
    private boolean mHasPasswordToggleTintMode;
    private boolean mHasReconstructedEditTextBackground;
    private CharSequence mHint;
    private boolean mHintAnimationEnabled;
    private boolean mHintEnabled;
    private boolean mHintExpanded;
    private boolean mInDrawableStateChanged;
    private LinearLayout mIndicatorArea;
    private int mIndicatorsAdded;
    private final FrameLayout mInputFrame;
    private Drawable mOriginalEditTextEndDrawable;
    private CharSequence mOriginalHint;
    private CharSequence mPasswordToggleContentDesc;
    private Drawable mPasswordToggleDrawable;
    private Drawable mPasswordToggleDummyDrawable;
    private boolean mPasswordToggleEnabled;
    private ColorStateList mPasswordToggleTintList;
    private PorterDuff.Mode mPasswordToggleTintMode;
    private CheckableImageButton mPasswordToggleView;
    private boolean mPasswordToggledVisible;
    /* access modifiers changed from: private */
    public boolean mRestoringSavedState;
    private Paint mTmpPaint;
    private final Rect mTmpRect;
    private Typeface mTypeface;

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mTmpRect = new Rect();
        this.mCollapsingTextHelper = new CollapsingTextHelper(this);
        ThemeUtils.checkAppCompatTheme(context);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        this.mInputFrame = new FrameLayout(context);
        this.mInputFrame.setAddStatesFromChildren(true);
        addView(this.mInputFrame);
        this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.mCollapsingTextHelper.setPositionInterpolator(new AccelerateInterpolator());
        this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0030R.styleable.TextInputLayout, i, C0030R.style.Widget_Design_TextInputLayout);
        this.mHintEnabled = obtainStyledAttributes.getBoolean(C0030R.styleable.TextInputLayout_hintEnabled, true);
        setHint(obtainStyledAttributes.getText(C0030R.styleable.TextInputLayout_android_hint));
        this.mHintAnimationEnabled = obtainStyledAttributes.getBoolean(C0030R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (obtainStyledAttributes.hasValue(C0030R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(C0030R.styleable.TextInputLayout_android_textColorHint);
            this.mFocusedTextColor = colorStateList;
            this.mDefaultTextColor = colorStateList;
        }
        if (obtainStyledAttributes.getResourceId(C0030R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(obtainStyledAttributes.getResourceId(C0030R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.mErrorTextAppearance = obtainStyledAttributes.getResourceId(C0030R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean z = obtainStyledAttributes.getBoolean(C0030R.styleable.TextInputLayout_errorEnabled, false);
        boolean z2 = obtainStyledAttributes.getBoolean(C0030R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(obtainStyledAttributes.getInt(C0030R.styleable.TextInputLayout_counterMaxLength, -1));
        this.mCounterTextAppearance = obtainStyledAttributes.getResourceId(C0030R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.mCounterOverflowTextAppearance = obtainStyledAttributes.getResourceId(C0030R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.mPasswordToggleEnabled = obtainStyledAttributes.getBoolean(C0030R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.mPasswordToggleDrawable = obtainStyledAttributes.getDrawable(C0030R.styleable.TextInputLayout_passwordToggleDrawable);
        this.mPasswordToggleContentDesc = obtainStyledAttributes.getText(C0030R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (obtainStyledAttributes.hasValue(C0030R.styleable.TextInputLayout_passwordToggleTint)) {
            this.mHasPasswordToggleTintList = true;
            this.mPasswordToggleTintList = obtainStyledAttributes.getColorStateList(C0030R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (obtainStyledAttributes.hasValue(C0030R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.mHasPasswordToggleTintMode = true;
            this.mPasswordToggleTintMode = ViewUtils.parseTintMode(obtainStyledAttributes.getInt(C0030R.styleable.TextInputLayout_passwordToggleTintMode, -1), (PorterDuff.Mode) null);
        }
        obtainStyledAttributes.recycle();
        setErrorEnabled(z);
        setCounterEnabled(z2);
        applyPasswordToggleTint();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new TextInputAccessibilityDelegate());
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.mInputFrame.addView(view, layoutParams2);
            this.mInputFrame.setLayoutParams(layoutParams);
            updateInputLayoutMargins();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    public void setTypeface(@Nullable Typeface typeface) {
        Typeface typeface2 = this.mTypeface;
        if ((typeface2 != null && !typeface2.equals(typeface)) || (this.mTypeface == null && typeface != null)) {
            this.mTypeface = typeface;
            this.mCollapsingTextHelper.setTypefaces(typeface);
            TextView textView = this.mCounterView;
            if (textView != null) {
                textView.setTypeface(typeface);
            }
            TextView textView2 = this.mErrorView;
            if (textView2 != null) {
                textView2.setTypeface(typeface);
            }
        }
    }

    @NonNull
    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText;
        if (this.mOriginalHint == null || (editText = this.mEditText) == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        CharSequence hint = editText.getHint();
        this.mEditText.setHint(this.mOriginalHint);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, i);
        } finally {
            this.mEditText.setHint(hint);
        }
    }

    private void setEditText(EditText editText) {
        if (this.mEditText == null) {
            if (!(editText instanceof TextInputEditText)) {
                Log.i(LOG_TAG, "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.mEditText = editText;
            if (!hasPasswordTransformation()) {
                this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface());
            }
            this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
            int gravity = this.mEditText.getGravity();
            this.mCollapsingTextHelper.setCollapsedTextGravity((gravity & -113) | 48);
            this.mCollapsingTextHelper.setExpandedTextGravity(gravity);
            this.mEditText.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.updateLabelState(!textInputLayout.mRestoringSavedState);
                    if (TextInputLayout.this.mCounterEnabled) {
                        TextInputLayout.this.updateCounter(editable.length());
                    }
                }
            });
            if (this.mDefaultTextColor == null) {
                this.mDefaultTextColor = this.mEditText.getHintTextColors();
            }
            if (this.mHintEnabled && TextUtils.isEmpty(this.mHint)) {
                this.mOriginalHint = this.mEditText.getHint();
                setHint(this.mOriginalHint);
                this.mEditText.setHint((CharSequence) null);
            }
            if (this.mCounterView != null) {
                updateCounter(this.mEditText.getText().length());
            }
            if (this.mIndicatorArea != null) {
                adjustIndicatorPadding();
            }
            updatePasswordToggleView();
            updateLabelState(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        int i;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mInputFrame.getLayoutParams();
        if (this.mHintEnabled) {
            if (this.mTmpPaint == null) {
                this.mTmpPaint = new Paint();
            }
            this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
            this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
            i = (int) (-this.mTmpPaint.ascent());
        } else {
            i = 0;
        }
        if (i != layoutParams.topMargin) {
            layoutParams.topMargin = i;
            this.mInputFrame.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean z) {
        updateLabelState(z, false);
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean z, boolean z2) {
        ColorStateList colorStateList;
        TextView textView;
        boolean isEnabled = isEnabled();
        EditText editText = this.mEditText;
        boolean z3 = editText != null && !TextUtils.isEmpty(editText.getText());
        boolean arrayContains = arrayContains(getDrawableState(), 16842908);
        boolean isEmpty = true ^ TextUtils.isEmpty(getError());
        ColorStateList colorStateList2 = this.mDefaultTextColor;
        if (colorStateList2 != null) {
            this.mCollapsingTextHelper.setExpandedTextColor(colorStateList2);
        }
        if (isEnabled && this.mCounterOverflowed && (textView = this.mCounterView) != null) {
            this.mCollapsingTextHelper.setCollapsedTextColor(textView.getTextColors());
        } else if (!isEnabled || !arrayContains || (colorStateList = this.mFocusedTextColor) == null) {
            ColorStateList colorStateList3 = this.mDefaultTextColor;
            if (colorStateList3 != null) {
                this.mCollapsingTextHelper.setCollapsedTextColor(colorStateList3);
            }
        } else {
            this.mCollapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (z3 || (isEnabled() && (arrayContains || isEmpty))) {
            if (z2 || this.mHintExpanded) {
                collapseHint(z);
            }
        } else if (z2 || !this.mHintExpanded) {
            expandHint(z);
        }
    }

    @Nullable
    public EditText getEditText() {
        return this.mEditText;
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.mHintEnabled) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    private void setHintInternal(CharSequence charSequence) {
        this.mHint = charSequence;
        this.mCollapsingTextHelper.setText(charSequence);
    }

    @Nullable
    public CharSequence getHint() {
        if (this.mHintEnabled) {
            return this.mHint;
        }
        return null;
    }

    public void setHintEnabled(boolean z) {
        if (z != this.mHintEnabled) {
            this.mHintEnabled = z;
            CharSequence hint = this.mEditText.getHint();
            if (!this.mHintEnabled) {
                if (!TextUtils.isEmpty(this.mHint) && TextUtils.isEmpty(hint)) {
                    this.mEditText.setHint(this.mHint);
                }
                setHintInternal((CharSequence) null);
            } else if (!TextUtils.isEmpty(hint)) {
                if (TextUtils.isEmpty(this.mHint)) {
                    setHint(hint);
                }
                this.mEditText.setHint((CharSequence) null);
            }
            if (this.mEditText != null) {
                updateInputLayoutMargins();
            }
        }
    }

    public boolean isHintEnabled() {
        return this.mHintEnabled;
    }

    public void setHintTextAppearance(@StyleRes int i) {
        this.mCollapsingTextHelper.setCollapsedTextAppearance(i);
        this.mFocusedTextColor = this.mCollapsingTextHelper.getCollapsedTextColor();
        if (this.mEditText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    private void addIndicator(TextView textView, int i) {
        if (this.mIndicatorArea == null) {
            this.mIndicatorArea = new LinearLayout(getContext());
            this.mIndicatorArea.setOrientation(0);
            addView(this.mIndicatorArea, -1, -2);
            this.mIndicatorArea.addView(new Space(getContext()), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.mEditText != null) {
                adjustIndicatorPadding();
            }
        }
        this.mIndicatorArea.setVisibility(0);
        this.mIndicatorArea.addView(textView, i);
        this.mIndicatorsAdded++;
    }

    private void adjustIndicatorPadding() {
        ViewCompat.setPaddingRelative(this.mIndicatorArea, ViewCompat.getPaddingStart(this.mEditText), 0, ViewCompat.getPaddingEnd(this.mEditText), this.mEditText.getPaddingBottom());
    }

    private void removeIndicator(TextView textView) {
        LinearLayout linearLayout = this.mIndicatorArea;
        if (linearLayout != null) {
            linearLayout.removeView(textView);
            int i = this.mIndicatorsAdded - 1;
            this.mIndicatorsAdded = i;
            if (i == 0) {
                this.mIndicatorArea.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0050  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setErrorEnabled(boolean r6) {
        /*
            r5 = this;
            boolean r0 = r5.mErrorEnabled
            if (r0 == r6) goto L_0x0086
            android.widget.TextView r0 = r5.mErrorView
            if (r0 == 0) goto L_0x000f
            android.view.ViewPropertyAnimator r0 = r0.animate()
            r0.cancel()
        L_0x000f:
            r0 = 0
            if (r6 == 0) goto L_0x0077
            android.support.v7.widget.AppCompatTextView r1 = new android.support.v7.widget.AppCompatTextView
            android.content.Context r2 = r5.getContext()
            r1.<init>(r2)
            r5.mErrorView = r1
            android.widget.TextView r1 = r5.mErrorView
            int r2 = android.support.design.C0030R.C0032id.textinput_error
            r1.setId(r2)
            android.graphics.Typeface r1 = r5.mTypeface
            if (r1 == 0) goto L_0x002d
            android.widget.TextView r2 = r5.mErrorView
            r2.setTypeface(r1)
        L_0x002d:
            r1 = 1
            android.widget.TextView r2 = r5.mErrorView     // Catch:{ Exception -> 0x004d }
            int r3 = r5.mErrorTextAppearance     // Catch:{ Exception -> 0x004d }
            android.support.p000v4.widget.TextViewCompat.setTextAppearance(r2, r3)     // Catch:{ Exception -> 0x004d }
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x004d }
            r3 = 23
            if (r2 < r3) goto L_0x004b
            android.widget.TextView r2 = r5.mErrorView     // Catch:{ Exception -> 0x004d }
            android.content.res.ColorStateList r2 = r2.getTextColors()     // Catch:{ Exception -> 0x004d }
            int r2 = r2.getDefaultColor()     // Catch:{ Exception -> 0x004d }
            r3 = -65281(0xffffffffffff00ff, float:NaN)
            if (r2 != r3) goto L_0x004b
            goto L_0x004d
        L_0x004b:
            r2 = 0
            goto L_0x004e
        L_0x004d:
            r2 = 1
        L_0x004e:
            if (r2 == 0) goto L_0x0066
            android.widget.TextView r2 = r5.mErrorView
            int r3 = android.support.p003v7.appcompat.C0359R.style.TextAppearance_AppCompat_Caption
            android.support.p000v4.widget.TextViewCompat.setTextAppearance(r2, r3)
            android.widget.TextView r2 = r5.mErrorView
            android.content.Context r3 = r5.getContext()
            int r4 = android.support.p003v7.appcompat.C0359R.color.error_color_material
            int r3 = android.support.p000v4.content.ContextCompat.getColor(r3, r4)
            r2.setTextColor(r3)
        L_0x0066:
            android.widget.TextView r2 = r5.mErrorView
            r3 = 4
            r2.setVisibility(r3)
            android.widget.TextView r2 = r5.mErrorView
            android.support.p000v4.view.ViewCompat.setAccessibilityLiveRegion(r2, r1)
            android.widget.TextView r1 = r5.mErrorView
            r5.addIndicator(r1, r0)
            goto L_0x0084
        L_0x0077:
            r5.mErrorShown = r0
            r5.updateEditTextBackground()
            android.widget.TextView r0 = r5.mErrorView
            r5.removeIndicator(r0)
            r0 = 0
            r5.mErrorView = r0
        L_0x0084:
            r5.mErrorEnabled = r6
        L_0x0086:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TextInputLayout.setErrorEnabled(boolean):void");
    }

    public void setErrorTextAppearance(@StyleRes int i) {
        this.mErrorTextAppearance = i;
        TextView textView = this.mErrorView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }

    public boolean isErrorEnabled() {
        return this.mErrorEnabled;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
        r0 = r1.mErrorView;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setError(@android.support.annotation.Nullable java.lang.CharSequence r2) {
        /*
            r1 = this;
            boolean r0 = android.support.p000v4.view.ViewCompat.isLaidOut(r1)
            if (r0 == 0) goto L_0x001c
            boolean r0 = r1.isEnabled()
            if (r0 == 0) goto L_0x001c
            android.widget.TextView r0 = r1.mErrorView
            if (r0 == 0) goto L_0x001a
            java.lang.CharSequence r0 = r0.getText()
            boolean r0 = android.text.TextUtils.equals(r0, r2)
            if (r0 != 0) goto L_0x001c
        L_0x001a:
            r0 = 1
            goto L_0x001d
        L_0x001c:
            r0 = 0
        L_0x001d:
            r1.setError(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.TextInputLayout.setError(java.lang.CharSequence):void");
    }

    private void setError(@Nullable final CharSequence charSequence, boolean z) {
        this.mError = charSequence;
        if (!this.mErrorEnabled) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        this.mErrorShown = !TextUtils.isEmpty(charSequence);
        this.mErrorView.animate().cancel();
        if (this.mErrorShown) {
            this.mErrorView.setText(charSequence);
            this.mErrorView.setVisibility(0);
            if (z) {
                if (this.mErrorView.getAlpha() == 1.0f) {
                    this.mErrorView.setAlpha(0.0f);
                }
                this.mErrorView.animate().alpha(1.0f).setDuration(200).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationStart(Animator animator) {
                        TextInputLayout.this.mErrorView.setVisibility(0);
                    }
                }).start();
            } else {
                this.mErrorView.setAlpha(1.0f);
            }
        } else if (this.mErrorView.getVisibility() == 0) {
            if (z) {
                this.mErrorView.animate().alpha(0.0f).setDuration(200).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        TextInputLayout.this.mErrorView.setText(charSequence);
                        TextInputLayout.this.mErrorView.setVisibility(4);
                    }
                }).start();
            } else {
                this.mErrorView.setText(charSequence);
                this.mErrorView.setVisibility(4);
            }
        }
        updateEditTextBackground();
        updateLabelState(z);
    }

    public void setCounterEnabled(boolean z) {
        if (this.mCounterEnabled != z) {
            if (z) {
                this.mCounterView = new AppCompatTextView(getContext());
                this.mCounterView.setId(C0030R.C0032id.textinput_counter);
                Typeface typeface = this.mTypeface;
                if (typeface != null) {
                    this.mCounterView.setTypeface(typeface);
                }
                this.mCounterView.setMaxLines(1);
                try {
                    TextViewCompat.setTextAppearance(this.mCounterView, this.mCounterTextAppearance);
                } catch (Exception unused) {
                    TextViewCompat.setTextAppearance(this.mCounterView, C0359R.style.TextAppearance_AppCompat_Caption);
                    this.mCounterView.setTextColor(ContextCompat.getColor(getContext(), C0359R.color.error_color_material));
                }
                addIndicator(this.mCounterView, -1);
                EditText editText = this.mEditText;
                if (editText == null) {
                    updateCounter(0);
                } else {
                    updateCounter(editText.getText().length());
                }
            } else {
                removeIndicator(this.mCounterView);
                this.mCounterView = null;
            }
            this.mCounterEnabled = z;
        }
    }

    public boolean isCounterEnabled() {
        return this.mCounterEnabled;
    }

    public void setCounterMaxLength(int i) {
        if (this.mCounterMaxLength != i) {
            if (i > 0) {
                this.mCounterMaxLength = i;
            } else {
                this.mCounterMaxLength = -1;
            }
            if (this.mCounterEnabled) {
                EditText editText = this.mEditText;
                updateCounter(editText == null ? 0 : editText.getText().length());
            }
        }
    }

    public void setEnabled(boolean z) {
        recursiveSetEnabled(this, z);
        super.setEnabled(z);
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) childAt, z);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.mCounterMaxLength;
    }

    /* access modifiers changed from: package-private */
    public void updateCounter(int i) {
        boolean z = this.mCounterOverflowed;
        int i2 = this.mCounterMaxLength;
        if (i2 == -1) {
            this.mCounterView.setText(String.valueOf(i));
            this.mCounterOverflowed = false;
        } else {
            this.mCounterOverflowed = i > i2;
            boolean z2 = this.mCounterOverflowed;
            if (z != z2) {
                TextViewCompat.setTextAppearance(this.mCounterView, z2 ? this.mCounterOverflowTextAppearance : this.mCounterTextAppearance);
            }
            this.mCounterView.setText(getContext().getString(C0030R.string.character_counter_pattern, new Object[]{Integer.valueOf(i), Integer.valueOf(this.mCounterMaxLength)}));
        }
        if (this.mEditText != null && z != this.mCounterOverflowed) {
            updateLabelState(false);
            updateEditTextBackground();
        }
    }

    private void updateEditTextBackground() {
        Drawable background;
        TextView textView;
        TextView textView2;
        EditText editText = this.mEditText;
        if (editText != null && (background = editText.getBackground()) != null) {
            ensureBackgroundDrawableStateWorkaround();
            if (DrawableUtils.canSafelyMutateDrawable(background)) {
                background = background.mutate();
            }
            if (this.mErrorShown && (textView2 = this.mErrorView) != null) {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView2.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            } else if (!this.mCounterOverflowed || (textView = this.mCounterView) == null) {
                DrawableCompat.clearColorFilter(background);
                this.mEditText.refreshDrawableState();
            } else {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    private void ensureBackgroundDrawableStateWorkaround() {
        Drawable background;
        int i = Build.VERSION.SDK_INT;
        if ((i == 21 || i == 22) && (background = this.mEditText.getBackground()) != null && !this.mHasReconstructedEditTextBackground) {
            Drawable newDrawable = background.getConstantState().newDrawable();
            if (background instanceof DrawableContainer) {
                this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer) background, newDrawable.getConstantState());
            }
            if (!this.mHasReconstructedEditTextBackground) {
                ViewCompat.setBackground(this.mEditText, newDrawable);
                this.mHasReconstructedEditTextBackground = true;
            }
        }
    }

    static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        CharSequence error;
        boolean isPasswordToggledVisible;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.isPasswordToggledVisible = parcel.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.error, parcel, i);
            parcel.writeInt(this.isPasswordToggledVisible ? 1 : 0);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.mErrorShown) {
            savedState.error = getError();
        }
        savedState.isPasswordToggledVisible = this.mPasswordToggledVisible;
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.error);
        if (savedState.isPasswordToggledVisible) {
            passwordVisibilityToggleRequested(true);
        }
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.mRestoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.mRestoringSavedState = false;
    }

    @Nullable
    public CharSequence getError() {
        if (this.mErrorEnabled) {
            return this.mError;
        }
        return null;
    }

    public boolean isHintAnimationEnabled() {
        return this.mHintAnimationEnabled;
    }

    public void setHintAnimationEnabled(boolean z) {
        this.mHintAnimationEnabled = z;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mHintEnabled) {
            this.mCollapsingTextHelper.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        updatePasswordToggleView();
        super.onMeasure(i, i2);
    }

    private void updatePasswordToggleView() {
        if (this.mEditText != null) {
            if (shouldShowPasswordIcon()) {
                if (this.mPasswordToggleView == null) {
                    this.mPasswordToggleView = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(C0030R.C0033layout.design_text_input_password_icon, this.mInputFrame, false);
                    this.mPasswordToggleView.setImageDrawable(this.mPasswordToggleDrawable);
                    this.mPasswordToggleView.setContentDescription(this.mPasswordToggleContentDesc);
                    this.mInputFrame.addView(this.mPasswordToggleView);
                    this.mPasswordToggleView.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextInputLayout.this.passwordVisibilityToggleRequested(false);
                        }
                    });
                }
                EditText editText = this.mEditText;
                if (editText != null && ViewCompat.getMinimumHeight(editText) <= 0) {
                    this.mEditText.setMinimumHeight(ViewCompat.getMinimumHeight(this.mPasswordToggleView));
                }
                this.mPasswordToggleView.setVisibility(0);
                this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
                if (this.mPasswordToggleDummyDrawable == null) {
                    this.mPasswordToggleDummyDrawable = new ColorDrawable();
                }
                this.mPasswordToggleDummyDrawable.setBounds(0, 0, this.mPasswordToggleView.getMeasuredWidth(), 1);
                Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.mEditText);
                if (compoundDrawablesRelative[2] != this.mPasswordToggleDummyDrawable) {
                    this.mOriginalEditTextEndDrawable = compoundDrawablesRelative[2];
                }
                TextViewCompat.setCompoundDrawablesRelative(this.mEditText, compoundDrawablesRelative[0], compoundDrawablesRelative[1], this.mPasswordToggleDummyDrawable, compoundDrawablesRelative[3]);
                this.mPasswordToggleView.setPadding(this.mEditText.getPaddingLeft(), this.mEditText.getPaddingTop(), this.mEditText.getPaddingRight(), this.mEditText.getPaddingBottom());
                return;
            }
            CheckableImageButton checkableImageButton = this.mPasswordToggleView;
            if (checkableImageButton != null && checkableImageButton.getVisibility() == 0) {
                this.mPasswordToggleView.setVisibility(8);
            }
            if (this.mPasswordToggleDummyDrawable != null) {
                Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.mEditText);
                if (compoundDrawablesRelative2[2] == this.mPasswordToggleDummyDrawable) {
                    TextViewCompat.setCompoundDrawablesRelative(this.mEditText, compoundDrawablesRelative2[0], compoundDrawablesRelative2[1], this.mOriginalEditTextEndDrawable, compoundDrawablesRelative2[3]);
                    this.mPasswordToggleDummyDrawable = null;
                }
            }
        }
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i) {
        setPasswordVisibilityToggleDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.mPasswordToggleDrawable = drawable;
        CheckableImageButton checkableImageButton = this.mPasswordToggleView;
        if (checkableImageButton != null) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int i) {
        setPasswordVisibilityToggleContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.mPasswordToggleContentDesc = charSequence;
        CheckableImageButton checkableImageButton = this.mPasswordToggleView;
        if (checkableImageButton != null) {
            checkableImageButton.setContentDescription(charSequence);
        }
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.mPasswordToggleDrawable;
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.mPasswordToggleContentDesc;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.mPasswordToggleEnabled;
    }

    public void setPasswordVisibilityToggleEnabled(boolean z) {
        EditText editText;
        if (this.mPasswordToggleEnabled != z) {
            this.mPasswordToggleEnabled = z;
            if (!z && this.mPasswordToggledVisible && (editText = this.mEditText) != null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.mPasswordToggledVisible = false;
            updatePasswordToggleView();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.mPasswordToggleTintList = colorStateList;
        this.mHasPasswordToggleTintList = true;
        applyPasswordToggleTint();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.mPasswordToggleTintMode = mode;
        this.mHasPasswordToggleTintMode = true;
        applyPasswordToggleTint();
    }

    /* access modifiers changed from: private */
    public void passwordVisibilityToggleRequested(boolean z) {
        if (this.mPasswordToggleEnabled) {
            int selectionEnd = this.mEditText.getSelectionEnd();
            if (hasPasswordTransformation()) {
                this.mEditText.setTransformationMethod((TransformationMethod) null);
                this.mPasswordToggledVisible = true;
            } else {
                this.mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.mPasswordToggledVisible = false;
            }
            this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
            if (z) {
                this.mPasswordToggleView.jumpDrawablesToCurrentState();
            }
            this.mEditText.setSelection(selectionEnd);
        }
    }

    private boolean hasPasswordTransformation() {
        EditText editText = this.mEditText;
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private boolean shouldShowPasswordIcon() {
        return this.mPasswordToggleEnabled && (hasPasswordTransformation() || this.mPasswordToggledVisible);
    }

    private void applyPasswordToggleTint() {
        Drawable drawable;
        if (this.mPasswordToggleDrawable == null) {
            return;
        }
        if (this.mHasPasswordToggleTintList || this.mHasPasswordToggleTintMode) {
            this.mPasswordToggleDrawable = DrawableCompat.wrap(this.mPasswordToggleDrawable).mutate();
            if (this.mHasPasswordToggleTintList) {
                DrawableCompat.setTintList(this.mPasswordToggleDrawable, this.mPasswordToggleTintList);
            }
            if (this.mHasPasswordToggleTintMode) {
                DrawableCompat.setTintMode(this.mPasswordToggleDrawable, this.mPasswordToggleTintMode);
            }
            CheckableImageButton checkableImageButton = this.mPasswordToggleView;
            if (checkableImageButton != null && checkableImageButton.getDrawable() != (drawable = this.mPasswordToggleDrawable)) {
                this.mPasswordToggleView.setImageDrawable(drawable);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        EditText editText;
        super.onLayout(z, i, i2, i3, i4);
        if (this.mHintEnabled && (editText = this.mEditText) != null) {
            Rect rect = this.mTmpRect;
            ViewGroupUtils.getDescendantRect(this, editText, rect);
            int compoundPaddingLeft = rect.left + this.mEditText.getCompoundPaddingLeft();
            int compoundPaddingRight = rect.right - this.mEditText.getCompoundPaddingRight();
            this.mCollapsingTextHelper.setExpandedBounds(compoundPaddingLeft, rect.top + this.mEditText.getCompoundPaddingTop(), compoundPaddingRight, rect.bottom - this.mEditText.getCompoundPaddingBottom());
            this.mCollapsingTextHelper.setCollapsedBounds(compoundPaddingLeft, getPaddingTop(), compoundPaddingRight, (i4 - i2) - getPaddingBottom());
            this.mCollapsingTextHelper.recalculate();
        }
    }

    private void collapseHint(boolean z) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (!z || !this.mHintAnimationEnabled) {
            this.mCollapsingTextHelper.setExpansionFraction(1.0f);
        } else {
            animateToExpansionFraction(1.0f);
        }
        this.mHintExpanded = false;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.mInDrawableStateChanged) {
            boolean z = true;
            this.mInDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            if (!ViewCompat.isLaidOut(this) || !isEnabled()) {
                z = false;
            }
            updateLabelState(z);
            updateEditTextBackground();
            CollapsingTextHelper collapsingTextHelper = this.mCollapsingTextHelper;
            if (collapsingTextHelper != null ? collapsingTextHelper.setState(drawableState) | false : false) {
                invalidate();
            }
            this.mInDrawableStateChanged = false;
        }
    }

    private void expandHint(boolean z) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        if (!z || !this.mHintAnimationEnabled) {
            this.mCollapsingTextHelper.setExpansionFraction(0.0f);
        } else {
            animateToExpansionFraction(0.0f);
        }
        this.mHintExpanded = true;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void animateToExpansionFraction(float f) {
        if (this.mCollapsingTextHelper.getExpansionFraction() != f) {
            if (this.mAnimator == null) {
                this.mAnimator = new ValueAnimator();
                this.mAnimator.setInterpolator(AnimationUtils.LINEAR_INTERPOLATOR);
                this.mAnimator.setDuration(200);
                this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.mAnimator.setFloatValues(new float[]{this.mCollapsingTextHelper.getExpansionFraction(), f});
            this.mAnimator.start();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public final boolean isHintExpanded() {
        return this.mHintExpanded;
    }

    private class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
        TextInputAccessibilityDelegate() {
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                accessibilityEvent.getText().add(text);
            }
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence text = TextInputLayout.this.mCollapsingTextHelper.getText();
            if (!TextUtils.isEmpty(text)) {
                accessibilityNodeInfoCompat.setText(text);
            }
            if (TextInputLayout.this.mEditText != null) {
                accessibilityNodeInfoCompat.setLabelFor(TextInputLayout.this.mEditText);
            }
            CharSequence text2 = TextInputLayout.this.mErrorView != null ? TextInputLayout.this.mErrorView.getText() : null;
            if (!TextUtils.isEmpty(text2)) {
                accessibilityNodeInfoCompat.setContentInvalid(true);
                accessibilityNodeInfoCompat.setError(text2);
            }
        }
    }

    private static boolean arrayContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
