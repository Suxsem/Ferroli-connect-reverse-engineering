package com.szacs.ferroliconnect.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;

public class MyProgressDialog extends Dialog {
    private Context context;
    private boolean mCanceledOnTouchOutside;

    public MyProgressDialog(Context context2) {
        this(context2, C1683R.style.Custom_Progress);
    }

    public MyProgressDialog(Context context2, int i) {
        super(context2, i);
        this.mCanceledOnTouchOutside = false;
        this.context = context2;
        setContentView(C1683R.C1686layout.widget_progress_dialog);
        getWindow().getAttributes().gravity = 17;
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    protected MyProgressDialog(Context context2, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context2, z, onCancelListener);
        this.mCanceledOnTouchOutside = false;
    }

    public void onWindowFocusChanged(boolean z) {
        ((AnimationDrawable) ((ImageView) findViewById(C1683R.C1685id.spinnerImageView)).getBackground()).start();
    }

    public void setMessage(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() > 0) {
            TextView textView = (TextView) findViewById(C1683R.C1685id.message);
            textView.setText(charSequence);
            textView.invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mCanceledOnTouchOutside || motionEvent.getAction() != 0 || !isOutOfBounds(motionEvent)) {
            return false;
        }
        cancel();
        return true;
    }

    private boolean isOutOfBounds(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(this.context).getScaledWindowTouchSlop();
        View decorView = getWindow().getDecorView();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop;
    }
}
