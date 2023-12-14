package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: Taobao */
public interface ParcelableNetworkListener extends IInterface {
    byte getListenerState() throws RemoteException;

    void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException;

    void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException;

    void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException;

    boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException;

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements ParcelableNetworkListener {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.ParcelableNetworkListener";
        static final int TRANSACTION_getListenerState = 5;
        static final int TRANSACTION_onDataReceived = 1;
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onInputStreamGet = 4;
        static final int TRANSACTION_onResponseCode = 3;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ParcelableNetworkListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof ParcelableNetworkListener)) {
                return new Proxy(iBinder);
            }
            return (ParcelableNetworkListener) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: anetwork.channel.aidl.DefaultProgressEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: anetwork.channel.aidl.DefaultFinishEvent} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: anetwork.channel.aidl.ParcelableHeader} */
        /* JADX WARNING: type inference failed for: r0v0 */
        /* JADX WARNING: type inference failed for: r0v10 */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: type inference failed for: r0v12 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                r0 = 0
                java.lang.String r1 = "anetwork.channel.aidl.ParcelableNetworkListener"
                r2 = 1
                if (r5 == r2) goto L_0x007a
                r3 = 2
                if (r5 == r3) goto L_0x0061
                r3 = 3
                if (r5 == r3) goto L_0x0040
                r0 = 4
                if (r5 == r0) goto L_0x002e
                r0 = 5
                if (r5 == r0) goto L_0x0020
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                if (r5 == r0) goto L_0x001c
                boolean r5 = super.onTransact(r5, r6, r7, r8)
                return r5
            L_0x001c:
                r7.writeString(r1)
                return r2
            L_0x0020:
                r6.enforceInterface(r1)
                byte r5 = r4.getListenerState()
                r7.writeNoException()
                r7.writeByte(r5)
                return r2
            L_0x002e:
                r6.enforceInterface(r1)
                android.os.IBinder r5 = r6.readStrongBinder()
                anetwork.channel.aidl.ParcelableInputStream r5 = anetwork.channel.aidl.ParcelableInputStream.Stub.asInterface(r5)
                r4.onInputStreamGet(r5)
                r7.writeNoException()
                return r2
            L_0x0040:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                int r8 = r6.readInt()
                if (r8 == 0) goto L_0x0056
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableHeader> r8 = anetwork.channel.aidl.ParcelableHeader.CREATOR
                java.lang.Object r6 = r8.createFromParcel(r6)
                r0 = r6
                anetwork.channel.aidl.ParcelableHeader r0 = (anetwork.channel.aidl.ParcelableHeader) r0
            L_0x0056:
                boolean r5 = r4.onResponseCode(r5, r0)
                r7.writeNoException()
                r7.writeInt(r5)
                return r2
            L_0x0061:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x0073
                android.os.Parcelable$Creator<anetwork.channel.aidl.DefaultFinishEvent> r5 = anetwork.channel.aidl.DefaultFinishEvent.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r0 = r5
                anetwork.channel.aidl.DefaultFinishEvent r0 = (anetwork.channel.aidl.DefaultFinishEvent) r0
            L_0x0073:
                r4.onFinished(r0)
                r7.writeNoException()
                return r2
            L_0x007a:
                r6.enforceInterface(r1)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x008c
                android.os.Parcelable$Creator<anetwork.channel.aidl.DefaultProgressEvent> r5 = anetwork.channel.aidl.DefaultProgressEvent.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r0 = r5
                anetwork.channel.aidl.DefaultProgressEvent r0 = (anetwork.channel.aidl.DefaultProgressEvent) r0
            L_0x008c:
                r4.onDataReceived(r0)
                r7.writeNoException()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.ParcelableNetworkListener.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        /* compiled from: Taobao */
        private static class Proxy implements ParcelableNetworkListener {

            /* renamed from: a */
            private IBinder f623a;

            Proxy(IBinder iBinder) {
                this.f623a = iBinder;
            }

            public IBinder asBinder() {
                return this.f623a;
            }

            public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultProgressEvent != null) {
                        obtain.writeInt(1);
                        defaultProgressEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f623a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultFinishEvent != null) {
                        obtain.writeInt(1);
                        defaultFinishEvent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f623a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean onResponseCode(int i, ParcelableHeader parcelableHeader) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    boolean z = true;
                    if (parcelableHeader != null) {
                        obtain.writeInt(1);
                        parcelableHeader.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f623a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z = false;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(parcelableInputStream != null ? parcelableInputStream.asBinder() : null);
                    this.f623a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public byte getListenerState() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f623a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readByte();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
