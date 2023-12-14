package com.kaopiz.kprogresshud;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

public class KProgressHUD {
    /* access modifiers changed from: private */
    public int mAnimateSpeed;
    private Context mContext;
    /* access modifiers changed from: private */
    public float mCornerRadius;
    /* access modifiers changed from: private */
    public float mDimAmount = 0.0f;
    /* access modifiers changed from: private */
    public boolean mIsAutoDismiss;
    /* access modifiers changed from: private */
    public int mMaxProgress;
    private ProgressDialog mProgressDialog;
    /* access modifiers changed from: private */
    public int mWindowColor;

    public enum Style {
        SPIN_INDETERMINATE,
        PIE_DETERMINATE,
        ANNULAR_DETERMINATE,
        BAR_DETERMINATE
    }

    public KProgressHUD(Context context) {
        this.mContext = context;
        this.mProgressDialog = new ProgressDialog(context);
        this.mWindowColor = context.getResources().getColor(C1609R.color.kprogresshud_default_color);
        this.mAnimateSpeed = 1;
        this.mCornerRadius = 10.0f;
        this.mIsAutoDismiss = true;
        setStyle(Style.SPIN_INDETERMINATE);
    }

    public static KProgressHUD create(Context context) {
        return new KProgressHUD(context);
    }

    public static KProgressHUD create(Context context, Style style) {
        return new KProgressHUD(context).setStyle(style);
    }

    /* renamed from: com.kaopiz.kprogresshud.KProgressHUD$1 */
    static /* synthetic */ class C16081 {
        static final /* synthetic */ int[] $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style = new int[Style.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.kaopiz.kprogresshud.KProgressHUD$Style[] r0 = com.kaopiz.kprogresshud.KProgressHUD.Style.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style = r0
                int[] r0 = $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.kaopiz.kprogresshud.KProgressHUD$Style r1 = com.kaopiz.kprogresshud.KProgressHUD.Style.SPIN_INDETERMINATE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style     // Catch:{ NoSuchFieldError -> 0x001f }
                com.kaopiz.kprogresshud.KProgressHUD$Style r1 = com.kaopiz.kprogresshud.KProgressHUD.Style.PIE_DETERMINATE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style     // Catch:{ NoSuchFieldError -> 0x002a }
                com.kaopiz.kprogresshud.KProgressHUD$Style r1 = com.kaopiz.kprogresshud.KProgressHUD.Style.ANNULAR_DETERMINATE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.kaopiz.kprogresshud.KProgressHUD$Style r1 = com.kaopiz.kprogresshud.KProgressHUD.Style.BAR_DETERMINATE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.kaopiz.kprogresshud.KProgressHUD.C16081.<clinit>():void");
        }
    }

    public KProgressHUD setStyle(Style style) {
        View view;
        int i = C16081.$SwitchMap$com$kaopiz$kprogresshud$KProgressHUD$Style[style.ordinal()];
        if (i == 1) {
            view = new SpinView(this.mContext);
        } else if (i == 2) {
            view = new PieView(this.mContext);
        } else if (i != 3) {
            view = i != 4 ? null : new BarView(this.mContext);
        } else {
            view = new AnnularView(this.mContext);
        }
        this.mProgressDialog.setView(view);
        return this;
    }

    public KProgressHUD setDimAmount(float f) {
        if (f >= 0.0f && f <= 1.0f) {
            this.mDimAmount = f;
        }
        return this;
    }

    public KProgressHUD setSize(int i, int i2) {
        this.mProgressDialog.setSize(i, i2);
        return this;
    }

    public KProgressHUD setWindowColor(int i) {
        this.mWindowColor = i;
        return this;
    }

    public KProgressHUD setCornerRadius(float f) {
        this.mCornerRadius = f;
        return this;
    }

    public KProgressHUD setAnimationSpeed(int i) {
        this.mAnimateSpeed = i;
        return this;
    }

    public KProgressHUD setLabel(String str) {
        this.mProgressDialog.setLabel(str);
        return this;
    }

    public KProgressHUD setDetailsLabel(String str) {
        this.mProgressDialog.setDetailsLabel(str);
        return this;
    }

    public KProgressHUD setMaxProgress(int i) {
        this.mMaxProgress = i;
        return this;
    }

    public void setProgress(int i) {
        this.mProgressDialog.setProgress(i);
    }

    public KProgressHUD setCustomView(View view) {
        if (view != null) {
            this.mProgressDialog.setView(view);
            return this;
        }
        throw new RuntimeException("Custom view must not be null!");
    }

    public KProgressHUD setCancellable(boolean z) {
        this.mProgressDialog.setCancelable(z);
        return this;
    }

    public KProgressHUD setAutoDismiss(boolean z) {
        this.mIsAutoDismiss = z;
        return this;
    }

