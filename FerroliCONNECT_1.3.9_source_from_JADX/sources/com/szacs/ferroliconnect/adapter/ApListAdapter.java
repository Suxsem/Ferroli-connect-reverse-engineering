package com.szacs.ferroliconnect.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.szacs.ferroliconnect.C1683R;
import java.util.List;

public class ApListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ApListAdapter(int i, @Nullable List<String> list) {
        super(i, list);
    }

    /* access modifiers changed from: protected */
    public void convert(BaseViewHolder baseViewHolder, String str) {
        baseViewHolder.setText((int) C1683R.C1685id.tv_pop_list_item, (CharSequence) str);
    }
}
