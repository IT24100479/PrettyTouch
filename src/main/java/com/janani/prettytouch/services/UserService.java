package com.janani.prettytouch.services;

import com.janani.prettytouch.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<UserModel> allUsers;
    private static UserService userService;

    private UserService() {
        this.ReadAllUsers();
    }
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    private void ReadAllUsers() {
        //TODO read all users form csv file and file allUsers list
        allUsers = new ArrayList<UserModel>();
    }

    public List<UserModel> getAllUsers() {
        return allUsers;
    }

    public UserModel getUserById(int id) {
        //ToDo find user from allUsers list and return
        return allUsers.getFirst();
    }

    public boolean AddUser(UserModel user) {
        //ToDo find user from allUsers list and add data same time it need to add data in csv file
        return true;
    }
    public boolean UpdateUser(UserModel user) {
        //ToDo find user from allUsers list and update data same time it need to update data in csv file
        return true;
    }
    public boolean DeleteUser(UserModel user) {
        //ToDo soft delete only status change 1 to 0
        return true;
    }

}
