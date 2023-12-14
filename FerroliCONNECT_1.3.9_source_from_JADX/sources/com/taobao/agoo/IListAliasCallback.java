package com.taobao.agoo;

import java.util.List;

/* compiled from: Taobao */
public abstract class IListAliasCallback extends ICallback {
    public final void onSuccess() {
    }

    public abstract void onSuccess(List<String> list);
}
