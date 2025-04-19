package com.janani.prettytouch.model;

import com.janani.prettytouch.util.TypeConverter;

import java.time.LocalDate;

public class UserModel extends Model {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber;
    private LocalDate dob;

    public UserModel(String id, String createdBy, String createdAt, String status, String username, String password, String firstName, String lastName, String role, String phoneNumber, String dob) {
        super(id, createdBy, createdAt, status);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        setDOB(dob);
    }

    public UserModel() {
        super();
    }

    @Override
    public String[] getCSVLine() {
        return new String[]{String.valueOf(id), String.valueOf(createdBy), String.valueOf(createdAt), String.valueOf(status), String.valueOf(username), String.valueOf(password), String.valueOf(firstName), String.valueOf(lastName), String.valueOf(role), String.valueOf(phoneNumber), String.valueOf(dob)};
    }

    @Override
    public boolean validate() {
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = TypeConverter.stringToLocalDate(dob);
    }
}
