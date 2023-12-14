package com.alibaba.sdk.android.push.noonesdk;

import android.app.Application;

public class PushInitConfig {
    private String appKey;
    private String appSecret;
    private Application application;
    private boolean disableChannelProcess;
    private boolean disableChannelProcessHeartbeat;
    private boolean disableForegroundCheck;
    private long loopInterval;
    private boolean loopStartChannel;

    public static class Builder {
        /* access modifiers changed from: private */
        public String appKey = null;
        /* access modifiers changed from: private */
        public String appSecret = null;
        /* access modifiers changed from: private */
        public Application application;
        /* access modifiers changed from: private */
        public boolean disableChannelProcess = false;
        /* access modifiers changed from: private */
        public boolean disableChannelProcessHeartbeat = true;
        /* access modifiers changed from: private */
        public boolean disableForegroundCheck = false;
        /* access modifiers changed from: private */
        public long loopInterval = 300000;
        /* access modifiers changed from: private */
        public boolean loopStartChannel = false;

        public Builder appKey(String str) {
            this.appKey = str;
            return this;
        }

        public Builder appSecret(String str) {
            this.appSecret = str;
            return this;
        }

        public Builder application(Application application2) {
            this.application = application2;
            return this;
        }

        public PushInitConfig build() {
            return new PushInitConfig(this);
        }

        public Builder disableChannelProcess(boolean z) {
            this.disableChannelProcess = z;
            return this;
        }

        public Builder disableChannelProcessheartbeat(boolean z) {
            this.disableChannelProcessHeartbeat = z;
            return this;
        }

        public Builder disableForegroundCheck(boolean z) {
            this.disableForegroundCheck = z;
            return this;
        }

        public Builder loopInterval(long j) {
            this.loopInterval = j;
            return this;
        }

        public Builder loopStartChannel(boolean z) {
            this.loopStartChannel = z;
            return this;
        }
    }

    protected PushInitConfig(Builder builder) {
        this.application = builder.application;
        this.appKey = builder.appKey;
        this.appSecret = builder.appSecret;
        this.disableChannelProcess = builder.disableChannelProcess;
        this.loopStartChannel = builder.loopStartChannel;
        this.loopInterval = builder.loopInterval;
        this.disableForegroundCheck = builder.disableForegroundCheck;
        this.disableChannelProcessHeartbeat = builder.disableChannelProcessHeartbeat;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public Application getApplication() {
        return this.application;
    }

    public long getLoopInterval() {
        return this.loopInterval;
    }

    public boolean isDisableChannelProcess() {
        return this.disableChannelProcess;
    }

    public boolean isDisableChannelProcessHeartbeat() {
        return this.disableChannelProcessHeartbeat;
    }

    public boolean isDisableForegroundCheck() {
        return this.disableForegroundCheck;
    }

    public boolean isLoopStartChannel() {
        return this.loopStartChannel;
    }
}
