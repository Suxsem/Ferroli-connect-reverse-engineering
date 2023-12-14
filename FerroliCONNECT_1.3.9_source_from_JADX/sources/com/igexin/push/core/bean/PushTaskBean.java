package com.igexin.push.core.bean;

import android.text.TextUtils;
import com.igexin.push.core.C1343f;
import java.util.List;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

public class PushTaskBean {

    /* renamed from: a */
    private String f1923a;

    /* renamed from: b */
    private String f1924b;

    /* renamed from: c */
    private String f1925c;

    /* renamed from: d */
    private String f1926d;

    /* renamed from: e */
    private String f1927e;

    /* renamed from: f */
    private List<BaseAction> f1928f;

    /* renamed from: g */
    private byte[] f1929g;

    /* renamed from: h */
    private String f1930h;

    /* renamed from: i */
    private String f1931i;

    /* renamed from: j */
    private int f1932j;

    /* renamed from: k */
    private int f1933k;

    /* renamed from: l */
    private boolean f1934l = false;

    /* renamed from: m */
    private boolean f1935m = false;

    /* renamed from: n */
    private boolean f1936n = false;

    /* renamed from: o */
    private Map<String, String> f1937o;

    /* renamed from: p */
    private int f1938p;

    /* renamed from: q */
    private int f1939q;

    public String getAction() {
        return this.f1923a;
    }

    public List<BaseAction> getActionChains() {
        return this.f1928f;
    }

    public String getAppKey() {
        return this.f1931i;
    }

    public String getAppid() {
        return this.f1924b;
    }

    public BaseAction getBaseAction(String str) {
        for (BaseAction next : getActionChains()) {
            if (next.getActionId().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public Map<String, String> getConditionMap() {
        return this.f1937o;
    }

    public int getCurrentActionid() {
        return this.f1932j;
    }

    public int getExecuteTimes() {
        return this.f1939q;
    }

    public String getId() {
        return this.f1925c;
    }

    public String getMessageId() {
        return this.f1926d;
    }

    public String getMsgAddress() {
        return this.f1930h;
    }

    public byte[] getMsgExtra() {
        return this.f1929g;
    }

    public int getPerActionid() {
        return this.f1933k;
    }

    public int getStatus() {
        return this.f1938p;
    }

    public String getTaskId() {
        return this.f1927e;
    }

    public boolean isCDNType() {
        return this.f1936n;
    }

    public boolean isHttpImg() {
        return this.f1934l;
    }

    public boolean isStop() {
        return this.f1935m;
    }

    public void parse(JSONObject jSONObject) {
        String string = jSONObject.getString(AgooConstants.MESSAGE_ID);
        String string2 = jSONObject.getString("appid");
        String string3 = jSONObject.getString("messageid");
        String string4 = jSONObject.getString("taskid");
        String string5 = jSONObject.has("appkey") ? jSONObject.getString("appkey") : null;
        if (string2 != null && string != null && string3 != null && string4 != null && string2.equals(C1343f.f2135a)) {
            setAppid(string2);
            setMessageId(string3);
            setTaskId(string4);
            setId(string);
            if (TextUtils.isEmpty(string5)) {
                string5 = C1343f.f2165b;
            }
            setAppKey(string5);
            setCurrentActionid(1);
            if (jSONObject.has("cdnType")) {
                setCDNType(jSONObject.getBoolean("cdnType"));
            }
        }
    }

    public void setAction(String str) {
        this.f1923a = str;
    }

    public void setActionChains(List<BaseAction> list) {
        this.f1928f = list;
    }

    public void setAppKey(String str) {
        this.f1931i = str;
    }

    public void setAppid(String str) {
        this.f1924b = str;
    }

    public void setCDNType(boolean z) {
        this.f1936n = z;
    }

    public void setConditionMap(Map<String, String> map) {
        this.f1937o = map;
    }

    public void setCurrentActionid(int i) {
        this.f1932j = i;
    }

    public void setExecuteTimes(int i) {
        this.f1939q = i;
    }

    public void setHttpImg(boolean z) {
        this.f1934l = z;
    }

    public void setId(String str) {
        this.f1925c = str;
    }

    public void setMessageId(String str) {
        this.f1926d = str;
    }

    public void setMsgAddress(String str) {
        this.f1930h = str;
    }

    public void setMsgExtra(byte[] bArr) {
        this.f1929g = bArr;
    }

    public void setPerActionid(int i) {
        this.f1933k = i;
    }

    public void setStatus(int i) {
        this.f1938p = i;
    }

    public void setStop(boolean z) {
        this.f1935m = z;
    }

    public void setTaskId(String str) {
        this.f1927e = str;
    }
}
