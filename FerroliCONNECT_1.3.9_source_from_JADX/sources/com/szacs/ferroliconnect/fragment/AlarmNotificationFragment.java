package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.szacs.ferroliconnect.C1683R;

public class AlarmNotificationFragment extends DialogFragment {
    private AlertDialog dialog;
    private View view;

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_alarm_notification, (ViewGroup) null);
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent launchIntentForPackage = AlarmNotificationFragment.this.getActivity().getPackageManager().getLaunchIntentForPackage(AlarmNotificationFragment.this.getActivity().getPackageName());
                launchIntentForPackage.addFlags(67108864);
                launchIntentForPackage.addFlags(32768);
                AlarmNotificationFragment.this.startActivity(launchIntentForPackage);
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).show();
        return this.dialog;
    }

    public AlertDialog getDialog() {
        return this.dialog;
    }
}
