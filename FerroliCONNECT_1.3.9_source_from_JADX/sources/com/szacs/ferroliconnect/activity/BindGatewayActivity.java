package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.BindDeviceResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import java.util.Calendar;
import java.util.Locale;

public class BindGatewayActivity extends MyNavigationActivity implements View.OnClickListener {
    @BindView(2131296327)
    QMUIRoundButton bindBtn;
    @BindView(2131296436)
    EditText etGatewayId;
    @BindView(2131296437)
    EditText etGatewayName;
    @BindView(2131296601)
    RelativeLayout mainLinearLayout;
    @BindView(2131296787)
    TextView tvAddGatewayStatus;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_add_gateway;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        setTitle(getResources().getString(C1683R.string.menu_add_device));
        this.tvAddGatewayStatus.getPaint().setFlags(8);
        this.tvAddGatewayStatus.getPaint().setAntiAlias(true);
        this.drawer.setDrawerLockMode(1);
        this.tvAddGatewayStatus.setOnClickListener(this);
        this.bindBtn.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 100 && i2 == -1) {
            String stringExtra = intent.getStringExtra("MAC");
            this.etGatewayName.setText(intent.getStringExtra("DID"));
            this.etGatewayId.setText(stringExtra);
        }
    }

    private void bindDevice(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.add_device_dev_empty));
        } else if (TextUtils.isEmpty(str2)) {
            ToastUtil.showShortToast(this.mContext, getString(C1683R.string.add_device_mac_empty));
        } else {
            ShowProgressDialog((String) null);
            if (!str.startsWith("0000010006")) {
                str = "0000010006" + str;
            }
            int i = (((Calendar.getInstance(Locale.getDefault()).get(15) / 60) / 60) / 1000) + 12;
            Log.d(this.TAG, " TimeZone = " + i);
            AppYunManager.getInstance().bindDeviceNew(str, str2, i, new IAppYunResponseListener<BindDeviceResponse>() {
                public void onSuccess(BindDeviceResponse bindDeviceResponse) {
                    Log.i("testHide", "hide1");
                    BindGatewayActivity.this.HideProgressDialog();
                    ToastUtil.showShortToast(BindGatewayActivity.this.mContext, BindGatewayActivity.this.getString(C1683R.string.add_device_successfully));
                    BindGatewayActivity.this.finish();
                }

                public void onFailure(int i, String str) {
                    Log.i("testHide", "hide2");
                    BindGatewayActivity.this.HideProgressDialog();
                    ToastUtil.showShortToast(BindGatewayActivity.this.mContext, HttpError.getMessage(BindGatewayActivity.this.mContext, i));
                }
            });
        }
    }

    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            view.startAnimation(AnimationUtils.loadAnimation(this.mContext, C1683R.anim.f3107an));
            int id = view.getId();
            if (id == C1683R.C1685id.btAddGateway) {
                bindDevice(this.etGatewayName.getText().toString(), this.etGatewayId.getText().toString());
            } else if (id == C1683R.C1685id.tvAddGatewayStatus) {
                Intent intent = new Intent();
                intent.setClass(this, ConfigWiFiActivity.class);
                intent.putExtra("FindDevice", true);
                startActivityForResult(intent, 100);
            }
        }
    }
}
