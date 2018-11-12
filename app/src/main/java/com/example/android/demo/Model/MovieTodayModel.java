package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MovieTodayModel extends BaseModel{
    private List<ResultBean> result;

    protected MovieTodayModel(Parcel in) {
        super(in);
    }
    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable{
        private String movieId;
        private String movieName;
        private String pic_url;

        protected ResultBean(Parcel in) {
            movieId = in.readString();
            movieName = in.readString();
            pic_url = in.readString();
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

        public String getMovieId() {
            return movieId;
        }

        public void setMovieId(String movieId) {
            this.movieId = movieId;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(movieId);
            dest.writeString(movieName);
            dest.writeString(pic_url);
        }
    }
}
