package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;

import java.util.List;

public class PaymentService implements Services{

    private List<Model> allServices;
    private static PaymentService paymentService;

    private PaymentService() {
        this.ReadAll();
    }
    public static PaymentService getInstance() {
        if (paymentService == null) {
            paymentService = new PaymentService();
        }
        return paymentService;
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
