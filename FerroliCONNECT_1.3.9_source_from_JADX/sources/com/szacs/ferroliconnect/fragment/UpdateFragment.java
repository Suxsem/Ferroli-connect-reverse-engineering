package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;

public class UpdateFragment extends DialogFragment {
    private AlertDialog dialog;
    private MainApplication mainApplication;
    private View view;

    public Dialog onCreateDialog(Bundle bundle) {
        this.mainApplication = MainApplication.getInstance();
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_update_notification, (ViewGroup) null);
        TextView textView = (TextView) this.view.findViewById(C1683R.C1685id.tvNotification);
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                UpdateFragment.this.getActivity().finish();
            }
        }).show();
        return this.dialog;
    }
}
