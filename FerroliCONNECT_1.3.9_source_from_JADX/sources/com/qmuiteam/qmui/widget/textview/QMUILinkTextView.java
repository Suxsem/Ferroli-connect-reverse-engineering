package com.qmuiteam.qmui.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.p000v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.link.QMUILinkTouchMovementMethod;
import com.qmuiteam.qmui.link.QMUILinkify;
import com.qmuiteam.qmui.span.QMUIOnSpanClickListener;
import java.util.HashSet;
import java.util.Set;

public class QMUILinkTextView extends TextView implements QMUIOnSpanClickListener, ISpanTouchFix {
    public static int AUTO_LINK_MASK_REQUIRED = 7;
    private static Set<String> AUTO_LINK_SCHEME_INTERRUPTED = new HashSet();
    private static final long DOUBLE_TAP_TIMEOUT = ((long) ViewConfiguration.getDoubleTapTimeout());
    private static final int MSG_CHECK_DOUBLE_TAP_TIMEOUT = 1000;
    private static final String TAG = "LinkTextView";
    private static final long TAP_TIMEOUT = 200;
    private int mAutoLinkMaskCompat;
    private long mDownMillis;
    private ColorStateList mLinkBgColor;
    private ColorStateList mLinkTextColor;
    private boolean mNeedForceEventToParent;
    /* access modifiers changed from: private */
    public OnLinkClickListener mOnLinkClickListener;
    private OnLinkLongClickListener mOnLinkLongClickListener;
    private CharSequence mOriginText;
    private Handler mSingleTapConfirmedHandler;
    private boolean mTouchSpanHit;

    public interface OnLinkClickListener {
        void onMailLinkClick(String str);

        void onTelLinkClick(String str);

        void onWebUrlLinkClick(String str);
    }

    public interface OnLinkLongClickListener {
        void onLongClick(String str);
    }

    static {
        AUTO_LINK_SCHEME_INTERRUPTED.add("tel");
        AUTO_LINK_SCHEME_INTERRUPTED.add("mailto");
        AUTO_LINK_SCHEME_INTERRUPTED.add("http");
        AUTO_LINK_SCHEME_INTERRUPTED.add("https");
    }

    public QMUILinkTextView(Context context) {
        this(context, (AttributeSet) null);
        this.mLinkBgColor = null;
        this.mLinkTextColor = ContextCompat.getColorStateList(context, C1614R.color.qmui_s_link_color);
    }

    public QMUILinkTextView(Context context, ColorStateList colorStateList, ColorStateList colorStateList2) {
        this(context, (AttributeSet) null);
        this.mLinkBgColor = colorStateList2;
        this.mLinkTextColor = colorStateList;
    }

