package com.qmuiteam.qmui.qqface;

import android.graphics.drawable.Drawable;

public interface IQMUIQQFaceManager {
    int getDoubleUnicodeEmoji(int i, int i2);

    int getEmojiResource(int i);

    int getQQfaceResource(CharSequence charSequence);

    int getSoftbankEmojiResource(char c);

    Drawable getSpecialBoundsDrawable(CharSequence charSequence);

    int getSpecialDrawableMaxHeight();

    boolean maybeEmoji(int i);

    boolean maybeSoftBankEmoji(char c);
}
