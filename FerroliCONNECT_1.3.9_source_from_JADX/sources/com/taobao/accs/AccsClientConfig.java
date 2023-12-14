package com.taobao.accs;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.logger.ILog;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.AccsLogger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
public class AccsClientConfig {
    public static final String[] DEFAULT_CENTER_HOSTS = {"msgacs.m.taobao.com", "msgacs.wapa.taobao.com", "msgacs.waptest.taobao.com"};
    /* access modifiers changed from: private */
    public static final String[] DEFAULT_CHANNEL_HOSTS = {"accscdn.m.taobao.com", "acs.wapa.taobao.com", "acs.waptest.taobao.com"};
    public static final String DEFAULT_CONFIGTAG = "default";
    public static final int SECURITY_OFF = 2;
    public static final int SECURITY_OPEN = 1;
    public static final int SECURITY_TAOBAO = 0;
    @Deprecated
    public static boolean loadedStaticConfig = true;
    /* access modifiers changed from: private */
    public static ILog log = AccsLogger.getLogger("AccsClientConfig");
    private static Context mContext;
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mDebugConfigs = new ConcurrentHashMap(1);
    @ENV
    public static int mEnv = 0;
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mPreviewConfigs = new ConcurrentHashMap(1);
    /* access modifiers changed from: private */
    public static Map<String, AccsClientConfig> mReleaseConfigs = new ConcurrentHashMap(1);
    /* access modifiers changed from: private */
    public long loopInterval;
    /* access modifiers changed from: private */
    public boolean mAccsHeartbeatEnable;
    /* access modifiers changed from: private */
    public String mAppKey;
    /* access modifiers changed from: private */
    public String mAppSecret;
    /* access modifiers changed from: private */
    public String mAuthCode;
    /* access modifiers changed from: private */
    public boolean mAutoUnit;
    /* access modifiers changed from: private */
    public String mChannelHost;
    /* access modifiers changed from: private */
    public boolean mChannelLoopStart;
    /* access modifiers changed from: private */
    public int mChannelPubKey;
    /* access modifiers changed from: private */
    public int mConfigEnv;
    /* access modifiers changed from: private */
    public boolean mDisableChannel;
    /* access modifiers changed from: private */
    public String mInappHost;
    /* access modifiers changed from: private */
    public int mInappPubKey;
    /* access modifiers changed from: private */
    public boolean mKeepalive;
    /* access modifiers changed from: private */
    public boolean mQuickReconnect;
    /* access modifiers changed from: private */
    public int mSecurity;
    /* access modifiers changed from: private */
    public String mStoreId;
    /* access modifiers changed from: private */
    public String mTag;

    @Retention(RetentionPolicy.CLASS)
    /* compiled from: Taobao */
    public @interface ENV {
    }

    @Retention(RetentionPolicy.CLASS)
    /* compiled from: Taobao */
    public @interface SECURITY_TYPE {
    }

