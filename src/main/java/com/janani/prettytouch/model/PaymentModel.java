package com.janani.prettytouch.model;

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

    public int getAppointmentId() {
        return appointmentId;
    }

    public AppointmentModel getAppointment() {
        //ToDo return appointment object using id
        return null;
    }

    public Double getBalance (){
        return cash - amount;
    }

    public void setAppointmentId(String appointmentId) {
        try{
            this.appointmentId = Integer.parseInt(appointmentId);
        }catch (Exception e){
            e.printStackTrace();
            this.appointmentId = 0;
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        try{
            this.amount = Double.parseDouble(amount);
        }catch (Exception e){
            e.printStackTrace();
            this.amount = 0.0;
        }
    }

    public double getCash() {
        return cash;
    }

    public void setCash(String cash) {
        try{
            this.cash = Double.parseDouble(cash);
        }catch (Exception e){
            e.printStackTrace();
            this.cash = 0.0;
        }
    }
}
