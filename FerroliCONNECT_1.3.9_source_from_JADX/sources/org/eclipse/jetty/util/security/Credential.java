package org.eclipse.jetty.util.security;

import java.io.Serializable;
import java.security.MessageDigest;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public abstract class Credential implements Serializable {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) Credential.class);
    private static final long serialVersionUID = -7760551052768181572L;

    public abstract boolean check(Object obj);

    public static Credential getCredential(String str) {
        if (str.startsWith(Crypt.__TYPE)) {
            return new Crypt(str);
        }
        if (str.startsWith(MD5.__TYPE)) {
            return new MD5(str);
        }
        return new Password(str);
    }

    public static class Crypt extends Credential {
        public static final String __TYPE = "CRYPT:";
        private static final long serialVersionUID = -2027792997664744210L;
        private final String _cooked;

        Crypt(String str) {
            this._cooked = str.startsWith(__TYPE) ? str.substring(6) : str;
        }

        public boolean check(Object obj) {
            if (obj instanceof char[]) {
                obj = new String((char[]) obj);
            }
            if (!(obj instanceof String) && !(obj instanceof Password)) {
                Logger access$000 = Credential.LOG;
                access$000.warn("Can't check " + obj.getClass() + " against CRYPT", new Object[0]);
            }
            String obj2 = obj.toString();
            String str = this._cooked;
            return str.equals(UnixCrypt.crypt(obj2, str));
        }

        public static String crypt(String str, String str2) {
            return __TYPE + UnixCrypt.crypt(str2, str);
        }
    }

    public static class MD5 extends Credential {
        public static final String __TYPE = "MD5:";
        private static MessageDigest __md = null;
        public static final Object __md5Lock = new Object();
        private static final long serialVersionUID = 5533846540822684240L;
        private final byte[] _digest;

        MD5(String str) {
            this._digest = TypeUtil.parseBytes(str.startsWith(__TYPE) ? str.substring(4) : str, 16);
        }

        public byte[] getDigest() {
            return this._digest;
        }

        public boolean check(Object obj) {
            byte[] digest;
            try {
                if (obj instanceof char[]) {
                    obj = new String((char[]) obj);
                }
                if (!(obj instanceof Password)) {
                    if (!(obj instanceof String)) {
                        if (obj instanceof MD5) {
                            MD5 md5 = (MD5) obj;
                            if (this._digest.length != md5._digest.length) {
                                return false;
                            }
                            for (int i = 0; i < this._digest.length; i++) {
                                if (this._digest[i] != md5._digest[i]) {
                                    return false;
                                }
                            }
                            return true;
                        } else if (obj instanceof Credential) {
                            return ((Credential) obj).check(this);
                        } else {
                            Logger access$000 = Credential.LOG;
                            access$000.warn("Can't check " + obj.getClass() + " against MD5", new Object[0]);
                            return false;
                        }
                    }
                }
                synchronized (__md5Lock) {
                    if (__md == null) {
                        __md = MessageDigest.getInstance("MD5");
                    }
                    __md.reset();
                    __md.update(obj.toString().getBytes(StringUtil.__ISO_8859_1));
                    digest = __md.digest();
                }
                if (digest != null) {
                    if (digest.length == this._digest.length) {
                        for (int i2 = 0; i2 < digest.length; i2++) {
                            if (digest[i2] != this._digest[i2]) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                Credential.LOG.warn(e);
                return false;
            }
        }

        public static String digest(String str) {
            byte[] digest;
            try {
                synchronized (__md5Lock) {
                    if (__md == null) {
                        try {
                            __md = MessageDigest.getInstance("MD5");
                        } catch (Exception e) {
                            Credential.LOG.warn(e);
                            return null;
                        }
                    }
                    __md.reset();
                    __md.update(str.getBytes(StringUtil.__ISO_8859_1));
                    digest = __md.digest();
                }
                return __TYPE + TypeUtil.toString(digest, 16);
            } catch (Exception e2) {
                Credential.LOG.warn(e2);
                return null;
            }
        }
    }
}
