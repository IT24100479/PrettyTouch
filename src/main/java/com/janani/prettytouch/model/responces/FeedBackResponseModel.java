package com.janani.prettytouch.model.responces;

import com.janani.prettytouch.model.FeedbackModel;

import java.util.List;

public class FeedBackResponseModel {
    private List<FeedbackModel> feedbacks;
    private int nextStart;

    public FeedBackResponseModel(List<FeedbackModel> feedbacks, int nextStart) {
        this.feedbacks = feedbacks;
        this.nextStart = nextStart;
    }

    public FeedBackResponseModel() {
    }

    public int getNextStart() {
        return nextStart;
    }

    public void setNextStart(int nextStart) {
        this.nextStart = nextStart;
    }

    public List<FeedbackModel> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackModel> feedbacks) {
        this.feedbacks = feedbacks;
    }
}

