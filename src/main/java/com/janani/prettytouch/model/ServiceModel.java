package com.janani.prettytouch.model;

import com.janani.prettytouch.util.TypeConverter;

public class ServiceModel extends Model {
    private String type;
    private String serviceName;
    private String description;
    private double printPrice;
    private double realPrice;
    private String imageUrl;

    public ServiceModel(String id, String createdBy, String createdAt, String status, String type, String serviceName, String description, String printPrice, String realPrice, String imageUrl) {
        super(id, createdBy, createdAt, status);
        this.type = type;
        this.serviceName = serviceName;
        this.description = description;
        setPrintPrice(printPrice);
        setRealPrice(realPrice);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ServiceModel() {
        super();
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(type), String.valueOf(serviceName), String.valueOf(description), String.valueOf(printPrice), String.valueOf(realPrice), String.valueOf(imageUrl)};
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
        this.printPrice = TypeConverter.stringToDouble(printPrice);
    }

    public double getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = TypeConverter.stringToDouble(realPrice);
    }


}