    public QMUILinkTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOriginText = null;
        this.mNeedForceEventToParent = false;
        this.mDownMillis = 0;
        this.mSingleTapConfirmedHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                if (1000 == message.what) {
                    Log.d(QMUILinkTextView.TAG, "handleMessage: " + message.obj);
                    if (message.obj instanceof String) {
                        String str = (String) message.obj;
                        if (QMUILinkTextView.this.mOnLinkClickListener != null && !TextUtils.isEmpty(str)) {
                            String lowerCase = str.toLowerCase();
                            if (lowerCase.startsWith("tel:")) {
                                QMUILinkTextView.this.mOnLinkClickListener.onTelLinkClick(Uri.parse(str).getSchemeSpecificPart());
                            } else if (lowerCase.startsWith("mailto:")) {
                                QMUILinkTextView.this.mOnLinkClickListener.onMailLinkClick(Uri.parse(str).getSchemeSpecificPart());
                            } else if (lowerCase.startsWith("http") || lowerCase.startsWith("https")) {
                                QMUILinkTextView.this.mOnLinkClickListener.onWebUrlLinkClick(str);
                            }
                        }
                    }
                }
            }
        };
        this.mAutoLinkMaskCompat = getAutoLinkMask() | AUTO_LINK_MASK_REQUIRED;
        setAutoLinkMask(0);
        setMovementMethod(QMUILinkTouchMovementMethod.getInstance());
        setHighlightColor(0);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUILinkTextView);
        this.mLinkBgColor = obtainStyledAttributes.getColorStateList(C1614R.styleable.QMUILinkTextView_qmui_linkBackgroundColor);
        this.mLinkTextColor = obtainStyledAttributes.getColorStateList(C1614R.styleable.QMUILinkTextView_qmui_linkTextColor);
        obtainStyledAttributes.recycle();
        CharSequence charSequence = this.mOriginText;
        if (charSequence != null) {
            setText(charSequence);
        }
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.mOnLinkClickListener = onLinkClickListener;
    }

    public void setOnLinkLongClickListener(OnLinkLongClickListener onLinkLongClickListener) {
        this.mOnLinkLongClickListener = onLinkLongClickListener;
    }

    public int getAutoLinkMaskCompat() {
        return this.mAutoLinkMaskCompat;
    }

    public void setAutoLinkMaskCompat(int i) {
        this.mAutoLinkMaskCompat = i;
    }

    public void addAutoLinkMaskCompat(int i) {
        this.mAutoLinkMaskCompat = i | this.mAutoLinkMaskCompat;
    }

    public void removeAutoLinkMaskCompat(int i) {
        this.mAutoLinkMaskCompat = (i ^ -1) & this.mAutoLinkMaskCompat;
    }

    public void setNeedForceEventToParent(boolean z) {
        if (this.mNeedForceEventToParent != z) {
            this.mNeedForceEventToParent = z;
            CharSequence charSequence = this.mOriginText;
            if (charSequence != null) {
                setText(charSequence);
            }
        }
    }

    public void setLinkColor(ColorStateList colorStateList) {
        this.mLinkTextColor = colorStateList;
    }

    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        this.mOriginText = charSequence;
        if (!TextUtils.isEmpty(charSequence)) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
            QMUILinkify.addLinks((Spannable) spannableStringBuilder, this.mAutoLinkMaskCompat, this.mLinkTextColor, this.mLinkBgColor, (QMUIOnSpanClickListener) this);
            charSequence = spannableStringBuilder;
        }
        super.setText(charSequence, bufferType);
        if (this.mNeedForceEventToParent && getLinksClickable()) {
            setFocusable(false);
            setClickable(false);
            setLongClickable(false);
        }
    }

    public boolean onSpanClick(String str) {
        if (str == null) {
            Log.w(TAG, "onSpanClick interrupt null text");
            return true;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mDownMillis;
        Log.w(TAG, "onSpanClick clickUpTime: " + uptimeMillis);
        if (this.mSingleTapConfirmedHandler.hasMessages(1000)) {
            disallowOnSpanClickInterrupt();
            return true;
        } else if (TAP_TIMEOUT < uptimeMillis) {
            Log.w(TAG, "onSpanClick interrupted because of TAP_TIMEOUT: " + uptimeMillis);
            return true;
        } else {
            String scheme = Uri.parse(str).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }
            if (!AUTO_LINK_SCHEME_INTERRUPTED.contains(scheme)) {
                return false;
            }
            this.mSingleTapConfirmedHandler.removeMessages(1000);
            Message obtain = Message.obtain();
            obtain.what = 1000;
            obtain.obj = str;
            this.mSingleTapConfirmedHandler.sendMessageDelayed(obtain, DOUBLE_TAP_TIMEOUT - uptimeMillis);
            return true;
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() & 255) == 0) {
            boolean hasMessages = this.mSingleTapConfirmedHandler.hasMessages(1000);
            Log.w(TAG, "onTouchEvent hasSingleTap: " + hasMessages);
            if (!hasMessages) {
                this.mDownMillis = SystemClock.uptimeMillis();
            } else {
                Log.w(TAG, "onTouchEvent disallow onSpanClick mSingleTapConfirmedHandler because of DOUBLE TAP");
                disallowOnSpanClickInterrupt();
            }
        }
        return this.mNeedForceEventToParent ? this.mTouchSpanHit : super.onTouchEvent(motionEvent);
    }

    private void disallowOnSpanClickInterrupt() {
        this.mSingleTapConfirmedHandler.removeMessages(1000);
        this.mDownMillis = 0;
    }

    /* access modifiers changed from: protected */
    public boolean performSpanLongClick(String str) {
        OnLinkLongClickListener onLinkLongClickListener = this.mOnLinkLongClickListener;
        if (onLinkLongClickListener == null) {
            return false;
        }
        onLinkLongClickListener.onLongClick(str);
        return true;
    }

    public boolean performLongClick() {
        int selectionEnd = getSelectionEnd();
        if (selectionEnd > 0) {
            return performSpanLongClick(getText().subSequence(getSelectionStart(), selectionEnd).toString()) || super.performLongClick();
        }
        return super.performLongClick();
    }

    public void setTouchSpanHit(boolean z) {
        if (this.mTouchSpanHit != z) {
            this.mTouchSpanHit = z;
        }
    }
}
