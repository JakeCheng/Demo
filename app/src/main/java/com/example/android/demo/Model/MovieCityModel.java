package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2018/10/30.
 */

public class MovieCityModel extends BaseModel{
    private List<ResultBean> result;

    protected MovieCityModel(Parcel in) {
        super(in);
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


    public static class ResultBean implements Parcelable{
        private String id;
        private String city_name;
        private String city_pre;
        private String city_pinyin;
        private String city_short;
        private String count;

        public ResultBean(Parcel in) {
            id = in.readString();
            city_name = in.readString();
            city_pre = in.readString();
            city_pinyin = in.readString();
            city_short = in.readString();
            count = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_pre() {
            return city_pre;
        }

        public void setCity_pre(String city_pre) {
            this.city_pre = city_pre;
        }

        public String getCity_pinyin() {
            return city_pinyin;
        }

        public void setCity_pinyin(String city_pinyin) {
            this.city_pinyin = city_pinyin;
        }

        public String getCity_short() {
            return city_short;
        }

        public void setCity_short(String city_short) {
            this.city_short = city_short;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(city_name);
            dest.writeString(city_pre);
            dest.writeString(city_pinyin);
            dest.writeString(city_short);
            dest.writeString(count);
        }
    }
}
