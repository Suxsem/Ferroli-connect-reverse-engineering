package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AlertDialog;
import android.support.p003v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.widget.PullRefreshLayout;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.HttpTokenResponse;
import com.p107tb.appyunsdk.bean.ModifyDeviceMsgResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.event.MqttEvent;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NetworkUtils;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.util.VersionChecker;
import com.szacs.ferroliconnect.widget.CommonAdapter;
import com.szacs.ferroliconnect.widget.MyProgressDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p109de.hdodenhof.circleimageview.CircleImageView;
import p110io.reactivex.Observable;
import p110io.reactivex.ObservableEmitter;
import p110io.reactivex.ObservableOnSubscribe;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class GatewayListActivity extends MyNavigationActivity implements VersionChecker.UpdateListener, PullRefreshLayout.OnRefreshListener {
    private static final String TAG = "GatewayListActivity";
    /* access modifiers changed from: private */
    public boolean bLoadMore = false;
    private boolean canLoadMore = true;
    /* access modifiers changed from: private */
    public CommonAdapter<DeviceListResponse.ResultsBean> commonAdapter;
    private int curGateWayPage = 1;
    /* access modifiers changed from: private */
    public List<DeviceListResponse.ResultsBean> gateways;
    private SwipeMenuListView listViewDevice;
    private Handler mRefreshHandler;
    /* access modifiers changed from: private */
    public MyProgressDialog progressDialog;
    /* access modifiers changed from: private */
    public PullRefreshLayout refreshLayout;
    private String token;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_gateway_list;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initWidget();
        initWidgetFunction();
        initGatewayList();
        checkNewVersion();
        initData();
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.myToolbar.setNavigationIcon((int) C1683R.C1684drawable.add);
        this.myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() != C1683R.C1685id.muAddGateway) {
                    return true;
                }
                if (GatewayListActivity.this.drawer.isDrawerOpen(5)) {
                    GatewayListActivity.this.drawer.closeDrawer(5);
                    return true;
                }
                GatewayListActivity.this.drawer.openDrawer(5);
                return true;
            }
        });
        setTitle("");
    }

    private void initWidget() {
        this.mRefreshHandler = new Handler();
        this.gateways = new ArrayList();
        this.token = getSharedPreferences("ferroli_user", 0).getString("token", "");
        this.listViewDevice = (SwipeMenuListView) findViewById(C1683R.C1685id.listViewDevice);
        this.refreshLayout = (PullRefreshLayout) findViewById(C1683R.C1685id.swipeRefreshLayout);
        this.listViewDevice.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(GatewayListActivity.this.getApplicationContext());
                swipeMenuItem.setBackground((int) C1683R.color.cloudwarm_green);
                swipeMenuItem.setWidth(GatewayListActivity.this.dp2px(70));
                swipeMenuItem.setIcon((int) C1683R.C1684drawable.edit);
                swipeMenu.addMenuItem(swipeMenuItem);
                SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(GatewayListActivity.this.getApplicationContext());
                swipeMenuItem2.setBackground((int) C1683R.color.cloudwarm_orange);
                swipeMenuItem2.setWidth(GatewayListActivity.this.dp2px(70));
                swipeMenuItem2.setIcon((int) C1683R.C1684drawable.delete);
                swipeMenu.addMenuItem(swipeMenuItem2);
            }
        });
        this.progressDialog = new MyProgressDialog(this);
        this.progressDialog.setMessage(getString(C1683R.string.public_loading));
        setTitle(getString(C1683R.string.nav_tab_wifiboxs_title));
    }

    private void initWidgetFunction() {
        this.listViewDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!ButtonUtils.isFastDoubleClick(i)) {
                    if (!NetworkUtils.isNetworkConnected(GatewayListActivity.this.mContext)) {
                        ToastUtil.showShortToast(GatewayListActivity.this.mContext, GatewayListActivity.this.getString(C1683R.string.public_network_error));
                        return;
                    }
                    if (!AppYunManager.getInstance().isConnected()) {
                        GatewayListActivity.this.verifyToken();
                    }
                    if (!((DeviceListResponse.ResultsBean) GatewayListActivity.this.gateways.get(i)).isOnline()) {
                        ToastUtil.showShortToast(GatewayListActivity.this.mContext, GatewayListActivity.this.getString(C1683R.string.device_list_device_disconnected));
                    }
                    DeviceListResponse.ResultsBean resultsBean = (DeviceListResponse.ResultsBean) GatewayListActivity.this.gateways.get(i);
                    UserCenter.setResultsBean(resultsBean);
                    Log.d(GatewayListActivity.TAG, " WIFI BOX LIST CLICK DEVICE ID = " + resultsBean.getDevice_code());
                    int unused = GatewayListActivity.this.thermostatOnlineCount(resultsBean);
                    boolean isIs_active = resultsBean.isIs_active();
                    Intent intent = new Intent();
                    if (!isIs_active || resultsBean.getChild_devices().size() != 0) {
                        intent.setClass(GatewayListActivity.this, GatewayActivity.class);
                    } else {
                        intent.putExtra("AllowControl", GatewayListActivity.this.thermostatOnlineCount(resultsBean) == 0);
                        if (resultsBean.getVersion() != null) {
                            intent.putExtra("McuSoftware", resultsBean.getVersion().getMcu_software());
                        }
                        int i2 = Integer.MIN_VALUE;
                        if (resultsBean.getExtra_info() == null || resultsBean.getExtra_info().getBoiler() == null || resultsBean.getExtra_info().getBoiler().size() <= 0 || resultsBean.getExtra_info().getBoiler().get(0) == null || resultsBean.getExtra_info().getBoiler().get(0).getType() == null || !resultsBean.getExtra_info().getBoiler().get(0).getType().equals(Constant.BOILER_TYPE_ON_OFF)) {
                            if (resultsBean.isOnline() && resultsBean.getWifiSignal() != null) {
                                i2 = resultsBean.getWifiSignal().intValue();
                            }
                            intent.putExtra("wifiSingnal", i2);
                            intent.setClass(GatewayListActivity.this, BoilerUnControlActivity.class);
                        } else {
                            if (resultsBean.isOnline() && resultsBean.getWifiSignal() != null) {
                                i2 = resultsBean.getWifiSignal().intValue();
                            }
                            intent.putExtra("wifiSingnal", i2);
                            intent.setClass(GatewayListActivity.this, SwitchBoilerActivity.class);
                        }
                    }
                    GatewayListActivity.this.startActivity(intent);
                }
            }
        });
        this.listViewDevice.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i2) {
                if (i2 == 0) {
                    GatewayListActivity.this.editGateway(i);
                    return false;
                } else if (i2 != 1) {
                    return false;
                } else {
                    GatewayListActivity.this.removeGateway(i);
                    return false;
                }
            }
        });
        this.listViewDevice.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            public void onSwipeStart(int i) {
                GatewayListActivity.this.refreshLayout.setEnabled(false);
            }

            public void onSwipeEnd(int i) {
                GatewayListActivity.this.refreshLayout.setEnabled(true);
            }
        });
        this.refreshLayout.setOnRefreshListener(this);
    }

    private void initGatewayList() {
        this.listViewDevice.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            }

            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == 0) {
                    absListView.getLastVisiblePosition();
                    absListView.getCount();
                }
            }
        });
        this.commonAdapter = new CommonAdapter<DeviceListResponse.ResultsBean>(this, this.gateways, C1683R.C1686layout.listivew_item) {
            /* access modifiers changed from: protected */
            public void InitView(View view, DeviceListResponse.ResultsBean resultsBean) {
                TextView textView = (TextView) view.findViewById(C1683R.C1685id.textViewDeviceName);
                TextView textView2 = (TextView) view.findViewById(C1683R.C1685id.textViewID);
                ImageView imageView = (ImageView) view.findViewById(C1683R.C1685id.imageViewEnterDevice);
                ImageView imageView2 = (ImageView) view.findViewById(C1683R.C1685id.defaultImg);
                ImageView imageView3 = (ImageView) view.findViewById(C1683R.C1685id.wifi_level);
                CircleImageView circleImageView = (CircleImageView) view.findViewById(C1683R.C1685id.circleImg);
                imageView.setImageResource(resultsBean.isOnline() ? C1683R.C1684drawable.online : C1683R.C1684drawable.offline);
                textView.setText(resultsBean.getName());
                textView2.setText(resultsBean.getMac_address());
                File latestFile = FileUtil.getLatestFile(FileUtil.CLOUDWARM_ROOT_PATH + resultsBean.getDevice_code(), "jpg");
                if (latestFile != null) {
                    latestFile.exists();
                }
                imageView2.setVisibility(0);
                circleImageView.setVisibility(8);
                Integer wifiSignal = resultsBean.getWifiSignal();
                if (wifiSignal == null) {
                    imageView3.setVisibility(8);
                } else {
                    imageView3.setVisibility(0);
                    if (wifiSignal.intValue() >= -50) {
                        imageView3.setBackgroundResource(C1683R.C1684drawable.signal_05);
                    } else if (wifiSignal.intValue() >= -60 && wifiSignal.intValue() < -50) {
                        imageView3.setBackgroundResource(C1683R.C1684drawable.signal_04);
                    } else if (wifiSignal.intValue() >= -70 && wifiSignal.intValue() < -60) {
                        imageView3.setBackgroundResource(C1683R.C1684drawable.signal_03);
                    } else if (wifiSignal.intValue() < -80 || wifiSignal.intValue() >= -70) {
                        imageView3.setBackgroundResource(C1683R.C1684drawable.signal_01);
                    } else {
                        imageView3.setBackgroundResource(C1683R.C1684drawable.signal_02);
                    }
                }
                if (resultsBean.isOnline()) {
                    imageView3.setVisibility(0);
                } else {
                    imageView3.setVisibility(8);
                }
            }
        };
        this.listViewDevice.setAdapter((ListAdapter) this.commonAdapter);
    }

    /* access modifiers changed from: private */
    public int dp2px(int i) {
        return (int) TypedValue.applyDimension(1, (float) i, getResources().getDisplayMetrics());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_gateway_list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent intent = new Intent();
        intent.putExtra("FindDevice", true);
        intent.setClass(this, ConfigWiFiActivity.class);
        startActivity(intent);
        return true;
    }

    /* access modifiers changed from: private */
    public void editGateway(final int i) {
        View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_edit_device_name, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(C1683R.C1685id.etDeviceName);
        final TextView textView = (TextView) inflate.findViewById(C1683R.C1685id.tv_confirm);
        final AlertDialog create = new AlertDialog.Builder(this).setView(inflate).create();
        create.show();
        editText.setText(this.gateways.get(i).getName());
        ((TextView) inflate.findViewById(C1683R.C1685id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (!TextUtils.isEmpty(obj)) {
                    GatewayListActivity.this.progressDialog.show();
                    AppYunManager.getInstance().modifyDeviceName(UserCenter.getUserInfo().getSlug(), ((DeviceListResponse.ResultsBean) GatewayListActivity.this.gateways.get(i)).getSlug(), obj, MainApplication.appSlug, new IAppYunResponseListener<ModifyDeviceMsgResponse>() {
                        public void onSuccess(ModifyDeviceMsgResponse modifyDeviceMsgResponse) {
                            ToastUtil.showShortToast(GatewayListActivity.this.mContext, GatewayListActivity.this.getString(C1683R.string.public_successfully));
                            GatewayListActivity.this.initData();
                            GatewayListActivity.this.progressDialog.hide();
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(GatewayListActivity.this.mContext, HttpError.getMessage(GatewayListActivity.this.mContext, i));
                            GatewayListActivity.this.progressDialog.hide();
                        }
                    });
                    create.dismiss();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    textView.setTextColor(ContextCompat.getColor(GatewayListActivity.this, C1683R.color.cloudwarm_grey));
                } else {
                    textView.setTextColor(ContextCompat.getColor(GatewayListActivity.this, C1683R.color.cloudwarm_green));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeGateway(final int i) {
        QMUIDialog.MessageDialogBuilder messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(this);
        messageDialogBuilder.setTitle(getString(C1683R.string.dialog_delete_device));
        messageDialogBuilder.setMessage((int) C1683R.string.dialog_confirm_remove_device);
        messageDialogBuilder.setCanceledOnTouchOutside(true);
        messageDialogBuilder.setCancelable(true);
        messageDialogBuilder.addAction((int) C1683R.string.public_cancel, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                qMUIDialog.dismiss();
            }
        });
        messageDialogBuilder.addAction((int) C1683R.string.public_confirm, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                GatewayListActivity.this.progressDialog.show();
                AppYunManager.getInstance().unBindDevice(UserCenter.getUserInfo().getSlug(), ((DeviceListResponse.ResultsBean) GatewayListActivity.this.gateways.get(i)).getSlug(), MainApplication.appSlug, new IAppYunResponseListener<ModifyDeviceMsgResponse>() {
                    public void onSuccess(ModifyDeviceMsgResponse modifyDeviceMsgResponse) {
                        ToastUtil.showShortToast(GatewayListActivity.this.mContext, GatewayListActivity.this.getString(C1683R.string.public_successfully));
                        GatewayListActivity.this.initData();
                        GatewayListActivity.this.progressDialog.hide();
                    }

                    public void onFailure(int i, String str) {
                        ToastUtil.showShortToast(GatewayListActivity.this.mContext, HttpError.getMessage(GatewayListActivity.this.mContext, i));
                        GatewayListActivity.this.progressDialog.hide();
                    }
                });
                qMUIDialog.dismiss();
            }
        });
        messageDialogBuilder.create().show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MqttEvent mqttEvent) {
        if (mqttEvent.getState() == 2 && NetworkUtils.isNetworkConnected(this.mContext)) {
            verifyToken();
        }
    }

    /* access modifiers changed from: private */
    public void verifyToken() {
        AppYunManager.getInstance().verifyHttpToken(this.token, new IAppYunResponseListener<HttpTokenResponse>() {
            public void onSuccess(HttpTokenResponse httpTokenResponse) {
                GatewayListActivity.this.doLoginByToken();
            }

            public void onFailure(int i, String str) {
                if (i != 401) {
                    ToastUtil.showShortToast(GatewayListActivity.this.mContext, HttpError.getMessage(GatewayListActivity.this.mContext, i));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void doLoginByToken() {
        AppYunManager.getInstance().loginByToken(this.token, new IYunManagerLoginListener() {
            public void userLoginSuccess(String str, UserResponse userResponse) {
                UserCenter.setUserInfo(userResponse);
                GatewayListActivity.this.setMqttClientId();
                AppYunManager.getInstance().initMqtt();
            }

            public void userLoginFailure(int i, String str) {
                LogUtil.m3315d(GatewayListActivity.TAG, "code: " + i + "   msg: " + str);
            }
        });
    }

    private void getUserInformation() {
        HttpUtils.getRetrofit().getUserInfo(this.authorzation).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserResponse>() {
            public void accept(UserResponse userResponse) throws Exception {
                GatewayListActivity.this.tvNavUsername.setText(userResponse.getName());
                GatewayListActivity.this.tvNavEmail.setText(userResponse.getMobile());
                SharedPreferences.Editor edit = GatewayListActivity.this.getSharedPreferences("ferroli_user", 0).edit();
                edit.putString("useralias", userResponse.getName());
                edit.apply();
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                LogUtil.m3315d(GatewayListActivity.TAG, "onGet user info fail");
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    public void initData() {
        AppYunManager.getInstance().getDeviceList(UserCenter.getUserInfo().getSlug(), 40, this.curGateWayPage, new IAppYunResponseListener<DeviceListResponse>() {
            public void onSuccess(DeviceListResponse deviceListResponse) {
                if (deviceListResponse.getResults() != null) {
                    GatewayListActivity.this.resetGatewaysName(deviceListResponse);
                    GatewayListActivity.this.resetLoadAndRrefresh();
                }
            }

            public void onFailure(int i, String str) {
                GatewayListActivity.this.refreshLayout.setRefreshing(false);
                GatewayListActivity.this.resetLoadAndRrefresh();
                LogUtil.m3315d(GatewayListActivity.TAG, String.format("获取设备列表失败，错误码: %d, 错误信息: %s", new Object[]{Integer.valueOf(i), str}));
            }
        });
    }

    /* access modifiers changed from: private */
    public void resetLoadAndRrefresh() {
        this.refreshLayout.setEnabled(true);
        this.canLoadMore = true;
    }

    /* access modifiers changed from: private */
    public void resetGatewaysName(final DeviceListResponse deviceListResponse) {
        Observable.create(new ObservableOnSubscribe<DeviceListResponse>() {
            public void subscribe(ObservableEmitter<DeviceListResponse> observableEmitter) throws Exception {
                observableEmitter.onNext(GatewayListActivity.this.renameBoiler(deviceListResponse));
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DeviceListResponse>() {
            public void accept(DeviceListResponse deviceListResponse) throws Exception {
                if (GatewayListActivity.this.bLoadMore) {
                    GatewayListActivity.this.gateways.addAll(deviceListResponse.getResults());
                    boolean unused = GatewayListActivity.this.bLoadMore = false;
                } else {
                    GatewayListActivity.this.gateways.clear();
                    GatewayListActivity.this.gateways.addAll(deviceListResponse.getResults());
                    GatewayListActivity.this.refreshLayout.setRefreshing(false);
                }
                GatewayListActivity.this.commonAdapter.notifyDataSetChanged();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initData();
    }

    /* access modifiers changed from: private */
    public int thermostatOnlineCount(DeviceListResponse.ResultsBean resultsBean) {
        int i = 0;
        for (DeviceListResponse.ResultsBean.ChildDevicesBean isOnline : resultsBean.getChild_devices()) {
            if (isOnline.isOnline()) {
                i++;
            }
        }
        return i;
    }

    public void OnFindViewVersion(boolean z) {
        if (!isFinishing() && z) {
            showUpdateDialog();
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    public void onRefresh() {
        this.curGateWayPage = 1;
        this.bLoadMore = false;
        this.canLoadMore = false;
        initData();
    }

    /* access modifiers changed from: private */
    public DeviceListResponse renameBoiler(DeviceListResponse deviceListResponse) {
        int i = 1;
        for (DeviceListResponse.ResultsBean next : deviceListResponse.getResults()) {
            if (next.getName().equals("")) {
                next.setName(String.format("%s%d", new Object[]{this.mContext.getResources().getString(C1683R.string.public_boiler_normal_title), Integer.valueOf(i)}));
                i++;
            }
        }
        return deviceListResponse;
    }
}
