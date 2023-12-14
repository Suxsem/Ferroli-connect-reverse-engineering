package com.qmuiteam.qmui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.content.res.AppCompatResources;
import android.support.p003v7.widget.AppCompatImageView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.QMUILog;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class QMUIBottomSheet extends Dialog {
    private static final String TAG = "QMUIBottomSheet";
    private static final int mAnimationDuration = 200;
    /* access modifiers changed from: private */
    public View mContentView;
    /* access modifiers changed from: private */
    public boolean mIsAnimating = false;
    private OnBottomSheetShowListener mOnBottomSheetShowListener;

    public interface OnBottomSheetShowListener {
        void onShow();
    }

    public QMUIBottomSheet(Context context) {
        super(context, C1614R.style.QMUI_BottomSheet);
    }

    public void setOnBottomSheetShowListener(OnBottomSheetShowListener onBottomSheetShowListener) {
        this.mOnBottomSheetShowListener = onBottomSheetShowListener;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -2;
        attributes.gravity = 81;
        int screenWidth = QMUIDisplayHelper.getScreenWidth(getContext());
        int screenHeight = QMUIDisplayHelper.getScreenHeight(getContext());
        if (screenWidth >= screenHeight) {
            screenWidth = screenHeight;
        }
        attributes.width = screenWidth;
        getWindow().setAttributes(attributes);
        setCanceledOnTouchOutside(true);
    }

    public void setContentView(int i) {
        this.mContentView = LayoutInflater.from(getContext()).inflate(i, (ViewGroup) null);
        super.setContentView(this.mContentView);
    }

    public void setContentView(@NonNull View view, ViewGroup.LayoutParams layoutParams) {
        this.mContentView = view;
        super.setContentView(view, layoutParams);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void setContentView(@NonNull View view) {
        this.mContentView = view;
        super.setContentView(view);
    }

    private void animateUp() {
        if (this.mContentView != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(200);
            animationSet.setFillAfter(true);
            this.mContentView.startAnimation(animationSet);
        }
    }

    private void animateDown() {
        if (this.mContentView != null) {
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(translateAnimation);
            animationSet.addAnimation(alphaAnimation);
            animationSet.setInterpolator(new DecelerateInterpolator());
            animationSet.setDuration(200);
            animationSet.setFillAfter(true);
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    boolean unused = QMUIBottomSheet.this.mIsAnimating = true;
                }

                public void onAnimationEnd(Animation animation) {
                    boolean unused = QMUIBottomSheet.this.mIsAnimating = false;
                    QMUIBottomSheet.this.mContentView.post(new Runnable() {
                        public void run() {
                            try {
                                C16561.super.dismiss();
                            } catch (Exception e) {
                                QMUILog.m3309w(QMUIBottomSheet.TAG, "dismiss error\n" + Log.getStackTraceString(e), new Object[0]);
                            }
                        }
                    });
                }
            });
            this.mContentView.startAnimation(animationSet);
        }
    }

    public void show() {
        super.show();
        animateUp();
        OnBottomSheetShowListener onBottomSheetShowListener = this.mOnBottomSheetShowListener;
        if (onBottomSheetShowListener != null) {
            onBottomSheetShowListener.onShow();
        }
    }

    public void dismiss() {
        if (!this.mIsAnimating) {
            animateDown();
        }
    }

    public static class BottomListSheetBuilder {
        private BaseAdapter mAdapter;
        /* access modifiers changed from: private */
        public int mCheckedIndex;
        /* access modifiers changed from: private */
        public ListView mContainerView;
        /* access modifiers changed from: private */
        public Context mContext;
        /* access modifiers changed from: private */
        public QMUIBottomSheet mDialog;
        private List<View> mHeaderViews;
        /* access modifiers changed from: private */
        public List<BottomSheetListItemData> mItems;
        /* access modifiers changed from: private */
        public boolean mNeedRightMark;
        private DialogInterface.OnDismissListener mOnBottomDialogDismissListener;
        /* access modifiers changed from: private */
        public OnSheetItemClickListener mOnSheetItemClickListener;
        private String mTitle;
        private TextView mTitleTv;

        public interface OnSheetItemClickListener {
            void onClick(QMUIBottomSheet qMUIBottomSheet, View view, int i, String str);
        }

        public BottomListSheetBuilder(Context context) {
            this(context, false);
        }

        public BottomListSheetBuilder(Context context, boolean z) {
            this.mContext = context;
            this.mItems = new ArrayList();
            this.mHeaderViews = new ArrayList();
            this.mNeedRightMark = z;
        }

        public BottomListSheetBuilder setCheckedIndex(int i) {
            this.mCheckedIndex = i;
            return this;
        }

        public BottomListSheetBuilder addItem(String str) {
            this.mItems.add(new BottomSheetListItemData(str, str));
            return this;
        }

        public BottomListSheetBuilder addItem(Drawable drawable, String str) {
            this.mItems.add(new BottomSheetListItemData(drawable, str, str));
            return this;
        }

        public BottomListSheetBuilder addItem(String str, String str2) {
            this.mItems.add(new BottomSheetListItemData(str, str2));
            return this;
        }

        public BottomListSheetBuilder addItem(int i, String str, String str2) {
            this.mItems.add(new BottomSheetListItemData(i != 0 ? ContextCompat.getDrawable(this.mContext, i) : null, str, str2));
            return this;
        }

        public BottomListSheetBuilder addItem(int i, String str, String str2, boolean z) {
            this.mItems.add(new BottomSheetListItemData(i != 0 ? ContextCompat.getDrawable(this.mContext, i) : null, str, str2, z));
            return this;
        }

        public BottomListSheetBuilder addItem(int i, String str, String str2, boolean z, boolean z2) {
            this.mItems.add(new BottomSheetListItemData(i != 0 ? ContextCompat.getDrawable(this.mContext, i) : null, str, str2, z, z2));
            return this;
        }

        public BottomListSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            this.mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public BottomListSheetBuilder setOnBottomDialogDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.mOnBottomDialogDismissListener = onDismissListener;
            return this;
        }

        public BottomListSheetBuilder addHeaderView(View view) {
            if (view != null) {
                this.mHeaderViews.add(view);
            }
            return this;
        }

        public BottomListSheetBuilder setTitle(String str) {
            this.mTitle = str;
            return this;
        }

        public BottomListSheetBuilder setTitle(int i) {
            this.mTitle = this.mContext.getResources().getString(i);
            return this;
        }

        public QMUIBottomSheet build() {
            this.mDialog = new QMUIBottomSheet(this.mContext);
            this.mDialog.setContentView(buildViews(), new ViewGroup.LayoutParams(-1, -2));
            DialogInterface.OnDismissListener onDismissListener = this.mOnBottomDialogDismissListener;
            if (onDismissListener != null) {
                this.mDialog.setOnDismissListener(onDismissListener);
            }
            return this.mDialog;
        }

        private View buildViews() {
            View inflate = View.inflate(this.mContext, getContentViewLayoutId(), (ViewGroup) null);
            this.mTitleTv = (TextView) inflate.findViewById(C1614R.C1616id.title);
            this.mContainerView = (ListView) inflate.findViewById(C1614R.C1616id.listview);
            String str = this.mTitle;
            if (str == null || str.length() == 0) {
                this.mTitleTv.setVisibility(8);
            } else {
                this.mTitleTv.setVisibility(0);
                this.mTitleTv.setText(this.mTitle);
            }
            if (this.mHeaderViews.size() > 0) {
                for (View addHeaderView : this.mHeaderViews) {
                    this.mContainerView.addHeaderView(addHeaderView);
                }
            }
            if (needToScroll()) {
                this.mContainerView.getLayoutParams().height = getListMaxHeight();
                this.mDialog.setOnBottomSheetShowListener(new OnBottomSheetShowListener() {
                    public void onShow() {
                        BottomListSheetBuilder.this.mContainerView.setSelection(BottomListSheetBuilder.this.mCheckedIndex);
                    }
                });
            }
            this.mAdapter = new ListAdapter();
            this.mContainerView.setAdapter(this.mAdapter);
            return inflate;
        }

        private boolean needToScroll() {
            int size = this.mItems.size() * QMUIResHelper.getAttrDimen(this.mContext, C1614R.attr.qmui_bottom_sheet_list_item_height);
            if (this.mHeaderViews.size() > 0) {
                for (View next : this.mHeaderViews) {
                    if (next.getMeasuredHeight() == 0) {
                        next.measure(0, 0);
                    }
                    size += next.getMeasuredHeight();
                }
            }
            if (this.mTitleTv != null && !QMUILangHelper.isNullOrEmpty(this.mTitle)) {
                size += QMUIResHelper.getAttrDimen(this.mContext, C1614R.attr.qmui_bottom_sheet_title_height);
            }
            if (size > getListMaxHeight()) {
                return true;
            }
            return false;
        }

        /* access modifiers changed from: protected */
        public int getListMaxHeight() {
            double screenHeight = (double) QMUIDisplayHelper.getScreenHeight(this.mContext);
            Double.isNaN(screenHeight);
            return (int) (screenHeight * 0.5d);
        }

        public void notifyDataSetChanged() {
            BaseAdapter baseAdapter = this.mAdapter;
            if (baseAdapter != null) {
                baseAdapter.notifyDataSetChanged();
            }
            if (needToScroll()) {
                this.mContainerView.getLayoutParams().height = getListMaxHeight();
                this.mContainerView.setSelection(this.mCheckedIndex);
            }
        }

        /* access modifiers changed from: protected */
        public int getContentViewLayoutId() {
            return C1614R.C1617layout.qmui_bottom_sheet_list;
        }

        private static class BottomSheetListItemData {
            boolean hasRedPoint = false;
            Drawable image = null;
            boolean isDisabled = false;
            String tag = "";
            String text;

            public BottomSheetListItemData(String str, String str2) {
                this.text = str;
                this.tag = str2;
            }

            public BottomSheetListItemData(Drawable drawable, String str, String str2) {
                this.image = drawable;
                this.text = str;
                this.tag = str2;
            }

            public BottomSheetListItemData(Drawable drawable, String str, String str2, boolean z) {
                this.image = drawable;
                this.text = str;
                this.tag = str2;
                this.hasRedPoint = z;
            }

            public BottomSheetListItemData(Drawable drawable, String str, String str2, boolean z, boolean z2) {
                this.image = drawable;
                this.text = str;
                this.tag = str2;
                this.hasRedPoint = z;
                this.isDisabled = z2;
            }
        }

        private static class ViewHolder {
            ImageView imageView;
            View markView;
            View redPoint;
            TextView textView;

            private ViewHolder() {
            }
        }

        private class ListAdapter extends BaseAdapter {
            public long getItemId(int i) {
                return 0;
            }

            private ListAdapter() {
            }

            public int getCount() {
                return BottomListSheetBuilder.this.mItems.size();
            }

            public BottomSheetListItemData getItem(int i) {
                return (BottomSheetListItemData) BottomListSheetBuilder.this.mItems.get(i);
            }

            public View getView(final int i, View view, ViewGroup viewGroup) {
                final ViewHolder viewHolder;
                final BottomSheetListItemData item = getItem(i);
                if (view == null) {
                    view = LayoutInflater.from(BottomListSheetBuilder.this.mContext).inflate(C1614R.C1617layout.qmui_bottom_sheet_list_item, viewGroup, false);
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) view.findViewById(C1614R.C1616id.bottom_dialog_list_item_img);
                    viewHolder.textView = (TextView) view.findViewById(C1614R.C1616id.bottom_dialog_list_item_title);
                    viewHolder.markView = view.findViewById(C1614R.C1616id.bottom_dialog_list_item_mark_view_stub);
                    viewHolder.redPoint = view.findViewById(C1614R.C1616id.bottom_dialog_list_item_point);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                if (item.image != null) {
                    viewHolder.imageView.setVisibility(0);
                    viewHolder.imageView.setImageDrawable(item.image);
                } else {
                    viewHolder.imageView.setVisibility(8);
                }
                viewHolder.textView.setText(item.text);
                if (item.hasRedPoint) {
                    viewHolder.redPoint.setVisibility(0);
                } else {
                    viewHolder.redPoint.setVisibility(8);
                }
                if (item.isDisabled) {
                    viewHolder.textView.setEnabled(false);
                    view.setEnabled(false);
                } else {
                    viewHolder.textView.setEnabled(true);
                    view.setEnabled(true);
                }
                if (BottomListSheetBuilder.this.mNeedRightMark) {
                    if (viewHolder.markView instanceof ViewStub) {
                        viewHolder.markView = ((ViewStub) viewHolder.markView).inflate();
                    }
                    if (BottomListSheetBuilder.this.mCheckedIndex == i) {
                        viewHolder.markView.setVisibility(0);
                    } else {
                        viewHolder.markView.setVisibility(8);
                    }
                } else {
                    viewHolder.markView.setVisibility(8);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (item.hasRedPoint) {
                            item.hasRedPoint = false;
                            viewHolder.redPoint.setVisibility(8);
                        }
                        if (BottomListSheetBuilder.this.mNeedRightMark) {
                            BottomListSheetBuilder.this.setCheckedIndex(i);
                            ListAdapter.this.notifyDataSetChanged();
                        }
                        if (BottomListSheetBuilder.this.mOnSheetItemClickListener != null) {
                            BottomListSheetBuilder.this.mOnSheetItemClickListener.onClick(BottomListSheetBuilder.this.mDialog, view, i, item.tag);
                        }
                    }
                });
                return view;
            }
        }
    }

    public static class BottomGridSheetBuilder implements View.OnClickListener {
        public static final int FIRST_LINE = 0;
        public static final int SECOND_LINE = 1;
        private TextView mBottomButton;
        private ViewGroup mBottomButtonContainer;
        private Typeface mBottomButtonTypeFace = null;
        private View.OnClickListener mButtonClickListener = null;
        private CharSequence mButtonText = null;
        private Context mContext;
        /* access modifiers changed from: private */
        public QMUIBottomSheet mDialog;
        private SparseArray<View> mFirstLineViews;
        private boolean mIsShowButton = true;
        private Typeface mItemTextTypeFace = null;
        private int mMiniItemWidth = -1;
        private OnSheetItemClickListener mOnSheetItemClickListener;
        private SparseArray<View> mSecondLineViews;

        public interface OnSheetItemClickListener {
            void onClick(QMUIBottomSheet qMUIBottomSheet, View view);
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface Style {
        }

        public BottomGridSheetBuilder(Context context) {
            this.mContext = context;
            this.mFirstLineViews = new SparseArray<>();
            this.mSecondLineViews = new SparseArray<>();
        }

        public BottomGridSheetBuilder addItem(int i, CharSequence charSequence, int i2) {
            return addItem(i, charSequence, charSequence, i2, 0);
        }

        public BottomGridSheetBuilder addItem(int i, CharSequence charSequence, Object obj, int i2) {
            return addItem(i, charSequence, obj, i2, 0);
        }

        public BottomGridSheetBuilder setIsShowButton(boolean z) {
            this.mIsShowButton = z;
            return this;
        }

        public BottomGridSheetBuilder setButtonText(CharSequence charSequence) {
            this.mButtonText = charSequence;
            return this;
        }

        public BottomGridSheetBuilder setButtonClickListener(View.OnClickListener onClickListener) {
            this.mButtonClickListener = onClickListener;
            return this;
        }

        public BottomGridSheetBuilder setItemTextTypeFace(Typeface typeface) {
            this.mItemTextTypeFace = typeface;
            return this;
        }

        public BottomGridSheetBuilder setBottomButtonTypeFace(Typeface typeface) {
            this.mBottomButtonTypeFace = typeface;
            return this;
        }

        public BottomGridSheetBuilder addItem(int i, CharSequence charSequence, Object obj, int i2, int i3) {
            return addItem(createItemView(AppCompatResources.getDrawable(this.mContext, i), charSequence, obj, i3), i2);
        }

        public BottomGridSheetBuilder addItem(View view, int i) {
            if (i == 0) {
                SparseArray<View> sparseArray = this.mFirstLineViews;
                sparseArray.append(sparseArray.size(), view);
            } else if (i == 1) {
                SparseArray<View> sparseArray2 = this.mSecondLineViews;
                sparseArray2.append(sparseArray2.size(), view);
            }
            return this;
        }

        public QMUIBottomSheetItemView createItemView(Drawable drawable, CharSequence charSequence, Object obj, int i) {
            QMUIBottomSheetItemView qMUIBottomSheetItemView = (QMUIBottomSheetItemView) LayoutInflater.from(this.mContext).inflate(getItemViewLayoutId(), (ViewGroup) null, false);
            TextView textView = (TextView) qMUIBottomSheetItemView.findViewById(C1614R.C1616id.grid_item_title);
            Typeface typeface = this.mItemTextTypeFace;
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            textView.setText(charSequence);
            qMUIBottomSheetItemView.setTag(obj);
            qMUIBottomSheetItemView.setOnClickListener(this);
            ((AppCompatImageView) qMUIBottomSheetItemView.findViewById(C1614R.C1616id.grid_item_image)).setImageDrawable(drawable);
            if (i != 0) {
                ((ImageView) ((ViewStub) qMUIBottomSheetItemView.findViewById(C1614R.C1616id.grid_item_subscript)).inflate()).setImageResource(i);
            }
            return qMUIBottomSheetItemView;
        }

        public void setItemVisibility(Object obj, int i) {
            View view = null;
            for (int i2 = 0; i2 < this.mFirstLineViews.size(); i2++) {
                View view2 = this.mFirstLineViews.get(i2);
                if (view2 != null && view2.getTag().equals(obj)) {
                    view = view2;
                }
            }
            for (int i3 = 0; i3 < this.mSecondLineViews.size(); i3++) {
                View view3 = this.mSecondLineViews.get(i3);
                if (view3 != null && view3.getTag().equals(obj)) {
                    view = view3;
                }
            }
            if (view != null) {
                view.setVisibility(i);
            }
        }

        public BottomGridSheetBuilder setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
            this.mOnSheetItemClickListener = onSheetItemClickListener;
            return this;
        }

        public void onClick(View view) {
            OnSheetItemClickListener onSheetItemClickListener = this.mOnSheetItemClickListener;
            if (onSheetItemClickListener != null) {
                onSheetItemClickListener.onClick(this.mDialog, view);
            }
        }

        public QMUIBottomSheet build() {
            this.mDialog = new QMUIBottomSheet(this.mContext);
            this.mDialog.setContentView(buildViews(), new ViewGroup.LayoutParams(-1, -2));
            return this.mDialog;
        }

        private View buildViews() {
            LinearLayout linearLayout = (LinearLayout) View.inflate(this.mContext, getContentViewLayoutId(), (ViewGroup) null);
            LinearLayout linearLayout2 = (LinearLayout) linearLayout.findViewById(C1614R.C1616id.bottom_sheet_first_linear_layout);
            LinearLayout linearLayout3 = (LinearLayout) linearLayout.findViewById(C1614R.C1616id.bottom_sheet_second_linear_layout);
            this.mBottomButtonContainer = (ViewGroup) linearLayout.findViewById(C1614R.C1616id.bottom_sheet_button_container);
            this.mBottomButton = (TextView) linearLayout.findViewById(C1614R.C1616id.bottom_sheet_close_button);
            int max = Math.max(this.mFirstLineViews.size(), this.mSecondLineViews.size());
            int screenWidth = QMUIDisplayHelper.getScreenWidth(this.mContext);
            int screenHeight = QMUIDisplayHelper.getScreenHeight(this.mContext);
            if (screenWidth >= screenHeight) {
                screenWidth = screenHeight;
            }
            int calculateItemWidth = calculateItemWidth(screenWidth, max, linearLayout2.getPaddingLeft(), linearLayout2.getPaddingRight());
            addViewsInSection(this.mFirstLineViews, linearLayout2, calculateItemWidth);
            addViewsInSection(this.mSecondLineViews, linearLayout3, calculateItemWidth);
            boolean z = true;
            boolean z2 = this.mFirstLineViews.size() > 0;
            if (this.mSecondLineViews.size() <= 0) {
                z = false;
            }
            if (!z2) {
                linearLayout2.setVisibility(8);
            }
            if (!z) {
                if (z2) {
                    linearLayout2.setPadding(linearLayout2.getPaddingLeft(), linearLayout2.getPaddingTop(), linearLayout2.getPaddingRight(), 0);
                }
                linearLayout3.setVisibility(8);
            }
            ViewGroup viewGroup = this.mBottomButtonContainer;
            if (viewGroup != null) {
                if (this.mIsShowButton) {
                    viewGroup.setVisibility(0);
                    linearLayout.setPadding(0, QMUIResHelper.getAttrDimen(this.mContext, C1614R.attr.qmui_bottom_sheet_grid_padding_vertical), 0, 0);
                } else {
                    viewGroup.setVisibility(8);
                }
                Typeface typeface = this.mBottomButtonTypeFace;
                if (typeface != null) {
                    this.mBottomButton.setTypeface(typeface);
                }
                CharSequence charSequence = this.mButtonText;
                if (charSequence != null) {
                    this.mBottomButton.setText(charSequence);
                }
                View.OnClickListener onClickListener = this.mButtonClickListener;
                if (onClickListener != null) {
                    this.mBottomButton.setOnClickListener(onClickListener);
                } else {
                    this.mBottomButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            BottomGridSheetBuilder.this.mDialog.dismiss();
                        }
                    });
                }
            }
            return linearLayout;
        }

        /* access modifiers changed from: protected */
        public int getContentViewLayoutId() {
            return C1614R.C1617layout.qmui_bottom_sheet_grid;
        }

        /* access modifiers changed from: protected */
        public int getItemViewLayoutId() {
            return C1614R.C1617layout.qmui_bottom_sheet_grid_item;
        }

        private int calculateItemWidth(int i, int i2, int i3, int i4) {
            int i5;
            if (this.mMiniItemWidth == -1) {
                this.mMiniItemWidth = QMUIResHelper.getAttrDimen(this.mContext, C1614R.attr.qmui_bottom_sheet_grid_item_mini_width);
            }
            int i6 = i - i3;
            int i7 = i6 - i4;
            int i8 = this.mMiniItemWidth;
            if (i2 >= 3 && (i5 = i7 - (i2 * i8)) > 0 && i5 < i8) {
                i8 = i7 / (i7 / i8);
            }
            if (i2 * i8 <= i7) {
                return i8;
            }
            return (int) (((float) i6) / (((float) (i6 / i8)) + 0.5f));
        }

        private void addViewsInSection(SparseArray<View> sparseArray, LinearLayout linearLayout, int i) {
            for (int i2 = 0; i2 < sparseArray.size(); i2++) {
                View view = sparseArray.get(i2);
                setItemWidth(view, i);
                linearLayout.addView(view);
            }
        }

        private void setItemWidth(View view, int i) {
            LinearLayout.LayoutParams layoutParams;
            if (view.getLayoutParams() != null) {
                layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams.width = i;
            } else {
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(i, -2);
                view.setLayoutParams(layoutParams2);
                layoutParams = layoutParams2;
            }
            layoutParams.gravity = 48;
        }
    }
}
