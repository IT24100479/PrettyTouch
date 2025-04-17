package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;

import java.util.List;

public class AppointmentService implements Services{

    private List<Model> allServices;
    private static AppointmentService appointmentService;

    private AppointmentService() {
        this.ReadAll();
    }
    public static AppointmentService getInstance() {
        if (appointmentService == null) {
            appointmentService = new AppointmentService();
        }
        return appointmentService;
    }

    @Override
    public void ReadAll() {

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
    public boolean Add(Model model) {
        return false;
    }

    @Override
    public boolean Update(Model model) {
        return false;
    }

    @Override
    public boolean Delete(Model model) {
        return false;
    }
}
