package com.janani.prettytouch.model;

public class ServiceModel extends Model {
    private String type;
    private String serviceName;
    private String description;
    private double printPrice;
    private double realPrice;

    public ServiceModel(String id, String createdBy, String createdAt, String status, String type, String serviceName, String description, String printPrice, String realPrice) {
        super(id, createdBy, createdAt, status);
        this.type = type;
        this.serviceName = serviceName;
        this.description = description;
        setPrintPrice(printPrice);
        setRealPrice(realPrice);    }

    public ServiceModel() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(String printPrice) {
        try{
            this.printPrice = Double.parseDouble(printPrice);
        }catch (Exception e){
            e.printStackTrace();
            this.printPrice = 0.0;
        }
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        try{
            this.realPrice = Double.parseDouble(realPrice);
        }catch (Exception e){
            e.printStackTrace();
            this.realPrice = 0.0;
        }
    }


}
