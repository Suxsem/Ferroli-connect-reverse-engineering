package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.ParcelableFuture;

/* compiled from: Taobao */
public interface RemoteNetwork extends IInterface {
    ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException;

    Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException;

    NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException;

    /* compiled from: Taobao */
    public static abstract class Stub extends Binder implements RemoteNetwork {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.RemoteNetwork";
        static final int TRANSACTION_asyncSend = 2;
        static final int TRANSACTION_getConnection = 3;
        static final int TRANSACTION_syncSend = 1;

        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static RemoteNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface == null || !(queryLocalInterface instanceof RemoteNetwork)) {
                return new Proxy(iBinder);
            }
            return (RemoteNetwork) queryLocalInterface;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: anetwork.channel.aidl.ParcelableRequest} */
        /* JADX WARNING: type inference failed for: r1v0 */
        /* JADX WARNING: type inference failed for: r1v4, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v6, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r1v8 */
        /* JADX WARNING: type inference failed for: r1v9 */
        /* JADX WARNING: type inference failed for: r1v10 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r5, android.os.Parcel r6, android.os.Parcel r7, int r8) throws android.os.RemoteException {
            /*
                r4 = this;
                java.lang.String r0 = "anetwork.channel.aidl.RemoteNetwork"
                r1 = 0
                r2 = 1
                if (r5 == r2) goto L_0x006a
                r3 = 2
                if (r5 == r3) goto L_0x003e
                r3 = 3
                if (r5 == r3) goto L_0x001a
                r1 = 1598968902(0x5f4e5446, float:1.4867585E19)
                if (r5 == r1) goto L_0x0016
                boolean r5 = super.onTransact(r5, r6, r7, r8)
                return r5
            L_0x0016:
                r7.writeString(r0)
                return r2
            L_0x001a:
                r6.enforceInterface(r0)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x002c
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r5 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                anetwork.channel.aidl.ParcelableRequest r5 = (anetwork.channel.aidl.ParcelableRequest) r5
                goto L_0x002d
            L_0x002c:
                r5 = r1
            L_0x002d:
                anetwork.channel.aidl.Connection r5 = r4.getConnection(r5)
                r7.writeNoException()
                if (r5 == 0) goto L_0x003a
                android.os.IBinder r1 = r5.asBinder()
            L_0x003a:
                r7.writeStrongBinder(r1)
                return r2
            L_0x003e:
                r6.enforceInterface(r0)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x0050
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r5 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                anetwork.channel.aidl.ParcelableRequest r5 = (anetwork.channel.aidl.ParcelableRequest) r5
                goto L_0x0051
            L_0x0050:
                r5 = r1
            L_0x0051:
                android.os.IBinder r6 = r6.readStrongBinder()
                anetwork.channel.aidl.ParcelableNetworkListener r6 = anetwork.channel.aidl.ParcelableNetworkListener.Stub.asInterface(r6)
                anetwork.channel.aidl.ParcelableFuture r5 = r4.asyncSend(r5, r6)
                r7.writeNoException()
                if (r5 == 0) goto L_0x0066
                android.os.IBinder r1 = r5.asBinder()
            L_0x0066:
                r7.writeStrongBinder(r1)
                return r2
            L_0x006a:
                r6.enforceInterface(r0)
                int r5 = r6.readInt()
                if (r5 == 0) goto L_0x007c
                android.os.Parcelable$Creator<anetwork.channel.aidl.ParcelableRequest> r5 = anetwork.channel.aidl.ParcelableRequest.CREATOR
                java.lang.Object r5 = r5.createFromParcel(r6)
                r1 = r5
                anetwork.channel.aidl.ParcelableRequest r1 = (anetwork.channel.aidl.ParcelableRequest) r1
            L_0x007c:
                anetwork.channel.aidl.NetworkResponse r5 = r4.syncSend(r1)
                r7.writeNoException()
                if (r5 == 0) goto L_0x008c
                r7.writeInt(r2)
                r5.writeToParcel(r7, r2)
                goto L_0x0090
            L_0x008c:
                r5 = 0
                r7.writeInt(r5)
            L_0x0090:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.RemoteNetwork.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }

        /* compiled from: Taobao */
        private static class Proxy implements RemoteNetwork {

            /* renamed from: a */
            private IBinder f624a;

            Proxy(IBinder iBinder) {
                this.f624a = iBinder;
            }

            public IBinder asBinder() {
                return this.f624a;
            }

            public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f624a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? NetworkResponse.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(parcelableNetworkListener != null ? parcelableNetworkListener.asBinder() : null);
                    this.f624a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return ParcelableFuture.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        obtain.writeInt(1);
                        parcelableRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.f624a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return Connection.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
