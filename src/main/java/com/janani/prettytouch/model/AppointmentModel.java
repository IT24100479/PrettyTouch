package com.janani.prettytouch.model;


import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.services.UserService;
import com.janani.prettytouch.util.TypeConverter;

import java.time.LocalDate;

public class AppointmentModel extends Model {
    private int serviceId;
    private LocalDate date;
    private int timeSlotId;
    private String requestData;
    private int userId;

    public AppointmentModel(String id, String createdBy, String createdAt, String status, String serviceId, String date, String timeSlotId, String requestData,String userId) {
        super(id, createdBy, createdAt, status);
        setServiceId(serviceId);
        setDate(date);
        setTimeSlotId(timeSlotId);
        setUserId(userId);
        this.requestData = requestData;
    }

    public AppointmentModel() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public UserModel getUser(){
        return (UserModel) UserService.getInstance().getById(this.userId);
    }

    public void setUserId(String userId) {
        this.userId = TypeConverter.stringToInt(userId);
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(serviceId), String.valueOf(date), String.valueOf(timeSlotId), String.valueOf(requestData),String.valueOf(userId)};
    }

    @Override
    public boolean validate() {
        return (this.serviceId != 0 && this.date != null && this.userId!=0);
    }

    public int getServiceId() {
        return serviceId;
    }

    public ServiceModel getService() {
        return (ServiceModel) ServiceService.getInstance().getById(serviceId);
    }

    public void setServiceId(String serviceId) {
        this.serviceId = TypeConverter.stringToInt(serviceId);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = TypeConverter.stringToLocalDate(date);
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(String timeSlotId) {
        this.timeSlotId = TypeConverter.stringToInt(timeSlotId);
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }



}
