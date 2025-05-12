package com.janani.prettytouch.model.responces;

public class PaymentResponseModel {
    private String msg;

    public PaymentResponseModel(String msg) {
        this.msg = msg;
    }

    public PaymentResponseModel() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
