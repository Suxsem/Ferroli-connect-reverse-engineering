package com.gyf.barlibrary;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;

public class KeyboardPatch {
    /* access modifiers changed from: private */
    public int actionBarHeight;
    /* access modifiers changed from: private */
    public int keyboardHeightPrevious;
    private Activity mActivity;
    /* access modifiers changed from: private */
    public BarParams mBarParams;
    /* access modifiers changed from: private */
    public View mChildView;
    /* access modifiers changed from: private */
    public View mContentView;
    /* access modifiers changed from: private */
    public View mDecorView;
    private Window mWindow;
    /* access modifiers changed from: private */
    public boolean navigationAtBottom;
    /* access modifiers changed from: private */
    public int navigationBarHeight;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    /* access modifiers changed from: private */
    public int paddingBottom;
    /* access modifiers changed from: private */
    public int paddingLeft;
    /* access modifiers changed from: private */
    public int paddingRight;
    /* access modifiers changed from: private */
    public int paddingTop;
    /* access modifiers changed from: private */
    public int statusBarHeight;

    private KeyboardPatch(Activity activity) {
        this(activity, ((FrameLayout) activity.getWindow().getDecorView().findViewById(16908290)).getChildAt(0));
    }

    private KeyboardPatch(Activity activity, View view) {
        this(activity, (Dialog) null, "", view);
    }

    private KeyboardPatch(Activity activity, Dialog dialog, String str) {
        this(activity, dialog, str, dialog.getWindow().findViewById(16908290));
    }

