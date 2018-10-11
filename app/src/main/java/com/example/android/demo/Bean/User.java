package com.example.android.demo.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 2018/10/8.
 */

public class User implements Parcelable{
    String id;
    String name;
    String sex;

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        sex = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(sex);
    }
}
