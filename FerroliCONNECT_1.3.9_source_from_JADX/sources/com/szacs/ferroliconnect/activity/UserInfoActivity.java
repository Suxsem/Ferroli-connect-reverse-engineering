package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p000v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.Constant;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.fragment.ChangePasswordFragment;
import com.szacs.ferroliconnect.fragment.ChooseLocationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.SpUtil;
import java.io.File;
import java.util.Date;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p109de.hdodenhof.circleimageview.CircleImageView;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends MyCameraRequestActivity implements View.OnClickListener {
    private static final String TAG = "UserInfoActivity";
    private ChangePasswordFragment changePasswordFragment;
    @BindView(2131296384)
    ImageView civPortrait;
    @BindView(2131296546)
    TextView languageTv;
    @BindView(2131296545)
    LinearLayout llLanguageSet;
    @BindView(2131296567)
    LinearLayout llLocation;
    @BindView(2131296788)
    TextView tvAddress;
    @BindView(2131296806)
    TextView tvEmail;
    @BindView(2131296849)
    TextView tvPhone;
    @BindView(2131296893)
    TextView tvUsername;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_user_info;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
        setTitle(getString(C1683R.string.menu_account));
        this.drawer.setDrawerLockMode(1);
        this.languageTv.setText(LanguageUtil.convertLogogram2Name(SpUtil.getInstance(this).getString("language")));
        this.tvAddress.setText(this.city);
        getUserInformation();
        findViewById(C1683R.C1685id.btn_logout).setOnClickListener(this);
        findViewById(C1683R.C1685id.civPortrait).setOnClickListener(this);
        findViewById(C1683R.C1685id.llLocation).setOnClickListener(this);
        findViewById(C1683R.C1685id.phone_layout).setOnClickListener(this);
        findViewById(C1683R.C1685id.email_layout).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_change_pwd).setOnClickListener(this);
        findViewById(C1683R.C1685id.language_set).setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1683R.C1685id.btn_change_pwd:
                this.changePasswordFragment = new ChangePasswordFragment();
                this.changePasswordFragment.show(getFragmentManager(), "changePasswordFragment");
                this.changePasswordFragment.setPasswordChangeCallback(new ChangePasswordFragment.OnPasswordChangeCallback() {
                    public void onChange() {
                        UserInfoActivity.this.confirmToLogout();
                    }
                });
                return;
            case C1683R.C1685id.btn_logout:
                logout();
                return;
            case C1683R.C1685id.email_layout:
                Intent intent = new Intent(this, EmailActivity.class);
                intent.putExtra("email", UserCenter.getUserInfo().getEmail());
                startActivity(intent);
                return;
            case C1683R.C1685id.language_set:
                startActivity(new Intent(this, SelectLanguageActivity.class));
                return;
            case C1683R.C1685id.llLocation:
                new ChooseLocationFragment().show(getFragmentManager(), "chooseLocationFragment");
                return;
            case C1683R.C1685id.phone_layout:
                Intent intent2 = new Intent(this, PhoneActivity.class);
                intent2.putExtra(Constant.PHONE, UserCenter.getUserInfo().getMobile());
                startActivity(intent2);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        getUserInformation();
    }

    /* access modifiers changed from: package-private */
    public void setPicToView(Uri uri) {
        LogUtil.m3315d("setPicToView", " uri: " + uri + " photo=" + null);
        Bitmap decodeUriAsBitmap = uri != null ? FileUtil.decodeUriAsBitmap(this, uri) : null;
        if (decodeUriAsBitmap != null) {
            this.civPortrait.setImageDrawable(new BitmapDrawable((Resources) null, decodeUriAsBitmap));
            saveAvatar(decodeUriAsBitmap);
        }
    }

    /* access modifiers changed from: private */
    public void getUserInformation() {
        ShowProgressDialog((String) null);
        AppYunManager.getInstance().getUserInfo(new IAppYunResponseListener<UserResponse>() {
            public void onSuccess(UserResponse userResponse) {
                UserInfoActivity.this.HideProgressDialog();
                Log.d(UserInfoActivity.TAG, " onGet user info success");
                UserInfoActivity.this.username = userResponse.getName();
                UserInfoActivity.this.saveUserInfo(userResponse);
                UserInfoActivity.this.setUserInfo(userResponse);
            }

            public void onFailure(int i, String str) {
                UserInfoActivity.this.HideProgressDialog();
                LogUtil.m3315d(UserInfoActivity.TAG, "onGet user info fail");
                if (UserCenter.getUserInfo() != null) {
                    UserInfoActivity.this.setUserInfo(UserCenter.getUserInfo());
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void saveUserInfo(UserResponse userResponse) {
        UserCenter.setUserInfo(userResponse);
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString(Constant.APP_USERNAME, userResponse.getName());
        edit.putString("email", userResponse.getEmail());
        edit.putString("create_date", userResponse.getCreate_date());
        edit.putString("update_date", userResponse.getUpdate_date());
        edit.putString("user_slug", userResponse.getSlug());
        edit.apply();
    }

    /* access modifiers changed from: private */
    public void setUserInfo(UserResponse userResponse) {
        this.tvUsername.setText(userResponse.getName());
        this.tvEmail.setText(userResponse.getEmail());
        this.tvPhone.setText(userResponse.getMobile());
        this.tvAddress.setText(this.city);
    }

    private void saveAvatar(Bitmap bitmap) {
        String valueOf = String.valueOf(new Date().getTime());
        String str = this.username;
        String saveFile = FileUtil.saveFile(this, str, valueOf + ".jpg", bitmap);
        Log.d("save image name", valueOf);
        File file = new File(saveFile);
        HttpUtils.getRetrofit().saveAvatar(this.authorzation, MultipartBody.Part.createFormData("avatar", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file))).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                LogUtil.m3315d(UserInfoActivity.TAG, "on save avatar success");
                UserInfoActivity.this.getUserInformation();
                UserInfoActivity.this.onGetPortraitSuccess();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(UserInfoActivity.TAG, "on save avatar faile");
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00e3 A[SYNTHETIC, Splitter:B:43:0x00e3] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e8 A[Catch:{ IOException -> 0x00f8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f0 A[Catch:{ IOException -> 0x00f8 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00f5 A[Catch:{ IOException -> 0x00f8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeResponseBodyToDisk(okhttp3.ResponseBody r12, java.lang.String r13) {
        /*
            r11 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f8 }
            r1.<init>()     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r2 = com.szacs.ferroliconnect.util.FileUtil.CLOUDWARM_ROOT_PATH     // Catch:{ IOException -> 0x00f8 }
            r1.append(r2)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r2 = r11.username     // Catch:{ IOException -> 0x00f8 }
            r1.append(r2)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00f8 }
            r11.imagePath = r1     // Catch:{ IOException -> 0x00f8 }
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x00f8 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f8 }
            r2.<init>()     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = r11.imagePath     // Catch:{ IOException -> 0x00f8 }
            r2.append(r3)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = "/"
            r2.append(r3)     // Catch:{ IOException -> 0x00f8 }
            r2.append(r13)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r13 = r2.toString()     // Catch:{ IOException -> 0x00f8 }
            r1.<init>(r13)     // Catch:{ IOException -> 0x00f8 }
            java.io.File r13 = r1.getParentFile()     // Catch:{ IOException -> 0x00f8 }
            boolean r13 = r13.exists()     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r2 = "UserInfoActivity"
            if (r13 != 0) goto L_0x0045
            java.lang.String r13 = " writeResponseBodyToDisk 创建目录"
            android.util.Log.d(r2, r13)     // Catch:{ IOException -> 0x00f8 }
            r1.mkdirs()     // Catch:{ IOException -> 0x00f8 }
        L_0x0045:
            boolean r13 = r1.exists()     // Catch:{ IOException -> 0x0057 }
            if (r13 == 0) goto L_0x004e
            r1.delete()     // Catch:{ IOException -> 0x0057 }
        L_0x004e:
            java.lang.String r13 = " writeResponseBodyToDisk 创建图片"
            android.util.Log.d(r2, r13)     // Catch:{ IOException -> 0x0057 }
            r1.createNewFile()     // Catch:{ IOException -> 0x0057 }
            goto L_0x005b
        L_0x0057:
            r13 = move-exception
            r13.printStackTrace()     // Catch:{ IOException -> 0x00f8 }
        L_0x005b:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f8 }
            r13.<init>()     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = "File path: "
            r13.append(r3)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = r1.getPath()     // Catch:{ IOException -> 0x00f8 }
            r13.append(r3)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x00f8 }
            com.szacs.ferroliconnect.util.LogUtil.m3315d((java.lang.String) r2, (java.lang.String) r13)     // Catch:{ IOException -> 0x00f8 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00f8 }
            r13.<init>()     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = "File name :"
            r13.append(r3)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r3 = r1.getName()     // Catch:{ IOException -> 0x00f8 }
            r13.append(r3)     // Catch:{ IOException -> 0x00f8 }
            java.lang.String r13 = r13.toString()     // Catch:{ IOException -> 0x00f8 }
            com.szacs.ferroliconnect.util.LogUtil.m3315d((java.lang.String) r2, (java.lang.String) r13)     // Catch:{ IOException -> 0x00f8 }
            r13 = 4096(0x1000, float:5.74E-42)
            r3 = 0
            byte[] r13 = new byte[r13]     // Catch:{ IOException -> 0x00ec, all -> 0x00de }
            long r4 = r12.contentLength()     // Catch:{ IOException -> 0x00ec, all -> 0x00de }
            r6 = 0
            java.io.InputStream r12 = r12.byteStream()     // Catch:{ IOException -> 0x00ec, all -> 0x00de }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00dc, all -> 0x00d9 }
            r8.<init>(r1)     // Catch:{ IOException -> 0x00dc, all -> 0x00d9 }
        L_0x009f:
            int r1 = r12.read(r13)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            r3 = -1
            if (r1 != r3) goto L_0x00b3
            r8.flush()     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            r13 = 1
            if (r12 == 0) goto L_0x00af
            r12.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00af:
            r8.close()     // Catch:{ IOException -> 0x00f8 }
            return r13
        L_0x00b3:
            r8.write(r13, r0, r1)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            long r9 = (long) r1     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            long r6 = r6 + r9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            r1.<init>()     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            java.lang.String r3 = "file download: "
            r1.append(r3)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            r1.append(r6)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            java.lang.String r3 = " of "
            r1.append(r3)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            r1.append(r4)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            android.util.Log.d(r2, r1)     // Catch:{ IOException -> 0x00d7, all -> 0x00d5 }
            goto L_0x009f
        L_0x00d5:
            r13 = move-exception
            goto L_0x00e1
        L_0x00d7:
            goto L_0x00ee
        L_0x00d9:
            r13 = move-exception
            r8 = r3
            goto L_0x00e1
        L_0x00dc:
            r8 = r3
            goto L_0x00ee
        L_0x00de:
            r13 = move-exception
            r12 = r3
            r8 = r12
        L_0x00e1:
            if (r12 == 0) goto L_0x00e6
            r12.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00e6:
            if (r8 == 0) goto L_0x00eb
            r8.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00eb:
            throw r13     // Catch:{ IOException -> 0x00f8 }
        L_0x00ec:
            r12 = r3
            r8 = r12
        L_0x00ee:
            if (r12 == 0) goto L_0x00f3
            r12.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00f3:
            if (r8 == 0) goto L_0x00f8
            r8.close()     // Catch:{ IOException -> 0x00f8 }
        L_0x00f8:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.activity.UserInfoActivity.writeResponseBodyToDisk(okhttp3.ResponseBody, java.lang.String):boolean");
    }

    private void getAvatar(String str) {
        Log.d(TAG, " getAvatar url = " + str);
        final String substring = str.substring(str.lastIndexOf("/") + 1);
        LogUtil.m3315d(TAG, "image name== " + substring);
        if (FileUtil.getFile(this.username, substring) != null) {
            Log.d(TAG, " findFile setIntoView");
        } else {
            HttpUtils.getRetrofit().getAvatar(str).enqueue(new Callback<ResponseBody>() {
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        boolean unused = UserInfoActivity.this.writeResponseBodyToDisk(response.body(), substring);
                        Log.d(UserInfoActivity.TAG, "下载成功");
                        return;
                    }
                    Log.d(UserInfoActivity.TAG, "下载失败");
                }

                public void onFailure(Call<ResponseBody> call, Throwable th) {
                    Log.e(UserInfoActivity.TAG, "error");
                }
            });
        }
    }

    private void initPortrait(CircleImageView circleImageView, File file) {
        if (file == null) {
            file = FileUtil.getLatestFile(this.imagePath, "jpg");
        }
        if (file != null && file.exists()) {
            Log.d(TAG, " initPortrait Uri.parse(file.getPath()) = " + Uri.parse(file.getPath()));
            circleImageView.setImageURI(Uri.parse(file.getPath()));
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == C1683R.C1685id.muAddGateway) {
            if (this.drawer.isDrawerOpen(5)) {
                this.drawer.closeDrawer(5);
            } else {
                this.drawer.openDrawer(5);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeLocation(LocationGroup locationGroup) {
        if (LanguageUtil.getSetLanguageLocale(this).getLanguage().equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            this.city = locationGroup.getName().getZh();
        } else {
            this.city = locationGroup.getName().getEn();
        }
        this.cityID = locationGroup.getCity_id();
        this.tvAddress.setText(this.city);
    }

    /* renamed from: com.szacs.ferroliconnect.activity.UserInfoActivity$6 */
    static /* synthetic */ class C18706 {
        static final /* synthetic */ int[] $SwitchMap$com$szacs$ferroliconnect$event$Event = new int[Event.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.szacs.ferroliconnect.event.Event[] r0 = com.szacs.ferroliconnect.event.Event.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$szacs$ferroliconnect$event$Event = r0
                int[] r0 = $SwitchMap$com$szacs$ferroliconnect$event$Event     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.szacs.ferroliconnect.event.Event r1 = com.szacs.ferroliconnect.event.Event.EVENT_OPEN_ALBUM     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$com$szacs$ferroliconnect$event$Event     // Catch:{ NoSuchFieldError -> 0x001f }
                com.szacs.ferroliconnect.event.Event r1 = com.szacs.ferroliconnect.event.Event.EVENT_OPEN_CAMERA     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.activity.UserInfoActivity.C18706.<clinit>():void");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BaseEvent baseEvent) {
        LogUtil.m3315d(TAG, "baseEvent: " + baseEvent);
        int i = C18706.$SwitchMap$com$szacs$ferroliconnect$event$Event[baseEvent.getEvent().ordinal()];
        if (i != 1) {
            if (i == 2) {
                if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                    openCamera();
                } else {
                    permissionNotify(1, "android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE");
                }
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            permissionNotify(2, "android.permission.WRITE_EXTERNAL_STORAGE");
        } else {
            openAlbum();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ChangePasswordFragment changePasswordFragment2 = this.changePasswordFragment;
        if (changePasswordFragment2 != null) {
            changePasswordFragment2.dismiss();
        }
    }
}
