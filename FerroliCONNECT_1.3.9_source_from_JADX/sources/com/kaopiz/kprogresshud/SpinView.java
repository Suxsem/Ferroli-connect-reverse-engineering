package com.kaopiz.kprogresshud;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

class SpinView extends ImageView implements Indeterminate {
    /* access modifiers changed from: private */
    public int mFrameTime;
    /* access modifiers changed from: private */
    public boolean mNeedToUpdateView;
    /* access modifiers changed from: private */
    public float mRotateDegrees;
    private Runnable mUpdateViewRunnable;

    public SpinView(Context context) {
        super(context);
        init();
    }

    public SpinView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setImageResource(C1609R.C1610drawable.kprogresshud_spinner);
        this.mFrameTime = 83;
        this.mUpdateViewRunnable = new Runnable() {
            public void run() {
                SpinView spinView = SpinView.this;
                float unused = spinView.mRotateDegrees = spinView.mRotateDegrees + 30.0f;
                SpinView spinView2 = SpinView.this;
                float unused2 = spinView2.mRotateDegrees = spinView2.mRotateDegrees < 360.0f ? SpinView.this.mRotateDegrees : SpinView.this.mRotateDegrees - 360.0f;
                SpinView.this.invalidate();
                if (SpinView.this.mNeedToUpdateView) {
                    SpinView spinView3 = SpinView.this;
                    spinView3.postDelayed(this, (long) spinView3.mFrameTime);
                }
            }
        };
    }

    public void setAnimationSpeed(float f) {
        this.mFrameTime = (int) (83.0f / f);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.rotate(this.mRotateDegrees, (float) (getWidth() / 2), (float) (getHeight() / 2));
        super.onDraw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mNeedToUpdateView = true;
        post(this.mUpdateViewRunnable);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mNeedToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
