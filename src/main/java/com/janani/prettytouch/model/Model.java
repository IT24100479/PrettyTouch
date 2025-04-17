package com.janani.prettytouch.model;

import com.janani.prettytouch.services.UserService;

import java.time.LocalDate;

public class Model {
    private int id;
    protected int createdBy;
    protected LocalDate createdAt;
    protected boolean status;

    public Model(String id, String createdBy, String createdAt, String status) {
        setId(id);
        setCreatedBy(createdBy);
        setCreatedAt(createdAt);
        setStatus(status);
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        try{
            this.id = Integer.parseInt(id);
        }catch (Exception e){
            e.printStackTrace();
            this.id = 0;
        }
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public UserModel getCreatedByUser() {
        return (UserModel) UserService.getInstance().getById(createdBy);
    }

    public void setCreatedBy(String createdBy) {
        try{
            this.createdBy = Integer.parseInt(createdBy);
        }catch (Exception e){
            e.printStackTrace();
            this.createdBy = 0;
        }
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        try{
            this.createdAt = LocalDate.parse(createdAt);
        } catch (Exception e) {
            e.printStackTrace();
            this.createdAt=null;
        }
    }

    public boolean getStatus() {
        return status;
    }

    public String getStatusForCsv() {
        return status?"1":"0";
    }

    public void setStatus(String status) {
        this.status="1".equals(status);
    }
}
