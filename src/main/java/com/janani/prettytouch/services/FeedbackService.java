package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;

import java.util.List;

public class FeedbackService implements Services{

    private List<Model> allServices;
    private static FeedbackService feedbackService;

    private FeedbackService() {
        this.readAll();
    }
    public static FeedbackService getInstance() {
        if (feedbackService == null) {
            feedbackService = new FeedbackService();
        }
        return feedbackService;
    }

    @Override
    public void readAll() {

    }

    @Override
    public List<Model> getAll() {
        return List.of();
    }

    @Override
    public Model getById(int id) {
        return null;
    }

    @Override
    public boolean add(Model model) {
        return false;
    }

    @Override
    public boolean update(Model model) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean updateFile() {
        return true;
    }
}
