package com.szacs.ferroliconnect.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> Datas;
    private LayoutInflater LInflat;
    private int Resid;
    private Context mContext;

    /* access modifiers changed from: protected */
    public abstract void InitView(View view, T t);

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return i;
    }

    public CommonAdapter() {
    }

    public CommonAdapter(Context context, @NonNull List<T> list, int i) {
        this.mContext = context;
        this.Datas = list;
        this.Resid = i;
        this.LInflat = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.Datas.size();
    }

    public T getItem(int i) {
        return this.Datas.get(i);
    }

    @SuppressLint({"ViewHolder"})
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.LInflat.inflate(this.Resid, (ViewGroup) null);
        }
        InitView(view, getItem(i));
        return view;
    }
}