    public KProgressHUD show() {
        if (!isShowing()) {
            this.mProgressDialog.show();
        }
        return this;
    }

    public boolean isShowing() {
        ProgressDialog progressDialog = this.mProgressDialog;
        return progressDialog != null && progressDialog.isShowing();
    }

    public void dismiss() {
        ProgressDialog progressDialog = this.mProgressDialog;
        if (progressDialog != null && progressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
        }
    }

    private class ProgressDialog extends Dialog {
        private BackgroundLayout mBackgroundLayout;
        private FrameLayout mCustomViewContainer;
        private String mDetailsLabel;
        private TextView mDetailsText;
        private Determinate mDeterminateView;
        private int mHeight;
        private Indeterminate mIndeterminateView;
        private String mLabel;
        private TextView mLabelText;
        private View mView;
        private int mWidth;

        public ProgressDialog(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            requestWindowFeature(1);
            setContentView(C1609R.C1612layout.kprogresshud_hud);
            Window window = getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.addFlags(2);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.dimAmount = KProgressHUD.this.mDimAmount;
            attributes.gravity = 17;
            window.setAttributes(attributes);
            setCanceledOnTouchOutside(false);
            initViews();
        }

        private void initViews() {
            this.mBackgroundLayout = (BackgroundLayout) findViewById(C1609R.C1611id.background);
            this.mBackgroundLayout.setBaseColor(KProgressHUD.this.mWindowColor);
            this.mBackgroundLayout.setCornerRadius(KProgressHUD.this.mCornerRadius);
            if (this.mWidth != 0) {
                updateBackgroundSize();
            }
            this.mCustomViewContainer = (FrameLayout) findViewById(C1609R.C1611id.container);
            addViewToFrame(this.mView);
            Determinate determinate = this.mDeterminateView;
            if (determinate != null) {
                determinate.setMax(KProgressHUD.this.mMaxProgress);
            }
            Indeterminate indeterminate = this.mIndeterminateView;
            if (indeterminate != null) {
                indeterminate.setAnimationSpeed((float) KProgressHUD.this.mAnimateSpeed);
            }
            this.mLabelText = (TextView) findViewById(C1609R.C1611id.label);
            String str = this.mLabel;
            if (str != null) {
                this.mLabelText.setText(str);
                this.mLabelText.setVisibility(0);
            } else {
                this.mLabelText.setVisibility(8);
            }
            this.mDetailsText = (TextView) findViewById(C1609R.C1611id.details_label);
            String str2 = this.mDetailsLabel;
            if (str2 != null) {
                this.mDetailsText.setText(str2);
                this.mDetailsText.setVisibility(0);
                return;
            }
            this.mDetailsText.setVisibility(8);
        }

        private void addViewToFrame(View view) {
            if (view != null) {
                this.mCustomViewContainer.addView(view, new ViewGroup.LayoutParams(-2, -2));
            }
        }

        private void updateBackgroundSize() {
            ViewGroup.LayoutParams layoutParams = this.mBackgroundLayout.getLayoutParams();
            layoutParams.width = Helper.dpToPixel((float) this.mWidth, getContext());
            layoutParams.height = Helper.dpToPixel((float) this.mHeight, getContext());
            this.mBackgroundLayout.setLayoutParams(layoutParams);
        }

        public void setProgress(int i) {
            Determinate determinate = this.mDeterminateView;
            if (determinate != null) {
                determinate.setProgress(i);
                if (KProgressHUD.this.mIsAutoDismiss && i >= KProgressHUD.this.mMaxProgress) {
                    dismiss();
                }
            }
        }

        public void setView(View view) {
            if (view != null) {
                if (view instanceof Determinate) {
                    this.mDeterminateView = (Determinate) view;
                }
                if (view instanceof Indeterminate) {
                    this.mIndeterminateView = (Indeterminate) view;
                }
                this.mView = view;
                if (isShowing()) {
                    this.mCustomViewContainer.removeAllViews();
                    addViewToFrame(view);
                }
            }
        }

        public void setLabel(String str) {
            this.mLabel = str;
            TextView textView = this.mLabelText;
            if (textView == null) {
                return;
            }
            if (str != null) {
                textView.setText(str);
                this.mLabelText.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
        }

        public void setDetailsLabel(String str) {
            this.mDetailsLabel = str;
            TextView textView = this.mDetailsText;
            if (textView == null) {
                return;
            }
            if (str != null) {
                textView.setText(str);
                this.mDetailsText.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
        }

        public void setSize(int i, int i2) {
            this.mWidth = i;
            this.mHeight = i2;
            if (this.mBackgroundLayout != null) {
                updateBackgroundSize();
            }
        }
    }
}
