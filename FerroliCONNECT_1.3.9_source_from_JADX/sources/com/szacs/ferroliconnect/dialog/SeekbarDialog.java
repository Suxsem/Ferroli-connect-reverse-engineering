package com.szacs.ferroliconnect.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.Centigrade;

public class SeekbarDialog implements DialogInterface.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private float Max;
    private float Min;
    @BindView(2131296894)
    TextView TvValue;
    private Centigrade centigrade;
    private AlertDialog dialog;
    private LayoutInflater inflate;
    private boolean isCentigrade;
    private ValueChangeListener listener;
    private float progress;
    @BindView(2131296725)
    SeekBar seekBar;
    private float value;

    public interface ValueChangeListener {
        void onValueChange(float f);
    }

    public void onStartTrackingTouch(SeekBar seekBar2) {
    }

    public void onStopTrackingTouch(SeekBar seekBar2) {
    }

    public SeekbarDialog(Context context, float f, float f2, float f3, boolean z) {
        String str;
        StringBuilder sb;
        this.isCentigrade = z;
        this.centigrade = new Centigrade(f3 + "");
        this.value = f3;
        f3 = f3 > f ? f : f3;
        f3 = f3 < f2 ? f2 : f3;
        this.Min = f2;
        this.Max = f;
        this.progress = (f3 - f2) * 5.0f;
        this.inflate = (LayoutInflater) context.getSystemService("layout_inflater");
        View inflate2 = this.inflate.inflate(C1683R.C1686layout.seekbar_dialog, (ViewGroup) null);
        ButterKnife.bind((Object) this, inflate2);
        this.seekBar.setMax(130);
        this.seekBar.setSecondaryProgress(130);
        this.seekBar.setProgress((int) this.progress);
        this.seekBar.setOnSeekBarChangeListener(this);
        TextView textView = this.TvValue;
        if (this.isCentigrade) {
            sb = new StringBuilder();
            sb.append(this.centigrade.getCenString(false));
            str = "℃";
        } else {
            sb = new StringBuilder();
            sb.append(this.centigrade.getFahString(false));
            str = "℉";
        }
        sb.append(str);
        textView.setText(sb.toString());
        this.dialog = new AlertDialog.Builder(context).setView(inflate2).setTitle("Set Temperature").setNegativeButton(C1683R.string.public_cancel, this).setPositiveButton(C1683R.string.public_confirm, this).create();
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        ValueChangeListener valueChangeListener;
        if (-1 == i && (valueChangeListener = this.listener) != null) {
            valueChangeListener.onValueChange(this.centigrade.getCenFloat());
        }
        dialogInterface.dismiss();
    }

    public void onProgressChanged(SeekBar seekBar2, int i, boolean z) {
        String str;
        StringBuilder sb;
        float f = (((float) i) * 0.2f) + this.Min;
        this.centigrade.setCenF(String.format("%.1f", new Object[]{Float.valueOf(f)}).replace(",", "."), true);
        TextView textView = this.TvValue;
        if (this.isCentigrade) {
            sb = new StringBuilder();
            sb.append(this.centigrade.getCenString(false));
            str = "℃";
        } else {
            sb = new StringBuilder();
            sb.append(this.centigrade.getFahString(false));
            str = "℉";
        }
        sb.append(str);
        textView.setText(sb.toString());
    }

    public void show() {
        if (!this.dialog.isShowing()) {
            this.dialog.show();
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

    public ValueChangeListener getListener() {
        return this.listener;
    }

    public SeekbarDialog setListener(ValueChangeListener valueChangeListener) {
        this.listener = valueChangeListener;
        return this;
    }
}
