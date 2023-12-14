package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import butterknife.BindView;
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
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.TherListUpdateBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.DensityUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.viewInterface.ThermostatListView;
import com.szacs.ferroliconnect.widget.CommonAdapter;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ThermostatListActivity extends MyNavigationActivity implements ThermostatListView, PullRefreshLayout.OnRefreshListener {
    /* access modifiers changed from: private */
    public List<DeviceListResponse.ResultsBean.ChildDevicesBean> beanList;
    /* access modifiers changed from: private */
    public CommonAdapter<DeviceListResponse.ResultsBean.ChildDevicesBean> commonAdapter;
    private String devCode;
    private String device_slug;
    @BindView(2131296557)
    SwipeMenuListView listViewThermostat;
    private String productCode;
    @BindView(2131296755)
    PullRefreshLayout refreshLayout;
    private String token;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_thermostat_list;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.device_slug = getIntent().getStringExtra("device_slug");
        String str = this.TAG;
        Log.d(str, " device_slug = " + this.device_slug);
        this.token = getSharedPreferences("ferroli_user", 0).getString("token", "");
        this.listViewThermostat.setMenuCreator(new SwipeMenuCreator() {
            public void create(SwipeMenu swipeMenu) {
                SwipeMenuItem swipeMenuItem = new SwipeMenuItem(ThermostatListActivity.this.getApplicationContext());
                swipeMenuItem.setBackground((int) C1683R.color.cloudwarm_green);
                swipeMenuItem.setWidth(DensityUtil.dip2px(ThermostatListActivity.this.mContext, 70.0f));
                swipeMenuItem.setIcon((int) C1683R.C1684drawable.edit);
                swipeMenu.addMenuItem(swipeMenuItem);
                SwipeMenuItem swipeMenuItem2 = new SwipeMenuItem(ThermostatListActivity.this.getApplicationContext());
                swipeMenuItem2.setBackground((int) C1683R.color.cloudwarm_orange);
                swipeMenuItem2.setWidth(DensityUtil.dip2px(ThermostatListActivity.this.mContext, 70.0f));
                swipeMenuItem2.setIcon((int) C1683R.C1684drawable.delete);
                swipeMenu.addMenuItem(swipeMenuItem2);
            }
        });
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
            String str2 = this.TAG;
            Log.d(str2, " ther list devCode  = " + this.devCode);
            setTitle(getResources().getString(C1683R.string.nav_tab_thermostat_list_title));
        }
        if (this.beanList == null) {
            this.beanList = new ArrayList();
        }
        this.commonAdapter = new CommonAdapter<DeviceListResponse.ResultsBean.ChildDevicesBean>(this, this.beanList, C1683R.C1686layout.list_item_thermostat) {
            /* access modifiers changed from: protected */
            public void InitView(View view, DeviceListResponse.ResultsBean.ChildDevicesBean childDevicesBean) {
                ImageView imageView = (ImageView) view.findViewById(C1683R.C1685id.imageViewEnterDevice);
                ((TextView) view.findViewById(C1683R.C1685id.textViewDeviceName)).setText(childDevicesBean.getName());
                ((TextView) view.findViewById(C1683R.C1685id.textViewDeviceId)).setText(childDevicesBean.getSdid());
                imageView.setImageResource(childDevicesBean.isOnline() ? C1683R.C1684drawable.online : C1683R.C1684drawable.offline);
            }
        };
        this.listViewThermostat.setAdapter((ListAdapter) this.commonAdapter);
        this.listViewThermostat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!ButtonUtils.isFastDoubleClick(i)) {
                    if (!AppYunManager.getInstance().isConnected()) {
                        ThermostatListActivity.this.verifyToken();
                    }
                    Intent intent = new Intent();
                    intent.setClass(ThermostatListActivity.this, ThermostatActivity.class);
                    ThermostatListActivity.this.startActivity(intent);
                    UserCenter.setChildDevicesBean((DeviceListResponse.ResultsBean.ChildDevicesBean) ThermostatListActivity.this.beanList.get(i));
                }
            }
        });
        this.listViewThermostat.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int i, SwipeMenu swipeMenu, int i2) {
                if (i2 == 0) {
                    ThermostatListActivity.this.editThermostat(i);
                    return false;
                } else if (i2 != 1) {
                    return false;
                } else {
                    ThermostatListActivity.this.removeThermostat(i);
                    return false;
                }
            }
        });
        this.listViewThermostat.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            public void onSwipeStart(int i) {
                ThermostatListActivity.this.refreshLayout.setEnabled(false);
            }

            public void onSwipeEnd(int i) {
                ThermostatListActivity.this.refreshLayout.setEnabled(true);
            }
        });
        this.refreshLayout.setOnRefreshListener(this);
        getThermostats();
    }

    private void unSubscribe() {
        AppYunManager.getInstance().unsubscribeThermostatListUpdate(this.productCode, this.devCode, (IAppYunManagerActionListener) null);
    }

    private void subscribe() {
        AppYunManager.getInstance().subscribeThermostatListUpdate(this.productCode, this.devCode, new IAppYunManagerActionListener() {
            public void onFailure(IMqttToken iMqttToken, Throwable th) {
            }

            public void onSuccess(IMqttToken iMqttToken) {
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        subscribe();
        getThermostats();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void therListUpdate(TherListUpdateBean therListUpdateBean) {
        getThermostats();
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

    /* access modifiers changed from: private */
    public void editThermostat(final int i) {
        View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_edit_device_name, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(C1683R.C1685id.etDeviceName);
        final TextView textView = (TextView) inflate.findViewById(C1683R.C1685id.tv_confirm);
        final AlertDialog create = new AlertDialog.Builder(this).setView(inflate).create();
        create.show();
        editText.setText(this.beanList.get(i).getName());
        ((TextView) inflate.findViewById(C1683R.C1685id.tv_cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = editText.getText().toString();
                if (!TextUtils.isEmpty(obj)) {
                    ThermostatListActivity.this.ShowProgressDialog((String) null);
                    AppYunManager.getInstance().modifyChildDeviceName(UserCenter.getResultsBean().getDevice_code(), ((DeviceListResponse.ResultsBean.ChildDevicesBean) ThermostatListActivity.this.beanList.get(i)).getSlug(), obj, new IAppYunResponseListener<ModifyDeviceMsgResponse>() {
                        public void onSuccess(ModifyDeviceMsgResponse modifyDeviceMsgResponse) {
                            ToastUtil.showShortToast(ThermostatListActivity.this.mContext, ThermostatListActivity.this.getString(C1683R.string.device_list_change_dev_name_success));
                            ThermostatListActivity.this.getThermostats();
                            ThermostatListActivity.this.HideProgressDialog();
                        }

                        public void onFailure(int i, String str) {
                            ToastUtil.showShortToast(ThermostatListActivity.this.mContext, HttpError.getMessage(ThermostatListActivity.this, i));
                            ThermostatListActivity.this.HideProgressDialog();
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
                    textView.setTextColor(ContextCompat.getColor(ThermostatListActivity.this, C1683R.color.cloudwarm_grey));
                } else {
                    textView.setTextColor(ContextCompat.getColor(ThermostatListActivity.this, C1683R.color.cloudwarm_green));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeThermostat(final int i) {
        if (!this.beanList.isEmpty() && i < this.beanList.size()) {
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
                    ThermostatListActivity.this.ShowProgressDialog((String) null);
                    ThermostatListActivity thermostatListActivity = ThermostatListActivity.this;
                    thermostatListActivity.deleteThermostat(((DeviceListResponse.ResultsBean.ChildDevicesBean) thermostatListActivity.beanList.get(i)).getSdid());
                    qMUIDialog.dismiss();
                }
            });
            messageDialogBuilder.create().show();
        }
    }

    /* access modifiers changed from: private */
    public void deleteThermostat(String str) {
        sendJsonMessage("{\"type\":\"del\",\"data\":{\"thermostats\":[{\"online\":\"0\",\"thermostats_id\":\"40f25ffa\"}]}}".replace("40f25ffa", str).getBytes());
    }

    private void sendMessage(final byte[] bArr) {
        if (bArr != null) {
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    String str = ThermostatListActivity.this.TAG;
                    Log.d(str, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(ThermostatListActivity.this.mContext, ThermostatListActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    private void sendJsonMessage(byte[] bArr) {
        if (bArr != null) {
            AppYunManager.getInstance().sendJsonMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    if (!ThermostatListActivity.this.isFinishing()) {
                        Log.d(ThermostatListActivity.this.TAG, " sendJsonMessage Remove Thermostat Msg Send Success");
                    }
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    if (!ThermostatListActivity.this.isFinishing()) {
                        Log.d(ThermostatListActivity.this.TAG, " sendJsonMessage Remove Thermostat Msg Send Failed");
                        ToastUtil.showShortToast(ThermostatListActivity.this.mContext, ThermostatListActivity.this.getString(C1683R.string.public_failed_to_set));
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void getThermostats() {
        AppYunManager.getInstance().getDevice(this.device_slug, UserCenter.getUserInfo().getSlug(), 40, 1, new IAppYunResponseListener<DeviceListResponse.ResultsBean>() {
            public void onSuccess(DeviceListResponse.ResultsBean resultsBean) {
                ThermostatListActivity.this.beanList.clear();
                ThermostatListActivity.this.beanList.addAll(resultsBean.getChild_devices());
                ThermostatListActivity.this.commonAdapter.notifyDataSetChanged();
                ThermostatListActivity.this.refreshLayout.setRefreshing(false);
            }

            public void onFailure(int i, String str) {
                ThermostatListActivity.this.refreshLayout.setRefreshing(false);
                LogUtil.m3315d(ThermostatListActivity.this.TAG, String.format("获取设备列表失败，错误码: %d, 错误信息: %s", new Object[]{Integer.valueOf(i), str}));
            }
        });
    }

    /* access modifiers changed from: private */
    public void verifyToken() {
        AppYunManager.getInstance().verifyHttpToken(this.token, new IAppYunResponseListener<HttpTokenResponse>() {
            public void onSuccess(HttpTokenResponse httpTokenResponse) {
                ThermostatListActivity.this.doLoginByToken();
            }

            public void onFailure(int i, String str) {
                if (i != 401) {
                    ToastUtil.showShortToast(ThermostatListActivity.this.mContext, HttpError.getMessage(ThermostatListActivity.this, i));
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void doLoginByToken() {
        AppYunManager.getInstance().loginByToken(this.token, new IYunManagerLoginListener() {
            public void userLoginSuccess(String str, UserResponse userResponse) {
                UserCenter.setUserInfo(userResponse);
                ThermostatListActivity.this.setMqttClientId();
                AppYunManager.getInstance().initMqtt();
            }

            public void userLoginFailure(int i, String str) {
                String str2 = ThermostatListActivity.this.TAG;
                LogUtil.m3315d(str2, "code: " + i + "   msg: " + str);
            }
        });
    }

    public void onRefresh() {
        getThermostats();
    }
}
