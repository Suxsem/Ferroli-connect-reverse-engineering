package com.qmuiteam.qmui.link;

import android.view.View;

public interface ITouchableSpan {
    void onClick(View view);

    void setPressed(boolean z);
}
