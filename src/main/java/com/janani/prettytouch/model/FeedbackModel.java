package com.janani.prettytouch.model;

import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.util.TypeConverter;

public class FeedbackModel extends Model {
    private int rating;
    private String comment;
    private int AppointmentId;

    public FeedbackModel(String id, String createdBy, String createdAt, String status, String rating, String comment,String appointmentId) {
        super(id, createdBy, createdAt, status);
        setRating(rating);
        setAppointmentId(appointmentId);
        this.comment = comment;
    }

    public FeedbackModel() {super();}

    public int getAppointmentId(){return AppointmentId;}

    public AppointmentModel getAppointment(){
        return (AppointmentModel) AppointmentService.getInstance().getById(AppointmentId);
    }
    public void setAppointmentId(String appointmentId){
        AppointmentId = TypeConverter.stringToInt(appointmentId);
    }

    public  ServiceModel getService(){
        return (ServiceModel) ServiceService.getInstance().getById(this.getAppointmentId());
    }
    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(rating), String.valueOf(comment)};
    }

    @Override
    public boolean validate() {
        return false;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = TypeConverter.stringToInt(rating);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