    public static Context getContext() {
        Context context = mContext;
        if (context != null) {
            return context;
        }
        synchronized (AccsClientConfig.class) {
            if (mContext != null) {
                Context context2 = mContext;
                return context2;
            }
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Object invoke = cls.getMethod("currentActivityThread", new Class[0]).invoke(cls, new Object[0]);
                mContext = (Context) invoke.getClass().getMethod("getApplication", new Class[0]).invoke(invoke, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Context context3 = mContext;
            return context3;
        }
    }

    protected AccsClientConfig() {
    }

    @Deprecated
    public static AccsClientConfig getConfig(String str) {
        Map<String, AccsClientConfig> map;
        int i = mEnv;
        if (i == 1) {
            map = mPreviewConfigs;
        } else if (i != 2) {
            map = mReleaseConfigs;
        } else {
            map = mDebugConfigs;
        }
        for (AccsClientConfig next : map.values()) {
            if (next.mAppKey.equals(str) && next.mConfigEnv == mEnv) {
                return next;
            }
        }
        log.mo9710e("getConfigByTag return null", Constants.KEY_APP_KEY, str);
        return null;
    }

    public static AccsClientConfig getConfigByTag(String str) {
        Map<String, AccsClientConfig> map;
        int i = mEnv;
        if (i == 1) {
            map = mPreviewConfigs;
        } else if (i != 2) {
            map = mReleaseConfigs;
        } else {
            map = mDebugConfigs;
        }
        AccsClientConfig accsClientConfig = map.get(str);
        if (accsClientConfig == null) {
            log.mo9715w("getConfigByTag return null", Constants.KEY_CONFIG_TAG, str);
        }
        return accsClientConfig;
    }

    public static List<String> tags() {
        Map<String, AccsClientConfig> map;
        int i = mEnv;
        if (i == 1) {
            map = mPreviewConfigs;
        } else if (i != 2) {
            map = mReleaseConfigs;
        } else {
            map = mDebugConfigs;
        }
        return new ArrayList(map.keySet());
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getAppSecret() {
        return this.mAppSecret;
    }

    public String getInappHost() {
        return this.mInappHost;
    }

    public String getChannelHost() {
        return this.mChannelHost;
    }

    public int getSecurity() {
        return this.mSecurity;
    }

    public String getAuthCode() {
        return this.mAuthCode;
    }

    public int getInappPubKey() {
        return this.mInappPubKey;
    }

    public int getChannelPubKey() {
        return this.mChannelPubKey;
    }

    public boolean isKeepalive() {
        return this.mKeepalive;
    }

    public boolean isAutoUnit() {
        return this.mAutoUnit;
    }

    public String getTag() {
        return this.mTag;
    }

    public int getConfigEnv() {
        return this.mConfigEnv;
    }

    public boolean getDisableChannel() {
        return this.mDisableChannel;
    }

    public boolean isQuickReconnect() {
        return this.mQuickReconnect;
    }

    public String getStoreId() {
        return this.mStoreId;
    }

    public boolean isAccsHeartbeatEnable() {
        return this.mAccsHeartbeatEnable;
    }

    public boolean isChannelLoopStart() {
        return this.mChannelLoopStart;
    }

    public long getLoopInterval() {
        return this.loopInterval;
    }

    public String toString() {
        return "AccsClientConfig{" + "Tag=" + this.mTag + ", ConfigEnv=" + this.mConfigEnv + ", AppKey=" + this.mAppKey + ", AppSecret=" + this.mAppSecret + ", InappHost=" + this.mInappHost + ", ChannelHost=" + this.mChannelHost + ", Security=" + this.mSecurity + ", AuthCode=" + this.mAuthCode + ", InappPubKey=" + this.mInappPubKey + ", ChannelPubKey=" + this.mChannelPubKey + ", Keepalive=" + this.mKeepalive + ", AutoUnit=" + this.mAutoUnit + ", DisableChannel=" + this.mDisableChannel + ", QuickReconnect=" + this.mQuickReconnect + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccsClientConfig accsClientConfig = (AccsClientConfig) obj;
        if (!this.mInappHost.equals(accsClientConfig.mInappHost) || this.mInappPubKey != accsClientConfig.mInappPubKey || !this.mChannelHost.equals(accsClientConfig.mChannelHost) || this.mChannelPubKey != accsClientConfig.mChannelPubKey || this.mSecurity != accsClientConfig.mSecurity || this.mConfigEnv != accsClientConfig.mConfigEnv || !this.mAppKey.equals(accsClientConfig.mAppKey) || this.mKeepalive != accsClientConfig.mKeepalive || this.mDisableChannel != accsClientConfig.mDisableChannel) {
            return false;
        }
        String str = this.mAuthCode;
        if (str == null ? accsClientConfig.mAuthCode != null : !str.equals(accsClientConfig.mAuthCode)) {
            return false;
        }
        String str2 = this.mAppSecret;
        if (str2 == null ? accsClientConfig.mAppSecret == null : str2.equals(accsClientConfig.mAppSecret)) {
            return this.mTag.equals(accsClientConfig.mTag);
        }
        return false;
    }

    /* compiled from: Taobao */
    public static class Builder {
        private long loopInterval = 300000;
        private boolean mAccsHeartbeatEnable = false;
        private String mAppKey;
        private String mAppSecret;
        private String mAuthCode;
        private boolean mAutoUnit = true;
        private String mChannelHost;
        private boolean mChannelLoopStart = false;
        private int mChannelPubKey = -1;
        private int mConfigEnv = -1;
        private boolean mDisableChannel = false;
        private String mInappHost;
        private int mInappPubKey = -1;
        private boolean mKeepalive = true;
        private boolean mQuickReconnect = false;
        private String mStoreId;
        private String mTag;

        public Builder setAppKey(String str) {
            this.mAppKey = str;
            return this;
        }

        public Builder setAppSecret(String str) {
            this.mAppSecret = str;
            return this;
        }

        public Builder setInappHost(String str) {
            this.mInappHost = str;
            return this;
        }

        public Builder setChannelHost(String str) {
            this.mChannelHost = str;
            return this;
        }

        @Deprecated
        public Builder setAutoCode(String str) {
            this.mAuthCode = str;
            return this;
        }

        public Builder setInappPubKey(int i) {
            this.mInappPubKey = i;
            return this;
        }

        public Builder setChannelPubKey(int i) {
            this.mChannelPubKey = i;
            return this;
        }

        public Builder setKeepAlive(boolean z) {
            this.mKeepalive = z;
            return this;
        }

        public Builder setAutoUnit(boolean z) {
            this.mAutoUnit = z;
            return this;
        }

        public Builder setConfigEnv(@ENV int i) {
            this.mConfigEnv = i;
            return this;
        }

        public Builder setStoreId(String str) {
            this.mStoreId = str;
            return this;
        }

        public Builder setTag(String str) {
            this.mTag = str;
            return this;
        }

        public Builder setDisableChannel(boolean z) {
            this.mDisableChannel = z;
            return this;
        }

        public Builder setQuickReconnect(boolean z) {
            this.mQuickReconnect = z;
            return this;
        }

        public Builder setAccsHeartbeatEnable(boolean z) {
            this.mAccsHeartbeatEnable = z;
            return this;
        }

        public Builder loopChannelStart(boolean z) {
            this.mChannelLoopStart = z;
            return this;
        }

        public Builder loopChannelInterval(long j) {
            this.loopInterval = j;
            return this;
        }

        public AccsClientConfig build() throws AccsException {
            Map map;
            if (TextUtils.isEmpty(this.mAppKey)) {
                throw new AccsException("appkey null");
            } else if (!TextUtils.isEmpty(this.mAppSecret)) {
                AccsClientConfig accsClientConfig = new AccsClientConfig();
                String unused = accsClientConfig.mAppKey = this.mAppKey;
                String unused2 = accsClientConfig.mAppSecret = this.mAppSecret;
                String unused3 = accsClientConfig.mAuthCode = this.mAuthCode;
                boolean unused4 = accsClientConfig.mKeepalive = this.mKeepalive;
                boolean unused5 = accsClientConfig.mAutoUnit = this.mAutoUnit;
                int unused6 = accsClientConfig.mInappPubKey = this.mInappPubKey;
                int unused7 = accsClientConfig.mChannelPubKey = this.mChannelPubKey;
                String unused8 = accsClientConfig.mInappHost = this.mInappHost;
                String unused9 = accsClientConfig.mChannelHost = this.mChannelHost;
                String unused10 = accsClientConfig.mTag = this.mTag;
                String unused11 = accsClientConfig.mStoreId = this.mStoreId;
                int unused12 = accsClientConfig.mConfigEnv = this.mConfigEnv;
                boolean unused13 = accsClientConfig.mDisableChannel = this.mDisableChannel;
                boolean unused14 = accsClientConfig.mQuickReconnect = this.mQuickReconnect;
                boolean unused15 = accsClientConfig.mAccsHeartbeatEnable = this.mAccsHeartbeatEnable;
                boolean unused16 = accsClientConfig.mChannelLoopStart = this.mChannelLoopStart;
                long unused17 = accsClientConfig.loopInterval = this.loopInterval;
                if (accsClientConfig.mConfigEnv < 0) {
                    int unused18 = accsClientConfig.mConfigEnv = AccsClientConfig.mEnv;
                }
                int unused19 = accsClientConfig.mSecurity = 2;
                if (TextUtils.isEmpty(accsClientConfig.mInappHost)) {
                    String unused20 = accsClientConfig.mInappHost = AccsClientConfig.DEFAULT_CENTER_HOSTS[accsClientConfig.mConfigEnv];
                }
                if (TextUtils.isEmpty(accsClientConfig.mChannelHost)) {
                    String unused21 = accsClientConfig.mChannelHost = AccsClientConfig.DEFAULT_CHANNEL_HOSTS[accsClientConfig.mConfigEnv];
                }
                if (TextUtils.isEmpty(accsClientConfig.mTag)) {
                    String unused22 = accsClientConfig.mTag = accsClientConfig.mAppKey;
                }
                int access$1100 = accsClientConfig.mConfigEnv;
                if (access$1100 == 1) {
                    map = AccsClientConfig.mPreviewConfigs;
                } else if (access$1100 != 2) {
                    map = AccsClientConfig.mReleaseConfigs;
                } else {
                    map = AccsClientConfig.mDebugConfigs;
                }
                AccsClientConfig.log.mo9707d("build config", accsClientConfig);
                AccsClientConfig accsClientConfig2 = (AccsClientConfig) map.get(accsClientConfig.getTag());
                if (accsClientConfig2 != null) {
                    AccsClientConfig.log.mo9715w("build cover", "old", accsClientConfig2, "new", accsClientConfig);
                }
                map.put(accsClientConfig.getTag(), accsClientConfig);
                return accsClientConfig;
            } else {
                throw new AccsException("appSecret null");
            }
        }
    }
}
