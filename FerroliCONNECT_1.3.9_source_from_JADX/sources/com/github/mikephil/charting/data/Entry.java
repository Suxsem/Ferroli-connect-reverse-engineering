package com.github.mikephil.charting.data;

import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

public class Entry implements Parcelable {
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
        public Entry createFromParcel(Parcel parcel) {
            return new Entry(parcel);
        }

        public Entry[] newArray(int i) {
            return new Entry[i];
        }
    };
    private Object mData;
    private float mVal;
    private int mXIndex;

    public int describeContents() {
        return 0;
    }

    public Entry(float f, int i) {
        this.mVal = 0.0f;
        this.mXIndex = 0;
        this.mData = null;
        this.mVal = f;
        this.mXIndex = i;
    }

    public Entry(float f, int i, Object obj) {
        this(f, i);
        this.mData = obj;
    }

    public int getXIndex() {
        return this.mXIndex;
    }

    public void setXIndex(int i) {
        this.mXIndex = i;
    }

    public float getVal() {
        return this.mVal;
    }

    public void setVal(float f) {
        this.mVal = f;
    }

    public Object getData() {
        return this.mData;
    }

    public void setData(Object obj) {
        this.mData = obj;
    }

    public Entry copy() {
        return new Entry(this.mVal, this.mXIndex, this.mData);
    }

    public boolean equalTo(Entry entry) {
        if (entry != null && entry.mData == this.mData && entry.mXIndex == this.mXIndex && Math.abs(entry.mVal - this.mVal) <= 1.0E-5f) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Entry, xIndex: " + this.mXIndex + " val (sum): " + getVal();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mVal);
        parcel.writeInt(this.mXIndex);
        Object obj = this.mData;
        if (obj == null) {
            parcel.writeInt(0);
        } else if (obj instanceof Parcelable) {
            parcel.writeInt(1);
            parcel.writeParcelable((Parcelable) this.mData, i);
        } else {
            throw new ParcelFormatException("Cannot parcel an Entry with non-parcelable data");
        }
    }

    protected Entry(Parcel parcel) {
        this.mVal = 0.0f;
        this.mXIndex = 0;
        this.mData = null;
        this.mVal = parcel.readFloat();
        this.mXIndex = parcel.readInt();
        if (parcel.readInt() == 1) {
            this.mData = parcel.readParcelable(Object.class.getClassLoader());
        }
    }
}
