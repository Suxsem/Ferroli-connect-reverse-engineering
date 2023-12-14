package anet.channel.strategy;

import kotlin.jvm.internal.LongCompanionObject;

/* compiled from: Taobao */
public class ConnEvent {
    public long connTime = LongCompanionObject.MAX_VALUE;
    public boolean isAccs = false;
    public boolean isSuccess = false;

    public String toString() {
        return this.isSuccess ? "ConnEvent#Success" : "ConnEvent#Fail";
    }
}
