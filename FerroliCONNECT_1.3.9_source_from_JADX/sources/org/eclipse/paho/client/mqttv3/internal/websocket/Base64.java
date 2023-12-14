package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;

public class Base64 {
    private static final Base64Encoder encoder;
    private static final Base64 instance = new Base64();

    static {
        Base64 base64 = instance;
        base64.getClass();
        encoder = new Base64Encoder();
    }

    public static String encode(String str) {
        encoder.putByteArray("akey", str.getBytes());
        return encoder.getBase64String();
    }

    public static String encodeBytes(byte[] bArr) {
        encoder.putByteArray("aKey", bArr);
        return encoder.getBase64String();
    }

    public class Base64Encoder extends AbstractPreferences {
        private String base64String = null;

        /* access modifiers changed from: protected */
        public AbstractPreferences childSpi(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public String[] childrenNamesSpi() throws BackingStoreException {
            return null;
        }

        /* access modifiers changed from: protected */
        public void flushSpi() throws BackingStoreException {
        }

        /* access modifiers changed from: protected */
        public String getSpi(String str) {
            return null;
        }

        /* access modifiers changed from: protected */
        public String[] keysSpi() throws BackingStoreException {
            return null;
        }

        /* access modifiers changed from: protected */
        public void removeNodeSpi() throws BackingStoreException {
        }

        /* access modifiers changed from: protected */
        public void removeSpi(String str) {
        }

        /* access modifiers changed from: protected */
        public void syncSpi() throws BackingStoreException {
        }

        public Base64Encoder() {
            super((AbstractPreferences) null, "");
        }

        /* access modifiers changed from: protected */
        public void putSpi(String str, String str2) {
            this.base64String = str2;
        }

        public String getBase64String() {
            return this.base64String;
        }
    }
}
