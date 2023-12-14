package com.qmuiteam.qmui.qqface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.QMUILog;
import com.qmuiteam.qmui.link.ITouchableSpan;
import com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class QMUIQQFaceView extends View {
    private static final String TAG = "QMUIQQFaceView";
    private static final String mEllipsizeText = "...";
    private QMUIQQFaceCompiler mCompiler;
    private int mContentCalMaxWidth;
    private int mCurrentCalLine;
    private int mCurrentCalWidth;
    private int mCurrentDrawBaseLine;
    private int mCurrentDrawLine;
    private QMUITouchableSpan mCurrentDrawSpan;
    private int mCurrentDrawUsedWidth;
    private Runnable mDelayTextSetter;
    private QMUIQQFaceCompiler.ElementList mElementList;
    private TextUtils.TruncateAt mEllipsize;
    private int mEllipsizeTextLength;
    private int mFirstBaseLine;
    /* access modifiers changed from: private */
    public int mFontHeight;
    private boolean mIncludePad;
    private boolean mIsExecutedMiddleEllipsize;
    private boolean mIsInDrawSpan;
    private boolean mIsNeedEllipsize;
    private boolean mIsSingleLine;
    private boolean mJumpHandleMeasureAndDraw;
    private int mLastCalContentWidth;
    private int mLastCalLimitWidth;
    private int mLastCalLines;
    private int mLastNeedStopLineRecord;
    /* access modifiers changed from: private */
    public int mLineSpace;
    private int mLines;
    private QQFaceViewListener mListener;
    private int mMaxLine;
    private int mMaxWidth;
    private int mMiddleEllipsizeWidthRecord;
    private int mMoreActionColor;
    private String mMoreActionText;
    private int mMoreActionTextLength;
    private int mNeedDrawLine;
    private boolean mNeedReCalculateLines;
    private boolean mOpenQQFace;
    private CharSequence mOriginText;
    private TextPaint mPaint;
    private int mParagraphSpace;
    /* access modifiers changed from: private */
    public PressCancelAction mPendingPressCancelAction;
    private int mQQFaceSize;
    private int mQQFaceSizeAddon;
    private Paint mSpanBgPaint;
    private Set<SpanInfo> mSpanInfos;
    private int mSpecialDrawablePadding;
    private int mTextColor;
    private int mTextSize;
    SpanInfo mTouchSpanInfo;
    private Typeface mTypeface;
    private boolean needReCalculateFontHeight;

    public interface QQFaceViewListener {
        void onCalculateLinesChange(int i);
    }

    public QMUIQQFaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public QMUIQQFaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUIQQFaceStyle);
    }

    public QMUIQQFaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOpenQQFace = true;
        this.mLineSpace = -1;
        this.mQQFaceSize = 0;
        this.mMaxLine = Integer.MAX_VALUE;
        this.mIsSingleLine = false;
        this.mLines = 0;
        this.mSpanInfos = new HashSet();
        this.mMoreActionTextLength = 0;
        this.mEllipsizeTextLength = 0;
        this.mEllipsize = TextUtils.TruncateAt.END;
        this.mIsNeedEllipsize = false;
        this.mNeedDrawLine = 0;
        this.mQQFaceSizeAddon = 0;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mPendingPressCancelAction = null;
        this.mJumpHandleMeasureAndDraw = false;
        this.mDelayTextSetter = null;
        this.mIncludePad = true;
        this.mTypeface = null;
        this.mParagraphSpace = 0;
        this.mSpecialDrawablePadding = 0;
        this.mTouchSpanInfo = null;
        this.needReCalculateFontHeight = true;
        this.mCurrentCalWidth = 0;
        this.mCurrentCalLine = 0;
        this.mContentCalMaxWidth = 0;
        this.mNeedReCalculateLines = false;
        this.mLastCalLimitWidth = 0;
        this.mLastCalContentWidth = 0;
        this.mLastCalLines = 0;
        this.mIsInDrawSpan = false;
        this.mMiddleEllipsizeWidthRecord = -1;
        this.mIsExecutedMiddleEllipsize = false;
        this.mLastNeedStopLineRecord = -1;
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIQQFaceView, i, 0);
        this.mQQFaceSizeAddon = -QMUIDisplayHelper.dp2px(context, 2);
        this.mTextSize = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIQQFaceView_android_textSize, QMUIDisplayHelper.dp2px(context, 14));
        this.mTextColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIQQFaceView_android_textColor, ViewCompat.MEASURED_STATE_MASK);
        this.mIsSingleLine = obtainStyledAttributes.getBoolean(C1614R.styleable.QMUIQQFaceView_android_singleLine, false);
        this.mMaxLine = obtainStyledAttributes.getInt(C1614R.styleable.QMUIQQFaceView_android_maxLines, this.mMaxLine);
        setLineSpace(obtainStyledAttributes.getDimensionPixelOffset(C1614R.styleable.QMUIQQFaceView_android_lineSpacingExtra, 0));
        int i2 = obtainStyledAttributes.getInt(C1614R.styleable.QMUIQQFaceView_android_ellipsize, -1);
        if (i2 == 1) {
            this.mEllipsize = TextUtils.TruncateAt.START;
        } else if (i2 != 2) {
            this.mEllipsize = TextUtils.TruncateAt.END;
        } else {
            this.mEllipsize = TextUtils.TruncateAt.MIDDLE;
        }
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIQQFaceView_android_maxWidth, this.mMaxWidth);
        this.mSpecialDrawablePadding = obtainStyledAttributes.getDimensionPixelSize(C1614R.styleable.QMUIQQFaceView_qmui_special_drawable_padding, 0);
        final String string = obtainStyledAttributes.getString(C1614R.styleable.QMUIQQFaceView_android_text);
        if (!QMUILangHelper.isNullOrEmpty(string)) {
            this.mDelayTextSetter = new Runnable() {
                public void run() {
                    QMUIQQFaceView.this.setText(string);
                }
            };
        }
        this.mMoreActionText = obtainStyledAttributes.getString(C1614R.styleable.QMUIQQFaceView_qmui_more_action_text);
        this.mMoreActionColor = obtainStyledAttributes.getColor(C1614R.styleable.QMUIQQFaceView_qmui_more_action_color, this.mTextColor);
        obtainStyledAttributes.recycle();
        this.mPaint = new TextPaint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize((float) this.mTextSize);
        this.mPaint.setColor(this.mTextColor);
        this.mEllipsizeTextLength = (int) Math.ceil((double) this.mPaint.measureText(mEllipsizeText));
        measureMoreActionTextLength();
        this.mSpanBgPaint = new Paint();
        this.mSpanBgPaint.setAntiAlias(true);
        this.mSpanBgPaint.setStyle(Paint.Style.FILL);
    }

    public void setOpenQQFace(boolean z) {
        this.mOpenQQFace = z;
    }

    public void setMaxWidth(int i) {
        if (this.mMaxWidth != i) {
            this.mMaxWidth = i;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (this.mSpanInfos.isEmpty()) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (this.mTouchSpanInfo == null && action != 0) {
            return super.onTouchEvent(motionEvent);
        }
        PressCancelAction pressCancelAction = this.mPendingPressCancelAction;
        if (pressCancelAction != null) {
            pressCancelAction.run();
            this.mPendingPressCancelAction = null;
        }
        if (action == 0) {
            this.mTouchSpanInfo = null;
            Iterator<SpanInfo> it = this.mSpanInfos.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SpanInfo next = it.next();
                if (next.onTouch(x, y)) {
                    this.mTouchSpanInfo = next;
                    break;
                }
            }
            SpanInfo spanInfo = this.mTouchSpanInfo;
            if (spanInfo == null) {
                return super.onTouchEvent(motionEvent);
            }
            spanInfo.setPressed(true);
            this.mTouchSpanInfo.invalidateSpan();
        } else if (action == 1) {
            this.mTouchSpanInfo.onClick();
            this.mPendingPressCancelAction = new PressCancelAction(this.mTouchSpanInfo);
            postDelayed(new Runnable() {
                public void run() {
                    if (QMUIQQFaceView.this.mPendingPressCancelAction != null) {
                        QMUIQQFaceView.this.mPendingPressCancelAction.run();
                    }
                }
            }, 100);
        } else if (action != 2) {
            if (action == 3) {
                this.mPendingPressCancelAction = null;
                this.mTouchSpanInfo.setPressed(false);
                this.mTouchSpanInfo.invalidateSpan();
            }
        } else if (!this.mTouchSpanInfo.onTouch(x, y)) {
            this.mTouchSpanInfo.setPressed(false);
            this.mTouchSpanInfo.invalidateSpan();
            this.mTouchSpanInfo = null;
        }
        return true;
    }

    public void setCompiler(QMUIQQFaceCompiler qMUIQQFaceCompiler) {
        this.mCompiler = qMUIQQFaceCompiler;
        Runnable runnable = this.mDelayTextSetter;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setTypeface(Typeface typeface) {
        if (this.mTypeface != typeface) {
            this.mTypeface = typeface;
            this.needReCalculateFontHeight = true;
            this.mPaint.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    public void setTypeface(Typeface typeface, int i) {
        Typeface typeface2;
        float f = 0.0f;
        boolean z = false;
        if (i > 0) {
            if (typeface == null) {
                typeface2 = Typeface.defaultFromStyle(i);
            } else {
                typeface2 = Typeface.create(typeface, i);
            }
            setTypeface(typeface2);
            int style = ((typeface2 != null ? typeface2.getStyle() : 0) ^ -1) & i;
            TextPaint textPaint = this.mPaint;
            if ((style & 1) != 0) {
                z = true;
            }
            textPaint.setFakeBoldText(z);
            TextPaint textPaint2 = this.mPaint;
            if ((style & 2) != 0) {
                f = -0.25f;
            }
            textPaint2.setTextSkewX(f);
            return;
        }
        this.mPaint.setFakeBoldText(false);
        this.mPaint.setTextSkewX(0.0f);
        setTypeface(typeface);
    }

    public void setParagraphSpace(int i) {
        if (this.mParagraphSpace != i) {
            this.mParagraphSpace = i;
            requestLayout();
            invalidate();
        }
    }

    public void setMoreActionText(String str) {
        String str2 = this.mMoreActionText;
        if (str2 == null || !str2.equals(str)) {
            this.mMoreActionText = str;
            measureMoreActionTextLength();
            requestLayout();
            invalidate();
        }
    }

    public void setMoreActionColor(int i) {
        if (i != this.mMoreActionColor) {
            this.mMoreActionColor = i;
            invalidate();
        }
    }

    private void measureMoreActionTextLength() {
        if (QMUILangHelper.isNullOrEmpty(this.mMoreActionText)) {
            this.mMoreActionTextLength = 0;
        } else {
            this.mMoreActionTextLength = (int) Math.ceil((double) this.mPaint.measureText(this.mMoreActionText));
        }
    }

    public void setSpecialDrawablePadding(int i) {
        if (this.mSpecialDrawablePadding != i) {
            this.mSpecialDrawablePadding = i;
            requestLayout();
            invalidate();
        }
    }

    public void setIncludeFontPadding(boolean z) {
        if (this.mIncludePad != z) {
            this.needReCalculateFontHeight = true;
            this.mIncludePad = z;
            requestLayout();
            invalidate();
        }
    }

    public void setQQFaceSizeAddon(int i) {
        if (this.mQQFaceSizeAddon != i) {
            this.mQQFaceSizeAddon = i;
            this.mNeedReCalculateLines = true;
            requestLayout();
            invalidate();
        }
    }

    public void setLineSpace(int i) {
        if (this.mLineSpace != i) {
            this.mLineSpace = i;
            requestLayout();
            invalidate();
        }
    }

    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
        if (this.mEllipsize != truncateAt) {
            this.mEllipsize = truncateAt;
            requestLayout();
            invalidate();
        }
    }

    public void setMaxLine(int i) {
        if (this.mMaxLine != i) {
            this.mMaxLine = i;
            requestLayout();
            invalidate();
        }
    }

    public int getMaxLine() {
        return this.mMaxLine;
    }

    public int getLineCount() {
        return this.mLines;
    }

    public void setSingleLine(boolean z) {
        if (this.mIsSingleLine != z) {
            this.mIsSingleLine = z;
            requestLayout();
            invalidate();
        }
    }

    public void setTextColor(@ColorInt int i) {
        if (this.mTextColor != i) {
            this.mTextColor = i;
            this.mPaint.setColor(i);
            invalidate();
        }
    }

    public TextPaint getPaint() {
        return this.mPaint;
    }

    public void setTextSize(int i) {
        if (this.mTextSize != i) {
            this.mTextSize = i;
            this.mPaint.setTextSize((float) this.mTextSize);
            this.needReCalculateFontHeight = true;
            this.mNeedReCalculateLines = true;
            this.mEllipsizeTextLength = (int) Math.ceil((double) this.mPaint.measureText(mEllipsizeText));
            measureMoreActionTextLength();
            requestLayout();
            invalidate();
        }
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public CharSequence getText() {
        return this.mOriginText;
    }

    public void setText(CharSequence charSequence) {
        QMUIQQFaceCompiler qMUIQQFaceCompiler;
        this.mDelayTextSetter = null;
        CharSequence charSequence2 = this.mOriginText;
        if (charSequence2 == null || !charSequence2.equals(charSequence)) {
            this.mOriginText = charSequence;
            if (this.mOpenQQFace && this.mCompiler == null) {
                throw new RuntimeException("mCompiler == null");
            } else if (!QMUILangHelper.isNullOrEmpty(this.mOriginText)) {
                if (!this.mOpenQQFace || (qMUIQQFaceCompiler = this.mCompiler) == null) {
                    this.mElementList = new QMUIQQFaceCompiler.ElementList(0, this.mOriginText.length());
                    String[] split = this.mOriginText.toString().split("\\n");
                    for (int i = 0; i < split.length; i++) {
                        this.mElementList.add(QMUIQQFaceCompiler.Element.createTextElement(split[i]));
                        if (i != split.length - 1) {
                            this.mElementList.add(QMUIQQFaceCompiler.Element.createNextLineElement());
                        }
                    }
                } else {
                    this.mElementList = qMUIQQFaceCompiler.compile(this.mOriginText);
                }
                this.mNeedReCalculateLines = true;
                int paddingLeft = getPaddingLeft() + getPaddingRight();
                if (getLayoutParams() != null) {
                    if (getLayoutParams().width == -2) {
                        requestLayout();
                        invalidate();
                    } else if (getWidth() > paddingLeft) {
                        this.mLines = 0;
                        calculateLinesAndContentWidth(getWidth());
                        int i2 = this.mNeedDrawLine;
                        calculateNeedDrawLine();
                        if (i2 == this.mNeedDrawLine || getLayoutParams().height != -2) {
                            invalidate();
                            return;
                        }
                        requestLayout();
                        invalidate();
                    }
                }
            } else if (!QMUILangHelper.isNullOrEmpty(charSequence2)) {
                this.mElementList = null;
                requestLayout();
                invalidate();
            }
        }
    }

    private void calculateFontHeight() {
        if (this.needReCalculateFontHeight) {
            Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
            if (fontMetricsInt == null) {
                this.mQQFaceSize = 0;
                this.mFontHeight = 0;
                return;
            }
            this.needReCalculateFontHeight = false;
            int fontHeightCalTop = getFontHeightCalTop(fontMetricsInt, this.mIncludePad);
            int fontHeightCalBottom = getFontHeightCalBottom(fontMetricsInt, this.mIncludePad) - fontHeightCalTop;
            this.mQQFaceSize = this.mQQFaceSizeAddon + fontHeightCalBottom;
            int max = Math.max(this.mQQFaceSize, this.mCompiler.getSpecialBoundsMaxHeight());
            if (fontHeightCalBottom >= max) {
                this.mFontHeight = fontHeightCalBottom;
                this.mFirstBaseLine = -fontHeightCalTop;
                return;
            }
            this.mFontHeight = max;
            this.mFirstBaseLine = (-fontHeightCalTop) + ((this.mFontHeight - max) / 2);
        }
    }

    /* access modifiers changed from: protected */
    public int getFontHeightCalTop(Paint.FontMetricsInt fontMetricsInt, boolean z) {
        return z ? fontMetricsInt.top : fontMetricsInt.ascent;
    }

    /* access modifiers changed from: protected */
    public int getFontHeightCalBottom(Paint.FontMetricsInt fontMetricsInt, boolean z) {
        return z ? fontMetricsInt.bottom : fontMetricsInt.descent;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        if (!(getPaddingLeft() == i && getPaddingRight() == i3)) {
            this.mNeedReCalculateLines = true;
        }
        super.setPadding(i, i2, i3, i4);
    }

    private int calculateLinesAndContentWidth(int i) {
        if (i <= getPaddingRight() + getPaddingLeft() || isElementEmpty()) {
            this.mLines = 0;
            this.mLastCalLines = 0;
            this.mLastCalContentWidth = 0;
            return this.mLastCalContentWidth;
        } else if (this.mNeedReCalculateLines || this.mLastCalLimitWidth != i) {
            this.mLastCalLimitWidth = i;
            List<QMUIQQFaceCompiler.Element> elements = this.mElementList.getElements();
            this.mSpanInfos.clear();
            this.mCurrentCalLine = 1;
            this.mCurrentCalWidth = getPaddingLeft();
            calculateLinesInner(elements, i);
            int i2 = this.mCurrentCalLine;
            if (i2 != this.mLines) {
                QQFaceViewListener qQFaceViewListener = this.mListener;
                if (qQFaceViewListener != null) {
                    qQFaceViewListener.onCalculateLinesChange(i2);
                }
                this.mLines = this.mCurrentCalLine;
            }
            if (this.mLines == 1) {
                this.mLastCalContentWidth = this.mCurrentCalWidth + getPaddingRight();
            } else {
                this.mLastCalContentWidth = i;
            }
            this.mLastCalLines = this.mLines;
            return this.mLastCalContentWidth;
        } else {
            this.mLines = this.mLastCalLines;
            return this.mLastCalContentWidth;
        }
    }

    private void calculateNeedDrawLine() {
        int i = this.mLines;
        this.mNeedDrawLine = i;
        boolean z = true;
        if (this.mIsSingleLine) {
            this.mNeedDrawLine = Math.min(1, i);
        } else {
            int i2 = this.mMaxLine;
            if (i2 < i) {
                this.mNeedDrawLine = i2;
            }
        }
        if (this.mLines <= this.mNeedDrawLine) {
            z = false;
        }
        this.mIsNeedEllipsize = z;
    }

    private void calculateLinesInner(List<QMUIQQFaceCompiler.Element> list, int i) {
        int i2;
        int paddingLeft = getPaddingLeft();
        int paddingRight = i - getPaddingRight();
        int i3 = 0;
        while (i3 < list.size() && !this.mJumpHandleMeasureAndDraw) {
            if (this.mCurrentCalLine <= this.mMaxLine || this.mEllipsize != TextUtils.TruncateAt.END || Build.VERSION.SDK_INT >= 21) {
                QMUIQQFaceCompiler.Element element = list.get(i3);
                if (element.getType() == QMUIQQFaceCompiler.ElementType.DRAWABLE) {
                    int i4 = this.mCurrentCalWidth;
                    int i5 = this.mQQFaceSize;
                    if (i4 + i5 > paddingRight) {
                        gotoCalNextLine(paddingLeft);
                        this.mCurrentCalWidth += this.mQQFaceSize;
                    } else if (i4 + i5 == paddingRight) {
                        gotoCalNextLine(paddingLeft);
                    } else {
                        this.mCurrentCalWidth = i4 + i5;
                    }
                    if (paddingRight - paddingLeft < this.mQQFaceSize) {
                        this.mJumpHandleMeasureAndDraw = true;
                    }
                } else if (element.getType() == QMUIQQFaceCompiler.ElementType.TEXT) {
                    measureText(element.getText(), paddingLeft, paddingRight);
                } else if (element.getType() == QMUIQQFaceCompiler.ElementType.SPAN) {
                    QMUIQQFaceCompiler.ElementList childList = element.getChildList();
                    QMUITouchableSpan touchableSpan = element.getTouchableSpan();
                    if (childList != null && childList.getElements().size() > 0) {
                        if (touchableSpan == null) {
                            calculateLinesInner(childList.getElements(), i);
                        } else {
                            SpanInfo spanInfo = new SpanInfo(touchableSpan);
                            spanInfo.setStart(this.mCurrentCalLine, this.mCurrentCalWidth);
                            calculateLinesInner(childList.getElements(), i);
                            spanInfo.setEnd(this.mCurrentCalLine, this.mCurrentCalWidth);
                            this.mSpanInfos.add(spanInfo);
                        }
                    }
                } else if (element.getType() == QMUIQQFaceCompiler.ElementType.NEXTLINE) {
                    gotoCalNextLine(paddingLeft);
                } else if (element.getType() == QMUIQQFaceCompiler.ElementType.SPECIAL_BOUNDS_DRAWABLE) {
                    int intrinsicWidth = element.getSpecialBoundsDrawable().getIntrinsicWidth();
                    if (i3 == 0 || i3 == list.size() - 1) {
                        i2 = this.mSpecialDrawablePadding;
                    } else {
                        i2 = this.mSpecialDrawablePadding * 2;
                    }
                    int i6 = intrinsicWidth + i2;
                    int i7 = this.mCurrentCalWidth;
                    if (i7 + i6 > paddingRight) {
                        gotoCalNextLine(paddingLeft);
                        this.mCurrentCalWidth += i6;
                    } else if (i7 + i6 == paddingRight) {
                        gotoCalNextLine(paddingLeft);
                    } else {
                        this.mCurrentCalWidth = i7 + i6;
                    }
                    if (paddingRight - paddingLeft < i6) {
                        this.mJumpHandleMeasureAndDraw = true;
                    }
                }
                i3++;
            } else {
                return;
            }
        }
    }

    private boolean isElementEmpty() {
        QMUIQQFaceCompiler.ElementList elementList = this.mElementList;
        return elementList == null || elementList.getElements() == null || this.mElementList.getElements().isEmpty();
    }

    private void setContentCalMaxWidth(int i) {
        this.mContentCalMaxWidth = Math.max(i, this.mContentCalMaxWidth);
    }

    private void gotoCalNextLine(int i) {
        this.mCurrentCalLine++;
        setContentCalMaxWidth(this.mCurrentCalWidth);
        this.mCurrentCalWidth = i;
    }

    private void measureText(CharSequence charSequence, int i, int i2) {
        float[] fArr = new float[charSequence.length()];
        this.mPaint.getTextWidths(charSequence.toString(), fArr);
        int i3 = i2 - i;
        long currentTimeMillis = System.currentTimeMillis();
        int i4 = 0;
        while (i4 < fArr.length) {
            if (((float) i3) < fArr[i4]) {
                this.mJumpHandleMeasureAndDraw = true;
                return;
            } else if (System.currentTimeMillis() - currentTimeMillis > 2000) {
                QMUILog.m3306d(TAG, "measureText: text = %s, mCurrentCalWidth = %d, widthStart = %d, widthEnd = %d", charSequence, Integer.valueOf(this.mCurrentCalWidth), Integer.valueOf(i), Integer.valueOf(i2));
                this.mJumpHandleMeasureAndDraw = true;
                return;
            } else {
                if (((float) this.mCurrentCalWidth) + fArr[i4] > ((float) i2)) {
                    gotoCalNextLine(i);
                }
                double d = (double) this.mCurrentCalWidth;
                double ceil = Math.ceil((double) fArr[i4]);
                Double.isNaN(d);
                this.mCurrentCalWidth = (int) (d + ceil);
                i4++;
            }
        }
    }

    public void setListener(QQFaceViewListener qQFaceViewListener) {
        this.mListener = qQFaceViewListener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        long currentTimeMillis = System.currentTimeMillis();
        this.mJumpHandleMeasureAndDraw = false;
        calculateFontHeight();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        Log.i(TAG, "widthSize = " + size + "; heightSize = " + size2);
        this.mLines = 0;
        if (mode == 0 || mode == 1073741824) {
            calculateLinesAndContentWidth(size);
        } else {
            CharSequence charSequence = this.mOriginText;
            size = (charSequence == null || charSequence.length() == 0) ? 0 : calculateLinesAndContentWidth(Math.min(size, this.mMaxWidth));
        }
        if (this.mJumpHandleMeasureAndDraw) {
            if (mode2 == Integer.MIN_VALUE) {
                size2 = 0;
            }
            setMeasuredDimension(size, size2);
            return;
        }
        calculateNeedDrawLine();
        if (mode2 != 1073741824) {
            int paddingTop = getPaddingTop() + getPaddingBottom();
            int i4 = this.mNeedDrawLine;
            if (i4 < 2) {
                i3 = i4 * this.mFontHeight;
            } else {
                int i5 = this.mFontHeight;
                i3 = ((i4 - 1) * (this.mLineSpace + i5)) + i5;
            }
            size2 = paddingTop + i3;
        }
        setMeasuredDimension(size, size2);
        Log.i(TAG, "mLines = " + this.mLines + " ; width = " + size + " ; height = " + size2 + "; measure time = " + (System.currentTimeMillis() - currentTimeMillis));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.mJumpHandleMeasureAndDraw && this.mOriginText != null && this.mLines != 0 && !isElementEmpty()) {
            long currentTimeMillis = System.currentTimeMillis();
            List<QMUIQQFaceCompiler.Element> elements = this.mElementList.getElements();
            this.mCurrentDrawBaseLine = getPaddingTop() + this.mFirstBaseLine;
            this.mCurrentDrawLine = 1;
            this.mCurrentDrawUsedWidth = getPaddingLeft();
            this.mIsExecutedMiddleEllipsize = false;
            drawElements(canvas, elements, (getWidth() - getPaddingLeft()) - getPaddingRight());
            Log.i(TAG, "onDraw spend time = " + (System.currentTimeMillis() - currentTimeMillis));
        }
    }

    private void drawElements(Canvas canvas, List<QMUIQQFaceCompiler.Element> list, int i) {
        int i2;
        Canvas canvas2 = canvas;
        int i3 = i;
        int paddingLeft = getPaddingLeft();
        int i4 = i3 + paddingLeft;
        if (this.mIsNeedEllipsize && this.mEllipsize == TextUtils.TruncateAt.START) {
            canvas.drawText(mEllipsizeText, 0, 3, (float) paddingLeft, (float) this.mFirstBaseLine, this.mPaint);
        }
        int i5 = 0;
        while (i5 < list.size()) {
            QMUIQQFaceCompiler.Element element = list.get(i5);
            QMUIQQFaceCompiler.ElementType type = element.getType();
            if (type == QMUIQQFaceCompiler.ElementType.DRAWABLE) {
                onDrawQQFace(canvas, element.getDrawableRes(), (Drawable) null, paddingLeft, i4, i5 == 0, i5 == list.size() - 1);
            } else if (type == QMUIQQFaceCompiler.ElementType.SPECIAL_BOUNDS_DRAWABLE) {
                onDrawQQFace(canvas, 0, element.getSpecialBoundsDrawable(), paddingLeft, i4, i5 == 0, i5 == list.size() - 1);
            } else if (type == QMUIQQFaceCompiler.ElementType.TEXT) {
                onDrawText(canvas2, element.getText(), paddingLeft, i4);
            } else if (type == QMUIQQFaceCompiler.ElementType.SPAN) {
                QMUIQQFaceCompiler.ElementList childList = element.getChildList();
                this.mCurrentDrawSpan = element.getTouchableSpan();
                if (childList != null && !childList.getElements().isEmpty()) {
                    QMUITouchableSpan qMUITouchableSpan = this.mCurrentDrawSpan;
                    if (qMUITouchableSpan == null) {
                        drawElements(canvas2, childList.getElements(), i3);
                    } else {
                        this.mIsInDrawSpan = true;
                        if (qMUITouchableSpan.isPressed()) {
                            i2 = this.mCurrentDrawSpan.getPressedTextColor();
                        } else {
                            i2 = this.mCurrentDrawSpan.getNormalTextColor();
                        }
                        TextPaint textPaint = this.mPaint;
                        if (i2 == 0) {
                            i2 = this.mTextColor;
                        }
                        textPaint.setColor(i2);
                        drawElements(canvas2, childList.getElements(), i3);
                        this.mPaint.setColor(this.mTextColor);
                        this.mIsInDrawSpan = false;
                    }
                }
            } else if (type == QMUIQQFaceCompiler.ElementType.NEXTLINE) {
                int i6 = this.mEllipsizeTextLength + this.mMoreActionTextLength;
                if (!this.mIsNeedEllipsize || this.mEllipsize != TextUtils.TruncateAt.END || this.mCurrentDrawUsedWidth > i4 - i6 || this.mCurrentDrawLine != this.mNeedDrawLine) {
                    toNewDrawLine(paddingLeft, true);
                } else {
                    drawText(canvas, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                    this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                    drawMoreActionText(canvas);
                    return;
                }
            } else {
                continue;
            }
            i5++;
        }
    }

    private void drawMoreActionText(Canvas canvas) {
        if (!QMUILangHelper.isNullOrEmpty(this.mMoreActionText)) {
            this.mPaint.setColor(this.mMoreActionColor);
            String str = this.mMoreActionText;
            canvas.drawText(str, 0, str.length(), (float) this.mCurrentDrawUsedWidth, (float) this.mCurrentDrawBaseLine, this.mPaint);
            this.mPaint.setColor(this.mTextColor);
        }
    }

    private void toNewDrawLine(int i) {
        toNewDrawLine(i, false);
    }

    private void toNewDrawLine(int i, boolean z) {
        int i2 = (z ? this.mParagraphSpace : 0) + this.mLineSpace;
        this.mCurrentDrawLine++;
        if (!this.mIsNeedEllipsize) {
            this.mCurrentDrawBaseLine += this.mFontHeight + i2;
        } else if (this.mEllipsize == TextUtils.TruncateAt.START) {
            if (this.mCurrentDrawLine > (this.mLines - this.mNeedDrawLine) + 1) {
                this.mCurrentDrawBaseLine += this.mFontHeight + i2;
            }
        } else if (this.mEllipsize != TextUtils.TruncateAt.MIDDLE) {
            this.mCurrentDrawBaseLine += this.mFontHeight + i2;
        } else if (!this.mIsExecutedMiddleEllipsize || this.mMiddleEllipsizeWidthRecord == -1) {
            this.mCurrentDrawBaseLine += this.mFontHeight + i2;
        }
        this.mCurrentDrawUsedWidth = i;
    }

    private void onRealDrawText(Canvas canvas, CharSequence charSequence, int i, int i2) {
        double ceil = Math.ceil((double) this.mPaint.measureText(charSequence, 0, charSequence.length()));
        while (true) {
            int i3 = (int) ceil;
            if (this.mCurrentDrawUsedWidth + i3 > i2) {
                int breakText = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (i2 - this.mCurrentDrawUsedWidth), (float[]) null);
                drawText(canvas, charSequence, 0, breakText, i2 - this.mCurrentDrawUsedWidth);
                toNewDrawLine(i);
                charSequence = charSequence.subSequence(breakText, charSequence.length());
                ceil = Math.ceil((double) this.mPaint.measureText(charSequence, 0, charSequence.length()));
            } else {
                drawText(canvas, charSequence, 0, charSequence.length(), i3);
                this.mCurrentDrawUsedWidth += i3;
                return;
            }
        }
    }

    private int getMiddleEllipsizeLine() {
        int i = this.mNeedDrawLine;
        if (i % 2 == 0) {
            return i / 2;
        }
        return (i + 1) / 2;
    }

    private void onDrawText(Canvas canvas, CharSequence charSequence, int i, int i2) {
        Canvas canvas2 = canvas;
        CharSequence charSequence2 = charSequence;
        int i3 = i;
        int i4 = i2;
        if (!this.mIsNeedEllipsize) {
            int i5 = i4;
            int i6 = i3;
            onRealDrawText(canvas, charSequence, i, i2);
        } else if (this.mEllipsize == TextUtils.TruncateAt.START) {
            int i7 = this.mCurrentDrawLine;
            int i8 = this.mLines;
            int i9 = this.mNeedDrawLine;
            if (i7 > i8 - i9) {
                onRealDrawText(canvas, charSequence, i, i2);
            } else if (i7 < i8 - i9) {
                int ceil = (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, charSequence.length()));
                int i10 = this.mCurrentDrawUsedWidth;
                if (ceil + i10 > i4) {
                    int breakText = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (i4 - this.mCurrentDrawUsedWidth), (float[]) null);
                    toNewDrawLine(i3);
                    onDrawText(canvas2, charSequence2.subSequence(breakText, charSequence.length()), i3, i4);
                    return;
                }
                this.mCurrentDrawUsedWidth = i10 + ceil;
            } else {
                int ceil2 = (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, charSequence.length()));
                int dp2px = this.mCurrentCalWidth + this.mEllipsizeTextLength + QMUIDisplayHelper.dp2px(getContext(), 5);
                int i11 = this.mCurrentDrawUsedWidth;
                if (ceil2 + i11 < dp2px) {
                    this.mCurrentDrawUsedWidth = i11 + ceil2;
                } else if (ceil2 + i11 == dp2px) {
                    toNewDrawLine(this.mEllipsizeTextLength + i3);
                } else {
                    int breakText2 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (dp2px - this.mCurrentDrawUsedWidth), (float[]) null);
                    toNewDrawLine(this.mEllipsizeTextLength + i3);
                    onDrawText(canvas2, charSequence2.subSequence(breakText2, charSequence.length()), i3, i4);
                }
            }
        } else if (this.mEllipsize == TextUtils.TruncateAt.MIDDLE) {
            int middleEllipsizeLine = getMiddleEllipsizeLine();
            int ceil3 = (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, charSequence.length()));
            int i12 = this.mCurrentDrawLine;
            if (i12 >= middleEllipsizeLine) {
                int i13 = i4;
                int i14 = i3;
                if (i12 == middleEllipsizeLine) {
                    int width = (getWidth() / 2) - (this.mEllipsizeTextLength / 2);
                    if (this.mIsExecutedMiddleEllipsize) {
                        handleTextAfterMiddleEllipsize(canvas, charSequence, i, i2, middleEllipsizeLine, ceil3);
                        return;
                    }
                    int i15 = this.mCurrentDrawUsedWidth;
                    if (ceil3 + i15 < width) {
                        drawText(canvas, charSequence, 0, charSequence.length(), ceil3);
                        this.mCurrentDrawUsedWidth += ceil3;
                    } else if (i15 + ceil3 == width) {
                        Canvas canvas3 = canvas;
                        drawText(canvas3, charSequence, 0, charSequence.length(), ceil3);
                        this.mCurrentDrawUsedWidth += ceil3;
                        drawText(canvas3, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                        this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                        this.mMiddleEllipsizeWidthRecord = this.mCurrentDrawUsedWidth;
                        this.mIsExecutedMiddleEllipsize = true;
                    } else {
                        int i16 = middleEllipsizeLine;
                        int breakText3 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (width - this.mCurrentDrawUsedWidth), (float[]) null);
                        int ceil4 = (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, breakText3));
                        int i17 = i14;
                        Canvas canvas4 = canvas;
                        int i18 = i13;
                        drawText(canvas4, charSequence, 0, breakText3, ceil4);
                        this.mCurrentDrawUsedWidth += ceil4;
                        drawText(canvas4, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                        this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                        this.mMiddleEllipsizeWidthRecord = this.mCurrentDrawUsedWidth;
                        this.mIsExecutedMiddleEllipsize = true;
                        if (breakText3 < charSequence.length()) {
                            CharSequence subSequence = charSequence2.subSequence(breakText3, charSequence.length());
                            handleTextAfterMiddleEllipsize(canvas, subSequence, i, i2, i16, (int) Math.ceil((double) this.mPaint.measureText(subSequence, 0, subSequence.length())));
                        }
                    }
                } else {
                    int i19 = middleEllipsizeLine;
                    int i20 = i14;
                    handleTextAfterMiddleEllipsize(canvas, charSequence, i, i2, i19, ceil3);
                }
            } else if (this.mCurrentDrawUsedWidth + ceil3 > i4) {
                int breakText4 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (i4 - this.mCurrentDrawUsedWidth), (float[]) null);
                int i21 = i3;
                drawText(canvas, charSequence, 0, breakText4, i4 - this.mCurrentDrawUsedWidth);
                toNewDrawLine(i21);
                onDrawText(canvas2, charSequence2.subSequence(breakText4, charSequence.length()), i21, i4);
            } else {
                drawText(canvas, charSequence, 0, charSequence.length(), ceil3);
                this.mCurrentDrawUsedWidth += ceil3;
            }
        } else {
            int i22 = i4;
            int i23 = i3;
            int ceil5 = (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, charSequence.length()));
            int i24 = this.mCurrentDrawLine;
            int i25 = this.mNeedDrawLine;
            if (i24 == i25) {
                int i26 = this.mEllipsizeTextLength + this.mMoreActionTextLength;
                int i27 = this.mCurrentDrawUsedWidth;
                int i28 = i22 - i26;
                if (ceil5 + i27 >= i28) {
                    if (i27 + ceil5 > i28) {
                        int breakText5 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) ((i22 - this.mCurrentDrawUsedWidth) - i26), (float[]) null);
                        i23 = i23;
                        drawText(canvas, charSequence, 0, breakText5, ceil5);
                        this.mCurrentDrawUsedWidth += (int) Math.ceil((double) this.mPaint.measureText(charSequence2, 0, breakText5));
                    } else {
                        drawText(canvas, charSequence, 0, charSequence.length(), ceil5);
                        this.mCurrentDrawUsedWidth += ceil5;
                    }
                    drawText(canvas, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                    this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                    drawMoreActionText(canvas);
                    toNewDrawLine(i23);
                    return;
                }
                drawText(canvas, charSequence, 0, charSequence.length(), ceil5);
                this.mCurrentDrawUsedWidth += ceil5;
                return;
            }
            int i29 = ceil5;
            if (i24 >= i25) {
                return;
            }
            if (i29 + this.mCurrentDrawUsedWidth > i22) {
                int i30 = i22;
                int breakText6 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (i22 - this.mCurrentDrawUsedWidth), (float[]) null);
                int i31 = i23;
                drawText(canvas, charSequence, 0, breakText6, i30 - this.mCurrentDrawUsedWidth);
                toNewDrawLine(i31);
                onDrawText(canvas2, charSequence2.subSequence(breakText6, charSequence.length()), i31, i30);
                return;
            }
            drawText(canvas, charSequence, 0, charSequence.length(), i29);
            this.mCurrentDrawUsedWidth += i29;
        }
    }

    private void handleTextAfterMiddleEllipsize(Canvas canvas, CharSequence charSequence, int i, int i2, int i3, int i4) {
        int i5;
        int i6 = this.mMiddleEllipsizeWidthRecord;
        if (i6 == -1) {
            onRealDrawText(canvas, charSequence, i, i2);
            return;
        }
        int i7 = this.mNeedDrawLine - i3;
        int i8 = (i2 - i6) - this.mCurrentCalWidth;
        int i9 = i8 > 0 ? (this.mLines - i7) - 1 : this.mLines - i7;
        if (i8 > 0) {
            i5 = i2 - i8;
        } else {
            i5 = this.mMiddleEllipsizeWidthRecord - (i2 - this.mCurrentCalWidth);
        }
        int dp2px = i5 + QMUIDisplayHelper.dp2px(getContext(), 5);
        int i10 = this.mCurrentDrawLine;
        if (i10 < i9) {
            int i11 = this.mCurrentDrawUsedWidth;
            if (i4 + i11 > i2) {
                int breakText = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (i2 - this.mCurrentDrawUsedWidth), (float[]) null);
                toNewDrawLine(i);
                onDrawText(canvas, charSequence.subSequence(breakText, charSequence.length()), i, i2);
                return;
            }
            this.mCurrentDrawUsedWidth = i11 + i4;
        } else if (i10 == i9) {
            int i12 = this.mCurrentDrawUsedWidth;
            if (i4 + i12 < dp2px) {
                this.mCurrentDrawUsedWidth = i12 + i4;
            } else if (i4 + i12 == dp2px) {
                this.mCurrentDrawUsedWidth = this.mMiddleEllipsizeWidthRecord;
                this.mMiddleEllipsizeWidthRecord = -1;
                this.mLastNeedStopLineRecord = i9;
            } else {
                int breakText2 = this.mPaint.breakText(charSequence, 0, charSequence.length(), true, (float) (dp2px - this.mCurrentDrawUsedWidth), (float[]) null);
                this.mCurrentDrawUsedWidth = this.mMiddleEllipsizeWidthRecord;
                this.mMiddleEllipsizeWidthRecord = -1;
                this.mLastNeedStopLineRecord = i9;
                onRealDrawText(canvas, charSequence.subSequence(breakText2, charSequence.length()), i, i2);
            }
        } else {
            onRealDrawText(canvas, charSequence, i, i2);
        }
    }

    private void drawText(Canvas canvas, CharSequence charSequence, int i, int i2, int i3) {
        QMUITouchableSpan qMUITouchableSpan;
        int i4;
        if (this.mIsInDrawSpan && (qMUITouchableSpan = this.mCurrentDrawSpan) != null) {
            if (qMUITouchableSpan.isPressed()) {
                i4 = this.mCurrentDrawSpan.getPressedBackgroundColor();
            } else {
                i4 = this.mCurrentDrawSpan.getNormalBackgroundColor();
            }
            if (i4 != 0) {
                this.mSpanBgPaint.setColor(i4);
                int i5 = this.mCurrentDrawUsedWidth;
                int i6 = this.mCurrentDrawBaseLine;
                int i7 = this.mFirstBaseLine;
                Canvas canvas2 = canvas;
                canvas2.drawRect((float) i5, (float) (i6 - i7), (float) (i5 + i3), (float) ((i6 - i7) + this.mFontHeight), this.mSpanBgPaint);
            }
        }
        canvas.drawText(charSequence, i, i2, (float) this.mCurrentDrawUsedWidth, (float) this.mCurrentDrawBaseLine, this.mPaint);
    }

    private void onDrawQQFace(Canvas canvas, int i, Drawable drawable, int i2, int i3, boolean z, boolean z2) {
        int intrinsicWidth;
        int i4 = i2;
        int i5 = i3;
        if (i != -1) {
            intrinsicWidth = this.mQQFaceSize;
        } else {
            intrinsicWidth = drawable.getIntrinsicWidth() + ((z || z2) ? this.mSpecialDrawablePadding : this.mSpecialDrawablePadding * 2);
        }
        int i6 = intrinsicWidth;
        if (!this.mIsNeedEllipsize) {
            onRealDrawQQFace(canvas, i, drawable, 0, i2, i3, z, z2);
        } else if (this.mEllipsize == TextUtils.TruncateAt.START) {
            int i7 = this.mCurrentDrawLine;
            int i8 = this.mLines;
            int i9 = this.mNeedDrawLine;
            if (i7 > i8 - i9) {
                onRealDrawQQFace(canvas, i, drawable, i9 - i8, i2, i3, z, z2);
            } else if (i7 < i8 - i9) {
                int i10 = this.mCurrentDrawUsedWidth;
                if (i6 + i10 > i5) {
                    toNewDrawLine(i2);
                    onDrawQQFace(canvas, i, drawable, i2, i3, z, z2);
                    return;
                }
                this.mCurrentDrawUsedWidth = i10 + i6;
            } else {
                int i11 = this.mCurrentCalWidth;
                int i12 = this.mEllipsizeTextLength;
                int i13 = i11 + i12;
                int i14 = this.mCurrentDrawUsedWidth;
                if (i6 + i14 < i13) {
                    this.mCurrentDrawUsedWidth = i14 + i6;
                } else {
                    toNewDrawLine(i4 + i12);
                }
            }
        } else if (this.mEllipsize == TextUtils.TruncateAt.MIDDLE) {
            int middleEllipsizeLine = getMiddleEllipsizeLine();
            int i15 = this.mCurrentDrawLine;
            if (i15 < middleEllipsizeLine) {
                if (this.mCurrentDrawUsedWidth + i6 > i5) {
                    onRealDrawQQFace(canvas, i, drawable, 0, i2, i3, z, z2);
                    return;
                }
                drawQQFace(canvas, i, drawable, i15, z, z2);
                this.mCurrentDrawUsedWidth += i6;
            } else if (i15 == middleEllipsizeLine) {
                int i16 = this.mEllipsizeTextLength;
                int width = (getWidth() / 2) - (i16 / 2);
                if (this.mIsExecutedMiddleEllipsize) {
                    handleQQFaceAfterMiddleEllipsize(canvas, i, drawable, i2, i3, middleEllipsizeLine, z, z2);
                    return;
                }
                int i17 = this.mCurrentDrawUsedWidth;
                if (i6 + i17 < width) {
                    drawQQFace(canvas, i, drawable, this.mCurrentDrawLine, z, z2);
                    this.mCurrentDrawUsedWidth += i6;
                } else if (i17 + i6 == width) {
                    drawQQFace(canvas, i, drawable, this.mCurrentDrawLine, z, z2);
                    this.mCurrentDrawUsedWidth += i6;
                    drawText(canvas, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                    this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                    this.mMiddleEllipsizeWidthRecord = this.mCurrentDrawUsedWidth;
                    this.mIsExecutedMiddleEllipsize = true;
                } else {
                    drawText(canvas, mEllipsizeText, 0, 3, i16);
                    this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                    this.mMiddleEllipsizeWidthRecord = this.mCurrentDrawUsedWidth;
                    this.mIsExecutedMiddleEllipsize = true;
                }
            } else {
                handleQQFaceAfterMiddleEllipsize(canvas, i, drawable, i2, i3, middleEllipsizeLine, z, z2);
            }
        } else {
            int i18 = this.mCurrentDrawLine;
            int i19 = this.mNeedDrawLine;
            if (i18 == i19) {
                int i20 = this.mEllipsizeTextLength + this.mMoreActionTextLength;
                int i21 = this.mCurrentDrawUsedWidth;
                int i22 = i5 - i20;
                if (i6 + i21 >= i22) {
                    if (i21 + i6 == i22) {
                        drawQQFace(canvas, i, drawable, i18, z, z2);
                        this.mCurrentDrawUsedWidth += i6;
                    }
                    drawText(canvas, mEllipsizeText, 0, 3, this.mEllipsizeTextLength);
                    this.mCurrentDrawUsedWidth += this.mEllipsizeTextLength;
                    drawMoreActionText(canvas);
                    toNewDrawLine(i2);
                    return;
                }
                drawQQFace(canvas, i, drawable, i18, z, z2);
                this.mCurrentDrawUsedWidth += i6;
            } else if (i18 >= i19) {
            } else {
                if (this.mCurrentDrawUsedWidth + i6 > i5) {
                    onRealDrawQQFace(canvas, i, drawable, 0, i2, i3, z, z2);
                    return;
                }
                drawQQFace(canvas, i, drawable, i18, z, z2);
                this.mCurrentDrawUsedWidth += i6;
            }
        }
    }

    private void handleQQFaceAfterMiddleEllipsize(Canvas canvas, int i, Drawable drawable, int i2, int i3, int i4, boolean z, boolean z2) {
        int i5;
        int i6;
        int i7 = i3;
        if (i != 0) {
            i5 = this.mQQFaceSize;
        } else {
            i5 = drawable.getIntrinsicWidth() + ((z || z2) ? this.mSpecialDrawablePadding : this.mSpecialDrawablePadding * 2);
        }
        int i8 = this.mMiddleEllipsizeWidthRecord;
        if (i8 == -1) {
            onRealDrawQQFace(canvas, i, drawable, i4 - this.mLastNeedStopLineRecord, i2, i3, z, z2);
            return;
        }
        int i9 = this.mNeedDrawLine - i4;
        int i10 = (i7 - i8) - this.mCurrentCalWidth;
        int i11 = i10 > 0 ? (this.mLines - i9) - 1 : this.mLines - i9;
        if (i10 > 0) {
            i6 = i7 - i10;
        } else {
            i6 = this.mMiddleEllipsizeWidthRecord - (i7 - this.mCurrentCalWidth);
        }
        int dp2px = i6 + QMUIDisplayHelper.dp2px(getContext(), 5);
        int i12 = this.mCurrentDrawLine;
        if (i12 < i11) {
            int i13 = this.mCurrentDrawUsedWidth;
            if (i5 + i13 > i7) {
                int i14 = i2;
                toNewDrawLine(i2);
                onDrawQQFace(canvas, i, drawable, i2, i3, z, z2);
                return;
            }
            this.mCurrentDrawUsedWidth = i13 + i5;
            return;
        }
        int i15 = i2;
        if (i12 == i11) {
            int i16 = this.mCurrentDrawUsedWidth;
            if (i5 + i16 < dp2px) {
                this.mCurrentDrawUsedWidth = i16 + i5;
                return;
            }
            this.mCurrentDrawUsedWidth = this.mMiddleEllipsizeWidthRecord;
            this.mMiddleEllipsizeWidthRecord = -1;
            this.mLastNeedStopLineRecord = i11;
            return;
        }
        onRealDrawQQFace(canvas, i, drawable, i4 - i11, i2, i3, z, z2);
    }

    private void onRealDrawQQFace(Canvas canvas, int i, Drawable drawable, int i2, int i3, int i4, boolean z, boolean z2) {
        int intrinsicWidth;
        if (i != 0) {
            intrinsicWidth = this.mQQFaceSize;
        } else {
            intrinsicWidth = drawable.getIntrinsicWidth() + ((z || z2) ? this.mSpecialDrawablePadding : this.mSpecialDrawablePadding * 2);
        }
        int i5 = intrinsicWidth;
        if (this.mCurrentDrawUsedWidth + i5 > i4) {
            int i6 = i3;
            toNewDrawLine(i3);
        }
        drawQQFace(canvas, i, drawable, this.mCurrentDrawLine + i2, z, z2);
        this.mCurrentDrawUsedWidth += i5;
    }

    private void drawQQFace(Canvas canvas, int i, Drawable drawable, int i2, boolean z, boolean z2) {
        int i3;
        QMUITouchableSpan qMUITouchableSpan;
        int i4;
        Drawable drawable2 = i != 0 ? ContextCompat.getDrawable(getContext(), i) : drawable;
        if (i != 0) {
            i3 = this.mQQFaceSize;
        } else {
            i3 = drawable.getIntrinsicWidth() + ((z || z2) ? this.mSpecialDrawablePadding : this.mSpecialDrawablePadding * 2);
        }
        if (drawable2 != null) {
            int i5 = 0;
            if (i != 0) {
                int i6 = this.mFontHeight;
                int i7 = this.mQQFaceSize;
                int i8 = (i6 - i7) / 2;
                drawable2.setBounds(0, i8, i7, i8 + i7);
            } else {
                int intrinsicHeight = (this.mFontHeight - drawable2.getIntrinsicHeight()) / 2;
                if (z2) {
                    i5 = this.mSpecialDrawablePadding;
                }
                drawable2.setBounds(i5, intrinsicHeight, drawable2.getIntrinsicWidth() + i5, drawable2.getIntrinsicHeight() + intrinsicHeight);
            }
            int paddingTop = getPaddingTop();
            if (i2 > 1) {
                paddingTop += (i2 - 1) * (this.mFontHeight + this.mLineSpace);
            }
            canvas.save();
            canvas.translate((float) this.mCurrentDrawUsedWidth, (float) paddingTop);
            if (this.mIsInDrawSpan && (qMUITouchableSpan = this.mCurrentDrawSpan) != null) {
                if (qMUITouchableSpan.isPressed()) {
                    i4 = this.mCurrentDrawSpan.getPressedBackgroundColor();
                } else {
                    i4 = this.mCurrentDrawSpan.getNormalBackgroundColor();
                }
                if (i4 != 0) {
                    this.mSpanBgPaint.setColor(i4);
                    canvas.drawRect(0.0f, 0.0f, (float) i3, (float) this.mFontHeight, this.mSpanBgPaint);
                }
            }
            drawable2.draw(canvas);
            canvas.restore();
        }
    }

    private class SpanInfo {
        private int mEndLine;
        private int mEndPoint;
        private int mStartLine;
        private int mStartPoint;
        private ITouchableSpan mTouchableSpan;

        public SpanInfo(ITouchableSpan iTouchableSpan) {
            this.mTouchableSpan = iTouchableSpan;
        }

        public void setStart(int i, int i2) {
            this.mStartLine = i;
            this.mStartPoint = i2;
        }

        public void setPressed(boolean z) {
            this.mTouchableSpan.setPressed(z);
        }

        public void setEnd(int i, int i2) {
            this.mEndLine = i;
            this.mEndPoint = i2;
        }

        public void onClick() {
            this.mTouchableSpan.onClick(QMUIQQFaceView.this);
        }

        public void invalidateSpan() {
            int paddingTop = QMUIQQFaceView.this.getPaddingTop();
            int i = this.mStartLine;
            if (i > 1) {
                paddingTop += (i - 1) * (QMUIQQFaceView.this.mFontHeight + QMUIQQFaceView.this.mLineSpace);
            }
            int access$100 = ((this.mEndLine - 1) * (QMUIQQFaceView.this.mFontHeight + QMUIQQFaceView.this.mLineSpace)) + paddingTop + QMUIQQFaceView.this.mFontHeight;
            Rect rect = new Rect();
            rect.top = paddingTop;
            rect.bottom = access$100;
            rect.left = QMUIQQFaceView.this.getPaddingLeft();
            rect.right = QMUIQQFaceView.this.getWidth() - QMUIQQFaceView.this.getPaddingRight();
            if (this.mStartLine == this.mEndLine) {
                rect.left = this.mStartPoint;
                rect.right = this.mEndPoint;
            }
            QMUIQQFaceView.this.invalidate(rect);
        }

        public boolean onTouch(int i, int i2) {
            int paddingTop = QMUIQQFaceView.this.getPaddingTop();
            int i3 = this.mStartLine;
            if (i3 > 1) {
                paddingTop += (i3 - 1) * (QMUIQQFaceView.this.mFontHeight + QMUIQQFaceView.this.mLineSpace);
            }
            int access$100 = ((this.mEndLine - 1) * (QMUIQQFaceView.this.mFontHeight + QMUIQQFaceView.this.mLineSpace)) + paddingTop + QMUIQQFaceView.this.mFontHeight;
            if (i2 < paddingTop || i2 > access$100) {
                return false;
            }
            if (this.mStartLine != this.mEndLine) {
                int access$1002 = paddingTop + QMUIQQFaceView.this.mFontHeight;
                int access$1003 = access$100 - QMUIQQFaceView.this.mFontHeight;
                if (i2 <= access$1002 || i2 >= access$1003) {
                    if (i2 <= access$1002) {
                        if (i >= this.mStartPoint) {
                            return true;
                        }
                        return false;
                    } else if (i <= this.mEndPoint) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (this.mEndLine - this.mStartLine != 1) {
                    return true;
                } else {
                    if (i < this.mStartPoint || i > this.mEndPoint) {
                        return false;
                    }
                    return true;
                }
            } else if (i < this.mStartPoint || i > this.mEndPoint) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static class PressCancelAction implements Runnable {
        private WeakReference<SpanInfo> mWeakReference;

        public PressCancelAction(SpanInfo spanInfo) {
            this.mWeakReference = new WeakReference<>(spanInfo);
        }

        public void run() {
            SpanInfo spanInfo = (SpanInfo) this.mWeakReference.get();
            if (spanInfo != null) {
                spanInfo.setPressed(false);
                spanInfo.invalidateSpan();
            }
        }
    }
}
