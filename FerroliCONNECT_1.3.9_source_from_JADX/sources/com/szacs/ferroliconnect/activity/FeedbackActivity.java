package com.szacs.ferroliconnect.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import butterknife.BindView;
import com.p107tb.appyunsdk.Constant;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.net.HttpExceptionEngine;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import java.util.HashMap;
import java.util.regex.Pattern;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class FeedbackActivity extends MyNavigationActivity {
    @BindView(2131296435)
    EditText etFeedback;
    @BindView(2131296440)
    EditText etPhone;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_feedback;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setTitle(getString(C1683R.string.menu_feedback));
        this.drawer.setDrawerLockMode(1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_feedback, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != C1683R.C1685id.muSubmit) {
            return super.onOptionsItemSelected(menuItem);
        }
        feedback(this.etFeedback.getText().toString(), this.etPhone.getText().toString());
        return true;
    }

    private void feedback(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("content", str);
        if (str2 != null && !str2.trim().equals("")) {
            if (isEmail(str2)) {
                Log.d(this.TAG, " 联系方式为邮箱");
                hashMap.put("email", str2);
            } else {
                Log.d(this.TAG, " 联系方式为手机号码");
                hashMap.put(Constant.PHONE, str2);
            }
        }
        String str3 = this.TAG;
        LogUtil.m3315d(str3, "params :" + hashMap);
        ShowProgressDialog((String) null);
        HttpUtils.getRetrofit().feedback(hashMap, this.authorzation, MainApplication.appSlug).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                LogUtil.m3315d(FeedbackActivity.this.TAG, "on feedback success");
                FeedbackActivity feedbackActivity = FeedbackActivity.this;
                ToastUtil.showShortToast(feedbackActivity, feedbackActivity.getString(C1683R.string.public_successfully));
                FeedbackActivity.this.HideProgressDialog();
                FeedbackActivity.this.finish();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                th.printStackTrace();
                LogUtil.m3315d(FeedbackActivity.this.TAG, "on feedback fail");
                FeedbackActivity.this.HideProgressDialog();
                FeedbackActivity feedbackActivity = FeedbackActivity.this;
                ToastUtil.showShortToast(feedbackActivity, HttpError.getMessage(feedbackActivity, HttpExceptionEngine.getErrorInfo(th).getCode()));
            }
        });
    }

    private boolean isEmail(String str) {
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(str).find();
    }
}
