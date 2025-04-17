package com.janani.prettytouch.model;


import java.time.LocalDate;

public class AppointmentModel extends Model {
    private int serviceId;
    private LocalDate date;
    private int timeSlotId;
    private String requestData;

    public AppointmentModel(String id, String createdBy, String createdAt, String status, String serviceId, String date, String timeSlotId, String requestData) {
        super(id, createdBy, createdAt, status);
        setServiceId(serviceId);
        setDate(date);
        setTimeSlotId(timeSlotId);
        this.requestData = requestData;
    }

    public AppointmentModel() {
        super();
    }

    public int getServiceId() {
        return serviceId;
    }

    public ServiceModel getService() {
        //ToDo return service object using id
        return null;
    }

    public void setServiceId(String serviceId) {
        try {
            this.serviceId = Integer.parseInt(serviceId);
        } catch (Exception e) {
            e.printStackTrace();
            this.serviceId = 0;
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        try{
            this.date = LocalDate.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            this.date=null;
        }
    }

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(String timeSlotId) {
        try {
            this.timeSlotId = Integer.parseInt(timeSlotId);
        } catch (Exception e) {
            e.printStackTrace();
            this.timeSlotId = 0;
        }
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }
}
