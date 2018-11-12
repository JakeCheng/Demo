package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2018/11/9.
 */

public class MovieCinameModel extends BaseModel{

    private List<ResultBean> result;

    protected MovieCinameModel(Parcel in) {
        super(in);
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {

        private String id;
        private String cityName;
        private String cinemaName;
        private String address;
        private String telephone;
        private String latitude;
        private String longitude;
        private String trafficRoutes;
        private int distance;

        protected ResultBean(Parcel in) {
            id = in.readString();
            cityName = in.readString();
            cinemaName = in.readString();
            address = in.readString();
            telephone = in.readString();
            latitude = in.readString();
            longitude = in.readString();
            trafficRoutes = in.readString();
            distance = in.readInt();
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

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getTrafficRoutes() {
            return trafficRoutes;
        }

        public void setTrafficRoutes(String trafficRoutes) {
            this.trafficRoutes = trafficRoutes;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(cityName);
            dest.writeString(cinemaName);
            dest.writeString(address);
            dest.writeString(telephone);
            dest.writeString(latitude);
            dest.writeString(longitude);
            dest.writeString(trafficRoutes);
            dest.writeInt(distance);
        }
    }
}
