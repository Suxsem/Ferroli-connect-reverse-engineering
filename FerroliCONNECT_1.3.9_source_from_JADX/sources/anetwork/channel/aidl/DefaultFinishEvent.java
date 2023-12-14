package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ErrorConstant;
import anetwork.channel.NetworkEvent;
import anetwork.channel.statist.StatisticData;

/* compiled from: Taobao */
public class DefaultFinishEvent implements Parcelable, NetworkEvent.FinishEvent {
    public static final Parcelable.Creator<DefaultFinishEvent> CREATOR = new C0613a();

    /* renamed from: a */
    Object f599a;

    /* renamed from: b */
    int f600b;

    /* renamed from: c */
    String f601c;

    /* renamed from: d */
    StatisticData f602d;
    public final Request request;

    /* renamed from: rs */
    public final RequestStatistic f603rs;

    public int describeContents() {
        return 0;
    }

    public Object getContext() {
        return this.f599a;
    }

    public void setContext(Object obj) {
        this.f599a = obj;
    }

    public int getHttpCode() {
        return this.f600b;
    }

    public String getDesc() {
        return this.f601c;
    }

    public StatisticData getStatisticData() {
        return this.f602d;
    }

    public DefaultFinishEvent(int i) {
        this(i, (String) null, (Request) null, (RequestStatistic) null);
    }

    public DefaultFinishEvent(int i, String str, RequestStatistic requestStatistic) {
        this(i, str, (Request) null, requestStatistic);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DefaultFinishEvent(int i, String str, Request request2) {
        this(i, str, request2, request2 != null ? request2.f322a : null);
    }

    private DefaultFinishEvent(int i, String str, Request request2, RequestStatistic requestStatistic) {
        this.f602d = new StatisticData();
        this.f600b = i;
        this.f601c = str == null ? ErrorConstant.getErrMsg(i) : str;
        this.request = request2;
        this.f603rs = requestStatistic;
    }

    public String toString() {
        return "DefaultFinishEvent [" + "code=" + this.f600b + ", desc=" + this.f601c + ", context=" + this.f599a + ", statisticData=" + this.f602d + "]";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f600b);
        parcel.writeString(this.f601c);
        StatisticData statisticData = this.f602d;
        if (statisticData != null) {
            parcel.writeSerializable(statisticData);
        }
    }

    /* renamed from: a */
    static DefaultFinishEvent m365a(Parcel parcel) {
        DefaultFinishEvent defaultFinishEvent = new DefaultFinishEvent(0);
        try {
            defaultFinishEvent.f600b = parcel.readInt();
            defaultFinishEvent.f601c = parcel.readString();
            defaultFinishEvent.f602d = (StatisticData) parcel.readSerializable();
        } catch (Throwable unused) {
        }
        return defaultFinishEvent;
    }
}
