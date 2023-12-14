package p110io.fogcloud.sdk.easylink.api;

import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import p110io.fogcloud.sdk.easylink.helper.Helper;
import p110io.fogcloud.sdk.easylink.plus.EasyLink_v2;
import p110io.fogcloud.sdk.easylink.plus.EasyLink_v3;

/* renamed from: io.fogcloud.sdk.easylink.api.EasyLink_plus */
public class EasyLink_plus {
    /* access modifiers changed from: private */

    /* renamed from: e2 */
    public static EasyLink_v2 f3621e2;
    /* access modifiers changed from: private */

    /* renamed from: e3 */
    public static EasyLink_v3 f3622e3;

    /* renamed from: me */
    private static EasyLink_plus f3623me;
    boolean sending = true;
    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private EasyLink_plus(Context context) {
        try {
            f3621e2 = EasyLink_v2.getInstence();
            f3622e3 = EasyLink_v3.getInstence();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EasyLink_plus getInstence(Context context) {
        if (f3623me == null) {
            f3623me = new EasyLink_plus(context);
        }
        return f3623me;
    }

    public void setSmallMtu(boolean z) {
        f3622e3.SetSmallMTU(z);
    }

    public void transmitSettings(String str, String str2, int i, int i2, String str3, String str4) {
        try {
            final byte[] bytes = str.getBytes("UTF-8");
            final byte[] bytes2 = str2.getBytes("UTF-8");
            byte[] bArr = null;
            if (i != 0) {
                bArr = new byte[(str3.getBytes().length + 5)];
                String format = String.format("%08x", new Object[]{Integer.valueOf(i)});
                if ("".equals(str3)) {
                    if (str3 == null) {
                        bArr[0] = 35;
                        System.arraycopy(Helper.hexStringToBytes(format), 0, bArr, 1, 4);
                    }
                }
                System.arraycopy(str3.getBytes(), 0, bArr, 0, str3.getBytes().length);
                bArr[str3.getBytes().length] = 35;
                System.arraycopy(Helper.hexStringToBytes(format), 0, bArr, str3.getBytes().length + 1, 4);
            }
            final byte[] bArr2 = bArr;
            this.singleThreadExecutor = Executors.newSingleThreadExecutor();
            this.sending = true;
            final int i3 = i2;
            final String str5 = str4;
            this.singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    while (EasyLink_plus.this.sending) {
                        try {
                            EasyLink_plus.f3621e2.transmitSettings(bytes, bytes2, bArr2, i3);
                            EasyLink_plus.f3622e3.transmitSettings(bytes, bytes2, bArr2, str5, i3);
                            try {
                                Thread.sleep(10000);
                                EasyLink_plus.f3621e2.stopTransmitting();
                                EasyLink_plus.f3622e3.stopTransmitting();
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void transmitSettings(byte[] bArr, byte[] bArr2, byte[] bArr3, int i, int i2, String str) {
        if (i != 0) {
            byte[] bArr4 = new byte[(bArr.length + 5)];
            String format = String.format("%08x", new Object[]{Integer.valueOf(i)});
            if (!"".equals(bArr) || bArr != null) {
                System.arraycopy(bArr, 0, bArr, 0, bArr.length);
                bArr4[bArr.length] = 35;
                System.arraycopy(Helper.hexStringToBytes(format), 0, bArr4, bArr.length + 1, 4);
            } else {
                bArr[0] = 35;
                System.arraycopy(Helper.hexStringToBytes(format), 0, bArr4, 1, 4);
            }
        }
        this.singleThreadExecutor = Executors.newSingleThreadExecutor();
        this.sending = true;
        final byte[] bArr5 = bArr2;
        final byte[] bArr6 = bArr3;
        final byte[] bArr7 = bArr;
        final int i3 = i2;
        final String str2 = str;
        this.singleThreadExecutor.execute(new Runnable() {
            public void run() {
                while (EasyLink_plus.this.sending) {
                    try {
                        EasyLink_plus.f3621e2.transmitSettings(bArr5, bArr6, bArr7, i3);
                        EasyLink_plus.f3622e3.transmitSettings(bArr5, bArr6, bArr7, str2, i3);
                        try {
                            Thread.sleep(10000);
                            EasyLink_plus.f3621e2.stopTransmitting();
                            EasyLink_plus.f3622e3.stopTransmitting();
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    }

    public void stopTransmitting() {
        this.sending = false;
        this.singleThreadExecutor.shutdown();
        f3621e2.stopTransmitting();
        f3622e3.stopTransmitting();
    }
}
