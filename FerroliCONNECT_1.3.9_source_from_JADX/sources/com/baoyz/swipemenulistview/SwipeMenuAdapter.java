package com.baoyz.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.p000v4.internal.view.SupportMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuView;

public class SwipeMenuAdapter implements WrapperListAdapter, SwipeMenuView.OnSwipeItemClickListener {
    private ListAdapter mAdapter;
    private Context mContext;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;

    public SwipeMenuAdapter(Context context, ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        this.mContext = context;
    }

    public int getCount() {
        return this.mAdapter.getCount();
    }

    public Object getItem(int i) {
        return this.mAdapter.getItem(i);
    }

    public long getItemId(int i) {
        return this.mAdapter.getItemId(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            View view2 = this.mAdapter.getView(i, view, viewGroup);
            SwipeMenu swipeMenu = new SwipeMenu(this.mContext);
            swipeMenu.setViewType(this.mAdapter.getItemViewType(i));
            createMenu(swipeMenu);
            SwipeMenuListView swipeMenuListView = (SwipeMenuListView) viewGroup;
            SwipeMenuView swipeMenuView = new SwipeMenuView(swipeMenu, swipeMenuListView);
            swipeMenuView.setOnSwipeItemClickListener(this);
            SwipeMenuLayout swipeMenuLayout = new SwipeMenuLayout(view2, swipeMenuView, swipeMenuListView.getCloseInterpolator(), swipeMenuListView.getOpenInterpolator());
            swipeMenuLayout.setPosition(i);
            return swipeMenuLayout;
        }
        SwipeMenuLayout swipeMenuLayout2 = (SwipeMenuLayout) view;
        swipeMenuLayout2.closeMenu();
        swipeMenuLayout2.setPosition(i);
        this.mAdapter.getView(i, swipeMenuLayout2.getContentView(), viewGroup);
        return swipeMenuLayout2;
    }

    public void createMenu(SwipeMenu swipeMenu) {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(this.mContext);
        swipeMenuItem.setTitle("Item 1");
        swipeMenuItem.setBackground((Drawable) new ColorDrawable(-7829368));
        swipeMenuItem.setWidth(300);
        swipeMenu.addMenuItem(swipeMenuItem);
        SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(this.mContext);
        swipeMenuItem2.setTitle("Item 2");
        swipeMenuItem2.setBackground((Drawable) new ColorDrawable(SupportMenu.CATEGORY_MASK));
        swipeMenuItem2.setWidth(300);
        swipeMenu.addMenuItem(swipeMenuItem2);
    }

    public void onItemClick(SwipeMenuView swipeMenuView, SwipeMenu swipeMenu, int i) {
        SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener2 = this.onMenuItemClickListener;
        if (onMenuItemClickListener2 != null) {
            onMenuItemClickListener2.onMenuItemClick(swipeMenuView.getPosition(), swipeMenu, i);
        }
    }

    public void setOnMenuItemClickListener(SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener2) {
        this.onMenuItemClickListener = onMenuItemClickListener2;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.mAdapter.registerDataSetObserver(dataSetObserver);
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.mAdapter.unregisterDataSetObserver(dataSetObserver);
    }

    public boolean areAllItemsEnabled() {
        return this.mAdapter.areAllItemsEnabled();
    }

    public boolean isEnabled(int i) {
        return this.mAdapter.isEnabled(i);
    }

    public boolean hasStableIds() {
        return this.mAdapter.hasStableIds();
    }

    public int getItemViewType(int i) {
        return this.mAdapter.getItemViewType(i);
    }

    public int getViewTypeCount() {
        return this.mAdapter.getViewTypeCount();
    }

    public boolean isEmpty() {
        return this.mAdapter.isEmpty();
    }

    public ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }
}
