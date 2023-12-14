package p110io.fogcloud.sdk.easylink.api;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.taobao.accs.utl.UtilityImpl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkParams;
import p110io.fogcloud.sdk.easylink.helper.P2PData;
import p110io.fogcloud.sdk.easylink.helper.UdpSend;
import p110io.fogcloud.sdk.easylink.helper.aws_broadcast;

/* renamed from: io.fogcloud.sdk.easylink.api.EasylinkP2P */
public class EasylinkP2P {
    /* access modifiers changed from: private */
    public static boolean close_AWS_Listen_flag = false;
    /* access modifiers changed from: private */
    public static boolean close_flag = false;
    /* access modifiers changed from: private */
    public static boolean threadtag = true;
    Handler LHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                EasylinkP2P.this.listenModulepack((String) message.obj);
            } else if (message.what == 3) {
                EasylinkP2P.this.sendTargetPack();
            }
        }
    };
    private String TAG = "---P2P---";
    /* access modifiers changed from: private */
    public aws_broadcast aws;
    /* access modifiers changed from: private */
    public CountDownTimer cdt;
    /* access modifiers changed from: private */
    public String chanelname;
    /* access modifiers changed from: private */
    public WifiP2pManager.Channel channel;
    private final int d_port = 65123;
    private boolean easylink_timeout = false;
    /* access modifiers changed from: private */
    public EasyLinkCallBack elcb;
    private Context mContext;
    private WifiInfo mWifiInfo;
    private WifiManager mWifiManager;
    /* access modifiers changed from: private */
    public WifiP2pManager manager;
    private WifiP2pDnsSdServiceRequest serviceRequest;

    public EasylinkP2P(Context context) {
        this.mContext = context;
        this.manager = (WifiP2pManager) this.mContext.getSystemService("wifip2p");
        WifiP2pManager wifiP2pManager = this.manager;
        Context context2 = this.mContext;
        this.channel = wifiP2pManager.initialize(context2, context2.getMainLooper(), new WifiP2pManager.ChannelListener() {
            public void onChannelDisconnected() {
            }
        });
        this.aws = new aws_broadcast();
    }

    /* access modifiers changed from: private */
    public void sendTargetPack() {
        new Thread() {
            final int d_port = 50000;

            public void run() {
                UdpSend udpSend = new UdpSend();
                while (EasylinkP2P.threadtag) {
                    try {
                        udpSend.sendConfigData(1248, 50000);
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void listenModulepack(final String str) {
        new Thread() {
            public void run() {
                byte[] bArr = new byte[1024];
                byte[] bArr2 = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
                bArr2[0] = -18;
                try {
                    DatagramSocket datagramSocket = new DatagramSocket((SocketAddress) null);
                    datagramSocket.setReuseAddress(true);
                    datagramSocket.bind(new InetSocketAddress(65123));
                    boolean z = true;
                    while (!EasylinkP2P.close_AWS_Listen_flag) {
                        try {
                            datagramSocket.setSoTimeout(3000);
                            datagramSocket.receive(datagramPacket);
                            if (datagramPacket.getLength() != 0) {
                                if (!EasylinkP2P.close_flag) {
                                    boolean unused = EasylinkP2P.close_flag = true;
                                    EasylinkP2P.this.clearService();
                                    EasylinkP2P.this.aws.set_stop_broad_flag(true);
                                }
                                byte[] data = datagramPacket.getData();
                                if (!Arrays.equals(data, bArr) && !Arrays.equals(data, bArr2)) {
                                    if (z) {
                                        String str = new String(datagramPacket.getData());
                                        Log.w("yyy", str);
                                        EasylinkP2P.this.elcb.onSuccess(0, str.substring(0, datagramPacket.getLength() - 1) + ",\"IP\":\"" + datagramPacket.getAddress().toString().replaceAll("/", "") + "\"}");
                                        z = false;
                                    }
                                    String str2 = "{\"STATUS\":\"OK\"," + "\"ExtraData\":\"" + str + "\"}";
                                    datagramSocket.send(new DatagramPacket(str2.getBytes(), str2.getBytes().length, datagramPacket.getAddress(), datagramPacket.getPort()));
                                    EasylinkP2P.this.cdt.cancel();
                                }
                            }
                            Thread.sleep(20);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    datagramSocket.close();
                } catch (SocketException e2) {
                    e2.printStackTrace();
                }
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void discoverService() {
        this.serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        this.manager.addServiceRequest(this.channel, this.serviceRequest, (WifiP2pManager.ActionListener) null);
        this.manager.discoverServices(this.channel, (WifiP2pManager.ActionListener) null);
    }

    /* access modifiers changed from: private */
    public void clearService() {
        this.manager.clearLocalServices(this.channel, (WifiP2pManager.ActionListener) null);
        this.manager.clearServiceRequests(this.channel, new WifiP2pManager.ActionListener() {
            public void onFailure(int i) {
            }

            public void onSuccess() {
            }
        });
    }

    private void clearService(final EasyLinkCallBack easyLinkCallBack) {
        clearName();
        this.manager.clearLocalServices(this.channel, (WifiP2pManager.ActionListener) null);
        this.manager.clearServiceRequests(this.channel, new WifiP2pManager.ActionListener() {
            public void onSuccess() {
                easyLinkCallBack.onSuccess(0, "stop easylink success");
            }

            public void onFailure(int i) {
                easyLinkCallBack.onFailure(1, "stop easylink failed");
            }
        });
    }

    private void clearService_internal(EasyLinkCallBack easyLinkCallBack) {
        clearName();
        this.manager.clearLocalServices(this.channel, (WifiP2pManager.ActionListener) null);
        this.manager.clearServiceRequests(this.channel, (WifiP2pManager.ActionListener) null);
        easyLinkCallBack.onFailure(1, "easylink timeout");
    }

    private void clearName() {
        new Thread() {
            public void run() {
                try {
                    String unused = EasylinkP2P.this.chanelname = "Android_Easy";
                    Method method = EasylinkP2P.this.manager.getClass().getMethod("setDeviceName", new Class[]{WifiP2pManager.Channel.class, String.class, WifiP2pManager.ActionListener.class});
                    method.setAccessible(true);
                    boolean unused2 = EasylinkP2P.threadtag = false;
                    boolean unused3 = EasylinkP2P.close_flag = false;
                    method.invoke(EasylinkP2P.this.manager, new Object[]{EasylinkP2P.this.channel, EasylinkP2P.this.chanelname, new WifiP2pManager.ActionListener() {
                        public void onFailure(int i) {
                        }

                        public void onSuccess() {
                        }
                    }});
                    EasylinkP2P.this.discoverService();
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String getData(String str, String str2) {
        try {
            return new P2PData().bgProtocol(this.aws, str, str2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSSID() {
        Context context = this.mContext;
        if (context == null) {
            return null;
        }
        this.mWifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        this.mWifiInfo = this.mWifiManager.getConnectionInfo();
        String ssid = this.mWifiInfo.getSSID();
        return ssid.substring(1, ssid.length() - 1);
    }

    public void startEasyLink(EasyLinkParams easyLinkParams, EasyLinkCallBack easyLinkCallBack) {
        this.elcb = easyLinkCallBack;
        threadtag = false;
        close_AWS_Listen_flag = false;
        send2handler(1, easyLinkParams.extraData);
        this.aws.set_stop_broad_flag(false);
        this.chanelname = getData(easyLinkParams.ssid, easyLinkParams.password);
        this.cdt = new CountDownTimer((long) (easyLinkParams.runSecond * 1000), 1000) {
            public void onTick(long j) {
            }

            public void onFinish() {
                EasylinkP2P easylinkP2P = EasylinkP2P.this;
                easylinkP2P.stopEasyLink_internel(easylinkP2P.elcb);
            }
        };
        this.cdt.start();
        new Thread() {
            public void run() {
                try {
                    Method method = EasylinkP2P.this.manager.getClass().getMethod("setDeviceName", new Class[]{WifiP2pManager.Channel.class, String.class, WifiP2pManager.ActionListener.class});
                    method.setAccessible(true);
                    method.invoke(EasylinkP2P.this.manager, new Object[]{EasylinkP2P.this.channel, EasylinkP2P.this.chanelname, new WifiP2pManager.ActionListener() {
                        public void onFailure(int i) {
                        }

                        public void onSuccess() {
                        }
                    }});
                    EasylinkP2P.this.discoverService();
                } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void stopEasyLink(EasyLinkCallBack easyLinkCallBack) {
        this.cdt.cancel();
        close_AWS_Listen_flag = true;
        send2handler(2, "");
        aws_broadcast aws_broadcast = this.aws;
        if (aws_broadcast != null) {
            aws_broadcast.set_stop_broad_flag(true);
        }
        clearService(easyLinkCallBack);
    }

    /* access modifiers changed from: private */
    public void stopEasyLink_internel(EasyLinkCallBack easyLinkCallBack) {
        close_AWS_Listen_flag = true;
        aws_broadcast aws_broadcast = this.aws;
        if (aws_broadcast != null) {
            aws_broadcast.set_stop_broad_flag(true);
        }
        clearService_internal(easyLinkCallBack);
    }

    private void send2handler(int i, String str) {
        Message message = new Message();
        message.what = i;
        message.obj = str;
        this.LHandler.sendMessage(message);
    }
}
