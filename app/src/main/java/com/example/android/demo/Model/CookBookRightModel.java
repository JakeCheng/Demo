package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CookBookRightModel implements Parcelable{

    private String resultcode;
    private String reason;
    private ResultBean result;
    private int error_code;

    protected CookBookRightModel(Parcel in) {
        resultcode = in.readString();
        reason = in.readString();
        result = in.readParcelable(ResultBean.class.getClassLoader());
        error_code = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resultcode);
        dest.writeString(reason);
        dest.writeParcelable(result, flags);
        dest.writeInt(error_code);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CookBookRightModel> CREATOR = new Creator<CookBookRightModel>() {
        @Override
        public CookBookRightModel createFromParcel(Parcel in) {
            return new CookBookRightModel(in);
        }

        @Override
        public CookBookRightModel[] newArray(int size) {
            return new CookBookRightModel[size];
        }
    };

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean implements Parcelable{

        private String totalNum;
        private String pn;
        private String rn;
        private List<DataBean> data;

        protected ResultBean(Parcel in) {
            totalNum = in.readString();
            pn = in.readString();
            rn = in.readString();
            data = in.createTypedArrayList(DataBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(totalNum);
            dest.writeString(pn);
            dest.writeString(rn);
            dest.writeTypedList(data);
        }

        @Override
        public int describeContents() {
            return 0;
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

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public String getPn() {
            return pn;
        }

        public void setPn(String pn) {
            this.pn = pn;
        }

        public String getRn() {
            return rn;
        }

        public void setRn(String rn) {
            this.rn = rn;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Parcelable{
            private String id;
            private String title;
            private String tags;
            private String imtro;
            private String ingredients;
            private String burden;
            private List<String> albums;
            private List<StepsBean> steps;

            protected DataBean(Parcel in) {
                id = in.readString();
                title = in.readString();
                tags = in.readString();
                imtro = in.readString();
                ingredients = in.readString();
                burden = in.readString();
                albums = in.createStringArrayList();
                steps = in.createTypedArrayList(StepsBean.CREATOR);
            }

            public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
                @Override
                public DataBean createFromParcel(Parcel in) {
                    return new DataBean(in);
                }

                @Override
                public DataBean[] newArray(int size) {
                    return new DataBean[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public String getImtro() {
                return imtro;
            }

            public void setImtro(String imtro) {
                this.imtro = imtro;
            }

            public String getIngredients() {
                return ingredients;
            }

            public void setIngredients(String ingredients) {
                this.ingredients = ingredients;
            }

            public String getBurden() {
                return burden;
            }

            public void setBurden(String burden) {
                this.burden = burden;
            }

            public List<String> getAlbums() {
                return albums;
            }

            public void setAlbums(List<String> albums) {
                this.albums = albums;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(title);
                dest.writeString(tags);
                dest.writeString(imtro);
                dest.writeString(ingredients);
                dest.writeString(burden);
                dest.writeStringList(albums);
                dest.writeTypedList(steps);
            }

            public static class StepsBean implements Parcelable{
                private String img;
                private String step;

                protected StepsBean(Parcel in) {
                    img = in.readString();
                    step = in.readString();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(img);
                    dest.writeString(step);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<StepsBean> CREATOR = new Creator<StepsBean>() {
                    @Override
                    public StepsBean createFromParcel(Parcel in) {
                        return new StepsBean(in);
                    }

                    @Override
                    public StepsBean[] newArray(int size) {
                        return new StepsBean[size];
                    }
                };

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getStep() {
                    return step;
                }

                public void setStep(String step) {
                    this.step = step;
                }
            }
        }
    }
}
