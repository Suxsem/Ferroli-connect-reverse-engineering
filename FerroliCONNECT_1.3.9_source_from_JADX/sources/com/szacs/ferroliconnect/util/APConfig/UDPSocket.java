package com.szacs.ferroliconnect.util.APConfig;

import android.content.Context;
import android.util.Log;
import com.szacs.ferroliconnect.util.APConfig.HeartbeatTimer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPSocket {
    /* access modifiers changed from: private */
    public static String BROADCAST_IP = "10.10.10.255";
    private static final int BUFFER_LENGTH = 1024;
    public static final int CLIENT_PORT = 20000;
    private static final long HEARTBEAT_MESSAGE_DURATION = 5000;
    private static final String TAG = "UDPSocket";
    private static final long TIME_OUT = 120000;
    /* access modifiers changed from: private */
    public DatagramSocket client;
    private Thread clientThread;
    private boolean isThreadRunning = false;
    /* access modifiers changed from: private */
    public long lastReceiveTime = 0;
    /* access modifiers changed from: private */
    public ApConfigListener listener;
    private Context mContext;
    private ExecutorService mThreadPool;
    private byte[] receiveByte = new byte[1024];
    private DatagramPacket receivePacket;
    /* access modifiers changed from: private */
    public String sendMsg;
    private HeartbeatTimer timer;

    public interface ApConfigListener {
        void onApConfigFailed();

        void onApConfigSuccess(String str);
    }

    public UDPSocket(Context context) {
        this.mContext = context;
        createThreadPool();
        this.lastReceiveTime = System.currentTimeMillis();
        Log.d(TAG, "创建 UDPSocket 对象");
    }

    private void createThreadPool() {
        this.mThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
    }

    public void startUDPSocket() {
        if (this.client == null) {
            ExecutorService executorService = this.mThreadPool;
            if (executorService == null) {
                Log.d(TAG, " 线程池置空重新创建线程池");
                createThreadPool();
            } else if (executorService.isShutdown()) {
                Log.d(TAG, " 线程池处于停止状态不接收任务 重新创建线程池");
                this.mThreadPool = null;
                createThreadPool();
            }
            try {
                this.client = new DatagramSocket(CLIENT_PORT);
                this.client.setReuseAddress(true);
                if (this.receivePacket == null) {
                    this.receivePacket = new DatagramPacket(this.receiveByte, 1024);
                }
                startSocketThread();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    private void startSocketThread() {
        this.clientThread = new Thread(new Runnable() {
            public void run() {
                UDPSocket.this.receiveMessage();
            }
        });
        this.isThreadRunning = true;
        this.clientThread.start();
        Log.d(TAG, "开启 UDPSocket 数据接收线程");
        startHeartbeatTimer();
    }

    /* access modifiers changed from: private */
    public void receiveMessage() {
        ApConfigListener apConfigListener;
        while (this.isThreadRunning) {
            try {
                if (this.client != null) {
                    this.client.receive(this.receivePacket);
                }
                this.lastReceiveTime = System.currentTimeMillis();
                Log.d(TAG, "receive packet success...");
                DatagramPacket datagramPacket = this.receivePacket;
                if (datagramPacket == null || datagramPacket.getLength() == 0) {
                    Log.e(TAG, "无法接收UDP数据或者接收到的UDP数据为空");
                } else {
                    String str = new String(this.receivePacket.getData(), this.receivePacket.getOffset(), this.receivePacket.getLength());
                    Log.d(TAG, str + " from " + this.receivePacket.getAddress().getHostAddress() + ":" + this.receivePacket.getPort());
                    if (this.receivePacket.getPort() == 20000 && str.contains("MAC") && (apConfigListener = this.listener) != null) {
                        apConfigListener.onApConfigSuccess(str);
                    }
                    DatagramPacket datagramPacket2 = this.receivePacket;
                    if (datagramPacket2 != null) {
                        datagramPacket2.setLength(1024);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "UDP数据包接收失败！线程停止");
                stopUDPSocket();
                e.printStackTrace();
                return;
            }
        }
    }

    public void stopUDPSocket() {
        this.isThreadRunning = false;
        this.receivePacket = null;
        stopHeartbeatTimer();
        Thread thread = this.clientThread;
        if (thread != null) {
            thread.interrupt();
        }
        ExecutorService executorService = this.mThreadPool;
        if (executorService != null) {
            executorService.shutdown();
            this.mThreadPool = null;
        }
        DatagramSocket datagramSocket = this.client;
        if (datagramSocket != null) {
            datagramSocket.close();
            this.client = null;
        }
        HeartbeatTimer heartbeatTimer = this.timer;
        if (heartbeatTimer != null) {
            heartbeatTimer.exit();
        }
    }

    public void startHeartbeatTimer() {
        if (this.timer == null) {
            this.timer = new HeartbeatTimer();
        }
        this.timer.setOnScheduleListener(new HeartbeatTimer.OnScheduleListener() {
            public void onSchedule() {
                Log.d(UDPSocket.TAG, "timer is onSchedule...");
                long currentTimeMillis = System.currentTimeMillis() - UDPSocket.this.lastReceiveTime;
                Log.d(UDPSocket.TAG, "duration:" + currentTimeMillis);
                if (currentTimeMillis > UDPSocket.TIME_OUT) {
                    Log.d(UDPSocket.TAG, " AP配网SOCKET心跳超时");
                    if (UDPSocket.this.listener != null) {
                        UDPSocket.this.listener.onApConfigFailed();
                    }
                } else if (currentTimeMillis > UDPSocket.HEARTBEAT_MESSAGE_DURATION) {
                    UDPSocket uDPSocket = UDPSocket.this;
                    uDPSocket.sendMessage(uDPSocket.sendMsg);
                }
            }
        });
        this.timer.startTimer(0, HEARTBEAT_MESSAGE_DURATION);
    }

    public void stopHeartbeatTimer() {
        HeartbeatTimer heartbeatTimer = this.timer;
        if (heartbeatTimer != null) {
            heartbeatTimer.exit();
            this.timer = null;
        }
    }

    public void sendMessage(final String str) {
        this.mThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    Log.d(UDPSocket.TAG, "BROADCAST_IP:" + UDPSocket.BROADCAST_IP);
                    UDPSocket.this.client.send(new DatagramPacket(str.getBytes(), str.length(), InetAddress.getByName(UDPSocket.BROADCAST_IP), UDPSocket.CLIENT_PORT));
                    Log.d(UDPSocket.TAG, "数据发送成功");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public String getSendMsg() {
        return this.sendMsg;
    }

    public void setSendMsg(String str) {
        this.sendMsg = str;
    }

    public void setApConfigListener(ApConfigListener apConfigListener) {
        this.listener = apConfigListener;
    }
}
