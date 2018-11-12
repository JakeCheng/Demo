package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseModel<T> implements Parcelable{
    private String reason;
    private int error_code;
    private T Data;

    protected BaseModel(Parcel in) {
        reason = in.readString();
        error_code = in.readInt();
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reason);
        dest.writeInt(error_code);
    }
}
