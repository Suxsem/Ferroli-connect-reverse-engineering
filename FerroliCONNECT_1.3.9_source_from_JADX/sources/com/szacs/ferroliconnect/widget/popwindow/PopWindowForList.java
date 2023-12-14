package com.szacs.ferroliconnect.widget.popwindow;

import android.app.Activity;
import android.support.p003v7.widget.LinearLayoutManager;
import android.support.p003v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.adapter.ApListAdapter;
import com.szacs.ferroliconnect.widget.popwindow.BasePopWindow;
import java.util.ArrayList;

public class PopWindowForList extends BasePopWindow {
    /* access modifiers changed from: private */
    public ArrayList<String> data = new ArrayList<>();
    private ViewHolder holder;
    /* access modifiers changed from: private */
    public ApListAdapter mAdapter;
    /* access modifiers changed from: private */
    public Activity mContext;

    public static class OperationListPopListener extends BasePopWindow.OperationPopListener {
        public void onItemClick(String str) {
        }
    }

    /* access modifiers changed from: protected */
    public int getPopLayout() {
        return C1683R.C1686layout.pop_for_list;
    }

    public PopWindowForList(Activity activity) {
        super(activity);
        this.mContext = activity;
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        if (this.holder == null) {
            this.mAdapter = new ApListAdapter(C1683R.C1686layout.item_pop_list, this.data);
            this.holder = new ViewHolder(view);
        }
        setPopWidth(-1);
        setOutsideTouchable(false);
    }

    /* access modifiers changed from: protected */
    public <D> void setPopView(final BasePopWindow.OperationPopListener operationPopListener, D... dArr) {
        try {
            this.holder.tvListPopTitle.setText((String) dArr[0]);
            this.data.clear();
            this.data.addAll((ArrayList) dArr[1]);
            this.mAdapter.notifyDataSetChanged();
            this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                    ((OperationListPopListener) operationPopListener).onItemClick((String) PopWindowForList.this.data.get(i));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissPopWindow() {
        dismissPop();
    }

    private class ViewHolder {
        RecyclerView rvListPop;
        TextView tvListPopTitle;

        ViewHolder(View view) {
            this.tvListPopTitle = (TextView) view.findViewById(C1683R.C1685id.tv_for_pop_title);
            this.rvListPop = (RecyclerView) view.findViewById(C1683R.C1685id.rv_for_pop_list);
            this.rvListPop.setLayoutManager(new LinearLayoutManager(PopWindowForList.this.mContext));
            this.rvListPop.setAdapter(PopWindowForList.this.mAdapter);
        }
    }
}
