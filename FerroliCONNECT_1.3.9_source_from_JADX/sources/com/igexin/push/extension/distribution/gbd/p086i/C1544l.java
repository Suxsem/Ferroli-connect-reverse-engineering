package com.igexin.push.extension.distribution.gbd.p086i;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* renamed from: com.igexin.push.extension.distribution.gbd.i.l */
class C1544l implements IInterface {

    /* renamed from: a */
    private IBinder f2957a;

    public C1544l(IBinder iBinder) {
        this.f2957a = iBinder;
    }

    /* JADX INFO: finally extract failed */
    /* renamed from: a */
    public String mo15176a() {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            this.f2957a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            String readString = obtain2.readString();
            obtain2.recycle();
            obtain.recycle();
            return readString;
        } catch (Exception e) {
            C1540h.m2996a(e);
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
        return this.f2957a;
    }
}
