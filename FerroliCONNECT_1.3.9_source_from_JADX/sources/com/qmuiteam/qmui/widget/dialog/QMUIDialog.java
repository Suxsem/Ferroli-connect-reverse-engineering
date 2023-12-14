package com.qmuiteam.qmui.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUILangHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIWrapContentScrollView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogMenuItemView;
import com.qmuiteam.qmui.widget.textview.QMUISpanTouchFixTextView;
import java.util.ArrayList;
import java.util.Iterator;

public class QMUIDialog extends Dialog {
    private Context mBaseContext;
    boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private boolean mCanceledOnTouchOutsideSet;

    public QMUIDialog(Context context) {
        this(context, C1614R.style.QMUI_Dialog);
    }

    public QMUIDialog(Context context, int i) {
        super(context, i);
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mBaseContext = context;
        init();
    }

    private void init() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initDialog();
    }

    private void initDialog() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -1;
            attributes.gravity = 17;
            window.setAttributes(attributes);
        }
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
        this.mCancelable = z;
    }

    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mCanceledOnTouchOutside = z;
        this.mCanceledOnTouchOutsideSet = true;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldWindowCloseOnTouchOutside() {
        if (!this.mCanceledOnTouchOutsideSet) {
            if (Build.VERSION.SDK_INT < 11) {
                this.mCanceledOnTouchOutside = true;
            } else {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
                this.mCanceledOnTouchOutside = obtainStyledAttributes.getBoolean(0, true);
                obtainStyledAttributes.recycle();
            }
            this.mCanceledOnTouchOutsideSet = true;
        }
        return this.mCanceledOnTouchOutside;
    }

    /* access modifiers changed from: package-private */
    public void cancelOutSide() {
        if (this.mCancelable && isShowing() && shouldWindowCloseOnTouchOutside()) {
            cancel();
        }
    }

    public void showWithImmersiveCheck(Activity activity) {
        Window window = getWindow();
        if (window != null) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility() & 1024;
            if (systemUiVisibility == 1024 || systemUiVisibility == 1024) {
                window.setFlags(8, 8);
                window.getDecorView().setSystemUiVisibility(activity.getWindow().getDecorView().getSystemUiVisibility());
                super.show();
                window.clearFlags(8);
                return;
            }
            super.show();
        }
    }

    public void showWithImmersiveCheck() {
        Context context = this.mBaseContext;
        if (!(context instanceof Activity)) {
            super.show();
        } else {
            showWithImmersiveCheck((Activity) context);
        }
    }

    public void show() {
        super.show();
    }

    public static class MessageDialogBuilder extends QMUIDialogBuilder<MessageDialogBuilder> {
        protected CharSequence mMessage;
        private QMUIWrapContentScrollView mScrollContainer;
        private QMUISpanTouchFixTextView mTextView;

        public MessageDialogBuilder(Context context) {
            super(context);
        }

        public MessageDialogBuilder setMessage(CharSequence charSequence) {
            this.mMessage = charSequence;
            return this;
        }

        public MessageDialogBuilder setMessage(int i) {
            return setMessage((CharSequence) getBaseContext().getResources().getString(i));
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            CharSequence charSequence = this.mMessage;
            if (charSequence != null && charSequence.length() != 0) {
                this.mTextView = new QMUISpanTouchFixTextView(context);
                assignMessageTvWithAttr(this.mTextView, hasTitle(), C1614R.attr.qmui_dialog_message_content_style);
                this.mTextView.setText(this.mMessage);
                this.mTextView.setMovementMethodDefault();
                this.mScrollContainer = new QMUIWrapContentScrollView(context);
                this.mScrollContainer.setMaxHeight(getContentAreaMaxHeight());
                this.mScrollContainer.setVerticalScrollBarEnabled(false);
                this.mScrollContainer.addView(this.mTextView);
                viewGroup.addView(this.mScrollContainer);
            }
        }

        /* access modifiers changed from: protected */
        public void onConfigTitleView(TextView textView) {
            super.onConfigTitleView(textView);
            CharSequence charSequence = this.mMessage;
            if (charSequence == null || charSequence.length() == 0) {
                TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes((AttributeSet) null, C1614R.styleable.QMUIDialogTitleTvCustomDef, C1614R.attr.qmui_dialog_title_style, 0);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i = 0; i < indexCount; i++) {
                    int index = obtainStyledAttributes.getIndex(i);
                    if (index == C1614R.styleable.QMUIDialogTitleTvCustomDef_qmui_paddingBottomWhenNotContent) {
                        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), obtainStyledAttributes.getDimensionPixelSize(index, textView.getPaddingBottom()));
                    }
                }
                obtainStyledAttributes.recycle();
            }
        }

        public QMUISpanTouchFixTextView getTextView() {
            return this.mTextView;
        }

        public static void assignMessageTvWithAttr(TextView textView, boolean z, int i) {
            QMUIResHelper.assignTextViewWithAttr(textView, i);
            if (!z) {
                TypedArray obtainStyledAttributes = textView.getContext().obtainStyledAttributes((AttributeSet) null, C1614R.styleable.QMUIDialogMessageTvCustomDef, i, 0);
                int indexCount = obtainStyledAttributes.getIndexCount();
                for (int i2 = 0; i2 < indexCount; i2++) {
                    int index = obtainStyledAttributes.getIndex(i2);
                    if (index == C1614R.styleable.QMUIDialogMessageTvCustomDef_qmui_paddingTopWhenNotTitle) {
                        textView.setPadding(textView.getPaddingLeft(), obtainStyledAttributes.getDimensionPixelSize(index, textView.getPaddingTop()), textView.getPaddingRight(), textView.getPaddingBottom());
                    }
                }
                obtainStyledAttributes.recycle();
            }
        }
    }

    public static class CheckBoxMessageDialogBuilder extends QMUIDialogBuilder<CheckBoxMessageDialogBuilder> {
        private Drawable mCheckMarkDrawable;
        /* access modifiers changed from: private */
        public boolean mIsChecked = false;
        protected String mMessage;
        private QMUIWrapContentScrollView mScrollContainer;
        private QMUISpanTouchFixTextView mTextView;

        public CheckBoxMessageDialogBuilder(Context context) {
            super(context);
            this.mCheckMarkDrawable = QMUIResHelper.getAttrDrawable(context, C1614R.attr.qmui_s_checkbox);
        }

        public CheckBoxMessageDialogBuilder setMessage(String str) {
            this.mMessage = str;
            return this;
        }

        public CheckBoxMessageDialogBuilder setMessage(int i) {
            return setMessage(getBaseContext().getResources().getString(i));
        }

        public boolean isChecked() {
            return this.mIsChecked;
        }

        public CheckBoxMessageDialogBuilder setChecked(boolean z) {
            if (this.mIsChecked != z) {
                this.mIsChecked = z;
                QMUISpanTouchFixTextView qMUISpanTouchFixTextView = this.mTextView;
                if (qMUISpanTouchFixTextView != null) {
                    qMUISpanTouchFixTextView.setSelected(z);
                }
            }
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            String str = this.mMessage;
            if (str != null && str.length() != 0) {
                this.mScrollContainer = new QMUIWrapContentScrollView(context);
                this.mTextView = new QMUISpanTouchFixTextView(context);
                this.mTextView.setMovementMethodDefault();
                MessageDialogBuilder.assignMessageTvWithAttr(this.mTextView, hasTitle(), C1614R.attr.qmui_dialog_message_content_style);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                layoutParams.gravity = this.mTextView.getGravity();
                this.mScrollContainer.addView(this.mTextView, layoutParams);
                this.mScrollContainer.setVerticalScrollBarEnabled(false);
                this.mScrollContainer.setMaxHeight(getContentAreaMaxHeight());
                this.mTextView.setText(this.mMessage);
                Drawable drawable = this.mCheckMarkDrawable;
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mCheckMarkDrawable.getIntrinsicHeight());
                this.mTextView.setCompoundDrawables(this.mCheckMarkDrawable, (Drawable) null, (Drawable) null, (Drawable) null);
                this.mTextView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        CheckBoxMessageDialogBuilder checkBoxMessageDialogBuilder = CheckBoxMessageDialogBuilder.this;
                        checkBoxMessageDialogBuilder.setChecked(!checkBoxMessageDialogBuilder.mIsChecked);
                    }
                });
                this.mTextView.setSelected(this.mIsChecked);
                viewGroup.addView(this.mScrollContainer);
            }
        }

        public QMUISpanTouchFixTextView getTextView() {
            return this.mTextView;
        }
    }

    public static class EditTextDialogBuilder extends QMUIDialogBuilder<EditTextDialogBuilder> {
        private CharSequence mDefaultText = null;
        protected EditText mEditText;
        private int mInputType = 1;
        protected RelativeLayout mMainLayout;
        protected String mPlaceholder;
        protected ImageView mRightImageView;
        protected TransformationMethod mTransformationMethod;

        public EditTextDialogBuilder(Context context) {
            super(context);
        }

        public EditTextDialogBuilder setPlaceholder(String str) {
            this.mPlaceholder = str;
            return this;
        }

        public EditTextDialogBuilder setPlaceholder(int i) {
            return setPlaceholder(getBaseContext().getResources().getString(i));
        }

        public EditTextDialogBuilder setDefaultText(CharSequence charSequence) {
            this.mDefaultText = charSequence;
            return this;
        }

        public EditTextDialogBuilder setTransformationMethod(TransformationMethod transformationMethod) {
            this.mTransformationMethod = transformationMethod;
            return this;
        }

        public EditTextDialogBuilder setInputType(int i) {
            this.mInputType = i;
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            this.mEditText = new EditText(context);
            MessageDialogBuilder.assignMessageTvWithAttr(this.mEditText, hasTitle(), C1614R.attr.qmui_dialog_edit_content_style);
            this.mEditText.setFocusable(true);
            this.mEditText.setFocusableInTouchMode(true);
            this.mEditText.setImeOptions(2);
            this.mEditText.setId(C1614R.C1616id.qmui_dialog_edit_input);
            if (!QMUILangHelper.isNullOrEmpty(this.mDefaultText)) {
                this.mEditText.setText(this.mDefaultText);
            }
            this.mRightImageView = new ImageView(context);
            this.mRightImageView.setId(C1614R.C1616id.qmui_dialog_edit_right_icon);
            this.mRightImageView.setVisibility(8);
            this.mMainLayout = new RelativeLayout(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            layoutParams.topMargin = this.mEditText.getPaddingTop();
            layoutParams.leftMargin = this.mEditText.getPaddingLeft();
            layoutParams.rightMargin = this.mEditText.getPaddingRight();
            layoutParams.bottomMargin = this.mEditText.getPaddingBottom();
            this.mMainLayout.setBackgroundResource(C1614R.C1615drawable.qmui_edittext_bg_border_bottom);
            this.mMainLayout.setLayoutParams(layoutParams);
            TransformationMethod transformationMethod = this.mTransformationMethod;
            if (transformationMethod != null) {
                this.mEditText.setTransformationMethod(transformationMethod);
            } else {
                this.mEditText.setInputType(this.mInputType);
            }
            this.mEditText.setBackgroundResource(0);
            this.mEditText.setPadding(0, 0, 0, QMUIDisplayHelper.dpToPx(5));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.addRule(0, this.mRightImageView.getId());
            layoutParams2.addRule(15, -1);
            String str = this.mPlaceholder;
            if (str != null) {
                this.mEditText.setHint(str);
            }
            this.mMainLayout.addView(this.mEditText, createEditTextLayoutParams());
            this.mMainLayout.addView(this.mRightImageView, createRightIconLayoutParams());
            viewGroup.addView(this.mMainLayout);
        }

        /* access modifiers changed from: protected */
        public RelativeLayout.LayoutParams createEditTextLayoutParams() {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.addRule(0, this.mRightImageView.getId());
            layoutParams.addRule(15, -1);
            return layoutParams;
        }

        /* access modifiers changed from: protected */
        public RelativeLayout.LayoutParams createRightIconLayoutParams() {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(11, -1);
            layoutParams.addRule(15, -1);
            layoutParams.leftMargin = QMUIDisplayHelper.dpToPx(5);
            return layoutParams;
        }

        /* access modifiers changed from: protected */
        public void onAfter(QMUIDialog qMUIDialog, LinearLayout linearLayout, Context context) {
            super.onAfter(qMUIDialog, linearLayout, context);
            final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
            qMUIDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    inputMethodManager.hideSoftInputFromWindow(EditTextDialogBuilder.this.mEditText.getWindowToken(), 0);
                }
            });
            this.mEditText.postDelayed(new Runnable() {
                public void run() {
                    EditTextDialogBuilder.this.mEditText.requestFocus();
                    inputMethodManager.showSoftInput(EditTextDialogBuilder.this.mEditText, 0);
                }
            }, 300);
        }

        public EditText getEditText() {
            return this.mEditText;
        }

        public ImageView getRightImageView() {
            return this.mRightImageView;
        }
    }

    private static class MenuBaseDialogBuilder<T extends QMUIDialogBuilder> extends QMUIDialogBuilder<T> {
        protected QMUIWrapContentScrollView mContentScrollView;
        protected LinearLayout mMenuItemContainer;
        protected LinearLayout.LayoutParams mMenuItemLp;
        protected ArrayList<QMUIDialogMenuItemView> mMenuItemViews = new ArrayList<>();

        /* access modifiers changed from: protected */
        public void onItemClick(int i) {
        }

        public MenuBaseDialogBuilder(Context context) {
            super(context);
        }

        public void clear() {
            this.mMenuItemViews.clear();
        }

        public T addItem(QMUIDialogMenuItemView qMUIDialogMenuItemView, final DialogInterface.OnClickListener onClickListener) {
            qMUIDialogMenuItemView.setMenuIndex(this.mMenuItemViews.size());
            qMUIDialogMenuItemView.setListener(new QMUIDialogMenuItemView.MenuItemViewListener() {
                public void onClick(int i) {
                    MenuBaseDialogBuilder.this.onItemClick(i);
                    DialogInterface.OnClickListener onClickListener = onClickListener;
                    if (onClickListener != null) {
                        onClickListener.onClick(MenuBaseDialogBuilder.this.mDialog, i);
                    }
                }
            });
            this.mMenuItemViews.add(qMUIDialogMenuItemView);
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            Context context2 = context;
            this.mMenuItemContainer = new LinearLayout(context2);
            this.mMenuItemContainer.setOrientation(1);
            this.mMenuItemContainer.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes((AttributeSet) null, C1614R.styleable.QMUIDialogMenuContainerStyleDef, C1614R.attr.qmui_dialog_menu_container_style, 0);
            int indexCount = obtainStyledAttributes.getIndexCount();
            int i = -1;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < indexCount; i7++) {
                int index = obtainStyledAttributes.getIndex(i7);
                if (index == C1614R.styleable.QMUIDialogMenuContainerStyleDef_android_paddingTop) {
                    i3 = obtainStyledAttributes.getDimensionPixelSize(index, i3);
                } else if (index == C1614R.styleable.QMUIDialogMenuContainerStyleDef_android_paddingBottom) {
                    i5 = obtainStyledAttributes.getDimensionPixelSize(index, i5);
                } else if (index == C1614R.styleable.f3101xea461266) {
                    i2 = obtainStyledAttributes.getDimensionPixelSize(index, i2);
                } else if (index == C1614R.styleable.f3100x3c9d1958) {
                    i4 = obtainStyledAttributes.getDimensionPixelSize(index, i4);
                } else if (index == C1614R.styleable.f3099x15fa50e2) {
                    i6 = obtainStyledAttributes.getDimensionPixelSize(index, i6);
                } else if (index == C1614R.styleable.QMUIDialogMenuContainerStyleDef_qmui_dialog_menu_item_height) {
                    i = obtainStyledAttributes.getDimensionPixelSize(index, i);
                }
            }
            obtainStyledAttributes.recycle();
            this.mMenuItemLp = new LinearLayout.LayoutParams(-1, i);
            this.mMenuItemLp.gravity = 16;
            if (this.mMenuItemViews.size() == 1) {
                i5 = i2;
            } else {
                i2 = i3;
            }
            if (hasTitle()) {
                i2 = i4;
            }
            if (this.mActions.size() > 0) {
                i5 = i6;
            }
            this.mMenuItemContainer.setPadding(0, i2, 0, i5);
            Iterator<QMUIDialogMenuItemView> it = this.mMenuItemViews.iterator();
            while (it.hasNext()) {
                this.mMenuItemContainer.addView(it.next(), this.mMenuItemLp);
            }
            this.mContentScrollView = new QMUIWrapContentScrollView(context2);
            this.mContentScrollView.setMaxHeight(getContentAreaMaxHeight());
            this.mContentScrollView.addView(this.mMenuItemContainer);
            this.mContentScrollView.setVerticalScrollBarEnabled(false);
            viewGroup.addView(this.mContentScrollView);
        }
    }

    public static class MenuDialogBuilder extends MenuBaseDialogBuilder<MenuDialogBuilder> {
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        public MenuDialogBuilder(Context context) {
            super(context);
        }

        public MenuDialogBuilder addItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            for (CharSequence textItemView : charSequenceArr) {
                addItem(new QMUIDialogMenuItemView.TextItemView(getBaseContext(), textItemView), onClickListener);
            }
            return this;
        }

        public MenuDialogBuilder addItem(CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
            addItem(new QMUIDialogMenuItemView.TextItemView(getBaseContext(), charSequence), onClickListener);
            return this;
        }
    }

    public static class CheckableDialogBuilder extends MenuBaseDialogBuilder<CheckableDialogBuilder> {
        private int mCheckedIndex = -1;

        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        public CheckableDialogBuilder(Context context) {
            super(context);
        }

        public int getCheckedIndex() {
            return this.mCheckedIndex;
        }

        public CheckableDialogBuilder setCheckedIndex(int i) {
            this.mCheckedIndex = i;
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            super.onCreateContent(qMUIDialog, viewGroup, context);
            int i = this.mCheckedIndex;
            if (i > -1 && i < this.mMenuItemViews.size()) {
                ((QMUIDialogMenuItemView) this.mMenuItemViews.get(this.mCheckedIndex)).setChecked(true);
            }
        }

        /* access modifiers changed from: protected */
        public void onItemClick(int i) {
            for (int i2 = 0; i2 < this.mMenuItemViews.size(); i2++) {
                QMUIDialogMenuItemView qMUIDialogMenuItemView = (QMUIDialogMenuItemView) this.mMenuItemViews.get(i2);
                if (i2 == i) {
                    qMUIDialogMenuItemView.setChecked(true);
                    this.mCheckedIndex = i;
                } else {
                    qMUIDialogMenuItemView.setChecked(false);
                }
            }
        }

        public CheckableDialogBuilder addItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            for (CharSequence markItemView : charSequenceArr) {
                addItem(new QMUIDialogMenuItemView.MarkItemView(getBaseContext(), markItemView), onClickListener);
            }
            return this;
        }
    }

    public static class MultiCheckableDialogBuilder extends MenuBaseDialogBuilder<MultiCheckableDialogBuilder> {
        private int mCheckedItems;

        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        public MultiCheckableDialogBuilder(Context context) {
            super(context);
        }

        public MultiCheckableDialogBuilder setCheckedItems(int i) {
            this.mCheckedItems = i;
            return this;
        }

        public MultiCheckableDialogBuilder setCheckedItems(int[] iArr) {
            int i = 0;
            if (iArr != null && iArr.length > 0) {
                int length = iArr.length;
                int i2 = 0;
                while (i < length) {
                    i2 += 2 << iArr[i];
                    i++;
                }
                i = i2;
            }
            return setCheckedItems(i);
        }

        public MultiCheckableDialogBuilder addItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
            for (CharSequence checkItemView : charSequenceArr) {
                addItem(new QMUIDialogMenuItemView.CheckItemView(getBaseContext(), true, checkItemView), onClickListener);
            }
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            super.onCreateContent(qMUIDialog, viewGroup, context);
            for (int i = 0; i < this.mMenuItemViews.size(); i++) {
                int i2 = 2 << i;
                ((QMUIDialogMenuItemView) this.mMenuItemViews.get(i)).setChecked((this.mCheckedItems & i2) == i2);
            }
        }

        /* access modifiers changed from: protected */
        public void onItemClick(int i) {
            QMUIDialogMenuItemView qMUIDialogMenuItemView = (QMUIDialogMenuItemView) this.mMenuItemViews.get(i);
            qMUIDialogMenuItemView.setChecked(!qMUIDialogMenuItemView.isChecked());
        }

        public int getCheckedItemRecord() {
            int size = this.mMenuItemViews.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                QMUIDialogMenuItemView qMUIDialogMenuItemView = (QMUIDialogMenuItemView) this.mMenuItemViews.get(i2);
                if (qMUIDialogMenuItemView.isChecked()) {
                    i += 2 << qMUIDialogMenuItemView.getMenuIndex();
                }
            }
            this.mCheckedItems = i;
            return i;
        }

        public int[] getCheckedItemIndexes() {
            ArrayList arrayList = new ArrayList();
            int size = this.mMenuItemViews.size();
            for (int i = 0; i < size; i++) {
                QMUIDialogMenuItemView qMUIDialogMenuItemView = (QMUIDialogMenuItemView) this.mMenuItemViews.get(i);
                if (qMUIDialogMenuItemView.isChecked()) {
                    arrayList.add(Integer.valueOf(qMUIDialogMenuItemView.getMenuIndex()));
                }
            }
            int[] iArr = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
            }
            return iArr;
        }

        /* access modifiers changed from: protected */
        public boolean existCheckedItem() {
            return getCheckedItemRecord() <= 0;
        }
    }

    public static class CustomDialogBuilder extends QMUIDialogBuilder {
        private int mLayoutId;

        public CustomDialogBuilder(Context context) {
            super(context);
        }

        public CustomDialogBuilder setLayout(@LayoutRes int i) {
            this.mLayoutId = i;
            return this;
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            viewGroup.addView(LayoutInflater.from(context).inflate(this.mLayoutId, viewGroup, false));
        }
    }

    public static abstract class AutoResizeDialogBuilder extends QMUIDialogBuilder {
        /* access modifiers changed from: private */
        public int mAnchorHeight = 0;
        /* access modifiers changed from: private */
        public int mScreenHeight = 0;
        /* access modifiers changed from: private */
        public int mScrollHeight = 0;
        /* access modifiers changed from: private */
        public ScrollView mScrollerView;

        public abstract View onBuildContent(QMUIDialog qMUIDialog, ScrollView scrollView);

        public int onGetScrollHeight() {
            return -2;
        }

        public AutoResizeDialogBuilder(Context context) {
            super(context);
        }

        /* access modifiers changed from: protected */
        public void onCreateContent(QMUIDialog qMUIDialog, ViewGroup viewGroup, Context context) {
            this.mScrollerView = new ScrollView(context);
            this.mScrollerView.setLayoutParams(new LinearLayout.LayoutParams(-1, onGetScrollHeight()));
            ScrollView scrollView = this.mScrollerView;
            scrollView.addView(onBuildContent(qMUIDialog, scrollView));
            viewGroup.addView(this.mScrollerView);
        }

        /* access modifiers changed from: protected */
        public void onAfter(QMUIDialog qMUIDialog, LinearLayout linearLayout, Context context) {
            super.onAfter(qMUIDialog, linearLayout, context);
            bindEvent(context);
        }

        private void bindEvent(final Context context) {
            this.mAnchorTopView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AutoResizeDialogBuilder.this.mDialog.dismiss();
                }
            });
            this.mAnchorBottomView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AutoResizeDialogBuilder.this.mDialog.dismiss();
                }
            });
            this.mRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    View decorView = AutoResizeDialogBuilder.this.mDialog.getWindow().getDecorView();
                    Rect rect = new Rect();
                    decorView.getWindowVisibleDisplayFrame(rect);
                    int unused = AutoResizeDialogBuilder.this.mScreenHeight = QMUIDisplayHelper.getScreenHeight(context);
                    int access$100 = AutoResizeDialogBuilder.this.mScreenHeight - rect.bottom;
                    if (access$100 != AutoResizeDialogBuilder.this.mAnchorHeight) {
                        int unused2 = AutoResizeDialogBuilder.this.mAnchorHeight = access$100;
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) AutoResizeDialogBuilder.this.mAnchorBottomView.getLayoutParams();
                        layoutParams.height = AutoResizeDialogBuilder.this.mAnchorHeight;
                        AutoResizeDialogBuilder.this.mAnchorBottomView.setLayoutParams(layoutParams);
                        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) AutoResizeDialogBuilder.this.mScrollerView.getLayoutParams();
                        if (AutoResizeDialogBuilder.this.onGetScrollHeight() == -2) {
                            AutoResizeDialogBuilder autoResizeDialogBuilder = AutoResizeDialogBuilder.this;
                            int unused3 = autoResizeDialogBuilder.mScrollHeight = Math.max(autoResizeDialogBuilder.mScrollHeight, AutoResizeDialogBuilder.this.mScrollerView.getMeasuredHeight());
                        } else {
                            AutoResizeDialogBuilder autoResizeDialogBuilder2 = AutoResizeDialogBuilder.this;
                            int unused4 = autoResizeDialogBuilder2.mScrollHeight = autoResizeDialogBuilder2.onGetScrollHeight();
                        }
                        if (AutoResizeDialogBuilder.this.mAnchorHeight == 0) {
                            layoutParams2.height = AutoResizeDialogBuilder.this.mScrollHeight;
                        } else {
                            AutoResizeDialogBuilder.this.mScrollerView.getChildAt(0).requestFocus();
                            layoutParams2.height = AutoResizeDialogBuilder.this.mScrollHeight - AutoResizeDialogBuilder.this.mAnchorHeight;
                        }
                        AutoResizeDialogBuilder.this.mScrollerView.setLayoutParams(layoutParams2);
                        return;
                    }
                    LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) AutoResizeDialogBuilder.this.mDialogView.getLayoutParams();
                    double access$1002 = (double) (((AutoResizeDialogBuilder.this.mScreenHeight - layoutParams3.bottomMargin) - layoutParams3.topMargin) - rect.top);
                    Double.isNaN(access$1002);
                    double d = access$1002 * 0.8d;
                    if (((double) AutoResizeDialogBuilder.this.mScrollerView.getMeasuredHeight()) > d) {
                        int unused5 = AutoResizeDialogBuilder.this.mScrollHeight = (int) d;
                        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) AutoResizeDialogBuilder.this.mScrollerView.getLayoutParams();
                        layoutParams4.height = AutoResizeDialogBuilder.this.mScrollHeight;
                        AutoResizeDialogBuilder.this.mScrollerView.setLayoutParams(layoutParams4);
                    }
                }
            });
        }
    }
}
