package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2018/10/10.
 */

public class DemoItemBean{

    public static final int ITEM_TYPE_1 = 1;
    public static final int ITEM_TYPE_2 = 2;

    private int errCode;

    private List<DateBean> data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
    public List<DateBean> getData() {
        return data;
    }

    public void setData(List<DateBean> data) {
        this.data = data;
    }

    public static class DateBean implements Parcelable {
        private String name;
        private int age;
        private int type;


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DateBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.age);
            dest.writeInt(this.type);
        }

        protected DateBean(Parcel in) {
            this.name = in.readString();
            this.age = in.readInt();
            this.type = in.readInt();
        }

        public static final Creator<DateBean> CREATOR = new Creator<DateBean>() {
            @Override
            public DateBean createFromParcel(Parcel source) {
                return new DateBean(source);
            }

            @Override
            public DateBean[] newArray(int size) {
                return new DateBean[size];
            }
        };
    }
}
