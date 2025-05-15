package com.janani.prettytouch.model.responces;

public class LoginResponseModel {
    private String msg;

    public LoginResponseModel(String msg) {
        this.msg = msg;
    }

    public LoginResponseModel() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
