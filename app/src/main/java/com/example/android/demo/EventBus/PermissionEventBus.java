package com.example.android.demo.EventBus;

/**
 * Created by android on 2018/11/5.
 */

public class PermissionEventBus {
    String type;
    String code;

    public PermissionEventBus(String type,String code){
        this.type = type;
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
