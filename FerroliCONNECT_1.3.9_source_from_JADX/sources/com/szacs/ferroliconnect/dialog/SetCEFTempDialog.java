package com.szacs.ferroliconnect.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.Centigrade;
import com.szacs.ferroliconnect.util.DensityUtil;
import java.util.List;

public class SetCEFTempDialog implements DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private boolean centigrade;
    private List<Centigrade> centigradeList;
    private Context context;
    private AlertDialog dialog;
    @BindView(2131296674)
    RelativeLayout frostLayout;
    private LayoutInflater inflate;
    private TempChangeListenner listenner;
    @BindView(2131296703)
    SeekBar sbComf;
    @BindView(2131296704)
    SeekBar sbEcon;
    @BindView(2131296705)
    SeekBar sbFrost;
    @BindView(2131296798)
    TextView tvComf;
    @BindView(2131296799)
    TextView tvComfUnit;
    @BindView(2131296804)
    TextView tvEcon;
    @BindView(2131296805)
    TextView tvEconUnit;
    @BindView(2131296811)
    TextView tvFrost;
    @BindView(2131296812)
    TextView tvFrostUnit;

    public interface TempChangeListenner {
        void onTempChange(List<Centigrade> list);
    }

    private float getT(int i, int i2, int i3) {
        float f = 5.0f;
        float f2 = i == 1 ? (((float) i2) * 0.5f) + 5.0f : 0.0f;
        if (i == 2) {
            float f3 = ((float) i2) * 0.5f;
            if (i3 != 2) {
                f = 5.5f;
            }
            f2 = f3 + f;
        }
        if (i != 3) {
            return f2;
        }
        return (((float) i2) * 0.5f) + ((float) (i3 == 2 ? 5 : 6));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public SetCEFTempDialog(Context context2, boolean z, List<Centigrade> list, boolean z2) {
        String str;
        this.context = context2;
        this.centigrade = z;
        this.centigradeList = list;
        this.inflate = (LayoutInflater) context2.getSystemService("layout_inflater");
        View inflate2 = this.inflate.inflate(C1683R.C1686layout.dialog_program_temp_set, (ViewGroup) null);
        ButterKnife.bind((Object) this, inflate2);
        this.dialog = new AlertDialog.Builder(context2).setView(inflate2).setNegativeButton(C1683R.string.public_cancel, this).setPositiveButton(C1683R.string.public_confirm, this).create();
        this.dialog.setCanceledOnTouchOutside(false);
        String str2 = "℃";
        this.tvComfUnit.setText(this.centigrade ? str2 : "℉");
        TextView textView = this.tvEconUnit;
        if (this.centigrade) {
            str = str2;
        } else {
            str = "℉";
        }
        textView.setText(str);
        this.tvFrostUnit.setText(!this.centigrade ? "℉" : str2);
        List<Centigrade> list2 = this.centigradeList;
        if (list2 == null || list2.size() != 3) {
            List<Centigrade> list3 = this.centigradeList;
            if (list3 != null && list3.size() == 2) {
                this.frostLayout.setVisibility(8);
                this.tvComf.setText(this.centigrade ? this.centigradeList.get(0).getCenString(false) : this.centigradeList.get(0).getFahString(true));
                this.tvEcon.setText(this.centigrade ? this.centigradeList.get(1).getCenString(false) : this.centigradeList.get(1).getFahString(true));
                float cenFloat = (this.centigradeList.get(0).getCenFloat() - 5.0f) * 2.0f;
                float cenFloat2 = (this.centigradeList.get(1).getCenFloat() - 5.0f) * 2.0f;
                cenFloat2 = cenFloat2 < 0.0f ? 0.0f : cenFloat2;
                cenFloat2 = cenFloat2 > 60.0f ? 60.0f : cenFloat2;
                cenFloat = cenFloat < 0.0f ? 0.0f : cenFloat;
                cenFloat = cenFloat > 60.0f ? 60.0f : cenFloat;
                this.sbComf.setMax(60);
                this.sbComf.setSecondaryProgress(60);
                this.sbComf.setProgress((int) cenFloat);
                this.sbEcon.setMax(60);
                this.sbEcon.setSecondaryProgress(60);
                this.sbEcon.setProgress((int) cenFloat2);
            }
        } else {
            this.tvComf.setText(this.centigrade ? this.centigradeList.get(0).getCenString(false) : this.centigradeList.get(0).getFahString(true));
            this.tvEcon.setText(this.centigrade ? this.centigradeList.get(1).getCenString(false) : this.centigradeList.get(1).getFahString(true));
            this.tvFrost.setText(this.centigrade ? this.centigradeList.get(2).getCenString(false) : this.centigradeList.get(2).getFahString(true));
            float cenFloat3 = (this.centigradeList.get(0).getCenFloat() - 6.0f) * 2.0f;
            float cenFloat4 = (this.centigradeList.get(1).getCenFloat() - 5.5f) * 2.0f;
            float cenFloat5 = (this.centigradeList.get(2).getCenFloat() - 5.0f) * 2.0f;
            cenFloat5 = cenFloat5 < 0.0f ? 0.0f : cenFloat5;
            cenFloat5 = cenFloat5 > 58.0f ? 58.0f : cenFloat5;
            cenFloat4 = cenFloat4 < 0.0f ? 0.0f : cenFloat4;
            cenFloat4 = cenFloat4 > 58.0f ? 58.0f : cenFloat4;
            cenFloat3 = cenFloat3 < 0.0f ? 0.0f : cenFloat3;
            cenFloat3 = cenFloat3 > 58.0f ? 58.0f : cenFloat3;
            this.sbComf.setMax(58);
            this.sbComf.setSecondaryProgress(58);
            this.sbComf.setProgress((int) cenFloat3);
            this.sbEcon.setMax(58);
            this.sbEcon.setSecondaryProgress(58);
            this.sbEcon.setProgress((int) cenFloat4);
            this.sbFrost.setMax(58);
            this.sbFrost.setSecondaryProgress(58);
            if (z2) {
                this.sbFrost.setProgress((int) cenFloat5);
            } else {
                this.sbFrost.setProgress(0);
                this.sbFrost.setEnabled(false);
                this.frostLayout.setVisibility(8);
            }
        }
        this.sbComf.setOnSeekBarChangeListener(this);
        this.sbEcon.setOnSeekBarChangeListener(this);
        this.sbFrost.setOnSeekBarChangeListener(this);
    }

    public void show() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null && !alertDialog.isShowing()) {
            this.dialog.show();
            if (this.dialog.getWindow() != null) {
                this.dialog.getWindow().setLayout(DensityUtil.dip2px(this.context, 600.0f), -2);
                this.dialog.getWindow().setGravity(17);
            }
        }
    }

    public void dismiss() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        List<Centigrade> list;
        TempChangeListenner tempChangeListenner;
        if (-1 == i && (list = this.centigradeList) != null && list.size() > 1 && (tempChangeListenner = this.listenner) != null) {
            tempChangeListenner.onTempChange(this.centigradeList);
        }
        dialogInterface.dismiss();
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        switch (seekBar.getId()) {
            case C1683R.C1685id.sbComf:
                if (this.centigradeList.size() == 3 && i < this.sbEcon.getProgress()) {
                    this.sbEcon.setProgress(i);
                } else if (this.centigradeList.size() == 2 && i > this.sbEcon.getProgress()) {
                    this.sbEcon.setProgress(i);
                }
                this.centigradeList.get(0).setCenF(String.format("%.1f", new Object[]{Float.valueOf(getT(3, i, this.centigradeList.size()))}).replace(",", "."), true);
                this.tvComf.setText(this.centigrade ? this.centigradeList.get(0).getCenString(false) : this.centigradeList.get(0).getFahString(false));
                return;
            case C1683R.C1685id.sbEcon:
                if (this.centigradeList.size() == 3) {
                    if (i < this.sbFrost.getProgress()) {
                        this.sbFrost.setProgress(i);
                    }
                    if (i > this.sbComf.getProgress()) {
                        this.sbComf.setProgress(i);
                    }
                } else if (this.centigradeList.size() == 2 && i < this.sbComf.getProgress()) {
                    this.sbComf.setProgress(i);
                }
                this.centigradeList.get(1).setCenF(String.format("%.1f", new Object[]{Float.valueOf(getT(2, i, this.centigradeList.size()))}).replace(",", "."), true);
                this.tvEcon.setText(this.centigrade ? this.centigradeList.get(1).getCenString(false) : this.centigradeList.get(1).getFahString(false));
                return;
            case C1683R.C1685id.sbFrost:
                if (i > this.sbEcon.getProgress()) {
                    this.sbEcon.setProgress(i);
                }
                this.centigradeList.get(2).setCenF(String.format("%.1f", new Object[]{Float.valueOf(getT(1, i, this.centigradeList.size()))}).replace(",", "."), true);
                this.tvFrost.setText(this.centigrade ? this.centigradeList.get(2).getCenString(false) : this.centigradeList.get(2).getFahString(false));
                return;
            default:
                return;
        }
    }

    public TempChangeListenner getListenner() {
        return this.listenner;
    }

    public SetCEFTempDialog setListenner(TempChangeListenner tempChangeListenner) {
        this.listenner = tempChangeListenner;
        return this;
    }
}
