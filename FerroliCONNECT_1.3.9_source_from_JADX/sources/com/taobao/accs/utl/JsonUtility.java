package com.taobao.accs.utl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class JsonUtility {
    public static String getString(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (jSONObject == null || !jSONObject.has(str)) {
            return str2;
        }
        String optString = jSONObject.optString(str, (String) null);
        return optString == null ? str2 : optString;
    }

    public static Map<String, String> getMap(JSONObject jSONObject, String str) {
        HashMap hashMap = null;
        if (jSONObject == null) {
            return null;
        }
        if (jSONObject.has(str)) {
            JSONObject optJSONObject = jSONObject.optJSONObject(str);
            Iterator<String> keys = optJSONObject.keys();
            hashMap = new HashMap();
            while (keys.hasNext()) {
                String next = keys.next();
                hashMap.put(next, optJSONObject.optString(next));
            }
        }
        return hashMap;
    }

    public static Map<String, String> toMap(JSONObject jSONObject) throws JSONException {
        HashMap hashMap = new HashMap();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            hashMap.put(next, jSONObject.get(next) == null ? null : jSONObject.get(next).toString());
        }
        return hashMap;
    }

    /* compiled from: Taobao */
    public static class JsonObjectBuilder {
        JSONObject jObject = new JSONObject();

        public JsonObjectBuilder put(String str, String str2) {
            if (!(str2 == null || str == null)) {
                try {
                    this.jObject.put(str, str2);
                } catch (JSONException unused) {
                }
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Integer num) {
            if (num == null) {
                return this;
            }
            try {
                this.jObject.put(str, num);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Boolean bool) {
            if (bool == null) {
                return this;
            }
            try {
                this.jObject.put(str, bool);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, Long l) {
            if (l == null) {
                return this;
            }
            try {
                this.jObject.put(str, l);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JsonObjectBuilder put(String str, JSONArray jSONArray) {
            if (jSONArray == null) {
                return this;
            }
            try {
                this.jObject.put(str, jSONArray);
            } catch (JSONException unused) {
            }
            return this;
        }

        public JSONObject build() {
            return this.jObject;
        }
    }
}
