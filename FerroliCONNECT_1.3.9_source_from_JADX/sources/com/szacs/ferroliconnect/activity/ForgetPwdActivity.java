package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.EmailCodeResponse;
import com.p107tb.appyunsdk.bean.ModifyResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.InputUtil;
import com.szacs.ferroliconnect.util.ToastUtil;

public class ForgetPwdActivity extends MyAppCompatActivity implements TextWatcher, View.OnClickListener {
    @BindView(2131296331)
    TextView CodeTV;
    /* access modifiers changed from: private */
    public int count;
    @BindView(2131296415)
    EditText editTextCode;
    @BindView(2131296416)
    EditText editTextEmail;
    @BindView(2131296417)
    EditText editTextPassword;
    @BindView(2131296441)
    EditText etConfirmPassword;
    /* access modifiers changed from: private */
    public boolean isEmailSend;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (!ForgetPwdActivity.this.isFinishing()) {
                if (ForgetPwdActivity.this.count == 0) {
                    boolean unused = ForgetPwdActivity.this.isEmailSend = false;
                    ForgetPwdActivity.this.CodeTV.setEnabled(true);
                    ForgetPwdActivity.this.CodeTV.setText(ForgetPwdActivity.this.getString(C1683R.string.register_get_code));
                    return;
                }
                ForgetPwdActivity.access$110(ForgetPwdActivity.this);
                ForgetPwdActivity.this.CodeTV.setText(String.format("%02d", new Object[]{Integer.valueOf(ForgetPwdActivity.this.count)}));
                ForgetPwdActivity.this.mHandler.postDelayed(this, 1000);
            }
        }
    };

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    static /* synthetic */ int access$110(ForgetPwdActivity forgetPwdActivity) {
        int i = forgetPwdActivity.count;
        forgetPwdActivity.count = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_forget_pwd);
        ButterKnife.bind((Activity) this);
        this.mHandler = new Handler();
        initToolbar();
        this.editTextEmail.addTextChangedListener(this);
        findViewById(C1683R.C1685id.btn_forget).setOnClickListener(this);
        findViewById(C1683R.C1685id.btnCode).setOnClickListener(this);
    }

    public void onClick(View view) {
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            view.startAnimation(AnimationUtils.loadAnimation(this.mContext, C1683R.anim.f3107an));
            int id = view.getId();
            if (id == C1683R.C1685id.btnCode) {
                String trim = this.editTextEmail.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    ToastUtil.showShortToast(this, getString(C1683R.string.register_email_cannot_be_blank));
                } else {
                    AppYunManager.getInstance().sendCodeToEmail(trim, "reset_pwd", new IAppYunResponseListener<EmailCodeResponse>() {
                        public void onSuccess(EmailCodeResponse emailCodeResponse) {
                            ToastUtil.showShortToast(ForgetPwdActivity.this.mContext, ForgetPwdActivity.this.getString(C1683R.string.get_verification_code_tip));
                            boolean unused = ForgetPwdActivity.this.isEmailSend = true;
                            ForgetPwdActivity.this.CodeTV.setEnabled(false);
                            int unused2 = ForgetPwdActivity.this.count = 120;
                            ForgetPwdActivity.this.CodeTV.setText(String.format("%02d", new Object[]{Integer.valueOf(ForgetPwdActivity.this.count)}));
                            ForgetPwdActivity.this.mHandler.postDelayed(ForgetPwdActivity.this.runnable, 1000);
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(ForgetPwdActivity.this.mContext, HttpError.getMessage(ForgetPwdActivity.this.mContext, i));
                        }
                    });
                }
            } else if (id == C1683R.C1685id.btn_forget) {
                String trim2 = this.editTextPassword.getText().toString().trim();
                String trim3 = this.etConfirmPassword.getText().toString().trim();
                String trim4 = this.editTextEmail.getText().toString().trim();
                String trim5 = this.editTextCode.getText().toString().trim();
                if (TextUtils.isEmpty(trim2)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.login_password_cannot_be_blank));
                } else if (TextUtils.isEmpty(trim3)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.confirm_password_cannot_be_blank));
                } else if (!trim3.equals(trim2)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.confirm_password_and_password_not_same));
                } else if (TextUtils.isEmpty(trim4)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_email_cannot_be_blank));
                } else if (TextUtils.isEmpty(trim5)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_verify_code_can_not_blank));
                } else {
                    ShowProgressDialog((String) null);
                    AppYunManager.getInstance().modifyPasswordByEmail(trim4, trim5, trim2, new IAppYunResponseListener<ModifyResponse>() {
                        public void onSuccess(ModifyResponse modifyResponse) {
                            ForgetPwdActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(ForgetPwdActivity.this.mContext, ForgetPwdActivity.this.getString(C1683R.string.register_modify_success));
                            ForgetPwdActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            ForgetPwdActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(ForgetPwdActivity.this.mContext, HttpError.getMessage(ForgetPwdActivity.this.mContext, i));
                        }
                    });
                }
            }
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (!this.isEmailSend) {
            if (InputUtil.isEmail(charSequence)) {
                this.CodeTV.setEnabled(true);
            } else {
                this.CodeTV.setEnabled(false);
            }
        }
    }
}
