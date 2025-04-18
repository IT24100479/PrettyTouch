package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public interface Services {
    public void readAll();
    public List<Model> getAll();
    public Model getById(int id);
    public boolean add(Model model);
    public boolean update(Model model);
    public boolean delete(int id);
    public boolean updateFile();
}
