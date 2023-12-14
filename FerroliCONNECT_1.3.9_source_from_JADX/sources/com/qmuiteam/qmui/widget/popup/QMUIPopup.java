package com.qmuiteam.qmui.widget.popup;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.qmuiteam.qmui.C1614R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class QMUIPopup extends QMUIBasePopup {
    public static final int ANIM_AUTO = 4;
    public static final int ANIM_GROW_FROM_CENTER = 3;
    public static final int ANIM_GROW_FROM_LEFT = 1;
    public static final int ANIM_GROW_FROM_RIGHT = 2;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_NONE = 2;
    public static final int DIRECTION_TOP = 0;
    protected int mAnimStyle;
    protected int mArrowCenter;
    protected ImageView mArrowDown;
    protected ImageView mArrowUp;
    protected int mDirection;
    private int mOffsetX;
    private int mOffsetYWhenBottom;
    private int mOffsetYWhenTop;
    private int mPopupLeftRightMinMargin;
    private int mPopupTopBottomMinMargin;
    private int mPreferredDirection;

    /* renamed from: mX */
    protected int f3105mX;

    /* renamed from: mY */
    protected int f3106mY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    /* access modifiers changed from: protected */
    public void onWindowSizeChange() {
    }

    public QMUIPopup(Context context) {
        this(context, 2);
    }

    public QMUIPopup(Context context, int i) {
        super(context);
        this.f3105mX = -1;
        this.f3106mY = -1;
        this.mPopupLeftRightMinMargin = 0;
        this.mPopupTopBottomMinMargin = 0;
        this.mOffsetX = 0;
        this.mOffsetYWhenTop = 0;
        this.mOffsetYWhenBottom = 0;
        this.mAnimStyle = 4;
        this.mPreferredDirection = i;
        this.mDirection = this.mPreferredDirection;
    }

    public void setPopupLeftRightMinMargin(int i) {
        this.mPopupLeftRightMinMargin = i;
    }

    public void setPopupTopBottomMinMargin(int i) {
        this.mPopupTopBottomMinMargin = i;
    }

    public void setPositionOffsetX(int i) {
        this.mOffsetX = i;
    }

    public void setPositionOffsetYWhenTop(int i) {
        this.mOffsetYWhenTop = i;
    }

    public void setPositionOffsetYWhenBottom(int i) {
        this.mOffsetYWhenBottom = i;
    }

    public void setPreferredDirection(int i) {
        this.mPreferredDirection = i;
    }

    /* access modifiers changed from: protected */
    public Point onShowBegin(View view, View view2) {
        int i;
        calculatePosition(view2);
        showArrow();
        setAnimationStyle(this.mScreenSize.x, this.mArrowCenter);
        int i2 = this.mDirection;
        if (i2 == 0) {
            i = this.mOffsetYWhenTop;
        } else {
            i = i2 == 1 ? this.mOffsetYWhenBottom : 0;
        }
        return new Point(this.f3105mX + this.mOffsetX, this.f3106mY + i);
    }

    private void calculatePosition(View view) {
        if (view != null) {
            int[] iArr = new int[2];
            view.getLocationOnScreen(iArr);
            this.mArrowCenter = iArr[0] + (view.getWidth() / 2);
            if (this.mArrowCenter < this.mScreenSize.x / 2) {
                int i = this.mArrowCenter - (this.mWindowWidth / 2);
                int i2 = this.mPopupLeftRightMinMargin;
                if (i > i2) {
                    this.f3105mX = this.mArrowCenter - (this.mWindowWidth / 2);
                } else {
                    this.f3105mX = i2;
                }
            } else if (this.mArrowCenter + (this.mWindowWidth / 2) < this.mScreenSize.x - this.mPopupLeftRightMinMargin) {
                this.f3105mX = this.mArrowCenter - (this.mWindowWidth / 2);
            } else {
                this.f3105mX = (this.mScreenSize.x - this.mPopupLeftRightMinMargin) - this.mWindowWidth;
            }
            int i3 = this.mPreferredDirection;
            this.mDirection = i3;
            if (i3 == 0) {
                this.f3106mY = iArr[1] - this.mWindowHeight;
                if (this.f3106mY < this.mPopupTopBottomMinMargin) {
                    this.f3106mY = iArr[1] + view.getHeight();
                    this.mDirection = 1;
                }
            } else if (i3 == 1) {
                this.f3106mY = iArr[1] + view.getHeight();
                if (this.f3106mY > (this.mScreenSize.y - this.mPopupTopBottomMinMargin) - this.mWindowHeight) {
                    this.f3106mY = iArr[1] - this.mWindowHeight;
                    this.mDirection = 0;
                }
            } else if (i3 == 2) {
                this.f3106mY = iArr[1];
            }
        } else {
            this.f3105mX = (this.mScreenSize.x - this.mWindowWidth) / 2;
            this.f3106mY = (this.mScreenSize.y - this.mWindowHeight) / 2;
            this.mDirection = 2;
        }
    }

    private void setAnimationStyle(int i, int i2) {
        ImageView imageView = this.mArrowUp;
        if (imageView != null) {
            i2 -= imageView.getMeasuredWidth() / 2;
        }
        boolean z = this.mDirection == 0;
        int i3 = this.mAnimStyle;
        if (i3 == 1) {
            this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Left : C1614R.style.QMUI_Animation_PopDownMenu_Left);
        } else if (i3 == 2) {
            this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Right : C1614R.style.QMUI_Animation_PopDownMenu_Right);
        } else if (i3 == 3) {
            this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Center : C1614R.style.QMUI_Animation_PopDownMenu_Center);
        } else if (i3 == 4) {
            int i4 = i / 4;
            if (i2 <= i4) {
                this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Left : C1614R.style.QMUI_Animation_PopDownMenu_Left);
            } else if (i2 <= i4 || i2 >= i4 * 3) {
                this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Right : C1614R.style.QMUI_Animation_PopDownMenu_Right);
            } else {
                this.mWindow.setAnimationStyle(z ? C1614R.style.QMUI_Animation_PopUpMenu_Center : C1614R.style.QMUI_Animation_PopDownMenu_Center);
            }
        }
    }

    private void showArrow() {
        ImageView imageView;
        int i = this.mDirection;
        if (i == 0) {
            setViewVisibility(this.mArrowDown, true);
            setViewVisibility(this.mArrowUp, false);
            imageView = this.mArrowDown;
        } else if (i != 1) {
            if (i == 2) {
                setViewVisibility(this.mArrowDown, false);
                setViewVisibility(this.mArrowUp, false);
            }
            imageView = null;
        } else {
            setViewVisibility(this.mArrowUp, true);
            setViewVisibility(this.mArrowDown, false);
            imageView = this.mArrowUp;
        }
        if (imageView != null) {
            ((ViewGroup.MarginLayoutParams) imageView.getLayoutParams()).leftMargin = (this.mArrowCenter - this.f3105mX) - (this.mArrowUp.getMeasuredWidth() / 2);
        }
    }

    public void setAnimStyle(int i) {
        this.mAnimStyle = i;
    }

    public void setContentView(View view) {
        FrameLayout frameLayout = (FrameLayout) LayoutInflater.from(this.mContext).inflate(getRootLayout(), (ViewGroup) null, false);
        this.mArrowDown = (ImageView) frameLayout.findViewById(C1614R.C1616id.arrow_down);
        this.mArrowUp = (ImageView) frameLayout.findViewById(C1614R.C1616id.arrow_up);
        ((FrameLayout) frameLayout.findViewById(C1614R.C1616id.box)).addView(view);
        super.setContentView((View) frameLayout);
    }

    /* access modifiers changed from: protected */
    @LayoutRes
    public int getRootLayout() {
        return C1614R.C1617layout.qmui_popup_layout;
    }

    private void setViewVisibility(View view, boolean z) {
        if (view != null) {
            view.setVisibility(z ? 0 : 4);
        }
    }

    public ViewGroup.LayoutParams generateLayoutParam(int i, int i2) {
        return new FrameLayout.LayoutParams(i, i2);
    }
}
