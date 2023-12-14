package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.EmailCodeResponse;
import com.p107tb.appyunsdk.bean.ModifyResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.util.ToastUtil;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import p110io.reactivex.Observable;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;

public class ChangePasswordFragment extends DialogFragment {
    static final String TAG = "ChangePasswordFragment";
    /* access modifiers changed from: private */
    public AlertDialog dialog;
    private EditText etConfirmNewPassword;
    /* access modifiers changed from: private */
    public EditText etNewPassword;
    /* access modifiers changed from: private */
    public TextView mBtnGetCode;
    private Disposable mCountDownDisposable;
    private EditText mEtEmail;
    /* access modifiers changed from: private */
    public EditText mEtVerifyCode;
    /* access modifiers changed from: private */
    public OnPasswordChangeCallback onPasswordChange;
    private View view;

    public interface OnPasswordChangeCallback {
        void onChange();
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_change_password, (ViewGroup) null);
        this.etNewPassword = (EditText) this.view.findViewById(C1683R.C1685id.etNewPassword);
        this.etConfirmNewPassword = (EditText) this.view.findViewById(C1683R.C1685id.etConfirmNewPassword);
        this.mBtnGetCode = (TextView) this.view.findViewById(C1683R.C1685id.btn_get_verify_code);
        this.mEtVerifyCode = (EditText) this.view.findViewById(C1683R.C1685id.et_verify_code);
        this.mEtEmail = (EditText) this.view.findViewById(C1683R.C1685id.et_email);
        this.mBtnGetCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ChangePasswordFragment.this.startCountDown();
                AppYunManager.getInstance().sendCodeToEmail(UserCenter.getUserInfo().getEmail(), "reset_pwd", new IAppYunResponseListener<EmailCodeResponse>() {
                    public void onSuccess(EmailCodeResponse emailCodeResponse) {
                        ToastUtil.showShortToast(ChangePasswordFragment.this.getActivity(), ChangePasswordFragment.this.getString(C1683R.string.get_verification_code_tip));
                    }

                    public void onFailure(int i, String str) {
                        if (ChangePasswordFragment.this.isAdded()) {
                            ToastUtil.showShortToast(ChangePasswordFragment.this.getActivity(), HttpError.getMessage(ChangePasswordFragment.this.getActivity(), i));
                            ChangePasswordFragment.this.stopCountDown();
                        }
                    }
                });
            }
        });
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setTitle(C1683R.string.user_info_change_pwd).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ChangePasswordFragment.this.dismiss();
            }
        }).create();
        this.dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                ChangePasswordFragment.this.dialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (ChangePasswordFragment.this.checkPassword()) {
                            String email = UserCenter.getUserInfo().getEmail();
                            if (email == null) {
                                ChangePasswordFragment.this.showToast(ChangePasswordFragment.this.getActivity(), ChangePasswordFragment.this.getString(C1683R.string.register_email_cannot_be_blank));
                                Log.e(ChangePasswordFragment.TAG, "get verify code failed, no user phone");
                                return;
                            }
                            AppYunManager.getInstance().modifyPasswordByEmail(email, ChangePasswordFragment.this.mEtVerifyCode.getText().toString(), ChangePasswordFragment.this.etNewPassword.getText().toString(), new IAppYunResponseListener<ModifyResponse>() {
                                public void onSuccess(ModifyResponse modifyResponse) {
                                    if (ChangePasswordFragment.this.onPasswordChange != null) {
                                        ChangePasswordFragment.this.onPasswordChange.onChange();
                                    }
                                    ChangePasswordFragment.this.dismiss();
                                }

                                public void onFailure(int i, String str) {
                                    ChangePasswordFragment.this.showToast(ChangePasswordFragment.this.getActivity(), HttpError.getMessage(ChangePasswordFragment.this.getActivity(), i));
                                }
                            });
                        }
                    }
                });
            }
        });
        return this.dialog;
    }

    /* access modifiers changed from: private */
    public void startCountDown() {
        Disposable disposable = this.mCountDownDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.mCountDownDisposable = Observable.intervalRange(0, 122, 0, 1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<Long>() {
            public void accept(Long l) throws Exception {
                if (120 - l.longValue() >= 0) {
                    ChangePasswordFragment.this.mBtnGetCode.setText(String.valueOf(120 - l.longValue()));
                    ChangePasswordFragment.this.mBtnGetCode.setBackgroundColor(ChangePasswordFragment.this.getResources().getColor(C1683R.color.cloudwarm_grey));
                    ChangePasswordFragment.this.mBtnGetCode.setEnabled(false);
                    return;
                }
                ChangePasswordFragment.this.mBtnGetCode.setText(ChangePasswordFragment.this.getString(C1683R.string.register_get_code));
                ChangePasswordFragment.this.mBtnGetCode.setBackgroundColor(ChangePasswordFragment.this.getResources().getColor(C1683R.color.ferroli_green));
                ChangePasswordFragment.this.mBtnGetCode.setEnabled(true);
            }
        }).subscribe();
    }

    public void onDetach() {
        super.onDetach();
        dismiss();
    }

    /* access modifiers changed from: private */
    public void stopCountDown() {
        Disposable disposable = this.mCountDownDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.mBtnGetCode.setText(getString(C1683R.string.register_get_code));
        this.mBtnGetCode.setBackgroundColor(getResources().getColor(C1683R.color.ferroli_green));
        this.mBtnGetCode.setEnabled(true);
    }

    /* access modifiers changed from: private */
    public boolean checkPassword() {
        if (TextUtils.isEmpty(this.etNewPassword.getText()) || TextUtils.isEmpty(this.etConfirmNewPassword.getText())) {
            showToast(getActivity(), getString(C1683R.string.login_password_cannot_be_blank));
            return false;
        } else if (!this.etNewPassword.getText().toString().equals(this.etConfirmNewPassword.getText().toString())) {
            showToast(getActivity(), getString(C1683R.string.register_password_do_not_match));
            return false;
        } else if (isPassword(this.etNewPassword.getText().toString())) {
            return true;
        } else {
            showToast(getActivity(), getString(C1683R.string.register_password_format_error));
            return false;
        }
    }

    private boolean isPassword(String str) {
        return Pattern.compile("^[a-zA-Z0-9]{6,16}$").matcher(str).find();
    }

    public void dismiss() {
        super.dismiss();
        Disposable disposable = this.mCountDownDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* access modifiers changed from: private */
    public void showToast(Context context, String str) {
        if (isAdded()) {
            ToastUtil.showShortToast(context, str);
        }
    }

    public void setPasswordChangeCallback(OnPasswordChangeCallback onPasswordChangeCallback) {
        this.onPasswordChange = onPasswordChangeCallback;
    }
}
