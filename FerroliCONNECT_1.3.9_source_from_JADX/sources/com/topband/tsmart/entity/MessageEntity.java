package com.topband.tsmart.entity;

import android.content.Context;
import com.topband.tsmart.TBPushMessage;
import java.io.Serializable;
import java.util.Map;

public class MessageEntity implements Serializable {
    private Context context;
    private Map<String, String> extraMap;
    private TBPushMessage message;
    private String openActivity;
    private int openType;
    private String openUrl;
    private String summary;
    private String title;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String str) {
        this.summary = str;
    }

    public Map<String, String> getExtraMap() {
        return this.extraMap;
    }

    public void setExtraMap(Map<String, String> map) {
        this.extraMap = map;
    }

    public TBPushMessage getMessage() {
        return this.message;
    }

    public void setMessage(TBPushMessage tBPushMessage) {
        this.message = tBPushMessage;
    }

    public int getOpenType() {
        return this.openType;
    }

    public void setOpenType(int i) {
        this.openType = i;
    }

    public String getOpenActivity() {
        return this.openActivity;
    }

    public void setOpenActivity(String str) {
        this.openActivity = str;
    }

    public String getOpenUrl() {
        return this.openUrl;
    }

    public void setOpenUrl(String str) {
        this.openUrl = str;
    }
}
