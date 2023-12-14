package com.qmuiteam.qmui.link;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.view.MotionEvent;
import android.widget.TextView;
import com.qmuiteam.qmui.widget.textview.ISpanTouchFix;

public class QMUILinkTouchDecorHelper {
    private ITouchableSpan mPressedSpan;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        boolean z = true;
        if (motionEvent.getAction() == 0) {
            this.mPressedSpan = getPressedSpan(textView, spannable, motionEvent);
            ITouchableSpan iTouchableSpan = this.mPressedSpan;
            if (iTouchableSpan != null) {
                iTouchableSpan.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(this.mPressedSpan), spannable.getSpanEnd(this.mPressedSpan));
            }
            if (textView instanceof ISpanTouchFix) {
                ((ISpanTouchFix) textView).setTouchSpanHit(this.mPressedSpan != null);
            }
            if (this.mPressedSpan != null) {
                return true;
            }
            return false;
        } else if (motionEvent.getAction() == 2) {
            ITouchableSpan pressedSpan = getPressedSpan(textView, spannable, motionEvent);
            ITouchableSpan iTouchableSpan2 = this.mPressedSpan;
            if (!(iTouchableSpan2 == null || pressedSpan == iTouchableSpan2)) {
                iTouchableSpan2.setPressed(false);
                this.mPressedSpan = null;
                Selection.removeSelection(spannable);
            }
            if (textView instanceof ISpanTouchFix) {
                ((ISpanTouchFix) textView).setTouchSpanHit(this.mPressedSpan != null);
            }
            if (this.mPressedSpan != null) {
                return true;
            }
            return false;
        } else if (motionEvent.getAction() == 1) {
            ITouchableSpan iTouchableSpan3 = this.mPressedSpan;
            if (iTouchableSpan3 != null) {
                iTouchableSpan3.setPressed(false);
                this.mPressedSpan.onClick(textView);
            } else {
                z = false;
            }
            this.mPressedSpan = null;
            Selection.removeSelection(spannable);
            if (textView instanceof ISpanTouchFix) {
                ((ISpanTouchFix) textView).setTouchSpanHit(z);
            }
            return z;
        } else {
            ITouchableSpan iTouchableSpan4 = this.mPressedSpan;
            if (iTouchableSpan4 != null) {
                iTouchableSpan4.setPressed(false);
            }
            if (textView instanceof ISpanTouchFix) {
                ((ISpanTouchFix) textView).setTouchSpanHit(false);
            }
            Selection.removeSelection(spannable);
            return false;
        }
    }

    public ITouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent motionEvent) {
        int x = ((int) motionEvent.getX()) - textView.getTotalPaddingLeft();
        int y = ((int) motionEvent.getY()) - textView.getTotalPaddingTop();
        int scrollX = x + textView.getScrollX();
        Layout layout = textView.getLayout();
        int lineForVertical = layout.getLineForVertical(y + textView.getScrollY());
        float f = (float) scrollX;
        int offsetForHorizontal = layout.getOffsetForHorizontal(lineForVertical, f);
        if (f < layout.getLineLeft(lineForVertical) || f > layout.getLineRight(lineForVertical)) {
            offsetForHorizontal = -1;
        }
        ITouchableSpan[] iTouchableSpanArr = (ITouchableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ITouchableSpan.class);
        if (iTouchableSpanArr.length > 0) {
            return iTouchableSpanArr[0];
        }
        return null;
    }
}
