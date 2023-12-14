package com.szacs.ferroliconnect.widget.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.BaseUtil;

public abstract class BasePopWindow {
    protected Activity activity;
    private int animStyle = C1683R.style.Dialog_Anim_Scale_center;
    private PopupWindow basePop;
    private LayoutInflater inflater;
    private boolean isChangeBg = true;
    private boolean isDrawDown = false;
    private boolean outsideTouchable = true;
    private int popHeight = -2;
    private int popWidth = -2;
    private int position = 17;
    private View relateView;
    private int xOffSet = 0;
    private int yOffSet = 0;

    public static class OperationPopListener implements PopListener {
        public void onCancel() {
        }

        public void onDismiss() {
        }

        public void onSure(Object... objArr) {
        }
    }

    public interface PopListener {
        void onCancel();

        void onDismiss();

        void onSure(Object... objArr);
    }

    /* access modifiers changed from: protected */
    public abstract int getPopLayout();

    /* access modifiers changed from: protected */
    public abstract void initView(View view);

    /* access modifiers changed from: protected */
    public abstract <D> void setPopView(OperationPopListener operationPopListener, D... dArr);

    public BasePopWindow(Activity activity2) {
        this.activity = activity2;
        this.inflater = activity2.getLayoutInflater();
    }

    public <D> void initPopShow(final OperationPopListener operationPopListener, D... dArr) {
        View view;
        if (!isShow()) {
            if (this.basePop == null) {
                View inflate = this.inflater.inflate(getPopLayout(), (ViewGroup) null);
                initView(inflate);
                this.basePop = new PopupWindow(inflate, this.popWidth, this.popHeight, true);
                if (this.isChangeBg) {
                    this.basePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        public void onDismiss() {
                            BaseUtil.changeAlpha(BasePopWindow.this.activity, 1.0f);
                        }
                    });
                }
                this.basePop.setBackgroundDrawable(new ColorDrawable());
                this.basePop.setFocusable(this.outsideTouchable);
                this.basePop.setOutsideTouchable(this.outsideTouchable);
                this.basePop.setTouchable(true);
                this.basePop.setAnimationStyle(this.animStyle);
                this.basePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    public void onDismiss() {
                        BaseUtil.changeAlpha(BasePopWindow.this.activity, 1.0f);
                        OperationPopListener operationPopListener = operationPopListener;
                        if (operationPopListener != null) {
                            operationPopListener.onDismiss();
                        }
                    }
                });
            }
            setPopView(operationPopListener, dArr);
            if (this.isChangeBg) {
                BaseUtil.changeAlpha(this.activity, 0.6f);
            }
            if (!this.isDrawDown || (view = this.relateView) == null) {
                this.basePop.showAtLocation(this.activity.getWindow().getDecorView(), this.position, this.xOffSet, this.yOffSet);
            } else {
                this.basePop.showAsDropDown(view, this.xOffSet, this.yOffSet);
            }
        }
    }

    public BasePopWindow setDrawDown(boolean z) {
        this.isDrawDown = z;
        return this;
    }

    public BasePopWindow setRelateView(View view) {
        this.relateView = view;
        return this;
    }

    public BasePopWindow setXOffSet(int i) {
        this.xOffSet = i;
        return this;
    }

    public BasePopWindow setYOffSet(int i) {
        this.yOffSet = i;
        return this;
    }

    public int getyOffSet() {
        return this.yOffSet;
    }

    /* access modifiers changed from: protected */
    public BasePopWindow setPopWidth(int i) {
        this.popWidth = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public BasePopWindow setPopHeight(int i) {
        this.popHeight = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public BasePopWindow setAnimStyle(int i) {
        this.animStyle = i;
        return this;
    }

    /* access modifiers changed from: protected */
    public BasePopWindow setPosition(int i) {
        this.position = i;
        return this;
    }

    public BasePopWindow setOutsideTouchable(boolean z) {
        this.outsideTouchable = z;
        return this;
    }

    public void setChangeBg(boolean z) {
        this.isChangeBg = z;
    }

    public boolean isShow() {
        PopupWindow popupWindow = this.basePop;
        return popupWindow != null && popupWindow.isShowing();
    }

    public void dismissPop() {
        if (isShow()) {
            BaseUtil.changeAlpha(this.activity, 1.0f);
            this.basePop.dismiss();
        }
    }
}
