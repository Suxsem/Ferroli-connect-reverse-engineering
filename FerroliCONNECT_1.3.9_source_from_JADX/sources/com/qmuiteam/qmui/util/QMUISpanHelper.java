package com.qmuiteam.qmui.util;

import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import com.qmuiteam.qmui.span.QMUIMarginImageSpan;

public class QMUISpanHelper {
    public static CharSequence generateSideIconText(boolean z, int i, CharSequence charSequence, Drawable drawable) {
        int i2;
        int i3;
        QMUIMarginImageSpan qMUIMarginImageSpan;
        if (drawable == null) {
            return charSequence;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (z) {
            spannableStringBuilder.append("[icon]");
            i3 = spannableStringBuilder.length();
            spannableStringBuilder.append(charSequence);
            i2 = 0;
        } else {
            spannableStringBuilder.append(charSequence);
            i2 = spannableStringBuilder.length();
            spannableStringBuilder.append("[icon]");
            i3 = spannableStringBuilder.length();
        }
        if (z) {
            qMUIMarginImageSpan = new QMUIMarginImageSpan(drawable, -100, 0, i);
        } else {
            qMUIMarginImageSpan = new QMUIMarginImageSpan(drawable, -100, i, 0);
        }
        spannableStringBuilder.setSpan(qMUIMarginImageSpan, i2, i3, 17);
        qMUIMarginImageSpan.setAvoidSuperChangeFontMetrics(true);
        return spannableStringBuilder;
    }
}
