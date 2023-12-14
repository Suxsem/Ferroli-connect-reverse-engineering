package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.util.Arrays;
import java.util.List;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GatewayActivity extends MyNavigationActivity {
    private String devCode;
    @BindView(2131296561)
    LinearLayout llBoiler;
    @BindView(2131296576)
    LinearLayout llRoom;
    private String productCode;
    @BindView(2131296797)
    TextView tvBoiler;
    @BindView(2131296854)
    TextView tvRoom;

    /* access modifiers changed from: private */
    public void initData() {
    }

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_gateway;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MsgBean msgBean) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initWidget();
        initWidgetFunction();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == C1683R.C1685id.muGatewayInfo) {
            Intent intent = new Intent();
            intent.setClass(this, GatewayInfoActivity.class);
            startActivity(intent);
        }
        return super.onNavigationItemSelected(menuItem);
    }

    private void initWidget() {
        EventBus.getDefault().register(this);
        if (UserCenter.getResultsBean() != null) {
            DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
            setTitle(resultsBean.getName());
            this.productCode = resultsBean.getProduct_code();
            this.devCode = resultsBean.getDevice_code();
            if (!resultsBean.isOnline()) {
                this.tvRoom.setText(getResources().getString(C1683R.string.choose_boiler_room_enter_room));
                this.tvBoiler.setText(getResources().getString(C1683R.string.choose_boiler_room_master_controller_disconnected));
                return;
            }
            if (resultsBean.getChild_devices().size() == 0 || (resultsBean.getChild_devices().size() == 1 && !resultsBean.getChild_devices().get(0).isOnline())) {
                this.tvRoom.setText(getResources().getString(C1683R.string.choose_boiler_room_enter_room));
            }
            if (!resultsBean.isIs_active()) {
                this.tvBoiler.setText(getResources().getString(C1683R.string.choose_boiler_room_boiler_disconnected));
            }
        }
    }

    private void initWidgetFunction() {
        this.llRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
                if (resultsBean != null) {
                    int size = resultsBean.getChild_devices().size();
                    if (size == 1) {
                        Intent intent = new Intent();
                        intent.setClass(GatewayActivity.this, ThermostatListActivity.class);
                        intent.putExtra("device_slug", resultsBean.getSlug());
                        GatewayActivity.this.startActivity(intent);
                    } else if (size > 1) {
                        Intent intent2 = new Intent();
                        intent2.setClass(GatewayActivity.this, ThermostatListActivity.class);
                        intent2.putExtra("device_slug", resultsBean.getSlug());
                        GatewayActivity.this.startActivity(intent2);
                    } else {
                        GatewayActivity gatewayActivity = GatewayActivity.this;
                        Toast.makeText(gatewayActivity, gatewayActivity.getResources().getString(C1683R.string.choose_boiler_room_thermostat_disconnected), 0).show();
                    }
                }
            }
        });
        this.llBoiler.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DeviceListResponse.ResultsBean resultsBean = UserCenter.getResultsBean();
                if (resultsBean != null) {
                    if (resultsBean.isIs_active()) {
                        Intent intent = new Intent();
                        intent.putExtra("AllowControl", GatewayActivity.this.thermostatOnlineCount(UserCenter.getResultsBean().getChild_devices()) == 0);
                        if (resultsBean.getVersion() != null) {
                            intent.putExtra("McuSoftware", resultsBean.getVersion().getMcu_software());
                        }
                        int i = Integer.MIN_VALUE;
                        if (resultsBean.getExtra_info() == null || resultsBean.getExtra_info().getBoiler() == null || resultsBean.getExtra_info().getBoiler().size() <= 0 || !resultsBean.getExtra_info().getBoiler().get(0).getType().equals(Constant.BOILER_TYPE_ON_OFF)) {
                            if (resultsBean.isOnline() && resultsBean.getWifiSignal() != null) {
                                i = resultsBean.getWifiSignal().intValue();
                            }
                            intent.putExtra("wifiSingnal", i);
                            intent.setClass(GatewayActivity.this, BoilerUnControlActivity.class);
                        } else {
                            if (resultsBean.isOnline() && resultsBean.getWifiSignal() != null) {
                                i = resultsBean.getWifiSignal().intValue();
                            }
                            intent.putExtra("wifiSingnal", i);
                            intent.setClass(GatewayActivity.this, SwitchBoilerActivity.class);
                        }
                        GatewayActivity.this.startActivity(intent);
                        return;
                    }
                    GatewayActivity gatewayActivity = GatewayActivity.this;
                    Toast.makeText(gatewayActivity, gatewayActivity.getResources().getString(C1683R.string.choose_boiler_room_boiler_disconnected), 0).show();
                }
            }
        });
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

    private void Subscribe() {
        AppYunManager.getInstance().subscribeDeviceMsg(this.productCode, this.devCode, new IAppYunManagerActionListener() {
            public void onFailure(IMqttToken iMqttToken, Throwable th) {
            }

            public void onSuccess(IMqttToken iMqttToken) {
            }
        });
        AppYunManager.getInstance().subscribeDeviceStatus(this.productCode, this.devCode, new IAppYunManagerActionListener() {
            public void onFailure(IMqttToken iMqttToken, Throwable th) {
            }

            public void onSuccess(IMqttToken iMqttToken) {
                GatewayActivity.this.initData();
            }
        });
    }

    private void sendMessage(final byte[] bArr) {
        if (bArr != null) {
            String str = this.TAG;
            LogUtil.m3315d(str, "send bytes " + Arrays.toString(bArr));
            BinaryUtils.printBytes(this.TAG, bArr);
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    String str = GatewayActivity.this.TAG;
                    Log.d(str, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    ToastUtil.showShortToast(GatewayActivity.this.mContext, GatewayActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public int thermostatOnlineCount(List<DeviceListResponse.ResultsBean.ChildDevicesBean> list) {
        int i = 0;
        for (DeviceListResponse.ResultsBean.ChildDevicesBean isOnline : list) {
            if (isOnline.isOnline()) {
                i++;
            }
        }
        return i;
    }
}
