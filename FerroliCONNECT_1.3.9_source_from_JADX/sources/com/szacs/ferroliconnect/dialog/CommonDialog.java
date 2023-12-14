package com.szacs.ferroliconnect.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.taobao.accs.common.Constants;

public class CommonDialog extends DialogFragment implements View.OnClickListener {
    private TextView MessageTv;
    private QMUIRoundButton NoButton;
    private QMUIRoundButton YesButton;
    private Activity activity;
    private AlertDialog dialog;
    private ConfirmListener listener;
    private String message;
    private View view;

    public interface ConfirmListener {
        void onConfirmClick();
    }

    public static CommonDialog getInstance(String str) {
        CommonDialog commonDialog = new CommonDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.SHARED_MESSAGE_ID_FILE, str);
        commonDialog.setArguments(bundle);
        return commonDialog;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.message = getArguments().getString(Constants.SHARED_MESSAGE_ID_FILE);
        if (this.message == null) {
            this.message = "";
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C1683R.C1686layout.common_dialog_layout, viewGroup);
        this.MessageTv = (TextView) inflate.findViewById(C1683R.C1685id.msgTv);
        this.MessageTv.setText(this.message);
        this.YesButton = (QMUIRoundButton) inflate.findViewById(C1683R.C1685id.YesBtn);
        this.YesButton.setOnClickListener(this);
        this.NoButton = (QMUIRoundButton) inflate.findViewById(C1683R.C1685id.NoBtn);
        this.NoButton.setOnClickListener(this);
        return inflate;
    }

    public void onClick(View view2) {
        int id = view2.getId();
        if (id == C1683R.C1685id.NoBtn) {
            dismiss();
        } else if (id == C1683R.C1685id.YesBtn) {
            ConfirmListener confirmListener = this.listener;
            if (confirmListener != null) {
                confirmListener.onConfirmClick();
            }
            dismiss();
        }
    }

    public ConfirmListener getListener() {
        return this.listener;
    }

    public void setListener(ConfirmListener confirmListener) {
        this.listener = confirmListener;
    }
}
