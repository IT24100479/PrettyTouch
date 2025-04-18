package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.UserModel;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UserService implements Services{
    private List<Model> allUsers;
    private static UserService userService;

    private UserService() {
        this.readAll();
    }
    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
    public UserModel checkLogin(String username, String password) {
        if (username != null && password != null) {
            for (int i = 0; i < allUsers.size(); i++) {
                UserModel user = (UserModel)allUsers.get(i);
                if (username.equalsIgnoreCase(user.getUsername()) &&
                password.equalsIgnoreCase(user.getPassword()) &&
                user.getStatus()) {
                    return user;
                }
            }
        }
        return null;
    }
    @Override
    public void readAll() {
        allUsers = new ArrayList<Model>();
        String filePath = FIleConst.FILE_PATH + FIleConst.USER_FILE;
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(filePath);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (int i = 0; i < allData.size(); i++) {
                String[] row = allData.get(i);
                if (row.length >= 11) {
                    UserModel user = new UserModel(row[0],
                            row[1],row[2],row[3],row[4],row[5],
                            row[6],row[7],row[8],row[9],row[10]
                            );
                    allUsers.add(user);
                }
            }
            csvReader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
    public boolean add(Model user) {
        //ToDo find user from allUsers list and add data same time it need to add data in csv file
        return true;
    }
    @Override
    public boolean update(Model user) {
        //ToDo find user from allUsers list and update data same time it need to update data in csv file
        return true;
    }
    @Override
    public boolean delete(int id) {
        //ToDo soft delete only status change 1 to 0
        return true;
    }

    @Override
    public boolean updateFile() {
        return true;
    }

}
