package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.EmailCodeResponse;
import com.p107tb.appyunsdk.bean.ModifyResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import java.util.concurrent.TimeUnit;
import p110io.reactivex.Observable;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;

public class EmailActivity extends MyAppCompatActivity implements TextWatcher, View.OnClickListener {
    @BindView(2131296331)
    TextView btnCode;
    @BindView(2131296335)
    QMUIRoundButton button;
    @BindView(2131296387)
    QMUILinearLayout codeLayout;
    @BindView(2131296415)
    EditText etCode;
    @BindView(2131296416)
    EditText etEmail;
    private Disposable mCountDownDisposable;
    private String oldEmail;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_email);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        ButterKnife.bind((Activity) this);
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EmailActivity.this.finish();
            }
        });
        this.oldEmail = getIntent().getStringExtra("email");
        if (!TextUtils.isEmpty(this.oldEmail)) {
            this.etEmail.setText(this.oldEmail);
            this.codeLayout.setVisibility(4);
            this.button.setVisibility(4);
            this.etEmail.addTextChangedListener(this);
        }
        findViewById(C1683R.C1685id.btn_forget).setOnClickListener(this);
        this.btnCode.setOnClickListener(this);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.btnCode) {
            String trim = this.etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                ToastUtil.showShortToast(this, getString(C1683R.string.register_email_cannot_be_blank));
            } else if (trim.equalsIgnoreCase(this.oldEmail)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_email_same));
            } else {
                startCountDown();
                if (TextUtils.isEmpty(this.oldEmail)) {
                    AppYunManager.getInstance().sendCodeToEmail(trim, "bind_email", new IAppYunResponseListener<EmailCodeResponse>() {
                        public void onSuccess(EmailCodeResponse emailCodeResponse) {
                            ToastUtil.showShortToast(EmailActivity.this.mContext, EmailActivity.this.getString(C1683R.string.get_verification_code_tip));
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(EmailActivity.this.mContext, HttpError.getMessage(EmailActivity.this, i));
                            EmailActivity.this.stopCountDown();
                        }
                    });
                } else if (!this.oldEmail.equalsIgnoreCase(trim)) {
                    AppYunManager.getInstance().sendCodeToEmail(trim, "reset_email", new IAppYunResponseListener<EmailCodeResponse>() {
                        public void onSuccess(EmailCodeResponse emailCodeResponse) {
                            ToastUtil.showShortToast(EmailActivity.this.mContext, EmailActivity.this.getString(C1683R.string.get_verification_code_tip));
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(EmailActivity.this.mContext, HttpError.getMessage(EmailActivity.this, i));
                            EmailActivity.this.stopCountDown();
                        }
                    });
                }
            }
        } else if (id == C1683R.C1685id.btn_forget) {
            String trim2 = this.etEmail.getText().toString().trim();
            String trim3 = this.etCode.getText().toString().trim();
            if (TextUtils.isEmpty(trim2)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_email_cannot_be_blank));
            } else if (TextUtils.isEmpty(trim3)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_verify_code_can_not_blank));
            } else if (trim2.equalsIgnoreCase(this.oldEmail)) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.register_email_same));
            } else {
                ShowProgressDialog((String) null);
                if (TextUtils.isEmpty(this.oldEmail)) {
                    AppYunManager.getInstance().bindEmailByCode(trim2, trim3, new IAppYunResponseListener<ModifyResponse>() {
                        public void onSuccess(ModifyResponse modifyResponse) {
                            EmailActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(EmailActivity.this.mContext, EmailActivity.this.getString(C1683R.string.register_modify_success));
                            EmailActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            EmailActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(EmailActivity.this.mContext, HttpError.getMessage(EmailActivity.this, i));
                        }
                    });
                } else {
                    AppYunManager.getInstance().modifyEmailByCode(trim2, trim3, new IAppYunResponseListener<ModifyResponse>() {
                        public void onSuccess(ModifyResponse modifyResponse) {
                            EmailActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(EmailActivity.this.mContext, EmailActivity.this.getString(C1683R.string.register_modify_success));
                            EmailActivity.this.finish();
                        }

                        public void onFailure(int i, String str) {
                            EmailActivity.this.HideProgressDialog();
                            ToastUtil.showShortToast(EmailActivity.this.mContext, HttpError.getMessage(EmailActivity.this, i));
                        }
                    });
                }
            }
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = 4;
        this.codeLayout.setVisibility(TextUtils.equals(charSequence, this.oldEmail) ? 4 : 0);
        QMUIRoundButton qMUIRoundButton = this.button;
        if (!TextUtils.equals(charSequence, this.oldEmail)) {
            i4 = 0;
        }
        qMUIRoundButton.setVisibility(i4);
    }

    private void startCountDown() {
        Disposable disposable = this.mCountDownDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.mCountDownDisposable = Observable.intervalRange(0, 122, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
            public void accept(Long l) throws Exception {
                if (120 - l.longValue() >= 0) {
                    EmailActivity.this.btnCode.setText(String.valueOf(120 - l.longValue()));
                    EmailActivity.this.btnCode.setBackgroundColor(EmailActivity.this.getResources().getColor(C1683R.color.cloudwarm_grey));
                    EmailActivity.this.btnCode.setEnabled(false);
                    return;
                }
                EmailActivity.this.btnCode.setText(EmailActivity.this.getString(C1683R.string.register_get_code));
                EmailActivity.this.btnCode.setBackgroundColor(EmailActivity.this.getResources().getColor(C1683R.color.ferroli_green));
                EmailActivity.this.btnCode.setEnabled(true);
            }
        }).subscribe();
    }

    /* access modifiers changed from: private */
    public void stopCountDown() {
        Disposable disposable = this.mCountDownDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.btnCode.setText(getString(C1683R.string.register_get_code));
        this.btnCode.setBackgroundColor(getResources().getColor(C1683R.color.ferroli_green));
        this.btnCode.setEnabled(true);
    }
}
