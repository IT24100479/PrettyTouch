package com.janani.prettytouch.model;

import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.util.TypeConverter;

public class PaymentModel extends Model {
    private int appointmentId;
    private double amount;
    private double cash;

    public PaymentModel(String id, String createdBy, String createdAt, String status, String appointmentId, String amount, String cash) {
        super(id, createdBy, createdAt, status);
        setAppointmentId(appointmentId);
        setAmount(amount);
        setCash(cash);
    }

    public PaymentModel() {
       super();
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(appointmentId), String.valueOf(amount), String.valueOf(cash)};
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public AppointmentModel getAppointment() {
        return (AppointmentModel) AppointmentService.getInstance().getById(appointmentId);
    }

    public Double getBalance (){
        return cash - amount;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = TypeConverter.stringToInt(appointmentId);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = TypeConverter.stringToDouble(amount);
    }

    public double getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.amount= TypeConverter.stringToDouble(cash);
    }
}
