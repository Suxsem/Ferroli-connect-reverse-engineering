package com.szacs.ferroliconnect.widget.Spinner;

import android.content.Context;
import android.os.Build;
import android.support.p000v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;

public abstract class NiceSpinnerBaseAdapter<T> extends BaseAdapter {
    protected int mBackgroundSelector;
    protected Context mContext;
    protected int mSelectedIndex;
    protected int mTextColor;

    public abstract int getCount();

    public abstract T getItem(int i);

    public long getItemId(int i) {
        return (long) i;
    }

    public abstract T getItemInDataset(int i);

    public NiceSpinnerBaseAdapter(Context context, int i, int i2) {
        this.mContext = context;
        this.mTextColor = i;
        this.mBackgroundSelector = i2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            view = View.inflate(this.mContext, C1683R.C1686layout.spinner_list_item, (ViewGroup) null);
            textView = (TextView) view.findViewById(C1683R.C1685id.tv_tinted_spinner);
            if (Build.VERSION.SDK_INT >= 16) {
                textView.setBackground(ContextCompat.getDrawable(this.mContext, this.mBackgroundSelector));
            }
            view.setTag(new ViewHolder(textView));
        } else {
            textView = ((ViewHolder) view.getTag()).textView;
        }
        textView.setText(getItem(i).toString());
        textView.setTextColor(this.mTextColor);
        return view;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }

    public void notifyItemSelected(int i) {
        this.mSelectedIndex = i;
    }

    protected static class ViewHolder {
        public TextView textView;

        public ViewHolder(TextView textView2) {
            this.textView = textView2;
        }
    }
}
