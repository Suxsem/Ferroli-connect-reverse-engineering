package com.igexin.push.util;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* renamed from: com.igexin.push.util.q */
final class C1592q implements IInterface {

    /* renamed from: a */
    private IBinder f3039a;

    public C1592q(IBinder iBinder) {
        this.f3039a = iBinder;
    }

    /* JADX INFO: finally extract failed */
    /* renamed from: a */
    public String mo15222a() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            this.f3039a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            obtain2.recycle();
            obtain.recycle();
            return readString;
        } catch (Exception unused) {
            obtain2.recycle();
            obtain.recycle();
            return null;
        } catch (Throwable th) {
            obtain2.recycle();
            obtain.recycle();
            throw th;
        }
    }

    public IBinder asBinder() {
        return this.f3039a;
    }
}
