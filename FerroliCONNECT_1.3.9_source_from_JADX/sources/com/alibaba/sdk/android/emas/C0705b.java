package com.alibaba.sdk.android.emas;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import com.google.android.gms.stats.CodePackage;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.GCMParameterSpec;

/* renamed from: com.alibaba.sdk.android.emas.b */
/* compiled from: AesGcmCipher */
class C0705b {

    /* renamed from: a */
    private static final C0705b f890a = new C0705b();

    /* renamed from: a */
    private KeyStore f891a;

    /* renamed from: b */
    private boolean f892b = true;

    private C0705b() {
        try {
            m612a();
            if (Build.VERSION.SDK_INT >= 23) {
                m615a(m612a());
            }
        } catch (Throwable unused) {
            this.f892b = false;
        }
    }

    /* renamed from: a */
    public static C0705b m612a() {
        return f890a;
    }

    /* renamed from: a */
    private void m614a() {
        try {
            this.f891a = KeyStore.getInstance("AndroidKeyStore");
            this.f891a.load((KeyStore.LoadStoreParameter) null);
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            LogUtil.m1030e(Log.getStackTraceString(e));
            this.f892b = false;
        }
    }

    /* renamed from: a */
    private void m615a(Key key) {
        try {
            if (!this.f891a.containsAlias("emas_rest_key")) {
                this.f891a.setKeyEntry("emas_rest_key", key, (char[]) null, (Certificate[]) null);
            }
        } catch (KeyStoreException e) {
            LogUtil.m1030e(Log.getStackTraceString(e));
            this.f892b = false;
        }
    }

    /* renamed from: a */
    private Key m613a() {
        try {
            if (Build.VERSION.SDK_INT < 23) {
                return null;
            }
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("emas_rest_key", 3);
            builder.setKeySize(256);
            builder.setBlockModes(new String[]{CodePackage.GCM});
            builder.setEncryptionPaddings(new String[]{"NoPadding"});
            if (Build.VERSION.SDK_INT >= 28) {
                builder.setUnlockedDeviceRequired(true);
            }
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            instance.init(builder.build());
            return instance.generateKey();
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            LogUtil.m1030e(Log.getStackTraceString(e));
            this.f892b = false;
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public byte[] mo9630a(byte[] bArr) {
        if (!this.f892b) {
            return null;
        }
        try {
            Cipher instance = Cipher.getInstance("AES/GCM/NoPadding");
            instance.init(1, this.f891a.getKey("emas_rest_key", (char[]) null));
            byte[] iv = instance.getIV();
            byte[] bArr2 = new byte[(instance.getOutputSize(bArr.length) + 12)];
            System.arraycopy(iv, 0, bArr2, 0, 12);
            instance.doFinal(bArr, 0, bArr.length, bArr2, 12);
            return bArr2;
        } catch (InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | ShortBufferException e) {
            LogUtil.m1030e(Log.getStackTraceString(e));
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public byte[] mo9631b(byte[] bArr) {
        if (!this.f892b) {
            return null;
        }
        try {
            Cipher instance = Cipher.getInstance("AES/GCM/NoPadding");
            instance.init(2, this.f891a.getKey("emas_rest_key", (char[]) null), new GCMParameterSpec(128, bArr, 0, 12));
            byte[] bArr2 = new byte[instance.getOutputSize(bArr.length - 12)];
            instance.doFinal(bArr, 12, bArr.length - 12, bArr2, 0);
            return bArr2;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | ShortBufferException e) {
            LogUtil.m1030e(Log.getStackTraceString(e));
            return null;
        }
    }
}
