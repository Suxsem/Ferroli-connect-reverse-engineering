package com.qmuiteam.qmui.widget.grouplist;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import com.qmuiteam.qmui.C1614R;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class QMUIGroupListView extends LinearLayout {
    public static final int SEPARATOR_STYLE_NONE = 1;
    public static final int SEPARATOR_STYLE_NORMAL = 0;
    private SparseArray<Section> mSections;
    private int mSeparatorStyle;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeparatorStyle {
    }

    public QMUIGroupListView(Context context) {
        this(context, (AttributeSet) null, C1614R.attr.QMUIGroupListViewStyle);
    }

    public QMUIGroupListView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1614R.attr.QMUIGroupListViewStyle);
    }

    public QMUIGroupListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C1614R.styleable.QMUIGroupListView, i, 0);
        this.mSeparatorStyle = obtainStyledAttributes.getInt(C1614R.styleable.QMUIGroupListView_separatorStyle, 0);
        obtainStyledAttributes.recycle();
        this.mSections = new SparseArray<>();
        setOrientation(1);
    }

    public static Section newSection(Context context) {
        return new Section(context);
    }

    public int getSeparatorStyle() {
        return this.mSeparatorStyle;
    }

    public void setSeparatorStyle(int i) {
        this.mSeparatorStyle = i;
    }

    public int getSectionCount() {
        return this.mSections.size();
    }

    public QMUICommonListItemView createItemView(Drawable drawable, CharSequence charSequence, String str, int i, int i2, int i3) {
        QMUICommonListItemView qMUICommonListItemView = new QMUICommonListItemView(getContext());
        qMUICommonListItemView.setOrientation(i);
        qMUICommonListItemView.setLayoutParams(new LinearLayout.LayoutParams(-1, i3));
        qMUICommonListItemView.setImageDrawable(drawable);
        qMUICommonListItemView.setText(charSequence);
        qMUICommonListItemView.setDetailText(str);
        qMUICommonListItemView.setAccessoryType(i2);
        return qMUICommonListItemView;
    }

    public QMUICommonListItemView createItemView(Drawable drawable, CharSequence charSequence, String str, int i, int i2) {
        if (i == 0) {
            return createItemView(drawable, charSequence, str, i, i2, QMUIResHelper.getAttrDimen(getContext(), C1614R.attr.qmui_list_item_height_higher));
        }
        return createItemView(drawable, charSequence, str, i, i2, QMUIResHelper.getAttrDimen(getContext(), C1614R.attr.qmui_list_item_height));
    }

    public QMUICommonListItemView createItemView(CharSequence charSequence) {
        return createItemView((Drawable) null, charSequence, (String) null, 1, 0);
    }

    public QMUICommonListItemView createItemView(int i) {
        return createItemView((Drawable) null, (CharSequence) null, (String) null, i, 0);
    }

    /* access modifiers changed from: private */
    public void addSection(Section section) {
        SparseArray<Section> sparseArray = this.mSections;
        sparseArray.append(sparseArray.size(), section);
    }

    /* access modifiers changed from: private */
    public void removeSection(Section section) {
        for (int i = 0; i < this.mSections.size(); i++) {
            if (this.mSections.valueAt(i) == section) {
                this.mSections.remove(i);
            }
        }
    }

    public Section getSection(int i) {
        return this.mSections.get(i);
    }

    public static class Section {
        private Context mContext;
        private QMUIGroupListSectionHeaderFooterView mDescriptionView;
        private SparseArray<QMUICommonListItemView> mItemViews;
        private int mSeparatorDrawableForBottom = 0;
        private int mSeparatorDrawableForMiddle = 0;
        private int mSeparatorDrawableForSingle = 0;
        private int mSeparatorDrawableForTop = 0;
        private QMUIGroupListSectionHeaderFooterView mTitleView;
        private boolean mUseDefaultTitleIfNone;
        private boolean mUseTitleViewForSectionSpace = true;

        public Section(Context context) {
            this.mContext = context;
            this.mItemViews = new SparseArray<>();
        }

        public Section addItemView(QMUICommonListItemView qMUICommonListItemView, View.OnClickListener onClickListener) {
            return addItemView(qMUICommonListItemView, onClickListener, (View.OnLongClickListener) null);
        }

        public Section addItemView(final QMUICommonListItemView qMUICommonListItemView, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener) {
            if (qMUICommonListItemView.getAccessoryType() == 2) {
                qMUICommonListItemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        qMUICommonListItemView.getSwitch().toggle();
                    }
                });
            } else if (onClickListener != null) {
                qMUICommonListItemView.setOnClickListener(onClickListener);
            }
            if (onLongClickListener != null) {
                qMUICommonListItemView.setOnLongClickListener(onLongClickListener);
            }
            SparseArray<QMUICommonListItemView> sparseArray = this.mItemViews;
            sparseArray.append(sparseArray.size(), qMUICommonListItemView);
            return this;
        }

        public Section setTitle(CharSequence charSequence) {
            this.mTitleView = createSectionHeader(charSequence);
            return this;
        }

        public Section setDescription(CharSequence charSequence) {
            this.mDescriptionView = createSectionFooter(charSequence);
            return this;
        }

        public Section setUseDefaultTitleIfNone(boolean z) {
            this.mUseDefaultTitleIfNone = z;
            return this;
        }

        public Section setUseTitleViewForSectionSpace(boolean z) {
            this.mUseTitleViewForSectionSpace = z;
            return this;
        }

        public Section setSeparatorDrawableRes(int i, int i2, int i3, int i4) {
            this.mSeparatorDrawableForSingle = i;
            this.mSeparatorDrawableForTop = i2;
            this.mSeparatorDrawableForBottom = i3;
            this.mSeparatorDrawableForMiddle = i4;
            return this;
        }

        public Section setSeparatorDrawableRes(int i) {
            this.mSeparatorDrawableForMiddle = i;
            return this;
        }

        public void addTo(QMUIGroupListView qMUIGroupListView) {
            int i;
            if (this.mTitleView == null) {
                if (this.mUseDefaultTitleIfNone) {
                    setTitle("Section " + qMUIGroupListView.getSectionCount());
                } else if (this.mUseTitleViewForSectionSpace) {
                    setTitle("");
                }
            }
            QMUIGroupListSectionHeaderFooterView qMUIGroupListSectionHeaderFooterView = this.mTitleView;
            if (qMUIGroupListSectionHeaderFooterView != null) {
                qMUIGroupListView.addView(qMUIGroupListSectionHeaderFooterView);
            }
            if (qMUIGroupListView.getSeparatorStyle() == 0) {
                if (this.mSeparatorDrawableForSingle == 0) {
                    this.mSeparatorDrawableForSingle = C1614R.C1615drawable.qmui_s_list_item_bg_with_border_double;
                }
                if (this.mSeparatorDrawableForTop == 0) {
                    this.mSeparatorDrawableForTop = C1614R.C1615drawable.qmui_s_list_item_bg_with_border_double;
                }
                if (this.mSeparatorDrawableForBottom == 0) {
                    this.mSeparatorDrawableForBottom = C1614R.C1615drawable.qmui_s_list_item_bg_with_border_bottom;
                }
                if (this.mSeparatorDrawableForMiddle == 0) {
                    this.mSeparatorDrawableForMiddle = C1614R.C1615drawable.qmui_s_list_item_bg_with_border_bottom;
                }
            }
            int size = this.mItemViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                QMUICommonListItemView qMUICommonListItemView = this.mItemViews.get(i2);
                if (qMUIGroupListView.getSeparatorStyle() != 0) {
                    i = C1614R.C1615drawable.qmui_s_list_item_bg_with_border_none;
                } else if (size == 1) {
                    i = this.mSeparatorDrawableForSingle;
                } else if (i2 == 0) {
                    i = this.mSeparatorDrawableForTop;
                } else if (i2 == size - 1) {
                    i = this.mSeparatorDrawableForBottom;
                } else {
                    i = this.mSeparatorDrawableForMiddle;
                }
                QMUIViewHelper.setBackgroundKeepingPadding((View) qMUICommonListItemView, i);
                qMUIGroupListView.addView(qMUICommonListItemView);
            }
            QMUIGroupListSectionHeaderFooterView qMUIGroupListSectionHeaderFooterView2 = this.mDescriptionView;
            if (qMUIGroupListSectionHeaderFooterView2 != null) {
                qMUIGroupListView.addView(qMUIGroupListSectionHeaderFooterView2);
            }
            qMUIGroupListView.addSection(this);
        }

        public void removeFrom(QMUIGroupListView qMUIGroupListView) {
            QMUIGroupListSectionHeaderFooterView qMUIGroupListSectionHeaderFooterView = this.mTitleView;
            if (qMUIGroupListSectionHeaderFooterView != null && qMUIGroupListSectionHeaderFooterView.getParent() == qMUIGroupListView) {
                qMUIGroupListView.removeView(this.mTitleView);
            }
            for (int i = 0; i < this.mItemViews.size(); i++) {
                qMUIGroupListView.removeView(this.mItemViews.get(i));
            }
            QMUIGroupListSectionHeaderFooterView qMUIGroupListSectionHeaderFooterView2 = this.mDescriptionView;
            if (qMUIGroupListSectionHeaderFooterView2 != null && qMUIGroupListSectionHeaderFooterView2.getParent() == qMUIGroupListView) {
                qMUIGroupListView.removeView(this.mDescriptionView);
            }
            qMUIGroupListView.removeSection(this);
        }

        public QMUIGroupListSectionHeaderFooterView createSectionHeader(CharSequence charSequence) {
            return new QMUIGroupListSectionHeaderFooterView(this.mContext, charSequence);
        }

        public QMUIGroupListSectionHeaderFooterView createSectionFooter(CharSequence charSequence) {
            return new QMUIGroupListSectionHeaderFooterView(this.mContext, charSequence, true);
        }
    }
}
