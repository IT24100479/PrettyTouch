package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public interface Services {
    public void ReadAll();
    public List<Model> getAll();
    public Model getById(int id);
    public boolean Add(Model model);
    public boolean Update(Model model);
    public boolean Delete(Model model);
}
