package com.google.android.gms.common;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.eclipse.jetty.util.StringUtil;

abstract class zze extends zzj {
    private int zzt;

    protected zze(byte[] bArr) {
        Preconditions.checkArgument(bArr.length == 25);
        this.zzt = Arrays.hashCode(bArr);
    }

    /* access modifiers changed from: package-private */
    public abstract byte[] getBytes();

    public int hashCode() {
        return this.zzt;
    }

    public boolean equals(Object obj) {
        IObjectWrapper zzb;
        if (obj != null && (obj instanceof zzi)) {
            try {
                zzi zzi = (zzi) obj;
                if (zzi.zzc() != hashCode() || (zzb = zzi.zzb()) == null) {
                    return false;
                }
                return Arrays.equals(getBytes(), (byte[]) ObjectWrapper.unwrap(zzb));
            } catch (RemoteException e) {
                Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            }
        }
        return false;
    }

    public final IObjectWrapper zzb() {
        return ObjectWrapper.wrap(getBytes());
    }

    public final int zzc() {
        return hashCode();
    }

    protected static byte[] zza(String str) {
        try {
            return str.getBytes(StringUtil.__ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
