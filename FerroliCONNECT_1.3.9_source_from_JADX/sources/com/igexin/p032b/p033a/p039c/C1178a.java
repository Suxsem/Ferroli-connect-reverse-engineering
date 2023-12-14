package com.igexin.p032b.p033a.p039c;

import android.text.TextUtils;
import com.igexin.p032b.p033a.p034a.C1150a;
import com.igexin.push.core.C1343f;
import com.igexin.push.util.C1584i;
import com.igexin.push.util.C1593r;
import com.igexin.push.util.EncryptUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import kotlin.UByte;

/* renamed from: com.igexin.b.a.c.a */
public class C1178a {
    /* renamed from: a */
    private static byte m1345a(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /* renamed from: a */
    private static String m1346a(byte[] bArr) {
        StringBuilder sb = new StringBuilder("");
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /* renamed from: a */
    public static CipherOutputStream m1347a(File file, SecretKeySpec secretKeySpec) {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        byte[] bArr = new byte[16];
        if (randomAccessFile.length() == 0) {
            randomAccessFile.write(m1350a(m1346a(secretKeySpec.getEncoded())));
            new SecureRandom().nextBytes(bArr);
            randomAccessFile.write(bArr);
        } else if (randomAccessFile.length() >= 144) {
            if (randomAccessFile.length() % 16 != 0) {
                m1349a(randomAccessFile);
            }
            randomAccessFile.seek(randomAccessFile.length() - 16);
            randomAccessFile.read(bArr);
        } else {
            throw new IllegalArgumentException("Invalid file length (need 2 blocks for iv and data)");
        }
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(bArr));
        return new CipherOutputStream(new FileOutputStream(randomAccessFile.getFD()), instance);
    }

    /* renamed from: a */
    public static void m1348a() {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        instance.init(128);
        C1343f.f2137aB = instance.generateKey().getEncoded();
        C1593r.m3268b(C1343f.f2169f, "logkey2", m1346a(EncryptUtils.getBytesEncrypted(C1343f.f2137aB)));
    }

    /* renamed from: a */
    public static void m1349a(RandomAccessFile randomAccessFile) {
        long length = (long) ((int) (randomAccessFile.length() % 16));
        if (length < 16 && length > 0) {
            randomAccessFile.setLength(randomAccessFile.length() - length);
        }
    }

    /* renamed from: a */
    private static byte[] m1350a(String str) {
        RSAPublicKey b = m1351b("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzbMQ22qV6umuPXYWXEOGdlpJR\nBWMP68/ArS7XG8+7GmRbWMW1HOMLOOdwuIfPFp9QiwOshG0mYXlm1ecQ/fCXhRMW\nfh+OMCoBdl7vnCpoDYPmjYQBkm9fRW6oej33UhZtlnTZjECAsyC2Eybha7jg3Lft\ngYVnwaPShTmv5+Z9SQIDAQAB");
        Cipher instance = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        instance.init(1, b);
        return instance.doFinal(str.getBytes("UTF-8"));
    }

    /* renamed from: b */
    private static RSAPublicKey m1351b(String str) {
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(C1584i.m3246a(str, 0)));
    }

    /* renamed from: b */
    public static byte[] m1352b() {
        if (C1343f.f2137aB == null) {
            String str = (String) C1593r.m3270c(C1343f.f2169f, "logkey2", "");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            C1343f.f2137aB = C1150a.m1234c(m1353c(str), C1343f.f2110B);
        }
        return C1343f.f2137aB;
    }

    /* renamed from: c */
    private static byte[] m1353c(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        String upperCase = str.toUpperCase();
        int length = upperCase.length() / 2;
        char[] charArray = upperCase.toCharArray();
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) (m1345a(charArray[i2 + 1]) | (m1345a(charArray[i2]) << 4));
        }
        return bArr;
    }
}
