package com.janani.prettytouch.model;

public class FeedbackModel extends Model {
    private int rating;
    private String comment;

    public FeedbackModel(String id, String createdBy, String createdAt, String status, String rating, String comment) {
        super(id, createdBy, createdAt, status);
        setRating(rating);
        this.comment = comment;
    }

    public FeedbackModel() {
        super();
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(rating), String.valueOf(comment)};
    }

    public int getRating() {
        return rating;
    }

    public void setRating(String rating) {
        try{
            this.rating = Integer.parseInt(rating);
        }catch (Exception e){
            e.printStackTrace();
            this.rating = 0;
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
