package com.janani.prettytouch.services;

import com.janani.prettytouch.constVar.FIleConst;
import com.janani.prettytouch.model.Model;
import com.janani.prettytouch.model.ServiceModel;
import com.janani.prettytouch.model.UserModel;
import com.janani.prettytouch.util.QuickSort;
import com.janani.prettytouch.util.TypeConverter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserService implements Services{
    private List<Model> allUsers;
    private static UserService userService;
    private final QuickSort quickSort;

    private UserService() {
        this.readAll();
        this.quickSort = new QuickSort();
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
                password.equals(user.getPassword()) &&
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
                if (row.length >= 12) {
                    UserModel user = new UserModel(row[0],
                            row[1],row[2],row[3],row[4],row[5],
                            row[6],row[7],row[8],row[9],row[10],row[11]
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
        return this.allUsers;
    }
    @Override
    public Model getById(int id) {
        for (int i = 0; i < allUsers.size(); i++) {
            UserModel user = (UserModel)allUsers.get(i);
            if (id == user.getId()) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean add(Model user) {
       UserModel userModel=(UserModel) user;
       userModel.checkCreatedAtAndID(allUsers);
       allUsers.add(userModel);
       Model[] temp = this.allUsers.toArray(Model[]::new);
        this.quickSort.quickSort(temp, 0, this.allUsers.size() - 1,"id");
       this.allUsers = new ArrayList<>(Arrays.asList(temp));
       return this.updateFile();
    }
    @Override
    public boolean update(Model user) {
        // find user from allUsers list and update data same time it need to update data in csv file
        for(int i =0;i<this.allUsers.size();i++){
            if(this.allUsers.get(i).getId()== user.getId()){
                this.allUsers.remove(i);
                return this.add(user);
            }
        }
        return false;
    }
    @Override
    public boolean delete(int id) {
        // soft delete only status change 1 to 0
        for(int i=0;i<this.allUsers.size();i++){
            if(this.allUsers.get(i).getId()==id){
                this.allUsers.get(i).setStatus("0");
                return this.update(this.allUsers.get(i));
            }
        }
        return false;
    }

    @Override
    public boolean updateFile() {
        String filePath = FIleConst.FILE_PATH + FIleConst.USER_FILE;
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filePath));
            writer.writeNext(FIleConst.USER_HEADERS);
            for (int i = 0; i < this.allUsers.size(); i++) {
                writer.writeNext(this.allUsers.get(i).getCSVLine());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public boolean isDuplicate(UserModel userModel){
        for (int i = 0; i < this.allUsers.size(); i++) {
            UserModel model = (UserModel) this.allUsers.get(i);
            if(model.getStatus() && model.getId()!=userModel.getId() && (model.getUsername().equalsIgnoreCase(userModel.getUsername())
                    || model.getEmail().equalsIgnoreCase(userModel.getEmail())
                    || model.getPhoneNumber().equals(userModel.getPhoneNumber()))) {
                return true;
            }
        }
        return false;
    }
    public String createReturnUrl(String base, String path, UserModel model, String error){
        String url = base+path;
        if(model!=null){
            url+="?uid="+model.getId();
            url+="&uName="+model.getUsername();
            url+="&fName="+model.getFirstName();
            url+="&lName="+model.getLastName();
            url+="&role="+model.getRole();
            url+="&pNumber="+model.getPhoneNumber();
            url+="&email="+model.getEmail();
            url+="&dob="+model.getDob();

        }
        if(TypeConverter.stringIsNotEmpty(error)){
            url+=(model!=null?"&error=":"?error=")+error;
        }
        return url;
    }
    public List<UserModel> getUserWithFilter(String type) {
        List<UserModel> data = new ArrayList<>();
        type=type+"";
        for (int i = 0; i < this.allUsers.size(); i++) {
            UserModel model = (UserModel) this.allUsers.get(i);
            if (model.getStatus() && (TypeConverter.stringIsEmpty(type) || type.equalsIgnoreCase(model.getRole()))) {
                data.add(model);
            }
        }return data;
    }

}