    private KeyboardPatch(Activity activity, Dialog dialog, String str, View view) {
        BarParams barParams;
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int i;
                int i2;
                int height;
                int i3;
                if (KeyboardPatch.this.navigationAtBottom) {
                    Rect rect = new Rect();
                    KeyboardPatch.this.mDecorView.getWindowVisibleDisplayFrame(rect);
                    boolean z = true;
                    if (KeyboardPatch.this.mBarParams.systemWindows) {
                        int height2 = (KeyboardPatch.this.mContentView.getHeight() - rect.bottom) - KeyboardPatch.this.navigationBarHeight;
                        if (KeyboardPatch.this.mBarParams.onKeyboardListener != null) {
                            if (height2 <= KeyboardPatch.this.navigationBarHeight) {
                                z = false;
                            }
                            KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, height2);
                        }
                    } else if (KeyboardPatch.this.mChildView != null) {
                        if (KeyboardPatch.this.mBarParams.isSupportActionBar) {
                            height = KeyboardPatch.this.mContentView.getHeight() + KeyboardPatch.this.statusBarHeight + KeyboardPatch.this.actionBarHeight;
                            i3 = rect.bottom;
                        } else if (KeyboardPatch.this.mBarParams.fits) {
                            height = KeyboardPatch.this.mContentView.getHeight() + KeyboardPatch.this.statusBarHeight;
                            i3 = rect.bottom;
                        } else {
                            height = KeyboardPatch.this.mContentView.getHeight();
                            i3 = rect.bottom;
                        }
                        int i4 = height - i3;
                        int access$400 = KeyboardPatch.this.mBarParams.fullScreen ? i4 - KeyboardPatch.this.navigationBarHeight : i4;
                        if (KeyboardPatch.this.mBarParams.fullScreen && i4 == KeyboardPatch.this.navigationBarHeight) {
                            i4 -= KeyboardPatch.this.navigationBarHeight;
                        }
                        if (access$400 != KeyboardPatch.this.keyboardHeightPrevious) {
                            KeyboardPatch.this.mContentView.setPadding(KeyboardPatch.this.paddingLeft, KeyboardPatch.this.paddingTop, KeyboardPatch.this.paddingRight, i4 + KeyboardPatch.this.paddingBottom);
                            int unused = KeyboardPatch.this.keyboardHeightPrevious = access$400;
                            if (KeyboardPatch.this.mBarParams.onKeyboardListener != null) {
                                if (access$400 <= KeyboardPatch.this.navigationBarHeight) {
                                    z = false;
                                }
                                KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, access$400);
                            }
                        }
                    } else {
                        int height3 = KeyboardPatch.this.mContentView.getHeight() - rect.bottom;
                        if (!KeyboardPatch.this.mBarParams.navigationBarEnable || !KeyboardPatch.this.mBarParams.navigationBarWithKitkatEnable) {
                            i = height3;
                        } else {
                            if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
                                i2 = KeyboardPatch.this.navigationBarHeight;
                            } else if (!KeyboardPatch.this.mBarParams.fullScreen) {
                                i = height3;
                                if (KeyboardPatch.this.mBarParams.fullScreen && height3 == KeyboardPatch.this.navigationBarHeight) {
                                    height3 -= KeyboardPatch.this.navigationBarHeight;
                                }
                            } else {
                                i2 = KeyboardPatch.this.navigationBarHeight;
                            }
                            i = height3 - i2;
                            height3 -= KeyboardPatch.this.navigationBarHeight;
                        }
                        if (i != KeyboardPatch.this.keyboardHeightPrevious) {
                            if (KeyboardPatch.this.mBarParams.isSupportActionBar) {
                                KeyboardPatch.this.mContentView.setPadding(0, KeyboardPatch.this.statusBarHeight + KeyboardPatch.this.actionBarHeight, 0, height3);
                            } else if (KeyboardPatch.this.mBarParams.fits) {
                                KeyboardPatch.this.mContentView.setPadding(0, KeyboardPatch.this.statusBarHeight, 0, height3);
                            } else {
                                KeyboardPatch.this.mContentView.setPadding(0, 0, 0, height3);
                            }
                            int unused2 = KeyboardPatch.this.keyboardHeightPrevious = i;
                            if (KeyboardPatch.this.mBarParams.onKeyboardListener != null) {
                                if (i <= KeyboardPatch.this.navigationBarHeight) {
                                    z = false;
                                }
                                KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, i);
                            }
                        }
                    }
                }
            }
        };
        this.mActivity = activity;
        this.mWindow = dialog != null ? dialog.getWindow() : activity.getWindow();
        this.mDecorView = this.mWindow.getDecorView();
        this.mContentView = view == null ? this.mWindow.getDecorView().findViewById(16908290) : view;
        if (dialog != null) {
            barParams = ImmersionBar.with(activity, dialog, str).getBarParams();
        } else {
            barParams = ImmersionBar.with(activity).getBarParams();
        }
        this.mBarParams = barParams;
        if (this.mBarParams == null) {
            throw new IllegalArgumentException("先使用ImmersionBar初始化");
        }
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private KeyboardPatch(android.app.Activity r2, android.view.Window r3) {
        /*
            r1 = this;
            r1.<init>()
            com.gyf.barlibrary.KeyboardPatch$1 r0 = new com.gyf.barlibrary.KeyboardPatch$1
            r0.<init>()
            r1.onGlobalLayoutListener = r0
            r1.mActivity = r2
            r1.mWindow = r3
            android.view.Window r2 = r1.mWindow
            android.view.View r2 = r2.getDecorView()
            r1.mDecorView = r2
            android.view.View r2 = r1.mDecorView
            r3 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r2 = r2.findViewById(r3)
            android.widget.FrameLayout r2 = (android.widget.FrameLayout) r2
            r3 = 0
            android.view.View r3 = r2.getChildAt(r3)
            r1.mChildView = r3
            android.view.View r3 = r1.mChildView
            if (r3 == 0) goto L_0x002d
            r2 = r3
        L_0x002d:
            r1.mContentView = r2
            android.view.View r2 = r1.mContentView
            int r2 = r2.getPaddingLeft()
            r1.paddingLeft = r2
            android.view.View r2 = r1.mContentView
            int r2 = r2.getPaddingTop()
            r1.paddingTop = r2
            android.view.View r2 = r1.mContentView
            int r2 = r2.getPaddingRight()
            r1.paddingRight = r2
            android.view.View r2 = r1.mContentView
            int r2 = r2.getPaddingBottom()
            r1.paddingBottom = r2
            com.gyf.barlibrary.BarConfig r2 = new com.gyf.barlibrary.BarConfig
            android.app.Activity r3 = r1.mActivity
            r2.<init>(r3)
            int r3 = r2.getStatusBarHeight()
            r1.statusBarHeight = r3
            int r3 = r2.getNavigationBarHeight()
            r1.navigationBarHeight = r3
            int r3 = r2.getActionBarHeight()
            r1.actionBarHeight = r3
            boolean r2 = r2.isNavigationAtBottom()
            r1.navigationAtBottom = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gyf.barlibrary.KeyboardPatch.<init>(android.app.Activity, android.view.Window):void");
    }

    public static KeyboardPatch patch(Activity activity) {
        return new KeyboardPatch(activity);
    }

    public static KeyboardPatch patch(Activity activity, View view) {
        return new KeyboardPatch(activity, view);
    }

    public static KeyboardPatch patch(Activity activity, Dialog dialog, String str) {
        return new KeyboardPatch(activity, dialog, str);
    }

    public static KeyboardPatch patch(Activity activity, Dialog dialog, String str, View view) {
        return new KeyboardPatch(activity, dialog, str, view);
    }

    protected static KeyboardPatch patch(Activity activity, Window window) {
        return new KeyboardPatch(activity, window);
    }

    /* access modifiers changed from: protected */
    public void setBarParams(BarParams barParams) {
        this.mBarParams = barParams;
    }

    public void enable() {
        enable(18);
    }

    public void enable(int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWindow.setSoftInputMode(i);
            this.mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }

    public void disable() {
        disable(18);
    }

    public void disable(int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWindow.setSoftInputMode(i);
            this.mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }
}
