package com.janani.prettytouch.services;

import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Services{
    private List<Model> allUsers;
    private static UserService userService;

    private UserService() {
        this.ReadAll();
    }
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
    @Override
    public void ReadAll() {
        //TODO read all users form csv file and file allUsers list
        allUsers = new ArrayList<Model>();
    }
    @Override
    public List<Model> getAll() {
        return allUsers;
    }
    @Override
    public Model getById(int id) {
        //ToDo find user from allUsers list and return
        return allUsers.getFirst();
    }

    @Override
    public boolean Add(Model user) {
        //ToDo find user from allUsers list and add data same time it need to add data in csv file
        return true;
    }
    @Override
    public boolean Update(Model user) {
        //ToDo find user from allUsers list and update data same time it need to update data in csv file
        return true;
    }
    @Override
    public boolean Delete(Model user) {
        //ToDo soft delete only status change 1 to 0
        return true;
    }

}
