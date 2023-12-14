package anetwork.channel.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.Response;
import anetwork.channel.statist.StatisticData;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class NetworkResponse implements Parcelable, Response {
    public static final Parcelable.Creator<NetworkResponse> CREATOR = new C0621c();

    /* renamed from: a */
    int f610a;

    /* renamed from: b */
    byte[] f611b;

    /* renamed from: c */
    private String f612c;

    /* renamed from: d */
    private Map<String, List<String>> f613d;

    /* renamed from: e */
    private Throwable f614e;

    /* renamed from: f */
    private StatisticData f615f;

    public int describeContents() {
        return 0;
    }

    public void setStatusCode(int i) {
        this.f610a = i;
        this.f612c = ErrorConstant.getErrMsg(i);
    }

    public byte[] getBytedata() {
        return this.f611b;
    }

    public void setBytedata(byte[] bArr) {
        this.f611b = bArr;
    }

    public void setConnHeadFields(Map<String, List<String>> map) {
        this.f613d = map;
    }

    public Map<String, List<String>> getConnHeadFields() {
        return this.f613d;
    }

    public void setDesc(String str) {
        this.f612c = str;
    }

    public String getDesc() {
        return this.f612c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkResponse [");
        sb.append("statusCode=");
        sb.append(this.f610a);
        sb.append(", desc=");
        sb.append(this.f612c);
        sb.append(", connHeadFields=");
        sb.append(this.f613d);
        sb.append(", bytedata=");
        byte[] bArr = this.f611b;
        sb.append(bArr != null ? new String(bArr) : "");
        sb.append(", error=");
        sb.append(this.f614e);
        sb.append(", statisticData=");
        sb.append(this.f615f);
        sb.append("]");
        return sb.toString();
    }

    public NetworkResponse() {
    }

    public NetworkResponse(int i) {
        this.f610a = i;
        this.f612c = ErrorConstant.getErrMsg(i);
    }

    public int getStatusCode() {
        return this.f610a;
    }

    public Throwable getError() {
        return this.f614e;
    }

    public void setError(Throwable th) {
        this.f614e = th;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f610a);
        parcel.writeString(this.f612c);
        byte[] bArr = this.f611b;
        int length = bArr != null ? bArr.length : 0;
        parcel.writeInt(length);
        if (length > 0) {
            parcel.writeByteArray(this.f611b);
        }
        parcel.writeMap(this.f613d);
        StatisticData statisticData = this.f615f;
        if (statisticData != null) {
            parcel.writeSerializable(statisticData);
        }
    }

    public static NetworkResponse readFromParcel(Parcel parcel) {
        NetworkResponse networkResponse = new NetworkResponse();
        try {
            networkResponse.f610a = parcel.readInt();
            networkResponse.f612c = parcel.readString();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                networkResponse.f611b = new byte[readInt];
                parcel.readByteArray(networkResponse.f611b);
            }
            networkResponse.f613d = parcel.readHashMap(NetworkResponse.class.getClassLoader());
            try {
                networkResponse.f615f = (StatisticData) parcel.readSerializable();
            } catch (Throwable unused) {
                ALog.m328i("anet.NetworkResponse", "[readFromParcel] source.readSerializable() error", (String) null, new Object[0]);
            }
        } catch (Exception e) {
            ALog.m329w("anet.NetworkResponse", "[readFromParcel]", (String) null, e, new Object[0]);
        }
        return networkResponse;
    }

    public void setStatisticData(StatisticData statisticData) {
        this.f615f = statisticData;
    }

    public StatisticData getStatisticData() {
        return this.f615f;
    }
}
