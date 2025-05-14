package com.janani.prettytouch.model;

import com.janani.prettytouch.services.AppointmentService;
import com.janani.prettytouch.services.ServiceService;
import com.janani.prettytouch.util.TypeConverter;

public class FeedbackModel extends Model {
    private int rating;
    private int AppointmentId;
    private String comment;
    private String shortComment;
    private UserModel createdByFullUser;

    public FeedbackModel(String id, String createdBy, String createdAt, String status, String rating, String comment, String appointmentId) {
        super(id, createdBy, createdAt, status);
        setRating(rating);
        setAppointmentId(appointmentId);
        this.comment = comment;
    }

    public FeedbackModel() {
        super();
    }

    public FeedbackModel(String id, String createdBy, String createdAt, String status, int rating, int appointmentId, String comment, UserModel createdByFullUser,String shortComment) {
        super(id, createdBy, createdAt, status);
        this.rating = rating;
        AppointmentId = appointmentId;
        this.comment = comment;
        this.shortComment = shortComment;
        this.createdByFullUser = createdByFullUser;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(int size) {
        if(TypeConverter.stringIsNotEmpty(comment) && comment.length()>size) {
            this.shortComment = comment.substring(0, size)+"...";
        }else{
            this.shortComment = comment;
        }
    }

    public UserModel getCreatedByFullUser() {
        return createdByFullUser;
    }

    public void setCreatedByFullUser() {
        this.createdByFullUser = super.getCreatedByUser();
    }

    public int getAppointmentId() {
        return AppointmentId;
    }

    public AppointmentModel getAppointment() {
        return (AppointmentModel)AppointmentService.getInstance().getById(AppointmentId);
    }

    public ServiceModel getService() {
        return (ServiceModel) ServiceService.getInstance().getById(this.getAppointment().getServiceId());
    }

    public void setAppointmentId(String appointmentId) {
        AppointmentId = TypeConverter.stringToInt(appointmentId);
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(rating), String.valueOf(comment),String.valueOf(AppointmentId)};
    }

    @Override
    public boolean validate() {
        return rating>-1 && rating<6;
    }

    public String getCommentShort() {
        if(TypeConverter.stringIsNotEmpty(comment) && comment.length()>50) {
            return comment.substring(0, 50)+"...";
        }
        return comment;
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
