package com.taobao.agoo;

import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public abstract class IListAliasCallbackInner extends IListAliasCallback {
    public void onSuccess(List<String> list) {
    }

    public abstract void onSuccess(Map<String, String> map);
}
