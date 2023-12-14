package com.szacs.ferroliconnect.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.ServerBean;
import java.util.ArrayList;

public class ChangeDomainNameAdapter implements SpinnerAdapter {
    private static final String TAG = "ChangeDomainNameAdapter";
    private Context context;
    private ArrayList<ServerBean> list;

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return 1;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public ChangeDomainNameAdapter(Context context2, ArrayList<ServerBean> arrayList) {
        this.context = context2;
        this.list = arrayList;
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View view2;
        DropDownHolder dropDownHolder;
        if (view == null) {
            dropDownHolder = new DropDownHolder();
            view2 = LayoutInflater.from(this.context).inflate(C1683R.C1686layout.item_change_domain, (ViewGroup) null);
            dropDownHolder.f3125ll = (LinearLayout) view2.findViewById(C1683R.C1685id.ll_drop);
            dropDownHolder.f3126tv = (TextView) view2.findViewById(C1683R.C1685id.tv_item);
            view2.setTag(dropDownHolder);
        } else {
            view2 = view;
            dropDownHolder = (DropDownHolder) view.getTag();
        }
        dropDownHolder.f3126tv.setText(this.list.get(i).getServerName());
        return view2;
    }

    public int getCount() {
        ArrayList<ServerBean> arrayList = this.list;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    public ServerBean getItem(int i) {
        return this.list.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view2 = LayoutInflater.from(this.context).inflate(C1683R.C1686layout.item_change_domain, (ViewGroup) null);
            holder.f3127tv = (TextView) view2.findViewById(C1683R.C1685id.tv_item);
            view2.setTag(holder);
        } else {
            view2 = view;
            holder = (Holder) view.getTag();
        }
        holder.f3127tv.setText(this.list.get(i).getServerName());
        return view2;
    }

    class Holder {

        /* renamed from: tv */
        TextView f3127tv;

        Holder() {
        }
    }

    class DropDownHolder {

        /* renamed from: ll */
        LinearLayout f3125ll;

        /* renamed from: tv */
        TextView f3126tv;

        DropDownHolder() {
        }
    }
}
