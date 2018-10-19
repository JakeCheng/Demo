package com.example.android.demo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2018/10/18.
 */

public class CookBookModel implements Parcelable {
    private String resultcode;
    private String reason;
    private int error_code;
    private List<ResultBean> result;

    protected CookBookModel(Parcel in) {
        resultcode = in.readString();
        reason = in.readString();
        error_code = in.readInt();
        result = in.createTypedArrayList(ResultBean.CREATOR);
    }

    public static final Creator<CookBookModel> CREATOR = new Creator<CookBookModel>() {
        @Override
        public CookBookModel createFromParcel(Parcel in) {
            return new CookBookModel(in);
        }

        @Override
        public CookBookModel[] newArray(int size) {
            return new CookBookModel[size];
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

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resultcode);
        dest.writeString(reason);
        dest.writeInt(error_code);
        dest.writeTypedList(result);
    }

    public static class ResultBean implements Parcelable {
        private String parentId;
        private String name;
        private List<ListBean> list;

        protected ResultBean(Parcel in) {
            parentId = in.readString();
            name = in.readString();
            list = in.createTypedArrayList(ListBean.CREATOR);
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

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(parentId);
            dest.writeString(name);
            dest.writeTypedList(list);
        }

        public static class ListBean implements Parcelable {
            private String id;
            private String name;
            private String parentId;

            protected ListBean(Parcel in) {
                id = in.readString();
                name = in.readString();
                parentId = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
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

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(name);
                dest.writeString(parentId);
            }
        }
    }
}
