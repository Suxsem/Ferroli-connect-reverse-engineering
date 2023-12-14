package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.event.BoilerHeatTargetTempSetOptions;
import org.greenrobot.eventbus.EventBus;

public class TargetTempSettingOptionFragment extends DialogFragment {
    private AlertDialog dialog;
    private View view;

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_target_temp_setting_options, (ViewGroup) null);
        final RadioButton radioButton = (RadioButton) this.view.findViewById(C1683R.C1685id.rbAuto);
        RadioButton radioButton2 = (RadioButton) this.view.findViewById(C1683R.C1685id.rbManual);
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                EventBus.getDefault().post(new BoilerHeatTargetTempSetOptions(radioButton.isChecked() ^ true ? 1 : 0));
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
        return this.dialog;
    }
}
