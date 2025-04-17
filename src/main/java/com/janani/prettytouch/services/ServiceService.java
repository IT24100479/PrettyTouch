package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;

import java.security.Provider;
import java.util.List;

public class ServiceService implements Services {

    private List<Model> allServices;
    private static ServiceService service;

    private ServiceService() {
        this.ReadAll();
    }
    public static ServiceService getInstance() {
        if (service == null) {
            service = new ServiceService();
        }
        return service;
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
