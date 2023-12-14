package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.event.ApplyEvent;
import org.greenrobot.eventbus.EventBus;

public class ApplyProgramFragment extends DialogFragment implements View.OnClickListener {
    /* access modifiers changed from: private */
    public CheckBox cbAll;
    /* access modifiers changed from: private */
    public CheckBox[] cbWeek = new CheckBox[7];

    public Dialog onCreateDialog(Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_apply_program, (ViewGroup) null);
        AlertDialog create = new AlertDialog.Builder(getActivity()).setView(inflate).create();
        inflate.findViewById(C1683R.C1685id.ivCancel).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.ivConfirm).setOnClickListener(this);
        this.cbWeek[0] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbSunday);
        this.cbWeek[1] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbMonday);
        this.cbWeek[2] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbTuesday);
        this.cbWeek[3] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbWednesday);
        this.cbWeek[4] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbThursday);
        this.cbWeek[5] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbFriday);
        this.cbWeek[6] = (CheckBox) inflate.findViewById(C1683R.C1685id.cbSaturday);
        this.cbAll = (CheckBox) inflate.findViewById(C1683R.C1685id.cbAll);
        inflate.findViewById(C1683R.C1685id.rlSun).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlMon).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlTues).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlWed).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlThur).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlFri).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlSat).setOnClickListener(this);
        inflate.findViewById(C1683R.C1685id.rlAll).setOnClickListener(this);
        this.cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (ApplyProgramFragment.this.cbAll.isChecked()) {
                    for (CheckBox checked : ApplyProgramFragment.this.cbWeek) {
                        checked.setChecked(true);
                    }
                    return;
                }
                for (CheckBox checked2 : ApplyProgramFragment.this.cbWeek) {
                    checked2.setChecked(false);
                }
            }
        });
        return create;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1683R.C1685id.ivCancel:
                getDialog().cancel();
                return;
            case C1683R.C1685id.ivConfirm:
                boolean[] zArr = new boolean[7];
                for (int i = 0; i < 7; i++) {
                    zArr[i] = this.cbWeek[i].isChecked();
                }
                ApplyEvent applyEvent = new ApplyEvent();
                applyEvent.setDays(zArr);
                EventBus.getDefault().post(applyEvent);
                getDialog().cancel();
                return;
            case C1683R.C1685id.rlAll:
                if (this.cbAll.isChecked()) {
                    this.cbAll.setChecked(false);
                    return;
                } else {
                    this.cbAll.setChecked(true);
                    return;
                }
            case C1683R.C1685id.rlFri:
                if (this.cbWeek[5].isChecked()) {
                    this.cbWeek[5].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[5].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlMon:
                if (this.cbWeek[1].isChecked()) {
                    this.cbWeek[1].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[1].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlSat:
                if (this.cbWeek[6].isChecked()) {
                    this.cbWeek[6].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[6].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlSun:
                if (this.cbWeek[0].isChecked()) {
                    this.cbWeek[0].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[0].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlThur:
                if (this.cbWeek[4].isChecked()) {
                    this.cbWeek[4].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[4].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlTues:
                if (this.cbWeek[2].isChecked()) {
                    this.cbWeek[2].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[2].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            case C1683R.C1685id.rlWed:
                if (this.cbWeek[3].isChecked()) {
                    this.cbWeek[3].setChecked(false);
                    this.cbAll.setChecked(false);
                    return;
                }
                this.cbWeek[3].setChecked(true);
                if (IsAllChecked()) {
                    this.cbAll.setChecked(true);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean IsAllChecked() {
        boolean z = true;
        for (CheckBox isChecked : this.cbWeek) {
            if (isChecked.isChecked()) {
                z = false;
            }
        }
        return z;
    }
}
