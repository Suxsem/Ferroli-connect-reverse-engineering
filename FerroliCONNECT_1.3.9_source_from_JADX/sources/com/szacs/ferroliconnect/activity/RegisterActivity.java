package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.EmailCodeResponse;
import com.p107tb.appyunsdk.bean.EmailRegisterResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.InputUtil;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.taobao.agoo.p105a.p106a.C2126c;

public class RegisterActivity extends MyAppCompatActivity implements TextWatcher, View.OnClickListener {
    @BindView(2131296331)
    TextView CodeTV;
    /* access modifiers changed from: private */
    public int count;
    @BindView(2131296414)
    EditText editTextCity;
    @BindView(2131296415)
    EditText editTextCode;
    @BindView(2131296416)
    EditText editTextEmail;
    @BindView(2131296417)
    EditText editTextPassword;
    @BindView(2131296418)
    EditText editTextPasswordConfirm;
    @BindView(2131296419)
    EditText editTextPhone;
    @BindView(2131296420)
    EditText editTextUsername;
    /* access modifiers changed from: private */
    public boolean isEmailSend;
    @BindView(2131296568)
    LinearLayout llMain;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public Runnable runnable = new Runnable() {
        public void run() {
            if (!RegisterActivity.this.isFinishing()) {
                if (RegisterActivity.this.count == 0) {
                    boolean unused = RegisterActivity.this.isEmailSend = false;
                    RegisterActivity.this.CodeTV.setEnabled(true);
                    RegisterActivity.this.CodeTV.setText(RegisterActivity.this.getString(C1683R.string.register_get_code));
                    return;
                }
                RegisterActivity.access$110(RegisterActivity.this);
                RegisterActivity.this.CodeTV.setText(String.format("%02d", new Object[]{Integer.valueOf(RegisterActivity.this.count)}));
                RegisterActivity.this.mHandler.postDelayed(this, 1000);
            }
        }
    };
    @BindView(2131296776)
    TextView titleTv;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    static /* synthetic */ int access$110(RegisterActivity registerActivity) {
        int i = registerActivity.count;
        registerActivity.count = i - 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_register);
        ButterKnife.bind((Activity) this);
        initToolbar();
        this.mHandler = new Handler();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        this.editTextEmail.addTextChangedListener(this);
        findViewById(C1683R.C1685id.btnCode).setOnClickListener(this);
        findViewById(C1683R.C1685id.editTextCity).setOnClickListener(this);
        findViewById(C1683R.C1685id.buttonRegister).setOnClickListener(this);
    }

    public void setLocation(String str, String str2, String str3, String str4, String str5, String str6) {
        if (getResources().getString(C1683R.string.app_language).equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            EditText editText = this.editTextCity;
            editText.setText(str + str3 + str4);
            return;
        }
        EditText editText2 = this.editTextCity;
        editText2.setText(str4 + " " + str3 + " " + str);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
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
                    AppYunManager.getInstance().sendCodeToEmail(trim, C2126c.JSON_CMD_REGISTER, new IAppYunResponseListener<EmailCodeResponse>() {
                        public void onSuccess(EmailCodeResponse emailCodeResponse) {
                            ToastUtil.showShortToast(RegisterActivity.this.mContext, RegisterActivity.this.getString(C1683R.string.get_verification_code_tip));
                            boolean unused = RegisterActivity.this.isEmailSend = true;
                            RegisterActivity.this.CodeTV.setEnabled(false);
                            int unused2 = RegisterActivity.this.count = 120;
                            RegisterActivity.this.CodeTV.setText(String.format("%02d", new Object[]{Integer.valueOf(RegisterActivity.this.count)}));
                            RegisterActivity.this.mHandler.postDelayed(RegisterActivity.this.runnable, 1000);
                        }

                        public void onFailure(int i, String str) {
                            RegisterActivity registerActivity = RegisterActivity.this;
                            ToastUtil.showShortToast(registerActivity, HttpError.getMessage(registerActivity.mContext, i));
                        }
                    });
                }
            } else if (id == C1683R.C1685id.buttonRegister) {
                String trim2 = this.editTextUsername.getText().toString().trim();
                String trim3 = this.editTextPassword.getText().toString().trim();
                String trim4 = this.editTextPasswordConfirm.getText().toString().trim();
                String trim5 = this.editTextEmail.getText().toString().trim();
                String trim6 = this.editTextCode.getText().toString().trim();
                if (TextUtils.isEmpty(trim2)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.login_username_cannot_be_blank));
                } else if (trim2.length() > 16 || trim2.length() < 4) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_username_format_error));
                } else if (TextUtils.isEmpty(trim3)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.login_password_cannot_be_blank));
                } else if (!trim3.equalsIgnoreCase(trim4)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_password_do_not_match));
                } else if (TextUtils.isEmpty(trim5)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_email_cannot_be_blank));
                } else if (TextUtils.isEmpty(trim6)) {
                    ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_verify_code_can_not_blank));
                } else {
                    ShowProgressDialog((String) null);
                    AppYunManager.getInstance().regitsterByEmail(trim2, trim3, trim5, trim6, new IAppYunResponseListener<EmailRegisterResponse>() {
                        public void onSuccess(EmailRegisterResponse emailRegisterResponse) {
                            RegisterActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(RegisterActivity.this.mContext, RegisterActivity.this.getString(C1683R.string.register_successfully));
                            RegisterActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            RegisterActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(RegisterActivity.this.mContext, HttpError.getMessage(RegisterActivity.this.mContext, i));
                        }
                    });
                }
            } else if (id == C1683R.C1685id.editTextCity) {
                new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
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
