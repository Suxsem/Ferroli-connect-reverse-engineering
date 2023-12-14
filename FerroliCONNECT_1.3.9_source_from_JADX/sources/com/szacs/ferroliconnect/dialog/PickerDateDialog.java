package com.szacs.ferroliconnect.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.adapter.ArrayWheelAdapter;
import com.szacs.ferroliconnect.event.HolidayTimeEvent;
import com.szacs.ferroliconnect.widget.wheel.WheelView;
import com.taobao.accs.common.Constants;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class PickerDateDialog implements DialogInterface.OnClickListener {
    private List<String> dates;
    private AlertDialog dialog;
    private int index;
    private LayoutInflater inflate;
    private List<Long> timeSeconds;
    @BindView(2131296957)
    WheelView wheelView;

    public PickerDateDialog(Context context, int i) {
        this.inflate = (LayoutInflater) context.getSystemService("layout_inflater");
        View inflate2 = this.inflate.inflate(C1683R.C1686layout.picker_layout, (ViewGroup) null);
        ButterKnife.bind((Object) this, inflate2);
        this.dates = new ArrayList();
        this.timeSeconds = new ArrayList();
        this.dates = getDates(this.timeSeconds);
        this.wheelView.setAdapter(new ArrayWheelAdapter(this.dates));
        this.wheelView.setCyclic(true);
        this.wheelView.setInterpolator(new AnticipateOvershootInterpolator());
        this.wheelView.setCurrentItem(this.index);
        this.index = getEndTimeIndex((long) i);
        this.dialog = new AlertDialog.Builder(context).setView(inflate2).setTitle(context.getString(C1683R.string.dialog_select_date)).setNegativeButton(C1683R.string.public_cancel, this).setPositiveButton(C1683R.string.public_confirm, this).create();
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (-1 == i) {
            EventBus.getDefault().post(new HolidayTimeEvent(this.timeSeconds.get(this.wheelView.getCurrentItem()).longValue()));
        }
        dialogInterface.dismiss();
    }

    public void show() {
        if (!this.dialog.isShowing()) {
            this.dialog.show();
            this.wheelView.setCurrentItem(this.index);
        }
    }

    public void dismiss() {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }

    private List<String> getDates(List<Long> list) {
        list.clear();
        ArrayList arrayList = new ArrayList();
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 2; i <= 91; i++) {
            long j = (((long) i) * Constants.CLIENT_FLUSH_INTERVAL) + currentTimeMillis;
            arrayList.add(simpleDateFormat.format(new Date(j)));
            list.add(Long.valueOf(j));
        }
        return arrayList;
    }

    private int getEndTimeIndex(long j) {
        for (int i = 0; i < this.timeSeconds.size(); i++) {
            if (1000 * j < this.timeSeconds.get(i).longValue()) {
                return i;
            }
        }
        return -1;
    }
}
