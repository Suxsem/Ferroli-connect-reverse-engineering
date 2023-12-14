package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.Constant;
import com.p107tb.appyunsdk.bean.CodeResponse;
import com.p107tb.appyunsdk.bean.ModifyResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.util.ToastUtil;

public class PhoneActivity extends MyAppCompatActivity implements TextWatcher, View.OnClickListener {
    @BindView(2131296335)
    QMUIRoundButton button;
    @BindView(2131296387)
    QMUILinearLayout codeLayout;
    @BindView(2131296415)
    EditText etCode;
    @BindView(2131296416)
    EditText etPhone;
    private String oldPhone;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_phone);
        ButterKnife.bind((Activity) this);
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PhoneActivity.this.finish();
            }
        });
        this.oldPhone = getIntent().getStringExtra(Constant.PHONE);
        if (!TextUtils.isEmpty(this.oldPhone)) {
            this.etPhone.setText(this.oldPhone);
            this.codeLayout.setVisibility(4);
            this.button.setVisibility(4);
            this.etPhone.addTextChangedListener(this);
        }
        findViewById(C1683R.C1685id.btn_forget).setOnClickListener(this);
        findViewById(C1683R.C1685id.btnCode).setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.btnCode) {
            String trim = this.etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.showShortToast(this, getString(C1683R.string.register_phone_empty));
            } else if (trim.equalsIgnoreCase(this.oldPhone)) {
                ToastUtil.showShortToast(this, getString(C1683R.string.register_phone_same));
            } else if (TextUtils.isEmpty(this.oldPhone)) {
                AppYunManager.getInstance().sendCodeForBind(trim, new IAppYunResponseListener<CodeResponse>() {
                    public void onSuccess(CodeResponse codeResponse) {
                        ToastUtil.showShortToast(PhoneActivity.this.mContext, PhoneActivity.this.getString(C1683R.string.register_phone_code_has_been_send));
                    }

                    public void onFailure(int i, String str) {
                        ToastUtil.showShortToast(PhoneActivity.this.mContext, HttpError.getMessage(PhoneActivity.this.mContext, i));
                    }
                });
            } else if (!this.oldPhone.equalsIgnoreCase(trim)) {
                AppYunManager.getInstance().sendCodeForPhone(trim, new IAppYunResponseListener<CodeResponse>() {
                    public void onSuccess(CodeResponse codeResponse) {
                        ToastUtil.showShortToast(PhoneActivity.this.mContext, PhoneActivity.this.getString(C1683R.string.register_phone_code_has_been_send));
                    }

                    public void onFailure(int i, String str) {
                        ToastUtil.showShortToast(PhoneActivity.this.mContext, HttpError.getMessage(PhoneActivity.this.mContext, i));
                    }
                });
            }
        } else if (id == C1683R.C1685id.btn_forget) {
            String trim2 = this.etPhone.getText().toString().trim();
            String trim3 = this.etCode.getText().toString().trim();
            if (TextUtils.isEmpty(trim2)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_phone_empty));
            } else if (TextUtils.isEmpty(trim3)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_verify_code_can_not_blank));
            } else if (trim2.equalsIgnoreCase(this.oldPhone)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_phone_same));
            } else {
                ShowProgressDialog((String) null);
                if (TextUtils.isEmpty(this.oldPhone)) {
                    AppYunManager.getInstance().bindPhoneByCode(trim2, trim3, new IAppYunResponseListener<ModifyResponse>() {
                        public void onSuccess(ModifyResponse modifyResponse) {
                            PhoneActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(PhoneActivity.this.mContext, PhoneActivity.this.getString(C1683R.string.register_modify_success));
                            PhoneActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            PhoneActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(PhoneActivity.this.mContext, HttpError.getMessage(PhoneActivity.this.mContext, i));
                        }
                    });
                } else {
                    AppYunManager.getInstance().modifyPhoneByCode(trim2, trim3, new IAppYunResponseListener<ModifyResponse>() {
                        public void onSuccess(ModifyResponse modifyResponse) {
                            PhoneActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(PhoneActivity.this.mContext, PhoneActivity.this.getString(C1683R.string.register_modify_success));
                            PhoneActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            PhoneActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(PhoneActivity.this.mContext, HttpError.getMessage(PhoneActivity.this.mContext, i));
                        }
                    });
                }
            }
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = 4;
        this.codeLayout.setVisibility(TextUtils.equals(charSequence, this.oldPhone) ? 4 : 0);
        QMUIRoundButton qMUIRoundButton = this.button;
        if (!TextUtils.equals(charSequence, this.oldPhone)) {
            i4 = 0;
        }
        qMUIRoundButton.setVisibility(i4);
    }
}
