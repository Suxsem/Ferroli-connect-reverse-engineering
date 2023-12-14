package com.szacs.ferroliconnect.widget.Spinner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.graphics.drawable.DrawableCompat;
import android.support.p000v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import java.util.List;

public class NiceSpinner extends TextView {
    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";
    private static final int MAX_LEVEL = 10000;
    private static final String SELECTED_INDEX = "selected_index";
    /* access modifiers changed from: private */
    public NiceSpinnerBaseAdapter adapter;
    private int backgroundSelector;
    private Drawable drawable;
    /* access modifiers changed from: private */
    public boolean isArrowHide;
    private ListView listView;
    /* access modifiers changed from: private */
    public AdapterView.OnItemClickListener onItemClickListener;
    /* access modifiers changed from: private */
    public AdapterView.OnItemSelectedListener onItemSelectedListener;
    private PopupWindow popupWindow;
    /* access modifiers changed from: private */
    public int selectedIndex;
    private int textColor;

    public NiceSpinner(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public NiceSpinner(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public NiceSpinner(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, this.selectedIndex);
        PopupWindow popupWindow2 = this.popupWindow;
        if (popupWindow2 != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow2.isShowing());
            dismissDropDown();
        }
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.selectedIndex = bundle.getInt(SELECTED_INDEX);
            NiceSpinnerBaseAdapter niceSpinnerBaseAdapter = this.adapter;
            if (niceSpinnerBaseAdapter != null) {
                setText(niceSpinnerBaseAdapter.getItemInDataset(this.selectedIndex).toString());
                this.adapter.notifyItemSelected(this.selectedIndex);
            }
            if (bundle.getBoolean(IS_POPUP_SHOWING) && this.popupWindow != null) {
                post(new Runnable() {
                    public void run() {
                        NiceSpinner.this.showDropDown();
                    }
                });
            }
            parcelable = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(parcelable);
    }

    private void init(Context context, AttributeSet attributeSet) {
        Resources resources = getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1683R.styleable.NiceSpinner);
        int dimensionPixelSize = resources.getDimensionPixelSize(C1683R.dimen.one_and_a_half_grid_unit);
        setGravity(8388627);
        setPadding(resources.getDimensionPixelSize(C1683R.dimen.three_grid_unit), dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        setClickable(true);
        this.backgroundSelector = obtainStyledAttributes.getResourceId(1, C1683R.C1684drawable.selector);
        setBackgroundResource(this.backgroundSelector);
        this.textColor = obtainStyledAttributes.getColor(3, -1);
        setTextColor(this.textColor);
        this.listView = new ListView(context);
        this.listView.setId(getId());
        this.listView.setDivider((Drawable) null);
        this.listView.setItemsCanFocus(true);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setHorizontalScrollBarEnabled(false);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (i >= NiceSpinner.this.selectedIndex && i < NiceSpinner.this.adapter.getCount()) {
                    i++;
                }
                int unused = NiceSpinner.this.selectedIndex = i;
                if (NiceSpinner.this.onItemClickListener != null) {
                    NiceSpinner.this.onItemClickListener.onItemClick(adapterView, view, i, j);
                }
                if (NiceSpinner.this.onItemSelectedListener != null) {
                    NiceSpinner.this.onItemSelectedListener.onItemSelected(adapterView, view, i, j);
                }
                NiceSpinner.this.adapter.notifyItemSelected(i);
                NiceSpinner niceSpinner = NiceSpinner.this;
                niceSpinner.setText(niceSpinner.adapter.getItemInDataset(i).toString());
                NiceSpinner.this.dismissDropDown();
            }
        });
        this.popupWindow = new PopupWindow(context);
        this.popupWindow.setContentView(this.listView);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        if (Build.VERSION.SDK_INT >= 21) {
            this.popupWindow.setElevation(16.0f);
            this.popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, C1683R.C1684drawable.spinner_drawable));
        } else {
            this.popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, C1683R.C1684drawable.drop_down_shadow));
        }
        this.popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                if (!NiceSpinner.this.isArrowHide) {
                    NiceSpinner.this.animateArrow(false);
                }
            }
        });
        this.isArrowHide = obtainStyledAttributes.getBoolean(2, false);
        if (!this.isArrowHide) {
            Drawable drawable2 = ContextCompat.getDrawable(context, C1683R.C1684drawable.arrow);
            int color = obtainStyledAttributes.getColor(0, -1);
            if (drawable2 != null) {
                this.drawable = DrawableCompat.wrap(drawable2);
                if (color != -1) {
                    DrawableCompat.setTint(this.drawable, color);
                }
            }
            setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, this.drawable, (Drawable) null);
        }
        obtainStyledAttributes.recycle();
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public void setSelectedIndex(int i) {
        NiceSpinnerBaseAdapter niceSpinnerBaseAdapter = this.adapter;
        if (niceSpinnerBaseAdapter == null) {
            return;
        }
        if (i < 0 || i > niceSpinnerBaseAdapter.getCount()) {
            throw new IllegalArgumentException("Position must be lower than adapter count!");
        }
        this.adapter.notifyItemSelected(i);
        this.selectedIndex = i;
        setText(this.adapter.getItemInDataset(i).toString());
    }

    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener onItemSelectedListener2) {
        this.onItemSelectedListener = onItemSelectedListener2;
    }

    public <T> void attachDataSource(@NonNull List<T> list) {
        this.adapter = new NiceSpinnerAdapter(getContext(), list, this.textColor, this.backgroundSelector);
        setAdapterInternal(this.adapter);
    }

    public void setAdapter(@NonNull ListAdapter listAdapter) {
        this.adapter = new NiceSpinnerAdapterWrapper(getContext(), listAdapter, this.textColor, this.backgroundSelector);
        setAdapterInternal(this.adapter);
    }

    private void setAdapterInternal(@NonNull NiceSpinnerBaseAdapter niceSpinnerBaseAdapter) {
        this.selectedIndex = 0;
        this.listView.setAdapter(niceSpinnerBaseAdapter);
        setText(niceSpinnerBaseAdapter.getItemInDataset(this.selectedIndex).toString());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        this.popupWindow.setWidth(View.MeasureSpec.getSize(i));
        this.popupWindow.setHeight(-2);
        super.onMeasure(i, i2);
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (!this.popupWindow.isShowing()) {
                showDropDown();
            } else {
                dismissDropDown();
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: private */
    public void animateArrow(boolean z) {
        int i = 10000;
        int i2 = z ? 0 : 10000;
        if (!z) {
            i = 0;
        }
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this.drawable, "level", new int[]{i2, i});
        ofInt.setInterpolator(new LinearOutSlowInInterpolator());
        ofInt.start();
    }

    public void dismissDropDown() {
        if (!this.isArrowHide) {
            animateArrow(false);
        }
        this.popupWindow.dismiss();
    }

    public void showDropDown() {
        if (!this.isArrowHide) {
            animateArrow(true);
        }
        this.popupWindow.showAsDropDown(this);
    }

    public void setTintColor(@ColorRes int i) {
        Drawable drawable2 = this.drawable;
        if (drawable2 != null && !this.isArrowHide) {
            DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), i));
        }
    }
}
