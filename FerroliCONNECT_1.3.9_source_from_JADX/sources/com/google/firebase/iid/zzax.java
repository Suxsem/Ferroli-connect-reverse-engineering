package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.GuardedBy;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.util.ArrayMap;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Map;

final class zzax {
    private final Context zzag;
    private final SharedPreferences zzdm;
    private final zzx zzdn;
    @GuardedBy("this")
    private final Map<String, zzaa> zzdo;

    public zzax(Context context) {
        this(context, new zzx());
    }

    private zzax(Context context, zzx zzx) {
        this.zzdo = new ArrayMap();
        this.zzag = context;
        this.zzdm = context.getSharedPreferences("com.google.android.gms.appid", 0);
        this.zzdn = zzx;
        File file = new File(ContextCompat.getNoBackupFilesDir(this.zzag), "com.google.android.gms.appid-no-backup");
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("FirebaseInstanceId", "App restored, clearing state");
                    zzal();
                    FirebaseInstanceId.getInstance().zzn();
                }
            } catch (IOException e) {
                if (Log.isLoggable("FirebaseInstanceId", 3)) {
                    String valueOf = String.valueOf(e.getMessage());
                    Log.d("FirebaseInstanceId", valueOf.length() != 0 ? "Error creating file in no backup dir: ".concat(valueOf) : new String("Error creating file in no backup dir: "));
                }
            }
        }
    }

    public final synchronized String zzak() {
        return this.zzdm.getString("topic_operaion_queue", "");
    }

    public final synchronized void zzh(String str) {
        this.zzdm.edit().putString("topic_operaion_queue", str).apply();
    }

    private final synchronized boolean isEmpty() {
        return this.zzdm.getAll().isEmpty();
    }

    private static String zza(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append("|T|");
        sb.append(str2);
        sb.append("|");
        sb.append(str3);
        return sb.toString();
    }

    static String zzd(String str, String str2) {
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length());
        sb.append(str);
        sb.append("|S|");
        sb.append(str2);
        return sb.toString();
    }

    public final synchronized void zzal() {
        this.zzdo.clear();
        zzx.zza(this.zzag);
        this.zzdm.edit().clear().commit();
    }

    public final synchronized zzaw zzb(String str, String str2, String str3) {
        return zzaw.zzf(this.zzdm.getString(zza(str, str2, str3), (String) null));
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String zza = zzaw.zza(str4, str5, System.currentTimeMillis());
        if (zza != null) {
            SharedPreferences.Editor edit = this.zzdm.edit();
            edit.putString(zza(str, str2, str3), zza);
            edit.commit();
        }
    }

    public final synchronized void zzc(String str, String str2, String str3) {
        String zza = zza(str, str2, str3);
        SharedPreferences.Editor edit = this.zzdm.edit();
        edit.remove(zza);
        edit.commit();
    }

    public final synchronized zzaa zzi(String str) {
        zzaa zzaa;
        zzaa zzaa2 = this.zzdo.get(str);
        if (zzaa2 != null) {
            return zzaa2;
        }
        try {
            zzaa = this.zzdn.zzb(this.zzag, str);
        } catch (zzz unused) {
            Log.w("FirebaseInstanceId", "Stored data is corrupt, generating new identity");
            FirebaseInstanceId.getInstance().zzn();
            zzaa = this.zzdn.zzc(this.zzag, str);
        }
        this.zzdo.put(str, zzaa);
        return zzaa;
    }

    public final synchronized void zzj(String str) {
        String concat = String.valueOf(str).concat("|T|");
        SharedPreferences.Editor edit = this.zzdm.edit();
        for (String next : this.zzdm.getAll().keySet()) {
            if (next.startsWith(concat)) {
                edit.remove(next);
            }
        }
        edit.commit();
    }
}
