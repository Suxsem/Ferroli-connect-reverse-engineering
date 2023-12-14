package com.szacs.ferroliconnect.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.ApConfigResponse;

public class ApAddDeviceDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "ApAddDeviceDialog";
    private Button addBtn;
    private ApConfigResponse apConfigResponse;
    private TextView didTv;
    private ApAddDeviceListener listener;
    private TextView macTv;

    public interface ApAddDeviceListener {
        void onApAddClick(ApConfigResponse apConfigResponse);
    }

    public ApAddDeviceDialog(@NonNull Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1683R.C1686layout.dialog_ap_add_device);
        initWidget();
    }

    private void initWidget() {
        this.didTv = (TextView) findViewById(C1683R.C1685id.tv_did);
        this.macTv = (TextView) findViewById(C1683R.C1685id.tv_mac);
        this.addBtn = (Button) findViewById(C1683R.C1685id.btn_add);
        this.didTv.setText(this.apConfigResponse.getDID());
        this.macTv.setText(this.apConfigResponse.getMAC());
        this.addBtn.setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == C1683R.C1685id.btn_add) {
            add();
        }
    }

    private void add() {
        ApAddDeviceListener apAddDeviceListener = this.listener;
        if (apAddDeviceListener != null) {
            apAddDeviceListener.onApAddClick(this.apConfigResponse);
        }
    }

    public ApConfigResponse getApConfigResponse() {
        return this.apConfigResponse;
    }

    public void setApConfigResponse(ApConfigResponse apConfigResponse2) {
        this.apConfigResponse = apConfigResponse2;
    }

    public void setApAddDeviceListener(ApAddDeviceListener apAddDeviceListener) {
        this.listener = apAddDeviceListener;
    }
}
